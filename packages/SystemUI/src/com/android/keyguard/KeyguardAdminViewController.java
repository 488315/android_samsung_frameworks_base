package com.android.keyguard;

import android.util.Log;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.widget.SystemUITextView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardAdminViewController extends KeyguardInputViewController {
    public KeyguardAdminViewController(KeyguardAdminView keyguardAdminView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardAdminView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags, selectedUserInteractor);
        SystemUITextView systemUITextView = (SystemUITextView) ((KeyguardAdminView) this.mView).findViewById(R.id.keyguard_admin_lock_help_text);
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor == null || !edmMonitor.mLicenseExpired) {
            return;
        }
        systemUITextView.setText(R.string.kg_knox_license_expired);
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
        Log.d("KeyguardAdminView", "onPause()");
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        Log.d("KeyguardAdminView", "onResume()");
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void reset$1() {
        Log.d("KeyguardAdminView", "reset()");
    }
}
