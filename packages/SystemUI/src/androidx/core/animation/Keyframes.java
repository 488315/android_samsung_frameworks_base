package androidx.core.animation;

/* loaded from: classes.dex */
public interface Keyframes extends Cloneable {

    public interface FloatKeyframes extends Keyframes {
    }

    public interface IntKeyframes extends Keyframes {
    }

    Keyframes clone();

    Object getValue(float f);
}
