package android.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ScrollCaptureTarget {
    private final ScrollCaptureCallback mCallback;
    private final View mContainingView;
    private final int mHint;
    private final Rect mLocalVisibleRect;
    private final Point mPositionInWindow;
    private Rect mScrollBounds;
    private final int[] mTmpIntArr = new int[2];

    public ScrollCaptureTarget(View scrollTarget, Rect localVisibleRect, Point positionInWindow, ScrollCaptureCallback callback) {
        this.mContainingView = (View) Objects.requireNonNull(scrollTarget);
        this.mHint = this.mContainingView.getScrollCaptureHint();
        this.mCallback = (ScrollCaptureCallback) Objects.requireNonNull(callback);
        this.mLocalVisibleRect = (Rect) Objects.requireNonNull(localVisibleRect);
        this.mPositionInWindow = (Point) Objects.requireNonNull(positionInWindow);
    }

    public int getHint() {
        return this.mHint;
    }

    public ScrollCaptureCallback getCallback() {
        return this.mCallback;
    }

    public View getContainingView() {
        return this.mContainingView;
    }

    public Rect getLocalVisibleRect() {
        return this.mLocalVisibleRect;
    }

    public Point getPositionInWindow() {
        return this.mPositionInWindow;
    }

    public Rect getScrollBounds() {
        return this.mScrollBounds;
    }

    public void setScrollBounds(Rect scrollBounds) {
        this.mScrollBounds = Rect.copyOrNull(scrollBounds);
        if (this.mScrollBounds != null && !this.mScrollBounds.intersect(0, 0, this.mContainingView.getWidth(), this.mContainingView.getHeight())) {
            this.mScrollBounds.setEmpty();
        }
    }

    public void updatePositionInWindow() {
        this.mContainingView.getLocationInWindow(this.mTmpIntArr);
        this.mPositionInWindow.x = this.mTmpIntArr[0];
        this.mPositionInWindow.y = this.mTmpIntArr[1];
    }

    public String toString() {
        return "ScrollCaptureTarget{view=" + this.mContainingView + ", callback=" + this.mCallback + ", scrollBounds=" + this.mScrollBounds + ", localVisibleRect=" + this.mLocalVisibleRect + ", positionInWindow=" + this.mPositionInWindow + "}";
    }

    void dump(PrintWriter writer) {
        View view = getContainingView();
        writer.println("view: " + view);
        writer.println("hint: " + this.mHint);
        writer.println("callback: " + this.mCallback);
        writer.println("scrollBounds: " + (this.mScrollBounds == null ? "null" : this.mScrollBounds.toShortString()));
        Point inWindow = getPositionInWindow();
        writer.println("positionInWindow: " + (inWindow == null ? "null" : NavigationBarInflaterView.SIZE_MOD_START + inWindow.x + "," + inWindow.y + NavigationBarInflaterView.SIZE_MOD_END));
        Rect localVisible = getLocalVisibleRect();
        writer.println("localVisibleRect: " + (localVisible != null ? localVisible.toShortString() : "null"));
    }
}
