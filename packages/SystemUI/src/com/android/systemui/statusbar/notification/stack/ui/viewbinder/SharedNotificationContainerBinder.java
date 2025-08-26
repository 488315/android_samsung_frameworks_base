package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.animation.ValueAnimator;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.common.ui.view.ViewExtKt$onLayoutChanged$2;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.kotlin.DisposableHandles;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TakeWhileSequence;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class SharedNotificationContainerBinder {
    public final SharedNotificationContainerBinder$$ExternalSyntheticLambda0 calculateMaxNotifications = new Function2() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            int i;
            boolean z;
            final float fFloatValue = ((Float) obj).floatValue();
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            SharedNotificationContainerBinder sharedNotificationContainerBinder = this.f$0;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = sharedNotificationContainerBinder.controller;
            NotificationShelfManager notificationShelfManager = notificationStackScrollLayoutController.mShelfManager;
            int iMax = 0;
            if (notificationShelfManager == null) {
                i = 0;
            } else {
                notificationShelfManager.updateShelfHeightResource(notificationShelfManager.statusBarState);
                i = notificationShelfManager.mShelfTextAreaHeight + notificationShelfManager.mShelfTextAreaPaddingTop + notificationShelfManager.mShelfTextAreaPaddingBottom;
            }
            float f = i;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            final float f2 = zBooleanValue ? f : 0.0f;
            float f3 = fFloatValue + f2;
            final NotificationStackSizeCalculator notificationStackSizeCalculator = sharedNotificationContainerBinder.notificationStackSizeCalculator;
            if (f3 <= 0.0f) {
                notificationStackSizeCalculator.getClass();
            } else {
                SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1ComputeHeightPerNotificationLimit = notificationStackSizeCalculator.computeHeightPerNotificationLimit(notificationStackScrollLayout, f);
                if (notificationStackSizeCalculator.mediaDataManager.hasActiveMediaOrRecommendation()) {
                    ((SplitShadeStateControllerImpl) notificationStackSizeCalculator.splitShadeStateController).shouldUseSplitNotificationShade();
                    z = true;
                } else {
                    z = false;
                }
                final int i2 = 0;
                int iCount = SequencesKt___SequencesKt.count(new TakeWhileSequence(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1ComputeHeightPerNotificationLimit, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        boolean z2 = false;
                        float f4 = f2;
                        float f5 = fFloatValue;
                        NotificationStackSizeCalculator notificationStackSizeCalculator2 = notificationStackSizeCalculator;
                        NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) obj3;
                        switch (i2) {
                            case 0:
                                KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
                                notificationStackSizeCalculator2.getClass();
                                if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f5, f4) == NotificationStackSizeCalculator.FitResult.FIT) {
                                    z2 = true;
                                }
                                return Boolean.valueOf(z2);
                            default:
                                KProperty[] kPropertyArr2 = NotificationStackSizeCalculator.$$delegatedProperties;
                                notificationStackSizeCalculator2.getClass();
                                if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f5, f4) != NotificationStackSizeCalculator.FitResult.NO_FIT) {
                                    z2 = true;
                                }
                                return Boolean.valueOf(z2);
                        }
                    }
                })) - 1;
                if (iCount >= (z ? 2 : 1)) {
                    notificationStackSizeCalculator.saveSpaceOnLockscreen = false;
                } else {
                    notificationStackSizeCalculator.saveSpaceOnLockscreen = true;
                    final int i3 = 1;
                    iCount = SequencesKt___SequencesKt.count(new TakeWhileSequence(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1ComputeHeightPerNotificationLimit, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj3) {
                            boolean z2 = false;
                            float f4 = f2;
                            float f5 = fFloatValue;
                            NotificationStackSizeCalculator notificationStackSizeCalculator2 = notificationStackSizeCalculator;
                            NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) obj3;
                            switch (i3) {
                                case 0:
                                    KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
                                    notificationStackSizeCalculator2.getClass();
                                    if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f5, f4) == NotificationStackSizeCalculator.FitResult.FIT) {
                                        z2 = true;
                                    }
                                    return Boolean.valueOf(z2);
                                default:
                                    KProperty[] kPropertyArr2 = NotificationStackSizeCalculator.$$delegatedProperties;
                                    notificationStackSizeCalculator2.getClass();
                                    if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f5, f4) != NotificationStackSizeCalculator.FitResult.NO_FIT) {
                                        z2 = true;
                                    }
                                    return Boolean.valueOf(z2);
                            }
                        }
                    })) - 1;
                }
                for (ExpandableView expandableView : SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(new TransformingSequence(ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout), new NotificationStackSizeCalculator$$ExternalSyntheticLambda3()), new NotificationStackSizeCalculator$$ExternalSyntheticLambda2(notificationStackSizeCalculator)))) {
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView).mSaveSpaceOnLockscreen = notificationStackSizeCalculator.saveSpaceOnLockscreen;
                    }
                }
                if (notificationStackSizeCalculator.onLockscreen()) {
                    iCount = Math.min(((Number) notificationStackSizeCalculator.maxKeyguardNotifications$delegate.getValue(notificationStackSizeCalculator, NotificationStackSizeCalculator.$$delegatedProperties[0])).intValue(), iCount);
                }
                iMax = Math.max(0, iCount);
            }
            return Integer.valueOf(iMax);
        }
    };
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final NotificationStackScrollLayoutController controller;
    public final KeyguardInteractor keyguardInteractor;
    public final CoroutineDispatcher mainImmediateDispatcher;
    public final NotificationStackSizeCalculator notificationStackSizeCalculator;

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ SharedNotificationContainer $view;
        final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SharedNotificationContainerBinder this$0;

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C05101 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainer $view;
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C05111 extends SuspendLambda implements Function2 {
                final /* synthetic */ SharedNotificationContainer $view;
                final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
                int label;
                final /* synthetic */ SharedNotificationContainerBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05111(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = sharedNotificationContainerViewModel;
                    this.$view = sharedNotificationContainer;
                    this.this$0 = sharedNotificationContainerBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05111(this.$viewModel, this.$view, this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05111) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                SharedNotificationContainerViewModel.ConfigurationBasedDimensions configurationBasedDimensions = (SharedNotificationContainerViewModel.ConfigurationBasedDimensions) obj2;
                                SharedNotificationContainerViewModel.HorizontalPosition horizontalPosition = configurationBasedDimensions.horizontalPosition;
                                SharedNotificationContainerBinder sharedNotificationContainerBinder2 = sharedNotificationContainerBinder;
                                float alpha = sharedNotificationContainerBinder2.controller.mView.getAlpha();
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = sharedNotificationContainerBinder2.controller;
                                int visibility = notificationStackScrollLayoutController.mView.getVisibility();
                                sharedNotificationContainer.updateConstraints(horizontalPosition, configurationBasedDimensions.marginStart, configurationBasedDimensions.marginEnd, configurationBasedDimensions.marginBottom, alpha, visibility, configurationBasedDimensions.panelWidth, configurationBasedDimensions.transitionX, configurationBasedDimensions.uiDisplayMode);
                                notificationStackScrollLayoutController.setOverExpansion(0.0f);
                                notificationStackScrollLayoutController.setOverScrollAmount(0);
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05101(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = sharedNotificationContainerViewModel;
                this.$view = sharedNotificationContainer;
                this.this$0 = sharedNotificationContainerBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C05101 c05101 = new C05101(this.$viewModel, this.$view, this.this$0, continuation);
                c05101.L$0 = obj;
                return c05101;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C05111(this.$viewModel, this.$view, this.this$0, null), 7);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = sharedNotificationContainerViewModel;
            this.$view = sharedNotificationContainer;
            this.this$0 = sharedNotificationContainerBinder;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.this$0, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C05101 c05101 = new C05101(this.$viewModel, this.$view, this.this$0, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c05101, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ SharedNotificationContainer $view;
        final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
        final /* synthetic */ ViewStateAccessor $viewState;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ SharedNotificationContainer $view;
            final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
            final /* synthetic */ ViewStateAccessor $viewState;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SharedNotificationContainerBinder this$0;

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C05131 extends SuspendLambda implements Function2 {
                final /* synthetic */ SharedNotificationContainer $view;
                final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
                int label;
                final /* synthetic */ SharedNotificationContainerBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05131(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = sharedNotificationContainerViewModel;
                    this.$view = sharedNotificationContainer;
                    this.this$0 = sharedNotificationContainerBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05131(this.$viewModel, this.$view, this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05131) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                SharedNotificationContainerBinder sharedNotificationContainerBinder2 = sharedNotificationContainerBinder;
                                float alpha = sharedNotificationContainerBinder2.controller.mView.getAlpha();
                                int visibility = sharedNotificationContainerBinder2.controller.mView.getVisibility();
                                sharedNotificationContainer.updateConstraints(horizontalPosition, configurationBasedDimensions.marginStart, configurationBasedDimensions.marginEnd, configurationBasedDimensions.marginBottom, alpha, visibility, configurationBasedDimensions.panelWidth, configurationBasedDimensions.transitionX, configurationBasedDimensions.uiDisplayMode);
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
                                float fFloatValue = ((Number) obj2).floatValue();
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = sharedNotificationContainerBinder.controller;
                                notificationStackScrollLayoutController.mMaxAlphaForGlanceableHub = fFloatValue;
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

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C05162 extends SuspendLambda implements Function2 {
                final /* synthetic */ SharedNotificationContainerViewModel $viewModel;
                int label;
                final /* synthetic */ SharedNotificationContainerBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05162(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = sharedNotificationContainerViewModel;
                    this.this$0 = sharedNotificationContainerBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05162(this.$viewModel, this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05162) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                    final SharedNotificationContainerBinder sharedNotificationContainerBinder2 = sharedNotificationContainerBinder;
                                    if (sharedNotificationContainerBinder2.controller.mView.getAlpha() != 1.0f) {
                                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(sharedNotificationContainerBinder2.controller.mView.getAlpha(), 1.0f);
                                        valueAnimatorOfFloat.setDuration(250L);
                                        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$2$1$2$1$1$1
                                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = sharedNotificationContainerBinder2.controller;
                                                notificationStackScrollLayoutController.mMaxAlphaForKeyguard = valueAnimator.getAnimatedFraction();
                                                notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "SharedNotificationContainerVB (collapseFadeIn)";
                                                notificationStackScrollLayoutController.updateAlpha$1$1();
                                            }
                                        });
                                        valueAnimatorOfFloat.start();
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
                                sharedNotificationContainerBinder.controller.setMaxDisplayedNotifications(lockscreenDisplayConfig2.maxNotifications);
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
                                sharedNotificationContainerBinder.controller.mView.setTranslationY(((Number) obj2).floatValue());
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
                                float fFloatValue = ((Number) obj2).floatValue();
                                if (!NotiRune.NOTI_STYLE_POP_OVER_TRANSLATION_X || !((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                                    sharedNotificationContainerBinder.controller.mView.setTranslationX(fFloatValue);
                                }
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
                        Flow flowKeyguardAlpha = this.$viewModel.keyguardAlpha(this.$viewState, (CoroutineScope) this.L$0);
                        final SharedNotificationContainerBinder sharedNotificationContainerBinder = this.this$0;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.2.1.8.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                float fFloatValue = ((Number) obj2).floatValue();
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = sharedNotificationContainerBinder.controller;
                                notificationStackScrollLayoutController.mMaxAlphaForKeyguard = fFloatValue;
                                notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "SharedNotificationContainerVB";
                                notificationStackScrollLayoutController.updateAlpha$1$1();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowKeyguardAlpha.collect(flowCollector, this) == coroutineSingletons) {
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
                                float fFloatValue = ((Number) obj2).floatValue();
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = sharedNotificationContainerBinder.controller;
                                notificationStackScrollLayoutController.mMaxAlphaFromView = fFloatValue;
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C05131(this.$viewModel, this.$view, this.this$0, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C05162(this.$viewModel, this.this$0, null), 7);
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
        public AnonymousClass2(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainer sharedNotificationContainer, ViewStateAccessor viewStateAccessor, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = sharedNotificationContainerViewModel;
            this.$view = sharedNotificationContainer;
            this.$viewState = viewStateAccessor;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = SharedNotificationContainerBinder.this.new AnonymousClass2(this.$viewModel, this.$view, this.$viewState, (Continuation) obj3);
            anonymousClass2.L$0 = (LifecycleOwner) obj;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(SharedNotificationContainerBinder.this, this.$viewModel, this.$view, this.$viewState, null);
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

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public final /* synthetic */ SharedNotificationContainerViewModel $viewModel;

        public AnonymousClass3(SharedNotificationContainerViewModel sharedNotificationContainerViewModel) {
            this.$viewModel = sharedNotificationContainerViewModel;
        }

        @Override // java.lang.Runnable
        public final void run() {
            StateFlowImpl stateFlowImpl = this.$viewModel.interactor._notificationStackChanged;
            LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda0] */
    public SharedNotificationContainerBinder(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationScrollViewBinder notificationScrollViewBinder, CommunalSettingsInteractor communalSettingsInteractor, CoroutineDispatcher coroutineDispatcher, KeyguardInteractor keyguardInteractor) {
        this.controller = notificationStackScrollLayoutController;
        this.notificationStackSizeCalculator = notificationStackSizeCalculator;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.mainImmediateDispatcher = coroutineDispatcher;
        this.keyguardInteractor = keyguardInteractor;
    }

    public final DisposableHandles bind(SharedNotificationContainer sharedNotificationContainer, final SharedNotificationContainerViewModel sharedNotificationContainerViewModel) {
        DisposableHandles disposableHandles = new DisposableHandles();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(sharedNotificationContainerViewModel, sharedNotificationContainer, this, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(sharedNotificationContainer, EmptyCoroutineContext.INSTANCE, anonymousClass1));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(sharedNotificationContainer, this.mainImmediateDispatcher, new AnonymousClass2(sharedNotificationContainerViewModel, sharedNotificationContainer, new ViewStateAccessor(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(this.f$0.controller.mView.getAlpha());
            }
        }, null, null, 6, null), null)));
        this.controller.mView.mOnHeightChangedRunnable = new AnonymousClass3(sharedNotificationContainerViewModel);
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder.bind.4
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                SharedNotificationContainerBinder.this.controller.mView.mOnHeightChangedRunnable = null;
            }
        });
        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                StateFlowImpl stateFlowImpl = sharedNotificationContainerViewModel.interactor._notificationStackChanged;
                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                return Unit.INSTANCE;
            }
        };
        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.common.ui.view.ViewExtKt$onLayoutChanged$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Function1 function12 = function1;
                view.getClass();
                function12.mo781invoke(view);
            }
        };
        sharedNotificationContainer.addOnLayoutChangeListener(onLayoutChangeListener);
        disposableHandles.plusAssign(new ViewExtKt$onLayoutChanged$2(sharedNotificationContainer, onLayoutChangeListener));
        return disposableHandles;
    }
}
