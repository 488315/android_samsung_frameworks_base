package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.R;
import com.android.systemui.common.ui.view.TouchHandlingView;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
final class DeviceEntryIconViewBinder$bind$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ CoroutineScope $applicationScope;
    final /* synthetic */ MSDLPlayer $msdlPlayer;
    final /* synthetic */ TouchHandlingView $touchHandlingView;
    final /* synthetic */ VibratorHelper $vibratorHelper;
    final /* synthetic */ DeviceEntryIconView $view;
    final /* synthetic */ DeviceEntryIconViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ CoroutineScope $applicationScope;
        final /* synthetic */ MSDLPlayer $msdlPlayer;
        final /* synthetic */ TouchHandlingView $touchHandlingView;
        final /* synthetic */ VibratorHelper $vibratorHelper;
        final /* synthetic */ DeviceEntryIconView $view;
        final /* synthetic */ DeviceEntryIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$1, reason: invalid class name and collision with other inner class name */
        final class C02521 extends SuspendLambda implements Function2 {
            final /* synthetic */ TouchHandlingView $touchHandlingView;
            final /* synthetic */ DeviceEntryIconView $view;
            final /* synthetic */ DeviceEntryIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02521(DeviceEntryIconViewModel deviceEntryIconViewModel, TouchHandlingView touchHandlingView, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = deviceEntryIconViewModel;
                this.$touchHandlingView = touchHandlingView;
                this.$view = deviceEntryIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02521(this.$viewModel, this.$touchHandlingView, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02521) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.isVisible;
                    final TouchHandlingView touchHandlingView = this.$touchHandlingView;
                    final DeviceEntryIconView deviceEntryIconView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder.bind.3.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                            touchHandlingView.setVisibility(!zBooleanValue ? 4 : 0);
                            deviceEntryIconView.setClickable(zBooleanValue);
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ TouchHandlingView $touchHandlingView;
            final /* synthetic */ DeviceEntryIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(DeviceEntryIconViewModel deviceEntryIconViewModel, TouchHandlingView touchHandlingView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = deviceEntryIconViewModel;
                this.$touchHandlingView = touchHandlingView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$touchHandlingView, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.isLongPressEnabled;
                    final TouchHandlingView touchHandlingView = this.$touchHandlingView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder.bind.3.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            touchHandlingView.getInteractionHandler().isLongPressHandlingEnabled = ((Boolean) obj2).booleanValue();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ TouchHandlingView $touchHandlingView;
            final /* synthetic */ DeviceEntryIconView $view;
            final /* synthetic */ DeviceEntryIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(DeviceEntryIconViewModel deviceEntryIconViewModel, TouchHandlingView touchHandlingView, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = deviceEntryIconViewModel;
                this.$touchHandlingView = touchHandlingView;
                this.$view = deviceEntryIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$touchHandlingView, this.$view, continuation);
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
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.isUdfpsSupported;
                    final TouchHandlingView touchHandlingView = this.$touchHandlingView;
                    final DeviceEntryIconView deviceEntryIconView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder.bind.3.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Function0 function0;
                            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                            final DeviceEntryIconView deviceEntryIconView2 = deviceEntryIconView;
                            if (zBooleanValue) {
                                final int i2 = 0;
                                function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$3$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i2) {
                                            case 0:
                                                return Long.valueOf(deviceEntryIconView2.getResources().getInteger(R.integer.config_udfpsDeviceEntryIconLongPress));
                                            default:
                                                return Long.valueOf(deviceEntryIconView2.getResources().getInteger(R.integer.config_lockIconLongPress));
                                        }
                                    }
                                };
                            } else {
                                final int i3 = 1;
                                function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$3$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (i3) {
                                            case 0:
                                                return Long.valueOf(deviceEntryIconView2.getResources().getInteger(R.integer.config_udfpsDeviceEntryIconLongPress));
                                            default:
                                                return Long.valueOf(deviceEntryIconView2.getResources().getInteger(R.integer.config_lockIconLongPress));
                                        }
                                    }
                                };
                            }
                            touchHandlingView.getInteractionHandler().longPressDuration = function0;
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

        /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $applicationScope;
            final /* synthetic */ MSDLPlayer $msdlPlayer;
            final /* synthetic */ VibratorHelper $vibratorHelper;
            final /* synthetic */ DeviceEntryIconView $view;
            final /* synthetic */ DeviceEntryIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(DeviceEntryIconViewModel deviceEntryIconViewModel, DeviceEntryIconView deviceEntryIconView, MSDLPlayer mSDLPlayer, VibratorHelper vibratorHelper, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = deviceEntryIconViewModel;
                this.$view = deviceEntryIconView;
                this.$msdlPlayer = mSDLPlayer;
                this.$vibratorHelper = vibratorHelper;
                this.$applicationScope = coroutineScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$view, this.$msdlPlayer, this.$vibratorHelper, this.$applicationScope, continuation);
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
                    final DeviceEntryIconViewModel deviceEntryIconViewModel = this.$viewModel;
                    ChannelFlowTransformLatest channelFlowTransformLatest = deviceEntryIconViewModel.accessibilityDelegateHint;
                    final DeviceEntryIconView deviceEntryIconView = this.$view;
                    final MSDLPlayer mSDLPlayer = this.$msdlPlayer;
                    final VibratorHelper vibratorHelper = this.$vibratorHelper;
                    final CoroutineScope coroutineScope = this.$applicationScope;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder.bind.3.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            DeviceEntryIconView.AccessibilityHintType accessibilityHintType = (DeviceEntryIconView.AccessibilityHintType) obj2;
                            DeviceEntryIconView deviceEntryIconView2 = deviceEntryIconView;
                            deviceEntryIconView2.accessibilityHintType = accessibilityHintType;
                            if (accessibilityHintType != DeviceEntryIconView.AccessibilityHintType.NONE) {
                                deviceEntryIconView2.setOnClickListener(new View.OnClickListener(accessibilityHintType, mSDLPlayer, vibratorHelper, deviceEntryIconView2, coroutineScope, deviceEntryIconViewModel) { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder.bind.3.1.4.1.1
                                    public final /* synthetic */ CoroutineScope $applicationScope;
                                    public final /* synthetic */ VibratorHelper $vibratorHelper;
                                    public final /* synthetic */ DeviceEntryIconView $view;
                                    public final /* synthetic */ DeviceEntryIconViewModel $viewModel;

                                    /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1$4$1$1$1, reason: invalid class name and collision with other inner class name */
                                    final class C02581 extends SuspendLambda implements Function2 {
                                        final /* synthetic */ DeviceEntryIconView $view;
                                        final /* synthetic */ DeviceEntryIconViewModel $viewModel;
                                        int label;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public C02581(DeviceEntryIconView deviceEntryIconView, DeviceEntryIconViewModel deviceEntryIconViewModel, Continuation continuation) {
                                            super(2, continuation);
                                            this.$view = deviceEntryIconView;
                                            this.$viewModel = deviceEntryIconViewModel;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(Object obj, Continuation continuation) {
                                            return new C02581(this.$view, this.$viewModel, continuation);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            return ((C02581) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) throws Throwable {
                                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            if (i == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                this.$view.clearFocus();
                                                this.$view.clearAccessibilityFocus();
                                                DeviceEntryIconViewModel deviceEntryIconViewModel = this.$viewModel;
                                                this.label = 1;
                                                ((StatusBarKeyguardViewManager) ((KeyguardViewController) deviceEntryIconViewModel.keyguardViewController.get())).showPrimaryBouncer("DeviceEntryIconViewModel#onUserInteraction", true);
                                                SharedFlowImpl sharedFlowImpl = deviceEntryIconViewModel.deviceEntrySourceInteractor._attemptEnterDeviceFromDeviceEntryIcon;
                                                Object obj2 = Unit.INSTANCE;
                                                Object objEmit = sharedFlowImpl.emit(obj2, this);
                                                if (objEmit != coroutineSingletons) {
                                                    objEmit = obj2;
                                                }
                                                if (objEmit == coroutineSingletons) {
                                                    obj2 = objEmit;
                                                }
                                                if (obj2 == coroutineSingletons) {
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

                                    {
                                        this.$vibratorHelper = vibratorHelper;
                                        this.$view = deviceEntryIconView2;
                                        this.$applicationScope = coroutineScope;
                                        this.$viewModel = deviceEntryIconViewModel;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        VibratorHelper vibratorHelper2 = this.$vibratorHelper;
                                        DeviceEntryIconView deviceEntryIconView3 = this.$view;
                                        vibratorHelper2.getClass();
                                        deviceEntryIconView3.performHapticFeedback(16);
                                        CoroutineTracingKt.launchTraced$default(this.$applicationScope, null, null, new C02581(this.$view, this.$viewModel, null), 7);
                                    }
                                });
                            } else {
                                deviceEntryIconView2.setOnClickListener(null);
                            }
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
        public AnonymousClass1(DeviceEntryIconViewModel deviceEntryIconViewModel, TouchHandlingView touchHandlingView, DeviceEntryIconView deviceEntryIconView, MSDLPlayer mSDLPlayer, VibratorHelper vibratorHelper, CoroutineScope coroutineScope, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = deviceEntryIconViewModel;
            this.$touchHandlingView = touchHandlingView;
            this.$view = deviceEntryIconView;
            this.$msdlPlayer = mSDLPlayer;
            this.$vibratorHelper = vibratorHelper;
            this.$applicationScope = coroutineScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$touchHandlingView, this.$view, this.$msdlPlayer, this.$vibratorHelper, this.$applicationScope, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02521(this.$viewModel, this.$touchHandlingView, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$touchHandlingView, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$touchHandlingView, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$view, this.$msdlPlayer, this.$vibratorHelper, this.$applicationScope, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewBinder$bind$3(DeviceEntryIconViewModel deviceEntryIconViewModel, TouchHandlingView touchHandlingView, DeviceEntryIconView deviceEntryIconView, MSDLPlayer mSDLPlayer, VibratorHelper vibratorHelper, CoroutineScope coroutineScope, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = deviceEntryIconViewModel;
        this.$touchHandlingView = touchHandlingView;
        this.$view = deviceEntryIconView;
        this.$msdlPlayer = mSDLPlayer;
        this.$vibratorHelper = vibratorHelper;
        this.$applicationScope = coroutineScope;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryIconViewBinder$bind$3 deviceEntryIconViewBinder$bind$3 = new DeviceEntryIconViewBinder$bind$3(this.$viewModel, this.$touchHandlingView, this.$view, this.$msdlPlayer, this.$vibratorHelper, this.$applicationScope, (Continuation) obj3);
        deviceEntryIconViewBinder$bind$3.L$0 = (LifecycleOwner) obj;
        return deviceEntryIconViewBinder$bind$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$touchHandlingView, this.$view, this.$msdlPlayer, this.$vibratorHelper, this.$applicationScope, null);
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
