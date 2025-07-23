package com.android.systemui.statusbar.notification.stack.ui.view;

import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationStatsLoggerImpl$maybeLogVisibilityChanges$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Map<String, NotificationStatsLoggerImpl.VisibilityState> $newlyVisible;
    final /* synthetic */ NotificationVisibility[] $newlyVisibleAr;
    final /* synthetic */ NotificationVisibility[] $noLongerVisibleAr;
    int label;
    final /* synthetic */ NotificationStatsLoggerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationStatsLoggerImpl$maybeLogVisibilityChanges$2(NotificationStatsLoggerImpl notificationStatsLoggerImpl, NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2, Map<String, NotificationStatsLoggerImpl.VisibilityState> map, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationStatsLoggerImpl;
        this.$newlyVisibleAr = notificationVisibilityArr;
        this.$noLongerVisibleAr = notificationVisibilityArr2;
        this.$newlyVisible = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationStatsLoggerImpl$maybeLogVisibilityChanges$2(this.this$0, this.$newlyVisibleAr, this.$noLongerVisibleAr, this.$newlyVisible, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationStatsLoggerImpl$maybeLogVisibilityChanges$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.statusBarService.onNotificationVisibilityChanged(this.$newlyVisibleAr, this.$noLongerVisibleAr);
        if (!this.$newlyVisible.isEmpty()) {
            this.this$0.notificationListenerService.setNotificationsShown((String[]) this.$newlyVisible.keySet().toArray(new String[0]));
        }
        return Unit.INSTANCE;
    }
}
