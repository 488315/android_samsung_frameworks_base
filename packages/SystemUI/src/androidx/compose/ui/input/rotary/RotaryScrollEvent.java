package androidx.compose.ui.input.rotary;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RotaryScrollEvent {
    public final float horizontalScrollPixels;
    public final int inputDeviceId;
    public final long uptimeMillis;
    public final float verticalScrollPixels;

    public RotaryScrollEvent(float f, float f2, long j, int i) {
        this.verticalScrollPixels = f;
        this.horizontalScrollPixels = f2;
        this.uptimeMillis = j;
        this.inputDeviceId = i;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof RotaryScrollEvent)) {
            return false;
        }
        RotaryScrollEvent rotaryScrollEvent = (RotaryScrollEvent) obj;
        return rotaryScrollEvent.verticalScrollPixels == this.verticalScrollPixels && rotaryScrollEvent.horizontalScrollPixels == this.horizontalScrollPixels && rotaryScrollEvent.uptimeMillis == this.uptimeMillis && rotaryScrollEvent.inputDeviceId == this.inputDeviceId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.inputDeviceId) + MoveResult$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.horizontalScrollPixels, Float.hashCode(this.verticalScrollPixels) * 31, 31), 31, this.uptimeMillis);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RotaryScrollEvent(verticalScrollPixels=");
        sb.append(this.verticalScrollPixels);
        sb.append(",horizontalScrollPixels=");
        sb.append(this.horizontalScrollPixels);
        sb.append(",uptimeMillis=");
        sb.append(this.uptimeMillis);
        sb.append(",deviceId=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.inputDeviceId, ')');
    }
}
