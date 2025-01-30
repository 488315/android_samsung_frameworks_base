package com.android.systemui.statusbar.notification.shelf.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.shelf.domain.interactor.NotificationShelfInteractor$isShelfStatic$1", m277f = "NotificationShelfInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NotificationShelfInteractor$isShelfStatic$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public NotificationShelfInteractor$isShelfStatic$1(Continuation<? super NotificationShelfInteractor$isShelfStatic$1> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        NotificationShelfInteractor$isShelfStatic$1 notificationShelfInteractor$isShelfStatic$1 = new NotificationShelfInteractor$isShelfStatic$1((Continuation) obj3);
        notificationShelfInteractor$isShelfStatic$1.Z$0 = booleanValue;
        notificationShelfInteractor$isShelfStatic$1.Z$1 = booleanValue2;
        return notificationShelfInteractor$isShelfStatic$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && this.Z$1);
    }
}
