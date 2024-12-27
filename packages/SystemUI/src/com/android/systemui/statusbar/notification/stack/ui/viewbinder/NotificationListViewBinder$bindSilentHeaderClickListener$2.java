package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.View;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationListViewBinder$bindSilentHeaderClickListener$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
    final /* synthetic */ NotificationStackScrollLayout $parentView;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationListViewBinder$bindSilentHeaderClickListener$2(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationListViewBinder;
        this.$parentView = notificationStackScrollLayout;
        this.$hasNonClearableSilentNotifications = stateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationListViewBinder$bindSilentHeaderClickListener$2 notificationListViewBinder$bindSilentHeaderClickListener$2 = new NotificationListViewBinder$bindSilentHeaderClickListener$2(this.this$0, this.$parentView, this.$hasNonClearableSilentNotifications, continuation);
        notificationListViewBinder$bindSilentHeaderClickListener$2.L$0 = obj;
        return notificationListViewBinder$bindSilentHeaderClickListener$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationListViewBinder$bindSilentHeaderClickListener$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Flow flow = (Flow) this.this$0.viewModel.hasClearableAlertingNotifications$delegate.getValue();
                this.label = 1;
                obj = FlowKt.stateIn(flow, coroutineScope, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    throw new KotlinNothingValueException();
                }
                ResultKt.throwOnFailure(obj);
            }
            final StateFlow stateFlow = (StateFlow) obj;
            final NotificationListViewBinder notificationListViewBinder = this.this$0;
            SectionHeaderController sectionHeaderController = notificationListViewBinder.silentHeaderController;
            final NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
            final StateFlow stateFlow2 = this.$hasNonClearableSilentNotifications;
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationListViewBinder notificationListViewBinder2 = NotificationListViewBinder.this;
                    NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayout;
                    boolean z = !((Boolean) stateFlow.getValue()).booleanValue();
                    ((Boolean) stateFlow2.getValue()).booleanValue();
                    notificationListViewBinder2.getClass();
                    notificationStackScrollLayout2.clearNotifications$1(2, z);
                }
            };
            SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl = (SectionHeaderNodeControllerImpl) sectionHeaderController;
            sectionHeaderNodeControllerImpl.clearAllClickListener = onClickListener;
            SectionHeaderView sectionHeaderView = sectionHeaderNodeControllerImpl._view;
            if (sectionHeaderView != null) {
                sectionHeaderView.mOnClearClickListener = onClickListener;
                sectionHeaderView.mClearAllButton.setOnClickListener(onClickListener);
            }
            this.label = 2;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            SectionHeaderController sectionHeaderController2 = this.this$0.silentHeaderController;
            AnonymousClass2 anonymousClass2 = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                }
            };
            SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl2 = (SectionHeaderNodeControllerImpl) sectionHeaderController2;
            sectionHeaderNodeControllerImpl2.clearAllClickListener = anonymousClass2;
            SectionHeaderView sectionHeaderView2 = sectionHeaderNodeControllerImpl2._view;
            if (sectionHeaderView2 != null) {
                sectionHeaderView2.mOnClearClickListener = anonymousClass2;
                sectionHeaderView2.mClearAllButton.setOnClickListener(anonymousClass2);
            }
            throw th;
        }
    }
}
