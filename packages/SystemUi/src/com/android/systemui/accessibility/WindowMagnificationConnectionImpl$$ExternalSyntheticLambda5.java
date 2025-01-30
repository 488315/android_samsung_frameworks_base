package com.android.systemui.accessibility;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowMagnificationConnectionImpl$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowMagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ WindowMagnificationConnectionImpl$$ExternalSyntheticLambda5(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = windowMagnificationConnectionImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.f$0;
                MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) windowMagnificationConnectionImpl.mWindowMagnification.mMagnificationSettingsSupplier.get(this.f$1);
                if (magnificationSettingsController != null) {
                    magnificationSettingsController.mContext.unregisterComponentCallbacks(magnificationSettingsController);
                    magnificationSettingsController.mWindowMagnificationSettings.hideSettingPanel(true);
                    break;
                }
                break;
            default:
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl2 = this.f$0;
                MagnificationModeSwitch magnificationModeSwitch = (MagnificationModeSwitch) windowMagnificationConnectionImpl2.mWindowMagnification.mModeSwitchesController.mSwitchSupplier.get(this.f$1);
                if (magnificationModeSwitch != null) {
                    magnificationModeSwitch.removeButton();
                    break;
                }
                break;
        }
    }
}
