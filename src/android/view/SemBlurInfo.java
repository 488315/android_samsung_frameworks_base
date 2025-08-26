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
    private int mClipRectBottom;
    private int mClipRectLeft;
    private int mClipRectRight;
    private int mClipRectTop;
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
        public SemBlurInfo createFromParcel(Parcel parcel) {
            return new SemBlurInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBlurInfo[] newArray(int i) {
            return new SemBlurInfo[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BlurMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemBlurInfo(int i, Bitmap bitmap, int i2, int i3, float f, float f2, float f3, float f4, int i4) {
        this.mBlurMode = i;
        this.mCapturedBitmap = bitmap;
        if (bitmap == null) {
            this.mHasCapturedBitmap = false;
        } else {
            this.mHasCapturedBitmap = true;
        }
        this.mBlurRadius = i2;
        this.mBackgroundBlurColor = i3;
        this.mCornerRadiusTL = f;
        this.mCornerRadiusTR = f2;
        this.mCornerRadiusBL = f3;
        this.mCornerRadiusBR = f4;
        this.mCanvasDownScale = i4;
    }

    public SemBlurInfo(int i, Bitmap bitmap, int i2, int i3, float f, float f2, float f3, float f4, int i4, ColorCurve colorCurve) {
        this(i, bitmap, i2, i3, f, f2, f3, f4, i4);
        this.mColorCurve = colorCurve;
    }

    public SemBlurInfo(int i, Bitmap bitmap, int i2, int i3, float f, float f2, float f3, float f4, int i4, int i5, int i6, int i7, int i8, ColorCurve colorCurve) {
        this(i, bitmap, i2, i3, f, f2, f3, f4, i8, colorCurve);
        this.mClipRectLeft = i4;
        this.mClipRectTop = i5;
        this.mClipRectRight = i6;
        this.mClipRectBottom = i7;
    }

    protected SemBlurInfo(Parcel parcel) {
        this.mBlurMode = parcel.readInt();
        this.mBlurRadius = parcel.readInt();
        this.mBackgroundBlurColor = parcel.readInt();
        this.mCanvasDownScale = parcel.readInt();
        this.mCornerRadiusTL = parcel.readFloat();
        this.mCornerRadiusTR = parcel.readFloat();
        this.mCornerRadiusBL = parcel.readFloat();
        this.mCornerRadiusBR = parcel.readFloat();
        this.mClipRectLeft = parcel.readInt();
        this.mClipRectTop = parcel.readInt();
        this.mClipRectRight = parcel.readInt();
        this.mClipRectBottom = parcel.readInt();
        boolean z = parcel.readBoolean();
        this.mHasCapturedBitmap = z;
        if (z) {
            this.mCapturedBitmap = Bitmap.CREATOR.createFromParcel(parcel);
        } else {
            this.mCapturedBitmap = null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mBlurMode);
        parcel.writeInt(this.mBlurRadius);
        parcel.writeInt(this.mBackgroundBlurColor);
        parcel.writeInt(this.mCanvasDownScale);
        parcel.writeFloat(this.mCornerRadiusTL);
        parcel.writeFloat(this.mCornerRadiusTR);
        parcel.writeFloat(this.mCornerRadiusBL);
        parcel.writeFloat(this.mCornerRadiusBR);
        parcel.writeInt(this.mClipRectLeft);
        parcel.writeInt(this.mClipRectTop);
        parcel.writeInt(this.mClipRectRight);
        parcel.writeInt(this.mClipRectBottom);
        parcel.writeBoolean(this.mHasCapturedBitmap);
        if (this.mHasCapturedBitmap) {
            this.mCapturedBitmap.writeToParcel(parcel, i);
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

    public void getBackgroundBlurCornerRadius(float[] fArr) {
        if (this.mBlurMode != 0) {
            throw new IllegalStateException("Failed to getBackgroundBlurCornerRadius, because of blurMode is not BLUR_MODE_WINDOW");
        }
        if (fArr == null || fArr.length < 4) {
            throw new IllegalArgumentException("outRadius must be an array of four integers");
        }
        fArr[0] = this.mCornerRadiusTL;
        fArr[1] = this.mCornerRadiusTR;
        fArr[2] = this.mCornerRadiusBL;
        fArr[3] = this.mCornerRadiusBR;
    }

    public void getBackgroundClipRect(int[] iArr) {
        if (this.mBlurMode != 0) {
            throw new IllegalStateException("Failed to getBackgroundClipRect, because of blurMode is not BLUR_MODE_WINDOW");
        }
        if (iArr == null || iArr.length < 4) {
            throw new IllegalArgumentException("outClipRect must be an array of four integers");
        }
        iArr[0] = this.mClipRectLeft;
        iArr[1] = this.mClipRectTop;
        iArr[2] = this.mClipRectRight;
        iArr[3] = this.mClipRectBottom;
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
        int i = this.mBlurMode;
        if (i != 0) {
            if (i == 1) {
                return super.toString() + " {BLUR_MODE_WINDOW_CAPTURED, mBlurRadius = " + this.mBlurRadius + ", mCapturedBitmap = " + this.mCapturedBitmap + ", mColorCurve = " + this.mColorCurve + "}";
            }
            if (i == 2) {
                return super.toString() + " {BLUR_MODE_CANVAS, mBlurRadius = " + this.mBlurRadius + ", mCanvasDownScale = " + this.mCanvasDownScale + ", mColorCurve = " + this.mColorCurve + "}";
            }
            return super.toString() + " {BLUR_MODE_NONE}";
        }
        return super.toString() + " {BLUR_MODE_WINDOW, mBlurRadius = " + this.mBlurRadius + ", mBackgroundBlurColor = " + this.mBackgroundBlurColor + ", mCornerRadiusTL = " + this.mCornerRadiusTL + ", mCornerRadiusTR = " + this.mCornerRadiusTR + ", mCornerRadiusBL = " + this.mCornerRadiusBL + ", mCornerRadiusBR = " + this.mCornerRadiusBR + ", mClipRectLeft = " + this.mClipRectLeft + ", mClipRectTop = " + this.mClipRectTop + ", mClipRectRight = " + this.mClipRectRight + ", mClipRectBottom = " + this.mClipRectBottom + ", mColorCurve = " + this.mColorCurve + "}";
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
        private Bitmap mCapturedBitmap = null;
        private int mCanvasDownScale = 8;
        private int mBackgroundBlurColor = 0;
        private float mCornerRadiusTL = 0.0f;
        private float mCornerRadiusTR = 0.0f;
        private float mCornerRadiusBL = 0.0f;
        private float mCornerRadiusBR = 0.0f;
        private int mClipRectLeft = 0;
        private int mClipRectTop = 0;
        private int mClipRectRight = Integer.MAX_VALUE;
        private int mClipRectBottom = Integer.MAX_VALUE;
        private ColorCurve mColorCurve = null;
        private int mPreset = 0;
        private long mBuilderFieldsSet = 1;

        private float checkValueRange(float f, float f2, float f3) {
            return f > f2 ? f2 : f < f3 ? f3 : f;
        }

        public Builder(int i) {
            this.mBlurMode = i;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.mBuilderFieldsSet |= 2;
            this.mCapturedBitmap = bitmap;
            return this;
        }

        public Builder setRadius(int i) {
            this.mBuilderFieldsSet |= 4;
            this.mBlurRadius = i;
            return this;
        }

        private Builder hidden_setRadius(int i) {
            return setRadius(i);
        }

        public Builder setBackgroundColor(int i) {
            this.mBuilderFieldsSet |= 8;
            this.mBackgroundBlurColor = i;
            return this;
        }

        private Builder hidden_setBackgroundColor(int i) {
            return setBackgroundColor(i);
        }

        public Builder setBackgroundCornerRadius(float f) {
            this.mBuilderFieldsSet |= 16;
            if (f < 0.0f) {
                Log.i(SemBlurInfo.TAG, "cornerRadius = (" + f + ") is negative, set to 0.0f");
                f = 0.0f;
            }
            setBackgroundCornerRadius(f, f, f, f);
            return this;
        }

        private Builder hidden_setBackgroundCornerRadius(float f) {
            return setBackgroundCornerRadius(f);
        }

        public Builder setBackgroundCornerRadius(float f, float f2, float f3, float f4) {
            this.mBuilderFieldsSet |= 16;
            this.mCornerRadiusTL = f;
            this.mCornerRadiusTR = f2;
            this.mCornerRadiusBL = f3;
            this.mCornerRadiusBR = f4;
            return this;
        }

        public Builder setBackgroundClipRect(int i, int i2, int i3, int i4) {
            this.mClipRectLeft = i;
            this.mClipRectTop = i2;
            this.mClipRectRight = i3;
            this.mClipRectBottom = i4;
            return this;
        }

        public Builder setCanvasScale(int i) {
            this.mBuilderFieldsSet |= 32;
            this.mCanvasDownScale = i;
            return this;
        }

        public Builder setColorCurve(float f, float f2, float f3, float f4, float f5, float f6) {
            this.mBuilderFieldsSet |= 128;
            this.mColorCurve = new ColorCurve(checkValueRange(f, 1.0f, -1.0f), checkValueRange(f2, 100.0f, CURVEGRAPH_CURVE_MIN_VALUE), checkValueRange(f3, CURVEGRAPH_MAX_XY_VALUE, 0.0f), checkValueRange(f4, CURVEGRAPH_MAX_XY_VALUE, 0.0f), checkValueRange(f5, CURVEGRAPH_MAX_XY_VALUE, 0.0f), checkValueRange(f6, CURVEGRAPH_MAX_XY_VALUE, 0.0f));
            return this;
        }

        public Builder setColorCurvePreset(int i) {
            this.mBuilderFieldsSet |= 64;
            this.mPreset = i;
            float[] blurPresetAttrs = getBlurPresetAttrs(i);
            if (blurPresetAttrs != null) {
                setRadius((int) blurPresetAttrs[0]);
                this.mColorCurve = new ColorCurve(blurPresetAttrs[1], blurPresetAttrs[2], blurPresetAttrs[3], blurPresetAttrs[4], blurPresetAttrs[5], blurPresetAttrs[6]);
            }
            return this;
        }

        public static float[] getBlurPresetAttrs(int i) {
            if (i >= 101 && i <= 137) {
                return SemBlurInfo.BLUR_PRESET[i - 101];
            }
            switch (i) {
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
                    Log.e(SemBlurInfo.TAG, "BlurPreset (" + i + ") is not valid. getBlurPresetAttrs return null");
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
                throw new IllegalStateException("Failed to create SemBlurInfo, BlurPreset and BlurColorCurve can not be used together");
            }
            if ((j & 64) != 0 && this.mColorCurve == null) {
                throw new IllegalStateException("Failed to create SemBlurInfo, you set the wrong preset value " + this.mPreset);
            }
            int i = this.mBlurMode;
            if (i == 0) {
                if ((j & 2) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW, can not set capturedBitmap");
                }
                if ((j & 32) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW, can not set canvasDownScale");
                }
            } else if (i == 1) {
                if ((j & 2) == 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, must set capturedBitmap");
                }
                if ((j & 8) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set backgroundColor");
                }
                if ((j & 16) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set backgroundCornerRadius");
                }
                if ((j & 32) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_WINDOW_CAPTURED, can not set canvasDownScale");
                }
            } else if (i == 2) {
                if ((j & 2) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not capturedBitmap");
                }
                if ((j & 8) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not set backgroundColor");
                }
                if ((j & 16) != 0) {
                    throw new IllegalStateException("Failed to create SemBlurInfo, Blurmode is BLUR_MODE_CANVAS, can not set backgroundCornerRadius");
                }
            }
            return new SemBlurInfo(this.mBlurMode, this.mCapturedBitmap, this.mBlurRadius, this.mBackgroundBlurColor, this.mCornerRadiusTL, this.mCornerRadiusTR, this.mCornerRadiusBL, this.mCornerRadiusBR, this.mClipRectLeft, this.mClipRectTop, this.mClipRectRight, this.mClipRectBottom, this.mCanvasDownScale, this.mColorCurve);
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

        public ColorCurve(float f, float f2, float f3, float f4, float f5, float f6) {
            this.mSaturation = f;
            this.mCurveBias = f2;
            this.mMinX = f3;
            this.mMaxX = f4;
            this.mMinY = f5;
            this.mMaxY = f6;
        }

        public String toString() {
            return super.toString() + " {minX = " + this.mMinX + ", minY = " + this.mMinY + ", maxX = " + this.mMaxX + ", maxY = " + this.mMaxY + ", curveBias = " + this.mCurveBias + ", saturation = " + this.mSaturation + "}";
        }
    }
}
