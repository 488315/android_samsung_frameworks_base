package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.animation.ValueAnimator;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SharedNotificationContainerBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ SharedNotificationContainer $view;
    final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
    final /* synthetic */ ViewStateAccessor $viewState;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SharedNotificationContainerBinder this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SharedNotificationContainer $view;
        final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
        final /* synthetic */ ViewStateAccessor $viewState;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SharedNotificationContainerBinder this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C03211 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainer $view;
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03211(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.$view = sharedNotificationContainer;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03211(this.$viewModel, this.$view, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03211) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.configurationBasedDimensions;
                    final SharedNotificationContainer sharedNotificationContainer = this.$view;
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            SharedNotificationContainerViewModel.ConfigurationBasedDimensions configurationBasedDimensions = (SharedNotificationContainerViewModel.ConfigurationBasedDimensions) obj2;
                            SharedNotificationContainerViewModel.HorizontalPosition horizontalPosition = configurationBasedDimensions.horizontalPosition;
                            float alpha = sharedNotificationContainerBinder.controller.mView.getAlpha();
                            SharedNotificationContainer.this.updateConstraints(horizontalPosition, configurationBasedDimensions.marginStart, configurationBasedDimensions.marginEnd, configurationBasedDimensions.marginBottom, alpha, configurationBasedDimensions.panelWidth, configurationBasedDimensions.transitionX, configurationBasedDimensions.uiDisplayMode);
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
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$11, reason: invalid class name */
        final class AnonymousClass11 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass11(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass11(this.$viewModel, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass11) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.glanceableHubAlpha;
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.11.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            NotificationStackScrollLayoutController notificationStackScrollLayoutController = SharedNotificationContainerBinder.this.controller;
                            notificationStackScrollLayoutController.mMaxAlphaForGlanceableHub = floatValue;
                            notificationStackScrollLayoutController.updateAlpha$1$1();
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
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.this$0, continuation);
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
                    StateFlow stateFlow = this.$viewModel.shadeCollapseFadeIn;
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            if (((Boolean) obj2).booleanValue()) {
                                final SharedNotificationContainerBinder sharedNotificationContainerBinder2 = SharedNotificationContainerBinder.this;
                                if (sharedNotificationContainerBinder2.controller.mView.getAlpha() != 1.0f) {
                                    ValueAnimator ofFloat = ValueAnimator.ofFloat(sharedNotificationContainerBinder2.controller.mView.getAlpha(), 1.0f);
                                    ofFloat.setDuration(250L);
                                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$2$1$1$1
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            NotificationStackScrollLayoutController notificationStackScrollLayoutController = SharedNotificationContainerBinder.this.controller;
                                            notificationStackScrollLayoutController.mMaxAlphaForKeyguard = valueAnimator.getAnimatedFraction();
                                            notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "SharedNotificationContainerVB (collapseFadeIn)";
                                            notificationStackScrollLayoutController.updateAlpha$1$1();
                                        }
                                    });
                                    ofFloat.start();
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow lockscreenDisplayConfig = this.$viewModel.getLockscreenDisplayConfig(this.this$0.calculateMaxNotifications);
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            SharedNotificationContainerViewModel.LockscreenDisplayConfig lockscreenDisplayConfig2 = (SharedNotificationContainerViewModel.LockscreenDisplayConfig) obj2;
                            boolean z = lockscreenDisplayConfig2.isOnLockscreen;
                            SharedNotificationContainerBinder.this.controller.setMaxDisplayedNotifications(lockscreenDisplayConfig2.maxNotifications);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (lockscreenDisplayConfig.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.translationY;
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            SharedNotificationContainerBinder.this.controller.mView.setTranslationY(((Number) obj2).floatValue());
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
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, this.this$0, continuation);
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
                    Flow flow = this.$viewModel.translationX;
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            SharedNotificationContainerBinder.this.controller.mView.setTranslationX(((Number) obj2).floatValue());
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
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            final /* synthetic */ ViewStateAccessor $viewState;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, ViewStateAccessor viewStateAccessor, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.$viewState = viewStateAccessor;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass8 anonymousClass8 = new AnonymousClass8(this.$viewModel, this.$viewState, this.this$0, continuation);
                anonymousClass8.L$0 = obj;
                return anonymousClass8;
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
                    Flow keyguardAlpha = this.$viewModel.keyguardAlpha(this.$viewState, (CoroutineScope) this.L$0);
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.8.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            NotificationStackScrollLayoutController notificationStackScrollLayoutController = SharedNotificationContainerBinder.this.controller;
                            notificationStackScrollLayoutController.mMaxAlphaForKeyguard = floatValue;
                            notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "SharedNotificationContainerVB";
                            notificationStackScrollLayoutController.updateAlpha$1$1();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (keyguardAlpha.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.$viewModel, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.panelAlpha;
                    final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.9.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            NotificationStackScrollLayoutController notificationStackScrollLayoutController = SharedNotificationContainerBinder.this.controller;
                            notificationStackScrollLayoutController.mMaxAlphaFromView = floatValue;
                            notificationStackScrollLayoutController.updateAlpha$1$1();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SharedNotificationContainerBinder sharedNotificationContainerBinder, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, ViewStateAccessor viewStateAccessor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = sharedNotificationContainerBinder;
            this.$viewModel = sharedNotificationContainerViewModel;
            this.$view = sharedNotificationContainer;
            this.$viewState = viewStateAccessor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$viewModel, this.$view, this.$viewState, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03211(this.$viewModel, this.$view, this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.$viewModel, this.$viewState, this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.this$0, null), 7);
            if (this.this$0.communalSettingsInteractor.isCommunalFlagEnabled()) {
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.this$0, null), 7);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerBinder$bind$2(SharedNotificationContainerBinder sharedNotificationContainerBinder, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, ViewStateAccessor viewStateAccessor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = sharedNotificationContainerBinder;
        this.$viewModel = sharedNotificationContainerViewModel;
        this.$view = sharedNotificationContainer;
        this.$viewState = viewStateAccessor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SharedNotificationContainerBinder$bind$2 sharedNotificationContainerBinder$bind$2 = new SharedNotificationContainerBinder$bind$2(this.this$0, this.$viewModel, this.$view, this.$viewState, (Continuation) obj3);
        sharedNotificationContainerBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return sharedNotificationContainerBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$viewModel, this.$view, this.$viewState, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
