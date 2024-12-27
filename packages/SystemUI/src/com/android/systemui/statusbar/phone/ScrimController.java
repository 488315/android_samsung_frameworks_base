package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.CoreStartable;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimViewBase;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.phone.ScrimStateLogger;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlinx.coroutines.CoroutineDispatcher;

public final class ScrimController implements ViewTreeObserver.OnPreDrawListener, Dumpable, CoreStartable {
    public AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public final AlternateBouncerToGoneTransitionViewModel mAlternateBouncerToGoneTransitionViewModel;
    public boolean mAnimateChange;
    public boolean mAnimatingPanelExpansionOnUnlock;
    public long mAnimationDelay;
    public Animator.AnimatorListener mAnimatorListener;
    public int mBehindTint;
    public boolean mBlankScreen;
    public ScrimController$$ExternalSyntheticLambda0 mBlankingTransitionRunnable;
    Consumer<TransitionStep> mBouncerToGoneTransition;
    public Callback mCallback;
    public final ColorExtractor.GradientColors mColors;
    public boolean mDarkenWhileDragging;
    public final float mDefaultScrimAlpha;
    public final DockManager mDockManager;
    public final DozeParameters mDozeParameters;
    public final Handler mHandler;
    public int mInFrontTint;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardInteractor mKeyguardInteractor;
    public boolean mKeyguardOccluded;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardTransitionInteractor mKeyguardTransitionInteractor;
    public final KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardVisibilityCallback mKeyguardVisibilityCallback;
    public final LargeScreenShadeInterpolator mLargeScreenShadeInterpolator;
    public final CoroutineDispatcher mMainDispatcher;
    public final Executor mMainExecutor;
    public boolean mNeedsDrawableColorUpdate;
    public ScrimView mNotificationsScrim;
    public int mNotificationsTint;
    public float mPanelScrimMinFraction;
    public ScrimController$$ExternalSyntheticLambda0 mPendingFrameCallback;
    public final PrimaryBouncerToGoneTransitionViewModel mPrimaryBouncerToGoneTransitionViewModel;
    public boolean mQsBottomVisible;
    public float mQsExpansion;
    public float mRawPanelExpansionFraction;
    public boolean mScreenBlankingCallbackCalled;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public boolean mScreenOn;
    public ScrimView mScrimBehind;
    public ScrimStateLogger mScrimColorState;
    public ScrimView mScrimInFront;
    public final ScrimController$$ExternalSyntheticLambda4 mScrimStateListener;
    public Consumer mScrimVisibleListener;
    public int mScrimsVisibility;
    public SecLsScrimControlHelper mSecLsScrimControlHelper;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final AlarmTimeout mTimeTicker;
    public float mTransitionToFullShadeProgress;
    public float mTransitionToLockScreenFullShadeNotificationsProgress;
    public boolean mTransitioningToFullShade;
    public boolean mTransparentScrimBackground;
    public boolean mUpdatePending;
    public final DelayedWakeLock mWakeLock;
    public boolean mWakeLockHeld;
    public final WallpaperRepository mWallpaperRepository;
    public boolean mWallpaperSupportsAmbientMode;
    public boolean mWallpaperVisibilityTimedOut;
    public static final boolean DEBUG = Log.isLoggable("ScrimController", 3);
    public static final int TAG_KEY_ANIM = R.id.scrim;
    public static final int TAG_START_ALPHA = R.id.scrim_alpha_start;
    public static final int TAG_END_ALPHA = R.id.scrim_alpha_end;
    public boolean mOccludeAnimationPlaying = false;
    public float mBouncerHiddenFraction = 1.0f;
    public ScrimState mState = ScrimState.UNINITIALIZED;
    public float mScrimBehindAlphaKeyguard = 0.2f;
    public float mPanelExpansionFraction = 1.0f;
    public boolean mExpansionAffectsAlpha = true;
    public long mAnimationDuration = -1;
    public final Interpolator mInterpolator = new DecelerateInterpolator();
    public float mInFrontAlpha = -1.0f;
    public float mBehindAlpha = -1.0f;
    public float mNotificationsAlpha = -1.0f;
    public boolean mIsBouncerToGoneTransitionRunning = false;
    public final ScrimController$$ExternalSyntheticLambda3 mScrimAlphaConsumer = new ScrimController$$ExternalSyntheticLambda3(this, 4);
    public final ScrimController$$ExternalSyntheticLambda3 mGlanceableHubConsumer = new ScrimController$$ExternalSyntheticLambda3(this, 0);

    /* renamed from: com.android.systemui.statusbar.phone.ScrimController$3, reason: invalid class name */
    public final class AnonymousClass3 implements ScrimStateLogger.Callback {
        public AnonymousClass3() {
        }
    }

    class KeyguardVisibilityCallback extends KeyguardUpdateMonitorCallback {
        public /* synthetic */ KeyguardVisibilityCallback(ScrimController scrimController, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            ScrimController scrimController = ScrimController.this;
            scrimController.mNeedsDrawableColorUpdate = true;
            scrimController.scheduleUpdate$1();
        }

        private KeyguardVisibilityCallback() {
        }
    }

