package com.android.systemui.statusbar.policy.domain.interactor;

import android.app.NotificationManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ZenModeInteractor$areNotificationsHiddenInShade$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public ZenModeInteractor$areNotificationsHiddenInShade$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ZenModeInteractor$areNotificationsHiddenInShade$1 zenModeInteractor$areNotificationsHiddenInShade$1 = new ZenModeInteractor$areNotificationsHiddenInShade$1((Continuation) obj3);
        zenModeInteractor$areNotificationsHiddenInShade$1.Z$0 = booleanValue;
        zenModeInteractor$areNotificationsHiddenInShade$1.L$0 = (NotificationManager.Policy) obj2;
        return zenModeInteractor$areNotificationsHiddenInShade$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        NotificationManager.Policy policy = (NotificationManager.Policy) this.L$0;
        boolean z2 = false;
        if (z) {
            if (!(policy != null ? policy.showInNotificationList() : true)) {
                z2 = true;
            }
        }
        return Boolean.valueOf(z2);
    }
}
