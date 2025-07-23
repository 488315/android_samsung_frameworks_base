package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.notification.emptyshade.ui.viewmodel.EmptyShadeViewModel;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerShelfViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import kotlin.Lazy;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationListViewBinder$bindWhileAttached$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Lazy $emptyShadeViewModel$delegate;
    final /* synthetic */ Lazy $footerViewModel$delegate;
    final /* synthetic */ NotificationShelf $shelf;
    final /* synthetic */ NotificationStackScrollLayout $view;
    final /* synthetic */ NotificationStackScrollLayoutController $viewController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Lazy $emptyShadeViewModel$delegate;
        final /* synthetic */ Lazy $footerViewModel$delegate;
        final /* synthetic */ NotificationShelf $shelf;
        final /* synthetic */ NotificationStackScrollLayout $view;
        final /* synthetic */ NotificationStackScrollLayoutController $viewController;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ NotificationListViewBinder this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationShelf $shelf;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(NotificationListViewBinder notificationListViewBinder, NotificationShelf notificationShelf, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$shelf = notificationShelf;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, this.$shelf, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    NotificationListViewBinder notificationListViewBinder = this.this$0;
                    NotificationShelf notificationShelf = this.$shelf;
                    this.label = 1;
                    notificationListViewBinder.getClass();
                    NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                    NotificationShelfViewModel notificationShelfViewModel = notificationListViewBinder.viewModel.shelf;
                    NotificationActivityStarter notificationActivityStarter = (NotificationActivityStarter) notificationListViewBinder.notificationActivityStarter.get();
                    notificationShelfViewBinder.getClass();
                    FalsingManager falsingManager = notificationListViewBinder.falsingManager;
                    NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder = notificationListViewBinder.nicBinder;
                    Object bind = NotificationShelfViewBinder.bind(notificationShelfViewModel, notificationShelf, falsingManager, notificationListViewBinder.shelfManager, notificationActivityStarter, notificationListViewBinder.iconAreaController, notificationIconContainerShelfViewBinder, this);
                    if (bind != obj2) {
                        bind = Unit.INSTANCE;
                    }
                    if (bind == obj2) {
                        return obj2;
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ Lazy $emptyShadeViewModel$delegate;
            final /* synthetic */ NotificationStackScrollLayout $view;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Lazy lazy, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
                this.$emptyShadeViewModel$delegate = lazy;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.this$0, this.$view, this.$emptyShadeViewModel$delegate, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    NotificationListViewBinder notificationListViewBinder = this.this$0;
                    EmptyShadeViewModel emptyShadeViewModel = (EmptyShadeViewModel) this.$emptyShadeViewModel$delegate.getValue();
                    final NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                    this.label = 2;
                    notificationListViewBinder.getClass();
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    int i2 = ModesEmptyShadeFix.$r8$clinit;
                    Object collect = FlowKt.combine((Flow) notificationListViewBinder.viewModel.shouldShowEmptyShadeView$delegate.getValue(), (Flow) emptyShadeViewModel.areNotificationsHiddenInShade$delegate.getValue(), emptyShadeViewModel.hasFilteredOutSeenNotifications, NotificationListViewBinder$bindEmptyShadeLegacy$4.INSTANCE).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindEmptyShadeLegacy$5
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Triple triple = (Triple) obj2;
                            boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
                            boolean booleanValue2 = ((Boolean) triple.component2()).booleanValue();
                            boolean booleanValue3 = ((Boolean) triple.component3()).booleanValue();
                            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                            notificationStackScrollLayout2.getClass();
                            if (!NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
                                int i3 = ModesEmptyShadeFix.$r8$clinit;
                                RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                                notificationStackScrollLayout2.mEmptyShadeView.setVisible(booleanValue, notificationStackScrollLayout2.mIsExpanded && notificationStackScrollLayout2.mAnimationsEnabled);
                                if (booleanValue2) {
                                    notificationStackScrollLayout2.updateEmptyShadeViewResources(R.string.dnd_suppressing_shade_text, 0, 0);
                                } else if (booleanValue3) {
                                    notificationStackScrollLayout2.updateEmptyShadeViewResources(R.string.no_unseen_notif_text, R.string.unlock_to_see_notif_text, R.drawable.ic_friction_lock_closed);
                                } else {
                                    notificationStackScrollLayout2.updateEmptyShadeViewResources(R.string.empty_shade_text, 0, 0);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, this);
                    if (collect != coroutineSingletons) {
                        collect = Unit.INSTANCE;
                    }
                    if (collect == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
            final /* synthetic */ NotificationStackScrollLayout $view;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
                this.$hasNonClearableSilentNotifications = stateFlow;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.this$0, this.$view, this.$hasNonClearableSilentNotifications, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    NotificationListViewBinder notificationListViewBinder = this.this$0;
                    NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                    StateFlow stateFlow = this.$hasNonClearableSilentNotifications;
                    this.label = 1;
                    notificationListViewBinder.getClass();
                    Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationListViewBinder$bindSilentHeaderClickListener$2(notificationListViewBinder, notificationStackScrollLayout, stateFlow, null), this);
                    if (coroutineScope != obj2) {
                        coroutineScope = Unit.INSTANCE;
                    }
                    if (coroutineScope == obj2) {
                        return obj2;
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $view;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.this$0, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.this$0.viewModel.isImportantForAccessibility;
                    final NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindWhileAttached.1.1.6.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            NotificationStackScrollLayout.this.setImportantForAccessibility(((Boolean) obj2).booleanValue() ? 1 : 2);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $view;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass8(this.this$0, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    NotificationListViewBinder notificationListViewBinder = this.this$0;
                    NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                    this.label = 1;
                    if (NotificationListViewBinder.access$bindLogger(notificationListViewBinder, notificationStackScrollLayout, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, NotificationShelf notificationShelf, Lazy lazy, Lazy lazy2, Continuation continuation) {
            super(2, continuation);
            this.$viewController = notificationStackScrollLayoutController;
            this.this$0 = notificationListViewBinder;
            this.$view = notificationStackScrollLayout;
            this.$shelf = notificationShelf;
            this.$footerViewModel$delegate = lazy;
            this.$emptyShadeViewModel$delegate = lazy2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewController, this.this$0, this.$view, this.$shelf, this.$footerViewModel$delegate, this.$emptyShadeViewModel$delegate, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new AnonymousClass2(this.this$0, this.$shelf, null), 7);
                HideNotificationsBinder hideNotificationsBinder = HideNotificationsBinder.INSTANCE;
                final NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.$viewController;
                NotificationListViewBinder notificationListViewBinder = this.this$0;
                NotificationListViewModel notificationListViewModel = notificationListViewBinder.viewModel;
                hideNotificationsBinder.getClass();
                final NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                if (notificationStackScrollLayout.isAttachedToWindow()) {
                    notificationStackScrollLayout.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.HideNotificationsBinder$bindHideList$$inlined$doOnDetach$1
                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewDetachedFromWindow(View view) {
                            notificationStackScrollLayout.removeOnAttachStateChangeListener(this);
                            HideNotificationsBinder.access$bindHideState(HideNotificationsBinder.INSTANCE, notificationStackScrollLayoutController, false);
                        }

                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewAttachedToWindow(View view) {
                        }
                    });
                } else {
                    HideNotificationsBinder.access$bindHideState(hideNotificationsBinder, notificationStackScrollLayoutController, false);
                }
                notificationListViewModel.hideListViewModel.getClass();
                EmptyFlow emptyFlow = EmptyFlow.INSTANCE;
                SharingStarted.Companion.getClass();
                ReadonlySharedFlow shareIn = FlowKt.shareIn(emptyFlow, coroutineScope2, SharingStarted.Companion.Lazily, 0);
                CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new HideNotificationsBinder$bindHideList$2(shareIn, notificationStackScrollLayoutController, null), 7);
                DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker = notificationListViewBinder.hiderTracker;
                CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new HideNotificationsBinder$bindHideList$3(displaySwitchNotificationsHiderTracker, shareIn, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new HideNotificationsBinder$bindHideList$4(displaySwitchNotificationsHiderTracker, shareIn, null), 7);
                Flow flow = this.this$0.viewModel.hasNonClearableSilentNotifications;
                this.L$0 = coroutineScope2;
                this.label = 1;
                Object stateIn = FlowKt.stateIn(flow, coroutineScope2, this);
                if (stateIn == coroutineSingletons) {
                    return coroutineSingletons;
                }
                coroutineScope = coroutineScope2;
                obj = stateIn;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                coroutineScope = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, this.$view, this.$emptyShadeViewModel$delegate, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.this$0, this.$view, (StateFlow) obj, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.this$0, this.$view, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.this$0, this.$view, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationListViewBinder$bindWhileAttached$1(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, NotificationShelf notificationShelf, Lazy lazy, Lazy lazy2, Continuation continuation) {
        super(3, continuation);
        this.$viewController = notificationStackScrollLayoutController;
        this.this$0 = notificationListViewBinder;
        this.$view = notificationStackScrollLayout;
        this.$shelf = notificationShelf;
        this.$footerViewModel$delegate = lazy;
        this.$emptyShadeViewModel$delegate = lazy2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationListViewBinder$bindWhileAttached$1 notificationListViewBinder$bindWhileAttached$1 = new NotificationListViewBinder$bindWhileAttached$1(this.$viewController, this.this$0, this.$view, this.$shelf, this.$footerViewModel$delegate, this.$emptyShadeViewModel$delegate, (Continuation) obj3);
        notificationListViewBinder$bindWhileAttached$1.L$0 = (LifecycleOwner) obj;
        return notificationListViewBinder$bindWhileAttached$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new AnonymousClass1(this.$viewController, this.this$0, this.$view, this.$shelf, this.$footerViewModel$delegate, this.$emptyShadeViewModel$delegate, null), 7);
        return Unit.INSTANCE;
    }
}
