package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.Network;
import com.android.systemui.kairos.internal.StateScopeImpl;
import java.util.concurrent.CancellationException;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes2.dex */
public final class LocalNetwork implements KairosNetwork {
    public final Events endSignal;
    public final Network network;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.kairos.LocalNetwork$activateSpec$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return LocalNetwork.this.activateSpec(null, this);
        }
    }

    /* renamed from: com.android.systemui.kairos.LocalNetwork$activateSpec$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $spec;
        final /* synthetic */ CoalescingMutableEvents $stopEmitter;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(CoalescingMutableEvents coalescingMutableEvents, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$stopEmitter = coalescingMutableEvents;
            this.$spec = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = LocalNetwork.this.new AnonymousClass2(this.$stopEmitter, this.$spec, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            EvalScope evalScope = (EvalScope) this.L$0;
            final CoalescingMutableEvents coalescingMutableEvents = this.$stopEmitter;
            final LocalNetwork localNetwork = LocalNetwork.this;
            BuildScopeImpl buildScopeImpl = new BuildScopeImpl(new StateScopeImpl(evalScope, LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.kairos.LocalNetwork$activateSpec$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return MergeKt.mergeLeft(coalescingMutableEvents, localNetwork.endSignal);
                }
            })), LocalNetwork.this.scope);
            final Function1 function1 = this.$spec;
            final CoalescingMutableEvents coalescingMutableEvents2 = this.$stopEmitter;
            return BuildScopeKt.launchScope(buildScopeImpl, new Function1() { // from class: com.android.systemui.kairos.LocalNetwork$activateSpec$2$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    BuildScope buildScope = (BuildScope) obj2;
                    BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                    buildScopeImpl2.getClass();
                    function1.mo781invoke(buildScopeImpl2);
                    return BuildScopeKt.launchEffect(buildScope, new LocalNetwork$activateSpec$2$1$1(coalescingMutableEvents2, null));
                }
            });
        }
    }

    /* renamed from: com.android.systemui.kairos.LocalNetwork$awaitOrCancel$1, reason: invalid class name and case insensitive filesystem */
    final class C08801<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08801(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LocalNetwork.this.awaitOrCancel(null, this);
        }
    }

    /* renamed from: com.android.systemui.kairos.LocalNetwork$joinOrCancel$1, reason: invalid class name and case insensitive filesystem */
    final class C08811 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08811(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LocalNetwork.this.joinOrCancel(null, this);
        }
    }

    /* renamed from: com.android.systemui.kairos.LocalNetwork$transact$2, reason: invalid class name and case insensitive filesystem */
    final class C08822 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $block;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08822(Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C08822 c08822 = new C08822(this.$block, continuation);
            c08822.L$0 = obj;
            return c08822;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08822) create((EvalScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.$block.mo781invoke((EvalScope) this.L$0);
        }
    }

    public LocalNetwork(Network network, CoroutineScope coroutineScope, Events events) {
        this.network = network;
        this.scope = coroutineScope;
        this.endSignal = events;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x007c, code lost:
    
        if (r0.joinOrCancel((kotlinx.coroutines.Job) r1, r2) == r3) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    @Override // com.android.systemui.kairos.KairosNetwork
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object activateSpec(Function1 function1, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        LocalNetwork localNetwork = this;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = localNetwork.new AnonymousClass1(continuation);
            }
        }
        Object objAwaitOrCancel = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objAwaitOrCancel);
            CompletableDeferredImpl completableDeferredImplTransaction = localNetwork.network.transaction("KairosNetwork.activateSpec", localNetwork.new AnonymousClass2(new CoalescingMutableEvents(null, new LocalNetwork$$ExternalSyntheticLambda0(), localNetwork.network, new LocalNetwork$$ExternalSyntheticLambda1(), null, 16, null), function1, null));
            anonymousClass1.L$0 = localNetwork;
            anonymousClass1.label = 1;
            objAwaitOrCancel = localNetwork.awaitOrCancel(completableDeferredImplTransaction, anonymousClass1);
            if (objAwaitOrCancel != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objAwaitOrCancel);
            return Unit.INSTANCE;
        }
        localNetwork = (LocalNetwork) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objAwaitOrCancel);
        anonymousClass1.L$0 = null;
        anonymousClass1.label = 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object awaitOrCancel(CompletableDeferredImpl completableDeferredImpl, ContinuationImpl continuationImpl) {
        C08801 c08801;
        if (continuationImpl instanceof C08801) {
            c08801 = (C08801) continuationImpl;
            int i = c08801.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08801.label = i - Integer.MIN_VALUE;
            } else {
                c08801 = new C08801(continuationImpl);
            }
        }
        Object obj = c08801.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08801.label;
        try {
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            c08801.L$0 = completableDeferredImpl;
            c08801.label = 1;
            Object objAwaitInternal = completableDeferredImpl.awaitInternal(c08801);
            return objAwaitInternal == obj2 ? obj2 : objAwaitInternal;
        } catch (CancellationException e) {
            completableDeferredImpl.cancelInternal(e);
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object joinOrCancel(Job job, ContinuationImpl continuationImpl) {
        C08811 c08811;
        if (continuationImpl instanceof C08811) {
            c08811 = (C08811) continuationImpl;
            int i = c08811.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08811.label = i - Integer.MIN_VALUE;
            } else {
                c08811 = new C08811(continuationImpl);
            }
        }
        Object obj = c08811.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08811.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                c08811.L$0 = job;
                c08811.label = 1;
                if (job.join(c08811) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                job = (Job) c08811.L$0;
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        } catch (CancellationException e) {
            job.cancel(e);
            throw e;
        }
    }

    @Override // com.android.systemui.kairos.KairosNetwork
    public final Object transact(Function1 function1, Continuation continuation) {
        return awaitOrCancel(this.network.transaction("KairosNetwork.transact", new C08822(function1, null)), (ContinuationImpl) continuation);
    }
}
