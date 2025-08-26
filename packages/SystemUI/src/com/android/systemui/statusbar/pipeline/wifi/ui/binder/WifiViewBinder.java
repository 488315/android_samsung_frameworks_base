package com.android.systemui.statusbar.pipeline.wifi.ui.binder;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.binder.ContentDescriptionViewBinder;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewVisibilityHelper;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.android.systemui.util.DeviceState;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
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
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class WifiViewBinder {

    /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
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

        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C05911 extends SuspendLambda implements Function2 {
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

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C05921 extends SuspendLambda implements Function2 {
                final /* synthetic */ StatusBarIconView $dotView;
                final /* synthetic */ ViewGroup $groupView;
                final /* synthetic */ MutableStateFlow $visibilityState;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05921(MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$visibilityState = mutableStateFlow;
                    this.$groupView = viewGroup;
                    this.$dotView = statusBarIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05921(this.$visibilityState, this.$groupView, this.$dotView, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05921) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                int iIntValue = ((Number) obj2).intValue();
                                ModernStatusBarViewVisibilityHelper.Companion companion = ModernStatusBarViewVisibilityHelper.Companion;
                                ViewGroup viewGroup2 = viewGroup;
                                companion.getClass();
                                ModernStatusBarViewVisibilityHelper.Companion.setVisibilityState(viewGroup2, statusBarIconView, iIntValue);
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

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $iconView;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ LocationBasedWifiViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(LocationBasedWifiViewModel locationBasedWifiViewModel, ViewGroup viewGroup, ImageView imageView, Continuation continuation) {
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
                        StateFlow wifiIcon = this.$viewModel.commonImpl.getWifiIcon();
                        final ViewGroup viewGroup = this.$view;
                        final LocationBasedWifiViewModel locationBasedWifiViewModel = this.$viewModel;
                        final ImageView imageView = this.$iconView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                                WifiIcon wifiIcon2 = (WifiIcon) obj2;
                                boolean z = wifiIcon2 instanceof WifiIcon.Visible;
                                viewGroup.setVisibility(z ? 0 : 8);
                                if (z) {
                                    if (locationBasedWifiViewModel.location != StatusBarLocation.QS || DeviceState.isShowingPopOverStatusBar(viewGroup.getContext())) {
                                        IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                        Icon.Resource resource = ((WifiIcon.Visible) wifiIcon2).icon;
                                        ImageView imageView2 = imageView;
                                        iconViewBinder.getClass();
                                        IconViewBinder.bind(resource, imageView2);
                                    } else {
                                        WifiIcon.Visible visible = (WifiIcon.Visible) wifiIcon2;
                                        imageView.setImageDrawable(LocationBasedWifiViewModel.getShadowDrawable(viewGroup, visible.icon.res));
                                        ContentDescriptionViewBinder contentDescriptionViewBinder = ContentDescriptionViewBinder.INSTANCE;
                                        ContentDescription contentDescription = visible.icon.contentDescription;
                                        ImageView imageView3 = imageView;
                                        contentDescriptionViewBinder.getClass();
                                        ContentDescriptionViewBinder.bind(contentDescription, imageView3);
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

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $dataActivity;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ LocationBasedWifiViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(LocationBasedWifiViewModel locationBasedWifiViewModel, ImageView imageView, ViewGroup viewGroup, Continuation continuation) {
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
                        StateFlow activityIcon = this.$viewModel.commonImpl.getActivityIcon();
                        final ImageView imageView = this.$dataActivity;
                        final LocationBasedWifiViewModel locationBasedWifiViewModel = this.$viewModel;
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int i2;
                                Icon.Resource resource = (Icon.Resource) obj2;
                                if (resource != null) {
                                    imageView.setVisibility(0);
                                    if (locationBasedWifiViewModel.location != StatusBarLocation.QS || (i2 = resource.res) == 0 || DeviceState.isShowingPopOverStatusBar(viewGroup.getContext())) {
                                        IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                        ImageView imageView2 = imageView;
                                        iconViewBinder.getClass();
                                        IconViewBinder.bind(resource, imageView2);
                                    } else {
                                        imageView.setImageDrawable(LocationBasedWifiViewModel.getShadowDrawable(viewGroup, i2));
                                    }
                                } else {
                                    imageView.setVisibility(8);
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
                public AnonymousClass4(MutableStateFlow mutableStateFlow, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, StatusBarIconView statusBarIconView, Continuation continuation) {
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
                                int iIntValue = ((Number) obj2).intValue();
                                ColorStateList colorStateListValueOf = ColorStateList.valueOf(iIntValue);
                                imageView.setImageTintList(colorStateListValueOf);
                                imageView2.setImageTintList(colorStateListValueOf);
                                imageView3.setImageTintList(colorStateListValueOf);
                                imageView4.setImageTintList(colorStateListValueOf);
                                statusBarIconView.setDecorColor(iIntValue);
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

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$1$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $decorTint;
                final /* synthetic */ StatusBarIconView $dotView;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(MutableStateFlow mutableStateFlow, StatusBarIconView statusBarIconView, Continuation continuation) {
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
                                statusBarIconView.setDecorColor(((Number) obj2).intValue());
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05911(Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, LocationBasedWifiViewModel locationBasedWifiViewModel, ViewGroup viewGroup2, ImageView imageView, ImageView imageView2, MutableStateFlow mutableStateFlow2, ImageView imageView3, ImageView imageView4, MutableStateFlow mutableStateFlow3, View view, View view2, View view3, Continuation continuation) {
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
                C05911 c05911 = new C05911(this.$isCollecting, this.$visibilityState, this.$groupView, this.$dotView, this.$viewModel, this.$view, this.$iconView, this.$dataActivity, this.$iconTint, this.$activityInView, this.$activityOutView, this.$decorTint, this.$activityContainerView, this.$airplaneSpacer, this.$signalSpacer, continuation);
                c05911.L$0 = obj;
                return c05911;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05911) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C05921(this.$visibilityState, this.$groupView, this.$dotView, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, this.$iconView, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$dataActivity, this.$view, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$iconTint, this.$iconView, this.$activityInView, this.$activityOutView, this.$dataActivity, this.$dotView, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$decorTint, this.$dotView, null), 7);
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
        public AnonymousClass1(Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, ViewGroup viewGroup, StatusBarIconView statusBarIconView, LocationBasedWifiViewModel locationBasedWifiViewModel, ViewGroup viewGroup2, ImageView imageView, ImageView imageView2, MutableStateFlow mutableStateFlow2, ImageView imageView3, ImageView imageView4, MutableStateFlow mutableStateFlow3, View view, View view2, View view3, Continuation continuation) {
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isCollecting, this.$visibilityState, this.$groupView, this.$dotView, this.$viewModel, this.$view, this.$iconView, this.$dataActivity, this.$iconTint, this.$activityInView, this.$activityOutView, this.$decorTint, this.$activityContainerView, this.$airplaneSpacer, this.$signalSpacer, (Continuation) obj3);
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
                Lifecycle.State state = Lifecycle.State.STARTED;
                C05911 c05911 = new C05911(this.$isCollecting, this.$visibilityState, this.$groupView, this.$dotView, this.$viewModel, this.$view, this.$iconView, this.$dataActivity, this.$iconTint, this.$activityInView, this.$activityOutView, this.$decorTint, this.$activityContainerView, this.$airplaneSpacer, this.$signalSpacer, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c05911, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ Ref$BooleanRef $isCollecting;
        final /* synthetic */ LocationBasedWifiViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Ref$BooleanRef $isCollecting;
            final /* synthetic */ LocationBasedWifiViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C05981 extends SuspendLambda implements Function2 {
                final /* synthetic */ LocationBasedWifiViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05981(LocationBasedWifiViewModel locationBasedWifiViewModel, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedWifiViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05981(this.$viewModel, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05981) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow deXWifiIcon = this.$viewModel.commonImpl.getDeXWifiIcon();
                        this.label = 1;
                        if (FlowKt.collect(deXWifiIcon, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C05992 extends SuspendLambda implements Function2 {
                final /* synthetic */ LocationBasedWifiViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05992(LocationBasedWifiViewModel locationBasedWifiViewModel, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = locationBasedWifiViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05992(this.$viewModel, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05992) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        StateFlow updateDeXWifiIconModel = this.$viewModel.commonImpl.getUpdateDeXWifiIconModel();
                        this.label = 1;
                        if (FlowKt.collect(updateDeXWifiIconModel, this) == coroutineSingletons) {
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
            public AnonymousClass1(Ref$BooleanRef ref$BooleanRef, LocationBasedWifiViewModel locationBasedWifiViewModel, Continuation continuation) {
                super(2, continuation);
                this.$isCollecting = ref$BooleanRef;
                this.$viewModel = locationBasedWifiViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isCollecting, this.$viewModel, continuation);
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
                        this.$isCollecting.element = true;
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C05981(this.$viewModel, null), 7);
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C05992(this.$viewModel, null), 7);
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
        public AnonymousClass2(Ref$BooleanRef ref$BooleanRef, LocationBasedWifiViewModel locationBasedWifiViewModel, Continuation continuation) {
            super(3, continuation);
            this.$isCollecting = ref$BooleanRef;
            this.$viewModel = locationBasedWifiViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$isCollecting, this.$viewModel, (Continuation) obj3);
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
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isCollecting, this.$viewModel, null);
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

    static {
        new WifiViewBinder();
    }

    private WifiViewBinder() {
    }

    /* JADX WARN: Type inference failed for: r0v22, types: [com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$3] */
    public static final AnonymousClass3 bind(ViewGroup viewGroup, final LocationBasedWifiViewModel locationBasedWifiViewModel) {
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.wifi_group);
        ImageView imageView = (ImageView) viewGroup.requireViewById(R.id.wifi_signal);
        StatusBarIconView statusBarIconView = (StatusBarIconView) viewGroup.requireViewById(R.id.status_bar_dot);
        ImageView imageView2 = (ImageView) viewGroup.requireViewById(R.id.wifi_in);
        ImageView imageView3 = (ImageView) viewGroup.requireViewById(R.id.wifi_out);
        View viewRequireViewById = viewGroup.requireViewById(R.id.inout_container);
        View viewRequireViewById2 = viewGroup.requireViewById(R.id.wifi_airplane_spacer);
        View viewRequireViewById3 = viewGroup.requireViewById(R.id.wifi_signal_spacer);
        ImageView imageView4 = (ImageView) viewGroup.requireViewById(R.id.wifi_activity);
        viewGroup.setVisibility(0);
        imageView.setVisibility(0);
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(2);
        locationBasedWifiViewModel.getClass();
        final StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(-1);
        final StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(-1);
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(ref$BooleanRef, stateFlowImplMutableStateFlow, viewGroup2, statusBarIconView, locationBasedWifiViewModel, viewGroup, imageView, imageView4, stateFlowImplMutableStateFlow2, imageView2, imageView3, stateFlowImplMutableStateFlow3, viewRequireViewById, viewRequireViewById2, viewRequireViewById3, null));
        if (locationBasedWifiViewModel.location == StatusBarLocation.HOME) {
            RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass2(ref$BooleanRef, locationBasedWifiViewModel, null));
        }
        return new ModernStatusBarViewBinding() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder.bind.3
            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final boolean getShouldIconBeVisible() {
                return locationBasedWifiViewModel.commonImpl.getWifiIcon().getValue() instanceof WifiIcon.Visible;
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final boolean isCollecting() {
                return ref$BooleanRef.element;
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onDecorTintChanged(int i) {
                stateFlowImplMutableStateFlow3.setValue(Integer.valueOf(i));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onIconTintChanged(int i, int i2) {
                stateFlowImplMutableStateFlow2.setValue(Integer.valueOf(i));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onVisibilityStateChanged(int i) {
                stateFlowImplMutableStateFlow.setValue(Integer.valueOf(i));
            }
        };
    }
}
