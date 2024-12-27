package com.samsung.android.core;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;

@Deprecated
public class CompatTranslator {
    private final CompatTranslator mParent;
    private Point mBoundsPosition = new Point();
    private Point mWindowPosition = new Point();

    public CompatTranslator(CompatTranslator parent) {
        this.mParent = parent;
    }

    private boolean shouldSavePositionInBounds() {
        return (this.mBoundsPosition.x == 0 && this.mBoundsPosition.y == 0) ? false : true;
    }

    public boolean savePositionInScreen(int windowLeft, int windowTop) {
        if (this.mWindowPosition.x != windowLeft || this.mWindowPosition.y != windowTop) {
            this.mWindowPosition.set(windowLeft, windowTop);
            return true;
        }
        return false;
    }

    public void translateToWindow(Rect outFrame) {
        outFrame.offset(-getWindowPositionX(), -getWindowPositionY());
    }

    public void translateToWindow(Point outPos) {
        outPos.offset(-getWindowPositionX(), -getWindowPositionY());
    }

    public void translateToWindow(MotionEvent outEvent) {
        outEvent.setWindowOffset(-getWindowPositionX(), -getWindowPositionY());
    }

    public void translateToScreen(Rect outFrame) {
        outFrame.offset(getWindowPositionX(), getWindowPositionY());
    }

    public void translateToScreen(PointF outPos) {
        outPos.offset(getWindowPositionX(), getWindowPositionY());
    }

    private int getWindowPositionX() {
        if (this.mParent != null) {
            return this.mParent.getWindowPositionX();
        }
        return (shouldSavePositionInBounds() ? this.mBoundsPosition : this.mWindowPosition).x;
    }

    private int getWindowPositionY() {
        if (this.mParent != null) {
            return this.mParent.getWindowPositionY();
        }
        return (shouldSavePositionInBounds() ? this.mBoundsPosition : this.mWindowPosition).y;
    }
}
