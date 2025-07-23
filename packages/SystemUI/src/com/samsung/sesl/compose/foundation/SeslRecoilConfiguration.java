package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslRecoilConfiguration {
    public final long color;
    public final SeslFeedbackAlpha feedbackAlpha;

    public /* synthetic */ SeslRecoilConfiguration(long j, SeslFeedbackAlpha seslFeedbackAlpha, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, seslFeedbackAlpha);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslRecoilConfiguration)) {
            return false;
        }
        SeslRecoilConfiguration seslRecoilConfiguration = (SeslRecoilConfiguration) obj;
        long j = seslRecoilConfiguration.color;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.color, j) && Intrinsics.areEqual(this.feedbackAlpha, seslRecoilConfiguration.feedbackAlpha);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.feedbackAlpha.hashCode() + (Long.hashCode(this.color) * 31);
    }

    public final String toString() {
        return "SeslRecoilConfiguration(color=" + Color.m462toStringimpl(this.color) + ", feedbackAlpha=" + this.feedbackAlpha + ")";
    }

    private SeslRecoilConfiguration(long j, SeslFeedbackAlpha seslFeedbackAlpha) {
        this.color = j;
        this.feedbackAlpha = seslFeedbackAlpha;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SeslRecoilConfiguration(long r1, com.samsung.sesl.compose.foundation.SeslFeedbackAlpha r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
        /*
            r0 = this;
            r5 = r4 & 1
            if (r5 == 0) goto Lb
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.graphics.Color.Unspecified
        Lb:
            r4 = r4 & 2
            if (r4 == 0) goto L16
            com.samsung.sesl.compose.foundation.SeslFeedbackAlpha$Companion r3 = com.samsung.sesl.compose.foundation.SeslFeedbackAlpha.Companion
            r3.getClass()
            com.samsung.sesl.compose.foundation.SeslFeedbackAlpha r3 = com.samsung.sesl.compose.foundation.SeslFeedbackAlpha.Unspecified
        L16:
            r4 = 0
            r0.<init>(r1, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.SeslRecoilConfiguration.<init>(long, com.samsung.sesl.compose.foundation.SeslFeedbackAlpha, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
