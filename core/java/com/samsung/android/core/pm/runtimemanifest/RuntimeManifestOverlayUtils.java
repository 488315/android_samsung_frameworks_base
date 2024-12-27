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

    public static <Component extends ParsedMainComponentImpl> void modifyMainComponent(Component component, RuntimeManifestPolicies.PolicyInfo policy) {
        if (policy.hasEnabled()) {
            component.setEnabled(policy.getEnabled());
        }
        modifyComponent(component, policy);
    }

    static <Component extends ParsedComponentImpl> void modifyComponent(Component component, RuntimeManifestPolicies.PolicyInfo policy) {
        if (policy.hasIcon()) {
            component.setIcon(policy.getIconRes());
        }
        if (policy.hasLabel()) {
            component.setLabelRes(policy.getLabelRes());
        }
        if (policy.hasCoercedLabel()) {
            component.setNonLocalizedLabel(policy.getCoercedLabel());
        }
    }

    public static void modifyPackage(ParsingPackage pkg, RuntimeManifestPolicies.PolicyInfo policy) {
        if (policy == null || pkg == null) {
            return;
        }
        if (policy.hasEnabled()) {
            pkg.setEnabled(policy.getEnabled());
        }
        if (policy.hasIcon()) {
            pkg.setIconResourceId(policy.getIconRes());
        }
        if (policy.hasLabel()) {
            pkg.setLabelResourceId(policy.getLabelRes());
        }
        if (policy.hasCoercedLabel()) {
            pkg.setNonLocalizedLabel(policy.getCoercedLabel());
        }
    }

    public static void applyRuntimeManifestIfNeeded(ParsingPackage pkg, Resources res) {
        try {
            XmlResourceParser parser = getRuntimeManifestOverlayParser(pkg, res);
            if (parser != null) {
                RuntimeManifestPolicies overlay = RuntimeManifestUtils.parseRuntimeManifestPolicies(parser, res);
                applyPackageRuntimeManifest(pkg, overlay.getApplicationPolicies());
                String packageName = pkg.getPackageName();
                applyComponentRuntimeManifest(packageName, pkg.getActivities(), overlay.getActivityPolicies());
                applyComponentRuntimeManifest(packageName, pkg.getServices(), overlay.getServicePolicies());
                applyComponentRuntimeManifest(packageName, pkg.getProviders(), overlay.getProviderPolicies());
                applyComponentRuntimeManifest(packageName, pkg.getReceivers(), overlay.getReceiverPolicies());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static XmlResourceParser getRuntimeManifestOverlayParser(ParsingPackage pkg, Resources res) {
        int resId;
        Bundle metaData = pkg.getMetaData();
        if (metaData != null && (resId = metaData.getInt(RuntimeManifestUtils.META_RUNTIME_MANIFEST)) != 0) {
            return res.getXml(resId);
        }
        return null;
    }

    public static <Component extends ParsedMainComponent> void applyComponentRuntimeManifest(String packageName, List<Component> components, Map<String, List<RuntimeManifestPolicies.PolicyInfo>> compPolicies) {
        if (components.size() == 0 || compPolicies.size() == 0) {
            return;
        }
        for (Map.Entry<String, List<RuntimeManifestPolicies.PolicyInfo>> entry : compPolicies.entrySet()) {
            String targetName = entry.getKey();
            List<RuntimeManifestPolicies.PolicyInfo> policies = entry.getValue();
            if (targetName != null && policies != null) {
                String targetName2 = RuntimeManifestUtils.buildClassName(packageName, targetName);
                ParsedMainComponent matchingComponent = getMatchingComponent(targetName2, components);
                if (matchingComponent == null) {
                    Slog.d(TAG, "Target " + targetName2 + " not found in manifest");
                } else {
                    RuntimeManifestPolicies.PolicyInfo matched = RuntimeManifestUtils.getMatchingPolicy(policies);
                    if (matched != null) {
                        modifyMainComponent((ParsedMainComponentImpl) matchingComponent, matched);
                    }
                }
            }
        }
    }

    public static <Component extends ParsedMainComponent> Component getMatchingComponent(String targetName, List<Component> components) {
        int size = ArrayUtils.size(components);
        for (int i = 0; i < size; i++) {
            Component comp = components.get(i);
            if (targetName.equals(comp.getName())) {
                return comp;
            }
        }
        return null;
    }

    public static void applyPackageRuntimeManifest(ParsingPackage pkg, List<RuntimeManifestPolicies.PolicyInfo> policies) {
        RuntimeManifestPolicies.PolicyInfo matched;
        if (pkg != null && policies != null && (matched = RuntimeManifestUtils.getMatchingPolicy(policies)) != null) {
            modifyPackage(pkg, matched);
        }
    }
}
