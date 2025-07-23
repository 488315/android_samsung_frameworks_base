package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class LockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ LockScreenMinimalismCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockScreenMinimalismCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2 lockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2 = new LockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2(this.this$0, continuation);
        lockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2.Z$0 = ((Boolean) obj).booleanValue();
        return lockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        long j;
        boolean z;
        LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger;
        LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger2;
        Set set;
        Set set2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z2 = this.Z$0;
            j = LockScreenMinimalismCoordinator.SHADE_VISIBLE_SEEN_TIMEOUT;
            this.Z$0 = z2;
            this.label = 1;
            if (DelayKt.m3449delayVtjQ1oo(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            z = z2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = this.Z$0;
            ResultKt.throwOnFailure(obj);
        }
        this.this$0.isShadeVisible = z;
        if (z) {
            lockScreenMinimalismCoordinatorLogger2 = this.this$0.logger;
            set = this.this$0.unseenNotifications;
            lockScreenMinimalismCoordinatorLogger2.logShadeVisible(set.size());
            set2 = this.this$0.unseenNotifications;
            set2.clear();
        } else {
            lockScreenMinimalismCoordinatorLogger = this.this$0.logger;
            lockScreenMinimalismCoordinatorLogger.logShadeHidden();
        }
        return Unit.INSTANCE;
    }

    public final Object invoke(boolean z, Continuation continuation) {
        return ((LockScreenMinimalismCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
