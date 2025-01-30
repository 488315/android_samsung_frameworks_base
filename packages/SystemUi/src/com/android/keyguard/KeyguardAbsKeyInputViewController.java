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
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.flags.FeatureFlags;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardAbsKeyInputViewController extends KeyguardInputViewController {
    public CountDownTimer mCountdownTimer;
    public boolean mDismissing;
    public final C06541 mEmergencyButtonCallback;
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

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.keyguard.KeyguardAbsKeyInputViewController$1] */
    public KeyguardAbsKeyInputViewController(KeyguardAbsKeyInputView keyguardAbsKeyInputView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags) {
        super(keyguardAbsKeyInputView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags);
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
        this.mCountdownTimer = new CountDownTimer(((long) Math.ceil((j - SystemClock.elapsedRealtime()) / 1000.0d)) * 1000, 1000L) { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController.2
            @Override // android.os.CountDownTimer
            public final void onFinish() {
                KeyguardAbsKeyInputViewController.this.mMessageAreaController.setMessage("", false);
                KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = KeyguardAbsKeyInputViewController.this;
                keyguardAbsKeyInputViewController.mLockedOut = false;
                keyguardAbsKeyInputViewController.resetState();
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j2) {
                int round = (int) Math.round(j2 / 1000.0d);
                HashMap hashMap = new HashMap();
                hashMap.put("count", Integer.valueOf(round));
                KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = KeyguardAbsKeyInputViewController.this;
                keyguardAbsKeyInputViewController.mMessageAreaController.setMessage(PluralsMessageFormatter.format(((KeyguardAbsKeyInputView) keyguardAbsKeyInputViewController.mView).getResources(), hashMap, R.string.kg_too_many_failed_attempts_countdown), false);
            }
        }.start();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return this instanceof KeyguardCarrierPasswordViewController;
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
    }

    public void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        boolean z3 = KeyguardUpdateMonitor.getCurrentUser() == i;
        if (z) {
            getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
            if (z3) {
                this.mDismissing = true;
                this.mLatencyTracker.onActionStart(11);
                getKeyguardSecurityCallback().dismiss(i, this.mSecurityMode, true);
                return;
            }
            return;
        }
        if (z2) {
            getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
            if (i2 > 0) {
                handleAttemptLockout(this.mLockPatternUtils.setLockoutAttemptDeadline(i, i2));
            }
        }
        ((KeyguardAbsKeyInputView) this.mView).resetPasswordText(true, false);
        if (i2 == 0) {
            this.mMessageAreaController.setMessage(((KeyguardAbsKeyInputView) this.mView).getWrongPasswordStringId());
        }
        startErrorAnimation();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        this.mPaused = true;
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
        reset();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        this.mPaused = false;
        this.mResumed = true;
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser());
        if (shouldLockout(lockoutAttemptDeadline)) {
            handleAttemptLockout(lockoutAttemptDeadline);
        }
    }

    public void onUserInput() {
        ((FalsingCollectorImpl) this.mFalsingCollector).updateFalseConfidence(FalsingClassifier.Result.passed(0.6d));
        getKeyguardSecurityCallback().userActivity();
        getKeyguardSecurityCallback().onUserInput();
        this.mMessageAreaController.setMessage("", false);
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        ((KeyguardAbsKeyInputView) this.mView).mKeyDownListener = this.mKeyDownListener;
        this.mEmergencyButtonController.mEmergencyButtonCallback = this.mEmergencyButtonCallback;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void reset() {
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
            ((KeyguardMessageArea) keyguardSecMessageAreaController.mView).setNextMessageColor(colorStateList);
        }
        keyguardSecMessageAreaController.setMessage(charSequence, z);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void showPromptReason(int i) {
        int promptReasonStringRes;
        if (i == 0 || (promptReasonStringRes = ((KeyguardAbsKeyInputView) this.mView).getPromptReasonStringRes(i)) == 0) {
            return;
        }
        this.mMessageAreaController.setMessage(((KeyguardAbsKeyInputView) this.mView).getResources().getString(promptReasonStringRes), false);
    }

    public void startErrorAnimation() {
    }
}
