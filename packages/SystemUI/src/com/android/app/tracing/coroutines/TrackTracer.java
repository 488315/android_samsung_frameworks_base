package com.android.app.tracing.coroutines;

import android.os.Trace;
import com.android.app.tracing.TrackGroupUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TrackTracer {
    public static final Companion Companion = new Companion(null);
    public final long traceTag;
    public final String trackName;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void instantForGroup(int i, String str, String str2) {
            Trace.traceCounter(4096L, TrackGroupUtils.trackGroup(str, str2), i);
        }

        private Companion() {
        }
    }

    public TrackTracer(String str, long j, String str2) {
        this.traceTag = j;
        this.trackName = str2 != null ? TrackGroupUtils.trackGroup(str2, str) : str;
    }

    public static final void instantForGroup(int i, String str, String str2) {
        Companion.getClass();
        Companion.instantForGroup(i, str, str2);
    }

    public /* synthetic */ TrackTracer(String str, long j, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? 4096L : j, (i & 4) != 0 ? null : str2);
    }
}
