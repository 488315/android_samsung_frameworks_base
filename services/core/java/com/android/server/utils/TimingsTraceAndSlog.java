package com.android.server.utils;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.util.Slog;
import android.util.TimingsTraceLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimingsTraceAndSlog extends TimingsTraceLog {
    public final String mTag;

    public TimingsTraceAndSlog() {
        this("SystemServerTiming");
    }

    public TimingsTraceAndSlog(long j, String str) {
        super(str, j);
        this.mTag = str;
    }

    public TimingsTraceAndSlog(TimingsTraceAndSlog timingsTraceAndSlog) {
        super(timingsTraceAndSlog);
        this.mTag = timingsTraceAndSlog.mTag;
    }

    public TimingsTraceAndSlog(String str) {
        this(524288L, str);
    }

    public static TimingsTraceAndSlog newAsyncLog() {
        return new TimingsTraceAndSlog(524288L, "SystemServerTimingAsync");
    }

    public final String toString() {
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("TimingsTraceAndSlog["), this.mTag, "]");
    }

    public final void traceBegin(String str) {
        Slog.d(this.mTag, str);
        super.traceBegin(str);
    }
}
