package com.android.systemui.brightness.domain.interactor;

import com.android.settingslib.display.BrightnessUtils;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.shared.model.LinearBrightness;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class ScreenBrightnessInteractor$gammaBrightness$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ float F$2;
    int label;
    final /* synthetic */ ScreenBrightnessInteractor this$0;

    public ScreenBrightnessInteractor$gammaBrightness$1$1(ScreenBrightnessInteractor screenBrightnessInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = screenBrightnessInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float f = ((LinearBrightness) obj).floatValue;
        float f2 = ((LinearBrightness) obj2).floatValue;
        float f3 = ((LinearBrightness) obj3).floatValue;
        ScreenBrightnessInteractor$gammaBrightness$1$1 screenBrightnessInteractor$gammaBrightness$1$1 = new ScreenBrightnessInteractor$gammaBrightness$1$1(this.this$0, (Continuation) obj4);
        screenBrightnessInteractor$gammaBrightness$1$1.F$0 = f;
        screenBrightnessInteractor$gammaBrightness$1$1.F$1 = f2;
        screenBrightnessInteractor$gammaBrightness$1$1.F$2 = f3;
        return screenBrightnessInteractor$gammaBrightness$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        float f2 = this.F$1;
        float f3 = this.F$2;
        ScreenBrightnessInteractor screenBrightnessInteractor = this.this$0;
        int i = ScreenBrightnessInteractor.$r8$clinit;
        screenBrightnessInteractor.getClass();
        return GammaBrightness.m933boximpl(BrightnessUtils.convertLinearToGammaFloat(f, f2, f3));
    }
}
