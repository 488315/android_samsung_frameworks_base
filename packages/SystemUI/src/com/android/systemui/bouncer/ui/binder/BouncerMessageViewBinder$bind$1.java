package com.android.systemui.bouncer.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.BouncerKeyguardMessageArea;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecMessageAreaController;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.bouncer.ui.BouncerMessageView;
import com.android.systemui.log.BouncerLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

final class BouncerMessageViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BouncerLogger $bouncerLogger;
    final /* synthetic */ KeyguardMessageAreaController.Factory $factory;
    final /* synthetic */ BouncerMessageInteractor $interactor;
    final /* synthetic */ BouncerMessageView $view;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ BouncerLogger $bouncerLogger;
        final /* synthetic */ BouncerMessageInteractor $interactor;
        final /* synthetic */ BouncerMessageView $view;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00531 extends SuspendLambda implements Function2 {
            final /* synthetic */ BouncerLogger $bouncerLogger;
            final /* synthetic */ BouncerMessageInteractor $interactor;
            final /* synthetic */ BouncerMessageView $view;
            int label;

            public C00531(BouncerMessageInteractor bouncerMessageInteractor, BouncerLogger bouncerLogger, BouncerMessageView bouncerMessageView, Continuation continuation) {
                super(2, continuation);
                this.$interactor = bouncerMessageInteractor;
                this.$bouncerLogger = bouncerLogger;
                this.$view = bouncerMessageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00531(this.$interactor, this.$bouncerLogger, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00531) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlowImpl stateFlowImpl = this.$interactor.bouncerMessage;
                    final BouncerLogger bouncerLogger = this.$bouncerLogger;
                    final BouncerMessageView bouncerMessageView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BouncerMessageModel bouncerMessageModel = (BouncerMessageModel) obj2;
                            BouncerLogger.this.bouncerMessageUpdated(bouncerMessageModel);
                            BouncerMessageViewBinder bouncerMessageViewBinder = BouncerMessageViewBinder.INSTANCE;
                            BouncerMessageView bouncerMessageView2 = bouncerMessageView;
                            BouncerMessageViewBinder.access$updateView(bouncerMessageViewBinder, bouncerMessageView2.primaryMessage, bouncerMessageView2.primaryMessageView, bouncerMessageModel != null ? bouncerMessageModel.message : null, true);
                            BouncerMessageViewBinder.access$updateView(bouncerMessageViewBinder, bouncerMessageView2.secondaryMessage, bouncerMessageView2.secondaryMessageView, bouncerMessageModel != null ? bouncerMessageModel.secondaryMessage : null, false);
                            bouncerMessageView2.requestLayout();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
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

        public AnonymousClass1(BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, BouncerMessageView bouncerMessageView, Continuation continuation) {
            super(2, continuation);
            this.$bouncerLogger = bouncerLogger;
            this.$interactor = bouncerMessageInteractor;
            this.$view = bouncerMessageView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$bouncerLogger, this.$interactor, this.$view, continuation);
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
            BouncerLogger bouncerLogger = this.$bouncerLogger;
            bouncerLogger.getClass();
            bouncerLogger.buffer.log("BouncerLog", LogLevel.DEBUG, "Starting BouncerMessageInteractor.bouncerMessage collector", null);
            BuildersKt.launch$default(coroutineScope, null, null, new C00531(this.$interactor, this.$bouncerLogger, this.$view, null), 3);
            return Unit.INSTANCE;
        }
    }

    public BouncerMessageViewBinder$bind$1(BouncerMessageView bouncerMessageView, KeyguardMessageAreaController.Factory factory, BouncerLogger bouncerLogger, BouncerMessageInteractor bouncerMessageInteractor, Continuation continuation) {
        super(3, continuation);
        this.$view = bouncerMessageView;
        this.$factory = factory;
        this.$bouncerLogger = bouncerLogger;
        this.$interactor = bouncerMessageInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BouncerMessageViewBinder$bind$1 bouncerMessageViewBinder$bind$1 = new BouncerMessageViewBinder$bind$1(this.$view, this.$factory, this.$bouncerLogger, this.$interactor, (Continuation) obj3);
        bouncerMessageViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return bouncerMessageViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            BouncerMessageView bouncerMessageView = this.$view;
            KeyguardMessageAreaController.Factory factory = this.$factory;
            BouncerKeyguardMessageArea bouncerKeyguardMessageArea = bouncerMessageView.primaryMessageView;
            KeyguardUpdateMonitor keyguardUpdateMonitor = factory.mKeyguardUpdateMonitor;
            ConfigurationController configurationController = factory.mConfigurationController;
            KeyguardSecMessageAreaController keyguardSecMessageAreaController = new KeyguardSecMessageAreaController(bouncerKeyguardMessageArea, keyguardUpdateMonitor, configurationController);
            bouncerMessageView.primaryMessage = keyguardSecMessageAreaController;
            keyguardSecMessageAreaController.init();
            KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = new KeyguardSecMessageAreaController(bouncerMessageView.secondaryMessageView, factory.mKeyguardUpdateMonitor, configurationController);
            bouncerMessageView.secondaryMessage = keyguardSecMessageAreaController2;
            keyguardSecMessageAreaController2.init();
            KeyguardSecMessageAreaController keyguardSecMessageAreaController3 = this.$view.primaryMessage;
            if (keyguardSecMessageAreaController3 != null) {
                keyguardSecMessageAreaController3.setIsVisible(true);
            }
            KeyguardSecMessageAreaController keyguardSecMessageAreaController4 = this.$view.secondaryMessage;
            if (keyguardSecMessageAreaController4 != null) {
                keyguardSecMessageAreaController4.setIsVisible(true);
            }
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$bouncerLogger, this.$interactor, this.$view, null);
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
