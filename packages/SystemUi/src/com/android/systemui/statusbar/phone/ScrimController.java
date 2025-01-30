package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.p009ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda4;
import com.android.systemui.scrim.ScrimViewBase;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.QSScrimViewSwitch;
import com.android.systemui.statusbar.phone.ScrimStateLogger;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScrimController implements ViewTreeObserver.OnPreDrawListener, Dumpable {
    public AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public boolean mAnimateChange;
    public boolean mAnimatingPanelExpansionOnUnlock;
    public long mAnimationDelay;
    public Animator.AnimatorListener mAnimatorListener;
    public int mBehindTint;
    public boolean mBlankScreen;
    public ScrimController$$ExternalSyntheticLambda0 mBlankingTransitionRunnable;
    public Callback mCallback;
    public final ColorExtractor.GradientColors mColors;
    public boolean mDarkenWhileDragging;
    public final float mDefaultScrimAlpha;
    public final DockManager mDockManager;
    public final DozeParameters mDozeParameters;
    public final FeatureFlags mFeatureFlags;
    public final Handler mHandler;
    public int mInFrontTint;
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
    public ScrimController$$ExternalSyntheticLambda2 mPrimaryBouncerToGoneTransition;
    public final PrimaryBouncerToGoneTransitionViewModel mPrimaryBouncerToGoneTransitionViewModel;
    public boolean mQsBottomVisible;
    public float mQsExpansion;
    public float mRawPanelExpansionFraction;
    public boolean mScreenBlankingCallbackCalled;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public boolean mScreenOn;
    public final ScrimController$$ExternalSyntheticLambda2 mScrimAlphaConsumer;
    public ScrimView mScrimBehind;
    public Runnable mScrimBehindChangeRunnable;
    public ScrimStateLogger mScrimColorState;
    public ScrimView mScrimInFront;
    public QSScrimViewSwitch.C30981 mScrimStateCallback;
    public final ScrimController$$ExternalSyntheticLambda3 mScrimStateListener;
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
    public final boolean mUseNewLightBarLogic;
    public final DelayedWakeLock mWakeLock;
    public boolean mWakeLockHeld;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.ScrimController$3 */
    public final class C31063 implements ScrimStateLogger.Callback {
        public C31063() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeyguardVisibilityCallback extends KeyguardUpdateMonitorCallback {
        public /* synthetic */ KeyguardVisibilityCallback(ScrimController scrimController, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            ScrimController scrimController = ScrimController.this;
            scrimController.mNeedsDrawableColorUpdate = true;
            scrimController.scheduleUpdate();
        }

        private KeyguardVisibilityCallback() {
        }
    }

    public ScrimController(LightBarController lightBarController, DozeParameters dozeParameters, AlarmManager alarmManager, final KeyguardStateController keyguardStateController, DelayedWakeLock.Builder builder, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, ConfigurationController configurationController, Executor executor, ScreenOffAnimationController screenOffAnimationController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, LargeScreenShadeInterpolator largeScreenShadeInterpolator, FeatureFlags featureFlags) {
        int i = 0;
        this.mScrimAlphaConsumer = new ScrimController$$ExternalSyntheticLambda2(this, i);
        Objects.requireNonNull(lightBarController);
        this.mScrimStateListener = new ScrimController$$ExternalSyntheticLambda3(lightBarController);
        this.mLargeScreenShadeInterpolator = largeScreenShadeInterpolator;
        this.mFeatureFlags = featureFlags;
        this.mUseNewLightBarLogic = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.NEW_LIGHT_BAR_LOGIC) && !BasicRune.NAVBAR_LIGHTBAR;
        this.mDefaultScrimAlpha = 1.0f;
        this.mKeyguardStateController = keyguardStateController;
        this.mDarkenWhileDragging = !r4.mCanDismissLockScreen;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardVisibilityCallback = new KeyguardVisibilityCallback(this, i);
        this.mHandler = handler;
        this.mMainExecutor = executor;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda4
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                ScrimController.this.onHideWallpaperTimeout();
            }
        }, "hide_aod_wallpaper", handler);
        builder.mHandler = handler;
        builder.mTag = "Scrims";
        this.mWakeLock = new DelayedWakeLock(handler, WakeLock.createPartial(builder.mContext, builder.mLogger, "Scrims"));
        this.mDozeParameters = dozeParameters;
        this.mDockManager = dockManager;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.ScrimController.1
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
                scrimController.scheduleUpdate();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                boolean z = ScrimController.DEBUG;
                ScrimController scrimController = ScrimController.this;
                scrimController.updateThemeColors();
                scrimController.scheduleUpdate();
            }
        });
        this.mColors = new ColorExtractor.GradientColors();
        this.mPrimaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mMainDispatcher = coroutineDispatcher;
    }

    public final void applyAndDispatchState() {
        applyState();
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
    */
    public final void applyState() {
        boolean z;
        ScrimState scrimState;
        SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
        secLsScrimControlHelper.getClass();
        int i = SecLsScrimControlHelper.AbstractC31224.$SwitchMap$com$android$systemui$statusbar$phone$ScrimState[secLsScrimControlHelper.mState.ordinal()];
        if (i != 1 && i != 2) {
            if (i != 3 || ((scrimState = secLsScrimControlHelper.mPreviousState) != ScrimState.AOD && scrimState != ScrimState.KEYGUARD && scrimState != ScrimState.BOUNCER && scrimState != ScrimState.BOUNCER_SCRIMMED && scrimState != ScrimState.DREAMING)) {
                z = false;
                ScrimState scrimState2 = this.mState;
                this.mInFrontTint = scrimState2.mFrontTint;
                this.mBehindTint = scrimState2.mBehindTint;
                this.mNotificationsTint = scrimState2.mNotifTint;
                this.mInFrontAlpha = scrimState2.mFrontAlpha;
                this.mBehindAlpha = scrimState2.mBehindAlpha;
                this.mNotificationsAlpha = scrimState2.mNotifAlpha;
                assertAlphasValid();
                if (this.mExpansionAffectsAlpha) {
                    return;
                }
                if (z) {
                    if (this.mState != ScrimState.UNLOCKED) {
                        this.mAnimatingPanelExpansionOnUnlock = false;
                        return;
                    }
                    return;
                }
                ScrimState scrimState3 = this.mState;
                ScrimState scrimState4 = ScrimState.UNLOCKED;
                if (scrimState3 == scrimState4 || scrimState3 == ScrimState.DREAMING) {
                    if (!this.mOccludeAnimationPlaying && !scrimState3.mLaunchingAffordanceWithPreview) {
                        r2 = false;
                    }
                    if (!this.mScreenOffAnimationController.shouldExpandNotifications() && !this.mAnimatingPanelExpansionOnUnlock && !r2) {
                        if (this.mTransparentScrimBackground) {
                            this.mBehindAlpha = 0.0f;
                            this.mNotificationsAlpha = 0.0f;
                        } else if (((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.LARGE_SHADE_GRANULAR_ALPHA_INTERPOLATION)) {
                            this.mBehindAlpha = this.mLargeScreenShadeInterpolator.getBehindScrimAlpha(this.mPanelExpansionFraction * this.mDefaultScrimAlpha);
                            this.mNotificationsAlpha = this.mLargeScreenShadeInterpolator.getNotificationScrimAlpha(this.mPanelExpansionFraction);
                        } else {
                            this.mBehindAlpha = MathUtils.constrainedMap(0.0f, 1.0f, 0.0f, 0.3f, this.mPanelExpansionFraction) * this.mDefaultScrimAlpha;
                            this.mNotificationsAlpha = MathUtils.constrainedMap(0.0f, 1.0f, 0.3f, 0.75f, this.mPanelExpansionFraction);
                        }
                        this.mBehindTint = this.mState.mBehindTint;
                        this.mInFrontAlpha = 0.0f;
                    }
                    if (this.mState == ScrimState.DREAMING) {
                        float f = this.mBouncerHiddenFraction;
                        if (f != 1.0f) {
                            float aboutToShowBouncerProgress = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f);
                            this.mBehindAlpha = MathUtils.lerp(this.mDefaultScrimAlpha, this.mBehindAlpha, aboutToShowBouncerProgress);
                            this.mBehindTint = ColorUtils.blendARGB(ScrimState.BOUNCER.mBehindTint, this.mBehindTint, aboutToShowBouncerProgress);
                        }
                    }
                } else if (scrimState3 == ScrimState.AUTH_SCRIMMED_SHADE) {
                    this.mNotificationsAlpha = (float) Math.pow(getInterpolatedFraction(), 0.800000011920929d);
                } else {
                    ScrimState scrimState5 = ScrimState.KEYGUARD;
                    if (scrimState3 == scrimState5 || scrimState3 == ScrimState.SHADE_LOCKED || scrimState3 == ScrimState.PULSING) {
                        Pair calculateBackStateForState = calculateBackStateForState(scrimState3);
                        int intValue = ((Integer) calculateBackStateForState.first).intValue();
                        float floatValue = ((Float) calculateBackStateForState.second).floatValue();
                        if (this.mTransitionToFullShadeProgress > 0.0f) {
                            Pair calculateBackStateForState2 = calculateBackStateForState(ScrimState.SHADE_LOCKED);
                            floatValue = MathUtils.lerp(floatValue, ((Float) calculateBackStateForState2.second).floatValue(), this.mTransitionToFullShadeProgress);
                            intValue = ColorUtils.blendARGB(intValue, ((Integer) calculateBackStateForState2.first).intValue(), this.mTransitionToFullShadeProgress);
                        }
                        ScrimState scrimState6 = this.mState;
                        this.mInFrontAlpha = scrimState6.mFrontAlpha;
                        this.mBehindAlpha = floatValue;
                        if (scrimState6 == scrimState5 && this.mTransitionToFullShadeProgress > 0.0f) {
                            this.mNotificationsAlpha = MathUtils.saturate(this.mTransitionToLockScreenFullShadeNotificationsProgress);
                        } else if (scrimState6 == ScrimState.SHADE_LOCKED) {
                            this.mNotificationsAlpha = getInterpolatedFraction();
                        } else {
                            this.mNotificationsAlpha = Math.max(1.0f - getInterpolatedFraction(), this.mQsExpansion);
                        }
                        ScrimState scrimState7 = this.mState;
                        this.mNotificationsTint = scrimState7.mNotifTint;
                        this.mBehindTint = intValue;
                        r2 = scrimState7 == scrimState5 && this.mTransitionToFullShadeProgress == 0.0f && this.mQsExpansion == 0.0f;
                        if (this.mKeyguardOccluded || r2) {
                            this.mNotificationsAlpha = 0.0f;
                        }
                    }
                }
                if (this.mState != scrimState4) {
                    this.mAnimatingPanelExpansionOnUnlock = false;
                }
                assertAlphasValid();
                return;
            }
            secLsScrimControlHelper.mState.mBehindAlpha = 0.0f;
        }
        z = true;
        ScrimState scrimState22 = this.mState;
        this.mInFrontTint = scrimState22.mFrontTint;
        this.mBehindTint = scrimState22.mBehindTint;
        this.mNotificationsTint = scrimState22.mNotifTint;
        this.mInFrontAlpha = scrimState22.mFrontAlpha;
        this.mBehindAlpha = scrimState22.mBehindAlpha;
        this.mNotificationsAlpha = scrimState22.mNotifAlpha;
        assertAlphasValid();
        if (this.mExpansionAffectsAlpha) {
        }
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
            boolean z = true;
            if (f != 0.0f && this.mKeyguardUnlockAnimationController.playingCannedUnlockAnimation) {
                this.mAnimatingPanelExpansionOnUnlock = true;
            } else if (f == 0.0f) {
                this.mAnimatingPanelExpansionOnUnlock = false;
            }
            this.mPanelExpansionFraction = f;
            ScrimState scrimState = this.mState;
            if (scrimState != ScrimState.UNLOCKED && scrimState != ScrimState.KEYGUARD && scrimState != ScrimState.DREAMING && scrimState != ScrimState.SHADE_LOCKED && scrimState != ScrimState.PULSING) {
                z = false;
            }
            if (z && this.mExpansionAffectsAlpha && !this.mAnimatingPanelExpansionOnUnlock) {
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
        if (this.mUseNewLightBarLogic) {
            this.mScrimStateListener.accept(this.mState, Float.valueOf(f), this.mColors);
            return;
        }
        ScrimController$$ExternalSyntheticLambda3 scrimController$$ExternalSyntheticLambda3 = this.mScrimStateListener;
        ScrimState scrimState = this.mState;
        Float valueOf = Float.valueOf(f);
        ScrimView scrimView = this.mScrimInFront;
        synchronized (scrimView.mColorLock) {
            scrimView.mTmpColors.set(scrimView.mColors);
        }
        scrimController$$ExternalSyntheticLambda3.accept(scrimState, valueOf, scrimView.mTmpColors);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchScrimsVisible() {
        int i;
        ScrimView scrimView = this.mScrimBehind;
        float f = this.mScrimInFront.mViewAlpha;
        if (f != 1.0f) {
            float f2 = scrimView.mViewAlpha;
            if (f2 != 1.0f) {
                i = (f == 0.0f && f2 == 0.0f) ? 0 : 1;
                if (this.mScrimsVisibility == i) {
                    this.mScrimsVisibility = i;
                    SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
                    boolean z = this.mScreenOn;
                    secLsScrimControlHelper.getClass();
                    ViewRootImpl viewRootImpl = (z || i != 2) ? null : secLsScrimControlHelper.mScrimInFront.getViewRootImpl();
                    if (LsRune.AOD_SUB_DISPLAY_COVER && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                        viewRootImpl = secLsScrimControlHelper.mScrimBehind.getViewRootImpl();
                    }
                    if (viewRootImpl != null) {
                        viewRootImpl.setReportNextDraw(false, "scrim");
                    }
                    this.mScrimVisibleListener.accept(Integer.valueOf(i));
                    return;
                }
                return;
            }
        }
        i = 2;
        if (this.mScrimsVisibility == i) {
        }
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

    public final boolean isAnimating(View view) {
        return (view == null || view.getTag(TAG_KEY_ANIM) == null) ? false : true;
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
            scheduleUpdate();
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

    public final void scheduleUpdate() {
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setOrAdaptCurrentAnimation(ScrimView scrimView) {
        boolean z;
        if (scrimView == null) {
            return;
        }
        SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
        if (!this.mBlankScreen) {
            secLsScrimControlHelper.getClass();
        } else if (secLsScrimControlHelper.mState == ScrimState.UNLOCKED && (scrimView == secLsScrimControlHelper.mScrimInFront || scrimView == secLsScrimControlHelper.mScrimBehind)) {
            z = true;
            if (!z) {
                Log.i("ScrimController", "skip setOrAdaptCurrentAnimation");
                return;
            }
            float currentScrimAlpha = getCurrentScrimAlpha(scrimView);
            boolean z2 = scrimView == this.mScrimBehind && this.mQsBottomVisible;
            if (!isAnimating(scrimView) || z2) {
                updateScrimColor(currentScrimAlpha, getCurrentScrimTint(scrimView), scrimView);
                return;
            }
            ValueAnimator valueAnimator = (ValueAnimator) scrimView.getTag(TAG_KEY_ANIM);
            int i = TAG_END_ALPHA;
            float floatValue = ((Float) scrimView.getTag(i)).floatValue();
            int i2 = TAG_START_ALPHA;
            scrimView.setTag(i2, Float.valueOf((currentScrimAlpha - floatValue) + ((Float) scrimView.getTag(i2)).floatValue()));
            scrimView.setTag(i, Float.valueOf(currentScrimAlpha));
            valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
            return;
        }
        z = false;
        if (!z) {
        }
    }

    public final void setScrimAlpha(float f, final ScrimView scrimView) {
        Callback callback;
        if (f == 0.0f) {
            scrimView.setClickable(false);
        } else {
            scrimView.setClickable(this.mState != ScrimState.AOD);
        }
        float f2 = scrimView.mViewAlpha;
        int i = TAG_KEY_ANIM;
        ViewState.C29791 c29791 = ViewState.NO_NEW_ANIMATIONS;
        ValueAnimator valueAnimator = (ValueAnimator) scrimView.getTag(i);
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (this.mPendingFrameCallback != null) {
            return;
        }
        if (this.mBlankScreen) {
            updateScrimColor(1.0f, EmergencyPhoneWidget.BG_COLOR, this.mScrimInFront);
            ScrimController$$ExternalSyntheticLambda0 scrimController$$ExternalSyntheticLambda0 = new ScrimController$$ExternalSyntheticLambda0(this, r1);
            this.mPendingFrameCallback = scrimController$$ExternalSyntheticLambda0;
            doOnTheNextFrame(scrimController$$ExternalSyntheticLambda0);
            return;
        }
        if (!this.mScreenBlankingCallbackCalled && (callback = this.mCallback) != null) {
            callback.onDisplayBlanked();
            this.mScreenBlankingCallbackCalled = true;
        }
        if (scrimView == this.mScrimBehind) {
            dispatchBackScrimState(f);
        }
        boolean z = f != f2;
        r1 = scrimView.mTintColor == getCurrentScrimTint(scrimView) ? 0 : 1;
        if (z || r1 != 0) {
            if (!this.mAnimateChange) {
                updateScrimColor(f, getCurrentScrimTint(scrimView), scrimView);
                return;
            }
            if (DEBUG) {
                LogUtil.m223d("ScrimController", "startScrimAnimation %s %f %d %d", getScrimName(scrimView), Float.valueOf(f2), Long.valueOf(this.mAnimationDelay), Long.valueOf(this.mAnimationDuration));
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            Animator.AnimatorListener animatorListener = this.mAnimatorListener;
            if (animatorListener != null) {
                ofFloat.addListener(animatorListener);
            }
            final int i2 = scrimView.mTintColor;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ScrimController scrimController = ScrimController.this;
                    View view = scrimView;
                    int i3 = i2;
                    scrimController.getClass();
                    float floatValue = ((Float) view.getTag(ScrimController.TAG_START_ALPHA)).floatValue();
                    float floatValue2 = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    scrimController.updateScrimColor(MathUtils.constrain(MathUtils.lerp(floatValue, scrimController.getCurrentScrimAlpha(view), floatValue2), 0.0f, 1.0f), ColorUtils.blendARGB(i3, scrimController.getCurrentScrimTint(view), floatValue2), view);
                    scrimController.dispatchScrimsVisible();
                }
            });
            ofFloat.setInterpolator(this.mInterpolator);
            ofFloat.setStartDelay(this.mAnimationDelay);
            ofFloat.setDuration(this.mAnimationDuration);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ScrimController.4
                public final Callback mLastCallback;
                public final ScrimState mLastState;

                {
                    this.mLastState = ScrimController.this.mState;
                    this.mLastCallback = ScrimController.this.mCallback;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    StringBuilder sb = new StringBuilder("onAnimationEnd ");
                    ScrimController scrimController = ScrimController.this;
                    View view = scrimView;
                    boolean z2 = ScrimController.DEBUG;
                    scrimController.getClass();
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb, view instanceof ScrimView ? scrimController.getScrimName((ScrimView) view) : view.toString(), "ScrimController");
                    ScrimStateLogger scrimStateLogger = ScrimController.this.mScrimColorState;
                    if (scrimStateLogger != null) {
                        scrimStateLogger.logScrimColor(true);
                    }
                    scrimView.setTag(ScrimController.TAG_KEY_ANIM, null);
                    ScrimController.this.onFinished(this.mLastCallback, this.mLastState);
                    ScrimController.this.dispatchScrimsVisible();
                    SecLsScrimControlHelper secLsScrimControlHelper = ScrimController.this.mSecLsScrimControlHelper;
                    View view2 = scrimView;
                    ScrimView scrimView2 = secLsScrimControlHelper.mScrimBehind;
                    if (view2 == scrimView2) {
                        ScrimState scrimState = secLsScrimControlHelper.mState;
                        if (scrimState == ScrimState.AOD) {
                            ((PluginAODManager) secLsScrimControlHelper.mPluginAODManagerLazy.get()).onAodTransitionEnd();
                        } else if (scrimState == ScrimState.UNLOCKED) {
                            secLsScrimControlHelper.mProvider.mDispatchBackScrimStateConsumer.accept(Float.valueOf(scrimView2.mViewAlpha));
                        }
                    }
                }
            });
            scrimView.setTag(TAG_START_ALPHA, Float.valueOf(f2));
            scrimView.setTag(TAG_END_ALPHA, Float.valueOf(getCurrentScrimAlpha(scrimView)));
            scrimView.setTag(i, ofFloat);
            ofFloat.start();
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:66:0x025e, code lost:
    
        if (r16.mKeyguardUpdateMonitor.hasLockscreenWallpaper() != false) goto L120;
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transitionTo(Callback callback, ScrimState scrimState) {
        boolean z;
        ScrimController$$ExternalSyntheticLambda0 scrimController$$ExternalSyntheticLambda0;
        ScrimState scrimState2;
        DelayedWakeLock delayedWakeLock;
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
        QSScrimViewSwitch qSScrimViewSwitch = QSScrimViewSwitch.this;
        if (qSScrimViewSwitch.mScrimState != scrimState) {
            Objects.toString(scrimState);
            qSScrimViewSwitch.updateScrimVisibility();
            qSScrimViewSwitch.mScrimState = scrimState;
        }
        ScrimStateLogger scrimStateLogger = this.mScrimColorState;
        boolean z2 = true;
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
        int i = SecLsScrimControlHelper.AbstractC31224.$SwitchMap$com$android$systemui$statusbar$phone$ScrimState[scrimState.ordinal()];
        int i2 = 0;
        Lazy lazy = secLsScrimControlHelper2.mCoverHostLazy;
        if (i == 1) {
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
        } else if (i == 2) {
            scrimState.mClipQsScrim = false;
            scrimState.prepare(scrimState4);
            scrimState.mBehindAlpha = 0.0f;
            if (scrimState4 == ScrimState.AOD) {
                AODParameters aODParameters = ((DozeParameters) secLsScrimControlHelper2.mDozeParametersLazy.get()).mAODParameters;
                if (aODParameters != null) {
                    scrimState.mAnimateChange = aODParameters.mDozeUiState;
                }
                if (((PluginAODManager) secLsScrimControlHelper2.mPluginAODManagerLazy.get()).mIsDifferentOrientation) {
                    scrimState.mBlankScreen = true;
                }
            }
        } else if (i == 3) {
            scrimState.mClipQsScrim = false;
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = secLsScrimControlHelper2.mKeyguardFastBioUnlockController;
            boolean isFastWakeAndUnlockMode = keyguardFastBioUnlockController.isFastWakeAndUnlockMode();
            ScrimState scrimState5 = ScrimState.AOD;
            if (scrimState4 == scrimState5 && isFastWakeAndUnlockMode) {
                scrimState.prepare(scrimState3);
                if (keyguardFastBioUnlockController.needsBlankScreen) {
                    scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimInFront);
                    scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimBehind);
                    scrimState.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
                    scrimState.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
                    scrimState.mAnimateChange = true;
                    scrimState.mBlankScreen = true;
                    scrimState.mAnimationDuration = 50L;
                } else {
                    scrimState.mBlankScreen = false;
                    scrimState.mAnimateChange = false;
                    scrimState.mAnimationDuration = 0L;
                }
            } else if (scrimState4 != scrimState5) {
                scrimState.prepare(scrimState4);
            } else if (LsRune.AOD_FULLSCREEN && secLsScrimControlHelper2.mAodAmbientWallpaperHelper.isAODFullScreenMode() && !((KeyguardStateControllerImpl) ((KeyguardStateController) secLsScrimControlHelper2.mKeyguardStateControllerLazy.get())).mShowing) {
                scrimState.mBlankScreen = false;
                scrimState.mAnimateChange = false;
            } else {
                scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimInFront);
                scrimState.updateScrimColor(secLsScrimControlHelper2.mScrimBehind);
                scrimState.mFrontTint = EmergencyPhoneWidget.BG_COLOR;
                scrimState.mBehindTint = EmergencyPhoneWidget.BG_COLOR;
                scrimState.mBlankScreen = true;
            }
            if (scrimState4 != scrimState5) {
                scrimState.mAnimateChange = false;
            } else if (!isFastWakeAndUnlockMode) {
                scrimState.mAnimateChange = true;
            }
        } else {
            if (i != 4) {
                z = false;
                if (!z) {
                    scrimState.prepare(scrimState4);
                }
                this.mScreenBlankingCallbackCalled = false;
                this.mAnimationDelay = 0L;
                boolean z3 = scrimState.mBlankScreen;
                this.mBlankScreen = z3;
                this.mAnimateChange = scrimState.mAnimateChange;
                this.mAnimationDuration = scrimState.mAnimationDuration;
                Object[] objArr = new Object[3];
                objArr[0] = this.mState;
                objArr[1] = Boolean.valueOf(z3);
                objArr[2] = Long.valueOf(!this.mAnimateChange ? this.mAnimationDuration : -1L);
                com.android.systemui.keyguard.Log.m139d("ScrimController", "State changed to %s, blankScreen=%s, animation=%d", objArr);
                applyState();
                this.mScrimInFront.setFocusable(!scrimState.isLowPowerState());
                this.mScrimBehind.setFocusable(!scrimState.isLowPowerState());
                this.mNotificationsScrim.setFocusable(!scrimState.isLowPowerState());
                this.mScrimInFront.mBlendWithMainColor = scrimState.shouldBlendWithMainColor();
                scrimController$$ExternalSyntheticLambda0 = this.mPendingFrameCallback;
                if (scrimController$$ExternalSyntheticLambda0 != null) {
                    this.mScrimBehind.removeCallbacks(scrimController$$ExternalSyntheticLambda0);
                    this.mPendingFrameCallback = null;
                }
                if (this.mHandler.hasCallbacks(this.mBlankingTransitionRunnable)) {
                    this.mHandler.removeCallbacks(this.mBlankingTransitionRunnable);
                    this.mBlankingTransitionRunnable = null;
                }
                this.mNeedsDrawableColorUpdate = scrimState == ScrimState.BRIGHTNESS_MIRROR;
                if (this.mState.isLowPowerState() && !this.mWakeLockHeld) {
                    delayedWakeLock = this.mWakeLock;
                    if (delayedWakeLock == null) {
                        this.mWakeLockHeld = true;
                        delayedWakeLock.acquire("ScrimController");
                    } else {
                        Log.w("ScrimController", "Cannot hold wake lock, it has not been set yet");
                    }
                }
                this.mWallpaperVisibilityTimedOut = false;
                if (this.mWallpaperSupportsAmbientMode && this.mState == ScrimState.AOD) {
                    if (this.mDozeParameters.getAlwaysOn()) {
                        this.mDockManager.getClass();
                    }
                }
                z2 = false;
                if (z2 || this.mAODAmbientWallpaperHelper.isAODAmbientWallpaperMode()) {
                    AlarmTimeout alarmTimeout = this.mTimeTicker;
                    Objects.requireNonNull(alarmTimeout);
                    DejankUtils.postAfterTraversal(new ScrimController$$ExternalSyntheticLambda0(alarmTimeout, 5));
                } else {
                    DejankUtils.postAfterTraversal(new ScrimController$$ExternalSyntheticLambda0(this, i2));
                }
                if (this.mKeyguardUpdateMonitor.mNeedsSlowUnlockTransition || this.mState != ScrimState.UNLOCKED) {
                    scrimState2 = ScrimState.AOD;
                    if (((scrimState4 != scrimState2 || scrimState4 == ScrimState.PULSING) && (!this.mDozeParameters.getAlwaysOn() || this.mState == ScrimState.UNLOCKED)) || (this.mState == scrimState2 && !this.mDozeParameters.getDisplayNeedsBlanking())) {
                        onPreDraw();
                    } else {
                        scheduleUpdate();
                    }
                } else {
                    this.mAnimationDelay = 100L;
                    scheduleUpdate();
                }
                dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
            }
            scrimState.mClipQsScrim = false;
            scrimState.prepare(scrimState4);
            if (LsRune.COVER_SUPPORTED && scrimState4 == ScrimState.AOD && ((CoverHostImpl) ((CoverHost) lazy.get())).isNeedScrimAnimation()) {
                scrimState.mBlankScreen = true;
                scrimState.mAnimateChange = true;
            }
        }
        z = true;
        if (!z) {
        }
        this.mScreenBlankingCallbackCalled = false;
        this.mAnimationDelay = 0L;
        boolean z32 = scrimState.mBlankScreen;
        this.mBlankScreen = z32;
        this.mAnimateChange = scrimState.mAnimateChange;
        this.mAnimationDuration = scrimState.mAnimationDuration;
        Object[] objArr2 = new Object[3];
        objArr2[0] = this.mState;
        objArr2[1] = Boolean.valueOf(z32);
        objArr2[2] = Long.valueOf(!this.mAnimateChange ? this.mAnimationDuration : -1L);
        com.android.systemui.keyguard.Log.m139d("ScrimController", "State changed to %s, blankScreen=%s, animation=%d", objArr2);
        applyState();
        this.mScrimInFront.setFocusable(!scrimState.isLowPowerState());
        this.mScrimBehind.setFocusable(!scrimState.isLowPowerState());
        this.mNotificationsScrim.setFocusable(!scrimState.isLowPowerState());
        this.mScrimInFront.mBlendWithMainColor = scrimState.shouldBlendWithMainColor();
        scrimController$$ExternalSyntheticLambda0 = this.mPendingFrameCallback;
        if (scrimController$$ExternalSyntheticLambda0 != null) {
        }
        if (this.mHandler.hasCallbacks(this.mBlankingTransitionRunnable)) {
        }
        this.mNeedsDrawableColorUpdate = scrimState == ScrimState.BRIGHTNESS_MIRROR;
        if (this.mState.isLowPowerState()) {
            delayedWakeLock = this.mWakeLock;
            if (delayedWakeLock == null) {
            }
        }
        this.mWallpaperVisibilityTimedOut = false;
        if (this.mWallpaperSupportsAmbientMode) {
            if (this.mDozeParameters.getAlwaysOn()) {
            }
        }
        z2 = false;
        if (z2) {
        }
        AlarmTimeout alarmTimeout2 = this.mTimeTicker;
        Objects.requireNonNull(alarmTimeout2);
        DejankUtils.postAfterTraversal(new ScrimController$$ExternalSyntheticLambda0(alarmTimeout2, 5));
        if (this.mKeyguardUpdateMonitor.mNeedsSlowUnlockTransition) {
        }
        scrimState2 = ScrimState.AOD;
        if (scrimState4 != scrimState2) {
        }
        onPreDraw();
        dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
    }

    public final void updateScrimColor(float f, int i, View view) {
        ScrimStateLogger scrimStateLogger = this.mScrimColorState;
        boolean z = false;
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
                z = true;
            }
            if (z) {
                SecLsScrimControlHelper secLsScrimControlHelper2 = this.mSecLsScrimControlHelper;
                int i4 = secLsScrimControlHelper2.mScrimBouncerColor;
                scrimView2.getClass();
                scrimView2.executeOnExecutor(new ScrimView$$ExternalSyntheticLambda4(scrimView2, i4));
                if (secLsScrimControlHelper2.needUpdateScrimColor()) {
                    scrimView2.setViewAlpha(0.0f);
                } else {
                    scrimView2.setViewAlpha(max);
                }
            } else {
                scrimView2.getClass();
                scrimView2.executeOnExecutor(new ScrimView$$ExternalSyntheticLambda4(scrimView2, i));
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
            if (this.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController.isEnabled()) {
                z = false;
                z2 = false;
            }
            SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
            ScrimState scrimState2 = secLsScrimControlHelper.mState;
            if (scrimState2 == ScrimState.BOUNCER_SCRIMMED || scrimState2 == ScrimState.BOUNCER) {
                ScrimView scrimView = secLsScrimControlHelper.mScrimInFront;
                ColorExtractor.GradientColors gradientColors = secLsScrimControlHelper.mBouncerColors;
                scrimView.setColors(gradientColors, false);
                secLsScrimControlHelper.mScrimBehind.setColors(gradientColors, false);
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
        setScrimAlpha(this.mInFrontAlpha, this.mScrimInFront);
        setScrimAlpha(this.mBehindAlpha, this.mScrimBehind);
        setScrimAlpha(this.mNotificationsAlpha, this.mNotificationsScrim);
        onFinished(this.mCallback, this.mState);
        dispatchScrimsVisible();
    }

    public final void updateThemeColors() {
        ScrimView scrimView = this.mScrimBehind;
        if (scrimView == null) {
            return;
        }
        int defaultColor = Utils.getColorAttr(android.R.attr.colorBackgroundFloating, scrimView.getContext()).getDefaultColor();
        int defaultColor2 = Utils.getColorAttr(android.R.attr.colorAccent, this.mScrimBehind.getContext()).getDefaultColor();
        this.mColors.setMainColor(defaultColor);
        this.mColors.setSecondaryColor(defaultColor2);
        if (this.mUseNewLightBarLogic) {
            this.mColors.setSupportsDarkText(!ContrastColorUtil.isColorDark(defaultColor));
        } else {
            ColorExtractor.GradientColors gradientColors = this.mColors;
            gradientColors.setSupportsDarkText(ColorUtils.calculateContrast(gradientColors.getMainColor(), -1) > 4.5d);
        }
        int defaultColor3 = Utils.getColorAttr(android.R.^attr-private.materialColorOnSecondaryContainer, this.mScrimBehind.getContext()).getDefaultColor();
        for (ScrimState scrimState : ScrimState.values()) {
            scrimState.setSurfaceColor(defaultColor3);
        }
        this.mNeedsDrawableColorUpdate = true;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onFinished();

        default void onCancelled() {
        }

        default void onDisplayBlanked() {
        }
    }
}
