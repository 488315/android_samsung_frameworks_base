package com.android.server.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class SeparatedAppsAnalytics {
  public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
  public final String TAG = "SeparatedAppsAnalytics";
  public final Context context;
  public final IKnoxAnalyticsContainer ifKnoxAnalyticsContainer;

  public SeparatedAppsAnalytics(IKnoxAnalyticsContainer iKnoxAnalyticsContainer, Context context) {
    this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainer;
    this.context = context;
  }

  /* JADX WARN: Type inference failed for: r3v4, types: [java.io.Serializable, java.lang.String[]] */
  public void logEvent(Bundle bundle, String str) {
    bundle.putLong("id", this.ifKnoxAnalyticsContainer.getSeparatedAppsContainerId());
    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_APP_SEPARATION", 1, str);
    for (String str2 : bundle.keySet()) {
      Object obj = bundle.get(str2);
      if (obj instanceof Integer) {
        knoxAnalyticsData.setProperty(str2, (Integer) obj);
      } else if (obj instanceof String) {
        knoxAnalyticsData.setProperty(str2, (String) obj);
      } else if (obj instanceof Long) {
        knoxAnalyticsData.setProperty(str2, (Long) obj);
      } else if (obj instanceof String[]) {
        knoxAnalyticsData.setProperty(str2, (String[]) obj);
      }
    }
    this.ifKnoxAnalyticsContainer.sendAnalyticsLog(knoxAnalyticsData);
    if (DEBUG) {
      Log.d("SeparatedAppsAnalytics", str + " / " + knoxAnalyticsData.toString());
    }
  }

  public void logEventActivationForAppSep(ArrayList arrayList, ArrayList arrayList2) {
    try {
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        Bundle bundle = new Bundle();
        bundle.putString(KnoxAnalyticsDataConverter.EVENT, "PACKAGE_INFO");
        bundle.putString("pN", str);
        bundle.putInt("add", 1);
        bundle.putInt("noIP", 0);
        bundle.putInt("noWP", arrayList2.size());
        logEvent(bundle, "PACKAGE_INFO");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onSeparatedAppsCreated() {
    Bundle separatedAppsConfig = this.ifKnoxAnalyticsContainer.getSeparatedAppsConfig();
    if (separatedAppsConfig == null) {
      return;
    }
    boolean z = separatedAppsConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
    String deviceOwnerPackage = this.ifKnoxAnalyticsContainer.getDeviceOwnerPackage();
    Bundle bundle = new Bundle();
    bundle.putString(KnoxAnalyticsDataConverter.EVENT, "APP_SEPARATION_CREATED");
    bundle.putInt("wP", z ? 1 : 0);
    bundle.putString("pN", deviceOwnerPackage);
    bundle.putString(
        "pV", this.ifKnoxAnalyticsContainer.getPackageInfo(deviceOwnerPackage, 0).versionName);
    logEvent(bundle, "APP_SEPARATION_CREATED");
  }

  public void onSeparatedAppsPolicyUpdated() {
    Bundle separatedAppsConfig = this.ifKnoxAnalyticsContainer.getSeparatedAppsConfig();
    if (separatedAppsConfig == null) {
      return;
    }
    boolean z = separatedAppsConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
    ArrayList<String> stringArrayList =
        separatedAppsConfig.getStringArrayList("APP_SEPARATION_APP_LIST");
    String str = "";
    if (stringArrayList != null) {
      String str2 = "";
      for (String str3 : stringArrayList) {
        str2 = str2.equals("") ? str3 : str3 + "," + str2;
      }
      str = str2;
    }
    Bundle bundle = new Bundle();
    bundle.putString(KnoxAnalyticsDataConverter.EVENT, "APP_SEPARATION_POLICYUPDATE");
    bundle.putInt("wP", z ? 1 : 0);
    bundle.putString("wLp", str);
    logEvent(bundle, "APP_SEPARATION_POLICYUPDATE");
  }

  public void logEventDeactivationForAppSep() {
    Bundle bundle = new Bundle();
    bundle.putString(KnoxAnalyticsDataConverter.EVENT, "APP_SEPARATION_REMOVED");
    bundle.putInt("rS", 0);
    logEvent(bundle, "APP_SEPARATION_REMOVED");
  }

  public void onAcitivtyChange(int i, String str) {
    Bundle bundle = new Bundle();
    bundle.putString(KnoxAnalyticsDataConverter.EVENT, "ACTIVITY_STAMP");
    bundle.putString("pN", str);
    bundle.putString("pV", this.ifKnoxAnalyticsContainer.getPackageInfo(str, i).versionName);
    logEvent(bundle, "ACTIVITY_STAMP");
  }

  public void onPackageChanged(int i, String str, int i2) {
    try {
      int i3 = 0;
      int i4 = 0;
      for (String str2 : this.ifKnoxAnalyticsContainer.getVisibleApps(i)) {
        IKnoxAnalyticsContainer iKnoxAnalyticsContainer = this.ifKnoxAnalyticsContainer;
        if (!iKnoxAnalyticsContainer.isSeparatedAppsIndepdentApp(
            iKnoxAnalyticsContainer.getPackageInfo(str2, i))) {
          i4++;
        }
      }
      ArrayList<String> stringArrayList =
          this.ifKnoxAnalyticsContainer
              .getSeparatedAppsConfig()
              .getStringArrayList("APP_SEPARATION_APP_LIST");
      Bundle bundle = new Bundle();
      bundle.putString(KnoxAnalyticsDataConverter.EVENT, "PACKAGE_INFO");
      bundle.putString("pN", str);
      bundle.putInt("add", i2);
      bundle.putInt("noIP", i4);
      if (stringArrayList != null) {
        i3 = stringArrayList.size();
      }
      bundle.putInt("noWP", i3);
      logEvent(bundle, "PACKAGE_INFO");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
