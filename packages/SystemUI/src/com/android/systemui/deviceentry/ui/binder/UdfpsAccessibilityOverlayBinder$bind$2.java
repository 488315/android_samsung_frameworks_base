package com.android.systemui.deviceentry.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class UdfpsAccessibilityOverlayBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ UdfpsAccessibilityOverlay $view;
    final /* synthetic */ UdfpsAccessibilityOverlayViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ UdfpsAccessibilityOverlay $view;
        final /* synthetic */ UdfpsAccessibilityOverlayViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel, UdfpsAccessibilityOverlay udfpsAccessibilityOverlay, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = udfpsAccessibilityOverlayViewModel;
            this.$view = udfpsAccessibilityOverlay;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$view, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.visible;
                final UdfpsAccessibilityOverlay udfpsAccessibilityOverlay = this.$view;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder.bind.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        UdfpsAccessibilityOverlay.this.setVisibility(((Boolean) obj2).booleanValue() ^ true ? 4 : 0);
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
    public UdfpsAccessibilityOverlayBinder$bind$2(UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel, UdfpsAccessibilityOverlay udfpsAccessibilityOverlay, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = udfpsAccessibilityOverlayViewModel;
        this.$view = udfpsAccessibilityOverlay;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UdfpsAccessibilityOverlayBinder$bind$2 udfpsAccessibilityOverlayBinder$bind$2 = new UdfpsAccessibilityOverlayBinder$bind$2(this.$viewModel, this.$view, (Continuation) obj3);
        udfpsAccessibilityOverlayBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return udfpsAccessibilityOverlayBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
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
