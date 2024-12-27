package com.android.systemui.biometrics;

import android.content.res.Configuration;
import android.util.MathUtils;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Flags;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.io.PrintWriter;
import java.util.Objects;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UdfpsKeyguardViewControllerLegacy extends UdfpsAnimationViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float activityLaunchProgress;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final UdfpsKeyguardViewControllerLegacy$configurationListener$1 configurationListener;
    public float inputBouncerExpansion;
    public boolean isLaunchingActivity;
    public final KeyguardStateController keyguardStateController;
    public final UdfpsKeyguardViewControllerLegacy$keyguardStateControllerCallback$1 keyguardStateControllerCallback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager keyguardViewManager;
    public boolean launchTransitionFadingAway;
    public final UdfpsKeyguardViewControllerLegacy$mActivityTransitionAnimatorListener$1 mActivityTransitionAnimatorListener;
    public final UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 occludingAppBiometricUI;
    public float panelExpansionFraction;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public float qsExpansion;
    public final SelectedUserInteractor selectedUserInteractor;
    public boolean showingUdfpsBouncer;
    public final UdfpsKeyguardViewControllerLegacy$stateListener$1 stateListener;
    public int statusBarState;
    public final KeyguardTransitionInteractor transitionInteractor;
    public final float transitionToFullShadeProgress;
    public final UdfpsController udfpsController;
    public final UdfpsKeyguardAccessibilityDelegate udfpsKeyguardAccessibilityDelegate;
    public boolean udfpsRequested;
    public final String uniqueIdentifier;
    public final UnlockedScreenOffAnimationController unlockedScreenOffAnimationController;
    public final UdfpsKeyguardViewLegacy view;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public UdfpsKeyguardViewControllerLegacy(UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, UdfpsController udfpsController, ActivityTransitionAnimator activityTransitionAnimator, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsKeyguardAccessibilityDelegate udfpsKeyguardAccessibilityDelegate, SelectedUserInteractor selectedUserInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, ShadeInteractor shadeInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        super(udfpsKeyguardViewLegacy, statusBarStateController, shadeInteractor, systemUIDialogManager, dumpManager, udfpsOverlayInteractor);
        this.view = udfpsKeyguardViewLegacy;
        this.keyguardViewManager = statusBarKeyguardViewManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardStateController = keyguardStateController;
        this.unlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.udfpsController = udfpsController;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.transitionInteractor = keyguardTransitionInteractor;
        new StatusBarStateController.StateListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$stateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.statusBarState = i;
                udfpsKeyguardViewControllerLegacy.updateAlpha();
                udfpsKeyguardViewControllerLegacy.updatePauseAuth();
            }
        };
        new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = UdfpsKeyguardViewControllerLegacy.$r8$clinit;
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                UdfpsOverlayParams udfpsOverlayParams = udfpsKeyguardViewControllerLegacy.udfpsController.mOverlayParams;
                UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy2 = udfpsKeyguardViewControllerLegacy.view;
                if (udfpsOverlayParams != null) {
                    udfpsKeyguardViewLegacy2.mScaleFactor = udfpsOverlayParams.scaleFactor;
                }
                udfpsKeyguardViewLegacy2.updatePadding();
                udfpsKeyguardViewLegacy2.updateColor();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                UdfpsKeyguardViewControllerLegacy.this.view.updateColor();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                UdfpsKeyguardViewControllerLegacy.this.view.updateColor();
            }
        };
        new KeyguardStateController.Callback() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$keyguardStateControllerCallback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onLaunchTransitionFadingAwayChanged() {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.launchTransitionFadingAway = ((KeyguardStateControllerImpl) udfpsKeyguardViewControllerLegacy.keyguardStateController).mLaunchTransitionFadingAway;
                udfpsKeyguardViewControllerLegacy.updatePauseAuth();
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                UdfpsKeyguardViewControllerLegacy.this.updatePauseAuth();
            }
        };
        new ActivityTransitionAnimator.Listener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$mActivityTransitionAnimatorListener$1
            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationEnd() {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.isLaunchingActivity = false;
                udfpsKeyguardViewControllerLegacy.updateAlpha();
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationProgress(float f) {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.activityLaunchProgress = f;
                udfpsKeyguardViewControllerLegacy.updateAlpha();
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationStart() {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.isLaunchingActivity = true;
                udfpsKeyguardViewControllerLegacy.activityLaunchProgress = 0.0f;
                udfpsKeyguardViewControllerLegacy.updateAlpha();
            }
        };
        new UdfpsKeyguardViewControllerLegacy$statusBarKeyguardViewManagerCallback$1(this);
        this.occludingAppBiometricUI = new UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1(this);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        throw new IllegalStateException("Legacy code path not supported when com.android.systemui.device_entry_udfps_refactor is enabled.".toString());
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        super.dump(printWriter, strArr);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("showingUdfpsAltBouncer=", this.showingUdfpsBouncer, printWriter);
        printWriter.println("altBouncerInteractor#isAlternateBouncerVisible=" + this.alternateBouncerInteractor.isVisibleState());
        printWriter.println("altBouncerInteractor#canShowAlternateBouncerForFingerprint=false");
        printWriter.println("faceDetectRunning=false");
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "statusBarState=", StatusBarState.toString(this.statusBarState));
        printWriter.println("transitionToFullShadeProgress=" + this.transitionToFullShadeProgress);
        printWriter.println("qsExpansion=" + this.qsExpansion);
        printWriter.println("panelExpansionFraction=" + this.panelExpansionFraction);
        UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = this.view;
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("unpausedAlpha=", udfpsKeyguardViewLegacy.mAlpha, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("udfpsRequestedByApp=", this.udfpsRequested, printWriter);
        printWriter.println("launchTransitionFadingAway=" + this.launchTransitionFadingAway);
        printWriter.println("lastDozeAmount=0.0");
        printWriter.println("inputBouncerExpansion=" + this.inputBouncerExpansion);
        printWriter.println("UdfpsKeyguardView (" + udfpsKeyguardViewLegacy + ")");
        StringBuilder m = KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("    mPauseAuth="), udfpsKeyguardViewLegacy.mPauseAuth, printWriter, "    mUnpausedAlpha="), udfpsKeyguardViewLegacy.mAlpha, printWriter, "    mUdfpsRequested="), udfpsKeyguardViewLegacy.mUdfpsRequested, printWriter, "    mInterpolatedDarkAmount="), udfpsKeyguardViewLegacy.mInterpolatedDarkAmount, printWriter, "    mAnimationType=");
        m.append(udfpsKeyguardViewLegacy.mAnimationType);
        printWriter.println(m.toString());
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public final String getTag() {
        return "UdfpsKeyguardViewController";
    }

    public final Object listenForAlternateBouncerToAodTransitions(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerToAodTransitions$2(this, null), 3);
    }

    public final Object listenForAlternateBouncerVisibility(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2(this, null), 3);
    }

    public final Object listenForAodToOccludedTransitions(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForAodToOccludedTransitions$2(this, null), 3);
    }

    public final Object listenForBouncerExpansion(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2(this, null), 3);
    }

    public final Object listenForDreamingToAodTransitions(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForDreamingToAodTransitions$2(this, null), 3);
    }

    public final Object listenForGoneToAodTransition(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForGoneToAodTransition$2(this, null), 3);
    }

    public final Object listenForLockscreenAodTransitions(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForLockscreenAodTransitions$2(this, null), 3);
    }

    public final Object listenForOccludedToAodTransition(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForOccludedToAodTransition$2(this, null), 3);
    }

    public final Object listenForPrimaryBouncerToAodTransitions(CoroutineScope coroutineScope, Continuation continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForPrimaryBouncerToAodTransitions$2(this, null), 3);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.keyguardViewManager;
        UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 = statusBarKeyguardViewManager.mOccludingAppBiometricUI;
        UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$12 = this.occludingAppBiometricUI;
        if (Objects.equals(udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1, udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$12)) {
            return;
        }
        statusBarKeyguardViewManager.mOccludingAppBiometricUI = udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$12;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.alternateBouncerInteractor.getClass();
        AlternateBouncerInteractor.setAlternateBouncerUIAvailable();
        throw null;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.alternateBouncerInteractor.getClass();
        AlternateBouncerInteractor.setAlternateBouncerUIAvailable();
        throw null;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public final boolean shouldPauseAuth() {
        if (this.showingUdfpsBouncer) {
            return false;
        }
        if (this.udfpsRequested && !this.notificationShadeVisible && this.inputBouncerExpansion != 1.0f && ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
            return false;
        }
        if (this.launchTransitionFadingAway) {
            return true;
        }
        if ((this.statusBarState == 1 || this.unlockedScreenOffAnimationController.isAnimationPlaying()) && this.inputBouncerExpansion < 0.5f) {
            return this.keyguardUpdateMonitor.getUserUnlockedWithBiometric(this.selectedUserInteractor.getSelectedUserId(false)) || ((double) this.view.mAlpha) < 25.5d;
        }
        return true;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public final void updateAlpha() {
        int constrain = this.showingUdfpsBouncer ? 255 : (int) MathUtils.constrain(MathUtils.map(0.5f, 0.9f, 0.0f, 255.0f, this.udfpsRequested ? 1.0f - this.inputBouncerExpansion : this.panelExpansionFraction), 0.0f, 255.0f);
        boolean z = this.showingUdfpsBouncer;
        UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = this.view;
        if (!z) {
            int interpolation = (int) ((1.0f - this.transitionToFullShadeProgress) * ((int) ((1.0f - ((PathInterpolator) Interpolators.EMPHASIZED_DECELERATE).getInterpolation(this.qsExpansion)) * constrain)));
            if (this.isLaunchingActivity && !this.udfpsRequested) {
                interpolation = (int) (interpolation * (1.0f - RangesKt___RangesKt.coerceIn(this.activityLaunchProgress * (ActivityTransitionAnimator.TIMINGS.totalDuration / 83), 0.0f, 1.0f)));
            }
            constrain = (int) (interpolation * udfpsKeyguardViewLegacy.mDialogSuggestedAlpha);
        }
        udfpsKeyguardViewLegacy.mAlpha = constrain;
        udfpsKeyguardViewLegacy.updateAlpha();
    }
}
