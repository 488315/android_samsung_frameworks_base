package android.graphics;

import android.graphics.ColorSpace;
import android.hardware.Camera;
import android.util.Half;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

/* loaded from: classes.dex */
public class Color {
    public static final int BLACK = -16777216;
    public static final int BLUE = -16776961;
    public static final int CYAN = -16711681;
    public static final int DKGRAY = -12303292;
    public static final int GRAY = -7829368;
    public static final int GREEN = -16711936;
    public static final int LTGRAY = -3355444;
    public static final int MAGENTA = -65281;
    public static final int RED = -65536;
    public static final int TRANSPARENT = 0;
    public static final int WHITE = -1;
    public static final int YELLOW = -256;
    private static final HashMap<String, Integer> sColorNameMap;
    private final ColorSpace mColorSpace;
    private final float[] mComponents;

    public static float alpha(long j) {
        float f;
        float f2;
        if ((63 & j) == 0) {
            f = (j >> 56) & 255;
            f2 = 255.0f;
        } else {
            f = (j >> 6) & 1023;
            f2 = 1023.0f;
        }
        return f / f2;
    }

    public static int alpha(int i) {
        return i >>> 24;
    }

    public static int argb(float f, float f2, float f3, float f4) {
        return (((int) ((f * 255.0f) + 0.5f)) << 24) | (((int) ((f2 * 255.0f) + 0.5f)) << 16) | (((int) ((f3 * 255.0f) + 0.5f)) << 8) | ((int) ((f4 * 255.0f) + 0.5f));
    }

    public static int argb(int i, int i2, int i3, int i4) {
        return (i << 24) | (i2 << 16) | (i3 << 8) | i4;
    }

    public static int blue(int i) {
        return i & 255;
    }

    public static int green(int i) {
        return (i >> 8) & 255;
    }

    private static native int nativeHSVToColor(int i, float[] fArr);

    private static native void nativeRGBToHSV(int i, int i2, int i3, float[] fArr);

    public static long pack(int i) {
        return (i & 4294967295L) << 32;
    }

    public static int red(int i) {
        return (i >> 16) & 255;
    }

    public static int rgb(float f, float f2, float f3) {
        return (((int) ((f * 255.0f) + 0.5f)) << 16) | (-16777216) | (((int) ((f2 * 255.0f) + 0.5f)) << 8) | ((int) ((f3 * 255.0f) + 0.5f));
    }

    public static int rgb(int i, int i2, int i3) {
        return (i << 16) | (-16777216) | (i2 << 8) | i3;
    }

    private static float saturate(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        return f;
    }

    public Color() {
        this.mComponents = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        this.mColorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
    }

