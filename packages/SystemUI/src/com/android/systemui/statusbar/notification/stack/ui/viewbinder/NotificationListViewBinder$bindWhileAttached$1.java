package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import java.util.WeakHashMap;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

final class NotificationListViewBinder$bindWhileAttached$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ NotificationShelf $shelf;
    final /* synthetic */ NotificationStackScrollLayout $view;
    final /* synthetic */ NotificationStackScrollLayoutController $viewController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
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
                    notificationShelfViewBinder.getClass();
                    Object bind = NotificationShelfViewBinder.bind(notificationShelfViewModel, notificationShelf, notificationListViewBinder.falsingManager, notificationListViewBinder.iconAreaController, notificationListViewBinder.nicBinder, this);
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

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
            final /* synthetic */ NotificationStackScrollLayout $view;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
                this.$hasNonClearableSilentNotifications = stateFlow;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, this.$view, this.$hasNonClearableSilentNotifications, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:13:0x006b, code lost:
            
                if (r12 == r0) goto L14;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r13) {
                /*
                    r12 = this;
                    kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r1 = r12.label
                    r2 = 1
                    if (r1 == 0) goto L15
                    if (r1 != r2) goto Ld
                    kotlin.ResultKt.throwOnFailure(r13)
                    goto L73
                Ld:
                    java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                    java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                    r12.<init>(r13)
                    throw r12
                L15:
                    kotlin.ResultKt.throwOnFailure(r13)
                    com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r3 = r12.this$0
                    com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r13 = r12.$view
                    kotlinx.coroutines.flow.StateFlow r10 = r12.$hasNonClearableSilentNotifications
                    r12.label = r2
                    com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel r1 = r3.viewModel
                    java.util.Optional r1 = r1.footer
                    r2 = 0
                    java.lang.Object r1 = r1.orElse(r2)
                    r11 = r1
                    com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel r11 = (com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel) r11
                    if (r11 == 0) goto L6e
                    com.android.systemui.common.ui.ConfigurationState r6 = r3.configuration
                    com.android.systemui.statusbar.policy.ConfigurationController r1 = r6.configurationController
                    kotlinx.coroutines.flow.Flow r4 = com.android.systemui.statusbar.policy.ConfigurationControllerExtKt.getOnThemeChanged(r1)
                    kotlinx.coroutines.flow.Flow r1 = com.android.systemui.statusbar.policy.ConfigurationControllerExtKt.getOnDensityOrFontScaleChanged(r1)
                    kotlinx.coroutines.flow.Flow[] r1 = new kotlinx.coroutines.flow.Flow[]{r4, r1}
                    kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge r1 = kotlinx.coroutines.flow.FlowKt.merge(r1)
                    com.android.systemui.util.kotlin.FlowKt$emitOnStart$1 r4 = new com.android.systemui.util.kotlin.FlowKt$emitOnStart$1
                    r4.<init>(r2)
                    kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r5 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
                    r5.<init>(r4, r1)
                    com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1 r1 = new com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1
                    r7 = 2131559509(0x7f0d0455, float:1.8744364E38)
                    r9 = 0
                    r4 = r1
                    r8 = r13
                    r4.<init>()
                    kotlinx.coroutines.CoroutineDispatcher r2 = r3.backgroundDispatcher
                    kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.flowOn(r1, r2)
                    com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$reinflateAndBindFooter$2$1 r8 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$reinflateAndBindFooter$2$1
                    r6 = 0
                    r1 = r8
                    r2 = r13
                    r4 = r11
                    r5 = r10
                    r1.<init>(r2, r3, r4, r5, r6)
                    java.lang.Object r12 = kotlinx.coroutines.flow.FlowKt.collectLatest(r7, r8, r12)
                    if (r12 != r0) goto L6e
                    goto L70
                L6e:
                    kotlin.Unit r12 = kotlin.Unit.INSTANCE
                L70:
                    if (r12 != r0) goto L73
                    return r0
                L73:
                    kotlin.Unit r12 = kotlin.Unit.INSTANCE
                    return r12
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1.AnonymousClass1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $view;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.this$0, this.$view, continuation);
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
                    final NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                    this.label = 1;
                    NotificationListViewModel notificationListViewModel = notificationListViewBinder.viewModel;
                    Object collect = FlowKt.combine((Flow) notificationListViewModel.shouldShowEmptyShadeView$delegate.getValue(), (Flow) notificationListViewModel.areNotificationsHiddenInShade$delegate.getValue(), (Flow) notificationListViewModel.hasFilteredOutSeenNotifications$delegate.getValue(), NotificationListViewBinder$bindEmptyShade$3.INSTANCE).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindEmptyShade$4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Triple triple = (Triple) obj2;
                            NotificationStackScrollLayout.this.updateEmptyShadeView(((Boolean) triple.component1()).booleanValue(), ((Boolean) triple.component2()).booleanValue(), ((Boolean) triple.component3()).booleanValue());
                            return Unit.INSTANCE;
                        }
                    }, this);
                    if (collect != coroutineSingletons) {
                        collect = Unit.INSTANCE;
                    }
                    if (collect == coroutineSingletons) {
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
                    Flow flow = (Flow) this.this$0.viewModel.isImportantForAccessibility$delegate.getValue();
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

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $view;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.this$0, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    NotificationListViewBinder notificationListViewBinder = this.this$0;
                    this.label = 1;
                    if (NotificationListViewBinder.access$bindLogger(notificationListViewBinder, this) == coroutineSingletons) {
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
        public AnonymousClass1(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, NotificationShelf notificationShelf, Continuation continuation) {
            super(2, continuation);
            this.$viewController = notificationStackScrollLayoutController;
            this.this$0 = notificationListViewBinder;
            this.$view = notificationStackScrollLayout;
            this.$shelf = notificationShelf;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewController, this.this$0, this.$view, this.$shelf, continuation);
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
                coroutineScope = (CoroutineScope) this.L$0;
                Flags.notificationsHeadsUpRefactor();
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$shelf, null), 3);
                HideNotificationsBinder hideNotificationsBinder = HideNotificationsBinder.INSTANCE;
                final NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.$viewController;
                NotificationListViewBinder notificationListViewBinder = this.this$0;
                NotificationListViewModel notificationListViewModel = notificationListViewBinder.viewModel;
                hideNotificationsBinder.getClass();
                final NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
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
                Flags.FEATURE_FLAGS.getClass();
                EmptyFlow emptyFlow = EmptyFlow.INSTANCE;
                SharingStarted.Companion.getClass();
                ReadonlySharedFlow shareIn = FlowKt.shareIn(emptyFlow, coroutineScope, SharingStarted.Companion.Lazily, 0);
                BuildersKt.launch$default(coroutineScope, null, null, new HideNotificationsBinder$bindHideList$2(shareIn, notificationStackScrollLayoutController, null), 3);
                DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker = notificationListViewBinder.hiderTracker;
                BuildersKt.launch$default(coroutineScope, null, null, new HideNotificationsBinder$bindHideList$3(displaySwitchNotificationsHiderTracker, shareIn, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new HideNotificationsBinder$bindHideList$4(displaySwitchNotificationsHiderTracker, shareIn, null), 3);
                Flags.notificationsFooterViewRefactor();
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                coroutineScope = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlow stateFlow = (StateFlow) obj;
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, this.$view, stateFlow, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, this.$view, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.this$0, this.$view, stateFlow, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.this$0, this.$view, null), 3);
            }
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(this.this$0, this.$view, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationListViewBinder$bindWhileAttached$1(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, NotificationShelf notificationShelf, Continuation continuation) {
        super(3, continuation);
        this.$viewController = notificationStackScrollLayoutController;
        this.this$0 = notificationListViewBinder;
        this.$view = notificationStackScrollLayout;
        this.$shelf = notificationShelf;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationListViewBinder$bindWhileAttached$1 notificationListViewBinder$bindWhileAttached$1 = new NotificationListViewBinder$bindWhileAttached$1(this.$viewController, this.this$0, this.$view, this.$shelf, (Continuation) obj3);
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
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new AnonymousClass1(this.$viewController, this.this$0, this.$view, this.$shelf, null), 3);
        return Unit.INSTANCE;
    }
}
