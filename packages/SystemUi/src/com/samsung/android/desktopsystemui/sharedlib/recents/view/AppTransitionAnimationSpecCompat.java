package com.samsung.android.desktopsystemui.sharedlib.recents.view;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.AppTransitionAnimationSpec;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AppTransitionAnimationSpecCompat {
    private Bitmap mBuffer;
    private Rect mRect;
    private int mTaskId;

    public AppTransitionAnimationSpecCompat(int i, Bitmap bitmap, Rect rect) {
        this.mTaskId = i;
        this.mBuffer = bitmap;
        this.mRect = rect;
    }

    public AppTransitionAnimationSpec toAppTransitionAnimationSpec() {
        int i = this.mTaskId;
        Bitmap bitmap = this.mBuffer;
        return new AppTransitionAnimationSpec(i, bitmap != null ? bitmap.getHardwareBuffer() : null, this.mRect);
    }
}
