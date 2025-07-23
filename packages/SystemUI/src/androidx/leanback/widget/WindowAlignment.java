package androidx.leanback.widget;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WindowAlignment {
    public final Axis horizontal;
    public Axis mMainAxis;
    public Axis mSecondAxis;
    public final Axis vertical;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int calculateKeyline = calculateKeyline();
            int i5 = this.mMinEdge;
            boolean z = i5 == Integer.MIN_VALUE;
            int i6 = this.mMaxEdge;
            boolean z2 = i6 == Integer.MAX_VALUE;
            if (!z) {
                int i7 = this.mPaddingMin;
                int i8 = calculateKeyline - i7;
                if (this.mReversedFlow ? (this.mWindowAlignment & 2) != 0 : (this.mWindowAlignment & 1) != 0) {
                    if (i - i5 <= i8) {
                        int i9 = i5 - i7;
                        return (z2 || i9 <= (i3 = this.mMaxScroll)) ? i9 : i3;
                    }
                }
            }
            if (!z2) {
                int i10 = this.mPaddingMax;
                int i11 = (i4 - calculateKeyline) - i10;
                if (this.mReversedFlow ? (1 & this.mWindowAlignment) != 0 : (this.mWindowAlignment & 2) != 0) {
                    if (i6 - i <= i11) {
                        int i12 = i6 - (i4 - i10);
                        return (z || i12 >= (i2 = this.mMinScroll)) ? i12 : i2;
                    }
                }
            }
            return i - calculateKeyline;
        }

        public final String toString() {
            return " min:" + this.mMinEdge + " " + this.mMinScroll + " max:" + this.mMaxEdge + " " + this.mMaxScroll;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0035, code lost:
        
            r6.mMinScroll = r0 - r6.mPaddingMin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0051, code lost:
        
            r6.mMaxScroll = (r4 - r6.mPaddingMin) - r7;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateMinMax(int r7, int r8, int r9, int r10) {
            /*
                r6 = this;
                r6.mMinEdge = r7
                r6.mMaxEdge = r8
                int r7 = r6.mSize
                int r8 = r6.mPaddingMin
                int r7 = r7 - r8
                int r8 = r6.mPaddingMax
                int r7 = r7 - r8
                int r8 = r6.calculateKeyline()
                int r0 = r6.mMinEdge
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = 0
                r3 = 1
                if (r0 != r1) goto L1a
                r1 = r3
                goto L1b
            L1a:
                r1 = r2
            L1b:
                int r4 = r6.mMaxEdge
                r5 = 2147483647(0x7fffffff, float:NaN)
                if (r4 != r5) goto L23
                r2 = r3
            L23:
                if (r1 != 0) goto L3f
                boolean r5 = r6.mReversedFlow
                if (r5 != 0) goto L2f
                int r5 = r6.mWindowAlignment
                r5 = r5 & r3
                if (r5 == 0) goto L3b
                goto L35
            L2f:
                int r5 = r6.mWindowAlignment
                r5 = r5 & 2
                if (r5 == 0) goto L3b
            L35:
                int r5 = r6.mPaddingMin
                int r0 = r0 - r5
                r6.mMinScroll = r0
                goto L3f
            L3b:
                int r0 = r9 - r8
                r6.mMinScroll = r0
            L3f:
                if (r2 != 0) goto L5c
                boolean r0 = r6.mReversedFlow
                if (r0 != 0) goto L4c
                int r0 = r6.mWindowAlignment
                r0 = r0 & 2
                if (r0 == 0) goto L58
                goto L51
            L4c:
                int r0 = r6.mWindowAlignment
                r0 = r0 & r3
                if (r0 == 0) goto L58
            L51:
                int r0 = r6.mPaddingMin
                int r4 = r4 - r0
                int r4 = r4 - r7
                r6.mMaxScroll = r4
                goto L5c
            L58:
                int r7 = r10 - r8
                r6.mMaxScroll = r7
            L5c:
                if (r2 != 0) goto Lb1
                if (r1 != 0) goto Lb1
                boolean r7 = r6.mReversedFlow
                if (r7 != 0) goto L8b
                int r7 = r6.mWindowAlignment
                r10 = r7 & 1
                if (r10 == 0) goto L75
                int r7 = r6.mMinScroll
                int r8 = r6.mMaxScroll
                int r7 = java.lang.Math.max(r7, r8)
                r6.mMaxScroll = r7
                return
            L75:
                r7 = r7 & 2
                if (r7 == 0) goto Lb1
                int r7 = r6.mMaxScroll
                int r9 = r9 - r8
                int r7 = java.lang.Math.max(r7, r9)
                r6.mMaxScroll = r7
                int r8 = r6.mMinScroll
                int r7 = java.lang.Math.min(r8, r7)
                r6.mMinScroll = r7
                return
            L8b:
                int r7 = r6.mWindowAlignment
                r9 = r7 & 1
                if (r9 == 0) goto L9c
                int r7 = r6.mMinScroll
                int r8 = r6.mMaxScroll
                int r7 = java.lang.Math.min(r7, r8)
                r6.mMinScroll = r7
                return
            L9c:
                r7 = r7 & 2
                if (r7 == 0) goto Lb1
                int r7 = r6.mMinScroll
                int r10 = r10 - r8
                int r7 = java.lang.Math.min(r7, r10)
                r6.mMinScroll = r7
                int r8 = r6.mMaxScroll
                int r7 = java.lang.Math.max(r7, r8)
                r6.mMaxScroll = r7
            Lb1:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.WindowAlignment.Axis.updateMinMax(int, int, int, int):void");
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
