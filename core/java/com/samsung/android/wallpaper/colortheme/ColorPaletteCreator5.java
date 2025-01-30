package com.samsung.android.wallpaper.colortheme;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.android.internal.graphics.ColorUtils;
import java.lang.reflect.Array;
import java.util.List;

/* loaded from: classes5.dex */
public class ColorPaletteCreator5 extends ColorPaletteCreator {
  private static final int GRAY_HUE_PRESET1 = 0;
  private static final int GRAY_HUE_PRESET2 = 120;
  private static final int GRAY_HUE_PRESET3 = 240;
  private static final float GRAY_SAT_PRESET1 = 0.0f;
  private static final float GRAY_SAT_PRESET2 = 0.05f;
  private static final int MAX_RANGE = 19;
  private static final String TAG = "ColorPaletteCreator5";
  private static final int[] range = {
    -8, 23, 34, 44, 52, 61, 79, 134, 168, 184, 194, 201, 222, 264, 289, 314, 329, 345, 352
  };
  float[][] mColorHsl;
  float[] accent1 = new float[3];
  float[] accent2 = new float[3];
  float[] accent3 = new float[3];
  float[] neutral1 = new float[3];
  float[] neutral2 = new float[3];

  @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
  public List<int[][]> getColorPalettes() {
    return this.mColorPalettes;
  }

  @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
  public void setColors(float[][] p) {
    if (p != null && p.length > 0) {
      this.mSeedColors = new int[p.length];
      for (int i = 0; i < p.length; i++) {
        this.mSeedColors[i] = ColorUtils.HSLToColor(p[i]);
      }
    }
  }

  @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
  public void setColors(int[] seeds) {
    this.mSeedColors = seeds;
  }

  @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
  public void generateColorPalette() {
    this.mColorPalettes.clear();
    populateStyles();
  }

  @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
  public void generateColorPalette(boolean monet) {
    if (monet) {
      super.generateColorPalette(true);
    } else {
      generateColorPalette();
    }
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
      if (r2[18] <= hue) {
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
    int[] iArr = range;
    float ratio = (((hue - iArr[r]) + 360.0f) % 360.0f) / (iArr[r + 1] - r1);
    if (ratio > 1.0f || ratio < 0.0f) {
      Log.m96e(TAG, "findRatio : ratio is more than 1");
      Log.m100v(
          TAG,
          "findRatio : hue = "
              + hue
              + " range[r] = "
              + iArr[r]
              + " range[r+1] = "
              + iArr[r + 1]
              + " ratio = "
              + ratio);
    }
    return ratio;
  }

  static float getHue(int r, float ratio) {
    float hue = range[((r0.length + r) - 1) % (r0.length - 1)] + ((r0[r1 + 1] - r4) * ratio);
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
      r = (r2 - step) % (range.length - 1);
    }
    return getHue(r, ratio);
  }

