package androidx.compose.ui.input.pointer.util;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DataPointAtTime {
    public float dataPoint;
    public long time;

    public DataPointAtTime(long j, float f) {
        this.time = j;
        this.dataPoint = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataPointAtTime)) {
            return false;
        }
        DataPointAtTime dataPointAtTime = (DataPointAtTime) obj;
        return this.time == dataPointAtTime.time && Float.compare(this.dataPoint, dataPointAtTime.dataPoint) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.dataPoint) + (Long.hashCode(this.time) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DataPointAtTime(time=");
        sb.append(this.time);
        sb.append(", dataPoint=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.dataPoint, ')');
    }
}
