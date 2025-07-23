package android.window;

import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public final class BackEvent {
    public static final int EDGE_LEFT = 0;
    public static final int EDGE_NONE = 2;
    public static final int EDGE_RIGHT = 1;
    private final long mFrameTimeMillis;
    private final float mProgress;
    private final int mSwipeEdge;
    private final float mTouchX;
    private final float mTouchY;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SwipeEdge {
    }

    public static BackEvent fromBackMotionEvent(BackMotionEvent backMotionEvent) {
        if (Flags.predictiveBackTimestampApi()) {
            return new BackEvent(backMotionEvent.getTouchX(), backMotionEvent.getTouchY(), backMotionEvent.getProgress(), backMotionEvent.getSwipeEdge(), backMotionEvent.getFrameTimeMillis());
        }
        return new BackEvent(backMotionEvent.getTouchX(), backMotionEvent.getTouchY(), backMotionEvent.getProgress(), backMotionEvent.getSwipeEdge());
    }

    public BackEvent(float f, float f2, float f3, int i) {
        this.mTouchX = f;
        this.mTouchY = f2;
        this.mProgress = f3;
        this.mSwipeEdge = i;
        this.mFrameTimeMillis = 0L;
    }

    public BackEvent(float f, float f2, float f3, int i, long j) {
        this.mTouchX = f;
        this.mTouchY = f2;
        this.mProgress = f3;
        this.mSwipeEdge = i;
        this.mFrameTimeMillis = j;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public float getTouchX() {
        return this.mTouchX;
    }

    public float getTouchY() {
        return this.mTouchY;
    }

    public int getSwipeEdge() {
        return this.mSwipeEdge;
    }

    public long getFrameTimeMillis() {
        return this.mFrameTimeMillis;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BackEvent)) {
            return false;
        }
        BackEvent backEvent = (BackEvent) obj;
        return this.mTouchX == backEvent.mTouchX && this.mTouchY == backEvent.mTouchY && this.mProgress == backEvent.mProgress && this.mSwipeEdge == backEvent.mSwipeEdge && this.mFrameTimeMillis == backEvent.mFrameTimeMillis;
    }

    public String toString() {
        return "BackEvent{mTouchX=" + this.mTouchX + ", mTouchY=" + this.mTouchY + ", mProgress=" + this.mProgress + ", mSwipeEdge=" + this.mSwipeEdge + ", mFrameTimeMillis=" + this.mFrameTimeMillis + "}";
    }
}
