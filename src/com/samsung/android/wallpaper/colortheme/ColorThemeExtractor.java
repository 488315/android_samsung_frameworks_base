package com.samsung.android.wallpaper.colortheme;

import android.graphics.Bitmap;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.image.BitmapHelper;

/* loaded from: classes6.dex */
public class ColorThemeExtractor {
    private static final int DEST_SCALE_HEIGHT = 150;
    private static final String TAG = "ColorThemeExtractor";

    public static int[] getSeedColors(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int[] iArrMakeClusterGroupColorBandBasedFromHueInterval = ColorPaletteExtractor.makeClusterGroupColorBandBasedFromHueInterval(30, new float[]{0.5f, 0.2f});
        float fFineScaleValueBySquareRootSize = BitmapHelper.fineScaleValueBySquareRootSize(bitmap.getWidth(), bitmap.getHeight(), 150);
        if (fFineScaleValueBySquareRootSize > 1.0f) {
            fFineScaleValueBySquareRootSize = 1.0f;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * fFineScaleValueBySquareRootSize), (int) (bitmap.getHeight() * fFineScaleValueBySquareRootSize), false);
        ColorPaletteExtractor.setSaturationThresholdForGrayscale(0.12f);
        ColorPaletteExtractor.setBrightnessThresholdForGrayscale(0.18f);
        ColorPaletteExtractor.setHsvSpaceHueRadiusValue(1.0f);
        ColorExtractor.DominantColorResult[] dominantColorResultArrKMeansHsv = ColorPaletteExtractor.kMeansHsv(bitmapCreateScaledBitmap, iArrMakeClusterGroupColorBandBasedFromHueInterval);
        double d = 45.0f;
        ColorPaletteExtractor.discardSameColorFromDominantColorsForColorPalette(dominantColorResultArrKMeansHsv, d / 360.0d, ColorPaletteExtractor.ColorSpace.HUE, true);
        int[] onlyColorsFromDominantColor = ColorPaletteExtractor.getOnlyColorsFromDominantColor(dominantColorResultArrKMeansHsv, d);
        return onlyColorsFromDominantColor == null ? new int[]{-16777216} : onlyColorsFromDominantColor;
    }
}
