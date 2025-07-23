package com.samsung.android.core.pm.runtimemanifest;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Slog;
import com.android.internal.pm.pkg.component.ParsedComponentImpl;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedMainComponentImpl;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.util.ArrayUtils;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestPolicies;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class RuntimeManifestOverlayUtils {
    public static final boolean DEBUG = false;
    public static final String TAG = "RuntimeManifestOverlayUtils";

    public static <Component extends ParsedMainComponentImpl> void modifyMainComponent(Component component, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo.hasEnabled()) {
            component.setEnabled(policyInfo.getEnabled());
        }
        modifyComponent(component, policyInfo);
    }

    static <Component extends ParsedComponentImpl> void modifyComponent(Component component, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo.hasIcon()) {
            component.setIcon(policyInfo.getIconRes());
        }
        if (policyInfo.hasLabel()) {
            component.setLabelRes(policyInfo.getLabelRes());
        }
        if (policyInfo.hasCoercedLabel()) {
            component.setNonLocalizedLabel(policyInfo.getCoercedLabel());
        }
    }

    public static void modifyPackage(ParsingPackage parsingPackage, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo == null || parsingPackage == null) {
            return;
        }
        if (policyInfo.hasEnabled()) {
            parsingPackage.setEnabled(policyInfo.getEnabled());
        }
        if (policyInfo.hasIcon()) {
            parsingPackage.setIconResourceId(policyInfo.getIconRes());
        }
        if (policyInfo.hasLabel()) {
            parsingPackage.setLabelResourceId(policyInfo.getLabelRes());
        }
        if (policyInfo.hasCoercedLabel()) {
            parsingPackage.setNonLocalizedLabel(policyInfo.getCoercedLabel());
        }
    }

    public static void applyRuntimeManifestIfNeeded(ParsingPackage parsingPackage, Resources resources) {
        try {
            XmlResourceParser runtimeManifestOverlayParser = getRuntimeManifestOverlayParser(parsingPackage, resources);
            if (runtimeManifestOverlayParser != null) {
                RuntimeManifestPolicies parseRuntimeManifestPolicies = RuntimeManifestUtils.parseRuntimeManifestPolicies(runtimeManifestOverlayParser, resources);
                applyPackageRuntimeManifest(parsingPackage, parseRuntimeManifestPolicies.getApplicationPolicies());
                String packageName = parsingPackage.getPackageName();
                applyComponentRuntimeManifest(packageName, parsingPackage.getActivities(), parseRuntimeManifestPolicies.getActivityPolicies());
                applyComponentRuntimeManifest(packageName, parsingPackage.getServices(), parseRuntimeManifestPolicies.getServicePolicies());
                applyComponentRuntimeManifest(packageName, parsingPackage.getProviders(), parseRuntimeManifestPolicies.getProviderPolicies());
                applyComponentRuntimeManifest(packageName, parsingPackage.getReceivers(), parseRuntimeManifestPolicies.getReceiverPolicies());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static XmlResourceParser getRuntimeManifestOverlayParser(ParsingPackage parsingPackage, Resources resources) {
        int i;
        Bundle metaData = parsingPackage.getMetaData();
        if (metaData == null || (i = metaData.getInt(RuntimeManifestUtils.META_RUNTIME_MANIFEST)) == 0) {
            return null;
        }
        return resources.getXml(i);
    }

    public static <Component extends ParsedMainComponent> void applyComponentRuntimeManifest(String str, List<Component> list, Map<String, List<RuntimeManifestPolicies.PolicyInfo>> map) {
        if (list.size() == 0 || map.size() == 0) {
            return;
        }
        for (Map.Entry<String, List<RuntimeManifestPolicies.PolicyInfo>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<RuntimeManifestPolicies.PolicyInfo> value = entry.getValue();
            if (key != null && value != null) {
                String buildClassName = RuntimeManifestUtils.buildClassName(str, key);
                ParsedMainComponent matchingComponent = getMatchingComponent(buildClassName, list);
                if (matchingComponent == null) {
                    Slog.d(TAG, "Target " + buildClassName + " not found in manifest");
                } else {
                    RuntimeManifestPolicies.PolicyInfo matchingPolicy = RuntimeManifestUtils.getMatchingPolicy(value);
                    if (matchingPolicy != null) {
                        modifyMainComponent((ParsedMainComponentImpl) matchingComponent, matchingPolicy);
                    }
                }
            }
        }
    }

    public static <Component extends ParsedMainComponent> Component getMatchingComponent(String str, List<Component> list) {
        int size = ArrayUtils.size(list);
        for (int i = 0; i < size; i++) {
            Component component = list.get(i);
            if (str.equals(component.getName())) {
                return component;
            }
        }
        return null;
    }

    public static void applyPackageRuntimeManifest(ParsingPackage parsingPackage, List<RuntimeManifestPolicies.PolicyInfo> list) {
        RuntimeManifestPolicies.PolicyInfo matchingPolicy;
        if (parsingPackage == null || list == null || (matchingPolicy = RuntimeManifestUtils.getMatchingPolicy(list)) == null) {
            return;
        }
        modifyPackage(parsingPackage, matchingPolicy);
    }
}
