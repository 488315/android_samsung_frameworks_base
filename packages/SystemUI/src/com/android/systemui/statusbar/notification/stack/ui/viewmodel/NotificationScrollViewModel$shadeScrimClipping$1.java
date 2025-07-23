package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimBounds;
import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimClipping;
import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimRounding;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationScrollViewModel$shadeScrimClipping$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public NotificationScrollViewModel$shadeScrimClipping$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        NotificationScrollViewModel$shadeScrimClipping$1 notificationScrollViewModel$shadeScrimClipping$1 = new NotificationScrollViewModel$shadeScrimClipping$1((Continuation) obj4);
        notificationScrollViewModel$shadeScrimClipping$1.Z$0 = booleanValue;
        notificationScrollViewModel$shadeScrimClipping$1.L$0 = (ShadeScrimBounds) obj2;
        notificationScrollViewModel$shadeScrimClipping$1.L$1 = (ShadeScrimRounding) obj3;
        return notificationScrollViewModel$shadeScrimClipping$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        ShadeScrimBounds shadeScrimBounds = (ShadeScrimBounds) this.L$0;
        ShadeScrimRounding shadeScrimRounding = (ShadeScrimRounding) this.L$1;
        if (shadeScrimBounds != null) {
            if (!z) {
                shadeScrimBounds = null;
            }
            if (shadeScrimBounds != null) {
                return new ShadeScrimClipping(shadeScrimBounds, shadeScrimRounding);
            }
        }
        return null;
    }
}
