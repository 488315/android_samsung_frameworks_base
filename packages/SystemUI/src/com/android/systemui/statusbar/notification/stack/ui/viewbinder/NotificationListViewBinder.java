package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableTextView;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.notification.emptyshade.ui.viewmodel.EmptyShadeViewModel;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerShelfViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.util.kotlin.Utils;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class NotificationListViewBinder {
    public final FalsingManager falsingManager;
    public final DisplaySwitchNotificationsHiderTracker hiderTracker;
    public final HeadsUpNotificationViewBinder hunBinder;
    public final NotificationIconAreaController iconAreaController;
    public final Optional loggerOptional;
    public final NotificationIconContainerShelfViewBinder nicBinder;
    public final Provider notificationActivityStarter;
    public final NotificationShelfManager shelfManager;
    public final SectionHeaderController silentHeaderController;
    public final NotificationListViewModel viewModel;

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ Lazy $emptyShadeViewModel$delegate;
        final /* synthetic */ Lazy $footerViewModel$delegate;
        final /* synthetic */ NotificationShelf $shelf;
        final /* synthetic */ NotificationStackScrollLayout $view;
        final /* synthetic */ NotificationStackScrollLayoutController $viewController;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ NotificationListViewBinder this$0;

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1, reason: invalid class name and collision with other inner class name */
        final class C05081 extends SuspendLambda implements Function2 {
            final /* synthetic */ Lazy $emptyShadeViewModel$delegate;
            final /* synthetic */ Lazy $footerViewModel$delegate;
            final /* synthetic */ NotificationShelf $shelf;
            final /* synthetic */ NotificationStackScrollLayout $view;
            final /* synthetic */ NotificationStackScrollLayoutController $viewController;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

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
                        Object objBind = NotificationShelfViewBinder.bind(notificationShelfViewModel, notificationShelf, falsingManager, notificationListViewBinder.shelfManager, notificationActivityStarter, notificationListViewBinder.iconAreaController, notificationIconContainerShelfViewBinder, this);
                        if (objBind != obj2) {
                            objBind = Unit.INSTANCE;
                        }
                        if (objBind == obj2) {
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
                        Object objCollect = FlowKt.combine((Flow) notificationListViewBinder.viewModel.shouldShowEmptyShadeView$delegate.getValue(), (Flow) emptyShadeViewModel.areNotificationsHiddenInShade$delegate.getValue(), emptyShadeViewModel.hasFilteredOutSeenNotifications, NotificationListViewBinder$bindEmptyShadeLegacy$4.INSTANCE).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindEmptyShadeLegacy$5
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Triple triple = (Triple) obj2;
                                boolean zBooleanValue = ((Boolean) triple.component1()).booleanValue();
                                boolean zBooleanValue2 = ((Boolean) triple.component2()).booleanValue();
                                boolean zBooleanValue3 = ((Boolean) triple.component3()).booleanValue();
                                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayout;
                                notificationStackScrollLayout2.getClass();
                                if (!NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
                                    int i3 = ModesEmptyShadeFix.$r8$clinit;
                                    RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                                    notificationStackScrollLayout2.mEmptyShadeView.setVisible(zBooleanValue, notificationStackScrollLayout2.mIsExpanded && notificationStackScrollLayout2.mAnimationsEnabled);
                                    if (zBooleanValue2) {
                                        notificationStackScrollLayout2.updateEmptyShadeViewResources(R.string.dnd_suppressing_shade_text, 0, 0);
                                    } else if (zBooleanValue3) {
                                        notificationStackScrollLayout2.updateEmptyShadeViewResources(R.string.no_unseen_notif_text, R.string.unlock_to_see_notif_text, R.drawable.ic_friction_lock_closed);
                                    } else {
                                        notificationStackScrollLayout2.updateEmptyShadeViewResources(R.string.empty_shade_text, 0, 0);
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, this);
                        if (objCollect != coroutineSingletons) {
                            objCollect = Unit.INSTANCE;
                        }
                        if (objCollect == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1 && i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

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
                        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new NotificationListViewBinder$bindSilentHeaderClickListener$2(notificationListViewBinder, notificationStackScrollLayout, stateFlow, null), this);
                        if (objCoroutineScope != obj2) {
                            objCoroutineScope = Unit.INSTANCE;
                        }
                        if (objCoroutineScope == obj2) {
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
                                notificationStackScrollLayout.setImportantForAccessibility(((Boolean) obj2).booleanValue() ? 1 : 2);
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
            public C05081(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, NotificationShelf notificationShelf, Lazy lazy, Lazy lazy2, Continuation continuation) {
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
                C05081 c05081 = new C05081(this.$viewController, this.this$0, this.$view, this.$shelf, this.$footerViewModel$delegate, this.$emptyShadeViewModel$delegate, continuation);
                c05081.L$0 = obj;
                return c05081;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05081) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    ReadonlySharedFlow readonlySharedFlowShareIn = FlowKt.shareIn(emptyFlow, coroutineScope2, SharingStarted.Companion.Lazily, 0);
                    CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new HideNotificationsBinder$bindHideList$2(readonlySharedFlowShareIn, notificationStackScrollLayoutController, null), 7);
                    DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker = notificationListViewBinder.hiderTracker;
                    CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new HideNotificationsBinder$bindHideList$3(displaySwitchNotificationsHiderTracker, readonlySharedFlowShareIn, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope2, null, null, new HideNotificationsBinder$bindHideList$4(displaySwitchNotificationsHiderTracker, readonlySharedFlowShareIn, null), 7);
                    Flow flow = this.this$0.viewModel.hasNonClearableSilentNotifications;
                    this.L$0 = coroutineScope2;
                    this.label = 1;
                    Object objStateIn = FlowKt.stateIn(flow, coroutineScope2, this);
                    if (objStateIn == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    coroutineScope = coroutineScope2;
                    obj = objStateIn;
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
        public AnonymousClass1(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, NotificationShelf notificationShelf, Lazy lazy, Lazy lazy2, Continuation continuation) {
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewController, this.this$0, this.$view, this.$shelf, this.$footerViewModel$delegate, this.$emptyShadeViewModel$delegate, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new C05081(this.$viewController, this.this$0, this.$view, this.$shelf, this.$footerViewModel$delegate, this.$emptyShadeViewModel$delegate, null), 7);
            return Unit.INSTANCE;
        }
    }

    public NotificationListViewBinder(CoroutineDispatcher coroutineDispatcher, DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker, ConfigurationState configurationState, FalsingManager falsingManager, HeadsUpNotificationViewBinder headsUpNotificationViewBinder, NotificationIconAreaController notificationIconAreaController, Optional<NotificationStatsLogger> optional, MetricsLogger metricsLogger, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Provider provider, SectionHeaderController sectionHeaderController, NotificationListViewModel notificationListViewModel, NotificationShelfManager notificationShelfManager) {
        this.hiderTracker = displaySwitchNotificationsHiderTracker;
        this.falsingManager = falsingManager;
        this.hunBinder = headsUpNotificationViewBinder;
        this.iconAreaController = notificationIconAreaController;
        this.loggerOptional = optional;
        this.nicBinder = notificationIconContainerShelfViewBinder;
        this.notificationActivityStarter = provider;
        this.silentHeaderController = sectionHeaderController;
        this.viewModel = notificationListViewModel;
        this.shelfManager = notificationShelfManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$bindLogger(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, ContinuationImpl continuationImpl) {
        NotificationListViewBinder$bindLogger$1 notificationListViewBinder$bindLogger$1;
        NotificationStatsLogger notificationStatsLogger;
        notificationListViewBinder.getClass();
        if (continuationImpl instanceof NotificationListViewBinder$bindLogger$1) {
            notificationListViewBinder$bindLogger$1 = (NotificationListViewBinder$bindLogger$1) continuationImpl;
            int i = notificationListViewBinder$bindLogger$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                notificationListViewBinder$bindLogger$1.label = i - Integer.MIN_VALUE;
            } else {
                notificationListViewBinder$bindLogger$1 = new NotificationListViewBinder$bindLogger$1(notificationListViewBinder, continuationImpl);
            }
        }
        Object obj = notificationListViewBinder$bindLogger$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = notificationListViewBinder$bindLogger$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationLoggerViewModel notificationLoggerViewModel = (NotificationLoggerViewModel) notificationListViewBinder.viewModel.logger.orElse(null);
            if (notificationLoggerViewModel != null && (notificationStatsLogger = (NotificationStatsLogger) notificationListViewBinder.loggerOptional.orElse(null)) != null) {
                NotificationStatsLoggerBinder notificationStatsLoggerBinder = NotificationStatsLoggerBinder.INSTANCE;
                notificationListViewBinder$bindLogger$1.label = 1;
                notificationStatsLoggerBinder.getClass();
                Object objCollectLatest = FlowKt.collectLatest(com.android.systemui.util.kotlin.FlowKt.sample(notificationLoggerViewModel.isLockscreenOrShadeInteractive, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(notificationLoggerViewModel.isOnLockScreen, notificationLoggerViewModel.activeNotifications, NotificationStatsLoggerBinder$bindLogger$4.INSTANCE), new NotificationStatsLoggerBinder$bindLogger$5(Utils.Companion)), new NotificationStatsLoggerBinder$bindLogger$6(notificationStatsLogger, notificationStackScrollLayout, notificationLoggerViewModel, null), notificationListViewBinder$bindLogger$1);
                if (objCollectLatest != obj2) {
                    objCollectLatest = Unit.INSTANCE;
                }
                if (objCollectLatest == obj2) {
                    return obj2;
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    public final void bindWhileAttached(NotificationStackScrollLayout notificationStackScrollLayout, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        int iIndexOfChild;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(notificationStackScrollLayout.getContext());
        this.shelfManager.getClass();
        final NotificationShelf notificationShelf = (NotificationShelf) layoutInflaterFrom.inflate(R.layout.sec_status_bar_notification_shelf, (ViewGroup) notificationStackScrollLayout, false);
        View view = notificationStackScrollLayout.mShelf;
        if (view != null) {
            iIndexOfChild = notificationStackScrollLayout.indexOfChild(view);
            notificationStackScrollLayout.removeView(notificationStackScrollLayout.mShelf);
        } else {
            iIndexOfChild = -1;
        }
        notificationStackScrollLayout.mShelf = notificationShelf;
        notificationStackScrollLayout.addView(notificationShelf, iIndexOfChild);
        notificationStackScrollLayout.mAmbientState.mShelf = notificationStackScrollLayout.mShelf;
        notificationStackScrollLayout.mStateAnimator.getClass();
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        NotificationRoundnessManager notificationRoundnessManager = notificationStackScrollLayout.mController.mNotificationRoundnessManager;
        notificationShelf.mAmbientState = ambientState;
        ambientState.mKeyguardNotiExpandListeners.add(notificationShelf);
        notificationShelf.mHostLayout = notificationStackScrollLayout;
        notificationShelf.mRoundnessManager = notificationRoundnessManager;
        NotificationShelfManager notificationShelfManager = (NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class);
        notificationShelf.mShelfManager = notificationShelfManager;
        notificationShelfManager.shelf = notificationShelf;
        StatusBarStateController statusBarStateController = notificationShelfManager.statusBarStateController;
        statusBarStateController.addCallback(notificationShelf);
        notificationShelfManager.statusBarState = statusBarStateController.getState();
        LaunchableTextView launchableTextView = (LaunchableTextView) notificationShelf.findViewById(R.id.noti_setting);
        notificationShelfManager.mSettingButton = launchableTextView;
        if (launchableTextView != null) {
            launchableTextView.setSelected(true);
        }
        LaunchableTextView launchableTextView2 = (LaunchableTextView) notificationShelf.findViewById(R.id.clear_all);
        notificationShelfManager.mClearAllButton = launchableTextView2;
        if (launchableTextView2 != null) {
            launchableTextView2.setSelected(true);
        }
        notificationShelfManager.mShelfTextArea = (LinearLayout) notificationShelf.findViewById(R.id.notification_shelf_text_area);
        notificationShelfManager.mNotificationIconContainer = (NotificationIconContainer) notificationShelf.findViewById(R.id.content);
        notificationShelfManager.updateResources();
        notificationShelfManager.statusBarState = statusBarStateController.getState();
        notificationShelfManager.updateShelfLayout();
        notificationShelfManager.updateShelfTextArea();
        LaunchableTextView launchableTextView3 = notificationShelfManager.mSettingButton;
        if (launchableTextView3 != null) {
            launchableTextView3.setVisibility(4);
        }
        LaunchableTextView launchableTextView4 = notificationShelfManager.mClearAllButton;
        if (launchableTextView4 != null) {
            launchableTextView4.setVisibility(4);
        }
        LinearLayout linearLayout = notificationShelfManager.mShelfTextArea;
        if (linearLayout != null) {
            linearLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.NotificationShelfManager$shelf$1$1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    notificationShelf.updateIconsPaddingEnd();
                }
            });
        }
        notificationShelf.updateResources$3();
        ContrastColorUtil.getInstance(notificationShelf.getContext());
        NotificationShelfManager notificationShelfManager2 = notificationStackScrollLayout.mShelfManager;
        NotificationStackScrollLayout$$ExternalSyntheticLambda1 notificationStackScrollLayout$$ExternalSyntheticLambda1 = new NotificationStackScrollLayout$$ExternalSyntheticLambda1(notificationStackScrollLayout, 1);
        LaunchableTextView launchableTextView5 = notificationShelfManager2.mClearAllButton;
        if (launchableTextView5 != null) {
            launchableTextView5.setOnClickListener(notificationStackScrollLayout$$ExternalSyntheticLambda1);
        }
        notificationStackScrollLayout.mShelfManager.updateAccessibility();
        final int i = 0;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationListViewBinder f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return this.f$0.viewModel.footerViewModelFactory.create();
                    default:
                        return this.f$0.viewModel.emptyShadeViewModelFactory.create();
                }
            }
        });
        final int i2 = 1;
        RepeatWhenAttachedKt.repeatWhenAttached(notificationStackScrollLayout, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(notificationStackScrollLayoutController, this, notificationStackScrollLayout, notificationShelf, lazy, LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationListViewBinder f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return this.f$0.viewModel.footerViewModelFactory.create();
                    default:
                        return this.f$0.viewModel.emptyShadeViewModelFactory.create();
                }
            }
        }), null));
    }
}
