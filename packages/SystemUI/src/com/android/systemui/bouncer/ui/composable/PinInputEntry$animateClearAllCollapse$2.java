package com.android.systemui.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.TweenSpec;
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
final class PinInputEntry$animateClearAllCollapse$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ PinInputEntry this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputEntry$animateClearAllCollapse$2(PinInputEntry pinInputEntry, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pinInputEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PinInputEntry$animateClearAllCollapse$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputEntry$animateClearAllCollapse$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        Animatable animatable = this.this$0.shapeSize;
        Dp m835boximpl = Dp.m835boximpl(0);
        TweenSpec tweenSpec = this.this$0.shapeAnimations.clearAllShapeSizeAnimationSpec;
        this.label = 1;
        Object animateTo$default = Animatable.animateTo$default(animatable, m835boximpl, tweenSpec, null, null, this, 12);
        return animateTo$default == coroutineSingletons ? coroutineSingletons : animateTo$default;
    }
}
