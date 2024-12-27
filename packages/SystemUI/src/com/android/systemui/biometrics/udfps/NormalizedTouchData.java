package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NormalizedTouchData {
    public final long gestureStart;
    public final float major;
    public final float minor;
    public final float orientation;
    public final int pointerId;
    public final long time;
    public final float x;
    public final float y;

    public NormalizedTouchData() {
        this(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, 0L, 255, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NormalizedTouchData)) {
            return false;
        }
        NormalizedTouchData normalizedTouchData = (NormalizedTouchData) obj;
        return this.pointerId == normalizedTouchData.pointerId && Float.compare(this.x, normalizedTouchData.x) == 0 && Float.compare(this.y, normalizedTouchData.y) == 0 && Float.compare(this.minor, normalizedTouchData.minor) == 0 && Float.compare(this.major, normalizedTouchData.major) == 0 && Float.compare(this.orientation, normalizedTouchData.orientation) == 0 && this.time == normalizedTouchData.time && this.gestureStart == normalizedTouchData.gestureStart;
    }

    public final int hashCode() {
        return Long.hashCode(this.gestureStart) + Scale$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.orientation, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.major, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.minor, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.y, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.x, Integer.hashCode(this.pointerId) * 31, 31), 31), 31), 31), 31), 31, this.time);
    }

    public final boolean isWithinBounds(Rect rect) {
        float f = rect.left;
        float f2 = this.x;
        if (f <= f2 && rect.right >= f2) {
            float f3 = rect.top;
            float f4 = this.y;
            if (f3 <= f4 && rect.bottom >= f4) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NormalizedTouchData(pointerId=");
        sb.append(this.pointerId);
        sb.append(", x=");
        sb.append(this.x);
        sb.append(", y=");
        sb.append(this.y);
        sb.append(", minor=");
        sb.append(this.minor);
        sb.append(", major=");
        sb.append(this.major);
        sb.append(", orientation=");
        sb.append(this.orientation);
        sb.append(", time=");
        sb.append(this.time);
        sb.append(", gestureStart=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.gestureStart, ")", sb);
    }

    public NormalizedTouchData(int i, float f, float f2, float f3, float f4, float f5, long j, long j2) {
        this.pointerId = i;
        this.x = f;
        this.y = f2;
        this.minor = f3;
        this.major = f4;
        this.orientation = f5;
        this.time = j;
        this.gestureStart = j2;
    }

    public /* synthetic */ NormalizedTouchData(int i, float f, float f2, float f3, float f4, float f5, long j, long j2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? -1 : i, (i2 & 2) != 0 ? 0.0f : f, (i2 & 4) != 0 ? 0.0f : f2, (i2 & 8) != 0 ? 0.0f : f3, (i2 & 16) != 0 ? 0.0f : f4, (i2 & 32) == 0 ? f5 : 0.0f, (i2 & 64) != 0 ? 0L : j, (i2 & 128) == 0 ? j2 : 0L);
    }
}
