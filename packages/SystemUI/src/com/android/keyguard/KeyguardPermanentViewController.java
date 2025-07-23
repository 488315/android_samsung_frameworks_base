package com.android.keyguard;

import android.util.Log;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardPermanentViewController extends KeyguardInputViewController {
    public KeyguardPermanentViewController(KeyguardPermanentView keyguardPermanentView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, BouncerHapticPlayer bouncerHapticPlayer) {
        super(keyguardPermanentView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags, selectedUserInteractor, bouncerHapticPlayer);
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
