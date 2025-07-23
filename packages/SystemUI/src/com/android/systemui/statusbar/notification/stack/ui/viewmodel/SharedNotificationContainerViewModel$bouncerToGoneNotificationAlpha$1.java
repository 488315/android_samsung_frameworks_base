package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ boolean Z$0;
    int label;

    public SharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj).floatValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        SharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1 sharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1 = new SharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1((Continuation) obj3);
        sharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1.F$0 = floatValue;
        sharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1.Z$0 = booleanValue;
        return sharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        if (this.Z$0) {
            f = 0.0f;
        }
        return new Float(f);
    }
}
