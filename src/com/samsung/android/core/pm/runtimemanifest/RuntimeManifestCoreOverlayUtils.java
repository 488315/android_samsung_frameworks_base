package com.samsung.android.core.pm.runtimemanifest;

import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageParser;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestPolicies;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public class RuntimeManifestCoreOverlayUtils {
    public static final boolean DEBUG = true;
    public static final String TAG = "RuntimeManifestUtils";

    public static <Component extends ComponentInfo> void modifyMainComponent(Component component, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo.hasEnabled()) {
            component.enabled = policyInfo.getEnabled();
            Slog.d("RuntimeManifestUtils", "Set component enabled to " + policyInfo.getEnabled());
        }
        modifyComponent(component, policyInfo);
    }

    static <Component extends ComponentInfo> void modifyComponent(Component component, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo.hasIcon()) {
            component.icon = policyInfo.getIconRes();
            Slog.d("RuntimeManifestUtils", "Set component icon to " + policyInfo.getIconRes());
        }
        if (policyInfo.hasLabel()) {
            component.labelRes = policyInfo.getLabelRes();
            Slog.d("RuntimeManifestUtils", "Set component labelRes to " + policyInfo.getLabelRes());
        }
        if (policyInfo.hasCoercedLabel()) {
            component.nonLocalizedLabel = policyInfo.getCoercedLabel();
            Slog.d("RuntimeManifestUtils", "Set component nonLocalizedLabel to " + ((Object) policyInfo.getCoercedLabel()));
        }
    }

    public static void modifyPackage(PackageParser.Package r3, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo == null || r3 == null) {
            return;
        }
        if (policyInfo.hasEnabled()) {
            r3.applicationInfo.enabled = policyInfo.getEnabled();
            Slog.d("RuntimeManifestUtils", "Set pkg.enabled to " + policyInfo.getEnabled());
        }
        if (policyInfo.hasIcon()) {
            Slog.d("RuntimeManifestUtils", "Set pkg.icon to " + policyInfo.getIconRes());
            r3.applicationInfo.iconRes = policyInfo.getIconRes();
        }
        if (policyInfo.hasLabel()) {
            r3.applicationInfo.labelRes = policyInfo.getLabelRes();
            Slog.d("RuntimeManifestUtils", "Set pkg.labelRes to " + policyInfo.getLabelRes());
        }
        if (policyInfo.hasCoercedLabel()) {
            r3.applicationInfo.nonLocalizedLabel = policyInfo.getCoercedLabel();
            Slog.d("RuntimeManifestUtils", "Set pkg.nonLocalizedLabel to " + ((Object) policyInfo.getCoercedLabel()));
        }
    }

    public static void applyRuntimeManifestIfNeeded(PackageParser.Package r3, Resources resources) {
        try {
            XmlResourceParser runtimeManifestOverlayParser = getRuntimeManifestOverlayParser(r3, resources);
            if (runtimeManifestOverlayParser != null) {
                RuntimeManifestPolicies runtimeManifestPolicies = RuntimeManifestUtils.parseRuntimeManifestPolicies(runtimeManifestOverlayParser, resources);
                applyPackageRuntimeManifest(r3, runtimeManifestPolicies.getApplicationPolicies());
                String str = r3.packageName;
                applyComponentRuntimeManifest(str, getActivityInfoList(r3.activities), runtimeManifestPolicies.getActivityPolicies());
                applyComponentRuntimeManifest(str, getServiceInfoList(r3.services), runtimeManifestPolicies.getServicePolicies());
                applyComponentRuntimeManifest(str, getProviderInfoList(r3.providers), runtimeManifestPolicies.getProviderPolicies());
                applyComponentRuntimeManifest(str, getActivityInfoList(r3.receivers), runtimeManifestPolicies.getReceiverPolicies());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<ServiceInfo> getServiceInfoList(List<PackageParser.Service> list) {
        final ArrayList arrayList = new ArrayList();
        if (list != null && list.size() != 0) {
            list.forEach(new Consumer() { // from class: com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(((PackageParser.Service) obj).info);
                }
            });
        }
        return arrayList;
    }

    private static List<ProviderInfo> getProviderInfoList(List<PackageParser.Provider> list) {
        final ArrayList arrayList = new ArrayList();
        if (list != null && list.size() != 0) {
            list.forEach(new Consumer() { // from class: com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(((PackageParser.Provider) obj).info);
                }
            });
        }
        return arrayList;
    }

    private static List<ActivityInfo> getActivityInfoList(List<PackageParser.Activity> list) {
        final ArrayList arrayList = new ArrayList();
        if (list != null && list.size() != 0) {
            list.forEach(new Consumer() { // from class: com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(((PackageParser.Activity) obj).info);
                }
            });
        }
        return arrayList;
    }

    public static XmlResourceParser getRuntimeManifestOverlayParser(PackageParser.Package r1, Resources resources) {
        int i;
        Bundle bundle = r1.mAppMetaData;
        if (bundle == null || (i = bundle.getInt(RuntimeManifestUtils.META_RUNTIME_MANIFEST)) == 0) {
            return null;
        }
        return resources.getXml(i);
    }

    public static <Component extends ComponentInfo> void applyComponentRuntimeManifest(String str, List<Component> list, Map<String, List<RuntimeManifestPolicies.PolicyInfo>> map) {
        if (list.size() == 0 || map.size() == 0) {
            return;
        }
        for (Map.Entry<String, List<RuntimeManifestPolicies.PolicyInfo>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<RuntimeManifestPolicies.PolicyInfo> value = entry.getValue();
            if (key != null && value != null) {
                String strBuildClassName = RuntimeManifestUtils.buildClassName(str, key);
                ComponentInfo matchingComponent = getMatchingComponent(strBuildClassName, list);
                if (matchingComponent == null) {
                    Slog.d("RuntimeManifestUtils", "Target " + strBuildClassName + " not found in manifest");
                } else {
                    RuntimeManifestPolicies.PolicyInfo matchingPolicy = RuntimeManifestUtils.getMatchingPolicy(value);
                    if (matchingPolicy != null) {
                        modifyMainComponent(matchingComponent, matchingPolicy);
                    }
                }
            }
        }
    }

    public static <Component extends ComponentInfo> Component getMatchingComponent(String str, List<Component> list) {
        int size = ArrayUtils.size(list);
        for (int i = 0; i < size; i++) {
            Component component = list.get(i);
            if (str.equals(component.name)) {
                return component;
            }
        }
        return null;
    }

    public static void applyPackageRuntimeManifest(PackageParser.Package r0, List<RuntimeManifestPolicies.PolicyInfo> list) {
        RuntimeManifestPolicies.PolicyInfo matchingPolicy;
        if (r0 == null || list == null || (matchingPolicy = RuntimeManifestUtils.getMatchingPolicy(list)) == null) {
            return;
        }
        modifyPackage(r0, matchingPolicy);
    }
}
