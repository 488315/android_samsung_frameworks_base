package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.kairos.CoalescingEventProducerScope;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;

/* loaded from: classes3.dex */
final class MobileConnectionRepositoryKairosImpl$callbackEvents$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryKairosImpl this$0;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 $callback;
        int label;
        final /* synthetic */ MobileConnectionRepositoryKairosImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl, MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1, Continuation continuation) {
            super(2, continuation);
            this.this$0 = mobileConnectionRepositoryKairosImpl;
            this.$callback = mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$callback, continuation);
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
            MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.this$0;
            mobileConnectionRepositoryKairosImpl.telephonyManager.registerTelephonyCallback(ExecutorsKt.asExecutor(mobileConnectionRepositoryKairosImpl.bgDispatcher), this.$callback);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryKairosImpl$callbackEvents$1$2(MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl, MobileInputLogger mobileInputLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryKairosImpl;
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryKairosImpl$callbackEvents$1$2 mobileConnectionRepositoryKairosImpl$callbackEvents$1$2 = new MobileConnectionRepositoryKairosImpl$callbackEvents$1$2(this.this$0, this.$logger, continuation);
        mobileConnectionRepositoryKairosImpl$callbackEvents$1$2.L$0 = obj;
        return mobileConnectionRepositoryKairosImpl$callbackEvents$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryKairosImpl$callbackEvents$1$2) create((CoalescingEventProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0055, code lost:
    
        if (com.android.systemui.kairos.BuildScopeKt.awaitClose(r4, r7) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 = new MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1(this.$logger, this.this$0, (CoalescingEventProducerScope) this.L$0);
            MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.this$0;
            CoroutineDispatcher coroutineDispatcher = mobileConnectionRepositoryKairosImpl.bgDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(mobileConnectionRepositoryKairosImpl, mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1, null);
            this.L$0 = mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1;
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) != coroutineSingletons) {
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
        mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 = (MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1) this.L$0;
        ResultKt.throwOnFailure(obj);
        MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 mobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 = new MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0(1, this.this$0, mobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1);
        this.L$0 = null;
        this.label = 2;
    }
}
