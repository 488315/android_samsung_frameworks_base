package com.android.systemui.biometrics.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class FingerprintPropertyRepositoryImpl$propertiesInitialized$11 extends SuspendLambda implements Function6 {
    /* synthetic */ boolean Z$0;
    int label;

    public FingerprintPropertyRepositoryImpl$propertiesInitialized$11(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        FingerprintPropertyRepositoryImpl$propertiesInitialized$11 fingerprintPropertyRepositoryImpl$propertiesInitialized$11 = new FingerprintPropertyRepositoryImpl$propertiesInitialized$11((Continuation) obj6);
        fingerprintPropertyRepositoryImpl$propertiesInitialized$11.Z$0 = booleanValue;
        return fingerprintPropertyRepositoryImpl$propertiesInitialized$11.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0);
    }
}
