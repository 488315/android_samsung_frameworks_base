package com.android.systemui.keyguard.ui.binder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.common.ui.binder.TextViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardTouchHandlingViewModel;
import com.android.systemui.keyguard.util.WallpaperPickerIntentUtils;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
final class KeyguardSettingsViewBinder$bind$disposableHandle$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ActivityStarter $activityStarter;
    final /* synthetic */ KeyguardRootViewModel $rootViewModel;
    final /* synthetic */ KeyguardTouchHandlingViewModel $touchHandlingViewModel;
    final /* synthetic */ VibratorHelper $vibratorHelper;
    final /* synthetic */ View $view;
    final /* synthetic */ KeyguardSettingsMenuViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ActivityStarter $activityStarter;
        final /* synthetic */ KeyguardRootViewModel $rootViewModel;
        final /* synthetic */ KeyguardTouchHandlingViewModel $touchHandlingViewModel;
        final /* synthetic */ VibratorHelper $vibratorHelper;
        final /* synthetic */ View $view;
        final /* synthetic */ KeyguardSettingsMenuViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C03181 extends SuspendLambda implements Function2 {
            final /* synthetic */ VibratorHelper $vibratorHelper;
            final /* synthetic */ View $view;
            final /* synthetic */ KeyguardSettingsMenuViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03181(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, View view, VibratorHelper vibratorHelper, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardSettingsMenuViewModel;
                this.$view = view;
                this.$vibratorHelper = vibratorHelper;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03181(this.$viewModel, this.$view, this.$vibratorHelper, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03181) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(this.$viewModel.isVisible);
                    final View view = this.$view;
                    final VibratorHelper vibratorHelper = this.$vibratorHelper;
                    final KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder.bind.disposableHandle.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            final boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                            KeyguardSettingsViewBinder keyguardSettingsViewBinder = KeyguardSettingsViewBinder.INSTANCE;
                            final View view2 = view;
                            keyguardSettingsViewBinder.getClass();
                            view2.animate().withStartAction(new Runnable() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$animateVisibility$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    if (zBooleanValue) {
                                        view2.setAlpha(0.0f);
                                        view2.setVisibility(0);
                                    }
                                }
                            }).alpha(zBooleanValue ? 1.0f : 0.0f).withEndAction(new Runnable() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$animateVisibility$2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    if (zBooleanValue) {
                                        return;
                                    }
                                    view2.setVisibility(8);
                                }
                            }).start();
                            if (zBooleanValue) {
                                KeyguardBottomAreaVibrations.INSTANCE.getClass();
                                vibratorHelper.vibrate(KeyguardBottomAreaVibrations.Activated);
                                TextView textView = (TextView) view.requireViewById(R.id.text);
                                View view3 = view;
                                KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel2 = keyguardSettingsMenuViewModel;
                                view3.setOnTouchListener(new KeyguardSettingsButtonOnTouchListener(keyguardSettingsMenuViewModel2));
                                IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                Icon.Resource resource = keyguardSettingsMenuViewModel2.icon;
                                ImageView imageView = (ImageView) view.requireViewById(R.id.icon);
                                iconViewBinder.getClass();
                                IconViewBinder.bind(resource, imageView);
                                TextViewBinder.INSTANCE.getClass();
                                TextViewBinder.bind(textView, keyguardSettingsMenuViewModel2.text);
                                textView.sendAccessibilityEvent(8);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowDistinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ ActivityStarter $activityStarter;
            final /* synthetic */ View $view;
            final /* synthetic */ KeyguardSettingsMenuViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, ActivityStarter activityStarter, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardSettingsMenuViewModel;
                this.$activityStarter = activityStarter;
                this.$view = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$activityStarter, this.$view, continuation);
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
                    final ReadonlyStateFlow readonlyStateFlow = this.$viewModel.shouldOpenSettings;
                    Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1$2$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1$2$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1$2$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                Object L$1;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i = anonymousClass1.label;
                                    if ((i & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = new AnonymousClass1(continuation);
                                    }
                                }
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i2 = anonymousClass1.label;
                                if (i2 == 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    if (((Boolean) obj).booleanValue()) {
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                } else {
                                    if (i2 != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final ActivityStarter activityStarter = this.$activityStarter;
                    final View view = this.$view;
                    final KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder.bind.disposableHandle.1.1.2.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            KeyguardSettingsViewBinder keyguardSettingsViewBinder = KeyguardSettingsViewBinder.INSTANCE;
                            View view2 = view;
                            keyguardSettingsViewBinder.getClass();
                            WallpaperPickerIntentUtils wallpaperPickerIntentUtils = WallpaperPickerIntentUtils.INSTANCE;
                            Context context = view2.getContext();
                            wallpaperPickerIntentUtils.getClass();
                            Intent intent = new Intent("android.intent.action.SET_WALLPAPER");
                            intent.setFlags(268435456);
                            String string = context.getString(R.string.config_wallpaperPickerPackage);
                            string.getClass();
                            if (string.length() <= 0) {
                                string = null;
                            }
                            if (string != null) {
                                intent.setPackage(string);
                            }
                            intent.putExtra("com.android.wallpaper.LAUNCH_SOURCE", "app_launched_keyguard");
                            activityStarter.postStartActivityDismissingKeyguard(intent, 0, ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, view2, null, 62), view2.getContext().getString(R.string.keyguard_unlock_to_customize_ls));
                            keyguardSettingsMenuViewModel.interactor._shouldOpenSettings.updateState(null, Boolean.FALSE);
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardRootViewModel $rootViewModel;
            final /* synthetic */ KeyguardTouchHandlingViewModel $touchHandlingViewModel;
            final /* synthetic */ View $view;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardRootViewModel keyguardRootViewModel, View view, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, Continuation continuation) {
                super(2, continuation);
                this.$rootViewModel = keyguardRootViewModel;
                this.$view = view;
                this.$touchHandlingViewModel = keyguardTouchHandlingViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$rootViewModel, this.$view, this.$touchHandlingViewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Flow flow;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    KeyguardRootViewModel keyguardRootViewModel = this.$rootViewModel;
                    if (keyguardRootViewModel != null && (flow = keyguardRootViewModel.lastRootViewTapPosition) != null) {
                        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(flow);
                        final View view = this.$view;
                        final KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel = this.$touchHandlingViewModel;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder.bind.disposableHandle.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Point point = (Point) obj2;
                                if (view.getVisibility() == 0) {
                                    Rect rect = new Rect();
                                    view.getHitRect(rect);
                                    if (!rect.contains(point.x, point.y)) {
                                        keyguardTouchHandlingViewModel.interactor.hideMenu();
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder$bind$disposableHandle$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $view;
            final /* synthetic */ KeyguardSettingsMenuViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardSettingsMenuViewModel;
                this.$view = view;
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
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.textSize;
                    final View view = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder.bind.disposableHandle.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((TextView) view.requireViewById(R.id.text)).setTextSize(0, ((Number) obj2).intValue());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(ActivityStarter activityStarter, KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, View view, VibratorHelper vibratorHelper, KeyguardRootViewModel keyguardRootViewModel, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, Continuation continuation) {
            super(2, continuation);
            this.$activityStarter = activityStarter;
            this.$viewModel = keyguardSettingsMenuViewModel;
            this.$view = view;
            this.$vibratorHelper = vibratorHelper;
            this.$rootViewModel = keyguardRootViewModel;
            this.$touchHandlingViewModel = keyguardTouchHandlingViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$activityStarter, this.$viewModel, this.$view, this.$vibratorHelper, this.$rootViewModel, this.$touchHandlingViewModel, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03181(this.$viewModel, this.$view, this.$vibratorHelper, null), 6);
            ActivityStarter activityStarter = this.$activityStarter;
            if (activityStarter != null) {
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, activityStarter, this.$view, null), 6);
            }
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$rootViewModel, this.$view, this.$touchHandlingViewModel, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$view, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardSettingsViewBinder$bind$disposableHandle$1(ActivityStarter activityStarter, KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, View view, VibratorHelper vibratorHelper, KeyguardRootViewModel keyguardRootViewModel, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, Continuation continuation) {
        super(3, continuation);
        this.$activityStarter = activityStarter;
        this.$viewModel = keyguardSettingsMenuViewModel;
        this.$view = view;
        this.$vibratorHelper = vibratorHelper;
        this.$rootViewModel = keyguardRootViewModel;
        this.$touchHandlingViewModel = keyguardTouchHandlingViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardSettingsViewBinder$bind$disposableHandle$1 keyguardSettingsViewBinder$bind$disposableHandle$1 = new KeyguardSettingsViewBinder$bind$disposableHandle$1(this.$activityStarter, this.$viewModel, this.$view, this.$vibratorHelper, this.$rootViewModel, this.$touchHandlingViewModel, (Continuation) obj3);
        keyguardSettingsViewBinder$bind$disposableHandle$1.L$0 = (LifecycleOwner) obj;
        return keyguardSettingsViewBinder$bind$disposableHandle$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$activityStarter, this.$viewModel, this.$view, this.$vibratorHelper, this.$rootViewModel, this.$touchHandlingViewModel, null);
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
