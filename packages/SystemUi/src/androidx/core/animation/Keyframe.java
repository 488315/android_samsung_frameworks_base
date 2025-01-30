package androidx.core.animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Keyframe implements Cloneable {
    public float mFraction;
    public boolean mHasValue;
    public Interpolator mInterpolator = null;
    public boolean mValueWasSetOnStart;

    @Override // 
    /* renamed from: clone */
    public abstract Keyframe mo308clone();

    public abstract Object getValue();

    public abstract void setValue(Object obj);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FloatKeyframe extends Keyframe {
        public float mValue;

        public FloatKeyframe(float f, float f2) {
            this.mFraction = f;
            this.mValue = f2;
            this.mHasValue = true;
        }

        @Override // androidx.core.animation.Keyframe
        public final Object getValue() {
            return Float.valueOf(this.mValue);
        }

        @Override // androidx.core.animation.Keyframe
        public final void setValue(Object obj) {
            Float f = (Float) obj;
            if (f == null || f.getClass() != Float.class) {
                return;
            }
            this.mValue = f.floatValue();
            this.mHasValue = true;
        }

        @Override // androidx.core.animation.Keyframe
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public final FloatKeyframe mo308clone() {
            FloatKeyframe floatKeyframe;
            if (this.mHasValue) {
                floatKeyframe = new FloatKeyframe(this.mFraction, this.mValue);
            } else {
                floatKeyframe = new FloatKeyframe(this.mFraction);
            }
            floatKeyframe.mInterpolator = this.mInterpolator;
            floatKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return floatKeyframe;
        }

        public FloatKeyframe(float f) {
            this.mFraction = f;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IntKeyframe extends Keyframe {
        public int mValue;

        public IntKeyframe(float f, int i) {
            this.mFraction = f;
            this.mValue = i;
            this.mHasValue = true;
        }

        @Override // androidx.core.animation.Keyframe
        public final Object getValue() {
            return Integer.valueOf(this.mValue);
        }

        @Override // androidx.core.animation.Keyframe
        public final void setValue(Object obj) {
            Integer num = (Integer) obj;
            if (num == null || num.getClass() != Integer.class) {
                return;
            }
            this.mValue = num.intValue();
            this.mHasValue = true;
        }

        @Override // androidx.core.animation.Keyframe
        /* renamed from: clone */
        public final IntKeyframe mo308clone() {
            IntKeyframe intKeyframe;
            if (this.mHasValue) {
                intKeyframe = new IntKeyframe(this.mFraction, this.mValue);
            } else {
                intKeyframe = new IntKeyframe(this.mFraction);
            }
            intKeyframe.mInterpolator = this.mInterpolator;
            intKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return intKeyframe;
        }

        public IntKeyframe(float f) {
            this.mFraction = f;
        }
    }
}
