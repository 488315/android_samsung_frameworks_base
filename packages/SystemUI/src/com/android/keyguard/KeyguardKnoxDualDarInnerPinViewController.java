package com.android.keyguard;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.knox.SemPersonaManager;

public final class KeyguardKnoxDualDarInnerPinViewController extends KeyguardSecPinViewController {
    public byte[] mEntry;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public final VibrationUtil mVibrationUtil;

    public KeyguardKnoxDualDarInnerPinViewController(KeyguardSecPINView keyguardSecPINView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, UiEventLogger uiEventLogger, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardSecPINView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, devicePostureController, featureFlags, selectedUserInteractor, uiEventLogger, keyguardKeyboardInteractor);
        this.mEntry = null;
        this.mLatencyTracker = latencyTracker;
        this.mVibrationUtil = vibrationUtil;
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        this.mLockPatternUtils = lockPatternUtils;
    }

    @Override // com.android.keyguard.KeyguardSecPinViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void displayDefaultSecurityMessage() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            Log.e("KeyguardKnoxDualDarInnerPinViewController", "displayDefaultSecurityMessage mMessageAreaController is null");
            return;
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView == null || ((SecPasswordTextView) passwordTextView).mText.length() <= 0) {
            String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.PIN);
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardKnoxDualDarInnerPinViewController");
            keyguardSecMessageAreaController.formatMessage(R.string.kg_knox_dual_dar_inner_instructions, defaultSecurityMessage);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        boolean z3 = this.mSelectedUserInteractor.getSelectedUserId(false) == ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getMainUserId(i);
        com.android.systemui.keyguard.Log.d("KeyguardKnoxDualDarInnerPinViewController", "!@onPasswordChecked matched=%b timeoutMs=%d userId=%d", Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(i));
        if (z) {
            Log.e("KeyguardKnoxDualDarInnerPinViewController", "onPasswordChecked");
            this.mVibrationUtil.playVibration(1);
            getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
            if (z3) {
                this.mDismissing = true;
                getKeyguardSecurityCallback().dismiss(true, i, true, ((KeyguardInputViewController) this).mSecurityMode);
            }
        } else {
            this.mVibrationUtil.playVibration(114);
            if (z2) {
                getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
                if (i2 > 0) {
                    setMessageTimeout(true);
                    handleAttemptLockout(((KnoxStateMonitorImpl) this.mKnoxStateMonitor).setLockoutAttemptDeadline(i, i2));
                }
            }
            if (i2 == 0) {
                setMessageTimeout(false);
                int remainingAttempt = ((KeyguardPinViewController) this).mKeyguardUpdateMonitor.getRemainingAttempt(2);
                Context context = getContext();
                ((KeyguardSecPINView) this.mView).getClass();
                String string = context.getString(R.string.kg_incorrect_pin);
                if (remainingAttempt > 0) {
                    string = ComponentActivity$1$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, " ("), getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                }
                KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
                keyguardSecMessageAreaController.setMessage$1(string, false);
                keyguardSecMessageAreaController.announceForAccessibility(string);
                keyguardSecMessageAreaController.displayFailedAnimation();
            }
        }
        if (this.mEntry != null) {
            this.mEntry = null;
        }
        ((KeyguardSecPINView) this.mView).resetPasswordText(true, !z);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void reset$1() {
        this.mDismissing = false;
        ((KeyguardSecPINView) this.mView).resetPasswordText(false, false);
        long dualDarInnerLockoutAttemptDeadline$1 = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getDualDarInnerLockoutAttemptDeadline$1();
        updateLayout();
        if (shouldLockout(dualDarInnerLockoutAttemptDeadline$1)) {
            handleAttemptLockout(dualDarInnerLockoutAttemptDeadline$1);
        } else {
            resetState();
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        if (!SemPersonaManager.isDualDARCustomCrypto(this.mSelectedUserInteractor.getSelectedUserId()) || !getContext().getPackageManager().isSafeMode()) {
            super.resetState();
        } else {
            Log.w("KeyguardKnoxDualDarInnerPinViewController", "DualDar at Do safe mode with custom crypto case");
            this.mPasswordEntry.setEnabled(false);
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController
    public final void setOkButtonEnabled(boolean z) {
        View view = this.mOkButton;
        if (view != null) {
            view.setFocusable(z);
            this.mOkButton.setClickable(z);
            this.mOkButton.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void showPromptReason(int i) {
        if (this.mMessageAreaController == null) {
            Log.d("KeyguardKnoxDualDarInnerPinViewController", "showPromptReason mMessageAreaController is null");
            return;
        }
        this.mPromptReason = i;
        if (i == 0 || ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getDualDarInnerLockoutAttemptDeadline$1() > 0) {
            return;
        }
        ((KeyguardSecPINView) this.mView).getClass();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        if (this.mDismissing) {
            Log.e("KeyguardKnoxDualDarInnerPinViewController", "verifyPasswordAndUnlock! already verified but haven't been dismissed. don't do it again.");
            return;
        }
        com.android.systemui.keyguard.Log.d("KeyguardKnoxDualDarInnerPinViewController", "verifyPasswordAndUnlock");
        this.mEntry = getPasswordText();
        final LockscreenCredential enteredCredential = ((KeyguardSecPINView) this.mView).getEnteredCredential();
        ((KeyguardSecPINView) this.mView).setPasswordEntryInputEnabled(false);
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        final int innerAuthUserId = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getInnerAuthUserId(this.mSelectedUserInteractor.getSelectedUserId(false));
        int size = enteredCredential.size();
        KeyguardSecPINView keyguardSecPINView = (KeyguardSecPINView) this.mView;
        if (size <= 3) {
            keyguardSecPINView.setPasswordEntryInputEnabled(true);
            onPasswordChecked(innerAuthUserId, 0, false, false);
            enteredCredential.zeroize();
        } else {
            this.mLatencyTracker.onActionStart(3);
            this.mLatencyTracker.onActionStart(4);
            ((KeyguardPinViewController) this).mKeyguardUpdateMonitor.setCredentialAttempted();
            this.mPendingLockCheck = LockPatternChecker.checkCredential(this.mLockPatternUtils, enteredCredential, innerAuthUserId, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardKnoxDualDarInnerPinViewController.1
                public final void onCancelled() {
                    KeyguardKnoxDualDarInnerPinViewController.this.mLatencyTracker.onActionEnd(4);
                    enteredCredential.zeroize();
                }

                public final void onChecked(boolean z, int i) {
                    KeyguardKnoxDualDarInnerPinViewController.this.mLatencyTracker.onActionEnd(4);
                    ((KeyguardSecPINView) ((ViewController) KeyguardKnoxDualDarInnerPinViewController.this).mView).setPasswordEntryInputEnabled(true);
                    KeyguardKnoxDualDarInnerPinViewController keyguardKnoxDualDarInnerPinViewController = KeyguardKnoxDualDarInnerPinViewController.this;
                    keyguardKnoxDualDarInnerPinViewController.mPendingLockCheck = null;
                    if (!z) {
                        keyguardKnoxDualDarInnerPinViewController.onPasswordChecked(innerAuthUserId, i, false, true);
                    }
                    enteredCredential.zeroize();
                }

                public final void onEarlyMatched() {
                    KeyguardKnoxDualDarInnerPinViewController.this.mLatencyTracker.onActionEnd(3);
                    KeyguardKnoxDualDarInnerPinViewController.this.onPasswordChecked(innerAuthUserId, 0, true, true);
                    enteredCredential.zeroize();
                }
            });
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinViewController
    public final void verifyNDigitsPIN() {
    }
}
