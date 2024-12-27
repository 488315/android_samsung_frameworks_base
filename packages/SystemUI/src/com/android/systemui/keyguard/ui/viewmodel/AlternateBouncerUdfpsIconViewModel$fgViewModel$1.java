package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class AlternateBouncerUdfpsIconViewModel$fgViewModel$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;

    public AlternateBouncerUdfpsIconViewModel$fgViewModel$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        AlternateBouncerUdfpsIconViewModel$fgViewModel$1 alternateBouncerUdfpsIconViewModel$fgViewModel$1 = new AlternateBouncerUdfpsIconViewModel$fgViewModel$1((Continuation) obj3);
        alternateBouncerUdfpsIconViewModel$fgViewModel$1.I$0 = intValue;
        alternateBouncerUdfpsIconViewModel$fgViewModel$1.I$1 = intValue2;
        return alternateBouncerUdfpsIconViewModel$fgViewModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new DeviceEntryForegroundViewModel.ForegroundIconViewModel(DeviceEntryIconView.IconType.FINGERPRINT, false, this.I$0, this.I$1);
    }
}
