package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class WifiViewModel$activity$1$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public WifiViewModel$activity$1$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WifiViewModel$activity$1$1 wifiViewModel$activity$1$1 = new WifiViewModel$activity$1$1((Continuation) obj3);
        wifiViewModel$activity$1$1.L$0 = (DataActivityModel) obj;
        wifiViewModel$activity$1$1.L$1 = (String) obj2;
        return wifiViewModel$activity$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DataActivityModel dataActivityModel = (DataActivityModel) this.L$0;
        if (((String) this.L$1) != null) {
            return dataActivityModel;
        }
        return null;
    }
}
