package com.android.systemui.communal.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.communal.data.repository.CommunalSceneRepository;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.shared.log.CommunalSceneLogger;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.EditModeState;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface OnSceneAboutToChangeListener {
        void onSceneAboutToChange(SceneKey sceneKey, KeyguardState keyguardState);
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$changeScene$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardState $keyguardState;
        final /* synthetic */ String $loggingReason;
        final /* synthetic */ SceneKey $newScene;
        final /* synthetic */ TransitionKey $transitionKey;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SceneKey sceneKey, String str, TransitionKey transitionKey, KeyguardState keyguardState, Continuation continuation) {
            super(2, continuation);
            this.$newScene = sceneKey;
            this.$loggingReason = str;
            this.$transitionKey = transitionKey;
            this.$keyguardState = keyguardState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalSceneInteractor.this.new AnonymousClass1(this.$newScene, this.$loggingReason, this.$transitionKey, this.$keyguardState, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (Intrinsics.areEqual(CommunalSceneInteractor.this.currentScene.$$delegate_0.getValue(), this.$newScene)) {
                return Unit.INSTANCE;
            }
            CommunalSceneInteractor communalSceneInteractor = CommunalSceneInteractor.this;
            communalSceneInteractor.logger.logSceneChangeRequested((SceneKey) communalSceneInteractor.currentScene.$$delegate_0.getValue(), this.$newScene, this.$loggingReason, false);
            CommunalSceneInteractor communalSceneInteractor2 = CommunalSceneInteractor.this;
            SceneKey sceneKey = this.$newScene;
            KeyguardState keyguardState = this.$keyguardState;
            Iterator it = communalSceneInteractor2.onSceneAboutToChangeListener.iterator();
            while (it.hasNext()) {
                ((OnSceneAboutToChangeListener) it.next()).onSceneAboutToChange(sceneKey, keyguardState);
            }
            CommunalSceneRepository communalSceneRepository = CommunalSceneInteractor.this.repository;
            ((CommunalSceneRepositoryImpl) communalSceneRepository).sceneDataSource.changeScene(this.$newScene, this.$transitionKey);
            return Unit.INSTANCE;
        }
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isLaunchingWidget = stateFlowImplMutableStateFlow;
        this.isLaunchingWidget = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        CommunalSceneRepositoryImpl communalSceneRepositoryImpl = (CommunalSceneRepositoryImpl) communalSceneRepository;
        final ReadonlyStateFlow readonlyStateFlow = communalSceneRepositoryImpl.communalContainerOrientation;
        this.willRotateToPortrait = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$1

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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).intValue() == 2 && !DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) this.this$0.keyguardStateController).mContext));
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        Flow flowPairwiseBy = com.android.systemui.util.kotlin.FlowKt.pairwiseBy(communalSceneRepositoryImpl.communalContainerOrientation, bool, new CommunalSceneInteractor$rotatedToPortrait$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.rotatedToPortrait = FlowKt.stateIn(flowPairwiseBy, coroutineScope, startedEagerly, bool);
        this.onSceneAboutToChangeListener = new LinkedHashSet();
        StateFlow stateFlow = communalSceneRepositoryImpl.currentScene;
        this.currentScene = FlowKt.stateIn(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(stateFlow, stateFlow.getValue(), new CommunalSceneInteractor$currentScene$1(this, null)), coroutineScope, startedEagerly, communalSceneRepositoryImpl.currentScene.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._editModeState = stateFlowImplMutableStateFlow2;
        final ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.editModeState = readonlyStateFlowAsStateFlow;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(communalSceneRepositoryImpl.transitionState, new CommunalSceneInteractor$transitionState$1(this, null)), coroutineScope, startedEagerly, communalSceneRepositoryImpl.transitionState.$$delegate_0.getValue());
        this.transitionState = readonlyStateFlowStateIn;
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$2

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
                        Boolean boolValueOf = Boolean.valueOf((observableTransitionState instanceof ObservableTransitionState.Idle) && Intrinsics.areEqual(((ObservableTransitionState.Idle) observableTransitionState).currentScene, CommunalScenes.Communal));
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, bool);
        this.isIdleOnCommunal = readonlyStateFlowStateIn2;
        BooleanFlowOperators.INSTANCE.allOf(readonlyStateFlowStateIn2, new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$3

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
                        Boolean boolValueOf = Boolean.valueOf(((EditModeState) obj) == null);
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
                Object objCollect = readonlyStateFlowAsStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isCommunalVisible = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$4

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
                        Boolean boolValueOf = Boolean.valueOf(((observableTransitionState instanceof ObservableTransitionState.Idle) && Intrinsics.areEqual(((ObservableTransitionState.Idle) observableTransitionState).currentScene, CommunalScenes.Blank)) ? false : true);
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isTransitioningToOrIdleOnCommunal = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$special$$inlined$map$5

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
                        Boolean boolValueOf = Boolean.valueOf(((observableTransitionState instanceof ObservableTransitionState.Idle) && Intrinsics.areEqual(((ObservableTransitionState.Idle) observableTransitionState).currentScene, CommunalScenes.Communal)) || ((observableTransitionState instanceof ObservableTransitionState.Transition) && Intrinsics.areEqual(((ObservableTransitionState.Transition) observableTransitionState).toContent, CommunalScenes.Communal)));
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(sceneKey, str, transitionKey, keyguardState, null);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainImmediateDispatcher, null, anonymousClass1, 4);
    }
}
