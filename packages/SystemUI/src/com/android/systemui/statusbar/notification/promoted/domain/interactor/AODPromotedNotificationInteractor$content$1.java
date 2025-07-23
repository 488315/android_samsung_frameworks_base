package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AODPromotedNotificationInteractor$content$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public AODPromotedNotificationInteractor$content$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        AODPromotedNotificationInteractor$content$1 aODPromotedNotificationInteractor$content$1 = new AODPromotedNotificationInteractor$content$1((Continuation) obj3);
        aODPromotedNotificationInteractor$content$1.L$0 = (PromotedNotificationContentModels) obj;
        aODPromotedNotificationInteractor$content$1.Z$0 = booleanValue;
        return aODPromotedNotificationInteractor$content$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromotedNotificationContentModels promotedNotificationContentModels = (PromotedNotificationContentModels) this.L$0;
        if (this.Z$0) {
            if (promotedNotificationContentModels != null) {
                return promotedNotificationContentModels.privateVersion;
            }
            return null;
        }
        if (promotedNotificationContentModels != null) {
            return promotedNotificationContentModels.publicVersion;
        }
        return null;
    }
}