    public ScrimController(LightBarController lightBarController, DozeParameters dozeParameters, AlarmManager alarmManager, final KeyguardStateController keyguardStateController, DelayedWakeLock.Factory factory, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, ConfigurationController configurationController, Executor executor, JavaAdapter javaAdapter, ScreenOffAnimationController screenOffAnimationController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, WallpaperRepository wallpaperRepository, CoroutineDispatcher coroutineDispatcher, LargeScreenShadeInterpolator largeScreenShadeInterpolator) {
        Objects.requireNonNull(lightBarController);
        this.mScrimStateListener = new ScrimController$$ExternalSyntheticLambda4(lightBarController);
        this.mLargeScreenShadeInterpolator = largeScreenShadeInterpolator;
        this.mDefaultScrimAlpha = 1.0f;
        this.mKeyguardStateController = keyguardStateController;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        this.mDarkenWhileDragging = true ^ keyguardStateControllerImpl.mCanDismissLockScreen;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardVisibilityCallback = new KeyguardVisibilityCallback(this, 0);
        this.mHandler = handler;
        this.mMainExecutor = executor;
        this.mJavaAdapter = javaAdapter;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda5
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                ScrimController.this.onHideWallpaperTimeout();
            }
        }, "hide_aod_wallpaper", handler);
        this.mWakeLock = factory.create("Scrims");
        this.mDozeParameters = dozeParameters;
        this.mDockManager = dockManager;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        keyguardStateControllerImpl.addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.ScrimController.1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                KeyguardStateController keyguardStateController2 = keyguardStateController;
                boolean z = ((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardFadingAway;
                long j = ((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardFadingAwayDuration;
                boolean z2 = ScrimController.DEBUG;
                ScrimController.this.getClass();
                for (ScrimState scrimState : ScrimState.values()) {
                    scrimState.mKeyguardFadingAway = z;
                    scrimState.mKeyguardFadingAwayDuration = j;
                }
            }
        });
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.ScrimController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                boolean z = ScrimController.DEBUG;
                ScrimController scrimController = ScrimController.this;
                scrimController.updateThemeColors();
                scrimController.scheduleUpdate$1();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                boolean z = ScrimController.DEBUG;
                ScrimController scrimController = ScrimController.this;
                scrimController.updateThemeColors();
                scrimController.scheduleUpdate$1();
            }
        });
        this.mColors = new ColorExtractor.GradientColors();
        this.mPrimaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.mAlternateBouncerToGoneTransitionViewModel = alternateBouncerToGoneTransitionViewModel;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mWallpaperRepository = wallpaperRepository;
        this.mMainDispatcher = coroutineDispatcher;
    }

    public static boolean isAnimating(View view) {
        return (view == null || view.getTag(TAG_KEY_ANIM) == null) ? false : true;
    }

    public final void applyAndDispatchState() {
        applyState$1$1();
        if (this.mUpdatePending) {
            return;
        }
        setOrAdaptCurrentAnimation(this.mScrimBehind);
        setOrAdaptCurrentAnimation(this.mNotificationsScrim);
        setOrAdaptCurrentAnimation(this.mScrimInFront);
        dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
        if (this.mWallpaperVisibilityTimedOut) {
            this.mWallpaperVisibilityTimedOut = false;
            DejankUtils.postAfterTraversal(new ScrimController$$ExternalSyntheticLambda0(this, 2));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyState$1$1() {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ScrimController.applyState$1$1():void");
    }

    public final void assertAlphasValid() {
        if (Float.isNaN(this.mBehindAlpha) || Float.isNaN(this.mInFrontAlpha) || Float.isNaN(this.mNotificationsAlpha)) {
            throw new IllegalStateException("Scrim opacity is NaN for state: " + this.mState + ", front: " + this.mInFrontAlpha + ", back: " + this.mBehindAlpha + ", notif: " + this.mNotificationsAlpha);
        }
    }

    public final void calculateAndUpdatePanelExpansion() {
        float f = this.mRawPanelExpansionFraction;
        float f2 = this.mPanelScrimMinFraction;
        if (f2 < 1.0f) {
            f = Math.max((f - f2) / (1.0f - f2), 0.0f);
        }
        if (this.mPanelExpansionFraction != f) {
            if (f != 0.0f && this.mKeyguardUnlockAnimationController.playingCannedUnlockAnimation && this.mState != ScrimState.UNLOCKED) {
                this.mAnimatingPanelExpansionOnUnlock = true;
            } else if (f == 0.0f) {
                this.mAnimatingPanelExpansionOnUnlock = false;
            }
            this.mPanelExpansionFraction = f;
            ScrimState scrimState = this.mState;
            if ((scrimState == ScrimState.UNLOCKED || scrimState == ScrimState.KEYGUARD || scrimState == ScrimState.DREAMING || scrimState == ScrimState.GLANCEABLE_HUB_OVER_DREAM || scrimState == ScrimState.SHADE_LOCKED || scrimState == ScrimState.PULSING) && this.mExpansionAffectsAlpha && !this.mAnimatingPanelExpansionOnUnlock) {
                applyAndDispatchState();
            }
        }
    }

    public final Pair calculateBackStateForState(ScrimState scrimState) {
        float interpolatedFraction = getInterpolatedFraction();
        float f = scrimState.mBehindAlpha;
        int i = scrimState.mBehindTint;
        float lerp = this.mDarkenWhileDragging ? MathUtils.lerp(this.mDefaultScrimAlpha, f, interpolatedFraction) : MathUtils.lerp(0.0f, f, interpolatedFraction);
        if (this.mStatusBarKeyguardViewManager.isPrimaryBouncerInTransit()) {
            i = ColorUtils.blendARGB(ScrimState.BOUNCER.mBehindTint, scrimState.mBehindTint, interpolatedFraction);
        }
        float f2 = this.mQsExpansion;
        if (f2 > 0.0f) {
            lerp = MathUtils.lerp(lerp, this.mDefaultScrimAlpha, f2);
            float f3 = this.mQsExpansion;
            if (this.mStatusBarKeyguardViewManager.isPrimaryBouncerInTransit()) {
                f3 = BouncerPanelExpansionCalculator.showBouncerProgress(this.mPanelExpansionFraction);
            }
            i = ColorUtils.blendARGB(i, ScrimState.SHADE_LOCKED.mBehindTint, f3);
        }
        return new Pair(Integer.valueOf(i), Float.valueOf(((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway ? 0.0f : lerp));
    }

    public final void dispatchBackScrimState(float f) {
        this.mScrimStateListener.accept(this.mState, Float.valueOf(f), this.mColors);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dispatchScrimsVisible() {
        /*
            r6 = this;
            com.android.systemui.scrim.ScrimView r0 = r6.mScrimBehind
            com.android.systemui.scrim.ScrimView r1 = r6.mScrimInFront
            float r1 = r1.mViewAlpha
            r2 = 1065353216(0x3f800000, float:1.0)
            int r3 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            r4 = 2
            r5 = 0
            if (r3 == 0) goto L22
            float r0 = r0.mViewAlpha
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 != 0) goto L15
            goto L22
        L15:
            r2 = 0
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 != 0) goto L20
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L20
            r0 = r5
            goto L23
        L20:
            r0 = 1
            goto L23
        L22:
            r0 = r4
        L23:
            int r1 = r6.mScrimsVisibility
            if (r1 == r0) goto L65
            r6.mScrimsVisibility = r0
            com.android.systemui.statusbar.phone.SecLsScrimControlHelper r1 = r6.mSecLsScrimControlHelper
            boolean r2 = r6.mScreenOn
            r1.getClass()
            if (r2 != 0) goto L3b
            if (r0 != r4) goto L3b
            com.android.systemui.scrim.ScrimView r2 = r1.mScrimInFront
            android.view.ViewRootImpl r2 = r2.getViewRootImpl()
            goto L3c
        L3b:
            r2 = 0
        L3c:
            boolean r3 = com.android.systemui.LsRune.AOD_SUB_DISPLAY_COVER
            if (r3 == 0) goto L54
            com.android.systemui.Dependency r3 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.keyguard.DisplayLifecycle> r4 = com.android.systemui.keyguard.DisplayLifecycle.class
            java.lang.Object r3 = r3.getDependencyInner(r4)
            com.android.systemui.keyguard.DisplayLifecycle r3 = (com.android.systemui.keyguard.DisplayLifecycle) r3
            boolean r3 = r3.mIsFolderOpened
            if (r3 != 0) goto L54
            com.android.systemui.scrim.ScrimView r1 = r1.mScrimBehind
            android.view.ViewRootImpl r2 = r1.getViewRootImpl()
        L54:
            if (r2 == 0) goto L5c
            java.lang.String r1 = "scrim"
            r2.setReportNextDraw(r5, r1)
        L5c:
            java.util.function.Consumer r6 = r6.mScrimVisibleListener
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r6.accept(r0)
        L65:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ScrimController.dispatchScrimsVisible():void");
    }

    public void doOnTheNextFrame(Runnable runnable) {
        this.mScrimBehind.postOnAnimationDelayed(runnable, 32L);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(" ScrimController: ");
        printWriter.print("  state: ");
        printWriter.println(this.mState);
        printWriter.println("    mClipQsScrim = " + this.mState.mClipQsScrim);
        printWriter.print("  frontScrim:");
        printWriter.print(" viewAlpha=");
        printWriter.print(this.mScrimInFront.mViewAlpha);
        printWriter.print(" alpha=");
        printWriter.print(this.mInFrontAlpha);
        printWriter.print(" tint=0x");
        printWriter.println(Integer.toHexString(this.mScrimInFront.mTintColor));
        printWriter.print("  behindScrim:");
        printWriter.print(" viewAlpha=");
        printWriter.print(this.mScrimBehind.mViewAlpha);
        printWriter.print(" alpha=");
        printWriter.print(this.mBehindAlpha);
        printWriter.print(" tint=0x");
        printWriter.println(Integer.toHexString(this.mScrimBehind.mTintColor));
        printWriter.print("  notificationsScrim:");
        printWriter.print(" viewAlpha=");
        printWriter.print(this.mNotificationsScrim.mViewAlpha);
        printWriter.print(" alpha=");
        printWriter.print(this.mNotificationsAlpha);
        printWriter.print(" tint=0x");
        printWriter.println(Integer.toHexString(this.mNotificationsScrim.mTintColor));
        printWriter.print(" expansionProgress=");
        printWriter.println(this.mTransitionToLockScreenFullShadeNotificationsProgress);
        printWriter.print("  mDefaultScrimAlpha=");
        printWriter.println(this.mDefaultScrimAlpha);
        printWriter.print("  mPanelExpansionFraction=");
        printWriter.println(this.mPanelExpansionFraction);
        printWriter.print("  mExpansionAffectsAlpha=");
        printWriter.println(this.mExpansionAffectsAlpha);
        printWriter.print("  mState.getMaxLightRevealScrimAlpha=");
        printWriter.println(this.mState.getMaxLightRevealScrimAlpha());
        SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
        secLsScrimControlHelper.getClass();
        printWriter.print("  mState.mWallpaperSupportsAmbientMode=");
        printWriter.println(secLsScrimControlHelper.mState.mWallpaperSupportsAmbientMode);
        printWriter.print("  mState.mHasBackdrop=");
        printWriter.println(secLsScrimControlHelper.mState.mHasBackdrop);
    }

    public boolean getClipQsScrim() {
        return false;
    }

    public final float getCurrentScrimAlpha(View view) {
        if (view == this.mScrimInFront) {
            return this.mInFrontAlpha;
        }
        if (view == this.mScrimBehind) {
            return this.mBehindAlpha;
        }
        if (view == this.mNotificationsScrim) {
            return this.mNotificationsAlpha;
        }
        throw new IllegalArgumentException("Unknown scrim view");
    }

    public final int getCurrentScrimTint(View view) {
        if (view == this.mScrimInFront) {
            return this.mInFrontTint;
        }
        if (view == this.mScrimBehind) {
            return this.mBehindTint;
        }
        if (view == this.mNotificationsScrim) {
            return this.mNotificationsTint;
        }
        throw new IllegalArgumentException("Unknown scrim view");
    }

    public final float getInterpolatedFraction() {
        return this.mStatusBarKeyguardViewManager.isPrimaryBouncerInTransit() ? BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(this.mPanelExpansionFraction) : ShadeInterpolation.getNotificationScrimAlpha(this.mPanelExpansionFraction);
    }

    public final String getScrimName(ScrimView scrimView) {
        return scrimView == this.mScrimInFront ? "front_scrim" : scrimView == this.mScrimBehind ? "behind_scrim" : scrimView == this.mNotificationsScrim ? "notifications_scrim" : "unknown_scrim";
    }

    public final void internalTransitionTo(Callback callback, ScrimState scrimState) {
        ScrimState scrimState2;
        AODParameters aODParameters;
        if (this.mIsBouncerToGoneTransitionRunning) {
            Log.i("ScrimController", "Skipping transition to: " + scrimState + " while mIsBouncerToGoneTransitionRunning");
            return;
        }
        if (scrimState == this.mState) {
            if (callback == null || this.mCallback == callback) {
                return;
            }
            callback.onFinished();
            return;
        }
        if (DEBUG) {
            Log.d("ScrimController", "State changed to: " + scrimState);
        }
        ScrimStateLogger scrimStateLogger = this.mScrimColorState;
        if (scrimStateLogger != null) {
            scrimStateLogger.mForceChanged = true;
        }
        ScrimState scrimState3 = ScrimState.UNINITIALIZED;
        if (scrimState == scrimState3) {
            throw new IllegalArgumentException("Cannot change to UNINITIALIZED.");
        }
        SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
        secLsScrimControlHelper.mPreviousState = secLsScrimControlHelper.mState;
        secLsScrimControlHelper.mState = scrimState;
        ScrimState scrimState4 = this.mState;
        this.mState = scrimState;
        Trace.traceCounter(4096L, "scrim_state", scrimState.ordinal());
        Callback callback2 = this.mCallback;
        if (callback2 != null) {
            callback2.onCancelled();
        }
        this.mCallback = callback;
        SecLsScrimControlHelper secLsScrimControlHelper2 = this.mSecLsScrimControlHelper;
        secLsScrimControlHelper2.getClass();
        int i = SecLsScrimControlHelper.AnonymousClass4.$SwitchMap$com$android$systemui$statusbar$phone$ScrimState[scrimState.ordinal()];
        Lazy lazy = secLsScrimControlHelper2.mCoverHostLazy;
        if (i != 1) {
            Lazy lazy2 = secLsScrimControlHelper2.mDozeParametersLazy;
            if (i == 2) {
                scrimState.mClipQsScrim = false;
                scrimState.prepare(scrimState4);
                scrimState.mBehindAlpha = 0.0f;
                if (scrimState4 == ScrimState.AOD) {
                    if (LsRune.KEYGUARD_SCREEN_ON_FADE_OUT_ANIM) {
                        scrimState.mAnimationDuration = 600L;
                        scrimState.mAnimateChange = true;
                    } else {
                        AODParameters aODParameters2 = ((DozeParameters) lazy2.get()).mAODParameters;
                        if (aODParameters2 != null) {
                            scrimState.mAnimateChange = aODParameters2.mDozeUiState;
                        }
                    }
                    if (((PluginAODManager) secLsScrimControlHelper2.mPluginAODManagerLazy.get()).mIsDifferentOrientation) {
                        scrimState.mBlankScreen = true;
                    }
                } else if (scrimState4 == ScrimState.UNLOCKED) {
                    scrimState.mAnimateChange = false;
                    scrimState.mAnimationDuration = 0L;
                }
            } else if (i == 3) {
                scrimState.mClipQsScrim = false;
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = secLsScrimControlHelper2.mKeyguardFastBioUnlockController;
                boolean isFastWakeAndUnlockMode = keyguardFastBioUnlockController.isFastWakeAndUnlockMode();
                ScrimState scrimState5 = ScrimState.AOD;
                if (scrimState4 == scrimState5 && isFastWakeAndUnlockMode) {
                    scrimState.prepare(scrimState3);
                    if (keyguardFastBioUnlockController.needsBlankScreen) {
                        scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimInFront, -16777216);
                        scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimBehind, -16777216);
                        scrimState.mFrontTint = -16777216;
                        scrimState.mBehindTint = -16777216;
                        scrimState.mAnimateChange = true;
                        scrimState.mBlankScreen = true;
                        scrimState.mAnimationDuration = 50L;
                    } else {
                        scrimState.mBlankScreen = false;
                        scrimState.mAnimateChange = false;
                        scrimState.mAnimationDuration = 0L;
                    }
                } else if (scrimState4 == scrimState5) {
                    if (secLsScrimControlHelper2.mPowerInteractor.screenPowerState.$$delegate_0.getValue() == ScreenPowerState.SCREEN_ON && (aODParameters = ((DozeParameters) lazy2.get()).mAODParameters) != null && !aODParameters.mDozeUiState) {
                        Log.i("ScrimController", "shouldPreventBlankScreen Screen is fully on");
                    } else if (LsRune.COVER_SUPPORTED && secLsScrimControlHelper2.mWakefulnessLifecycle.mLastWakeReason == 103) {
                        Log.i("ScrimController", "shouldPreventBlankScreen cover is opening");
                    } else if (LsRune.AOD_FULLSCREEN && secLsScrimControlHelper2.mAodAmbientWallpaperHelper.isAODFullScreenAndShowing() && !((KeyguardStateControllerImpl) ((KeyguardStateController) secLsScrimControlHelper2.mKeyguardStateControllerLazy.get())).mShowing) {
                        Log.i("ScrimController", "shouldPreventBlankScreen AOD FullScreen and Lock None");
                    } else if (!secLsScrimControlHelper2.mKeyguardVisibilityMonitor.isVisible() || keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted) {
                        Log.i("ScrimController", "shouldPreventBlankScreen already keyguard is invisible or isInvisibleAfterGoingAwayTransStarted");
                    } else {
                        scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimInFront, -16777216);
                        scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimBehind, -16777216);
                        scrimState.mFrontTint = -16777216;
                        scrimState.mBehindTint = -16777216;
                        scrimState.mAnimationDuration = 300L;
                        scrimState.mBlankScreen = true;
                    }
                    scrimState.mBlankScreen = false;
                    scrimState.mAnimateChange = false;
                    scrimState.mAnimationDuration = 0L;
                } else {
                    scrimState.prepare(scrimState4);
                }
                if (scrimState4 != scrimState5) {
                    scrimState.mAnimateChange = false;
                } else if (!isFastWakeAndUnlockMode) {
                    scrimState.mAnimateChange = true;
                }
            } else if (i != 4) {
                scrimState.prepare(scrimState4);
            } else {
                scrimState.mClipQsScrim = false;
                scrimState.prepare(scrimState4);
                if (LsRune.COVER_SUPPORTED && scrimState4 == ScrimState.AOD && ((CoverHostImpl) ((CoverHost) lazy.get())).isNeedScrimAnimation()) {
                    scrimState.mBlankScreen = true;
                    scrimState.mAnimateChange = true;
                }
            }
        } else {
            scrimState.mClipQsScrim = false;
            scrimState.prepare(scrimState4);
            scrimState.mAnimationDuration = 500L;
            if (LsRune.AOD_LIGHT_REVEAL) {
                scrimState.mBehindAlpha = 0.0f;
                scrimState.mAnimateChange = false;
            } else {
                scrimState.mBehindAlpha = (!scrimState.mWallpaperSupportsAmbientMode || scrimState.mHasBackdrop) ? 1.0f : 0.0f;
            }
            if (LsRune.COVER_SUPPORTED && !((CoverHostImpl) ((CoverHost) lazy.get())).isNeedScrimAnimation()) {
                scrimState.mBlankScreen = false;
                scrimState.mAnimateChange = false;
                scrimState.mAnimationDuration = 0L;
            }
        }
        this.mScreenBlankingCallbackCalled = false;
        this.mAnimationDelay = 0L;
        boolean z = scrimState.mBlankScreen;
        this.mBlankScreen = z;
        this.mAnimateChange = scrimState.mAnimateChange;
        this.mAnimationDuration = scrimState.mAnimationDuration;
        com.android.systemui.keyguard.Log.d("ScrimController", "State changed to %s, blankScreen=%s, animation=%d", this.mState, Boolean.valueOf(z), Long.valueOf(this.mAnimateChange ? this.mAnimationDuration : -1L));
        if (this.mState == ScrimState.GLANCEABLE_HUB_OVER_DREAM) {
            this.mPanelExpansionFraction = 0.0f;
        }
        applyState$1$1();
        this.mScrimInFront.mBlendWithMainColor = scrimState.shouldBlendWithMainColor();
        ScrimController$$ExternalSyntheticLambda0 scrimController$$ExternalSyntheticLambda0 = this.mPendingFrameCallback;
        if (scrimController$$ExternalSyntheticLambda0 != null) {
            this.mScrimBehind.removeCallbacks(scrimController$$ExternalSyntheticLambda0);
            this.mPendingFrameCallback = null;
        }
        if (this.mHandler.hasCallbacks(this.mBlankingTransitionRunnable)) {
            this.mHandler.removeCallbacks(this.mBlankingTransitionRunnable);
            this.mBlankingTransitionRunnable = null;
        }
        this.mNeedsDrawableColorUpdate = scrimState != ScrimState.BRIGHTNESS_MIRROR;
        if (this.mState.isLowPowerState() && !this.mWakeLockHeld) {
            DelayedWakeLock delayedWakeLock = this.mWakeLock;
            if (delayedWakeLock != null) {
                this.mWakeLockHeld = true;
                delayedWakeLock.acquire("ScrimController");
            } else {
                Log.w("ScrimController", "Cannot hold wake lock, it has not been set yet");
            }
        }
        this.mWallpaperVisibilityTimedOut = false;
        if (this.mWallpaperSupportsAmbientMode && this.mState == ScrimState.AOD) {
            if (this.mDozeParameters.getAlwaysOn()) {
                this.mKeyguardUpdateMonitor.getClass();
                if (!this.mAODAmbientWallpaperHelper.isAODFullScreenMode()) {
                    DejankUtils.postAfterTraversal(new ScrimController$$ExternalSyntheticLambda0(this, 3));
                    if (this.mKeyguardUpdateMonitor.mNeedsSlowUnlockTransition || this.mState != ScrimState.UNLOCKED) {
                        scrimState2 = ScrimState.AOD;
                        if (((scrimState4 != scrimState2 || scrimState4 == ScrimState.PULSING) && (!this.mDozeParameters.getAlwaysOn() || this.mState == ScrimState.UNLOCKED)) || (this.mState == scrimState2 && !this.mDozeParameters.getDisplayNeedsBlanking())) {
                            onPreDraw();
                        } else {
                            scheduleUpdate$1();
                        }
                    } else {
                        this.mAnimationDelay = 100L;
                        scheduleUpdate$1();
                    }
                    dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
                }
            } else {
                this.mDockManager.getClass();
            }
        }
        AlarmTimeout alarmTimeout = this.mTimeTicker;
        Objects.requireNonNull(alarmTimeout);
        DejankUtils.postAfterTraversal(new ScrimController$$ExternalSyntheticLambda0(alarmTimeout, 5));
        if (this.mKeyguardUpdateMonitor.mNeedsSlowUnlockTransition) {
        }
        scrimState2 = ScrimState.AOD;
        if (scrimState4 != scrimState2) {
        }
        onPreDraw();
        dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
    }

    public final void legacyTransitionTo(ScrimState scrimState) {
        SceneContainerFlag.assertInLegacyMode();
        internalTransitionTo(null, scrimState);
    }

    public final void onFinished(Callback callback, ScrimState scrimState) {
        if (this.mPendingFrameCallback != null) {
            return;
        }
        if (isAnimating(this.mScrimBehind) || isAnimating(this.mNotificationsScrim) || isAnimating(this.mScrimInFront)) {
            if (callback == null || callback == this.mCallback) {
                return;
            }
            callback.onFinished();
            return;
        }
        if (this.mWakeLockHeld) {
            this.mWakeLock.release("ScrimController");
            this.mWakeLockHeld = false;
        }
        if (callback != null) {
            callback.onFinished();
            if (callback == this.mCallback) {
                this.mCallback = null;
            }
        }
        if (scrimState == ScrimState.UNLOCKED) {
            this.mInFrontTint = 0;
            ScrimState scrimState2 = this.mState;
            this.mBehindTint = scrimState2.mBehindTint;
            this.mNotificationsTint = scrimState2.mNotifTint;
            updateScrimColor(this.mInFrontAlpha, 0, this.mScrimInFront);
            updateScrimColor(this.mBehindAlpha, this.mBehindTint, this.mScrimBehind);
            updateScrimColor(this.mNotificationsAlpha, this.mNotificationsTint, this.mNotificationsScrim);
        }
    }

    public void onHideWallpaperTimeout() {
        ScrimState scrimState = this.mState;
        if (scrimState == ScrimState.AOD || scrimState == ScrimState.PULSING) {
            if (!this.mWakeLockHeld) {
                DelayedWakeLock delayedWakeLock = this.mWakeLock;
                if (delayedWakeLock != null) {
                    this.mWakeLockHeld = true;
                    delayedWakeLock.acquire("ScrimController");
                } else {
                    Log.w("ScrimController", "Cannot hold wake lock, it has not been set yet");
                }
            }
            this.mWallpaperVisibilityTimedOut = true;
            this.mAnimateChange = this.mScrimsVisibility != 0;
            this.mAnimationDuration = this.mDozeParameters.mAlwaysOnPolicy.wallpaperFadeOutDuration;
            scheduleUpdate$1();
        }
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        this.mScrimBehind.getViewTreeObserver().removeOnPreDrawListener(this);
        this.mUpdatePending = false;
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.getClass();
        }
        updateScrims();
        return true;
    }

    public final void onScreenTurnedOn() {
        this.mScreenOn = true;
        if (this.mHandler.hasCallbacks(this.mBlankingTransitionRunnable)) {
            if (DEBUG) {
                Log.d("ScrimController", "Shorter blanking because screen turned on. All good.");
            }
            this.mHandler.removeCallbacks(this.mBlankingTransitionRunnable);
            this.mBlankingTransitionRunnable.run();
        }
    }

    public final void scheduleUpdate$1() {
        ScrimView scrimView;
        if (this.mUpdatePending || (scrimView = this.mScrimBehind) == null) {
            return;
        }
        scrimView.invalidate();
        this.mScrimBehind.getViewTreeObserver().addOnPreDrawListener(this);
        this.mUpdatePending = true;
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.mAnimatorListener = animatorListener;
    }

    public final void setOccludeAnimationPlaying(boolean z) {
        this.mOccludeAnimationPlaying = z;
        for (ScrimState scrimState : ScrimState.values()) {
            scrimState.mOccludeAnimationPlaying = z;
        }
        applyAndDispatchState();
    }

    public final void setOrAdaptCurrentAnimation(View view) {
        if (view == null) {
            return;
        }
        SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
        if (!this.mBlankScreen) {
            secLsScrimControlHelper.getClass();
        } else if (secLsScrimControlHelper.mState == ScrimState.UNLOCKED && (view == secLsScrimControlHelper.mScrimInFront || view == secLsScrimControlHelper.mScrimBehind)) {
            Log.i("ScrimController", "skip setOrAdaptCurrentAnimation");
            return;
        }
        float currentScrimAlpha = getCurrentScrimAlpha(view);
        boolean z = view == this.mScrimBehind && this.mQsBottomVisible;
        if (!isAnimating(view) || z) {
            updateScrimColor(currentScrimAlpha, getCurrentScrimTint(view), view);
            return;
        }
        ValueAnimator valueAnimator = (ValueAnimator) view.getTag(TAG_KEY_ANIM);
        int i = TAG_END_ALPHA;
        float floatValue = ((Float) view.getTag(i)).floatValue();
        int i2 = TAG_START_ALPHA;
        view.setTag(i2, Float.valueOf((currentScrimAlpha - floatValue) + ((Float) view.getTag(i2)).floatValue()));
        view.setTag(i, Float.valueOf(currentScrimAlpha));
        valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setScrimAlpha(final com.android.systemui.scrim.ScrimView r10, float r11) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ScrimController.setScrimAlpha(com.android.systemui.scrim.ScrimView, float):void");
    }

    public final void setWakeLockScreenSensorActive(boolean z) {
        for (ScrimState scrimState : ScrimState.values()) {
            scrimState.mWakeLockScreenSensorActive = z;
        }
        ScrimState scrimState2 = this.mState;
        if (scrimState2 == ScrimState.PULSING) {
            float f = scrimState2.mBehindAlpha;
            if (this.mBehindAlpha != f) {
                this.mBehindAlpha = f;
                if (!Float.isNaN(f)) {
                    updateScrims();
                    return;
                }
                throw new IllegalStateException("Scrim opacity is NaN for state: " + this.mState + ", back: " + this.mBehindAlpha);
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mJavaAdapter.alwaysCollectFlow(((WallpaperRepositoryImpl) this.mWallpaperRepository).wallpaperSupportsAmbientMode, new ScrimController$$ExternalSyntheticLambda3(this, 1));
    }

    public final void updateScrimColor(float f, int i, View view) {
        ScrimStateLogger scrimStateLogger = this.mScrimColorState;
        if (scrimStateLogger != null) {
            int i2 = this.mScrimsVisibility;
            int i3 = 0;
            for (ScrimViewBase scrimViewBase : scrimStateLogger.mScrimViews) {
                ScrimView scrimView = (ScrimView) scrimViewBase;
                scrimStateLogger.mColors[i3] = scrimView.getMainColor();
                scrimStateLogger.mAlphas[i3] = scrimView.mViewAlpha;
                i3++;
            }
            scrimStateLogger.mScrimVisibility = i2;
        }
        float max = Math.max(0.0f, Math.min(1.0f, f));
        if (view instanceof ScrimView) {
            ScrimView scrimView2 = (ScrimView) view;
            Trace.traceCounter(4096L, getScrimName(scrimView2).concat("_alpha"), (int) (255.0f * max));
            Trace.traceCounter(4096L, getScrimName(scrimView2).concat("_tint"), Color.alpha(i));
            SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
            if (scrimView2 == secLsScrimControlHelper.mScrimInFront && (secLsScrimControlHelper.mState == ScrimState.BOUNCER_SCRIMMED || secLsScrimControlHelper.needUpdateScrimColor())) {
                SecLsScrimControlHelper secLsScrimControlHelper2 = this.mSecLsScrimControlHelper;
                scrimView2.setTint(secLsScrimControlHelper2.mScrimBouncerColor);
                if (secLsScrimControlHelper2.needUpdateScrimColor()) {
                    scrimView2.setViewAlpha(0.0f);
                } else {
                    scrimView2.setViewAlpha(max);
                }
            } else {
                scrimView2.setTint(i);
                if (!this.mIsBouncerToGoneTransitionRunning) {
                    scrimView2.setViewAlpha(max);
                }
            }
        } else {
            view.setAlpha(max);
        }
        dispatchScrimsVisible();
        ScrimStateLogger scrimStateLogger2 = this.mScrimColorState;
        if (scrimStateLogger2 != null) {
            scrimStateLogger2.logScrimColor(DEBUG);
        }
    }

    public final void updateScrims() {
        ScrimState scrimState;
        if (this.mNeedsDrawableColorUpdate) {
            this.mNeedsDrawableColorUpdate = false;
            boolean z = (this.mScrimInFront.mViewAlpha == 0.0f || this.mBlankScreen) ? false : true;
            boolean z2 = (this.mScrimBehind.mViewAlpha == 0.0f || this.mBlankScreen) ? false : true;
            boolean z3 = (this.mNotificationsScrim.mViewAlpha == 0.0f || this.mBlankScreen) ? false : true;
            SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
            secLsScrimControlHelper.getClass();
            int i = KeyguardFastBioUnlockController.MODE_FLAG_ENABLED;
            if (secLsScrimControlHelper.mKeyguardFastBioUnlockController.isMode(i)) {
                StringBuilder sb = new StringBuilder("updateScrims: isWakeAndUnlockAnimationAODFullScreenMode()=");
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController;
                int i2 = KeyguardFastBioUnlockController.MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN;
                sb.append(keyguardFastBioUnlockController.isMode(i | i2));
                Log.d("ScrimController", sb.toString());
                if (this.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController.isMode(i | i2)) {
                    this.mScrimInFront.setViewAlpha(0.0f);
                }
                z = false;
                z2 = false;
            }
            SecLsScrimControlHelper secLsScrimControlHelper2 = this.mSecLsScrimControlHelper;
            ScrimState scrimState2 = secLsScrimControlHelper2.mState;
            if (scrimState2 == ScrimState.BOUNCER_SCRIMMED || scrimState2 == ScrimState.BOUNCER) {
                secLsScrimControlHelper2.mScrimInFront.setColors(secLsScrimControlHelper2.mBouncerColors, false);
                secLsScrimControlHelper2.mScrimBehind.setColors(secLsScrimControlHelper2.mBouncerColors, false);
            } else {
                this.mScrimInFront.setColors(this.mColors, z);
                this.mScrimBehind.setColors(this.mColors, z2);
                this.mNotificationsScrim.setColors(this.mColors, z3);
            }
            dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
        }
        ScrimState scrimState3 = this.mState;
        ScrimState scrimState4 = ScrimState.AOD;
        boolean z4 = (scrimState3 == scrimState4 || scrimState3 == ScrimState.PULSING) && this.mWallpaperVisibilityTimedOut;
        boolean z5 = (scrimState3 == ScrimState.PULSING || scrimState3 == scrimState4) && this.mKeyguardOccluded;
        if ((z4 || z5) && (!LsRune.AOD_FULLSCREEN || !this.mAODAmbientWallpaperHelper.isAODFullScreenMode())) {
            this.mBehindAlpha = 1.0f;
        }
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
            this.mNotificationsAlpha = 0.0f;
        }
        if (this.mKeyguardOccluded && ((scrimState = this.mState) == ScrimState.KEYGUARD || scrimState == ScrimState.SHADE_LOCKED)) {
            this.mBehindAlpha = 0.0f;
            this.mNotificationsAlpha = 0.0f;
        }
        setScrimAlpha(this.mScrimInFront, this.mInFrontAlpha);
        setScrimAlpha(this.mScrimBehind, this.mBehindAlpha);
        setScrimAlpha(this.mNotificationsScrim, this.mNotificationsAlpha);
        onFinished(this.mCallback, this.mState);
        dispatchScrimsVisible();
    }

    public final void updateThemeColors() {
        ScrimView scrimView = this.mScrimBehind;
        if (scrimView == null) {
            return;
        }
        int defaultColor = Utils.getColorAttr(android.R.^attr-private.materialColorTertiary, scrimView.getContext()).getDefaultColor();
        int defaultColor2 = Utils.getColorAttr(android.R.^attr-private.materialColorPrimaryFixedDim, this.mScrimBehind.getContext()).getDefaultColor();
        this.mColors.setMainColor(defaultColor);
        this.mColors.setSecondaryColor(defaultColor2);
        this.mColors.setSupportsDarkText(!ContrastColorUtil.isColorDark(defaultColor));
        int defaultColor3 = Utils.getColorAttr(android.R.^attr-private.materialColorSurfaceContainerHigh, this.mScrimBehind.getContext()).getDefaultColor();
        for (ScrimState scrimState : ScrimState.values()) {
            scrimState.setSurfaceColor(defaultColor3);
        }
        this.mNeedsDrawableColorUpdate = true;
    }

    public interface Callback {
        void onFinished();

        default void onCancelled() {
        }

        default void onDisplayBlanked() {
        }
    }
}
