package com.android.server.companion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public abstract class PackageUtils {
  public static final Intent COMPANION_SERVICE_INTENT =
      new Intent("android.companion.CompanionDeviceService");

  public static PackageInfo getPackageInfo(Context context, final int i, final String str) {
    final PackageManager packageManager = context.getPackageManager();
    final PackageManager.PackageInfoFlags of = PackageManager.PackageInfoFlags.of(20480L);
    return (PackageInfo)
        Binder.withCleanCallingIdentity(
            new FunctionalUtils.ThrowingSupplier() { // from class:
              // com.android.server.companion.PackageUtils$$ExternalSyntheticLambda0
              public final Object getOrThrow() {
                PackageInfo lambda$getPackageInfo$0;
                lambda$getPackageInfo$0 =
                    PackageUtils.lambda$getPackageInfo$0(packageManager, str, of, i);
                return lambda$getPackageInfo$0;
              }
            });
  }

  public static /* synthetic */ PackageInfo lambda$getPackageInfo$0(
      PackageManager packageManager,
      String str,
      PackageManager.PackageInfoFlags packageInfoFlags,
      int i) {
    try {
      return packageManager.getPackageInfoAsUser(str, packageInfoFlags, i);
    } catch (PackageManager.NameNotFoundException e) {
      Slog.e(
          "PackageUtils_CompanionDeviceManagerService",
          "Failed to get PackageInfo for package " + str,
          e);
      return null;
    }
  }

  public static void enforceUsesCompanionDeviceFeature(Context context, int i, String str) {
    FeatureInfo[] featureInfoArr = getPackageInfo(context, i, str).reqFeatures;
    if (featureInfoArr != null) {
      for (FeatureInfo featureInfo : featureInfoArr) {
        if ("android.software.companion_device_setup".equals(featureInfo.name)) {
          return;
        }
      }
    }
    throw new IllegalStateException(
        "Must declare uses-feature android.software.companion_device_setup in manifest to use this"
            + " API");
  }

  public static Map getCompanionServicesForUser(Context context, int i) {
    PackageManager packageManager = context.getPackageManager();
    List queryIntentServicesAsUser =
        packageManager.queryIntentServicesAsUser(
            COMPANION_SERVICE_INTENT, PackageManager.ResolveInfoFlags.of(0L), i);
    HashMap hashMap = new HashMap(queryIntentServicesAsUser.size());
    Iterator it = queryIntentServicesAsUser.iterator();
    while (it.hasNext()) {
      ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
      if (!"android.permission.BIND_COMPANION_DEVICE_SERVICE".equals(serviceInfo.permission)) {
        Slog.w(
            "CDM_CompanionDeviceManagerService",
            "CompanionDeviceService "
                + serviceInfo.getComponentName().flattenToShortString()
                + " must require android.permission.BIND_COMPANION_DEVICE_SERVICE");
      } else {
        ArrayList arrayList =
            (ArrayList)
                hashMap.computeIfAbsent(
                    serviceInfo.packageName,
                    new Function() { // from class:
                      // com.android.server.companion.PackageUtils$$ExternalSyntheticLambda1
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        List lambda$getCompanionServicesForUser$1;
                        lambda$getCompanionServicesForUser$1 =
                            PackageUtils.lambda$getCompanionServicesForUser$1((String) obj);
                        return lambda$getCompanionServicesForUser$1;
                      }
                    });
        ComponentName componentName = serviceInfo.getComponentName();
        if (isPrimaryCompanionDeviceService(packageManager, componentName, i)) {
          arrayList.add(0, componentName);
        } else {
          arrayList.add(componentName);
        }
      }
    }
    return hashMap;
  }

  public static /* synthetic */ List lambda$getCompanionServicesForUser$1(String str) {
    return new ArrayList(1);
  }

  public static boolean isPrimaryCompanionDeviceService(
      PackageManager packageManager, ComponentName componentName, int i) {
    try {
      return packageManager
          .getPropertyAsUser(
              "android.companion.PROPERTY_PRIMARY_COMPANION_DEVICE_SERVICE",
              componentName.getPackageName(),
              componentName.getClassName(),
              i)
          .getBoolean();
    } catch (PackageManager.NameNotFoundException unused) {
      return false;
    }
  }
}
