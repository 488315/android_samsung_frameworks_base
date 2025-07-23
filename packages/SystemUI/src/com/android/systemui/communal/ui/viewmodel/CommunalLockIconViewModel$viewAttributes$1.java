package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalLockIconViewModel$viewAttributes$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    /* synthetic */ Object L$0;
    int label;

    public CommunalLockIconViewModel$viewAttributes$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int intValue = ((Number) obj2).intValue();
        int intValue2 = ((Number) obj3).intValue();
        CommunalLockIconViewModel$viewAttributes$1 communalLockIconViewModel$viewAttributes$1 = new CommunalLockIconViewModel$viewAttributes$1((Continuation) obj4);
        communalLockIconViewModel$viewAttributes$1.L$0 = (DeviceEntryIconView.IconType) obj;
        communalLockIconViewModel$viewAttributes$1.I$0 = intValue;
        communalLockIconViewModel$viewAttributes$1.I$1 = intValue2;
        return communalLockIconViewModel$viewAttributes$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new CommunalLockIconAttributes((DeviceEntryIconView.IconType) this.L$0, this.I$0, this.I$1);
    }
}
