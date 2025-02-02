package com.android.server.wm;

import android.hardware.HardwareBuffer;
import android.window.TaskSnapshot;
import java.io.File;

/* loaded from: classes3.dex */
public abstract class BaseAppSnapshotPersister {
  public final Object mLock;
  public final PersistInfoProvider mPersistInfoProvider;
  public final SnapshotPersistQueue mSnapshotPersistQueue;

  public interface DirectoryResolver {
    File getSystemDirectoryForUser(int i);
  }

  public BaseAppSnapshotPersister(
      SnapshotPersistQueue snapshotPersistQueue, PersistInfoProvider persistInfoProvider) {
    this.mSnapshotPersistQueue = snapshotPersistQueue;
    this.mPersistInfoProvider = persistInfoProvider;
    this.mLock = snapshotPersistQueue.getLock();
  }

  public void persistSnapshot(int i, int i2, TaskSnapshot taskSnapshot) {
    synchronized (this.mLock) {
      SnapshotPersistQueue snapshotPersistQueue = this.mSnapshotPersistQueue;
      snapshotPersistQueue.sendToQueueLocked(
          snapshotPersistQueue.createStoreWriteQueueItem(
              i, i2, taskSnapshot, this.mPersistInfoProvider));
    }
  }

  public void removeSnap(int i, int i2) {
    synchronized (this.mLock) {
      SnapshotPersistQueue snapshotPersistQueue = this.mSnapshotPersistQueue;
      snapshotPersistQueue.sendToQueueLocked(
          snapshotPersistQueue.createDeleteWriteQueueItem(i, i2, this.mPersistInfoProvider));
    }
  }

  public void closeBuffer(HardwareBuffer hardwareBuffer) {
    synchronized (this.mLock) {
      SnapshotPersistQueue snapshotPersistQueue = this.mSnapshotPersistQueue;
      snapshotPersistQueue.sendToQueueLocked(
          snapshotPersistQueue.createCloseBufferWriteQueueItem(
              hardwareBuffer, this.mPersistInfoProvider));
    }
  }

  public class PersistInfoProvider {
    public final String mDirName;
    public final DirectoryResolver mDirectoryResolver;
    public final boolean mEnableLowResSnapshots;
    public final float mLowResScaleFactor;
    public final boolean mUse16BitFormat;

    public PersistInfoProvider(
        DirectoryResolver directoryResolver, String str, boolean z, float f, boolean z2) {
      this.mDirectoryResolver = directoryResolver;
      this.mDirName = str;
      this.mEnableLowResSnapshots = z;
      this.mLowResScaleFactor = f;
      this.mUse16BitFormat = z2;
    }

    public File getDirectory(int i) {
      return new File(this.mDirectoryResolver.getSystemDirectoryForUser(i), this.mDirName);
    }

    public boolean use16BitFormat() {
      return this.mUse16BitFormat;
    }

    public boolean createDirectory(int i) {
      File directory = getDirectory(i);
      return directory.exists() || directory.mkdir();
    }

    public File getProtoFile(int i, int i2) {
      return new File(getDirectory(i2), i + ".proto");
    }

    public File getLowResolutionBitmapFile(int i, int i2) {
      return new File(getDirectory(i2), i + "_reduced.jpg");
    }

    public File getHighResolutionBitmapFile(int i, int i2) {
      return new File(getDirectory(i2), i + ".jpg");
    }

    public boolean enableLowResSnapshots() {
      return this.mEnableLowResSnapshots;
    }

    public float lowResScaleFactor() {
      return this.mLowResScaleFactor;
    }
  }
}
