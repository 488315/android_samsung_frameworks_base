package com.android.systemui.accessibility;

import android.os.RemoteException;
import android.util.Log;
import android.view.accessibility.IMagnificationConnectionCallback;
import com.android.systemui.accessibility.MagnificationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationImpl$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MagnificationImpl.AnonymousClass4 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ MagnificationImpl$4$$ExternalSyntheticLambda0(MagnificationImpl.AnonymousClass4 anonymousClass4, int i, int i2, int i3) {
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
                MagnificationImpl.AnonymousClass4 anonymousClass4 = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                MagnificationImpl magnificationImpl = MagnificationImpl.this;
                if (((WindowMagnificationController) magnificationImpl.mWindowMagnificationControllerSupplier.get(i)).isActivated() ^ (i2 == 2)) {
                    MagnificationSettingsController magnificationSettingsController = (MagnificationSettingsController) magnificationImpl.mMagnificationSettingsSupplier.get(i);
                    if (magnificationSettingsController != null && magnificationSettingsController.mWindowMagnificationSettings != null) {
                        magnificationSettingsController.mContext.unregisterComponentCallbacks(magnificationSettingsController);
                        magnificationSettingsController.mWindowMagnificationSettings.hideSettingPanel(true);
                    }
                    MagnificationConnectionImpl magnificationConnectionImpl = magnificationImpl.mMagnificationConnectionImpl;
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
                MagnificationImpl.AnonymousClass4 anonymousClass42 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) MagnificationImpl.this.mWindowMagnificationControllerSupplier.get(i3);
                if (windowMagnificationController != null && windowMagnificationController.mMagnificationSizeScaleOptions.contains(i4)) {
                    windowMagnificationController.mSettingsButtonIndex = i4;
                    int magnificationWindowSizeFromIndex = windowMagnificationController.getMagnificationWindowSizeFromIndex(i4);
                    windowMagnificationController.setWindowSizeAndCenter(magnificationWindowSizeFromIndex, Float.NaN, Float.NaN, magnificationWindowSizeFromIndex);
                    windowMagnificationController.mPreviousMagnificationSize = i4;
                    break;
                }
                break;
        }
    }
}
