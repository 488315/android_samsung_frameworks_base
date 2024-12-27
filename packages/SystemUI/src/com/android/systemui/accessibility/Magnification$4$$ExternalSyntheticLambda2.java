package com.android.systemui.accessibility;

import android.os.RemoteException;
import android.util.Log;
import android.view.accessibility.IMagnificationConnectionCallback;
import com.android.systemui.accessibility.Magnification;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class Magnification$4$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Magnification.AnonymousClass4 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ Magnification$4$$ExternalSyntheticLambda2(Magnification.AnonymousClass4 anonymousClass4, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = anonymousClass4;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IMagnificationConnectionCallback iMagnificationConnectionCallback;
        switch (this.$r8$classId) {
            case 0:
                Magnification.AnonymousClass4 anonymousClass4 = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                Magnification magnification = Magnification.this;
                if (((WindowMagnificationController) magnification.mWindowMagnificationControllerSupplier.get(i)).isActivated() ^ (i2 == 2)) {
                    MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) magnification.mMagnificationSettingsSupplier.get(i);
                    if (magnificationSettingsController != null && magnificationSettingsController.mWindowMagnificationSettings != null) {
                        magnificationSettingsController.mContext.unregisterComponentCallbacks(magnificationSettingsController);
                        magnificationSettingsController.mWindowMagnificationSettings.hideSettingPanel(true);
                    }
                    MagnificationConnectionImpl magnificationConnectionImpl = magnification.mMagnificationConnectionImpl;
                    if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                        try {
                            iMagnificationConnectionCallback.onChangeMagnificationMode(i, i2);
                            break;
                        } catch (RemoteException e) {
                            Log.e("WindowMagnificationConnectionImpl", "Failed to inform changing magnification mode", e);
                            return;
                        }
                    }
                }
                break;
            default:
                Magnification.AnonymousClass4 anonymousClass42 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) Magnification.this.mWindowMagnificationControllerSupplier.get(i3);
                if (windowMagnificationController != null && windowMagnificationController.mMagnificationSizeScaleOptions.contains(i4)) {
                    int magnificationWindowSizeFromIndex = windowMagnificationController.getMagnificationWindowSizeFromIndex(i4);
                    windowMagnificationController.setWindowSizeAndCenter(magnificationWindowSizeFromIndex, Float.NaN, Float.NaN, magnificationWindowSizeFromIndex);
                    windowMagnificationController.mPreviousMagnificationSize = i4;
                    break;
                }
                break;
        }
    }
}
