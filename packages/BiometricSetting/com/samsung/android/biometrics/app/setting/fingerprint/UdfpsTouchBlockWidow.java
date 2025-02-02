package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.SysUiWindow;

/* loaded from: classes.dex */
public final class UdfpsTouchBlockWidow extends SysUiWindow {
    private final UdfpsInfo mSensorInfo;

    /* renamed from: mX */
    private final int f29mX;

    /* renamed from: mY */
    private final int f30mY;

    public UdfpsTouchBlockWidow(Context context, UdfpsInfo udfpsInfo, int i, int i2) {
        super(context);
        this.mSensorInfo = udfpsInfo;
        this.f29mX = i;
        this.f30mY = i2;
        try {
            View view = new View(context);
            this.mBaseView = view;
            view.setOnTouchListener(new UdfpsTouchBlockWidow$$ExternalSyntheticLambda0());
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("UdfpsTouchBlockWidow: "), "BSS_SysUiWindow.TB");
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2009, 16777512, -3);
        layoutParams.setTitle("FP TB");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags = 536870928;
        int imageSize = this.mSensorInfo.getImageSize();
        layoutParams.height = imageSize;
        layoutParams.width = imageSize;
        layoutParams.x = this.f29mX;
        layoutParams.y = this.f30mY;
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
