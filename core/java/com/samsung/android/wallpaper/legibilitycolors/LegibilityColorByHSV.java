package com.samsung.android.wallpaper.legibilitycolors;

import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;

/* loaded from: classes5.dex */
public class LegibilityColorByHSV {
  static final int BLACK_COLOR = -16777216;
  static final float CONTRAST_BLACK_THRESHOLD = 1.34f;
  static final float CONTRAST_WHITE_THRESHOLD = 1.24f;
  static final float DIFF_V = 0.11f;
  static final float PERCENTAGE_THRESHOLD = 0.22f;
  static final float SIMILAR_CONTRAST_THRESHOLD = 1.8f;
  static final float SIMILAR_PERCENTAGE_THRESHOLD = 0.1f;
  static final int WHITE_COLOR = -1;
  private static float[][] mLegibilityTable = {
    new float[] {
      76.0f, 82.0f, 85.0f, 87.0f, 89.0f, 92.0f, 95.0f, 98.0f, 100.0f, 100.0f, 100.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    },
    new float[] {
      76.0f, 82.0f, 85.0f, 87.0f, 89.0f, 92.0f, 94.0f, 95.0f, 98.0f, 100.0f, 100.0f, 100.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    },
    new float[] {
      76.0f, 82.0f, 85.0f, 86.5f, 88.5f, 90.0f, 91.0f, 91.5f, 94.0f, 96.0f, 97.5f, 98.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    },
    new float[] {
      76.0f, 82.0f, 86.0f, 88.0f, 90.0f, 92.0f, 92.85f, 93.21f, 94.0f, 94.0f, 94.0f, 94.5f, 94.5f,
      95.0f, 95.0f, 95.5f, 95.5f, 96.0f, 96.0f, 96.0f
    },
    new float[] {
      76.0f, 82.0f, 86.0f, 87.5f, 89.0f, 89.0f, 89.3f, 90.0f, 90.2f, 90.71f, 90.71f, 90.71f, 90.71f,
      90.71f, 91.0f, 91.0f, 92.0f, 92.0f, 93.0f, 93.0f
    },
    new float[] {
      76.0f, 82.0f, 86.0f, 87.0f, 88.0f, 88.92f, 89.28f, 90.0f, 90.2f, 90.3f, 90.6f, 91.5f, 92.5f,
      93.0f, 93.5f, 93.5f, 94.0f, 94.5f, 95.0f, 95.0f
    },
    new float[] {
      76.0f, 82.0f, 86.0f, 89.0f, 90.5f, 91.5f, 92.1f, 92.3f, 92.4f, 92.5f, 92.6f, 92.6f, 92.7f,
      92.9f, 93.5f, 93.54f, 94.0f, 94.5f, 95.0f, 95.0f
    },
    new float[] {
      76.0f, 82.0f, 85.0f, 87.0f, 89.0f, 90.35f, 91.07f, 91.42f, 91.8f, 92.5f, 93.0f, 93.5f, 93.8f,
      93.8f, 93.9f, 94.0f, 94.1f, 94.16f, 94.16f, 94.16f
    },
    new float[] {
      76.0f, 82.0f, 83.0f, 85.0f, 86.0f, 87.0f, 89.0f, 90.0f, 91.0f, 92.0f, 92.5f, 92.5f, 92.5f,
      92.5f, 92.5f, 92.5f, 93.0f, 93.5f, 93.8f, 94.0f
    },
    new float[] {
      76.0f, 82.0f, 84.0f, 85.5f, 86.0f, 87.0f, 90.0f, 93.0f, 95.0f, 96.0f, 97.0f, 98.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    },
    new float[] {
      76.0f, 82.0f, 84.5f, 86.0f, 87.0f, 89.0f, 93.0f, 96.0f, 100.0f, 100.0f, 100.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    },
    new float[] {
      76.0f, 82.0f, 85.0f, 87.0f, 92.0f, 96.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    },
    new float[] {
      76.0f, 82.0f, 85.0f, 87.0f, 92.0f, 96.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    },
    new float[] {
      76.0f, 82.0f, 88.0f, 92.0f, 96.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f,
      100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f
    }
  };
  static SimilarColorResult mSimilarColorResult = new SimilarColorResult();

