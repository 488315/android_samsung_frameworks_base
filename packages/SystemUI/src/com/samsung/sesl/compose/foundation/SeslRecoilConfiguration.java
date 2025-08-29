package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        return ULong.m3446equalsimpl0(this.color, j) && Intrinsics.areEqual(this.feedbackAlpha, seslRecoilConfiguration.feedbackAlpha);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.feedbackAlpha.hashCode() + (Long.hashCode(this.color) * 31);
    }

    public final String toString() {
        return "SeslRecoilConfiguration(color=" + Color.m464toStringimpl(this.color) + ", feedbackAlpha=" + this.feedbackAlpha + ")";
    }

    private SeslRecoilConfiguration(long j, SeslFeedbackAlpha seslFeedbackAlpha) {
        this.color = j;
        this.feedbackAlpha = seslFeedbackAlpha;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SeslRecoilConfiguration(long j, SeslFeedbackAlpha seslFeedbackAlpha, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j = Color.Unspecified;
        }
        if ((i & 2) != 0) {
            SeslFeedbackAlpha.Companion.getClass();
            seslFeedbackAlpha = SeslFeedbackAlpha.Unspecified;
        }
        this(j, seslFeedbackAlpha, null);
    }
}
