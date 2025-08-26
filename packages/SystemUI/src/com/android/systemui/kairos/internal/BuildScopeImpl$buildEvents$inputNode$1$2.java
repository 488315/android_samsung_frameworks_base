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
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class BuildScopeImpl$buildEvents$inputNode$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $builder;
    final /* synthetic */ CoroutineScope $childScope;
    final /* synthetic */ Ref$ObjectRef<Pair<Events, Object>> $emitter;
    final /* synthetic */ CoalescingMutableEvents $stopEmitter;
    final /* synthetic */ EvalScope $this_InputNode;
    int label;
    final /* synthetic */ BuildScopeImpl this$0;

    /* renamed from: com.android.systemui.kairos.internal.BuildScopeImpl$buildEvents$inputNode$1$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $builder;
        final /* synthetic */ CoroutineScope $childScope;
        final /* synthetic */ Ref$ObjectRef<Pair<Events, Object>> $emitter;
        final /* synthetic */ CoalescingMutableEvents $stopEmitter;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ BuildScopeImpl this$0;

        /* renamed from: com.android.systemui.kairos.internal.BuildScopeImpl$buildEvents$inputNode$1$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C02061 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function2 $builder;
            final /* synthetic */ Ref$ObjectRef<Pair<Events, Object>> $emitter;
            final /* synthetic */ CoalescingMutableEvents $stopEmitter;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02061(Function2 function2, Ref$ObjectRef<Pair<Events, Object>> ref$ObjectRef, CoalescingMutableEvents coalescingMutableEvents, Continuation continuation) {
                super(2, continuation);
                this.$builder = function2;
                this.$emitter = ref$ObjectRef;
                this.$stopEmitter = coalescingMutableEvents;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02061(this.$builder, this.$emitter, this.$stopEmitter, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02061) create((KairosCoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            return BuildScopeKt.launchEffect(new BuildScopeImpl(new StateScopeImpl(evalScope, buildScopeImpl.stateScope.endSignalLazy), this.$childScope), new C02061(this.$builder, this.$emitter, this.$stopEmitter, null));
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004c, code lost:
    
        if (((kotlinx.coroutines.Job) r12).join(r11) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CompletableDeferredImpl completableDeferredImplTransaction = this.$this_InputNode.getNetwork().transaction("buildEvents", new AnonymousClass1(this.this$0, this.$childScope, this.$builder, this.$emitter, this.$stopEmitter, null));
            this.label = 1;
            obj = completableDeferredImplTransaction.awaitInternal(this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        this.label = 2;
    }
}
