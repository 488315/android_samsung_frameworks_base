package com.android.keyguard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.telephony.PinResult;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.PluralMessageFormaterKt;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardSimPinViewController extends KeyguardSecPinBasedInputViewController {
    public CheckSimPin mCheckSimPinThread;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public AlertDialog mRemainingAttemptsDialog;
    public boolean mShowDefaultMessage;
    public ImageView mSimImageView;
    public ProgressDialog mSimUnlockProgressDialog;
    public int mSubId;
    public final TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSimPinViewController$2, reason: invalid class name */
    public final class AnonymousClass2 extends CheckSimPin {
        public AnonymousClass2(String str, int i) {
            super(str, i);
        }

        @Override // com.android.keyguard.KeyguardSimPinViewController.CheckSimPin
        public final void onSimCheckResponse(PinResult pinResult) {
            ((KeyguardSimPinView) ((ViewController) KeyguardSimPinViewController.this).mView).post(new KeyguardSimPinViewController$2$$ExternalSyntheticLambda0(this, pinResult, 0));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class CheckSimPin extends Thread {
        public final String mPin;
        public final int mSubId;

        public CheckSimPin(String str, int i) {
            this.mPin = str;
            this.mSubId = i;
        }

        public abstract void onSimCheckResponse(PinResult pinResult);

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            PinResult supplyIccLockPin = KeyguardSimPinViewController.this.mTelephonyManager.createForSubscriptionId(this.mSubId).supplyIccLockPin(this.mPin);
            supplyIccLockPin.toString();
            ((KeyguardSimPinView) ((ViewController) KeyguardSimPinViewController.this).mView).post(new KeyguardSimPinViewController$2$$ExternalSyntheticLambda0(this, supplyIccLockPin, 1));
        }
    }

    /* renamed from: -$$Nest$mgetSimRemainingAttemptsDialog, reason: not valid java name */
    public static Dialog m841$$Nest$mgetSimRemainingAttemptsDialog(KeyguardSimPinViewController keyguardSimPinViewController, int i) {
        String pinPasswordErrorMessage = keyguardSimPinViewController.getPinPasswordErrorMessage(i);
        AlertDialog alertDialog = keyguardSimPinViewController.mRemainingAttemptsDialog;
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(((KeyguardSimPinView) keyguardSimPinViewController.mView).getContext());
            builder.setMessage(pinPasswordErrorMessage);
            builder.setCancelable(false);
            builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            keyguardSimPinViewController.mRemainingAttemptsDialog = create;
            create.getWindow().setType(2009);
        } else {
            alertDialog.setMessage(pinPasswordErrorMessage);
        }
        return keyguardSimPinViewController.mRemainingAttemptsDialog;
    }

    public KeyguardSimPinViewController(KeyguardSimPinView keyguardSimPinView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardSimPinView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor);
        this.mShowDefaultMessage = true;
        this.mSubId = -1;
        new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPinViewController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                KeyguardSimPinViewController keyguardSimPinViewController = KeyguardSimPinViewController.this;
                if (i == keyguardSimPinViewController.mSubId && i3 == 3) {
                    keyguardSimPinViewController.getKeyguardSecurityCallback().showCurrentSecurityScreen();
                } else if (i3 != 5) {
                    keyguardSimPinViewController.resetState();
                } else {
                    keyguardSimPinViewController.getClass();
                    keyguardSimPinViewController.resetState();
                }
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mSimImageView = (ImageView) ((KeyguardSimPinView) this.mView).findViewById(R.id.keyguard_sim);
    }

    public final String getPinPasswordErrorMessage(int i) {
        String string = i == 0 ? ((KeyguardSimPinView) this.mView).getResources().getString(R.string.kg_password_wrong_pin_code_pukked) : i > 0 ? PluralMessageFormaterKt.icuMessageFormat(((KeyguardSimPinView) this.mView).getResources(), R.string.kg_password_wrong_pin_code, i) : ((KeyguardSimPinView) this.mView).getResources().getString(R.string.kg_password_pin_failed);
        if (KeyguardEsimArea.isEsimLocked(this.mSubId, ((KeyguardSimPinView) this.mView).getContext())) {
            string = ((KeyguardSimPinView) this.mView).getResources().getString(R.string.kg_sim_lock_esim_instructions, string);
        }
        Log.d("KeyguardSimPinView", "getPinPasswordErrorMessage: attemptsRemaining=" + i + " displayMessage=" + string);
        return string;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        super.onPause();
        ProgressDialog progressDialog = this.mSimUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public boolean startDisappearAnimation(Runnable runnable) {
        return false;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void updateMessageAreaVisibility() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            return;
        }
        keyguardSecMessageAreaController.setIsVisible(true);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public void verifyPasswordAndUnlock() {
        String editable = this.mPasswordEntry.getText().toString();
        if (editable.length() < 4 || editable.length() > 8) {
            ((KeyguardSimPinView) this.mView).resetPasswordText(true, true);
            getKeyguardSecurityCallback().userActivity();
            this.mMessageAreaController.setMessage(R.string.kg_invalid_sim_pin_hint);
            return;
        }
        if (this.mSimUnlockProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(((KeyguardSimPinView) this.mView).getContext());
            this.mSimUnlockProgressDialog = progressDialog;
            progressDialog.setMessage(((KeyguardSimPinView) this.mView).getResources().getString(R.string.kg_sim_unlock_progress_dialog_message));
            this.mSimUnlockProgressDialog.setIndeterminate(true);
            this.mSimUnlockProgressDialog.setCancelable(false);
            this.mSimUnlockProgressDialog.getWindow().setType(2009);
        }
        this.mSimUnlockProgressDialog.show();
        if (this.mCheckSimPinThread == null) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(editable, this.mSubId);
            this.mCheckSimPinThread = anonymousClass2;
            anonymousClass2.start();
        }
    }
}
