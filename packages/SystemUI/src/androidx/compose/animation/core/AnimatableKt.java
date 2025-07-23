package androidx.compose.animation.core;

import kotlin.jvm.internal.FloatCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimatableKt {
    public static final AnimationVector1D positiveInfinityBounds1D = new AnimationVector1D(Float.POSITIVE_INFINITY);
    public static final AnimationVector2D positiveInfinityBounds2D = new AnimationVector2D(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final AnimationVector3D positiveInfinityBounds3D = new AnimationVector3D(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final AnimationVector4D positiveInfinityBounds4D = new AnimationVector4D(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final AnimationVector1D negativeInfinityBounds1D = new AnimationVector1D(Float.NEGATIVE_INFINITY);
    public static final AnimationVector2D negativeInfinityBounds2D = new AnimationVector2D(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    public static final AnimationVector3D negativeInfinityBounds3D = new AnimationVector3D(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    public static final AnimationVector4D negativeInfinityBounds4D = new AnimationVector4D(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

    public static final Animatable Animatable(float f, float f2) {
        Float valueOf = Float.valueOf(f);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return new Animatable(valueOf, VectorConvertersKt.FloatToVector, Float.valueOf(f2), null, 8, null);
    }
}
