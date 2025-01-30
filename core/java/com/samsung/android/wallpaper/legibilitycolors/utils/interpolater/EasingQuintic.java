package com.samsung.android.wallpaper.legibilitycolors.utils.interpolater;


/* loaded from: classes5.dex */
public class EasingQuintic implements IEasing {
  private static EasingQuintic mInstance = null;

  private EasingQuintic() {}

  public static EasingQuintic getInstance() {
    if (mInstance == null) {
      mInstance = new EasingQuintic();
    }
    return mInstance;
  }

  @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
  public float easeIn(float t, float b, float c, float d) {
    float t2 = t / d;
    return (t2 * c * t2 * t2 * t2 * t2) + b;
  }

  @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
  public float easeInOut(float t, float b, float c, float d) {
    float t2 = t / (d / 2.0f);
    if (t2 >= 1.0f) {
      float t3 = t2 - 2.0f;
      return ((c / 2.0f) * ((t3 * t3 * t3 * t3 * t3) + 2.0f)) + b;
    }
    return ((c / 2.0f) * t2 * t2 * t2 * t2 * t2) + b;
  }

  @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
  public float easeOut(float t, float b, float c, float d) {
    float t2 = (t / d) - 1.0f;
    return (((t2 * t2 * t2 * t2 * t2) + 1.0f) * c) + b;
  }

  @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
  public float easeOutIn(float t, float b, float c, float d) {
    if (t < d / 2.0f) {
      float t2 = ((2.0f * t) / d) - 1.0f;
      return ((c / 2.0f) * ((t2 * t2 * t2 * t2 * t2) + 1.0f)) + b;
    }
    float t3 = ((t * 2.0f) - d) / d;
    return ((c / 2.0f) * t3 * t3 * t3 * t3 * t3) + (c / 2.0f) + b;
  }

  /* renamed from: com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.EasingQuintic$1 */
  static /* synthetic */ class C53231 {

    /* renamed from: $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing */
    static final /* synthetic */ int[] f3084xed974fcc;

    static {
      int[] iArr = new int[IEasing.EEasing.values().length];
      f3084xed974fcc = iArr;
      try {
        iArr[IEasing.EEasing.In.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f3084xed974fcc[IEasing.EEasing.Out.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        f3084xed974fcc[IEasing.EEasing.InOut.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f3084xed974fcc[IEasing.EEasing.OutIn.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
    }
  }

  @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
  public float ease(float t, float b, float c, float d, IEasing.EEasing easing) {
    switch (C53231.f3084xed974fcc[easing.ordinal()]) {
      case 1:
        return easeIn(t, b, c, d);
      case 2:
        return easeOut(t, b, c, d);
      case 3:
        return easeInOut(t, b, c, d);
      case 4:
        return easeOutIn(t, b, c, d);
      default:
        return ((c * t) / d) + b;
    }
  }
}
