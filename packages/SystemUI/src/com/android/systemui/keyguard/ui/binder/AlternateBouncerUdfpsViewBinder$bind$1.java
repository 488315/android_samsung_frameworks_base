package com.android.systemui.keyguard.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

final class AlternateBouncerUdfpsViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DeviceEntryIconView $view;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ DeviceEntryIconView $view;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

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
            AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel = this.$viewModel;
            DeviceEntryIconView deviceEntryIconView = this.$view;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1("AlternateBouncerUdfpsViewBinder#viewModel.accessibilityDelegateHint", null, alternateBouncerUdfpsIconViewModel, deviceEntryIconView), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new AlternateBouncerUdfpsViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2("AlternateBouncerUdfpsViewBinder#viewModel.alpha", null, this.$viewModel, this.$view), 2);
            return Unit.INSTANCE;
        }
    }

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
