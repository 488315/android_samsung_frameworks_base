package com.android.systemui.statusbar.phone;

import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import java.util.function.Consumer;

public final /* synthetic */ class BiometricUnlockController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BiometricUnlockController f$0;

    public /* synthetic */ BiometricUnlockController$$ExternalSyntheticLambda0(BiometricUnlockController biometricUnlockController, int i) {
        this.$r8$classId = i;
        this.f$0 = biometricUnlockController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        BiometricUnlockController biometricUnlockController = this.f$0;
        switch (i) {
            case 0:
                biometricUnlockController.consumeTransitionStepOnStartedKeyguardState((TransitionStep) obj);
                break;
            case 1:
                BiometricUnlockController.UI_EVENT_LOGGER.log((BiometricUnlockController.BiometricUiEvent) obj, biometricUnlockController.mSessionTracker.getSessionId(1));
                break;
            case 2:
                BiometricUnlockController.UI_EVENT_LOGGER.log((BiometricUnlockController.BiometricUiEvent) obj, biometricUnlockController.mSessionTracker.getSessionId(1));
                break;
            case 3:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                SecLockIconViewController secLockIconViewController = (SecLockIconViewController) biometricUnlockController.mSecLockIconViewControllerLazy.get();
                secLockIconViewController.mIsBiometricToastViewAnimating = booleanValue;
                secLockIconViewController.updateVisibility$1();
                break;
            default:
                BiometricUnlockController.UI_EVENT_LOGGER.log((BiometricUnlockController.BiometricUiEvent) obj, biometricUnlockController.mSessionTracker.getSessionId(1));
                break;
        }
    }
}
