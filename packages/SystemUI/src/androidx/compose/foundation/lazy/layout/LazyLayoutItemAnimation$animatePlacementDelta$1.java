package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation;
import androidx.compose.ui.unit.IntOffset;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class LazyLayoutItemAnimation$animatePlacementDelta$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FiniteAnimationSpec<IntOffset> $spec;
    final /* synthetic */ long $totalDelta;
    Object L$0;
    int label;
    final /* synthetic */ LazyLayoutItemAnimation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyLayoutItemAnimation$animatePlacementDelta$1(LazyLayoutItemAnimation lazyLayoutItemAnimation, FiniteAnimationSpec<IntOffset> finiteAnimationSpec, long j, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lazyLayoutItemAnimation;
        this.$spec = finiteAnimationSpec;
        this.$totalDelta = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LazyLayoutItemAnimation$animatePlacementDelta$1(this.this$0, this.$spec, this.$totalDelta, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LazyLayoutItemAnimation$animatePlacementDelta$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0098, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r11, r4, r5, null, r7, r8, 4) != r0) goto L31;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FiniteAnimationSpec finiteAnimationSpec;
        FiniteAnimationSpec finiteAnimationSpec2;
        LazyLayoutItemAnimation$animatePlacementDelta$1 lazyLayoutItemAnimation$animatePlacementDelta$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.this$0.placementDeltaAnimation.isRunning()) {
                FiniteAnimationSpec<IntOffset> finiteAnimationSpec3 = this.$spec;
                finiteAnimationSpec = finiteAnimationSpec3 instanceof SpringSpec ? (SpringSpec) finiteAnimationSpec3 : LazyLayoutItemAnimationKt.InterruptionSpec;
            } else {
                finiteAnimationSpec = this.$spec;
            }
            finiteAnimationSpec2 = finiteAnimationSpec;
            if (this.this$0.placementDeltaAnimation.isRunning()) {
                FiniteAnimationSpec finiteAnimationSpec4 = finiteAnimationSpec2;
                final long jM852minusqkQi6aY = IntOffset.m852minusqkQi6aY(((IntOffset) this.this$0.placementDeltaAnimation.internalState.getValue()).packedValue, this.$totalDelta);
                Animatable animatable = this.this$0.placementDeltaAnimation;
                IntOffset intOffsetM849boximpl = IntOffset.m849boximpl(jM852minusqkQi6aY);
                final LazyLayoutItemAnimation lazyLayoutItemAnimation = this.this$0;
                Function1 function1 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animatePlacementDelta$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        LazyLayoutItemAnimation lazyLayoutItemAnimation2 = lazyLayoutItemAnimation;
                        long jM852minusqkQi6aY2 = IntOffset.m852minusqkQi6aY(((IntOffset) ((Animatable) obj2).internalState.getValue()).packedValue, jM852minusqkQi6aY);
                        LazyLayoutItemAnimation.Companion companion = LazyLayoutItemAnimation.Companion;
                        lazyLayoutItemAnimation2.m167setPlacementDeltagyyYBs(jM852minusqkQi6aY2);
                        lazyLayoutItemAnimation.onLayerPropertyChanged.invoke();
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = null;
                this.label = 2;
                lazyLayoutItemAnimation$animatePlacementDelta$1 = this;
            } else {
                Animatable animatable2 = this.this$0.placementDeltaAnimation;
                IntOffset intOffsetM849boximpl2 = IntOffset.m849boximpl(this.$totalDelta);
                this.L$0 = finiteAnimationSpec2;
                this.label = 1;
                if (animatable2.snapTo(intOffsetM849boximpl2, this) == coroutineSingletons) {
                }
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            lazyLayoutItemAnimation$animatePlacementDelta$1 = this;
            LazyLayoutItemAnimation lazyLayoutItemAnimation2 = lazyLayoutItemAnimation$animatePlacementDelta$1.this$0;
            LazyLayoutItemAnimation.Companion companion = LazyLayoutItemAnimation.Companion;
            lazyLayoutItemAnimation2.setPlacementAnimationInProgress(false);
            lazyLayoutItemAnimation$animatePlacementDelta$1.this$0.isRunningMovingAwayAnimation = false;
            return Unit.INSTANCE;
        }
        finiteAnimationSpec2 = (FiniteAnimationSpec) this.L$0;
        ResultKt.throwOnFailure(obj);
        this.this$0.onLayerPropertyChanged.invoke();
        FiniteAnimationSpec finiteAnimationSpec42 = finiteAnimationSpec2;
        final long jM852minusqkQi6aY2 = IntOffset.m852minusqkQi6aY(((IntOffset) this.this$0.placementDeltaAnimation.internalState.getValue()).packedValue, this.$totalDelta);
        Animatable animatable3 = this.this$0.placementDeltaAnimation;
        IntOffset intOffsetM849boximpl3 = IntOffset.m849boximpl(jM852minusqkQi6aY2);
        final LazyLayoutItemAnimation lazyLayoutItemAnimation3 = this.this$0;
        Function1 function12 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animatePlacementDelta$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                LazyLayoutItemAnimation lazyLayoutItemAnimation22 = lazyLayoutItemAnimation3;
                long jM852minusqkQi6aY22 = IntOffset.m852minusqkQi6aY(((IntOffset) ((Animatable) obj2).internalState.getValue()).packedValue, jM852minusqkQi6aY2);
                LazyLayoutItemAnimation.Companion companion2 = LazyLayoutItemAnimation.Companion;
                lazyLayoutItemAnimation22.m167setPlacementDeltagyyYBs(jM852minusqkQi6aY22);
                lazyLayoutItemAnimation3.onLayerPropertyChanged.invoke();
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.label = 2;
        lazyLayoutItemAnimation$animatePlacementDelta$1 = this;
    }
}
