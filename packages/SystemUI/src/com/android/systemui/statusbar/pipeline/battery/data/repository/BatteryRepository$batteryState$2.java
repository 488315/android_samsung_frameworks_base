package com.android.systemui.statusbar.pipeline.battery.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class BatteryRepository$batteryState$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public BatteryRepository$batteryState$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BatteryRepository$batteryState$2 batteryRepository$batteryState$2 = new BatteryRepository$batteryState$2((Continuation) obj3);
        batteryRepository$batteryState$2.L$0 = (BatteryCallbackState) obj;
        batteryRepository$batteryState$2.L$1 = (Function1) obj2;
        return batteryRepository$batteryState$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((Function1) this.L$1).mo781invoke((BatteryCallbackState) this.L$0);
    }
}
