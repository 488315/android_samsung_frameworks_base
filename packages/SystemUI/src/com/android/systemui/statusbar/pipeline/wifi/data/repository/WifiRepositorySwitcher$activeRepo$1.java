package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class WifiRepositorySwitcher$activeRepo$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WifiRepositorySwitcher this$0;

    public WifiRepositorySwitcher$activeRepo$1(WifiRepositorySwitcher wifiRepositorySwitcher, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiRepositorySwitcher;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositorySwitcher$activeRepo$1 wifiRepositorySwitcher$activeRepo$1 = new WifiRepositorySwitcher$activeRepo$1(this.this$0, continuation);
        wifiRepositorySwitcher$activeRepo$1.Z$0 = ((Boolean) obj).booleanValue();
        return wifiRepositorySwitcher$activeRepo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((WifiRepositorySwitcher$activeRepo$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.Z$0 ? this.this$0.demoImpl : this.this$0.realImpl;
    }
}
