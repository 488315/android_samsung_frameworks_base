package com.android.systemui.haptics.slider;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SliderHapticFeedbackFilter {
    public final boolean vibrateOnLowerBookend;
    public final boolean vibrateOnUpperBookend;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SliderHapticFeedbackFilter() {
        /*
            r3 = this;
            r0 = 3
            r1 = 0
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.slider.SliderHapticFeedbackFilter.<init>():void");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SliderHapticFeedbackFilter)) {
            return false;
        }
        SliderHapticFeedbackFilter sliderHapticFeedbackFilter = (SliderHapticFeedbackFilter) obj;
        return this.vibrateOnUpperBookend == sliderHapticFeedbackFilter.vibrateOnUpperBookend && this.vibrateOnLowerBookend == sliderHapticFeedbackFilter.vibrateOnLowerBookend;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.vibrateOnLowerBookend) + (Boolean.hashCode(this.vibrateOnUpperBookend) * 31);
    }

    public final String toString() {
        return "SliderHapticFeedbackFilter(vibrateOnUpperBookend=" + this.vibrateOnUpperBookend + ", vibrateOnLowerBookend=" + this.vibrateOnLowerBookend + ")";
    }

    public SliderHapticFeedbackFilter(boolean z, boolean z2) {
        this.vibrateOnUpperBookend = z;
        this.vibrateOnLowerBookend = z2;
    }

    public /* synthetic */ SliderHapticFeedbackFilter(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z, (i & 2) != 0 ? true : z2);
    }
}
