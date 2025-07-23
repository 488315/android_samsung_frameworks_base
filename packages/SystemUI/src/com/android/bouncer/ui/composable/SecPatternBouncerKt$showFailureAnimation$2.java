package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
final class SecPatternBouncerKt$showFailureAnimation$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<List<PatternDotViewModel>> $dotsByRow;
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $scalingAnimatables;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SecPatternBouncerKt$showFailureAnimation$2(List<? extends List<PatternDotViewModel>> list, Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map, Continuation continuation) {
        super(2, continuation);
        this.$dotsByRow = list;
        this.$scalingAnimatables = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecPatternBouncerKt$showFailureAnimation$2 secPatternBouncerKt$showFailureAnimation$2 = new SecPatternBouncerKt$showFailureAnimation$2(this.$dotsByRow, this.$scalingAnimatables, continuation);
        secPatternBouncerKt$showFailureAnimation$2.L$0 = obj;
        return secPatternBouncerKt$showFailureAnimation$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$showFailureAnimation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        List<List<PatternDotViewModel>> list = this.$dotsByRow;
        Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map = this.$scalingAnimatables;
        int i = 0;
        for (Object obj2 : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            Iterator it = ((List) obj2).iterator();
            while (it.hasNext()) {
                Animatable<Float, AnimationVector1D> animatable = map.get((PatternDotViewModel) it.next());
                if (animatable != null) {
                    BuildersKt.launch$default(coroutineScope, null, null, new SecPatternBouncerKt$showFailureAnimation$2$1$1$1$1(animatable, i, null), 3);
                }
            }
            i = i2;
        }
        return Unit.INSTANCE;
    }
}
