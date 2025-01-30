package androidx.leanback.widget;

import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WindowAlignment {
    public final Axis horizontal;
    public Axis mMainAxis;
    public Axis mSecondAxis;
    public final Axis vertical;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Axis {
        public int mMaxScroll;
        public int mMinScroll;
        public int mPaddingMax;
        public int mPaddingMin;
        public boolean mReversedFlow;
        public int mSize;
        public final int mPreferredKeyLine = 2;
        public int mWindowAlignment = 3;
        public final float mWindowAlignmentOffsetPercent = 50.0f;
        public int mMinEdge = VideoPlayer.MEDIA_ERROR_SYSTEM;
        public int mMaxEdge = Integer.MAX_VALUE;

        public Axis(String str) {
        }

        public final int calculateKeyline() {
            boolean z = this.mReversedFlow;
            float f = this.mWindowAlignmentOffsetPercent;
            if (z) {
                int i = this.mSize;
                int i2 = i + 0;
                return f != -1.0f ? i2 - ((int) ((i * f) / 100.0f)) : i2;
            }
            if (f != -1.0f) {
                return 0 + ((int) ((this.mSize * f) / 100.0f));
            }
            return 0;
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

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
        
            r7.mMinScroll = r0 - r7.mPaddingMin;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0053, code lost:
        
            r7.mMaxScroll = (r4 - r7.mPaddingMin) - r8;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateMinMax(int i, int i2, int i3, int i4) {
            this.mMinEdge = i;
            this.mMaxEdge = i2;
            int i5 = (this.mSize - this.mPaddingMin) - this.mPaddingMax;
            int calculateKeyline = calculateKeyline();
            int i6 = this.mMinEdge;
            boolean z = i6 == Integer.MIN_VALUE;
            int i7 = this.mMaxEdge;
            boolean z2 = i7 == Integer.MAX_VALUE;
            if (!z) {
                if (this.mReversedFlow) {
                    this.mMinScroll = i3 - calculateKeyline;
                } else {
                    this.mMinScroll = i3 - calculateKeyline;
                }
            }
            if (!z2) {
                if (this.mReversedFlow) {
                    this.mMaxScroll = i4 - calculateKeyline;
                } else {
                    this.mMaxScroll = i4 - calculateKeyline;
                }
            }
            if (z2 || z) {
                return;
            }
            boolean z3 = this.mReversedFlow;
            int i8 = this.mPreferredKeyLine;
            if (z3) {
                int i9 = this.mWindowAlignment;
                if ((i9 & 1) != 0) {
                    if ((i8 & 1) != 0) {
                        this.mMaxScroll = Math.max(this.mMaxScroll, i3 - calculateKeyline);
                    }
                    this.mMinScroll = Math.min(this.mMinScroll, this.mMaxScroll);
                    return;
                } else {
                    if ((i9 & 2) != 0) {
                        if ((i8 & 2) != 0) {
                            this.mMinScroll = Math.min(this.mMinScroll, i4 - calculateKeyline);
                        }
                        this.mMaxScroll = Math.max(this.mMinScroll, this.mMaxScroll);
                        return;
                    }
                    return;
                }
            }
            int i10 = this.mWindowAlignment;
            if ((i10 & 1) != 0) {
                if ((i8 & 1) != 0) {
                    this.mMinScroll = Math.min(this.mMinScroll, i4 - calculateKeyline);
                }
                this.mMaxScroll = Math.max(this.mMinScroll, this.mMaxScroll);
            } else if ((i10 & 2) != 0) {
                if ((i8 & 2) != 0) {
                    this.mMaxScroll = Math.max(this.mMaxScroll, i3 - calculateKeyline);
                }
                this.mMinScroll = Math.min(this.mMinScroll, this.mMaxScroll);
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
