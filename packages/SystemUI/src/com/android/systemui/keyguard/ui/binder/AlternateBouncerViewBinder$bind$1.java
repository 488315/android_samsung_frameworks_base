package com.android.systemui.keyguard.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.ui.SwipeUpAnywhereGestureHandler;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel$special$$inlined$map$1;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.statusbar.gesture.TapGestureDetector;
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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AlternateBouncerViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AlternateBouncerDependencies $alternateBouncerDependencies;
    final /* synthetic */ ScrimView $scrim;
    final /* synthetic */ SwipeUpAnywhereGestureHandler $swipeUpAnywhereGestureHandler;
    final /* synthetic */ TapGestureDetector $tapGestureDetector;
    final /* synthetic */ AlternateBouncerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AlternateBouncerDependencies $alternateBouncerDependencies;
        final /* synthetic */ ScrimView $scrim;
        final /* synthetic */ SwipeUpAnywhereGestureHandler $swipeUpAnywhereGestureHandler;
        final /* synthetic */ TapGestureDetector $tapGestureDetector;
        final /* synthetic */ AlternateBouncerViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C01341 extends SuspendLambda implements Function2 {
            final /* synthetic */ AlternateBouncerDependencies $alternateBouncerDependencies;
            final /* synthetic */ SwipeUpAnywhereGestureHandler $swipeUpAnywhereGestureHandler;
            final /* synthetic */ TapGestureDetector $tapGestureDetector;
            final /* synthetic */ AlternateBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01341(AlternateBouncerViewModel alternateBouncerViewModel, SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler, TapGestureDetector tapGestureDetector, AlternateBouncerDependencies alternateBouncerDependencies, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerViewModel;
                this.$swipeUpAnywhereGestureHandler = swipeUpAnywhereGestureHandler;
                this.$tapGestureDetector = tapGestureDetector;
                this.$alternateBouncerDependencies = alternateBouncerDependencies;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01341(this.$viewModel, this.$swipeUpAnywhereGestureHandler, this.$tapGestureDetector, this.$alternateBouncerDependencies, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01341) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final AlternateBouncerViewModel alternateBouncerViewModel = this.$viewModel;
                    Flow flow = alternateBouncerViewModel.registerForDismissGestures;
                    final SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler = this.$swipeUpAnywhereGestureHandler;
                    final TapGestureDetector tapGestureDetector = this.$tapGestureDetector;
                    final AlternateBouncerDependencies alternateBouncerDependencies = this.$alternateBouncerDependencies;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            TapGestureDetector tapGestureDetector2 = tapGestureDetector;
                            SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler2 = SwipeUpAnywhereGestureHandler.this;
                            if (booleanValue) {
                                AlternateBouncerDependencies alternateBouncerDependencies2 = alternateBouncerDependencies;
                                AlternateBouncerViewModel alternateBouncerViewModel2 = alternateBouncerViewModel;
                                swipeUpAnywhereGestureHandler2.addOnGestureDetectedCallback("AlternateBouncer-SWIPE", new AlternateBouncerViewBinder$bind$1$1$$ExternalSyntheticLambda0(1, alternateBouncerDependencies2, alternateBouncerViewModel2));
                                tapGestureDetector2.addOnGestureDetectedCallback("AlternateBouncer-TAP", new AlternateBouncerViewBinder$bind$1$1$$ExternalSyntheticLambda0(2, alternateBouncerDependencies2, alternateBouncerViewModel2));
                            } else {
                                swipeUpAnywhereGestureHandler2.removeOnGestureDetectedCallback("AlternateBouncer-SWIPE");
                                tapGestureDetector2.removeOnGestureDetectedCallback("AlternateBouncer-TAP");
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ ScrimView $scrim;
            final /* synthetic */ AlternateBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(AlternateBouncerViewModel alternateBouncerViewModel, ScrimView scrimView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerViewModel;
                this.$scrim = scrimView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$scrim, continuation);
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
                    AlternateBouncerViewModel$special$$inlined$map$1 alternateBouncerViewModel$special$$inlined$map$1 = this.$viewModel.scrimAlpha;
                    final ScrimView scrimView = this.$scrim;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ScrimView.this.setViewAlpha(((Number) obj2).floatValue());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (alternateBouncerViewModel$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ ScrimView $scrim;
            final /* synthetic */ AlternateBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(AlternateBouncerViewModel alternateBouncerViewModel, ScrimView scrimView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerViewModel;
                this.$scrim = scrimView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$scrim, continuation);
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
                    FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = this.$viewModel.scrimColor;
                    final ScrimView scrimView = this.$scrim;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder.bind.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ScrimView.this.setTint(((Number) obj2).intValue());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(AlternateBouncerViewModel alternateBouncerViewModel, SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler, TapGestureDetector tapGestureDetector, AlternateBouncerDependencies alternateBouncerDependencies, ScrimView scrimView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = alternateBouncerViewModel;
            this.$swipeUpAnywhereGestureHandler = swipeUpAnywhereGestureHandler;
            this.$tapGestureDetector = tapGestureDetector;
            this.$alternateBouncerDependencies = alternateBouncerDependencies;
            this.$scrim = scrimView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$swipeUpAnywhereGestureHandler, this.$tapGestureDetector, this.$alternateBouncerDependencies, this.$scrim, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01341(this.$viewModel, this.$swipeUpAnywhereGestureHandler, this.$tapGestureDetector, this.$alternateBouncerDependencies, null), 6).invokeOnCompletion(new AlternateBouncerViewBinder$bind$1$1$$ExternalSyntheticLambda0(0, this.$swipeUpAnywhereGestureHandler, this.$tapGestureDetector));
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$scrim, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$scrim, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerViewBinder$bind$1(AlternateBouncerViewModel alternateBouncerViewModel, SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler, TapGestureDetector tapGestureDetector, AlternateBouncerDependencies alternateBouncerDependencies, ScrimView scrimView, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = alternateBouncerViewModel;
        this.$swipeUpAnywhereGestureHandler = swipeUpAnywhereGestureHandler;
        this.$tapGestureDetector = tapGestureDetector;
        this.$alternateBouncerDependencies = alternateBouncerDependencies;
        this.$scrim = scrimView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerViewBinder$bind$1 alternateBouncerViewBinder$bind$1 = new AlternateBouncerViewBinder$bind$1(this.$viewModel, this.$swipeUpAnywhereGestureHandler, this.$tapGestureDetector, this.$alternateBouncerDependencies, this.$scrim, (Continuation) obj3);
        alternateBouncerViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return alternateBouncerViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$swipeUpAnywhereGestureHandler, this.$tapGestureDetector, this.$alternateBouncerDependencies, this.$scrim, null);
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
