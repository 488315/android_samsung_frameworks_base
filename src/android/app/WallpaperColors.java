package android.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.MathUtils;
import android.util.Size;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.cam.Cam;
import com.android.internal.graphics.palette.CelebiQuantizer;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.VariationalKMeansQuantizer;
import com.android.internal.util.ContrastColorUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class WallpaperColors implements Parcelable {
    private static final float DARK_PIXEL_CONTRAST = 5.5f;
    private static final float DARK_THEME_MEAN_LUMINANCE = 0.3f;
    private static final boolean DEBUG_DARK_PIXELS = false;
    public static final int HINT_FROM_BITMAP = 4;
    public static final int HINT_SUPPORTS_DARK_TEXT = 1;
    public static final int HINT_SUPPORTS_DARK_THEME = 2;
    private static final int MAX_BITMAP_SIZE = 112;
    private static final int MAX_WALLPAPER_EXTRACTION_AREA = 12544;
    private static final float MIN_COLOR_OCCURRENCE = 0.05f;
    private final Map<Integer, Integer> mAllColors;
    private int mColorHints;
    private final List<Color> mMainColors;
    private static final float BRIGHT_IMAGE_MEAN_LUMINANCE = SystemProperties.getInt("persist.wallpapercolors.threshold", 70) / 100.0f;
    private static final float MAX_DARK_AREA = SystemProperties.getInt("persist.wallpapercolors.max_dark_area", 5) / 100.0f;
    public static final Parcelable.Creator<WallpaperColors> CREATOR = new Parcelable.Creator<WallpaperColors>() { // from class: android.app.WallpaperColors.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperColors createFromParcel(Parcel parcel) {
            return new WallpaperColors(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperColors[] newArray(int i) {
            return new WallpaperColors[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorsHints {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WallpaperColors(Parcel parcel) {
        this.mMainColors = new ArrayList();
        this.mAllColors = new HashMap();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mMainColors.add(Color.valueOf(parcel.readInt()));
        }
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            this.mAllColors.put(Integer.valueOf(parcel.readInt()), Integer.valueOf(parcel.readInt()));
        }
        this.mColorHints = parcel.readInt();
    }

    public static WallpaperColors fromDrawable(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null");
        }
        Trace.beginSection("WallpaperColors#fromDrawable");
        Rect rectCopyBounds = drawable.copyBounds();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            intrinsicWidth = 112;
            intrinsicHeight = 112;
        }
        Size sizeCalculateOptimalSize = calculateOptimalSize(intrinsicWidth, intrinsicHeight);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(sizeCalculateOptimalSize.getWidth(), sizeCalculateOptimalSize.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        drawable.draw(canvas);
        WallpaperColors wallpaperColorsFromBitmap = fromBitmap(bitmapCreateBitmap);
        bitmapCreateBitmap.recycle();
        drawable.setBounds(rectCopyBounds);
        Trace.endSection();
        return wallpaperColorsFromBitmap;
    }

    public static WallpaperColors fromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap can't be null");
        }
        return fromBitmap(bitmap, 0.0f);
    }

    public static WallpaperColors fromBitmap(Bitmap bitmap, float f) {
        boolean z;
        Palette paletteGenerate;
        Objects.requireNonNull(bitmap, "Bitmap can't be null");
        Trace.beginSection("WallpaperColors#fromBitmap");
        int width = bitmap.getWidth() * bitmap.getHeight();
        if (width > MAX_WALLPAPER_EXTRACTION_AREA) {
            Size sizeCalculateOptimalSize = calculateOptimalSize(bitmap.getWidth(), bitmap.getHeight());
            bitmap = Bitmap.createScaledBitmap(bitmap, sizeCalculateOptimalSize.getWidth(), sizeCalculateOptimalSize.getHeight(), false);
            z = true;
        } else {
            z = false;
        }
        if (ActivityManager.isLowRamDeviceStatic()) {
            paletteGenerate = Palette.from(bitmap, new VariationalKMeansQuantizer()).maximumColorCount(5).resizeBitmapArea(MAX_WALLPAPER_EXTRACTION_AREA).generate();
        } else {
            paletteGenerate = Palette.from(bitmap, new CelebiQuantizer()).maximumColorCount(Math.max(5, Math.min(128, width / 16))).resizeBitmapArea(MAX_WALLPAPER_EXTRACTION_AREA).generate();
        }
        ArrayList arrayList = new ArrayList(paletteGenerate.getSwatches());
        arrayList.sort(new Comparator() { // from class: android.app.WallpaperColors$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return WallpaperColors.lambda$fromBitmap$0((Palette.Swatch) obj, (Palette.Swatch) obj2);
            }
        });
        int size = arrayList.size();
        HashMap map = new HashMap();
        for (int i = 0; i < size; i++) {
            Palette.Swatch swatch = (Palette.Swatch) arrayList.get(i);
            map.put(Integer.valueOf(swatch.getInt()), Integer.valueOf(swatch.getPopulation()));
        }
        int iCalculateDarkHints = calculateDarkHints(bitmap, f);
        if (z) {
            bitmap.recycle();
        }
        Trace.endSection();
        return new WallpaperColors(map, iCalculateDarkHints | 4);
    }

    static /* synthetic */ int lambda$fromBitmap$0(Palette.Swatch swatch, Palette.Swatch swatch2) {
        return swatch2.getPopulation() - swatch.getPopulation();
    }

    public WallpaperColors(Color color, Color color2, Color color3) {
        this(color, color2, color3, 0);
        float[] fArr = new float[3];
        ColorUtils.colorToHSL(color.toArgb(), fArr);
        if (fArr[2] < DARK_THEME_MEAN_LUMINANCE) {
            this.mColorHints = 2 | this.mColorHints;
        }
    }

    public WallpaperColors(Color color, Color color2, Color color3, int i) {
        if (color == null) {
            throw new IllegalArgumentException("Primary color should never be null.");
        }
        ArrayList arrayList = new ArrayList(3);
        this.mMainColors = arrayList;
        HashMap map = new HashMap();
        this.mAllColors = map;
        arrayList.add(color);
        map.put(Integer.valueOf(color.toArgb()), 0);
        if (color2 != null) {
            arrayList.add(color2);
            map.put(Integer.valueOf(color2.toArgb()), 0);
        }
        if (color3 != null) {
            if (color2 == null) {
                throw new IllegalArgumentException("tertiaryColor can't be specified when secondaryColor is null");
            }
            arrayList.add(color3);
            map.put(Integer.valueOf(color3.toArgb()), 0);
        }
        this.mColorHints = i;
    }

    public WallpaperColors(Map<Integer, Integer> map, int i) {
        this.mAllColors = map;
        HashMap map2 = new HashMap();
        for (Integer num : map.keySet()) {
            map2.put(num, Cam.fromInt(num.intValue()));
        }
        Map<Integer, Double> mapColorToHueProportion = colorToHueProportion(map.keySet(), map2, hueProportions(map2, map));
        HashMap map3 = new HashMap();
        for (Map.Entry<Integer, Double> entry : mapColorToHueProportion.entrySet()) {
            Integer key = entry.getKey();
            key.intValue();
            map3.put(key, Double.valueOf(score((Cam) map2.get(key), entry.getValue().doubleValue())));
        }
        ArrayList arrayList = new ArrayList(map3.entrySet());
        arrayList.sort(new Comparator() { // from class: android.app.WallpaperColors$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((Double) ((Map.Entry) obj2).getValue()).compareTo((Double) ((Map.Entry) obj).getValue());
            }
        });
        ArrayList<Integer> arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add((Integer) ((Map.Entry) it.next()).getKey());
        }
        ArrayList arrayList3 = new ArrayList();
        for (Integer num2 : arrayList2) {
            num2.intValue();
            Cam cam = (Cam) map2.get(num2);
            Iterator it2 = arrayList3.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    arrayList3.add(num2);
                    break;
                }
                Integer num3 = (Integer) it2.next();
                num3.intValue();
                if (hueDiff(cam, (Cam) map2.get(num3)) < 15.0d) {
                    break;
                }
            }
        }
        ArrayList arrayList4 = new ArrayList();
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList4.add(Color.valueOf(((Integer) it3.next()).intValue()));
        }
        this.mMainColors = arrayList4;
        this.mColorHints = i;
    }

    private static double hueDiff(Cam cam, Cam cam2) {
        return 180.0f - Math.abs(Math.abs(cam.getHue() - cam2.getHue()) - 180.0f);
    }

    private static double score(Cam cam, double d) {
        return cam.getChroma() + (d * 100.0d);
    }

    private static Map<Integer, Double> colorToHueProportion(Set<Integer> set, Map<Integer, Cam> map, double[] dArr) {
        HashMap map2 = new HashMap();
        for (Integer num : set) {
            num.intValue();
            int iWrapDegrees = wrapDegrees(Math.round(map.get(num).getHue()));
            double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (int i = iWrapDegrees - 15; i < iWrapDegrees + 15; i++) {
                d += dArr[wrapDegrees(i)];
            }
            map2.put(num, Double.valueOf(d));
        }
        return map2;
    }

    private static int wrapDegrees(int i) {
        if (i < 0) {
            return (i % 360) + 360;
        }
        return i >= 360 ? i % 360 : i;
    }

    private static double[] hueProportions(Map<Integer, Cam> map, Map<Integer, Integer> map2) {
        double[] dArr = new double[360];
        Iterator<Map.Entry<Integer, Integer>> it = map2.entrySet().iterator();
        double dIntValue = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        while (it.hasNext()) {
            dIntValue += it.next().getValue().intValue();
        }
        Iterator<Map.Entry<Integer, Integer>> it2 = map2.entrySet().iterator();
        while (it2.hasNext()) {
            Integer key = it2.next().getKey();
            key.intValue();
            int iIntValue = map2.get(key).intValue();
            int iWrapDegrees = wrapDegrees(Math.round(map.get(key).getHue()));
            dArr[iWrapDegrees] = dArr[iWrapDegrees] + (iIntValue / dIntValue);
        }
        return dArr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        List<Color> mainColors = getMainColors();
        int size = mainColors.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeInt(mainColors.get(i2).toArgb());
        }
        parcel.writeInt(this.mAllColors.size());
        for (Map.Entry<Integer, Integer> entry : this.mAllColors.entrySet()) {
            if (entry.getKey() != null) {
                parcel.writeInt(entry.getKey().intValue());
                Integer value = entry.getValue();
                parcel.writeInt(value != null ? value.intValue() : 0);
            }
        }
        parcel.writeInt(this.mColorHints);
    }

    public Color getPrimaryColor() {
        return this.mMainColors.get(0);
    }

    public Color getSecondaryColor() {
        if (this.mMainColors.size() < 2) {
            return null;
        }
        return this.mMainColors.get(1);
    }

    public Color getTertiaryColor() {
        if (this.mMainColors.size() < 3) {
            return null;
        }
        return this.mMainColors.get(2);
    }

    public List<Color> getMainColors() {
        return Collections.unmodifiableList(this.mMainColors);
    }

    public Map<Integer, Integer> getAllColors() {
        return Collections.unmodifiableMap(this.mAllColors);
    }

    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            WallpaperColors wallpaperColors = (WallpaperColors) obj;
            if (this.mMainColors.equals(wallpaperColors.mMainColors) && this.mAllColors.equals(wallpaperColors.mAllColors) && this.mColorHints == wallpaperColors.mColorHints) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.mMainColors.hashCode() * 31 * this.mAllColors.hashCode()) + this.mColorHints;
    }

    public int getColorHints() {
        return this.mColorHints;
    }

    private static int calculateDarkHints(Bitmap bitmap, float f) {
        int i = 0;
        if (bitmap == null) {
            return 0;
        }
        Trace.beginSection("WallpaperColors#calculateDarkHints");
        float fSaturate = MathUtils.saturate(f);
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        int i2 = (int) (width * MAX_DARK_AREA);
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int alphaComponent = ColorUtils.setAlphaComponent(-16777216, (int) (fSaturate * 255.0f));
        float[] fArr = new float[3];
        double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        int i3 = 0;
        for (int i4 = 0; i4 < width; i4++) {
            int i5 = iArr[i4];
            ColorUtils.colorToHSL(i5, fArr);
            int iAlpha = Color.alpha(i5);
            double dCalculateLuminance = ColorUtils.calculateLuminance(ColorUtils.compositeColors(alphaComponent, i5));
            if (ContrastColorUtil.calculateContrast(i5, -16777216) <= 5.5d && iAlpha != 0) {
                i3++;
            }
            d += dCalculateLuminance;
        }
        double d2 = d / width;
        if (d2 > BRIGHT_IMAGE_MEAN_LUMINANCE && i3 <= i2) {
            i = 1;
        }
        if (d2 < 0.30000001192092896d) {
            i |= 2;
        }
        Trace.endSection();
        return i;
    }

    private static Size calculateOptimalSize(int i, int i2) {
        int i3 = i * i2;
        double dSqrt = i3 > MAX_WALLPAPER_EXTRACTION_AREA ? Math.sqrt(12544.0d / i3) : 1.0d;
        int i4 = (int) (i * dSqrt);
        int i5 = (int) (i2 * dSqrt);
        if (i4 == 0) {
            i4 = 1;
        }
        if (i5 == 0) {
            i5 = 1;
        }
        return new Size(i4, i5);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mMainColors.size(); i++) {
            sb.append(Integer.toHexString(this.mMainColors.get(i).toArgb()));
            sb.append(" ");
        }
        return "[WallpaperColors: " + sb.toString() + "h: " + this.mColorHints + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
