package com.android.keyguard;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.PluralsMessageFormatter;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import java.util.HashMap;

public abstract class KeyguardAbsKeyInputViewController extends KeyguardInputViewController {
    public CountDownTimer mCountdownTimer;
    public boolean mDismissing;
    public final AnonymousClass1 mEmergencyButtonCallback;
    public final EmergencyButtonController mEmergencyButtonController;
    public final FalsingCollector mFalsingCollector;
    public final KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 mKeyDownListener;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public boolean mLockedOut;
    public AsyncTask mPendingLockCheck;
    public LockscreenCredential mPrevCredential;
    public boolean mResumed;

    public KeyguardAbsKeyInputViewController(KeyguardAbsKeyInputView keyguardAbsKeyInputView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardAbsKeyInputView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags, selectedUserInteractor);
        this.mKeyDownListener = new KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0(this);
        this.mEmergencyButtonCallback = new EmergencyButtonController.EmergencyButtonCallback() { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController.1
            @Override // com.android.keyguard.EmergencyButtonController.EmergencyButtonCallback
            public final void onEmergencyButtonClickedWhenInCall() {
                KeyguardAbsKeyInputViewController.this.getKeyguardSecurityCallback().reset();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockPatternUtils = lockPatternUtils;
        this.mLatencyTracker = latencyTracker;
        this.mFalsingCollector = falsingCollector;
        this.mEmergencyButtonController = emergencyButtonController;
    }

    public void handleAttemptLockout(long j) {
        ((KeyguardAbsKeyInputView) this.mView).setPasswordEntryEnabled(false);
        ((KeyguardAbsKeyInputView) this.mView).setPasswordEntryInputEnabled(false);
        this.mLockedOut = true;
        long ceil = (long) Math.ceil((j - SystemClock.elapsedRealtime()) / 1000.0d);
        getKeyguardSecurityCallback().onAttemptLockoutStart(ceil);
        this.mCountdownTimer = new CountDownTimer(ceil * 1000, 1000L) { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController.2
            @Override // android.os.CountDownTimer
            public final void onFinish() {
                KeyguardAbsKeyInputViewController.this.mMessageAreaController.setMessage$1("", false);
                KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = KeyguardAbsKeyInputViewController.this;
                keyguardAbsKeyInputViewController.mLockedOut = false;
                keyguardAbsKeyInputViewController.resetState();
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j2) {
                int round = (int) Math.round(j2 / 1000.0d);
                HashMap hashMap = new HashMap();
                hashMap.put(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(round));
                KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = KeyguardAbsKeyInputViewController.this;
                keyguardAbsKeyInputViewController.mMessageAreaController.setMessage$1(PluralsMessageFormatter.format(((KeyguardAbsKeyInputView) ((ViewController) keyguardAbsKeyInputViewController).mView).getResources(), hashMap, R.string.kg_too_many_failed_attempts_countdown), false);
            }
        }.start();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return this instanceof KeyguardCarrierPasswordViewController;
    }

    public void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        boolean z3 = this.mSelectedUserInteractor.getSelectedUserId(false) == i;
        if (z) {
            getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
            if (z3) {
                this.mDismissing = true;
                this.mLatencyTracker.onActionStart(11);
                getKeyguardSecurityCallback().dismiss(true, i, this.mSecurityMode);
                return;
            }
            return;
        }
        ((KeyguardAbsKeyInputView) this.mView).resetPasswordText(true, false);
        if (z2) {
            getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
            if (i2 > 0) {
                handleAttemptLockout(this.mLockPatternUtils.setLockoutAttemptDeadline(i, i2));
            }
        }
        if (i2 == 0) {
            this.mMessageAreaController.setMessage(((KeyguardAbsKeyInputView) this.mView).getWrongPasswordStringId());
        }
        startErrorAnimation();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        super.onPause();
        this.mResumed = false;
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
            this.mPendingLockCheck = null;
        }
        reset$1();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        this.mPaused = false;
        this.mResumed = true;
    }

    public void onUserInput() {
        this.mFalsingCollector.updateFalseConfidence(FalsingClassifier.Result.passed(0.6d));
        getKeyguardSecurityCallback().userActivity();
        getKeyguardSecurityCallback().onUserInput();
        this.mMessageAreaController.setMessage$1("", false);
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        ((KeyguardAbsKeyInputView) this.mView).mKeyDownListener = this.mKeyDownListener;
        this.mEmergencyButtonController.mEmergencyButtonCallback = this.mEmergencyButtonCallback;
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(this.mSelectedUserInteractor.getSelectedUserId(false));
        if (shouldLockout(lockoutAttemptDeadline)) {
            handleAttemptLockout(lockoutAttemptDeadline);
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void reset$1() {
        this.mDismissing = false;
        ((KeyguardAbsKeyInputView) this.mView).resetPasswordText(false, false);
        resetState();
    }

    public abstract void resetState();

    @Override // com.android.keyguard.KeyguardInputViewController
    public void showMessage(CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            return;
        }
        if (colorStateList != null) {
            keyguardSecMessageAreaController.setNextMessageColor(colorStateList);
        }
        keyguardSecMessageAreaController.setMessage$1(charSequence, z);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void showPromptReason(int i) {
        int promptReasonStringRes;
        if (i == 0 || (promptReasonStringRes = ((KeyguardAbsKeyInputView) this.mView).getPromptReasonStringRes(i)) == 0) {
            return;
        }
        this.mMessageAreaController.setMessage$1(((KeyguardAbsKeyInputView) this.mView).getResources().getString(promptReasonStringRes), false);
    }

    public void startErrorAnimation() {
    }
}
