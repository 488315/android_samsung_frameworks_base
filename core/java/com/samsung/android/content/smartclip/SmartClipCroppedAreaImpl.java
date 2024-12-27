package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.view.View;

public class SmartClipCroppedAreaImpl implements SemSmartClipCroppedArea {
    private Rect mRect;

    public SmartClipCroppedAreaImpl(Rect rect) {
        this.mRect = null;
        this.mRect = rect;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipCroppedArea
    public Rect getRect() {
        return new Rect(this.mRect);
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipCroppedArea
    public boolean intersects(View view) {
        if (view == null || this.mRect == null) {
            return false;
        }
        Rect viewRect = SmartClipUtils.getViewBoundsOnScreen(view);
        return intersects(viewRect);
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipCroppedArea
    public boolean intersects(Rect rect) {
        if (rect == null || this.mRect == null) {
            return false;
        }
        return Rect.intersects(getRect(), rect);
    }
}
