package com.android.systemui.biometrics.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class FingerprintPropertyRepositoryImpl$propertiesInitialized$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FingerprintPropertyRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintPropertyRepositoryImpl$propertiesInitialized$2(FingerprintPropertyRepositoryImpl fingerprintPropertyRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintPropertyRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FingerprintPropertyRepositoryImpl$propertiesInitialized$2 fingerprintPropertyRepositoryImpl$propertiesInitialized$2 = new FingerprintPropertyRepositoryImpl$propertiesInitialized$2(this.this$0, continuation);
        fingerprintPropertyRepositoryImpl$propertiesInitialized$2.L$0 = obj;
        return fingerprintPropertyRepositoryImpl$propertiesInitialized$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintPropertyRepositoryImpl$propertiesInitialized$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(!Intrinsics.areEqual(this.this$0.props.$$delegate_0.getValue(), FingerprintPropertyRepositoryImpl.UNINITIALIZED_PROPS));
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
