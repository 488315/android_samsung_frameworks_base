package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import dagger.Lazy;
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

final class AlternateBouncerViewBinder$optionallyAddUdfpsViews$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Lazy $udfpsA11yOverlayViewModel;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel $udfpsIconViewModel;
    final /* synthetic */ ConstraintLayout $view;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Lazy $udfpsA11yOverlayViewModel;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $udfpsIconViewModel;
        final /* synthetic */ ConstraintLayout $view;
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ConstraintLayout constraintLayout, Lazy lazy, Continuation continuation) {
            super(2, continuation);
            this.$udfpsIconViewModel = alternateBouncerUdfpsIconViewModel;
            this.$view = constraintLayout;
            this.$udfpsA11yOverlayViewModel = lazy;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, continuation);
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
            BuildersKt.launch$default((CoroutineScope) this.L$0, EmptyCoroutineContext.INSTANCE, null, new AlternateBouncerViewBinder$optionallyAddUdfpsViews$1$1$invokeSuspend$$inlined$launch$default$1("AlternateBouncerViewBinder#udfpsIconViewModel.iconLocation", null, this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel), 2);
            return Unit.INSTANCE;
        }
    }

    public AlternateBouncerViewBinder$optionallyAddUdfpsViews$1(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ConstraintLayout constraintLayout, Lazy lazy, Continuation continuation) {
        super(3, continuation);
        this.$udfpsIconViewModel = alternateBouncerUdfpsIconViewModel;
        this.$view = constraintLayout;
        this.$udfpsA11yOverlayViewModel = lazy;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerViewBinder$optionallyAddUdfpsViews$1 alternateBouncerViewBinder$optionallyAddUdfpsViews$1 = new AlternateBouncerViewBinder$optionallyAddUdfpsViews$1(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, (Continuation) obj3);
        alternateBouncerViewBinder$optionallyAddUdfpsViews$1.L$0 = (LifecycleOwner) obj;
        return alternateBouncerViewBinder$optionallyAddUdfpsViews$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$udfpsIconViewModel, this.$view, this.$udfpsA11yOverlayViewModel, null);
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
