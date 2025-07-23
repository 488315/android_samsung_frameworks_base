package com.android.systemui.haptics.slider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SliderEvent {
    public final float currentProgress;
    public final SliderEventType type;

    public SliderEvent(SliderEventType sliderEventType, float f) {
        this.type = sliderEventType;
        this.currentProgress = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SliderEvent)) {
            return false;
        }
        SliderEvent sliderEvent = (SliderEvent) obj;
        return this.type == sliderEvent.type && Float.compare(this.currentProgress, sliderEvent.currentProgress) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.currentProgress) + (this.type.hashCode() * 31);
    }

    public final String toString() {
        return "SliderEvent(type=" + this.type + ", currentProgress=" + this.currentProgress + ")";
    }
}
