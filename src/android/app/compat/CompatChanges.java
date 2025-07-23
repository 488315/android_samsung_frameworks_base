package android.app.compat;

import android.annotation.SystemApi;
import android.compat.Compatibility;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.internal.compat.CompatibilityOverrideConfig;
import com.android.internal.compat.CompatibilityOverridesByPackageConfig;
import com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig;
import com.android.internal.compat.CompatibilityOverridesToRemoveConfig;
import java.util.Map;
import java.util.Set;

@SystemApi
/* loaded from: classes.dex */
public final class CompatChanges {
    private static final ChangeIdStateCache QUERY_CACHE = new ChangeIdStateCache();

    private CompatChanges() {
    }

    public static boolean isChangeEnabled(long j) {
        return Compatibility.isChangeEnabled(j);
    }

    public static boolean isChangeEnabled(long j, String str, UserHandle userHandle) {
        return QUERY_CACHE.query(ChangeIdStateQuery.byPackageName(j, str, userHandle.getIdentifier())).booleanValue();
    }

    public static boolean isChangeEnabled(long j, int i) {
        return QUERY_CACHE.query(ChangeIdStateQuery.byUid(j, i)).booleanValue();
    }

    public static void putAllPackageOverrides(Map<String, Map<Long, PackageOverride>> map) {
        ArrayMap arrayMap = new ArrayMap();
        for (String str : map.keySet()) {
            arrayMap.put(str, new CompatibilityOverrideConfig(map.get(str)));
        }
        try {
            QUERY_CACHE.getPlatformCompatService().putAllOverridesOnReleaseBuilds(new CompatibilityOverridesByPackageConfig(arrayMap));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static void putPackageOverrides(String str, Map<Long, PackageOverride> map) {
        try {
            QUERY_CACHE.getPlatformCompatService().putOverridesOnReleaseBuilds(new CompatibilityOverrideConfig(map), str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static void removeAllPackageOverrides(Map<String, Set<Long>> map) {
        ArrayMap arrayMap = new ArrayMap();
        for (String str : map.keySet()) {
            arrayMap.put(str, new CompatibilityOverridesToRemoveConfig(map.get(str)));
        }
        try {
            QUERY_CACHE.getPlatformCompatService().removeAllOverridesOnReleaseBuilds(new CompatibilityOverridesToRemoveByPackageConfig(arrayMap));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static void removePackageOverrides(String str, Set<Long> set) {
        try {
            QUERY_CACHE.getPlatformCompatService().removeOverridesOnReleaseBuilds(new CompatibilityOverridesToRemoveConfig(set), str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
