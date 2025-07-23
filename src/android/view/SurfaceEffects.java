package android.view;

import android.graphics.Rect;
import java.util.Iterator;
import java.util.Vector;

/* loaded from: classes4.dex */
public class SurfaceEffects {
    public static final Effect EMPTY_EFFECT = newBuilder().build();
    private static final long FRAME_LENGTH_NANOS = 16666666;

    public enum InterpMode {
        HOLD(0),
        LINEAR(1),
        SMOOTH(4),
        SMOOTH_OUT(2),
        SMOOTH_IN(3);

        public final int id;

        InterpMode(int i) {
            this.id = i;
        }
    }

    public enum AnimationMode {
        STATIC(0),
        LOOP(1),
        ONCE_STAY_START(2),
        ONCE_STAY_END(3),
        ONCE_DESTROY(4);

        public final int id;

        AnimationMode(int i) {
            this.id = i;
        }
    }

    public enum AnimParam {
        ALPHA(12),
        BLUR_RADIUS(13),
        BLUR_SATURATION(14),
        BLUR_ALPHA(15),
        SATURATION(16),
        REGION_SIZE_X(20),
        REGION_SIZE_Y(21),
        REGION_CORNER_SIZE(22),
        REGION_POS_X(23),
        REGION_POS_Y(24),
        REGION_FACTOR_X(27),
        REGION_FACTOR_Y(28),
        REGION_OFFSET_X(31),
        REGION_OFFSET_Y(32);

        public final int id;

        AnimParam(int i) {
            this.id = i;
        }
    }

    public enum EffectTarget {
        SELF(1),
        BEHIND(2),
        SELF_AND_BEHIND(3);

        public final int id;

        EffectTarget(int i) {
            this.id = i;
        }
    }

    public enum PixEffectType {
        NONE(0),
        BLUR(1),
        SATURATE(2),
        MAGNIFIER(11);

        public final int id;

        PixEffectType(int i) {
            this.id = i;
        }
    }

    public static class Effect {
        private String mBytes;

        public static class Builder {
            private AnimationMode mAnimationMode;
            private final Vector<Rect> mEffectRegion;
            private EffectTarget mEffectTarget;
            private final Vector<AnimKeyFrame> mGeometryAnimationVector;
            private final Vector<AnimKeyFrame> mPixelAnimationVector;
            private PixEffectType mPixelEffectType;

            private Builder() {
                this.mAnimationMode = AnimationMode.STATIC;
                this.mEffectTarget = EffectTarget.BEHIND;
                this.mPixelEffectType = PixEffectType.NONE;
                this.mEffectRegion = new Vector<>();
                this.mPixelAnimationVector = new Vector<>();
                this.mGeometryAnimationVector = new Vector<>();
            }

            public AnimationMode getAnimationMode() {
                return this.mAnimationMode;
            }

            public Builder setAnimationMode(AnimationMode animationMode) {
                this.mAnimationMode = animationMode;
                return this;
            }

            public EffectTarget getEffectTarget() {
                return this.mEffectTarget;
            }

            public Builder setEffectTarget(EffectTarget effectTarget) {
                this.mEffectTarget = effectTarget;
                return this;
            }

            public PixEffectType getPixelEffectType() {
                return this.mPixelEffectType;
            }

            public Builder setPixelEffectType(PixEffectType pixEffectType) {
                this.mPixelEffectType = pixEffectType;
                return this;
            }

            public Builder addPixAnimation(AnimParam animParam, int i, float f, InterpMode interpMode) {
                this.mPixelAnimationVector.add(new AnimKeyFrame(animParam, i, f, interpMode));
                return this;
            }

            public Vector<AnimKeyFrame> getPixelAnimationVector() {
                return this.mPixelAnimationVector;
            }

            public Builder addGeoAnimation(AnimParam animParam, int i, float f, InterpMode interpMode) {
                this.mGeometryAnimationVector.add(new AnimKeyFrame(animParam, i, f, interpMode));
                return this;
            }

            public Vector<AnimKeyFrame> getGeometryAnimationVector() {
                return this.mGeometryAnimationVector;
            }

            public Builder addEffectRegionRect(Rect rect) {
                this.mEffectRegion.add(new Rect(rect));
                return this;
            }

            public Builder makeFullscreen() {
                this.mEffectRegion.clear();
                this.mEffectRegion.add(new Rect(0, 0, 10000, 10000));
                return this;
            }

            public Vector<Rect> getEffectRegion() {
                return this.mEffectRegion;
            }

            public Effect build() {
                return new Effect(this);
            }
        }

        private Effect(Builder builder) {
            this.mBytes = serializeEffect(builder);
        }

        public Effect(Effect effect) {
            this.mBytes = effect.mBytes;
        }

        public String getBytes() {
            return this.mBytes;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mBytes.equals(((Effect) obj).mBytes);
        }

        public int hashCode() {
            return this.mBytes.hashCode();
        }

        public static String serializeEffect(Builder builder) {
            String str = ((((((("" + Float.toString(builder.mAnimationMode.id)) + " ") + Float.toString(builder.mEffectTarget.id)) + " ") + Float.toString(builder.mPixelEffectType.id)) + " ") + Float.toString(builder.getEffectRegion().size())) + " ";
            Iterator it = builder.mEffectRegion.iterator();
            while (it.hasNext()) {
                Rect rect = (Rect) it.next();
                str = (((((((str + Float.toString(rect.left)) + " ") + Float.toString(rect.top)) + " ") + Float.toString(rect.right)) + " ") + Float.toString(rect.bottom)) + " ";
            }
            String str2 = (str + Float.toString(builder.mPixelAnimationVector.size())) + " ";
            Iterator it2 = builder.mPixelAnimationVector.iterator();
            while (it2.hasNext()) {
                AnimKeyFrame animKeyFrame = (AnimKeyFrame) it2.next();
                str2 = (((((((str2 + Float.toString(animKeyFrame.animParam.id)) + " ") + Float.toString(animKeyFrame.timeMs)) + " ") + Float.toString(animKeyFrame.value)) + " ") + Float.toString(animKeyFrame.interp.id)) + " ";
            }
            String str3 = (str2 + Float.toString(builder.mGeometryAnimationVector.size())) + " ";
            Iterator it3 = builder.mGeometryAnimationVector.iterator();
            while (it3.hasNext()) {
                AnimKeyFrame animKeyFrame2 = (AnimKeyFrame) it3.next();
                str3 = (((((((str3 + Float.toString(animKeyFrame2.animParam.id)) + " ") + Float.toString(animKeyFrame2.timeMs)) + " ") + Float.toString(animKeyFrame2.value)) + " ") + Float.toString(animKeyFrame2.interp.id)) + " ";
            }
            return str3;
        }
    }

    public static Effect.Builder newBuilder() {
        return new Effect.Builder();
    }

    private static class AnimKeyFrame {
        public final AnimParam animParam;
        public final InterpMode interp;
        public final int timeMs;
        public final float value;

        public AnimKeyFrame(AnimParam animParam, int i, float f, InterpMode interpMode) {
            this.animParam = animParam;
            this.timeMs = i;
            this.value = f;
            this.interp = interpMode;
        }
    }
}
