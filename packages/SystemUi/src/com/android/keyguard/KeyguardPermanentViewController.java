package com.android.keyguard;

import android.util.Log;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.flags.FeatureFlags;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPermanentViewController extends KeyguardInputViewController {
    public KeyguardPermanentViewController(KeyguardPermanentView keyguardPermanentView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags) {
        super(keyguardPermanentView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags);
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
    public final void reset() {
        Log.d("KeyguardPermanentView", "reset()");
    }
}
