package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPinBouncerKt$showFailureAnimation$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<Animatable<Float, AnimationVector1D>> $buttonScaleAnimatables;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPinBouncerKt$showFailureAnimation$2(List<Animatable<Float, AnimationVector1D>> list, Continuation continuation) {
        super(2, continuation);
        this.$buttonScaleAnimatables = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecPinBouncerKt$showFailureAnimation$2 secPinBouncerKt$showFailureAnimation$2 = new SecPinBouncerKt$showFailureAnimation$2(this.$buttonScaleAnimatables, continuation);
        secPinBouncerKt$showFailureAnimation$2.L$0 = obj;
        return secPinBouncerKt$showFailureAnimation$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPinBouncerKt$showFailureAnimation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        int i = 0;
        for (Object obj2 : this.$buttonScaleAnimatables) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            BuildersKt.launch$default(coroutineScope, null, null, new SecPinBouncerKt$showFailureAnimation$2$1$1((Animatable) obj2, i, null), 3);
            i = i2;
        }
        return Unit.INSTANCE;
    }
}
