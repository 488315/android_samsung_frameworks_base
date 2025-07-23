package com.android.systemui.statusbar.phone;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.ScrimController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrimController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda3(ScrimController scrimController, int i) {
        this.$r8$classId = i;
        this.f$0 = scrimController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ScrimController scrimController = this.f$0;
        switch (i) {
            case 0:
                ScrimController.Callback callback = scrimController.mCallback;
                if (callback != null) {
                    callback.onDisplayBlanked();
                    scrimController.mScreenBlankingCallbackCalled = true;
                }
                scrimController.mBlankingTransitionRunnable = new ScrimController$$ExternalSyntheticLambda3(scrimController, 2);
                int i2 = scrimController.mScreenOn ? scrimController.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController.isFastWakeAndUnlockMode() ? 300 : 32 : 500;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "Fading out scrims with delay: ", "ScrimController");
                scrimController.mHandler.postDelayed(scrimController.mBlankingTransitionRunnable, i2);
                break;
            case 1:
                boolean z = ScrimController.DEBUG;
                scrimController.updateScrims();
                break;
            default:
                scrimController.mBlankingTransitionRunnable = null;
                scrimController.mPendingFrameCallback = null;
                scrimController.mBlankScreen = false;
                scrimController.updateScrims();
                break;
        }
    }
}
