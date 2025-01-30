package com.android.systemui.accessibility;

import com.android.systemui.accessibility.WindowMagnification;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowMagnification$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ WindowMagnification$$ExternalSyntheticLambda2(int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        Object[] objArr = 0;
        switch (this.$r8$classId) {
            case 0:
                WindowMagnification windowMagnification = (WindowMagnification) this.f$0;
                int i = this.f$1;
                WindowMagnification.C10003 c10003 = (WindowMagnification.C10003) windowMagnification.mMagnificationSettingsControllerCallback;
                WindowMagnification.this.mHandler.post(new WindowMagnification$3$$ExternalSyntheticLambda0(c10003, i, 2, objArr == true ? 1 : 0));
                break;
            default:
                MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) WindowMagnification.this.mMagnificationSettingsSupplier.get(this.f$1);
                if (magnificationSettingsController != null) {
                    if (!magnificationSettingsController.mWindowMagnificationSettings.mIsVisible) {
                        magnificationSettingsController.onConfigurationChanged(magnificationSettingsController.mContext.getResources().getConfiguration());
                        magnificationSettingsController.mContext.registerComponentCallbacks(magnificationSettingsController);
                    }
                    WindowMagnificationSettings windowMagnificationSettings = magnificationSettingsController.mWindowMagnificationSettings;
                    windowMagnificationSettings.mAllowDiagonalScrollingSwitch.setChecked(windowMagnificationSettings.mSecureSettings.getIntForUser(1, -2, "accessibility_allow_diagonal_scrolling") == 1);
                    windowMagnificationSettings.showSettingPanel(true);
                    break;
                }
                break;
        }
    }
}
