package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LockScreenMinimalismCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockScreenMinimalismCoordinator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean invokeSuspend$lambda$1(String str, NotificationEntry notificationEntry) {
        return Intrinsics.areEqual(notificationEntry.mKey, str);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3 lockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3 = new LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3(this.this$0, continuation);
        lockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3.L$0 = obj;
        return lockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Set set;
        LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger;
        long j;
        final String str;
        LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger2;
        Set set2;
        LockScreenMinimalismCoordinatorLogger lockScreenMinimalismCoordinatorLogger3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            String str2 = (String) this.L$0;
            boolean z = false;
            if (str2 == null) {
                lockScreenMinimalismCoordinatorLogger2 = this.this$0.logger;
                lockScreenMinimalismCoordinatorLogger2.logTopHeadsUpRow(null, false);
            } else {
                set = this.this$0.unseenNotifications;
                Set set3 = set;
                if (!(set3 instanceof Collection) || !set3.isEmpty()) {
                    Iterator it = set3.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (Intrinsics.areEqual(((NotificationEntry) it.next()).mKey, str2)) {
                            z = true;
                            break;
                        }
                    }
                }
                lockScreenMinimalismCoordinatorLogger = this.this$0.logger;
                lockScreenMinimalismCoordinatorLogger.logTopHeadsUpRow(str2, z);
                if (z) {
                    j = LockScreenMinimalismCoordinator.HEADS_UP_SEEN_TIMEOUT;
                    this.L$0 = str2;
                    this.label = 1;
                    if (DelayKt.m3449delayVtjQ1oo(j, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    str = str2;
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        str = (String) this.L$0;
        ResultKt.throwOnFailure(obj);
        set2 = this.this$0.unseenNotifications;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                boolean invokeSuspend$lambda$1;
                invokeSuspend$lambda$1 = LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3.invokeSuspend$lambda$1(str, (NotificationEntry) obj2);
                return Boolean.valueOf(invokeSuspend$lambda$1);
            }
        };
        boolean removeIf = set2.removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenMinimalismCoordinator$sam$java_util_function_Predicate$0
            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj2) {
                return ((Boolean) Function1.this.mo779invoke(obj2)).booleanValue();
            }
        });
        lockScreenMinimalismCoordinatorLogger3 = this.this$0.logger;
        lockScreenMinimalismCoordinatorLogger3.logHunHasBeenSeen(str, removeIf);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(String str, Continuation continuation) {
        return ((LockScreenMinimalismCoordinator$markHeadsUpNotificationsAsSeen$3) create(str, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
