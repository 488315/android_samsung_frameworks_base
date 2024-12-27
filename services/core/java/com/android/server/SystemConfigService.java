package com.android.server;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.ISystemConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemConfigService extends SystemService {
    public final Context mContext;
    public final AnonymousClass1 mInterface;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.SystemConfigService$1] */
    public SystemConfigService(Context context) {
        super(context);
        this.mInterface =
                new ISystemConfig.Stub() { // from class: com.android.server.SystemConfigService.1
                    public final List getDefaultVrComponents() {
                        SystemConfigService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.QUERY_ALL_PACKAGES",
                                        "Caller must hold android.permission.QUERY_ALL_PACKAGES");
                        return new ArrayList(SystemConfig.getInstance().mDefaultVrComponents);
                    }

                    public final List getDisabledUntilUsedPreinstalledCarrierApps() {
                        SystemConfigService.this.mContext.enforceCallingOrSelfPermission(
                                "android.permission.READ_CARRIER_APP_INFO",
                                "getDisabledUntilUsedPreInstalledCarrierApps requires"
                                    + " READ_CARRIER_APP_INFO");
                        return new ArrayList(
                                SystemConfig.getInstance()
                                        .mDisabledUntilUsedPreinstalledCarrierApps);
                    }

                    public final Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() {
                        SystemConfigService.this.mContext.enforceCallingOrSelfPermission(
                                "android.permission.READ_CARRIER_APP_INFO",
                                "getDisabledUntilUsedPreInstalledCarrierAssociatedAppEntries"
                                    + " requires READ_CARRIER_APP_INFO");
                        return SystemConfig.getInstance()
                                .mDisabledUntilUsedPreinstalledCarrierAssociatedApps;
                    }

                    public final Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
                        SystemConfigService.this.mContext.enforceCallingOrSelfPermission(
                                "android.permission.READ_CARRIER_APP_INFO",
                                "getDisabledUntilUsedPreInstalledCarrierAssociatedApps requires"
                                    + " READ_CARRIER_APP_INFO");
                        return (Map)
                                SystemConfig.getInstance()
                                        .mDisabledUntilUsedPreinstalledCarrierAssociatedApps
                                        .entrySet()
                                        .stream()
                                        .collect(
                                                Collectors.toMap(
                                                        new SystemConfigService$1$$ExternalSyntheticLambda0(
                                                                1),
                                                        new SystemConfigService$1$$ExternalSyntheticLambda0(
                                                                2)));
                    }

                    public final List getEnabledComponentOverrides(String str) {
                        ArrayMap arrayMap =
                                (ArrayMap)
                                        SystemConfig.getInstance()
                                                .mPackageComponentEnabledState
                                                .get(str);
                        ArrayList arrayList = new ArrayList();
                        if (arrayMap != null) {
                            for (Map.Entry entry : arrayMap.entrySet()) {
                                if (Boolean.TRUE.equals(entry.getValue())) {
                                    arrayList.add(new ComponentName(str, (String) entry.getKey()));
                                }
                            }
                        }
                        return arrayList;
                    }

                    public final List getEnhancedConfirmationTrustedInstallers() {
                        SystemConfigService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES",
                                        "Caller must hold"
                                            + " android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES");
                        return SystemConfig.getInstance()
                                .mEnhancedConfirmationTrustedInstallers
                                .stream()
                                .map(new SystemConfigService$1$$ExternalSyntheticLambda0(0))
                                .toList();
                    }

                    public final List getEnhancedConfirmationTrustedPackages() {
                        SystemConfigService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES",
                                        "Caller must hold"
                                            + " android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES");
                        return SystemConfig.getInstance()
                                .mEnhancedConfirmationTrustedPackages
                                .stream()
                                .map(new SystemConfigService$1$$ExternalSyntheticLambda0(0))
                                .toList();
                    }

                    public final List getPreventUserDisablePackages() {
                        final PackageManagerInternal packageManagerInternal =
                                (PackageManagerInternal)
                                        LocalServices.getService(PackageManagerInternal.class);
                        return (List)
                                SystemConfig.getInstance().mPreventUserDisablePackages.stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                    // com.android.server.SystemConfigService$1$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        return PackageManagerInternal.this
                                                                .canQueryPackage(
                                                                        Binder.getCallingUid(),
                                                                        (String) obj);
                                                    }
                                                })
                                        .collect(Collectors.toList());
                    }

                    public final int[] getSystemPermissionUids(String str) {
                        SystemConfigService.this.mContext.enforceCallingOrSelfPermission(
                                "android.permission.GET_RUNTIME_PERMISSIONS",
                                "getSystemPermissionUids requires GET_RUNTIME_PERMISSIONS");
                        ArrayList arrayList = new ArrayList();
                        SparseArray sparseArray = SystemConfig.getInstance().mSystemPermissions;
                        for (int i = 0; i < sparseArray.size(); i++) {
                            ArraySet arraySet = (ArraySet) sparseArray.valueAt(i);
                            if (arraySet != null && arraySet.contains(str)) {
                                arrayList.add(Integer.valueOf(sparseArray.keyAt(i)));
                            }
                        }
                        return ArrayUtils.convertToIntArray(arrayList);
                    }
                };
        this.mContext = context;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("system_config", this.mInterface);
    }
}
