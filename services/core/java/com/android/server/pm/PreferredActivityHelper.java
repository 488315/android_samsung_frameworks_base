package com.android.server.pm;

import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.net.NetworkPolicyManagerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.samsung.android.server.pm.PmLog;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PreferredActivityHelper {
    public final BroadcastHelper mBroadcastHelper;
    public final PackageManagerService mPm;

    public PreferredActivityHelper(PackageManagerService packageManagerService, BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        this.mBroadcastHelper = broadcastHelper;
    }

    public static boolean isHomeFilter(WatchedIntentFilter watchedIntentFilter) {
        return watchedIntentFilter.mFilter.hasAction("android.intent.action.MAIN") && watchedIntentFilter.mFilter.hasCategory("android.intent.category.HOME") && watchedIntentFilter.mFilter.hasCategory("android.intent.category.DEFAULT");
    }

    public static void restoreFromXml(TypedXmlPullParser typedXmlPullParser, int i, String str, PreferredActivityHelper$$ExternalSyntheticLambda1 preferredActivityHelper$$ExternalSyntheticLambda1) {
        int next;
        do {
            next = typedXmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2 && str.equals(typedXmlPullParser.getName())) {
            while (typedXmlPullParser.next() == 4) {
            }
            switch (preferredActivityHelper$$ExternalSyntheticLambda1.$r8$classId) {
                case 0:
                    PreferredActivityHelper preferredActivityHelper = preferredActivityHelper$$ExternalSyntheticLambda1.f$0;
                    preferredActivityHelper.getClass();
                    String readDefaultApps = Settings.readDefaultApps(typedXmlPullParser);
                    if (readDefaultApps != null) {
                        PackageSetting packageStateInternal = preferredActivityHelper.mPm.snapshotComputer().getPackageStateInternal(readDefaultApps);
                        if (packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                            preferredActivityHelper.mPm.mDefaultAppProvider.setDefaultBrowser(i, readDefaultApps);
                            return;
                        }
                        PackageManagerTracedLock packageManagerTracedLock = preferredActivityHelper.mPm.mLock;
                        boolean z = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock) {
                            try {
                                preferredActivityHelper.mPm.mSettings.mPendingDefaultBrowser.put(i, readDefaultApps);
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                default:
                    PreferredActivityHelper preferredActivityHelper2 = preferredActivityHelper$$ExternalSyntheticLambda1.f$0;
                    PackageManagerTracedLock packageManagerTracedLock2 = preferredActivityHelper2.mPm.mLock;
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    synchronized (packageManagerTracedLock2) {
                        try {
                            preferredActivityHelper2.mPm.mSettings.readPreferredActivitiesLPw(i, typedXmlPullParser);
                        } finally {
                        }
                    }
                    preferredActivityHelper2.updateDefaultHomeNotLocked(preferredActivityHelper2.mPm.snapshotComputer(), i);
                    return;
            }
        }
    }

    public final void addPreferredActivity(Computer computer, WatchedIntentFilter watchedIntentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, boolean z, int i2, String str, boolean z2) {
        int callingUid = Binder.getCallingUid();
        computer.enforceCrossUserPermission("add preferred activity", callingUid, i2, true, false);
        if (this.mPm.mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") != 0) {
            if (computer.getUidTargetSdkVersion(callingUid) < 8) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(callingUid, "Ignoring addPreferredActivity() from uid ", "PackageManager");
                return;
            }
            this.mPm.mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
        }
        if (watchedIntentFilter.mFilter.countActions() == 0) {
            Slog.w("PackageManager", "Cannot set a preferred activity with no filter actions");
            return;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                PreferredIntentResolver editPreferredActivitiesLPw = this.mPm.mSettings.editPreferredActivitiesLPw(i2);
                ArrayList findFilters = editPreferredActivitiesLPw.findFilters(watchedIntentFilter);
                if (z2 && findFilters != null) {
                    PmLog.logDebugInfoAndLogcat("Removing prefs while adding by removeExisting");
                    Settings.removeFilters(editPreferredActivitiesLPw, findFilters);
                }
                PreferredActivityLog.logPreferenceChange(new PreferredActivity(watchedIntentFilter, i, componentNameArr, componentName, z), str);
                editPreferredActivitiesLPw.addFilter((PackageDataSnapshot) this.mPm.snapshotComputer(), (WatchedIntentFilter) new PreferredActivity(watchedIntentFilter, i, componentNameArr, componentName, z));
                this.mPm.scheduleWritePackageRestrictions(i2);
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        if (isHomeFilter(watchedIntentFilter) && updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), i2)) {
            return;
        }
        this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i2);
    }

    public final void clearPackagePreferredActivities(int i, String str) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.clearPackagePreferredActivitiesLPw(str, sparseBooleanArray, i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        if (sparseBooleanArray.size() > 0) {
            updateDefaultHomeNotLocked(this.mPm.snapshotComputer(), sparseBooleanArray);
            this.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
            this.mPm.scheduleWritePackageRestrictions(i);
        }
    }

    public final ResolveInfo findPreferredActivityNotLocked(Intent intent, String str, long j, List list, boolean z, boolean z2, int i) {
        return findPreferredActivityNotLocked(intent, str, j, list, z, z2, false, i, UserHandle.getAppId(Binder.getCallingUid()) >= 10000);
    }

    public final ResolveInfo findPreferredActivityNotLocked(Intent intent, String str, long j, List list, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal2;
        if (Thread.holdsLock(this.mPm.mLock)) {
            Slog.wtf("PackageManager", "Calling thread " + Thread.currentThread().getName() + " is holding mLock", new Throwable());
        }
        if (!this.mPm.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        boolean z5 = Settings.Global.getInt(this.mPm.mContext.getContentResolver(), "device_provisioned", 0) == 1;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                findPreferredActivityInternal2 = this.mPm.snapshotComputer().findPreferredActivityInternal2(intent, str, j, list, z, z2, z3, i, z4, z5);
            } catch (Throwable th) {
                boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        if (findPreferredActivityInternal2.mChanged) {
            this.mPm.scheduleWritePackageRestrictions(i);
        }
        if (z3 && findPreferredActivityInternal2.mPreferredResolveInfo == null) {
            Slog.v("PackageManager", "No preferred activity to return");
        }
        return findPreferredActivityInternal2.mPreferredResolveInfo;
    }

    /* JADX WARN: Code restructure failed: missing block: B:83:0x0146, code lost:
    
        com.samsung.android.server.pm.PmLog.logDebugInfoAndLogcat("Removing prefs while replacing");
        com.android.server.pm.Settings.removeFilters(r4, r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void replacePreferredActivity(com.android.server.pm.Computer r18, com.android.server.pm.WatchedIntentFilter r19, int r20, android.content.ComponentName[] r21, android.content.ComponentName r22, int r23) {
        /*
            Method dump skipped, instructions count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PreferredActivityHelper.replacePreferredActivity(com.android.server.pm.Computer, com.android.server.pm.WatchedIntentFilter, int, android.content.ComponentName[], android.content.ComponentName, int):void");
    }

    public final void resetNetworkPolicies(int i) {
        NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl networkPolicyManagerInternalImpl = (NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl) this.mPm.mInjector.mGetLocalServiceProducer.produce(NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl.class);
        synchronized (NetworkPolicyManagerService.this.mUidRulesFirstLock) {
            boolean removeUserStateUL = NetworkPolicyManagerService.this.removeUserStateUL(i, false, true);
            if (NetworkPolicyManagerService.this.addDefaultRestrictBackgroundAllowlistUidsUL(i) || removeUserStateUL) {
                synchronized (NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                    NetworkPolicyManagerService.this.writePolicyAL();
                }
            }
        }
    }

    public final void updateDefaultHomeNotLocked(Computer computer, SparseBooleanArray sparseBooleanArray) {
        if (Thread.holdsLock(this.mPm.mLock)) {
            Slog.wtf("PackageManager", "Calling thread " + Thread.currentThread().getName() + " is holding mLock", new Throwable());
        }
        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
            updateDefaultHomeNotLocked(computer, sparseBooleanArray.keyAt(size));
        }
    }

    public final boolean updateDefaultHomeNotLocked(Computer computer, final int i) {
        ActivityInfo activityInfo;
        if (Thread.holdsLock(this.mPm.mLock)) {
            Slog.wtf("PackageManager", "Calling thread " + Thread.currentThread().getName() + " is holding mLock", new Throwable());
        }
        if (!this.mPm.mSystemReady) {
            return false;
        }
        Intent homeIntent = computer.getHomeIntent();
        ResolveInfo findPreferredActivityNotLocked = findPreferredActivityNotLocked(homeIntent, null, 0L, computer.queryIntentActivitiesInternal(homeIntent, null, 786432L, i), true, false, i);
        String str = (findPreferredActivityNotLocked == null || (activityInfo = findPreferredActivityNotLocked.activityInfo) == null) ? null : activityInfo.packageName;
        if (TextUtils.equals(this.mPm.mDefaultAppProvider.getDefaultHome(i), str)) {
            return false;
        }
        String[] packagesForUid = computer.getPackagesForUid(Binder.getCallingUid());
        if ((packagesForUid != null && ArrayUtils.contains(packagesForUid, this.mPm.mRequiredPermissionControllerPackage)) || str == null) {
            return false;
        }
        PackageManagerService packageManagerService = this.mPm;
        Consumer consumer = new Consumer() { // from class: com.android.server.pm.PreferredActivityHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PreferredActivityHelper preferredActivityHelper = PreferredActivityHelper.this;
                int i2 = i;
                preferredActivityHelper.getClass();
                if (((Boolean) obj).booleanValue()) {
                    preferredActivityHelper.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i2);
                }
            }
        };
        Executor mainExecutor = packageManagerService.mContext.getMainExecutor();
        RoleManager roleManager = (RoleManager) packageManagerService.mDefaultAppProvider.mRoleManagerSupplier.get();
        if (roleManager == null) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            roleManager.addRoleHolderAsUser("android.app.role.HOME", str, 0, UserHandle.of(i), mainExecutor, consumer);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
