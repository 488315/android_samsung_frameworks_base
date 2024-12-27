package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardCoordinator$trackUnseenFilterSettingChanges$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$trackUnseenFilterSettingChanges$2(KeyguardCoordinator keyguardCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardCoordinator$trackUnseenFilterSettingChanges$2 keyguardCoordinator$trackUnseenFilterSettingChanges$2 = new KeyguardCoordinator$trackUnseenFilterSettingChanges$2(this.this$0, continuation);
        keyguardCoordinator$trackUnseenFilterSettingChanges$2.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardCoordinator$trackUnseenFilterSettingChanges$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        Object trackSeenNotifications;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z2 = this.Z$0;
            z = this.this$0.unseenFilterEnabled;
            if (z2 != z) {
                this.this$0.unseenFilterEnabled = z2;
                this.this$0.getUnseenNotifFilter$frameworks__base__packages__SystemUI__android_common__SystemUI_core().invalidateList("unseen setting changed");
            }
            if (z2) {
                KeyguardCoordinator keyguardCoordinator = this.this$0;
                this.label = 1;
                trackSeenNotifications = keyguardCoordinator.trackSeenNotifications(this);
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
        return ((KeyguardCoordinator$trackUnseenFilterSettingChanges$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
