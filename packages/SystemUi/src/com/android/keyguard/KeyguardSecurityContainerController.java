package com.android.keyguard;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.media.AudioManager;
import android.metrics.LogMaker;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.biometrics.SideFpsController;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.data.repository.DeviceEntryFaceAuthRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardFaceAuthInteractor;
import com.android.systemui.keyguard.domain.interactor.SystemUIKeyguardFaceAuthInteractor;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardSecurityContainerController extends ViewController implements KeyguardSecurityView {
    public final AdminSecondaryLockScreenController mAdminSecondaryLockScreenController;
    public final AudioManager mAudioManager;
    public Runnable mCancelAction;
    public final ConfigurationController mConfigurationController;
    public final C07644 mConfigurationListener;
    public KeyguardSecurityModel.SecurityMode mCurrentSecurityMode;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public ActivityStarter.OnDismissAction mDismissAction;
    public final FalsingA11yDelegate mFalsingA11yDelegate;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final GlobalSettings mGlobalSettings;
    final Gefingerpoken mGlobalTouchListener;
    public final KeyguardFaceAuthInteractor mKeyguardFaceAuthInteractor;
    public final KeyguardSecurityCallback mKeyguardSecurityCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public int mLastOrientation;
    public final LockPatternUtils mLockPatternUtils;
    public final MetricsLogger mMetricsLogger;
    public final KeyguardSecurityContainerController$$ExternalSyntheticLambda2 mOnKeyListener;
    public final KeyguardSecurityModel mSecurityModel;
    public final KeyguardSecurityViewFlipperController mSecurityViewFlipperController;
    public final SessionTracker mSessionTracker;
    public final Optional mSideFpsController;
    public final TelephonyManager mTelephonyManager;
    public int mTranslationY;
    public final UiEventLogger mUiEventLogger;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final KeyguardSecurityContainerController$$ExternalSyntheticLambda3 mUserSwitchCallback;
    public final UserSwitcherController mUserSwitcherController;
    public final ViewMediatorCallback mViewMediatorCallback;
    public boolean mWillRunDismissFromKeyguard;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecurityContainerController$7 */
    public abstract /* synthetic */ class AbstractC07677 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f209xdc0e830a;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f209xdc0e830a = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f209xdc0e830a[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f209xdc0e830a[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f209xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f209xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.KeyguardSecurityContainerController$4] */
    public KeyguardSecurityContainerController(KeyguardSecurityContainer keyguardSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, DeviceProvisionedController deviceProvisionedController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, Optional<SideFpsController> optional, FalsingA11yDelegate falsingA11yDelegate, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, AudioManager audioManager, KeyguardFaceAuthInteractor keyguardFaceAuthInteractor) {
        super(keyguardSecurityContainer);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return KeyguardSecurityContainerController.this.interceptMediaKey(keyEvent);
            }
        };
        this.mLastOrientation = 0;
        this.mCurrentSecurityMode = KeyguardSecurityModel.SecurityMode.Invalid;
        this.mUserSwitchCallback = new UserSwitcherController.UserSwitchCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.policy.UserSwitcherController.UserSwitchCallback
            public final void onUserSwitched() {
                KeyguardSecurityContainerController.this.showPrimarySecurityScreen();
            }
        };
        this.mGlobalTouchListener = new Gefingerpoken() { // from class: com.android.keyguard.KeyguardSecurityContainerController.1
            public MotionEvent mTouchDown;

            @Override // com.android.systemui.Gefingerpoken
            public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            /* JADX WARN: Code restructure failed: missing block: B:6:0x001f, code lost:
            
                if (r4.isTouchOnTheOtherSideOfSecurity(r7, r4.isLeftAligned()) != false) goto L10;
             */
            @Override // com.android.systemui.Gefingerpoken
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                boolean z = true;
                if (motionEvent.getActionMasked() == 0) {
                    KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                    KeyguardSecurityContainer.ViewMode viewMode = ((KeyguardSecurityContainer) keyguardSecurityContainerController.mView).mViewMode;
                    if (viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) {
                        KeyguardSecurityContainer.SidedSecurityMode sidedSecurityMode = (KeyguardSecurityContainer.SidedSecurityMode) viewMode;
                    }
                    z = false;
                    if (z) {
                        ((FalsingCollectorImpl) keyguardSecurityContainerController.mFalsingCollector).avoidGesture();
                    }
                    MotionEvent motionEvent2 = this.mTouchDown;
                    if (motionEvent2 != null) {
                        motionEvent2.recycle();
                        this.mTouchDown = null;
                    }
                    this.mTouchDown = MotionEvent.obtain(motionEvent);
                } else if (this.mTouchDown != null && (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3)) {
                    this.mTouchDown.recycle();
                    this.mTouchDown = null;
                }
                return false;
            }
        };
        KeyguardSecurityCallback securityCallback = getSecurityCallback();
        this.mKeyguardSecurityCallback = securityCallback;
        new Object(this) { // from class: com.android.keyguard.KeyguardSecurityContainerController.3
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController.4
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                KeyguardSecurityContainerController.this.getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                keyguardSecurityContainerController.getClass();
                keyguardSecurityContainerController.reset();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                KeyguardSecurityContainerController.this.getClass();
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController.5
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDevicePolicyManagerStateChanged() {
                KeyguardSecurityContainerController.this.showPrimarySecurityScreen();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustGrantedForCurrentUser(boolean z, TrustGrantFlags trustGrantFlags, String str) {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                if (z) {
                    if (!((KeyguardSecurityContainer) keyguardSecurityContainerController.mView).isVisibleToUser()) {
                        Log.i("KeyguardSecurityContainer", "TrustAgent dismissed Keyguard.");
                    }
                    keyguardSecurityContainerController.mKeyguardSecurityCallback.dismiss(false, KeyguardUpdateMonitor.getCurrentUser(), false, KeyguardSecurityModel.SecurityMode.Invalid);
                } else {
                    int i = trustGrantFlags.mFlags;
                    if (!((i & 1) != 0)) {
                        if (!((i & 2) != 0)) {
                            return;
                        }
                    }
                    keyguardSecurityContainerController.mViewMediatorCallback.playTrustedSound();
                }
            }
        };
        this.mLockPatternUtils = lockPatternUtils;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mSecurityModel = keyguardSecurityModel;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mSecurityViewFlipperController = keyguardSecurityViewFlipperController;
        this.mAdminSecondaryLockScreenController = new AdminSecondaryLockScreenController(factory.mContext, factory.mParent, factory.mUpdateMonitor, securityCallback, factory.mHandler, 0);
        this.mConfigurationController = configurationController;
        this.mLastOrientation = getResources().getConfiguration().orientation;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mFeatureFlags = featureFlags;
        this.mGlobalSettings = globalSettings;
        this.mSessionTracker = sessionTracker;
        this.mSideFpsController = optional;
        this.mFalsingA11yDelegate = falsingA11yDelegate;
        this.mTelephonyManager = telephonyManager;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mAudioManager = audioManager;
        this.mKeyguardFaceAuthInteractor = keyguardFaceAuthInteractor;
        this.mDeviceProvisionedController = deviceProvisionedController;
    }

    public void configureMode() {
        int i;
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        int i2 = 1;
        boolean z = securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
        if (!((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.BOUNCER_USER_SWITCHER) || z) {
            KeyguardSecurityModel.SecurityMode securityMode2 = this.mCurrentSecurityMode;
            if (!((securityMode2 == KeyguardSecurityModel.SecurityMode.Pattern || securityMode2 == KeyguardSecurityModel.SecurityMode.PIN) ? getResources().getBoolean(R.bool.can_use_one_handed_bouncer) : false)) {
                i = 0;
                ((KeyguardSecurityContainer) this.mView).initMode(i, this.mGlobalSettings, this.mFalsingManager, this.mUserSwitcherController, new KeyguardSecurityContainerController$$ExternalSyntheticLambda0(this), this.mFalsingA11yDelegate);
            }
        } else {
            i2 = 2;
        }
        i = i2;
        ((KeyguardSecurityContainer) this.mView).initMode(i, this.mGlobalSettings, this.mFalsingManager, this.mUserSwitcherController, new KeyguardSecurityContainerController$$ExternalSyntheticLambda0(this), this.mFalsingA11yDelegate);
    }

    public final void getCurrentSecurityController(KeyguardSecurityViewFlipperController.OnViewInflatedCallback onViewInflatedCallback) {
        this.mSecurityViewFlipperController.getSecurityView(this.mCurrentSecurityMode, this.mKeyguardSecurityCallback, onViewInflatedCallback);
    }

    public KeyguardSecurityCallback getSecurityCallback() {
        return new KeyguardSecurityCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController.2
            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void dismiss(int i, KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
                dismiss(z, i, false, securityMode);
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void finish(int i, boolean z) {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                if (!((KeyguardStateControllerImpl) keyguardSecurityContainerController.mKeyguardStateController).mCanDismissLockScreen && !z) {
                    Log.e("KeyguardSecurityContainer", "Tried to dismiss keyguard when lockscreen is not dismissible and user was not authenticated with a primary security method (pin/password/pattern).");
                    return;
                }
                boolean z2 = false;
                keyguardSecurityContainerController.mWillRunDismissFromKeyguard = false;
                ActivityStarter.OnDismissAction onDismissAction = keyguardSecurityContainerController.mDismissAction;
                if (onDismissAction != null) {
                    z2 = onDismissAction.onDismiss();
                    keyguardSecurityContainerController.mWillRunDismissFromKeyguard = keyguardSecurityContainerController.mDismissAction.willRunAnimationOnKeyguard();
                    keyguardSecurityContainerController.mDismissAction = null;
                    keyguardSecurityContainerController.mCancelAction = null;
                }
                ViewMediatorCallback viewMediatorCallback = keyguardSecurityContainerController.mViewMediatorCallback;
                if (viewMediatorCallback != null) {
                    if (z2) {
                        viewMediatorCallback.keyguardDonePending(i);
                    } else {
                        viewMediatorCallback.keyguardDone(i);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void onCancelClicked() {
                KeyguardSecurityContainerController.this.mViewMediatorCallback.onCancelClicked();
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void onSecurityModeChanged(boolean z) {
                KeyguardSecurityContainerController.this.mViewMediatorCallback.setNeedsInput(z);
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void onUserInput() {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                ((DeviceEntryFaceAuthRepositoryImpl) ((SystemUIKeyguardFaceAuthInteractor) keyguardSecurityContainerController.mKeyguardFaceAuthInteractor).repository).cancel();
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardSecurityContainerController.mUpdateMonitor;
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void reportUnlockAttempt(int i, int i2, boolean z) {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                KeyguardSecurityContainer.ViewMode viewMode = ((KeyguardSecurityContainer) keyguardSecurityContainerController.mView).mViewMode;
                int i3 = 0;
                if (viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) {
                    if ((viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) && ((KeyguardSecurityContainer.SidedSecurityMode) viewMode).isLeftAligned()) {
                        i3 = 1;
                    }
                    i3 = i3 != 0 ? 1 : 2;
                }
                if (z) {
                    SysUiStatsLog.write(64, 2, i3);
                    keyguardSecurityContainerController.mLockPatternUtils.reportSuccessfulPasswordAttempt(i);
                    ThreadUtils.postOnBackgroundThread(new KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0());
                } else {
                    SysUiStatsLog.write(64, 1, i3);
                    keyguardSecurityContainerController.reportFailedUnlockAttempt(i, i2);
                }
                keyguardSecurityContainerController.mMetricsLogger.write(new LogMaker(197).setType(z ? 10 : 11));
                keyguardSecurityContainerController.mUiEventLogger.log(z ? KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_PASSWORD_SUCCESS : KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_PASSWORD_FAILURE, keyguardSecurityContainerController.mSessionTracker.getSessionId(1));
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void reset() {
                KeyguardSecurityContainerController.this.mViewMediatorCallback.resetKeyguard();
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void userActivity() {
                KeyguardSecurityContainerController.this.mViewMediatorCallback.userActivity();
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final boolean dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
                return KeyguardSecurityContainerController.this.showNextSecurityScreenOrFinish(z, i, z2, securityMode);
            }
        };
    }

    public final CharSequence getTitle() {
        KeyguardInputView securityView = ((KeyguardSecurityContainer) this.mView).mSecurityViewFlipper.getSecurityView();
        return securityView != null ? securityView.getTitle() : "";
    }

    public final boolean interceptMediaKey(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        int action = keyEvent.getAction();
        AudioManager audioManager = this.mAudioManager;
        if (action == 0) {
            if (keyCode != 79 && keyCode != 130 && keyCode != 222) {
                if (keyCode != 126 && keyCode != 127) {
                    switch (keyCode) {
                    }
                }
                TelephonyManager telephonyManager = this.mTelephonyManager;
                return (telephonyManager == null || telephonyManager.getCallState() == 0) ? false : true;
            }
            audioManager.dispatchMediaKeyEvent(keyEvent);
            return true;
        }
        if (keyEvent.getAction() == 1) {
            if (keyCode != 79 && keyCode != 130 && keyCode != 222 && keyCode != 126 && keyCode != 127) {
                switch (keyCode) {
                }
            }
            audioManager.dispatchMediaKeyEvent(keyEvent);
            return true;
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return false;
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        this.mSecurityViewFlipperController.init();
        updateResources();
        configureMode();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final void onStartingToHide() {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda4(2));
        }
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        this.mUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((KeyguardSecurityContainer) this.mView).getClass();
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        ((ArrayList) keyguardSecurityContainer.mMotionEventListeners).add(this.mGlobalTouchListener);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mUserSwitcherController.addUserSwitchCallback(this.mUserSwitchCallback);
        KeyguardSecurityContainer keyguardSecurityContainer2 = (KeyguardSecurityContainer) this.mView;
        ViewMediatorCallback viewMediatorCallback = this.mViewMediatorCallback;
        keyguardSecurityContainer2.mViewMediatorCallback = viewMediatorCallback;
        viewMediatorCallback.setNeedsInput(false);
        ((KeyguardSecurityContainer) this.mView).setOnKeyListener(this.mOnKeyListener);
        showPrimarySecurityScreen();
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        ((ArrayList) keyguardSecurityContainer.mMotionEventListeners).remove(this.mGlobalTouchListener);
        this.mUserSwitcherController.removeUserSwitchCallback(this.mUserSwitchCallback);
    }

    public void reportFailedUnlockAttempt(int i, int i2) {
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i3 = 1;
        int currentFailedPasswordAttempts = lockPatternUtils.getCurrentFailedPasswordAttempts(i) + 1;
        Log.d("KeyguardSecurityContainer", "reportFailedPatternAttempt: #" + currentFailedPasswordAttempts);
        DevicePolicyManager devicePolicyManager = lockPatternUtils.getDevicePolicyManager();
        int maximumFailedPasswordsForWipe = devicePolicyManager.getMaximumFailedPasswordsForWipe(null, i);
        int i4 = maximumFailedPasswordsForWipe > 0 ? maximumFailedPasswordsForWipe - currentFailedPasswordAttempts : Integer.MAX_VALUE;
        if (i4 < 5) {
            int profileWithMinimumFailedPasswordsForWipe = devicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(i);
            if (profileWithMinimumFailedPasswordsForWipe == i) {
                if (profileWithMinimumFailedPasswordsForWipe != 0) {
                    i3 = 3;
                }
            } else if (profileWithMinimumFailedPasswordsForWipe != -10000) {
                i3 = 2;
            }
            if (i4 > 0) {
                ((KeyguardSecurityContainer) this.mView).showAlmostAtWipeDialog(currentFailedPasswordAttempts, i4, i3);
            } else {
                Slog.i("KeyguardSecurityContainer", "Too many unlock attempts; user " + profileWithMinimumFailedPasswordsForWipe + " will be wiped!");
                ((KeyguardSecurityContainer) this.mView).showWipeDialog(currentFailedPasswordAttempts, i3);
            }
        }
        lockPatternUtils.reportFailedPasswordAttempt(i);
        if (i2 > 0) {
            lockPatternUtils.reportPasswordLockout(i2, i);
            ((KeyguardSecurityContainer) this.mView).showTimeoutDialog(i, i2, lockPatternUtils, this.mSecurityModel.getSecurityMode(i));
        }
    }

    public final void reset() {
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        keyguardSecurityContainer.mViewMode.reset();
        keyguardSecurityContainer.mDisappearAnimRunning = false;
        KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = this.mSecurityViewFlipperController;
        Iterator it = ((ArrayList) keyguardSecurityViewFlipperController.mChildren).iterator();
        while (it.hasNext()) {
            KeyguardInputViewController keyguardInputViewController = (KeyguardInputViewController) it.next();
            if (((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).indexOfChild(keyguardInputViewController.mView) == ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).getDisplayedChild()) {
                keyguardInputViewController.reset();
            }
        }
    }

    public void showMessage(final CharSequence charSequence, final ColorStateList colorStateList, final boolean z) {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda7
                @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
                public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                    keyguardInputViewController.showMessage(charSequence, colorStateList, z);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0157  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean showNextSecurityScreenOrFinish(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
        int i2;
        KeyguardSecurityContainer.BouncerUiEvent bouncerUiEvent;
        boolean z3;
        boolean z4;
        Intent intent;
        Log.d("KeyguardSecurityContainer", "showNextSecurityScreenOrFinish(" + z + ")");
        if (securityMode != KeyguardSecurityModel.SecurityMode.Invalid && securityMode != this.mCurrentSecurityMode) {
            Log.w("KeyguardSecurityContainer", "Attempted to invoke showNextSecurityScreenOrFinish with securityMode " + securityMode + ", but current mode is " + this.mCurrentSecurityMode);
            return false;
        }
        KeyguardSecurityContainer.BouncerUiEvent bouncerUiEvent2 = KeyguardSecurityContainer.BouncerUiEvent.UNKNOWN;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (keyguardUpdateMonitor.getUserHasTrust(i)) {
            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_EXTENDED_ACCESS;
            i2 = 3;
        } else {
            i2 = 2;
            if (!keyguardUpdateMonitor.getUserUnlockedWithBiometric(i)) {
                KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.None;
                KeyguardSecurityModel.SecurityMode securityMode3 = this.mCurrentSecurityMode;
                KeyguardSecurityModel keyguardSecurityModel = this.mSecurityModel;
                if (securityMode2 == securityMode3) {
                    KeyguardSecurityModel.SecurityMode securityMode4 = keyguardSecurityModel.getSecurityMode(i);
                    if (securityMode2 == securityMode4) {
                        bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_NONE_SECURITY;
                        i2 = 0;
                        z3 = true;
                    } else {
                        showSecurityScreen(securityMode4);
                        bouncerUiEvent = bouncerUiEvent2;
                        z3 = false;
                        i2 = -1;
                    }
                    z4 = false;
                } else {
                    if (z) {
                        int i3 = AbstractC07677.f209xdc0e830a[securityMode3.ordinal()];
                        if (i3 == 1 || i3 == 2 || i3 == 3) {
                            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_PASSWORD;
                            z3 = true;
                            i2 = 1;
                            z4 = true;
                        } else if (i3 == 4 || i3 == 5) {
                            KeyguardSecurityModel.SecurityMode securityMode5 = keyguardSecurityModel.getSecurityMode(i);
                            boolean z5 = this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser()) || !((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isUserSetup(i);
                            if (securityMode5 == securityMode2 && z5) {
                                bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_SIM;
                                i2 = 4;
                            } else {
                                showSecurityScreen(securityMode5);
                            }
                        } else {
                            Objects.toString(this.mCurrentSecurityMode);
                            showPrimarySecurityScreen();
                        }
                    }
                    bouncerUiEvent = bouncerUiEvent2;
                    z3 = false;
                    z4 = false;
                    i2 = -1;
                }
                if (z3 && !z2) {
                    intent = (Intent) ((HashMap) keyguardUpdateMonitor.mSecondaryLockscreenRequirement).get(Integer.valueOf(i));
                    if (intent != null) {
                        AdminSecondaryLockScreenController adminSecondaryLockScreenController = this.mAdminSecondaryLockScreenController;
                        if (adminSecondaryLockScreenController.mClient == null) {
                            adminSecondaryLockScreenController.mContext.bindService(intent, adminSecondaryLockScreenController.mConnection, 1);
                        }
                        AdminSecondaryLockScreenController.AdminSecurityView adminSecurityView = adminSecondaryLockScreenController.mView;
                        if (!adminSecurityView.isAttachedToWindow()) {
                            KeyguardSecurityContainer keyguardSecurityContainer = adminSecondaryLockScreenController.mParent;
                            keyguardSecurityContainer.addView(adminSecurityView);
                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(keyguardSecurityContainer);
                            constraintSet.connect(adminSecurityView.getId(), 3, 0, 3);
                            constraintSet.connect(adminSecurityView.getId(), 6, 0, 6);
                            constraintSet.connect(adminSecurityView.getId(), 7, 0, 7);
                            constraintSet.connect(adminSecurityView.getId(), 4, 0, 4);
                            constraintSet.constrainHeight(adminSecurityView.getId(), 0);
                            constraintSet.constrainWidth(adminSecurityView.getId(), 0);
                            constraintSet.applyTo(keyguardSecurityContainer);
                        }
                        return false;
                    }
                }
                if (i2 != -1) {
                    this.mMetricsLogger.write(new LogMaker(197).setType(5).setSubtype(i2));
                }
                if (bouncerUiEvent != bouncerUiEvent2) {
                    this.mUiEventLogger.log(bouncerUiEvent, this.mSessionTracker.getSessionId(1));
                }
                if (z3) {
                    this.mKeyguardSecurityCallback.finish(i, z4);
                }
                return z3;
            }
            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_BIOMETRIC;
        }
        z4 = false;
        z3 = true;
        if (z3) {
            intent = (Intent) ((HashMap) keyguardUpdateMonitor.mSecondaryLockscreenRequirement).get(Integer.valueOf(i));
            if (intent != null) {
            }
        }
        if (i2 != -1) {
        }
        if (bouncerUiEvent != bouncerUiEvent2) {
        }
        if (z3) {
        }
        return z3;
    }

    public final void showPrimarySecurityScreen() {
        Log.d("KeyguardSecurityContainer", "show()");
        showSecurityScreen((KeyguardSecurityModel.SecurityMode) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardSecurityContainerController.this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
            }
        }));
    }

    public void showSecurityScreen(final KeyguardSecurityModel.SecurityMode securityMode) {
        Log.d("KeyguardSecurityContainer", "showSecurityScreen(" + securityMode + ")");
        if (securityMode == KeyguardSecurityModel.SecurityMode.Invalid || securityMode == this.mCurrentSecurityMode) {
            return;
        }
        getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda4(0));
        this.mCurrentSecurityMode = securityMode;
        getCurrentSecurityController(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda5
            @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
            public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                keyguardSecurityContainerController.getClass();
                keyguardInputViewController.onResume(2);
                KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = keyguardSecurityContainerController.mSecurityViewFlipperController;
                int indexOfChild = ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).indexOfChild(keyguardInputViewController.mView);
                if (indexOfChild != -1) {
                    ((KeyguardSecurityViewFlipper) keyguardSecurityViewFlipperController.mView).setDisplayedChild(indexOfChild);
                }
                keyguardSecurityContainerController.configureMode();
                keyguardSecurityContainerController.mKeyguardSecurityCallback.onSecurityModeChanged(keyguardInputViewController.needsInput());
            }
        });
    }

    public void startAppearAnimation() {
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        if (securityMode != KeyguardSecurityModel.SecurityMode.None) {
            KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
            keyguardSecurityContainer.setTranslationY(0.0f);
            keyguardSecurityContainer.updateChildren(1.0f, 0);
            keyguardSecurityContainer.mViewMode.startAppearAnimation(securityMode);
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda4(1));
        }
    }

    public final void updateResources() {
        Resources resources = ((KeyguardSecurityContainer) this.mView).getResources();
        int integer = resources.getBoolean(R.bool.can_use_one_handed_bouncer) ? resources.getInteger(R.integer.keyguard_host_view_one_handed_gravity) : resources.getInteger(R.integer.keyguard_host_view_gravity);
        this.mTranslationY = resources.getDimensionPixelSize(R.dimen.keyguard_host_view_translation_y);
        if (((KeyguardSecurityContainer) this.mView).getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ((KeyguardSecurityContainer) this.mView).getLayoutParams();
            if (layoutParams.gravity != integer) {
                layoutParams.gravity = integer;
                ((KeyguardSecurityContainer) this.mView).setLayoutParams(layoutParams);
            }
        }
        int i = getResources().getConfiguration().orientation;
        if (i != this.mLastOrientation) {
            this.mLastOrientation = i;
            configureMode();
        }
    }
}
