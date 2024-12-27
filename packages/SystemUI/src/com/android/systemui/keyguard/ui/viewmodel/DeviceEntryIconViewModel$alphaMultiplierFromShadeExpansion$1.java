package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.ranges.RangesKt___RangesKt;

final class DeviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1 extends SuspendLambda implements Function4 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ boolean Z$0;
    int label;

    public DeviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        float floatValue = ((Number) obj2).floatValue();
        float floatValue2 = ((Number) obj3).floatValue();
        DeviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1 deviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1 = new DeviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1((Continuation) obj4);
        deviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1.Z$0 = booleanValue;
        deviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1.F$0 = floatValue;
        deviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1.F$1 = floatValue2;
        return deviceEntryIconViewModel$alphaMultiplierFromShadeExpansion$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        float f = this.F$0;
        float f2 = 1.0f;
        float coerceIn = RangesKt___RangesKt.coerceIn(this.F$1 * 2, 0.0f, 1.0f);
        if (!z) {
            f2 = (1.0f - coerceIn) * (1.0f - f);
        }
        return new Float(f2);
    }
}
