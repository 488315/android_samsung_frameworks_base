package com.android.systemui.statusbar.policy;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSClockBellSound {
    public final String DateText;
    public final boolean Demo;
    public final String QuickStarDateText;
    public final String ShortDateText;
    public final boolean ShowSecondsClock;
    public final String TimeContentDescription;
    public final String TimeText;
    public final String TimeTextWithSeconds;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QSClockBellSound(String str, String str2, String str3, String str4, boolean z, String str5, boolean z2, String str6) {
        this.TimeText = str;
        this.TimeContentDescription = str2;
        this.DateText = str3;
        this.ShortDateText = str4;
        this.Demo = z;
        this.TimeTextWithSeconds = str5;
        this.ShowSecondsClock = z2;
        this.QuickStarDateText = str6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSClockBellSound)) {
            return false;
        }
        QSClockBellSound qSClockBellSound = (QSClockBellSound) obj;
        return Intrinsics.areEqual(this.TimeText, qSClockBellSound.TimeText) && Intrinsics.areEqual(this.TimeContentDescription, qSClockBellSound.TimeContentDescription) && Intrinsics.areEqual(this.DateText, qSClockBellSound.DateText) && Intrinsics.areEqual(this.ShortDateText, qSClockBellSound.ShortDateText) && this.Demo == qSClockBellSound.Demo && Intrinsics.areEqual(this.TimeTextWithSeconds, qSClockBellSound.TimeTextWithSeconds) && this.ShowSecondsClock == qSClockBellSound.ShowSecondsClock && Intrinsics.areEqual(this.QuickStarDateText, qSClockBellSound.QuickStarDateText);
    }

    public final int hashCode() {
        return this.QuickStarDateText.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.TimeText.hashCode() * 31, 31, this.TimeContentDescription), 31, this.DateText), 31, this.ShortDateText), 31, this.Demo), 31, this.TimeTextWithSeconds), 31, this.ShowSecondsClock);
    }

    public final String toString() {
        return "QSClockBellSound - TimeText:" + this.TimeText + ", TimeContentDescription:" + this.TimeContentDescription + ", DateText:" + this.DateText + ", ShortDateText:" + this.ShortDateText + ", Demo:" + this.Demo;
    }
}
