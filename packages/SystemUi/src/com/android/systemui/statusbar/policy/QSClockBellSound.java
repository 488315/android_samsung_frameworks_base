package com.android.systemui.statusbar.policy;

import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSClockBellSound {
    public final String DateText;
    public final boolean Demo;
    public final String QuickStarDateText;
    public final String ShortDateText;
    public final boolean ShowSecondsClock;
    public final String TimeContentDescription;
    public final String TimeText;
    public final String TimeTextWithSeconds;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.ShortDateText, AppInfo$$ExternalSyntheticOutline0.m41m(this.DateText, AppInfo$$ExternalSyntheticOutline0.m41m(this.TimeContentDescription, this.TimeText.hashCode() * 31, 31), 31), 31);
        boolean z = this.Demo;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int m41m2 = AppInfo$$ExternalSyntheticOutline0.m41m(this.TimeTextWithSeconds, (m41m + i) * 31, 31);
        boolean z2 = this.ShowSecondsClock;
        return this.QuickStarDateText.hashCode() + ((m41m2 + (z2 ? 1 : z2 ? 1 : 0)) * 31);
    }

    public final String toString() {
        return "QSClockBellSound - TimeText:" + this.TimeText + ", TimeContentDescription:" + this.TimeContentDescription + ", DateText:" + this.DateText + ", ShortDateText:" + this.ShortDateText + ", Demo:" + this.Demo + ", QuickStar-Second(" + this.ShowSecondsClock + "|" + this.TimeTextWithSeconds + "), QuickStar-DateText:" + this.QuickStarDateText;
    }
}
