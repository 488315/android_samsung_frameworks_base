package com.android.server.cocktailbar.utils;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.util.Slog;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes.dex */
public abstract class CocktailBarUtils {
  public static final String TAG = "CocktailBarUtils";

  public static boolean isExistKioskContainers(Context context) {
    return SemPersonaManager.isKioskModeEnabled(context);
  }

  public abstract class CocktailBarWhiteList {
    public static boolean isCategoryOfWhiteList(int i) {
      return i == 32 || i == 128;
    }

    public static boolean isAllowedCocktailCategory(
        CocktailProviderInfo cocktailProviderInfo, int i) {
      if (isCategoryOfWhiteList(cocktailProviderInfo.category)) {
        return isSystemApplication(cocktailProviderInfo.provider.getPackageName(), i);
      }
      return true;
    }

    public static boolean isSystemApplication(String str, int i) {
      IPackageManager packageManager = AppGlobals.getPackageManager();
      if (packageManager == null) {
        Slog.d(CocktailBarUtils.TAG, "Can not get PM");
        return false;
      }
      try {
        PackageInfo packageInfo = packageManager.getPackageInfo(str, 64L, i);
        if (packageInfo != null && (packageInfo.applicationInfo.flags & 129) != 0) {
          return true;
        }
        StringBuffer stringBuffer = new StringBuffer("isSystemApplication: ");
        stringBuffer.append(str);
        if (packageInfo == null) {
          stringBuffer.append(" is no signature app");
        } else {
          stringBuffer.append(" f=");
          stringBuffer.append(packageInfo.applicationInfo.flags);
        }
        Slog.d(CocktailBarUtils.TAG, stringBuffer.toString());
        return false;
      } catch (RemoteException e) {
        e.printStackTrace();
        return false;
      }
    }
  }
}
