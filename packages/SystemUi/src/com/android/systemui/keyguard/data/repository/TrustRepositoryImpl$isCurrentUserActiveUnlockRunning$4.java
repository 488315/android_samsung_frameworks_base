package com.android.systemui.keyguard.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4", m277f = "TrustRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ TrustRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4(TrustRepositoryImpl trustRepositoryImpl, Continuation<? super TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4> continuation) {
        super(2, continuation);
        this.this$0 = trustRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4 trustRepositoryImpl$isCurrentUserActiveUnlockRunning$4 = new TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4(this.this$0, continuation);
        trustRepositoryImpl$isCurrentUserActiveUnlockRunning$4.Z$0 = ((Boolean) obj).booleanValue();
        return trustRepositoryImpl$isCurrentUserActiveUnlockRunning$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.logger.isCurrentUserActiveUnlockRunning(this.Z$0);
        return Unit.INSTANCE;
    }
}
