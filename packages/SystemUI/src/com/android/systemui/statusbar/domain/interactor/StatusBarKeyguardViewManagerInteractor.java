package com.android.systemui.statusbar.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class StatusBarKeyguardViewManagerInteractor {
    public final DistinctFlowImpl keyguardViewOcclusionState;
    public final StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2 occlusionStateFromFinishedStep;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 occlusionStateFromStartedStep;

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    public StatusBarKeyguardViewManagerInteractor(KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, PowerInteractor powerInteractor, WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor, KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor) {
        final Flow flowSample = FlowKt.sample(keyguardTransitionInteractor.startedKeyguardTransitionStep, powerInteractor.detailedWakefulness, StatusBarKeyguardViewManagerInteractor$occlusionStateFromStartedStep$3.INSTANCE);
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Pair pair = (Pair) obj;
                        TransitionStep transitionStep = (TransitionStep) pair.component1();
                        WakefulnessModel wakefulnessModel = (WakefulnessModel) pair.component2();
                        KeyguardState.Companion companion = KeyguardState.Companion;
                        KeyguardState keyguardState = transitionStep.from;
                        companion.getClass();
                        boolean zDeviceIsAsleepInState = KeyguardState.Companion.deviceIsAsleepInState(keyguardState);
                        KeyguardState keyguardState2 = transitionStep.to;
                        boolean z = zDeviceIsAsleepInState && keyguardState2 == KeyguardState.OCCLUDED && wakefulnessModel.powerButtonLaunchGestureTriggered;
                        KeyguardState keyguardState3 = KeyguardState.OCCLUDED;
                        OccludedState occludedState = (keyguardState2 != keyguardState3 || z) ? (transitionStep.from == keyguardState3 && keyguardState2 == KeyguardState.LOCKSCREEN) ? new OccludedState(false, false) : null : new OccludedState(true, false);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(occludedState, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowSample.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.occlusionStateFromStartedStep = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        SceneKey sceneKey = Scenes.Communal;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = kotlinx.coroutines.flow.FlowKt.combine(keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE), keyguardTransitionInteractor.isFinishedIn$1(KeyguardState.OCCLUDED), keyguardOcclusionInteractor.isShowWhenLockedActivityOnTop, StatusBarKeyguardViewManagerInteractor$occlusionStateFromFinishedStep$3.INSTANCE);
        ?? r4 = new Flow() { // from class: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Triple triple = (Triple) obj;
                        OccludedState occludedState = new OccludedState(((Boolean) triple.component2()).booleanValue() || (((Boolean) triple.component3()).booleanValue() && !((Boolean) triple.component1()).booleanValue()), false);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(occludedState, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.occlusionStateFromFinishedStep = r4;
        this.keyguardViewOcclusionState = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(kotlinx.coroutines.flow.FlowKt.merge(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, r4), new StatusBarKeyguardViewManagerInteractor$$ExternalSyntheticLambda0(), FlowKt__DistinctKt.defaultAreEquivalent);
        kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(windowManagerLockscreenVisibilityInteractor.lockscreenVisibility, keyguardSurfaceBehindInteractor.isAnimatingSurface, new StatusBarKeyguardViewManagerInteractor$keyguardViewVisibility$1(null)));
    }
}
