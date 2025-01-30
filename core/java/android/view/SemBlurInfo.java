package android.view;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class SemBlurInfo implements Parcelable {
  public static final int BLUR_MODE_CANVAS = 2;
  public static final int BLUR_MODE_WINDOW = 0;
  public static final int BLUR_MODE_WINDOW_CAPTURED = 1;
  public static final int COLOR_CURVE_TYPE_BEGIN = 11;
  public static final int COLOR_CURVE_TYPE_DIM_BACKGROUND_DARK = 15;
  public static final int COLOR_CURVE_TYPE_DIM_BACKGROUND_LIGHT = 12;
  public static final int COLOR_CURVE_TYPE_END = 16;
  public static final int COLOR_CURVE_TYPE_SPATIAL_BACKGROUND_DARK = 14;
  public static final int COLOR_CURVE_TYPE_SPATIAL_BACKGROUND_LIGHT = 11;
  public static final int COLOR_CURVE_TYPE_ULTRA_BACKGROUND_DARK = 16;
  public static final int COLOR_CURVE_TYPE_ULTRA_BACKGROUND_LIGHT = 13;
  private static final String TAG = "SemBlurInfo";
  private final int mBackgroundBlurColor;
  private final int mBlurMode;
  private final int mBlurRadius;
  private final int mCanvasDownScale;
  private final Bitmap mCapturedBitmap;
  private ColorCurve mColorCurve;
  private float mCornerRadiusBL;
  private float mCornerRadiusBR;
  private float mCornerRadiusTL;
  private float mCornerRadiusTR;
  private final boolean mHasCapturedBitmap;
  public static final float[] COLOR_CURVE_PRESET_SPATIAL_BACKGROUND_LIGHT = {
    150.0f, 0.0f, 5.0f, 0.0f, 255.0f, 1.0f, 212.0f
  };
  public static final float[] COLOR_CURVE_PRESET_DIM_BACKGROUND_LIGHT = {
    300.0f, 0.0f, 14.0f, 0.0f, 255.0f, 146.6f, 242.0f
  };
  public static final float[] COLOR_CURVE_PRESET_ULTRA_BACKGROUND_LIGHT = {
    348.0f, 0.0f, 8.0f, 0.0f, 255.0f, 81.0f, 207.0f
  };
  public static final float[] COLOR_CURVE_PRESET_SPATIAL_BACKGROUND_DARK = {
    150.0f, 0.0f, 8.0f, 0.0f, 255.0f, 2.0f, 152.0f
  };
  public static final float[] COLOR_CURVE_PRESET_DIM_BACKGROUND_DARK = {
    300.0f, 0.0f, 14.0f, 0.0f, 255.0f, 2.4f, 94.2f
  };
  public static final float[] COLOR_CURVE_PRESET_ULTRA_BACKGROUND_DARK = {
    348.0f, 0.0f, 8.0f, 0.0f, 255.0f, 41.0f, 167.0f
  };
  public static final Parcelable.Creator<SemBlurInfo> CREATOR =
      new Parcelable.Creator<SemBlurInfo>() { // from class: android.view.SemBlurInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBlurInfo createFromParcel(Parcel in) {
          return new SemBlurInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBlurInfo[] newArray(int size) {
          return new SemBlurInfo[size];
        }
      };

  @Retention(RetentionPolicy.SOURCE)
  public @interface BlurMode {}

  public SemBlurInfo(
      int blurMode,
      Bitmap capturedBitmap,
      int blurRadius,
      int backgroundBlurColor,
      float cornerRadiusTL,
      float cornerRadiusTR,
      float cornerRadiusBL,
      float cornerRadiusBR,
      int scale) {
    this.mBlurMode = blurMode;
    this.mCapturedBitmap = capturedBitmap;
    if (capturedBitmap == null) {
      this.mHasCapturedBitmap = false;
    } else {
      this.mHasCapturedBitmap = true;
    }
    this.mBlurRadius = blurRadius;
    this.mBackgroundBlurColor = backgroundBlurColor;
    this.mCornerRadiusTL = cornerRadiusTL;
    this.mCornerRadiusTR = cornerRadiusTR;
    this.mCornerRadiusBL = cornerRadiusBL;
    this.mCornerRadiusBR = cornerRadiusBR;
    this.mCanvasDownScale = scale;
  }

  public SemBlurInfo(
      int blurMode,
      Bitmap capturedBitmap,
      int blurRadius,
      int backgroundBlurColor,
      float cornerRadiusTL,
      float cornerRadiusTR,
      float cornerRadiusBL,
      float cornerRadiusBR,
      int scale,
      ColorCurve colorCurve) {
    this(
        blurMode,
        capturedBitmap,
        blurRadius,
        backgroundBlurColor,
        cornerRadiusTL,
        cornerRadiusTR,
        cornerRadiusBL,
        cornerRadiusBR,
        scale);
    this.mColorCurve = colorCurve;
  }

  protected SemBlurInfo(Parcel in) {
    this.mBlurMode = in.readInt();
    this.mBlurRadius = in.readInt();
    this.mBackgroundBlurColor = in.readInt();
    this.mCanvasDownScale = in.readInt();
    this.mCornerRadiusTL = in.readFloat();
    this.mCornerRadiusTR = in.readFloat();
    this.mCornerRadiusBL = in.readFloat();
    this.mCornerRadiusBR = in.readFloat();
    boolean readBoolean = in.readBoolean();
    this.mHasCapturedBitmap = readBoolean;
    if (readBoolean) {
      this.mCapturedBitmap = Bitmap.CREATOR.createFromParcel(in);
    } else {
      this.mCapturedBitmap = null;
    }
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeInt(this.mBlurMode);
    parcel.writeInt(this.mBlurRadius);
    parcel.writeInt(this.mBackgroundBlurColor);
    parcel.writeInt(this.mCanvasDownScale);
    parcel.writeFloat(this.mCornerRadiusTL);
    parcel.writeFloat(this.mCornerRadiusTR);
    parcel.writeFloat(this.mCornerRadiusBL);
    parcel.writeFloat(this.mCornerRadiusBR);
    parcel.writeBoolean(this.mHasCapturedBitmap);
    if (this.mHasCapturedBitmap) {
      this.mCapturedBitmap.writeToParcel(parcel, flags);
    }
  }

  public int getBlurMode() {
    return this.mBlurMode;
  }

  public int getBlurRadius() {
    return this.mBlurRadius;
  }

  public int getBackgroundBlurColor() {
    if (this.mBlurMode != 0) {
      throw new IllegalStateException(
          "Failed to getBackgroundBlurColor, because of blurMode is not BLUR_MODE_WINDOW");
    }
    return this.mBackgroundBlurColor;
  }

  public void getBackgroundBlurCornerRadius(float[] outRadius) {
    if (this.mBlurMode != 0) {
      throw new IllegalStateException(
          "Failed to getBackgroundBlurCornerRadius, because of blurMode is not BLUR_MODE_WINDOW");
    }
    if (outRadius == null || outRadius.length < 4) {
      throw new IllegalArgumentException("outRadius must be an array of four integers");
    }
    outRadius[0] = this.mCornerRadiusTL;
    outRadius[1] = this.mCornerRadiusTR;
    outRadius[2] = this.mCornerRadiusBL;
    outRadius[3] = this.mCornerRadiusBR;
  }

  public Bitmap getCapturedBitmap() {
    if (this.mBlurMode != 1) {
      throw new IllegalStateException(
          "Failed to getCapturedBitmap, because of blurMode is not BLUR_MODE_WINDOW_CAPTURED");
    }
    return this.mCapturedBitmap;
  }

  public int getCanvasDownScale() {
    if (this.mBlurMode != 2) {
      throw new IllegalStateException(
          "Failed to getCanvasDownScale, because of blurMode is not BLUR_MODE_CANVAS");
    }
    return this.mCanvasDownScale;
  }

  public ColorCurve getColorCurve() {
    return this.mColorCurve;
  }

  public static class Builder {
    private static final float CURVEGRAPH_CURVE_MAX_VALUE = 100.0f;
    private static final float CURVEGRAPH_CURVE_MIN_VALUE = -100.0f;
    private static final float CURVEGRAPH_MAX_XY_VALUE = 255.0f;
    private static final float CURVEGRAPH_MIN_XY_VALUE = 0.0f;
    private static final int FLAG_FINISH_BLUR_INFO = 256;
    private static final int FLAG_SET_BACKGROUND_BLUR_COLOR = 8;
    private static final int FLAG_SET_BACKGROUND_BLUR_CORNER_RADIUS = 16;
    private static final int FLAG_SET_BACKGROUND_CANVAS_SCALE = 32;
    private static final int FLAG_SET_BLUR_BITMAP = 2;
    private static final int FLAG_SET_BLUR_MODE = 1;
    private static final int FLAG_SET_BLUR_RADIUS = 4;
    public static final int FLAG_SET_COLOR_CURVE = 128;
    public static final int FLAG_SET_PRESET = 64;
    private static final float SATURATION_MAX_VALUE = 1.0f;
    private static final float SATURATION_MIN_VALUE = -1.0f;
    private int mBlurMode;
    private long mBuilderFieldsSet;
    private int mBlurRadius = 128;
    private Bitmap mCapturedBitmap = null;
    private int mCanvasDownScale = 8;
    private int mBackgroundBlurColor = 0;
    private float mCornerRadiusTL = 0.0f;
    private float mCornerRadiusTR = 0.0f;
    private float mCornerRadiusBL = 0.0f;
    private float mCornerRadiusBR = 0.0f;
    private ColorCurve mColorCurve = null;
    private int mPreset = 0;

    public Builder(int blurMode) {
      this.mBlurMode = 0;
      this.mBuilderFieldsSet = 0L;
      this.mBuilderFieldsSet = 0 | 1;
      this.mBlurMode = blurMode;
    }

    public Builder setBitmap(Bitmap capturedBitmap) {
      this.mBuilderFieldsSet |= 2;
      this.mCapturedBitmap = capturedBitmap;
      return this;
    }

    public Builder setRadius(int blurRadius) {
      this.mBuilderFieldsSet |= 4;
      this.mBlurRadius = blurRadius;
      return this;
    }

    private Builder hidden_setRadius(int blurRadius) {
      return setRadius(blurRadius);
    }

    public Builder setBackgroundColor(int color) {
      this.mBuilderFieldsSet |= 8;
      this.mBackgroundBlurColor = color;
      return this;
    }

    private Builder hidden_setBackgroundColor(int color) {
      return setBackgroundColor(color);
    }

    public Builder setBackgroundCornerRadius(float cornerRadius) {
      this.mBuilderFieldsSet |= 16;
      if (cornerRadius < 0.0f) {
        Log.m98i(SemBlurInfo.TAG, "cornerRadius = (" + cornerRadius + ") is negative, set to 0.0f");
        cornerRadius = 0.0f;
      }
      setBackgroundCornerRadius(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
      return this;
    }

    private Builder hidden_setBackgroundCornerRadius(float cornerRadius) {
      return setBackgroundCornerRadius(cornerRadius);
    }

    public Builder setBackgroundCornerRadius(
        float topLeft, float topRight, float bottomLeft, float bottomRight) {
      this.mBuilderFieldsSet |= 16;
      this.mCornerRadiusTL = topLeft;
      this.mCornerRadiusTR = topRight;
      this.mCornerRadiusBL = bottomLeft;
      this.mCornerRadiusBR = bottomRight;
      return this;
    }

    public Builder setCanvasScale(int scale) {
      this.mBuilderFieldsSet |= 32;
      this.mCanvasDownScale = scale;
      return this;
    }

    public Builder setColorCurve(
        float saturation, float curve, float minX, float maxX, float minY, float maxY) {
      this.mBuilderFieldsSet |= 128;
      this.mColorCurve =
          new ColorCurve(
              checkValueRange(saturation, 1.0f, -1.0f),
              checkValueRange(curve, 100.0f, CURVEGRAPH_CURVE_MIN_VALUE),
              checkValueRange(minX, CURVEGRAPH_MAX_XY_VALUE, 0.0f),
              checkValueRange(maxX, CURVEGRAPH_MAX_XY_VALUE, 0.0f),
              checkValueRange(minY, CURVEGRAPH_MAX_XY_VALUE, 0.0f),
              checkValueRange(maxY, CURVEGRAPH_MAX_XY_VALUE, 0.0f));
      return this;
    }

    private float checkValueRange(float value, float max, float min) {
      if (value > max) {
        return max;
      }
      if (value >= min) {
        return value;
      }
      return min;
    }

    public Builder setColorCurvePreset(int preset) {
      this.mBuilderFieldsSet |= 64;
      this.mPreset = preset;
      float[] presetAttrs = getBlurPresetAttrs(preset);
      if (presetAttrs != null) {
        setRadius((int) presetAttrs[0]);
        this.mColorCurve =
            new ColorCurve(
                presetAttrs[1],
                presetAttrs[2],
                presetAttrs[3],
                presetAttrs[4],
                presetAttrs[5],
                presetAttrs[6]);
      }
      return this;
    }

    public static float[] getBlurPresetAttrs(int preset) {
      switch (preset) {
        case 11:
          return SemBlurInfo.COLOR_CURVE_PRESET_SPATIAL_BACKGROUND_LIGHT;
        case 12:
          return SemBlurInfo.COLOR_CURVE_PRESET_DIM_BACKGROUND_LIGHT;
        case 13:
          return SemBlurInfo.COLOR_CURVE_PRESET_ULTRA_BACKGROUND_LIGHT;
        case 14:
          return SemBlurInfo.COLOR_CURVE_PRESET_SPATIAL_BACKGROUND_DARK;
        case 15:
          return SemBlurInfo.COLOR_CURVE_PRESET_DIM_BACKGROUND_DARK;
        case 16:
          return SemBlurInfo.COLOR_CURVE_PRESET_ULTRA_BACKGROUND_DARK;
        default:
          return null;
      }
    }

    public SemBlurInfo build() {
      checkNotUsed();
      long j = this.mBuilderFieldsSet | 256;
      this.mBuilderFieldsSet = j;
      if ((1 & j) == 0) {
        throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is not set");
      }
      if ((128 & j) != 0 && (j & 64) != 0) {
        throw new IllegalStateException(
            "Failed to create SemBlurInfo, BlurPreset and BlurColorCurve can not be used together");
      }
      if ((j & 64) != 0 && this.mColorCurve == null) {
        throw new IllegalStateException(
            "Failed to create SemBlurInfo, you set the wrong preset value " + this.mPreset);
      }
      int i = this.mBlurMode;
      if (i == 0) {
        if ((j & 2) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW, can not set"
                  + " capturedBitmap");
        }
        if ((j & 32) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW, can not set"
                  + " canvasDownScale");
        }
      } else if (i == 1) {
        if ((j & 2) == 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, must set"
                  + " capturedBitmap");
        }
        if ((j & 8) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set"
                  + " backgroundColor");
        }
        if ((j & 16) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set"
                  + " backgroundCornerRadius");
        }
        if ((j & 32) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set"
                  + " canvasDownScale");
        }
      } else if (i == 2) {
        if ((j & 2) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not capturedBitmap");
        }
        if ((j & 8) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not set"
                  + " backgroundColor");
        }
        if ((j & 16) != 0) {
          throw new IllegalStateException(
              "Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not set"
                  + " backgroundCornerRadius");
        }
      }
      return new SemBlurInfo(
          this.mBlurMode,
          this.mCapturedBitmap,
          this.mBlurRadius,
          this.mBackgroundBlurColor,
          this.mCornerRadiusTL,
          this.mCornerRadiusTR,
          this.mCornerRadiusBL,
          this.mCornerRadiusBR,
          this.mCanvasDownScale,
          this.mColorCurve);
    }

    private SemBlurInfo hidden_build() {
      return build();
    }

    private void checkNotUsed() {
      if ((this.mBuilderFieldsSet & 256) != 0) {
        throw new IllegalStateException(
            "Failed to create SemBlurInfo, This Builder should not be reused. Use a new Builder"
                + " instance instead");
      }
    }
  }

  public static class ColorCurve {
    public float mCurveBias;
    public float mMaxX;
    public float mMaxY;
    public float mMinX;
    public float mMinY;
    public float mSaturation;

    public ColorCurve(
        float saturation, float curve, float minX, float maxX, float minY, float maxY) {
      this.mSaturation = saturation;
      this.mCurveBias = curve;
      this.mMinX = minX;
      this.mMaxX = maxX;
      this.mMinY = minY;
      this.mMaxY = maxY;
    }

    public String toString() {
      return super.toString()
          + " {minX = "
          + this.mMinX
          + ", minY = "
          + this.mMinY
          + ", maxX = "
          + this.mMaxX
          + ", maxY = "
          + this.mMaxY
          + ", curveBias = "
          + this.mCurveBias
          + ", saturation = "
          + this.mSaturation
          + "}";
    }
  }
}
