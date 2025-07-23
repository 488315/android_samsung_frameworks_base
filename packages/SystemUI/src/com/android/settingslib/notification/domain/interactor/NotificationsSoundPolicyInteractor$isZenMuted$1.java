package com.android.settingslib.notification.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NotificationsSoundPolicyInteractor$isZenMuted$1 extends SuspendLambda implements Function6 {
    final /* synthetic */ int $stream;
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationsSoundPolicyInteractor$isZenMuted$1(int i, Continuation continuation) {
        super(6, continuation);
        this.$stream = i;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        int intValue = ((Number) obj).intValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        boolean booleanValue4 = ((Boolean) obj5).booleanValue();
        NotificationsSoundPolicyInteractor$isZenMuted$1 notificationsSoundPolicyInteractor$isZenMuted$1 = new NotificationsSoundPolicyInteractor$isZenMuted$1(this.$stream, (Continuation) obj6);
        notificationsSoundPolicyInteractor$isZenMuted$1.I$0 = intValue;
        notificationsSoundPolicyInteractor$isZenMuted$1.Z$0 = booleanValue;
        notificationsSoundPolicyInteractor$isZenMuted$1.Z$1 = booleanValue2;
        notificationsSoundPolicyInteractor$isZenMuted$1.Z$2 = booleanValue3;
        notificationsSoundPolicyInteractor$isZenMuted$1.Z$3 = booleanValue4;
        return notificationsSoundPolicyInteractor$isZenMuted$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        boolean z4 = this.Z$3;
        boolean z5 = true;
        if (i == 1) {
            int i2 = this.$stream;
            if (i2 == 4 && !z) {
                return Boolean.TRUE;
            }
            if (i2 == 3 && !z2) {
                return Boolean.TRUE;
            }
            if (i2 == 1 && !z4) {
                return Boolean.TRUE;
            }
            if ((i2 == 2 || i2 == 5) && !z3) {
                return Boolean.TRUE;
            }
        } else {
            if (i == 2) {
                return Boolean.TRUE;
            }
            if (i == 3) {
                int i3 = this.$stream;
                if (i3 != 2 && i3 != 5 && i3 != 1) {
                    z5 = false;
                }
                return Boolean.valueOf(z5);
            }
        }
        return Boolean.FALSE;
    }
}
