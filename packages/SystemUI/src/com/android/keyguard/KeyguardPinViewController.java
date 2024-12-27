package com.android.keyguard;

import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardInputView;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.vibrate.VibrationUtil;

public class KeyguardPinViewController extends KeyguardSecPinBasedInputViewController {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;
    public final long mPinLength;
    public final KeyguardPinViewController$$ExternalSyntheticLambda0 mPostureCallback;
    public final DevicePostureController mPostureController;
    public final UiEventLogger mUiEventLogger;

    enum PinBouncerUiEvent implements UiEventLogger.UiEventEnum {
        ATTEMPT_UNLOCK_WITH_AUTO_CONFIRM_FEATURE(1547);

        private final int mId;

        PinBouncerUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda0] */
    public KeyguardPinViewController(KeyguardPINView keyguardPINView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, UiEventLogger uiEventLogger, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardPINView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor);
        this.mPostureCallback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                ((KeyguardPINView) KeyguardPinViewController.this.mView).onDevicePostureChanged(i);
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPostureController = devicePostureController;
        this.mLockPatternUtils = lockPatternUtils;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        keyguardPINView.mIsSmallLockScreenLandscapeEnabled = false;
        this.mPinLength = lockPatternUtils.getPinLength(selectedUserInteractor.getSelectedUserId(false));
        this.mUiEventLogger = uiEventLogger;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void onUserInput() {
        super.onUserInput();
        if (this.mLockPatternUtils.isAutoPinConfirmEnabled(this.mSelectedUserInteractor.getSelectedUserId()) && this.mPinLength != -1 && this.mPasswordEntry.getText().length() == this.mPinLength && this.mOkButton.getVisibility() == 4) {
            this.mUiEventLogger.log(PinBouncerUiEvent.ATTEMPT_UNLOCK_WITH_AUTO_CONFIRM_FEATURE);
            verifyPasswordAndUnlock();
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        View findViewById = ((KeyguardPINView) this.mView).findViewById(R.id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardPinViewController keyguardPinViewController = KeyguardPinViewController.this;
                    keyguardPinViewController.getKeyguardSecurityCallback().reset();
                    keyguardPinViewController.getKeyguardSecurityCallback().onCancelClicked();
                }
            });
        }
        ((KeyguardPINView) this.mView).onDevicePostureChanged(((DevicePostureControllerImpl) this.mPostureController).getDevicePosture());
        ((DevicePostureControllerImpl) this.mPostureController).addCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        ((DevicePostureControllerImpl) this.mPostureController).removeCallback(this.mPostureCallback);
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
        }, new KeyguardInputView.AnonymousClass1(22));
        return true;
    }
}
