package com.samsung.android.server.pm.runtimemanifest;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.pkg.component.ParsedComponentImpl;
import com.android.server.pm.pkg.component.ParsedMainComponent;
import com.android.server.pm.pkg.component.ParsedMainComponentImpl;
import com.android.server.pm.pkg.parsing.ParsingPackage;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestPolicies;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class RuntimeManifestOverlayUtils {
    public static void modifyMainComponent(ParsedMainComponentImpl parsedMainComponentImpl, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo.hasEnabled()) {
            parsedMainComponentImpl.setEnabled(policyInfo.getEnabled());
        }
        modifyComponent(parsedMainComponentImpl, policyInfo);
    }

    public static void modifyComponent(ParsedComponentImpl parsedComponentImpl, RuntimeManifestPolicies.PolicyInfo policyInfo) {
        if (policyInfo.hasIcon()) {
            parsedComponentImpl.setIcon(policyInfo.getIconRes());
        }
        if (policyInfo.hasLabel()) {
            parsedComponentImpl.setLabelRes(policyInfo.getLabelRes());
        }
        if (policyInfo.hasCoercedLabel()) {
            parsedComponentImpl.setNonLocalizedLabel(policyInfo.getCoercedLabel());
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
        if (metaData == null || (i = metaData.getInt("runtime.manifest.overlay")) == 0) {
            return null;
        }
        return resources.getXml(i);
    }

    public static void applyComponentRuntimeManifest(String str, List list, Map map) {
        if (list.size() == 0 || map.size() == 0) {
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            String str2 = (String) entry.getKey();
            List list2 = (List) entry.getValue();
            if (str2 != null && list2 != null) {
                String buildClassName = RuntimeManifestUtils.buildClassName(str, str2);
                ParsedMainComponent matchingComponent = getMatchingComponent(buildClassName, list);
                if (matchingComponent == null) {
                    Slog.d("RuntimeManifestOverlayUtils", "Target " + buildClassName + " not found in manifest");
                } else {
                    RuntimeManifestPolicies.PolicyInfo matchingPolicy = RuntimeManifestUtils.getMatchingPolicy(list2);
                    if (matchingPolicy != null) {
                        modifyMainComponent((ParsedMainComponentImpl) matchingComponent, matchingPolicy);
                    }
                }
            }
        }
    }

    public static ParsedMainComponent getMatchingComponent(String str, List list) {
        int size = ArrayUtils.size(list);
        for (int i = 0; i < size; i++) {
            ParsedMainComponent parsedMainComponent = (ParsedMainComponent) list.get(i);
            if (str.equals(parsedMainComponent.getName())) {
                return parsedMainComponent;
            }
        }
        return null;
    }

    public static void applyPackageRuntimeManifest(ParsingPackage parsingPackage, List list) {
        RuntimeManifestPolicies.PolicyInfo matchingPolicy;
        if (parsingPackage == null || list == null || (matchingPolicy = RuntimeManifestUtils.getMatchingPolicy(list)) == null) {
            return;
        }
        modifyPackage(parsingPackage, matchingPolicy);
    }
}
