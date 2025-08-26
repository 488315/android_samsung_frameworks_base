package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class SeslRecoilNode$updateAnimation$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SeslRecoilNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeslRecoilNode$updateAnimation$1(SeslRecoilNode seslRecoilNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = seslRecoilNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeslRecoilNode$updateAnimation$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeslRecoilNode$updateAnimation$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SeslRecoilNode seslRecoilNode = this.this$0;
            Animatable animatable = seslRecoilNode.scaleAnimatable;
            if (animatable != null) {
                SeslRecoilDefaults seslRecoilDefaults = SeslRecoilDefaults.INSTANCE;
                boolean z = seslRecoilNode.interactionState.pressed;
                float f = seslRecoilNode.scale;
                seslRecoilDefaults.getClass();
                if (!z) {
                    f = 1.0f;
                }
                Float f2 = new Float(f);
                boolean z2 = this.this$0.interactionState.pressed;
                TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(z2 ? 100 : 350, 0, z2 ? SeslRecoilDefaults.SCALE_ANIMATION_DOWN_EASING : SeslRecoilDefaults.SCALE_ANIMATION_UP_EASING, 2);
                this.label = 1;
                obj = Animatable.animateTo$default(animatable, f2, tweenSpecTween$default, null, null, this, 12);
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
