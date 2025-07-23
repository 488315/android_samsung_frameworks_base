package android.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
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

    public ScrollCaptureTarget(View view, Rect rect, Point point, ScrollCaptureCallback scrollCaptureCallback) {
        View view2 = (View) Objects.requireNonNull(view);
        this.mContainingView = view2;
        this.mHint = view2.getScrollCaptureHint();
        this.mCallback = (ScrollCaptureCallback) Objects.requireNonNull(scrollCaptureCallback);
        this.mLocalVisibleRect = (Rect) Objects.requireNonNull(rect);
        this.mPositionInWindow = (Point) Objects.requireNonNull(point);
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

    public void setScrollBounds(Rect rect) {
        Rect copyOrNull = Rect.copyOrNull(rect);
        this.mScrollBounds = copyOrNull;
        if (copyOrNull == null || copyOrNull.intersect(0, 0, this.mContainingView.getWidth(), this.mContainingView.getHeight())) {
            return;
        }
        this.mScrollBounds.setEmpty();
    }

    public void updatePositionInWindow() {
        this.mContainingView.getLocationInWindow(this.mTmpIntArr);
        this.mPositionInWindow.x = this.mTmpIntArr[0];
        this.mPositionInWindow.y = this.mTmpIntArr[1];
    }

    public String toString() {
        return "ScrollCaptureTarget{view=" + this.mContainingView + ", callback=" + this.mCallback + ", scrollBounds=" + this.mScrollBounds + ", localVisibleRect=" + this.mLocalVisibleRect + ", positionInWindow=" + this.mPositionInWindow + "}";
    }

    void dump(PrintWriter printWriter) {
        String str;
        printWriter.println("view: " + getContainingView());
        printWriter.println("hint: " + this.mHint);
        printWriter.println("callback: " + this.mCallback);
        StringBuilder sb = new StringBuilder("scrollBounds: ");
        Rect rect = this.mScrollBounds;
        String str2 = PerfettoProtoLogImpl.NULL_STRING;
        sb.append(rect == null ? PerfettoProtoLogImpl.NULL_STRING : rect.toShortString());
        printWriter.println(sb.toString());
        Point positionInWindow = getPositionInWindow();
        StringBuilder sb2 = new StringBuilder("positionInWindow: ");
        if (positionInWindow == null) {
            str = PerfettoProtoLogImpl.NULL_STRING;
        } else {
            str = NavigationBarInflaterView.SIZE_MOD_START + positionInWindow.x + "," + positionInWindow.y + NavigationBarInflaterView.SIZE_MOD_END;
        }
        sb2.append(str);
        printWriter.println(sb2.toString());
        Rect localVisibleRect = getLocalVisibleRect();
        StringBuilder sb3 = new StringBuilder("localVisibleRect: ");
        if (localVisibleRect != null) {
            str2 = localVisibleRect.toShortString();
        }
        sb3.append(str2);
        printWriter.println(sb3.toString());
    }
}