  public static class EdgeCaseResultForIndicator {
    public float black_contrast_percent;
    public int color;
    public LegibilityDefinition.ColorType colorType;
    public LegibilityDefinition.ColorType initColorType;
    public boolean isEdgeCase;
    public float white_contrast_percent;
  }

  public static LegibilityDefinition.ColorType getLegibilityColorType(float h, float s, float v) {
    return mGetLegibilityColorType(h, s, v);
  }

  protected static LegibilityDefinition.ColorType mGetLegibilityColorType(
      float h, float s, float v) {
    LegibilityDefinition.ColorType ret = LegibilityDefinition.ColorType.LIGHT;
    int hIdx = 0;
    if (h >= 10.0f && h < 20.0f) {
      hIdx = 1;
    } else if (h >= 20.0f && h < 40.0f) {
      hIdx = 2;
    } else if (h >= 40.0f && h < 50.0f) {
      hIdx = 3;
    } else if (h >= 50.0f && h < 61.0f) {
      hIdx = 4;
    } else if (h >= 61.0f && h < 83.0f) {
      hIdx = 5;
    } else if (h >= 83.0f && h < 140.0f) {
      hIdx = 6;
    } else if (h >= 140.0f && h < 166.0f) {
      hIdx = 7;
    } else if (h >= 166.0f && h < 186.0f) {
      hIdx = 8;
    } else if (h >= 186.0f && h < 211.0f) {
      hIdx = 9;
    } else if (h >= 211.0f && h < 241.0f) {
      hIdx = 10;
    } else if (h >= 241.0f && h < 261.0f) {
      hIdx = 11;
    } else if (h >= 261.0f && h < 318.0f) {
      hIdx = 12;
    } else if (h >= 318.0f && h < 339.0f) {
      hIdx = 13;
    }
    float tmpVal = s * 100.0f;
    int tmpValInt = (int) tmpVal;
    int sIdx = (tmpValInt / 5) - (tmpValInt / 100);
    if (100.0f * v > mLegibilityTable[hIdx][sIdx]) {
      LegibilityDefinition.ColorType ret2 = LegibilityDefinition.ColorType.DARK;
      return ret2;
    }
    return ret;
  }

  public static LegibilityDefinition.ColorWeightType getLegibilityColorWeight(
      LegibilityDefinition.ColorType majorColorType,
      float majorH,
      float majorS,
      float majorV,
      LegibilityDefinition.ColorType minorColorType,
      float minorH,
      float minorS,
      float minorV) {
    return mGetLegibilityColorWeight(
        majorColorType, majorH, majorS, majorV, minorColorType, minorH, minorS, minorV);
  }

  private static LegibilityDefinition.ColorWeightType mGetLegibilityColorWeight(
      LegibilityDefinition.ColorType majorColorType,
      float majorH,
      float majorS,
      float majorV,
      LegibilityDefinition.ColorType minorColorType,
      float minorH,
      float minorS,
      float minorV) {
    LegibilityDefinition.ColorWeightType ret = LegibilityDefinition.ColorWeightType.EACH;
    if (Math.abs(majorV - minorV) >= DIFF_V
        || minorColorType != LegibilityDefinition.ColorType.LIGHT) {
      return ret;
    }
    if (minorS < 0.9f && minorV > 90.0f) {
      return LegibilityDefinition.ColorWeightType.EACH;
    }
    return LegibilityDefinition.ColorWeightType.UNITY;
  }

  public static EdgeCaseResultForIndicator calcurateIndicatorLegibility(int[] pixels) {
    ColorHSV indicator_hsv = new ColorHSV();
    indicator_hsv.calcAvgColor(pixels);
    LegibilityDefinition.ColorType colorType =
        getLegibilityColorType(
            indicator_hsv.getAvgH(), indicator_hsv.getAvgS(), indicator_hsv.getAvgV());
    ColorExtractor.DominantColorResult[] result =
        ColorExtractor.kMeansHsv(pixels, ColorExtractor.makeClusterGroupColorBandBased2());
    return checkEdgeCaseForIndicator(result, colorType);
  }

