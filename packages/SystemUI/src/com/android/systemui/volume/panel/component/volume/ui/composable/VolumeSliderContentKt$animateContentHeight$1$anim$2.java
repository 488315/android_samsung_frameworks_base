package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.ui.layout.Placeable;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class VolumeSliderContentKt$animateContentHeight$1$anim$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable $currentAnimation;
    final /* synthetic */ Placeable $placeable;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeSliderContentKt$animateContentHeight$1$anim$2(Animatable animatable, Placeable placeable, Continuation continuation) {
        super(2, continuation);
        this.$currentAnimation = animatable;
        this.$placeable = placeable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeSliderContentKt$animateContentHeight$1$anim$2(this.$currentAnimation, this.$placeable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeSliderContentKt$animateContentHeight$1$anim$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Animatable animatable = this.$currentAnimation;
            Integer num = new Integer(this.$placeable.height);
            this.label = 1;
            if (Animatable.animateTo$default(animatable, num, null, null, null, this, 14) == coroutineSingletons) {
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
