package android.view;

import android.graphics.Point;
import android.graphics.Rect;
import java.util.Objects;

/* loaded from: classes4.dex */
public class ScrollCaptureSession {
    private final Point mPositionInWindow;
    private final Rect mScrollBounds;
    private final Surface mSurface;

    public ScrollCaptureSession(Surface surface, Rect rect, Point point) {
        this.mSurface = (Surface) Objects.requireNonNull(surface);
        this.mScrollBounds = (Rect) Objects.requireNonNull(rect);
        this.mPositionInWindow = (Point) Objects.requireNonNull(point);
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public Rect getScrollBounds() {
        return this.mScrollBounds;
    }

    public Point getPositionInWindow() {
        return this.mPositionInWindow;
    }
}
