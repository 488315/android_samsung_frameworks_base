package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationScrollViewModel$qsAllowsClipping$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    int label;

    public NotificationScrollViewModel$qsAllowsClipping$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj2).floatValue();
        NotificationScrollViewModel$qsAllowsClipping$1 notificationScrollViewModel$qsAllowsClipping$1 = new NotificationScrollViewModel$qsAllowsClipping$1((Continuation) obj3);
        notificationScrollViewModel$qsAllowsClipping$1.L$0 = (ShadeMode) obj;
        notificationScrollViewModel$qsAllowsClipping$1.F$0 = floatValue;
        return notificationScrollViewModel$qsAllowsClipping$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ShadeMode shadeMode = (ShadeMode) this.L$0;
        float f = this.F$0;
        boolean z = true;
        if (!(shadeMode instanceof ShadeMode.Dual) && !(shadeMode instanceof ShadeMode.Split)) {
            if (!(shadeMode instanceof ShadeMode.Single)) {
                throw new NoWhenBranchMatchedException();
            }
            if (f >= 0.5f) {
                z = false;
            }
        }
        return Boolean.valueOf(z);
    }
}
