package com.android.keyguard;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.metrics.LogMaker;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.window.OnBackAnimationCallback;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
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
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.biometrics.FaceAuthAccessibilityDelegate;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepositoryImpl;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractorKt;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.bouncer.shared.model.Message;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.math.MathKt__MathJVMKt;

public class KeyguardSecurityContainerController extends ViewController implements KeyguardSecurityView {
    public final AdminSecondaryLockScreenController mAdminSecondaryLockScreenController;
    public final AudioManager mAudioManager;
    public final BouncerMessageInteractor mBouncerMessageInteractor;
    public Runnable mCancelAction;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass5 mConfigurationListener;
    public KeyguardSecurityModel.SecurityMode mCurrentSecurityMode;
    public int mCurrentUser;
    public final DeviceEntryFaceAuthInteractor mDeviceEntryFaceAuthInteractor;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public ActivityStarter.OnDismissAction mDismissAction;
    public final FalsingA11yDelegate mFalsingA11yDelegate;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final GlobalSettings mGlobalSettings;
    final Gefingerpoken mGlobalTouchListener;
    public final KeyguardSecurityCallback mKeyguardSecurityCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public int mLastOrientation;
    public final LockPatternUtils mLockPatternUtils;
    public final MetricsLogger mMetricsLogger;
    public final KeyguardSecurityContainerController$$ExternalSyntheticLambda0 mOnKeyListener;
    public final Lazy mPrimaryBouncerInteractor;
    public final KeyguardSecurityModel mSecurityModel;
    public final KeyguardSecurityViewFlipperController mSecurityViewFlipperController;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SessionTracker mSessionTracker;
    public final TelephonyManager mTelephonyManager;
    public int mTranslationY;
    public final UiEventLogger mUiEventLogger;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final AnonymousClass1 mUserSwitchCallback;
    public final UserSwitcherController mUserSwitcherController;
    public final ViewMediatorCallback mViewMediatorCallback;
    public boolean mWillRunDismissFromKeyguard;

