package com.samsung.android.cocktailbar;

import android.content.Context;
import android.content.pm.PackageManager;
import java.io.File;

/* loaded from: classes5.dex */
public class CocktailBarFeatures {
  public static final String CATEGORY_NORMAL = "normal";
  public static final boolean COCKTAIL_ENABLED = true;
  private static final int FEATURE_COCKTAIL_BAR = 1;
  private static final int FEATURE_COCKTAIL_PANEL = 2;
  private static final int FEATURE_NONE = 0;
  private static boolean sQueriedTypeCocktail = false;
  private static int sCocktailFeature = 0;

  private static void ensureCocktailFeature(Context context) {
    if (!sQueriedTypeCocktail) {
      sQueriedTypeCocktail = true;
      PackageManager pm = null;
      if (context != null) {
        pm = context.getPackageManager();
      }
      try {
        int verifyCocktailFeature = verifyCocktailFeature(pm, 1, "com.sec.feature.cocktailbar");
        sCocktailFeature = verifyCocktailFeature;
        if (verifyCocktailFeature == 0) {
          sCocktailFeature =
              verifyCocktailFeature(pm, 2, PackageManager.SEM_FEATURE_COCKTAIL_PANEL);
        }
      } catch (Exception e) {
      }
    }
  }

  private static int verifyCocktailFeature(PackageManager pm, int feature, String systemFeature) {
    if (pm != null) {
      int result = pm.hasSystemFeature(systemFeature) ? feature : 0;
      return result;
    }
    String fileName = "system/etc/permissions/" + systemFeature + ".xml";
    int result2 = new File(fileName).exists() ? feature : 0;
    return result2;
  }

  public static boolean isSupportCocktailPanel(Context context) {
    ensureCocktailFeature(context);
    int i = sCocktailFeature;
    return i == 1 || i == 2;
  }

  @Deprecated
  public static boolean isSystemBarType(Context context) {
    return false;
  }
}
