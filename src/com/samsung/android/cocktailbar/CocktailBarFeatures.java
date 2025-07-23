package com.samsung.android.cocktailbar;

import android.content.Context;
import android.content.pm.PackageManager;
import java.io.File;

/* loaded from: classes6.dex */
public class CocktailBarFeatures {
    public static final String CATEGORY_NORMAL = "normal";
    public static final boolean COCKTAIL_ENABLED = true;
    private static final int FEATURE_COCKTAIL_BAR = 1;
    private static final int FEATURE_COCKTAIL_PANEL = 2;
    private static final int FEATURE_NONE = 0;
    private static int sCocktailFeature = 0;
    private static boolean sQueriedTypeCocktail = false;

    @Deprecated
    public static boolean isSystemBarType(Context context) {
        return false;
    }

    private static void ensureCocktailFeature(Context context) {
        if (sQueriedTypeCocktail) {
            return;
        }
        sQueriedTypeCocktail = true;
        PackageManager packageManager = context != null ? context.getPackageManager() : null;
        try {
            int verifyCocktailFeature = verifyCocktailFeature(packageManager, 1, "com.sec.feature.cocktailbar");
            sCocktailFeature = verifyCocktailFeature;
            if (verifyCocktailFeature == 0) {
                sCocktailFeature = verifyCocktailFeature(packageManager, 2, PackageManager.SEM_FEATURE_COCKTAIL_PANEL);
            }
        } catch (Exception unused) {
        }
    }

    private static int verifyCocktailFeature(PackageManager packageManager, int i, String str) {
        if (packageManager != null) {
            if (!packageManager.hasSystemFeature(str)) {
                return 0;
            }
        } else {
            if (!new File("system/etc/permissions/" + str + ".xml").exists()) {
                return 0;
            }
        }
        return i;
    }

    public static boolean isSupportCocktailPanel(Context context) {
        ensureCocktailFeature(context);
        int i = sCocktailFeature;
        return i == 1 || i == 2;
    }
}
