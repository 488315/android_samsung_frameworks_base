package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.util.RotationUtils;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.settingslib.udfps.UdfpsUtils;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Objects;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsControllerOverlay {
    public final AccessibilityManager accessibilityManager;
    public final ActivityLaunchAnimator activityLaunchAnimator;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final ConfigurationController configurationController;
    public final Context context;
    public final IUdfpsOverlayControllerCallback controllerCallback;
    public final WindowManager.LayoutParams coreLayoutParams;
    public final SystemUIDialogManager dialogManager;
    public final DumpManager dumpManager;
    public final FeatureFlags featureFlags;
    public final LayoutInflater inflater;
    public final boolean isDebuggable;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Function3 onTouch;
    public UdfpsOverlayParams overlayParams;
    public UdfpsControllerOverlay$show$1$1 overlayTouchListener;
    public UdfpsView overlayView;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final long requestId;
    public final int requestReason;
    public Rect sensorBounds;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final StatusBarStateController statusBarStateController;
    public boolean touchExplorationEnabled;
    public final LockscreenShadeTransitionController transitionController;
    public final UdfpsDisplayModeProvider udfpsDisplayModeProvider;
    public final UdfpsUtils udfpsUtils;
    public final UnlockedScreenOffAnimationController unlockedScreenOffAnimationController;
    public final WindowManager windowManager;

    public UdfpsControllerOverlay(Context context, FingerprintManager fingerprintManager, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, SecureSettings secureSettings, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3 function3, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsUtils udfpsUtils) {
        this(context, fingerprintManager, layoutInflater, windowManager, accessibilityManager, statusBarStateController, shadeExpansionStateManager, statusBarKeyguardViewManager, keyguardUpdateMonitor, systemUIDialogManager, dumpManager, lockscreenShadeTransitionController, configurationController, keyguardStateController, unlockedScreenOffAnimationController, udfpsDisplayModeProvider, secureSettings, j, i, iUdfpsOverlayControllerCallback, function3, activityLaunchAnimator, featureFlags, primaryBouncerInteractor, alternateBouncerInteractor, false, udfpsUtils, QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final UdfpsAnimationViewController inflateUdfpsAnimation(UdfpsController udfpsController, UdfpsView udfpsView) {
        int i;
        UdfpsAnimationViewController udfpsFpmEmptyViewController;
        boolean z = false;
        int i2 = this.requestReason;
        if (i2 == 1 || i2 == 2) {
            if (this.isDebuggable && Settings.Global.getInt(this.context.getContentResolver(), "udfps_overlay_remove_enrollment_ui", 0) != 0) {
                z = true;
            }
            if (z) {
                i = 5;
                LayoutInflater layoutInflater = this.inflater;
                switch (i) {
                    case 1:
                    case 2:
                        View inflate = layoutInflater.inflate(R.layout.udfps_fpm_empty_view, (ViewGroup) null);
                        if (inflate == null) {
                            throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsFpmEmptyView");
                        }
                        UdfpsFpmEmptyView udfpsFpmEmptyView = (UdfpsFpmEmptyView) inflate;
                        udfpsView.addView(udfpsFpmEmptyView);
                        Rect rect = this.sensorBounds;
                        View findViewById = udfpsFpmEmptyView.findViewById(R.id.udfps_enroll_accessibility_view);
                        ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
                        layoutParams.width = rect.width();
                        layoutParams.height = rect.height();
                        findViewById.setLayoutParams(layoutParams);
                        findViewById.requestLayout();
                        udfpsFpmEmptyViewController = new UdfpsFpmEmptyViewController(udfpsFpmEmptyView, this.statusBarStateController, this.shadeExpansionStateManager, this.dialogManager, this.dumpManager);
                        break;
                    case 3:
                        View inflate2 = layoutInflater.inflate(R.layout.udfps_bp_view, (ViewGroup) null);
                        if (inflate2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsBpView");
                        }
                        UdfpsBpView udfpsBpView = (UdfpsBpView) inflate2;
                        udfpsView.addView(udfpsBpView);
                        udfpsFpmEmptyViewController = new UdfpsBpViewController(udfpsBpView, this.statusBarStateController, this.shadeExpansionStateManager, this.dialogManager, this.dumpManager);
                        break;
                    case 4:
                        View inflate3 = layoutInflater.inflate(R.layout.udfps_keyguard_view_legacy, (ViewGroup) null);
                        if (inflate3 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsKeyguardViewLegacy");
                        }
                        UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = (UdfpsKeyguardViewLegacy) inflate3;
                        udfpsView.addView(udfpsKeyguardViewLegacy);
                        udfpsKeyguardViewLegacy.mSensorBounds.set(this.sensorBounds);
                        udfpsFpmEmptyViewController = new UdfpsKeyguardViewControllerLegacy(udfpsKeyguardViewLegacy, this.statusBarStateController, this.shadeExpansionStateManager, this.statusBarKeyguardViewManager, this.keyguardUpdateMonitor, this.dumpManager, this.transitionController, this.configurationController, this.keyguardStateController, this.unlockedScreenOffAnimationController, this.dialogManager, udfpsController, this.activityLaunchAnimator, this.featureFlags, this.primaryBouncerInteractor, this.alternateBouncerInteractor);
                        break;
                    case 5:
                    case 6:
                        View inflate4 = layoutInflater.inflate(R.layout.udfps_fpm_empty_view, (ViewGroup) null);
                        if (inflate4 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsFpmEmptyView");
                        }
                        UdfpsFpmEmptyView udfpsFpmEmptyView2 = (UdfpsFpmEmptyView) inflate4;
                        udfpsView.addView(udfpsFpmEmptyView2);
                        udfpsFpmEmptyViewController = new UdfpsFpmEmptyViewController(udfpsFpmEmptyView2, this.statusBarStateController, this.shadeExpansionStateManager, this.dialogManager, this.dumpManager);
                        break;
                    default:
                        Log.e("UdfpsControllerOverlay", "Animation for reason " + i2 + " not supported yet");
                        return null;
                }
                return udfpsFpmEmptyViewController;
            }
        }
        i = i2;
        LayoutInflater layoutInflater2 = this.inflater;
        switch (i) {
        }
        return udfpsFpmEmptyViewController;
    }

    public final boolean matchesRequestId(long j) {
        long j2 = this.requestId;
        return j2 == -1 || j2 == j;
    }

    public final void updateDimensions(WindowManager.LayoutParams layoutParams, UdfpsAnimationViewController udfpsAnimationViewController) {
        Rect rect;
        ReleasedFlag releasedFlag = Flags.UDFPS_NEW_TOUCH_DETECTION;
        FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) this.featureFlags;
        if (!featureFlagsRelease.isEnabled(releasedFlag) && udfpsAnimationViewController != null && (udfpsAnimationViewController instanceof UdfpsKeyguardViewControllerLegacy)) {
            layoutParams.flags |= 262144;
        }
        boolean z = true;
        int i = this.requestReason;
        boolean z2 = i == 1 || i == 2;
        if (!featureFlagsRelease.isEnabled(releasedFlag)) {
            rect = new Rect(this.overlayParams.sensorBounds);
        } else if (this.accessibilityManager.isTouchExplorationEnabled() && z2) {
            rect = new Rect(this.overlayParams.sensorBounds);
        } else {
            UdfpsOverlayParams udfpsOverlayParams = this.overlayParams;
            rect = new Rect(0, 0, udfpsOverlayParams.naturalDisplayWidth, udfpsOverlayParams.naturalDisplayHeight);
        }
        int i2 = this.overlayParams.rotation;
        if (i2 == 1 || i2 == 3) {
            boolean z3 = udfpsAnimationViewController instanceof UdfpsKeyguardViewControllerLegacy;
            KeyguardStateController keyguardStateController = this.keyguardStateController;
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
            if (z3 && (keyguardUpdateMonitor.mGoingToSleep || !((KeyguardStateControllerImpl) keyguardStateController).mOccluded)) {
                z = false;
            }
            if (z) {
                Surface.rotationToString(i2);
                UdfpsOverlayParams udfpsOverlayParams2 = this.overlayParams;
                RotationUtils.rotateBounds(rect, udfpsOverlayParams2.naturalDisplayWidth, udfpsOverlayParams2.naturalDisplayHeight, i2);
                if (featureFlagsRelease.isEnabled(releasedFlag)) {
                    Rect rect2 = this.sensorBounds;
                    UdfpsOverlayParams udfpsOverlayParams3 = this.overlayParams;
                    RotationUtils.rotateBounds(rect2, udfpsOverlayParams3.naturalDisplayWidth, udfpsOverlayParams3.naturalDisplayHeight, i2);
                }
            } else {
                Surface.rotationToString(i2);
                boolean z4 = keyguardUpdateMonitor.mGoingToSleep;
                boolean z5 = ((KeyguardStateControllerImpl) keyguardStateController).mOccluded;
                Objects.toString(udfpsAnimationViewController);
            }
        }
        layoutParams.x = rect.left - 0;
        layoutParams.y = rect.top - 0;
        layoutParams.height = rect.height() + 0;
        layoutParams.width = rect.width() + 0;
    }

    public UdfpsControllerOverlay(Context context, FingerprintManager fingerprintManager, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, SecureSettings secureSettings, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3 function3, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, boolean z, UdfpsUtils udfpsUtils) {
        this.context = context;
        this.inflater = layoutInflater;
        this.windowManager = windowManager;
        this.accessibilityManager = accessibilityManager;
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.dialogManager = systemUIDialogManager;
        this.dumpManager = dumpManager;
        this.transitionController = lockscreenShadeTransitionController;
        this.configurationController = configurationController;
        this.keyguardStateController = keyguardStateController;
        this.unlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.udfpsDisplayModeProvider = udfpsDisplayModeProvider;
        this.requestId = j;
        this.requestReason = i;
        this.controllerCallback = iUdfpsOverlayControllerCallback;
        this.onTouch = function3;
        this.activityLaunchAnimator = activityLaunchAnimator;
        this.featureFlags = featureFlags;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.isDebuggable = z;
        this.udfpsUtils = udfpsUtils;
        this.overlayParams = new UdfpsOverlayParams(null, null, 0, 0, 0.0f, 0, 63, null);
        this.sensorBounds = new Rect();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2024, 0, -3);
        layoutParams.setTitle("UdfpsControllerOverlay");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.flags = 25166120;
        layoutParams.privateFlags = QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT;
        layoutParams.accessibilityTitle = " ";
        if (((FeatureFlagsRelease) featureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION)) {
            layoutParams.inputFeatures = 4;
        }
        this.coreLayoutParams = layoutParams;
    }

    public /* synthetic */ UdfpsControllerOverlay(Context context, FingerprintManager fingerprintManager, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, SecureSettings secureSettings, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3 function3, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, boolean z, UdfpsUtils udfpsUtils, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, fingerprintManager, layoutInflater, windowManager, accessibilityManager, statusBarStateController, shadeExpansionStateManager, statusBarKeyguardViewManager, keyguardUpdateMonitor, systemUIDialogManager, dumpManager, lockscreenShadeTransitionController, configurationController, keyguardStateController, unlockedScreenOffAnimationController, udfpsDisplayModeProvider, secureSettings, j, i, iUdfpsOverlayControllerCallback, function3, activityLaunchAnimator, featureFlags, primaryBouncerInteractor, alternateBouncerInteractor, (i2 & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0 ? Build.IS_DEBUGGABLE : z, udfpsUtils);
    }
}
