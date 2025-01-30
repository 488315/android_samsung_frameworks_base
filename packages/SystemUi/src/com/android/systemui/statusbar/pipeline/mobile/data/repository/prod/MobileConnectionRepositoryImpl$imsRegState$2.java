package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$imsRegState$2", m277f = "MobileConnectionRepositoryImpl.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileConnectionRepositoryImpl$imsRegState$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$imsRegState$2(MobileInputLogger mobileInputLogger, MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, Continuation<? super MobileConnectionRepositoryImpl$imsRegState$2> continuation) {
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
