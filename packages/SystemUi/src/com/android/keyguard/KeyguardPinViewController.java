package com.android.keyguard;

import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardInputView;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.vibrate.VibrationUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardPinViewController extends KeyguardSecPinBasedInputViewController {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;
    public final long mPinLength;
    public final KeyguardPinViewController$$ExternalSyntheticLambda1 mPostureCallback;
    public final DevicePostureController mPostureController;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda1] */
    public KeyguardPinViewController(KeyguardPINView keyguardPINView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, DevicePostureController devicePostureController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController) {
        super(keyguardPINView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
        this.mPostureCallback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                ((KeyguardPINView) KeyguardPinViewController.this.mView).getClass();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPostureController = devicePostureController;
        this.mLockPatternUtils = lockPatternUtils;
        this.mPinLength = lockPatternUtils.getPinLength(KeyguardUpdateMonitor.getCurrentUser());
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void handleAttemptLockout(long j) {
        super.handleAttemptLockout(j);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void onUserInput() {
        super.onUserInput();
        if (this.mLockPatternUtils.isAutoPinConfirmEnabled(KeyguardUpdateMonitor.getCurrentUser()) && this.mPasswordEntry.getText().length() == this.mPinLength && this.mOkButton.getVisibility() == 4) {
            verifyPasswordAndUnlock();
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        View findViewById = ((KeyguardPINView) this.mView).findViewById(R.id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardPinViewController keyguardPinViewController = KeyguardPinViewController.this;
                    keyguardPinViewController.getKeyguardSecurityCallback().reset();
                    keyguardPinViewController.getKeyguardSecurityCallback().onCancelClicked();
                }
            });
        }
        ((DevicePostureControllerImpl) this.mPostureController).addCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        ((DevicePostureControllerImpl) this.mPostureController).removeCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void startAppearAnimation() {
        super.startAppearAnimation();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public boolean startDisappearAnimation(final Runnable runnable) {
        KeyguardPINView keyguardPINView = (KeyguardPINView) this.mView;
        boolean z = this.mKeyguardUpdateMonitor.mNeedsSlowUnlockTransition;
        if (keyguardPINView.mAppearAnimator.isRunning()) {
            keyguardPINView.mAppearAnimator.cancel();
        }
        keyguardPINView.setTranslationY(0.0f);
        (z ? keyguardPINView.mDisappearAnimationUtilsLocked : keyguardPINView.mDisappearAnimationUtils).createAnimation(keyguardPINView, 0L, 200L, keyguardPINView.mDisappearYTranslation, false, keyguardPINView.mDisappearAnimationUtils.mInterpolator, new Runnable() { // from class: com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Runnable runnable2 = runnable;
                int i = KeyguardPINView.$r8$clinit;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        }, new KeyguardInputView.C06871(22));
        return true;
    }
}
