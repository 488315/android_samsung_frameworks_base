package com.android.keyguard;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.PluralsMessageFormatter;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputView;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.animation.DisappearAnimationUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardPatternViewController extends KeyguardInputViewController {
    public final RunnableC07062 mCancelPatternRunnable;
    public CountDownTimer mCountdownTimer;
    public final C07051 mEmergencyButtonCallback;
    public final EmergencyButtonController mEmergencyButtonController;
    public final FalsingCollector mFalsingCollector;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public final LockPatternView mLockPatternView;
    public AsyncTask mPendingLockCheck;
    public final KeyguardPatternViewController$$ExternalSyntheticLambda0 mPostureCallback;
    public final DevicePostureController mPostureController;
    public LockscreenCredential mPrevCredential;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.keyguard.KeyguardPatternViewController$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.keyguard.KeyguardPatternViewController$2] */
    public KeyguardPatternViewController(KeyguardPatternView keyguardPatternView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, DevicePostureController devicePostureController, FeatureFlags featureFlags) {
        super(keyguardPatternView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags);
        this.mPostureCallback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                ((KeyguardPatternView) KeyguardPatternViewController.this.mView).getClass();
            }
        };
        this.mEmergencyButtonCallback = new EmergencyButtonController.EmergencyButtonCallback() { // from class: com.android.keyguard.KeyguardPatternViewController.1
            @Override // com.android.keyguard.EmergencyButtonController.EmergencyButtonCallback
            public final void onEmergencyButtonClickedWhenInCall() {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reset();
            }
        };
        this.mCancelPatternRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardPatternViewController.2
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPatternViewController.this.mLockPatternView.clearPattern();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockPatternUtils = lockPatternUtils;
        this.mLatencyTracker = latencyTracker;
        this.mFalsingCollector = falsingCollector;
        this.mEmergencyButtonController = emergencyButtonController;
        this.mLockPatternView = ((KeyguardPatternView) this.mView).findViewById(R.id.lockPatternView);
        this.mPostureController = devicePostureController;
    }

    public void displayDefaultSecurityMessage() {
        this.mMessageAreaController.setMessage(getInitialMessageResId());
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public int getInitialMessageResId() {
        return R.string.keyguard_enter_your_pattern;
    }

    public void handleAttemptLockout(long j) {
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.clearPattern();
        lockPatternView.setEnabled(false);
        this.mCountdownTimer = new CountDownTimer(((long) Math.ceil((j - SystemClock.elapsedRealtime()) / 1000.0d)) * 1000, 1000L) { // from class: com.android.keyguard.KeyguardPatternViewController.3
            @Override // android.os.CountDownTimer
            public final void onFinish() {
                KeyguardPatternViewController.this.mLockPatternView.setEnabled(true);
                KeyguardPatternViewController.this.displayDefaultSecurityMessage();
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j2) {
                int round = (int) Math.round(j2 / 1000.0d);
                HashMap hashMap = new HashMap();
                hashMap.put("count", Integer.valueOf(round));
                KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
                keyguardPatternViewController.mMessageAreaController.setMessage(PluralsMessageFormatter.format(((KeyguardPatternView) keyguardPatternViewController.mView).getResources(), hashMap, R.string.kg_too_many_failed_attempts_countdown), false);
            }
        }.start();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        this.mPaused = true;
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
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        this.mPaused = false;
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser());
        if (lockoutAttemptDeadline != 0) {
            handleAttemptLockout(lockoutAttemptDeadline);
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        UnlockPatternListener unlockPatternListener = new UnlockPatternListener();
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.setOnPatternListener(unlockPatternListener);
        lockPatternView.setSaveEnabled(false);
        lockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(KeyguardUpdateMonitor.getCurrentUser()));
        lockPatternView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
                keyguardPatternViewController.getClass();
                if (motionEvent.getActionMasked() != 0) {
                    return false;
                }
                ((FalsingCollectorImpl) keyguardPatternViewController.mFalsingCollector).avoidGesture();
                return false;
            }
        });
        this.mEmergencyButtonController.mEmergencyButtonCallback = this.mEmergencyButtonCallback;
        View findViewById = ((KeyguardPatternView) this.mView).findViewById(R.id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
                    keyguardPatternViewController.getKeyguardSecurityCallback().reset();
                    keyguardPatternViewController.getKeyguardSecurityCallback().onCancelClicked();
                }
            });
        }
        ((DevicePostureControllerImpl) this.mPostureController).addCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.setOnPatternListener((LockPatternView.OnPatternListener) null);
        lockPatternView.setOnTouchListener((View.OnTouchListener) null);
        this.mEmergencyButtonController.mEmergencyButtonCallback = null;
        View findViewById = ((KeyguardPatternView) this.mView).findViewById(R.id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(null);
        }
        ((DevicePostureControllerImpl) this.mPostureController).removeCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void reset() {
        boolean z = !this.mLockPatternUtils.isVisiblePatternEnabled(KeyguardUpdateMonitor.getCurrentUser());
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.setInStealthMode(z);
        lockPatternView.enableInput();
        lockPatternView.setEnabled(true);
        lockPatternView.clearPattern();
        displayDefaultSecurityMessage();
    }

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
        int i2;
        if (i == 0) {
            i2 = 0;
        } else if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    i2 = R.string.kg_prompt_reason_device_admin;
                } else if (i == 4) {
                    i2 = R.string.kg_prompt_after_user_lockdown_pattern;
                } else if (i == 6) {
                    i2 = R.string.kg_prompt_unattended_update_pattern;
                } else if (i != 7 && i != 8 && i == 16) {
                    i2 = R.string.kg_prompt_after_update_pattern;
                }
            }
            i2 = R.string.kg_prompt_reason_timeout_pattern;
        } else {
            i2 = R.string.kg_prompt_reason_restart_pattern;
        }
        if (i2 != 0) {
            this.mMessageAreaController.setMessage(getResources().getText(i2), false);
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void startAppearAnimation() {
        super.startAppearAnimation();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public boolean startDisappearAnimation(final Runnable runnable) {
        final KeyguardPatternView keyguardPatternView = (KeyguardPatternView) this.mView;
        boolean z = this.mKeyguardUpdateMonitor.mNeedsSlowUnlockTransition;
        keyguardPatternView.getClass();
        float f = z ? 1.5f : 1.0f;
        keyguardPatternView.mLockPatternView.clearPattern();
        keyguardPatternView.enableClipping(false);
        keyguardPatternView.setTranslationY(0.0f);
        DisappearAnimationUtils disappearAnimationUtils = keyguardPatternView.mDisappearAnimationUtils;
        AppearAnimationUtils.startTranslationYAnimation(keyguardPatternView, 0L, (long) (300.0f * f), -disappearAnimationUtils.mStartTranslation, disappearAnimationUtils.mInterpolator, new KeyguardInputView.C06871(21));
        (z ? keyguardPatternView.mDisappearAnimationUtilsLocked : keyguardPatternView.mDisappearAnimationUtils).startAnimation2d(keyguardPatternView.mLockPatternView.getCellStates(), new Runnable() { // from class: com.android.keyguard.KeyguardPatternView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPatternView keyguardPatternView2 = KeyguardPatternView.this;
                Runnable runnable2 = runnable;
                int i = KeyguardPatternView.$r8$clinit;
                keyguardPatternView2.enableClipping(true);
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        }, keyguardPatternView);
        if (TextUtils.isEmpty(keyguardPatternView.mSecurityMessageDisplay.getText())) {
            return true;
        }
        DisappearAnimationUtils disappearAnimationUtils2 = keyguardPatternView.mDisappearAnimationUtils;
        disappearAnimationUtils2.createAnimation((View) keyguardPatternView.mSecurityMessageDisplay, 0L, (long) (f * 200.0f), (-disappearAnimationUtils2.mStartTranslation) * 3.0f, false, disappearAnimationUtils2.mInterpolator, (Runnable) null);
        return true;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class UnlockPatternListener implements LockPatternView.OnPatternListener {
        public UnlockPatternListener() {
        }

        public final void onPatternCellAdded(List list) {
            KeyguardPatternViewController.this.getKeyguardSecurityCallback().userActivity();
            KeyguardPatternViewController.this.getKeyguardSecurityCallback().onUserInput();
        }

        public void onPatternChecked(int i, int i2, boolean z, boolean z2) {
            boolean z3 = KeyguardUpdateMonitor.getCurrentUser() == i;
            if (z) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
                if (z3) {
                    KeyguardPatternViewController.this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                    KeyguardPatternViewController.this.mLatencyTracker.onActionStart(11);
                    KeyguardPatternViewController.this.getKeyguardSecurityCallback().dismiss(i, KeyguardSecurityModel.SecurityMode.Pattern, true);
                    return;
                }
                return;
            }
            KeyguardPatternViewController.this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            if (z2) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
                if (i2 > 0) {
                    KeyguardPatternViewController.this.handleAttemptLockout(KeyguardPatternViewController.this.mLockPatternUtils.setLockoutAttemptDeadline(i, i2));
                }
            }
            if (i2 == 0) {
                KeyguardPatternViewController.this.mMessageAreaController.setMessage(R.string.kg_wrong_pattern);
                KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
                keyguardPatternViewController.mLockPatternView.postDelayed(keyguardPatternViewController.mCancelPatternRunnable, 2000L);
            }
        }

        public void onPatternDetected(List list) {
            KeyguardPatternViewController.this.mKeyguardUpdateMonitor.setCredentialAttempted();
            KeyguardPatternViewController.this.mLockPatternView.disableInput();
            AsyncTask asyncTask = KeyguardPatternViewController.this.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
            }
            boolean isForgotPasswordView = KeyguardPatternViewController.this.mKeyguardUpdateMonitor.isForgotPasswordView();
            final int currentUser = isForgotPasswordView ? -9998 : KeyguardUpdateMonitor.getCurrentUser();
            if (LsRune.SECURITY_UNPACK) {
                Log.e("KeyguardPatternViewController", "just for UNPACK device. Always match success");
            } else if (list.size() < 4) {
                if (list.size() == 1) {
                    ((FalsingCollectorImpl) KeyguardPatternViewController.this.mFalsingCollector).updateFalseConfidence(FalsingClassifier.Result.falsed(0.7d, getClass().getSimpleName(), "empty pattern input"));
                }
                KeyguardPatternViewController.this.mLockPatternView.enableInput();
                onPatternChecked(currentUser, 0, false, false);
                return;
            }
            KeyguardPatternViewController.this.mLatencyTracker.onActionStart(3);
            KeyguardPatternViewController.this.mLatencyTracker.onActionStart(4);
            final LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
            if (isForgotPasswordView) {
                KeyguardPatternViewController.this.mPrevCredential = createPattern;
            }
            KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
            keyguardPatternViewController.mPendingLockCheck = LockPatternChecker.checkCredential(keyguardPatternViewController.mLockPatternUtils, isForgotPasswordView ? keyguardPatternViewController.mPrevCredential : createPattern, currentUser, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener.1
                public final void onCancelled() {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(4);
                    createPattern.zeroize();
                }

                public final void onChecked(boolean z, int i) {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(4);
                    KeyguardPatternViewController.this.mLockPatternView.enableInput();
                    UnlockPatternListener unlockPatternListener = UnlockPatternListener.this;
                    KeyguardPatternViewController.this.mPendingLockCheck = null;
                    if (!z) {
                        unlockPatternListener.onPatternChecked(currentUser, i, false, true);
                    }
                    createPattern.zeroize();
                }

                public final void onEarlyMatched() {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(3);
                    UnlockPatternListener.this.onPatternChecked(currentUser, 0, true, true);
                    createPattern.zeroize();
                }
            });
            if (list.size() > 2) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().userActivity();
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().onUserInput();
            }
        }

        public void onPatternStart() {
            KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
            keyguardPatternViewController.mLockPatternView.removeCallbacks(keyguardPatternViewController.mCancelPatternRunnable);
            KeyguardPatternViewController.this.mMessageAreaController.setMessage("", false);
        }

        public final void onPatternCleared() {
        }
    }
}
