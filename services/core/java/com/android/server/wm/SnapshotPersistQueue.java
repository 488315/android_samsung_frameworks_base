package com.android.server.wm;

import android.graphics.Bitmap;
import android.hardware.HardwareBuffer;
import android.os.Process;
import android.os.SystemClock;
import android.util.AtomicFile;
import android.util.Slog;
import android.window.TaskSnapshot;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.nano.WindowManagerProtos;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;

/* loaded from: classes3.dex */
public class SnapshotPersistQueue {
  public boolean mPaused;
  public boolean mQueueIdling;
  public boolean mStarted;
  public final ArrayDeque mWriteQueue = new ArrayDeque();
  public final ArrayDeque mStoreQueueItems = new ArrayDeque();
  public final Object mLock = new Object();
  public final Thread mPersister =
      new Thread(
          "TaskSnapshotPersister") { // from class: com.android.server.wm.SnapshotPersistQueue.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
          WriteQueueItem writeQueueItem;
          boolean z;
          Process.setThreadPriority(10);
          while (true) {
            synchronized (SnapshotPersistQueue.this.mLock) {
              if (SnapshotPersistQueue.this.mPaused) {
                writeQueueItem = null;
              } else {
                writeQueueItem = (WriteQueueItem) SnapshotPersistQueue.this.mWriteQueue.poll();
                if (writeQueueItem != null) {
                  if (writeQueueItem.isReady()) {
                    writeQueueItem.onDequeuedLocked();
                    z = true;
                  } else {
                    SnapshotPersistQueue.this.mWriteQueue.addLast(writeQueueItem);
                  }
                }
              }
              z = false;
            }
            if (writeQueueItem != null) {
              if (z) {
                writeQueueItem.write();
              }
              SystemClock.sleep(100L);
            }
            synchronized (SnapshotPersistQueue.this.mLock) {
              boolean isEmpty = SnapshotPersistQueue.this.mWriteQueue.isEmpty();
              if (isEmpty || SnapshotPersistQueue.this.mPaused) {
                try {
                  SnapshotPersistQueue.this.mQueueIdling = isEmpty;
                  SnapshotPersistQueue.this.mLock.wait();
                  SnapshotPersistQueue.this.mQueueIdling = false;
                } catch (InterruptedException unused) {
                }
              }
            }
          }
        }
      };
  public final UserManagerInternal mUserManagerInternal =
      (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);

  public Object getLock() {
    return this.mLock;
  }

  public void systemReady() {
    start();
  }

  public void start() {
    if (this.mStarted) {
      return;
    }
    this.mStarted = true;
    this.mPersister.start();
  }

  public void setPaused(boolean z) {
    synchronized (this.mLock) {
      this.mPaused = z;
      if (!z) {
        this.mLock.notifyAll();
      }
    }
  }

  public void sendToQueueLocked(WriteQueueItem writeQueueItem) {
    this.mWriteQueue.offer(writeQueueItem);
    writeQueueItem.onQueuedLocked();
    ensureStoreQueueDepthLocked();
    if (this.mPaused) {
      return;
    }
    this.mLock.notifyAll();
  }

  public final void ensureStoreQueueDepthLocked() {
    while (this.mStoreQueueItems.size() > 2) {
      StoreWriteQueueItem storeWriteQueueItem = (StoreWriteQueueItem) this.mStoreQueueItems.poll();
      this.mWriteQueue.remove(storeWriteQueueItem);
      Slog.i(
          StartingSurfaceController.TAG,
          "Queue is too deep! Purged item with index=" + storeWriteQueueItem.mId);
    }
  }

  public void deleteSnapshot(
      int i, int i2, BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
    File protoFile = persistInfoProvider.getProtoFile(i, i2);
    File lowResolutionBitmapFile = persistInfoProvider.getLowResolutionBitmapFile(i, i2);
    protoFile.delete();
    if (lowResolutionBitmapFile.exists()) {
      lowResolutionBitmapFile.delete();
    }
    File highResolutionBitmapFile = persistInfoProvider.getHighResolutionBitmapFile(i, i2);
    if (highResolutionBitmapFile.exists()) {
      highResolutionBitmapFile.delete();
    }
  }

  public abstract class WriteQueueItem {
    public final BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;

    public boolean isReady() {
      return true;
    }

    public void onDequeuedLocked() {}

    public void onQueuedLocked() {}

    public abstract void write();

    public WriteQueueItem(BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
      this.mPersistInfoProvider = persistInfoProvider;
    }
  }

  public StoreWriteQueueItem createStoreWriteQueueItem(
      int i,
      int i2,
      TaskSnapshot taskSnapshot,
      BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
    return new StoreWriteQueueItem(i, i2, taskSnapshot, persistInfoProvider);
  }

  public class StoreWriteQueueItem extends WriteQueueItem {
    public final int mId;
    public final TaskSnapshot mSnapshot;
    public final int mUserId;

    public StoreWriteQueueItem(
        int i,
        int i2,
        TaskSnapshot taskSnapshot,
        BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
      super(persistInfoProvider);
      this.mId = i;
      this.mUserId = i2;
      this.mSnapshot = taskSnapshot;
    }

    @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
    public void onQueuedLocked() {
      SnapshotPersistQueue.this.mStoreQueueItems.offer(this);
    }

    @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
    public void onDequeuedLocked() {
      SnapshotPersistQueue.this.mStoreQueueItems.remove(this);
    }

    @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
    public boolean isReady() {
      return SnapshotPersistQueue.this.mUserManagerInternal.isUserUnlocked(this.mUserId);
    }

    @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
    public void write() {
      if (!this.mPersistInfoProvider.createDirectory(this.mUserId)) {
        Slog.e(
            StartingSurfaceController.TAG,
            "Unable to create snapshot directory for user dir="
                + this.mPersistInfoProvider.getDirectory(this.mUserId));
      }
      if (writeBuffer() ? !writeProto() : true) {
        SnapshotPersistQueue.this.deleteSnapshot(this.mId, this.mUserId, this.mPersistInfoProvider);
      }
    }

    public boolean writeProto() {
      FileOutputStream fileOutputStream;
      WindowManagerProtos.TaskSnapshotProto taskSnapshotProto =
          new WindowManagerProtos.TaskSnapshotProto();
      taskSnapshotProto.orientation = this.mSnapshot.getOrientation();
      taskSnapshotProto.rotation = this.mSnapshot.getRotation();
      taskSnapshotProto.taskWidth = this.mSnapshot.getTaskSize().x;
      taskSnapshotProto.taskHeight = this.mSnapshot.getTaskSize().y;
      taskSnapshotProto.insetLeft = this.mSnapshot.getContentInsets().left;
      taskSnapshotProto.insetTop = this.mSnapshot.getContentInsets().top;
      taskSnapshotProto.insetRight = this.mSnapshot.getContentInsets().right;
      taskSnapshotProto.insetBottom = this.mSnapshot.getContentInsets().bottom;
      taskSnapshotProto.letterboxInsetLeft = this.mSnapshot.getLetterboxInsets().left;
      taskSnapshotProto.letterboxInsetTop = this.mSnapshot.getLetterboxInsets().top;
      taskSnapshotProto.letterboxInsetRight = this.mSnapshot.getLetterboxInsets().right;
      taskSnapshotProto.letterboxInsetBottom = this.mSnapshot.getLetterboxInsets().bottom;
      taskSnapshotProto.isRealSnapshot = this.mSnapshot.isRealSnapshot();
      taskSnapshotProto.windowingMode = this.mSnapshot.getWindowingMode();
      taskSnapshotProto.appearance = this.mSnapshot.getAppearance();
      taskSnapshotProto.isTranslucent = this.mSnapshot.isTranslucent();
      taskSnapshotProto.topActivityComponent =
          this.mSnapshot.getTopActivityComponent().flattenToString();
      taskSnapshotProto.id = this.mSnapshot.getId();
      if (this.mSnapshot.getCutoutInsets() != null) {
        taskSnapshotProto.cutoutInsetLeft = this.mSnapshot.getCutoutInsets().left;
        taskSnapshotProto.cutoutInsetTop = this.mSnapshot.getCutoutInsets().top;
        taskSnapshotProto.cutoutInsetRight = this.mSnapshot.getCutoutInsets().right;
        taskSnapshotProto.cutoutInsetBottom = this.mSnapshot.getCutoutInsets().bottom;
      }
      byte[] byteArray = WindowManagerProtos.TaskSnapshotProto.toByteArray(taskSnapshotProto);
      File protoFile = this.mPersistInfoProvider.getProtoFile(this.mId, this.mUserId);
      AtomicFile atomicFile = new AtomicFile(protoFile);
      try {
        fileOutputStream = atomicFile.startWrite();
      } catch (IOException e) {
        e = e;
        fileOutputStream = null;
      }
      try {
        fileOutputStream.write(byteArray);
        atomicFile.finishWrite(fileOutputStream);
        return true;
      } catch (IOException e2) {
        e = e2;
        atomicFile.failWrite(fileOutputStream);
        Slog.e(
            StartingSurfaceController.TAG, "Unable to open " + protoFile + " for persisting. " + e);
        return false;
      }
    }

    public boolean writeBuffer() {
      if (AbsAppSnapshotController.isInvalidHardwareBuffer(this.mSnapshot.getHardwareBuffer())) {
        Slog.e(
            StartingSurfaceController.TAG, "Invalid task snapshot hw buffer, taskId=" + this.mId);
        return false;
      }
      Bitmap wrapHardwareBuffer =
          Bitmap.wrapHardwareBuffer(
              this.mSnapshot.getHardwareBuffer(), this.mSnapshot.getColorSpace());
      if (wrapHardwareBuffer == null) {
        Slog.e(StartingSurfaceController.TAG, "Invalid task snapshot hw bitmap");
        return false;
      }
      Bitmap copy = wrapHardwareBuffer.copy(Bitmap.Config.ARGB_8888, false);
      if (copy == null) {
        Slog.e(
            StartingSurfaceController.TAG,
            "Bitmap conversion from (config="
                + wrapHardwareBuffer.getConfig()
                + ", isMutable="
                + wrapHardwareBuffer.isMutable()
                + ") to (config=ARGB_8888, isMutable=false) failed.");
        return false;
      }
      File highResolutionBitmapFile =
          this.mPersistInfoProvider.getHighResolutionBitmapFile(this.mId, this.mUserId);
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(highResolutionBitmapFile);
        copy.compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
        fileOutputStream.close();
        if (!this.mPersistInfoProvider.enableLowResSnapshots()) {
          copy.recycle();
          return true;
        }
        Bitmap createScaledBitmap =
            Bitmap.createScaledBitmap(
                copy,
                (int)
                    (wrapHardwareBuffer.getWidth() * this.mPersistInfoProvider.lowResScaleFactor()),
                (int)
                    (wrapHardwareBuffer.getHeight()
                        * this.mPersistInfoProvider.lowResScaleFactor()),
                true);
        copy.recycle();
        File lowResolutionBitmapFile =
            this.mPersistInfoProvider.getLowResolutionBitmapFile(this.mId, this.mUserId);
        try {
          FileOutputStream fileOutputStream2 = new FileOutputStream(lowResolutionBitmapFile);
          createScaledBitmap.compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream2);
          fileOutputStream2.close();
          createScaledBitmap.recycle();
          return true;
        } catch (IOException e) {
          Slog.e(
              StartingSurfaceController.TAG,
              "Unable to open " + lowResolutionBitmapFile + " for persisting.",
              e);
          return false;
        }
      } catch (IOException e2) {
        Slog.e(
            StartingSurfaceController.TAG,
            "Unable to open " + highResolutionBitmapFile + " for persisting.",
            e2);
        return false;
      }
    }
  }

  public DeleteWriteQueueItem createDeleteWriteQueueItem(
      int i, int i2, BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
    return new DeleteWriteQueueItem(i, i2, persistInfoProvider);
  }

  public class DeleteWriteQueueItem extends WriteQueueItem {
    public final int mId;
    public final int mUserId;

    public DeleteWriteQueueItem(
        int i, int i2, BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
      super(persistInfoProvider);
      this.mId = i;
      this.mUserId = i2;
    }

    @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
    public void write() {
      SnapshotPersistQueue.this.deleteSnapshot(this.mId, this.mUserId, this.mPersistInfoProvider);
    }
  }

  public CloseBufferWriteQueueItem createCloseBufferWriteQueueItem(
      HardwareBuffer hardwareBuffer,
      BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
    return new CloseBufferWriteQueueItem(hardwareBuffer, persistInfoProvider);
  }

  public class CloseBufferWriteQueueItem extends WriteQueueItem {
    public HardwareBuffer mHardwareBuffer;

    public CloseBufferWriteQueueItem(
        HardwareBuffer hardwareBuffer,
        BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
      super(persistInfoProvider);
      this.mHardwareBuffer = hardwareBuffer;
    }

    @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
    public void write() {
      HardwareBuffer hardwareBuffer = this.mHardwareBuffer;
      if (hardwareBuffer != null) {
        hardwareBuffer.close();
        this.mHardwareBuffer = null;
      }
    }
  }
}
