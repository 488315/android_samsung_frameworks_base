package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;
import com.samsung.sesl.compose.foundation.SeslFeedbackAlpha;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class SeslFeedbackNode$updateAnimation$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SeslFeedbackNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeslFeedbackNode$updateAnimation$1(SeslFeedbackNode seslFeedbackNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = seslFeedbackNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeslFeedbackNode$updateAnimation$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeslFeedbackNode$updateAnimation$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SeslFeedbackNode seslFeedbackNode = this.this$0;
            Animatable animatable = seslFeedbackNode.alphaAnimatable;
            if (animatable != null) {
                SeslFeedbackAlpha seslFeedbackAlpha = (SeslFeedbackAlpha) seslFeedbackNode.feedbackAlpha.invoke();
                SeslInteractionState seslInteractionState = this.this$0.interactionState;
                seslFeedbackAlpha.getClass();
                SeslFeedbackAlpha.Companion.Impl28.INSTANCE.getClass();
                Float f = new Float(seslInteractionState.pressed ? seslFeedbackAlpha.pressedAlpha : seslInteractionState.focused ? seslFeedbackAlpha.focusedAlpha : seslInteractionState.hovered ? seslFeedbackAlpha.hoveredAlpha : seslInteractionState.dragged ? seslFeedbackAlpha.draggedAlpha : 0.0f);
                SeslFeedbackDefaults seslFeedbackDefaults = SeslFeedbackDefaults.INSTANCE;
                boolean z = this.this$0.interactionState.pressed;
                seslFeedbackDefaults.getClass();
                TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(z ? 100 : 350, 0, z ? SeslFeedbackDefaults.FEEDBACK_ANIMATION_DOWN_EASING : SeslFeedbackDefaults.FEEDBACK_ANIMATION_UP_EASING, 2);
                this.label = 1;
                obj = Animatable.animateTo$default(animatable, f, tweenSpecTween$default, null, null, this, 12);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Unit.INSTANCE;
    }
}
