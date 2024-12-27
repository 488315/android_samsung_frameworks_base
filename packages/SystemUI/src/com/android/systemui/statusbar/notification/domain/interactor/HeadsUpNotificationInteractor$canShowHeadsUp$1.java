package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

final class HeadsUpNotificationInteractor$canShowHeadsUp$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public HeadsUpNotificationInteractor$canShowHeadsUp$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        HeadsUpNotificationInteractor$canShowHeadsUp$1 headsUpNotificationInteractor$canShowHeadsUp$1 = new HeadsUpNotificationInteractor$canShowHeadsUp$1((Continuation) obj5);
        headsUpNotificationInteractor$canShowHeadsUp$1.Z$0 = booleanValue;
        headsUpNotificationInteractor$canShowHeadsUp$1.Z$1 = booleanValue2;
        headsUpNotificationInteractor$canShowHeadsUp$1.L$0 = (KeyguardState) obj3;
        headsUpNotificationInteractor$canShowHeadsUp$1.Z$2 = booleanValue3;
        return headsUpNotificationInteractor$canShowHeadsUp$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        KeyguardState keyguardState = (KeyguardState) this.L$0;
        boolean z3 = this.Z$2;
        boolean z4 = keyguardState == KeyguardState.LOCKSCREEN;
        if (z3 || !z2) {
            z = false;
        } else if (!z4) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
