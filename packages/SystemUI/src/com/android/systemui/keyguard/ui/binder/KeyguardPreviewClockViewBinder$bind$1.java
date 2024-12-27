package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
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

final class KeyguardPreviewClockViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $largeClockHostView;
    final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $largeClockHostView;
        final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, View view, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardPreviewClockViewModel;
            this.$largeClockHostView = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$largeClockHostView, continuation);
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
            BuildersKt.launch$default((CoroutineScope) this.L$0, EmptyCoroutineContext.INSTANCE, null, new KeyguardPreviewClockViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1("KeyguardPreviewClockViewBinder#viewModel.isLargeClockVisible", null, this.$viewModel, this.$largeClockHostView), 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardPreviewClockViewBinder$bind$1(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, View view, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardPreviewClockViewModel;
        this.$largeClockHostView = view;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardPreviewClockViewBinder$bind$1 keyguardPreviewClockViewBinder$bind$1 = new KeyguardPreviewClockViewBinder$bind$1(this.$viewModel, this.$largeClockHostView, (Continuation) obj3);
        keyguardPreviewClockViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return keyguardPreviewClockViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$largeClockHostView, null);
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
