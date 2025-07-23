package androidx.compose.material3;

import android.window.BackEvent;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.material3.internal.BackHandlerKt;
import androidx.compose.material3.internal.PredictiveBack;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1$onBackStarted$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BackEvent $backEvent;
    final /* synthetic */ Animatable<Float, AnimationVector1D> $predictiveBackProgress;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1$onBackStarted$1(Animatable<Float, AnimationVector1D> animatable, BackEvent backEvent, Continuation continuation) {
        super(2, continuation);
        this.$predictiveBackProgress = animatable;
        this.$backEvent = backEvent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1$onBackStarted$1(this.$predictiveBackProgress, this.$backEvent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1$onBackStarted$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Animatable<Float, AnimationVector1D> animatable = this.$predictiveBackProgress;
            PredictiveBack predictiveBack = PredictiveBack.INSTANCE;
            float progress = this.$backEvent.getProgress();
            predictiveBack.getClass();
            Float f = new Float(BackHandlerKt.PredictiveBackEasing.transform(progress));
            this.label = 1;
            if (animatable.snapTo(f, this) == coroutineSingletons) {
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
