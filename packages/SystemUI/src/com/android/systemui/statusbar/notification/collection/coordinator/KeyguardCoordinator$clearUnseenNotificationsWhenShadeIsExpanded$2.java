package com.android.systemui.statusbar.notification.collection.coordinator;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.YieldKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2(KeyguardCoordinator keyguardCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2 keyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2 = new KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2(this.this$0, continuation);
        keyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        KeyguardCoordinatorLogger keyguardCoordinatorLogger;
        Set set;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z2 = this.Z$0;
            this.Z$0 = z2;
            this.label = 1;
            if (YieldKt.yield(this) == coroutineSingletons) {
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
        if (z) {
            keyguardCoordinatorLogger = this.this$0.logger;
            keyguardCoordinatorLogger.logShadeExpanded();
            set = this.this$0.unseenNotifications;
            set.clear();
        }
        return Unit.INSTANCE;
    }

    public final Object invoke(boolean z, Continuation continuation) {
        return ((KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
