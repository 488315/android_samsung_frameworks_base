package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DeviceEntryForegroundViewModel$isShowingAodOrDozing$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    int label;

    public DeviceEntryForegroundViewModel$isShowingAodOrDozing$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj2).floatValue();
        DeviceEntryForegroundViewModel$isShowingAodOrDozing$1 deviceEntryForegroundViewModel$isShowingAodOrDozing$1 = new DeviceEntryForegroundViewModel$isShowingAodOrDozing$1((Continuation) obj3);
        deviceEntryForegroundViewModel$isShowingAodOrDozing$1.L$0 = (KeyguardState) obj;
        deviceEntryForegroundViewModel$isShowingAodOrDozing$1.F$0 = floatValue;
        return deviceEntryForegroundViewModel$isShowingAodOrDozing$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((KeyguardState) this.L$0) == KeyguardState.AOD || this.F$0 == 1.0f);
    }
}
