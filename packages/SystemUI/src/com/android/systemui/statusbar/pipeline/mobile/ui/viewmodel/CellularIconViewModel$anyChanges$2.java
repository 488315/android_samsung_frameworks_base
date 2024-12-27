package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class CellularIconViewModel$anyChanges$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$anyChanges$2(CellularIconViewModel cellularIconViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CellularIconViewModel$anyChanges$2 cellularIconViewModel$anyChanges$2 = new CellularIconViewModel$anyChanges$2(this.this$0, continuation);
        cellularIconViewModel$anyChanges$2.L$0 = obj;
        return cellularIconViewModel$anyChanges$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CellularIconViewModel$anyChanges$2) create((MobileSimpleLogger) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MobileSimpleLogger mobileSimpleLogger = (MobileSimpleLogger) this.L$0;
        Log.d("MobileIconViewModel", mobileSimpleLogger.toString());
        if (!this.this$0.voiceServiceAcquired && mobileSimpleLogger.connected) {
            Log.d("MobileIconViewModel", "!@Boot: Voice SVC is acquired");
            this.this$0.voiceServiceAcquired = true;
        }
        if (!this.this$0.dataServiceAcquired && mobileSimpleLogger.dataConnected) {
            Log.d("MobileIconViewModel", "!@Boot: Data SVC is acquired");
            this.this$0.dataServiceAcquired = true;
        }
        return Unit.INSTANCE;
    }
}
