package com.android.bouncer.ui.composable;

import android.view.View;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.State;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPatternBouncerKt$SecPatternBouncer$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<PatternDotViewModel> $currentDot$delegate;
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $dotActivationColorAnimatables;
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $dotScalingAnimatables;
    final /* synthetic */ State<Boolean> $isAnimationEnabled$delegate;
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $lineFadeOutAnimatables;
    final /* synthetic */ int $lineFadeOutAnimationDelayMs;
    final /* synthetic */ int $lineFadeOutAnimationDurationMs;
    final /* synthetic */ CoroutineScope $scope;
    final /* synthetic */ State<List<PatternDotViewModel>> $selectedDots$delegate;
    final /* synthetic */ View $view;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SecPatternBouncerKt$SecPatternBouncer$3$1(View view, Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map, Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map2, State<PatternDotViewModel> state, State<Boolean> state2, CoroutineScope coroutineScope, State<? extends List<PatternDotViewModel>> state3, Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map3, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.$view = view;
        this.$dotActivationColorAnimatables = map;
        this.$dotScalingAnimatables = map2;
        this.$currentDot$delegate = state;
        this.$isAnimationEnabled$delegate = state2;
        this.$scope = coroutineScope;
        this.$selectedDots$delegate = state3;
        this.$lineFadeOutAnimatables = map3;
        this.$lineFadeOutAnimationDurationMs = i;
        this.$lineFadeOutAnimationDelayMs = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecPatternBouncerKt$SecPatternBouncer$3$1 secPatternBouncerKt$SecPatternBouncer$3$1 = new SecPatternBouncerKt$SecPatternBouncer$3$1(this.$view, this.$dotActivationColorAnimatables, this.$dotScalingAnimatables, this.$currentDot$delegate, this.$isAnimationEnabled$delegate, this.$scope, this.$selectedDots$delegate, this.$lineFadeOutAnimatables, this.$lineFadeOutAnimationDurationMs, this.$lineFadeOutAnimationDelayMs, continuation);
        secPatternBouncerKt$SecPatternBouncer$3$1.L$0 = obj;
        return secPatternBouncerKt$SecPatternBouncer$3$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$SecPatternBouncer$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        if (((PatternDotViewModel) this.$currentDot$delegate.getValue()) != null) {
            this.$view.performHapticFeedback(1, 1);
        }
        if (!((Boolean) this.$isAnimationEnabled$delegate.getValue()).booleanValue()) {
            return Unit.INSTANCE;
        }
        Iterator<T> it = this.$dotActivationColorAnimatables.entrySet().iterator();
        while (it.hasNext()) {
            BuildersKt.launch$default(coroutineScope, null, null, new SecPatternBouncerKt$SecPatternBouncer$3$1$1$1((Animatable) ((Map.Entry) it.next()).getValue(), null), 3);
        }
        Set<Map.Entry<PatternDotViewModel, Animatable<Float, AnimationVector1D>>> entrySet = this.$dotScalingAnimatables.entrySet();
        CoroutineScope coroutineScope2 = this.$scope;
        State<PatternDotViewModel> state = this.$currentDot$delegate;
        Iterator<T> it2 = entrySet.iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            PatternDotViewModel patternDotViewModel = (PatternDotViewModel) entry.getKey();
            BuildersKt.launch$default(coroutineScope2, null, null, new SecPatternBouncerKt$SecPatternBouncer$3$1$2$1(Intrinsics.areEqual(patternDotViewModel, (PatternDotViewModel) state.getValue()), (Animatable) entry.getValue(), null), 3);
        }
        List<PatternDotViewModel> list = (List) this.$selectedDots$delegate.getValue();
        Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map = this.$lineFadeOutAnimatables;
        CoroutineScope coroutineScope3 = this.$scope;
        int i = this.$lineFadeOutAnimationDurationMs;
        int i2 = this.$lineFadeOutAnimationDelayMs;
        State<PatternDotViewModel> state2 = this.$currentDot$delegate;
        for (PatternDotViewModel patternDotViewModel2 : list) {
            Animatable<Float, AnimationVector1D> animatable = map.get(patternDotViewModel2);
            if (animatable != null && !animatable.isRunning()) {
                BuildersKt.launch$default(coroutineScope3, null, null, new SecPatternBouncerKt$SecPatternBouncer$3$1$3$1$1(patternDotViewModel2, animatable, i, i2, state2, null), 3);
            }
        }
        return Unit.INSTANCE;
    }
}
