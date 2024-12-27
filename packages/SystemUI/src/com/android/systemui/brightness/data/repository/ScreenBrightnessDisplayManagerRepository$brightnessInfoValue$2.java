package com.android.systemui.brightness.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ScreenBrightnessDisplayManagerRepository this$0;

    public ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2(ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenBrightnessDisplayManagerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository = this.this$0;
        return screenBrightnessDisplayManagerRepository.displayManager.getDisplay(screenBrightnessDisplayManagerRepository.displayId).getBrightnessInfo();
    }
}
