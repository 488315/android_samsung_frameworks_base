package com.android.systemui.accessibility;

import com.android.systemui.accessibility.Magnification;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class Magnification$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ Magnification$$ExternalSyntheticLambda2(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WindowMagnificationSettings windowMagnificationSettings;
        switch (this.$r8$classId) {
            case 0:
                Magnification magnification = (Magnification) this.f$0;
                int i = this.f$1;
                Magnification.AnonymousClass4 anonymousClass4 = (Magnification.AnonymousClass4) magnification.mMagnificationSettingsControllerCallback;
                Magnification.this.mHandler.post(new Magnification$4$$ExternalSyntheticLambda2(anonymousClass4, i, 2, 0));
                break;
            default:
                MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) Magnification.this.mMagnificationSettingsSupplier.get(this.f$1);
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
        }
    }
}
