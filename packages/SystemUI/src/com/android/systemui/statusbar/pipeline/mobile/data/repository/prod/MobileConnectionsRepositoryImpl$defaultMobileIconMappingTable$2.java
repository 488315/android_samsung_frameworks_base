package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.util.Log;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2 mobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2 = new MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2.L$0 = obj;
        return mobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2) create((Map) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
        for (Map.Entry entry : map.entrySet()) {
            Log.d("MobileConnectionsRepository", "mobileIconMappingTable(" + entry.getKey() + "): " + entry.getValue() + " ");
            mobileConnectionsRepositoryImpl.logger.logMobileIconMappingTable(((Number) entry.getKey()).intValue(), (Map) entry.getValue());
        }
        return Unit.INSTANCE;
    }
}
