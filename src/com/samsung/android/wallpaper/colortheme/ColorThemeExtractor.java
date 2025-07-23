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
        int[] makeClusterGroupColorBandBasedFromHueInterval = ColorPaletteExtractor.makeClusterGroupColorBandBasedFromHueInterval(30, new float[]{0.5f, 0.2f});
        float fineScaleValueBySquareRootSize = BitmapHelper.fineScaleValueBySquareRootSize(bitmap.getWidth(), bitmap.getHeight(), 150);
        if (fineScaleValueBySquareRootSize > 1.0f) {
            fineScaleValueBySquareRootSize = 1.0f;
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * fineScaleValueBySquareRootSize), (int) (bitmap.getHeight() * fineScaleValueBySquareRootSize), false);
        ColorPaletteExtractor.setSaturationThresholdForGrayscale(0.12f);
        ColorPaletteExtractor.setBrightnessThresholdForGrayscale(0.18f);
        ColorPaletteExtractor.setHsvSpaceHueRadiusValue(1.0f);
        ColorExtractor.DominantColorResult[] kMeansHsv = ColorPaletteExtractor.kMeansHsv(createScaledBitmap, makeClusterGroupColorBandBasedFromHueInterval);
        double d = 45.0f;
        ColorPaletteExtractor.discardSameColorFromDominantColorsForColorPalette(kMeansHsv, d / 360.0d, ColorPaletteExtractor.ColorSpace.HUE, true);
        int[] onlyColorsFromDominantColor = ColorPaletteExtractor.getOnlyColorsFromDominantColor(kMeansHsv, d);
        return onlyColorsFromDominantColor == null ? new int[]{-16777216} : onlyColorsFromDominantColor;
    }
}
