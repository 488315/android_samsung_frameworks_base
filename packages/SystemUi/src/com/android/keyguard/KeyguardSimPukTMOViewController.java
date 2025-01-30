package com.android.keyguard;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.View;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.widget.SystemUIButton;
import com.android.systemui.widget.SystemUITextView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSimPukTMOViewController extends KeyguardInputViewController {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    public KeyguardSimPukTMOViewController(KeyguardSimPukTMOView keyguardSimPukTMOView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags) {
        super(keyguardSimPukTMOView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags);
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPukTMOViewController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                KeyguardSimPukTMOViewController keyguardSimPukTMOViewController = KeyguardSimPukTMOViewController.this;
                KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSimPukTMOViewController.getKeyguardSecurityCallback();
                if (i3 != 1) {
                    if (i3 == 4) {
                        if (keyguardSimPukTMOViewController.mPaused || keyguardSecurityCallback2 == null) {
                            return;
                        }
                        keyguardSecurityCallback2.dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardSimPukTMOViewController.mSecurityMode, true);
                        return;
                    }
                    if (i3 != 5) {
                        return;
                    }
                }
                if (keyguardSimPukTMOViewController.mPaused || keyguardSecurityCallback2 == null || keyguardSimPukTMOViewController.mKeyguardUpdateMonitor.isSimState(3)) {
                    return;
                }
                keyguardSecurityCallback2.dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardSimPukTMOViewController.mSecurityMode, true);
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        final KeyguardSecurityCallback keyguardSecurityCallback2 = getKeyguardSecurityCallback();
        SystemUITextView systemUITextView = (SystemUITextView) ((KeyguardSimPukTMOView) this.mView).findViewById(R.id.keyguard_unlock_view_help_text);
        boolean z = getResources().getBoolean(android.R.bool.config_supportsSystemDecorsOnSecondaryDisplays);
        if (systemUITextView != null) {
            String str = getContext().getString(R.string.kg_sim_puk_tmo_help_pin_blocked) + "\n\n" + getContext().getString(R.string.kg_sim_puk_tmo_help_contact_service_provider);
            if (z) {
                StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "\n\n");
                m2m.append(getContext().getString(android.R.string.indeterminate_progress_08));
                str = m2m.toString();
            }
            systemUITextView.setText(str);
        }
        EmergencyButton emergencyButton = (EmergencyButton) ((KeyguardSimPukTMOView) this.mView).findViewById(R.id.emergency_call_button);
        if (emergencyButton != null) {
            if (z) {
                emergencyButton.setText(R.string.kg_lockscreen_emergency_call_button_text);
            } else {
                emergencyButton.setText(R.string.kg_sim_puk_tmo_enter_unlock_code);
            }
            emergencyButtonController.setEmergencyView(emergencyButton);
        }
        SystemUIButton systemUIButton = (SystemUIButton) ((KeyguardSimPukTMOView) this.mView).findViewById(R.id.forgot_password_button);
        if (!z) {
            if (systemUIButton != null) {
                systemUIButton.setVisibility(8);
            }
        } else if (systemUIButton != null) {
            systemUIButton.setVisibility(0);
            systemUIButton.setText(R.string.kg_sim_puk_tmo_sos_call);
            systemUIButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardSimPukTMOViewController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardSimPukTMOViewController keyguardSimPukTMOViewController = KeyguardSimPukTMOViewController.this;
                    KeyguardSecurityCallback keyguardSecurityCallback3 = keyguardSecurityCallback2;
                    keyguardSimPukTMOViewController.getClass();
                    if (keyguardSecurityCallback3 == null) {
                        return;
                    }
                    keyguardSecurityCallback3.userActivity();
                    Intent intent = new Intent("android.intent.action.CALL_EMERGENCY");
                    intent.setData(Uri.fromParts("tel", DATA.DM_FIELD_INDEX.EPDG_T_EPDG_LTE, null));
                    intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    keyguardSimPukTMOViewController.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
    }
}