  @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
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
    if (this.mSeedColors == null || this.mSeedColors.length <= 0) {
      return;
    }
    Log.m100v(TAG, "populateStyles : seedsColor length" + this.mSeedColors.length);
    this.mColorHsl =
        (float[][]) Array.newInstance((Class<?>) Float.TYPE, this.mSeedColors.length, 3);
    for (int i = 0; i < this.mSeedColors.length; i++) {
      ColorUtils.colorToHSL(this.mSeedColors[i], this.mColorHsl[i]);
      Log.m100v(
          TAG,
          "populateStyles : seed = "
              + this.mSeedColors[i]
              + " C"
              + (i + 1)
              + " = "
              + this.mColorHsl[i][0]);
    }
    if (isGrayImage(this.mColorHsl)) {
      addGrayStylePalette();
      return;
    }
    for (int i2 = 0; i2 < this.mSeedColors.length; i2++) {
      float[] floats = this.mColorHsl[i2];
      addTonalSpot(floats);
      addNeutral(floats);
      addVibrant(floats);
      addExpressive(floats, i2);
    }
  }

  private void addGrayStylePalette() {
    float[] fArr = this.accent1;
    fArr[0] = 0.0f;
    fArr[1] = 0.0f;
    float[] fArr2 = this.accent2;
    fArr2[0] = 0.0f;
    fArr2[1] = 0.0f;
    float[] fArr3 = this.accent3;
    fArr3[0] = 0.0f;
    fArr3[1] = 0.0f;
    float[] fArr4 = this.neutral1;
    fArr4[0] = 0.0f;
    fArr4[1] = 0.0f;
    float[] fArr5 = this.neutral2;
    fArr5[0] = 0.0f;
    fArr5[1] = 0.0f;
    this.mColorPalettes.add(
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2)
            .getTable());
    float[] fArr6 = this.accent1;
    fArr6[0] = 0.0f;
    fArr6[1] = 0.05f;
    float[] fArr7 = this.accent2;
    fArr7[0] = 0.0f;
    fArr7[1] = 0.05f;
    float[] fArr8 = this.accent3;
    fArr8[0] = 0.0f;
    fArr8[1] = 0.05f;
    float[] fArr9 = this.neutral1;
    fArr9[0] = 0.0f;
    fArr9[1] = 0.05f;
    float[] fArr10 = this.neutral2;
    fArr10[0] = 0.0f;
    fArr10[1] = 0.05f;
    this.mColorPalettes.add(
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2)
            .getTable());
    float[] fArr11 = this.accent1;
    fArr11[0] = 120.0f;
    fArr11[1] = 0.05f;
    float[] fArr12 = this.accent2;
    fArr12[0] = 120.0f;
    fArr12[1] = 0.05f;
    float[] fArr13 = this.accent3;
    fArr13[0] = 120.0f;
    fArr13[1] = 0.05f;
    float[] fArr14 = this.neutral1;
    fArr14[0] = 120.0f;
    fArr14[1] = 0.05f;
    float[] fArr15 = this.neutral2;
    fArr15[0] = 120.0f;
    fArr15[1] = 0.05f;
    this.mColorPalettes.add(
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2)
            .getTable());
    float[] fArr16 = this.accent1;
    fArr16[0] = 240.0f;
    fArr16[1] = 0.05f;
    float[] fArr17 = this.accent2;
    fArr17[0] = 240.0f;
    fArr17[1] = 0.05f;
    float[] fArr18 = this.accent3;
    fArr18[0] = 240.0f;
    fArr18[1] = 0.05f;
    float[] fArr19 = this.neutral1;
    fArr19[0] = 240.0f;
    fArr19[1] = 0.05f;
    float[] fArr20 = this.neutral2;
    fArr20[0] = 240.0f;
    fArr20[1] = 0.05f;
    this.mColorPalettes.add(
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2)
            .getTable());
  }

  private ColorPalette addTonalSpot(float[] colorHsl) {
    float[] fArr = this.accent1;
    fArr[0] = colorHsl[0];
    fArr[1] = Math.min(colorHsl[1] + 0.3f, 0.7f);
    this.accent2[0] = hueMove(colorHsl[0], 2);
    this.accent2[1] = Math.min(colorHsl[1] + 0.0f, 0.4f);
    this.accent3[0] = hueMove(colorHsl[0], 5);
    this.accent3[1] = Math.min(colorHsl[1] + 0.1f, 0.5f);
    float[] fArr2 = this.neutral1;
    fArr2[0] = colorHsl[0];
    fArr2[1] = Math.min(colorHsl[1] + 0.0f, 0.15f);
    float[] fArr3 = this.neutral2;
    fArr3[0] = colorHsl[0];
    fArr3[1] = Math.min(colorHsl[1] + 0.0f, 0.0f);
    ColorPalette palette =
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
    this.mColorPalettes.add(palette.getTable());
    return palette;
  }

  private ColorPalette addNeutral(float[] colorHsl) {
    float[] fArr = this.accent1;
    fArr[0] = colorHsl[0];
    fArr[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
    this.accent2[0] = hueMove(colorHsl[0], 2);
    this.accent2[1] = Math.min(colorHsl[1] + 0.0f, 0.1f);
    this.accent3[0] = hueMove(colorHsl[0], 5);
    this.accent3[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
    float[] fArr2 = this.neutral1;
    fArr2[0] = colorHsl[0];
    fArr2[1] = Math.min(colorHsl[1] + 0.0f, GRAY_SAT_PRESET2);
    float[] fArr3 = this.neutral2;
    fArr3[0] = colorHsl[0];
    fArr3[1] = Math.min(colorHsl[1] + 0.0f, 0.0f);
    ColorPalette palette =
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
    this.mColorPalettes.add(palette.getTable());
    return palette;
  }

  private ColorPalette addVibrant(float[] colorHsl) {
    float[] fArr = this.accent1;
    fArr[0] = colorHsl[0];
    fArr[1] = Math.min(colorHsl[1] + 0.3f, 0.8f);
    float[] fArr2 = this.accent2;
    fArr2[0] = colorHsl[0];
    fArr2[1] = Math.min(colorHsl[1] + 0.0f, 0.6f);
    this.accent3[0] = hueMove(colorHsl[0], 1);
    this.accent3[1] = Math.min(colorHsl[1] + 0.1f, 0.6f);
    float[] fArr3 = this.neutral1;
    fArr3[0] = colorHsl[0];
    fArr3[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
    float[] fArr4 = this.neutral2;
    fArr4[0] = colorHsl[0];
    fArr4[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
    ColorPalette palette =
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
    this.mColorPalettes.add(palette.getTable());
    return palette;
  }

  private ColorPalette addExpressive(float[] colorHsl, int order) {
    this.accent1[0] = getExpressiveHue(colorHsl[0], order);
    this.accent1[1] = Math.min(colorHsl[1] + 0.0f, 0.7f);
    float[] fArr = this.accent2;
    fArr[0] = colorHsl[0];
    fArr[1] = Math.min(colorHsl[1] + 0.0f, 0.6f);
    this.accent3[0] = hueMove(colorHsl[0], 1);
    this.accent3[1] = Math.min(colorHsl[1] + 0.0f, 0.4f);
    float[] fArr2 = this.neutral1;
    fArr2[0] = colorHsl[0];
    fArr2[1] = Math.min(colorHsl[1] + 0.0f, 0.3f);
    float[] fArr3 = this.neutral2;
    fArr3[0] = colorHsl[0];
    fArr3[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
    ColorPalette palette =
        new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
    this.mColorPalettes.add(palette.getTable());
    return palette;
  }

  public float getExpressiveHue(float hue, int order) {
    float targetHue;
    int r = findRange(hue);
    float ratio = findRatio(hue, r);
    int[] iArr = range;
    int leftRange = (((r - 5) + iArr.length) - 1) % (iArr.length - 1);
    float leftHue = getHue(leftRange, ratio);
    int rightRange = (((r + 5) + iArr.length) - 1) % (iArr.length - 1);
    float rightHue = getHue(rightRange, ratio);
    Log.m100v(
        TAG,
        "getExpressiveHue : leftHue = "
            + leftHue
            + " rightHue = "
            + rightHue
            + " C"
            + (order + 1)
            + " case");
    if (leftHue > rightHue) {
      Log.m100v(
          TAG,
          "getExpressiveHue : leftHue("
              + leftHue
              + ") > c1, c2, c3, c4 > rightHue("
              + rightHue
              + NavigationBarInflaterView.KEY_CODE_END);
      int i = 0;
      while (true) {
        float[][] fArr = this.mColorHsl;
        if (i >= fArr.length) {
          break;
        }
        if (i != order) {
          float targetHue2 = fArr[i][0];
          if (rightHue < targetHue2 && targetHue2 < leftHue) {
            Log.m100v(TAG, "getExpressiveHue : return seeds C" + (i + 1) + " " + targetHue2);
            return targetHue2;
          }
        }
        i++;
      }
    } else {
      Log.m100v(
          TAG,
          "getExpressiveHue : 360 > c1,c2,c3,c4 > rightHue("
              + rightHue
              + ") || 0 < c1,c2,c3,c4 < leftHue("
              + leftHue
              + NavigationBarInflaterView.KEY_CODE_END);
      int i2 = 0;
      while (true) {
        float[][] fArr2 = this.mColorHsl;
        if (i2 >= fArr2.length) {
          break;
        }
        if (i2 != order) {
          targetHue = fArr2[i2][0];
          if ((360.0f > targetHue && targetHue > rightHue)
              || (leftHue > targetHue && targetHue > 0.0f)) {
            break;
          }
        }
        i2++;
      }
      Log.m100v(TAG, "getExpressiveHue : return seeds C" + (i2 + 1) + " " + targetHue);
      return targetHue;
    }
    return hueMove(hue, -5);
  }
}
