package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.util.MathUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.animation.Interpolators;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsKeyguardViewControllerLegacy extends UdfpsAnimationViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityLaunchAnimator activityLaunchAnimator;
    public final C1075x8ec7d624 activityLaunchAnimatorListener;
    public float activityLaunchProgress;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final ConfigurationController configurationController;
    public final UdfpsKeyguardViewControllerLegacy$configurationListener$1 configurationListener;
    public float inputBouncerExpansion;
    public boolean isLaunchingActivity;
    public final KeyguardStateController keyguardStateController;
    public final C1076xb671a45f keyguardStateControllerCallback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager keyguardViewManager;
    public float lastDozeAmount;
    public boolean launchTransitionFadingAway;
    public final LockscreenShadeTransitionController lockScreenShadeTransitionController;
    public final UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 occludingAppBiometricUI;
    public float panelExpansionFraction;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public float qsExpansion;
    public final UdfpsKeyguardViewControllerLegacy$shadeExpansionListener$1 shadeExpansionListener;
    public boolean showingUdfpsBouncer;
    public final UdfpsKeyguardViewControllerLegacy$stateListener$1 stateListener;
    public final C1079xbb37c88d statusBarKeyguardViewManagerCallback;
    public int statusBarState;
    public float transitionToFullShadeProgress;
    public final UdfpsController udfpsController;
    public boolean udfpsRequested;
    public final UnlockedScreenOffAnimationController unlockedScreenOffAnimationController;
    public final ValueAnimator unlockedScreenOffDozeAnimator;
    public final boolean useExpandedOverlay;
    public final UdfpsKeyguardViewLegacy view;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$1", m277f = "UdfpsKeyguardViewControllerLegacy.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$1 */
    public static final class C10741 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$1$1", m277f = "UdfpsKeyguardViewControllerLegacy.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList, 254}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = udfpsKeyguardViewControllerLegacy;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineScope coroutineScope;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    coroutineScope = (CoroutineScope) this.L$0;
                    UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
                    this.L$0 = coroutineScope;
                    this.label = 1;
                    if (udfpsKeyguardViewControllerLegacy.listenForBouncerExpansion(coroutineScope, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    coroutineScope = (CoroutineScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy2 = this.this$0;
                this.L$0 = null;
                this.label = 2;
                if (udfpsKeyguardViewControllerLegacy2.listenForAlternateBouncerVisibility(coroutineScope, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }

        public C10741(Continuation<? super C10741> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C10741 c10741 = UdfpsKeyguardViewControllerLegacy.this.new C10741((Continuation) obj3);
            c10741.L$0 = (LifecycleOwner) obj;
            return c10741.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(UdfpsKeyguardViewControllerLegacy.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r0v19, types: [com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$keyguardStateControllerCallback$1] */
    /* JADX WARN: Type inference failed for: r0v20, types: [com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$activityLaunchAnimatorListener$1] */
    public UdfpsKeyguardViewControllerLegacy(UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, UdfpsController udfpsController, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor) {
        super(udfpsKeyguardViewLegacy, statusBarStateController, shadeExpansionStateManager, systemUIDialogManager, dumpManager);
        this.view = udfpsKeyguardViewLegacy;
        this.keyguardViewManager = statusBarKeyguardViewManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.lockScreenShadeTransitionController = lockscreenShadeTransitionController;
        this.configurationController = configurationController;
        this.keyguardStateController = keyguardStateController;
        this.unlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.udfpsController = udfpsController;
        this.activityLaunchAnimator = activityLaunchAnimator;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.useExpandedOverlay = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(360L);
        ofFloat.setInterpolator(Interpolators.ALPHA_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$unlockedScreenOffDozeAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy2 = UdfpsKeyguardViewControllerLegacy.this.view;
                valueAnimator.getAnimatedFraction();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                udfpsKeyguardViewLegacy2.mAnimationType = 2;
                udfpsKeyguardViewLegacy2.mInterpolatedDarkAmount = floatValue;
                udfpsKeyguardViewLegacy2.updateAlpha();
            }
        });
        this.unlockedScreenOffDozeAnimator = ofFloat;
        this.stateListener = new UdfpsKeyguardViewControllerLegacy$stateListener$1(this);
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = UdfpsKeyguardViewControllerLegacy.$r8$clinit;
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                UdfpsOverlayParams udfpsOverlayParams = udfpsKeyguardViewControllerLegacy.udfpsController.mOverlayParams;
                if (udfpsOverlayParams != null) {
                    udfpsKeyguardViewControllerLegacy.view.mScaleFactor = udfpsOverlayParams.scaleFactor;
                }
                UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy2 = udfpsKeyguardViewControllerLegacy.view;
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
        this.shadeExpansionListener = new UdfpsKeyguardViewControllerLegacy$shadeExpansionListener$1(this);
        this.keyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$keyguardStateControllerCallback$1
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
        this.activityLaunchAnimatorListener = new ActivityLaunchAnimator.Listener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$activityLaunchAnimatorListener$1
            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public final void onLaunchAnimationEnd() {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.isLaunchingActivity = false;
                udfpsKeyguardViewControllerLegacy.updateAlpha();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public final void onLaunchAnimationProgress(float f) {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.activityLaunchProgress = f;
                udfpsKeyguardViewControllerLegacy.updateAlpha();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public final void onLaunchAnimationStart() {
                UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = UdfpsKeyguardViewControllerLegacy.this;
                udfpsKeyguardViewControllerLegacy.isLaunchingActivity = true;
                udfpsKeyguardViewControllerLegacy.activityLaunchProgress = 0.0f;
                udfpsKeyguardViewControllerLegacy.updateAlpha();
            }
        };
        this.statusBarKeyguardViewManagerCallback = new C1079xbb37c88d(this);
        this.occludingAppBiometricUI = new UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1(this);
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsKeyguardViewLegacy, EmptyCoroutineContext.INSTANCE, new C10741(null));
    }

    public static final void access$showUdfpsBouncer(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy, boolean z) {
        if (udfpsKeyguardViewControllerLegacy.showingUdfpsBouncer == z) {
            return;
        }
        boolean shouldPauseAuth = udfpsKeyguardViewControllerLegacy.shouldPauseAuth();
        udfpsKeyguardViewControllerLegacy.showingUdfpsBouncer = z;
        KeyguardUpdateMonitor keyguardUpdateMonitor = udfpsKeyguardViewControllerLegacy.keyguardUpdateMonitor;
        if (z) {
            UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = udfpsKeyguardViewControllerLegacy.view;
            if (shouldPauseAuth && !udfpsKeyguardViewLegacy.mBackgroundInAnimator.isRunning() && udfpsKeyguardViewLegacy.mFullyInflated) {
                AnimatorSet animatorSet = new AnimatorSet();
                udfpsKeyguardViewLegacy.mBackgroundInAnimator = animatorSet;
                animatorSet.playTogether(ObjectAnimator.ofFloat(udfpsKeyguardViewLegacy.mBgProtection, (Property<ImageView, Float>) View.ALPHA, 0.0f, 1.0f), ObjectAnimator.ofFloat(udfpsKeyguardViewLegacy.mBgProtection, (Property<ImageView, Float>) View.SCALE_X, 0.0f, 1.0f), ObjectAnimator.ofFloat(udfpsKeyguardViewLegacy.mBgProtection, (Property<ImageView, Float>) View.SCALE_Y, 0.0f, 1.0f));
                udfpsKeyguardViewLegacy.mBackgroundInAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                udfpsKeyguardViewLegacy.mBackgroundInAnimator.setDuration(500L);
                udfpsKeyguardViewLegacy.mBackgroundInAnimator.addListener(new AnimatorListenerAdapter(udfpsKeyguardViewLegacy, null) { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewLegacy.1
                    public final /* synthetic */ Runnable val$onEndAnimation;

                    public C10811(UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy2, Runnable runnable) {
                        this.val$onEndAnimation = runnable;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        Runnable runnable = this.val$onEndAnimation;
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                });
                udfpsKeyguardViewLegacy2.mBackgroundInAnimator.start();
            }
            if (((KeyguardStateControllerImpl) udfpsKeyguardViewControllerLegacy.keyguardStateController).mOccluded) {
                keyguardUpdateMonitor.requestFaceAuthOnOccludingApp(true);
            }
            udfpsKeyguardViewLegacy2.announceForAccessibility(udfpsKeyguardViewLegacy2.getContext().getString(R.string.accessibility_fingerprint_bouncer));
        } else {
            keyguardUpdateMonitor.requestFaceAuthOnOccludingApp(false);
        }
        udfpsKeyguardViewControllerLegacy.updateAlpha();
        udfpsKeyguardViewControllerLegacy.updatePauseAuth();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        super.dump(printWriter, strArr);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("showingUdfpsAltBouncer=", this.showingUdfpsBouncer, printWriter);
        printWriter.println("altBouncerInteractor#isAlternateBouncerVisible=" + this.alternateBouncerInteractor.isVisibleState());
        printWriter.println("altBouncerInteractor#canShowAlternateBouncerForFingerprint=false");
        printWriter.println("faceDetectRunning=false");
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("statusBarState=", StatusBarState.toString(this.statusBarState), printWriter);
        printWriter.println("transitionToFullShadeProgress=" + this.transitionToFullShadeProgress);
        printWriter.println("qsExpansion=" + this.qsExpansion);
        printWriter.println("panelExpansionFraction=" + this.panelExpansionFraction);
        UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = this.view;
        SideFpsController$$ExternalSyntheticOutline0.m105m("unpausedAlpha=", udfpsKeyguardViewLegacy.mAlpha, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("udfpsRequestedByApp=", this.udfpsRequested, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("launchTransitionFadingAway=", this.launchTransitionFadingAway, printWriter);
        printWriter.println("lastDozeAmount=" + this.lastDozeAmount);
        printWriter.println("inputBouncerExpansion=" + this.inputBouncerExpansion);
        printWriter.println("UdfpsKeyguardView (" + udfpsKeyguardViewLegacy + ")");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("    mPauseAuth="), udfpsKeyguardViewLegacy.mPauseAuth, printWriter, "    mUnpausedAlpha="), udfpsKeyguardViewLegacy.mAlpha, printWriter, "    mUdfpsRequested="), udfpsKeyguardViewLegacy.mUdfpsRequested, printWriter, "    mInterpolatedDarkAmount="), udfpsKeyguardViewLegacy.mInterpolatedDarkAmount, printWriter, "    mAnimationType="), udfpsKeyguardViewLegacy.mAnimationType, printWriter, "    mUseExpandedOverlay="), udfpsKeyguardViewLegacy.mUseExpandedOverlay, printWriter);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public final String getTag() {
        return "UdfpsKeyguardViewController";
    }

    public final Object listenForAlternateBouncerVisibility(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new C1077xb116dad8(this, null), 3);
    }

    public final Object listenForBouncerExpansion(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2(this, null), 3);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
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
        ((KeyguardBouncerRepositoryImpl) this.alternateBouncerInteractor.bouncerRepository)._alternateBouncerUIAvailable.setValue(Boolean.TRUE);
        StatusBarStateController statusBarStateController = this.statusBarStateController;
        float dozeAmount = statusBarStateController.getDozeAmount();
        this.lastDozeAmount = dozeAmount;
        UdfpsKeyguardViewControllerLegacy$stateListener$1 udfpsKeyguardViewControllerLegacy$stateListener$1 = this.stateListener;
        udfpsKeyguardViewControllerLegacy$stateListener$1.onDozeAmountChanged(dozeAmount, dozeAmount);
        statusBarStateController.addCallback(udfpsKeyguardViewControllerLegacy$stateListener$1);
        this.udfpsRequested = false;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        this.launchTransitionFadingAway = keyguardStateControllerImpl.mLaunchTransitionFadingAway;
        keyguardStateControllerImpl.addCallback(this.keyguardStateControllerCallback);
        this.statusBarState = statusBarStateController.getState();
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.keyguardViewManager;
        this.qsExpansion = statusBarKeyguardViewManager.getQsExpansion();
        ((HashSet) statusBarKeyguardViewManager.mCallbacks).add(this.statusBarKeyguardViewManagerCallback);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        ShadeExpansionStateManager shadeExpansionStateManager = this.shadeExpansionStateManager;
        UdfpsKeyguardViewControllerLegacy$shadeExpansionListener$1 udfpsKeyguardViewControllerLegacy$shadeExpansionListener$1 = this.shadeExpansionListener;
        udfpsKeyguardViewControllerLegacy$shadeExpansionListener$1.onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(udfpsKeyguardViewControllerLegacy$shadeExpansionListener$1));
        UdfpsOverlayParams udfpsOverlayParams = this.udfpsController.mOverlayParams;
        UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = this.view;
        if (udfpsOverlayParams != null) {
            udfpsKeyguardViewLegacy.mScaleFactor = udfpsOverlayParams.scaleFactor;
        }
        udfpsKeyguardViewLegacy.updatePadding();
        updateAlpha();
        updatePauseAuth();
        UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 = statusBarKeyguardViewManager.mOccludingAppBiometricUI;
        UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$12 = this.occludingAppBiometricUI;
        if (!Objects.equals(udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1, udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$12)) {
            statusBarKeyguardViewManager.mOccludingAppBiometricUI = udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$12;
        }
        this.lockScreenShadeTransitionController.mUdfpsKeyguardViewControllerLegacy = this;
        this.activityLaunchAnimator.listeners.add(this.activityLaunchAnimatorListener);
        udfpsKeyguardViewLegacy.mUseExpandedOverlay = this.useExpandedOverlay;
        udfpsKeyguardViewLegacy.startIconAsyncInflate();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((KeyguardBouncerRepositoryImpl) this.alternateBouncerInteractor.bouncerRepository)._alternateBouncerUIAvailable.setValue(Boolean.FALSE);
        ((KeyguardStateControllerImpl) this.keyguardStateController).removeCallback(this.keyguardStateControllerCallback);
        this.statusBarStateController.removeCallback(this.stateListener);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.keyguardViewManager;
        if (Objects.equals(statusBarKeyguardViewManager.mOccludingAppBiometricUI, this.occludingAppBiometricUI)) {
            statusBarKeyguardViewManager.mOccludingAppBiometricUI = null;
        }
        this.keyguardUpdateMonitor.requestFaceAuthOnOccludingApp(false);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
        this.shadeExpansionStateManager.expansionListeners.remove(this.shadeExpansionListener);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.lockScreenShadeTransitionController;
        if (lockscreenShadeTransitionController.mUdfpsKeyguardViewControllerLegacy == this) {
            lockscreenShadeTransitionController.mUdfpsKeyguardViewControllerLegacy = null;
        }
        this.activityLaunchAnimator.listeners.remove(this.activityLaunchAnimatorListener);
        ((HashSet) statusBarKeyguardViewManager.mCallbacks).remove(this.statusBarKeyguardViewManagerCallback);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public final boolean shouldPauseAuth() {
        if (this.showingUdfpsBouncer) {
            return false;
        }
        if (this.udfpsRequested && !this.notificationShadeVisible) {
            if (!(this.inputBouncerExpansion == 1.0f) && ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
                return false;
            }
        }
        if (this.launchTransitionFadingAway) {
            return true;
        }
        if (this.statusBarState == 1 || this.unlockedScreenOffAnimationController.isAnimationPlaying()) {
            return ((this.inputBouncerExpansion > 0.5f ? 1 : (this.inputBouncerExpansion == 0.5f ? 0 : -1)) >= 0) || this.keyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser()) || ((double) this.view.mAlpha) < 25.5d;
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
                interpolation = (int) (interpolation * (1.0f - RangesKt___RangesKt.coerceIn(this.activityLaunchProgress * (ActivityLaunchAnimator.TIMINGS.totalDuration / 83))));
            }
            constrain = (int) (interpolation * udfpsKeyguardViewLegacy.mDialogSuggestedAlpha);
        }
        udfpsKeyguardViewLegacy.mAlpha = constrain;
        udfpsKeyguardViewLegacy.updateAlpha();
    }
}
