package androidx.compose.animation.core;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.jvm.internal.FloatCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimationStateKt {
    public static AnimationState AnimationState$default(float f, float f2, int i) {
        if ((i & 2) != 0) {
            f2 = 0.0f;
        }
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return new AnimationState(VectorConvertersKt.FloatToVector, Float.valueOf(f), new AnimationVector1D(f2), Long.MIN_VALUE, Long.MIN_VALUE, false);
    }

    public static AnimationState copy$default(AnimationState animationState, float f, float f2, int i) {
        if ((i & 1) != 0) {
            f = ((Number) ((SnapshotMutableStateImpl) animationState.value$delegate).getValue()).floatValue();
        }
        if ((i & 2) != 0) {
            f2 = ((AnimationVector1D) animationState.velocityVector).value;
        }
        return new AnimationState(animationState.typeConverter, Float.valueOf(f), new AnimationVector1D(f2), animationState.lastFrameTimeNanos, animationState.finishedTimeNanos, animationState.isRunning);
    }

    public static AnimationState AnimationState$default(TwoWayConverter twoWayConverter) {
        Float valueOf = Float.valueOf(0.0f);
        return new AnimationState(twoWayConverter, valueOf, (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo779invoke(valueOf), Long.MIN_VALUE, Long.MIN_VALUE, false);
    }
}
