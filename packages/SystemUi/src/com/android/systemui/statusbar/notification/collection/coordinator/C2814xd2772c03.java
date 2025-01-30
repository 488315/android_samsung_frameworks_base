package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.YieldKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2", m277f = "KeyguardCoordinator.kt", m278l = {195}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$clearUnseenNotificationsWhenShadeIsExpanded$2 */
/* loaded from: classes2.dex */
final class C2814xd2772c03 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2814xd2772c03(KeyguardCoordinator keyguardCoordinator, Continuation<? super C2814xd2772c03> continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        C2814xd2772c03 c2814xd2772c03 = new C2814xd2772c03(this.this$0, continuation);
        c2814xd2772c03.Z$0 = ((Boolean) obj).booleanValue();
        return c2814xd2772c03;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C2814xd2772c03) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
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
            LogBuffer.log$default(this.this$0.logger.buffer, "KeyguardCoordinator", LogLevel.DEBUG, "Notifications have been marked as seen due to shade expansion.", null, 8, null);
            this.this$0.unseenNotifications.clear();
        }
        return Unit.INSTANCE;
    }
}