  private static EdgeCaseResultForIndicator checkEdgeCaseForIndicator(
      ColorExtractor.DominantColorResult[] result, LegibilityDefinition.ColorType colorType) {
    EdgeCaseResultForIndicator ret = new EdgeCaseResultForIndicator();
    ret.initColorType = colorType;
    ret.colorType = LegibilityDefinition.ColorType.NONE;
    ret.isEdgeCase = false;
    ret.white_contrast_percent = 0.0f;
    ret.black_contrast_percent = 0.0f;
    ret.color = -1;
    float white_contrast_percent = 0.0f;
    float black_contrast_percent = 0.0f;
    for (ColorExtractor.DominantColorResult dominantColorResult : result) {
      if (dominantColorResult.percentage <= 0.0f) {
        break;
      }
      if (ColorUtils.calculateContrast(-1, dominantColorResult.color) < 1.2400000095367432d) {
        white_contrast_percent += dominantColorResult.percentage;
      }
      if (ColorUtils.calculateContrast(-16777216, dominantColorResult.color) < 1.340000033378601d) {
        black_contrast_percent += dominantColorResult.percentage;
      }
    }
    ret.white_contrast_percent = white_contrast_percent;
    ret.black_contrast_percent = black_contrast_percent;
    if (white_contrast_percent > PERCENTAGE_THRESHOLD
        && black_contrast_percent > PERCENTAGE_THRESHOLD) {
      ret.isEdgeCase = true;
      if (white_contrast_percent >= black_contrast_percent) {
        float[] color = {0.0f, 0.0f, ((black_contrast_percent / 0.5f) * 0.3f) + 0.2f};
        ret.color = IUXColorUtils.HSVToColor(color);
        ret.colorType = LegibilityDefinition.ColorType.GRAY;
      } else if (white_contrast_percent < black_contrast_percent) {
        float[] color2 = {0.0f, 0.0f, 0.8f - ((white_contrast_percent / 0.5f) * 0.3f)};
        ret.color = IUXColorUtils.HSVToColor(color2);
        ret.colorType = LegibilityDefinition.ColorType.GRAY;
      }
      if (checkSimilarColor(result, colorType, ret.color)) {
        ret.color = mSimilarColorResult.color;
        ret.colorType = mSimilarColorResult.colorType;
        ret.isEdgeCase = false;
      }
    } else if (white_contrast_percent > PERCENTAGE_THRESHOLD) {
      ret.isEdgeCase = true;
      ret.color = -16777216;
      ret.colorType = LegibilityDefinition.ColorType.DARK;
    } else if (black_contrast_percent > PERCENTAGE_THRESHOLD) {
      ret.isEdgeCase = true;
      ret.color = -1;
      ret.colorType = LegibilityDefinition.ColorType.LIGHT;
    } else {
      ret.isEdgeCase = false;
      ret.colorType = colorType;
    }
    return ret;
  }

  private static boolean checkSimilarColor(
      ColorExtractor.DominantColorResult[] result,
      LegibilityDefinition.ColorType colorType,
      int refColor) {
    for (ColorExtractor.DominantColorResult colorResult : result) {
      double tmpContrast = ColorUtils.calculateContrast(refColor, colorResult.color);
      if (tmpContrast < 1.7999999523162842d && colorResult.percentage > 0.1f) {
        if (colorType == LegibilityDefinition.ColorType.LIGHT) {
          mSimilarColorResult.color = -1;
          mSimilarColorResult.colorType = LegibilityDefinition.ColorType.LIGHT;
        } else if (colorType == LegibilityDefinition.ColorType.DARK) {
          mSimilarColorResult.color = -16777216;
          mSimilarColorResult.colorType = LegibilityDefinition.ColorType.DARK;
        }
        return true;
      }
    }
    return false;
  }

  private static class SimilarColorResult {
    int color;
    LegibilityDefinition.ColorType colorType;

    private SimilarColorResult() {}
  }
}
