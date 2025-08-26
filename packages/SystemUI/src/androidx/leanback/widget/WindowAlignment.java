package androidx.leanback.widget;

/* loaded from: classes.dex */
public final class WindowAlignment {
    public final Axis horizontal;
    public Axis mMainAxis;
    public Axis mSecondAxis;
    public final Axis vertical;

    public final class Axis {
        public int mMaxScroll;
        public int mMinScroll;
        public int mPaddingMax;
        public int mPaddingMin;
        public boolean mReversedFlow;
        public int mSize;
        public int mWindowAlignment = 3;
        public int mMinEdge = Integer.MIN_VALUE;
        public int mMaxEdge = Integer.MAX_VALUE;

        public Axis(String str) {
        }

        public final int calculateKeyline() {
            if (!this.mReversedFlow) {
                return (int) ((this.mSize * 50.0f) / 100.0f);
            }
            int i = this.mSize;
            return i - ((int) ((i * 50.0f) / 100.0f));
        }

        public final int getScroll(int i) {
            int i2;
            int i3;
            int i4 = this.mSize;
            int iCalculateKeyline = calculateKeyline();
            int i5 = this.mMinEdge;
            boolean z = i5 == Integer.MIN_VALUE;
            int i6 = this.mMaxEdge;
            boolean z2 = i6 == Integer.MAX_VALUE;
            if (!z) {
                int i7 = this.mPaddingMin;
                int i8 = iCalculateKeyline - i7;
                if (this.mReversedFlow ? (this.mWindowAlignment & 2) != 0 : (this.mWindowAlignment & 1) != 0) {
                    if (i - i5 <= i8) {
                        int i9 = i5 - i7;
                        return (z2 || i9 <= (i3 = this.mMaxScroll)) ? i9 : i3;
                    }
                }
            }
            if (!z2) {
                int i10 = this.mPaddingMax;
                int i11 = (i4 - iCalculateKeyline) - i10;
                if (this.mReversedFlow ? (1 & this.mWindowAlignment) != 0 : (this.mWindowAlignment & 2) != 0) {
                    if (i6 - i <= i11) {
                        int i12 = i6 - (i4 - i10);
                        return (z || i12 >= (i2 = this.mMinScroll)) ? i12 : i2;
                    }
                }
            }
            return i - iCalculateKeyline;
        }

        public final String toString() {
            return " min:" + this.mMinEdge + " " + this.mMinScroll + " max:" + this.mMaxEdge + " " + this.mMaxScroll;
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0035, code lost:
        
            r6.mMinScroll = r0 - r6.mPaddingMin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0051, code lost:
        
            r6.mMaxScroll = (r4 - r6.mPaddingMin) - r7;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateMinMax(int i, int i2, int i3, int i4) {
            this.mMinEdge = i;
            this.mMaxEdge = i2;
            int i5 = (this.mSize - this.mPaddingMin) - this.mPaddingMax;
            int iCalculateKeyline = calculateKeyline();
            int i6 = this.mMinEdge;
            boolean z = i6 == Integer.MIN_VALUE;
            int i7 = this.mMaxEdge;
            boolean z2 = i7 == Integer.MAX_VALUE;
            if (!z) {
                if (this.mReversedFlow) {
                    this.mMinScroll = i3 - iCalculateKeyline;
                } else {
                    this.mMinScroll = i3 - iCalculateKeyline;
                }
            }
            if (!z2) {
                if (this.mReversedFlow) {
                    this.mMaxScroll = i4 - iCalculateKeyline;
                } else {
                    this.mMaxScroll = i4 - iCalculateKeyline;
                }
            }
            if (z2 || z) {
                return;
            }
            if (this.mReversedFlow) {
                int i8 = this.mWindowAlignment;
                if ((i8 & 1) != 0) {
                    this.mMinScroll = Math.min(this.mMinScroll, this.mMaxScroll);
                    return;
                } else {
                    if ((i8 & 2) != 0) {
                        int iMin = Math.min(this.mMinScroll, i4 - iCalculateKeyline);
                        this.mMinScroll = iMin;
                        this.mMaxScroll = Math.max(iMin, this.mMaxScroll);
                        return;
                    }
                    return;
                }
            }
            int i9 = this.mWindowAlignment;
            if ((i9 & 1) != 0) {
                this.mMaxScroll = Math.max(this.mMinScroll, this.mMaxScroll);
            } else if ((i9 & 2) != 0) {
                int iMax = Math.max(this.mMaxScroll, i3 - iCalculateKeyline);
                this.mMaxScroll = iMax;
                this.mMinScroll = Math.min(this.mMinScroll, iMax);
            }
        }
    }

    public WindowAlignment() {
        Axis axis = new Axis("vertical");
        this.vertical = axis;
        Axis axis2 = new Axis("horizontal");
        this.horizontal = axis2;
        this.mMainAxis = axis2;
        this.mSecondAxis = axis;
    }

    public final String toString() {
        return "horizontal=" + this.horizontal + "; vertical=" + this.vertical;
    }
}
