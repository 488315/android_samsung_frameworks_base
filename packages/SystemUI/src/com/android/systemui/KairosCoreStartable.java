package com.android.systemui;

import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.kairos.KairosNetworkKt;
import com.android.systemui.kairos.RootKairosNetwork;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class KairosCoreStartable implements CoreStartable, KairosNetwork {
    public final Lazy activatables;
    public final CoroutineScope appScope;
    public final CompletableDeferredImpl started;
    public final RootKairosNetwork unwrappedNetwork;

    /* renamed from: com.android.systemui.KairosCoreStartable$activateSpec$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KairosCoreStartable.this.activateSpec(null, this);
        }
    }

    /* renamed from: com.android.systemui.KairosCoreStartable$start$1, reason: invalid class name and case insensitive filesystem */
    final class C07801 extends SuspendLambda implements Function2 {
        int label;

        public C07801(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KairosCoreStartable.this.new C07801(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07801) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KairosCoreStartable kairosCoreStartable = KairosCoreStartable.this;
                RootKairosNetwork rootKairosNetwork = kairosCoreStartable.unwrappedNetwork;
                KairosCoreStartable$start$1$$ExternalSyntheticLambda0 kairosCoreStartable$start$1$$ExternalSyntheticLambda0 = new KairosCoreStartable$start$1$$ExternalSyntheticLambda0(kairosCoreStartable, 0);
                this.label = 1;
                if (rootKairosNetwork.$$delegate_1.activateSpec(kairosCoreStartable$start$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.KairosCoreStartable$transact$1, reason: invalid class name and case insensitive filesystem */
    final class C07811<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C07811(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KairosCoreStartable.this.transact(null, this);
        }
    }

    private KairosCoreStartable(CoroutineScope coroutineScope, Lazy lazy, RootKairosNetwork rootKairosNetwork) {
        this.appScope = coroutineScope;
        this.activatables = lazy;
        this.unwrappedNetwork = rootKairosNetwork;
        this.started = CompletableDeferredKt.CompletableDeferred$default();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0060, code lost:
    
        if (r5.$$delegate_1.activateSpec(r6, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.kairos.KairosNetwork
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object activateSpec(Function1 function1, Continuation continuation) {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = function1;
            anonymousClass1.label = 1;
            if (this.started.awaitInternal(anonymousClass1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        function1 = (Function1) anonymousClass1.L$1;
        this = (KairosCoreStartable) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        RootKairosNetwork rootKairosNetwork = this.unwrappedNetwork;
        anonymousClass1.L$0 = null;
        anonymousClass1.L$1 = null;
        anonymousClass1.label = 2;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.appScope, null, null, new C07801(null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.kairos.KairosNetwork
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object transact(Function1 function1, Continuation continuation) {
        C07811 c07811;
        if (continuation instanceof C07811) {
            c07811 = (C07811) continuation;
            int i = c07811.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07811.label = i - Integer.MIN_VALUE;
            } else {
                c07811 = new C07811(continuation);
            }
        }
        Object obj = c07811.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07811.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            c07811.L$0 = this;
            c07811.L$1 = function1;
            c07811.label = 1;
            if (this.started.awaitInternal(c07811) != coroutineSingletons) {
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        function1 = (Function1) c07811.L$1;
        this = (KairosCoreStartable) c07811.L$0;
        ResultKt.throwOnFailure(obj);
        RootKairosNetwork rootKairosNetwork = this.unwrappedNetwork;
        c07811.L$0 = null;
        c07811.L$1 = null;
        c07811.label = 2;
        Object objTransact = rootKairosNetwork.$$delegate_1.transact(function1, c07811);
        return objTransact == coroutineSingletons ? coroutineSingletons : objTransact;
    }

    public KairosCoreStartable(CoroutineScope coroutineScope, Lazy lazy) {
        this(coroutineScope, lazy, KairosNetworkKt.launchKairosNetwork$default(coroutineScope));
    }
}
