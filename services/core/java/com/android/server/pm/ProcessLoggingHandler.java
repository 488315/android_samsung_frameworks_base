package com.android.server.pm;

import android.app.admin.SecurityLog;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.android.internal.os.BackgroundThread;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ProcessLoggingHandler extends Handler {
    public final Executor mExecutor;
    public final ArrayMap mLoggingInfo;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LoggingInfo {
        public String apkHash;
        public List pendingLogEntries;
    }

    public ProcessLoggingHandler() {
        super(BackgroundThread.getHandler().getLooper());
        this.mExecutor = new HandlerExecutor(this);
        this.mLoggingInfo = new ArrayMap();
    }

    public static void logSecurityLogEvent(Bundle bundle, String str) {
        long j = bundle.getLong("startTimestamp");
        String string = bundle.getString("processName");
        int i = bundle.getInt("uid");
        SecurityLog.writeEvent(
                210005,
                new Object[] {
                    string,
                    Long.valueOf(j),
                    Integer.valueOf(i),
                    Integer.valueOf(bundle.getInt("pid")),
                    bundle.getString("seinfo"),
                    str
                });
    }

    public static void processChecksum(LoggingInfo loggingInfo, byte[] bArr) {
        String str;
        if (bArr != null) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < bArr.length) {
                i =
                        MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                                "%02x", new Object[] {Byte.valueOf(bArr[i])}, sb, i, 1);
            }
            str = sb.toString();
        } else {
            str = "Failed to count APK hash";
        }
        synchronized (loggingInfo) {
            try {
                if (TextUtils.isEmpty(loggingInfo.apkHash)) {
                    loggingInfo.apkHash = str;
                    List list = loggingInfo.pendingLogEntries;
                    loggingInfo.pendingLogEntries = null;
                    if (list != null) {
                        Iterator it = ((ArrayList) list).iterator();
                        while (it.hasNext()) {
                            logSecurityLogEvent((Bundle) it.next(), str);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
