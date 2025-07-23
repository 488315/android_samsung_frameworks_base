package com.android.systemui.security.data.repository;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SecurityRepositoryImpl$security$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ SecurityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecurityRepositoryImpl$security$1(SecurityRepositoryImpl securityRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = securityRepositoryImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r6v5, types: [kotlinx.coroutines.channels.SendChannel] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object invokeSuspend$updateState(kotlinx.coroutines.channels.ProducerScope r6, com.android.systemui.security.data.repository.SecurityRepositoryImpl r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            boolean r0 = r8 instanceof com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$updateState$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$updateState$1 r0 = (com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$updateState$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$updateState$1 r0 = new com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$updateState$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.SendChannel r6 = (kotlinx.coroutines.channels.SendChannel) r6
            java.lang.Object r7 = r0.L$0
            com.android.systemui.common.coroutine.ChannelExt r7 = (com.android.systemui.common.coroutine.ChannelExt) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L55
        L2f:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L37:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.common.coroutine.ChannelExt r8 = com.android.systemui.common.coroutine.ChannelExt.INSTANCE
            com.android.systemui.security.data.model.SecurityModel$Companion r2 = com.android.systemui.security.data.model.SecurityModel.Companion
            com.android.systemui.statusbar.policy.SecurityController r4 = r7.securityController
            r0.L$0 = r8
            r0.L$1 = r6
            r0.label = r3
            r2.getClass()
            kotlinx.coroutines.CoroutineDispatcher r7 = r7.bgDispatcher
            java.lang.Object r7 = com.android.systemui.security.data.model.SecurityModel.Companion.create(r4, r7, r0)
            if (r7 != r1) goto L52
            return r1
        L52:
            r5 = r8
            r8 = r7
            r7 = r5
        L55:
            java.lang.String r0 = "SecurityRepositoryImpl"
            com.android.systemui.common.coroutine.ChannelExt.trySendWithFailureLogging$default(r7, r6, r8, r0)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1.invokeSuspend$updateState(kotlinx.coroutines.channels.ProducerScope, com.android.systemui.security.data.repository.SecurityRepositoryImpl, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecurityRepositoryImpl$security$1 securityRepositoryImpl$security$1 = new SecurityRepositoryImpl$security$1(this.this$0, continuation);
        securityRepositoryImpl$security$1.L$0 = obj;
        return securityRepositoryImpl$security$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecurityRepositoryImpl$security$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x005d, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r4, r5) == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L60
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            java.lang.Object r1 = r5.L$1
            com.android.systemui.statusbar.policy.SecurityController$SecurityControllerCallback r1 = (com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback) r1
            java.lang.Object r3 = r5.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4b
        L24:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
            com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1 r1 = new com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$callback$1
            com.android.systemui.security.data.repository.SecurityRepositoryImpl r4 = r5.this$0
            r1.<init>()
            com.android.systemui.security.data.repository.SecurityRepositoryImpl r4 = r5.this$0
            com.android.systemui.statusbar.policy.SecurityController r4 = r4.securityController
            com.android.systemui.statusbar.policy.SecurityControllerImpl r4 = (com.android.systemui.statusbar.policy.SecurityControllerImpl) r4
            r4.addCallback(r1)
            com.android.systemui.security.data.repository.SecurityRepositoryImpl r4 = r5.this$0
            r5.L$0 = r6
            r5.L$1 = r1
            r5.label = r3
            java.lang.Object r3 = invokeSuspend$updateState(r6, r4, r5)
            if (r3 != r0) goto L4a
            goto L5f
        L4a:
            r3 = r6
        L4b:
            com.android.systemui.security.data.repository.SecurityRepositoryImpl r6 = r5.this$0
            com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$$ExternalSyntheticLambda0 r4 = new com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1$$ExternalSyntheticLambda0
            r4.<init>()
            r6 = 0
            r5.L$0 = r6
            r5.L$1 = r6
            r5.label = r2
            java.lang.Object r5 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r4, r5)
            if (r5 != r0) goto L60
        L5f:
            return r0
        L60:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
