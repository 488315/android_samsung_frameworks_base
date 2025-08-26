package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final class PanelExpansionInteractorImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PanelExpansionInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PanelExpansionInteractorImpl$special$$inlined$flatMapLatest$1(Continuation continuation, PanelExpansionInteractorImpl panelExpansionInteractorImpl) {
        super(3, continuation);
        this.this$0 = panelExpansionInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PanelExpansionInteractorImpl$special$$inlined$flatMapLatest$1 panelExpansionInteractorImpl$special$$inlined$flatMapLatest$1 = new PanelExpansionInteractorImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        panelExpansionInteractorImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        panelExpansionInteractorImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return panelExpansionInteractorImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$1;
            if (observableTransitionState instanceof ObservableTransitionState.Idle) {
                ObservableTransitionState.Idle idle = (ObservableTransitionState.Idle) observableTransitionState;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Float((Intrinsics.areEqual(idle.currentScene, Scenes.Gone) && idle.currentOverlays.isEmpty()) ? 0.0f : 1.0f));
            } else {
                if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
                    throw new NoWhenBranchMatchedException();
                }
                ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
                ContentKey contentKey = transition.fromContent;
                SceneKey sceneKey = Scenes.Gone;
                if (Intrinsics.areEqual(contentKey, sceneKey)) {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = PanelExpansionInteractorImpl.access$isExpandable(this.this$0, transition.toContent) ? transition.progress : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Float(1.0f));
                } else if (!Intrinsics.areEqual(transition.toContent, sceneKey)) {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Float(1.0f));
                } else if (PanelExpansionInteractorImpl.access$isExpandable(this.this$0, transition.fromContent)) {
                    final Flow flow = transition.progress;
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.shade.domain.interactor.PanelExpansionInteractorImpl$legacyPanelExpansion$lambda$1$$inlined$map$1

                        /* renamed from: com.android.systemui.shade.domain.interactor.PanelExpansionInteractorImpl$legacyPanelExpansion$lambda$1$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.shade.domain.interactor.PanelExpansionInteractorImpl$legacyPanelExpansion$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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
                                    Float f = new Float(1 - ((Number) obj).floatValue());
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                        public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                            Object objCollect = flow.collect(new AnonymousClass2(flowCollector2), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                } else {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Float(0.0f));
                }
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
