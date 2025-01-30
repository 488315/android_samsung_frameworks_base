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

    /* renamed from: id */
    public final int f554id;

    InterpMode(int id) {
      this.f554id = id;
    }
  }

  public enum AnimationMode {
    STATIC(0),
    LOOP(1),
    ONCE_STAY_START(2),
    ONCE_STAY_END(3),
    ONCE_DESTROY(4);

    /* renamed from: id */
    public final int f552id;

    AnimationMode(int id) {
      this.f552id = id;
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

    /* renamed from: id */
    public final int f551id;

    AnimParam(int id) {
      this.f551id = id;
    }
  }

  public enum EffectTarget {
    SELF(1),
    BEHIND(2),
    SELF_AND_BEHIND(3);

    /* renamed from: id */
    public final int f553id;

    EffectTarget(int id) {
      this.f553id = id;
    }
  }

  public enum PixEffectType {
    NONE(0),
    BLUR(1),
    SATURATE(2),
    MAGNIFIER(11);

    /* renamed from: id */
    public final int f555id;

    PixEffectType(int id) {
      this.f555id = id;
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

      public Builder setAnimationMode(AnimationMode mAnimationMode) {
        this.mAnimationMode = mAnimationMode;
        return this;
      }

      public EffectTarget getEffectTarget() {
        return this.mEffectTarget;
      }

      public Builder setEffectTarget(EffectTarget mEffectTarget) {
        this.mEffectTarget = mEffectTarget;
        return this;
      }

      public PixEffectType getPixelEffectType() {
        return this.mPixelEffectType;
      }

      public Builder setPixelEffectType(PixEffectType mPixelEffectType) {
        this.mPixelEffectType = mPixelEffectType;
        return this;
      }

      public Builder addPixAnimation(
          AnimParam animParam, int timeMs, float value, InterpMode interpolation) {
        this.mPixelAnimationVector.add(new AnimKeyFrame(animParam, timeMs, value, interpolation));
        return this;
      }

      public Vector<AnimKeyFrame> getPixelAnimationVector() {
        return this.mPixelAnimationVector;
      }

      public Builder addGeoAnimation(
          AnimParam animParam, int timeMs, float value, InterpMode interpolation) {
        this.mGeometryAnimationVector.add(
            new AnimKeyFrame(animParam, timeMs, value, interpolation));
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

    public Effect(Effect o) {
      this.mBytes = o.mBytes;
    }

    public String getBytes() {
      return this.mBytes;
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Effect effect = (Effect) o;
      return this.mBytes.equals(effect.mBytes);
    }

    public int hashCode() {
      return this.mBytes.hashCode();
    }

    public static String serializeEffect(Builder builder) {
      String newArgsList = "" + Float.toString(builder.mAnimationMode.f552id);
      String newArgsList2 =
          ((((((newArgsList + " ") + Float.toString(builder.mEffectTarget.f553id)) + " ")
                          + Float.toString(builder.mPixelEffectType.f555id))
                      + " ")
                  + Float.toString(builder.getEffectRegion().size()))
              + " ";
      Iterator it = builder.mEffectRegion.iterator();
      while (it.hasNext()) {
        Rect r = (Rect) it.next();
        newArgsList2 =
            (((((((newArgsList2 + Float.toString(r.left)) + " ") + Float.toString(r.top)) + " ")
                            + Float.toString(r.right))
                        + " ")
                    + Float.toString(r.bottom))
                + " ";
      }
      String newArgsList3 =
          (newArgsList2 + Float.toString(builder.mPixelAnimationVector.size())) + " ";
      Iterator it2 = builder.mPixelAnimationVector.iterator();
      while (it2.hasNext()) {
        AnimKeyFrame anim = (AnimKeyFrame) it2.next();
        newArgsList3 =
            (((((((newArgsList3 + Float.toString(anim.animParam.f551id)) + " ")
                                    + Float.toString(anim.timeMs))
                                + " ")
                            + Float.toString(anim.value))
                        + " ")
                    + Float.toString(anim.interp.f554id))
                + " ";
      }
      String newArgsList4 =
          (newArgsList3 + Float.toString(builder.mGeometryAnimationVector.size())) + " ";
      Iterator it3 = builder.mGeometryAnimationVector.iterator();
      while (it3.hasNext()) {
        AnimKeyFrame anim2 = (AnimKeyFrame) it3.next();
        newArgsList4 =
            (((((((newArgsList4 + Float.toString(anim2.animParam.f551id)) + " ")
                                    + Float.toString(anim2.timeMs))
                                + " ")
                            + Float.toString(anim2.value))
                        + " ")
                    + Float.toString(anim2.interp.f554id))
                + " ";
      }
      return newArgsList4;
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

    public AnimKeyFrame(AnimParam animParam, int timeMs, float value, InterpMode interp) {
      this.animParam = animParam;
      this.timeMs = timeMs;
      this.value = value;
      this.interp = interp;
    }
  }
}
