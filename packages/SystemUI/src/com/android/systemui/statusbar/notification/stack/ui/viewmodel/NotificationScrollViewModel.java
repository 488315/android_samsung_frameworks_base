package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import android.util.IndentingPrintWriter;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.BaseShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.util.kotlin.ActivatableFlowDumper;
import com.android.systemui.util.kotlin.ActivatableFlowDumperImpl;
import java.io.PrintWriter;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class NotificationScrollViewModel extends ExclusiveActivatable implements ActivatableFlowDumper {
    public final /* synthetic */ ActivatableFlowDumperImpl $$delegate_0;
    public final Function1 accessibilityScrollEventConsumer;
    public final ReadonlyStateFlow alphaForLockscreenFadeIn;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 blurFraction;
    public final Function1 currentGestureInGutsConsumer;
    public final Function1 currentGestureOverscrollConsumer;
    public final Flow expandFraction;
    public final Lazy isDozing$delegate;
    public final Flow isOccluded;
    public final Lazy isPulsing$delegate;
    public final Flow isScrollable;
    public final Flow isShowingStackOnLockscreen;
    public final StateFlow maxAlpha;
    public final Flow qsExpandFraction;
    public final Function1 remoteInputRowBottomBoundConsumer;
    public final Flow shadeScrimClipping;
    public final ReadonlyStateFlow shadeScrollState;
    public final Lazy shouldAnimatePulse$delegate;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shouldCloseGuts;
    public final NotificationStackAppearanceInteractor stackAppearanceInteractor;
    public final NotificationScrollViewModel$special$$inlined$map$1 suppressHeightUpdates;
    public final Function1 syntheticScrollConsumer;

    public interface Factory {
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return NotificationScrollViewModel.this.onActivated(this);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1] */
    public NotificationScrollViewModel(DumpManager dumpManager, NotificationStackAppearanceInteractor notificationStackAppearanceInteractor, ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor, BouncerInteractor bouncerInteractor, RemoteInputInteractor remoteInputInteractor, SceneInteractor sceneInteractor, dagger.Lazy lazy) {
        ActivatableFlowDumperImpl activatableFlowDumperImpl = new ActivatableFlowDumperImpl(dumpManager, "NotificationScrollViewModel");
        this.$$delegate_0 = activatableFlowDumperImpl;
        this.stackAppearanceInteractor = notificationStackAppearanceInteractor;
        final ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
        this.suppressHeightUpdates = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:23:0x005a  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ObservableTransitionState observableTransitionState = (ObservableTransitionState) obj;
                        if (observableTransitionState instanceof ObservableTransitionState.Transition) {
                            ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
                            boolean z = Intrinsics.areEqual(transition.fromContent, Scenes.Lockscreen) && (Intrinsics.areEqual(transition.toContent, Overlays.Bouncer) || Intrinsics.areEqual(transition.toContent, Scenes.Gone));
                            Boolean boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        StateFlow shadeExpansion = shadeInteractorImpl.baseShadeInteractor.getShadeExpansion();
        BaseShadeInteractor baseShadeInteractor = shadeInteractorImpl.baseShadeInteractor;
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        this.expandFraction = activatableFlowDumperImpl.dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(shadeExpansion, baseShadeInteractor.getQsExpansion(), shadeModeInteractorImpl.shadeMode, sceneInteractor.transitionState, sceneInteractor.currentOverlays, new NotificationScrollViewModel$expandFraction$1(this, null))), "expandFraction");
        this.qsExpandFraction = activatableFlowDumperImpl.dumpWhileCollecting(baseShadeInteractor.getQsExpansion(), "qsExpandFraction");
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = bouncerInteractor.bouncerExpansion;
        this.isOccluded = activatableFlowDumperImpl.dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() == 1.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "isOccluded");
        this.blurFraction = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Float.valueOf(0.0f));
        this.shouldCloseGuts = notificationStackAppearanceInteractor.shouldCloseGuts;
        final ReadonlyStateFlow readonlyStateFlow2 = sceneInteractor.transitionState;
        this.isShowingStackOnLockscreen = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ObservableTransitionState observableTransitionState = (ObservableTransitionState) obj;
                        SceneKey sceneKey = Scenes.Lockscreen;
                        Boolean boolValueOf = Boolean.valueOf(ObservableTransitionState.isIdle$default(observableTransitionState, sceneKey, null, 2) || observableTransitionState.isTransitioning(sceneKey, Scenes.Shade));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.alphaForLockscreenFadeIn = notificationStackAppearanceInteractor.alphaForLockscreenFadeIn;
        this.shadeScrimClipping = activatableFlowDumperImpl.dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeModeInteractorImpl.shadeMode, baseShadeInteractor.getQsExpansion(), new NotificationScrollViewModel$qsAllowsClipping$1(null))), notificationStackAppearanceInteractor.notificationShadeScrimBounds, notificationStackAppearanceInteractor.shadeScrimRounding, new NotificationScrollViewModel$shadeScrimClipping$1(null))), "stackClipping");
        this.maxAlpha = activatableFlowDumperImpl.dumpValue(notificationStackAppearanceInteractor.alphaForBrightnessMirror, "maxAlpha");
        this.shadeScrollState = notificationStackAppearanceInteractor.shadeScrollState;
        this.syntheticScrollConsumer = new NotificationScrollViewModel$syntheticScrollConsumer$1(notificationStackAppearanceInteractor);
        this.accessibilityScrollEventConsumer = new NotificationScrollViewModel$accessibilityScrollEventConsumer$1(notificationStackAppearanceInteractor);
        this.currentGestureOverscrollConsumer = new NotificationScrollViewModel$currentGestureOverscrollConsumer$1(notificationStackAppearanceInteractor);
        this.currentGestureInGutsConsumer = new NotificationScrollViewModel$currentGestureInGutsConsumer$1(notificationStackAppearanceInteractor);
        this.remoteInputRowBottomBoundConsumer = new NotificationScrollViewModel$remoteInputRowBottomBoundConsumer$1(remoteInputInteractor);
        this.isScrollable = activatableFlowDumperImpl.dumpWhileCollecting(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(sceneInteractor.currentScene, sceneInteractor.currentOverlays, new NotificationScrollViewModel$isScrollable$1(this, null)), "isScrollable");
        final int i = 0;
        this.isDozing$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i2 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                    default:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return StateFlowKt.MutableStateFlow(Boolean.FALSE);
                }
            }
        });
        final int i2 = 1;
        this.isPulsing$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                    default:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return StateFlowKt.MutableStateFlow(Boolean.FALSE);
                }
            }
        });
        final int i3 = 2;
        this.shouldAnimatePulse$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                    default:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        return StateFlowKt.MutableStateFlow(Boolean.FALSE);
                }
            }
        });
    }

    public static final float access$expandFractionDuringOverlayTransition(NotificationScrollViewModel notificationScrollViewModel, ObservableTransitionState.Transition transition, SceneKey sceneKey, Set set, float f) {
        notificationScrollViewModel.getClass();
        if (Intrinsics.areEqual(sceneKey, Scenes.Lockscreen)) {
            return 1.0f;
        }
        OverlayKey overlayKey = Overlays.NotificationsShade;
        transition.getClass();
        return (ObservableTransitionState.isTransitioning$default(transition, overlayKey, null, 2) || ObservableTransitionState.isTransitioning$default(transition, null, overlayKey, 1)) ? f : set.contains(overlayKey) ? 1.0f : 0.0f;
    }

    public static final boolean access$showsNotifications(NotificationScrollViewModel notificationScrollViewModel, ContentKey contentKey) {
        notificationScrollViewModel.getClass();
        return Intrinsics.areEqual(contentKey, Overlays.NotificationsShade) || Intrinsics.areEqual(contentKey, Scenes.Lockscreen) || Intrinsics.areEqual(contentKey, Scenes.Shade);
    }

    public static boolean expandedInScene(SceneKey sceneKey) {
        return Intrinsics.areEqual(sceneKey, Scenes.Lockscreen) || Intrinsics.areEqual(sceneKey, Scenes.Shade) || Intrinsics.areEqual(sceneKey, Scenes.QuickSettings);
    }

    @Override // com.android.systemui.util.kotlin.ActivatableFlowDumper
    public final Object activateFlowDumper(Continuation continuation) {
        return this.$$delegate_0.activateFlowDumper(continuation);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.$$delegate_0.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        this.$$delegate_0.dumpFlows(indentingPrintWriter);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final SharedFlow dumpReplayCache(SharedFlow sharedFlow, String str) {
        return this.$$delegate_0.dumpReplayCache(sharedFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final StateFlow dumpValue(StateFlow stateFlow, String str) {
        return this.$$delegate_0.dumpValue(stateFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final Flow dumpWhileCollecting(Flow flow, String str) {
        return this.$$delegate_0.dumpWhileCollecting(flow, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.label = 1;
            if (this.$$delegate_0.activateFlowDumper(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
