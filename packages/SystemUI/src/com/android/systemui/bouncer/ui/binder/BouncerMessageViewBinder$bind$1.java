package com.android.systemui.bouncer.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.BouncerKeyguardMessageArea;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecMessageAreaController;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.bouncer.shared.model.Message;
import com.android.systemui.bouncer.ui.BouncerMessageView;
import com.android.systemui.log.BouncerLogger;
import com.android.systemui.log.BouncerLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerMessageViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BouncerLogger $bouncerLogger;
    final /* synthetic */ KeyguardMessageAreaController.Factory $factory;
    final /* synthetic */ BouncerMessageInteractor $interactor;
    final /* synthetic */ BouncerMessageView $view;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ BouncerLogger $bouncerLogger;
        final /* synthetic */ BouncerMessageInteractor $interactor;
        final /* synthetic */ BouncerMessageView $view;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00651 extends SuspendLambda implements Function2 {
            final /* synthetic */ BouncerLogger $bouncerLogger;
            final /* synthetic */ BouncerMessageInteractor $interactor;
            final /* synthetic */ BouncerMessageView $view;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00651(BouncerMessageInteractor bouncerMessageInteractor, BouncerLogger bouncerLogger, BouncerMessageView bouncerMessageView, Continuation continuation) {
                super(2, continuation);
                this.$interactor = bouncerMessageInteractor;
                this.$bouncerLogger = bouncerLogger;
                this.$view = bouncerMessageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00651(this.$interactor, this.$bouncerLogger, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00651) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                            Message message;
                            Message message2;
                            Integer num;
                            Message message3;
                            Message message4;
                            Integer num2;
                            BouncerMessageModel bouncerMessageModel = (BouncerMessageModel) obj2;
                            BouncerLogger bouncerLogger2 = BouncerLogger.this;
                            bouncerLogger2.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            BouncerLogger$$ExternalSyntheticLambda0 bouncerLogger$$ExternalSyntheticLambda0 = new BouncerLogger$$ExternalSyntheticLambda0(1);
                            LogBuffer logBuffer = bouncerLogger2.buffer;
                            LogMessage obtain = logBuffer.obtain("BouncerLog", logLevel, bouncerLogger$$ExternalSyntheticLambda0, null);
                            int i2 = -1;
                            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                            logMessageImpl.int1 = (bouncerMessageModel == null || (message4 = bouncerMessageModel.message) == null || (num2 = message4.messageResId) == null) ? -1 : num2.intValue();
                            logMessageImpl.str1 = (bouncerMessageModel == null || (message3 = bouncerMessageModel.message) == null) ? null : message3.message;
                            if (bouncerMessageModel != null && (message2 = bouncerMessageModel.secondaryMessage) != null && (num = message2.messageResId) != null) {
                                i2 = num.intValue();
                            }
                            logMessageImpl.int2 = i2;
                            logMessageImpl.str2 = (bouncerMessageModel == null || (message = bouncerMessageModel.secondaryMessage) == null) ? null : message.message;
                            logBuffer.commit(obtain);
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            LogBuffer.log$default(bouncerLogger.buffer, "BouncerLog", LogLevel.DEBUG, "Starting BouncerMessageInteractor.bouncerMessage collector");
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00651(this.$interactor, this.$bouncerLogger, this.$view, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
