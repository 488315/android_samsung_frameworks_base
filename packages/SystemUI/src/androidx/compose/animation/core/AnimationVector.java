package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimationVector {
    public /* synthetic */ AnimationVector(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract float get$animation_core(int i);

    public abstract int getSize$animation_core();

    public abstract AnimationVector newVector$animation_core();

    public abstract void reset$animation_core();

    public abstract void set$animation_core(float f, int i);

    private AnimationVector() {
    }
}
