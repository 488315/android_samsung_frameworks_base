package com.android.systemui.media.controls.ui.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TraceUtilsKt;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
import com.android.systemui.complication.Complication;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.dream.MediaDreamComplication;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Collection;
import java.util.Iterator;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlow;

public final class MediaHierarchyManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float animationCrossFadeProgress;
    public boolean animationPending;
    public float animationStartAlpha;
    public float animationStartCrossFadeProgress;
    public final ValueAnimator animator;
    public final KeyguardBypassController bypassController;
    public float carouselAlpha;
    public boolean collapsingShadeFromQS;
    public final Context context;
    public int currentAttachmentLocation;
    public int desiredLocation;
    public int distanceForFullShadeTransition;
    public boolean dozeAnimationRunning;
    public boolean dreamMediaComplicationActive;
    public boolean dreamOverlayActive;
    public final DreamOverlayStateController dreamOverlayStateController;
    public float fullShadeTransitionProgress;
    public boolean fullyAwake;
    public boolean goingToSleep;
    public boolean isCommunalShowing;
    public boolean isCrossFadeAnimatorRunning;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Uri lockScreenMediaPlayerUri;
    public final MediaViewLogger logger;
    public final MediaCarouselController mediaCarouselController;
    public final MediaFlags mediaFlags;
    public final MediaHost[] mediaHosts;
    public final MediaDataManager mediaManager;
    public boolean onCommunalDreamingAndShadeExpanding;
    public boolean onCommunalNotDreaming;
    public int previousLocation;
    public boolean qsExpanded;
    public float qsExpansion;
    public ViewGroupOverlay rootOverlay;
    public View rootView;
    public final SecureSettings secureSettings;
    public boolean skipQqsOnExpansion;
    public final SplitShadeStateController splitShadeStateController;
    public final MediaHierarchyManager$startAnimation$1 startAnimation;
    public final SysuiStatusBarStateController statusBarStateController;
    public int statusbarState;
    public boolean allowMediaPlayerOnLockScreen = true;
    public final Rect currentBounds = new Rect();
    public final Rect animationStartBounds = new Rect();
    public final Rect animationStartClipping = new Rect();
    public Rect currentClipping = new Rect();
    public Rect targetClipping = new Rect();
    public int crossFadeAnimationStartLocation = -1;
    public int crossFadeAnimationEndLocation = -1;
    public Rect targetBounds = new Rect();

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$5, reason: invalid class name */
    final /* synthetic */ class AnonymousClass5 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass5(Object obj) {
            super(0, obj, MediaHierarchyManager.class, "updateUserVisibility", "updateUserVisibility()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            MediaHierarchyManager mediaHierarchyManager = (MediaHierarchyManager) this.receiver;
            int i = MediaHierarchyManager.$r8$clinit;
            mediaHierarchyManager.updateUserVisibility();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        int label;
        final /* synthetic */ MediaHierarchyManager this$0;

        public AnonymousClass7(ShadeInteractor shadeInteractor, MediaHierarchyManager mediaHierarchyManager, Continuation continuation) {
            super(2, continuation);
            this.$shadeInteractor = shadeInteractor;
            this.this$0 = mediaHierarchyManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass7(this.$shadeInteractor, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow isQsBypassingShade = ((ShadeInteractorImpl) this.$shadeInteractor).baseShadeInteractor.isQsBypassingShade();
                final MediaHierarchyManager mediaHierarchyManager = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.7.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MediaHierarchyManager mediaHierarchyManager2 = MediaHierarchyManager.this;
                        mediaHierarchyManager2.skipQqsOnExpansion = booleanValue;
                        MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager2, false, 3);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (isQsBypassingShade.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        int label;
        final /* synthetic */ MediaHierarchyManager this$0;

        public AnonymousClass8(ShadeInteractor shadeInteractor, MediaHierarchyManager mediaHierarchyManager, Continuation continuation) {
            super(2, continuation);
            this.$shadeInteractor = shadeInteractor;
            this.this$0 = mediaHierarchyManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass8(this.$shadeInteractor, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow shadeExpansion = ((ShadeInteractorImpl) this.$shadeInteractor).baseShadeInteractor.getShadeExpansion();
                final MediaHierarchyManager mediaHierarchyManager = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.8.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        float floatValue = ((Number) obj2).floatValue();
                        if (floatValue >= 1.0f || floatValue <= 0.0f) {
                            MediaHierarchyManager.this.setTransitionToFullShadeAmount(floatValue);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (shadeExpansion.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$9, reason: invalid class name */
    final class AnonymousClass9 extends SuspendLambda implements Function2 {
        final /* synthetic */ CommunalTransitionViewModel $communalTransitionViewModel;
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        int label;
        final /* synthetic */ MediaHierarchyManager this$0;

        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$9$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            /* synthetic */ float F$0;
            int label;

            public AnonymousClass1(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
                anonymousClass1.F$0 = ((Number) obj).floatValue();
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(this.F$0 < 0.4f);
            }
        }

        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$9$3, reason: invalid class name */
        final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function4 {
            public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

            public AnonymousClass3() {
                super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                Boolean bool2 = (Boolean) obj2;
                bool2.booleanValue();
                Boolean bool3 = (Boolean) obj3;
                bool3.booleanValue();
                return new Triple(bool, bool2, bool3);
            }
        }

        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$9$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MediaHierarchyManager this$0;

            public AnonymousClass4(MediaHierarchyManager mediaHierarchyManager, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaHierarchyManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.this$0, continuation);
                anonymousClass4.L$0 = obj;
                return anonymousClass4;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Triple triple = (Triple) this.L$0;
                boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
                boolean booleanValue2 = ((Boolean) triple.component2()).booleanValue();
                boolean booleanValue3 = ((Boolean) triple.component3()).booleanValue();
                MediaHierarchyManager mediaHierarchyManager = this.this$0;
                mediaHierarchyManager.isCommunalShowing = booleanValue;
                boolean z = false;
                mediaHierarchyManager.onCommunalDreamingAndShadeExpanding = booleanValue && booleanValue2 && booleanValue3;
                if (booleanValue && !booleanValue2) {
                    z = true;
                }
                mediaHierarchyManager.onCommunalNotDreaming = z;
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass9(CommunalTransitionViewModel communalTransitionViewModel, MediaHierarchyManager mediaHierarchyManager, ShadeInteractor shadeInteractor, Continuation continuation) {
            super(2, continuation);
            this.$communalTransitionViewModel = communalTransitionViewModel;
            this.this$0 = mediaHierarchyManager;
            this.$shadeInteractor = shadeInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass9(this.$communalTransitionViewModel, this.this$0, this.$shadeInteractor, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(this.$communalTransitionViewModel.isUmoOnCommunal, this.this$0.keyguardInteractor.isDreaming, FlowKt.distinctUntilChanged(FlowKt.mapLatest(((ShadeInteractorImpl) this.$shadeInteractor).baseShadeInteractor.getShadeExpansion(), new AnonymousClass1(null))), AnonymousClass3.INSTANCE);
                AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(combine, anonymousClass4, this) == coroutineSingletons) {
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

    public MediaHierarchyManager(Context context, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, MediaCarouselController mediaCarouselController, MediaDataManager mediaDataManager, KeyguardViewController keyguardViewController, DreamOverlayStateController dreamOverlayStateController, KeyguardInteractor keyguardInteractor, CommunalTransitionViewModel communalTransitionViewModel, ConfigurationController configurationController, WakefulnessLifecycle wakefulnessLifecycle, ShadeInteractor shadeInteractor, SecureSettings secureSettings, final Handler handler, CoroutineScope coroutineScope, SplitShadeStateController splitShadeStateController, MediaViewLogger mediaViewLogger, MediaFlags mediaFlags) {
        this.context = context;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.keyguardStateController = keyguardStateController;
        this.bypassController = keyguardBypassController;
        this.mediaCarouselController = mediaCarouselController;
        this.mediaManager = mediaDataManager;
        this.keyguardViewController = keyguardViewController;
        this.dreamOverlayStateController = dreamOverlayStateController;
        this.keyguardInteractor = keyguardInteractor;
        this.secureSettings = secureSettings;
        this.splitShadeStateController = splitShadeStateController;
        this.logger = mediaViewLogger;
        this.mediaFlags = mediaFlags;
        this.lockScreenMediaPlayerUri = secureSettings.getUriFor("media_controls_lock_screen");
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        this.statusbarState = statusBarStateControllerImpl.mState;
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float lerp;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                int i = MediaHierarchyManager.$r8$clinit;
                mediaHierarchyManager.updateTargetState();
                float animatedFraction = ofFloat.getAnimatedFraction();
                MediaHierarchyManager mediaHierarchyManager2 = MediaHierarchyManager.this;
                if (mediaHierarchyManager2.isCrossFadeAnimatorRunning) {
                    mediaHierarchyManager2.animationCrossFadeProgress = MathUtils.lerp(mediaHierarchyManager2.animationStartCrossFadeProgress, 1.0f, ofFloat.getAnimatedFraction());
                    float f = MediaHierarchyManager.this.animationCrossFadeProgress;
                    float f2 = f < 0.5f ? 0.0f : 1.0f;
                    lerp = f <= 0.5f ? 1.0f - (f / 0.5f) : (f - 0.5f) / 0.5f;
                    animatedFraction = f2;
                } else {
                    lerp = MathUtils.lerp(mediaHierarchyManager2.animationStartAlpha, 1.0f, ofFloat.getAnimatedFraction());
                }
                MediaHierarchyManager mediaHierarchyManager3 = MediaHierarchyManager.this;
                MediaHierarchyManager.interpolateBounds(mediaHierarchyManager3.animationStartBounds, mediaHierarchyManager3.targetBounds, animatedFraction, mediaHierarchyManager3.currentBounds);
                MediaHierarchyManager mediaHierarchyManager4 = MediaHierarchyManager.this;
                Rect rect = mediaHierarchyManager4.currentClipping;
                if (mediaHierarchyManager4.animationStartClipping.isEmpty()) {
                    rect.set(mediaHierarchyManager4.targetClipping);
                } else if (mediaHierarchyManager4.targetClipping.isEmpty()) {
                    rect.set(mediaHierarchyManager4.animationStartClipping);
                } else {
                    rect.setIntersect(mediaHierarchyManager4.animationStartClipping, mediaHierarchyManager4.targetClipping);
                }
                MediaHierarchyManager mediaHierarchyManager5 = MediaHierarchyManager.this;
                MediaHierarchyManager.applyState$default(mediaHierarchyManager5, mediaHierarchyManager5.currentBounds, lerp, false, mediaHierarchyManager5.currentClipping, 4);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$animator$1$2
            public boolean cancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.cancelled = true;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                mediaHierarchyManager.animationPending = false;
                View view = mediaHierarchyManager.rootView;
                if (view != null) {
                    view.removeCallbacks(mediaHierarchyManager.startAnimation);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                mediaHierarchyManager.isCrossFadeAnimatorRunning = false;
                if (this.cancelled) {
                    return;
                }
                mediaHierarchyManager.applyTargetStateIfNotAnimating();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                this.cancelled = false;
                MediaHierarchyManager.this.animationPending = false;
            }
        });
        this.animator = ofFloat;
        this.mediaHosts = new MediaHost[5];
        this.previousLocation = -1;
        this.desiredLocation = -1;
        this.currentAttachmentLocation = -1;
        this.startAnimation = new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$startAnimation$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaHierarchyManager.this.animator.start();
            }
        };
        this.animationCrossFadeProgress = 1.0f;
        this.carouselAlpha = 1.0f;
        this.distanceForFullShadeTransition = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_media_transition_distance);
        context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = MediaHierarchyManager.$r8$clinit;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                mediaHierarchyManager.distanceForFullShadeTransition = mediaHierarchyManager.context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_media_transition_distance);
                mediaHierarchyManager.context.getResources();
                ((SplitShadeStateControllerImpl) mediaHierarchyManager.splitShadeStateController).shouldUseSplitNotificationShade();
                mediaHierarchyManager.updateDesiredLocation(true, true);
            }
        });
        statusBarStateControllerImpl.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.2
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                boolean z = (f == 0.0f || f == 1.0f) ? false : true;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                if (mediaHierarchyManager.dozeAnimationRunning != z) {
                    mediaHierarchyManager.dozeAnimationRunning = z;
                    if (z) {
                        return;
                    }
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                if (z) {
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
                    mediaHierarchyManager.setQsExpanded(false);
                    MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                    mediaHierarchyManager.mediaCarouselController.closeGuts(true);
                } else {
                    if (mediaHierarchyManager.dozeAnimationRunning) {
                        mediaHierarchyManager.dozeAnimationRunning = false;
                        MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
                    }
                    if (mediaHierarchyManager.isLockScreenVisibleToUser()) {
                        mediaHierarchyManager.mediaCarouselController.logSmartspaceImpression(mediaHierarchyManager.qsExpanded);
                    }
                }
                mediaHierarchyManager.updateUserVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                int i = MediaHierarchyManager.$r8$clinit;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                if (mediaHierarchyManager.isHomeScreenShadeVisibleToUser()) {
                    mediaHierarchyManager.mediaCarouselController.logSmartspaceImpression(mediaHierarchyManager.qsExpanded);
                }
                mediaHierarchyManager.updateUserVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                int i2 = MediaHierarchyManager.$r8$clinit;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                mediaHierarchyManager.updateTargetState();
                if (i == 2 && mediaHierarchyManager.isLockScreenShadeVisibleToUser()) {
                    mediaHierarchyManager.mediaCarouselController.logSmartspaceImpression(mediaHierarchyManager.qsExpanded);
                }
                mediaHierarchyManager.updateUserVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStatePreChange(int i, int i2) {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                if (i2 == 2 && i == 1 && mediaHierarchyManager.fullShadeTransitionProgress < 1.0f) {
                    mediaHierarchyManager.setTransitionToFullShadeAmount(mediaHierarchyManager.distanceForFullShadeTransition);
                }
                mediaHierarchyManager.statusbarState = i2;
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
            }
        });
        dreamOverlayStateController.addCallback(new DreamOverlayStateController.Callback() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.3
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onComplicationsChanged() {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                Collection complications = mediaHierarchyManager.dreamOverlayStateController.getComplications();
                boolean z = false;
                if (!complications.isEmpty()) {
                    Iterator it = complications.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (((Complication) it.next()) instanceof MediaDreamComplication) {
                            z = true;
                            break;
                        }
                    }
                }
                if (mediaHierarchyManager.dreamMediaComplicationActive != z) {
                    mediaHierarchyManager.dreamMediaComplicationActive = z;
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
                }
            }

            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                boolean isOverlayActive = mediaHierarchyManager.dreamOverlayStateController.isOverlayActive();
                if (mediaHierarchyManager.dreamOverlayActive != isOverlayActive) {
                    mediaHierarchyManager.dreamOverlayActive = isOverlayActive;
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
                }
            }
        });
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.4
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                MediaHierarchyManager.access$setGoingToSleep(MediaHierarchyManager.this, false);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                MediaHierarchyManager.access$setGoingToSleep(mediaHierarchyManager, false);
                if (!mediaHierarchyManager.fullyAwake) {
                    mediaHierarchyManager.fullyAwake = true;
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                MediaHierarchyManager.access$setGoingToSleep(mediaHierarchyManager, true);
                if (mediaHierarchyManager.fullyAwake) {
                    mediaHierarchyManager.fullyAwake = false;
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                MediaHierarchyManager.access$setGoingToSleep(MediaHierarchyManager.this, false);
            }
        });
        mediaCarouselController.updateUserVisibility = new AnonymousClass5(this);
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                for (MediaHost mediaHost : MediaHierarchyManager.this.mediaHosts) {
                    if (mediaHost != null) {
                        mediaHost.updateViewVisibility();
                    }
                }
                return Unit.INSTANCE;
            }
        };
        mediaCarouselController.updateHostVisibility = function0;
        mediaCarouselController.mediaCarouselViewModel.updateHostVisibility = function0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(shadeInteractor, this, null), 3);
        Flags.FEATURE_FLAGS.getClass();
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass8(shadeInteractor, this, null), 3);
        secureSettings.registerContentObserverForUserSync("media_controls_lock_screen", new ContentObserver(handler) { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (Intrinsics.areEqual(uri, MediaHierarchyManager.this.lockScreenMediaPlayerUri)) {
                    MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                    mediaHierarchyManager.allowMediaPlayerOnLockScreen = mediaHierarchyManager.secureSettings.getBoolForUser("media_controls_lock_screen", true, -2);
                }
            }
        }, -1);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass9(communalTransitionViewModel, this, shadeInteractor, null), 3);
    }

    public static final void access$setGoingToSleep(MediaHierarchyManager mediaHierarchyManager, boolean z) {
        if (mediaHierarchyManager.goingToSleep != z) {
            mediaHierarchyManager.goingToSleep = z;
            if (z) {
                return;
            }
            updateDesiredLocation$default(mediaHierarchyManager, false, 3);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0051, code lost:
    
        if ((r7.isTransitioningToFullShade() ? true : r7.isCrossFadeAnimatorRunning) != false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void applyState$default(com.android.systemui.media.controls.ui.controller.MediaHierarchyManager r7, android.graphics.Rect r8, float r9, boolean r10, android.graphics.Rect r11, int r12) {
        /*
            r0 = r12 & 4
            r1 = 0
            if (r0 == 0) goto L6
            r10 = r1
        L6:
            r12 = r12 & 8
            if (r12 == 0) goto Lc
            android.graphics.Rect r11 = com.android.systemui.media.controls.ui.controller.MediaHierarchyManagerKt.EMPTY_RECT
        Lc:
            com.android.systemui.media.controls.ui.controller.MediaCarouselController r12 = r7.mediaCarouselController
            boolean r0 = android.os.Trace.isEnabled()
            if (r0 == 0) goto L19
            java.lang.String r2 = "MediaHierarchyManager#applyState"
            com.android.app.tracing.TraceUtilsKt.beginSlice(r2)
        L19:
            android.graphics.Rect r2 = r7.currentBounds     // Catch: java.lang.Throwable -> L98
            r2.set(r8)     // Catch: java.lang.Throwable -> L98
            r7.currentClipping = r11     // Catch: java.lang.Throwable -> L98
            boolean r8 = r7.isTransitioningToFullShade()     // Catch: java.lang.Throwable -> L98
            r11 = 1
            if (r8 == 0) goto L29
            r8 = r11
            goto L2b
        L29:
            boolean r8 = r7.isCrossFadeAnimatorRunning     // Catch: java.lang.Throwable -> L98
        L2b:
            r2 = 1065353216(0x3f800000, float:1.0)
            if (r8 == 0) goto L30
            goto L31
        L30:
            r9 = r2
        L31:
            float r8 = r7.carouselAlpha     // Catch: java.lang.Throwable -> L98
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L38
            goto L41
        L38:
            r7.carouselAlpha = r9     // Catch: java.lang.Throwable -> L98
            com.android.systemui.media.controls.ui.controller.MediaCarouselController r8 = r7.mediaCarouselController     // Catch: java.lang.Throwable -> L98
            android.view.ViewGroup r8 = r8.mediaFrame     // Catch: java.lang.Throwable -> L98
            com.android.systemui.statusbar.CrossFadeHelper.fadeIn(r8, r9, r1)     // Catch: java.lang.Throwable -> L98
        L41:
            boolean r8 = r7.isCurrentlyInGuidedTransformation()     // Catch: java.lang.Throwable -> L98
            if (r8 == 0) goto L53
            boolean r8 = r7.isTransitioningToFullShade()     // Catch: java.lang.Throwable -> L98
            if (r8 == 0) goto L4f
            r8 = r11
            goto L51
        L4f:
            boolean r8 = r7.isCrossFadeAnimatorRunning     // Catch: java.lang.Throwable -> L98
        L51:
            if (r8 == 0) goto L54
        L53:
            r1 = r11
        L54:
            r8 = -1
            if (r1 == 0) goto L59
            r9 = r8
            goto L5b
        L59:
            int r9 = r7.previousLocation     // Catch: java.lang.Throwable -> L98
        L5b:
            if (r1 == 0) goto L5e
            goto L62
        L5e:
            float r2 = r7.getTransformationProgress()     // Catch: java.lang.Throwable -> L98
        L62:
            boolean r11 = r7.isCrossFadeAnimatorRunning     // Catch: java.lang.Throwable -> L98
            if (r11 == 0) goto L7a
            float r11 = r7.animationCrossFadeProgress     // Catch: java.lang.Throwable -> L98
            double r3 = (double) r11     // Catch: java.lang.Throwable -> L98
            r5 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r11 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r11 > 0) goto L77
            int r11 = r7.previousLocation     // Catch: java.lang.Throwable -> L98
            if (r11 != r8) goto L74
            goto L77
        L74:
            int r8 = r7.crossFadeAnimationStartLocation     // Catch: java.lang.Throwable -> L98
            goto L7c
        L77:
            int r8 = r7.crossFadeAnimationEndLocation     // Catch: java.lang.Throwable -> L98
            goto L7c
        L7a:
            int r8 = r7.desiredLocation     // Catch: java.lang.Throwable -> L98
        L7c:
            r12.setCurrentState(r9, r8, r2, r10)     // Catch: java.lang.Throwable -> L98
            r7.updateHostAttachment()     // Catch: java.lang.Throwable -> L98
            int r8 = r7.currentAttachmentLocation     // Catch: java.lang.Throwable -> L98
            r9 = -1000(0xfffffffffffffc18, float:NaN)
            if (r8 != r9) goto La9
            android.graphics.Rect r8 = r7.currentClipping     // Catch: java.lang.Throwable -> L98
            boolean r8 = r8.isEmpty()     // Catch: java.lang.Throwable -> L98
            if (r8 != 0) goto L9a
            android.graphics.Rect r8 = r7.currentBounds     // Catch: java.lang.Throwable -> L98
            android.graphics.Rect r9 = r7.currentClipping     // Catch: java.lang.Throwable -> L98
            r8.intersect(r9)     // Catch: java.lang.Throwable -> L98
            goto L9a
        L98:
            r7 = move-exception
            goto Lb1
        L9a:
            android.view.ViewGroup r8 = r12.mediaFrame     // Catch: java.lang.Throwable -> L98
            android.graphics.Rect r7 = r7.currentBounds     // Catch: java.lang.Throwable -> L98
            int r9 = r7.left     // Catch: java.lang.Throwable -> L98
            int r10 = r7.top     // Catch: java.lang.Throwable -> L98
            int r11 = r7.right     // Catch: java.lang.Throwable -> L98
            int r7 = r7.bottom     // Catch: java.lang.Throwable -> L98
            r8.setLeftTopRightBottom(r9, r10, r11, r7)     // Catch: java.lang.Throwable -> L98
        La9:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L98
            if (r0 == 0) goto Lb0
            com.android.app.tracing.TraceUtilsKt.endSlice()
        Lb0:
            return
        Lb1:
            if (r0 == 0) goto Lb6
            com.android.app.tracing.TraceUtilsKt.endSlice()
        Lb6:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.applyState$default(com.android.systemui.media.controls.ui.controller.MediaHierarchyManager, android.graphics.Rect, float, boolean, android.graphics.Rect, int):void");
    }

    public static Rect interpolateBounds(Rect rect, Rect rect2, float f, Rect rect3) {
        int lerp = (int) MathUtils.lerp(rect.left, rect2.left, f);
        int lerp2 = (int) MathUtils.lerp(rect.top, rect2.top, f);
        int lerp3 = (int) MathUtils.lerp(rect.right, rect2.right, f);
        int lerp4 = (int) MathUtils.lerp(rect.bottom, rect2.bottom, f);
        if (rect3 == null) {
            rect3 = new Rect();
        }
        rect3.set(lerp, lerp2, lerp3, lerp4);
        return rect3;
    }

    public static /* synthetic */ void updateDesiredLocation$default(MediaHierarchyManager mediaHierarchyManager, boolean z, int i) {
        if ((i & 1) != 0) {
            z = false;
        }
        mediaHierarchyManager.updateDesiredLocation(z, false);
    }

    public final void applyTargetStateIfNotAnimating() {
        if (this.animator.isRunning()) {
            return;
        }
        applyState$default(this, this.targetBounds, this.carouselAlpha, false, this.targetClipping, 4);
    }

    public final int calculateLocation() {
        int i;
        MediaHost host;
        if (this.goingToSleep || this.dozeAnimationRunning) {
            return this.desiredLocation;
        }
        boolean z = !this.bypassController.getBypassEnabled() && this.statusbarState == 1;
        boolean z2 = (this.onCommunalNotDreaming && this.qsExpansion == 0.0f) || this.onCommunalDreamingAndShadeExpanding;
        this.mediaFlags.getClass();
        Flags.sceneContainer();
        if (this.dreamOverlayActive && this.dreamMediaComplicationActive) {
            i = 3;
        } else {
            if (!z2) {
                float f = this.qsExpansion;
                if ((f > 0.0f && !z) || (f > 0.4f && z)) {
                    i = 0;
                } else {
                    if (!z || !isTransitioningToFullShade() || this.fullShadeTransitionProgress <= 0.5f) {
                        if (!this.isCommunalShowing) {
                            if (z && this.allowMediaPlayerOnLockScreen) {
                                i = 2;
                            }
                        }
                    }
                    i = 1;
                }
            }
            i = 4;
        }
        if (i == 2 && (((host = getHost(i)) == null || !host.state.visible) && !((StatusBarStateControllerImpl) this.statusBarStateController).mIsDozing)) {
            return 0;
        }
        if (i == 2 && this.desiredLocation == 0 && this.collapsingShadeFromQS) {
            return 0;
        }
        if (i != 2 && this.desiredLocation == 2 && !this.fullyAwake) {
            return 2;
        }
        if (this.isCommunalShowing) {
            return i;
        }
        if (this.skipQqsOnExpansion) {
            return 0;
        }
        return i;
    }

    public final int calculateTransformationType() {
        if (isHubTransition() || isTransitioningToFullShade()) {
            return 1;
        }
        int i = this.previousLocation;
        if ((i == 2 && this.desiredLocation == 0) || (i == 0 && this.desiredLocation == 2)) {
            return 1;
        }
        return (i == 2 && this.desiredLocation == 1) ? 1 : 0;
    }

    public final void cancelAnimationAndApplyDesiredState() {
        this.animator.cancel();
        MediaHost host = getHost(this.desiredLocation);
        if (host != null) {
            applyState$default(this, host.getCurrentBounds(), 1.0f, true, null, 8);
        }
    }

    public final Pair getAnimationParams(int i, int i2) {
        long j;
        long j2 = 0;
        if (i == 2 && i2 == 1) {
            if (this.statusbarState == 0) {
                KeyguardStateController keyguardStateController = this.keyguardStateController;
                if (((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway) {
                    j2 = ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDelay;
                }
            }
            j = 224;
        } else {
            j = (i == 1 && i2 == 2) ? 464L : 200L;
        }
        return new Pair(Long.valueOf(j), Long.valueOf(j2));
    }

    public final MediaHost getHost(int i) {
        if (i < 0) {
            return null;
        }
        return this.mediaHosts[i];
    }

    public final float getQSTransformationProgress() {
        MediaHost host = getHost(this.desiredLocation);
        MediaHost host2 = getHost(this.previousLocation);
        if (host == null || host.location != 0 || host2 == null || host2.location != 1) {
            return -1.0f;
        }
        if (host2.state.visible || this.statusbarState != 1) {
            return this.qsExpansion;
        }
        return -1.0f;
    }

    public final float getTransformationProgress() {
        if (!this.skipQqsOnExpansion && !isHubTransition()) {
            float qSTransformationProgress = getQSTransformationProgress();
            if (this.statusbarState != 1 && qSTransformationProgress >= 0.0f) {
                return qSTransformationProgress;
            }
            if (isTransitioningToFullShade()) {
                return this.fullShadeTransitionProgress;
            }
        }
        return -1.0f;
    }

    public final boolean isCurrentlyInGuidedTransformation() {
        MediaHost host;
        MediaHost host2;
        return this.previousLocation != -1 && this.desiredLocation != -1 && getTransformationProgress() >= 0.0f && (((host = getHost(this.previousLocation)) != null && host.state.visible && (host2 = getHost(this.desiredLocation)) != null && host2.state.visible) || !this.mediaManager.hasActiveMediaOrRecommendation());
    }

    public final boolean isHomeScreenShadeVisibleToUser() {
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        return !((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsDozing && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 0 && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsExpanded;
    }

    public final boolean isHubTransition() {
        int i = this.desiredLocation;
        return i == 4 || (this.previousLocation == 4 && i == 0);
    }

    public final boolean isLockScreenShadeVisibleToUser() {
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        if (!((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsDozing && !this.keyguardViewController.isBouncerShowing()) {
            if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 2) {
                return true;
            }
            if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1 && this.qsExpanded) {
                return true;
            }
        }
        return false;
    }

    public final boolean isLockScreenVisibleToUser() {
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        return !((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsDozing && !this.keyguardViewController.isBouncerShowing() && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1 && this.allowMediaPlayerOnLockScreen && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsExpanded && !this.qsExpanded;
    }

    public final boolean isTransitioningToFullShade() {
        return (this.fullShadeTransitionProgress == 0.0f || this.bypassController.getBypassEnabled() || this.statusbarState != 1) ? false : true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void performTransitionToNewLocation(boolean r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.performTransitionToNewLocation(boolean, boolean):void");
    }

    public final UniqueObjectHostView register(MediaHost mediaHost) {
        final UniqueObjectHostView uniqueObjectHostView = new UniqueObjectHostView(this.context);
        uniqueObjectHostView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$createUniqueObjectHost$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                if (mediaHierarchyManager.rootOverlay == null) {
                    mediaHierarchyManager.rootView = uniqueObjectHostView.getViewRootImpl().getView();
                    MediaHierarchyManager mediaHierarchyManager2 = MediaHierarchyManager.this;
                    View view2 = mediaHierarchyManager2.rootView;
                    Intrinsics.checkNotNull(view2);
                    mediaHierarchyManager2.rootOverlay = (ViewGroupOverlay) view2.getOverlay();
                }
                uniqueObjectHostView.removeOnAttachStateChangeListener(this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        mediaHost.hostView = uniqueObjectHostView;
        mediaHost.visibleChangedListeners.add(new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$register$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Boolean) obj).getClass();
                MediaHierarchyManager.updateDesiredLocation$default(MediaHierarchyManager.this, true, 2);
                return Unit.INSTANCE;
            }
        });
        int i = mediaHost.location;
        this.mediaHosts[i] = mediaHost;
        if (i == this.desiredLocation) {
            this.desiredLocation = -1;
        }
        if (i == this.currentAttachmentLocation) {
            this.currentAttachmentLocation = -1;
        }
        updateDesiredLocation$default(this, false, 3);
        return uniqueObjectHostView;
    }

    public final void setQsExpanded(boolean z) {
        boolean z2 = this.qsExpanded;
        MediaCarouselController mediaCarouselController = this.mediaCarouselController;
        if (z2 != z) {
            this.qsExpanded = z;
            mediaCarouselController.mediaCarouselScrollHandler.qsExpanded = z;
        }
        if (z && (isLockScreenShadeVisibleToUser() || isHomeScreenShadeVisibleToUser())) {
            mediaCarouselController.logSmartspaceImpression(z);
        }
        updateUserVisibility();
    }

    public final void setTransitionToFullShadeAmount(float f) {
        float saturate = MathUtils.saturate(f / this.distanceForFullShadeTransition);
        if (this.fullShadeTransitionProgress == saturate) {
            return;
        }
        this.fullShadeTransitionProgress = saturate;
        if (this.bypassController.getBypassEnabled()) {
            return;
        }
        if (this.statusbarState != 1) {
            return;
        }
        updateDesiredLocation$default(this, isTransitioningToFullShade() ? true : this.isCrossFadeAnimatorRunning, 2);
        if (saturate >= 0.0f) {
            updateTargetState();
            float f2 = this.fullShadeTransitionProgress;
            float f3 = f2 <= 0.5f ? 1.0f - (f2 / 0.5f) : (f2 - 0.5f) / 0.5f;
            if (this.carouselAlpha != f3) {
                this.carouselAlpha = f3;
                CrossFadeHelper.fadeIn((View) this.mediaCarouselController.mediaFrame, f3, false);
            }
            applyTargetStateIfNotAnimating();
        }
    }

    public final boolean shouldAnimateTransition(int i, int i2) {
        Object parent;
        if (isCurrentlyInGuidedTransformation() || this.skipQqsOnExpansion || isHubTransition()) {
            return false;
        }
        if (i2 == 2 && this.desiredLocation == 1 && this.statusbarState == 0) {
            return false;
        }
        if (i == 1 && i2 == 2 && (((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide || this.statusbarState == 2)) {
            return true;
        }
        if (this.desiredLocation == 0 && i2 == 2 && this.statusbarState == 0) {
            return false;
        }
        if (this.statusbarState == 1 && (i == 2 || i2 == 2)) {
            return false;
        }
        View view = this.mediaCarouselController.mediaFrame;
        Rect rect = MediaHierarchyManagerKt.EMPTY_RECT;
        while (view.getVisibility() == 0 && view.getAlpha() != 0.0f && (parent = view.getParent()) != null) {
            if (!(parent instanceof View)) {
                break;
            }
            view = (View) parent;
        }
        if (!this.animator.isRunning() && !this.animationPending) {
            return false;
        }
        return true;
    }

    public final void updateDesiredLocation(boolean z, boolean z2) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaHierarchyManager#updateDesiredLocation");
        }
        try {
            int calculateLocation = calculateLocation();
            int i = this.desiredLocation;
            if (calculateLocation != i || (z2 && !this.goingToSleep && !this.dozeAnimationRunning)) {
                if (i >= 0 && calculateLocation != i) {
                    this.previousLocation = i;
                } else if (z2) {
                    boolean z3 = !this.bypassController.getBypassEnabled() && this.statusbarState == 1;
                    if (calculateLocation == 0 && this.previousLocation == 2 && !z3) {
                        this.previousLocation = 1;
                    }
                }
                boolean z4 = this.desiredLocation == -1;
                this.desiredLocation = calculateLocation;
                boolean z5 = !z && shouldAnimateTransition(calculateLocation, this.previousLocation);
                Pair animationParams = getAnimationParams(this.previousLocation, calculateLocation);
                long longValue = ((Number) animationParams.component1()).longValue();
                long longValue2 = ((Number) animationParams.component2()).longValue();
                MediaHost host = getHost(calculateLocation);
                if (calculateTransformationType() != 1 || isCurrentlyInGuidedTransformation() || !z5) {
                    this.mediaCarouselController.onDesiredLocationChanged(calculateLocation, host, z5, longValue, longValue2);
                }
                performTransitionToNewLocation(z4, z5);
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final void updateHostAttachment() {
        int i;
        MediaHost host;
        MediaHost host2;
        MediaCarouselController mediaCarouselController = this.mediaCarouselController;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaHierarchyManager#updateHostAttachment");
        }
        try {
            this.mediaFlags.getClass();
            Flags.sceneContainer();
            if (this.isCrossFadeAnimatorRunning) {
                if (this.animationCrossFadeProgress <= 0.5d && this.previousLocation != -1) {
                    i = this.crossFadeAnimationStartLocation;
                }
                i = this.crossFadeAnimationEndLocation;
            } else {
                i = this.desiredLocation;
            }
            boolean z = true;
            boolean z2 = !(isTransitioningToFullShade() ? true : this.isCrossFadeAnimatorRunning) && this.mediaManager.hasActiveMediaOrRecommendation();
            if (this.isCrossFadeAnimatorRunning && (host = getHost(i)) != null && host.state.visible && (host2 = getHost(i)) != null) {
                UniqueObjectHostView uniqueObjectHostView = host2.hostView;
                if (uniqueObjectHostView == null) {
                    uniqueObjectHostView = null;
                }
                if (uniqueObjectHostView != null && !uniqueObjectHostView.isShown() && i != this.desiredLocation) {
                    z2 = true;
                }
            }
            if (((!isCurrentlyInGuidedTransformation() || getTransformationProgress() == 1.0f) && !this.animator.isRunning() && !this.animationPending) || this.rootOverlay == null || !z2) {
                z = false;
            }
            if (z) {
                i = -1000;
            }
            int i2 = i;
            if (this.currentAttachmentLocation != i2) {
                this.currentAttachmentLocation = i2;
                ViewGroup viewGroup = (ViewGroup) mediaCarouselController.mediaFrame.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(mediaCarouselController.mediaFrame);
                }
                if (z) {
                    ViewGroupOverlay viewGroupOverlay = this.rootOverlay;
                    Intrinsics.checkNotNull(viewGroupOverlay);
                    viewGroupOverlay.add(mediaCarouselController.mediaFrame);
                } else {
                    MediaHost host3 = getHost(i2);
                    Intrinsics.checkNotNull(host3);
                    UniqueObjectHostView uniqueObjectHostView2 = host3.hostView;
                    if (uniqueObjectHostView2 == null) {
                        uniqueObjectHostView2 = null;
                    }
                    uniqueObjectHostView2.addView(mediaCarouselController.mediaFrame);
                }
                MediaViewLogger mediaViewLogger = this.logger;
                int i3 = this.currentAttachmentLocation;
                mediaViewLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                MediaViewLogger$logMediaHostAttachment$2 mediaViewLogger$logMediaHostAttachment$2 = new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewLogger$logMediaHostAttachment$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Host (updateHostAttachment): ");
                    }
                };
                LogBuffer logBuffer = mediaViewLogger.buffer;
                LogMessage obtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$logMediaHostAttachment$2, null);
                ((LogMessageImpl) obtain).int1 = i3;
                logBuffer.commit(obtain);
                if (this.isCrossFadeAnimatorRunning) {
                    this.mediaCarouselController.onDesiredLocationChanged(i2, getHost(i2), false, 200L, 0L);
                }
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final void updateTargetState() {
        MediaHost host = getHost(this.previousLocation);
        MediaHost host2 = getHost(this.desiredLocation);
        if (isCurrentlyInGuidedTransformation()) {
            if (!(isTransitioningToFullShade() ? true : this.isCrossFadeAnimatorRunning) && host != null && host2 != null) {
                float transformationProgress = getTransformationProgress();
                if (!host2.state.visible) {
                    host2 = host;
                } else if (host.state.visible) {
                    host2 = host;
                    host = host2;
                } else {
                    host = host2;
                }
                this.targetBounds = interpolateBounds(host2.getCurrentBounds(), host.getCurrentBounds(), transformationProgress, null);
                this.targetClipping = host.currentClipping;
                return;
            }
        }
        if (host2 != null) {
            this.targetBounds.set(host2.getCurrentBounds());
            this.targetClipping = host2.currentClipping;
        }
    }

    public final void updateUserVisibility() {
        boolean z = isLockScreenVisibleToUser() || isLockScreenShadeVisibleToUser() || isHomeScreenShadeVisibleToUser();
        boolean z2 = this.qsExpanded || this.mediaManager.hasActiveMediaOrRecommendation();
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselController.mediaCarouselScrollHandler;
        boolean z3 = z && z2;
        if (mediaCarouselScrollHandler.visibleToUser != z3) {
            mediaCarouselScrollHandler.visibleToUser = z3;
            mediaCarouselScrollHandler.seekBarUpdateListener.invoke(Boolean.valueOf(z3));
            mediaCarouselScrollHandler.visibleStateLogger.log(String.valueOf(mediaCarouselScrollHandler.visibleToUser));
        }
    }
}
