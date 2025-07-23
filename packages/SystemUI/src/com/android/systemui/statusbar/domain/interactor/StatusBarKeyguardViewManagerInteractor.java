package com.android.systemui.statusbar.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.FlowKt;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarKeyguardViewManagerInteractor {
    public final DistinctFlowImpl keyguardViewOcclusionState;
    public final StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2 occlusionStateFromFinishedStep;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 occlusionStateFromStartedStep;

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    public StatusBarKeyguardViewManagerInteractor(KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, PowerInteractor powerInteractor, WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor, KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor) {
        final Flow sample = FlowKt.sample(keyguardTransitionInteractor.startedKeyguardTransitionStep, powerInteractor.detailedWakefulness, StatusBarKeyguardViewManagerInteractor$occlusionStateFromStartedStep$3.INSTANCE);
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L81
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlin.Pair r7 = (kotlin.Pair) r7
                        java.lang.Object r8 = r7.component1()
                        com.android.systemui.keyguard.shared.model.TransitionStep r8 = (com.android.systemui.keyguard.shared.model.TransitionStep) r8
                        java.lang.Object r7 = r7.component2()
                        com.android.systemui.power.shared.model.WakefulnessModel r7 = (com.android.systemui.power.shared.model.WakefulnessModel) r7
                        com.android.systemui.keyguard.shared.model.KeyguardState$Companion r2 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion
                        com.android.systemui.keyguard.shared.model.KeyguardState r4 = r8.from
                        r2.getClass()
                        boolean r2 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion.deviceIsAsleepInState(r4)
                        r4 = 0
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = r8.to
                        if (r2 == 0) goto L5a
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
                        if (r5 != r2) goto L5a
                        boolean r7 = r7.powerButtonLaunchGestureTriggered
                        if (r7 == 0) goto L5a
                        r7 = r3
                        goto L5b
                    L5a:
                        r7 = r4
                    L5b:
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
                        if (r5 != r2) goto L67
                        if (r7 != 0) goto L67
                        com.android.systemui.statusbar.domain.interactor.OccludedState r7 = new com.android.systemui.statusbar.domain.interactor.OccludedState
                        r7.<init>(r3, r4)
                        goto L76
                    L67:
                        com.android.systemui.keyguard.shared.model.KeyguardState r7 = r8.from
                        if (r7 != r2) goto L75
                        com.android.systemui.keyguard.shared.model.KeyguardState r7 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
                        if (r5 != r7) goto L75
                        com.android.systemui.statusbar.domain.interactor.OccludedState r7 = new com.android.systemui.statusbar.domain.interactor.OccludedState
                        r7.<init>(r4, r4)
                        goto L76
                    L75:
                        r7 = 0
                    L76:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L81
                        return r1
                    L81:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.occlusionStateFromStartedStep = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        SceneKey sceneKey = Scenes.Communal;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = kotlinx.coroutines.flow.FlowKt.combine(keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE), keyguardTransitionInteractor.isFinishedIn$1(KeyguardState.OCCLUDED), keyguardOcclusionInteractor.isShowWhenLockedActivityOnTop, StatusBarKeyguardViewManagerInteractor$occlusionStateFromFinishedStep$3.INSTANCE);
        ?? r4 = new Flow() { // from class: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L6d
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Triple r6 = (kotlin.Triple) r6
                        java.lang.Object r7 = r6.component1()
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        java.lang.Object r2 = r6.component2()
                        java.lang.Boolean r2 = (java.lang.Boolean) r2
                        boolean r2 = r2.booleanValue()
                        java.lang.Object r6 = r6.component3()
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        r4 = 0
                        if (r2 != 0) goto L5c
                        if (r6 == 0) goto L5a
                        if (r7 != 0) goto L5a
                        goto L5c
                    L5a:
                        r6 = r4
                        goto L5d
                    L5c:
                        r6 = r3
                    L5d:
                        com.android.systemui.statusbar.domain.interactor.OccludedState r7 = new com.android.systemui.statusbar.domain.interactor.OccludedState
                        r7.<init>(r6, r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L6d
                        return r1
                    L6d:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.occlusionStateFromFinishedStep = r4;
        this.keyguardViewOcclusionState = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(kotlinx.coroutines.flow.FlowKt.merge(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, r4), new StatusBarKeyguardViewManagerInteractor$$ExternalSyntheticLambda0(), FlowKt__DistinctKt.defaultAreEquivalent);
        kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(windowManagerLockscreenVisibilityInteractor.lockscreenVisibility, keyguardSurfaceBehindInteractor.isAnimatingSurface, new StatusBarKeyguardViewManagerInteractor$keyguardViewVisibility$1(null)));
    }
}
