package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.util.kotlin.DisposableHandleExtKt;
import com.android.systemui.util.ui.AnimatedValue;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

final class NotificationListViewBinder$bindFooter$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FooterView $footerView;
    final /* synthetic */ FooterViewModel $footerViewModel;
    final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
    final /* synthetic */ NotificationStackScrollLayout $parentView;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footerView;
        int label;
        final /* synthetic */ NotificationListViewBinder this$0;

        public AnonymousClass1(NotificationListViewBinder notificationListViewBinder, FooterView footerView, Continuation continuation) {
            super(2, continuation);
            this.this$0 = notificationListViewBinder;
            this.$footerView = footerView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$footerView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = (Flow) this.this$0.viewModel.shouldIncludeFooterView$delegate.getValue();
                final FooterView footerView = this.$footerView;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindFooter.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object value;
                        AnimatedValue animatedValue = (AnimatedValue) obj2;
                        boolean z = animatedValue instanceof AnimatedValue.Animating;
                        if (z) {
                            value = ((AnimatedValue.Animating) animatedValue).getValue();
                        } else {
                            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                        }
                        FooterView.this.setVisible(((Boolean) value).booleanValue(), z);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footerView;
        int label;
        final /* synthetic */ NotificationListViewBinder this$0;

        public AnonymousClass2(NotificationListViewBinder notificationListViewBinder, FooterView footerView, Continuation continuation) {
            super(2, continuation);
            this.this$0 = notificationListViewBinder;
            this.$footerView = footerView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$footerView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = (Flow) this.this$0.viewModel.shouldHideFooterView$delegate.getValue();
                final FooterView footerView = this.$footerView;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindFooter.2.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        FooterView.this.getClass();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public NotificationListViewBinder$bindFooter$2(FooterView footerView, FooterViewModel footerViewModel, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
        super(2, continuation);
        this.$footerView = footerView;
        this.$footerViewModel = footerViewModel;
        this.this$0 = notificationListViewBinder;
        this.$parentView = notificationStackScrollLayout;
        this.$hasNonClearableSilentNotifications = stateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationListViewBinder$bindFooter$2 notificationListViewBinder$bindFooter$2 = new NotificationListViewBinder$bindFooter$2(this.$footerView, this.$footerViewModel, this.this$0, this.$parentView, this.$hasNonClearableSilentNotifications, continuation);
        notificationListViewBinder$bindFooter$2.L$0 = obj;
        return notificationListViewBinder$bindFooter$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationListViewBinder$bindFooter$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            FooterViewBinder footerViewBinder = FooterViewBinder.INSTANCE;
            FooterView footerView = this.$footerView;
            FooterViewModel footerViewModel = this.$footerViewModel;
            final NotificationListViewBinder notificationListViewBinder = this.this$0;
            final NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
            final StateFlow stateFlow = this.$hasNonClearableSilentNotifications;
            ?? r5 = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$disposableHandle$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationListViewBinder notificationListViewBinder2 = NotificationListViewBinder.this;
                    NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayout;
                    ((Boolean) stateFlow.getValue()).booleanValue();
                    notificationListViewBinder2.metricsLogger.action(148);
                    notificationStackScrollLayout2.clearNotifications$1(0, true);
                }
            };
            final NotificationListViewBinder notificationListViewBinder2 = this.this$0;
            ?? r6 = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$disposableHandle$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ((StatusBarNotificationActivityStarter) ((NotificationActivityStarter) NotificationListViewBinder.this.notificationActivityStarter.get())).startHistoryIntent(view, false);
                }
            };
            final NotificationListViewBinder notificationListViewBinder3 = this.this$0;
            ?? r7 = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$disposableHandle$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ((StatusBarNotificationActivityStarter) ((NotificationActivityStarter) NotificationListViewBinder.this.notificationActivityStarter.get())).startHistoryIntent(view, true);
                }
            };
            footerViewBinder.getClass();
            RepeatWhenAttachedKt$repeatWhenAttached$1 bindWhileAttached = FooterViewBinder.bindWhileAttached(footerView, footerViewModel, r5, r6, r7);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, this.$footerView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$footerView, null), 3);
            this.label = 1;
            if (DisposableHandleExtKt.awaitCancellationThenDispose(bindWhileAttached, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
