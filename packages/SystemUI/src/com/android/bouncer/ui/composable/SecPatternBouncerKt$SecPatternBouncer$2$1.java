package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.MutableState;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPatternBouncerKt$SecPatternBouncer$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $dotAppearFadeInAnimatables;
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $dotAppearMoveUpAnimatables;
    final /* synthetic */ MutableState<Boolean> $entryAnimationCompleted$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPatternBouncerKt$SecPatternBouncer$2$1(Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map, Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map2, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$dotAppearFadeInAnimatables = map;
        this.$dotAppearMoveUpAnimatables = map2;
        this.$entryAnimationCompleted$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPatternBouncerKt$SecPatternBouncer$2$1(this.$dotAppearFadeInAnimatables, this.$dotAppearMoveUpAnimatables, this.$entryAnimationCompleted$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$SecPatternBouncer$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map = this.$dotAppearFadeInAnimatables;
            Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map2 = this.$dotAppearMoveUpAnimatables;
            this.label = 1;
            Object coroutineScope = CoroutineScopeKt.coroutineScope(new SecPatternBouncerKt$showEntryAnimation$2(map, map2, null), this);
            if (coroutineScope != obj2) {
                coroutineScope = Unit.INSTANCE;
            }
            if (coroutineScope == obj2) {
                return obj2;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$entryAnimationCompleted$delegate.setValue(Boolean.TRUE);
        return Unit.INSTANCE;
    }
}
