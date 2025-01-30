package com.android.systemui.statusbar.pipeline.mobile.p026ui.binder;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.common.p004ui.binder.ContentDescriptionViewBinder;
import com.android.systemui.common.p004ui.binder.IconViewBinder;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.VerboseMobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1", m277f = "MobileIconBinder.kt", m278l = {91}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class MobileIconBinder$bind$1 extends SuspendLambda implements Function3 {
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
    final /* synthetic */ ImageView $networkTypeView;
    final /* synthetic */ Space $roamingSpace;
    final /* synthetic */ ImageView $roamingView;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ LocationBasedMobileViewModel $viewModel;
    final /* synthetic */ MutableStateFlow $visibilityState;
    final /* synthetic */ ImageView $voiceNoService;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1", m277f = "MobileIconBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1 */
    final class C33251 extends SuspendLambda implements Function2 {
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
        final /* synthetic */ ImageView $networkTypeView;
        final /* synthetic */ Space $roamingSpace;
        final /* synthetic */ ImageView $roamingView;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ LocationBasedMobileViewModel $viewModel;
        final /* synthetic */ MutableStateFlow $visibilityState;
        final /* synthetic */ ImageView $voiceNoService;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$1", m277f = "MobileIconBinder.kt", m278l = {96}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarIconView $dotView;
            final /* synthetic */ ViewGroup $mobileGroupView;
            final /* synthetic */ MutableStateFlow $visibilityState;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$visibilityState = mutableStateFlow;
                this.$mobileGroupView = viewGroup;
                this.$dotView = statusBarIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$visibilityState, this.$mobileGroupView, this.$dotView, continuation);
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
                    MutableStateFlow mutableStateFlow = this.$visibilityState;
                    final ViewGroup viewGroup = this.$mobileGroupView;
                    final StatusBarIconView statusBarIconView = this.$dotView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            StatusBarIconView statusBarIconView2 = statusBarIconView;
                            ViewGroup viewGroup2 = viewGroup;
                            if (intValue == 0) {
                                viewGroup2.setVisibility(0);
                                statusBarIconView2.setVisibility(8);
                            } else if (intValue == 1) {
                                viewGroup2.setVisibility(4);
                                statusBarIconView2.setVisibility(0);
                            } else if (intValue == 2) {
                                viewGroup2.setVisibility(4);
                                statusBarIconView2.setVisibility(4);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$10", m277f = "MobileIconBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            final /* synthetic */ ImageView $voiceNoService;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Continuation<? super AnonymousClass10> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$voiceNoService = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass10(this.$viewModel, this.$voiceNoService, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow voiceNoServiceIcon = this.$viewModel.getVoiceNoServiceIcon();
                    final ImageView imageView = this.$voiceNoService;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.10.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                            Icon.Resource resource = new Icon.Resource(intValue, null);
                            iconViewBinder.getClass();
                            ImageView imageView2 = imageView;
                            IconViewBinder.bind(resource, imageView2);
                            imageView2.setVisibility(intValue != 0 ? 0 : 8);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$11", m277f = "MobileIconBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$11, reason: invalid class name */
        final class AnonymousClass11 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $activityIn;
            final /* synthetic */ ImageView $activityOut;
            final /* synthetic */ ImageView $dataActivity;
            final /* synthetic */ StatusBarIconView $dotView;
            final /* synthetic */ MutableStateFlow $iconTint;
            final /* synthetic */ ImageView $iconView;
            final /* synthetic */ ImageView $networkTypeView;
            final /* synthetic */ ImageView $roamingView;
            final /* synthetic */ ImageView $voiceNoService;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass11(MutableStateFlow mutableStateFlow, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, StatusBarIconView statusBarIconView, Continuation<? super AnonymousClass11> continuation) {
                super(2, continuation);
                this.$iconTint = mutableStateFlow;
                this.$iconView = imageView;
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
                return new AnonymousClass11(this.$iconTint, this.$iconView, this.$networkTypeView, this.$roamingView, this.$activityIn, this.$activityOut, this.$dataActivity, this.$voiceNoService, this.$dotView, continuation);
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
                    MutableStateFlow mutableStateFlow = this.$iconTint;
                    final ImageView imageView = this.$iconView;
                    final ImageView imageView2 = this.$networkTypeView;
                    final ImageView imageView3 = this.$roamingView;
                    final ImageView imageView4 = this.$activityIn;
                    final ImageView imageView5 = this.$activityOut;
                    final ImageView imageView6 = this.$dataActivity;
                    final ImageView imageView7 = this.$voiceNoService;
                    final StatusBarIconView statusBarIconView = this.$dotView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.11.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            ColorStateList valueOf = ColorStateList.valueOf(intValue);
                            imageView.setImageTintList(valueOf);
                            imageView2.setImageTintList(valueOf);
                            imageView3.setImageTintList(valueOf);
                            imageView4.setImageTintList(valueOf);
                            imageView5.setImageTintList(valueOf);
                            imageView6.setImageTintList(valueOf);
                            imageView7.setImageTintList(valueOf);
                            statusBarIconView.setDecorColor(intValue);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$12", m277f = "MobileIconBinder.kt", m278l = {254}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$12, reason: invalid class name */
        final class AnonymousClass12 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $decorTint;
            final /* synthetic */ StatusBarIconView $dotView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass12(MutableStateFlow mutableStateFlow, StatusBarIconView statusBarIconView, Continuation<? super AnonymousClass12> continuation) {
                super(2, continuation);
                this.$decorTint = mutableStateFlow;
                this.$dotView = statusBarIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass12(this.$decorTint, this.$dotView, continuation);
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
                    MutableStateFlow mutableStateFlow = this.$decorTint;
                    final StatusBarIconView statusBarIconView = this.$dotView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.12.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            StatusBarIconView.this.setDecorColor(((Number) obj2).intValue());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$13", m277f = "MobileIconBinder.kt", m278l = {258}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$13, reason: invalid class name */
        final class AnonymousClass13 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $dataActivity;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass13(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, ViewGroup viewGroup, Continuation<? super AnonymousClass13> continuation) {
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
                    Flow activityIcon = this.$viewModel.getActivityIcon();
                    final LocationBasedMobileViewModel locationBasedMobileViewModel = this.$viewModel;
                    final ImageView imageView = this.$dataActivity;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.13.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int i2;
                            Icon.Resource resource = (Icon.Resource) obj2;
                            if (resource != null) {
                                LocationBasedMobileViewModel locationBasedMobileViewModel2 = LocationBasedMobileViewModel.this;
                                boolean areEqual = Intrinsics.areEqual(locationBasedMobileViewModel2.locationName, InterfaceC1922QS.TAG);
                                ImageView imageView2 = imageView;
                                if (!areEqual || (i2 = resource.res) == 0) {
                                    IconViewBinder.INSTANCE.getClass();
                                    IconViewBinder.bind(resource, imageView2);
                                } else {
                                    imageView2.setImageDrawable(locationBasedMobileViewModel2.getShadowDrawable(viewGroup, i2));
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$14", m277f = "MobileIconBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setForcedDisplaySizeDensity}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$14, reason: invalid class name */
        final class AnonymousClass14 extends SuspendLambda implements Function2 {
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass14(LocationBasedMobileViewModel locationBasedMobileViewModel, Continuation<? super AnonymousClass14> continuation) {
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
                    Flow anyChanges = this.$viewModel.getAnyChanges();
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$2", m277f = "MobileIconBinder.kt", m278l = {114}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$view, continuation);
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
                    StateFlow isVisible = this.$viewModel.isVisible();
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            viewGroup.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$3", m277f = "MobileIconBinder.kt", m278l = {118}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ ConfigurationController $configuration;
            final /* synthetic */ ImageView $iconView;
            final /* synthetic */ SignalDrawable $mobileDrawable;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, ImageView imageView, ConfigurationController configurationController, SignalDrawable signalDrawable, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$view = viewGroup;
                this.$iconView = imageView;
                this.$configuration = configurationController;
                this.$mobileDrawable = signalDrawable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$view, this.$iconView, this.$configuration, this.$mobileDrawable, continuation);
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
                    Flow distinctUntilChanged = FlowKt.distinctUntilChanged(this.$viewModel.getIcon());
                    FlowCollector flowCollector = new FlowCollector(this.$view, this.$iconView, this.$configuration, this.$mobileDrawable) { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.3.1
                        public final /* synthetic */ ConfigurationController $configuration;
                        public final /* synthetic */ ImageView $iconView;
                        public final /* synthetic */ ViewGroup $view;

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            SignalIconModel signalIconModel = (SignalIconModel) obj2;
                            LocationBasedMobileViewModel locationBasedMobileViewModel = LocationBasedMobileViewModel.this;
                            VerboseMobileViewLogger verboseMobileViewLogger = locationBasedMobileViewModel.verboseLogger;
                            ViewGroup viewGroup = this.$view;
                            if (verboseMobileViewLogger != null) {
                                verboseMobileViewLogger.logBinderReceivedSignalIcon(viewGroup, locationBasedMobileViewModel.getSubscriptionId(), signalIconModel);
                            }
                            boolean z = signalIconModel instanceof SignalIconModel.Cellular;
                            ImageView imageView = this.$iconView;
                            if (z) {
                                if (Intrinsics.areEqual(locationBasedMobileViewModel.locationName, InterfaceC1922QS.TAG)) {
                                    imageView.setImageDrawable(locationBasedMobileViewModel.getShadowDrawable(viewGroup, ((SignalIconModel.Cellular) signalIconModel).iconId));
                                    ConfigurationController configurationController = this.$configuration;
                                    imageView.setScaleX((configurationController == null || !((ConfigurationControllerImpl) configurationController).isLayoutRtl()) ? 1.0f : -1.0f);
                                } else {
                                    IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                    Icon.Resource resource = new Icon.Resource(((SignalIconModel.Cellular) signalIconModel).iconId, null);
                                    iconViewBinder.getClass();
                                    IconViewBinder.bind(resource, imageView);
                                }
                            } else if (signalIconModel instanceof SignalIconModel.Satellite) {
                                IconViewBinder iconViewBinder2 = IconViewBinder.INSTANCE;
                                Icon.Resource resource2 = ((SignalIconModel.Satellite) signalIconModel).icon;
                                iconViewBinder2.getClass();
                                IconViewBinder.bind(resource2, imageView);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$4", m277f = "MobileIconBinder.kt", m278l = {155}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$view, continuation);
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
                    Flow distinctUntilChanged = FlowKt.distinctUntilChanged(this.$viewModel.getContentDescription());
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ContentDescriptionViewBinder.INSTANCE.getClass();
                            ContentDescriptionViewBinder.bind((ContentDescription) obj2, viewGroup);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$5", m277f = "MobileIconBinder.kt", m278l = {162}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $networkTypeView;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(LocationBasedMobileViewModel locationBasedMobileViewModel, ViewGroup viewGroup, ImageView imageView, Continuation<? super AnonymousClass5> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$view = viewGroup;
                this.$networkTypeView = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$view, this.$networkTypeView, continuation);
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
                    Flow distinctUntilChanged = FlowKt.distinctUntilChanged(this.$viewModel.getNetworkTypeIcon());
                    final LocationBasedMobileViewModel locationBasedMobileViewModel = this.$viewModel;
                    final ViewGroup viewGroup = this.$view;
                    final ImageView imageView = this.$networkTypeView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Icon.Resource resource = (Icon.Resource) obj2;
                            LocationBasedMobileViewModel locationBasedMobileViewModel2 = LocationBasedMobileViewModel.this;
                            VerboseMobileViewLogger verboseMobileViewLogger = locationBasedMobileViewModel2.verboseLogger;
                            ViewGroup viewGroup2 = viewGroup;
                            if (verboseMobileViewLogger != null) {
                                verboseMobileViewLogger.logBinderReceivedNetworkTypeIcon(viewGroup2, locationBasedMobileViewModel2.getSubscriptionId(), resource);
                            }
                            ImageView imageView2 = imageView;
                            if (resource != null) {
                                if (Intrinsics.areEqual(locationBasedMobileViewModel2.locationName, InterfaceC1922QS.TAG)) {
                                    imageView2.setImageDrawable(locationBasedMobileViewModel2.getShadowDrawable(viewGroup2, resource.res));
                                } else {
                                    IconViewBinder.INSTANCE.getClass();
                                    IconViewBinder.bind(resource, imageView2);
                                }
                            }
                            imageView2.setVisibility((resource == null || resource.res == 0) ? 8 : 0);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$6", m277f = "MobileIconBinder.kt", m278l = {190}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ ConfigurationController $configuration;
            final /* synthetic */ Space $roamingSpace;
            final /* synthetic */ ImageView $roamingView;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Space space, ConfigurationController configurationController, Continuation<? super AnonymousClass6> continuation) {
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
                    Flow roamingIcon = this.$viewModel.getRoamingIcon();
                    final ImageView imageView = this.$roamingView;
                    final ConfigurationController configurationController = this.$configuration;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.6.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Icon.Resource resource = (Icon.Resource) obj2;
                            ImageView imageView2 = imageView;
                            if (resource != null) {
                                IconViewBinder.INSTANCE.getClass();
                                IconViewBinder.bind(resource, imageView2);
                                ConfigurationController configurationController2 = configurationController;
                                imageView2.setTranslationX((configurationController2 == null || !((ConfigurationControllerImpl) configurationController2).isLayoutRtl() || imageView2.getDrawable() == null) ? 0.0f : imageView2.getDrawable().getIntrinsicWidth() / 2);
                            }
                            imageView2.setVisibility(resource != null && resource.res != 0 ? 0 : 8);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$7", m277f = "MobileIconBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $activityIn;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Continuation<? super AnonymousClass7> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$activityIn = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, this.$activityIn, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$8", m277f = "MobileIconBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $activityOut;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(LocationBasedMobileViewModel locationBasedMobileViewModel, ImageView imageView, Continuation<? super AnonymousClass8> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$activityOut = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass8(this.$viewModel, this.$activityOut, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$9", m277f = "MobileIconBinder.kt", m278l = {220}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$1$1$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $activityContainer;
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(LocationBasedMobileViewModel locationBasedMobileViewModel, View view, Continuation<? super AnonymousClass9> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
                this.$activityContainer = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.$viewModel, this.$activityContainer, continuation);
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
                    Flow activityContainerVisible = this.$viewModel.getActivityContainerVisible();
                    final View view = this.$activityContainer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder.bind.1.1.9.1
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C33251(MobileViewLogger mobileViewLogger, ViewGroup viewGroup, LocationBasedMobileViewModel locationBasedMobileViewModel, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup2, StatusBarIconView statusBarIconView, ImageView imageView, ConfigurationController configurationController, SignalDrawable signalDrawable, ImageView imageView2, ImageView imageView3, Space space, ImageView imageView4, ImageView imageView5, View view, ImageView imageView6, MutableStateFlow mutableStateFlow2, ImageView imageView7, MutableStateFlow mutableStateFlow3, Continuation<? super C33251> continuation) {
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
            this.$networkTypeView = imageView2;
            this.$roamingView = imageView3;
            this.$roamingSpace = space;
            this.$activityIn = imageView4;
            this.$activityOut = imageView5;
            this.$activityContainer = view;
            this.$voiceNoService = imageView6;
            this.$iconTint = mutableStateFlow2;
            this.$dataActivity = imageView7;
            this.$decorTint = mutableStateFlow3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C33251 c33251 = new C33251(this.$logger, this.$view, this.$viewModel, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeView, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$iconTint, this.$dataActivity, this.$decorTint, continuation);
            c33251.L$0 = obj;
            return c33251;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33251) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$visibilityState, this.$mobileGroupView, this.$dotView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$view, this.$iconView, this.$configuration, this.$mobileDrawable, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$view, this.$networkTypeView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$roamingView, this.$roamingSpace, this.$configuration, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$activityIn, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass8(this.$viewModel, this.$activityOut, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$activityContainer, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$voiceNoService, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass11(this.$iconTint, this.$iconView, this.$networkTypeView, this.$roamingView, this.$activityIn, this.$activityOut, this.$dataActivity, this.$voiceNoService, this.$dotView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass12(this.$decorTint, this.$dotView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, this.$dataActivity, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass14(this.$viewModel, null), 3);
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
    public MobileIconBinder$bind$1(MobileViewLogger mobileViewLogger, ViewGroup viewGroup, LocationBasedMobileViewModel locationBasedMobileViewModel, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup2, StatusBarIconView statusBarIconView, ImageView imageView, ConfigurationController configurationController, SignalDrawable signalDrawable, ImageView imageView2, ImageView imageView3, Space space, ImageView imageView4, ImageView imageView5, View view, ImageView imageView6, MutableStateFlow mutableStateFlow2, ImageView imageView7, MutableStateFlow mutableStateFlow3, Continuation<? super MobileIconBinder$bind$1> continuation) {
        super(3, continuation);
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
        this.$networkTypeView = imageView2;
        this.$roamingView = imageView3;
        this.$roamingSpace = space;
        this.$activityIn = imageView4;
        this.$activityOut = imageView5;
        this.$activityContainer = view;
        this.$voiceNoService = imageView6;
        this.$iconTint = mutableStateFlow2;
        this.$dataActivity = imageView7;
        this.$decorTint = mutableStateFlow3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconBinder$bind$1 mobileIconBinder$bind$1 = new MobileIconBinder$bind$1(this.$logger, this.$view, this.$viewModel, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeView, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$iconTint, this.$dataActivity, this.$decorTint, (Continuation) obj3);
        mobileIconBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return mobileIconBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C33251 c33251 = new C33251(this.$logger, this.$view, this.$viewModel, this.$isCollecting, this.$visibilityState, this.$mobileGroupView, this.$dotView, this.$iconView, this.$configuration, this.$mobileDrawable, this.$networkTypeView, this.$roamingView, this.$roamingSpace, this.$activityIn, this.$activityOut, this.$activityContainer, this.$voiceNoService, this.$iconTint, this.$dataActivity, this.$decorTint, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c33251, this) == coroutineSingletons) {
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
