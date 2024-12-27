package com.android.keyguard;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricSourceType;
import android.os.VibrationAttributes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.function.Consumer;

public class LegacyLockIconViewController extends ViewController implements Dumpable, LockIconViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LegacyLockIconViewController$$ExternalSyntheticLambda3 mA11yClickListener;
    public final AuthController mAuthController;
    public boolean mCanDismissLockScreen;
    public final ConfigurationController mConfigurationController;
    final Consumer<Float> mDozeTransitionCallback;
    public final DelayableExecutor mExecutor;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public float mInterpolatedDarkAmount;
    public boolean mIsActiveDreamLockscreenHosted;
    final Consumer<Boolean> mIsActiveDreamLockscreenHostedCallback;
    public boolean mIsBiometricToastViewAnimating;
    public boolean mIsBouncerShowing;
    public boolean mIsDozing;
    final Consumer<Boolean> mIsDozingCallback;
    public boolean mIsKeyguardShowing;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KeyguardViewController mKeyguardViewController;
    public CharSequence mLockedLabel;
    public final Resources mResources;
    public boolean mRunningFPS;
    public final Rect mSensorTouchLocation;
    public boolean mShowLockIcon;
    public boolean mShowUnlockIcon;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public CharSequence mUnlockedLabel;
    public final VibratorHelper mVibrator;

    static {
        int i = DisplayMetrics.DENSITY_DEVICE_STABLE;
        VibrationAttributes.createForUsage(18);
    }

    public LegacyLockIconViewController(LockIconView lockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper, AuthRippleController authRippleController, Resources resources, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, Context context, Lazy lazy, KeyguardEditModeController keyguardEditModeController) {
        super(lockIconView);
        this.mIsBiometricToastViewAnimating = false;
        this.mSensorTouchLocation = new Rect();
        final int i = 0;
        this.mDozeTransitionCallback = new Consumer(this) { // from class: com.android.keyguard.LegacyLockIconViewController$$ExternalSyntheticLambda0
            public final /* synthetic */ LegacyLockIconViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                LegacyLockIconViewController legacyLockIconViewController = this.f$0;
                switch (i2) {
                    case 0:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mInterpolatedDarkAmount = ((Float) obj).floatValue();
                        throw null;
                    case 1:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mIsDozing = ((Boolean) obj).booleanValue();
                        legacyLockIconViewController.updateVisibility$1();
                        return;
                    default:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mIsActiveDreamLockscreenHosted = ((Boolean) obj).booleanValue();
                        legacyLockIconViewController.updateVisibility$1();
                        return;
                }
            }
        };
        final int i2 = 1;
        this.mIsDozingCallback = new Consumer(this) { // from class: com.android.keyguard.LegacyLockIconViewController$$ExternalSyntheticLambda0
            public final /* synthetic */ LegacyLockIconViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                LegacyLockIconViewController legacyLockIconViewController = this.f$0;
                switch (i22) {
                    case 0:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mInterpolatedDarkAmount = ((Float) obj).floatValue();
                        throw null;
                    case 1:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mIsDozing = ((Boolean) obj).booleanValue();
                        legacyLockIconViewController.updateVisibility$1();
                        return;
                    default:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mIsActiveDreamLockscreenHosted = ((Boolean) obj).booleanValue();
                        legacyLockIconViewController.updateVisibility$1();
                        return;
                }
            }
        };
        final int i3 = 2;
        this.mIsActiveDreamLockscreenHostedCallback = new Consumer(this) { // from class: com.android.keyguard.LegacyLockIconViewController$$ExternalSyntheticLambda0
            public final /* synthetic */ LegacyLockIconViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i3;
                LegacyLockIconViewController legacyLockIconViewController = this.f$0;
                switch (i22) {
                    case 0:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mInterpolatedDarkAmount = ((Float) obj).floatValue();
                        throw null;
                    case 1:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mIsDozing = ((Boolean) obj).booleanValue();
                        legacyLockIconViewController.updateVisibility$1();
                        return;
                    default:
                        legacyLockIconViewController.getClass();
                        legacyLockIconViewController.mIsActiveDreamLockscreenHosted = ((Boolean) obj).booleanValue();
                        legacyLockIconViewController.updateVisibility$1();
                        return;
                }
            }
        };
        new StatusBarStateController.StateListener() { // from class: com.android.keyguard.LegacyLockIconViewController.3
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                FeatureFlags featureFlags2 = legacyLockIconViewController.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags2.getClass();
                legacyLockIconViewController.mIsDozing = z;
                legacyLockIconViewController.getClass();
                legacyLockIconViewController.updateVisibility$1();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i4) {
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                legacyLockIconViewController.mStatusBarState = i4;
                legacyLockIconViewController.updateVisibility$1();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.LegacyLockIconViewController.4
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerStateChanged(boolean z) {
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                legacyLockIconViewController.mIsBouncerShowing = z;
                legacyLockIconViewController.updateVisibility$1();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            }
        };
        new KeyguardStateController.Callback() { // from class: com.android.keyguard.LegacyLockIconViewController.5
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                legacyLockIconViewController.updateKeyguardShowing();
                legacyLockIconViewController.updateVisibility$1();
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                boolean z = legacyLockIconViewController.mCanDismissLockScreen;
                legacyLockIconViewController.mCanDismissLockScreen = ((KeyguardStateControllerImpl) legacyLockIconViewController.mKeyguardStateController).mCanDismissLockScreen;
                legacyLockIconViewController.updateKeyguardShowing();
                if (z != legacyLockIconViewController.mCanDismissLockScreen) {
                    legacyLockIconViewController.updateVisibility$1();
                }
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
            }
        };
        new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.LegacyLockIconViewController.6
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i4 = LegacyLockIconViewController.$r8$clinit;
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                legacyLockIconViewController.getClass();
                legacyLockIconViewController.getClass();
                throw null;
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                int i4 = LegacyLockIconViewController.$r8$clinit;
                LegacyLockIconViewController.this.getClass();
                throw null;
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                int i4 = LegacyLockIconViewController.$r8$clinit;
                LegacyLockIconViewController.this.getClass();
                throw null;
            }
        };
        new AuthController.Callback() { // from class: com.android.keyguard.LegacyLockIconViewController.7
            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onAllAuthenticatorsRegistered(int i4) {
                if (i4 == 2) {
                    int i5 = LegacyLockIconViewController.$r8$clinit;
                    LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                    legacyLockIconViewController.getClass();
                    legacyLockIconViewController.mExecutor.execute(new LegacyLockIconViewController$$ExternalSyntheticLambda4(legacyLockIconViewController));
                }
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onEnrollmentsChanged(int i4) {
                if (i4 == 2) {
                    int i5 = LegacyLockIconViewController.$r8$clinit;
                    LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                    legacyLockIconViewController.getClass();
                    legacyLockIconViewController.mExecutor.execute(new LegacyLockIconViewController$$ExternalSyntheticLambda4(legacyLockIconViewController));
                }
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onUdfpsLocationChanged(UdfpsOverlayParams udfpsOverlayParams) {
                int i4 = LegacyLockIconViewController.$r8$clinit;
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                legacyLockIconViewController.getClass();
                legacyLockIconViewController.mExecutor.execute(new LegacyLockIconViewController$$ExternalSyntheticLambda4(legacyLockIconViewController));
            }
        };
        new View.OnClickListener() { // from class: com.android.keyguard.LegacyLockIconViewController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LegacyLockIconViewController.this.onLongPress();
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardViewController = keyguardViewController;
        this.mKeyguardStateController = keyguardStateController;
        this.mFalsingManager = falsingManager;
        this.mConfigurationController = configurationController;
        this.mExecutor = delayableExecutor;
        this.mVibrator = vibratorHelper;
        this.mFeatureFlags = featureFlags;
        resources.getDimensionPixelSize(R.dimen.udfps_burn_in_offset_x);
        resources.getDimensionPixelSize(R.dimen.udfps_burn_in_offset_y);
        this.mUnlockedLabel = resources.getString(R.string.accessibility_unlock_button);
        this.mLockedLabel = resources.getString(R.string.accessibility_lock_icon);
        resources.getInteger(R.integer.config_lockIconLongPress);
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "LockIconViewController", this);
        this.mResources = resources;
        new View.AccessibilityDelegate() { // from class: com.android.keyguard.LegacyLockIconViewController.1
            public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityAuthenticateHint;
            public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityEnterHint;

            {
                this.mAccessibilityAuthenticateHint = new AccessibilityNodeInfo.AccessibilityAction(16, LegacyLockIconViewController.this.mResources.getString(R.string.accessibility_authenticate_hint));
                this.mAccessibilityEnterHint = new AccessibilityNodeInfo.AccessibilityAction(16, LegacyLockIconViewController.this.mResources.getString(R.string.accessibility_enter_hint));
            }

            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                LegacyLockIconViewController legacyLockIconViewController = LegacyLockIconViewController.this;
                if (legacyLockIconViewController.mIsBouncerShowing ? false : legacyLockIconViewController.mShowUnlockIcon) {
                    if (legacyLockIconViewController.mShowLockIcon) {
                        accessibilityNodeInfo.addAction(this.mAccessibilityAuthenticateHint);
                    } else if (legacyLockIconViewController.mShowUnlockIcon) {
                        accessibilityNodeInfo.addAction(this.mAccessibilityEnterHint);
                    }
                }
            }
        };
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mUdfpsSupported: false");
        printWriter.println("mUdfpsEnrolled: false");
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("mIsKeyguardShowing: "), this.mIsKeyguardShowing, printWriter, "mIsBiometricToastViewAnimating: ");
        m.append(this.mIsBiometricToastViewAnimating);
        printWriter.println(m.toString());
        printWriter.println();
        StringBuilder m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder(" mShowUnlockIcon: "), this.mShowUnlockIcon, printWriter, " mShowLockIcon: ");
        m2.append(this.mShowLockIcon);
        printWriter.println(m2.toString());
        printWriter.println(" mShowAodUnlockedIcon: false");
        printWriter.println();
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder(" mIsDozing: "), this.mIsDozing, printWriter);
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        printWriter.println(" isFlagEnabled(DOZING_MIGRATION_1): false");
        StringBuilder m3 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder(" mIsBouncerShowing: "), this.mIsBouncerShowing, printWriter, " mRunningFPS: "), this.mRunningFPS, printWriter, " mCanDismissLockScreen: "), this.mCanDismissLockScreen, printWriter, " mStatusBarState: ");
        m3.append(StatusBarState.toString(this.mStatusBarState));
        printWriter.println(m3.toString());
        StringBuilder m4 = KeyguardStatusViewController$$ExternalSyntheticOutline0.m(new StringBuilder(" mInterpolatedDarkAmount: "), this.mInterpolatedDarkAmount, printWriter, " mSensorTouchLocation: ");
        m4.append(this.mSensorTouchLocation);
        printWriter.println(m4.toString());
        printWriter.println(" mDefaultPaddingPx: 0");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder(" mIsActiveDreamLockscreenHosted: "), this.mIsActiveDreamLockscreenHosted, printWriter);
    }

    public final boolean isLockScreen() {
        return (this.mIsDozing || this.mIsBouncerShowing || this.mStatusBarState != 1) ? false : true;
    }

    public void onLongPress() {
        if (this.mFalsingManager.isFalseLongTap(1)) {
            return;
        }
        this.mIsBouncerShowing = true;
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        com.android.systemui.Flags.deviceEntryUdfpsRefactor();
        updateVisibility$1();
        vibrateOnLongPress();
        int i = SceneContainerFlag.$r8$clinit;
        com.android.systemui.Flags.sceneContainer();
        this.mKeyguardViewController.showPrimaryBouncer(true);
    }

    public final void updateKeyguardShowing() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        this.mIsKeyguardShowing = ((KeyguardStateControllerImpl) keyguardStateController).mShowing && !((KeyguardStateControllerImpl) keyguardStateController).mKeyguardGoingAway;
    }

    public void updateVisibility$1() {
        boolean z = this.mIsKeyguardShowing;
        if (!z && !this.mIsDozing) {
            throw null;
        }
        if (z && this.mIsActiveDreamLockscreenHosted) {
            throw null;
        }
        this.mShowLockIcon = !this.mCanDismissLockScreen && isLockScreen();
        this.mShowUnlockIcon = this.mCanDismissLockScreen && isLockScreen();
        throw null;
    }

    public void vibrateOnLongPress() {
        this.mVibrator.getClass();
        throw null;
    }

    public void vibrateOnTouchExploration() {
        this.mVibrator.getClass();
        throw null;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
