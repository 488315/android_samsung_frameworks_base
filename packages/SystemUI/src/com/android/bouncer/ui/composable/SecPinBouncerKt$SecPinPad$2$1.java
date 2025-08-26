package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.State;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
final class SecPinBouncerKt$SecPinPad$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Boolean> $animateFailure$delegate;
    final /* synthetic */ List<Animatable<Float, AnimationVector1D>> $buttonScaleAnimatables;
    final /* synthetic */ PinBouncerViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPinBouncerKt$SecPinPad$2$1(List<Animatable<Float, AnimationVector1D>> list, PinBouncerViewModel pinBouncerViewModel, State<Boolean> state, Continuation continuation) {
        super(2, continuation);
        this.$buttonScaleAnimatables = list;
        this.$viewModel = pinBouncerViewModel;
        this.$animateFailure$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPinBouncerKt$SecPinPad$2$1(this.$buttonScaleAnimatables, this.$viewModel, this.$animateFailure$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPinBouncerKt$SecPinPad$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            State<Boolean> state = this.$animateFailure$delegate;
            float f = SecPinBouncerKt.pinButtonErrorShrinkFactor;
            if (((Boolean) state.getValue()).booleanValue()) {
                List<Animatable<Float, AnimationVector1D>> list = this.$buttonScaleAnimatables;
                this.label = 1;
                Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new SecPinBouncerKt$showFailureAnimation$2(list, null), this);
                if (objCoroutineScope != obj2) {
                    objCoroutineScope = Unit.INSTANCE;
                }
                if (objCoroutineScope == obj2) {
                    return obj2;
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$viewModel.onFailureAnimationShown();
        return Unit.INSTANCE;
    }
}
