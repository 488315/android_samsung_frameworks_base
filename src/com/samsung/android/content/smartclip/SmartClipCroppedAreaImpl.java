package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.view.View;

/* loaded from: classes6.dex */
public class SmartClipCroppedAreaImpl implements SemSmartClipCroppedArea {
    private Rect mRect;

    public SmartClipCroppedAreaImpl(Rect rect) {
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
        return intersects(SmartClipUtils.getViewBoundsOnScreen(view));
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipCroppedArea
    public boolean intersects(Rect rect) {
        if (rect == null || this.mRect == null) {
            return false;
        }
        return Rect.intersects(getRect(), rect);
    }
}
