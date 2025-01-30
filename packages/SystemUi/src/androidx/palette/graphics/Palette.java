package androidx.palette.graphics;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import androidx.collection.ArrayMap;
import androidx.core.graphics.ColorUtils;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Palette {
    public static final C03491 DEFAULT_FILTER = new Filter() { // from class: androidx.palette.graphics.Palette.1
    };
    public final Swatch mDominantSwatch;
    public final List mSwatches;
    public final List mTargets;
    public final SparseBooleanArray mUsedColors = new SparseBooleanArray();
    public final ArrayMap mSelectedSwatches = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Filter {
    }

    public Palette(List<Swatch> list, List<Target> list2) {
        this.mSwatches = list;
        this.mTargets = list2;
        int size = list.size();
        int i = VideoPlayer.MEDIA_ERROR_SYSTEM;
        Swatch swatch = null;
        for (int i2 = 0; i2 < size; i2++) {
            Swatch swatch2 = list.get(i2);
            int i3 = swatch2.mPopulation;
            if (i3 > i) {
                swatch = swatch2;
                i = i3;
            }
        }
        this.mDominantSwatch = swatch;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Swatch {
        public final int mBlue;
        public int mBodyTextColor;
        public boolean mGeneratedTextColors;
        public final int mGreen;
        public float[] mHsl;
        public final int mPopulation;
        public final int mRed;
        public final int mRgb;
        public int mTitleTextColor;

        public Swatch(int i, int i2) {
            this.mRed = Color.red(i);
            this.mGreen = Color.green(i);
            this.mBlue = Color.blue(i);
            this.mRgb = i;
            this.mPopulation = i2;
        }

        public final void ensureTextColorsGenerated() {
            if (this.mGeneratedTextColors) {
                return;
            }
            int i = this.mRgb;
            int calculateMinimumAlpha = ColorUtils.calculateMinimumAlpha(4.5f, -1, i);
            int calculateMinimumAlpha2 = ColorUtils.calculateMinimumAlpha(3.0f, -1, i);
            if (calculateMinimumAlpha != -1 && calculateMinimumAlpha2 != -1) {
                this.mBodyTextColor = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha);
                this.mTitleTextColor = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha2);
                this.mGeneratedTextColors = true;
                return;
            }
            int calculateMinimumAlpha3 = ColorUtils.calculateMinimumAlpha(4.5f, EmergencyPhoneWidget.BG_COLOR, i);
            int calculateMinimumAlpha4 = ColorUtils.calculateMinimumAlpha(3.0f, EmergencyPhoneWidget.BG_COLOR, i);
            if (calculateMinimumAlpha3 == -1 || calculateMinimumAlpha4 == -1) {
                this.mBodyTextColor = calculateMinimumAlpha != -1 ? ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha) : ColorUtils.setAlphaComponent(EmergencyPhoneWidget.BG_COLOR, calculateMinimumAlpha3);
                this.mTitleTextColor = calculateMinimumAlpha2 != -1 ? ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha2) : ColorUtils.setAlphaComponent(EmergencyPhoneWidget.BG_COLOR, calculateMinimumAlpha4);
                this.mGeneratedTextColors = true;
            } else {
                this.mBodyTextColor = ColorUtils.setAlphaComponent(EmergencyPhoneWidget.BG_COLOR, calculateMinimumAlpha3);
                this.mTitleTextColor = ColorUtils.setAlphaComponent(EmergencyPhoneWidget.BG_COLOR, calculateMinimumAlpha4);
                this.mGeneratedTextColors = true;
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || Swatch.class != obj.getClass()) {
                return false;
            }
            Swatch swatch = (Swatch) obj;
            return this.mPopulation == swatch.mPopulation && this.mRgb == swatch.mRgb;
        }

        public final float[] getHsl() {
            if (this.mHsl == null) {
                this.mHsl = new float[3];
            }
            ColorUtils.RGBToHSL(this.mRed, this.mGreen, this.mBlue, this.mHsl);
            return this.mHsl;
        }

        public final int hashCode() {
            return (this.mRgb * 31) + this.mPopulation;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(Swatch.class.getSimpleName());
            sb.append(" [RGB: #");
            sb.append(Integer.toHexString(this.mRgb));
            sb.append("] [HSL: ");
            sb.append(Arrays.toString(getHsl()));
            sb.append("] [Population: ");
            sb.append(this.mPopulation);
            sb.append("] [Title Text: #");
            ensureTextColorsGenerated();
            sb.append(Integer.toHexString(this.mTitleTextColor));
            sb.append("] [Body Text: #");
            ensureTextColorsGenerated();
            sb.append(Integer.toHexString(this.mBodyTextColor));
            sb.append(']');
            return sb.toString();
        }

        public Swatch(int i, int i2, int i3, int i4) {
            this.mRed = i;
            this.mGreen = i2;
            this.mBlue = i3;
            this.mRgb = Color.rgb(i, i2, i3);
            this.mPopulation = i4;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Swatch(float[] fArr, int i) {
            this(Color.rgb(ColorUtils.constrain(r0), ColorUtils.constrain(r1), ColorUtils.constrain(r2)), i);
            int round;
            int round2;
            ThreadLocal threadLocal = ColorUtils.TEMP_ARRAY;
            int i2 = 0;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float abs = (1.0f - Math.abs((f3 * 2.0f) - 1.0f)) * f2;
            float f4 = f3 - (0.5f * abs);
            float abs2 = (1.0f - Math.abs(((f / 60.0f) % 2.0f) - 1.0f)) * abs;
            switch (((int) f) / 60) {
                case 0:
                    i2 = Math.round((abs + f4) * 255.0f);
                    round = Math.round((abs2 + f4) * 255.0f);
                    round2 = Math.round(f4 * 255.0f);
                    break;
                case 1:
                    i2 = Math.round((abs2 + f4) * 255.0f);
                    round = Math.round((abs + f4) * 255.0f);
                    round2 = Math.round(f4 * 255.0f);
                    break;
                case 2:
                    i2 = Math.round(f4 * 255.0f);
                    round = Math.round((abs + f4) * 255.0f);
                    round2 = Math.round((abs2 + f4) * 255.0f);
                    break;
                case 3:
                    i2 = Math.round(f4 * 255.0f);
                    round = Math.round((abs2 + f4) * 255.0f);
                    round2 = Math.round((abs + f4) * 255.0f);
                    break;
                case 4:
                    i2 = Math.round((abs2 + f4) * 255.0f);
                    round = Math.round(f4 * 255.0f);
                    round2 = Math.round((abs + f4) * 255.0f);
                    break;
                case 5:
                case 6:
                    i2 = Math.round((abs + f4) * 255.0f);
                    round = Math.round(f4 * 255.0f);
                    round2 = Math.round((abs2 + f4) * 255.0f);
                    break;
                default:
                    round = 0;
                    round2 = 0;
                    break;
            }
            this.mHsl = fArr;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final Bitmap mBitmap;
        public final List mFilters;
        public int mMaxColors;
        public final int mResizeArea;
        public final int mResizeMaxDimension;
        public final List mSwatches;
        public final List mTargets;

        public Builder(Bitmap bitmap) {
            ArrayList arrayList = new ArrayList();
            this.mTargets = arrayList;
            this.mMaxColors = 16;
            this.mResizeArea = 12544;
            this.mResizeMaxDimension = -1;
            ArrayList arrayList2 = new ArrayList();
            this.mFilters = arrayList2;
            if (bitmap == null || bitmap.isRecycled()) {
                throw new IllegalArgumentException("Bitmap is not valid");
            }
            arrayList2.add(Palette.DEFAULT_FILTER);
            this.mBitmap = bitmap;
            this.mSwatches = null;
            arrayList.add(Target.LIGHT_VIBRANT);
            arrayList.add(Target.VIBRANT);
            arrayList.add(Target.DARK_VIBRANT);
            arrayList.add(Target.LIGHT_MUTED);
            arrayList.add(Target.MUTED);
            arrayList.add(Target.DARK_MUTED);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x003d  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x007c  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x008f  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x011c  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0180  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x007e  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x003f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Palette generate() {
            List list;
            int i;
            List list2;
            int i2;
            boolean z;
            int i3;
            char c;
            float f;
            int max;
            double d;
            Bitmap createScaledBitmap;
            int i4 = 0;
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                int i5 = this.mResizeArea;
                if (i5 > 0) {
                    int height = bitmap.getHeight() * bitmap.getWidth();
                    if (height > i5) {
                        d = Math.sqrt(i5 / height);
                        createScaledBitmap = d > 0.0d ? bitmap : Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(bitmap.getWidth() * d), (int) Math.ceil(bitmap.getHeight() * d), false);
                        int width = createScaledBitmap.getWidth();
                        int height2 = createScaledBitmap.getHeight();
                        int[] iArr = new int[width * height2];
                        createScaledBitmap.getPixels(iArr, 0, width, 0, 0, width, height2);
                        int i6 = this.mMaxColors;
                        ArrayList arrayList = (ArrayList) this.mFilters;
                        ColorCutQuantizer colorCutQuantizer = new ColorCutQuantizer(iArr, i6, !arrayList.isEmpty() ? null : (Filter[]) arrayList.toArray(new Filter[arrayList.size()]));
                        if (createScaledBitmap != bitmap) {
                            createScaledBitmap.recycle();
                        }
                        list = colorCutQuantizer.mQuantizedColors;
                    }
                    d = -1.0d;
                    if (d > 0.0d) {
                    }
                    int width2 = createScaledBitmap.getWidth();
                    int height22 = createScaledBitmap.getHeight();
                    int[] iArr2 = new int[width2 * height22];
                    createScaledBitmap.getPixels(iArr2, 0, width2, 0, 0, width2, height22);
                    int i62 = this.mMaxColors;
                    ArrayList arrayList2 = (ArrayList) this.mFilters;
                    ColorCutQuantizer colorCutQuantizer2 = new ColorCutQuantizer(iArr2, i62, !arrayList2.isEmpty() ? null : (Filter[]) arrayList2.toArray(new Filter[arrayList2.size()]));
                    if (createScaledBitmap != bitmap) {
                    }
                    list = colorCutQuantizer2.mQuantizedColors;
                } else {
                    int i7 = this.mResizeMaxDimension;
                    if (i7 > 0 && (max = Math.max(bitmap.getWidth(), bitmap.getHeight())) > i7) {
                        d = i7 / max;
                        if (d > 0.0d) {
                        }
                        int width22 = createScaledBitmap.getWidth();
                        int height222 = createScaledBitmap.getHeight();
                        int[] iArr22 = new int[width22 * height222];
                        createScaledBitmap.getPixels(iArr22, 0, width22, 0, 0, width22, height222);
                        int i622 = this.mMaxColors;
                        ArrayList arrayList22 = (ArrayList) this.mFilters;
                        ColorCutQuantizer colorCutQuantizer22 = new ColorCutQuantizer(iArr22, i622, !arrayList22.isEmpty() ? null : (Filter[]) arrayList22.toArray(new Filter[arrayList22.size()]));
                        if (createScaledBitmap != bitmap) {
                        }
                        list = colorCutQuantizer22.mQuantizedColors;
                    }
                    d = -1.0d;
                    if (d > 0.0d) {
                    }
                    int width222 = createScaledBitmap.getWidth();
                    int height2222 = createScaledBitmap.getHeight();
                    int[] iArr222 = new int[width222 * height2222];
                    createScaledBitmap.getPixels(iArr222, 0, width222, 0, 0, width222, height2222);
                    int i6222 = this.mMaxColors;
                    ArrayList arrayList222 = (ArrayList) this.mFilters;
                    ColorCutQuantizer colorCutQuantizer222 = new ColorCutQuantizer(iArr222, i6222, !arrayList222.isEmpty() ? null : (Filter[]) arrayList222.toArray(new Filter[arrayList222.size()]));
                    if (createScaledBitmap != bitmap) {
                    }
                    list = colorCutQuantizer222.mQuantizedColors;
                }
            } else {
                list = this.mSwatches;
                if (list == null) {
                    throw new AssertionError();
                }
            }
            Palette palette = new Palette(list, this.mTargets);
            List list3 = palette.mTargets;
            int size = list3.size();
            int i8 = 0;
            while (true) {
                SparseBooleanArray sparseBooleanArray = palette.mUsedColors;
                if (i8 >= size) {
                    sparseBooleanArray.clear();
                    return palette;
                }
                Target target = (Target) list3.get(i8);
                float[] fArr = target.mWeights;
                int length = fArr.length;
                float f2 = 0.0f;
                for (int i9 = i4; i9 < length; i9++) {
                    float f3 = fArr[i9];
                    if (f3 > 0.0f) {
                        f2 += f3;
                    }
                }
                if (f2 != 0.0f) {
                    int length2 = fArr.length;
                    for (int i10 = i4; i10 < length2; i10++) {
                        float f4 = fArr[i10];
                        if (f4 > 0.0f) {
                            fArr[i10] = f4 / f2;
                        }
                    }
                }
                List list4 = palette.mSwatches;
                int size2 = list4.size();
                int i11 = i4;
                float f5 = 0.0f;
                Swatch swatch = null;
                while (i11 < size2) {
                    Swatch swatch2 = (Swatch) list4.get(i11);
                    float[] hsl = swatch2.getHsl();
                    float f6 = hsl[1];
                    float[] fArr2 = target.mSaturationTargets;
                    float f7 = fArr2[i4];
                    float[] fArr3 = target.mLightnessTargets;
                    if (f6 >= f7 && f6 <= fArr2[2]) {
                        float f8 = hsl[2];
                        if (f8 >= fArr3[i4] && f8 <= fArr3[2] && !sparseBooleanArray.get(swatch2.mRgb)) {
                            i = 1;
                            if (i == 0) {
                                float[] hsl2 = swatch2.getHsl();
                                Swatch swatch3 = palette.mDominantSwatch;
                                if (swatch3 != null) {
                                    i3 = swatch3.mPopulation;
                                    list2 = list3;
                                } else {
                                    list2 = list3;
                                    i3 = 1;
                                }
                                float[] fArr4 = target.mWeights;
                                i2 = 0;
                                float f9 = fArr4[0];
                                if (f9 > 0.0f) {
                                    c = 1;
                                    f = (1.0f - Math.abs(hsl2[1] - fArr2[1])) * f9;
                                } else {
                                    c = 1;
                                    f = 0.0f;
                                }
                                float f10 = fArr4[c];
                                z = false;
                                float abs = f10 > 0.0f ? (1.0f - Math.abs(hsl2[2] - fArr3[c])) * f10 : 0.0f;
                                float f11 = fArr4[2];
                                float f12 = f + abs + (f11 > 0.0f ? f11 * (swatch2.mPopulation / i3) : 0.0f);
                                if (swatch == null || f12 > f5) {
                                    f5 = f12;
                                    swatch = swatch2;
                                }
                            } else {
                                list2 = list3;
                                i2 = i4;
                                z = false;
                            }
                            i11++;
                            i4 = i2;
                            list3 = list2;
                        }
                    }
                    i = i4;
                    if (i == 0) {
                    }
                    i11++;
                    i4 = i2;
                    list3 = list2;
                }
                List list5 = list3;
                int i12 = i4;
                if (swatch != null && target.mIsExclusive) {
                    sparseBooleanArray.append(swatch.mRgb, true);
                }
                palette.mSelectedSwatches.put(target, swatch);
                i8++;
                i4 = i12;
                list3 = list5;
            }
        }

        public Builder(List<Swatch> list) {
            this.mTargets = new ArrayList();
            this.mMaxColors = 16;
            this.mResizeArea = 12544;
            this.mResizeMaxDimension = -1;
            ArrayList arrayList = new ArrayList();
            this.mFilters = arrayList;
            if (list != null && !list.isEmpty()) {
                arrayList.add(Palette.DEFAULT_FILTER);
                this.mSwatches = list;
                this.mBitmap = null;
                return;
            }
            throw new IllegalArgumentException("List of Swatches is not valid");
        }
    }
}
