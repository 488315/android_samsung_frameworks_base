package com.android.keyguard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.telephony.PinResult;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.vibrate.VibrationUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardSimPukViewController extends KeyguardSecPinBasedInputViewController {
    public CheckSimPuk mCheckSimPukThread;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public String mPinText;
    public String mPukText;
    public int mRemainingAttempts;
    public AlertDialog mRemainingAttemptsDialog;
    public boolean mShowDefaultMessage;
    public ImageView mSimImageView;
    public ProgressDialog mSimUnlockProgressDialog;
    public StateMachine mStateMachine;
    public int mSubId;
    public final TelephonyManager mTelephonyManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSimPukViewController$3 */
    public final class C07793 extends CheckSimPuk {
        public C07793(String str, String str2, int i) {
            super(str, str2, i);
        }

        @Override // com.android.keyguard.KeyguardSimPukViewController.CheckSimPuk
        public final void onSimLockChangedResponse(PinResult pinResult) {
            ((KeyguardSimPukView) KeyguardSimPukViewController.this.mView).post(new KeyguardSimPukViewController$3$$ExternalSyntheticLambda0(this, pinResult, 0));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CheckSimPuk extends Thread {
        public final String mPin;
        public final String mPuk;
        public final int mSubId;

        public CheckSimPuk(String str, String str2, int i) {
            this.mPuk = str;
            this.mPin = str2;
            this.mSubId = i;
        }

        public abstract void onSimLockChangedResponse(PinResult pinResult);

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            PinResult supplyIccLockPuk = KeyguardSimPukViewController.this.mTelephonyManager.createForSubscriptionId(this.mSubId).supplyIccLockPuk(this.mPuk, this.mPin);
            supplyIccLockPuk.toString();
            ((KeyguardSimPukView) KeyguardSimPukViewController.this.mView).post(new KeyguardSimPukViewController$3$$ExternalSyntheticLambda0(this, supplyIccLockPuk, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class StateMachine {
        public int mState = 0;

        public StateMachine() {
        }

        public void next() {
            int i;
            int i2 = this.mState;
            KeyguardSimPukViewController keyguardSimPukViewController = KeyguardSimPukViewController.this;
            if (i2 == 0) {
                if (keyguardSimPukViewController.checkPuk()) {
                    this.mState = 1;
                    i = R.string.kg_puk_enter_pin_hint;
                } else {
                    i = R.string.kg_invalid_sim_puk_hint;
                }
            } else if (i2 == 1) {
                if (keyguardSimPukViewController.checkPin()) {
                    this.mState = 2;
                    i = R.string.kg_enter_confirm_pin_hint;
                } else {
                    i = R.string.kg_invalid_sim_pin_hint;
                }
            } else if (i2 != 2) {
                i = 0;
            } else if (keyguardSimPukViewController.confirmPin()) {
                this.mState = 3;
                keyguardSimPukViewController.updateSim();
                i = R.string.keyguard_sim_unlock_progress_dialog_message;
            } else {
                this.mState = 1;
                i = R.string.kg_invalid_confirm_pin_hint;
            }
            ((KeyguardSimPukView) keyguardSimPukViewController.mView).resetPasswordText(true, true);
            if (i != 0) {
                keyguardSimPukViewController.mMessageAreaController.setMessage(i);
            }
        }

        public void reset() {
            KeyguardSimPukViewController keyguardSimPukViewController = KeyguardSimPukViewController.this;
            keyguardSimPukViewController.mPinText = "";
            keyguardSimPukViewController.mPukText = "";
            this.mState = 0;
            int nextSubIdForState = keyguardSimPukViewController.mKeyguardUpdateMonitor.getNextSubIdForState(3);
            if (nextSubIdForState != keyguardSimPukViewController.mSubId && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
                keyguardSimPukViewController.mSubId = nextSubIdForState;
                keyguardSimPukViewController.mShowDefaultMessage = true;
                keyguardSimPukViewController.mRemainingAttempts = -1;
            }
            if (keyguardSimPukViewController.mShowDefaultMessage) {
                keyguardSimPukViewController.showDefaultMessage();
            }
            KeyguardSimPukView keyguardSimPukView = (KeyguardSimPukView) keyguardSimPukViewController.mView;
            boolean isEsimLocked = KeyguardEsimArea.isEsimLocked(keyguardSimPukViewController.mSubId, keyguardSimPukView.getContext());
            int i = keyguardSimPukViewController.mSubId;
            KeyguardEsimArea keyguardEsimArea = keyguardSimPukView.disableESimButton;
            if (keyguardEsimArea != null) {
                keyguardEsimArea.mSubscriptionId = i;
            }
            if (keyguardEsimArea != null) {
                keyguardEsimArea.setVisibility(isEsimLocked ? 0 : 8);
            }
            ImageView imageView = keyguardSimPukView.simImageView;
            if (imageView != null) {
                imageView.setVisibility(isEsimLocked ? 8 : 0);
            }
            keyguardSimPukViewController.mPasswordEntry.requestFocus();
        }
    }

    public KeyguardSimPukViewController(KeyguardSimPukView keyguardSimPukView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController) {
        super(keyguardSimPukView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
        this.mStateMachine = new StateMachine();
        this.mSubId = -1;
        new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPukViewController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                KeyguardSimPukViewController keyguardSimPukViewController = KeyguardSimPukViewController.this;
                if (i3 != 5) {
                    keyguardSimPukViewController.resetState();
                    return;
                }
                keyguardSimPukViewController.mRemainingAttempts = -1;
                keyguardSimPukViewController.mShowDefaultMessage = true;
                keyguardSimPukViewController.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecurityModel.SecurityMode.SimPuk, true);
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mSimImageView = (ImageView) ((KeyguardSimPukView) this.mView).findViewById(R.id.keyguard_sim);
    }

    public boolean checkPin() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        int length = passwordTextView.getText().length();
        if (length < 4 || length > 8) {
            return false;
        }
        this.mPinText = passwordTextView.getText().toString();
        return true;
    }

    public boolean checkPuk() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView.getText().length() != 8) {
            return false;
        }
        this.mPukText = passwordTextView.getText().toString();
        return true;
    }

    public boolean confirmPin() {
        return this.mPinText.equals(this.mPasswordEntry.getText());
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        ProgressDialog progressDialog = this.mSimUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        super.onResume(i);
        if (this.mShowDefaultMessage) {
            showDefaultMessage();
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        super.resetState();
        this.mStateMachine.reset();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final boolean shouldLockout(long j) {
        return false;
    }

    public void showDefaultMessage() {
        String str;
        int i = this.mRemainingAttempts;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (i >= 0) {
            View view = this.mView;
            keyguardSecMessageAreaController.setMessage(((KeyguardSimPukView) view).getPukPasswordErrorMessage(i, true, KeyguardEsimArea.isEsimLocked(this.mSubId, ((KeyguardSimPukView) view).getContext())), false);
            return;
        }
        boolean isEsimLocked = KeyguardEsimArea.isEsimLocked(this.mSubId, ((KeyguardSimPukView) this.mView).getContext());
        TelephonyManager telephonyManager = this.mTelephonyManager;
        int activeModemCount = telephonyManager != null ? telephonyManager.getActiveModemCount() : 1;
        Resources resources = ((KeyguardSimPukView) this.mView).getResources();
        TypedArray obtainStyledAttributes = ((KeyguardSimPukView) this.mView).getContext().obtainStyledAttributes(new int[]{android.R.attr.textColor});
        int color = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        String str2 = "";
        if (activeModemCount < 2) {
            str = resources.getString(R.string.kg_puk_enter_puk_hint);
        } else {
            SubscriptionInfo subscriptionInfoForSubId = this.mKeyguardUpdateMonitor.getSubscriptionInfoForSubId(this.mSubId);
            String string = resources.getString(R.string.kg_puk_enter_puk_hint_multi, subscriptionInfoForSubId != null ? subscriptionInfoForSubId.getDisplayName() : "");
            if (subscriptionInfoForSubId != null) {
                color = subscriptionInfoForSubId.getIconTint();
            }
            str = string;
        }
        if (isEsimLocked) {
            str = resources.getString(R.string.kg_sim_lock_esim_instructions, str);
        }
        keyguardSecMessageAreaController.setMessage(str, false);
        this.mSimImageView.setImageTintList(ColorStateList.valueOf(color));
        new CheckSimPuk(str2, str2, this.mSubId) { // from class: com.android.keyguard.KeyguardSimPukViewController.2
            @Override // com.android.keyguard.KeyguardSimPukViewController.CheckSimPuk
            public final void onSimLockChangedResponse(PinResult pinResult) {
                if (pinResult == null) {
                    Log.e("KeyguardSimPukView", "onSimCheckResponse, pin result is NULL");
                    return;
                }
                Log.d("KeyguardSimPukView", "onSimCheckResponse  empty One result " + pinResult.toString());
                if (pinResult.getAttemptsRemaining() >= 0) {
                    KeyguardSimPukViewController.this.mRemainingAttempts = pinResult.getAttemptsRemaining();
                    KeyguardSimPukViewController keyguardSimPukViewController = KeyguardSimPukViewController.this;
                    keyguardSimPukViewController.mMessageAreaController.setMessage(((KeyguardSimPukView) keyguardSimPukViewController.mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), true, KeyguardEsimArea.isEsimLocked(this.mSubId, ((KeyguardSimPukView) KeyguardSimPukViewController.this.mView).getContext())), false);
                }
            }
        }.start();
    }

    public void updateSim() {
        if (this.mSimUnlockProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(((KeyguardSimPukView) this.mView).getContext());
            this.mSimUnlockProgressDialog = progressDialog;
            progressDialog.setMessage(((KeyguardSimPukView) this.mView).getResources().getString(R.string.kg_sim_unlock_progress_dialog_message));
            this.mSimUnlockProgressDialog.setIndeterminate(true);
            this.mSimUnlockProgressDialog.setCancelable(false);
            if (!(((KeyguardSimPukView) this.mView).getContext() instanceof Activity)) {
                this.mSimUnlockProgressDialog.getWindow().setType(2009);
            }
        }
        this.mSimUnlockProgressDialog.show();
        if (this.mCheckSimPukThread == null) {
            C07793 c07793 = new C07793(this.mPukText, this.mPinText, this.mSubId);
            this.mCheckSimPukThread = c07793;
            c07793.start();
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public void verifyPasswordAndUnlock() {
        this.mStateMachine.next();
    }
}
