package com.android.systemui.haptics.slider;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SliderHapticFeedbackFilter {
    public final boolean vibrateOnLowerBookend;
    public final boolean vibrateOnUpperBookend;

    /* JADX WARN: Illegal instructions before constructor call */
    public SliderHapticFeedbackFilter() {
        boolean z = false;
        this(z, z, 3, null);
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
