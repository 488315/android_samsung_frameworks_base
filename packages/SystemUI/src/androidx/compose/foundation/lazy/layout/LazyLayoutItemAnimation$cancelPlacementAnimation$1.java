package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.Animatable;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation;
import androidx.compose.ui.unit.IntOffset;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazyLayoutItemAnimation$cancelPlacementAnimation$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LazyLayoutItemAnimation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyLayoutItemAnimation$cancelPlacementAnimation$1(LazyLayoutItemAnimation lazyLayoutItemAnimation, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lazyLayoutItemAnimation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LazyLayoutItemAnimation$cancelPlacementAnimation$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LazyLayoutItemAnimation$cancelPlacementAnimation$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Animatable animatable = this.this$0.placementDeltaAnimation;
            IntOffset.Companion.getClass();
            IntOffset m847boximpl = IntOffset.m847boximpl(0L);
            this.label = 1;
            if (animatable.snapTo(m847boximpl, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        LazyLayoutItemAnimation lazyLayoutItemAnimation = this.this$0;
        IntOffset.Companion.getClass();
        LazyLayoutItemAnimation.Companion companion = LazyLayoutItemAnimation.Companion;
        lazyLayoutItemAnimation.m166setPlacementDeltagyyYBs(0L);
        this.this$0.setPlacementAnimationInProgress(false);
        return Unit.INSTANCE;
    }
}
