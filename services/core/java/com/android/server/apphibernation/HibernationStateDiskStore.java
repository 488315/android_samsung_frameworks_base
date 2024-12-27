package com.android.server.apphibernation;

import android.util.AtomicFile;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class HibernationStateDiskStore {
    public final ScheduledExecutorService mExecutorService;
    public ScheduledFuture mFuture;
    public final File mHibernationFile;
    public final ProtoReadWriter mProtoReadWriter;
    public List mScheduledStatesToWrite;

    public HibernationStateDiskStore(
            File file,
            ProtoReadWriter protoReadWriter,
            ScheduledExecutorService scheduledExecutorService) {
        this(file, protoReadWriter, scheduledExecutorService, "states");
    }

    public HibernationStateDiskStore(
            File file,
            ProtoReadWriter protoReadWriter,
            ScheduledExecutorService scheduledExecutorService,
            String str) {
        this.mScheduledStatesToWrite = new ArrayList();
        this.mHibernationFile = new File(file, str);
        this.mExecutorService = scheduledExecutorService;
        this.mProtoReadWriter = protoReadWriter;
    }

    public final List readHibernationStates() {
        synchronized (this) {
            try {
                if (!this.mHibernationFile.exists()) {
                    Slog.i(
                            "HibernationStateDiskStore",
                            "No hibernation file on disk for file "
                                    + this.mHibernationFile.getPath());
                    return null;
                }
                try {
                    return (List)
                            this.mProtoReadWriter.readFromProto(
                                    new ProtoInputStream(
                                            new AtomicFile(this.mHibernationFile).openRead()));
                } catch (IOException e) {
                    Slog.e("HibernationStateDiskStore", "Failed to read states protobuf.", e);
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void scheduleWriteHibernationStates(List list) {
        synchronized (this) {
            try {
                this.mScheduledStatesToWrite = list;
                if (this.mExecutorService.isShutdown()) {
                    Slog.e("HibernationStateDiskStore", "Scheduled executor service is shut down.");
                } else if (this.mFuture != null) {
                    Slog.i(
                            "HibernationStateDiskStore",
                            "Write already scheduled. Skipping schedule.");
                } else {
                    this.mFuture =
                            this.mExecutorService.schedule(
                                    new Runnable() { // from class:
                                                     // com.android.server.apphibernation.HibernationStateDiskStore$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            HibernationStateDiskStore hibernationStateDiskStore =
                                                    HibernationStateDiskStore.this;
                                            synchronized (hibernationStateDiskStore) {
                                                hibernationStateDiskStore.writeStateProto(
                                                        hibernationStateDiskStore
                                                                .mScheduledStatesToWrite);
                                                hibernationStateDiskStore.mScheduledStatesToWrite
                                                        .clear();
                                                hibernationStateDiskStore.mFuture = null;
                                            }
                                        }
                                    },
                                    60000L,
                                    TimeUnit.MILLISECONDS);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void writeStateProto(List list) {
        AtomicFile atomicFile = new AtomicFile(this.mHibernationFile);
        try {
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream(startWrite);
                this.mProtoReadWriter.writeToProto(protoOutputStream, list);
                protoOutputStream.flush();
                atomicFile.finishWrite(startWrite);
            } catch (Exception e) {
                Slog.e(
                        "HibernationStateDiskStore",
                        "Failed to finish write to states protobuf.",
                        e);
                atomicFile.failWrite(startWrite);
            }
        } catch (IOException e2) {
            Slog.e("HibernationStateDiskStore", "Failed to start write to states protobuf.", e2);
        }
    }
}
