package com.samsung.android.core.p036pm.runtimemanifest;

import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ComponentInfo;
import android.content.p002pm.PackageParser;
import android.content.p002pm.ProviderInfo;
import android.content.p002pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.p009os.Bundle;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class RuntimeManifestCoreOverlayUtils {
  public static final boolean DEBUG = true;
  public static final String TAG = "RuntimeManifestUtils";

  public static <Component extends ComponentInfo> void modifyMainComponent(
      Component component, RuntimeManifestPolicies.PolicyInfo policy) {
    if (policy.hasEnabled()) {
      component.enabled = policy.getEnabled();
      Slog.m113d("RuntimeManifestUtils", "Set component enabled to " + policy.getEnabled());
    }
    modifyComponent(component, policy);
  }

  static <Component extends ComponentInfo> void modifyComponent(
      Component component, RuntimeManifestPolicies.PolicyInfo policy) {
    if (policy.hasIcon()) {
      component.icon = policy.getIconRes();
      Slog.m113d("RuntimeManifestUtils", "Set component icon to " + policy.getIconRes());
    }
    if (policy.hasLabel()) {
      component.labelRes = policy.getLabelRes();
      Slog.m113d("RuntimeManifestUtils", "Set component labelRes to " + policy.getLabelRes());
    }
    if (policy.hasCoercedLabel()) {
      component.nonLocalizedLabel = policy.getCoercedLabel();
      Slog.m113d(
          "RuntimeManifestUtils",
          "Set component nonLocalizedLabel to " + ((Object) policy.getCoercedLabel()));
    }
  }

  public static void modifyPackage(
      PackageParser.Package pkg, RuntimeManifestPolicies.PolicyInfo policy) {
    if (policy == null || pkg == null) {
      return;
    }
    if (policy.hasEnabled()) {
      pkg.applicationInfo.enabled = policy.getEnabled();
      Slog.m113d("RuntimeManifestUtils", "Set pkg.enabled to " + policy.getEnabled());
    }
    if (policy.hasIcon()) {
      Slog.m113d("RuntimeManifestUtils", "Set pkg.icon to " + policy.getIconRes());
      pkg.applicationInfo.iconRes = policy.getIconRes();
    }
    if (policy.hasLabel()) {
      pkg.applicationInfo.labelRes = policy.getLabelRes();
      Slog.m113d("RuntimeManifestUtils", "Set pkg.labelRes to " + policy.getLabelRes());
    }
    if (policy.hasCoercedLabel()) {
      pkg.applicationInfo.nonLocalizedLabel = policy.getCoercedLabel();
      Slog.m113d(
          "RuntimeManifestUtils",
          "Set pkg.nonLocalizedLabel to " + ((Object) policy.getCoercedLabel()));
    }
  }

  public static void applyRuntimeManifestIfNeeded(PackageParser.Package pkg, Resources res) {
    try {
      XmlResourceParser parser = getRuntimeManifestOverlayParser(pkg, res);
      if (parser != null) {
        RuntimeManifestPolicies overlay =
            RuntimeManifestUtils.parseRuntimeManifestPolicies(parser, res);
        applyPackageRuntimeManifest(pkg, overlay.getApplicationPolicies());
        String packageName = pkg.packageName;
        applyComponentRuntimeManifest(
            packageName, getActivityInfoList(pkg.activities), overlay.getActivityPolicies());
        applyComponentRuntimeManifest(
            packageName, getServiceInfoList(pkg.services), overlay.getServicePolicies());
        applyComponentRuntimeManifest(
            packageName, getProviderInfoList(pkg.providers), overlay.getProviderPolicies());
        applyComponentRuntimeManifest(
            packageName, getActivityInfoList(pkg.receivers), overlay.getReceiverPolicies());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static List<ServiceInfo> getServiceInfoList(List<PackageParser.Service> services) {
    final List<ServiceInfo> infoList = new ArrayList<>();
    if (services == null || services.size() == 0) {
      return infoList;
    }
    services.forEach(
        new Consumer() { // from class:
                         // com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            infoList.add(((PackageParser.Service) obj).info);
          }
        });
    return infoList;
  }

  private static List<ProviderInfo> getProviderInfoList(List<PackageParser.Provider> providers) {
    final List<ProviderInfo> infoList = new ArrayList<>();
    if (providers == null || providers.size() == 0) {
      return infoList;
    }
    providers.forEach(
        new Consumer() { // from class:
                         // com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            infoList.add(((PackageParser.Provider) obj).info);
          }
        });
    return infoList;
  }

  private static List<ActivityInfo> getActivityInfoList(List<PackageParser.Activity> activities) {
    final List<ActivityInfo> infoList = new ArrayList<>();
    if (activities == null || activities.size() == 0) {
      return infoList;
    }
    activities.forEach(
        new Consumer() { // from class:
                         // com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            infoList.add(((PackageParser.Activity) obj).info);
          }
        });
    return infoList;
  }

  public static XmlResourceParser getRuntimeManifestOverlayParser(
      PackageParser.Package pkg, Resources res) {
    int resId;
    Bundle metaData = pkg.mAppMetaData;
    if (metaData != null
        && (resId = metaData.getInt(RuntimeManifestUtils.META_RUNTIME_MANIFEST)) != 0) {
      return res.getXml(resId);
    }
    return null;
  }

  public static <Component extends ComponentInfo> void applyComponentRuntimeManifest(
      String packageName,
      List<Component> components,
      Map<String, List<RuntimeManifestPolicies.PolicyInfo>> compPolicies) {
    if (components.size() == 0 || compPolicies.size() == 0) {
      return;
    }
    for (Map.Entry<String, List<RuntimeManifestPolicies.PolicyInfo>> entry :
        compPolicies.entrySet()) {
      String targetName = entry.getKey();
      List<RuntimeManifestPolicies.PolicyInfo> policies = entry.getValue();
      if (targetName != null && policies != null) {
        String targetName2 = RuntimeManifestUtils.buildClassName(packageName, targetName);
        ComponentInfo matchingComponent = getMatchingComponent(targetName2, components);
        if (matchingComponent == null) {
          Slog.m113d("RuntimeManifestUtils", "Target " + targetName2 + " not found in manifest");
        } else {
          RuntimeManifestPolicies.PolicyInfo matched =
              RuntimeManifestUtils.getMatchingPolicy(policies);
          if (matched != null) {
            modifyMainComponent(matchingComponent, matched);
          }
        }
      }
    }
  }

  public static <Component extends ComponentInfo> Component getMatchingComponent(
      String targetName, List<Component> components) {
    int size = ArrayUtils.size(components);
    for (int i = 0; i < size; i++) {
      Component comp = components.get(i);
      if (targetName.equals(comp.name)) {
        return comp;
      }
    }
    return null;
  }

  public static void applyPackageRuntimeManifest(
      PackageParser.Package pkg, List<RuntimeManifestPolicies.PolicyInfo> policies) {
    RuntimeManifestPolicies.PolicyInfo matched;
    if (pkg != null
        && policies != null
        && (matched = RuntimeManifestUtils.getMatchingPolicy(policies)) != null) {
      modifyPackage(pkg, matched);
    }
  }
}
