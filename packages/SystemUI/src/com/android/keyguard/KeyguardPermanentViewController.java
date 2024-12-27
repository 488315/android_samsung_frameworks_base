package com.android.keyguard;

import android.util.Log;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardPermanentViewController extends KeyguardInputViewController {
    public KeyguardPermanentViewController(KeyguardPermanentView keyguardPermanentView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardPermanentView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags, selectedUserInteractor);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        Log.d("KeyguardPermanentView", "onPause()");
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        Log.d("KeyguardPermanentView", "onResume()");
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void reset$1() {
        Log.d("KeyguardPermanentView", "reset()");
    }
}
