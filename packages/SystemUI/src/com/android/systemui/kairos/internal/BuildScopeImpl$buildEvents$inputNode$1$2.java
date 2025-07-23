package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.CoalescingMutableEvents;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.KairosCoroutineScope;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScopeImpl$buildEvents$inputNode$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $builder;
    final /* synthetic */ CoroutineScope $childScope;
    final /* synthetic */ Ref$ObjectRef<Pair<Events, Object>> $emitter;
    final /* synthetic */ CoalescingMutableEvents $stopEmitter;
    final /* synthetic */ EvalScope $this_InputNode;
    int label;
    final /* synthetic */ BuildScopeImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.kairos.internal.BuildScopeImpl$buildEvents$inputNode$1$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $builder;
        final /* synthetic */ CoroutineScope $childScope;
        final /* synthetic */ Ref$ObjectRef<Pair<Events, Object>> $emitter;
        final /* synthetic */ CoalescingMutableEvents $stopEmitter;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ BuildScopeImpl this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.kairos.internal.BuildScopeImpl$buildEvents$inputNode$1$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C01091 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function2 $builder;
            final /* synthetic */ Ref$ObjectRef<Pair<Events, Object>> $emitter;
            final /* synthetic */ CoalescingMutableEvents $stopEmitter;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01091(Function2 function2, Ref$ObjectRef<Pair<Events, Object>> ref$ObjectRef, CoalescingMutableEvents coalescingMutableEvents, Continuation continuation) {
                super(2, continuation);
                this.$builder = function2;
                this.$emitter = ref$ObjectRef;
                this.$stopEmitter = coalescingMutableEvents;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01091(this.$builder, this.$emitter, this.$stopEmitter, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01091) create((KairosCoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Function2 function2 = this.$builder;
                    Pair<Events, Object> pair = this.$emitter.element;
                    Object second = (pair == null ? null : pair).getSecond();
                    this.label = 1;
                    if (function2.invoke(second, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                CoalescingMutableEvents coalescingMutableEvents = this.$stopEmitter;
                Unit unit = Unit.INSTANCE;
                coalescingMutableEvents.emit(unit);
                return unit;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BuildScopeImpl buildScopeImpl, CoroutineScope coroutineScope, Function2 function2, Ref$ObjectRef<Pair<Events, Object>> ref$ObjectRef, CoalescingMutableEvents coalescingMutableEvents, Continuation continuation) {
            super(2, continuation);
            this.this$0 = buildScopeImpl;
            this.$childScope = coroutineScope;
            this.$builder = function2;
            this.$emitter = ref$ObjectRef;
            this.$stopEmitter = coalescingMutableEvents;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$childScope, this.$builder, this.$emitter, this.$stopEmitter, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            EvalScope evalScope = (EvalScope) this.L$0;
            BuildScopeImpl buildScopeImpl = this.this$0;
            return BuildScopeKt.launchEffect(new BuildScopeImpl(new StateScopeImpl(evalScope, buildScopeImpl.stateScope.endSignalLazy), this.$childScope), new C01091(this.$builder, this.$emitter, this.$stopEmitter, null));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScopeImpl$buildEvents$inputNode$1$2(EvalScope evalScope, BuildScopeImpl buildScopeImpl, CoroutineScope coroutineScope, Function2 function2, Ref$ObjectRef<Pair<Events, Object>> ref$ObjectRef, CoalescingMutableEvents coalescingMutableEvents, Continuation continuation) {
        super(2, continuation);
        this.$this_InputNode = evalScope;
        this.this$0 = buildScopeImpl;
        this.$childScope = coroutineScope;
        this.$builder = function2;
        this.$emitter = ref$ObjectRef;
        this.$stopEmitter = coalescingMutableEvents;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BuildScopeImpl$buildEvents$inputNode$1$2(this.$this_InputNode, this.this$0, this.$childScope, this.$builder, this.$emitter, this.$stopEmitter, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildScopeImpl$buildEvents$inputNode$1$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004c, code lost:
    
        if (((kotlinx.coroutines.Job) r12).join(r11) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x004e, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
    
        if (r12 == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r12)
            goto L4f
        L10:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L18:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L44
        L1c:
            kotlin.ResultKt.throwOnFailure(r12)
            com.android.systemui.kairos.internal.EvalScope r12 = r11.$this_InputNode
            com.android.systemui.kairos.internal.Network r12 = r12.getNetwork()
            com.android.systemui.kairos.internal.BuildScopeImpl$buildEvents$inputNode$1$2$1 r4 = new com.android.systemui.kairos.internal.BuildScopeImpl$buildEvents$inputNode$1$2$1
            com.android.systemui.kairos.internal.BuildScopeImpl r5 = r11.this$0
            kotlinx.coroutines.CoroutineScope r6 = r11.$childScope
            kotlin.jvm.functions.Function2 r7 = r11.$builder
            kotlin.jvm.internal.Ref$ObjectRef<kotlin.Pair<com.android.systemui.kairos.Events, java.lang.Object>> r8 = r11.$emitter
            com.android.systemui.kairos.CoalescingMutableEvents r9 = r11.$stopEmitter
            r10 = 0
            r4.<init>(r5, r6, r7, r8, r9, r10)
            java.lang.String r1 = "buildEvents"
            kotlinx.coroutines.CompletableDeferredImpl r12 = r12.transaction(r1, r4)
            r11.label = r3
            java.lang.Object r12 = r12.awaitInternal(r11)
            if (r12 != r0) goto L44
            goto L4e
        L44:
            kotlinx.coroutines.Job r12 = (kotlinx.coroutines.Job) r12
            r11.label = r2
            java.lang.Object r11 = r12.join(r11)
            if (r11 != r0) goto L4f
        L4e:
            return r0
        L4f:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.BuildScopeImpl$buildEvents$inputNode$1$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
