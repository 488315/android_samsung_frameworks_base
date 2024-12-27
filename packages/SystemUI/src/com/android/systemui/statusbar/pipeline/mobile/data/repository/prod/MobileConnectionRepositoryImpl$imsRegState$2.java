package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryImpl$imsRegState$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$imsRegState$2(MobileInputLogger mobileInputLogger, MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$logger = mobileInputLogger;
        this.this$0 = mobileConnectionRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$imsRegState$2 mobileConnectionRepositoryImpl$imsRegState$2 = new MobileConnectionRepositoryImpl$imsRegState$2(this.$logger, this.this$0, continuation);
        mobileConnectionRepositoryImpl$imsRegState$2.L$0 = obj;
        return mobileConnectionRepositoryImpl$imsRegState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$imsRegState$2) create((ImsRegState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$logger.logImsRegState(this.this$0.slotId, (ImsRegState) this.L$0);
        return Unit.INSTANCE;
    }
}
