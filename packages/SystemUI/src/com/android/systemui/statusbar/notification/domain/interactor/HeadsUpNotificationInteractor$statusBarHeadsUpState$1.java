package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.domain.model.TopPinnedState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class HeadsUpNotificationInteractor$statusBarHeadsUpState$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public HeadsUpNotificationInteractor$statusBarHeadsUpState$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        HeadsUpNotificationInteractor$statusBarHeadsUpState$1 headsUpNotificationInteractor$statusBarHeadsUpState$1 = new HeadsUpNotificationInteractor$statusBarHeadsUpState$1((Continuation) obj3);
        headsUpNotificationInteractor$statusBarHeadsUpState$1.L$0 = (TopPinnedState) obj;
        headsUpNotificationInteractor$statusBarHeadsUpState$1.Z$0 = zBooleanValue;
        return headsUpNotificationInteractor$statusBarHeadsUpState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.Z$0 ? (TopPinnedState) this.L$0 : TopPinnedState.NothingPinned.INSTANCE;
    }
}
