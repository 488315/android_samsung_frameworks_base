package com.android.systemui.haptics.slider;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SeekableSliderTrackerConfig {
    public final float jumpThreshold;
    public final float lowerBookendThreshold;
    public final float upperBookendThreshold;
    public final long waitTimeMillis;

    public SeekableSliderTrackerConfig() {
        this(0L, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeekableSliderTrackerConfig)) {
            return false;
        }
        SeekableSliderTrackerConfig seekableSliderTrackerConfig = (SeekableSliderTrackerConfig) obj;
        return this.waitTimeMillis == seekableSliderTrackerConfig.waitTimeMillis && Float.compare(this.jumpThreshold, seekableSliderTrackerConfig.jumpThreshold) == 0 && Float.compare(this.lowerBookendThreshold, seekableSliderTrackerConfig.lowerBookendThreshold) == 0 && Float.compare(this.upperBookendThreshold, seekableSliderTrackerConfig.upperBookendThreshold) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.upperBookendThreshold) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.lowerBookendThreshold, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.jumpThreshold, Long.hashCode(this.waitTimeMillis) * 31, 31), 31);
    }

    public final String toString() {
        return "SeekableSliderTrackerConfig(waitTimeMillis=" + this.waitTimeMillis + ", jumpThreshold=" + this.jumpThreshold + ", lowerBookendThreshold=" + this.lowerBookendThreshold + ", upperBookendThreshold=" + this.upperBookendThreshold + ")";
    }

    public SeekableSliderTrackerConfig(long j, float f, float f2, float f3) {
        this.waitTimeMillis = j;
        this.jumpThreshold = f;
        this.lowerBookendThreshold = f2;
        this.upperBookendThreshold = f3;
    }

    public /* synthetic */ SeekableSliderTrackerConfig(long j, float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 100L : j, (i & 2) != 0 ? 0.02f : f, (i & 4) != 0 ? 0.05f : f2, (i & 8) != 0 ? 0.95f : f3);
    }
}
