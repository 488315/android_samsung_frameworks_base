package androidx.compose.runtime.tooling;

import kotlin.jvm.internal.Intrinsics;

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
        int iHashCode = this.sourceInfo.hashCode() * 31;
        Integer num = this.groupOffset;
        return iHashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "ComposeStackTraceFrame(sourceInfo=" + this.sourceInfo + ", groupOffset=" + this.groupOffset + ')';
    }
}
