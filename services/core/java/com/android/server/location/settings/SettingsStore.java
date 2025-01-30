package com.android.server.location.settings;

import android.util.AtomicFile;
import android.util.Log;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

/* loaded from: classes2.dex */
public abstract class SettingsStore {
  public VersionedSettings mCache;
  public final AtomicFile mFile;
  public boolean mInitialized;

  public interface VersionedSettings {
    int getVersion();
  }

  public abstract void onChange(
      VersionedSettings versionedSettings, VersionedSettings versionedSettings2);

  public abstract VersionedSettings read(int i, DataInput dataInput);

  public abstract void write(DataOutput dataOutput, VersionedSettings versionedSettings);

  public SettingsStore(File file) {
    this.mFile = new AtomicFile(file);
  }

  public final synchronized void initializeCache() {
    if (!this.mInitialized) {
      if (this.mFile.exists()) {
        try {
          DataInputStream dataInputStream = new DataInputStream(this.mFile.openRead());
          try {
            VersionedSettings read = read(dataInputStream.readInt(), dataInputStream);
            this.mCache = read;
            Preconditions.checkState(read.getVersion() < Integer.MAX_VALUE);
            dataInputStream.close();
          } catch (Throwable th) {
            try {
              dataInputStream.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
            throw th;
          }
        } catch (IOException e) {
          Log.e(
              "LocationManagerService",
              "error reading location settings (" + this.mFile + "), falling back to defaults",
              e);
        }
      }
      if (this.mCache == null) {
        try {
          VersionedSettings read2 =
              read(Integer.MAX_VALUE, new DataInputStream(new ByteArrayInputStream(new byte[0])));
          this.mCache = read2;
          Preconditions.checkState(read2.getVersion() < Integer.MAX_VALUE);
        } catch (IOException e2) {
          throw new AssertionError(e2);
        }
      }
      this.mInitialized = true;
    }
  }

  public final synchronized VersionedSettings get() {
    initializeCache();
    return this.mCache;
  }

  public synchronized void update(Function function) {
    initializeCache();
    VersionedSettings versionedSettings = this.mCache;
    VersionedSettings versionedSettings2 = (VersionedSettings) function.apply(versionedSettings);
    Objects.requireNonNull(versionedSettings2);
    VersionedSettings versionedSettings3 = versionedSettings2;
    if (versionedSettings.equals(versionedSettings3)) {
      return;
    }
    this.mCache = versionedSettings3;
    Preconditions.checkState(versionedSettings3.getVersion() < Integer.MAX_VALUE);
    writeLazily(versionedSettings3);
    onChange(versionedSettings, versionedSettings3);
  }

  public synchronized void flushFile() {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    BackgroundThread.getExecutor()
        .execute(new SettingsStore$$ExternalSyntheticLambda1(countDownLatch));
    countDownLatch.await();
  }

  public synchronized void deleteFile() {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    BackgroundThread.getExecutor()
        .execute(
            new Runnable() { // from class:
                             // com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SettingsStore.this.lambda$deleteFile$0(countDownLatch);
              }
            });
    countDownLatch.await();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$deleteFile$0(CountDownLatch countDownLatch) {
    this.mFile.delete();
    countDownLatch.countDown();
  }

  public final void writeLazily(final VersionedSettings versionedSettings) {
    BackgroundThread.getExecutor()
        .execute(
            new Runnable() { // from class:
                             // com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                SettingsStore.this.lambda$writeLazily$1(versionedSettings);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$writeLazily$1(VersionedSettings versionedSettings) {
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = this.mFile.startWrite();
      DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
      dataOutputStream.writeInt(versionedSettings.getVersion());
      write(dataOutputStream, versionedSettings);
      this.mFile.finishWrite(fileOutputStream);
    } catch (IOException e) {
      this.mFile.failWrite(fileOutputStream);
      Log.e("LocationManagerService", "failure serializing location settings", e);
    } catch (Throwable th) {
      this.mFile.failWrite(fileOutputStream);
      throw th;
    }
  }
}
