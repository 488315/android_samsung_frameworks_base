package com.android.server.wm;

import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import android.util.AtomicFile;
import android.util.Slog;
import com.android.server.wm.nano.WindowManagerProtos;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class LetterboxConfigurationPersister {
  static final String LETTERBOX_CONFIGURATION_FILENAME = "letterbox_config";
  public final Consumer mCompletionCallback;
  public final AtomicFile mConfigurationFile;
  public final Context mContext;
  public final Supplier mDefaultBookModeReachabilitySupplier;
  public final Supplier mDefaultHorizontalReachabilitySupplier;
  public final Supplier mDefaultTabletopModeReachabilitySupplier;
  public final Supplier mDefaultVerticalReachabilitySupplier;
  public volatile int mLetterboxPositionForBookModeReachability;
  public volatile int mLetterboxPositionForHorizontalReachability;
  public volatile int mLetterboxPositionForTabletopModeReachability;
  public volatile int mLetterboxPositionForVerticalReachability;
  public final PersisterQueue mPersisterQueue;

  public LetterboxConfigurationPersister(
      Context context,
      Supplier supplier,
      Supplier supplier2,
      Supplier supplier3,
      Supplier supplier4) {
    this(
        context,
        supplier,
        supplier2,
        supplier3,
        supplier4,
        Environment.getDataSystemDirectory(),
        new PersisterQueue(),
        null);
  }

  public LetterboxConfigurationPersister(
      Context context,
      Supplier supplier,
      Supplier supplier2,
      Supplier supplier3,
      Supplier supplier4,
      File file,
      PersisterQueue persisterQueue,
      Consumer consumer) {
    this.mContext = context.createDeviceProtectedStorageContext();
    this.mDefaultHorizontalReachabilitySupplier = supplier;
    this.mDefaultVerticalReachabilitySupplier = supplier2;
    this.mDefaultBookModeReachabilitySupplier = supplier3;
    this.mDefaultTabletopModeReachabilitySupplier = supplier4;
    this.mCompletionCallback = consumer;
    this.mConfigurationFile = new AtomicFile(new File(file, LETTERBOX_CONFIGURATION_FILENAME));
    this.mPersisterQueue = persisterQueue;
    runWithDiskReadsThreadPolicy(
        new Runnable() { // from class:
                         // com.android.server.wm.LetterboxConfigurationPersister$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            LetterboxConfigurationPersister.this.readCurrentConfiguration();
          }
        });
  }

  public void start() {
    this.mPersisterQueue.startPersisting();
  }

  public int getLetterboxPositionForHorizontalReachability(boolean z) {
    if (z) {
      return this.mLetterboxPositionForBookModeReachability;
    }
    return this.mLetterboxPositionForHorizontalReachability;
  }

  public int getLetterboxPositionForVerticalReachability(boolean z) {
    if (z) {
      return this.mLetterboxPositionForTabletopModeReachability;
    }
    return this.mLetterboxPositionForVerticalReachability;
  }

  public void setLetterboxPositionForHorizontalReachability(boolean z, int i) {
    if (z) {
      if (this.mLetterboxPositionForBookModeReachability != i) {
        this.mLetterboxPositionForBookModeReachability = i;
        updateConfiguration();
        return;
      }
      return;
    }
    if (this.mLetterboxPositionForHorizontalReachability != i) {
      this.mLetterboxPositionForHorizontalReachability = i;
      updateConfiguration();
    }
  }

  public void setLetterboxPositionForVerticalReachability(boolean z, int i) {
    if (z) {
      if (this.mLetterboxPositionForTabletopModeReachability != i) {
        this.mLetterboxPositionForTabletopModeReachability = i;
        updateConfiguration();
        return;
      }
      return;
    }
    if (this.mLetterboxPositionForVerticalReachability != i) {
      this.mLetterboxPositionForVerticalReachability = i;
      updateConfiguration();
    }
  }

  public void useDefaultValue() {
    this.mLetterboxPositionForHorizontalReachability =
        ((Integer) this.mDefaultHorizontalReachabilitySupplier.get()).intValue();
    this.mLetterboxPositionForVerticalReachability =
        ((Integer) this.mDefaultVerticalReachabilitySupplier.get()).intValue();
    this.mLetterboxPositionForBookModeReachability =
        ((Integer) this.mDefaultBookModeReachabilitySupplier.get()).intValue();
    this.mLetterboxPositionForTabletopModeReachability =
        ((Integer) this.mDefaultTabletopModeReachabilitySupplier.get()).intValue();
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0047 -> B:11:0x004d). Please report as a decompilation issue!!! */
  public final void readCurrentConfiguration() {
    if (!this.mConfigurationFile.exists()) {
      useDefaultValue();
      return;
    }
    FileInputStream fileInputStream = null;
    try {
      try {
        try {
          fileInputStream = this.mConfigurationFile.openRead();
          WindowManagerProtos.LetterboxProto parseFrom =
              WindowManagerProtos.LetterboxProto.parseFrom(readInputStream(fileInputStream));
          this.mLetterboxPositionForHorizontalReachability =
              parseFrom.letterboxPositionForHorizontalReachability;
          this.mLetterboxPositionForVerticalReachability =
              parseFrom.letterboxPositionForVerticalReachability;
          this.mLetterboxPositionForBookModeReachability =
              parseFrom.letterboxPositionForBookModeReachability;
          this.mLetterboxPositionForTabletopModeReachability =
              parseFrom.letterboxPositionForTabletopModeReachability;
          if (fileInputStream != null) {
            fileInputStream.close();
          }
        } catch (IOException e) {
          Slog.e(
              StartingSurfaceController.TAG,
              "Error reading from LetterboxConfigurationPersister. Using default values!",
              e);
          useDefaultValue();
          if (fileInputStream == null) {
          } else {
            fileInputStream.close();
          }
        }
      } catch (IOException e2) {
        useDefaultValue();
        Slog.e(
            StartingSurfaceController.TAG,
            "Error reading from LetterboxConfigurationPersister ",
            e2);
      }
    } catch (Throwable th) {
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (IOException e3) {
          useDefaultValue();
          Slog.e(
              StartingSurfaceController.TAG,
              "Error reading from LetterboxConfigurationPersister ",
              e3);
        }
      }
      throw th;
    }
  }

  public final void updateConfiguration() {
    this.mPersisterQueue.addItem(
        new UpdateValuesCommand(
            this.mConfigurationFile,
            this.mLetterboxPositionForHorizontalReachability,
            this.mLetterboxPositionForVerticalReachability,
            this.mLetterboxPositionForBookModeReachability,
            this.mLetterboxPositionForTabletopModeReachability,
            this.mCompletionCallback),
        true);
  }

  public static byte[] readInputStream(InputStream inputStream) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      byte[] bArr = new byte[1024];
      int read = inputStream.read(bArr);
      while (read > 0) {
        byteArrayOutputStream.write(bArr, 0, read);
        read = inputStream.read(bArr);
      }
      return byteArrayOutputStream.toByteArray();
    } finally {
      byteArrayOutputStream.close();
    }
  }

  public final void runWithDiskReadsThreadPolicy(Runnable runnable) {
    StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitDiskReads().build());
    runnable.run();
    StrictMode.setThreadPolicy(threadPolicy);
  }

  public class UpdateValuesCommand implements PersisterQueue.WriteQueueItem {
    public final int mBookModeReachability;
    public final AtomicFile mFileToUpdate;
    public final int mHorizontalReachability;
    public final Consumer mOnComplete;
    public final int mTabletopModeReachability;
    public final int mVerticalReachability;

    public UpdateValuesCommand(
        AtomicFile atomicFile, int i, int i2, int i3, int i4, Consumer consumer) {
      this.mFileToUpdate = atomicFile;
      this.mHorizontalReachability = i;
      this.mVerticalReachability = i2;
      this.mBookModeReachability = i3;
      this.mTabletopModeReachability = i4;
      this.mOnComplete = consumer;
    }

    @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
    public void process() {
      Consumer consumer;
      WindowManagerProtos.LetterboxProto letterboxProto = new WindowManagerProtos.LetterboxProto();
      letterboxProto.letterboxPositionForHorizontalReachability = this.mHorizontalReachability;
      letterboxProto.letterboxPositionForVerticalReachability = this.mVerticalReachability;
      letterboxProto.letterboxPositionForBookModeReachability = this.mBookModeReachability;
      letterboxProto.letterboxPositionForTabletopModeReachability = this.mTabletopModeReachability;
      byte[] byteArray = WindowManagerProtos.LetterboxProto.toByteArray(letterboxProto);
      FileOutputStream fileOutputStream = null;
      try {
        try {
          fileOutputStream = this.mFileToUpdate.startWrite();
          fileOutputStream.write(byteArray);
          this.mFileToUpdate.finishWrite(fileOutputStream);
          consumer = this.mOnComplete;
          if (consumer == null) {
            return;
          }
        } catch (IOException e) {
          this.mFileToUpdate.failWrite(fileOutputStream);
          Slog.e(
              StartingSurfaceController.TAG,
              "Error writing to LetterboxConfigurationPersister. Using default values!",
              e);
          consumer = this.mOnComplete;
          if (consumer == null) {
            return;
          }
        }
        consumer.accept("UpdateValuesCommand");
      } catch (Throwable th) {
        Consumer consumer2 = this.mOnComplete;
        if (consumer2 != null) {
          consumer2.accept("UpdateValuesCommand");
        }
        throw th;
      }
    }
  }
}
