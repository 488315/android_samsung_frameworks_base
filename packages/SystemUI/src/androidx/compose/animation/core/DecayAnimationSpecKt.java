package androidx.compose.animation.core;

import kotlin.jvm.internal.FloatCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DecayAnimationSpecKt {
    public static final float calculateTargetValue(DecayAnimationSpec decayAnimationSpec, float f, float f2) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
        return ((AnimationVector1D) new VectorizedFloatDecaySpec(((DecayAnimationSpecImpl) decayAnimationSpec).floatDecaySpec).getTargetValue(new AnimationVector1D(f), new AnimationVector1D(f2))).value;
    }

    public static DecayAnimationSpec exponentialDecay$default() {
        return new DecayAnimationSpecImpl(new FloatExponentialDecaySpec(1.0f, 0.1f));
    }

    public static final DecayAnimationSpec generateDecayAnimationSpec(FloatDecayAnimationSpec floatDecayAnimationSpec) {
        return new DecayAnimationSpecImpl(floatDecayAnimationSpec);
    }
}
