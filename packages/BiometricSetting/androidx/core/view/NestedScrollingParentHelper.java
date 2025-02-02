package androidx.core.view;

/* loaded from: classes.dex */
public final class NestedScrollingParentHelper {
    private int mNestedScrollAxesNonTouch;
    private int mNestedScrollAxesTouch;

    public final int getNestedScrollAxes() {
        return this.mNestedScrollAxesNonTouch | this.mNestedScrollAxesTouch;
    }

    public final void onNestedScrollAccepted(int i, int i2) {
        if (i2 == 1) {
            this.mNestedScrollAxesNonTouch = i;
        } else {
            this.mNestedScrollAxesTouch = i;
        }
    }

    public final void onStopNestedScroll(int i) {
        if (i == 1) {
            this.mNestedScrollAxesNonTouch = 0;
        } else {
            this.mNestedScrollAxesTouch = 0;
        }
    }
}
