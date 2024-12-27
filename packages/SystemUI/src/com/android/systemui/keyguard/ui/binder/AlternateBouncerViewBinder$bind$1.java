package com.android.systemui.keyguard.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.SwipeUpAnywhereGestureHandler;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.statusbar.gesture.TapGestureDetector;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;

final class AlternateBouncerViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AlternateBouncerDependencies $alternateBouncerDependencies;
    final /* synthetic */ ScrimView $scrim;
    final /* synthetic */ SwipeUpAnywhereGestureHandler $swipeUpAnywhereGestureHandler;
    final /* synthetic */ TapGestureDetector $tapGestureDetector;
    final /* synthetic */ AlternateBouncerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AlternateBouncerDependencies $alternateBouncerDependencies;
        final /* synthetic */ ScrimView $scrim;
        final /* synthetic */ SwipeUpAnywhereGestureHandler $swipeUpAnywhereGestureHandler;
        final /* synthetic */ TapGestureDetector $tapGestureDetector;
        final /* synthetic */ AlternateBouncerViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

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
            AlternateBouncerViewModel alternateBouncerViewModel = this.$viewModel;
            SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler = this.$swipeUpAnywhereGestureHandler;
            TapGestureDetector tapGestureDetector = this.$tapGestureDetector;
            AlternateBouncerDependencies alternateBouncerDependencies = this.$alternateBouncerDependencies;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            Job launch$default = BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1("AlternateBouncerViewBinder#viewModel.registerForDismissGestures", null, alternateBouncerViewModel, swipeUpAnywhereGestureHandler, tapGestureDetector, alternateBouncerDependencies), 2);
            final SwipeUpAnywhereGestureHandler swipeUpAnywhereGestureHandler2 = this.$swipeUpAnywhereGestureHandler;
            final TapGestureDetector tapGestureDetector2 = this.$tapGestureDetector;
            ((JobSupport) launch$default).invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder.bind.1.1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    SwipeUpAnywhereGestureHandler.this.removeOnGestureDetectedCallback("AlternateBouncer-SWIPE");
                    tapGestureDetector2.removeOnGestureDetectedCallback("AlternateBouncer-TAP");
                    return Unit.INSTANCE;
                }
            });
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2("AlternateBouncerViewBinder#viewModel.scrimAlpha", null, this.$viewModel, this.$scrim), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new AlternateBouncerViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$3("AlternateBouncerViewBinder#viewModel.scrimColor", null, this.$viewModel, this.$scrim), 2);
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
