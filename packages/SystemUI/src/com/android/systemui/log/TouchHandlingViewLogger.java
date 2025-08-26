package com.android.systemui.log;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class TouchHandlingViewLogger {
    public final LogBuffer logBuffer;
    public final String tag;

    public TouchHandlingViewLogger(LogBuffer logBuffer, String str) {
        this.logBuffer = logBuffer;
        this.tag = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TouchHandlingViewLogger)) {
            return false;
        }
        TouchHandlingViewLogger touchHandlingViewLogger = (TouchHandlingViewLogger) obj;
        return Intrinsics.areEqual(this.logBuffer, touchHandlingViewLogger.logBuffer) && Intrinsics.areEqual(this.tag, touchHandlingViewLogger.tag);
    }

    public final int hashCode() {
        return this.tag.hashCode() + (this.logBuffer.hashCode() * 31);
    }

    public final String toString() {
        return "TouchHandlingViewLogger(logBuffer=" + this.logBuffer + ", tag=" + this.tag + ")";
    }

    public /* synthetic */ TouchHandlingViewLogger(LogBuffer logBuffer, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(logBuffer, (i & 2) != 0 ? "TouchHandlingViewLogger" : str);
    }
}
