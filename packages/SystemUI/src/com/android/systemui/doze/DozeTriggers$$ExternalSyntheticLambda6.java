package com.android.systemui.doze;

import android.util.Log;
import com.android.systemui.Flags;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsControllerOverlay;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;

public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ DozeTriggers f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float[] f$3;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda6(DozeTriggers dozeTriggers, float f, float f2, float[] fArr) {
        this.f$0 = dozeTriggers;
        this.f$1 = f;
        this.f$2 = f2;
        this.f$3 = fArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DozeTriggers dozeTriggers = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        float[] fArr = this.f$3;
        dozeTriggers.getClass();
        final int i = (int) f;
        final int i2 = (int) f2;
        final float f3 = fArr[3];
        final float f4 = fArr[4];
        final UdfpsController udfpsController = dozeTriggers.mAuthController.mUdfpsController;
        if (udfpsController == null || udfpsController.mIsAodInterruptActive) {
            return;
        }
        if (udfpsController.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            final long j = udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : -1L;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsController udfpsController2 = UdfpsController.this;
                    long j2 = j;
                    int i3 = i;
                    int i4 = i2;
                    float f5 = f4;
                    float f6 = f3;
                    udfpsController2.mIsAodInterruptActive = true;
                    udfpsController2.mCancelAodFingerUpAction = udfpsController2.mFgExecutor.executeDelayed(new UdfpsController$$ExternalSyntheticLambda2(udfpsController2, 0), 1000L);
                    udfpsController2.onFingerDown(j2, -1, i3, i4, f5, f6, 0.0f, 0L, 0L, true);
                }
            };
            udfpsController.mAodInterruptRunnable = runnable;
            if (udfpsController.mScreenOn) {
                runnable.run();
                udfpsController.mAodInterruptRunnable = null;
                return;
            }
            return;
        }
        if (udfpsController.mFalsingManager.isFalseLongTap(1)) {
            return;
        }
        udfpsController.mKeyguardViewManager.showPrimaryBouncer(true);
        UdfpsControllerOverlay udfpsControllerOverlay2 = udfpsController.mOverlay;
        if (udfpsControllerOverlay2 != null) {
            Flags.deviceEntryUdfpsRefactor();
            if (udfpsControllerOverlay2.overlayTouchView != null) {
                UdfpsControllerOverlay udfpsControllerOverlay3 = udfpsController.mOverlay;
                udfpsControllerOverlay3.getClass();
                Flags.deviceEntryUdfpsRefactor();
                UdfpsTouchOverlay udfpsTouchOverlay = udfpsControllerOverlay3.overlayTouchView;
                udfpsController.mVibrator.getClass();
                udfpsTouchOverlay.performHapticFeedback(0);
                return;
            }
        }
        Log.e("UdfpsController", "No haptics played. Could not obtain overlay view to performvibration. Either the controller overlay is null or has no view");
    }
}
