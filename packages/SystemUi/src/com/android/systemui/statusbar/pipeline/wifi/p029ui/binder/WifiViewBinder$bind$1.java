package com.android.systemui.statusbar.pipeline.wifi.p029ui.binder;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.common.p004ui.binder.IconViewBinder;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.LocationBasedWifiViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1", m277f = "WifiViewBinder.kt", m278l = {88}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WifiViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $activityContainerView;
    final /* synthetic */ ImageView $activityInView;
    final /* synthetic */ ImageView $activityOutView;
    final /* synthetic */ View $airplaneSpacer;
    final /* synthetic */ ImageView $dataActivity;
    final /* synthetic */ MutableStateFlow $decorTint;
    final /* synthetic */ StatusBarIconView $dotView;
    final /* synthetic */ ViewGroup $groupView;
    final /* synthetic */ MutableStateFlow $iconTint;
    final /* synthetic */ ImageView $iconView;
    final /* synthetic */ Ref$BooleanRef $isCollecting;
    final /* synthetic */ View $signalSpacer;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ LocationBasedWifiViewModel $viewModel;
    final /* synthetic */ MutableStateFlow $visibilityState;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1", m277f = "WifiViewBinder.kt", m278l = {189}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1 */
    final class C33641 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $activityContainerView;
        final /* synthetic */ ImageView $activityInView;
        final /* synthetic */ ImageView $activityOutView;
        final /* synthetic */ View $airplaneSpacer;
        final /* synthetic */ ImageView $dataActivity;
        final /* synthetic */ MutableStateFlow $decorTint;
        final /* synthetic */ StatusBarIconView $dotView;
        final /* synthetic */ ViewGroup $groupView;
        final /* synthetic */ MutableStateFlow $iconTint;
        final /* synthetic */ ImageView $iconView;
        final /* synthetic */ Ref$BooleanRef $isCollecting;
        final /* synthetic */ View $signalSpacer;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ LocationBasedWifiViewModel $viewModel;
        final /* synthetic */ MutableStateFlow $visibilityState;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$1", m277f = "WifiViewBinder.kt", m278l = {92}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ StatusBarIconView $dotView;
            final /* synthetic */ ViewGroup $groupView;
            final /* synthetic */ MutableStateFlow $visibilityState;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$visibilityState = mutableStateFlow;
                this.$groupView = viewGroup;
                this.$dotView = statusBarIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$visibilityState, this.$groupView, this.$dotView, continuation);
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
                    final ViewGroup viewGroup = this.$groupView;
                    final StatusBarIconView statusBarIconView = this.$dotView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            viewGroup.setVisibility(intValue == 0 ? 0 : 8);
                            statusBarIconView.setVisibility(intValue == 1 ? 0 : 8);
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
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$10", m277f = "WifiViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $signalSpacer;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(LocationBasedWifiViewModel locationBasedWifiViewModel, View view, Continuation<? super AnonymousClass10> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedWifiViewModel;
                this.$signalSpacer = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass10(this.$viewModel, this.$signalSpacer, continuation);
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$2", m277f = "WifiViewBinder.kt", m278l = {99}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $iconView;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(LocationBasedWifiViewModel locationBasedWifiViewModel, ViewGroup viewGroup, ImageView imageView, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedWifiViewModel;
                this.$view = viewGroup;
                this.$iconView = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$view, this.$iconView, continuation);
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
                    StateFlow wifiIcon = this.$viewModel.getWifiIcon();
                    final ViewGroup viewGroup = this.$view;
                    final LocationBasedWifiViewModel locationBasedWifiViewModel = this.$viewModel;
                    final ImageView imageView = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            WifiIcon wifiIcon2 = (WifiIcon) obj2;
                            boolean z = wifiIcon2 instanceof WifiIcon.Visible;
                            int i2 = z ? 0 : 8;
                            ViewGroup viewGroup2 = viewGroup;
                            viewGroup2.setVisibility(i2);
                            if (z) {
                                StatusBarLocation statusBarLocation = locationBasedWifiViewModel.location;
                                StatusBarLocation statusBarLocation2 = StatusBarLocation.QS;
                                ImageView imageView2 = imageView;
                                if (statusBarLocation == statusBarLocation2) {
                                    imageView2.setImageDrawable(LocationBasedWifiViewModel.getShadowDrawable(viewGroup2, ((WifiIcon.Visible) wifiIcon2).icon.res));
                                } else {
                                    IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                    Icon.Resource resource = ((WifiIcon.Visible) wifiIcon2).icon;
                                    iconViewBinder.getClass();
                                    IconViewBinder.bind(resource, imageView2);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (wifiIcon.collect(flowCollector, this) == coroutineSingletons) {
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
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$3", m277f = "WifiViewBinder.kt", m278l = {116}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $dataActivity;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(LocationBasedWifiViewModel locationBasedWifiViewModel, ImageView imageView, ViewGroup viewGroup, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedWifiViewModel;
                this.$dataActivity = imageView;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$dataActivity, this.$view, continuation);
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
                    StateFlow activityIcon = this.$viewModel.getActivityIcon();
                    final ImageView imageView = this.$dataActivity;
                    final LocationBasedWifiViewModel locationBasedWifiViewModel = this.$viewModel;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int i2;
                            Icon.Resource resource = (Icon.Resource) obj2;
                            ImageView imageView2 = imageView;
                            if (resource != null) {
                                imageView2.setVisibility(0);
                                if (locationBasedWifiViewModel.location != StatusBarLocation.QS || (i2 = resource.res) == 0) {
                                    IconViewBinder.INSTANCE.getClass();
                                    IconViewBinder.bind(resource, imageView2);
                                } else {
                                    imageView2.setImageDrawable(LocationBasedWifiViewModel.getShadowDrawable(viewGroup, i2));
                                }
                            } else {
                                imageView2.setVisibility(8);
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
                throw new KotlinNothingValueException();
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$4", m277f = "WifiViewBinder.kt", m278l = {136}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $activityInView;
            final /* synthetic */ ImageView $activityOutView;
            final /* synthetic */ ImageView $dataActivity;
            final /* synthetic */ StatusBarIconView $dotView;
            final /* synthetic */ MutableStateFlow $iconTint;
            final /* synthetic */ ImageView $iconView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(MutableStateFlow mutableStateFlow, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, StatusBarIconView statusBarIconView, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$iconTint = mutableStateFlow;
                this.$iconView = imageView;
                this.$activityInView = imageView2;
                this.$activityOutView = imageView3;
                this.$dataActivity = imageView4;
                this.$dotView = statusBarIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$iconTint, this.$iconView, this.$activityInView, this.$activityOutView, this.$dataActivity, this.$dotView, continuation);
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
                    MutableStateFlow mutableStateFlow = this.$iconTint;
                    final ImageView imageView = this.$iconView;
                    final ImageView imageView2 = this.$activityInView;
                    final ImageView imageView3 = this.$activityOutView;
                    final ImageView imageView4 = this.$dataActivity;
                    final StatusBarIconView statusBarIconView = this.$dotView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            ColorStateList valueOf = ColorStateList.valueOf(intValue);
                            imageView.setImageTintList(valueOf);
                            imageView2.setImageTintList(valueOf);
                            imageView3.setImageTintList(valueOf);
                            imageView4.setImageTintList(valueOf);
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
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$5", m277f = "WifiViewBinder.kt", m278l = {148}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $decorTint;
            final /* synthetic */ StatusBarIconView $dotView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(MutableStateFlow mutableStateFlow, StatusBarIconView statusBarIconView, Continuation<? super AnonymousClass5> continuation) {
                super(2, continuation);
                this.$decorTint = mutableStateFlow;
                this.$dotView = statusBarIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$decorTint, this.$dotView, continuation);
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
                    MutableStateFlow mutableStateFlow = this.$decorTint;
                    final StatusBarIconView statusBarIconView = this.$dotView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.1.1.5.1
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
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$6", m277f = "WifiViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $activityInView;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(LocationBasedWifiViewModel locationBasedWifiViewModel, ImageView imageView, Continuation<? super AnonymousClass6> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedWifiViewModel;
                this.$activityInView = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$viewModel, this.$activityInView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$7", m277f = "WifiViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $activityOutView;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(LocationBasedWifiViewModel locationBasedWifiViewModel, ImageView imageView, Continuation<? super AnonymousClass7> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedWifiViewModel;
                this.$activityOutView = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, this.$activityOutView, continuation);
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
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$8", m277f = "WifiViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $activityContainerView;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(LocationBasedWifiViewModel locationBasedWifiViewModel, View view, Continuation<? super AnonymousClass8> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedWifiViewModel;
                this.$activityContainerView = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass8(this.$viewModel, this.$activityContainerView, continuation);
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
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$9", m277f = "WifiViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $airplaneSpacer;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(LocationBasedWifiViewModel locationBasedWifiViewModel, View view, Continuation<? super AnonymousClass9> continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedWifiViewModel;
                this.$airplaneSpacer = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.$viewModel, this.$airplaneSpacer, continuation);
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
        public C33641(Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, LocationBasedWifiViewModel locationBasedWifiViewModel, ViewGroup viewGroup2, ImageView imageView, ImageView imageView2, MutableStateFlow mutableStateFlow2, ImageView imageView3, ImageView imageView4, MutableStateFlow mutableStateFlow3, View view, View view2, View view3, Continuation<? super C33641> continuation) {
            super(2, continuation);
            this.$isCollecting = ref$BooleanRef;
            this.$visibilityState = mutableStateFlow;
            this.$groupView = viewGroup;
            this.$dotView = statusBarIconView;
            this.$viewModel = locationBasedWifiViewModel;
            this.$view = viewGroup2;
            this.$iconView = imageView;
            this.$dataActivity = imageView2;
            this.$iconTint = mutableStateFlow2;
            this.$activityInView = imageView3;
            this.$activityOutView = imageView4;
            this.$decorTint = mutableStateFlow3;
            this.$activityContainerView = view;
            this.$airplaneSpacer = view2;
            this.$signalSpacer = view3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C33641 c33641 = new C33641(this.$isCollecting, this.$visibilityState, this.$groupView, this.$dotView, this.$viewModel, this.$view, this.$iconView, this.$dataActivity, this.$iconTint, this.$activityInView, this.$activityOutView, this.$decorTint, this.$activityContainerView, this.$airplaneSpacer, this.$signalSpacer, continuation);
            c33641.L$0 = obj;
            return c33641;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33641) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    this.$isCollecting.element = true;
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$visibilityState, this.$groupView, this.$dotView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, this.$iconView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$dataActivity, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$iconTint, this.$iconView, this.$activityInView, this.$activityOutView, this.$dataActivity, this.$dotView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$decorTint, this.$dotView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$activityInView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$activityOutView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass8(this.$viewModel, this.$activityContainerView, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$airplaneSpacer, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$signalSpacer, null), 3);
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
                throw th;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiViewBinder$bind$1(Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, LocationBasedWifiViewModel locationBasedWifiViewModel, ViewGroup viewGroup2, ImageView imageView, ImageView imageView2, MutableStateFlow mutableStateFlow2, ImageView imageView3, ImageView imageView4, MutableStateFlow mutableStateFlow3, View view, View view2, View view3, Continuation<? super WifiViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$isCollecting = ref$BooleanRef;
        this.$visibilityState = mutableStateFlow;
        this.$groupView = viewGroup;
        this.$dotView = statusBarIconView;
        this.$viewModel = locationBasedWifiViewModel;
        this.$view = viewGroup2;
        this.$iconView = imageView;
        this.$dataActivity = imageView2;
        this.$iconTint = mutableStateFlow2;
        this.$activityInView = imageView3;
        this.$activityOutView = imageView4;
        this.$decorTint = mutableStateFlow3;
        this.$activityContainerView = view;
        this.$airplaneSpacer = view2;
        this.$signalSpacer = view3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WifiViewBinder$bind$1 wifiViewBinder$bind$1 = new WifiViewBinder$bind$1(this.$isCollecting, this.$visibilityState, this.$groupView, this.$dotView, this.$viewModel, this.$view, this.$iconView, this.$dataActivity, this.$iconTint, this.$activityInView, this.$activityOutView, this.$decorTint, this.$activityContainerView, this.$airplaneSpacer, this.$signalSpacer, (Continuation) obj3);
        wifiViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return wifiViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C33641 c33641 = new C33641(this.$isCollecting, this.$visibilityState, this.$groupView, this.$dotView, this.$viewModel, this.$view, this.$iconView, this.$dataActivity, this.$iconTint, this.$activityInView, this.$activityOutView, this.$decorTint, this.$activityContainerView, this.$airplaneSpacer, this.$signalSpacer, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c33641, this) == coroutineSingletons) {
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
