package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.common.shared.model.NotificationContainerBounds;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$bounds$2$2 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SharedNotificationContainerViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$bounds$2$2(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = sharedNotificationContainerViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj3).intValue();
        SharedNotificationContainerViewModel$bounds$2$2 sharedNotificationContainerViewModel$bounds$2$2 = new SharedNotificationContainerViewModel$bounds$2$2(this.this$0, (Continuation) obj5);
        sharedNotificationContainerViewModel$bounds$2$2.Z$0 = booleanValue;
        sharedNotificationContainerViewModel$bounds$2$2.L$0 = (NotificationContainerBounds) obj2;
        sharedNotificationContainerViewModel$bounds$2$2.I$0 = intValue;
        sharedNotificationContainerViewModel$bounds$2$2.L$1 = (Triple) obj4;
        return sharedNotificationContainerViewModel$bounds$2$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        NotificationContainerBounds notificationContainerBounds = (NotificationContainerBounds) this.L$0;
        int i = this.I$0;
        Triple triple = (Triple) this.L$1;
        float floatValue = ((Number) triple.component1()).floatValue();
        boolean booleanValue = ((Boolean) triple.component2()).booleanValue();
        float floatValue2 = ((Number) triple.component3()).floatValue();
        float floatValue3 = ((Number) this.this$0.interactor.bottomPosition.$$delegate_0.getValue()).floatValue();
        if (z) {
            return new NotificationContainerBounds(floatValue - i, notificationContainerBounds.bottom - floatValue3, floatValue3 == -1.0f);
        }
        if (floatValue2 == 0.0f && !booleanValue) {
            r5 = true;
        }
        return new NotificationContainerBounds(floatValue, notificationContainerBounds.bottom - floatValue3, r5);
    }
}
