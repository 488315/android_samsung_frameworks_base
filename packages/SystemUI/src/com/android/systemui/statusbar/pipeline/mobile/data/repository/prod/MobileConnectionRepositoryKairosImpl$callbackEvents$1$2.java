package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.kairos.CoalescingEventProducerScope;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryKairosImpl$callbackEvents$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryKairosImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0055, code lost:
    
        if (com.android.systemui.kairos.BuildScopeKt.awaitClose(r4, r7) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0057, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0042, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r5, r6, r7) == r0) goto L15;
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
            if (r1 == 0) goto L21
            if (r1 == r4) goto L19
            if (r1 == r3) goto L15
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L15:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L58
        L19:
            java.lang.Object r1 = r7.L$0
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 r1 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L45
        L21:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            com.android.systemui.kairos.CoalescingEventProducerScope r8 = (com.android.systemui.kairos.CoalescingEventProducerScope) r8
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 r1 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1
            com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger r5 = r7.$logger
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl r6 = r7.this$0
            r1.<init>(r5, r6, r8)
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl r8 = r7.this$0
            kotlinx.coroutines.CoroutineDispatcher r5 = r8.bgDispatcher
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$1 r6 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$1
            r6.<init>(r8, r1, r2)
            r7.L$0 = r1
            r7.label = r4
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r7)
            if (r8 != r0) goto L45
            goto L57
        L45:
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl r8 = r7.this$0
            com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 r4 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0
            r5 = 1
            r4.<init>(r5, r8, r1)
            r7.L$0 = r2
            r7.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r7 = com.android.systemui.kairos.BuildScopeKt.awaitClose(r4, r7)
            if (r7 != r0) goto L58
        L57:
            return r0
        L58:
            kotlin.KotlinNothingValueException r7 = new kotlin.KotlinNothingValueException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$callbackEvents$1$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
