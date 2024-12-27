package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.BroadcastOptions;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.content.pm.UserPackage;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Pair;
import android.util.SparseArray;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.util.ArrayUtils;
import com.android.server.LocalServices;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateInternal;

import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.monetization.MonetizationUtils;

import java.util.ArrayList;
import java.util.function.BiFunction;

public final class BroadcastHelper {
    public static final String[] INSTANT_APP_BROADCAST_PERMISSION = {
        "android.permission.ACCESS_INSTANT_APPS"
    };
    public static final String vzwCarrierId = SystemProperties.get("ro.boot.carrierid");
    public static final String vzwSalesCode = SystemProperties.get("ro.csc.sales_code");
    public final ActivityManagerInternal mAmInternal;
    public final AppsFilterImpl mAppsFilter;
    public final Context mContext;
    public final Handler mHandler;
    public final MonetizationUtils mMonetizationUtils;
    public final PackageMonitorCallbackHelper mPackageMonitorCallbackHelper;
    public final UserManagerService.LocalService mUmInternal;

    public BroadcastHelper(PackageManagerServiceInjector packageManagerServiceInjector) {
        this.mUmInternal = packageManagerServiceInjector.getUserManagerService().mLocalService;
        this.mAmInternal =
                (ActivityManagerInternal)
                        packageManagerServiceInjector.mGetLocalServiceProducer.produce(
                                ActivityManagerInternal.class);
        Context context = packageManagerServiceInjector.mContext;
        this.mContext = context;
        this.mHandler = packageManagerServiceInjector.getHandler();
        this.mPackageMonitorCallbackHelper =
                (PackageMonitorCallbackHelper)
                        packageManagerServiceInjector.mPackageMonitorCallbackHelper.get(
                                packageManagerServiceInjector.mPackageManager,
                                packageManagerServiceInjector);
        this.mAppsFilter =
                (AppsFilterImpl)
                        packageManagerServiceInjector.mAppsFilterProducer.get(
                                packageManagerServiceInjector.mPackageManager,
                                packageManagerServiceInjector);
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            this.mMonetizationUtils = MonetizationUtils.getInstance(context);
        }
    }

    public static Bundle filterExtrasChangedPackageList(Computer computer, int i, Bundle bundle) {
        if (UserHandle.isCore(i)) {
            return bundle;
        }
        String[] stringArray = bundle.getStringArray("android.intent.extra.changed_package_list");
        if (ArrayUtils.isEmpty(stringArray)) {
            return bundle;
        }
        int i2 = bundle.getInt("android.intent.extra.user_handle", UserHandle.getUserId(i));
        int[] intArray = bundle.getIntArray("android.intent.extra.changed_uid_list");
        int length = stringArray.length;
        int length2 = !ArrayUtils.isEmpty(intArray) ? intArray.length : 0;
        ArrayList arrayList = new ArrayList(length);
        IntArray intArray2 = length2 > 0 ? new IntArray(length2) : null;
        for (int i3 = 0; i3 < length; i3++) {
            String str = stringArray[i3];
            if (!computer.shouldFilterApplication(computer.getPackageStateInternal(str), i, i2)) {
                arrayList.add(str);
                if (intArray2 != null && i3 < length2) {
                    intArray2.add(intArray[i3]);
                }
            }
        }
        Pair pair =
                new Pair(
                        arrayList.size() > 0
                                ? (String[]) arrayList.toArray(new String[arrayList.size()])
                                : null,
                        (intArray2 == null || intArray2.size() <= 0) ? null : intArray2.toArray());
        if (ArrayUtils.isEmpty((String[]) pair.first)) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        bundle2.putStringArray("android.intent.extra.changed_package_list", (String[]) pair.first);
        bundle2.putIntArray("android.intent.extra.changed_uid_list", (int[]) pair.second);
        return bundle2;
    }

    public static boolean isPrivacySafetyLabelChangeNotificationsEnabled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return (!DeviceConfig.getBoolean(
                                "privacy", "safety_label_change_notifications_enabled", true)
                        || packageManager.hasSystemFeature("android.hardware.type.automotive")
                        || packageManager.hasSystemFeature("android.software.leanback")
                        || packageManager.hasSystemFeature("android.hardware.type.watch"))
                ? false
                : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01bc, code lost:

       if ((r26.mContext.getPackageManager().getPackageInfo(r0, android.content.pm.PackageManager.PackageInfoFlags.of(0)).applicationInfo.flags & 129) != 0) goto L88;
    */
    /* JADX WARN: Removed duplicated region for block: B:74:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doSendBroadcast(
            java.lang.String r27,
            java.lang.String r28,
            android.os.Bundle r29,
            int r30,
            java.lang.String r31,
            com.android.server.pm.AsecInstallHelper.AnonymousClass3 r32,
            int[] r33,
            boolean r34,
            android.util.SparseArray r35,
            java.util.function.BiFunction r36,
            android.os.Bundle r37) {
        /*
            Method dump skipped, instructions count: 804
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.BroadcastHelper.doSendBroadcast(java.lang.String,"
                    + " java.lang.String, android.os.Bundle, int, java.lang.String,"
                    + " com.android.server.pm.AsecInstallHelper$3, int[], boolean,"
                    + " android.util.SparseArray, java.util.function.BiFunction,"
                    + " android.os.Bundle):void");
    }

    public final void notifyResourcesChanged(boolean z, boolean z2, String[] strArr, int[] iArr) {
        PackageMonitorCallbackHelper packageMonitorCallbackHelper =
                this.mPackageMonitorCallbackHelper;
        packageMonitorCallbackHelper.getClass();
        Bundle bundle = new Bundle();
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        if (z2) {
            bundle.putBoolean("android.intent.extra.REPLACING", z2);
        }
        packageMonitorCallbackHelper.notifyPackageMonitor(
                z
                        ? "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE"
                        : "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE",
                null,
                bundle,
                null,
                null,
                null,
                this.mHandler,
                null);
    }

    public final void sendApplicationHiddenForUser(
            String str,
            PackageStateInternal packageStateInternal,
            int i,
            PackageSender packageSender) {
        PackageRemovedInfo packageRemovedInfo = new PackageRemovedInfo();
        packageRemovedInfo.mRemovedPackage = str;
        packageRemovedInfo.mInstallerPackageName =
                packageStateInternal.getInstallSource().mInstallerPackageName;
        packageRemovedInfo.mRemovedUsers = new int[] {i};
        packageRemovedInfo.mBroadcastUsers = new int[] {i};
        packageRemovedInfo.mUid = UserHandle.getUid(i, packageStateInternal.getAppId());
        packageRemovedInfo.mRemovedPackageVersionCode = packageStateInternal.getVersionCode();
        packageRemovedInfo.mHidden = true;
        sendPackageRemovedBroadcasts(packageRemovedInfo, packageSender, true, false, false);
    }

    public final void sendDistractingPackagesChanged(
            final Computer computer, String[] strArr, int[] iArr, final int i, int i2) {
        final Bundle bundle = new Bundle();
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        bundle.putInt("android.intent.extra.distraction_restrictions", i2);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        BroadcastHelper broadcastHelper = BroadcastHelper.this;
                        Bundle bundle2 = bundle;
                        int i3 = i;
                        Computer computer2 = computer;
                        broadcastHelper.getClass();
                        broadcastHelper.sendPackageBroadcast(
                                "android.intent.action.DISTRACTING_PACKAGES_CHANGED",
                                null,
                                bundle2,
                                1073741824,
                                null,
                                null,
                                new int[] {i3},
                                null,
                                null,
                                new BroadcastHelper$$ExternalSyntheticLambda6(computer2, 1),
                                null);
                    }
                });
    }

    public final void sendFirstLaunchBroadcast(String str, String str2, int[] iArr, int[] iArr2) {
        if (!PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            sendPackageBroadcast(
                    "android.intent.action.PACKAGE_FIRST_LAUNCH",
                    str,
                    null,
                    0,
                    str2,
                    null,
                    iArr,
                    iArr2,
                    null,
                    null,
                    null);
            return;
        }
        boolean equals = "MONETIZED_APP_OPENED".equals(str2);
        MonetizationUtils monetizationUtils = this.mMonetizationUtils;
        if (equals) {
            monetizationUtils.updateSettingsForMonetization(str, true, true, true);
            return;
        }
        sendPackageBroadcast(
                "android.intent.action.PACKAGE_FIRST_LAUNCH",
                str,
                null,
                0,
                str2,
                null,
                iArr,
                iArr2,
                null,
                null,
                null);
        if (monetizationUtils.mGalaxyStoreBadgeEnabled.get()
                && "com.sec.android.app.samsungapps".equals(str2)
                && iArr[0] == 0) {
            monetizationUtils.updateSettingsForMonetization(str, true, false, false);
        }
    }

    public final void sendPackageAddedForNewUsers(
            Computer computer,
            final String str,
            boolean z,
            final boolean z2,
            final int i,
            final int[] iArr,
            final int[] iArr2,
            final boolean z3,
            final int i2) {
        if (ArrayUtils.isEmpty(iArr) && ArrayUtils.isEmpty(iArr2)) {
            return;
        }
        final SparseArray visibilityAllowList =
                this.mAppsFilter.getVisibilityAllowList(
                        computer,
                        computer.getPackageStateInternal(1000, str),
                        iArr,
                        computer.getPackageStates());
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BroadcastHelper broadcastHelper = BroadcastHelper.this;
                        String str2 = str;
                        int i3 = i;
                        int[] iArr3 = iArr;
                        int[] iArr4 = iArr2;
                        boolean z4 = z3;
                        int i4 = i2;
                        SparseArray sparseArray = visibilityAllowList;
                        broadcastHelper.getClass();
                        Bundle bundle = new Bundle(1);
                        bundle.putInt(
                                "android.intent.extra.UID",
                                UserHandle.getUid(
                                        ArrayUtils.isEmpty(iArr3) ? iArr4[0] : iArr3[0], i3));
                        if (z4) {
                            bundle.putBoolean("android.intent.extra.ARCHIVAL", true);
                        }
                        bundle.putInt("android.content.pm.extra.DATA_LOADER_TYPE", i4);
                        broadcastHelper.sendPackageBroadcast(
                                "android.intent.action.PACKAGE_ADDED",
                                str2,
                                bundle,
                                0,
                                null,
                                null,
                                iArr3,
                                iArr4,
                                sparseArray,
                                null,
                                null);
                        if (BroadcastHelper.isPrivacySafetyLabelChangeNotificationsEnabled(
                                broadcastHelper.mContext)) {
                            broadcastHelper.sendPackageBroadcast(
                                    "android.intent.action.PACKAGE_ADDED",
                                    str2,
                                    bundle,
                                    0,
                                    broadcastHelper
                                            .mContext
                                            .getPackageManager()
                                            .getPermissionControllerPackageName(),
                                    null,
                                    iArr3,
                                    iArr4,
                                    sparseArray,
                                    null,
                                    null);
                        }
                    }
                };
        Handler handler = this.mHandler;
        handler.post(runnable);
        PackageMonitorCallbackHelper packageMonitorCallbackHelper =
                this.mPackageMonitorCallbackHelper;
        packageMonitorCallbackHelper.getClass();
        Bundle bundle = new Bundle(2);
        bundle.putInt(
                "android.intent.extra.UID",
                UserHandle.getUid(ArrayUtils.isEmpty(iArr) ? iArr2[0] : iArr[0], i));
        if (z3) {
            bundle.putBoolean("android.intent.extra.ARCHIVAL", true);
        }
        bundle.putInt("android.content.pm.extra.DATA_LOADER_TYPE", i2);
        packageMonitorCallbackHelper.notifyPackageMonitor(
                "android.intent.action.PACKAGE_ADDED",
                str,
                bundle,
                iArr,
                iArr2,
                visibilityAllowList,
                this.mHandler,
                null);
        if (!z || ArrayUtils.isEmpty(iArr)) {
            return;
        }
        handler.post(
                new Runnable() { // from class:
                    // com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i3;
                        BroadcastHelper broadcastHelper = BroadcastHelper.this;
                        int[] iArr3 = iArr;
                        String str2 = str;
                        boolean z4 = z2;
                        broadcastHelper.getClass();
                        for (int i4 : iArr3) {
                            if (broadcastHelper.mUmInternal.isUserRunning(i4)) {
                                UserManagerService.LocalService localService =
                                        broadcastHelper.mUmInternal;
                                synchronized (UserManagerService.this.mUserStates) {
                                    i3 = UserManagerService.this.mUserStates.get(i4);
                                }
                                if (i3 == 0) {
                                    continue;
                                } else {
                                    IActivityManager service = ActivityManager.getService();
                                    try {
                                        Intent intent =
                                                new Intent(
                                                                "android.intent.action.LOCKED_BOOT_COMPLETED")
                                                        .setPackage(str2);
                                        intent.putExtra("android.intent.extra.user_handle", i4);
                                        if (z4) {
                                            intent.addFlags(32);
                                        }
                                        String[] strArr = {
                                            "android.permission.RECEIVE_BOOT_COMPLETED"
                                        };
                                        ActivityManagerInternal activityManagerInternal =
                                                broadcastHelper.mAmInternal;
                                        long bootTimeTempAllowListDuration =
                                                activityManagerInternal != null
                                                        ? activityManagerInternal
                                                                .getBootTimeTempAllowListDuration()
                                                        : 10000L;
                                        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                                        makeBasic.setTemporaryAppAllowlist(
                                                bootTimeTempAllowListDuration, 0, 202, "");
                                        service.broadcastIntentWithFeature(
                                                (IApplicationThread) null,
                                                (String) null,
                                                intent,
                                                (String) null,
                                                (IIntentReceiver) null,
                                                0,
                                                (String) null,
                                                (Bundle) null,
                                                strArr,
                                                (String[]) null,
                                                (String[]) null,
                                                -1,
                                                makeBasic.toBundle(),
                                                false,
                                                false,
                                                i4);
                                        if (broadcastHelper.mUmInternal.isUserUnlockingOrUnlocked(
                                                i4)) {
                                            Intent intent2 =
                                                    new Intent(
                                                                    "android.intent.action.BOOT_COMPLETED")
                                                            .setPackage(str2);
                                            intent2.putExtra(
                                                    "android.intent.extra.user_handle", i4);
                                            if (z4) {
                                                intent2.addFlags(32);
                                            }
                                            service.broadcastIntentWithFeature(
                                                    (IApplicationThread) null,
                                                    (String) null,
                                                    intent2,
                                                    (String) null,
                                                    (IIntentReceiver) null,
                                                    0,
                                                    (String) null,
                                                    (Bundle) null,
                                                    strArr,
                                                    (String[]) null,
                                                    (String[]) null,
                                                    -1,
                                                    makeBasic.toBundle(),
                                                    false,
                                                    false,
                                                    i4);
                                        }
                                    } catch (RemoteException e) {
                                        throw e.rethrowFromSystemServer();
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public final void sendPackageAddedForUser(
            Computer computer,
            String str,
            PackageStateInternal packageStateInternal,
            int i,
            boolean z,
            String str2) {
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        boolean isSystem = packageStateInternal.isSystem();
        boolean isInstantApp = userStateOrDefault.isInstantApp();
        sendPackageAddedForNewUsers(
                computer,
                str,
                isSystem,
                false,
                packageStateInternal.getAppId(),
                isInstantApp ? PackageManagerService.EMPTY_INT_ARRAY : new int[] {i},
                isInstantApp ? new int[] {i} : PackageManagerService.EMPTY_INT_ARRAY,
                z,
                0);
        PackageInstaller.SessionInfo sessionInfo = new PackageInstaller.SessionInfo();
        sessionInfo.installReason = userStateOrDefault.getInstallReason();
        sessionInfo.appPackageName = str;
        sendSessionCommitBroadcast(computer, sessionInfo, i, str2);
    }

    public final void sendPackageBroadcast(
            String str,
            String str2,
            Bundle bundle,
            int i,
            String str3,
            AsecInstallHelper.AnonymousClass3 anonymousClass3,
            int[] iArr,
            int[] iArr2,
            SparseArray sparseArray,
            BiFunction biFunction,
            Bundle bundle2) {
        try {
            IActivityManager service = ActivityManager.getService();
            if (service == null) {
                return;
            }
            int[] runningUserIds = iArr == null ? service.getRunningUserIds() : iArr;
            if (ArrayUtils.isEmpty(iArr2)) {
                doSendBroadcast(
                        str,
                        str2,
                        bundle,
                        i,
                        str3,
                        anonymousClass3,
                        runningUserIds,
                        false,
                        sparseArray,
                        biFunction,
                        bundle2);
            } else {
                doSendBroadcast(
                        str,
                        str2,
                        bundle,
                        i,
                        str3,
                        anonymousClass3,
                        iArr2,
                        true,
                        null,
                        null,
                        bundle2);
            }
        } catch (RemoteException unused) {
        }
    }

    public final void sendPackageBroadcastAndNotify(
            final String str,
            final String str2,
            final Bundle bundle,
            final int i,
            final String str3,
            final int[] iArr,
            final int[] iArr2,
            final SparseArray sparseArray,
            final Bundle bundle2) {
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda4
                    public final /* synthetic */ IIntentReceiver f$6 = null;

                    @Override // java.lang.Runnable
                    public final void run() {
                        BroadcastHelper.this.sendPackageBroadcast(
                                str,
                                str2,
                                bundle,
                                i,
                                str3,
                                (AsecInstallHelper.AnonymousClass3) this.f$6,
                                iArr,
                                iArr2,
                                sparseArray,
                                null,
                                bundle2);
                    }
                });
        if (str3 == null) {
            this.mPackageMonitorCallbackHelper.notifyPackageMonitor(
                    str, str2, bundle, iArr, iArr2, sparseArray, this.mHandler, null);
        }
    }

    public final void sendPackageChangedBroadcast(
            Computer computer,
            final String str,
            final boolean z,
            final ArrayList arrayList,
            final int i,
            final String str2) {
        if (computer.getPackageStateInternal(1000, str) == null) {
            return;
        }
        int userId = UserHandle.getUserId(i);
        boolean isInstantAppInternal = computer.isInstantAppInternal(userId, 1000, str);
        final int[] iArr =
                isInstantAppInternal ? PackageManagerService.EMPTY_INT_ARRAY : new int[] {userId};
        int[] iArr2 =
                isInstantAppInternal ? new int[] {userId} : PackageManagerService.EMPTY_INT_ARRAY;
        final SparseArray visibilityAllowLists =
                isInstantAppInternal ? null : computer.getVisibilityAllowLists(str, iArr);
        final int[] iArr3 = iArr2;
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BroadcastHelper broadcastHelper = BroadcastHelper.this;
                        String str3 = str;
                        boolean z2 = z;
                        ArrayList arrayList2 = arrayList;
                        int i2 = i;
                        String str4 = str2;
                        int[] iArr4 = iArr;
                        int[] iArr5 = iArr3;
                        SparseArray sparseArray = visibilityAllowLists;
                        broadcastHelper.getClass();
                        Bundle bundle = new Bundle(4);
                        bundle.putString(
                                "android.intent.extra.changed_component_name",
                                (String) arrayList2.get(0));
                        String[] strArr = new String[arrayList2.size()];
                        arrayList2.toArray(strArr);
                        bundle.putStringArray(
                                "android.intent.extra.changed_component_name_list", strArr);
                        bundle.putBoolean("android.intent.extra.DONT_KILL_APP", z2);
                        bundle.putInt("android.intent.extra.UID", i2);
                        if (str4 != null) {
                            bundle.putString("android.intent.extra.REASON", str4);
                        }
                        broadcastHelper.sendPackageBroadcast(
                                "android.intent.action.PACKAGE_CHANGED",
                                str3,
                                bundle,
                                !arrayList2.contains(str3) ? 1073741824 : 0,
                                null,
                                null,
                                iArr4,
                                iArr5,
                                sparseArray,
                                null,
                                null);
                    }
                });
        PackageMonitorCallbackHelper packageMonitorCallbackHelper =
                this.mPackageMonitorCallbackHelper;
        packageMonitorCallbackHelper.getClass();
        Bundle bundle = new Bundle(4);
        bundle.putString("android.intent.extra.changed_component_name", (String) arrayList.get(0));
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        bundle.putStringArray("android.intent.extra.changed_component_name_list", strArr);
        bundle.putBoolean("android.intent.extra.DONT_KILL_APP", z);
        bundle.putInt("android.intent.extra.UID", i);
        if (str2 != null) {
            bundle.putString("android.intent.extra.REASON", str2);
        }
        packageMonitorCallbackHelper.notifyPackageMonitor(
                "android.intent.action.PACKAGE_CHANGED",
                str,
                bundle,
                iArr,
                iArr2,
                visibilityAllowLists,
                this.mHandler,
                null);
    }

    public final void sendPackageRemovedBroadcasts(
            PackageRemovedInfo packageRemovedInfo,
            PackageSender packageSender,
            boolean z,
            boolean z2,
            boolean z3) {
        Bundle bundle;
        boolean z4;
        boolean z5;
        Bundle bundle2;
        ArraySet arraySet;
        String str = packageRemovedInfo.mRemovedPackage;
        String str2 = packageRemovedInfo.mInstallerPackageName;
        int[] iArr = packageRemovedInfo.mBroadcastUsers;
        int[] iArr2 = packageRemovedInfo.mInstantUserIds;
        SparseArray sparseArray = packageRemovedInfo.mBroadcastAllowList;
        boolean z6 = packageRemovedInfo.mDataRemoved;
        boolean z7 = packageRemovedInfo.mIsUpdate;
        boolean z8 = packageRemovedInfo.mIsRemovedPackageSystemUpdate;
        boolean z9 = packageRemovedInfo.mRemovedForAllUsers;
        boolean z10 = packageRemovedInfo.mIsStaticSharedLib;
        Bundle bundle3 = new Bundle();
        bundle3.putInt("android.intent.extra.UID", packageRemovedInfo.mUid);
        bundle3.putBoolean("android.intent.extra.DATA_REMOVED", z6);
        bundle3.putBoolean("android.intent.extra.SYSTEM_UPDATE_UNINSTALL", z8);
        bundle3.putBoolean("android.intent.extra.DONT_KILL_APP", !z);
        bundle3.putBoolean("android.intent.extra.USER_INITIATED", !z2);
        bundle3.putBoolean("android.intent.extra.HIDDEN", packageRemovedInfo.mHidden);
        boolean z11 = z7 || z8;
        if (z11 || z3) {
            bundle3.putBoolean("android.intent.extra.REPLACING", true);
        }
        if (z3) {
            bundle3.putBoolean("android.intent.extra.ARCHIVAL", true);
        }
        bundle3.putBoolean("android.intent.extra.REMOVED_FOR_ALL_USERS", z9);
        if (str == null || str2 == null) {
            bundle = bundle3;
            z4 = z10;
            z5 = z8;
        } else {
            bundle = bundle3;
            z4 = z10;
            z5 = z8;
            sendPackageBroadcastAndNotify(
                    "android.intent.action.PACKAGE_REMOVED",
                    str,
                    bundle3,
                    0,
                    str2,
                    iArr,
                    iArr2,
                    null,
                    null);
        }
        if (z4) {
            return;
        }
        if (str != null) {
            Bundle bundle4 = bundle;
            sendPackageBroadcastAndNotify(
                    "android.intent.action.PACKAGE_REMOVED",
                    str,
                    bundle4,
                    0,
                    null,
                    iArr,
                    iArr2,
                    sparseArray,
                    null);
            sendPackageBroadcastAndNotify(
                    "android.intent.action.PACKAGE_REMOVED_INTERNAL",
                    str,
                    bundle4,
                    0,
                    "android",
                    iArr,
                    iArr2,
                    sparseArray,
                    null);
            if (z6 && !z5) {
                sendPackageBroadcastAndNotify(
                        "android.intent.action.PACKAGE_FULLY_REMOVED",
                        str,
                        bundle,
                        16777216,
                        null,
                        iArr,
                        iArr2,
                        sparseArray,
                        null);
                int i = packageRemovedInfo.mUid;
                PackageObserverHelper packageObserverHelper =
                        ((PackageManagerService) packageSender).mPackageObserverHelper;
                synchronized (packageObserverHelper.mLock) {
                    arraySet = packageObserverHelper.mActiveSnapshot;
                }
                int size = arraySet.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((PackageManagerInternal.PackageListObserver) arraySet.valueAt(i2))
                            .onPackageRemoved(str, i);
                }
                UserPackage.removeFromCache(UserHandle.getUserId(i), str);
            }
        }
        if (packageRemovedInfo.mIsAppIdRemoved) {
            if (z11 || z3) {
                bundle2 = bundle;
                bundle2.putString("android.intent.extra.PACKAGE_NAME", str);
            } else {
                bundle2 = bundle;
            }
            sendPackageBroadcastAndNotify(
                    "android.intent.action.UID_REMOVED",
                    null,
                    bundle2,
                    16777216,
                    null,
                    iArr,
                    iArr2,
                    sparseArray,
                    null);
        }
    }

    public final void sendPackagesSuspendedOrUnsuspendedForUser(
            Computer computer,
            final String str,
            String[] strArr,
            int[] iArr,
            boolean z,
            final int i) {
        final Bundle bundle = new Bundle(3);
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        if (z) {
            bundle.putBoolean("android.intent.extra.quarantined", true);
        }
        final Bundle bundle2 = new BroadcastOptions().setDeferralPolicy(2).toBundle();
        final BroadcastHelper$$ExternalSyntheticLambda6 broadcastHelper$$ExternalSyntheticLambda6 =
                new BroadcastHelper$$ExternalSyntheticLambda6(computer, 2);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        BroadcastHelper.this.sendPackageBroadcast(
                                str,
                                null,
                                bundle,
                                1342177280,
                                null,
                                null,
                                new int[] {i},
                                null,
                                null,
                                broadcastHelper$$ExternalSyntheticLambda6,
                                bundle2);
                    }
                });
        this.mPackageMonitorCallbackHelper.notifyPackageMonitor(
                str,
                null,
                bundle,
                new int[] {i},
                null,
                null,
                this.mHandler,
                broadcastHelper$$ExternalSyntheticLambda6);
    }

    public final void sendPreferredActivityChangedBroadcast(final int i) {
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        IActivityManager service = ActivityManager.getService();
                        if (service == null) {
                            return;
                        }
                        Intent intent =
                                new Intent(
                                        "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED");
                        intent.putExtra("android.intent.extra.user_handle", i2);
                        intent.addFlags(67108864);
                        try {
                            service.broadcastIntentWithFeature(
                                    (IApplicationThread) null,
                                    (String) null,
                                    intent,
                                    (String) null,
                                    (IIntentReceiver) null,
                                    0,
                                    (String) null,
                                    (Bundle) null,
                                    (String[]) null,
                                    (String[]) null,
                                    (String[]) null,
                                    -1,
                                    (Bundle) null,
                                    false,
                                    false,
                                    i2);
                        } catch (RemoteException unused) {
                        }
                    }
                });
    }

    public final void sendResourcesChangedBroadcast(
            Computer computer,
            boolean z,
            boolean z2,
            String[] strArr,
            int[] iArr,
            AsecInstallHelper.AnonymousClass3 anonymousClass3) {
        if (ArrayUtils.isEmpty(strArr) || ArrayUtils.isEmpty(iArr)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        if (z2) {
            bundle.putBoolean("android.intent.extra.REPLACING", z2);
        }
        sendPackageBroadcast(
                z
                        ? "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE"
                        : "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE",
                null,
                bundle,
                0,
                null,
                anonymousClass3,
                null,
                null,
                null,
                new BroadcastHelper$$ExternalSyntheticLambda6(computer, 0),
                null);
    }

    public final void sendResourcesChangedBroadcastAndNotify(
            Computer computer,
            boolean z,
            boolean z2,
            ArrayList arrayList,
            AsecInstallHelper.AnonymousClass3 anonymousClass3) {
        int size = arrayList.size();
        String[] strArr = new String[size];
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            AndroidPackage androidPackage = (AndroidPackage) arrayList.get(i);
            strArr[i] = androidPackage.getPackageName();
            iArr[i] = androidPackage.getUid();
        }
        sendResourcesChangedBroadcast(computer, z, z2, strArr, iArr, anonymousClass3);
        notifyResourcesChanged(z, z2, strArr, iArr);
    }

    public final void sendSessionCommitBroadcast(
            Computer computer, PackageInstaller.SessionInfo sessionInfo, int i, String str) {
        UserManagerService userManagerService = UserManagerService.getInstance();
        if (userManagerService == null || sessionInfo.isStaged()) {
            return;
        }
        UserInfo profileParent = userManagerService.getProfileParent(i);
        int i2 = profileParent != null ? profileParent.id : i;
        ComponentName defaultHomeActivity = computer.getDefaultHomeActivity(i2);
        if (defaultHomeActivity != null
                && (!Flags.allowPrivateProfile()
                        || !android.multiuser.Flags.enablePermissionToAccessHiddenProfiles()
                        || !android.multiuser.Flags.enablePrivateSpaceFeatures()
                        || this.mUmInternal.getUserProperties(i).getProfileApiVisibility() != 1
                        || this.mContext
                                        .getPackageManager()
                                        .checkPermission(
                                                "android.permission.ACCESS_HIDDEN_PROFILES_FULL",
                                                defaultHomeActivity.getPackageName())
                                == 0
                        || this.mContext
                                        .getPackageManager()
                                        .checkPermission(
                                                "android.permission.ACCESS_HIDDEN_PROFILES",
                                                defaultHomeActivity.getPackageName())
                                == 0)) {
            this.mContext.sendBroadcastAsUser(
                    new Intent("android.content.pm.action.SESSION_COMMITTED")
                            .putExtra("android.content.pm.extra.SESSION", sessionInfo)
                            .putExtra("android.intent.extra.USER", UserHandle.of(i))
                            .setPackage(defaultHomeActivity.getPackageName()),
                    UserHandle.of(i2));
        }
        if (str != null) {
            this.mContext.sendBroadcastAsUser(
                    new Intent("android.content.pm.action.SESSION_COMMITTED")
                            .putExtra("android.content.pm.extra.SESSION", sessionInfo)
                            .putExtra("android.intent.extra.USER", UserHandle.of(i))
                            .setPackage(str),
                    UserHandle.of(i2));
        }
        Context context = this.mContext;
        if (i != 0
                || defaultHomeActivity == null
                || "com.sec.android.app.desktoplauncher"
                        .equals(defaultHomeActivity.getPackageName())) {
            return;
        }
        DesktopModeManagerInternal desktopModeManagerInternal =
                (DesktopModeManagerInternal)
                        LocalServices.getService(DesktopModeManagerInternal.class);
        if (desktopModeManagerInternal != null
                && desktopModeManagerInternal.isDesktopModeEnablingOrEnabled()) {
            context.sendBroadcastAsUser(
                    new Intent("android.content.pm.action.SESSION_COMMITTED")
                            .putExtra("android.content.pm.extra.SESSION", sessionInfo)
                            .putExtra("android.intent.extra.USER", UserHandle.of(i))
                            .setPackage("com.sec.android.app.desktoplauncher"),
                    UserHandle.of(i));
        }
    }

    public final void sendSystemPackageUpdatedBroadcasts(PackageRemovedInfo packageRemovedInfo) {
        if (packageRemovedInfo.mIsRemovedPackageSystemUpdate) {
            String str = packageRemovedInfo.mRemovedPackage;
            String str2 = packageRemovedInfo.mInstallerPackageName;
            SparseArray sparseArray = packageRemovedInfo.mBroadcastAllowList;
            Bundle bundle = new Bundle(2);
            bundle.putInt("android.intent.extra.UID", packageRemovedInfo.mUid);
            bundle.putBoolean("android.intent.extra.REPLACING", true);
            sendPackageBroadcastAndNotify(
                    "android.intent.action.PACKAGE_ADDED",
                    str,
                    bundle,
                    0,
                    null,
                    null,
                    null,
                    sparseArray,
                    null);
            if (str2 != null) {
                sendPackageBroadcastAndNotify(
                        "android.intent.action.PACKAGE_ADDED",
                        str,
                        bundle,
                        0,
                        str2,
                        null,
                        null,
                        null,
                        null);
                sendPackageBroadcastAndNotify(
                        "android.intent.action.PACKAGE_REPLACED",
                        str,
                        bundle,
                        0,
                        str2,
                        null,
                        null,
                        null,
                        null);
            }
            sendPackageBroadcastAndNotify(
                    "android.intent.action.PACKAGE_REPLACED",
                    str,
                    bundle,
                    0,
                    null,
                    null,
                    null,
                    sparseArray,
                    null);
            ActivityManagerInternal activityManagerInternal = this.mAmInternal;
            long bootTimeTempAllowListDuration =
                    activityManagerInternal != null
                            ? activityManagerInternal.getBootTimeTempAllowListDuration()
                            : 10000L;
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setTemporaryAppAllowlist(bootTimeTempAllowListDuration, 0, 311, "");
            sendPackageBroadcastAndNotify(
                    "android.intent.action.MY_PACKAGE_REPLACED",
                    null,
                    null,
                    0,
                    str,
                    null,
                    null,
                    null,
                    makeBasic.toBundle());
        }
    }
}
