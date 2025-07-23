package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AlternateBouncerUdfpsViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DeviceEntryIconView $view;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ DeviceEntryIconView $view;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C01261 extends SuspendLambda implements Function2 {
            final /* synthetic */ DeviceEntryIconView $view;
            final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01261(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerUdfpsIconViewModel;
                this.$view = deviceEntryIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01261(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01261) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel = this.$viewModel;
                    ChannelFlowTransformLatest channelFlowTransformLatest = alternateBouncerUdfpsIconViewModel.accessibilityDelegateHint;
                    final DeviceEntryIconView deviceEntryIconView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            DeviceEntryIconView.AccessibilityHintType accessibilityHintType = (DeviceEntryIconView.AccessibilityHintType) obj2;
                            DeviceEntryIconView deviceEntryIconView2 = DeviceEntryIconView.this;
                            deviceEntryIconView2.accessibilityHintType = accessibilityHintType;
                            if (accessibilityHintType != DeviceEntryIconView.AccessibilityHintType.NONE) {
                                final AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel2 = alternateBouncerUdfpsIconViewModel;
                                deviceEntryIconView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.1.1.1.1.1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AlternateBouncerUdfpsIconViewModel.this.statusBarKeyguardViewManager.showPrimaryBouncer("AlternateBouncerUdfpsIconViewModel#onTapped", true);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ DeviceEntryIconView $view;
            final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerUdfpsIconViewModel;
                this.$view = deviceEntryIconView;
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
                    AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1 alternateBouncerUdfpsIconViewModel$special$$inlined$map$1 = this.$viewModel.alpha;
                    final DeviceEntryIconView deviceEntryIconView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            DeviceEntryIconView.this.setAlpha(((Number) obj2).floatValue());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (alternateBouncerUdfpsIconViewModel$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(DeviceEntryIconView deviceEntryIconView, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, Continuation continuation) {
            super(2, continuation);
            this.$view = deviceEntryIconView;
            this.$viewModel = alternateBouncerUdfpsIconViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, continuation);
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
            this.$view.setAlpha(0.0f);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01261(this.$viewModel, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerUdfpsViewBinder$bind$1(DeviceEntryIconView deviceEntryIconView, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, Continuation continuation) {
        super(3, continuation);
        this.$view = deviceEntryIconView;
        this.$viewModel = alternateBouncerUdfpsIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerUdfpsViewBinder$bind$1 alternateBouncerUdfpsViewBinder$bind$1 = new AlternateBouncerUdfpsViewBinder$bind$1(this.$view, this.$viewModel, (Continuation) obj3);
        alternateBouncerUdfpsViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return alternateBouncerUdfpsViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, null);
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
