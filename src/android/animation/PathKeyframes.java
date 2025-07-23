package android.animation;

import android.animation.Keyframes;
import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PathKeyframes implements Keyframes {
    private static final ArrayList<Keyframe> EMPTY_KEYFRAMES = new ArrayList<>();
    private static final int FRACTION_OFFSET = 0;
    private static final int NUM_COMPONENTS = 3;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 2;
    private float[] mKeyframeData;
    private PointF mTempPointF;

    private static float interpolate(float f, float f2, float f3) {
        return f2 + ((f3 - f2) * f);
    }

    @Override // android.animation.Keyframes
    public void setEvaluator(TypeEvaluator typeEvaluator) {
    }

    public PathKeyframes(Path path) {
        this(path, 0.5f);
    }

    public PathKeyframes(Path path, float f) {
        this.mTempPointF = new PointF();
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("The path must not be null or empty");
        }
        this.mKeyframeData = path.approximate(f);
    }

    @Override // android.animation.Keyframes
    public ArrayList<Keyframe> getKeyframes() {
        return EMPTY_KEYFRAMES;
    }

    @Override // android.animation.Keyframes
    public Object getValue(float f) {
        int length = this.mKeyframeData.length / 3;
        int i = 0;
        if (f < 0.0f) {
            return interpolateInRange(f, 0, 1);
        }
        if (f > 1.0f) {
            return interpolateInRange(f, length - 2, length - 1);
        }
        if (f == 0.0f) {
            return pointForIndex(0);
        }
        if (f == 1.0f) {
            return pointForIndex(length - 1);
        }
        int i2 = length - 1;
        while (i <= i2) {
            int i3 = (i + i2) / 2;
            float f2 = this.mKeyframeData[i3 * 3];
            if (f < f2) {
                i2 = i3 - 1;
            } else {
                if (f <= f2) {
                    return pointForIndex(i3);
                }
                i = i3 + 1;
            }
        }
        return interpolateInRange(f, i2, i);
    }

    private PointF interpolateInRange(float f, int i, int i2) {
        int i3 = i * 3;
        int i4 = i2 * 3;
        float[] fArr = this.mKeyframeData;
        float f2 = fArr[i3];
        float f3 = (f - f2) / (fArr[i4] - f2);
        float f4 = fArr[i3 + 1];
        float f5 = fArr[i4 + 1];
        float f6 = fArr[i3 + 2];
        float f7 = fArr[i4 + 2];
        this.mTempPointF.set(interpolate(f3, f4, f5), interpolate(f3, f6, f7));
        return this.mTempPointF;
    }

    @Override // android.animation.Keyframes
    public Class getType() {
        return PointF.class;
    }

    @Override // android.animation.Keyframes
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Keyframes m107clone() {
        try {
            return (Keyframes) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    private PointF pointForIndex(int i) {
        int i2 = i * 3;
        PointF pointF = this.mTempPointF;
        float[] fArr = this.mKeyframeData;
        pointF.set(fArr[i2 + 1], fArr[i2 + 2]);
        return this.mTempPointF;
    }

    public Keyframes.FloatKeyframes createXFloatKeyframes() {
        return new FloatKeyframesBase() { // from class: android.animation.PathKeyframes.1
            @Override // android.animation.Keyframes.FloatKeyframes
            public float getFloatValue(float f) {
                return ((PointF) PathKeyframes.this.getValue(f)).x;
            }
        };
    }

    public Keyframes.FloatKeyframes createYFloatKeyframes() {
        return new FloatKeyframesBase() { // from class: android.animation.PathKeyframes.2
            @Override // android.animation.Keyframes.FloatKeyframes
            public float getFloatValue(float f) {
                return ((PointF) PathKeyframes.this.getValue(f)).y;
            }
        };
    }

    public Keyframes.IntKeyframes createXIntKeyframes() {
        return new IntKeyframesBase() { // from class: android.animation.PathKeyframes.3
            @Override // android.animation.Keyframes.IntKeyframes
            public int getIntValue(float f) {
                return Math.round(((PointF) PathKeyframes.this.getValue(f)).x);
            }
        };
    }

    public Keyframes.IntKeyframes createYIntKeyframes() {
        return new IntKeyframesBase() { // from class: android.animation.PathKeyframes.4
            @Override // android.animation.Keyframes.IntKeyframes
            public int getIntValue(float f) {
                return Math.round(((PointF) PathKeyframes.this.getValue(f)).y);
            }
        };
    }

    private static abstract class SimpleKeyframes implements Keyframes {
        @Override // android.animation.Keyframes
        public void setEvaluator(TypeEvaluator typeEvaluator) {
        }

        private SimpleKeyframes() {
        }

        @Override // android.animation.Keyframes
        public ArrayList<Keyframe> getKeyframes() {
            return PathKeyframes.EMPTY_KEYFRAMES;
        }

        @Override // android.animation.Keyframes
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Keyframes m108clone() {
            try {
                return (Keyframes) super.clone();
            } catch (CloneNotSupportedException unused) {
                return null;
            }
        }
    }

    static abstract class IntKeyframesBase extends SimpleKeyframes implements Keyframes.IntKeyframes {
        IntKeyframesBase() {
            super();
        }

        @Override // android.animation.Keyframes
        public Class getType() {
            return Integer.class;
        }

        @Override // android.animation.Keyframes
        public Object getValue(float f) {
            return Integer.valueOf(getIntValue(f));
        }
    }

    static abstract class FloatKeyframesBase extends SimpleKeyframes implements Keyframes.FloatKeyframes {
        FloatKeyframesBase() {
            super();
        }

        @Override // android.animation.Keyframes
        public Class getType() {
            return Float.class;
        }

        @Override // android.animation.Keyframes
        public Object getValue(float f) {
            return Float.valueOf(getFloatValue(f));
        }
    }
}
