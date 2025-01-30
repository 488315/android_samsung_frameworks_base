package com.android.server.compat.overrides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.compat.CompatibilityOverrideConfig;
import com.android.internal.compat.CompatibilityOverridesByPackageConfig;
import com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig;
import com.android.internal.compat.CompatibilityOverridesToRemoveConfig;
import com.android.internal.compat.IPlatformCompat;
import com.android.server.SystemService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class AppCompatOverridesService {
    public static final List SUPPORTED_NAMESPACES = Arrays.asList("app_compat_overrides");
    public final Context mContext;
    public final List mDeviceConfigListeners;
    public final AppCompatOverridesParser mOverridesParser;
    public final PackageManager mPackageManager;
    public final PackageReceiver mPackageReceiver;
    public final IPlatformCompat mPlatformCompat;
    public final List mSupportedNamespaces;

    public AppCompatOverridesService(Context context) {
        this(context, IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat")), SUPPORTED_NAMESPACES);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AppCompatOverridesService(Context context, IPlatformCompat iPlatformCompat, List list) {
        this.mContext = context;
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        this.mPlatformCompat = iPlatformCompat;
        this.mSupportedNamespaces = list;
        this.mOverridesParser = new AppCompatOverridesParser(packageManager);
        Object[] objArr = 0;
        this.mPackageReceiver = new PackageReceiver(context);
        this.mDeviceConfigListeners = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mDeviceConfigListeners.add(new DeviceConfigListener(this.mContext, (String) it.next()));
        }
    }

    public void finalize() {
        unregisterDeviceConfigListeners();
        unregisterPackageReceiver();
    }

    public void registerDeviceConfigListeners() {
        Iterator it = this.mDeviceConfigListeners.iterator();
        while (it.hasNext()) {
            ((DeviceConfigListener) it.next()).register();
        }
    }

    public final void unregisterDeviceConfigListeners() {
        Iterator it = this.mDeviceConfigListeners.iterator();
        while (it.hasNext()) {
            ((DeviceConfigListener) it.next()).unregister();
        }
    }

    public void registerPackageReceiver() {
        this.mPackageReceiver.register();
    }

    public final void unregisterPackageReceiver() {
        this.mPackageReceiver.unregister();
    }

    public final void applyAllOverrides(String str, Set set, Map map) {
        applyOverrides(DeviceConfig.getProperties(str, new String[0]), set, map);
    }

    public final void applyOverrides(DeviceConfig.Properties properties, Set set, Map map) {
        ArraySet<String> arraySet = new ArraySet(properties.getKeyset());
        arraySet.remove("owned_change_ids");
        arraySet.remove("remove_overrides");
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        for (String str : arraySet) {
            Set set2 = (Set) map.getOrDefault(str, Collections.emptySet());
            Map emptyMap = Collections.emptyMap();
            Long versionCodeOrNull = getVersionCodeOrNull(str);
            if (versionCodeOrNull != null) {
                emptyMap = this.mOverridesParser.parsePackageOverrides(properties.getString(str, ""), str, versionCodeOrNull.longValue(), set2);
            }
            if (!emptyMap.isEmpty()) {
                arrayMap.put(str, new CompatibilityOverrideConfig(emptyMap));
            }
            ArraySet arraySet2 = new ArraySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Long l = (Long) it.next();
                if (!emptyMap.containsKey(l) && !set2.contains(l)) {
                    arraySet2.add(l);
                }
            }
            if (!arraySet2.isEmpty()) {
                arrayMap2.put(str, new CompatibilityOverridesToRemoveConfig(arraySet2));
            }
        }
        putAllPackageOverrides(arrayMap);
        removeAllPackageOverrides(arrayMap2);
    }

    public final void addAllPackageOverrides(String str) {
        Long versionCodeOrNull = getVersionCodeOrNull(str);
        if (versionCodeOrNull == null) {
            return;
        }
        for (String str2 : this.mSupportedNamespaces) {
            putPackageOverrides(str, this.mOverridesParser.parsePackageOverrides(DeviceConfig.getString(str2, str, ""), str, versionCodeOrNull.longValue(), (Set) getOverridesToRemove(str2, getOwnedChangeIds(str2)).getOrDefault(str, Collections.emptySet())));
        }
    }

    public final void removeAllPackageOverrides(String str) {
        for (String str2 : this.mSupportedNamespaces) {
            if (!DeviceConfig.getString(str2, str, "").isEmpty()) {
                removePackageOverrides(str, getOwnedChangeIds(str2));
            }
        }
    }

    public final void removeOverrides(Map map) {
        ArrayMap arrayMap = new ArrayMap();
        for (Map.Entry entry : map.entrySet()) {
            arrayMap.put((String) entry.getKey(), new CompatibilityOverridesToRemoveConfig((Set) entry.getValue()));
        }
        removeAllPackageOverrides(arrayMap);
    }

    public final Map getOverridesToRemove(String str, Set set) {
        return this.mOverridesParser.parseRemoveOverrides(DeviceConfig.getString(str, "remove_overrides", ""), set);
    }

    public static Set getOwnedChangeIds(String str) {
        return AppCompatOverridesParser.parseOwnedChangeIds(DeviceConfig.getString(str, "owned_change_ids", ""));
    }

    public final void putAllPackageOverrides(Map map) {
        if (map.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.putAllOverridesOnReleaseBuilds(new CompatibilityOverridesByPackageConfig(map));
        } catch (RemoteException e) {
            Slog.e("AppCompatOverridesService", "Failed to call IPlatformCompat#putAllOverridesOnReleaseBuilds", e);
        }
    }

    public final void putPackageOverrides(String str, Map map) {
        if (map.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.putOverridesOnReleaseBuilds(new CompatibilityOverrideConfig(map), str);
        } catch (RemoteException e) {
            Slog.e("AppCompatOverridesService", "Failed to call IPlatformCompat#putOverridesOnReleaseBuilds", e);
        }
    }

    public final void removeAllPackageOverrides(Map map) {
        if (map.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.removeAllOverridesOnReleaseBuilds(new CompatibilityOverridesToRemoveByPackageConfig(map));
        } catch (RemoteException e) {
            Slog.e("AppCompatOverridesService", "Failed to call IPlatformCompat#removeAllOverridesOnReleaseBuilds", e);
        }
    }

    public final void removePackageOverrides(String str, Set set) {
        if (set.isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.removeOverridesOnReleaseBuilds(new CompatibilityOverridesToRemoveConfig(set), str);
        } catch (RemoteException e) {
            Slog.e("AppCompatOverridesService", "Failed to call IPlatformCompat#removeOverridesOnReleaseBuilds", e);
        }
    }

    public final boolean isInstalledForAnyUser(String str) {
        return getVersionCodeOrNull(str) != null;
    }

    public final Long getVersionCodeOrNull(String str) {
        try {
            return Long.valueOf(this.mPackageManager.getApplicationInfo(str, 4194304).longVersionCode);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final class Lifecycle extends SystemService {
        public AppCompatOverridesService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            AppCompatOverridesService appCompatOverridesService = new AppCompatOverridesService(getContext());
            this.mService = appCompatOverridesService;
            appCompatOverridesService.registerDeviceConfigListeners();
            this.mService.registerPackageReceiver();
        }
    }

    public final class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public final Context mContext;
        public final String mNamespace;

        public DeviceConfigListener(Context context, String str) {
            this.mContext = context;
            this.mNamespace = str;
        }

        public final void register() {
            DeviceConfig.addOnPropertiesChangedListener(this.mNamespace, this.mContext.getMainExecutor(), this);
        }

        public final void unregister() {
            DeviceConfig.removeOnPropertiesChangedListener(this);
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            boolean contains = properties.getKeyset().contains("remove_overrides");
            boolean contains2 = properties.getKeyset().contains("owned_change_ids");
            Set ownedChangeIds = AppCompatOverridesService.getOwnedChangeIds(this.mNamespace);
            Map overridesToRemove = AppCompatOverridesService.this.getOverridesToRemove(this.mNamespace, ownedChangeIds);
            if (contains || contains2) {
                AppCompatOverridesService.this.removeOverrides(overridesToRemove);
            }
            if (contains) {
                AppCompatOverridesService.this.applyAllOverrides(this.mNamespace, ownedChangeIds, overridesToRemove);
            } else {
                AppCompatOverridesService.this.applyOverrides(properties, ownedChangeIds, overridesToRemove);
            }
        }
    }

    public final class PackageReceiver extends BroadcastReceiver {
        public final Context mContext;
        public final IntentFilter mIntentFilter;

        public PackageReceiver(Context context) {
            this.mContext = context;
            IntentFilter intentFilter = new IntentFilter();
            this.mIntentFilter = intentFilter;
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
        }

        public final void register() {
            this.mContext.registerReceiverForAllUsers(this, this.mIntentFilter, null, null);
        }

        public final void unregister() {
            this.mContext.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data = intent.getData();
            if (data == null) {
                Slog.w("AppCompatOverridesService", "Failed to get package name in package receiver");
            }
            String schemeSpecificPart = data.getSchemeSpecificPart();
            String action = intent.getAction();
            if (action == null) {
                Slog.w("AppCompatOverridesService", "Failed to get action in package receiver");
                return;
            }
            switch (action) {
                case "android.intent.action.PACKAGE_CHANGED":
                case "android.intent.action.PACKAGE_ADDED":
                    AppCompatOverridesService.this.addAllPackageOverrides(schemeSpecificPart);
                    break;
                case "android.intent.action.PACKAGE_REMOVED":
                    if (!AppCompatOverridesService.this.isInstalledForAnyUser(schemeSpecificPart)) {
                        AppCompatOverridesService.this.removeAllPackageOverrides(schemeSpecificPart);
                        break;
                    }
                    break;
                default:
                    Slog.w("AppCompatOverridesService", "Unsupported action in package receiver: " + action);
                    break;
            }
        }
    }
}