    /* renamed from: com.android.keyguard.KeyguardSecurityContainerController$8, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass8 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardSecurityContainerController$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.KeyguardSecurityContainerController$5] */
    public KeyguardSecurityContainerController(KeyguardSecurityContainer keyguardSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, FalsingA11yDelegate falsingA11yDelegate, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, AudioManager audioManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, BouncerMessageInteractor bouncerMessageInteractor, Provider provider, SelectedUserInteractor selectedUserInteractor, DeviceProvisionedController deviceProvisionedController, FaceAuthAccessibilityDelegate faceAuthAccessibilityDelegate, DevicePolicyManager devicePolicyManager, KeyguardTransitionInteractor keyguardTransitionInteractor, Lazy lazy, Provider provider2) {
        super(keyguardSecurityContainer);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return KeyguardSecurityContainerController.this.interceptMediaKey(keyEvent);
            }
        };
        this.mCurrentSecurityMode = KeyguardSecurityModel.SecurityMode.Invalid;
        this.mCurrentUser = -10000;
        this.mUserSwitchCallback = new UserSwitcherController.UserSwitchCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController.1
            @Override // com.android.systemui.statusbar.policy.UserSwitcherController.UserSwitchCallback
            public final void onUserSwitched() {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                int i = keyguardSecurityContainerController.mCurrentUser;
                SelectedUserInteractor selectedUserInteractor2 = keyguardSecurityContainerController.mSelectedUserInteractor;
                if (i == selectedUserInteractor2.getSelectedUserId(false)) {
                    return;
                }
                keyguardSecurityContainerController.mCurrentUser = selectedUserInteractor2.getSelectedUserId(false);
                keyguardSecurityContainerController.showPrimarySecurityScreen();
                KeyguardSecurityModel.SecurityMode securityMode = keyguardSecurityContainerController.mCurrentSecurityMode;
                if (securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk) {
                    return;
                }
                keyguardSecurityContainerController.reinflateViewFlipper(new KeyguardSecurityContainerController$$ExternalSyntheticLambda1(4));
            }
        };
        this.mGlobalTouchListener = new Gefingerpoken() { // from class: com.android.keyguard.KeyguardSecurityContainerController.2
            public MotionEvent mTouchDown;

            @Override // com.android.systemui.Gefingerpoken
            public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // com.android.systemui.Gefingerpoken
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() != 0) {
                    if (this.mTouchDown == null) {
                        return false;
                    }
                    if (motionEvent.getActionMasked() != 1 && motionEvent.getActionMasked() != 3) {
                        return false;
                    }
                    this.mTouchDown.recycle();
                    this.mTouchDown = null;
                    return false;
                }
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                KeyguardSecurityContainer.ViewMode viewMode = ((KeyguardSecurityContainer) ((ViewController) keyguardSecurityContainerController).mView).mViewMode;
                if (viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) {
                    KeyguardSecurityContainer.SidedSecurityMode sidedSecurityMode = (KeyguardSecurityContainer.SidedSecurityMode) viewMode;
                    if (sidedSecurityMode.isTouchOnTheOtherSideOfSecurity(motionEvent, sidedSecurityMode.isLeftAligned())) {
                        keyguardSecurityContainerController.mFalsingCollector.avoidGesture();
                    }
                }
                MotionEvent motionEvent2 = this.mTouchDown;
                if (motionEvent2 != null) {
                    motionEvent2.recycle();
                    this.mTouchDown = null;
                }
                this.mTouchDown = MotionEvent.obtain(motionEvent);
                return false;
            }
        };
        KeyguardSecurityCallback securityCallback = getSecurityCallback();
        this.mKeyguardSecurityCallback = securityCallback;
        new Object(this) { // from class: com.android.keyguard.KeyguardSecurityContainerController.4
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController.5
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardSecurityContainerController.this.configureMode();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                KeyguardSecurityContainerController.this.getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onOrientationChanged(int i) {
                FeatureFlags featureFlags2 = KeyguardSecurityContainerController.this.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags2.getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                KeyguardSecurityContainerController.this.getClass();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                KeyguardSecurityContainerController.this.getClass();
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController.6
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDevicePolicyManagerStateChanged() {
                KeyguardSecurityContainerController.this.showPrimarySecurityScreen();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                if (z) {
                    if (!((KeyguardSecurityContainer) ((ViewController) keyguardSecurityContainerController).mView).isVisibleToUser()) {
                        Log.i("KeyguardSecurityContainer", "TrustAgent dismissed Keyguard.");
                    }
                    keyguardSecurityContainerController.mKeyguardSecurityCallback.dismiss(false, keyguardSecurityContainerController.mSelectedUserInteractor.getSelectedUserId(false), false, KeyguardSecurityModel.SecurityMode.Invalid);
                } else {
                    if ((trustGrantFlags.mFlags & 1) == 0 && !trustGrantFlags.dismissKeyguardRequested()) {
                        return;
                    }
                    keyguardSecurityContainerController.mViewMediatorCallback.playTrustedSound();
                }
            }
        };
        keyguardSecurityContainer.setAccessibilityDelegate(faceAuthAccessibilityDelegate);
        this.mLockPatternUtils = lockPatternUtils;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mSecurityModel = keyguardSecurityModel;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mSecurityViewFlipperController = keyguardSecurityViewFlipperController;
        this.mAdminSecondaryLockScreenController = new AdminSecondaryLockScreenController(factory.mContext, factory.mParent, factory.mUpdateMonitor, securityCallback, factory.mHandler, factory.mSelectedUserInteractor, 0);
        this.mConfigurationController = configurationController;
        this.mLastOrientation = getResources().getConfiguration().orientation;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mFeatureFlags = featureFlags;
        this.mGlobalSettings = globalSettings;
        this.mSessionTracker = sessionTracker;
        this.mFalsingA11yDelegate = falsingA11yDelegate;
        this.mTelephonyManager = telephonyManager;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mAudioManager = audioManager;
        this.mDeviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.mBouncerMessageInteractor = bouncerMessageInteractor;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mPrimaryBouncerInteractor = lazy;
        this.mDevicePolicyManager = devicePolicyManager;
    }

    public final void appear() {
        ((KeyguardSecurityContainer) this.mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController.7
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                ((KeyguardSecurityContainer) ((ViewController) KeyguardSecurityContainerController.this).mView).getViewTreeObserver().removeOnPreDrawListener(this);
                KeyguardSecurityContainerController.this.startAppearAnimation();
                return true;
            }
        });
        ((KeyguardSecurityContainer) this.mView).requestLayout();
    }

    public void configureMode() {
        int i;
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        int i2 = 1;
        boolean z = securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
        if (!getContext().getResources().getBoolean(R.bool.config_enableBouncerUserSwitcher) || z) {
            int i3 = AnonymousClass8.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[this.mCurrentSecurityMode.ordinal()];
            if (!((i3 == 1 || i3 == 3 || i3 == 4 || i3 == 5) ? getResources().getBoolean(R.bool.can_use_one_handed_bouncer) : false)) {
                i = 0;
                ((KeyguardSecurityContainer) this.mView).initMode(i, this.mGlobalSettings, this.mFalsingManager, this.mUserSwitcherController, new KeyguardSecurityContainerController$$ExternalSyntheticLambda3(this), this.mFalsingA11yDelegate);
            }
        } else {
            i2 = 2;
        }
        i = i2;
        ((KeyguardSecurityContainer) this.mView).initMode(i, this.mGlobalSettings, this.mFalsingManager, this.mUserSwitcherController, new KeyguardSecurityContainerController$$ExternalSyntheticLambda3(this), this.mFalsingA11yDelegate);
    }

    public final OnBackAnimationCallback getBackCallback() {
        return ((KeyguardSecurityContainer) this.mView).mBackCallback;
    }

    public final void getCurrentSecurityController(KeyguardSecurityViewFlipperController.OnViewInflatedCallback onViewInflatedCallback) {
        this.mSecurityViewFlipperController.getSecurityView(this.mCurrentSecurityMode, this.mKeyguardSecurityCallback, onViewInflatedCallback);
    }

    public KeyguardSecurityCallback getSecurityCallback() {
        return new KeyguardSecurityCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController.3
            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void dismiss(boolean z, int i, KeyguardSecurityModel.SecurityMode securityMode) {
                dismiss(z, i, false, securityMode);
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void finish(int i) {
                int i2 = SceneContainerFlag.$r8$clinit;
                com.android.systemui.Flags.sceneContainer();
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                boolean z = false;
                keyguardSecurityContainerController.mWillRunDismissFromKeyguard = false;
                ActivityStarter.OnDismissAction onDismissAction = keyguardSecurityContainerController.mDismissAction;
                if (onDismissAction != null) {
                    z = onDismissAction.onDismiss();
                    keyguardSecurityContainerController.mWillRunDismissFromKeyguard = keyguardSecurityContainerController.mDismissAction.willRunAnimationOnKeyguard();
                    keyguardSecurityContainerController.mDismissAction = null;
                    keyguardSecurityContainerController.mCancelAction = null;
                }
                ViewMediatorCallback viewMediatorCallback = keyguardSecurityContainerController.mViewMediatorCallback;
                if (viewMediatorCallback != null) {
                    if (z) {
                        viewMediatorCallback.keyguardDonePending(i);
                    } else {
                        viewMediatorCallback.keyguardDone(i);
                    }
                }
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                com.android.systemui.Flags.keyguardWmStateRefactor();
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void onAttemptLockoutStart(long j) {
                BouncerMessageInteractor bouncerMessageInteractor = KeyguardSecurityContainerController.this.mBouncerMessageInteractor;
                bouncerMessageInteractor.getClass();
                final BouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1 bouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1 = new BouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1(bouncerMessageInteractor);
                final long j2 = j * 1000;
                bouncerMessageInteractor.countDownTimerUtil.getClass();
                final long j3 = 1000;
                new CountDownTimer(j2, j3) { // from class: com.android.systemui.bouncer.domain.interactor.CountDownTimerUtil$startNewTimer$1
                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        BouncerMessageInteractor bouncerMessageInteractor2 = ((BouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1) bouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1).this$0;
                        ((BouncerMessageRepositoryImpl) bouncerMessageInteractor2.repository).setMessage(bouncerMessageInteractor2.getDefaultMessage());
                    }

                    @Override // android.os.CountDownTimer
                    public final void onTick(long j4) {
                        BouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1 bouncerMessageInteractor$onPrimaryAuthLockedOut$callback$12 = (BouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1) bouncerMessageInteractor$onPrimaryAuthLockedOut$callback$1;
                        bouncerMessageInteractor$onPrimaryAuthLockedOut$callback$12.getClass();
                        int roundToInt = MathKt__MathJVMKt.roundToInt(j4 / 1000.0d);
                        BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
                        BouncerMessageInteractor bouncerMessageInteractor2 = bouncerMessageInteractor$onPrimaryAuthLockedOut$callback$12.this$0;
                        AuthenticationMethodModel authModel = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor2.getCurrentSecurityMode());
                        bouncerMessageStrings.getClass();
                        BouncerMessageModel message = BouncerMessageInteractorKt.toMessage(authModel.equals(AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_too_many_failed_attempts_countdown), Integer.valueOf(R.string.kg_primary_auth_locked_out_pattern)) : authModel.equals(AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_too_many_failed_attempts_countdown), Integer.valueOf(R.string.kg_primary_auth_locked_out_password)) : authModel.equals(AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_too_many_failed_attempts_countdown), Integer.valueOf(R.string.kg_primary_auth_locked_out_pin)) : BouncerMessageStrings.EmptyMessage);
                        Message message2 = message.message;
                        if (message2 != null) {
                            message2.animate = false;
                        }
                        if (message2 != null) {
                            message2.formatterArgs = MapsKt__MapsKt.mutableMapOf(new Pair(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(roundToInt)));
                        }
                        ((BouncerMessageRepositoryImpl) bouncerMessageInteractor2.repository).setMessage(message);
                    }
                }.start();
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
                BouncerMessageInteractor bouncerMessageInteractor = keyguardSecurityContainerController.mBouncerMessageInteractor;
                ((BouncerMessageRepositoryImpl) bouncerMessageInteractor.repository).setMessage(bouncerMessageInteractor.getDefaultMessage());
                keyguardSecurityContainerController.mDeviceEntryFaceAuthInteractor.onPrimaryBouncerUserInput();
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public final void reportUnlockAttempt(int i, int i2, boolean z) {
                int i3;
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                if (i2 == 0 && !z) {
                    BouncerMessageInteractor bouncerMessageInteractor = keyguardSecurityContainerController.mBouncerMessageInteractor;
                    bouncerMessageInteractor.getClass();
                    BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
                    AuthenticationMethodModel authModel = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                    boolean booleanValue = ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
                    bouncerMessageStrings.getClass();
                    int i4 = booleanValue ? R.string.kg_wrong_input_try_fp_suggestion : 0;
                    ((BouncerMessageRepositoryImpl) bouncerMessageInteractor.repository).setMessage(BouncerMessageInteractorKt.toMessage(authModel.equals(AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_wrong_pattern_try_again), Integer.valueOf(i4)) : authModel.equals(AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_wrong_password_try_again), Integer.valueOf(i4)) : authModel.equals(AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_wrong_pin_try_again), Integer.valueOf(i4)) : BouncerMessageStrings.EmptyMessage));
                }
                if (((KeyguardSecurityContainer) ((ViewController) keyguardSecurityContainerController).mView).mViewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) {
                    KeyguardSecurityContainer.ViewMode viewMode = ((KeyguardSecurityContainer) ((ViewController) keyguardSecurityContainerController).mView).mViewMode;
                    i3 = ((viewMode instanceof KeyguardSecurityContainer.SidedSecurityMode) && ((KeyguardSecurityContainer.SidedSecurityMode) viewMode).isLeftAligned()) ? 1 : 2;
                } else {
                    i3 = 0;
                }
                if (z) {
                    SysUiStatsLog.write(64, 2, i3);
                    keyguardSecurityContainerController.mLockPatternUtils.reportSuccessfulPasswordAttempt(i);
                    ThreadUtils.postOnBackgroundThread(new KeyguardSecurityContainerController$3$$ExternalSyntheticLambda0());
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
            public final void showCurrentSecurityScreen() {
                KeyguardSecurityContainerController.this.showPrimarySecurityScreen();
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
        if (keyEvent.getAction() == 0) {
            if (keyCode != 79 && keyCode != 130 && keyCode != 222) {
                if (keyCode != 126 && keyCode != 127) {
                    switch (keyCode) {
                    }
                }
                TelephonyManager telephonyManager = this.mTelephonyManager;
                return (telephonyManager == null || telephonyManager.getCallState() == 0) ? false : true;
            }
            this.mAudioManager.dispatchMediaKeyEvent(keyEvent);
            return true;
        }
        if (keyEvent.getAction() == 1) {
            if (keyCode != 79 && keyCode != 130 && keyCode != 222 && keyCode != 126 && keyCode != 127) {
                switch (keyCode) {
                }
            }
            this.mAudioManager.dispatchMediaKeyEvent(keyEvent);
            return true;
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return false;
    }

    public final void onBouncerVisibilityChanged() {
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        keyguardSecurityContainer.setScaleX(1.0f);
        keyguardSecurityContainer.setScaleY(1.0f);
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        this.mSecurityViewFlipperController.init();
        updateResources$1();
        configureMode();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final void onStartingToHide() {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda1(2));
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
        int i = SceneContainerFlag.$r8$clinit;
        com.android.systemui.Flags.sceneContainer();
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        ((ArrayList) keyguardSecurityContainer.mMotionEventListeners).remove(this.mGlobalTouchListener);
        AnonymousClass1 anonymousClass1 = this.mUserSwitchCallback;
        UserSwitcherController userSwitcherController = this.mUserSwitcherController;
        UserSwitcherInteractor.UserCallback userCallback = (UserSwitcherInteractor.UserCallback) userSwitcherController.callbackCompatMap.remove(anonymousClass1);
        if (userCallback != null) {
            userSwitcherController.getMUserSwitcherInteractor().removeCallback(userCallback);
        }
    }

    public final void prepareToShow() {
        View findViewById = ((KeyguardSecurityContainer) this.mView).findViewById(R.id.keyguard_bouncer_user_switcher);
        if (findViewById != null) {
            findViewById.setAlpha(0.0f);
        }
    }

    public void reinflateViewFlipper(KeyguardSecurityViewFlipperController.OnViewInflatedCallback onViewInflatedCallback) {
        KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController = this.mSecurityViewFlipperController;
        keyguardSecurityViewFlipperController.clearViews();
        keyguardSecurityViewFlipperController.asynchronouslyInflateView(this.mCurrentSecurityMode, this.mKeyguardSecurityCallback, onViewInflatedCallback);
    }

    public void reportFailedUnlockAttempt(int i, int i2) {
        int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(i) + 1;
        ListPopupWindow$$ExternalSyntheticOutline0.m(currentFailedPasswordAttempts, "reportFailedPatternAttempt: #", "KeyguardSecurityContainer");
        int maximumFailedPasswordsForWipe = this.mDevicePolicyManager.getMaximumFailedPasswordsForWipe(null, i);
        int i3 = maximumFailedPasswordsForWipe > 0 ? maximumFailedPasswordsForWipe - currentFailedPasswordAttempts : Integer.MAX_VALUE;
        if (i3 < 5) {
            showMessageForFailedUnlockAttempt(i, this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(i), Integer.valueOf(((UserRepositoryImpl) this.mSelectedUserInteractor.repository).mainUserId), i3, currentFailedPasswordAttempts);
        }
        this.mLockPatternUtils.reportFailedPasswordAttempt(i);
        if (i2 > 0) {
            this.mLockPatternUtils.reportPasswordLockout(i2, i);
        }
    }

    public final void reset$1() {
        KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
        keyguardSecurityContainer.mViewMode.reset();
        keyguardSecurityContainer.mDisappearAnimRunning = false;
        this.mSecurityViewFlipperController.reset$1();
    }

    public final void setAlpha(float f) {
        ((KeyguardSecurityContainer) this.mView).setAlpha(f);
    }

    public final void setExpansion(float f) {
        float showBouncerProgress = BouncerPanelExpansionCalculator.showBouncerProgress(f);
        setAlpha(MathUtils.constrain(1.0f - showBouncerProgress, 0.0f, 1.0f));
        ((KeyguardSecurityContainer) this.mView).setTranslationY(showBouncerProgress * this.mTranslationY);
    }

    public final void setInteractable() {
        ((KeyguardSecurityContainer) this.mView).getClass();
    }

    public void showMessage(final CharSequence charSequence, final ColorStateList colorStateList, final boolean z) {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda5
                @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
                public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                    keyguardInputViewController.showMessage(charSequence, colorStateList, z);
                }
            });
        }
    }

    public void showMessageForFailedUnlockAttempt(int i, int i2, Integer num, int i3, int i4) {
        int i5 = 1;
        if (i2 == i) {
            android.app.admin.flags.Flags.FEATURE_FLAGS.getClass();
            if (i2 != (num != null ? num.intValue() : 0)) {
                i5 = 3;
            }
        } else if (i2 != -10000) {
            i5 = 2;
        }
        if (i3 > 0) {
            ((KeyguardSecurityContainer) this.mView).showAlmostAtWipeDialog(i4, i3, i5);
            return;
        }
        Slog.i("KeyguardSecurityContainer", "Too many unlock attempts; user " + i2 + " will be wiped!");
        ((KeyguardSecurityContainer) this.mView).showWipeDialog(i4, i5);
    }

    public boolean showNextSecurityScreenOrFinish(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
        int i2;
        KeyguardSecurityContainer.BouncerUiEvent bouncerUiEvent;
        boolean z3;
        Log.d("KeyguardSecurityContainer", "showNextSecurityScreenOrFinish(" + z + ")");
        if (securityMode != KeyguardSecurityModel.SecurityMode.Invalid && securityMode != this.mCurrentSecurityMode) {
            Log.w("KeyguardSecurityContainer", "Attempted to invoke showNextSecurityScreenOrFinish with securityMode " + securityMode + ", but current mode is " + this.mCurrentSecurityMode);
            return false;
        }
        KeyguardSecurityContainer.BouncerUiEvent bouncerUiEvent2 = KeyguardSecurityContainer.BouncerUiEvent.UNKNOWN;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (keyguardUpdateMonitor.forceIsDismissibleIsKeepingDeviceUnlocked()) {
            bouncerUiEvent = bouncerUiEvent2;
            z3 = true;
            i2 = 5;
        } else if (keyguardUpdateMonitor.getUserHasTrust(i)) {
            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_EXTENDED_ACCESS;
            z3 = true;
            i2 = 3;
        } else {
            i2 = 2;
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(i)) {
                bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_BIOMETRIC;
            } else {
                KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.None;
                KeyguardSecurityModel.SecurityMode securityMode3 = this.mCurrentSecurityMode;
                KeyguardSecurityModel keyguardSecurityModel = this.mSecurityModel;
                if (securityMode2 == securityMode3) {
                    KeyguardSecurityModel.SecurityMode securityMode4 = keyguardSecurityModel.getSecurityMode(i);
                    if (securityMode2 == securityMode4) {
                        bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_NONE_SECURITY;
                        i2 = 0;
                    } else {
                        showSecurityScreen(securityMode4);
                    }
                } else if (z) {
                    int i3 = AnonymousClass8.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode3.ordinal()];
                    if (i3 == 1 || i3 == 2 || i3 == 3) {
                        bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_PASSWORD;
                        z3 = true;
                        i2 = 1;
                    } else if (i3 == 4 || i3 == 5) {
                        KeyguardSecurityModel.SecurityMode securityMode5 = keyguardSecurityModel.getSecurityMode(i);
                        boolean z4 = this.mLockPatternUtils.isLockScreenDisabled(this.mSelectedUserInteractor.getSelectedUserId(false)) || !((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isUserSetup(i);
                        if (securityMode5 == securityMode2 && z4) {
                            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_SIM;
                            z3 = true;
                            i2 = 4;
                        } else if (Arrays.asList(KeyguardSecurityModel.SecurityMode.SimPin, KeyguardSecurityModel.SecurityMode.SimPuk).contains(securityMode5)) {
                            showSecurityScreen(securityMode5);
                        }
                    } else {
                        Objects.toString(this.mCurrentSecurityMode);
                        showPrimarySecurityScreen();
                    }
                }
                bouncerUiEvent = bouncerUiEvent2;
                z3 = false;
                i2 = -1;
            }
            z3 = true;
        }
        if (z3 && !z2) {
            Intent intent = (Intent) ((HashMap) keyguardUpdateMonitor.mSecondaryLockscreenRequirement).get(Integer.valueOf(i));
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
        int i4 = SceneContainerFlag.$r8$clinit;
        com.android.systemui.Flags.sceneContainer();
        if (z3) {
            this.mKeyguardSecurityCallback.finish(i);
        }
        return z3;
    }

    public final void showPrimarySecurityScreen() {
        Log.d("KeyguardSecurityContainer", "show()");
        KeyguardSecurityModel.SecurityMode securityMode = (KeyguardSecurityModel.SecurityMode) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                return keyguardSecurityContainerController.mSecurityModel.getSecurityMode(keyguardSecurityContainerController.mSelectedUserInteractor.getSelectedUserId());
            }
        });
        ((KeyguardBouncerRepositoryImpl) ((PrimaryBouncerInteractor) this.mPrimaryBouncerInteractor.get()).repository)._lastShownSecurityMode.updateState(null, securityMode);
        showSecurityScreen(securityMode);
    }

    public void showSecurityScreen(final KeyguardSecurityModel.SecurityMode securityMode) {
        Log.d("KeyguardSecurityContainer", "showSecurityScreen(" + securityMode + ")");
        if (securityMode == KeyguardSecurityModel.SecurityMode.Invalid || securityMode == this.mCurrentSecurityMode) {
            return;
        }
        getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda1(0));
        this.mCurrentSecurityMode = securityMode;
        getCurrentSecurityController(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback(securityMode) { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda2
            public final /* synthetic */ KeyguardSecurityModel.SecurityMode f$1;

            @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
            public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                KeyguardSecurityContainerController keyguardSecurityContainerController = KeyguardSecurityContainerController.this;
                keyguardSecurityContainerController.getClass();
                keyguardInputViewController.onResume(2);
                keyguardSecurityContainerController.mSecurityViewFlipperController.show(keyguardInputViewController);
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
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda1(1));
        }
    }

    public boolean startDisappearAnimation(Runnable runnable) {
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        if (securityMode != KeyguardSecurityModel.SecurityMode.None) {
            KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) this.mView;
            keyguardSecurityContainer.mDisappearAnimRunning = true;
            if (securityMode == KeyguardSecurityModel.SecurityMode.Password && (keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView() instanceof KeyguardPasswordView)) {
                ((KeyguardPasswordView) keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView()).mDisappearAnimationListener = new KeyguardSecurityContainer$$ExternalSyntheticLambda2(keyguardSecurityContainer);
            } else {
                keyguardSecurityContainer.mViewMode.startDisappearAnimation(securityMode);
            }
            getCurrentSecurityController(new KeyguardSecurityContainerController$$ExternalSyntheticLambda3(runnable));
        }
        return true;
    }

    public final void updateKeyguardPosition(float f) {
        ((KeyguardSecurityContainer) this.mView).mViewMode.updatePositionByTouchX(f);
    }

    public final void updateResources$1() {
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
