package com.android.systemui.statusbar.phone;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.util.AlarmTimeout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ScrimController scrimController = (ScrimController) obj;
                ScrimController.Callback callback = scrimController.mCallback;
                if (callback != null) {
                    callback.onDisplayBlanked();
                    scrimController.mScreenBlankingCallbackCalled = true;
                }
                scrimController.mBlankingTransitionRunnable = new ScrimController$$ExternalSyntheticLambda0(scrimController, 4);
                int i2 = scrimController.mScreenOn ? scrimController.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController.isFastWakeAndUnlockMode() ? 300 : 32 : 500;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "Fading out scrims with delay: ", "ScrimController");
                scrimController.mHandler.postDelayed(scrimController.mBlankingTransitionRunnable, i2);
                break;
            case 1:
                ((ScrimController) obj).updateScrims();
                break;
            case 2:
                ScrimController scrimController2 = (ScrimController) obj;
                AlarmTimeout alarmTimeout = scrimController2.mTimeTicker;
                DozeParameters dozeParameters = scrimController2.mDozeParameters;
                alarmTimeout.schedule(dozeParameters.mControlScreenOffAnimation ? 2500L : dozeParameters.mAlwaysOnPolicy.wallpaperVisibilityDuration, 1);
                break;
            case 3:
                ScrimController scrimController3 = (ScrimController) obj;
                AlarmTimeout alarmTimeout2 = scrimController3.mTimeTicker;
                DozeParameters dozeParameters2 = scrimController3.mDozeParameters;
                alarmTimeout2.schedule(dozeParameters2.mControlScreenOffAnimation ? 2500L : dozeParameters2.mAlwaysOnPolicy.wallpaperVisibilityDuration, 1);
                break;
            case 4:
                ScrimController scrimController4 = (ScrimController) obj;
                scrimController4.mBlankingTransitionRunnable = null;
                scrimController4.mPendingFrameCallback = null;
                scrimController4.mBlankScreen = false;
                scrimController4.updateScrims();
                break;
            default:
                ((AlarmTimeout) obj).cancel();
                break;
        }
    }
}
