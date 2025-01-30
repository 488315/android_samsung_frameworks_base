package com.android.systemui.statusbar.phone;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.util.AlarmTimeout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScrimController scrimController = (ScrimController) this.f$0;
                AlarmTimeout alarmTimeout = scrimController.mTimeTicker;
                DozeParameters dozeParameters = scrimController.mDozeParameters;
                alarmTimeout.schedule(1, dozeParameters.mControlScreenOffAnimation ? 2500L : dozeParameters.mAlwaysOnPolicy.wallpaperVisibilityDuration);
                break;
            case 1:
                ScrimController scrimController2 = (ScrimController) this.f$0;
                ScrimController.Callback callback = scrimController2.mCallback;
                if (callback != null) {
                    callback.onDisplayBlanked();
                    scrimController2.mScreenBlankingCallbackCalled = true;
                }
                scrimController2.mBlankingTransitionRunnable = new ScrimController$$ExternalSyntheticLambda0(scrimController2, 3);
                int i = scrimController2.mScreenOn ? scrimController2.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController.isFastWakeAndUnlockMode() ? 300 : 32 : 500;
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("Fading out scrims with delay: ", i, "ScrimController");
                scrimController2.mHandler.postDelayed(scrimController2.mBlankingTransitionRunnable, i);
                break;
            case 2:
                ScrimController scrimController3 = (ScrimController) this.f$0;
                AlarmTimeout alarmTimeout2 = scrimController3.mTimeTicker;
                DozeParameters dozeParameters2 = scrimController3.mDozeParameters;
                alarmTimeout2.schedule(1, dozeParameters2.mControlScreenOffAnimation ? 2500L : dozeParameters2.mAlwaysOnPolicy.wallpaperVisibilityDuration);
                break;
            case 3:
                ScrimController scrimController4 = (ScrimController) this.f$0;
                scrimController4.mBlankingTransitionRunnable = null;
                scrimController4.mPendingFrameCallback = null;
                scrimController4.mBlankScreen = false;
                scrimController4.updateScrims();
                break;
            case 4:
                ((ScrimController) this.f$0).updateScrims();
                break;
            default:
                ((AlarmTimeout) this.f$0).cancel();
                break;
        }
    }
}
