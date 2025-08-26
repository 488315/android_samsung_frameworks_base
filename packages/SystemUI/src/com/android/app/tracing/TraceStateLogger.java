package com.android.app.tracing;

import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class TraceStateLogger {
    public final boolean instantEvent;
    public final boolean logOnlyIfDifferent;
    public final boolean logcat;
    public String previousValue;
    public final String trackName;

    public TraceStateLogger(String str) {
        this(str, false, false, false, 14, null);
    }

    public final void log(String str) {
        boolean z = this.instantEvent;
        String str2 = this.trackName;
        if (z) {
            Trace.instantForTrack(4096L, str2, str);
        }
        if (this.logOnlyIfDifferent && Intrinsics.areEqual(this.previousValue, str)) {
            return;
        }
        if (this.previousValue != null) {
            Trace.asyncTraceForTrackEnd(4096L, str2, 0);
        }
        Trace.asyncTraceForTrackBegin(4096L, str2, str, 0);
        if (this.logcat) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("newValue: ", str, str2);
        }
        this.previousValue = str;
    }

    public TraceStateLogger(String str, boolean z) {
        this(str, z, false, false, 12, null);
    }

    public TraceStateLogger(String str, boolean z, boolean z2) {
        this(str, z, z2, false, 8, null);
    }

    public TraceStateLogger(String str, boolean z, boolean z2, boolean z3) {
        this.trackName = str;
        this.logOnlyIfDifferent = z;
        this.instantEvent = z2;
        this.logcat = z3;
    }

    public /* synthetic */ TraceStateLogger(String str, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? true : z, (i & 4) != 0 ? true : z2, (i & 8) != 0 ? false : z3);
    }
}
