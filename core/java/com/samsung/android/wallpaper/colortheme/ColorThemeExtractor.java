package com.samsung.android.wallpaper.colortheme;

import android.graphics.Bitmap;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorPaletteExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.image.BitmapHelper;

/* loaded from: classes5.dex */
public class ColorThemeExtractor {
    private static final int DEST_SCALE_HEIGHT = 150;
    private static final String TAG = "ColorThemeExtractor";

    public static int[] getSeedColors(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        float[] clusterGroupSVvalues = {0.5f, 0.2f};
        int[] clusterGroupArray = ColorPaletteExtractor.makeClusterGroupColorBandBasedFromHueInterval(30, clusterGroupSVvalues);
        float bitmapScale = BitmapHelper.fineScaleValueBySquareRootSize(bitmap.getWidth(), bitmap.getHeight(), 150);
        float bitmapScale2 = bitmapScale <= 1.0f ? bitmapScale : 1.0f;
        Bitmap resizedSampleImage = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * bitmapScale2), (int) (bitmap.getHeight() * bitmapScale2), false);
        ColorPaletteExtractor.setSaturationThresholdForGrayscale(0.12f);
        ColorPaletteExtractor.setBrightnessThresholdForGrayscale(0.18f);
        ColorPaletteExtractor.setHsvSpaceHueRadiusValue(1.0f);
        ColorExtractor.DominantColorResult[] resultDominantColors = ColorPaletteExtractor.kMeansHsv(resizedSampleImage, clusterGroupArray);
        ColorPaletteExtractor.discardSameColorFromDominantColorsForColorPalette(resultDominantColors, 45.0f / 360.0d, ColorPaletteExtractor.ColorSpace.HUE, true);
        int[] resultColors = ColorPaletteExtractor.getOnlyColorsFromDominantColor(resultDominantColors, 45.0f);
        if (resultColors == null) {
            return new int[]{-16777216};
        }
        return resultColors;
    }
}
