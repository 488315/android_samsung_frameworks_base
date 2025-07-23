package kotlin.ranges;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ClosedFloatRange implements ClosedFloatingPointRange {
    public final float _endInclusive;
    public final float _start;

    public ClosedFloatRange(float f, float f2) {
        this._start = f;
        this._endInclusive = f2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean contains(Comparable comparable) {
        float floatValue = ((Number) comparable).floatValue();
        return floatValue >= this._start && floatValue <= this._endInclusive;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ClosedFloatRange)) {
            return false;
        }
        if (isEmpty() && ((ClosedFloatRange) obj).isEmpty()) {
            return true;
        }
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) obj;
        return this._start == closedFloatRange._start && this._endInclusive == closedFloatRange._endInclusive;
    }

    public final int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return Float.hashCode(this._endInclusive) + (Float.hashCode(this._start) * 31);
    }

    public final boolean isEmpty() {
        return this._start > this._endInclusive;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean lessThanOrEquals(Comparable comparable, Comparable comparable2) {
        return ((Number) comparable).floatValue() <= ((Number) comparable2).floatValue();
    }

    public final String toString() {
        return this._start + ".." + this._endInclusive;
    }
}
