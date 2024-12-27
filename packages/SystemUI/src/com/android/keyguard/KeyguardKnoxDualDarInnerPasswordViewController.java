package com.android.keyguard;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
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
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardKnoxDualDarInnerPasswordViewController extends KeyguardSecPasswordViewController {
    public byte[] mEntry;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final VibrationUtil mVibrationUtil;

    public KeyguardKnoxDualDarInnerPasswordViewController(KeyguardSecPasswordView keyguardSecPasswordView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, InputMethodManager inputMethodManager, EmergencyButtonController emergencyButtonController, DelayableExecutor delayableExecutor, Resources resources, FalsingCollector falsingCollector, KeyguardViewController keyguardViewController, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardSecPasswordView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, inputMethodManager, emergencyButtonController, delayableExecutor, resources, falsingCollector, keyguardViewController, devicePostureController, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor);
        this.mEntry = null;
        this.mLatencyTracker = latencyTracker;
        this.mVibrationUtil = vibrationUtil;
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.keyguard.KeyguardSecPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void displayDefaultSecurityMessage() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            Log.e("KeyguardKnoxDualDarInnerPasswordViewController", "displayDefaultSecurityMessage mMessageAreaController is null");
            return;
        }
        String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Password);
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardKnoxDualDarInnerPasswordViewController");
        keyguardSecMessageAreaController.formatMessage(R.string.kg_knox_dual_dar_inner_instructions, defaultSecurityMessage);
    }

    @Override // com.android.keyguard.KeyguardSecPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        boolean z3 = selectedUserId == knoxStateMonitorImpl.getMainUserId(i);
        com.android.systemui.keyguard.Log.d("KeyguardKnoxDualDarInnerPasswordViewController", "!@onPasswordChecked matched=%b timeoutMs=%d userId=%d", Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(i));
        VibrationUtil vibrationUtil = this.mVibrationUtil;
        if (z) {
            Log.e("KeyguardKnoxDualDarInnerPasswordViewController", "onPasswordChecked");
            this.mInputMethodManager.forceHideSoftInput();
            vibrationUtil.playVibration(1);
            getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
            if (z3) {
                this.mDismissing = true;
                getKeyguardSecurityCallback().dismiss(true, i, true, this.mSecurityMode);
            }
        } else {
            vibrationUtil.playVibration(114);
            if (z2) {
                getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
                if (i2 > 0) {
                    setMessageTimeout(true);
                    handleAttemptLockout(knoxStateMonitorImpl.setLockoutAttemptDeadline(i, i2));
                }
            }
            if (i2 == 0) {
                setMessageTimeout(false);
                int remainingAttempt = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.getRemainingAttempt(2);
                Context context = getContext();
                ((KeyguardSecPasswordView) this.mView).getClass();
                String string = context.getString(R.string.kg_incorrect_password);
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
        ((KeyguardSecPasswordView) this.mView).resetPasswordText(true, !z);
    }

    @Override // com.android.keyguard.KeyguardSecPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void reset$1() {
        this.mDismissing = false;
        ((KeyguardSecPasswordView) this.mView).resetPasswordText(false, false);
        long dualDarInnerLockoutAttemptDeadline$1 = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getDualDarInnerLockoutAttemptDeadline$1();
        updateLayout();
        if (shouldLockout(dualDarInnerLockoutAttemptDeadline$1)) {
            handleAttemptLockout(dualDarInnerLockoutAttemptDeadline$1);
        } else {
            resetState();
        }
    }

    @Override // com.android.keyguard.KeyguardSecPasswordViewController, com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        if (!SemPersonaManager.isDualDARCustomCrypto(this.mSelectedUserInteractor.getSelectedUserId()) || !getContext().getPackageManager().isSafeMode()) {
            super.resetState();
        } else {
            Log.w("KeyguardKnoxDualDarInnerPasswordViewController", "DualDar at Do safe mode with custom crypto case");
            this.mPasswordEntry.setEnabled(false);
        }
    }

    @Override // com.android.keyguard.KeyguardSecPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void showPromptReason(int i) {
        if (this.mMessageAreaController == null) {
            Log.d("KeyguardKnoxDualDarInnerPasswordViewController", "showPromptReason mMessageAreaController is null");
            return;
        }
        this.mPromptReason = i;
        if (i == 0 || ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getDualDarInnerLockoutAttemptDeadline$1() > 0) {
            return;
        }
        ((KeyguardSecPasswordView) this.mView).getClass();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        if (this.mDismissing) {
            Log.e("KeyguardKnoxDualDarInnerPasswordViewController", "verifyPasswordAndUnlock! already verified but haven't been dismissed. don't do it again.");
            return;
        }
        com.android.systemui.keyguard.Log.d("KeyguardKnoxDualDarInnerPasswordViewController", "verifyPasswordAndUnlock");
        this.mEntry = KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(this.mPasswordEntry.getText());
        final LockscreenCredential enteredCredential = ((KeyguardSecPasswordView) this.mView).getEnteredCredential();
        ((KeyguardSecPasswordView) this.mView).setPasswordEntryInputEnabled(false);
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        final int innerAuthUserId = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getInnerAuthUserId(this.mSelectedUserInteractor.getSelectedUserId(false));
        int size = enteredCredential.size();
        KeyguardSecPasswordView keyguardSecPasswordView = (KeyguardSecPasswordView) this.mView;
        if (size <= 3) {
            keyguardSecPasswordView.setPasswordEntryInputEnabled(true);
            onPasswordChecked(innerAuthUserId, 0, false, false);
            enteredCredential.zeroize();
        } else {
            this.mLatencyTracker.onActionStart(3);
            this.mLatencyTracker.onActionStart(4);
            ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.setCredentialAttempted();
            this.mPendingLockCheck = LockPatternChecker.checkCredential(this.mLockPatternUtils, enteredCredential, innerAuthUserId, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController.1
                public final void onCancelled() {
                    KeyguardKnoxDualDarInnerPasswordViewController.this.mLatencyTracker.onActionEnd(4);
                    enteredCredential.zeroize();
                }

                public final void onChecked(boolean z, int i) {
                    KeyguardKnoxDualDarInnerPasswordViewController.this.mLatencyTracker.onActionEnd(4);
                    ((KeyguardSecPasswordView) ((ViewController) KeyguardKnoxDualDarInnerPasswordViewController.this).mView).setPasswordEntryInputEnabled(true);
                    KeyguardKnoxDualDarInnerPasswordViewController keyguardKnoxDualDarInnerPasswordViewController = KeyguardKnoxDualDarInnerPasswordViewController.this;
                    keyguardKnoxDualDarInnerPasswordViewController.mPendingLockCheck = null;
                    if (!z) {
                        keyguardKnoxDualDarInnerPasswordViewController.onPasswordChecked(innerAuthUserId, i, false, true);
                    }
                    enteredCredential.zeroize();
                }

                public final void onEarlyMatched() {
                    KeyguardKnoxDualDarInnerPasswordViewController.this.mLatencyTracker.onActionEnd(3);
                    KeyguardKnoxDualDarInnerPasswordViewController.this.onPasswordChecked(innerAuthUserId, 0, true, true);
                    enteredCredential.zeroize();
                }
            });
        }
    }
}
