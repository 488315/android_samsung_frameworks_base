package com.android.keyguard;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.VibrationAttributes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LockIconViewController extends ViewController implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C08201 mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public final LockIconViewController$$ExternalSyntheticLambda1 mAccessibilityStateChangeListener;
    public final AuthController mAuthController;
    public final C08256 mAuthControllerCallback;
    public boolean mCanDismissLockScreen;
    public final ConfigurationController mConfigurationController;
    public final C08245 mConfigurationListener;
    final Consumer<TransitionStep> mDozeTransitionCallback;
    public final DelayableExecutor mExecutor;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final AnimatedStateListDrawable mIcon;
    public float mInterpolatedDarkAmount;
    public boolean mIsBiometricToastViewAnimating;
    public boolean mIsBouncerShowing;
    public boolean mIsDozing;
    final Consumer<Boolean> mIsDozingCallback;
    public boolean mIsKeyguardShowing;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final C08234 mKeyguardStateCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KeyguardViewController mKeyguardViewController;
    public final CharSequence mLockedLabel;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public boolean mRunningFPS;
    public final Rect mSensorTouchLocation;
    public boolean mShowLockIcon;
    public boolean mShowUnlockIcon;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public final C08212 mStatusBarStateListener;
    public final CharSequence mUnlockedLabel;

    /* renamed from: -$$Nest$mupdateColors, reason: not valid java name */
    public static void m362$$Nest$mupdateColors(LockIconViewController lockIconViewController) {
        ((LockIconView) lockIconViewController.mView).getClass();
    }

    static {
        int i = DisplayMetrics.DENSITY_DEVICE_STABLE;
        VibrationAttributes.createForUsage(18);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.keyguard.LockIconViewController$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.LockIconViewController$2] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.keyguard.LockIconViewController$4] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.keyguard.LockIconViewController$5] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.keyguard.LockIconViewController$6] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1] */
    public LockIconViewController(LockIconView lockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper, AuthRippleController authRippleController, Resources resources, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, KeyguardEditModeController keyguardEditModeController) {
        super(lockIconView);
        final int i = 0;
        this.mIsBiometricToastViewAnimating = false;
        this.mSensorTouchLocation = new Rect();
        this.mDozeTransitionCallback = new Consumer(this) { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0
            public final /* synthetic */ LockIconViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                    case 0:
                        LockIconViewController lockIconViewController = this.f$0;
                        lockIconViewController.getClass();
                        lockIconViewController.mInterpolatedDarkAmount = ((TransitionStep) obj).value;
                        ((LockIconView) lockIconViewController.mView).getClass();
                        break;
                    default:
                        LockIconViewController lockIconViewController2 = this.f$0;
                        lockIconViewController2.getClass();
                        lockIconViewController2.mIsDozing = ((Boolean) obj).booleanValue();
                        lockIconViewController2.updateVisibility();
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mIsDozingCallback = new Consumer(this) { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0
            public final /* synthetic */ LockIconViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        LockIconViewController lockIconViewController = this.f$0;
                        lockIconViewController.getClass();
                        lockIconViewController.mInterpolatedDarkAmount = ((TransitionStep) obj).value;
                        ((LockIconView) lockIconViewController.mView).getClass();
                        break;
                    default:
                        LockIconViewController lockIconViewController2 = this.f$0;
                        lockIconViewController2.getClass();
                        lockIconViewController2.mIsDozing = ((Boolean) obj).booleanValue();
                        lockIconViewController2.updateVisibility();
                        break;
                }
            }
        };
        this.mAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.keyguard.LockIconViewController.1
            public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityAuthenticateHint;
            public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityEnterHint;

            {
                int i3 = LockIconViewController.$r8$clinit;
                this.mAccessibilityAuthenticateHint = new AccessibilityNodeInfo.AccessibilityAction(16, LockIconViewController.this.getResources().getString(R.string.accessibility_authenticate_hint));
                this.mAccessibilityEnterHint = new AccessibilityNodeInfo.AccessibilityAction(16, LockIconViewController.this.getResources().getString(R.string.accessibility_enter_hint));
            }

            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                LockIconViewController lockIconViewController = LockIconViewController.this;
                if (lockIconViewController.mIsBouncerShowing ? false : lockIconViewController.mShowUnlockIcon) {
                    if (lockIconViewController.mShowLockIcon) {
                        accessibilityNodeInfo.addAction(this.mAccessibilityAuthenticateHint);
                    } else if (lockIconViewController.mShowUnlockIcon) {
                        accessibilityNodeInfo.addAction(this.mAccessibilityEnterHint);
                    }
                }
            }
        };
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.keyguard.LockIconViewController.2
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                LockIconViewController lockIconViewController = LockIconViewController.this;
                FeatureFlags featureFlags2 = lockIconViewController.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags2.getClass();
                lockIconViewController.mIsDozing = z;
                lockIconViewController.getClass();
                lockIconViewController.updateVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i3) {
                LockIconViewController lockIconViewController = LockIconViewController.this;
                lockIconViewController.mStatusBarState = i3;
                lockIconViewController.updateVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.LockIconViewController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerStateChanged(boolean z) {
                LockIconViewController lockIconViewController = LockIconViewController.this;
                lockIconViewController.mIsBouncerShowing = z;
                lockIconViewController.updateVisibility();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
            }
        };
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.keyguard.LockIconViewController.4
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                LockIconViewController lockIconViewController = LockIconViewController.this;
                lockIconViewController.updateKeyguardShowing();
                lockIconViewController.updateVisibility();
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                LockIconViewController lockIconViewController = LockIconViewController.this;
                boolean z = lockIconViewController.mCanDismissLockScreen;
                lockIconViewController.mCanDismissLockScreen = ((KeyguardStateControllerImpl) lockIconViewController.mKeyguardStateController).mCanDismissLockScreen;
                lockIconViewController.updateKeyguardShowing();
                if (z != lockIconViewController.mCanDismissLockScreen) {
                    lockIconViewController.updateVisibility();
                }
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.LockIconViewController.5
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i3 = LockIconViewController.$r8$clinit;
                LockIconViewController lockIconViewController = LockIconViewController.this;
                lockIconViewController.getClass();
                LockIconViewController.m362$$Nest$mupdateColors(lockIconViewController);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                LockIconViewController.m362$$Nest$mupdateColors(LockIconViewController.this);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                LockIconViewController.m362$$Nest$mupdateColors(LockIconViewController.this);
            }
        };
        this.mAuthControllerCallback = new AuthController.Callback() { // from class: com.android.keyguard.LockIconViewController.6
            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onAllAuthenticatorsRegistered(int i3) {
                if (i3 == 2) {
                    int i4 = LockIconViewController.$r8$clinit;
                    LockIconViewController lockIconViewController = LockIconViewController.this;
                    lockIconViewController.getClass();
                    ((ExecutorImpl) lockIconViewController.mExecutor).execute(new LockIconViewController$$ExternalSyntheticLambda2(lockIconViewController));
                }
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onEnrollmentsChanged(int i3) {
                if (i3 == 2) {
                    int i4 = LockIconViewController.$r8$clinit;
                    LockIconViewController lockIconViewController = LockIconViewController.this;
                    lockIconViewController.getClass();
                    ((ExecutorImpl) lockIconViewController.mExecutor).execute(new LockIconViewController$$ExternalSyntheticLambda2(lockIconViewController));
                }
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onUdfpsLocationChanged(UdfpsOverlayParams udfpsOverlayParams) {
                int i3 = LockIconViewController.$r8$clinit;
                LockIconViewController lockIconViewController = LockIconViewController.this;
                lockIconViewController.getClass();
                ((ExecutorImpl) lockIconViewController.mExecutor).execute(new LockIconViewController$$ExternalSyntheticLambda2(lockIconViewController));
            }
        };
        this.mAccessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1
            @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
            public final void onAccessibilityStateChanged(boolean z) {
                LockIconViewController.this.getClass();
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mAuthController = authController;
        this.mKeyguardViewController = keyguardViewController;
        this.mKeyguardStateController = keyguardStateController;
        this.mFalsingManager = falsingManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mConfigurationController = configurationController;
        this.mExecutor = delayableExecutor;
        this.mFeatureFlags = featureFlags;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        resources.getDimensionPixelSize(R.dimen.udfps_burn_in_offset_x);
        resources.getDimensionPixelSize(R.dimen.udfps_burn_in_offset_y);
        AnimatedStateListDrawable animatedStateListDrawable = (AnimatedStateListDrawable) resources.getDrawable(R.drawable.super_lock_icon, ((LockIconView) this.mView).getContext().getTheme());
        this.mIcon = animatedStateListDrawable;
        ((LockIconView) this.mView).setImageDrawable(animatedStateListDrawable);
        this.mUnlockedLabel = resources.getString(R.string.accessibility_unlock_button);
        this.mLockedLabel = resources.getString(R.string.accessibility_lock_icon);
        resources.getInteger(R.integer.config_lockIconLongPress);
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "LockIconViewController", this);
        this.mKeyguardEditModeController = keyguardEditModeController;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mUdfpsSupported: false");
        printWriter.println("mUdfpsEnrolled: false");
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("mIsKeyguardShowing: "), this.mIsKeyguardShowing, printWriter, "mIsBiometricToastViewAnimating: ");
        m64m.append(this.mIsBiometricToastViewAnimating);
        printWriter.println(m64m.toString());
        printWriter.println(" mIcon: ");
        for (int i : this.mIcon.getState()) {
            printWriter.print(" " + i);
        }
        printWriter.println();
        StringBuilder m64m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder(" mShowUnlockIcon: "), this.mShowUnlockIcon, printWriter, " mShowLockIcon: ");
        m64m2.append(this.mShowLockIcon);
        printWriter.println(m64m2.toString());
        printWriter.println(" mShowAodUnlockedIcon: false");
        printWriter.println();
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder(" mIsDozing: "), this.mIsDozing, printWriter);
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        printWriter.println(" isFlagEnabled(DOZING_MIGRATION_1): false");
        StringBuilder m64m3 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder(" mIsBouncerShowing: "), this.mIsBouncerShowing, printWriter, " mRunningFPS: "), this.mRunningFPS, printWriter, " mCanDismissLockScreen: "), this.mCanDismissLockScreen, printWriter, " mStatusBarState: ");
        m64m3.append(StatusBarState.toString(this.mStatusBarState));
        printWriter.println(m64m3.toString());
        StringBuilder m81m = LockIconView$$ExternalSyntheticOutline0.m81m(new StringBuilder(" mInterpolatedDarkAmount: "), this.mInterpolatedDarkAmount, printWriter, " mSensorTouchLocation: ");
        m81m.append(this.mSensorTouchLocation);
        printWriter.println(m81m.toString());
        printWriter.println(" mDefaultPaddingPx: 0");
        View view = this.mView;
        if (view != null) {
            ((LockIconView) view).dump(printWriter, strArr);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        ((LockIconView) this.mView).setAccessibilityDelegate(this.mAccessibilityDelegate);
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        updateKeyguardShowing();
        this.mIsBouncerShowing = this.mKeyguardViewController.isBouncerShowing();
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        this.mIsDozing = statusBarStateController.isDozing();
        this.mInterpolatedDarkAmount = statusBarStateController.getDozeAmount();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        this.mRunningFPS = keyguardUpdateMonitor.isFingerprintDetectionRunning();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        this.mCanDismissLockScreen = keyguardStateControllerImpl.mCanDismissLockScreen;
        this.mStatusBarState = statusBarStateController.getState();
        ((LockIconView) this.mView).getClass();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mAuthController.addCallback(this.mAuthControllerCallback);
        keyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        statusBarStateController.addCallback(this.mStatusBarStateListener);
        keyguardStateControllerImpl.addCallback(this.mKeyguardStateCallback);
        updateVisibility();
        this.mAccessibilityManager.addAccessibilityStateChangeListener(this.mAccessibilityStateChangeListener);
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mAuthController.removeCallback(this.mAuthControllerCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mKeyguardStateCallback);
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityStateChangeListener);
    }

    public final void updateKeyguardShowing() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        this.mIsKeyguardShowing = ((KeyguardStateControllerImpl) keyguardStateController).mShowing && !((KeyguardStateControllerImpl) keyguardStateController).mKeyguardGoingAway;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateVisibility() {
        boolean z;
        boolean z2;
        CharSequence contentDescription;
        boolean z3;
        if (!this.mIsKeyguardShowing && !this.mIsDozing) {
            ((LockIconView) this.mView).setVisibility(4);
            return;
        }
        boolean z4 = this.mCanDismissLockScreen;
        boolean z5 = false;
        if (!z4) {
            if ((this.mIsDozing || this.mIsBouncerShowing || this.mStatusBarState != 1) ? false : true) {
                z = true;
                this.mShowLockIcon = z;
                if (z4) {
                    if ((this.mIsDozing || this.mIsBouncerShowing || this.mStatusBarState != 1) ? false : true) {
                        z2 = true;
                        this.mShowUnlockIcon = z2;
                        contentDescription = ((LockIconView) this.mView).getContentDescription();
                        z3 = this.mShowLockIcon;
                        CharSequence charSequence = this.mLockedLabel;
                        if (z3) {
                            ((LockIconView) this.mView).updateIcon(0, false);
                            ((LockIconView) this.mView).setContentDescription(charSequence);
                            ((LockIconView) this.mView).setVisibility(0);
                        } else {
                            boolean z6 = this.mShowUnlockIcon;
                            CharSequence charSequence2 = this.mUnlockedLabel;
                            if (z6) {
                                ((LockIconView) this.mView).updateIcon(2, false);
                                ((LockIconView) this.mView).setContentDescription(charSequence2);
                                ((LockIconView) this.mView).setVisibility(0);
                            } else {
                                ((LockIconView) this.mView).updateIcon(-1, false);
                                ((LockIconView) this.mView).setVisibility(4);
                                ((LockIconView) this.mView).setContentDescription(null);
                            }
                        }
                        if (!this.mPrimaryBouncerInteractor.isAnimatingAway() && ((LockIconView) this.mView).isVisibleToUser()) {
                            z5 = true;
                        }
                        ((LockIconView) this.mView).setImportantForAccessibility(z5 ? 1 : 2);
                        if (Objects.equals(contentDescription, ((LockIconView) this.mView).getContentDescription()) || ((LockIconView) this.mView).getContentDescription() == null || !z5) {
                            return;
                        }
                        LockIconView lockIconView = (LockIconView) this.mView;
                        lockIconView.announceForAccessibility(lockIconView.getContentDescription());
                        return;
                    }
                }
                z2 = false;
                this.mShowUnlockIcon = z2;
                contentDescription = ((LockIconView) this.mView).getContentDescription();
                z3 = this.mShowLockIcon;
                CharSequence charSequence3 = this.mLockedLabel;
                if (z3) {
                }
                if (!this.mPrimaryBouncerInteractor.isAnimatingAway()) {
                    z5 = true;
                }
                ((LockIconView) this.mView).setImportantForAccessibility(z5 ? 1 : 2);
                if (Objects.equals(contentDescription, ((LockIconView) this.mView).getContentDescription())) {
                    return;
                } else {
                    return;
                }
            }
        }
        z = false;
        this.mShowLockIcon = z;
        if (z4) {
        }
        z2 = false;
        this.mShowUnlockIcon = z2;
        contentDescription = ((LockIconView) this.mView).getContentDescription();
        z3 = this.mShowLockIcon;
        CharSequence charSequence32 = this.mLockedLabel;
        if (z3) {
        }
        if (!this.mPrimaryBouncerInteractor.isAnimatingAway()) {
        }
        ((LockIconView) this.mView).setImportantForAccessibility(z5 ? 1 : 2);
        if (Objects.equals(contentDescription, ((LockIconView) this.mView).getContentDescription())) {
        }
    }
}
