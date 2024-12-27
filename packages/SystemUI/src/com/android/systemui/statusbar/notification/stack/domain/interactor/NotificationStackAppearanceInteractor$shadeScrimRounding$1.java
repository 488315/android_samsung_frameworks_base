package com.android.systemui.statusbar.notification.stack.domain.interactor;

import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrimRounding;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationStackAppearanceInteractor$shadeScrimRounding$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public NotificationStackAppearanceInteractor$shadeScrimRounding$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        NotificationStackAppearanceInteractor$shadeScrimRounding$1 notificationStackAppearanceInteractor$shadeScrimRounding$1 = new NotificationStackAppearanceInteractor$shadeScrimRounding$1((Continuation) obj3);
        notificationStackAppearanceInteractor$shadeScrimRounding$1.L$0 = (ShadeMode) obj;
        notificationStackAppearanceInteractor$shadeScrimRounding$1.Z$0 = booleanValue;
        return notificationStackAppearanceInteractor$shadeScrimRounding$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new ShadeScrimRounding((Intrinsics.areEqual((ShadeMode) this.L$0, ShadeMode.Split.INSTANCE) && this.Z$0) ? false : true, !Intrinsics.areEqual(r4, ShadeMode.Single.INSTANCE));
    }
}
