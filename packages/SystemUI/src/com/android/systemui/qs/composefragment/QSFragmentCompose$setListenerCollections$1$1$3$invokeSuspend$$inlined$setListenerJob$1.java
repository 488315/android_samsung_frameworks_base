package com.android.systemui.qs.composefragment;

import com.android.systemui.plugins.qs.QSContainerController;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;

/* loaded from: classes2.dex */
public final class QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $dataFlow;
    final /* synthetic */ MutableStateFlow $listenerFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ CoroutineScope $$this$coroutineScope;
        final /* synthetic */ Flow $dataFlow;
        /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C03841 extends SuspendLambda implements Function2 {
            final /* synthetic */ Object $currentListener;
            final /* synthetic */ Flow $dataFlow;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03841(Flow flow, Object obj, Continuation continuation) {
                super(2, continuation);
                this.$dataFlow = flow;
                this.$currentListener = obj;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03841(this.$dataFlow, this.$currentListener, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03841) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$dataFlow;
                    final Object obj2 = this.$currentListener;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$.inlined.setListenerJob.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj3, Continuation continuation) {
                            ((QSContainerController) obj2).setCustomizerShowing(((Boolean) obj3).booleanValue(), 500L);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CoroutineScope coroutineScope, Flow flow, Continuation continuation) {
            super(2, continuation);
            this.$dataFlow = flow;
            this.$$this$coroutineScope = coroutineScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$coroutineScope, this.$dataFlow, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Object obj2 = this.L$0;
            if (obj2 != null) {
                BuildersKt.launch$default(this.$$this$coroutineScope, null, null, new C03841(this.$dataFlow, obj2, null), 3);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1(MutableStateFlow mutableStateFlow, Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$listenerFlow = mutableStateFlow;
        this.$dataFlow = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1 qSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1 = new QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1(this.$listenerFlow, this.$dataFlow, continuation);
        qSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1.L$0 = obj;
        return qSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentCompose$setListenerCollections$1$1$3$invokeSuspend$$inlined$setListenerJob$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r7) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                MutableStateFlow mutableStateFlow = this.$listenerFlow;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(coroutineScope, this.$dataFlow, null);
                this.label = 1;
                if (FlowKt.collectLatest(mutableStateFlow, anonymousClass1, this) == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            ResultKt.throwOnFailure(obj);
            this.label = 2;
        } catch (Throwable th) {
            this.$listenerFlow.setValue(null);
            throw th;
        }
    }
}
