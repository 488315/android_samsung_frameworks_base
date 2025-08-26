package com.android.systemui.communal.log;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class CommunalLoggerStartable implements CoreStartable {
    public final CoroutineScope backgroundScope;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final KeyguardInteractor keyguardInteractor;
    public final UiEventLogger uiEventLogger;

    /* renamed from: com.android.systemui.communal.log.CommunalLoggerStartable$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = CommunalLoggerStartable.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CommunalUiEvent) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalLoggerStartable.this.uiEventLogger.log((CommunalUiEvent) this.L$0);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.log.CommunalLoggerStartable$start$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
            anonymousClass3.L$0 = (WithPrev) obj;
            anonymousClass3.Z$0 = zBooleanValue;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            WithPrev withPrev = (WithPrev) this.L$0;
            boolean z = this.Z$0;
            ObservableTransitionState observableTransitionState = (ObservableTransitionState) withPrev.component1();
            ObservableTransitionState observableTransitionState2 = (ObservableTransitionState) withPrev.component2();
            if (CommunalLoggerStartableKt.access$isOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingToCommunal(observableTransitionState)) {
                return z ? CommunalUiEvent.DREAM_TO_COMMUNAL_HUB_SWIPE_FINISH : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_ENTER_FINISH;
            }
            if (CommunalLoggerStartableKt.access$isOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingFromCommunal(observableTransitionState)) {
                return z ? CommunalUiEvent.COMMUNAL_HUB_TO_DREAM_SWIPE_CANCEL : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_EXIT_CANCEL;
            }
            if (CommunalLoggerStartableKt.access$isNotOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingFromCommunal(observableTransitionState)) {
                return z ? CommunalUiEvent.COMMUNAL_HUB_TO_DREAM_SWIPE_FINISH : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_EXIT_FINISH;
            }
            if (CommunalLoggerStartableKt.access$isNotOnCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isSwipingToCommunal(observableTransitionState)) {
                return z ? CommunalUiEvent.DREAM_TO_COMMUNAL_HUB_SWIPE_CANCEL : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_ENTER_CANCEL;
            }
            if (CommunalLoggerStartableKt.access$isSwipingToCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isNotOnCommunal(observableTransitionState)) {
                return z ? CommunalUiEvent.DREAM_TO_COMMUNAL_HUB_SWIPE_START : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_ENTER_START;
            }
            if (CommunalLoggerStartableKt.access$isSwipingFromCommunal(observableTransitionState2) && CommunalLoggerStartableKt.access$isOnCommunal(observableTransitionState)) {
                return z ? CommunalUiEvent.COMMUNAL_HUB_TO_DREAM_SWIPE_START : CommunalUiEvent.COMMUNAL_HUB_SWIPE_TO_EXIT_START;
            }
            return null;
        }
    }

    /* renamed from: com.android.systemui.communal.log.CommunalLoggerStartable$start$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = CommunalLoggerStartable.this.new AnonymousClass4(continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CommunalUiEvent) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalLoggerStartable.this.uiEventLogger.log((CommunalUiEvent) this.L$0);
            return Unit.INSTANCE;
        }
    }

    public CommunalLoggerStartable(CoroutineScope coroutineScope, CommunalSceneInteractor communalSceneInteractor, KeyguardInteractor keyguardInteractor, UiEventLogger uiEventLogger) {
        this.backgroundScope = coroutineScope;
        this.communalSceneInteractor = communalSceneInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.uiEventLogger = uiEventLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CommunalSceneInteractor communalSceneInteractor = this.communalSceneInteractor;
        final ReadonlyStateFlow readonlyStateFlow = communalSceneInteractor.transitionState;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.drop(FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.communal.log.CommunalLoggerStartable$start$$inlined$map$1

            /* renamed from: com.android.systemui.communal.log.CommunalLoggerStartable$start$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.log.CommunalLoggerStartable$start$$inlined$map$1$2$1, reason: invalid class name */
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
                        CommunalUiEvent communalUiEvent = CommunalLoggerStartableKt.access$isOnCommunal(observableTransitionState) ? CommunalUiEvent.COMMUNAL_HUB_SHOWN : CommunalLoggerStartableKt.access$isNotOnCommunal(observableTransitionState) ? CommunalUiEvent.COMMUNAL_HUB_GONE : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(communalUiEvent, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }))), new AnonymousClass2(null));
        CoroutineScope coroutineScope = this.backgroundScope;
        FlowKt.launchIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(com.android.systemui.util.kotlin.FlowKt.pairwise(communalSceneInteractor.transitionState), this.keyguardInteractor.isDreamingWithOverlay, new AnonymousClass3(null)))), new AnonymousClass4(null)), coroutineScope);
    }
}
