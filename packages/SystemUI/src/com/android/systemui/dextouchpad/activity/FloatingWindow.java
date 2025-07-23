package com.android.systemui.dextouchpad.activity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FloatingWindow extends ViewPositionTracker {
    public final String mTitle;
    public final int mType;
    public final int mWindowFlag;
    public View mWindowView;
    public boolean mWindowViewAdded;

    public FloatingWindow(int i, String str) {
        this.mWindowFlag = 0;
        this.mWindowViewAdded = false;
        this.mType = i;
        this.mTitle = str;
    }

    public final WindowManager.LayoutParams createLayoutParams(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int width = view.getWidth();
        int height = view.getHeight();
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = this.mWindowFlag;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, height, i, i2, this.mType, (i3 == 1073741824 ? 16 : 0) | 520, -2);
        layoutParams.gravity = 8388659;
        layoutParams.setTitle(this.mTitle);
        if (i3 != 0) {
            layoutParams.semAddExtensionFlags(i3);
        }
        return layoutParams;
    }

    @Override // com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public final void onLayoutSetup() {
        try {
            this.mActivity.getWindowManager().addView(this.mWindowView, createLayoutParams(this.mBaseView));
            this.mWindowViewAdded = true;
        } catch (WindowManager.BadTokenException e) {
            Log.e("DexTouchpadFloatingWindow", "onLayoutSetup(), addView failed, ", e);
        }
    }

    @Override // com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public void onStartSetup() {
        this.mWindowView = new View(this.mActivity);
    }

    @Override // com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public void onStartTearDown() {
        if (this.mWindowViewAdded) {
            this.mActivity.getWindowManager().removeView(this.mWindowView);
            this.mWindowViewAdded = false;
        }
    }

    public FloatingWindow(int i, String str, int i2) {
        this.mWindowViewAdded = false;
        this.mType = i;
        this.mTitle = str;
        this.mWindowFlag = i2;
    }

    @Override // com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public void onStopWindow() {
    }
}
