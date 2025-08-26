package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
final class DeviceEntryIconViewModel$nonAnimatedBurnInOffsets$1 extends SuspendLambda implements Function4 {
    /* synthetic */ float F$0;
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;

    public DeviceEntryIconViewModel$nonAnimatedBurnInOffsets$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int iIntValue = ((Number) obj).intValue();
        int iIntValue2 = ((Number) obj2).intValue();
        float fFloatValue = ((Number) obj3).floatValue();
        DeviceEntryIconViewModel$nonAnimatedBurnInOffsets$1 deviceEntryIconViewModel$nonAnimatedBurnInOffsets$1 = new DeviceEntryIconViewModel$nonAnimatedBurnInOffsets$1((Continuation) obj4);
        deviceEntryIconViewModel$nonAnimatedBurnInOffsets$1.I$0 = iIntValue;
        deviceEntryIconViewModel$nonAnimatedBurnInOffsets$1.I$1 = iIntValue2;
        deviceEntryIconViewModel$nonAnimatedBurnInOffsets$1.F$0 = fFloatValue;
        return deviceEntryIconViewModel$nonAnimatedBurnInOffsets$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new BurnInOffsets(this.I$0, this.I$1, this.F$0);
    }
}
