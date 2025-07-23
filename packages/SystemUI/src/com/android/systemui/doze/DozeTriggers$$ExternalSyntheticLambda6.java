package com.android.systemui.doze;

import android.R;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda1;
import com.android.systemui.biometrics.UdfpsControllerOverlay;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        UdfpsTouchOverlay udfpsTouchOverlay;
        DozeTriggers dozeTriggers = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        float[] fArr = this.f$3;
        boolean z = DozeTriggers.sWakeDisplaySensorState;
        int i = (int) f;
        int i2 = (int) f2;
        float f3 = fArr[3];
        float f4 = fArr[4];
        UdfpsController udfpsController = dozeTriggers.mAuthController.mUdfpsController;
        if (udfpsController == null || udfpsController.mIsAodInterruptActive) {
            return;
        }
        if (udfpsController.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
            udfpsController.mAodInterruptRunnable = new UdfpsController$$ExternalSyntheticLambda1(udfpsController, udfpsControllerOverlay != null ? udfpsControllerOverlay.requestId : -1L, i, i2, f4, f3);
            if ((!udfpsController.mContext.getResources().getBoolean(R.bool.config_supportSpeakerNearUltrasound) || Settings.Secure.getIntForUser(udfpsController.mContext.getContentResolver(), "screen_off_udfps_enabled", 0, udfpsController.mContext.getUserId()) == 0) && !udfpsController.mScreenOn) {
                return;
            }
            udfpsController.mAodInterruptRunnable.run();
            udfpsController.mAodInterruptRunnable = null;
            return;
        }
        if (udfpsController.mFalsingManager.isFalseLongTap(1)) {
            return;
        }
        udfpsController.mKeyguardViewManager.showPrimaryBouncer("UdfpsController#onAodInterrupt", true);
        UdfpsControllerOverlay udfpsControllerOverlay2 = udfpsController.mOverlay;
        if (udfpsControllerOverlay2 == null || (udfpsTouchOverlay = udfpsControllerOverlay2.overlayTouchView) == null) {
            Log.e("UdfpsController", "No haptics played. Could not obtain overlay view to performvibration. Either the controller overlay is null or has no view");
        } else {
            udfpsController.mVibrator.getClass();
            udfpsTouchOverlay.performHapticFeedback(0);
        }
    }
}
