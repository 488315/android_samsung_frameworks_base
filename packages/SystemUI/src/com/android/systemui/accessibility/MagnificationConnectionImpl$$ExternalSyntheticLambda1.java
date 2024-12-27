package com.android.systemui.accessibility;

import com.android.systemui.Flags;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MagnificationConnectionImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MagnificationConnectionImpl$$ExternalSyntheticLambda1(MagnificationConnectionImpl magnificationConnectionImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = magnificationConnectionImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MagnificationConnectionImpl magnificationConnectionImpl = this.f$0;
                MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) magnificationConnectionImpl.mMagnification.mMagnificationSettingsSupplier.get(this.f$1);
                if (magnificationSettingsController != null && magnificationSettingsController.mWindowMagnificationSettings != null) {
                    magnificationSettingsController.mContext.unregisterComponentCallbacks(magnificationSettingsController);
                    magnificationSettingsController.mWindowMagnificationSettings.hideSettingPanel(true);
                    break;
                }
                break;
            default:
                MagnificationConnectionImpl magnificationConnectionImpl2 = this.f$0;
                int i = this.f$1;
                Magnification magnification = magnificationConnectionImpl2.mMagnification;
                magnification.getClass();
                Flags.FEATURE_FLAGS.getClass();
                magnification.mHandler.removeMessages(1);
                MagnificationModeSwitch magnificationModeSwitch = (MagnificationModeSwitch) magnification.mModeSwitchesController.mSwitchSupplier.get(i);
                if (magnificationModeSwitch != null) {
                    magnificationModeSwitch.removeButton();
                    break;
                }
                break;
        }
    }
}
