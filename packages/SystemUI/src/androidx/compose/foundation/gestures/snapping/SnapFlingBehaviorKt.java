package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationScope;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.foundation.ComposeFoundationFlags;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.unit.Dp;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;

/* loaded from: classes.dex */
public abstract class SnapFlingBehaviorKt {
    public static final float MinFlingVelocityDp;

    static {
        Dp.Companion companion = Dp.Companion;
        MinFlingVelocityDp = 400;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$animateDecay(final ScrollScope scrollScope, final float f, AnimationState animationState, DecayAnimationSpec decayAnimationSpec, final Function1 function1, ContinuationImpl continuationImpl) {
        SnapFlingBehaviorKt$animateDecay$1 snapFlingBehaviorKt$animateDecay$1;
        Ref$FloatRef ref$FloatRef;
        if (continuationImpl instanceof SnapFlingBehaviorKt$animateDecay$1) {
            snapFlingBehaviorKt$animateDecay$1 = (SnapFlingBehaviorKt$animateDecay$1) continuationImpl;
            int i = snapFlingBehaviorKt$animateDecay$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                snapFlingBehaviorKt$animateDecay$1.label = i - Integer.MIN_VALUE;
            } else {
                snapFlingBehaviorKt$animateDecay$1 = new SnapFlingBehaviorKt$animateDecay$1(continuationImpl);
            }
        }
        Object obj = snapFlingBehaviorKt$animateDecay$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = snapFlingBehaviorKt$animateDecay$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            boolean z = ((Number) animationState.getVelocity()).floatValue() == 0.0f;
            Function1 function12 = new Function1() { // from class: androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    AnimationScope animationScope = (AnimationScope) obj2;
                    float fAbs = Math.abs(((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue());
                    float fAbs2 = Math.abs(f);
                    MutableState mutableState = animationScope.value$delegate;
                    if (fAbs >= fAbs2) {
                        float fCoerceToTarget = SnapFlingBehaviorKt.coerceToTarget(((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).floatValue(), f);
                        SnapFlingBehaviorKt.access$animateDecay$consumeDelta(animationScope, scrollScope, function1, fCoerceToTarget - ref$FloatRef2.element);
                        animationScope.cancelAnimation();
                        ref$FloatRef2.element = fCoerceToTarget;
                    } else {
                        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) mutableState;
                        SnapFlingBehaviorKt.access$animateDecay$consumeDelta(animationScope, scrollScope, function1, ((Number) snapshotMutableStateImpl.getValue()).floatValue() - ref$FloatRef2.element);
                        ref$FloatRef2.element = ((Number) snapshotMutableStateImpl.getValue()).floatValue();
                    }
                    return Unit.INSTANCE;
                }
            };
            snapFlingBehaviorKt$animateDecay$1.L$0 = animationState;
            snapFlingBehaviorKt$animateDecay$1.L$1 = ref$FloatRef2;
            snapFlingBehaviorKt$animateDecay$1.F$0 = f;
            snapFlingBehaviorKt$animateDecay$1.label = 1;
            if (SuspendAnimationKt.animateDecay(animationState, decayAnimationSpec, !z, function12, snapFlingBehaviorKt$animateDecay$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$FloatRef = ref$FloatRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            f = snapFlingBehaviorKt$animateDecay$1.F$0;
            ref$FloatRef = (Ref$FloatRef) snapFlingBehaviorKt$animateDecay$1.L$1;
            animationState = (AnimationState) snapFlingBehaviorKt$animateDecay$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return new AnimationResult(new Float(f - ref$FloatRef.element), animationState);
    }

    public static final void access$animateDecay$consumeDelta(AnimationScope animationScope, ScrollScope scrollScope, Function1 function1, float f) {
        float fScrollBy;
        if (ComposeFoundationFlags.NewNestedFlingPropagationEnabled) {
            try {
                fScrollBy = scrollScope.scrollBy(f);
            } catch (CancellationException unused) {
                animationScope.cancelAnimation();
                fScrollBy = 0.0f;
            }
        } else {
            fScrollBy = scrollScope.scrollBy(f);
        }
        function1.mo781invoke(Float.valueOf(fScrollBy));
        if (Math.abs(f - fScrollBy) > 0.5f) {
            animationScope.cancelAnimation();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$animateWithTarget(final ScrollScope scrollScope, float f, final float f2, AnimationState animationState, AnimationSpec animationSpec, final Function1 function1, ContinuationImpl continuationImpl) {
        SnapFlingBehaviorKt$animateWithTarget$1 snapFlingBehaviorKt$animateWithTarget$1;
        Ref$FloatRef ref$FloatRef;
        AnimationState animationState2;
        float f3;
        if (continuationImpl instanceof SnapFlingBehaviorKt$animateWithTarget$1) {
            snapFlingBehaviorKt$animateWithTarget$1 = (SnapFlingBehaviorKt$animateWithTarget$1) continuationImpl;
            int i = snapFlingBehaviorKt$animateWithTarget$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                snapFlingBehaviorKt$animateWithTarget$1.label = i - Integer.MIN_VALUE;
            } else {
                snapFlingBehaviorKt$animateWithTarget$1 = new SnapFlingBehaviorKt$animateWithTarget$1(continuationImpl);
            }
        }
        SnapFlingBehaviorKt$animateWithTarget$1 snapFlingBehaviorKt$animateWithTarget$12 = snapFlingBehaviorKt$animateWithTarget$1;
        Object obj = snapFlingBehaviorKt$animateWithTarget$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = snapFlingBehaviorKt$animateWithTarget$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            float fFloatValue = ((Number) animationState.getVelocity()).floatValue();
            Float f4 = new Float(f);
            boolean z = ((Number) animationState.getVelocity()).floatValue() == 0.0f;
            Function1 function12 = new Function1() { // from class: androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    float fScrollBy;
                    AnimationScope animationScope = (AnimationScope) obj2;
                    float fCoerceToTarget = SnapFlingBehaviorKt.coerceToTarget(((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue(), f2);
                    float f5 = fCoerceToTarget - ref$FloatRef2.element;
                    if (ComposeFoundationFlags.NewNestedFlingPropagationEnabled) {
                        try {
                            fScrollBy = scrollScope.scrollBy(f5);
                        } catch (CancellationException unused) {
                            animationScope.cancelAnimation();
                            fScrollBy = 0.0f;
                        }
                    } else {
                        fScrollBy = scrollScope.scrollBy(f5);
                    }
                    function1.mo781invoke(Float.valueOf(fScrollBy));
                    if (Math.abs(f5 - fScrollBy) > 0.5f || fCoerceToTarget != ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue()) {
                        animationScope.cancelAnimation();
                    }
                    ref$FloatRef2.element += fScrollBy;
                    return Unit.INSTANCE;
                }
            };
            snapFlingBehaviorKt$animateWithTarget$12.L$0 = animationState;
            snapFlingBehaviorKt$animateWithTarget$12.L$1 = ref$FloatRef2;
            snapFlingBehaviorKt$animateWithTarget$12.F$0 = f;
            snapFlingBehaviorKt$animateWithTarget$12.F$1 = fFloatValue;
            snapFlingBehaviorKt$animateWithTarget$12.label = 1;
            if (SuspendAnimationKt.animateTo(animationState, f4, animationSpec, !z, function12, snapFlingBehaviorKt$animateWithTarget$12) == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$FloatRef = ref$FloatRef2;
            animationState2 = animationState;
            f3 = fFloatValue;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            f3 = snapFlingBehaviorKt$animateWithTarget$12.F$1;
            f = snapFlingBehaviorKt$animateWithTarget$12.F$0;
            ref$FloatRef = (Ref$FloatRef) snapFlingBehaviorKt$animateWithTarget$12.L$1;
            animationState2 = (AnimationState) snapFlingBehaviorKt$animateWithTarget$12.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return new AnimationResult(new Float(f - ref$FloatRef.element), AnimationStateKt.copy$default(animationState2, 0.0f, coerceToTarget(((Number) animationState2.getVelocity()).floatValue(), f3), 29));
    }

    public static final float coerceToTarget(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        return (f2 <= 0.0f ? f >= f2 : f <= f2) ? f : f2;
    }
}
