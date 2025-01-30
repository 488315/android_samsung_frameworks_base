package kotlin.ranges;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ClosedFloatRange {
    public final float _endInclusive;
    public final float _start;

    public ClosedFloatRange(float f, float f2) {
        this._start = f;
        this._endInclusive = f2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
    
        if ((r2._start > r2._endInclusive) == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        if (!(obj instanceof ClosedFloatRange)) {
            return false;
        }
        float f = this._start;
        float f2 = this._endInclusive;
        if (f > f2) {
            ClosedFloatRange closedFloatRange = (ClosedFloatRange) obj;
        }
        ClosedFloatRange closedFloatRange2 = (ClosedFloatRange) obj;
        if (!(f == closedFloatRange2._start)) {
            return false;
        }
        if (!(f2 == closedFloatRange2._endInclusive)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        float f = this._start;
        float f2 = this._endInclusive;
        if (f > f2) {
            return -1;
        }
        return Float.hashCode(f2) + (Float.hashCode(f) * 31);
    }

    public final String toString() {
        return this._start + ".." + this._endInclusive;
    }
}
