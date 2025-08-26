package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.StatusBarState;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public SharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        SharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2 sharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2 = new SharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2((Continuation) obj4);
        sharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2.Z$0 = zBooleanValue;
        sharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2.L$0 = (StatusBarState) obj2;
        sharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2.Z$1 = zBooleanValue2;
        return sharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        return new Pair(Boolean.valueOf(((StatusBarState) this.L$0) == StatusBarState.SHADE_LOCKED || !z || this.Z$1), Boolean.valueOf(z));
    }
}
