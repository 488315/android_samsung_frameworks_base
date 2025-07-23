package androidx.compose.material3;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.unit.Dp;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class IndicatorLineNode$invalidateIndicator$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ IndicatorLineNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IndicatorLineNode$invalidateIndicator$2(IndicatorLineNode indicatorLineNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = indicatorLineNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IndicatorLineNode$invalidateIndicator$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IndicatorLineNode$invalidateIndicator$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            IndicatorLineNode indicatorLineNode = this.this$0;
            Animatable animatable = indicatorLineNode.widthAnimatable;
            Dp m835boximpl = Dp.m835boximpl((indicatorLineNode.focused && indicatorLineNode.enabled) ? indicatorLineNode.focusedIndicatorWidth : indicatorLineNode.unfocusedIndicatorWidth);
            IndicatorLineNode indicatorLineNode2 = this.this$0;
            AnimationSpec fromToken = indicatorLineNode2.enabled ? MotionSchemeKt.fromToken((MotionScheme) CompositionLocalConsumerModifierNodeKt.currentValueOf(indicatorLineNode2, MotionSchemeKt.LocalMotionScheme), MotionSchemeKeyTokens.FastSpatial) : AnimationSpecKt.snap$default();
            this.label = 1;
            if (Animatable.animateTo$default(animatable, m835boximpl, fromToken, null, null, this, 12) == coroutineSingletons) {
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
