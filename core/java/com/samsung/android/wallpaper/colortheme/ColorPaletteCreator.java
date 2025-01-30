package com.samsung.android.wallpaper.colortheme;

import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.colortheme.monet.ColorScheme;
import com.samsung.android.wallpaper.colortheme.monet.Style;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

/* loaded from: classes5.dex */
public class ColorPaletteCreator {
  private static final float ACCENT1_SAT_DELTA = 0.3f;
  private static final float ACCENT1_SAT_MAX = 0.8f;
  private static final float ACCENT2_SAT_MAX = 0.4f;
  private static final float ACCENT3_SAT_DELTA = 0.1f;
  private static final float ACCENT3_SAT_MAX = 0.6f;
  private static final int GRAY_HUE_PRESET1 = 0;
  private static final int GRAY_HUE_PRESET2 = 240;
  private static final float GRAY_SAT_PRESET1 = 0.0f;
  private static final float GRAY_SAT_PRESET2 = 0.05f;
  private static final int MAX_RANGE = 17;
  private static final float NEUTRAL_SAT_MAX = 0.15f;
  private static final int[] hueRange = {
    8, 22, 40, 52, 60, 81, 139, 169, 200, 219, 256, 279, 318, 337, 348, 356, 361
  };
  private static final int[] range = {
    -4, 8, 22, 40, 52, 60, 81, 139, 169, 200, 219, 256, 279, 318, 337, 348, 356
  };
  protected int[] mSeedColors;
  protected final List<int[][]> mColorPalettes = new ArrayList();
  protected final Style[] mWallpaperColorStyles = {
    Style.TONAL_SPOT, Style.SPRITZ, Style.VIBRANT, Style.EXPRESSIVE
  };

  public List<int[][]> getColorPalettes() {
    return this.mColorPalettes;
  }

  public int[] getSeedColors() {
    return this.mSeedColors;
  }

  public void setColors(float[][] p) {
    if (p != null && p.length > 0) {
      this.mSeedColors = new int[p.length];
      for (int i = 0; i < p.length; i++) {
        this.mSeedColors[i] = ColorUtils.HSLToColor(p[i]);
      }
    }
  }

  public void setColors(int[] seeds) {
    this.mSeedColors = seeds;
  }

  public void generateColorPalette() {
    this.mColorPalettes.clear();
    populateStyles();
  }

  public void generateColorPalette(boolean fromGoogle) {
    this.mColorPalettes.clear();
    if (fromGoogle) {
      for (int seed : this.mSeedColors) {
        for (Style style : this.mWallpaperColorStyles) {
          ColorScheme colorScheme = new ColorScheme(seed, false, style);
          this.mColorPalettes.add(new ColorPalette(colorScheme).getTable());
        }
      }
      return;
    }
    populateStyles();
  }

  static int findRange(float hue) {
    if (hue < 0.0f) {
      return 0;
    }
    int i = 0;
    while (true) {
      if (i >= range.length) {
        return -1;
      }
      if (r2[16] <= hue) {
        return 0;
      }
      if (hue >= r2[i]) {
        i++;
      } else {
        return i - 1;
      }
    }
  }

  static float findRatio(float hue, int r) {
    return (hue - range[r]) / (r0[r + 1] - r1);
  }

  static float getHue(int r, float ratio) {
    float hue = range[r] + ((r0[r + 1] - r1) * ratio);
    if (hue < 0.0f) {
      return hue + 360.0f;
    }
    if (hue > 360.0f) {
      return hue - 360.0f;
    }
    return hue;
  }

  static float hueMove(float hue, int step) {
    int r;
    int r2 = findRange(hue);
    float ratio = findRatio(hue, r2);
    if (ratio > 0.5f) {
      r = (r2 + step) % (range.length - 1);
    } else {
      int[] iArr = range;
      r = (((r2 - step) + iArr.length) - 1) % (iArr.length - 1);
    }
    return getHue(r, ratio);
  }

  float comp(float hue) {
    float hue2 = hue + 180.0f;
    if (hue2 > 360.0f) {
      return hue2 - 360.0f;
    }
    return hue2;
  }

  static boolean isGrayImage(float[][] colorHsl) {
    for (float[] color : colorHsl) {
      if (color[1] > 0.01f) {
        return false;
      }
    }
    return true;
  }

