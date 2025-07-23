package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Space;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.MobileContentDescription$Cellular;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewVisibilityHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileIconBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $activityContainer;
    final /* synthetic */ ImageView $activityIn;
    final /* synthetic */ ImageView $activityOut;
    final /* synthetic */ ConfigurationController $configuration;
    final /* synthetic */ ImageView $dataActivity;
    final /* synthetic */ MutableStateFlow $decorTint;
    final /* synthetic */ StatusBarIconView $dotView;
    final /* synthetic */ MutableStateFlow $iconTint;
    final /* synthetic */ ImageView $iconView;
    final /* synthetic */ Ref$BooleanRef $isCollecting;
    final /* synthetic */ MobileViewLogger $logger;
    final /* synthetic */ SignalDrawable $mobileDrawable;
    final /* synthetic */ ViewGroup $mobileGroupView;
    final /* synthetic */ FrameLayout $networkTypeContainer;
    final /* synthetic */ ImageView $networkTypeView;
    final /* synthetic */ Space $roamingSpace;
    final /* synthetic */ ImageView $roamingView;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ LocationBasedMobileViewModel $viewModel;
    final /* synthetic */ MutableStateFlow $visibilityState;
    final /* synthetic */ ImageView $voiceNoService;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ LocationBasedMobileViewModel $viewModel;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C03441 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C03451 extends SuspendLambda implements Function2 {
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03451(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03451(this.$viewModel, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03451) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        StateFlow isVisible = this.$viewModel.isVisible();
                        final LocationBasedMobileViewModel locationBasedMobileViewModel = this.$viewModel;
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                boolean booleanValue = ((Boolean) obj2).booleanValue();
                                LocationBasedMobileViewModel locationBasedMobileViewModel2 = LocationBasedMobileViewModel.this;
                                VerboseMobileViewLogger verboseMobileViewLogger = locationBasedMobileViewModel2.verboseLogger;
                                if (verboseMobileViewLogger != null) {
                                    ViewGroup viewGroup2 = viewGroup;
                                    int subscriptionId = locationBasedMobileViewModel2.commonImpl.getSubscriptionId();
                                    verboseMobileViewLogger.getClass();
                                    LogLevel logLevel = LogLevel.VERBOSE;
                                    VerboseMobileViewLogger$$ExternalSyntheticLambda0 verboseMobileViewLogger$$ExternalSyntheticLambda0 = new VerboseMobileViewLogger$$ExternalSyntheticLambda0(1);
                                    LogBuffer logBuffer = verboseMobileViewLogger.buffer;
                                    LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$$ExternalSyntheticLambda0, null);
                                    MobileViewLogger.Companion.getClass();
                                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                                    logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(viewGroup2);
                                    logMessageImpl.int1 = subscriptionId;
                                    logMessageImpl.bool1 = booleanValue;
                                    logBuffer.commit(obtain);
                                }
                                viewGroup.setVisibility(booleanValue ? 0 : 8);
                                viewGroup.requestLayout();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (isVisible.collect(flowCollector, this) == coroutineSingletons) {
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
            public C03441(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03441 c03441 = new C03441(this.$viewModel, this.$view, continuation);
                c03441.L$0 = obj;
                return c03441;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03441) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C03451(this.$viewModel, this.$view, null), 7);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LifecycleOwner lifecycleOwner, LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$viewModel = locationBasedMobileViewModel;
            this.$view = viewGroup;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$repeatWhenAttached, this.$viewModel, this.$view, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C03441 c03441 = new C03441(this.$viewModel, this.$view, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c03441, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ View $activityContainer;
        final /* synthetic */ ImageView $activityIn;
        final /* synthetic */ ImageView $activityOut;
        final /* synthetic */ ConfigurationController $configuration;
        final /* synthetic */ ImageView $dataActivity;
        final /* synthetic */ MutableStateFlow $decorTint;
        final /* synthetic */ StatusBarIconView $dotView;
        final /* synthetic */ MutableStateFlow $iconTint;
        final /* synthetic */ ImageView $iconView;
        final /* synthetic */ Ref$BooleanRef $isCollecting;
        final /* synthetic */ MobileViewLogger $logger;
        final /* synthetic */ SignalDrawable $mobileDrawable;
        final /* synthetic */ ViewGroup $mobileGroupView;
        final /* synthetic */ FrameLayout $networkTypeContainer;
        final /* synthetic */ ImageView $networkTypeView;
        final /* synthetic */ Space $roamingSpace;
        final /* synthetic */ ImageView $roamingView;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ LocationBasedMobileViewModel $viewModel;
        final /* synthetic */ MutableStateFlow $visibilityState;
        final /* synthetic */ ImageView $voiceNoService;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $activityContainer;
            final /* synthetic */ ImageView $activityIn;
            final /* synthetic */ ImageView $activityOut;
            final /* synthetic */ ConfigurationController $configuration;
            final /* synthetic */ ImageView $dataActivity;
            final /* synthetic */ MutableStateFlow $decorTint;
            final /* synthetic */ StatusBarIconView $dotView;
            final /* synthetic */ MutableStateFlow $iconTint;
            final /* synthetic */ ImageView $iconView;
            final /* synthetic */ Ref$BooleanRef $isCollecting;
            final /* synthetic */ MobileViewLogger $logger;
            final /* synthetic */ SignalDrawable $mobileDrawable;
            final /* synthetic */ ViewGroup $mobileGroupView;
            final /* synthetic */ FrameLayout $networkTypeContainer;
            final /* synthetic */ ImageView $networkTypeView;
            final /* synthetic */ Space $roamingSpace;
            final /* synthetic */ ImageView $roamingView;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            final /* synthetic */ MutableStateFlow $visibilityState;
            final /* synthetic */ ImageView $voiceNoService;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C03471 extends SuspendLambda implements Function2 {
                final /* synthetic */ StatusBarIconView $dotView;
                final /* synthetic */ ViewGroup $mobileGroupView;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ MutableStateFlow $visibilityState;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03471(MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, ViewGroup viewGroup2, Continuation continuation) {
                    super(2, continuation);
                    this.$visibilityState = mutableStateFlow;
                    this.$mobileGroupView = viewGroup;
                    this.$dotView = statusBarIconView;
                    this.$view = viewGroup2;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03471(this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03471) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        MutableStateFlow mutableStateFlow = this.$visibilityState;
                        final ViewGroup viewGroup = this.$mobileGroupView;
                        final StatusBarIconView statusBarIconView = this.$dotView;
                        final ViewGroup viewGroup2 = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                ModernStatusBarViewVisibilityHelper.Companion companion = ModernStatusBarViewVisibilityHelper.Companion;
                                ViewGroup viewGroup3 = viewGroup;
                                companion.getClass();
                                ModernStatusBarViewVisibilityHelper.Companion.setVisibilityState(viewGroup3, statusBarIconView, intValue);
                                viewGroup2.requestLayout();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$10, reason: invalid class name */
            final class AnonymousClass10 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $activityOut;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass10(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$activityOut = imageView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass10(this.$viewModel, this.$activityOut, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
            }

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$11, reason: invalid class name */
            final class AnonymousClass11 extends SuspendLambda implements Function2 {
                final /* synthetic */ View $activityContainer;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass11(LocationBasedMobileViewModel locationBasedMobileViewModel, View view, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$activityContainer = view;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass11(this.$viewModel, this.$activityContainer, continuation);
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
                        Flow activityContainerVisible = this.$viewModel.commonImpl.getActivityContainerVisible();
                        final View view = this.$activityContainer;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.11.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                view.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (activityContainerVisible.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$12, reason: invalid class name */
            final class AnonymousClass12 extends SuspendLambda implements Function2 {
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                final /* synthetic */ ImageView $voiceNoService;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass12(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$voiceNoService = imageView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass12(this.$viewModel, this.$voiceNoService, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass12) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        StateFlow voiceNoServiceIcon = this.$viewModel.commonImpl.getVoiceNoServiceIcon();
                        final ImageView imageView = this.$voiceNoService;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.12.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                ImageView imageView2 = imageView;
                                IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                Icon.Resource resource = new Icon.Resource(intValue, null);
                                iconViewBinder.getClass();
                                IconViewBinder.bind(resource, imageView2);
                                imageView.setVisibility(intValue != 0 ? 0 : 8);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (voiceNoServiceIcon.collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$13, reason: invalid class name */
            final class AnonymousClass13 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $dataActivity;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass13(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$dataActivity = imageView;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass13(this.$viewModel, this.$dataActivity, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass13) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow activityIcon = this.$viewModel.commonImpl.getActivityIcon();
                        final LocationBasedMobileViewModel locationBasedMobileViewModel = this.$viewModel;
                        final ImageView imageView = this.$dataActivity;
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.13.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int i2;
                                Icon.Resource resource = (Icon.Resource) obj2;
                                if (resource != null) {
                                    ImageView imageView2 = imageView;
                                    ViewGroup viewGroup2 = viewGroup;
                                    LocationBasedMobileViewModel locationBasedMobileViewModel2 = LocationBasedMobileViewModel.this;
                                    if (locationBasedMobileViewModel2.location != StatusBarLocation.QS || (i2 = resource.res) == 0) {
                                        IconViewBinder.INSTANCE.getClass();
                                        IconViewBinder.bind(resource, imageView2);
                                    } else {
                                        imageView2.setImageDrawable(locationBasedMobileViewModel2.commonImpl.getShadowDrawable(viewGroup2, i2));
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (activityIcon.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$14, reason: invalid class name */
            final class AnonymousClass14 extends SuspendLambda implements Function2 {
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass14(LocationBasedMobileViewModel locationBasedMobileViewModel, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass14(this.$viewModel, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass14) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow anyChanges = this.$viewModel.commonImpl.getAnyChanges();
                        this.label = 1;
                        if (FlowKt.collect(anyChanges, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$15, reason: invalid class name */
            final class AnonymousClass15 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $activityIn;
                final /* synthetic */ ImageView $activityOut;
                final /* synthetic */ ImageView $dataActivity;
                final /* synthetic */ StatusBarIconView $dotView;
                final /* synthetic */ MutableStateFlow $iconTint;
                final /* synthetic */ ImageView $iconView;
                final /* synthetic */ FrameLayout $networkTypeContainer;
                final /* synthetic */ ImageView $networkTypeView;
                final /* synthetic */ ImageView $roamingView;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                final /* synthetic */ ImageView $voiceNoService;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass15(MutableStateFlow mutableStateFlow, ImageView imageView, LocationBasedMobileViewModel locationBasedMobileViewModel, FrameLayout frameLayout, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, StatusBarIconView statusBarIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$iconTint = mutableStateFlow;
                    this.$iconView = imageView;
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$networkTypeContainer = frameLayout;
                    this.$networkTypeView = imageView2;
                    this.$roamingView = imageView3;
                    this.$activityIn = imageView4;
                    this.$activityOut = imageView5;
                    this.$dataActivity = imageView6;
                    this.$voiceNoService = imageView7;
                    this.$dotView = statusBarIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass15(this.$iconTint, this.$iconView, this.$viewModel, this.$networkTypeContainer, this.$networkTypeView, this.$roamingView, this.$activityIn, this.$activityOut, this.$dataActivity, this.$voiceNoService, this.$dotView, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass15) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        MutableStateFlow mutableStateFlow = this.$iconTint;
                        final ImageView imageView = this.$iconView;
                        final LocationBasedMobileViewModel locationBasedMobileViewModel = this.$viewModel;
                        final FrameLayout frameLayout = this.$networkTypeContainer;
                        final ImageView imageView2 = this.$networkTypeView;
                        final ImageView imageView3 = this.$roamingView;
                        final ImageView imageView4 = this.$activityIn;
                        final ImageView imageView5 = this.$activityOut;
                        final ImageView imageView6 = this.$dataActivity;
                        final ImageView imageView7 = this.$voiceNoService;
                        final StatusBarIconView statusBarIconView = this.$dotView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.15.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                MobileIconColors mobileIconColors = (MobileIconColors) obj2;
                                ColorStateList valueOf = ColorStateList.valueOf(mobileIconColors.tint);
                                ColorStateList valueOf2 = ColorStateList.valueOf(mobileIconColors.contrast);
                                imageView.setImageTintList(valueOf);
                                if (locationBasedMobileViewModel.commonImpl.getNetworkTypeBackground().getValue() != null) {
                                    frameLayout.setBackgroundTintList(valueOf);
                                    imageView2.setImageTintList(valueOf2);
                                } else {
                                    imageView2.setImageTintList(valueOf);
                                }
                                imageView3.setImageTintList(valueOf);
                                imageView4.setImageTintList(valueOf);
                                imageView5.setImageTintList(valueOf);
                                imageView6.setImageTintList(valueOf);
                                imageView7.setImageTintList(valueOf);
                                statusBarIconView.setDecorColor(mobileIconColors.tint);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$16, reason: invalid class name */
            final class AnonymousClass16 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $decorTint;
                final /* synthetic */ StatusBarIconView $dotView;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass16(MutableStateFlow mutableStateFlow, StatusBarIconView statusBarIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$decorTint = mutableStateFlow;
                    this.$dotView = statusBarIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass16(this.$decorTint, this.$dotView, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass16) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        MutableStateFlow mutableStateFlow = this.$decorTint;
                        final StatusBarIconView statusBarIconView = this.$dotView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.16.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                StatusBarIconView.this.setDecorColor(((Number) obj2).intValue());
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C03542 extends SuspendLambda implements Function2 {
                final /* synthetic */ ConfigurationController $configuration;
                final /* synthetic */ ImageView $iconView;
                final /* synthetic */ SignalDrawable $mobileDrawable;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$2$1, reason: invalid class name and collision with other inner class name */
                final class C03551 extends SuspendLambda implements Function3 {
                    /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public C03551(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        C03551 c03551 = new C03551((Continuation) obj3);
                        c03551.L$0 = (SignalIconModel) obj;
                        c03551.L$1 = (SignalIconModel) obj2;
                        return c03551.invokeSuspend(Unit.INSTANCE);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:10:0x0027, code lost:
                    
                        if (((com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular) r4).numberOfLevels != ((com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular) r3).numberOfLevels) goto L15;
                     */
                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invokeSuspend(java.lang.Object r4) {
                        /*
                            r3 = this;
                            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r0 = r3.label
                            if (r0 != 0) goto L35
                            kotlin.ResultKt.throwOnFailure(r4)
                            java.lang.Object r4 = r3.L$0
                            com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel r4 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel) r4
                            java.lang.Object r3 = r3.L$1
                            com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel r3 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel) r3
                            r0 = 1
                            if (r4 != 0) goto L15
                            goto L2b
                        L15:
                            boolean r1 = r4 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular
                            r2 = 0
                            if (r1 == 0) goto L2a
                            boolean r1 = r3 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular
                            if (r1 == 0) goto L2a
                            com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel$Cellular r4 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular) r4
                            r1 = r3
                            com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel$Cellular r1 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular) r1
                            int r1 = r1.numberOfLevels
                            int r4 = r4.numberOfLevels
                            if (r4 == r1) goto L2a
                            goto L2b
                        L2a:
                            r0 = r2
                        L2b:
                            kotlin.Pair r4 = new kotlin.Pair
                            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                            r4.<init>(r0, r3)
                            return r4
                        L35:
                            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                            r3.<init>(r4)
                            throw r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1.AnonymousClass2.AnonymousClass1.C03542.C03551.invokeSuspend(java.lang.Object):java.lang.Object");
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03542(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, ImageView imageView, ConfigurationController configurationController, SignalDrawable signalDrawable, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$view = viewGroup;
                    this.$iconView = imageView;
                    this.$configuration = configurationController;
                    this.$mobileDrawable = signalDrawable;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03542(this.$viewModel, this.$view, this.$iconView, this.$configuration, this.$mobileDrawable, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03542) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow pairwiseBy = com.android.systemui.util.kotlin.FlowKt.pairwiseBy(this.$viewModel.commonImpl.getIcon(), (Object) null, new C03551(null));
                        FlowCollector flowCollector = new FlowCollector(this.$view, this.$iconView, this.$configuration, this.$mobileDrawable) { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.2.2
                            public final /* synthetic */ ConfigurationController $configuration;
                            public final /* synthetic */ ImageView $iconView;
                            public final /* synthetic */ ViewGroup $view;

                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Pair pair = (Pair) obj2;
                                boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                                SignalIconModel signalIconModel = (SignalIconModel) pair.component2();
                                LocationBasedMobileViewModel locationBasedMobileViewModel = LocationBasedMobileViewModel.this;
                                VerboseMobileViewLogger verboseMobileViewLogger = locationBasedMobileViewModel.verboseLogger;
                                MobileIconViewModelCommon mobileIconViewModelCommon = locationBasedMobileViewModel.commonImpl;
                                if (verboseMobileViewLogger != null) {
                                    ViewGroup viewGroup = this.$view;
                                    int subscriptionId = mobileIconViewModelCommon.getSubscriptionId();
                                    verboseMobileViewLogger.getClass();
                                    LogLevel logLevel = LogLevel.VERBOSE;
                                    VerboseMobileViewLogger$$ExternalSyntheticLambda0 verboseMobileViewLogger$$ExternalSyntheticLambda0 = new VerboseMobileViewLogger$$ExternalSyntheticLambda0(0);
                                    LogBuffer logBuffer = verboseMobileViewLogger.buffer;
                                    LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$$ExternalSyntheticLambda0, null);
                                    MobileViewLogger.Companion.getClass();
                                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                                    logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(viewGroup);
                                    logMessageImpl.int1 = subscriptionId;
                                    logMessageImpl.int2 = signalIconModel.getLevel();
                                    logMessageImpl.bool1 = signalIconModel instanceof SignalIconModel.Cellular ? ((SignalIconModel.Cellular) signalIconModel).showExclamationMark : false;
                                    logBuffer.commit(obtain);
                                }
                                if (signalIconModel instanceof SignalIconModel.Cellular) {
                                    if (locationBasedMobileViewModel.location == StatusBarLocation.QS) {
                                        this.$iconView.setImageDrawable(mobileIconViewModelCommon.getShadowDrawable(this.$view, ((SignalIconModel.Cellular) signalIconModel).iconId));
                                        ConfigurationController configurationController = this.$configuration;
                                        this.$iconView.setScaleX((configurationController == null || !((ConfigurationControllerImpl) configurationController).isLayoutRtl()) ? 1.0f : -1.0f);
                                    } else {
                                        IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                        Icon.Resource resource = new Icon.Resource(((SignalIconModel.Cellular) signalIconModel).iconId, null);
                                        ImageView imageView = this.$iconView;
                                        iconViewBinder.getClass();
                                        IconViewBinder.bind(resource, imageView);
                                    }
                                } else if (signalIconModel instanceof SignalIconModel.Satellite) {
                                    IconViewBinder iconViewBinder2 = IconViewBinder.INSTANCE;
                                    Icon.Resource resource2 = ((SignalIconModel.Satellite) signalIconModel).icon;
                                    ImageView imageView2 = this.$iconView;
                                    iconViewBinder2.getClass();
                                    IconViewBinder.bind(resource2, imageView2);
                                }
                                if (booleanValue) {
                                    this.$iconView.requestLayout();
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (pairwiseBy.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$viewModel, this.$view, continuation);
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
                        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(this.$viewModel.commonImpl.getContentDescription());
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                String str;
                                MobileContentDescription$Cellular mobileContentDescription$Cellular = (MobileContentDescription$Cellular) obj2;
                                MobileContentDescriptionViewBinder mobileContentDescriptionViewBinder = MobileContentDescriptionViewBinder.INSTANCE;
                                ViewGroup viewGroup2 = viewGroup;
                                mobileContentDescriptionViewBinder.getClass();
                                if (mobileContentDescription$Cellular != null) {
                                    Context context = viewGroup2.getContext();
                                    str = context.getString(R.string.accessibility_phone_string_format, mobileContentDescription$Cellular.networkName, context.getString(mobileContentDescription$Cellular.levelDescriptionRes));
                                } else {
                                    str = null;
                                }
                                viewGroup2.setContentDescription(str);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (distinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$4, reason: invalid class name */
            final class AnonymousClass4 extends SuspendLambda implements Function2 {
                final /* synthetic */ FrameLayout $networkTypeContainer;
                final /* synthetic */ ImageView $networkTypeView;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass4(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, FrameLayout frameLayout, ImageView imageView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$view = viewGroup;
                    this.$networkTypeContainer = frameLayout;
                    this.$networkTypeView = imageView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass4(this.$viewModel, this.$view, this.$networkTypeContainer, this.$networkTypeView, continuation);
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
                        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(this.$viewModel.commonImpl.getNetworkTypeIcon());
                        final LocationBasedMobileViewModel locationBasedMobileViewModel = this.$viewModel;
                        final ViewGroup viewGroup = this.$view;
                        final FrameLayout frameLayout = this.$networkTypeContainer;
                        final ImageView imageView = this.$networkTypeView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.4.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Icon.Resource resource = (Icon.Resource) obj2;
                                LocationBasedMobileViewModel locationBasedMobileViewModel2 = LocationBasedMobileViewModel.this;
                                VerboseMobileViewLogger verboseMobileViewLogger = locationBasedMobileViewModel2.verboseLogger;
                                MobileIconViewModelCommon mobileIconViewModelCommon = locationBasedMobileViewModel2.commonImpl;
                                if (verboseMobileViewLogger != null) {
                                    ViewGroup viewGroup2 = viewGroup;
                                    int subscriptionId = mobileIconViewModelCommon.getSubscriptionId();
                                    verboseMobileViewLogger.getClass();
                                    LogLevel logLevel = LogLevel.VERBOSE;
                                    VerboseMobileViewLogger$$ExternalSyntheticLambda0 verboseMobileViewLogger$$ExternalSyntheticLambda0 = new VerboseMobileViewLogger$$ExternalSyntheticLambda0(2);
                                    LogBuffer logBuffer = verboseMobileViewLogger.buffer;
                                    LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$$ExternalSyntheticLambda0, null);
                                    MobileViewLogger.Companion.getClass();
                                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                                    logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(viewGroup2);
                                    logMessageImpl.int1 = subscriptionId;
                                    logMessageImpl.bool1 = resource != null;
                                    logMessageImpl.int2 = resource != null ? resource.res : -1;
                                    logBuffer.commit(obtain);
                                }
                                if (resource != null) {
                                    ImageView imageView2 = imageView;
                                    ViewGroup viewGroup3 = viewGroup;
                                    if (locationBasedMobileViewModel2.location == StatusBarLocation.QS) {
                                        imageView2.setImageDrawable(mobileIconViewModelCommon.getShadowDrawable(viewGroup3, resource.res));
                                    } else {
                                        IconViewBinder.INSTANCE.getClass();
                                        IconViewBinder.bind(resource, imageView2);
                                    }
                                }
                                int visibility = frameLayout.getVisibility();
                                frameLayout.setVisibility(resource != null ? 0 : 8);
                                if (visibility != frameLayout.getVisibility()) {
                                    viewGroup.requestLayout();
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (distinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $iconTint;
                final /* synthetic */ FrameLayout $networkTypeContainer;
                final /* synthetic */ ImageView $networkTypeView;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(LocationBasedMobileViewModel locationBasedMobileViewModel, FrameLayout frameLayout, MutableStateFlow mutableStateFlow, ImageView imageView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$networkTypeContainer = frameLayout;
                    this.$iconTint = mutableStateFlow;
                    this.$networkTypeView = imageView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass5(this.$viewModel, this.$networkTypeContainer, this.$iconTint, this.$networkTypeView, continuation);
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
                        StateFlow networkTypeBackground = this.$viewModel.commonImpl.getNetworkTypeBackground();
                        final FrameLayout frameLayout = this.$networkTypeContainer;
                        final MutableStateFlow mutableStateFlow = this.$iconTint;
                        final ImageView imageView = this.$networkTypeView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.5.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Icon.Resource resource = (Icon.Resource) obj2;
                                frameLayout.setBackgroundResource(resource != null ? resource.res : 0);
                                Integer num = resource != null ? new Integer(resource.res) : null;
                                MutableStateFlow mutableStateFlow2 = mutableStateFlow;
                                if (num != null) {
                                    frameLayout.setBackgroundTintList(ColorStateList.valueOf(((MobileIconColors) mutableStateFlow2.getValue()).tint));
                                    imageView.setImageTintList(ColorStateList.valueOf(((MobileIconColors) mutableStateFlow2.getValue()).contrast));
                                } else {
                                    imageView.setImageTintList(ColorStateList.valueOf(((MobileIconColors) mutableStateFlow2.getValue()).tint));
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (networkTypeBackground.collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                final /* synthetic */ ConfigurationController $configuration;
                final /* synthetic */ Space $roamingSpace;
                final /* synthetic */ ImageView $roamingView;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Space space, ConfigurationController configurationController, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$roamingView = imageView;
                    this.$roamingSpace = space;
                    this.$configuration = configurationController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.$viewModel, this.$roamingView, this.$roamingSpace, this.$configuration, continuation);
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
                        Flow roamingIcon = this.$viewModel.commonImpl.getRoamingIcon();
                        final ImageView imageView = this.$roamingView;
                        final ConfigurationController configurationController = this.$configuration;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.2.1.6.2
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Icon.Resource resource = (Icon.Resource) obj2;
                                if (resource != null) {
                                    ImageView imageView2 = imageView;
                                    IconViewBinder.INSTANCE.getClass();
                                    IconViewBinder.bind(resource, imageView2);
                                    ConfigurationController configurationController2 = configurationController;
                                    imageView2.setTranslationX((configurationController2 == null || !((ConfigurationControllerImpl) configurationController2).isLayoutRtl() || imageView2.getDrawable() == null) ? 0.0f : imageView2.getDrawable().getIntrinsicWidth() / 2);
                                }
                                imageView.setVisibility((resource == null || resource.res == 0) ? 8 : 0);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (roamingIcon.collect(flowCollector, this) == coroutineSingletons) {
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
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$2$1$9, reason: invalid class name */
            final class AnonymousClass9 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $activityIn;
                final /* synthetic */ LocationBasedMobileViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass9(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedMobileViewModel;
                    this.$activityIn = imageView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass9(this.$viewModel, this.$activityIn, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(MobileViewLogger mobileViewLogger, ViewGroup viewGroup, LocationBasedMobileViewModel locationBasedMobileViewModel, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup2, StatusBarIconView statusBarIconView, ImageView imageView, ConfigurationController configurationController, SignalDrawable signalDrawable, FrameLayout frameLayout, ImageView imageView2, MutableStateFlow mutableStateFlow2, ImageView imageView3, Space space, ImageView imageView4, ImageView imageView5, View view, ImageView imageView6, ImageView imageView7, MutableStateFlow mutableStateFlow3, Continuation continuation) {
                super(2, continuation);
                this.$logger = mobileViewLogger;
                this.$view = viewGroup;
                this.$viewModel = locationBasedMobileViewModel;
                this.$isCollecting = ref$BooleanRef;
                this.$visibilityState = mutableStateFlow;
                this.$mobileGroupView = viewGroup2;
                this.$dotView = statusBarIconView;
                this.$iconView = imageView;
                this.$configuration = configurationController;
                this.$mobileDrawable = signalDrawable;
                this.$networkTypeContainer = frameLayout;
                this.$networkTypeView = imageView2;
                this.$iconTint = mutableStateFlow2;
                this.$roamingView = imageView3;
                this.$roamingSpace = space;
                this.$activityIn = imageView4;
                this.$activityOut = imageView5;
                this.$activityContainer = view;
                this.$voiceNoService = imageView6;
                this.$dataActivity = imageView7;
                this.$decorTint = mutableStateFlow3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$logger, this.$view, this.$viewModel, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeContainer, this.$networkTypeView, this.$iconTint, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$dataActivity, this.$decorTint, continuation);
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
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        this.$logger.logCollectionStarted(this.$view, this.$viewModel);
                        this.$isCollecting.element = true;
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03471(this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$view, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03542(this.$viewModel, this.$view, this.$iconView, this.$configuration, this.$mobileDrawable, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$view, this.$networkTypeContainer, this.$networkTypeView, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$networkTypeContainer, this.$iconTint, this.$networkTypeView, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$roamingView, this.$roamingSpace, this.$configuration, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$activityIn, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$activityOut, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.$activityContainer, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass12(this.$viewModel, this.$voiceNoService, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, this.$dataActivity, this.$view, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass14(this.$viewModel, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass15(this.$iconTint, this.$iconView, this.$viewModel, this.$networkTypeContainer, this.$networkTypeView, this.$roamingView, this.$activityIn, this.$activityOut, this.$dataActivity, this.$voiceNoService, this.$dotView, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass16(this.$decorTint, this.$dotView, null), 7);
                        this.label = 1;
                        if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    throw new KotlinNothingValueException();
                } catch (Throwable th) {
                    this.$isCollecting.element = false;
                    this.$logger.logCollectionStopped(this.$view, this.$viewModel);
                    throw th;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(LifecycleOwner lifecycleOwner, MobileViewLogger mobileViewLogger, ViewGroup viewGroup, LocationBasedMobileViewModel locationBasedMobileViewModel, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup2, StatusBarIconView statusBarIconView, ImageView imageView, ConfigurationController configurationController, SignalDrawable signalDrawable, FrameLayout frameLayout, ImageView imageView2, MutableStateFlow mutableStateFlow2, ImageView imageView3, Space space, ImageView imageView4, ImageView imageView5, View view, ImageView imageView6, ImageView imageView7, MutableStateFlow mutableStateFlow3, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$logger = mobileViewLogger;
            this.$view = viewGroup;
            this.$viewModel = locationBasedMobileViewModel;
            this.$isCollecting = ref$BooleanRef;
            this.$visibilityState = mutableStateFlow;
            this.$mobileGroupView = viewGroup2;
            this.$dotView = statusBarIconView;
            this.$iconView = imageView;
            this.$configuration = configurationController;
            this.$mobileDrawable = signalDrawable;
            this.$networkTypeContainer = frameLayout;
            this.$networkTypeView = imageView2;
            this.$iconTint = mutableStateFlow2;
            this.$roamingView = imageView3;
            this.$roamingSpace = space;
            this.$activityIn = imageView4;
            this.$activityOut = imageView5;
            this.$activityContainer = view;
            this.$voiceNoService = imageView6;
            this.$dataActivity = imageView7;
            this.$decorTint = mutableStateFlow3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$$this$repeatWhenAttached, this.$logger, this.$view, this.$viewModel, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeContainer, this.$networkTypeView, this.$iconTint, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$dataActivity, this.$decorTint, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$logger, this.$view, this.$viewModel, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeContainer, this.$networkTypeView, this.$iconTint, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$dataActivity, this.$decorTint, null);
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconBinder$bind$1(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, MobileViewLogger mobileViewLogger, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup2, StatusBarIconView statusBarIconView, ImageView imageView, ConfigurationController configurationController, SignalDrawable signalDrawable, FrameLayout frameLayout, ImageView imageView2, MutableStateFlow mutableStateFlow2, ImageView imageView3, Space space, ImageView imageView4, ImageView imageView5, View view, ImageView imageView6, ImageView imageView7, MutableStateFlow mutableStateFlow3, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = locationBasedMobileViewModel;
        this.$view = viewGroup;
        this.$logger = mobileViewLogger;
        this.$isCollecting = ref$BooleanRef;
        this.$visibilityState = mutableStateFlow;
        this.$mobileGroupView = viewGroup2;
        this.$dotView = statusBarIconView;
        this.$iconView = imageView;
        this.$configuration = configurationController;
        this.$mobileDrawable = signalDrawable;
        this.$networkTypeContainer = frameLayout;
        this.$networkTypeView = imageView2;
        this.$iconTint = mutableStateFlow2;
        this.$roamingView = imageView3;
        this.$roamingSpace = space;
        this.$activityIn = imageView4;
        this.$activityOut = imageView5;
        this.$activityContainer = view;
        this.$voiceNoService = imageView6;
        this.$dataActivity = imageView7;
        this.$decorTint = mutableStateFlow3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconBinder$bind$1 mobileIconBinder$bind$1 = new MobileIconBinder$bind$1(this.$viewModel, this.$view, this.$logger, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeContainer, this.$networkTypeView, this.$iconTint, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$dataActivity, this.$decorTint, (Continuation) obj3);
        mobileIconBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return mobileIconBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
        CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$viewModel, this.$view, null), 7);
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(lifecycleOwner, this.$logger, this.$view, this.$viewModel, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeContainer, this.$networkTypeView, this.$iconTint, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$dataActivity, this.$decorTint, null), 7);
        return Unit.INSTANCE;
    }
}
