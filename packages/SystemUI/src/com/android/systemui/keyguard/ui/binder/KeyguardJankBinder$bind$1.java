package com.android.systemui.keyguard.ui.binder;

import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardJankViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardJankBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardClockInteractor $clockInteractor;
    final /* synthetic */ InteractionJankMonitor $jankMonitor;
    final /* synthetic */ KeyguardViewMediator $keyguardViewMediator;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ KeyguardJankViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardJankBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardClockInteractor $clockInteractor;
        final /* synthetic */ InteractionJankMonitor $jankMonitor;
        final /* synthetic */ KeyguardViewMediator $keyguardViewMediator;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardJankViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardJankBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C01741 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardClockInteractor $clockInteractor;
            final /* synthetic */ InteractionJankMonitor $jankMonitor;
            final /* synthetic */ KeyguardViewMediator $keyguardViewMediator;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardJankViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01741(KeyguardJankViewModel keyguardJankViewModel, KeyguardViewMediator keyguardViewMediator, KeyguardClockInteractor keyguardClockInteractor, ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardJankViewModel;
                this.$keyguardViewMediator = keyguardViewMediator;
                this.$clockInteractor = keyguardClockInteractor;
                this.$view = viewGroup;
                this.$jankMonitor = interactionJankMonitor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01741(this.$viewModel, this.$keyguardViewMediator, this.$clockInteractor, this.$view, this.$jankMonitor, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01741) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.goneToAodTransition;
                    final KeyguardViewMediator keyguardViewMediator = this.$keyguardViewMediator;
                    final KeyguardClockInteractor keyguardClockInteractor = this.$clockInteractor;
                    final ViewGroup viewGroup = this.$view;
                    final InteractionJankMonitor interactionJankMonitor = this.$jankMonitor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardJankBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardViewMediator keyguardViewMediator2;
                            TransitionStep transitionStep = (TransitionStep) obj2;
                            KeyguardJankBinder.access$bind$processStep(keyguardClockInteractor, viewGroup, interactionJankMonitor, transitionStep, 41);
                            if (transitionStep.transitionState == TransitionState.FINISHED && (keyguardViewMediator2 = KeyguardViewMediator.this) != null) {
                                keyguardViewMediator2.maybeHandlePendingLock();
                            }
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardJankBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardClockInteractor $clockInteractor;
            final /* synthetic */ InteractionJankMonitor $jankMonitor;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardJankViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardJankViewModel keyguardJankViewModel, KeyguardClockInteractor keyguardClockInteractor, ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardJankViewModel;
                this.$clockInteractor = keyguardClockInteractor;
                this.$view = viewGroup;
                this.$jankMonitor = interactionJankMonitor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$clockInteractor, this.$view, this.$jankMonitor, continuation);
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
                    Flow flow = this.$viewModel.lockscreenToAodTransition;
                    final KeyguardClockInteractor keyguardClockInteractor = this.$clockInteractor;
                    final ViewGroup viewGroup = this.$view;
                    final InteractionJankMonitor interactionJankMonitor = this.$jankMonitor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardJankBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ViewGroup viewGroup2 = viewGroup;
                            InteractionJankMonitor interactionJankMonitor2 = interactionJankMonitor;
                            KeyguardJankBinder.access$bind$processStep(KeyguardClockInteractor.this, viewGroup2, interactionJankMonitor2, (TransitionStep) obj2, 24);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardJankBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardClockInteractor $clockInteractor;
            final /* synthetic */ InteractionJankMonitor $jankMonitor;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardJankViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardJankViewModel keyguardJankViewModel, KeyguardClockInteractor keyguardClockInteractor, ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardJankViewModel;
                this.$clockInteractor = keyguardClockInteractor;
                this.$view = viewGroup;
                this.$jankMonitor = interactionJankMonitor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$clockInteractor, this.$view, this.$jankMonitor, continuation);
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
                    Flow flow = this.$viewModel.aodToLockscreenTransition;
                    final KeyguardClockInteractor keyguardClockInteractor = this.$clockInteractor;
                    final ViewGroup viewGroup = this.$view;
                    final InteractionJankMonitor interactionJankMonitor = this.$jankMonitor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardJankBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ViewGroup viewGroup2 = viewGroup;
                            InteractionJankMonitor interactionJankMonitor2 = interactionJankMonitor;
                            KeyguardJankBinder.access$bind$processStep(KeyguardClockInteractor.this, viewGroup2, interactionJankMonitor2, (TransitionStep) obj2, 23);
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardJankViewModel keyguardJankViewModel, KeyguardViewMediator keyguardViewMediator, KeyguardClockInteractor keyguardClockInteractor, ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardJankViewModel;
            this.$keyguardViewMediator = keyguardViewMediator;
            this.$clockInteractor = keyguardClockInteractor;
            this.$view = viewGroup;
            this.$jankMonitor = interactionJankMonitor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$keyguardViewMediator, this.$clockInteractor, this.$view, this.$jankMonitor, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01741(this.$viewModel, this.$keyguardViewMediator, this.$clockInteractor, this.$view, this.$jankMonitor, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$clockInteractor, this.$view, this.$jankMonitor, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$clockInteractor, this.$view, this.$jankMonitor, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardJankBinder$bind$1(KeyguardJankViewModel keyguardJankViewModel, KeyguardViewMediator keyguardViewMediator, KeyguardClockInteractor keyguardClockInteractor, ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardJankViewModel;
        this.$keyguardViewMediator = keyguardViewMediator;
        this.$clockInteractor = keyguardClockInteractor;
        this.$view = viewGroup;
        this.$jankMonitor = interactionJankMonitor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardJankBinder$bind$1 keyguardJankBinder$bind$1 = new KeyguardJankBinder$bind$1(this.$viewModel, this.$keyguardViewMediator, this.$clockInteractor, this.$view, this.$jankMonitor, (Continuation) obj3);
        keyguardJankBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return keyguardJankBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$keyguardViewMediator, this.$clockInteractor, this.$view, this.$jankMonitor, null);
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
