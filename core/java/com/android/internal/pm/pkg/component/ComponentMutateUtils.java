package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ComponentMutateUtils {
    public static void setMaxAspectRatio(ParsedActivity activity, int resizeMode, float maxAspectRatio) {
        ((ParsedActivityImpl) activity).setMaxAspectRatio(resizeMode, maxAspectRatio);
    }

    public static void setMinAspectRatio(ParsedActivity activity, int resizeMode, float minAspectRatio) {
        ((ParsedActivityImpl) activity).setMinAspectRatio(resizeMode, minAspectRatio);
    }

    public static void setSupportsSizeChanges(ParsedActivity activity, boolean supportsSizeChanges) {
        ((ParsedActivityImpl) activity).setSupportsSizeChanges(supportsSizeChanges);
    }

    public static void setResizeMode(ParsedActivity activity, int resizeMode) {
        ((ParsedActivityImpl) activity).setResizeMode(resizeMode);
    }

    public static void setExactFlags(ParsedComponent component, int exactFlags) {
        ((ParsedComponentImpl) component).setFlags(exactFlags);
    }

    public static void setEnabled(ParsedMainComponent component, boolean enabled) {
        ((ParsedMainComponentImpl) component).setEnabled(enabled);
    }

    public static void setPackageName(ParsedComponent component, String packageName) {
        ((ParsedComponentImpl) component).setPackageName(packageName);
    }

    public static void setDirectBootAware(ParsedMainComponent component, boolean directBootAware) {
        ((ParsedMainComponentImpl) component).setDirectBootAware(directBootAware);
    }

    public static void setExported(ParsedMainComponent component, boolean exported) {
        ((ParsedMainComponentImpl) component).setExported(exported);
    }

    public static void setAuthority(ParsedProvider provider, String authority) {
        ((ParsedProviderImpl) provider).setAuthority(authority);
    }

    public static void setSyncable(ParsedProvider provider, boolean syncable) {
        ((ParsedProviderImpl) provider).setSyncable(syncable);
    }

    public static void setProtectionLevel(ParsedPermission permission, int protectionLevel) {
        ((ParsedPermissionImpl) permission).setProtectionLevel(protectionLevel);
    }

    public static void setParsedPermissionGroup(ParsedPermission permission, ParsedPermissionGroup permissionGroup) {
        ((ParsedPermissionImpl) permission).setParsedPermissionGroup(permissionGroup);
    }

    public static void setPriority(ParsedPermissionGroup parsedPermissionGroup, int priority) {
        ((ParsedPermissionGroupImpl) parsedPermissionGroup).setPriority(priority);
    }

    public static void addStateFrom(ParsedProcess oldProcess, ParsedProcess newProcess) {
        ((ParsedProcessImpl) oldProcess).addStateFrom(newProcess);
    }
}
