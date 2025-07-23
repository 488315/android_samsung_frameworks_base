package androidx.appcompat.widget;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RtlSpacingHelper {
    public int mLeft = 0;
    public int mRight = 0;
    public int mStart = Integer.MIN_VALUE;
    public int mEnd = Integer.MIN_VALUE;
    public int mExplicitLeft = 0;
    public int mExplicitRight = 0;
    public boolean mIsRtl = false;
    public boolean mIsRelative = false;

    public final void setRelative(int i, int i2) {
        this.mStart = i;
        this.mEnd = i2;
        this.mIsRelative = true;
        if (this.mIsRtl) {
            if (i2 != Integer.MIN_VALUE) {
                this.mLeft = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.mRight = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.mLeft = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.mRight = i2;
        }
    }
}
