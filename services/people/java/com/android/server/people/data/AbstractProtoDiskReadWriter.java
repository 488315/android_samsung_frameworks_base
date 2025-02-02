package com.android.server.people.data;

import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public abstract class AbstractProtoDiskReadWriter {
  public static final String TAG = "AbstractProtoDiskReadWriter";
  public final File mRootDir;
  public final ScheduledExecutorService mScheduledExecutorService;
  public Map mScheduledFileDataMap = new ArrayMap();
  public ScheduledFuture mScheduledFuture;

  public interface ProtoStreamReader {
    Object read(ProtoInputStream protoInputStream);
  }

  public interface ProtoStreamWriter {
    void write(ProtoOutputStream protoOutputStream, Object obj);
  }

  public abstract ProtoStreamReader protoStreamReader();

  public abstract ProtoStreamWriter protoStreamWriter();

  public AbstractProtoDiskReadWriter(File file, ScheduledExecutorService scheduledExecutorService) {
    this.mRootDir = file;
    this.mScheduledExecutorService = scheduledExecutorService;
  }

  public void delete(String str) {
    synchronized (this) {
      this.mScheduledFileDataMap.remove(str);
    }
    File file = getFile(str);
    if (file.exists() && !file.delete()) {
      Slog.e(TAG, "Failed to delete file: " + file.getPath());
    }
  }

  public void writeTo(String str, Object obj) {
    AtomicFile atomicFile = new AtomicFile(getFile(str));
    try {
      FileOutputStream startWrite = atomicFile.startWrite();
      try {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(startWrite);
        protoStreamWriter().write(protoOutputStream, obj);
        protoOutputStream.flush();
        atomicFile.finishWrite(startWrite);
        atomicFile.failWrite(null);
      } catch (Throwable th) {
        atomicFile.failWrite(startWrite);
        throw th;
      }
    } catch (IOException e) {
      Slog.e(TAG, "Failed to write to protobuf file.", e);
    }
  }

  public Object read(final String str) {
    File[] listFiles =
        this.mRootDir.listFiles(
            new FileFilter() { // from class:
              // com.android.server.people.data.AbstractProtoDiskReadWriter$$ExternalSyntheticLambda1
              @Override // java.io.FileFilter
              public final boolean accept(File file) {
                boolean lambda$read$0;
                lambda$read$0 = AbstractProtoDiskReadWriter.lambda$read$0(str, file);
                return lambda$read$0;
              }
            });
    if (listFiles == null || listFiles.length == 0) {
      return null;
    }
    if (listFiles.length > 1) {
      Slog.w(TAG, "Found multiple files with the same name: " + Arrays.toString(listFiles));
    }
    return parseFile(listFiles[0]);
  }

  public static /* synthetic */ boolean lambda$read$0(String str, File file) {
    return file.isFile() && file.getName().equals(str);
  }

  public synchronized void scheduleSave(String str, Object obj) {
    this.mScheduledFileDataMap.put(str, obj);
    if (this.mScheduledExecutorService.isShutdown()) {
      Slog.e(TAG, "Worker is shutdown, failed to schedule data saving.");
    } else {
      if (this.mScheduledFuture != null) {
        return;
      }
      this.mScheduledFuture =
          this.mScheduledExecutorService.schedule(
              new AbstractProtoDiskReadWriter$$ExternalSyntheticLambda0(this),
              120000L,
              TimeUnit.MILLISECONDS);
    }
  }

  public void saveImmediately(String str, Object obj) {
    synchronized (this) {
      this.mScheduledFileDataMap.put(str, obj);
    }
    triggerScheduledFlushEarly();
  }

  public final void triggerScheduledFlushEarly() {
    synchronized (this) {
      if (!this.mScheduledFileDataMap.isEmpty() && !this.mScheduledExecutorService.isShutdown()) {
        ScheduledFuture scheduledFuture = this.mScheduledFuture;
        if (scheduledFuture != null) {
          scheduledFuture.cancel(true);
        }
        try {
          this.mScheduledExecutorService
              .submit(new AbstractProtoDiskReadWriter$$ExternalSyntheticLambda0(this))
              .get(5000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
          Slog.e(TAG, "Failed to save data immediately.", e);
        }
      }
    }
  }

  public final synchronized void flushScheduledData() {
    if (this.mScheduledFileDataMap.isEmpty()) {
      this.mScheduledFuture = null;
      return;
    }
    for (String str : this.mScheduledFileDataMap.keySet()) {
      writeTo(str, this.mScheduledFileDataMap.get(str));
    }
    this.mScheduledFileDataMap.clear();
    this.mScheduledFuture = null;
  }

  public final Object parseFile(File file) {
    try {
      FileInputStream openRead = new AtomicFile(file).openRead();
      try {
        Object read = protoStreamReader().read(new ProtoInputStream(openRead));
        if (openRead != null) {
          openRead.close();
        }
        return read;
      } finally {
      }
    } catch (IOException e) {
      Slog.e(TAG, "Failed to parse protobuf file.", e);
      return null;
    }
  }

  public final File getFile(String str) {
    return new File(this.mRootDir, str);
  }
}
