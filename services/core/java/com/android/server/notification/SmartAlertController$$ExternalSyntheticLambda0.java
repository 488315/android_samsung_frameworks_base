package com.android.server.notification;

import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Slog;

import com.samsung.android.gesture.SemMotionRecognitionManager;

public final /* synthetic */ class SmartAlertController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SmartAlertController$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SemMotionRecognitionManager semMotionRecognitionManager;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SmartAlertController smartAlertController = (SmartAlertController) obj;
                if (smartAlertController.mMotionRegistered
                        && (semMotionRecognitionManager =
                                        smartAlertController.mSmartAlertMotionManager)
                                != null) {
                    semMotionRecognitionManager.unregisterListener(
                            smartAlertController.mSmartAlertMotionListener);
                    smartAlertController.mMotionRegistered = false;
                    Slog.d("SmartAlertController", "SmartAlert - unregisterListener");
                    break;
                }
                break;
            default:
                SmartAlertController.AnonymousClass1 anonymousClass1 =
                        (SmartAlertController.AnonymousClass1) obj;
                if (!anonymousClass1.this$0.mInCall) {
                    Slog.d("SmartAlertController", "SmartAlert - vibrate");
                    SmartAlertController smartAlertController2 = anonymousClass1.this$0;
                    smartAlertController2.mVibrator.vibrate(
                            1000,
                            "android",
                            VibrationEffect.createWaveform(
                                    smartAlertController2.mPickUpVibratePattern, -1),
                            "SmartAlertController",
                            new VibrationAttributes.Builder().setUsage(49).setFlags(1).build());
                    break;
                } else {
                    Slog.d(
                            "SmartAlertController",
                            "SmartAlert - inCall, vibration will be returned");
                    break;
                }
        }
    }
}
