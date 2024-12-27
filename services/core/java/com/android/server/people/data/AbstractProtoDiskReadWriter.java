package com.android.server.people.data;

import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

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

public abstract class AbstractProtoDiskReadWriter {
    public final File mRootDir;
    public final ScheduledExecutorService mScheduledExecutorService;
    public final Map mScheduledFileDataMap = new ArrayMap();
    public ScheduledFuture mScheduledFuture;

    public interface ProtoStreamReader {
        Object read(ProtoInputStream protoInputStream);
    }

    public interface ProtoStreamWriter {
        void write(ProtoOutputStream protoOutputStream, Object obj);
    }

    public AbstractProtoDiskReadWriter(
            File file, ScheduledExecutorService scheduledExecutorService) {
        this.mRootDir = file;
        this.mScheduledExecutorService = scheduledExecutorService;
    }

    public final void delete(String str) {
        synchronized (this) {
            ((ArrayMap) this.mScheduledFileDataMap).remove(str);
        }
        File file = new File(this.mRootDir, str);
        if (file.exists() && !file.delete()) {
            Slog.e("AbstractProtoDiskReadWriter", "Failed to delete file: " + file.getPath());
        }
    }

    public abstract ProtoStreamReader protoStreamReader();

    public abstract ProtoStreamWriter protoStreamWriter();

    public final Object read(final String str) {
        File[] listFiles =
                this.mRootDir.listFiles(
                        new FileFilter() { // from class:
                                           // com.android.server.people.data.AbstractProtoDiskReadWriter$$ExternalSyntheticLambda1
                            @Override // java.io.FileFilter
                            public final boolean accept(File file) {
                                return file.isFile() && file.getName().equals(str);
                            }
                        });
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        if (listFiles.length > 1) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Found multiple files with the same name: "),
                    Arrays.toString(listFiles),
                    "AbstractProtoDiskReadWriter");
        }
        try {
            FileInputStream openRead = new AtomicFile(listFiles[0]).openRead();
            try {
                Object read = protoStreamReader().read(new ProtoInputStream(openRead));
                if (openRead != null) {
                    openRead.close();
                }
                return read;
            } finally {
            }
        } catch (IOException e) {
            Slog.e("AbstractProtoDiskReadWriter", "Failed to parse protobuf file.", e);
            return null;
        }
    }

    public final void saveImmediately(String str, Object obj) {
        synchronized (this) {
            ((ArrayMap) this.mScheduledFileDataMap).put(str, obj);
        }
        synchronized (this) {
            try {
                if (!((ArrayMap) this.mScheduledFileDataMap).isEmpty()
                        && !this.mScheduledExecutorService.isShutdown()) {
                    ScheduledFuture scheduledFuture = this.mScheduledFuture;
                    if (scheduledFuture != null) {
                        scheduledFuture.cancel(true);
                    }
                    try {
                        this.mScheduledExecutorService
                                .submit(
                                        new AbstractProtoDiskReadWriter$$ExternalSyntheticLambda0(
                                                this))
                                .get(5000L, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        Slog.e(
                                "AbstractProtoDiskReadWriter",
                                "Failed to save data immediately.",
                                e);
                    }
                }
            } finally {
            }
        }
    }

    public final synchronized void scheduleSave(String str, Object obj) {
        ((ArrayMap) this.mScheduledFileDataMap).put(str, obj);
        if (this.mScheduledExecutorService.isShutdown()) {
            Slog.e(
                    "AbstractProtoDiskReadWriter",
                    "Worker is shutdown, failed to schedule data saving.");
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

    public final void writeTo(String str, Object obj) {
        AtomicFile atomicFile = new AtomicFile(new File(this.mRootDir, str));
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
            Slog.e("AbstractProtoDiskReadWriter", "Failed to write to protobuf file.", e);
        }
    }
}
