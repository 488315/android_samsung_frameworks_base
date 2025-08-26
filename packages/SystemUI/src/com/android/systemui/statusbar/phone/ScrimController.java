package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
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
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimViewBase;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.ScrimStateLogger;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public class ScrimController implements ViewTreeObserver.OnPreDrawListener, Dumpable {
    public AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public final AlternateBouncerToGoneTransitionViewModel mAlternateBouncerToGoneTransitionViewModel;
    public boolean mAnimateChange;
    public boolean mAnimatingPanelExpansionOnUnlock;
    public long mAnimationDelay;
    public Animator.AnimatorListener mAnimatorListener;
    public int mBehindTint;
    public boolean mBlankScreen;
    public ScrimController$$ExternalSyntheticLambda3 mBlankingTransitionRunnable;
    Consumer<TransitionStep> mBouncerToGoneTransition;
    public Callback mCallback;
    public final ColorExtractor.GradientColors mColors;
    public final Context mContext;
    public boolean mDarkenWhileDragging;
    public final DockManager mDockManager;
    public final DozeParameters mDozeParameters;
    public final Handler mHandler;
    public int mInFrontTint;
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
    public ScrimController$$ExternalSyntheticLambda3 mPendingFrameCallback;
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
    public final ScrimController$$ExternalSyntheticLambda2 mScrimStateListener;
    public CentralSurfacesImpl$$ExternalSyntheticLambda2 mScrimVisibleListener;
    public int mScrimsVisibility;
    public SecLsScrimControlHelper mSecLsScrimControlHelper;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public float mTransitionToFullShadeProgress;
    public float mTransitionToLockScreenFullShadeNotificationsProgress;
    public boolean mTransitioningToFullShade;
    public boolean mTransparentScrimBackground;
    public boolean mUpdatePending;
    public final DelayedWakeLock mWakeLock;
    public boolean mWakeLockHeld;
    public final Lazy mWindowRootViewBlurInteractor;
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
    public final ScrimController$$ExternalSyntheticLambda1 mScrimAlphaConsumer = new ScrimController$$ExternalSyntheticLambda1(this, 1);
    public final ScrimController$$ExternalSyntheticLambda1 mGlanceableHubConsumer = new ScrimController$$ExternalSyntheticLambda1(this, 0);

    /* renamed from: com.android.systemui.statusbar.phone.ScrimController$3, reason: invalid class name */
    public class AnonymousClass3 implements ScrimStateLogger.Callback {
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

    public ScrimController(LightBarController lightBarController, DozeParameters dozeParameters, final KeyguardStateController keyguardStateController, DelayedWakeLock.Factory factory, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, ConfigurationController configurationController, Executor executor, JavaAdapter javaAdapter, ScreenOffAnimationController screenOffAnimationController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, CoroutineDispatcher coroutineDispatcher, LargeScreenShadeInterpolator largeScreenShadeInterpolator, BlurConfig blurConfig, Context context, Lazy lazy) {
        this.mContext = context;
        Objects.requireNonNull(lightBarController);
        this.mScrimStateListener = new ScrimController$$ExternalSyntheticLambda2(lightBarController);
        this.mLargeScreenShadeInterpolator = largeScreenShadeInterpolator;
        this.mWindowRootViewBlurInteractor = lazy;
        this.mKeyguardStateController = keyguardStateController;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        this.mDarkenWhileDragging = true ^ keyguardStateControllerImpl.mCanDismissLockScreen;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardVisibilityCallback = new KeyguardVisibilityCallback(this, 0);
        this.mHandler = handler;
        this.mMainExecutor = executor;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mWakeLock = factory.create("Scrims");
        this.mDozeParameters = dozeParameters;
        this.mDockManager = dockManager;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        keyguardStateControllerImpl.addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.ScrimController.1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                KeyguardStateControllerImpl keyguardStateControllerImpl2 = (KeyguardStateControllerImpl) keyguardStateController;
                boolean z = keyguardStateControllerImpl2.mKeyguardFadingAway;
                long j = keyguardStateControllerImpl2.mKeyguardFadingAwayDuration;
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
        this.mMainDispatcher = coroutineDispatcher;
    }

    public final void applyAndDispatchState() {
        applyState$1();
        if (this.mUpdatePending) {
            return;
        }
        setOrAdaptCurrentAnimation(this.mScrimBehind);
        setOrAdaptCurrentAnimation(this.mNotificationsScrim);
        setOrAdaptCurrentAnimation(this.mScrimInFront);
        dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
    }

    public final void applyState$1() {
        boolean z;
        ScrimState scrimState;
        SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
        secLsScrimControlHelper.getClass();
        int i = SecLsScrimControlHelper.AnonymousClass4.$SwitchMap$com$android$systemui$statusbar$phone$ScrimState[secLsScrimControlHelper.mState.ordinal()];
        if (i == 1 || i == 2) {
            z = true;
        } else if (i == 3 && ((scrimState = secLsScrimControlHelper.mPreviousState) == ScrimState.AOD || scrimState == ScrimState.KEYGUARD || scrimState == ScrimState.BOUNCER || scrimState == ScrimState.BOUNCER_SCRIMMED || scrimState == ScrimState.DREAMING)) {
            secLsScrimControlHelper.mState.mBehindAlpha = 0.0f;
            z = true;
        } else {
            z = false;
        }
        ScrimState scrimState2 = this.mState;
        this.mInFrontTint = scrimState2.mFrontTint;
        this.mBehindTint = scrimState2.mBehindTint;
        this.mNotificationsTint = scrimState2.mNotifTint;
        this.mInFrontAlpha = scrimState2.mFrontAlpha;
        this.mBehindAlpha = scrimState2.mBehindAlpha;
        this.mNotificationsAlpha = scrimState2.mNotifAlpha;
        assertAlphasValid();
        if (this.mExpansionAffectsAlpha) {
            if (z) {
                if (this.mState != ScrimState.UNLOCKED) {
                    this.mAnimatingPanelExpansionOnUnlock = false;
                    return;
                }
                return;
            }
            ScrimState scrimState3 = this.mState;
            ScrimState scrimState4 = ScrimState.UNLOCKED;
            if (scrimState3 == scrimState4 || scrimState3 == ScrimState.DREAMING || scrimState3 == ScrimState.GLANCEABLE_HUB_OVER_DREAM) {
                if (!this.mOccludeAnimationPlaying && !scrimState3.mLaunchingAffordanceWithPreview) {
                    z = false;
                }
                if (!this.mScreenOffAnimationController.shouldExpandNotifications() && !this.mAnimatingPanelExpansionOnUnlock && !z) {
                    if (this.mTransparentScrimBackground) {
                        this.mBehindAlpha = 0.0f;
                        this.mNotificationsAlpha = 0.0f;
                    } else {
                        this.mBehindAlpha = this.mLargeScreenShadeInterpolator.getBehindScrimAlpha(this.mPanelExpansionFraction * 1.0f);
                        this.mNotificationsAlpha = this.mLargeScreenShadeInterpolator.getNotificationScrimAlpha(this.mPanelExpansionFraction);
                    }
                    this.mBehindTint = this.mState.mBehindTint;
                    this.mInFrontAlpha = 0.0f;
                }
                ScrimState scrimState5 = this.mState;
                if (scrimState5 == ScrimState.DREAMING || scrimState5 == ScrimState.GLANCEABLE_HUB_OVER_DREAM) {
                    float f = this.mBouncerHiddenFraction;
                    if (f != 1.0f) {
                        float fAboutToShowBouncerProgress = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f);
                        this.mBehindAlpha = MathUtils.lerp(1.0f, this.mBehindAlpha, fAboutToShowBouncerProgress);
                        this.mBehindTint = ColorUtils.blendARGB(ScrimState.BOUNCER.mBehindTint, this.mBehindTint, fAboutToShowBouncerProgress);
                    }
                }
            } else {
                ScrimState scrimState6 = ScrimState.KEYGUARD;
                if (scrimState3 == scrimState6 || scrimState3 == ScrimState.SHADE_LOCKED || scrimState3 == ScrimState.PULSING || scrimState3 == ScrimState.GLANCEABLE_HUB) {
                    Pair pairCalculateBackStateForState = calculateBackStateForState(scrimState3);
                    int iIntValue = ((Integer) pairCalculateBackStateForState.first).intValue();
                    float fFloatValue = ((Float) pairCalculateBackStateForState.second).floatValue();
                    float f2 = this.mTransitionToFullShadeProgress;
                    if (f2 > 0.0f) {
                        Pair pairCalculateBackStateForState2 = calculateBackStateForState(ScrimState.SHADE_LOCKED);
                        fFloatValue = MathUtils.lerp(fFloatValue, ((Float) pairCalculateBackStateForState2.second).floatValue(), this.mTransitionToFullShadeProgress);
                        iIntValue = ColorUtils.blendARGB(iIntValue, ((Integer) pairCalculateBackStateForState2.first).intValue(), this.mTransitionToFullShadeProgress);
                    } else if (this.mState == ScrimState.GLANCEABLE_HUB && f2 == 0.0f && this.mBouncerHiddenFraction == 1.0f) {
                        fFloatValue = 0.0f;
                    }
                    ScrimState scrimState7 = this.mState;
                    this.mInFrontAlpha = scrimState7.mFrontAlpha;
                    this.mBehindAlpha = fFloatValue;
                    if (scrimState7 == scrimState6 && this.mTransitionToFullShadeProgress > 0.0f) {
                        this.mNotificationsAlpha = MathUtils.saturate(this.mTransitionToLockScreenFullShadeNotificationsProgress);
                    } else if (scrimState7 == ScrimState.SHADE_LOCKED) {
                        this.mNotificationsAlpha = getInterpolatedFraction();
                    } else if (scrimState7 == ScrimState.GLANCEABLE_HUB && this.mTransitionToFullShadeProgress == 0.0f) {
                        this.mNotificationsAlpha = 0.0f;
                    } else {
                        this.mNotificationsAlpha = Math.max(1.0f - getInterpolatedFraction(), this.mQsExpansion);
                    }
                    ScrimState scrimState8 = this.mState;
                    this.mNotificationsTint = scrimState8.mNotifTint;
                    this.mBehindTint = iIntValue;
                    z = scrimState8 == scrimState6 && this.mTransitionToFullShadeProgress == 0.0f && this.mQsExpansion == 0.0f;
                    if (this.mKeyguardOccluded || z) {
                        this.mNotificationsAlpha = 0.0f;
                    }
                }
            }
            if (this.mState != scrimState4) {
                this.mAnimatingPanelExpansionOnUnlock = false;
            }
            assertAlphasValid();
        }
    }

    public final void assertAlphasValid() {
        if (Float.isNaN(this.mBehindAlpha) || Float.isNaN(this.mInFrontAlpha) || Float.isNaN(this.mNotificationsAlpha)) {
            throw new IllegalStateException("Scrim opacity is NaN for state: " + this.mState + ", front: " + this.mInFrontAlpha + ", back: " + this.mBehindAlpha + ", notif: " + this.mNotificationsAlpha);
        }
    }

    public final void calculateAndUpdatePanelExpansion() {
        float fMax = this.mRawPanelExpansionFraction;
        float f = this.mPanelScrimMinFraction;
        if (f < 1.0f) {
            fMax = Math.max((fMax - f) / (1.0f - f), 0.0f);
        }
        if (this.mPanelExpansionFraction != fMax) {
            if (fMax != 0.0f && this.mKeyguardUnlockAnimationController.playingCannedUnlockAnimation && this.mState != ScrimState.UNLOCKED) {
                this.mAnimatingPanelExpansionOnUnlock = true;
            } else if (fMax == 0.0f) {
                this.mAnimatingPanelExpansionOnUnlock = false;
            }
            this.mPanelExpansionFraction = fMax;
            ScrimState scrimState = this.mState;
            if ((scrimState == ScrimState.UNLOCKED || scrimState == ScrimState.KEYGUARD || scrimState == ScrimState.DREAMING || scrimState == ScrimState.GLANCEABLE_HUB_OVER_DREAM || scrimState == ScrimState.SHADE_LOCKED || scrimState == ScrimState.PULSING) && this.mExpansionAffectsAlpha && !this.mAnimatingPanelExpansionOnUnlock) {
                applyAndDispatchState();
            }
        }
    }

    public final Pair calculateBackStateForState(ScrimState scrimState) {
        float interpolatedFraction = getInterpolatedFraction();
        float f = scrimState.mBehindAlpha;
        int iBlendARGB = scrimState.mBehindTint;
        float fLerp = this.mDarkenWhileDragging ? MathUtils.lerp(1.0f, f, interpolatedFraction) : MathUtils.lerp(0.0f, f, interpolatedFraction);
        if (this.mStatusBarKeyguardViewManager.isPrimaryBouncerInTransit()) {
            iBlendARGB = ColorUtils.blendARGB(ScrimState.BOUNCER.mBehindTint, scrimState.mBehindTint, interpolatedFraction);
        }
        float f2 = this.mQsExpansion;
        if (f2 > 0.0f) {
            fLerp = MathUtils.lerp(fLerp, 1.0f, f2);
            float f3 = this.mQsExpansion;
            if (this.mStatusBarKeyguardViewManager.isPrimaryBouncerInTransit()) {
                float f4 = this.mPanelExpansionFraction;
                int i = BouncerPanelExpansionCalculator.$r8$clinit;
                f3 = f4 < 0.9f ? ((double) f4) < 0.6d ? 0.0f : (f4 - 0.6f) / 0.3f : 1.0f;
            }
            iBlendARGB = ColorUtils.blendARGB(iBlendARGB, ScrimState.SHADE_LOCKED.mBehindTint, f3);
        }
        return new Pair(Integer.valueOf(iBlendARGB), Float.valueOf(((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway ? 0.0f : fLerp));
    }

    public final void dispatchBackScrimState(float f) {
        this.mScrimStateListener.accept(this.mState, Float.valueOf(f), this.mColors);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchScrimsVisible() {
        int i;
        ScrimView scrimView = this.mScrimBehind;
        float f = this.mScrimInFront.mViewAlpha;
        if (f != 1.0f) {
            float f2 = scrimView.mViewAlpha;
            i = f2 == 1.0f ? 2 : (f == 0.0f && f2 == 0.0f) ? 0 : 1;
        }
        if (this.mScrimsVisibility != i) {
            this.mScrimsVisibility = i;
            SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
            boolean z = this.mScreenOn;
            secLsScrimControlHelper.getClass();
            ViewRootImpl viewRootImpl = (z || i != 2) ? null : secLsScrimControlHelper.mScrimInFront.getViewRootImpl();
            if (LsRune.AOD_SUB_DISPLAY_COVER && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                viewRootImpl = secLsScrimControlHelper.mScrimBehind.getViewRootImpl();
            }
            if (viewRootImpl != null) {
                viewRootImpl.setReportNextDraw(false, "scrim");
            }
            this.mScrimVisibleListener.accept(Integer.valueOf(i));
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
        printWriter.println(1.0f);
        printWriter.print("  mPanelExpansionFraction=");
        printWriter.println(this.mPanelExpansionFraction);
        printWriter.print("  mExpansionAffectsAlpha=");
        printWriter.println(this.mExpansionAffectsAlpha);
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
        long j;
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
        ScrimState scrimState2 = ScrimState.UNINITIALIZED;
        if (scrimState == scrimState2) {
            throw new IllegalArgumentException("Cannot change to UNINITIALIZED.");
        }
        SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
        secLsScrimControlHelper.mPreviousState = secLsScrimControlHelper.mState;
        secLsScrimControlHelper.mState = scrimState;
        ScrimState scrimState3 = this.mState;
        this.mState = scrimState;
        TrackTracer.instantForGroup(scrimState.ordinal(), "scrim", "state");
        Callback callback2 = this.mCallback;
        if (callback2 != null) {
            callback2.onCancelled();
        }
        this.mCallback = callback;
        SecLsScrimControlHelper secLsScrimControlHelper2 = this.mSecLsScrimControlHelper;
        secLsScrimControlHelper2.getClass();
        int i = SecLsScrimControlHelper.AnonymousClass4.$SwitchMap$com$android$systemui$statusbar$phone$ScrimState[scrimState.ordinal()];
        AODAmbientWallpaperHelper aODAmbientWallpaperHelper = secLsScrimControlHelper2.mAodAmbientWallpaperHelper;
        Lazy lazy = secLsScrimControlHelper2.mCoverHostLazy;
        if (i != 1) {
            WakefulnessLifecycle wakefulnessLifecycle = secLsScrimControlHelper2.mWakefulnessLifecycle;
            Lazy lazy2 = secLsScrimControlHelper2.mDozeParametersLazy;
            if (i == 2) {
                scrimState.mClipQsScrim = false;
                scrimState.prepare(scrimState3);
                scrimState.mBehindAlpha = 0.0f;
                if (scrimState3 == ScrimState.AOD) {
                    if (!LsRune.KEYGUARD_SCREEN_ON_FADE_OUT_ANIM) {
                        AODParameters aODParameters2 = ((DozeParameters) lazy2.get()).mAODParameters;
                        if (aODParameters2 != null) {
                            scrimState.mAnimateChange = aODParameters2.mDozeUiState;
                        }
                    } else if (LsRune.KEYGUARD_SUB_DISPLAY_COVER && wakefulnessLifecycle.mLastWakeReason == 12) {
                        scrimState.mAnimateChange = false;
                    } else {
                        scrimState.mAnimationDuration = 600L;
                        scrimState.mAnimateChange = true;
                    }
                    if (((PluginAODManager) secLsScrimControlHelper2.mPluginAODManagerLazy.get()).mIsDifferentOrientation) {
                        scrimState.mBlankScreen = true;
                    }
                } else if (scrimState3 == ScrimState.UNLOCKED) {
                    scrimState.mAnimateChange = false;
                    j = 0;
                    scrimState.mAnimationDuration = 0L;
                }
            } else if (i == 3) {
                scrimState.mClipQsScrim = false;
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = secLsScrimControlHelper2.mKeyguardFastBioUnlockController;
                boolean zIsFastWakeAndUnlockMode = keyguardFastBioUnlockController.isFastWakeAndUnlockMode();
                ScrimState scrimState4 = ScrimState.AOD;
                if (scrimState3 == scrimState4 && zIsFastWakeAndUnlockMode) {
                    scrimState.prepare(scrimState2);
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
                } else if (scrimState3 == scrimState4) {
                    if (secLsScrimControlHelper2.mPowerInteractor.screenPowerState.$$delegate_0.getValue() == ScreenPowerState.SCREEN_ON && (aODParameters = ((DozeParameters) lazy2.get()).mAODParameters) != null && !aODParameters.mDozeUiState) {
                        Log.i("ScrimController", "shouldPreventBlankScreen Screen is fully on");
                    } else if (LsRune.COVER_SUPPORTED && !DeviceType.isTablet() && wakefulnessLifecycle.mLastWakeReason == 103) {
                        Log.i("ScrimController", "shouldPreventBlankScreen cover is opening");
                    } else if (LsRune.AOD_FULLSCREEN && aODAmbientWallpaperHelper.isAODFullScreenAndShowing() && !((KeyguardStateControllerImpl) ((KeyguardStateController) secLsScrimControlHelper2.mKeyguardStateControllerLazy.get())).mShowing) {
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
                    scrimState.prepare(scrimState3);
                }
                if (scrimState3 != scrimState4) {
                    scrimState.mAnimateChange = false;
                } else if (!zIsFastWakeAndUnlockMode) {
                    scrimState.mAnimateChange = true;
                }
            } else if (i != 4) {
                scrimState.prepare(scrimState3);
            } else {
                scrimState.mClipQsScrim = false;
                scrimState.prepare(scrimState3);
                if (LsRune.COVER_SUPPORTED && scrimState3 == ScrimState.AOD && ((CoverHostImpl) ((CoverHost) lazy.get())).isNeedScrimAnimation()) {
                    scrimState.mBlankScreen = true;
                    scrimState.mAnimateChange = true;
                }
            }
            j = 0;
        } else {
            scrimState.mClipQsScrim = false;
            scrimState.prepare(scrimState3);
            scrimState.mAnimationDuration = 500L;
            if (LsRune.AOD_LIGHT_REVEAL) {
                scrimState.mBehindAlpha = 0.0f;
                scrimState.mAnimateChange = false;
            } else {
                scrimState.mBehindAlpha = aODAmbientWallpaperHelper.isWonderLandAmbientWallpaper() ? 0.0f : 1.0f;
            }
            if (!LsRune.COVER_SUPPORTED || ((CoverHostImpl) ((CoverHost) lazy.get())).isNeedScrimAnimation()) {
                j = 0;
            } else {
                scrimState.mBlankScreen = false;
                scrimState.mAnimateChange = false;
                j = 0;
                scrimState.mAnimationDuration = 0L;
            }
        }
        this.mScreenBlankingCallbackCalled = false;
        this.mAnimationDelay = j;
        boolean z = scrimState.mBlankScreen;
        this.mBlankScreen = z;
        this.mAnimateChange = scrimState.mAnimateChange;
        this.mAnimationDuration = scrimState.mAnimationDuration;
        com.android.systemui.keyguard.Log.d("ScrimController", "State changed to %s, blankScreen=%s, animation=%d", this.mState, Boolean.valueOf(z), Long.valueOf(this.mAnimateChange ? this.mAnimationDuration : -1L));
        if (this.mState == ScrimState.GLANCEABLE_HUB_OVER_DREAM) {
            this.mPanelExpansionFraction = 0.0f;
        }
        applyState$1();
        this.mScrimInFront.mBlendWithMainColor = scrimState.shouldBlendWithMainColor();
        ScrimController$$ExternalSyntheticLambda3 scrimController$$ExternalSyntheticLambda3 = this.mPendingFrameCallback;
        if (scrimController$$ExternalSyntheticLambda3 != null) {
            this.mScrimBehind.removeCallbacks(scrimController$$ExternalSyntheticLambda3);
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
        if (this.mKeyguardUpdateMonitor.mNeedsSlowUnlockTransition && this.mState == ScrimState.UNLOCKED) {
            this.mAnimationDelay = 100L;
            scheduleUpdate$1();
        } else {
            ScrimState scrimState5 = ScrimState.AOD;
            if (((scrimState3 == scrimState5 || scrimState3 == ScrimState.PULSING) && (!this.mDozeParameters.getAlwaysOn() || this.mState == ScrimState.UNLOCKED)) || (this.mState == scrimState5 && !this.mDozeParameters.getDisplayNeedsBlanking())) {
                onPreDraw();
            } else {
                scheduleUpdate$1();
            }
        }
        dispatchBackScrimState(this.mScrimBehind.mViewAlpha);
    }

    public final void legacyTransitionTo(ScrimState scrimState) {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        internalTransitionTo(null, scrimState);
    }

    public final void onFinished(Callback callback, ScrimState scrimState) {
        ScrimView scrimView;
        ScrimView scrimView2;
        if (this.mPendingFrameCallback != null) {
            return;
        }
        ScrimView scrimView3 = this.mScrimBehind;
        if ((scrimView3 != null && scrimView3.getTag(TAG_KEY_ANIM) != null) || (((scrimView = this.mNotificationsScrim) != null && scrimView.getTag(TAG_KEY_ANIM) != null) || ((scrimView2 = this.mScrimInFront) != null && scrimView2.getTag(TAG_KEY_ANIM) != null))) {
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
            updateScrimColor(this.mScrimInFront, this.mInFrontAlpha, 0);
            updateScrimColor(this.mScrimBehind, this.mBehindAlpha, this.mBehindTint);
            updateScrimColor(this.mNotificationsScrim, this.mNotificationsAlpha, this.mNotificationsTint);
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
        int i = TAG_KEY_ANIM;
        if (view.getTag(i) == null || z) {
            updateScrimColor(view, currentScrimAlpha, getCurrentScrimTint(view));
            return;
        }
        ValueAnimator valueAnimator = (ValueAnimator) view.getTag(i);
        int i2 = TAG_END_ALPHA;
        float fFloatValue = ((Float) view.getTag(i2)).floatValue();
        int i3 = TAG_START_ALPHA;
        view.setTag(i3, Float.valueOf((currentScrimAlpha - fFloatValue) + ((Float) view.getTag(i3)).floatValue()));
        view.setTag(i2, Float.valueOf(currentScrimAlpha));
        valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setScrimAlpha(final ScrimView scrimView, float f) {
        ValueAnimator valueAnimatorOfFloat;
        Callback callback;
        int i = 0;
        if (f == 0.0f) {
            scrimView.setClickable(false);
        } else {
            scrimView.setClickable(this.mState != ScrimState.AOD);
        }
        float f2 = scrimView.mViewAlpha;
        int i2 = TAG_KEY_ANIM;
        ViewState.AnonymousClass1 anonymousClass1 = ViewState.NO_NEW_ANIMATIONS;
        ValueAnimator valueAnimator = (ValueAnimator) scrimView.getTag(i2);
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (this.mPendingFrameCallback != null) {
            return;
        }
        if (this.mBlankScreen) {
            updateScrimColor(this.mScrimInFront, 1.0f, -16777216);
            ScrimController$$ExternalSyntheticLambda3 scrimController$$ExternalSyntheticLambda3 = new ScrimController$$ExternalSyntheticLambda3(this, i);
            this.mPendingFrameCallback = scrimController$$ExternalSyntheticLambda3;
            doOnTheNextFrame(scrimController$$ExternalSyntheticLambda3);
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
        boolean z2 = scrimView.mTintColor != getCurrentScrimTint(scrimView);
        if (z || z2) {
            if (!this.mAnimateChange) {
                updateScrimColor(scrimView, f, getCurrentScrimTint(scrimView));
                return;
            }
            if (DEBUG) {
                LogUtil.d("ScrimController", "startScrimAnimation %s %f %d %d", getScrimName(scrimView), Float.valueOf(f2), Long.valueOf(this.mAnimationDelay), Long.valueOf(this.mAnimationDuration));
            }
            if (LsRune.KEYGUARD_SCREEN_ON_FADE_OUT_ANIM) {
                SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
                AODParameters aODParameters = ((DozeParameters) secLsScrimControlHelper.mDozeParametersLazy.get()).mAODParameters;
                valueAnimatorOfFloat = !(aODParameters != null ? aODParameters.mDozeUiState : false) && secLsScrimControlHelper.mPreviousState == ScrimState.AOD && secLsScrimControlHelper.mState == ScrimState.KEYGUARD ? ValueAnimator.ofFloat(0.3f, 1.0f) : ValueAnimator.ofFloat(0.0f, 1.0f);
            }
            Animator.AnimatorListener animatorListener = this.mAnimatorListener;
            if (animatorListener != null) {
                valueAnimatorOfFloat.addListener(animatorListener);
            }
            final int i3 = scrimView.mTintColor;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ScrimController scrimController = this.f$0;
                    ScrimView scrimView2 = scrimView;
                    int i4 = i3;
                    boolean z3 = ScrimController.DEBUG;
                    scrimController.getClass();
                    float fFloatValue = ((Float) scrimView2.getTag(ScrimController.TAG_START_ALPHA)).floatValue();
                    float fFloatValue2 = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    scrimController.updateScrimColor(scrimView2, MathUtils.constrain(MathUtils.lerp(fFloatValue, scrimController.getCurrentScrimAlpha(scrimView2), fFloatValue2), 0.0f, 1.0f), ColorUtils.blendARGB(i4, scrimController.getCurrentScrimTint(scrimView2), fFloatValue2));
                    scrimController.dispatchScrimsVisible();
                }
            });
            valueAnimatorOfFloat.setInterpolator(this.mInterpolator);
            valueAnimatorOfFloat.setStartDelay(this.mAnimationDelay);
            valueAnimatorOfFloat.setDuration(this.mAnimationDuration);
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.ScrimController.4
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
                    boolean z3 = ScrimController.DEBUG;
                    scrimController.getClass();
                    ExifInterface$$ExternalSyntheticOutline0.m(sb, view instanceof ScrimView ? scrimController.getScrimName((ScrimView) view) : view.toString(), "ScrimController");
                    ScrimStateLogger scrimStateLogger = ScrimController.this.mScrimColorState;
                    if (scrimStateLogger != null) {
                        scrimStateLogger.logScrimColor(true);
                    }
                    scrimView.setTag(ScrimController.TAG_KEY_ANIM, null);
                    ScrimController.this.onFinished(this.mLastCallback, this.mLastState);
                    ScrimController.this.dispatchScrimsVisible();
                    if (LsRune.AOD_LIGHT_REVEAL) {
                        return;
                    }
                    SecLsScrimControlHelper secLsScrimControlHelper2 = ScrimController.this.mSecLsScrimControlHelper;
                    if (scrimView == secLsScrimControlHelper2.mScrimBehind && secLsScrimControlHelper2.mState == ScrimState.AOD) {
                        PluginAODManager pluginAODManager = (PluginAODManager) secLsScrimControlHelper2.mPluginAODManagerLazy.get();
                        if (pluginAODManager.mAODMachine == null || !LsRune.SUBSCREEN_WATCHFACE || pluginAODManager.mDisplayLifeCycle.mIsFolderOpened) {
                            return;
                        }
                        Log.d("PluginAODManager", "onAodTransitionEnd() in folded state");
                        pluginAODManager.onTransitionEnded();
                    }
                }
            });
            scrimView.setTag(TAG_START_ALPHA, Float.valueOf(f2));
            scrimView.setTag(TAG_END_ALPHA, Float.valueOf(getCurrentScrimAlpha(scrimView)));
            scrimView.setTag(i2, valueAnimatorOfFloat);
            valueAnimatorOfFloat.start();
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

    public final void updateScrimColor(View view, float f, int i) {
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
        float fMax = Math.max(0.0f, Math.min(1.0f, f));
        if (view instanceof ScrimView) {
            ScrimView scrimView2 = (ScrimView) view;
            TrackTracer.instantForGroup((int) (255.0f * fMax), "scrim", getScrimName(scrimView2).concat("_alpha"));
            TrackTracer.instantForGroup(Color.alpha(i), "scrim", getScrimName(scrimView2).concat("_tint"));
            SecLsScrimControlHelper secLsScrimControlHelper = this.mSecLsScrimControlHelper;
            if (scrimView2 == secLsScrimControlHelper.mScrimInFront && (secLsScrimControlHelper.mState == ScrimState.BOUNCER_SCRIMMED || secLsScrimControlHelper.needUpdateScrimColor())) {
                SecLsScrimControlHelper secLsScrimControlHelper2 = this.mSecLsScrimControlHelper;
                scrimView2.setTint(secLsScrimControlHelper2.mScrimBouncerColor);
                if (secLsScrimControlHelper2.needUpdateScrimColor()) {
                    scrimView2.setViewAlpha(0.0f);
                } else {
                    scrimView2.setViewAlpha(fMax);
                }
            } else {
                scrimView2.setTint(i);
                if (!this.mIsBouncerToGoneTransitionRunning) {
                    scrimView2.setViewAlpha(fMax);
                }
            }
        } else {
            view.setAlpha(fMax);
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
            if (secLsScrimControlHelper.mKeyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
                Log.d("ScrimController", "updateScrims: isWakeAndUnlockAnimationAODFullScreenMode()=" + this.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController.isWakeAndUnlockAnimationAODFullScreenMode());
                if (this.mSecLsScrimControlHelper.mKeyguardFastBioUnlockController.isWakeAndUnlockAnimationAODFullScreenMode()) {
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
        if ((scrimState3 == ScrimState.PULSING || scrimState3 == ScrimState.AOD) && this.mKeyguardOccluded && (!LsRune.AOD_FULLSCREEN || !this.mAODAmbientWallpaperHelper.isAODFullScreenMode())) {
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
        if (this.mScrimBehind == null) {
            return;
        }
        int color = this.mContext.getColor(android.R.color.surface_header_light);
        int color2 = this.mContext.getColor(android.R.color.secondary_text_inverse_when_activated_material);
        this.mColors.setMainColor(color);
        this.mColors.setSecondaryColor(color2);
        this.mColors.setSupportsDarkText(!ContrastColorUtil.isColorDark(color));
        int color3 = this.mContext.getColor(android.R.color.side_fps_text_color);
        for (ScrimState scrimState : ScrimState.values()) {
            scrimState.setSurfaceColor(color3);
        }
        this.mNeedsDrawableColorUpdate = true;
    }

    public interface Callback {
        void onCancelled();

        void onFinished();

        default void onDisplayBlanked() {
        }
    }
}
