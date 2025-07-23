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
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
import com.android.systemui.complication.Complication;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dump.DumpManager;
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
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
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
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaHierarchyManager implements Dumpable {
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
    public boolean isAnyShadeFullyExpanded;
    public boolean isCommunalShowing;
    public boolean isCrossFadeAnimatorRunning;
    public boolean isPrimaryBouncerShowing;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Uri lockScreenMediaPlayerUri;
    public final MediaViewLogger logger;
    public final MediaCarouselController mediaCarouselController;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$10, reason: invalid class name */
    final class AnonymousClass10 extends SuspendLambda implements Function2 {
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        int label;
        final /* synthetic */ MediaHierarchyManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass10(ShadeInteractor shadeInteractor, MediaHierarchyManager mediaHierarchyManager, Continuation continuation) {
            super(2, continuation);
            this.$shadeInteractor = shadeInteractor;
            this.this$0 = mediaHierarchyManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass10(this.$shadeInteractor, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow shadeExpansion = ((ShadeInteractorImpl) this.$shadeInteractor).baseShadeInteractor.getShadeExpansion();
                final MediaHierarchyManager mediaHierarchyManager = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.10.1
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$11, reason: invalid class name */
    final class AnonymousClass11 extends SuspendLambda implements Function2 {
        final /* synthetic */ CommunalTransitionViewModel $communalTransitionViewModel;
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        int label;
        final /* synthetic */ MediaHierarchyManager this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$11$1, reason: invalid class name */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$11$4, reason: invalid class name */
        final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function4 {
            public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

            public AnonymousClass4() {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$11$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MediaHierarchyManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(MediaHierarchyManager mediaHierarchyManager, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaHierarchyManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass5 anonymousClass5 = new AnonymousClass5(this.this$0, continuation);
                anonymousClass5.L$0 = obj;
                return anonymousClass5;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                this.this$0.updateUserVisibility();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass11(CommunalTransitionViewModel communalTransitionViewModel, MediaHierarchyManager mediaHierarchyManager, ShadeInteractor shadeInteractor, Continuation continuation) {
            super(2, continuation);
            this.$communalTransitionViewModel = communalTransitionViewModel;
            this.this$0 = mediaHierarchyManager;
            this.$shadeInteractor = shadeInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass11(this.$communalTransitionViewModel, this.this$0, this.$shadeInteractor, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass11) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(this.$communalTransitionViewModel.isUmoOnCommunal, this.this$0.keyguardInteractor.isDreaming, FlowKt.distinctUntilChanged(FlowKt.mapLatest(((ShadeInteractorImpl) this.$shadeInteractor).baseShadeInteractor.getShadeExpansion(), new AnonymousClass1(null))), AnonymousClass4.INSTANCE);
                AnonymousClass5 anonymousClass5 = new AnonymousClass5(this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(combine, anonymousClass5, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        int label;
        final /* synthetic */ MediaHierarchyManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        final /* synthetic */ ShadeInteractor $shadeInteractor;
        int label;
        final /* synthetic */ MediaHierarchyManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                ReadonlyStateFlow readonlyStateFlow = ((ShadeInteractorImpl) this.$shadeInteractor).isAnyFullyExpanded;
                final MediaHierarchyManager mediaHierarchyManager = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.8.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MediaHierarchyManager mediaHierarchyManager2 = MediaHierarchyManager.this;
                        mediaHierarchyManager2.isAnyShadeFullyExpanded = booleanValue;
                        mediaHierarchyManager2.updateUserVisibility();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$9, reason: invalid class name */
    final class AnonymousClass9 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass9(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaHierarchyManager.this.new AnonymousClass9(continuation);
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
                final MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                ReadonlyStateFlow readonlyStateFlow = mediaHierarchyManager.keyguardInteractor.primaryBouncerShowing;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.9.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MediaHierarchyManager mediaHierarchyManager2 = MediaHierarchyManager.this;
                        mediaHierarchyManager2.isPrimaryBouncerShowing = booleanValue;
                        mediaHierarchyManager2.updateUserVisibility();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r9v5, types: [com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$startAnimation$1] */
    public MediaHierarchyManager(Context context, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, MediaCarouselController mediaCarouselController, MediaDataManager mediaDataManager, KeyguardViewController keyguardViewController, DreamOverlayStateController dreamOverlayStateController, KeyguardInteractor keyguardInteractor, CommunalTransitionViewModel communalTransitionViewModel, ConfigurationController configurationController, WakefulnessLifecycle wakefulnessLifecycle, ShadeInteractor shadeInteractor, SecureSettings secureSettings, final Handler handler, CoroutineScope coroutineScope, SplitShadeStateController splitShadeStateController, MediaViewLogger mediaViewLogger, DumpManager dumpManager) {
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
        this.lockScreenMediaPlayerUri = secureSettings.getUriFor("media_controls_lock_screen");
        this.statusbarState = sysuiStatusBarStateController.getState();
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
                MediaHierarchyManager.applyState$default(mediaHierarchyManager5, mediaHierarchyManager5.currentBounds, lerp, mediaHierarchyManager5.currentClipping, 4);
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
                MediaHierarchyManager.this.isCrossFadeAnimatorRunning = false;
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
        this.mediaHosts = new MediaHost[6];
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
        Rect rect = MediaHierarchyManagerKt.EMPTY_RECT;
        dumpManager.registerNormalDumpable("MediaHierarchyManager", this);
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
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.2
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
                    boolean z2 = mediaHierarchyManager.qsExpanded;
                    MediaCarouselController mediaCarouselController2 = mediaHierarchyManager.mediaCarouselController;
                    if (z2) {
                        mediaHierarchyManager.qsExpanded = false;
                        mediaCarouselController2.mediaCarouselScrollHandler.getClass();
                    }
                    mediaHierarchyManager.updateUserVisibility();
                    MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                    mediaCarouselController2.getClass();
                    MediaCarouselController.closeGuts(true);
                } else if (mediaHierarchyManager.dozeAnimationRunning) {
                    mediaHierarchyManager.dozeAnimationRunning = false;
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
                }
                mediaHierarchyManager.updateUserVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                int i = MediaHierarchyManager.$r8$clinit;
                MediaHierarchyManager.this.updateUserVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                int i2 = MediaHierarchyManager.$r8$clinit;
                MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                mediaHierarchyManager.updateTargetState();
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
                boolean containsState = mediaHierarchyManager.dreamOverlayStateController.containsState(1);
                if (mediaHierarchyManager.dreamOverlayActive != containsState) {
                    mediaHierarchyManager.dreamOverlayActive = containsState;
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
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$$ExternalSyntheticLambda0
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
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(shadeInteractor, this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(shadeInteractor, this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(shadeInteractor, this, null), 7);
        secureSettings.registerContentObserverForUserAsync("media_controls_lock_screen", new ContentObserver(handler) { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (Intrinsics.areEqual(uri, MediaHierarchyManager.this.lockScreenMediaPlayerUri)) {
                    MediaHierarchyManager mediaHierarchyManager = MediaHierarchyManager.this;
                    mediaHierarchyManager.allowMediaPlayerOnLockScreen = mediaHierarchyManager.secureSettings.getBoolForUser("media_controls_lock_screen", true, -2);
                }
            }
        }, -1);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(communalTransitionViewModel, this, shadeInteractor, null), 7);
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

    public static void applyState$default(MediaHierarchyManager mediaHierarchyManager, Rect rect, float f, Rect rect2, int i) {
        int i2;
        boolean z = true;
        boolean z2 = (i & 4) == 0;
        if ((i & 8) != 0) {
            rect2 = MediaHierarchyManagerKt.EMPTY_RECT;
        }
        MediaCarouselController mediaCarouselController = mediaHierarchyManager.mediaCarouselController;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaHierarchyManager#applyState");
        }
        try {
            mediaHierarchyManager.currentBounds.set(rect);
            mediaHierarchyManager.currentClipping = rect2;
            float f2 = 1.0f;
            if (!(mediaHierarchyManager.isTransitioningToFullShade() ? true : mediaHierarchyManager.isCrossFadeAnimatorRunning)) {
                f = 1.0f;
            }
            if (mediaHierarchyManager.carouselAlpha != f) {
                mediaHierarchyManager.carouselAlpha = f;
                CrossFadeHelper.fadeIn((View) mediaHierarchyManager.mediaCarouselController.mediaFrame, f, false);
            }
            if (mediaHierarchyManager.isCurrentlyInGuidedTransformation()) {
                if (!(mediaHierarchyManager.isTransitioningToFullShade() ? true : mediaHierarchyManager.isCrossFadeAnimatorRunning)) {
                    z = false;
                }
            }
            int i3 = z ? -1 : mediaHierarchyManager.previousLocation;
            if (!z) {
                f2 = mediaHierarchyManager.getTransformationProgress();
            }
            if (mediaHierarchyManager.isCrossFadeAnimatorRunning) {
                if (mediaHierarchyManager.animationCrossFadeProgress <= 0.5d && mediaHierarchyManager.previousLocation != -1) {
                    i2 = mediaHierarchyManager.crossFadeAnimationStartLocation;
                }
                i2 = mediaHierarchyManager.crossFadeAnimationEndLocation;
            } else {
                i2 = mediaHierarchyManager.desiredLocation;
            }
            mediaCarouselController.setCurrentState(i3, i2, f2, z2);
            if (mediaHierarchyManager.currentAttachmentLocation == -1000) {
                if (!mediaHierarchyManager.currentClipping.isEmpty()) {
                    mediaHierarchyManager.currentBounds.intersect(mediaHierarchyManager.currentClipping);
                }
                ViewGroup viewGroup = mediaCarouselController.mediaFrame;
                Rect rect3 = mediaHierarchyManager.currentBounds;
                viewGroup.setLeftTopRightBottom(rect3.left, rect3.top, rect3.right, rect3.bottom);
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
        applyState$default(this, this.targetBounds, this.carouselAlpha, this.targetClipping, 4);
    }

    public final int calculateLocation() {
        int i;
        MediaHost host;
        if (this.goingToSleep || this.dozeAnimationRunning) {
            return this.desiredLocation;
        }
        boolean z = !this.bypassController.getBypassEnabled() && this.statusbarState == 1;
        boolean z2 = (this.onCommunalNotDreaming && this.qsExpansion == 0.0f) || this.onCommunalDreamingAndShadeExpanding;
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
        if ((i != 2 || (((host = getHost(i)) != null && host.state.visible) || this.statusBarStateController.isDozing())) && (i != 2 || this.desiredLocation != 0 || !this.collapsingShadeFromQS)) {
            if (i != 2 && this.desiredLocation == 2 && !this.fullyAwake) {
                return 2;
            }
            if (this.isCommunalShowing || !this.skipQqsOnExpansion) {
                return i;
            }
        }
        return 0;
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
            applyState$default(this, host.getCurrentBounds(), 1.0f, null, 8);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int i = this.currentAttachmentLocation;
        int i2 = this.desiredLocation;
        MediaHost host = getHost(i2);
        Boolean valueOf = host != null ? Boolean.valueOf(host.state.visible) : null;
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "current attachment: ", ", desired location: ", ", visible ");
        m.append(valueOf);
        printWriter.println(m.toString());
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("previous location: ", this.previousLocation, printWriter);
        printWriter.println("bounds: " + this.currentBounds + ", target " + this.targetBounds);
        printWriter.println("clipping: " + this.currentClipping + ", target " + this.targetClipping);
    }

    public final Pair getAnimationParams(int i, int i2) {
        long j;
        long j2 = 0;
        if (i == 2 && i2 == 1) {
            if (this.statusbarState == 0) {
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
                if (keyguardStateControllerImpl.mKeyguardFadingAway) {
                    j2 = keyguardStateControllerImpl.mKeyguardFadingAwayDelay;
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
        if (this.previousLocation == -1 || this.desiredLocation == -1 || getTransformationProgress() < 0.0f) {
            return false;
        }
        MediaHost host2 = getHost(this.previousLocation);
        return (host2 != null && host2.state.visible && (host = getHost(this.desiredLocation)) != null && host.state.visible) || !this.mediaManager.hasActiveMediaOrRecommendation();
    }

    public final boolean isHubTransition() {
        int i = this.desiredLocation;
        if (i != 4) {
            return this.previousLocation == 4 && i == 0;
        }
        return true;
    }

    public final boolean isTransitioningToFullShade() {
        return (this.fullShadeTransitionProgress == 0.0f || this.bypassController.getBypassEnabled() || this.statusbarState != 1) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0083 A[Catch: all -> 0x0033, TryCatch #0 {all -> 0x0033, blocks: (B:5:0x000b, B:9:0x0013, B:13:0x0025, B:15:0x002e, B:16:0x00e7, B:23:0x0038, B:25:0x0047, B:28:0x004d, B:31:0x0054, B:32:0x0073, B:35:0x007d, B:37:0x0083, B:40:0x008b, B:42:0x00a6, B:44:0x00d8, B:46:0x00dc, B:48:0x008f, B:50:0x0095, B:53:0x009c, B:56:0x0065, B:57:0x00e4, B:58:0x00ef, B:63:0x00f8), top: B:4:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d8 A[Catch: all -> 0x0033, TryCatch #0 {all -> 0x0033, blocks: (B:5:0x000b, B:9:0x0013, B:13:0x0025, B:15:0x002e, B:16:0x00e7, B:23:0x0038, B:25:0x0047, B:28:0x004d, B:31:0x0054, B:32:0x0073, B:35:0x007d, B:37:0x0083, B:40:0x008b, B:42:0x00a6, B:44:0x00d8, B:46:0x00dc, B:48:0x008f, B:50:0x0095, B:53:0x009c, B:56:0x0065, B:57:0x00e4, B:58:0x00ef, B:63:0x00f8), top: B:4:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x007c  */
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
        if (i != 1 || i2 != 2 || (!((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide && this.statusbarState != 2)) {
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
            if (this.animator.isRunning() || this.animationPending) {
                break;
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0023, code lost:
    
        if (r5 != false) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDesiredLocation(boolean r11, boolean r12) {
        /*
            r10 = this;
            boolean r1 = android.os.Trace.isEnabled()
            if (r1 == 0) goto Lb
            java.lang.String r0 = "MediaHierarchyManager#updateDesiredLocation"
            com.android.app.tracing.TraceUtilsKt.beginSlice(r0)
        Lb:
            int r3 = r10.calculateLocation()     // Catch: java.lang.Throwable -> L2c
            int r0 = r10.desiredLocation     // Catch: java.lang.Throwable -> L2c
            r2 = 1
            r4 = 0
            if (r3 != r0) goto L25
            if (r12 == 0) goto La0
            boolean r5 = r10.goingToSleep     // Catch: java.lang.Throwable -> L2c
            if (r5 != 0) goto L22
            boolean r5 = r10.dozeAnimationRunning     // Catch: java.lang.Throwable -> L2c
            if (r5 == 0) goto L20
            goto L22
        L20:
            r5 = r4
            goto L23
        L22:
            r5 = r2
        L23:
            if (r5 != 0) goto La0
        L25:
            if (r0 < 0) goto L30
            if (r3 == r0) goto L30
            r10.previousLocation = r0     // Catch: java.lang.Throwable -> L2c
            goto L4c
        L2c:
            r0 = move-exception
            r10 = r0
            goto La8
        L30:
            if (r12 == 0) goto L4c
            com.android.systemui.statusbar.phone.KeyguardBypassController r12 = r10.bypassController     // Catch: java.lang.Throwable -> L2c
            boolean r12 = r12.getBypassEnabled()     // Catch: java.lang.Throwable -> L2c
            if (r12 != 0) goto L40
            int r12 = r10.statusbarState     // Catch: java.lang.Throwable -> L2c
            if (r12 != r2) goto L40
            r12 = r2
            goto L41
        L40:
            r12 = r4
        L41:
            if (r3 != 0) goto L4c
            int r0 = r10.previousLocation     // Catch: java.lang.Throwable -> L2c
            r5 = 2
            if (r0 != r5) goto L4c
            if (r12 != 0) goto L4c
            r10.previousLocation = r2     // Catch: java.lang.Throwable -> L2c
        L4c:
            int r12 = r10.desiredLocation     // Catch: java.lang.Throwable -> L2c
            r0 = -1
            if (r12 != r0) goto L53
            r12 = r2
            goto L54
        L53:
            r12 = r4
        L54:
            r10.desiredLocation = r3     // Catch: java.lang.Throwable -> L2c
            if (r11 != 0) goto L62
            int r11 = r10.previousLocation     // Catch: java.lang.Throwable -> L2c
            boolean r11 = r10.shouldAnimateTransition(r3, r11)     // Catch: java.lang.Throwable -> L2c
            if (r11 == 0) goto L62
            r5 = r2
            goto L63
        L62:
            r5 = r4
        L63:
            int r11 = r10.previousLocation     // Catch: java.lang.Throwable -> L2c
            kotlin.Pair r11 = r10.getAnimationParams(r11, r3)     // Catch: java.lang.Throwable -> L2c
            java.lang.Object r0 = r11.component1()     // Catch: java.lang.Throwable -> L2c
            java.lang.Number r0 = (java.lang.Number) r0     // Catch: java.lang.Throwable -> L2c
            long r6 = r0.longValue()     // Catch: java.lang.Throwable -> L2c
            java.lang.Object r11 = r11.component2()     // Catch: java.lang.Throwable -> L2c
            java.lang.Number r11 = (java.lang.Number) r11     // Catch: java.lang.Throwable -> L2c
            long r8 = r11.longValue()     // Catch: java.lang.Throwable -> L2c
            com.android.systemui.media.controls.ui.view.MediaHost r4 = r10.getHost(r3)     // Catch: java.lang.Throwable -> L2c
            int r11 = r10.calculateTransformationType()     // Catch: java.lang.Throwable -> L2c
            if (r11 != r2) goto L8f
            boolean r11 = r10.isCurrentlyInGuidedTransformation()     // Catch: java.lang.Throwable -> L2c
            if (r11 != 0) goto L8f
            if (r5 != 0) goto L9d
        L8f:
            com.android.systemui.media.controls.ui.controller.MediaViewLogger r11 = r10.logger     // Catch: java.lang.Throwable -> L2c
            java.lang.String r0 = "no fade"
            int r2 = r10.currentAttachmentLocation     // Catch: java.lang.Throwable -> L2c
            r11.logMediaLocation(r2, r3, r0)     // Catch: java.lang.Throwable -> L2c
            com.android.systemui.media.controls.ui.controller.MediaCarouselController r2 = r10.mediaCarouselController     // Catch: java.lang.Throwable -> L2c
            r2.onDesiredLocationChanged(r3, r4, r5, r6, r8)     // Catch: java.lang.Throwable -> L2c
        L9d:
            r10.performTransitionToNewLocation(r12, r5)     // Catch: java.lang.Throwable -> L2c
        La0:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L2c
            if (r1 == 0) goto La7
            com.android.app.tracing.TraceUtilsKt.endSlice()
        La7:
            return
        La8:
            if (r1 == 0) goto Lad
            com.android.app.tracing.TraceUtilsKt.endSlice()
        Lad:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager.updateDesiredLocation(boolean, boolean):void");
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
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        boolean isDozing = sysuiStatusBarStateController.isDozing();
        KeyguardViewController keyguardViewController = this.keyguardViewController;
        boolean z = false;
        boolean z2 = (!isDozing && !keyguardViewController.isBouncerShowing() && sysuiStatusBarStateController.getState() == 1 && this.allowMediaPlayerOnLockScreen && sysuiStatusBarStateController.isExpanded() && !this.qsExpanded) || !(sysuiStatusBarStateController.isDozing() || keyguardViewController.isBouncerShowing() || (sysuiStatusBarStateController.getState() != 2 && (sysuiStatusBarStateController.getState() != 1 || !this.qsExpanded))) || ((!sysuiStatusBarStateController.isDozing() && sysuiStatusBarStateController.getState() == 0 && sysuiStatusBarStateController.isExpanded()) || !(!this.isCommunalShowing || this.isPrimaryBouncerShowing || this.isAnyShadeFullyExpanded));
        boolean z3 = this.qsExpanded || this.mediaManager.hasActiveMediaOrRecommendation();
        MediaViewLogger mediaViewLogger = this.logger;
        mediaViewLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaViewLogger$$ExternalSyntheticLambda0 mediaViewLogger$$ExternalSyntheticLambda0 = new MediaViewLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = mediaViewLogger.buffer;
        LogMessage obtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z3;
        logBuffer.commit(obtain);
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselController.mediaCarouselScrollHandler;
        if (z2 && z3) {
            z = true;
        }
        if (mediaCarouselScrollHandler.visibleToUser != z) {
            mediaCarouselScrollHandler.visibleToUser = z;
            mediaCarouselScrollHandler.seekBarUpdateListener.mo779invoke(Boolean.valueOf(z));
            mediaCarouselScrollHandler.visibleStateLogger.log(String.valueOf(mediaCarouselScrollHandler.visibleToUser));
        }
    }
}
