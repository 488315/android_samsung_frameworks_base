package com.samsung.android.sdk.look;

import android.app.ActivityThread;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import com.samsung.android.cocktailbar.CocktailBarManager;
import java.util.List;

/* loaded from: classes6.dex */
public class SlookImpl {
    private static final int AIRBUTTON = 1;
    private static final int COCKTAIL_BAR = 6;
    private static final int COCKTAIL_PANEL = 7;
    public static final boolean DEBUG = true;
    private static final int SMARTCLIP = 2;
    private static final int SPEN_HOVER_ICON = 4;
    private static final int WRITINGBUDDY = 3;
    private static final int SDK_INT = SystemProperties.getInt("ro.slook.ver", 0);
    private static int sCocktailLevel = -1;
    private static int sUspLevel = -1;
    private static int sHasMetaEdgeSingle = -1;

    public static class VERSION_CODES {
        public static final int L1 = 1;
        public static final int L2 = 2;
    }

    public static int getVersionCode() {
        return SDK_INT;
    }

    public static boolean isFeatureEnabled(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i != 6) {
                            if (i != 7) {
                                return false;
                            }
                            checkCocktailLevel();
                            int i2 = sCocktailLevel;
                            return i2 > 0 && i2 <= i;
                        }
                        checkCocktailLevel();
                        int i3 = sCocktailLevel;
                        if (i3 > 0 && i3 <= i) {
                            return true;
                        }
                        if (i3 > 0) {
                            checkValidCocktailMetaData();
                            if (sHasMetaEdgeSingle == 1) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        if (sUspLevel == -1) {
            ActivityThread.getPackageManager();
        }
        if (i == 1) {
            int i4 = sUspLevel;
            return i4 >= 2 && i4 <= 3;
        }
        if (i != 3) {
            return sUspLevel >= 2;
        }
        int i5 = sUspLevel;
        return i5 >= 2 && i5 <= 9;
    }

    private static void checkCocktailLevel() {
        IPackageManager packageManager;
        if (sCocktailLevel != -1 || (packageManager = ActivityThread.getPackageManager()) == null) {
            return;
        }
        try {
            int i = packageManager.hasSystemFeature("com.sec.feature.cocktailbar", 0) ? 6 : 0;
            sCocktailLevel = i;
            if (i == 0) {
                sCocktailLevel = packageManager.hasSystemFeature(PackageManager.SEM_FEATURE_COCKTAIL_PANEL, 0) ? 7 : 0;
            }
        } catch (RemoteException e) {
            throw new RuntimeException("Package manager has died", e);
        }
    }

    private static void checkValidCocktailMetaData() {
        Bundle bundle;
        String string;
        String string2;
        if (sHasMetaEdgeSingle == -1) {
            sHasMetaEdgeSingle = 0;
            IPackageManager packageManager = ActivityThread.getPackageManager();
            String strCurrentOpPackageName = ActivityThread.currentOpPackageName();
            if (packageManager == null || strCurrentOpPackageName == null) {
                return;
            }
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(strCurrentOpPackageName, 128L, UserHandle.myUserId());
                if (applicationInfo != null) {
                    Bundle bundle2 = applicationInfo.metaData;
                    if (bundle2 != null && (string2 = bundle2.getString("com.samsung.android.cocktail.mode", "")) != null && string2.equals("edge_single")) {
                        sHasMetaEdgeSingle = 1;
                    }
                    if (sHasMetaEdgeSingle == 0) {
                        Intent intent = new Intent(CocktailBarManager.ACTION_COCKTAIL_UPDATE);
                        intent.setPackage(strCurrentOpPackageName);
                        intent.resolveTypeIfNeeded(ActivityThread.currentApplication().getContentResolver());
                        List list = packageManager.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(ActivityThread.currentApplication().getContentResolver()), 128L, UserHandle.myUserId()).getList();
                        int size = list == null ? 0 : list.size();
                        for (int i = 0; i < size; i++) {
                            ActivityInfo activityInfo = ((ResolveInfo) list.get(i)).activityInfo;
                            if ((activityInfo.applicationInfo.flags & 262144) == 0 && strCurrentOpPackageName.equals(activityInfo.packageName) && (bundle = activityInfo.metaData) != null && (string = bundle.getString("com.samsung.android.cocktail.mode", "")) != null && string.equals("edge_single")) {
                                sHasMetaEdgeSingle = 1;
                                return;
                            }
                        }
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
