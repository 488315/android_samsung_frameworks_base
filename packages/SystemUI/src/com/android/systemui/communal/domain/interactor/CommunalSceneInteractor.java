package com.android.systemui.communal.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.communal.data.repository.CommunalSceneRepository;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.shared.log.CommunalSceneLogger;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSceneInteractor {
    public final StateFlowImpl _editModeState;
    public final StateFlowImpl _isLaunchingWidget;
    public final CoroutineScope applicationScope;
    public final ReadonlyStateFlow currentScene;
    public final ReadonlyStateFlow editModeState;
    public final ReadonlyStateFlow isCommunalVisible;
    public final ReadonlyStateFlow isIdleOnCommunal;
    public final ReadonlyStateFlow isLaunchingWidget;
    public final ReadonlyStateFlow isTransitioningToOrIdleOnCommunal;
    public final KeyguardStateController keyguardStateController;
    public final CommunalSceneLogger logger;
    public final CoroutineDispatcher mainImmediateDispatcher;
    public final Set onSceneAboutToChangeListener;
    public final CommunalSceneRepository repository;
    public final ReadonlyStateFlow rotatedToPortrait;
    public final ReadonlyStateFlow transitionState;
    public final Flow willRotateToPortrait;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnSceneAboutToChangeListener {
        void onSceneAboutToChange(SceneKey sceneKey, KeyguardState keyguardState);
    }

    static {
        new Companion(null);
    }

    public CommunalSceneInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CommunalSceneRepository communalSceneRepository, CommunalSceneLogger communalSceneLogger, SceneInteractor sceneInteractor, KeyguardStateController keyguardStateController) {
        this.applicationScope = coroutineScope;
        this.mainImmediateDispatcher = coroutineDispatcher;
        this.repository = communalSceneRepository;
        this.logger = communalSceneLogger;
        this.keyguardStateController = keyguardStateController;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isLaunchingWidget = MutableStateFlow;
        this.isLaunchingWidget = FlowKt.asStateFlow(MutableStateFlow);
        CommunalSceneRepositoryImpl communalSceneRepositoryImpl = (CommunalSceneRepositoryImpl) communalSceneRepository;
        final ReadonlyStateFlow readonlyStateFlow = communalSceneRepositoryImpl.communalContainerOrientation;
        this.willRotateToPortrait = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CommunalSceneInteractor this$0;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CommunalSceneInteractor communalSceneInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalSceneInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        r6 = 2
                        if (r5 != r6) goto L4b
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r5 = r4.this$0
                        com.android.systemui.statusbar.policy.KeyguardStateController r5 = r5.keyguardStateController
                        com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r5 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r5
                        android.content.Context r5 = r5.mContext
                        boolean r5 = com.android.systemui.util.DeviceState.shouldEnableKeyguardScreenRotation(r5)
                        if (r5 != 0) goto L4b
                        r5 = r3
                        goto L4c
                    L4b:
                        r5 = 0
                    L4c:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5b
                        return r1
                    L5b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        Flow pairwiseBy = com.android.systemui.util.kotlin.FlowKt.pairwiseBy(communalSceneRepositoryImpl.communalContainerOrientation, bool, new CommunalSceneInteractor$rotatedToPortrait$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.rotatedToPortrait = FlowKt.stateIn(pairwiseBy, coroutineScope, startedEagerly, bool);
        this.onSceneAboutToChangeListener = new LinkedHashSet();
        StateFlow stateFlow = communalSceneRepositoryImpl.currentScene;
        this.currentScene = FlowKt.stateIn(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(stateFlow, stateFlow.getValue(), new CommunalSceneInteractor$currentScene$1(this, null)), coroutineScope, startedEagerly, communalSceneRepositoryImpl.currentScene.getValue());
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._editModeState = MutableStateFlow2;
        final ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow2);
        this.editModeState = asStateFlow;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(communalSceneRepositoryImpl.transitionState, new CommunalSceneInteractor$transitionState$1(this, null)), coroutineScope, startedEagerly, communalSceneRepositoryImpl.transitionState.$$delegate_0.getValue());
        this.transitionState = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.ObservableTransitionState r5 = (com.android.compose.animation.scene.ObservableTransitionState) r5
                        boolean r6 = r5 instanceof com.android.compose.animation.scene.ObservableTransitionState.Idle
                        if (r6 == 0) goto L46
                        com.android.compose.animation.scene.ObservableTransitionState$Idle r5 = (com.android.compose.animation.scene.ObservableTransitionState.Idle) r5
                        com.android.compose.animation.scene.SceneKey r5 = r5.currentScene
                        com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.communal.shared.model.CommunalScenes.Communal
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        if (r5 == 0) goto L46
                        r5 = r3
                        goto L47
                    L46:
                        r5 = 0
                    L47:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, bool);
        this.isIdleOnCommunal = stateIn2;
        BooleanFlowOperators.INSTANCE.allOf(stateIn2, new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.communal.shared.model.EditModeState r5 = (com.android.systemui.communal.shared.model.EditModeState) r5
                        if (r5 != 0) goto L38
                        r5 = r3
                        goto L39
                    L38:
                        r5 = 0
                    L39:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isCommunalVisible = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.ObservableTransitionState r5 = (com.android.compose.animation.scene.ObservableTransitionState) r5
                        boolean r6 = r5 instanceof com.android.compose.animation.scene.ObservableTransitionState.Idle
                        if (r6 == 0) goto L47
                        com.android.compose.animation.scene.ObservableTransitionState$Idle r5 = (com.android.compose.animation.scene.ObservableTransitionState.Idle) r5
                        com.android.compose.animation.scene.SceneKey r5 = r5.currentScene
                        com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.communal.shared.model.CommunalScenes.Blank
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        if (r5 != 0) goto L45
                        goto L47
                    L45:
                        r5 = 0
                        goto L48
                    L47:
                        r5 = r3
                    L48:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isTransitioningToOrIdleOnCommunal = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L67
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.ObservableTransitionState r5 = (com.android.compose.animation.scene.ObservableTransitionState) r5
                        boolean r6 = r5 instanceof com.android.compose.animation.scene.ObservableTransitionState.Idle
                        if (r6 == 0) goto L45
                        r6 = r5
                        com.android.compose.animation.scene.ObservableTransitionState$Idle r6 = (com.android.compose.animation.scene.ObservableTransitionState.Idle) r6
                        com.android.compose.animation.scene.SceneKey r6 = r6.currentScene
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.communal.shared.model.CommunalScenes.Communal
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 != 0) goto L55
                    L45:
                        boolean r6 = r5 instanceof com.android.compose.animation.scene.ObservableTransitionState.Transition
                        if (r6 == 0) goto L57
                        com.android.compose.animation.scene.ObservableTransitionState$Transition r5 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r5
                        com.android.compose.animation.scene.ContentKey r5 = r5.toContent
                        com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.communal.shared.model.CommunalScenes.Communal
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        if (r5 == 0) goto L57
                    L55:
                        r5 = r3
                        goto L58
                    L57:
                        r5 = 0
                    L58:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L67
                        return r1
                    L67:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
    }

    public static /* synthetic */ void changeScene$default(CommunalSceneInteractor communalSceneInteractor, SceneKey sceneKey, String str, TransitionKey transitionKey, KeyguardState keyguardState, int i) {
        if ((i & 4) != 0) {
            transitionKey = null;
        }
        if ((i & 8) != 0) {
            keyguardState = null;
        }
        communalSceneInteractor.changeScene(sceneKey, str, transitionKey, keyguardState);
    }

    public static void snapToScene$default(CommunalSceneInteractor communalSceneInteractor, SceneKey sceneKey, String str, long j, int i) {
        if ((i & 4) != 0) {
            j = 0;
        }
        communalSceneInteractor.getClass();
        CoroutineTracingKt.launchTraced$default(communalSceneInteractor.applicationScope, null, null, new CommunalSceneInteractor$snapToScene$1(communalSceneInteractor, sceneKey, str, j, null, null), 6);
    }

    public final void changeScene(SceneKey sceneKey, String str, TransitionKey transitionKey, KeyguardState keyguardState) {
        CommunalSceneInteractor$changeScene$1 communalSceneInteractor$changeScene$1 = new CommunalSceneInteractor$changeScene$1(this, sceneKey, str, transitionKey, keyguardState, null);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainImmediateDispatcher, null, communalSceneInteractor$changeScene$1, 4);
    }
}
