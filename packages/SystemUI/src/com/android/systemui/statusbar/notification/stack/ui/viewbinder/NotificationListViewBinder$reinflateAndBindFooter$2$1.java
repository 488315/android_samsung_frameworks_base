package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.app.tracing.TraceProxy_platformKt;
import com.android.app.tracing.TraceUtils;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class NotificationListViewBinder$reinflateAndBindFooter$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FooterViewModel $footerViewModel;
    final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
    final /* synthetic */ NotificationStackScrollLayout $parentView;
    int I$0;
    /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationListViewBinder$reinflateAndBindFooter$2$1(NotificationStackScrollLayout notificationStackScrollLayout, NotificationListViewBinder notificationListViewBinder, FooterViewModel footerViewModel, StateFlow stateFlow, Continuation continuation) {
        super(2, continuation);
        this.$parentView = notificationStackScrollLayout;
        this.this$0 = notificationListViewBinder;
        this.$footerViewModel = footerViewModel;
        this.$hasNonClearableSilentNotifications = stateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationListViewBinder$reinflateAndBindFooter$2$1 notificationListViewBinder$reinflateAndBindFooter$2$1 = new NotificationListViewBinder$reinflateAndBindFooter$2$1(this.$parentView, this.this$0, this.$footerViewModel, this.$hasNonClearableSilentNotifications, continuation);
        notificationListViewBinder$reinflateAndBindFooter$2$1.L$0 = obj;
        return notificationListViewBinder$reinflateAndBindFooter$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationListViewBinder$reinflateAndBindFooter$2$1) create((FooterView) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String str;
        int i;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            FooterView footerView = (FooterView) this.L$0;
            NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
            NotificationListViewBinder notificationListViewBinder = this.this$0;
            FooterViewModel footerViewModel = this.$footerViewModel;
            StateFlow stateFlow = this.$hasNonClearableSilentNotifications;
            int i3 = TraceUtils.$r8$clinit;
            int nextInt = ThreadLocalRandom.current().nextInt();
            str = "AsyncTraces";
            TraceProxy_platformKt.asyncTraceForTrackBegin(nextInt, "AsyncTraces", "bind FooterView");
            try {
                notificationStackScrollLayout.setFooterView(footerView);
                this.L$0 = "bind FooterView";
                this.L$1 = "AsyncTraces";
                this.I$0 = nextInt;
                this.label = 1;
                notificationListViewBinder.getClass();
                Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationListViewBinder$bindFooter$2(footerView, footerViewModel, notificationListViewBinder, notificationStackScrollLayout, stateFlow, null), this);
                if (coroutineScope != obj2) {
                    coroutineScope = Unit.INSTANCE;
                }
                if (coroutineScope == obj2) {
                    return obj2;
                }
                i = nextInt;
            } catch (Throwable th) {
                th = th;
                i = nextInt;
                TraceProxy_platformKt.asyncTraceForTrackEnd(i, str);
                throw th;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = this.I$0;
            str = (String) this.L$1;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th2) {
                th = th2;
                TraceProxy_platformKt.asyncTraceForTrackEnd(i, str);
                throw th;
            }
        }
        Unit unit = Unit.INSTANCE;
        TraceProxy_platformKt.asyncTraceForTrackEnd(i, str);
        return Unit.INSTANCE;
    }
}
