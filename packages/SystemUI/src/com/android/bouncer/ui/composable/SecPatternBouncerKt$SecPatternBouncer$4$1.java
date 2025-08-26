package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.State;
import com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
final class SecPatternBouncerKt$SecPatternBouncer$4$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Boolean> $animateFailure$delegate;
    final /* synthetic */ Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> $dotScalingAnimatables;
    final /* synthetic */ State<List<PatternDotViewModel>> $dots$delegate;
    final /* synthetic */ PatternBouncerViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SecPatternBouncerKt$SecPatternBouncer$4$1(Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map, PatternBouncerViewModel patternBouncerViewModel, State<Boolean> state, State<? extends List<PatternDotViewModel>> state2, Continuation continuation) {
        super(2, continuation);
        this.$dotScalingAnimatables = map;
        this.$viewModel = patternBouncerViewModel;
        this.$animateFailure$delegate = state;
        this.$dots$delegate = state2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPatternBouncerKt$SecPatternBouncer$4$1(this.$dotScalingAnimatables, this.$viewModel, this.$animateFailure$delegate, this.$dots$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$SecPatternBouncer$4$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (((Boolean) this.$animateFailure$delegate.getValue()).booleanValue()) {
                List<PatternDotViewModel> list = (List) this.$dots$delegate.getValue();
                Map<PatternDotViewModel, Animatable<Float, AnimationVector1D>> map = this.$dotScalingAnimatables;
                this.label = 1;
                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                for (PatternDotViewModel patternDotViewModel : list) {
                    int i2 = patternDotViewModel.y;
                    while (listBuilderCreateListBuilder.getSize() <= i2) {
                        listBuilderCreateListBuilder.add(new ArrayList());
                    }
                    ((List) listBuilderCreateListBuilder.get(i2)).add(patternDotViewModel);
                }
                Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new SecPatternBouncerKt$showFailureAnimation$2(listBuilderCreateListBuilder.build(), map, null), this);
                if (objCoroutineScope != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    objCoroutineScope = Unit.INSTANCE;
                }
                if (objCoroutineScope == obj2) {
                    return obj2;
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$viewModel.onFailureAnimationShown();
        return Unit.INSTANCE;
    }
}
