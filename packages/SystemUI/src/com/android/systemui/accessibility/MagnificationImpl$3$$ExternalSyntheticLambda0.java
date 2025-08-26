package com.android.systemui.accessibility;

import com.android.systemui.accessibility.MagnificationImpl;

/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationImpl$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ MagnificationImpl.AnonymousClass3 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MagnificationImpl$3$$ExternalSyntheticLambda0(MagnificationImpl.AnonymousClass3 anonymousClass3, int i) {
        this.f$0 = anonymousClass3;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WindowMagnificationSettings windowMagnificationSettings;
        switch (this.$r8$classId) {
            case 0:
                MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) MagnificationImpl.this.mMagnificationSettingsSupplier.get(this.f$1);
                if (magnificationSettingsController != null && (windowMagnificationSettings = magnificationSettingsController.mWindowMagnificationSettings) != null) {
                    if (!windowMagnificationSettings.mIsVisible) {
                        magnificationSettingsController.onConfigurationChanged(magnificationSettingsController.mContext.getResources().getConfiguration());
                        magnificationSettingsController.mContext.registerComponentCallbacks(magnificationSettingsController);
                    }
                    WindowMagnificationSettings windowMagnificationSettings2 = magnificationSettingsController.mWindowMagnificationSettings;
                    if (!windowMagnificationSettings2.mIsVisible) {
                        windowMagnificationSettings2.showSettingPanel(true);
                        break;
                    } else {
                        windowMagnificationSettings2.hideSettingPanel(true);
                        break;
                    }
                }
                break;
            default:
                MagnificationSettingsController magnificationSettingsController2 = (MagnificationSettingsController) MagnificationImpl.this.mMagnificationSettingsSupplier.get(this.f$1);
                if (magnificationSettingsController2 != null) {
                    magnificationSettingsController2.mWindowMagnificationSettings.getClass();
                    break;
                }
                break;
        }
    }

    public /* synthetic */ MagnificationImpl$3$$ExternalSyntheticLambda0(MagnificationImpl.AnonymousClass3 anonymousClass3, int i, int i2) {
        this.f$0 = anonymousClass3;
        this.f$1 = i;
    }
}
