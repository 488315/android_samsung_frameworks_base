package android.view;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class SemBlurInfo implements Parcelable {
    private static final int BLUR_BASE_OFFSET = 101;
    public static final int BLUR_BG_REGULAR_DARK = 135;
    public static final int BLUR_BG_REGULAR_LIGHT = 132;
    public static final int BLUR_BG_THICK_DARK = 136;
    public static final int BLUR_BG_THICK_DARK_GRAYISH = 137;
    public static final int BLUR_BG_THICK_LIGHT = 133;
    public static final int BLUR_BG_THIN_DARK = 134;
    public static final int BLUR_BG_THIN_LIGHT = 131;
    public static final int BLUR_MODE_CANVAS = 2;
    public static final int BLUR_MODE_WINDOW = 0;
    public static final int BLUR_MODE_WINDOW_CAPTURED = 1;
    private static final int BLUR_PRESET_BEGIN = 101;
    private static final int BLUR_PRESET_END = 137;
    public static final int BLUR_UI_HIGH_REGULAR_DARK = 128;
    public static final int BLUR_UI_HIGH_REGULAR_LIGHT = 113;
    public static final int BLUR_UI_HIGH_THICK_DARK = 129;
    public static final int BLUR_UI_HIGH_THICK_LIGHT = 114;
    public static final int BLUR_UI_HIGH_THIN_DARK = 127;
    public static final int BLUR_UI_HIGH_THIN_LIGHT = 112;
    public static final int BLUR_UI_HIGH_ULTRA_THICK_DARK = 130;
    public static final int BLUR_UI_HIGH_ULTRA_THICK_LIGHT = 115;
    public static final int BLUR_UI_HIGH_ULTRA_THIN_DARK = 126;
    public static final int BLUR_UI_HIGH_ULTRA_THIN_LIGHT = 111;
    public static final int BLUR_UI_LOW_REGULAR_DARK = 118;
    public static final int BLUR_UI_LOW_REGULAR_LIGHT = 103;
    public static final int BLUR_UI_LOW_THICK_DARK = 119;
    public static final int BLUR_UI_LOW_THICK_LIGHT = 104;
    public static final int BLUR_UI_LOW_THIN_DARK = 117;
    public static final int BLUR_UI_LOW_THIN_LIGHT = 102;
    public static final int BLUR_UI_LOW_ULTRA_THICK_DARK = 120;
    public static final int BLUR_UI_LOW_ULTRA_THICK_LIGHT = 105;
    public static final int BLUR_UI_LOW_ULTRA_THIN_DARK = 116;
    public static final int BLUR_UI_LOW_ULTRA_THIN_LIGHT = 101;
    public static final int BLUR_UI_MEDIUM_REGULAR_DARK = 123;
    public static final int BLUR_UI_MEDIUM_REGULAR_LIGHT = 108;
    public static final int BLUR_UI_MEDIUM_THICK_DARK = 124;
    public static final int BLUR_UI_MEDIUM_THICK_LIGHT = 109;
    public static final int BLUR_UI_MEDIUM_THIN_DARK = 122;
    public static final int BLUR_UI_MEDIUM_THIN_LIGHT = 107;
    public static final int BLUR_UI_MEDIUM_ULTRA_THICK_DARK = 125;
    public static final int BLUR_UI_MEDIUM_ULTRA_THICK_LIGHT = 110;
    public static final int BLUR_UI_MEDIUM_ULTRA_THIN_DARK = 121;
    public static final int BLUR_UI_MEDIUM_ULTRA_THIN_LIGHT = 106;
    public static final int COLOR_CURVE_TYPE_DIM_BACKGROUND_DARK = 15;
    public static final int COLOR_CURVE_TYPE_DIM_BACKGROUND_LIGHT = 12;
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
    private static final float[] COLOR_CURVE_PRESET_SPATIAL_BACKGROUND_LIGHT = {150.0f, 0.0f, 5.0f, 0.0f, 255.0f, 1.0f, 212.0f};
    private static final float[] COLOR_CURVE_PRESET_DIM_BACKGROUND_LIGHT = {300.0f, 0.0f, 14.0f, 0.0f, 255.0f, 146.6f, 242.0f};
    private static final float[] COLOR_CURVE_PRESET_ULTRA_BACKGROUND_LIGHT = {348.0f, 0.0f, 8.0f, 0.0f, 255.0f, 81.0f, 207.0f};
    private static final float[] COLOR_CURVE_PRESET_SPATIAL_BACKGROUND_DARK = {150.0f, 0.0f, 8.0f, 0.0f, 255.0f, 2.0f, 152.0f};
    private static final float[] COLOR_CURVE_PRESET_DIM_BACKGROUND_DARK = {300.0f, 0.0f, 14.0f, 0.0f, 255.0f, 2.4f, 94.2f};
    private static final float[] COLOR_CURVE_PRESET_ULTRA_BACKGROUND_DARK = {348.0f, 0.0f, 8.0f, 0.0f, 255.0f, 41.0f, 167.0f};
    private static final float[][] BLUR_PRESET = {new float[]{300.0f, 0.1f, 5.0f, 0.0f, 250.0f, 17.8f, 247.3f}, new float[]{300.0f, 0.1f, 5.0f, 0.0f, 250.0f, 68.8f, 247.4f}, new float[]{300.0f, 0.1f, 5.0f, 0.0f, 250.0f, 118.6f, 246.1f}, new float[]{300.0f, 0.1f, 5.0f, 0.0f, 250.0f, 169.6f, 246.1f}, new float[]{300.0f, 0.1f, 5.0f, 0.0f, 250.0f, 208.1f, 246.3f}, new float[]{300.0f, 0.3f, 12.0f, 5.0f, 235.0f, 17.8f, 247.3f}, new float[]{300.0f, 0.3f, 12.0f, 15.0f, 235.0f, 68.8f, 247.4f}, new float[]{300.0f, 0.35f, 15.0f, 15.0f, 235.0f, 118.6f, 246.1f}, new float[]{300.0f, 0.4f, 15.0f, 15.0f, 235.0f, 169.6f, 246.1f}, new float[]{300.0f, 0.5f, 15.0f, 15.0f, 235.0f, 208.1f, 246.3f}, new float[]{300.0f, 0.4f, 20.0f, 5.0f, 235.0f, 17.8f, 247.3f}, new float[]{300.0f, 0.45f, 20.0f, 15.0f, 235.0f, 68.8f, 247.4f}, new float[]{300.0f, 0.5f, 23.0f, 15.0f, 235.0f, 118.6f, 246.1f}, new float[]{300.0f, 0.65f, 25.0f, 15.0f, 235.0f, 169.6f, 246.1f}, new float[]{300.0f, 0.75f, 25.0f, 15.0f, 235.0f, 208.1f, 246.3f}, new float[]{300.0f, 0.1f, -15.0f, 0.0f, 255.0f, 13.3f, 235.1f}, new float[]{300.0f, 0.1f, -15.0f, 0.0f, 255.0f, 14.0f, 205.3f}, new float[]{300.0f, 0.1f, -15.0f, 0.0f, 255.0f, 13.4f, 179.1f}, new float[]{300.0f, 0.1f, -20.0f, 0.0f, 255.0f, 13.5f, 133.4f}, new float[]{300.0f, 0.1f, -20.0f, 0.0f, 255.0f, 14.3f, 90.8f}, new float[]{300.0f, 0.35f, -5.0f, 0.0f, 255.0f, 13.3f, 235.1f}, new float[]{300.0f, 0.4f, -10.0f, 0.0f, 255.0f, 14.0f, 205.3f}, new float[]{300.0f, 0.45f, -10.0f, 0.0f, 255.0f, 13.4f, 179.1f}, new float[]{300.0f, 0.5f, -15.0f, 0.0f, 255.0f, 13.5f, 133.4f}, new float[]{300.0f, 0.55f, -15.0f, 0.0f, 255.0f, 14.3f, 90.8f}, new float[]{300.0f, 0.45f, -5.0f, 0.0f, 255.0f, 13.3f, 235.1f}, new float[]{300.0f, 0.5f, -10.0f, 0.0f, 255.0f, 14.0f, 205.3f}, new float[]{300.0f, 0.6f, -10.0f, 0.0f, 255.0f, 13.4f, 179.1f}, new float[]{300.0f, 0.65f, -15.0f, 0.0f, 255.0f, 13.5f, 133.4f}, new float[]{300.0f, 0.7f, -15.0f, 0.0f, 255.0f, 14.3f, 90.8f}, new float[]{110.0f, 0.25f, 10.0f, 15.0f, 255.0f, 40.2f, 205.9f}, new float[]{250.0f, 0.25f, 10.0f, 15.0f, 255.0f, 40.2f, 205.9f}, new float[]{400.0f, 0.25f, 10.0f, 15.0f, 255.0f, 40.2f, 205.9f}, new float[]{110.0f, 0.25f, 10.0f, 15.0f, 255.0f, 34.4f, 174.7f}, new float[]{250.0f, 0.25f, 10.0f, 15.0f, 255.0f, 34.4f, 174.7f}, new float[]{400.0f, 0.25f, 10.0f, 15.0f, 255.0f, 34.4f, 174.7f}, new float[]{400.0f, 0.25f, 10.0f, 15.0f, 255.0f, 14.0f, 128.8f}};
    public static final Parcelable.Creator<SemBlurInfo> CREATOR = new Parcelable.Creator<SemBlurInfo>() { // from class: android.view.SemBlurInfo.1
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
    public @interface BlurMode {
    }

    public SemBlurInfo(int blurMode, Bitmap capturedBitmap, int blurRadius, int backgroundBlurColor, float cornerRadiusTL, float cornerRadiusTR, float cornerRadiusBL, float cornerRadiusBR, int scale) {
        this.mBlurMode = blurMode;
        this.mCapturedBitmap = capturedBitmap;
        if (this.mCapturedBitmap == null) {
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

    public SemBlurInfo(int blurMode, Bitmap capturedBitmap, int blurRadius, int backgroundBlurColor, float cornerRadiusTL, float cornerRadiusTR, float cornerRadiusBL, float cornerRadiusBR, int scale, ColorCurve colorCurve) {
        this(blurMode, capturedBitmap, blurRadius, backgroundBlurColor, cornerRadiusTL, cornerRadiusTR, cornerRadiusBL, cornerRadiusBR, scale);
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
        this.mHasCapturedBitmap = in.readBoolean();
        if (this.mHasCapturedBitmap) {
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
            throw new IllegalStateException("Failed to getBackgroundBlurColor, because of blurMode is not BLUR_MODE_WINDOW");
        }
        return this.mBackgroundBlurColor;
    }

    public void getBackgroundBlurCornerRadius(float[] outRadius) {
        if (this.mBlurMode != 0) {
            throw new IllegalStateException("Failed to getBackgroundBlurCornerRadius, because of blurMode is not BLUR_MODE_WINDOW");
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
            throw new IllegalStateException("Failed to getCapturedBitmap, because of blurMode is not BLUR_MODE_WINDOW_CAPTURED");
        }
        return this.mCapturedBitmap;
    }

    public int getCanvasDownScale() {
        if (this.mBlurMode != 2) {
            throw new IllegalStateException("Failed to getCanvasDownScale, because of blurMode is not BLUR_MODE_CANVAS");
        }
        return this.mCanvasDownScale;
    }

    public ColorCurve getColorCurve() {
        return this.mColorCurve;
    }

    public String toString() {
        switch (this.mBlurMode) {
            case 0:
                return super.toString() + " {BLUR_MODE_WINDOW, mBlurRadius = " + this.mBlurRadius + ", mBackgroundBlurColor = " + this.mBackgroundBlurColor + ", mCornerRadiusTL = " + this.mCornerRadiusTL + ", mCornerRadiusTR = " + this.mCornerRadiusTR + ", mCornerRadiusBL = " + this.mCornerRadiusBL + ", mCornerRadiusBR = " + this.mCornerRadiusBR + ", mColorCurve = " + this.mColorCurve + "}";
            case 1:
                return super.toString() + " {BLUR_MODE_WINDOW_CAPTURED, mBlurRadius = " + this.mBlurRadius + ", mCapturedBitmap = " + this.mCapturedBitmap + ", mColorCurve = " + this.mColorCurve + "}";
            case 2:
                return super.toString() + " {BLUR_MODE_CANVAS, mBlurRadius = " + this.mBlurRadius + ", mCanvasDownScale = " + this.mCanvasDownScale + ", mColorCurve = " + this.mColorCurve + "}";
            default:
                return super.toString() + " {BLUR_MODE_NONE}";
        }
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
        private int mBlurRadius = 128;
        private long mBuilderFieldsSet = 0;
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
            this.mBuilderFieldsSet |= 1;
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
                Log.i(SemBlurInfo.TAG, "cornerRadius = (" + cornerRadius + ") is negative, set to 0.0f");
                cornerRadius = 0.0f;
            }
            setBackgroundCornerRadius(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
            return this;
        }

        private Builder hidden_setBackgroundCornerRadius(float cornerRadius) {
            return setBackgroundCornerRadius(cornerRadius);
        }

        public Builder setBackgroundCornerRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
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

        public Builder setColorCurve(float saturation, float curve, float minX, float maxX, float minY, float maxY) {
            this.mBuilderFieldsSet |= 128;
            this.mColorCurve = new ColorCurve(checkValueRange(saturation, 1.0f, -1.0f), checkValueRange(curve, 100.0f, CURVEGRAPH_CURVE_MIN_VALUE), checkValueRange(minX, CURVEGRAPH_MAX_XY_VALUE, 0.0f), checkValueRange(maxX, CURVEGRAPH_MAX_XY_VALUE, 0.0f), checkValueRange(minY, CURVEGRAPH_MAX_XY_VALUE, 0.0f), checkValueRange(maxY, CURVEGRAPH_MAX_XY_VALUE, 0.0f));
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
                this.mColorCurve = new ColorCurve(presetAttrs[1], presetAttrs[2], presetAttrs[3], presetAttrs[4], presetAttrs[5], presetAttrs[6]);
            }
            return this;
        }

        public static float[] getBlurPresetAttrs(int preset) {
            if (preset >= 101 && preset <= 137) {
                return SemBlurInfo.BLUR_PRESET[preset - 101];
            }
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
                    Log.e(SemBlurInfo.TAG, "BlurPreset (" + preset + ") is not valid. getBlurPresetAttrs return null");
                    return null;
            }
        }

        public SemBlurInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is not set");
            }
            if ((this.mBuilderFieldsSet & 128) != 0 && (this.mBuilderFieldsSet & 64) != 0) {
                throw new IllegalStateException("Failed to create SemBlurInfo, BlurPreset and BlurColorCurve can not be used together");
            }
            if ((this.mBuilderFieldsSet & 64) != 0 && this.mColorCurve == null) {
                throw new IllegalStateException("Failed to create SemBlurInfo, you set the wrong preset value " + this.mPreset);
            }
            if (this.mBlurMode == 0) {
                if ((this.mBuilderFieldsSet & 2) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW, can not set capturedBitmap");
                }
                if ((this.mBuilderFieldsSet & 32) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW, can not set canvasDownScale");
                }
            } else if (this.mBlurMode == 1) {
                if ((this.mBuilderFieldsSet & 2) == 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, must set capturedBitmap");
                }
                if ((this.mBuilderFieldsSet & 8) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set backgroundColor");
                }
                if ((this.mBuilderFieldsSet & 16) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set backgroundCornerRadius");
                }
                if ((this.mBuilderFieldsSet & 32) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set canvasDownScale");
                }
            } else if (this.mBlurMode == 2) {
                if ((this.mBuilderFieldsSet & 2) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not capturedBitmap");
                }
                if ((this.mBuilderFieldsSet & 8) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not set backgroundColor");
                }
                if ((this.mBuilderFieldsSet & 16) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not set backgroundCornerRadius");
                }
            }
            return new SemBlurInfo(this.mBlurMode, this.mCapturedBitmap, this.mBlurRadius, this.mBackgroundBlurColor, this.mCornerRadiusTL, this.mCornerRadiusTR, this.mCornerRadiusBL, this.mCornerRadiusBR, this.mCanvasDownScale, this.mColorCurve);
        }

        private SemBlurInfo hidden_build() {
            return build();
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 256) != 0) {
                throw new IllegalStateException("Failed to create SemBlurInfo, This Builder should not be reused. Use a new Builder instance instead");
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

        public ColorCurve(float saturation, float curve, float minX, float maxX, float minY, float maxY) {
            this.mSaturation = saturation;
            this.mCurveBias = curve;
            this.mMinX = minX;
            this.mMaxX = maxX;
            this.mMinY = minY;
            this.mMaxY = maxY;
        }

        public String toString() {
            return super.toString() + " {minX = " + this.mMinX + ", minY = " + this.mMinY + ", maxX = " + this.mMaxX + ", maxY = " + this.mMaxY + ", curveBias = " + this.mCurveBias + ", saturation = " + this.mSaturation + "}";
        }
    }
}
