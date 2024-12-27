package com.android.server.compat;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.compat.ChangeIdStateCache;
import android.app.compat.PackageOverride;
import android.compat.Compatibility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManagerInternal;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;

import com.android.internal.compat.AndroidBuildClassifier;
import com.android.internal.compat.ChangeReporter;
import com.android.internal.compat.CompatibilityChangeConfig;
import com.android.internal.compat.CompatibilityChangeInfo;
import com.android.internal.compat.CompatibilityOverrideConfig;
import com.android.internal.compat.CompatibilityOverridesByPackageConfig;
import com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig;
import com.android.internal.compat.CompatibilityOverridesToRemoveConfig;
import com.android.internal.compat.IOverrideValidator;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.util.DumpUtils;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;

public final class PlatformCompat extends IPlatformCompat.Stub {
    public final AndroidBuildClassifier mBuildClassifier;
    public final ChangeReporter mChangeReporter = new ChangeReporter(2);
    public final CompatConfig mCompatConfig;
    public final Context mContext;

    public PlatformCompat(Context context) {
        this.mContext = context;
        AndroidBuildClassifier androidBuildClassifier = new AndroidBuildClassifier();
        this.mBuildClassifier = androidBuildClassifier;
        CompatConfig compatConfig = new CompatConfig(androidBuildClassifier, context);
        compatConfig.initConfigFromLib(
                Environment.buildPath(
                        Environment.getRootDirectory(), new String[] {"etc", "compatconfig"}));
        compatConfig.initConfigFromLib(
                Environment.buildPath(
                        Environment.getRootDirectory(),
                        new String[] {"system_ext", "etc", "compatconfig"}));
        Iterator it = ApexManager.getInstance().getActiveApexInfos().iterator();
        while (it.hasNext()) {
            compatConfig.initConfigFromLib(
                    Environment.buildPath(
                            ((ApexManager.ActiveApexInfo) it.next()).apexDirectory,
                            new String[] {"etc", "compatconfig"}));
        }
        compatConfig.initOverrides(
                new File("/data/misc/appcompat", "compat_framework_overrides.xml"),
                new File("/product/etc/appcompat", "compat_framework_overrides.xml"));
        ChangeIdStateCache.invalidate();
        this.mCompatConfig = compatConfig;
    }

    public PlatformCompat(
            Context context,
            CompatConfig compatConfig,
            AndroidBuildClassifier androidBuildClassifier) {
        this.mContext = context;
        this.mCompatConfig = compatConfig;
        this.mBuildClassifier = androidBuildClassifier;
        registerPackageReceiver(context);
    }

    public static ApplicationInfo getApplicationInfo(String str, int i) {
        return ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                .getApplicationInfo(Process.myUid(), i, 0L, str);
    }