    private Color(float f, float f2, float f3, float f4) {
        this(f, f2, f3, f4, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    private Color(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        this.mComponents = new float[]{f, f2, f3, f4};
        this.mColorSpace = colorSpace;
    }

    private Color(float[] fArr, ColorSpace colorSpace) {
        this.mComponents = fArr;
        this.mColorSpace = colorSpace;
    }

    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    public ColorSpace.Model getModel() {
        return this.mColorSpace.getModel();
    }

    public boolean isWideGamut() {
        return getColorSpace().isWideGamut();
    }

    public boolean isSrgb() {
        return getColorSpace().isSrgb();
    }

    public int getComponentCount() {
        return this.mColorSpace.getComponentCount() + 1;
    }

    public long pack() {
        float[] fArr = this.mComponents;
        return pack(fArr[0], fArr[1], fArr[2], fArr[3], this.mColorSpace);
    }

    public Color convert(ColorSpace colorSpace) {
        ColorSpace.Connector connectorConnect = ColorSpace.connect(this.mColorSpace, colorSpace);
        float[] fArr = this.mComponents;
        float[] fArr2 = {fArr[0], fArr[1], fArr[2], fArr[3]};
        connectorConnect.transform(fArr2);
        return new Color(fArr2, colorSpace);
    }

    public int toArgb() {
        if (this.mColorSpace.isSrgb()) {
            float[] fArr = this.mComponents;
            return ((int) ((fArr[2] * 255.0f) + 0.5f)) | (((int) ((fArr[3] * 255.0f) + 0.5f)) << 24) | (((int) ((fArr[0] * 255.0f) + 0.5f)) << 16) | (((int) ((fArr[1] * 255.0f) + 0.5f)) << 8);
        }
        float[] fArr2 = this.mComponents;
        float[] fArr3 = {fArr2[0], fArr2[1], fArr2[2], fArr2[3]};
        ColorSpace.connect(this.mColorSpace).transform(fArr3);
        return (((int) ((fArr3[3] * 255.0f) + 0.5f)) << 24) | (((int) ((fArr3[0] * 255.0f) + 0.5f)) << 16) | (((int) ((fArr3[1] * 255.0f) + 0.5f)) << 8) | ((int) ((fArr3[2] * 255.0f) + 0.5f));
    }

    public float red() {
        return this.mComponents[0];
    }

    public float green() {
        return this.mComponents[1];
    }

    public float blue() {
        return this.mComponents[2];
    }

    public float alpha() {
        return this.mComponents[r1.length - 1];
    }

    public float[] getComponents() {
        float[] fArr = this.mComponents;
        return Arrays.copyOf(fArr, fArr.length);
    }

    public float[] getComponents(float[] fArr) {
        if (fArr == null) {
            float[] fArr2 = this.mComponents;
            return Arrays.copyOf(fArr2, fArr2.length);
        }
        int length = fArr.length;
        float[] fArr3 = this.mComponents;
        if (length < fArr3.length) {
            throw new IllegalArgumentException("The specified array's length must be at least " + this.mComponents.length);
        }
        System.arraycopy(fArr3, 0, fArr, 0, fArr3.length);
        return fArr;
    }

    public float getComponent(int i) {
        return this.mComponents[i];
    }

    public float luminance() {
        if (this.mColorSpace.getModel() != ColorSpace.Model.RGB) {
            throw new IllegalArgumentException("The specified color must be encoded in an RGB color space. The supplied color space is " + this.mColorSpace.getModel());
        }
        DoubleUnaryOperator eotf = ((ColorSpace.Rgb) this.mColorSpace).getEotf();
        return saturate((float) ((eotf.applyAsDouble(this.mComponents[0]) * 0.2126d) + (eotf.applyAsDouble(this.mComponents[1]) * 0.7152d) + (eotf.applyAsDouble(this.mComponents[2]) * 0.0722d)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Color color = (Color) obj;
        if (Arrays.equals(this.mComponents, color.mComponents)) {
            return this.mColorSpace.equals(color.mColorSpace);
        }
        return false;
    }

    public int hashCode() {
        return (Arrays.hashCode(this.mComponents) * 31) + this.mColorSpace.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Color(");
        for (float f : this.mComponents) {
            sb.append(f);
            sb.append(", ");
        }
        sb.append(this.mColorSpace.getName());
        sb.append(')');
        return sb.toString();
    }

    public static ColorSpace colorSpace(long j) {
        return ColorSpace.get((int) (j & 63));
    }

    public static float red(long j) {
        return (63 & j) == 0 ? ((j >> 48) & 255) / 255.0f : Half.toFloat((short) ((j >> 48) & 65535));
    }

    public static float green(long j) {
        return (63 & j) == 0 ? ((j >> 40) & 255) / 255.0f : Half.toFloat((short) ((j >> 32) & 65535));
    }

    public static float blue(long j) {
        return (63 & j) == 0 ? ((j >> 32) & 255) / 255.0f : Half.toFloat((short) ((j >> 16) & 65535));
    }

    public static boolean isSrgb(long j) {
        return colorSpace(j).isSrgb();
    }

    public static boolean isWideGamut(long j) {
        return colorSpace(j).isWideGamut();
    }

    public static boolean isInColorSpace(long j, ColorSpace colorSpace) {
        return ((int) (j & 63)) == colorSpace.getId();
    }

    public static int toArgb(long j) {
        if ((63 & j) == 0) {
            return (int) (j >> 32);
        }
        float fRed = red(j);
        float fGreen = green(j);
        float fBlue = blue(j);
        float fAlpha = alpha(j);
        float[] fArrTransform = ColorSpace.connect(colorSpace(j)).transform(fRed, fGreen, fBlue);
        return ((int) ((fArrTransform[2] * 255.0f) + 0.5f)) | (((int) ((fAlpha * 255.0f) + 0.5f)) << 24) | (((int) ((fArrTransform[0] * 255.0f) + 0.5f)) << 16) | (((int) ((fArrTransform[1] * 255.0f) + 0.5f)) << 8);
    }

    public static Color valueOf(int i) {
        return new Color(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static Color valueOf(long j) {
        return new Color(red(j), green(j), blue(j), alpha(j), colorSpace(j));
    }

    public static Color valueOf(float f, float f2, float f3) {
        return new Color(f, f2, f3, 1.0f);
    }

    public static Color valueOf(float f, float f2, float f3, float f4) {
        return new Color(saturate(f), saturate(f2), saturate(f3), saturate(f4));
    }

    public static Color valueOf(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        if (colorSpace.getComponentCount() > 3) {
            throw new IllegalArgumentException("The specified color space must use a color model with at most 3 color components");
        }
        return new Color(f, f2, f3, f4, colorSpace);
    }

    public static Color valueOf(float[] fArr, ColorSpace colorSpace) {
        if (fArr.length < colorSpace.getComponentCount() + 1) {
            throw new IllegalArgumentException("Received a component array of length " + fArr.length + " but the color model requires " + (colorSpace.getComponentCount() + 1) + " (including alpha)");
        }
        return new Color(Arrays.copyOf(fArr, colorSpace.getComponentCount() + 1), colorSpace);
    }

    public static long pack(float f, float f2, float f3) {
        return pack(f, f2, f3, 1.0f, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static long pack(float f, float f2, float f3, float f4) {
        return pack(f, f2, f3, f4, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static long pack(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        if (colorSpace.isSrgb()) {
            return (((((((int) ((f * 255.0f) + 0.5f)) << 16) | (((int) ((f4 * 255.0f) + 0.5f)) << 24)) | (((int) ((f2 * 255.0f) + 0.5f)) << 8)) | ((int) ((f3 * 255.0f) + 0.5f))) & 4294967295L) << 32;
        }
        int id = colorSpace.getId();
        if (id == -1) {
            throw new IllegalArgumentException("Unknown color space, please use a color space returned by ColorSpace.get()");
        }
        if (colorSpace.getComponentCount() > 3) {
            throw new IllegalArgumentException("The color space must use a color model with at most 3 components");
        }
        return ((Half.toHalf(f2) & 65535) << 32) | ((Half.toHalf(f) & 65535) << 48) | ((Half.toHalf(f3) & 65535) << 16) | ((((int) ((Math.max(0.0f, Math.min(f4, 1.0f)) * 1023.0f) + 0.5f)) & 1023) << 6) | (id & 63);
    }

    public static long convert(int i, ColorSpace colorSpace) {
        return convert(((i >> 16) & 255) / 255.0f, ((i >> 8) & 255) / 255.0f, (i & 255) / 255.0f, ((i >> 24) & 255) / 255.0f, ColorSpace.get(ColorSpace.Named.SRGB), colorSpace);
    }

    public static long convert(long j, ColorSpace colorSpace) {
        return convert(red(j), green(j), blue(j), alpha(j), colorSpace(j), colorSpace);
    }

    public static long convert(float f, float f2, float f3, float f4, ColorSpace colorSpace, ColorSpace colorSpace2) {
        float[] fArrTransform = ColorSpace.connect(colorSpace, colorSpace2).transform(f, f2, f3);
        return pack(fArrTransform[0], fArrTransform[1], fArrTransform[2], f4, colorSpace2);
    }

    public static long convert(long j, ColorSpace.Connector connector) {
        return convert(red(j), green(j), blue(j), alpha(j), connector);
    }

    public static long convert(float f, float f2, float f3, float f4, ColorSpace.Connector connector) {
        float[] fArrTransform = connector.transform(f, f2, f3);
        return pack(fArrTransform[0], fArrTransform[1], fArrTransform[2], f4, connector.getDestination());
    }

    public static float luminance(long j) {
        ColorSpace colorSpace = colorSpace(j);
        if (colorSpace.getModel() != ColorSpace.Model.RGB) {
            throw new IllegalArgumentException("The specified color must be encoded in an RGB color space. The supplied color space is " + colorSpace.getModel());
        }
        DoubleUnaryOperator eotf = ((ColorSpace.Rgb) colorSpace).getEotf();
        return saturate((float) ((eotf.applyAsDouble(red(j)) * 0.2126d) + (eotf.applyAsDouble(green(j)) * 0.7152d) + (eotf.applyAsDouble(blue(j)) * 0.0722d)));
    }

    public static float luminance(int i) {
        DoubleUnaryOperator eotf = ((ColorSpace.Rgb) ColorSpace.get(ColorSpace.Named.SRGB)).getEotf();
        return (float) ((eotf.applyAsDouble(red(i) / 255.0d) * 0.2126d) + (eotf.applyAsDouble(green(i) / 255.0d) * 0.7152d) + (eotf.applyAsDouble(blue(i) / 255.0d) * 0.0722d));
    }

    public static int parseColor(String str) throws NumberFormatException {
        if (str.charAt(0) == '#') {
            long j = Long.parseLong(str.substring(1), 16);
            if (str.length() == 7) {
                j |= -16777216;
            } else if (str.length() != 9) {
                throw new IllegalArgumentException("Unknown color");
            }
            return (int) j;
        }
        Integer num = sColorNameMap.get(str.toLowerCase(Locale.ROOT));
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalArgumentException("Unknown color");
    }

    public static void RGBToHSV(int i, int i2, int i3, float[] fArr) {
        if (fArr.length < 3) {
            throw new RuntimeException("3 components required for hsv");
        }
        nativeRGBToHSV(i, i2, i3, fArr);
    }

    public static void colorToHSV(int i, float[] fArr) {
        RGBToHSV((i >> 16) & 255, (i >> 8) & 255, i & 255, fArr);
    }

    public static int HSVToColor(float[] fArr) {
        return HSVToColor(255, fArr);
    }

    public static int HSVToColor(int i, float[] fArr) {
        if (fArr.length < 3) {
            throw new RuntimeException("3 components required for hsv");
        }
        return nativeHSVToColor(i, fArr);
    }

    static {
        HashMap<String, Integer> map = new HashMap<>();
        sColorNameMap = map;
        map.put("black", -16777216);
        map.put("darkgray", -12303292);
        Integer numValueOf = Integer.valueOf(GRAY);
        map.put("gray", numValueOf);
        Integer numValueOf2 = Integer.valueOf(LTGRAY);
        map.put("lightgray", numValueOf2);
        map.put("white", -1);
        map.put("red", -65536);
        Integer numValueOf3 = Integer.valueOf(GREEN);
        map.put("green", numValueOf3);
        map.put("blue", -16776961);
        map.put("yellow", -256);
        Integer numValueOf4 = Integer.valueOf(CYAN);
        map.put("cyan", numValueOf4);
        Integer numValueOf5 = Integer.valueOf(MAGENTA);
        map.put("magenta", numValueOf5);
        map.put(Camera.Parameters.EFFECT_AQUA, numValueOf4);
        map.put("fuchsia", numValueOf5);
        map.put("darkgrey", -12303292);
        map.put("grey", numValueOf);
        map.put("lightgrey", numValueOf2);
        map.put("lime", numValueOf3);
        map.put("maroon", -8388608);
        map.put("navy", -16777088);
        map.put("olive", -8355840);
        map.put("purple", -8388480);
        map.put("silver", -4144960);
        map.put("teal", -16744320);
    }
}
