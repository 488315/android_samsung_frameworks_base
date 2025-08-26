package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class SecPatternBouncerKt$showEntryAnimation$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $dotAppearFadeInAnimatables;
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $dotAppearMoveUpAnimatables;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPatternBouncerKt$showEntryAnimation$2(Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map, Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map2, Continuation continuation) {
        super(2, continuation);
        this.$dotAppearFadeInAnimatables = map;
        this.$dotAppearMoveUpAnimatables = map2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecPatternBouncerKt$showEntryAnimation$2 secPatternBouncerKt$showEntryAnimation$2 = new SecPatternBouncerKt$showEntryAnimation$2(this.$dotAppearFadeInAnimatables, this.$dotAppearMoveUpAnimatables, continuation);
        secPatternBouncerKt$showEntryAnimation$2.L$0 = obj;
        return secPatternBouncerKt$showEntryAnimation$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$showEntryAnimation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        for (Map.Entry<PatternDotViewModel, Animatable<Float, AnimationVector1D>> entry : this.$dotAppearFadeInAnimatables.entrySet()) {
            BuildersKt.launch$default(coroutineScope, null, null, new SecPatternBouncerKt$showEntryAnimation$2$1$1(entry.getValue(), entry.getKey(), null), 3);
        }
        for (Map.Entry<PatternDotViewModel, Animatable<Float, AnimationVector1D>> entry2 : this.$dotAppearMoveUpAnimatables.entrySet()) {
            BuildersKt.launch$default(coroutineScope, null, null, new SecPatternBouncerKt$showEntryAnimation$2$2$1(entry2.getValue(), entry2.getKey(), null), 3);
        }
        return Unit.INSTANCE;
    }
}