    public static void killPackage(String str) {
        int packageUid =
                ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                        .getPackageUid(str, 0L, UserHandle.myUserId());
        if (packageUid < 0) {
            PinnerService$$ExternalSyntheticOutline0.m(
                    "Didn't find package ", str, " on device.", "Compatibility");
            return;
        }
        Slog.d(
                "Compatibility",
                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                        packageUid, "Killing package ", str, " (UID ", ")."));
        int appId = UserHandle.getAppId(packageUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                service.killUid(appId, -1, "PlatformCompat overrides");
            }
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void checkAllCompatOverridesAreOverridable(Collection collection) {
        CompatChange compatChange;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Long l = (Long) it.next();
            l.longValue();
            if (this.mCompatConfig.mChanges.containsKey(l)
                    && ((compatChange = (CompatChange) this.mCompatConfig.mChanges.get(l)) == null
                            || !compatChange.getOverridable())) {
                throw new SecurityException(
                        "Only change ids marked as Overridable can be overridden.");
            }
        }
    }

    public final boolean clearOverride(long j, String str) {
        boolean removeOverrideUnsafe;
        clearOverride_enforcePermission();
        CompatConfig compatConfig = this.mCompatConfig;
        synchronized (compatConfig) {
            Long versionCodeOrNull = compatConfig.getVersionCodeOrNull(str);
            CompatChange compatChange = (CompatChange) compatConfig.mChanges.get(Long.valueOf(j));
            removeOverrideUnsafe =
                    compatChange != null
                            ? compatConfig.removeOverrideUnsafe(
                                    compatChange, str, versionCodeOrNull)
                            : false;
            if (removeOverrideUnsafe) {
                compatConfig.saveOverrides();
                ChangeIdStateCache.invalidate();
            }
        }
        killPackage(str);
        return removeOverrideUnsafe;
    }

    public final boolean clearOverrideForTest(long j, String str) {
        boolean removeOverrideUnsafe;
        clearOverrideForTest_enforcePermission();
        CompatConfig compatConfig = this.mCompatConfig;
        synchronized (compatConfig) {
            Long versionCodeOrNull = compatConfig.getVersionCodeOrNull(str);
            CompatChange compatChange = (CompatChange) compatConfig.mChanges.get(Long.valueOf(j));
            removeOverrideUnsafe =
                    compatChange != null
                            ? compatConfig.removeOverrideUnsafe(
                                    compatChange, str, versionCodeOrNull)
                            : false;
            if (removeOverrideUnsafe) {
                compatConfig.saveOverrides();
                ChangeIdStateCache.invalidate();
            }
        }
        return removeOverrideUnsafe;
    }

    public final void clearOverrides(String str) {
        clearOverrides_enforcePermission();
        this.mCompatConfig.removePackageOverrides(str);
        killPackage(str);
    }

    public final void clearOverridesForTest(String str) {
        clearOverridesForTest_enforcePermission();
        this.mCompatConfig.removePackageOverrides(str);
    }

    public final boolean containsOverride(long j, String str) {
        CompatChange compatChange;
        boolean containsKey;
        if (str == null
                || (compatChange = (CompatChange) this.mCompatConfig.mChanges.get(Long.valueOf(j)))
                        == null) {
            return false;
        }
        synchronized (compatChange) {
            containsKey = compatChange.mRawOverrides.containsKey(str);
        }
        return containsKey;
    }

    public final int disableTargetSdkChanges(String str, int i) {
        disableTargetSdkChanges_enforcePermission();
        CompatConfig compatConfig = this.mCompatConfig;
        long[] allowedChangesSinceTargetSdkForPackage =
                compatConfig.getAllowedChangesSinceTargetSdkForPackage(i, str);
        boolean z = false;
        for (long j : allowedChangesSinceTargetSdkForPackage) {
            z |=
                    compatConfig.addOverrideUnsafe(
                            j, str, new PackageOverride.Builder().setEnabled(false).build());
        }
        if (z) {
            compatConfig.saveOverrides();
            ChangeIdStateCache.invalidate();
        }
        int length = allowedChangesSinceTargetSdkForPackage.length;
        killPackage(str);
        return length;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpAndUsageStatsPermission(
                this.mContext, "platform_compat", printWriter)) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.READ_COMPAT_CHANGE_CONFIG", "Cannot read compat change");
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.LOG_COMPAT_CHANGE", "Cannot read log compat change usage");
            CompatConfig compatConfig = this.mCompatConfig;
            if (compatConfig.mChanges.size() == 0) {
                printWriter.println("No compat overrides.");
                return;
            }
            for (CompatChange compatChange : compatConfig.mChanges.values()) {
                if (compatChange.getId() != 349045028) {
                    printWriter.println(compatChange.toString());
                }
            }
        }
    }

    public final int enableTargetSdkChanges(String str, int i) {
        enableTargetSdkChanges_enforcePermission();
        CompatConfig compatConfig = this.mCompatConfig;
        long[] allowedChangesSinceTargetSdkForPackage =
                compatConfig.getAllowedChangesSinceTargetSdkForPackage(i, str);
        boolean z = false;
        for (long j : allowedChangesSinceTargetSdkForPackage) {
            z |=
                    compatConfig.addOverrideUnsafe(
                            j, str, new PackageOverride.Builder().setEnabled(true).build());
        }
        if (z) {
            compatConfig.saveOverrides();
            ChangeIdStateCache.invalidate();
        }
        int length = allowedChangesSinceTargetSdkForPackage.length;
        killPackage(str);
        return length;
    }

    public final CompatibilityChangeConfig getAppConfig(ApplicationInfo applicationInfo) {
        getAppConfig_enforcePermission();
        CompatConfig compatConfig = this.mCompatConfig;
        compatConfig.getClass();
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (CompatChange compatChange : compatConfig.mChanges.values()) {
            if (compatChange.isEnabled(applicationInfo, compatConfig.mAndroidBuildClassifier)) {
                hashSet.add(Long.valueOf(compatChange.getId()));
            } else {
                hashSet2.add(Long.valueOf(compatChange.getId()));
            }
        }
        return new CompatibilityChangeConfig(new Compatibility.ChangeConfig(hashSet, hashSet2));
    }

    public final IOverrideValidator getOverrideValidator() {
        return this.mCompatConfig.mOverrideValidator;
    }

    public final boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) {
        isChangeEnabled_enforcePermission();
        return isChangeEnabledInternal(j, applicationInfo);
    }

    public final boolean isChangeEnabledByPackageName(long j, String str, int i) {
        isChangeEnabledByPackageName_enforcePermission();
        ApplicationInfo applicationInfo = getApplicationInfo(str, i);
        return applicationInfo == null
                ? this.mCompatConfig.willChangeBeEnabled(j, str)
                : isChangeEnabledInternal(j, applicationInfo);
    }

    public final boolean isChangeEnabledByUid(long j, int i) {
        isChangeEnabledByUid_enforcePermission();
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        boolean z = true;
        if (packagesForUid == null || packagesForUid.length == 0) {
            CompatChange compatChange =
                    (CompatChange) this.mCompatConfig.mChanges.get(Long.valueOf(j));
            if (compatChange == null) {
                return true;
            }
            return true ^ compatChange.getDisabled();
        }
        int userId = UserHandle.getUserId(i);
        for (String str : packagesForUid) {
            z &= isChangeEnabledInternal(j, getApplicationInfo(str, userId));
        }
        return z;
    }

    public final boolean isChangeEnabledInternal(long j, ApplicationInfo applicationInfo) {
        boolean isEnabled;
        boolean z;
        CompatChange compatChange = (CompatChange) this.mCompatConfig.mChanges.get(Long.valueOf(j));
        CompatConfig compatConfig = this.mCompatConfig;
        boolean z2 = true;
        if (compatChange == null) {
            compatConfig.getClass();
            isEnabled = true;
        } else {
            isEnabled =
                    compatChange.isEnabled(applicationInfo, compatConfig.mAndroidBuildClassifier);
        }
        int i = isEnabled ? 1 : 2;
        if (applicationInfo != null) {
            CompatConfig compatConfig2 = this.mCompatConfig;
            int i2 = applicationInfo.targetSdkVersion;
            compatConfig2.getClass();
            int i3 = -1;
            if (compatChange != null && compatChange.getEnableSinceTargetSdk() != -1) {
                i3 = compatChange.getEnableSinceTargetSdk() - 1;
            }
            int i4 = i3 + 1;
            if (i4 <= 0) {
                z = false;
            } else {
                if (i4 != 10000 && i4 != i2) {
                    z2 = false;
                }
                z = z2;
            }
            this.mChangeReporter.reportChange(applicationInfo.uid, j, i, z);
        }
        return isEnabled;
    }

    public final boolean isChangeEnabledInternalNoLogging(long j, ApplicationInfo applicationInfo) {
        CompatConfig compatConfig = this.mCompatConfig;
        CompatChange compatChange = (CompatChange) compatConfig.mChanges.get(Long.valueOf(j));
        if (compatChange == null) {
            return true;
        }
        return compatChange.isEnabled(applicationInfo, compatConfig.mAndroidBuildClassifier);
    }

    public final CompatibilityChangeInfo[] listAllChanges() {
        listAllChanges_enforcePermission();
        CompatConfig compatConfig = this.mCompatConfig;
        CompatibilityChangeInfo[] compatibilityChangeInfoArr =
                new CompatibilityChangeInfo[compatConfig.mChanges.size()];
        Iterator it = compatConfig.mChanges.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            compatibilityChangeInfoArr[i] = new CompatibilityChangeInfo((CompatChange) it.next());
            i++;
        }
        return compatibilityChangeInfoArr;
    }

    public final CompatibilityChangeInfo[] listUIChanges() {
        return (CompatibilityChangeInfo[])
                Arrays.stream(listAllChanges())
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.server.compat.PlatformCompat$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        PlatformCompat platformCompat = PlatformCompat.this;
                                        CompatibilityChangeInfo compatibilityChangeInfo =
                                                (CompatibilityChangeInfo) obj;
                                        platformCompat.getClass();
                                        if (compatibilityChangeInfo.getLoggingOnly()
                                                || compatibilityChangeInfo.getId() == 149391281) {
                                            return false;
                                        }
                                        return compatibilityChangeInfo.getEnableSinceTargetSdk()
                                                        <= 0
                                                || (compatibilityChangeInfo
                                                                        .getEnableSinceTargetSdk()
                                                                >= 29
                                                        && compatibilityChangeInfo
                                                                        .getEnableSinceTargetSdk()
                                                                <= platformCompat.mBuildClassifier
                                                                        .platformTargetSdk());
                                    }
                                })
                        .toArray(new PlatformCompat$$ExternalSyntheticLambda1());
    }

    public final void putAllOverridesOnReleaseBuilds(
            CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) {
        putAllOverridesOnReleaseBuilds_enforcePermission();
        Iterator it =
                compatibilityOverridesByPackageConfig.packageNameToOverrides.values().iterator();
        while (it.hasNext()) {
            checkAllCompatOverridesAreOverridable(
                    ((CompatibilityOverrideConfig) it.next()).overrides.keySet());
        }
        CompatConfig compatConfig = this.mCompatConfig;
        synchronized (compatConfig) {
            try {
                for (String str :
                        compatibilityOverridesByPackageConfig.packageNameToOverrides.keySet()) {
                    compatConfig.addPackageOverridesWithoutSaving(
                            (CompatibilityOverrideConfig)
                                    compatibilityOverridesByPackageConfig.packageNameToOverrides
                                            .get(str),
                            str,
                            true);
                }
                compatConfig.saveOverrides();
                ChangeIdStateCache.invalidate();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void putOverridesOnReleaseBuilds(
            CompatibilityOverrideConfig compatibilityOverrideConfig, String str) {
        putOverridesOnReleaseBuilds_enforcePermission();
        checkAllCompatOverridesAreOverridable(compatibilityOverrideConfig.overrides.keySet());
        this.mCompatConfig.addPackageOverrides(compatibilityOverrideConfig, str, true);
    }

    public final void registerListener(final long j, CompatChange.ChangeListener changeListener) {
        final CompatConfig compatConfig = this.mCompatConfig;
        compatConfig.getClass();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        CompatChange compatChange =
                (CompatChange)
                        compatConfig.mChanges.computeIfAbsent(
                                Long.valueOf(j),
                                new Function() { // from class:
                                                 // com.android.server.compat.CompatConfig$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        CompatConfig compatConfig2 = CompatConfig.this;
                                        AtomicBoolean atomicBoolean2 = atomicBoolean;
                                        long j2 = j;
                                        compatConfig2.getClass();
                                        atomicBoolean2.set(false);
                                        ChangeIdStateCache.invalidate();
                                        return new CompatChange(j2);
                                    }
                                });
        synchronized (compatChange) {
            if (compatChange.mListener != null) {
                throw new IllegalStateException(
                        "Listener for change " + compatChange.toString() + " already registered.");
            }
            compatChange.mListener = changeListener;
        }
        atomicBoolean.get();
    }

    public final void registerPackageReceiver(Context context) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class: com.android.server.compat.PlatformCompat.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Uri data;
                        String schemeSpecificPart;
                        if (intent == null
                                || (data = intent.getData()) == null
                                || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                            return;
                        }
                        CompatConfig compatConfig = PlatformCompat.this.mCompatConfig;
                        Long versionCodeOrNull =
                                compatConfig.getVersionCodeOrNull(schemeSpecificPart);
                        boolean z = false;
                        for (CompatChange compatChange : compatConfig.mChanges.values()) {
                            z |=
                                    compatChange.recheckOverride(
                                            schemeSpecificPart,
                                            compatConfig.mOverrideValidator
                                                    .getOverrideAllowedStateInternal(
                                                            schemeSpecificPart,
                                                            compatChange.getId(),
                                                            true),
                                            versionCodeOrNull);
                        }
                        if (z) {
                            ChangeIdStateCache.invalidate();
                        }
                    }
                };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        context.registerReceiverForAllUsers(broadcastReceiver, intentFilter, null, null);
    }

    public final void removeAllOverridesOnReleaseBuilds(
            CompatibilityOverridesToRemoveByPackageConfig
                    compatibilityOverridesToRemoveByPackageConfig) {
        removeAllOverridesOnReleaseBuilds_enforcePermission();
        Iterator it =
                compatibilityOverridesToRemoveByPackageConfig
                        .packageNameToOverridesToRemove
                        .values()
                        .iterator();
        while (it.hasNext()) {
            checkAllCompatOverridesAreOverridable(
                    ((CompatibilityOverridesToRemoveConfig) it.next()).changeIds);
        }
        CompatConfig compatConfig = this.mCompatConfig;
        synchronized (compatConfig) {
            try {
                boolean z = false;
                for (String str :
                        compatibilityOverridesToRemoveByPackageConfig.packageNameToOverridesToRemove
                                .keySet()) {
                    z |=
                            compatConfig.removePackageOverridesWithoutSaving(
                                    (CompatibilityOverridesToRemoveConfig)
                                            compatibilityOverridesToRemoveByPackageConfig
                                                    .packageNameToOverridesToRemove.get(str),
                                    str);
                }
                if (z) {
                    compatConfig.saveOverrides();
                    ChangeIdStateCache.invalidate();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeOverridesOnReleaseBuilds(
            CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, String str) {
        removeOverridesOnReleaseBuilds_enforcePermission();
        checkAllCompatOverridesAreOverridable(compatibilityOverridesToRemoveConfig.changeIds);
        CompatConfig compatConfig = this.mCompatConfig;
        synchronized (compatConfig) {
            if (compatConfig.removePackageOverridesWithoutSaving(
                    compatibilityOverridesToRemoveConfig, str)) {
                compatConfig.saveOverrides();
                ChangeIdStateCache.invalidate();
            }
        }
    }

    public final void reportChange(long j, ApplicationInfo applicationInfo) {
        reportChange_enforcePermission();
        this.mChangeReporter.reportChange(applicationInfo.uid, j, 3, true);
    }

    public final void reportChangeByPackageName(long j, String str, int i) {
        reportChangeByPackageName_enforcePermission();
        ApplicationInfo applicationInfo = getApplicationInfo(str, i);
        if (applicationInfo != null) {
            this.mChangeReporter.reportChange(applicationInfo.uid, j, 3, true);
        }
    }

    public final void reportChangeByUid(long j, int i) {
        reportChangeByUid_enforcePermission();
        this.mChangeReporter.reportChange(i, j, 3, true);
    }

    public final void setOverrides(
            CompatibilityChangeConfig compatibilityChangeConfig, String str) {
        setOverrides_enforcePermission();
        HashMap hashMap = new HashMap();
        for (Long l : compatibilityChangeConfig.enabledChanges()) {
            l.getClass();
            hashMap.put(l, new PackageOverride.Builder().setEnabled(true).build());
        }
        for (Long l2 : compatibilityChangeConfig.disabledChanges()) {
            l2.getClass();
            hashMap.put(l2, new PackageOverride.Builder().setEnabled(false).build());
        }
        this.mCompatConfig.addPackageOverrides(
                new CompatibilityOverrideConfig(hashMap), str, false);
        killPackage(str);
    }

    public final void setOverridesForTest(
            CompatibilityChangeConfig compatibilityChangeConfig, String str) {
        setOverridesForTest_enforcePermission();
        HashMap hashMap = new HashMap();
        for (Long l : compatibilityChangeConfig.enabledChanges()) {
            l.longValue();
            hashMap.put(l, new PackageOverride.Builder().setEnabled(true).build());
        }
        for (Long l2 : compatibilityChangeConfig.disabledChanges()) {
            l2.longValue();
            hashMap.put(l2, new PackageOverride.Builder().setEnabled(false).build());
        }
        this.mCompatConfig.addPackageOverrides(
                new CompatibilityOverrideConfig(hashMap), str, false);
    }
}
