package com.android.systemui.keyguard.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.AuthKeyguardMessageArea;
import com.android.systemui.deviceentry.shared.model.BiometricMessage;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerMessageAreaViewModel;
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

/* loaded from: classes2.dex */
final class AlternateBouncerMessageAreaViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AuthKeyguardMessageArea $view;
    final /* synthetic */ AlternateBouncerMessageAreaViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerMessageAreaViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AuthKeyguardMessageArea $view;
        final /* synthetic */ AlternateBouncerMessageAreaViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerMessageAreaViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02301 extends SuspendLambda implements Function2 {
            final /* synthetic */ AuthKeyguardMessageArea $view;
            final /* synthetic */ AlternateBouncerMessageAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02301(AlternateBouncerMessageAreaViewModel alternateBouncerMessageAreaViewModel, AuthKeyguardMessageArea authKeyguardMessageArea, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerMessageAreaViewModel;
                this.$view = authKeyguardMessageArea;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02301(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02301) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.message;
                    final AuthKeyguardMessageArea authKeyguardMessageArea = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerMessageAreaViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BiometricMessage biometricMessage = (BiometricMessage) obj2;
                            AuthKeyguardMessageArea authKeyguardMessageArea2 = authKeyguardMessageArea;
                            if (biometricMessage == null) {
                                authKeyguardMessageArea2.setMessage("", true);
                            } else {
                                authKeyguardMessageArea2.setMessage(biometricMessage.message, true);
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
        public AnonymousClass1(AlternateBouncerMessageAreaViewModel alternateBouncerMessageAreaViewModel, AuthKeyguardMessageArea authKeyguardMessageArea, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = alternateBouncerMessageAreaViewModel;
            this.$view = authKeyguardMessageArea;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, continuation);
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
            CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C02301(this.$viewModel, this.$view, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerMessageAreaViewBinder$bind$1(AlternateBouncerMessageAreaViewModel alternateBouncerMessageAreaViewModel, AuthKeyguardMessageArea authKeyguardMessageArea, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = alternateBouncerMessageAreaViewModel;
        this.$view = authKeyguardMessageArea;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerMessageAreaViewBinder$bind$1 alternateBouncerMessageAreaViewBinder$bind$1 = new AlternateBouncerMessageAreaViewBinder$bind$1(this.$viewModel, this.$view, (Continuation) obj3);
        alternateBouncerMessageAreaViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return alternateBouncerMessageAreaViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, null);
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
