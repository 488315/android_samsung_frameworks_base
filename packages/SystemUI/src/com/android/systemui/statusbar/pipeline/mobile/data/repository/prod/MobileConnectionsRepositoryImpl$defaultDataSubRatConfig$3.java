package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3 mobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3 = new MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3.L$0 = obj;
        return mobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$defaultDataSubRatConfig$3) create((MobileMappings.Config) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MobileMappings.Config config = (MobileMappings.Config) this.L$0;
        MobileInputLogger mobileInputLogger = this.this$0.logger;
        Intrinsics.checkNotNull(config);
        mobileInputLogger.logDefaultDataSubRatConfig(config);
        return Unit.INSTANCE;
    }
}
