package com.android.internal.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.hardware.scontext.SContextConstants;
import android.text.NoCopySpan;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.Pair;
import com.android.internal.R;
import com.samsung.android.graphics.spr.SemPathRenderingDrawable;
import java.util.Arrays;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class ContrastColorUtil {
    private static final boolean DEBUG = false;
    private static final String TAG = "ContrastColorUtil";
    private static ContrastColorUtil sInstance;
    private static final Object sLock = new Object();
    private final int mGrayscaleIconMaxSize;
    private final ImageUtils mImageUtils = new ImageUtils();
    private final WeakHashMap<Bitmap, Pair<Boolean, Integer>> mGrayscaleBitmapCache = new WeakHashMap<>();

    public static boolean shouldInvertTextColor(float f, boolean z) {
        return !z && f < 0.25f;
    }

    public static ContrastColorUtil getInstance(Context context) {
        ContrastColorUtil contrastColorUtil;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new ContrastColorUtil(context);
            }
            contrastColorUtil = sInstance;
        }
        return contrastColorUtil;
    }

    private ContrastColorUtil(Context context) {
        this.mGrayscaleIconMaxSize = context.getResources().getDimensionPixelSize(R.dimen.notification_gray_scale_size_limit);
    }

    public boolean isGrayscaleIcon(Bitmap bitmap) {
        boolean isGrayscale;
        int generationId;
        if (bitmap.getWidth() > this.mGrayscaleIconMaxSize || bitmap.getHeight() > this.mGrayscaleIconMaxSize) {
            Log.d(TAG, "GrayScale=false. Bitmap(Width=" + bitmap.getWidth() + "px, Height=" + bitmap.getHeight() + "px) is larger than " + this.mGrayscaleIconMaxSize + "px.");
            return false;
        }
        Object obj = sLock;
        synchronized (obj) {
            Pair<Boolean, Integer> pair = this.mGrayscaleBitmapCache.get(bitmap);
            if (pair != null && pair.second.intValue() == bitmap.getGenerationId()) {
                return pair.first.booleanValue();
            }
            synchronized (this.mImageUtils) {
                isGrayscale = this.mImageUtils.isGrayscale(bitmap);
                generationId = bitmap.getGenerationId();
            }
            synchronized (obj) {
                this.mGrayscaleBitmapCache.put(bitmap, Pair.create(Boolean.valueOf(isGrayscale), Integer.valueOf(generationId)));
            }
            if (!isGrayscale) {
                Log.d(TAG, "GrayScale=false. Bitmap is not grayscale.");
            }
            return isGrayscale;
        }
    }

    public boolean isGrayscaleIcon(Drawable drawable) {
        if (drawable == null) {
            return false;
        }
        if (drawable instanceof SemPathRenderingDrawable) {
            SemPathRenderingDrawable semPathRenderingDrawable = (SemPathRenderingDrawable) drawable;
            return semPathRenderingDrawable.getBitmap() != null && isGrayscaleIcon(semPathRenderingDrawable.getBitmap());
        }
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            return layerDrawable.getNumberOfLayers() > 0 && isGrayscaleIcon(layerDrawable.getDrawable(0));
        }
        if (drawable instanceof InsetDrawable) {
            return true;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap() != null && isGrayscaleIcon(bitmapDrawable.getBitmap());
        }
        if (!(drawable instanceof AnimationDrawable)) {
            return drawable instanceof VectorDrawable;
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
        return animationDrawable.getNumberOfFrames() > 0 && isGrayscaleIcon(animationDrawable.getFrame(0));
    }

    public boolean isGrayscaleIcon(Context context, Icon icon) {
        if (icon == null) {
            return false;
        }
        int type = icon.getType();
        if (type == 1) {
            return isGrayscaleIcon(icon.getBitmap());
        }
        if (type != 2) {
            return false;
        }
        return isGrayscaleIcon(context, icon.getResId());
    }

    public boolean isGrayscaleIcon(Context context, int i) {
        if (i != 0) {
            try {
                return isGrayscaleIcon(context.getDrawable(i));
            } catch (Resources.NotFoundException unused) {
                Log.e(TAG, "Drawable not found: " + i);
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003c, code lost:
    
        if (r6 != r5) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.CharSequence invertCharSequenceColors(java.lang.CharSequence r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof android.text.Spanned
            if (r0 == 0) goto L67
            android.text.Spanned r9 = (android.text.Spanned) r9
            int r0 = r9.length()
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            r2 = 0
            java.lang.Object[] r0 = r9.getSpans(r2, r0, r1)
            android.text.SpannableStringBuilder r1 = new android.text.SpannableStringBuilder
            java.lang.String r3 = r9.toString()
            r1.<init>(r3)
            int r3 = r0.length
        L1b:
            if (r2 >= r3) goto L66
            r4 = r0[r2]
            boolean r5 = r4 instanceof android.text.NoCopySpan
            if (r5 == 0) goto L24
            goto L63
        L24:
            boolean r5 = r4 instanceof android.text.style.CharacterStyle
            if (r5 == 0) goto L30
            r5 = r4
            android.text.style.CharacterStyle r5 = (android.text.style.CharacterStyle) r5
            android.text.style.CharacterStyle r5 = r5.getUnderlying()
            goto L31
        L30:
            r5 = r4
        L31:
            boolean r6 = r5 instanceof android.text.style.TextAppearanceSpan
            if (r6 == 0) goto L3f
            r6 = r4
            android.text.style.TextAppearanceSpan r6 = (android.text.style.TextAppearanceSpan) r6
            android.text.style.TextAppearanceSpan r6 = r8.processTextAppearanceSpan(r6)
            if (r6 == r5) goto L53
            goto L54
        L3f:
            boolean r6 = r5 instanceof android.text.style.ForegroundColorSpan
            if (r6 == 0) goto L53
            android.text.style.ForegroundColorSpan r5 = (android.text.style.ForegroundColorSpan) r5
            int r5 = r5.getForegroundColor()
            android.text.style.ForegroundColorSpan r6 = new android.text.style.ForegroundColorSpan
            int r5 = r8.processColor(r5)
            r6.<init>(r5)
            goto L54
        L53:
            r6 = r4
        L54:
            int r5 = r9.getSpanStart(r4)
            int r7 = r9.getSpanEnd(r4)
            int r4 = r9.getSpanFlags(r4)
            r1.setSpan(r6, r5, r7, r4)
        L63:
            int r2 = r2 + 1
            goto L1b
        L66:
            return r1
        L67:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.ContrastColorUtil.invertCharSequenceColors(java.lang.CharSequence):java.lang.CharSequence");
    }

    private TextAppearanceSpan processTextAppearanceSpan(TextAppearanceSpan textAppearanceSpan) {
        ColorStateList textColor = textAppearanceSpan.getTextColor();
        if (textColor != null) {
            int[] colors = textColor.getColors();
            boolean z = false;
            for (int i = 0; i < colors.length; i++) {
                if (ImageUtils.isGrayscale(colors[i])) {
                    if (!z) {
                        colors = Arrays.copyOf(colors, colors.length);
                    }
                    colors[i] = processColor(colors[i]);
                    z = true;
                }
            }
            if (z) {
                return new TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), new ColorStateList(textColor.getStates(), colors), textAppearanceSpan.getLinkTextColor());
            }
        }
        return textAppearanceSpan;
    }

    public static CharSequence clearColorSpans(CharSequence charSequence) {
        if (!(charSequence instanceof Spanned)) {
            return charSequence;
        }
        Spanned spanned = (Spanned) charSequence;
        Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
        for (Object obj : spans) {
            if (!(obj instanceof NoCopySpan)) {
                Object underlying = obj instanceof CharacterStyle ? ((CharacterStyle) obj).getUnderlying() : obj;
                if (underlying instanceof TextAppearanceSpan) {
                    TextAppearanceSpan textAppearanceSpan = (TextAppearanceSpan) underlying;
                    if (textAppearanceSpan.getTextColor() != null) {
                        underlying = new TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), null, textAppearanceSpan.getLinkTextColor());
                    }
                } else if (!(underlying instanceof ForegroundColorSpan) && !(underlying instanceof BackgroundColorSpan)) {
                    underlying = obj;
                }
                spannableStringBuilder.setSpan(underlying, spanned.getSpanStart(obj), spanned.getSpanEnd(obj), spanned.getSpanFlags(obj));
            }
        }
        return spannableStringBuilder;
    }

    public static CharSequence ensureColorSpanContrast(CharSequence charSequence, int i) {
        Object[] objArr;
        ColorStateList colorStateList;
        CharSequence charSequence2 = charSequence;
        if (charSequence2 == null || !(charSequence2 instanceof Spanned)) {
            return charSequence;
        }
        Spanned spanned = (Spanned) charSequence2;
        int i2 = 0;
        Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
        int length = spans.length;
        int i3 = 0;
        while (i3 < length) {
            Object obj = spans[i3];
            if (obj instanceof NoCopySpan) {
                objArr = spans;
            } else {
                int spanStart = spanned.getSpanStart(obj);
                int spanEnd = spanned.getSpanEnd(obj);
                int i4 = spanEnd - spanStart == charSequence2.length() ? 1 : i2;
                Object underlying = obj instanceof CharacterStyle ? ((CharacterStyle) obj).getUnderlying() : obj;
                Object obj2 = null;
                if (underlying instanceof TextAppearanceSpan) {
                    TextAppearanceSpan textAppearanceSpan = (TextAppearanceSpan) underlying;
                    ColorStateList textColor = textAppearanceSpan.getTextColor();
                    if (textColor != null) {
                        if (i4 != 0) {
                            objArr = spans;
                            colorStateList = null;
                        } else {
                            int[] colors = textColor.getColors();
                            int length2 = colors.length;
                            int[] iArr = new int[length2];
                            while (i2 < length2) {
                                iArr[i2] = ensureLargeTextContrast(colors[i2], i, isColorDark(i));
                                i2++;
                                spans = spans;
                            }
                            objArr = spans;
                            colorStateList = new ColorStateList((int[][]) textColor.getStates().clone(), iArr);
                        }
                        underlying = new TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), colorStateList, textAppearanceSpan.getLinkTextColor());
                    } else {
                        objArr = spans;
                    }
                    obj2 = underlying;
                } else {
                    objArr = spans;
                    if (!(underlying instanceof ForegroundColorSpan)) {
                        obj2 = obj;
                    } else if (i4 == 0) {
                        obj2 = new ForegroundColorSpan(ensureLargeTextContrast(((ForegroundColorSpan) underlying).getForegroundColor(), i, isColorDark(i)));
                    }
                }
                if (obj2 != null) {
                    spannableStringBuilder.setSpan(obj2, spanStart, spanEnd, spanned.getSpanFlags(obj));
                }
            }
            i3++;
            charSequence2 = charSequence;
            spans = objArr;
            i2 = 0;
        }
        return spannableStringBuilder;
    }

    public static boolean isColorDark(int i) {
        return calculateLuminance(i) <= 0.17912878474d;
    }

    public static boolean isColorDarkLab(int i) {
        double[] tempDouble3Array = ColorUtilsFromCompat.getTempDouble3Array();
        ColorUtilsFromCompat.colorToLAB(i, tempDouble3Array);
        return tempDouble3Array[0] < 50.0d;
    }

    private int processColor(int i) {
        return Color.argb(Color.alpha(i), 255 - Color.red(i), 255 - Color.green(i), 255 - Color.blue(i));
    }

    public static int findContrastColor(int i, int i2, boolean z, double d) {
        int i3 = z ? i : i2;
        int i4 = z ? i2 : i;
        if (ColorUtilsFromCompat.calculateContrast(i3, i4) >= d) {
            return i;
        }
        double[] dArr = new double[3];
        ColorUtilsFromCompat.colorToLAB(z ? i3 : i4, dArr);
        double d2 = dArr[0];
        double d3 = dArr[1];
        double d4 = dArr[2];
        double d5 = 0.0d;
        for (int i5 = 0; i5 < 15 && d2 - d5 > 1.0E-5d; i5++) {
            double d6 = (d5 + d2) / 2.0d;
            if (z) {
                i3 = ColorUtilsFromCompat.LABToColor(d6, d3, d4);
            } else {
                i4 = ColorUtilsFromCompat.LABToColor(d6, d3, d4);
            }
            if (ColorUtilsFromCompat.calculateContrast(i3, i4) > d) {
                d5 = d6;
            } else {
                d2 = d6;
            }
        }
        return ColorUtilsFromCompat.LABToColor(d5, d3, d4);
    }

    public static int findAlphaToMeetContrast(int i, int i2, double d) {
        if (ColorUtilsFromCompat.calculateContrast(i, i2) >= d) {
            return i;
        }
        int alpha = Color.alpha(i);
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        int i3 = 255;
        for (int i4 = 0; i4 < 15 && i3 - alpha > 0; i4++) {
            int i5 = (alpha + i3) / 2;
            if (ColorUtilsFromCompat.calculateContrast(Color.argb(i5, red, green, blue), i2) > d) {
                i3 = i5;
            } else {
                alpha = i5;
            }
        }
        return Color.argb(i3, red, green, blue);
    }

    public static int findContrastColorAgainstDark(int i, int i2, boolean z, double d) {
        int i3 = z ? i : i2;
        if (!z) {
            i2 = i;
        }
        if (ColorUtilsFromCompat.calculateContrast(i3, i2) >= d) {
            return i;
        }
        float[] fArr = new float[3];
        ColorUtilsFromCompat.colorToHSL(z ? i3 : i2, fArr);
        float f = fArr[2];
        float f2 = 1.0f;
        for (int i4 = 0; i4 < 15 && f2 - f > 1.0E-5d; i4++) {
            float f3 = (f + f2) / 2.0f;
            fArr[2] = f3;
            if (z) {
                i3 = ColorUtilsFromCompat.HSLToColor(fArr);
            } else {
                i2 = ColorUtilsFromCompat.HSLToColor(fArr);
            }
            if (ColorUtilsFromCompat.calculateContrast(i3, i2) > d) {
                f2 = f3;
            } else {
                f = f3;
            }
        }
        fArr[2] = f2;
        return ColorUtilsFromCompat.HSLToColor(fArr);
    }

    public static int ensureTextContrastOnBlack(int i) {
        return findContrastColorAgainstDark(i, -16777216, true, 12.0d);
    }

    public static int ensureLargeTextContrast(int i, int i2, boolean z) {
        if (z) {
            return findContrastColorAgainstDark(i, i2, true, 3.0d);
        }
        return findContrastColor(i, i2, true, 3.0d);
    }

    public static int ensureTextContrast(int i, int i2, boolean z) {
        return ensureContrast(i, i2, z, 4.5d);
    }

    public static int ensureContrast(int i, int i2, boolean z, double d) {
        if (z) {
            return findContrastColorAgainstDark(i, i2, true, d);
        }
        return findContrastColor(i, i2, true, d);
    }

    public static int ensureTextBackgroundColor(int i, int i2, int i3) {
        return findContrastColor(findContrastColor(i, i3, false, 3.0d), i2, false, 4.5d);
    }

    private static String contrastChange(int i, int i2, int i3) {
        return String.format("from %.2f:1 to %.2f:1", Double.valueOf(ColorUtilsFromCompat.calculateContrast(i, i3)), Double.valueOf(ColorUtilsFromCompat.calculateContrast(i2, i3)));
    }

    public static int resolveColor(Context context, int i, boolean z) {
        if (i == 0) {
            return context.getColor(z ? R.color.notification_default_color_dark : R.color.notification_default_color_light);
        }
        return i;
    }

    public static int resolveContrastColor(Context context, int i, int i2) {
        return resolveContrastColor(context, i, i2, false);
    }

    public static int resolveContrastColor(Context context, int i, int i2, boolean z) {
        return ensureTextContrast(resolveColor(context, i, z), i2, z);
    }

    public static int changeColorLightness(int i, int i2) {
        double[] tempDouble3Array = ColorUtilsFromCompat.getTempDouble3Array();
        ColorUtilsFromCompat.colorToLAB(i, tempDouble3Array);
        double max = Math.max(Math.min(100.0d, tempDouble3Array[0] + i2), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
        tempDouble3Array[0] = max;
        return ColorUtilsFromCompat.LABToColor(max, tempDouble3Array[1], tempDouble3Array[2]);
    }

    public static int resolvePrimaryColor(Context context, int i, boolean z) {
        if (shouldUseDark(i, z)) {
            return context.getColor(R.color.notification_primary_text_color_light);
        }
        return context.getColor(R.color.notification_primary_text_color_dark);
    }

    public static int resolveSecondaryColor(Context context, int i, boolean z) {
        if (shouldUseDark(i, z)) {
            return context.getColor(R.color.notification_secondary_text_color_light);
        }
        return context.getColor(R.color.notification_secondary_text_color_dark);
    }

    public static int resolveThirdColor(Context context, int i, boolean z) {
        if (shouldUseDark(i, z)) {
            return context.getColor(R.color.notification_third_text_color_light);
        }
        return context.getColor(R.color.notification_third_text_color_dark);
    }

    public static int resolveDefaultColor(Context context, int i, boolean z) {
        if (shouldUseDark(i, z)) {
            return context.getColor(R.color.notification_default_color_light);
        }
        return context.getColor(R.color.notification_default_color_dark);
    }

    public static int getShiftedColor(int i, int i2) {
        double[] tempDouble3Array = ColorUtilsFromCompat.getTempDouble3Array();
        ColorUtilsFromCompat.colorToLAB(i, tempDouble3Array);
        double d = tempDouble3Array[0];
        if (d >= 4.0d) {
            tempDouble3Array[0] = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, d - i2);
        } else {
            tempDouble3Array[0] = Math.min(100.0d, d + i2);
        }
        return ColorUtilsFromCompat.LABToColor(tempDouble3Array[0], tempDouble3Array[1], tempDouble3Array[2]);
    }

    public static int getMutedColor(int i, float f) {
        return compositeColors(ColorUtilsFromCompat.setAlphaComponent(-1, (int) (f * 255.0f)), i);
    }

    private static boolean shouldUseDark(int i, boolean z) {
        return i == 0 ? !z : ColorUtilsFromCompat.calculateLuminance(i) > 0.5d;
    }

    public static double calculateLuminance(int i) {
        return ColorUtilsFromCompat.calculateLuminance(i);
    }

    public static double calculateContrast(int i, int i2) {
        return ColorUtilsFromCompat.calculateContrast(i, i2);
    }

    public static boolean satisfiesTextContrast(int i, int i2) {
        return calculateContrast(i2, i) >= 4.5d;
    }

    public static int compositeColors(int i, int i2) {
        return ColorUtilsFromCompat.compositeColors(i, i2);
    }

    public static boolean isColorLight(int i) {
        return calculateLuminance(i) > 0.5d;
    }

    private static class ColorUtilsFromCompat {
        private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
        private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
        private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal<>();
        private static final double XYZ_EPSILON = 0.008856d;
        private static final double XYZ_KAPPA = 903.3d;
        private static final double XYZ_WHITE_REFERENCE_X = 95.047d;
        private static final double XYZ_WHITE_REFERENCE_Y = 100.0d;
        private static final double XYZ_WHITE_REFERENCE_Z = 108.883d;

        private static float constrain(float f, float f2, float f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }

        private static int constrain(int i, int i2, int i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }

        private ColorUtilsFromCompat() {
        }

        public static int compositeColors(int i, int i2) {
            int alpha = Color.alpha(i2);
            int alpha2 = Color.alpha(i);
            int compositeAlpha = compositeAlpha(alpha2, alpha);
            return Color.argb(compositeAlpha, compositeComponent(Color.red(i), alpha2, Color.red(i2), alpha, compositeAlpha), compositeComponent(Color.green(i), alpha2, Color.green(i2), alpha, compositeAlpha), compositeComponent(Color.blue(i), alpha2, Color.blue(i2), alpha, compositeAlpha));
        }

        private static int compositeAlpha(int i, int i2) {
            return 255 - (((255 - i2) * (255 - i)) / 255);
        }

        private static int compositeComponent(int i, int i2, int i3, int i4, int i5) {
            if (i5 == 0) {
                return 0;
            }
            return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
        }

        public static int setAlphaComponent(int i, int i2) {
            if (i2 < 0 || i2 > 255) {
                throw new IllegalArgumentException("alpha must be between 0 and 255.");
            }
            return (i & 16777215) | (i2 << 24);
        }

        public static double calculateLuminance(int i) {
            double[] tempDouble3Array = getTempDouble3Array();
            colorToXYZ(i, tempDouble3Array);
            return tempDouble3Array[1] / XYZ_WHITE_REFERENCE_Y;
        }

        public static double calculateContrast(int i, int i2) {
            if (Color.alpha(i) < 255) {
                i = compositeColors(i, i2);
            }
            double calculateLuminance = calculateLuminance(i) + 0.05d;
            double calculateLuminance2 = calculateLuminance(i2) + 0.05d;
            return Math.max(calculateLuminance, calculateLuminance2) / Math.min(calculateLuminance, calculateLuminance2);
        }

        public static void colorToLAB(int i, double[] dArr) {
            RGBToLAB(Color.red(i), Color.green(i), Color.blue(i), dArr);
        }

        public static void RGBToLAB(int i, int i2, int i3, double[] dArr) {
            RGBToXYZ(i, i2, i3, dArr);
            XYZToLAB(dArr[0], dArr[1], dArr[2], dArr);
        }

        public static void colorToXYZ(int i, double[] dArr) {
            RGBToXYZ(Color.red(i), Color.green(i), Color.blue(i), dArr);
        }

        public static void RGBToXYZ(int i, int i2, int i3, double[] dArr) {
            if (dArr.length != 3) {
                throw new IllegalArgumentException("outXyz must have a length of 3.");
            }
            double d = i / 255.0d;
            double pow = d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d);
            double d2 = i2 / 255.0d;
            double pow2 = d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 + 0.055d) / 1.055d, 2.4d);
            double d3 = i3 / 255.0d;
            double pow3 = d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 + 0.055d) / 1.055d, 2.4d);
            dArr[0] = ((0.4124d * pow) + (0.3576d * pow2) + (0.1805d * pow3)) * XYZ_WHITE_REFERENCE_Y;
            dArr[1] = ((0.2126d * pow) + (0.7152d * pow2) + (0.0722d * pow3)) * XYZ_WHITE_REFERENCE_Y;
            dArr[2] = ((pow * 0.0193d) + (pow2 * 0.1192d) + (pow3 * 0.9505d)) * XYZ_WHITE_REFERENCE_Y;
        }

        public static void XYZToLAB(double d, double d2, double d3, double[] dArr) {
            if (dArr.length != 3) {
                throw new IllegalArgumentException("outLab must have a length of 3.");
            }
            double pivotXyzComponent = pivotXyzComponent(d / XYZ_WHITE_REFERENCE_X);
            double pivotXyzComponent2 = pivotXyzComponent(d2 / XYZ_WHITE_REFERENCE_Y);
            double pivotXyzComponent3 = pivotXyzComponent(d3 / XYZ_WHITE_REFERENCE_Z);
            dArr[0] = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, (116.0d * pivotXyzComponent2) - 16.0d);
            dArr[1] = (pivotXyzComponent - pivotXyzComponent2) * 500.0d;
            dArr[2] = (pivotXyzComponent2 - pivotXyzComponent3) * 200.0d;
        }

        public static void LABToXYZ(double d, double d2, double d3, double[] dArr) {
            double d4 = (d + 16.0d) / 116.0d;
            double d5 = (d2 / 500.0d) + d4;
            double d6 = d4 - (d3 / 200.0d);
            double pow = Math.pow(d5, 3.0d);
            if (pow <= XYZ_EPSILON) {
                pow = ((d5 * 116.0d) - 16.0d) / XYZ_KAPPA;
            }
            double pow2 = d > 7.9996247999999985d ? Math.pow(d4, 3.0d) : d / XYZ_KAPPA;
            double pow3 = Math.pow(d6, 3.0d);
            if (pow3 <= XYZ_EPSILON) {
                pow3 = ((d6 * 116.0d) - 16.0d) / XYZ_KAPPA;
            }
            dArr[0] = pow * XYZ_WHITE_REFERENCE_X;
            dArr[1] = pow2 * XYZ_WHITE_REFERENCE_Y;
            dArr[2] = pow3 * XYZ_WHITE_REFERENCE_Z;
        }

        public static int XYZToColor(double d, double d2, double d3) {
            double d4 = (((3.2406d * d) + ((-1.5372d) * d2)) + ((-0.4986d) * d3)) / XYZ_WHITE_REFERENCE_Y;
            double d5 = ((((-0.9689d) * d) + (1.8758d * d2)) + (0.0415d * d3)) / XYZ_WHITE_REFERENCE_Y;
            double d6 = (((0.0557d * d) + ((-0.204d) * d2)) + (1.057d * d3)) / XYZ_WHITE_REFERENCE_Y;
            return Color.rgb(constrain((int) Math.round((d4 > 0.0031308d ? (Math.pow(d4, 0.4166666666666667d) * 1.055d) - 0.055d : d4 * 12.92d) * 255.0d), 0, 255), constrain((int) Math.round((d5 > 0.0031308d ? (Math.pow(d5, 0.4166666666666667d) * 1.055d) - 0.055d : d5 * 12.92d) * 255.0d), 0, 255), constrain((int) Math.round((d6 > 0.0031308d ? (Math.pow(d6, 0.4166666666666667d) * 1.055d) - 0.055d : d6 * 12.92d) * 255.0d), 0, 255));
        }

        public static int LABToColor(double d, double d2, double d3) {
            double[] tempDouble3Array = getTempDouble3Array();
            LABToXYZ(d, d2, d3, tempDouble3Array);
            return XYZToColor(tempDouble3Array[0], tempDouble3Array[1], tempDouble3Array[2]);
        }

        private static double pivotXyzComponent(double d) {
            return d > XYZ_EPSILON ? Math.pow(d, 0.3333333333333333d) : ((d * XYZ_KAPPA) + 16.0d) / 116.0d;
        }

        public static double[] getTempDouble3Array() {
            ThreadLocal<double[]> threadLocal = TEMP_ARRAY;
            double[] dArr = threadLocal.get();
            if (dArr != null) {
                return dArr;
            }
            double[] dArr2 = new double[3];
            threadLocal.set(dArr2);
            return dArr2;
        }

        public static int HSLToColor(float[] fArr) {
            int round;
            int round2;
            int round3;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float abs = (1.0f - Math.abs((f3 * 2.0f) - 1.0f)) * f2;
            float f4 = f3 - (0.5f * abs);
            float abs2 = (1.0f - Math.abs(((f / 60.0f) % 2.0f) - 1.0f)) * abs;
            switch (((int) f) / 60) {
                case 0:
                    round = Math.round((abs + f4) * 255.0f);
                    round2 = Math.round((abs2 + f4) * 255.0f);
                    round3 = Math.round(f4 * 255.0f);
                    break;
                case 1:
                    round = Math.round((abs2 + f4) * 255.0f);
                    round2 = Math.round((abs + f4) * 255.0f);
                    round3 = Math.round(f4 * 255.0f);
                    break;
                case 2:
                    round = Math.round(f4 * 255.0f);
                    round2 = Math.round((abs + f4) * 255.0f);
                    round3 = Math.round((abs2 + f4) * 255.0f);
                    break;
                case 3:
                    round = Math.round(f4 * 255.0f);
                    round2 = Math.round((abs2 + f4) * 255.0f);
                    round3 = Math.round((abs + f4) * 255.0f);
                    break;
                case 4:
                    round = Math.round((abs2 + f4) * 255.0f);
                    round2 = Math.round(f4 * 255.0f);
                    round3 = Math.round((abs + f4) * 255.0f);
                    break;
                case 5:
                case 6:
                    round = Math.round((abs + f4) * 255.0f);
                    round2 = Math.round(f4 * 255.0f);
                    round3 = Math.round((abs2 + f4) * 255.0f);
                    break;
                default:
                    round3 = 0;
                    round = 0;
                    round2 = 0;
                    break;
            }
            return Color.rgb(constrain(round, 0, 255), constrain(round2, 0, 255), constrain(round3, 0, 255));
        }

        public static void colorToHSL(int i, float[] fArr) {
            RGBToHSL(Color.red(i), Color.green(i), Color.blue(i), fArr);
        }

        public static void RGBToHSL(int i, int i2, int i3, float[] fArr) {
            float f;
            float abs;
            float f2 = i / 255.0f;
            float f3 = i2 / 255.0f;
            float f4 = i3 / 255.0f;
            float max = Math.max(f2, Math.max(f3, f4));
            float min = Math.min(f2, Math.min(f3, f4));
            float f5 = max - min;
            float f6 = (max + min) / 2.0f;
            if (max == min) {
                f = 0.0f;
                abs = 0.0f;
            } else {
                f = max == f2 ? ((f3 - f4) / f5) % 6.0f : max == f3 ? ((f4 - f2) / f5) + 2.0f : 4.0f + ((f2 - f3) / f5);
                abs = f5 / (1.0f - Math.abs((2.0f * f6) - 1.0f));
            }
            float f7 = (f * 60.0f) % 360.0f;
            if (f7 < 0.0f) {
                f7 += 360.0f;
            }
            fArr[0] = constrain(f7, 0.0f, 360.0f);
            fArr[1] = constrain(abs, 0.0f, 1.0f);
            fArr[2] = constrain(f6, 0.0f, 1.0f);
        }
    }
}
