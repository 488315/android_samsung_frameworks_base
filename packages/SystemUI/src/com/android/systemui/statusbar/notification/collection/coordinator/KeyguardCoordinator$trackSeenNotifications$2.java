package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardCoordinator$trackSeenNotifications$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<NotificationEntry> $notificationsSeenWhileLocked;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$trackSeenNotifications$2(KeyguardCoordinator keyguardCoordinator, Set<NotificationEntry> set, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
        this.$notificationsSeenWhileLocked = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardCoordinator$trackSeenNotifications$2 keyguardCoordinator$trackSeenNotifications$2 = new KeyguardCoordinator$trackSeenNotifications$2(this.this$0, this.$notificationsSeenWhileLocked, continuation);
        keyguardCoordinator$trackSeenNotifications$2.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardCoordinator$trackSeenNotifications$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object trackSeenNotificationsWhileUnlocked;
        Set set;
        KeyguardCoordinatorLogger keyguardCoordinatorLogger;
        Set set2;
        Object trackSeenNotificationsWhileLocked;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.Z$0) {
                KeyguardCoordinator keyguardCoordinator = this.this$0;
                Set<NotificationEntry> set3 = this.$notificationsSeenWhileLocked;
                this.label = 1;
                trackSeenNotificationsWhileLocked = keyguardCoordinator.trackSeenNotificationsWhileLocked(set3, this);
                if (trackSeenNotificationsWhileLocked == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (!this.$notificationsSeenWhileLocked.isEmpty()) {
                    set = this.this$0.unseenNotifications;
                    set.removeAll(this.$notificationsSeenWhileLocked);
                    keyguardCoordinatorLogger = this.this$0.logger;
                    int size = this.$notificationsSeenWhileLocked.size();
                    set2 = this.this$0.unseenNotifications;
                    keyguardCoordinatorLogger.logAllMarkedSeenOnUnlock(size, set2.size());
                    this.$notificationsSeenWhileLocked.clear();
                }
                this.this$0.getUnseenNotifFilter$frameworks__base__packages__SystemUI__android_common__SystemUI_core().invalidateList("keyguard no longer showing");
                KeyguardCoordinator keyguardCoordinator2 = this.this$0;
                this.label = 2;
                trackSeenNotificationsWhileUnlocked = keyguardCoordinator2.trackSeenNotificationsWhileUnlocked(this);
                if (trackSeenNotificationsWhileUnlocked == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    public final Object invoke(boolean z, Continuation continuation) {
        return ((KeyguardCoordinator$trackSeenNotifications$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