  private void populateStyles() {
    int[] iArr = this.mSeedColors;
    if (iArr == null || iArr.length <= 0) {
      return;
    }
    float[] accent1 = new float[3];
    float[] accent2 = new float[3];
    float[] accent3 = new float[3];
    float[] neutral1 = new float[3];
    float[] neutral2 = new float[3];
    float[][] colorHsl = (float[][]) Array.newInstance((Class<?>) Float.TYPE, iArr.length, 3);
    int i = 0;
    while (true) {
      int[] iArr2 = this.mSeedColors;
      if (i >= iArr2.length) {
        break;
      }
      ColorUtils.colorToHSL(iArr2[i], colorHsl[i]);
      i++;
    }
    if (isGrayImage(colorHsl)) {
      accent1[0] = 0.0f;
      accent1[1] = 0.0f;
      accent2[0] = 0.0f;
      accent2[1] = 0.0f;
      accent3[0] = 0.0f;
      accent3[1] = 0.0f;
      neutral1[0] = 0.0f;
      neutral1[1] = 0.0f;
      neutral2[0] = 0.0f;
      neutral2[1] = 0.0f;
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      accent1[0] = 0.0f;
      accent1[1] = 0.05f;
      accent2[0] = 0.0f;
      accent2[1] = 0.05f;
      accent3[0] = 0.0f;
      accent3[1] = 0.05f;
      neutral1[0] = 0.0f;
      neutral1[1] = 0.05f;
      neutral2[0] = 0.0f;
      neutral2[1] = 0.05f;
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      accent1[0] = 240.0f;
      accent1[1] = 0.05f;
      accent2[0] = 240.0f;
      accent2[1] = 0.05f;
      accent3[0] = 240.0f;
      accent3[1] = 0.05f;
      neutral1[0] = 240.0f;
      neutral1[1] = 0.05f;
      neutral2[0] = 240.0f;
      neutral2[1] = 0.05f;
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      return;
    }
    int[] iArr3 = this.mSeedColors;
    if (iArr3.length == 1) {
      accent1[0] = colorHsl[0][0];
      accent2[0] = hueMove(colorHsl[0][0], 1);
      accent3[0] = comp(colorHsl[0][0]);
      neutral1[0] = colorHsl[0][0];
      neutral2[0] = comp(colorHsl[0][0]);
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent2[1] = Math.min(colorHsl[0][1], 0.4f);
      accent3[1] = Math.min(colorHsl[0][1] + 0.1f, 0.6f);
      neutral1[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
      neutral2[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      accent1[0] = hueMove(colorHsl[0][0], 1);
      accent2[0] = hueMove(colorHsl[0][0], 2);
      accent3[0] = comp(hueMove(colorHsl[0][0], 1));
      neutral1[0] = hueMove(colorHsl[0][0], 1);
      neutral2[0] = comp(hueMove(colorHsl[0][0], 1));
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      accent1[0] = colorHsl[0][0];
      accent2[0] = comp(colorHsl[0][0]);
      accent3[0] = hueMove(colorHsl[0][0], 1);
      neutral1[0] = colorHsl[0][0];
      neutral2[0] = comp(colorHsl[0][0]);
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      return;
    }
    if (iArr3.length == 2) {
      accent1[0] = colorHsl[0][0];
      accent2[0] = hueMove(colorHsl[0][0], 1);
      accent3[0] = colorHsl[1][0];
      neutral1[0] = colorHsl[0][0];
      neutral2[0] = colorHsl[1][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent2[1] = Math.min(colorHsl[0][1], 0.4f);
      accent3[1] = Math.min(colorHsl[1][1] + 0.1f, 0.6f);
      neutral1[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
      neutral2[1] = Math.min(colorHsl[1][1], NEUTRAL_SAT_MAX);
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      accent1[0] = colorHsl[1][0];
      accent2[0] = hueMove(colorHsl[1][0], 1);
      accent3[0] = colorHsl[0][0];
      neutral1[0] = colorHsl[1][0];
      neutral2[0] = colorHsl[0][0];
      accent1[1] = Math.min(colorHsl[1][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent2[1] = Math.min(colorHsl[1][1], 0.4f);
      accent3[1] = Math.min(colorHsl[0][1] + 0.1f, 0.6f);
      neutral1[1] = Math.min(colorHsl[1][1], NEUTRAL_SAT_MAX);
      neutral2[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      accent1[0] = hueMove(colorHsl[0][0], 1);
      accent2[0] = hueMove(colorHsl[0][0], 2);
      accent3[0] = colorHsl[1][0];
      neutral1[0] = hueMove(colorHsl[0][0], 1);
      neutral2[0] = colorHsl[1][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent2[1] = Math.min(colorHsl[0][1], 0.4f);
      accent3[1] = Math.min(colorHsl[1][1] + 0.1f, 0.6f);
      neutral1[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
      neutral2[1] = Math.min(colorHsl[1][1], NEUTRAL_SAT_MAX);
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      accent1[0] = colorHsl[0][0];
      accent2[0] = colorHsl[1][0];
      accent3[0] = hueMove(colorHsl[1][0], 1);
      neutral1[0] = colorHsl[0][0];
      neutral2[0] = colorHsl[1][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent2[1] = Math.min(colorHsl[1][1], 0.4f);
      accent3[1] = Math.min(colorHsl[1][1] + 0.1f, 0.6f);
      neutral1[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
      neutral2[1] = Math.min(colorHsl[1][1], NEUTRAL_SAT_MAX);
      this.mColorPalettes.add(
          new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
      return;
    }
    accent1[0] = colorHsl[0][0];
    accent2[0] = hueMove(colorHsl[0][0], 1);
    accent3[0] = colorHsl[1][0];
    neutral1[0] = colorHsl[0][0];
    neutral2[0] = colorHsl[1][0];
    accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
    accent2[1] = Math.min(colorHsl[0][1], 0.4f);
    accent3[1] = Math.min(colorHsl[1][1] + 0.1f, 0.6f);
    neutral1[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
    neutral2[1] = Math.min(colorHsl[1][1], NEUTRAL_SAT_MAX);
    this.mColorPalettes.add(
        new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
    accent1[0] = colorHsl[1][0];
    accent2[0] = hueMove(colorHsl[1][0], 1);
    accent3[0] = colorHsl[0][0];
    neutral1[0] = colorHsl[1][0];
    neutral2[0] = colorHsl[0][0];
    accent1[1] = Math.min(colorHsl[1][1] + ACCENT1_SAT_DELTA, 0.8f);
    accent2[1] = Math.min(colorHsl[1][1], 0.4f);
    accent3[1] = Math.min(colorHsl[0][1] + 0.1f, 0.6f);
    neutral1[1] = Math.min(colorHsl[1][1], NEUTRAL_SAT_MAX);
    neutral2[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
    this.mColorPalettes.add(
        new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
    accent1[0] = colorHsl[2][0];
    accent2[0] = hueMove(colorHsl[2][0], 1);
    accent3[0] = colorHsl[0][0];
    neutral1[0] = colorHsl[2][0];
    neutral2[0] = colorHsl[0][0];
    accent1[1] = Math.min(colorHsl[2][1] + ACCENT1_SAT_DELTA, 0.8f);
    accent2[1] = Math.min(colorHsl[2][1], 0.4f);
    accent3[1] = Math.min(colorHsl[0][1] + 0.1f, 0.6f);
    neutral1[1] = Math.min(colorHsl[2][1], NEUTRAL_SAT_MAX);
    neutral2[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
    this.mColorPalettes.add(
        new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
    accent1[0] = colorHsl[0][0];
    accent2[0] = colorHsl[1][0];
    accent3[0] = colorHsl[2][0];
    neutral1[0] = colorHsl[0][0];
    neutral2[0] = colorHsl[1][0];
    accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
    accent2[1] = Math.min(colorHsl[1][1], 0.4f);
    accent3[1] = Math.min(colorHsl[2][1] + 0.1f, 0.6f);
    neutral1[1] = Math.min(colorHsl[0][1], NEUTRAL_SAT_MAX);
    neutral2[1] = Math.min(colorHsl[1][1], NEUTRAL_SAT_MAX);
    this.mColorPalettes.add(
        new ColorPalette(accent1, accent2, accent3, neutral1, neutral2).getTable());
  }

  public static int[] converAccent1ToSeedColors(int[] seeds) {
    if (seeds == null || seeds.length <= 0) {
      return null;
    }
    List<Integer> covertedSeeds = new ArrayList<>();
    float[] accent1 = new float[3];
    float[][] colorHsl = (float[][]) Array.newInstance((Class<?>) Float.TYPE, seeds.length, 3);
    for (int i = 0; i < seeds.length; i++) {
      ColorUtils.colorToHSL(seeds[i], colorHsl[i]);
    }
    if (isGrayImage(colorHsl)) {
      accent1[0] = 0.0f;
      accent1[1] = 0.0f;
      accent1[2] = 0.5f;
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = 0.0f;
      accent1[1] = 0.05f;
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = 240.0f;
      accent1[1] = 0.05f;
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
    } else if (seeds.length == 1) {
      accent1[0] = colorHsl[0][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent1[2] = 0.5f;
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = hueMove(colorHsl[0][0], 1);
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = colorHsl[0][0];
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
    } else if (seeds.length == 2) {
      accent1[0] = colorHsl[0][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent1[2] = 0.5f;
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = colorHsl[1][0];
      accent1[1] = Math.min(colorHsl[1][1] + ACCENT1_SAT_DELTA, 0.8f);
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = hueMove(colorHsl[0][0], 1);
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = colorHsl[0][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
    } else {
      accent1[0] = colorHsl[0][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      accent1[2] = 0.5f;
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = colorHsl[1][0];
      accent1[1] = Math.min(colorHsl[1][1] + ACCENT1_SAT_DELTA, 0.8f);
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = colorHsl[2][0];
      accent1[1] = Math.min(colorHsl[2][1] + ACCENT1_SAT_DELTA, 0.8f);
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
      accent1[0] = colorHsl[0][0];
      accent1[1] = Math.min(colorHsl[0][1] + ACCENT1_SAT_DELTA, 0.8f);
      covertedSeeds.add(Integer.valueOf(ColorUtils.HSLToColor(accent1)));
    }
    return covertedSeeds.stream()
        .mapToInt(
            new ToIntFunction() { // from class:
                                  // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator$$ExternalSyntheticLambda0
              @Override // java.util.function.ToIntFunction
              public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
              }
            })
        .toArray();
  }
}
