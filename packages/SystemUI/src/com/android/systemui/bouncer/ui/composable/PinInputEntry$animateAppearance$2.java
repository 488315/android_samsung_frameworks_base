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

/* loaded from: classes.dex */
final class PinInputEntry$animateAppearance$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ PinInputEntry this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputEntry$animateAppearance$2(PinInputEntry pinInputEntry, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pinInputEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PinInputEntry$animateAppearance$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputEntry$animateAppearance$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Animatable animatable = this.this$0.entryWidth;
            Dp dpM837boximpl = Dp.m837boximpl(0);
            this.label = 1;
            if (animatable.snapTo(dpM837boximpl, this) != coroutineSingletons) {
            }
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        PinInputEntry pinInputEntry = this.this$0;
        Animatable animatable2 = pinInputEntry.entryWidth;
        Dp dpM837boximpl2 = Dp.m837boximpl(pinInputEntry.shapeAnimations.shapeSize);
        TweenSpec tweenSpec = this.this$0.shapeAnimations.inputShiftAnimationSpec;
        this.label = 2;
        Object objAnimateTo$default = Animatable.animateTo$default(animatable2, dpM837boximpl2, tweenSpec, null, null, this, 12);
        return objAnimateTo$default == coroutineSingletons ? coroutineSingletons : objAnimateTo$default;
    }
}
