package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.admin.IntentFilterPolicyKey;
import android.app.admin.PolicyKey;
import android.app.admin.flags.Flags;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.ProtectedPackages;
import com.android.server.pm.UserManagerInternal;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.Slogf;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final /* synthetic */ class PolicyEnforcerCallbacks$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(
            int i, int i2, Object obj, Object obj2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = i;
    }

    public /* synthetic */ PolicyEnforcerCallbacks$$ExternalSyntheticLambda0(
            Context context, int i, Set set) {
        this.$r8$classId = 1;
        this.f$1 = context;
        this.f$2 = i;
        this.f$0 = set;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                Boolean bool = (Boolean) this.f$0;
                Context context = (Context) this.f$1;
                int i = this.f$2;
                if (bool == null || !bool.booleanValue()) {
                    PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                            (PackageManagerService.PackageManagerInternalImpl)
                                    ((PackageManagerInternal)
                                            LocalServices.getService(PackageManagerInternal.class));
                    packageManagerInternalImpl.getClass();
                    if (Flags.crossUserSuspensionEnabledRo()) {
                        i = 0;
                    }
                    PackageManagerService packageManagerService =
                            packageManagerInternalImpl.mService;
                    packageManagerService.unsuspendForSuspendingPackage(
                            i, packageManagerService.snapshotComputer(), "android", false);
                    return;
                }
                String[] personalAppsForSuspension =
                        new PersonalAppsSuspensionHelper(
                                        context.createContextAsUser(UserHandle.of(i), 0))
                                .getPersonalAppsForSuspension();
                Slogf.i(
                        "PolicyEnforcerCallbacks",
                        "Suspending personal apps: %s",
                        String.join(",", personalAppsForSuspension));
                String[] packagesSuspendedByAdmin =
                        ((PackageManagerInternal)
                                        LocalServices.getService(PackageManagerInternal.class))
                                .setPackagesSuspendedByAdmin(i, true, personalAppsForSuspension);
                if (ArrayUtils.isEmpty(packagesSuspendedByAdmin)) {
                    return;
                }
                Slogf.wtf(
                        "PolicyEnforcerCallbacks",
                        "Failed to suspend apps: " + String.join(",", packagesSuspendedByAdmin));
                return;
            case 1:
                Context context2 = (Context) this.f$1;
                int i2 = this.f$2;
                Set<String> set = (Set) this.f$0;
                PackageManagerInternal packageManagerInternal =
                        (PackageManagerInternal)
                                LocalServices.getService(PackageManagerInternal.class);
                AppOpsManager appOpsManager =
                        (AppOpsManager) context2.getSystemService(AppOpsManager.class);
                List list = set == null ? null : set.stream().toList();
                PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl2 =
                        (PackageManagerService.PackageManagerInternalImpl) packageManagerInternal;
                ProtectedPackages protectedPackages = PackageManagerService.this.mProtectedPackages;
                synchronized (protectedPackages) {
                    try {
                        if (list == null) {
                            protectedPackages.mOwnerProtectedPackages.remove(i2);
                        } else {
                            protectedPackages.mOwnerProtectedPackages.put(i2, new ArraySet(list));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                UsageStatsService.this.mAppStandby.setAdminProtectedPackages(
                        set != null ? new ArraySet(set) : null, i2);
                if (set == null || set.isEmpty()) {
                    return;
                }
                Iterator it =
                        (i2 == -1
                                        ? ((UserManagerInternal)
                                                        LocalServices.getService(
                                                                UserManagerInternal.class))
                                                .getUsers(true).stream()
                                                        .map(
                                                                new PolicyEnforcerCallbacks$$ExternalSyntheticLambda11())
                                                        .toList()
                                        : List.of(Integer.valueOf(i2)))
                                .iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    if (Flags.disallowUserControlBgUsageFix()) {
                        Iterator it2 = set.iterator();
                        while (it2.hasNext()) {
                            ApplicationInfo applicationInfo =
                                    packageManagerInternal.getApplicationInfo(
                                            Process.myUid(), intValue, 786432, (String) it2.next());
                            if (applicationInfo != null) {
                                String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                                appOpsManager.setMode(
                                        70, applicationInfo.uid, applicationInfo.packageName, 0);
                                if (applicationInfo.targetSdkVersion < 26) {
                                    appOpsManager.setMode(
                                            63,
                                            applicationInfo.uid,
                                            applicationInfo.packageName,
                                            0);
                                }
                            }
                        }
                    }
                    if (Flags.disallowUserControlStoppedStateFix()) {
                        for (String str : set) {
                            PackageManagerService packageManagerService2 =
                                    packageManagerInternalImpl2.mService;
                            packageManagerService2.setPackageStoppedState(
                                    intValue,
                                    packageManagerService2.snapshotComputer(),
                                    str,
                                    false);
                        }
                    }
                }
                return;
            default:
                IntentFilterPolicyKey intentFilterPolicyKey = (PolicyKey) this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                int i3 = this.f$2;
                try {
                    if (!(intentFilterPolicyKey instanceof IntentFilterPolicyKey)) {
                        throw new IllegalArgumentException(
                                "policyKey is not of type IntentFilterPolicyKey, passed in"
                                    + " policyKey is: "
                                        + intentFilterPolicyKey);
                    }
                    IntentFilter intentFilter = intentFilterPolicyKey.getIntentFilter();
                    Objects.requireNonNull(intentFilter);
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    if (componentName != null) {
                        packageManager.addPersistentPreferredActivity(
                                intentFilter, componentName, i3);
                    } else {
                        packageManager.clearPersistentPreferredActivity(intentFilter, i3);
                    }
                    packageManager.flushPackageRestrictionsAsUser(i3);
                    return;
                } catch (RemoteException e) {
                    Slog.wtf(
                            "PolicyEnforcerCallbacks",
                            "Error adding/removing persistent preferred activity",
                            e);
                    return;
                }
        }
    }
}
