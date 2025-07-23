package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class LockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ LockScreenMinimalismCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockScreenMinimalismCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2 lockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2 = new LockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2(this.this$0, continuation);
        lockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2.Z$0 = ((Boolean) obj).booleanValue();
        return lockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger;
        Object trackSeenNotifications;
        Set set;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z2 = this.Z$0;
            z = this.this$0.minimalismEnabled;
            if (z2 != z) {
                this.this$0.minimalismEnabled = z2;
                set = this.this$0.unseenNotifications;
                set.clear();
                this.this$0.getUnseenNotifPromoter().invalidateList("unseen setting changed");
            }
            lockScreenMinimalismCoordinatorLogger = this.this$0.logger;
            lockScreenMinimalismCoordinatorLogger.logTrackingUnseen(z2);
            if (z2) {
                LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator = this.this$0;
                this.label = 1;
                trackSeenNotifications = lockScreenMinimalismCoordinator.trackSeenNotifications(this);
                if (trackSeenNotifications == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    public final Object invoke(boolean z, Continuation continuation) {
        return ((LockScreenMinimalismCoordinator$trackLockScreenNotificationMinimalismSettingChanges$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
