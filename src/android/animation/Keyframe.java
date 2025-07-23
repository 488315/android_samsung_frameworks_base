package android.animation;

/* loaded from: classes.dex */
public abstract class Keyframe implements Cloneable {
    float mFraction;
    boolean mHasValue;
    private TimeInterpolator mInterpolator = null;
    Class mValueType;
    boolean mValueWasSetOnStart;

    @Override // 
    /* renamed from: clone */
    public abstract Keyframe mo84clone();

    public abstract Object getValue();

    public abstract void setValue(Object obj);

    public static Keyframe ofInt(float f, int i) {
        return new IntKeyframe(f, i);
    }

    public static Keyframe ofInt(float f) {
        return new IntKeyframe(f);
    }

    public static Keyframe ofFloat(float f, float f2) {
        return new FloatKeyframe(f, f2);
    }

    public static Keyframe ofFloat(float f) {
        return new FloatKeyframe(f);
    }

    public static Keyframe ofObject(float f, Object obj) {
        return new ObjectKeyframe(f, obj);
    }

    public static Keyframe ofObject(float f) {
        return new ObjectKeyframe(f, null);
    }

    public boolean hasValue() {
        return this.mHasValue;
    }

    boolean valueWasSetOnStart() {
        return this.mValueWasSetOnStart;
    }

    void setValueWasSetOnStart(boolean z) {
        this.mValueWasSetOnStart = z;
    }

    public float getFraction() {
        return this.mFraction;
    }

    public void setFraction(float f) {
        this.mFraction = f;
    }

    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
    }

    public Class getType() {
        return this.mValueType;
    }

    static class ObjectKeyframe extends Keyframe {
        Object mValue;

        ObjectKeyframe(float f, Object obj) {
            this.mFraction = f;
            this.mValue = obj;
            this.mHasValue = obj != null;
            this.mValueType = this.mHasValue ? obj.getClass() : Object.class;
        }

        @Override // android.animation.Keyframe
        public Object getValue() {
            return this.mValue;
        }

        @Override // android.animation.Keyframe
        public void setValue(Object obj) {
            this.mValue = obj;
            this.mHasValue = obj != null;
        }

        @Override // android.animation.Keyframe
        /* renamed from: clone */
        public ObjectKeyframe mo84clone() {
            ObjectKeyframe objectKeyframe = new ObjectKeyframe(getFraction(), hasValue() ? this.mValue : null);
            objectKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            objectKeyframe.setInterpolator(getInterpolator());
            return objectKeyframe;
        }
    }

    static class IntKeyframe extends Keyframe {
        int mValue;

        IntKeyframe(float f, int i) {
            this.mFraction = f;
            this.mValue = i;
            this.mValueType = Integer.TYPE;
            this.mHasValue = true;
        }

        IntKeyframe(float f) {
            this.mFraction = f;
            this.mValueType = Integer.TYPE;
        }

        public int getIntValue() {
            return this.mValue;
        }

        @Override // android.animation.Keyframe
        public Object getValue() {
            return Integer.valueOf(this.mValue);
        }

        @Override // android.animation.Keyframe
        public void setValue(Object obj) {
            if (obj == null || obj.getClass() != Integer.class) {
                return;
            }
            this.mValue = ((Integer) obj).intValue();
            this.mHasValue = true;
        }

        @Override // android.animation.Keyframe
        /* renamed from: clone */
        public IntKeyframe mo84clone() {
            IntKeyframe intKeyframe;
            if (this.mHasValue) {
                intKeyframe = new IntKeyframe(getFraction(), this.mValue);
            } else {
                intKeyframe = new IntKeyframe(getFraction());
            }
            intKeyframe.setInterpolator(getInterpolator());
            intKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return intKeyframe;
        }
    }

    static class FloatKeyframe extends Keyframe {
        float mValue;

        FloatKeyframe(float f, float f2) {
            this.mFraction = f;
            this.mValue = f2;
            this.mValueType = Float.TYPE;
            this.mHasValue = true;
        }

        FloatKeyframe(float f) {
            this.mFraction = f;
            this.mValueType = Float.TYPE;
        }

        public float getFloatValue() {
            return this.mValue;
        }

        @Override // android.animation.Keyframe
        public Object getValue() {
            return Float.valueOf(this.mValue);
        }

        @Override // android.animation.Keyframe
        public void setValue(Object obj) {
            if (obj == null || obj.getClass() != Float.class) {
                return;
            }
            this.mValue = ((Float) obj).floatValue();
            this.mHasValue = true;
        }

        @Override // android.animation.Keyframe
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public FloatKeyframe mo84clone() {
            FloatKeyframe floatKeyframe;
            if (this.mHasValue) {
                floatKeyframe = new FloatKeyframe(getFraction(), this.mValue);
            } else {
                floatKeyframe = new FloatKeyframe(getFraction());
            }
            floatKeyframe.setInterpolator(getInterpolator());
            floatKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return floatKeyframe;
        }
    }
}
