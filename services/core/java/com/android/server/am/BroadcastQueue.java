package com.android.server.am;

import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.util.Slog;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BroadcastQueue {
    public final Handler mHandler;
    public final BroadcastHistory mHistory;
    public final String mQueueName;
    public final ActivityManagerService mService;
    public final BroadcastSkipPolicy mSkipPolicy;

    public BroadcastQueue(ActivityManagerService activityManagerService, Handler handler, BroadcastSkipPolicy broadcastSkipPolicy, BroadcastHistory broadcastHistory) {
        Objects.requireNonNull(activityManagerService);
        this.mService = activityManagerService;
        Objects.requireNonNull(handler);
        this.mHandler = handler;
        this.mQueueName = "modern";
        this.mSkipPolicy = broadcastSkipPolicy;
        this.mHistory = broadcastHistory;
    }

    public static void checkState(boolean z, String str) {
        if (!z) {
            throw new IllegalStateException(str);
        }
    }

    public static void logw(String str) {
        Slog.w("BroadcastQueue", str);
    }

    public static int traceBegin(String str) {
        int hashCode = str.hashCode();
        Trace.asyncTraceForTrackBegin(64L, "BroadcastQueue", str, hashCode);
        return hashCode;
    }

    public static void traceEnd(int i) {
        Trace.asyncTraceForTrackEnd(64L, "BroadcastQueue", i);
    }

    public abstract boolean dumpLocked(PrintWriter printWriter, boolean z, boolean z2, boolean z3, String str, boolean z4);

    public abstract boolean finishReceiverLocked(ProcessRecord processRecord, int i, String str, Bundle bundle, boolean z);

    public final String toString() {
        return this.mQueueName;
    }
}
