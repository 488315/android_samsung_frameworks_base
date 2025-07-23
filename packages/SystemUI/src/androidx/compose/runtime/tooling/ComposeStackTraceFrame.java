package androidx.compose.runtime.tooling;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposeStackTraceFrame {
    public final Integer groupOffset;
    public final ParsedSourceInformation sourceInfo;

    public ComposeStackTraceFrame(ParsedSourceInformation parsedSourceInformation, Integer num) {
        this.sourceInfo = parsedSourceInformation;
        this.groupOffset = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComposeStackTraceFrame)) {
            return false;
        }
        ComposeStackTraceFrame composeStackTraceFrame = (ComposeStackTraceFrame) obj;
        return Intrinsics.areEqual(this.sourceInfo, composeStackTraceFrame.sourceInfo) && Intrinsics.areEqual(this.groupOffset, composeStackTraceFrame.groupOffset);
    }

    public final int hashCode() {
        int hashCode = this.sourceInfo.hashCode() * 31;
        Integer num = this.groupOffset;
        return hashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "ComposeStackTraceFrame(sourceInfo=" + this.sourceInfo + ", groupOffset=" + this.groupOffset + ')';
    }
}
