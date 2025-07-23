package com.android.systemui.qs.composefragment;

import com.android.systemui.plugins.qs.QS;
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
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $dataFlow;
    final /* synthetic */ MutableStateFlow $listenerFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ CoroutineScope $$this$coroutineScope;
        final /* synthetic */ Flow $dataFlow;
        /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C02391 extends SuspendLambda implements Function2 {
            final /* synthetic */ Object $currentListener;
            final /* synthetic */ Flow $dataFlow;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02391(Flow flow, Object obj, Continuation continuation) {
                super(2, continuation);
                this.$dataFlow = flow;
                this.$currentListener = obj;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02391(this.$dataFlow, this.$currentListener, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02391) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$dataFlow;
                    final Object obj2 = this.$currentListener;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$.inlined.setListenerJob.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj3, Continuation continuation) {
                            ((Boolean) obj3).getClass();
                            ((QS.HeightListener) obj2).onQsHeightChanged();
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
                BuildersKt.launch$default(this.$$this$coroutineScope, null, null, new C02391(this.$dataFlow, obj2, null), 3);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1(MutableStateFlow mutableStateFlow, Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$listenerFlow = mutableStateFlow;
        this.$dataFlow = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1 qSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1 = new QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1(this.$listenerFlow, this.$dataFlow, continuation);
        qSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1.L$0 = obj;
        return qSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003e, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r7) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1f
            if (r1 == r4) goto L1b
            if (r1 == r3) goto L15
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L15:
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L19
            goto L41
        L19:
            r8 = move-exception
            goto L47
        L1b:
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L19
            goto L38
        L1f:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.CoroutineScope r8 = (kotlinx.coroutines.CoroutineScope) r8
            kotlinx.coroutines.flow.MutableStateFlow r1 = r7.$listenerFlow     // Catch: java.lang.Throwable -> L19
            com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1$1 r5 = new com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1$1     // Catch: java.lang.Throwable -> L19
            kotlinx.coroutines.flow.Flow r6 = r7.$dataFlow     // Catch: java.lang.Throwable -> L19
            r5.<init>(r8, r6, r2)     // Catch: java.lang.Throwable -> L19
            r7.label = r4     // Catch: java.lang.Throwable -> L19
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.collectLatest(r1, r5, r7)     // Catch: java.lang.Throwable -> L19
            if (r8 != r0) goto L38
            goto L40
        L38:
            r7.label = r3     // Catch: java.lang.Throwable -> L19
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = kotlinx.coroutines.DelayKt.awaitCancellation(r7)     // Catch: java.lang.Throwable -> L19
            if (r8 != r0) goto L41
        L40:
            return r0
        L41:
            kotlin.KotlinNothingValueException r8 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L19
            r8.<init>()     // Catch: java.lang.Throwable -> L19
            throw r8     // Catch: java.lang.Throwable -> L19
        L47:
            kotlinx.coroutines.flow.MutableStateFlow r7 = r7.$listenerFlow
            r7.setValue(r2)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentCompose$setListenerCollections$1$1$2$invokeSuspend$$inlined$setListenerJob$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
