package androidx.recyclerview.widget;

/* loaded from: classes.dex */
final class LayoutState {
    int mAvailable;
    int mCurrentPosition;
    int mItemDirection;
    int mLayoutDirection;
    boolean mRecycle = true;
    int mStartLine = 0;
    int mEndLine = 0;

    LayoutState() {
    }

    public final String toString() {
        return "LayoutState{mAvailable=" + this.mAvailable + ", mCurrentPosition=" + this.mCurrentPosition + ", mItemDirection=" + this.mItemDirection + ", mLayoutDirection=" + this.mLayoutDirection + ", mStartLine=" + this.mStartLine + ", mEndLine=" + this.mEndLine + '}';
    }
}
