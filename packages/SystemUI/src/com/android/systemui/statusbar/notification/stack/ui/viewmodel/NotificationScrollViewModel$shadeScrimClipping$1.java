package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimBounds;
import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimClipping;
import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimRounding;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class NotificationScrollViewModel$shadeScrimClipping$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public NotificationScrollViewModel$shadeScrimClipping$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationScrollViewModel$shadeScrimClipping$1 notificationScrollViewModel$shadeScrimClipping$1 = new NotificationScrollViewModel$shadeScrimClipping$1((Continuation) obj3);
        notificationScrollViewModel$shadeScrimClipping$1.L$0 = (ShadeScrimBounds) obj;
        notificationScrollViewModel$shadeScrimClipping$1.L$1 = (ShadeScrimRounding) obj2;
        return notificationScrollViewModel$shadeScrimClipping$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ShadeScrimBounds shadeScrimBounds = (ShadeScrimBounds) this.L$0;
        ShadeScrimRounding shadeScrimRounding = (ShadeScrimRounding) this.L$1;
        if (shadeScrimBounds != null) {
            return new ShadeScrimClipping(shadeScrimBounds, shadeScrimRounding);
        }
        return null;
    }
}
