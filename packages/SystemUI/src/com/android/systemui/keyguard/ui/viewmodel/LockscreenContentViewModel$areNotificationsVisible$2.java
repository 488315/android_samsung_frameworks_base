package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.ClockSize;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LockscreenContentViewModel$areNotificationsVisible$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public LockscreenContentViewModel$areNotificationsVisible$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        LockscreenContentViewModel$areNotificationsVisible$2 lockscreenContentViewModel$areNotificationsVisible$2 = new LockscreenContentViewModel$areNotificationsVisible$2((Continuation) obj3);
        lockscreenContentViewModel$areNotificationsVisible$2.L$0 = (ClockSize) obj;
        lockscreenContentViewModel$areNotificationsVisible$2.Z$0 = booleanValue;
        return lockscreenContentViewModel$areNotificationsVisible$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((ClockSize) this.L$0) == ClockSize.SMALL || this.Z$0);
    }
}
