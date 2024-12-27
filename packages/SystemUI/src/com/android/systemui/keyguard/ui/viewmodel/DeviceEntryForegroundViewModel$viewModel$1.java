package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DeviceEntryForegroundViewModel$viewModel$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public DeviceEntryForegroundViewModel$viewModel$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        int intValue = ((Number) obj3).intValue();
        int intValue2 = ((Number) obj4).intValue();
        DeviceEntryForegroundViewModel$viewModel$1 deviceEntryForegroundViewModel$viewModel$1 = new DeviceEntryForegroundViewModel$viewModel$1((Continuation) obj5);
        deviceEntryForegroundViewModel$viewModel$1.L$0 = (DeviceEntryIconView.IconType) obj;
        deviceEntryForegroundViewModel$viewModel$1.Z$0 = booleanValue;
        deviceEntryForegroundViewModel$viewModel$1.I$0 = intValue;
        deviceEntryForegroundViewModel$viewModel$1.I$1 = intValue2;
        return deviceEntryForegroundViewModel$viewModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new DeviceEntryForegroundViewModel.ForegroundIconViewModel((DeviceEntryIconView.IconType) this.L$0, this.Z$0, this.I$0, this.I$1);
    }
}
