package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.gestures.ScrollExtensionsKt;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class EditTileKt$AutoScrollGrid$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ScrollState $scrollState;
    final /* synthetic */ State<Integer> $scrollTarget$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTileKt$AutoScrollGrid$2$1(State<Integer> state, ScrollState scrollState, Continuation continuation) {
        super(2, continuation);
        this.$scrollTarget$delegate = state;
        this.$scrollState = scrollState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditTileKt$AutoScrollGrid$2$1(this.$scrollTarget$delegate, this.$scrollState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditTileKt$AutoScrollGrid$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Integer num = (Integer) this.$scrollTarget$delegate.getValue();
            if (num != null) {
                ScrollState scrollState = this.$scrollState;
                TweenSpec tween$default = AnimationSpecKt.tween$default(Math.abs(num.intValue() - scrollState.getValue()) * 2, 0, EasingKt.LinearEasing, 2);
                this.label = 1;
                Object animateScrollBy = ScrollExtensionsKt.animateScrollBy(scrollState, r8 - scrollState.getValue(), tween$default, this);
                if (animateScrollBy != obj2) {
                    animateScrollBy = Unit.INSTANCE;
                }
                if (animateScrollBy == obj2) {
                    return obj2;
                }
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
