package androidx.compose.material3;

import androidx.activity.BackEventCompat;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class SearchBarKt$SearchBar$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animationProgress;
    final /* synthetic */ MutableState<BackEventCompat> $currentBackEvent;
    final /* synthetic */ boolean $expanded;
    final /* synthetic */ MutableFloatState $finalBackProgress;
    final /* synthetic */ MutableState<BackEventCompat> $firstBackEvent;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SearchBarKt$SearchBar$3$1(Animatable<Float, AnimationVector1D> animatable, boolean z, MutableFloatState mutableFloatState, MutableState<BackEventCompat> mutableState, MutableState<BackEventCompat> mutableState2, Continuation continuation) {
        super(2, continuation);
        this.$animationProgress = animatable;
        this.$expanded = z;
        this.$finalBackProgress = mutableFloatState;
        this.$firstBackEvent = mutableState;
        this.$currentBackEvent = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SearchBarKt$SearchBar$3$1(this.$animationProgress, this.$expanded, this.$finalBackProgress, this.$firstBackEvent, this.$currentBackEvent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SearchBarKt$SearchBar$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SearchBarKt$SearchBar$3$1 searchBarKt$SearchBar$3$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            TweenSpec tweenSpec = (((Number) this.$animationProgress.internalState.getValue()).floatValue() <= 0.0f || ((Number) this.$animationProgress.internalState.getValue()).floatValue() >= 1.0f) ? this.$expanded ? SearchBarKt.AnimationEnterFloatSpec : SearchBarKt.AnimationExitFloatSpec : SearchBarKt.AnimationPredictiveBackExitFloatSpec;
            float f = this.$expanded ? 1.0f : 0.0f;
            if (((Number) this.$animationProgress.internalState.getValue()).floatValue() != f) {
                Animatable<Float, AnimationVector1D> animatable = this.$animationProgress;
                Float f2 = new Float(f);
                this.label = 1;
                searchBarKt$SearchBar$3$1 = this;
                if (Animatable.animateTo$default(animatable, f2, tweenSpec, null, null, searchBarKt$SearchBar$3$1, 12) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            if (!searchBarKt$SearchBar$3$1.$expanded) {
                ((SnapshotMutableFloatStateImpl) searchBarKt$SearchBar$3$1.$finalBackProgress).setFloatValue(Float.NaN);
                searchBarKt$SearchBar$3$1.$firstBackEvent.setValue(null);
                searchBarKt$SearchBar$3$1.$currentBackEvent.setValue(null);
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        searchBarKt$SearchBar$3$1 = this;
        if (!searchBarKt$SearchBar$3$1.$expanded) {
        }
        return Unit.INSTANCE;
    }
}
