package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.SysUiWindow;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class UdfpsTouchBlockWidow extends SysUiWindow {
    public final FingerprintSensorInfo mSensorInfo;
    public final int mX;
    public final int mY;

    public UdfpsTouchBlockWidow(
            Context context, FingerprintSensorInfo fingerprintSensorInfo, int i, int i2) {
        super(context);
        this.mSensorInfo = fingerprintSensorInfo;
        this.mX = i;
        this.mY = i2;
        try {
            View view = new View(context);
            this.mBaseView = view;
            view.setOnTouchListener(new UdfpsTouchBlockWidow$$ExternalSyntheticLambda0());
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("UdfpsTouchBlockWidow: "), "BSS_SysUiWindow.TB");
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(2009, 16777512, -3);
        layoutParams.setTitle("FP TB");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags = 536870928;
        int i = this.mSensorInfo.mSensorImageSize;
        layoutParams.height = i;
        layoutParams.width = i;
        layoutParams.x = this.mX;
        layoutParams.y = this.mY;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.TB";
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        this.mBaseView.setOnTouchListener(null);
        super.removeView();
    }
}
