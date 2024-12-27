package com.android.server.compat.overrides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.KeyValueListParser;
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
import java.util.regex.Pattern;

public final class AppCompatOverridesService {
    public static final List SUPPORTED_NAMESPACES = Arrays.asList("app_compat_overrides");
    public final Context mContext;
    public final List mDeviceConfigListeners;
    public final AppCompatOverridesParser mOverridesParser;
    public final PackageManager mPackageManager;
    public final PackageReceiver mPackageReceiver;
    public final IPlatformCompat mPlatformCompat;
    public final List mSupportedNamespaces;

    public final class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public final Context mContext;
        public final String mNamespace;

        public DeviceConfigListener(Context context, String str) {
            this.mContext = context;
            this.mNamespace = str;
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            boolean contains = properties.getKeyset().contains("remove_overrides");
            boolean contains2 = properties.getKeyset().contains("owned_change_ids");
            Set ownedChangeIds = AppCompatOverridesService.getOwnedChangeIds(this.mNamespace);
            Map overridesToRemove =
                    AppCompatOverridesService.this.getOverridesToRemove(
                            this.mNamespace, ownedChangeIds);
            if (contains || contains2) {
                AppCompatOverridesService appCompatOverridesService =
                        AppCompatOverridesService.this;
                appCompatOverridesService.getClass();
                ArrayMap arrayMap = new ArrayMap();
                for (Map.Entry entry : overridesToRemove.entrySet()) {
                    arrayMap.put(
                            (String) entry.getKey(),
                            new CompatibilityOverridesToRemoveConfig((Set) entry.getValue()));
                }
                appCompatOverridesService.removeAllPackageOverrides(arrayMap);
            }
            if (!contains) {
                AppCompatOverridesService.this.applyOverrides(
                        properties, ownedChangeIds, overridesToRemove);
                return;
            }
            AppCompatOverridesService appCompatOverridesService2 = AppCompatOverridesService.this;
            String str = this.mNamespace;
            appCompatOverridesService2.getClass();
            appCompatOverridesService2.applyOverrides(
                    DeviceConfig.getProperties(str, new String[0]),
                    ownedChangeIds,
                    overridesToRemove);
        }
    }

    public final class Lifecycle extends SystemService {
        public AppCompatOverridesService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            AppCompatOverridesService appCompatOverridesService =
                    new AppCompatOverridesService(
                            getContext(),
                            IPlatformCompat.Stub.asInterface(
                                    ServiceManager.getService("platform_compat")),
                            AppCompatOverridesService.SUPPORTED_NAMESPACES);
            this.mService = appCompatOverridesService;
            appCompatOverridesService.registerDeviceConfigListeners();
            this.mService.registerPackageReceiver();
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

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Uri data = intent.getData();
            if (data == null) {
                Slog.w(
                        "AppCompatOverridesService",
                        "Failed to get package name in package receiver");
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
                    AppCompatOverridesService appCompatOverridesService =
                            AppCompatOverridesService.this;
                    Long versionCodeOrNull =
                            appCompatOverridesService.getVersionCodeOrNull(schemeSpecificPart);
                    if (versionCodeOrNull != null) {
                        for (String str : appCompatOverridesService.mSupportedNamespaces) {
                            Set ownedChangeIds = AppCompatOverridesService.getOwnedChangeIds(str);
                            Map parsePackageOverrides =
                                    appCompatOverridesService.mOverridesParser
                                            .parsePackageOverrides(
                                                    DeviceConfig.getString(
                                                            str, schemeSpecificPart, ""),
                                                    schemeSpecificPart,
                                                    versionCodeOrNull.longValue(),
                                                    (Set)
                                                            appCompatOverridesService
                                                                    .getOverridesToRemove(
                                                                            str, ownedChangeIds)
                                                                    .getOrDefault(
                                                                            schemeSpecificPart,
                                                                            Collections
                                                                                    .emptySet()));
                            if (!parsePackageOverrides.isEmpty()) {
                                try {
                                    appCompatOverridesService.mPlatformCompat
                                            .putOverridesOnReleaseBuilds(
                                                    new CompatibilityOverrideConfig(
                                                            parsePackageOverrides),
                                                    schemeSpecificPart);
                                } catch (RemoteException e) {
                                    Slog.e(
                                            "AppCompatOverridesService",
                                            "Failed to call"
                                                + " IPlatformCompat#putOverridesOnReleaseBuilds",
                                            e);
                                }
                            }
                        }
                        break;
                    }
                    break;
                case "android.intent.action.PACKAGE_REMOVED":
                    if (AppCompatOverridesService.this.getVersionCodeOrNull(schemeSpecificPart)
                            == null) {
                        AppCompatOverridesService appCompatOverridesService2 =
                                AppCompatOverridesService.this;
                        for (String str2 : appCompatOverridesService2.mSupportedNamespaces) {
                            if (!DeviceConfig.getString(str2, schemeSpecificPart, "").isEmpty()) {
                                Set ownedChangeIds2 =
                                        AppCompatOverridesService.getOwnedChangeIds(str2);
                                if (!ownedChangeIds2.isEmpty()) {
                                    try {
                                        appCompatOverridesService2.mPlatformCompat
                                                .removeOverridesOnReleaseBuilds(
                                                        new CompatibilityOverridesToRemoveConfig(
                                                                ownedChangeIds2),
                                                        schemeSpecificPart);
                                    } catch (RemoteException e2) {
                                        Slog.e(
                                                "AppCompatOverridesService",
                                                "Failed to call"
                                                    + " IPlatformCompat#removeOverridesOnReleaseBuilds",
                                                e2);
                                    }
                                }
                            }
                        }
                        break;
                    }
                    break;
                default:
                    Slog.w(
                            "AppCompatOverridesService",
                            "Unsupported action in package receiver: ".concat(action));
                    break;
            }
        }
    }

    public AppCompatOverridesService(Context context, IPlatformCompat iPlatformCompat, List list) {
        this.mContext = context;
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        this.mPlatformCompat = iPlatformCompat;
        this.mSupportedNamespaces = list;
        this.mOverridesParser = new AppCompatOverridesParser(packageManager);
        this.mPackageReceiver = new PackageReceiver(context);
        this.mDeviceConfigListeners = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            ((ArrayList) this.mDeviceConfigListeners)
                    .add(new DeviceConfigListener(this.mContext, str));
        }
    }

    public static Set getOwnedChangeIds(String str) {
        String string = DeviceConfig.getString(str, "owned_change_ids", "");
        Pattern pattern = AppCompatOverridesParser.BOOLEAN_PATTERN;
        if (string.isEmpty()) {
            return Collections.emptySet();
        }
        ArraySet arraySet = new ArraySet();
        for (String str2 : string.split(",")) {
            try {
                arraySet.add(Long.valueOf(Long.parseLong(str2)));
            } catch (NumberFormatException e) {
                Slog.w(
                        "AppCompatOverridesParser",
                        "Invalid change ID in 'owned_change_ids' flag: " + str2,
                        e);
            }
        }
        return arraySet;
    }

    public final void applyOverrides(DeviceConfig.Properties properties, Set set, Map map) {
        ArraySet arraySet = new ArraySet(properties.getKeyset());
        arraySet.remove("owned_change_ids");
        arraySet.remove("remove_overrides");
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Set set2 = (Set) map.getOrDefault(str, Collections.emptySet());
            Map emptyMap = Collections.emptyMap();
            Long versionCodeOrNull = getVersionCodeOrNull(str);
            if (versionCodeOrNull != null) {
                emptyMap =
                        this.mOverridesParser.parsePackageOverrides(
                                properties.getString(str, ""),
                                str,
                                versionCodeOrNull.longValue(),
                                set2);
            }
            if (!emptyMap.isEmpty()) {
                arrayMap.put(str, new CompatibilityOverrideConfig(emptyMap));
            }
            ArraySet arraySet2 = new ArraySet();
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                Long l = (Long) it2.next();
                if (!emptyMap.containsKey(l) && !set2.contains(l)) {
                    arraySet2.add(l);
                }
            }
            if (!arraySet2.isEmpty()) {
                arrayMap2.put(str, new CompatibilityOverridesToRemoveConfig(arraySet2));
            }
        }
        if (!arrayMap.isEmpty()) {
            try {
                this.mPlatformCompat.putAllOverridesOnReleaseBuilds(
                        new CompatibilityOverridesByPackageConfig(arrayMap));
            } catch (RemoteException e) {
                Slog.e(
                        "AppCompatOverridesService",
                        "Failed to call IPlatformCompat#putAllOverridesOnReleaseBuilds",
                        e);
            }
        }
        removeAllPackageOverrides(arrayMap2);
    }

    public final void finalize() {
        Iterator it = ((ArrayList) this.mDeviceConfigListeners).iterator();
        while (it.hasNext()) {
            DeviceConfigListener deviceConfigListener = (DeviceConfigListener) it.next();
            deviceConfigListener.getClass();
            DeviceConfig.removeOnPropertiesChangedListener(deviceConfigListener);
        }
        PackageReceiver packageReceiver = this.mPackageReceiver;
        packageReceiver.mContext.unregisterReceiver(packageReceiver);
    }

    public final Map getOverridesToRemove(String str, Set set) {
        String string = DeviceConfig.getString(str, "remove_overrides", "");
        AppCompatOverridesParser appCompatOverridesParser = this.mOverridesParser;
        appCompatOverridesParser.getClass();
        if (string.isEmpty()) {
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap();
        if (!string.equals("*")) {
            KeyValueListParser keyValueListParser = new KeyValueListParser(',');
            try {
                keyValueListParser.setString(string);
                for (int i = 0; i < keyValueListParser.size(); i++) {
                    String keyAt = keyValueListParser.keyAt(i);
                    String string2 = keyValueListParser.getString(keyAt, "");
                    if (!string2.equals("*")) {
                        for (String str2 : string2.split(":")) {
                            try {
                                ((Set)
                                                arrayMap.computeIfAbsent(
                                                        keyAt,
                                                        new AppCompatOverridesParser$$ExternalSyntheticLambda0()))
                                        .add(Long.valueOf(Long.parseLong(str2)));
                            } catch (NumberFormatException e) {
                                Slog.w(
                                        "AppCompatOverridesParser",
                                        "Invalid change ID in 'remove_overrides' flag: " + str2,
                                        e);
                            }
                        }
                    } else if (set.isEmpty()) {
                        Slog.w(
                                "AppCompatOverridesParser",
                                "Wildcard can't be used in 'remove_overrides' flag with an empty"
                                    + " owned_change_ids' flag");
                    } else {
                        arrayMap.put(keyAt, set);
                    }
                }
            } catch (IllegalArgumentException e2) {
                Slog.w(
                        "AppCompatOverridesParser",
                        "Invalid format in 'remove_overrides' flag: ".concat(string),
                        e2);
                return Collections.emptyMap();
            }
        } else {
            if (set.isEmpty()) {
                Slog.w(
                        "AppCompatOverridesParser",
                        "Wildcard can't be used in 'remove_overrides' flag with an empty"
                            + " owned_change_ids' flag");
                return Collections.emptyMap();
            }
            Iterator<ApplicationInfo> it =
                    appCompatOverridesParser
                            .mPackageManager
                            .getInstalledApplications(4194304)
                            .iterator();
            while (it.hasNext()) {
                arrayMap.put(it.next().packageName, set);
            }
        }
        return arrayMap;
    }

    public final Long getVersionCodeOrNull(String str) {
        try {
            return Long.valueOf(
                    this.mPackageManager.getApplicationInfo(str, 4194304).longVersionCode);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public void registerDeviceConfigListeners() {
        Iterator it = ((ArrayList) this.mDeviceConfigListeners).iterator();
        while (it.hasNext()) {
            DeviceConfigListener deviceConfigListener = (DeviceConfigListener) it.next();
            DeviceConfig.addOnPropertiesChangedListener(
                    deviceConfigListener.mNamespace,
                    deviceConfigListener.mContext.getMainExecutor(),
                    deviceConfigListener);
        }
    }

    public void registerPackageReceiver() {
        PackageReceiver packageReceiver = this.mPackageReceiver;
        packageReceiver.mContext.registerReceiverForAllUsers(
                packageReceiver, packageReceiver.mIntentFilter, null, null);
    }

    public final void removeAllPackageOverrides(Map map) {
        if (((ArrayMap) map).isEmpty()) {
            return;
        }
        try {
            this.mPlatformCompat.removeAllOverridesOnReleaseBuilds(
                    new CompatibilityOverridesToRemoveByPackageConfig(map));
        } catch (RemoteException e) {
            Slog.e(
                    "AppCompatOverridesService",
                    "Failed to call IPlatformCompat#removeAllOverridesOnReleaseBuilds",
                    e);
        }
    }
}
