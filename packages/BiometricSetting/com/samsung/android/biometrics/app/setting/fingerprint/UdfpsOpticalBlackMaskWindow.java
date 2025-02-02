package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;

/* loaded from: classes.dex */
public final class UdfpsOpticalBlackMaskWindow extends SysUiWindow {
    protected BlackMaskView mBlackMaskView;

    public UdfpsOpticalBlackMaskWindow(Context context) {
        super(context);
        BlackMaskView blackMaskView = new BlackMaskView(getContext(), null);
        this.mBlackMaskView = blackMaskView;
        blackMaskView.setBlackMaskMode(false);
        this.mBlackMaskView.setVisibility(8);
        this.mBaseView = this.mBlackMaskView;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        Point displaySize = Utils.getDisplaySize(getContext());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(displaySize.x, displaySize.y, 2415, 16777240, -3);
        layoutParams.flags &= -65537;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 8272;
        if (!Utils.isTpaMode(getContext())) {
            layoutParams.privateFlags |= 1048576;
        }
        layoutParams.setFitInsetsTypes(0);
        layoutParams.semAddExtensionFlags(262144);
        layoutParams.semAddPrivateFlags(536870912);
        layoutParams.semAddExtensionFlags(131072);
        layoutParams.semAddExtensionFlags(8);
        layoutParams.setTitle("FP BlackMaskView");
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_UdfpsOpticalBlackMaskWindow";
    }
}
