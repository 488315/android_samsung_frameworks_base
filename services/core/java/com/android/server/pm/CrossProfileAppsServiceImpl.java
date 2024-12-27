package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.CrossProfileAppsInternal;
import android.content.pm.ICrossProfileApps;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CrossProfileAppsServiceImpl extends ICrossProfileApps.Stub {
    public final Context mContext;
    public final Injector mInjector;
    public final LocalService mLocalService = new LocalService();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Injector {
        int checkComponentPermission(int i, String str);

        ActivityTaskManagerInternal getActivityTaskManagerInternal();

        AppOpsManager getAppOpsManager();

        int getCallingPid();

        int getCallingUid();

        int getCallingUserId();

        DevicePolicyManagerInternal getDevicePolicyManagerInternal();

        IPackageManager getIPackageManager();

        PackageManager getPackageManager();

        PackageManagerInternal getPackageManagerInternal();

        UserManager getUserManager();

        void killUid(int i);

        void sendBroadcastAsUser(Intent intent, UserHandle userHandle);

        Object withCleanCallingIdentity(FunctionalUtils.ThrowingSupplier throwingSupplier);

        void withCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InjectorImpl implements Injector {
        public final Context mContext;

        public InjectorImpl(Context context) {
            this.mContext = context;
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final int checkComponentPermission(int i, String str) {
            return ActivityManager.checkComponentPermission(str, i, -1, true);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final ActivityTaskManagerInternal getActivityTaskManagerInternal() {
            return (ActivityTaskManagerInternal)
                    LocalServices.getService(ActivityTaskManagerInternal.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final AppOpsManager getAppOpsManager() {
            return (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final int getCallingPid() {
            return Binder.getCallingPid();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final int getCallingUid() {
            return Binder.getCallingUid();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final int getCallingUserId() {
            return UserHandle.getCallingUserId();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final DevicePolicyManagerInternal getDevicePolicyManagerInternal() {
            return (DevicePolicyManagerInternal)
                    LocalServices.getService(DevicePolicyManagerInternal.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final IPackageManager getIPackageManager() {
            return AppGlobals.getPackageManager();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final PackageManager getPackageManager() {
            return this.mContext.getPackageManager();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final PackageManagerInternal getPackageManagerInternal() {
            return (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final UserManager getUserManager() {
            return (UserManager) this.mContext.getSystemService(UserManager.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final void killUid(int i) {
            int appId = UserHandle.getAppId(i);
            int userId = UserHandle.getUserId(i);
            ConcurrentHashMap concurrentHashMap =
                    PermissionManagerService.sRunningAttributionSources;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IActivityManager service = ActivityManager.getService();
                if (service != null) {
                    try {
                        service.killUidForPermissionChange(appId, userId, "permissions revoked");
                    } catch (RemoteException unused) {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final void sendBroadcastAsUser(Intent intent, UserHandle userHandle) {
            this.mContext.sendBroadcastAsUser(intent, userHandle);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final Object withCleanCallingIdentity(
                FunctionalUtils.ThrowingSupplier throwingSupplier) {
            return Binder.withCleanCallingIdentity(throwingSupplier);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final void withCleanCallingIdentity(
                FunctionalUtils.ThrowingRunnable throwingRunnable) {
            Binder.withCleanCallingIdentity(throwingRunnable);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends CrossProfileAppsInternal {
        public LocalService() {}

        public final List getTargetUserProfiles(String str, int i) {
            return CrossProfileAppsServiceImpl.this.getTargetUserProfilesUnchecked(i, str);
        }

        public final void setInteractAcrossProfilesAppOp(String str, int i, int i2) {
            CrossProfileAppsServiceImpl.this.setInteractAcrossProfilesAppOpUnchecked(i, i2, str);
        }

        public final boolean verifyPackageHasInteractAcrossProfilePermission(String str, int i) {
            PackageManager packageManager =
                    CrossProfileAppsServiceImpl.this.mInjector.getPackageManager();
            Objects.requireNonNull(str);
            ApplicationInfo applicationInfoAsUser =
                    packageManager.getApplicationInfoAsUser(str, 0, i);
            Objects.requireNonNull(applicationInfoAsUser);
            return CrossProfileAppsServiceImpl.this.hasInteractAcrossProfilesPermission(
                    applicationInfoAsUser.uid, -1, str);
        }

        public final boolean verifyUidHasInteractAcrossProfilePermission(String str, int i) {
            Objects.requireNonNull(str);
            return CrossProfileAppsServiceImpl.this.hasInteractAcrossProfilesPermission(i, -1, str);
        }
    }

    public CrossProfileAppsServiceImpl(Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
    }

    public final boolean canConfigureInteractAcrossProfiles(int i, String str) {
        if (this.mInjector.getCallingUserId() != i) {
            this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS");
        }
        return canConfigureInteractAcrossProfiles(str, i);
    }

    public final boolean canConfigureInteractAcrossProfiles(String str, int i) {
        if (!canUserAttemptToConfigureInteractAcrossProfiles(str, i)
                || !((Boolean)
                                this.mInjector.withCleanCallingIdentity(
                                        new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3(
                                                i, 2, this, str)))
                        .booleanValue()
                || !hasRequestedAppOpPermission(i, AppOpsManager.opToPermission(93), str)) {
            return false;
        }
        return ((Boolean)
                        this.mInjector.withCleanCallingIdentity(
                                new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3(
                                        this.mInjector.getCallingUserId(), 1, this, str)))
                .booleanValue();
    }

    public final boolean canInteractAcrossProfiles(final String str) {
        Objects.requireNonNull(str);
        verifyCallingPackage(str);
        List<UserHandle> targetUserProfilesUnchecked =
                getTargetUserProfilesUnchecked(this.mInjector.getCallingUserId(), str);
        if (targetUserProfilesUnchecked.isEmpty()
                || !hasInteractAcrossProfilesPermission(
                        this.mInjector.getCallingUid(), this.mInjector.getCallingPid(), str)) {
            return false;
        }
        for (final UserHandle userHandle : targetUserProfilesUnchecked) {
            int intValue =
                    ((Integer)
                                    this.mInjector.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingSupplier() { // from class:
                                                                          // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda10
                                                public final Object getOrThrow() {
                                                    CrossProfileAppsServiceImpl
                                                            crossProfileAppsServiceImpl =
                                                                    CrossProfileAppsServiceImpl
                                                                            .this;
                                                    String str2 = str;
                                                    UserHandle userHandle2 = userHandle;
                                                    crossProfileAppsServiceImpl.getClass();
                                                    try {
                                                        return Integer.valueOf(
                                                                crossProfileAppsServiceImpl
                                                                        .mInjector
                                                                        .getPackageManager()
                                                                        .getPackageUidAsUser(
                                                                                str2,
                                                                                0,
                                                                                userHandle2
                                                                                        .getIdentifier()));
                                                    } catch (
                                                            PackageManager.NameNotFoundException
                                                                    unused) {
                                                        return -1;
                                                    }
                                                }
                                            }))
                            .intValue();
            if (intValue == -1 || !hasInteractAcrossProfilesPermission(intValue, -1, str)) {
                return false;
            }
        }
        return true;
    }

    public final boolean canRequestInteractAcrossProfiles(String str) {
        Objects.requireNonNull(str);
        verifyCallingPackage(str);
        int callingUserId = this.mInjector.getCallingUserId();
        int[] profileIdsExcludingHidden =
                this.mInjector.getUserManager().getProfileIdsExcludingHidden(callingUserId, true);
        if (profileIdsExcludingHidden.length >= 2
                && !isProfileOwner(str, profileIdsExcludingHidden)) {
            return hasRequestedAppOpPermission(
                    callingUserId, AppOpsManager.opToPermission(93), str);
        }
        return false;
    }

    public final boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, String str) {
        if (this.mInjector.getCallingUserId() != i) {
            this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS");
        }
        return canUserAttemptToConfigureInteractAcrossProfiles(str, i);
    }

    public final boolean canUserAttemptToConfigureInteractAcrossProfiles(final String str, int i) {
        boolean z = false;
        int[] profileIds = this.mInjector.getUserManager().getProfileIds(i, false);
        ArrayList arrayList = new ArrayList();
        for (int i2 : profileIds) {
            if (!SemDualAppManager.isDualAppId(i2) && !SemPersonaManager.isSecureFolderId(i2)) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        int[] array =
                arrayList.stream()
                        .mapToInt(new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda8())
                        .toArray();
        if (array.length < 2
                || isProfileOwner(str, array)
                || !hasRequestedAppOpPermission(i, AppOpsManager.opToPermission(93), str)) {
            return false;
        }
        if (!((Boolean)
                        this.mInjector.withCleanCallingIdentity(
                                new FunctionalUtils
                                        .ThrowingSupplier() { // from class:
                                                              // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda4
                                    public final Object getOrThrow() {
                                        CrossProfileAppsServiceImpl crossProfileAppsServiceImpl =
                                                CrossProfileAppsServiceImpl.this;
                                        return Boolean.valueOf(
                                                crossProfileAppsServiceImpl
                                                        .mInjector
                                                        .getDevicePolicyManagerInternal()
                                                        .getDefaultCrossProfilePackages()
                                                        .contains(str));
                                    }
                                }))
                .booleanValue()) {
            int length = array.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                int packageUid =
                        this.mInjector
                                .getPackageManagerInternal()
                                .getPackageUid(str, 0L, array[i3]);
                if (packageUid != -1
                        && isPermissionGranted(
                                packageUid, "android.permission.INTERACT_ACROSS_PROFILES")) {
                    z = true;
                    break;
                }
                i3++;
            }
        }
        return !z;
    }

    public final void clearInteractAcrossProfilesAppOps(final int i) {
        final int opToDefaultMode =
                AppOpsManager.opToDefaultMode(
                        AppOpsManager.permissionToOp(
                                "android.permission.INTERACT_ACROSS_PROFILES"));
        ((List)
                        this.mInjector
                                .getPackageManagerInternal()
                                .getInstalledApplications(
                                        this.mInjector.getCallingUserId(),
                                        this.mInjector.getCallingUid(),
                                        0L)
                                .stream()
                                .map(new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda14())
                                .collect(Collectors.toList()))
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CrossProfileAppsServiceImpl.this.setInteractAcrossProfilesAppOp(
                                        i, (String) obj, opToDefaultMode);
                            }
                        });
    }

    public final List getTargetUserProfiles(String str) {
        Objects.requireNonNull(str);
        verifyCallingPackage(str);
        DevicePolicyEventLogger.createEvent(125).setStrings(new String[] {str}).write();
        return getTargetUserProfilesUnchecked(this.mInjector.getCallingUserId(), str);
    }

    public final List getTargetUserProfilesUnchecked(int i, String str) {
        return (List)
                this.mInjector.withCleanCallingIdentity(
                        new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3(i, 3, this, str));
    }

    public final boolean hasInteractAcrossProfilesPermission(int i, int i2, String str) {
        return isPermissionGranted(i, "android.permission.INTERACT_ACROSS_USERS_FULL")
                || isPermissionGranted(i, "android.permission.INTERACT_ACROSS_USERS")
                || PermissionChecker.checkPermissionForPreflight(
                                this.mContext,
                                "android.permission.INTERACT_ACROSS_PROFILES",
                                i2,
                                i,
                                str)
                        == 0;
    }

    public final boolean hasRequestedAppOpPermission(int i, String str, String str2) {
        try {
            return ArrayUtils.contains(
                    this.mInjector.getIPackageManager().getAppOpPermissionPackages(str, i), str2);
        } catch (RemoteException unused) {
            Slog.e("CrossProfileAppsService", "PackageManager dead. Cannot get permission info");
            return false;
        }
    }

    public final boolean isPermissionGranted(int i, String str) {
        return this.mInjector.checkComponentPermission(i, str) == 0;
    }

    public final boolean isProfileOwner(String str, int[] iArr) {
        for (int i : iArr) {
            ComponentName componentName =
                    (ComponentName)
                            this.mInjector.withCleanCallingIdentity(
                                    new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda1(
                                            this, i, 1));
            if (componentName == null ? false : componentName.getPackageName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final void resetInteractAcrossProfilesAppOps(int i, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (canConfigureInteractAcrossProfiles(str, i)) {
                PinnerService$$ExternalSyntheticOutline0.m(
                        "Not resetting app-op for package ",
                        str,
                        " since it is still configurable by users.",
                        "CrossProfileAppsService");
            } else {
                setInteractAcrossProfilesAppOp(
                        i,
                        str,
                        AppOpsManager.opToDefaultMode(
                                AppOpsManager.permissionToOp(
                                        "android.permission.INTERACT_ACROSS_PROFILES")));
            }
        }
    }

    public final void setInteractAcrossProfilesAppOp(int i, String str, int i2) {
        int callingUid = this.mInjector.getCallingUid();
        if (!isPermissionGranted(callingUid, "android.permission.INTERACT_ACROSS_USERS_FULL")
                && !isPermissionGranted(callingUid, "android.permission.INTERACT_ACROSS_USERS")) {
            throw new SecurityException(
                    "INTERACT_ACROSS_USERS or INTERACT_ACROSS_USERS_FULL is required to set the"
                        + " app-op for interacting across profiles.");
        }
        if (!isPermissionGranted(callingUid, "android.permission.MANAGE_APP_OPS_MODES")
                && !isPermissionGranted(
                        callingUid, "android.permission.CONFIGURE_INTERACT_ACROSS_PROFILES")) {
            throw new SecurityException(
                    "MANAGE_APP_OPS_MODES or CONFIGURE_INTERACT_ACROSS_PROFILES is required to set"
                        + " the app-op for interacting across profiles.");
        }
        setInteractAcrossProfilesAppOpUnchecked(i2, i, str);
    }

    public final void setInteractAcrossProfilesAppOpForProfileOrThrow(
            final int i, int i2, final String str, boolean z) {
        final int packageUidAsUser =
                this.mInjector.getPackageManager().getPackageUidAsUser(str, 0, i2);
        final String permissionToOp =
                AppOpsManager.permissionToOp("android.permission.INTERACT_ACROSS_PROFILES");
        if (((Boolean)
                        this.mInjector.withCleanCallingIdentity(
                                new FunctionalUtils
                                        .ThrowingSupplier() { // from class:
                                                              // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda15
                                    public final Object getOrThrow() {
                                        return Boolean.valueOf(
                                                i
                                                        == CrossProfileAppsServiceImpl.this
                                                                .mInjector
                                                                .getAppOpsManager()
                                                                .unsafeCheckOpNoThrow(
                                                                        permissionToOp,
                                                                        packageUidAsUser,
                                                                        str));
                                    }
                                }))
                .booleanValue()) {
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i,
                            "Attempt to set mode to existing value of ",
                            " for ",
                            str,
                            " on profile user ID "),
                    i2,
                    "CrossProfileAppsService");
            return;
        }
        boolean hasInteractAcrossProfilesPermission =
                hasInteractAcrossProfilesPermission(packageUidAsUser, -1, str);
        if (isPermissionGranted(
                this.mInjector.getCallingUid(),
                "android.permission.CONFIGURE_INTERACT_ACROSS_PROFILES")) {
            this.mInjector.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda6
                        public final void runOrThrow() {
                            CrossProfileAppsServiceImpl crossProfileAppsServiceImpl =
                                    CrossProfileAppsServiceImpl.this;
                            crossProfileAppsServiceImpl
                                    .mInjector
                                    .getAppOpsManager()
                                    .setUidMode(93, packageUidAsUser, i);
                        }
                    });
        } else {
            this.mInjector.getAppOpsManager().setUidMode(93, packageUidAsUser, i);
        }
        if (hasInteractAcrossProfilesPermission
                && !hasInteractAcrossProfilesPermission(packageUidAsUser, -1, str)) {
            this.mInjector.killUid(packageUidAsUser);
        }
        UserHandle of = UserHandle.of(i2);
        Intent intent =
                new Intent("android.content.pm.action.CAN_INTERACT_ACROSS_PROFILES_CHANGED")
                        .setPackage(str);
        if (this.mInjector.getPackageManagerInternal().getPackage(str).isCrossProfile()) {
            intent.addFlags(285212672);
        } else {
            intent.addFlags(1073741824);
        }
        Iterator it =
                this.mInjector
                        .getPackageManager()
                        .queryBroadcastReceiversAsUser(intent, 0, of)
                        .iterator();
        while (it.hasNext()) {
            intent.setComponent(((ResolveInfo) it.next()).getComponentInfo().getComponentName());
            this.mInjector.sendBroadcastAsUser(intent, of);
        }
        if (z) {
            DevicePolicyEventLogger.createEvent(139)
                    .setStrings(new String[] {str})
                    .setInt(i)
                    .setBoolean(
                            this.mInjector
                                    .getPackageManagerInternal()
                                    .getPackage(str)
                                    .isCrossProfile())
                    .write();
        }
    }

    public final void setInteractAcrossProfilesAppOpUnchecked(int i, int i2, String str) {
        if (i == 0 && !canConfigureInteractAcrossProfiles(str, i2)) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Tried to turn on the appop for interacting across profiles for invalid app ",
                    str,
                    "CrossProfileAppsService");
            return;
        }
        int[] profileIdsExcludingHidden =
                this.mInjector.getUserManager().getProfileIdsExcludingHidden(i2, false);
        int length = profileIdsExcludingHidden.length;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = profileIdsExcludingHidden[i3];
            if (((Boolean)
                            this.mInjector.withCleanCallingIdentity(
                                    new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3(
                                            this, str, i4)))
                    .booleanValue()) {
                try {
                    setInteractAcrossProfilesAppOpForProfileOrThrow(i, i4, str, i4 == i2);
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.e(
                            "CrossProfileAppsService",
                            SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                                    .m(i4, "Missing package ", str, " on profile user ID "),
                            e);
                }
            }
        }
    }

    public final void startActivityAsUser(
            IApplicationThread iApplicationThread,
            String str,
            String str2,
            final ComponentName componentName,
            final int i,
            boolean z,
            IBinder iBinder,
            Bundle bundle) {
        Bundle bundle2 = bundle;
        Objects.requireNonNull(str);
        Objects.requireNonNull(componentName);
        verifyCallingPackage(str);
        DevicePolicyEventLogger.createEvent(126).setStrings(new String[] {str}).write();
        final int callingUserId = this.mInjector.getCallingUserId();
        final int callingUid = this.mInjector.getCallingUid();
        int callingPid = this.mInjector.getCallingPid();
        if (!getTargetUserProfilesUnchecked(callingUserId, str).contains(UserHandle.of(i))) {
            throw new SecurityException(
                    VpnManagerService$$ExternalSyntheticOutline0.m(
                            i, str, " cannot access unrelated user "));
        }
        if (!str.equals(componentName.getPackageName())) {
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            str, " attempts to start an activity in other package - ");
            m.append(componentName.getPackageName());
            throw new SecurityException(m.toString());
        }
        final Intent intent = new Intent();
        if (z) {
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            if (iBinder == null || bundle2 != null) {
                intent.addFlags(270532608);
            } else {
                intent.addFlags(2097152);
            }
            intent.setPackage(componentName.getPackageName());
        } else {
            if (callingUserId != i) {
                if (!hasInteractAcrossProfilesPermission(callingUid, callingPid, str)
                        && !isPermissionGranted(
                                callingUid, "android.permission.START_CROSS_PROFILE_ACTIVITIES")) {
                    throw new SecurityException(
                            "Attempt to launch activity without one of the required"
                                + " android.permission.INTERACT_ACROSS_PROFILES or"
                                + " android.permission.START_CROSS_PROFILE_ACTIVITIES"
                                + " permissions.");
                }
                if (!((Boolean)
                                this.mInjector.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingSupplier() { // from class:
                                                                      // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda9
                                            public final Object getOrThrow() {
                                                CrossProfileAppsServiceImpl
                                                        crossProfileAppsServiceImpl =
                                                                CrossProfileAppsServiceImpl.this;
                                                return Boolean.valueOf(
                                                        crossProfileAppsServiceImpl
                                                                .mInjector
                                                                .getUserManager()
                                                                .isSameProfileGroup(
                                                                        callingUserId, i));
                                            }
                                        }))
                        .booleanValue()) {
                    throw new SecurityException(
                            "Attempt to launch activity when target user is not in the same profile"
                                + " group.");
                }
            }
            intent.setComponent(componentName);
        }
        this.mInjector.withCleanCallingIdentity(
                new FunctionalUtils.ThrowingRunnable() { // from class:
                    // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        CrossProfileAppsServiceImpl crossProfileAppsServiceImpl =
                                CrossProfileAppsServiceImpl.this;
                        Intent intent2 = intent;
                        int i2 = callingUid;
                        int i3 = i;
                        ComponentName componentName2 = componentName;
                        List queryIntentActivities =
                                crossProfileAppsServiceImpl
                                        .mInjector
                                        .getPackageManagerInternal()
                                        .queryIntentActivities(
                                                intent2,
                                                intent2.resolveTypeIfNeeded(
                                                        crossProfileAppsServiceImpl.mContext
                                                                .getContentResolver()),
                                                786432L,
                                                i2,
                                                i3);
                        int size = queryIntentActivities.size();
                        for (int i4 = 0; i4 < size; i4++) {
                            ActivityInfo activityInfo =
                                    ((ResolveInfo) queryIntentActivities.get(i4)).activityInfo;
                            if (TextUtils.equals(
                                            activityInfo.packageName,
                                            componentName2.getPackageName())
                                    && TextUtils.equals(
                                            activityInfo.name, componentName2.getClassName())
                                    && activityInfo.exported) {
                                return;
                            }
                        }
                        throw new SecurityException(
                                AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                                        componentName2,
                                        "Attempt to launch activity without  category"
                                            + " Intent.CATEGORY_LAUNCHER or activity is not"
                                            + " exported"));
                    }
                });
        if (bundle2 == null) {
            bundle2 = ActivityOptions.makeOpenCrossProfileAppsAnimation().toBundle();
        } else {
            bundle2.putAll(ActivityOptions.makeOpenCrossProfileAppsAnimation().toBundle());
        }
        intent.setPackage(null);
        intent.setComponent(componentName);
        this.mInjector
                .getActivityTaskManagerInternal()
                .startActivityAsUser(iApplicationThread, str, str2, intent, iBinder, 0, bundle2, i);
    }

    public final void startActivityAsUserByIntent(
            IApplicationThread iApplicationThread,
            String str,
            String str2,
            Intent intent,
            final int i,
            IBinder iBinder,
            Bundle bundle) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(intent);
        Objects.requireNonNull(intent.getComponent(), "The intent must have a Component set");
        verifyCallingPackage(str);
        int callingUserId = this.mInjector.getCallingUserId();
        final int callingUid = this.mInjector.getCallingUid();
        List targetUserProfilesUnchecked = getTargetUserProfilesUnchecked(callingUserId, str);
        if (callingUserId != i && !targetUserProfilesUnchecked.contains(UserHandle.of(i))) {
            throw new SecurityException(
                    VpnManagerService$$ExternalSyntheticOutline0.m(
                            i, str, " cannot access unrelated user "));
        }
        final Intent intent2 = new Intent(intent);
        intent2.setPackage(str);
        if (!str.equals(intent2.getComponent().getPackageName())) {
            StringBuilder m =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            str, " attempts to start an activity in other package - ");
            m.append(intent2.getComponent().getPackageName());
            throw new SecurityException(m.toString());
        }
        if (callingUserId != i
                && !hasInteractAcrossProfilesPermission(
                        this.mInjector.getCallingUid(), this.mInjector.getCallingPid(), str)) {
            throw new SecurityException(
                    "Attempt to launch activity without required"
                        + " android.permission.INTERACT_ACROSS_PROFILES permission or target user"
                        + " is not in the same profile group.");
        }
        this.mInjector.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda11
                    public final void runOrThrow() {
                        CrossProfileAppsServiceImpl crossProfileAppsServiceImpl =
                                CrossProfileAppsServiceImpl.this;
                        Intent intent3 = intent2;
                        if (crossProfileAppsServiceImpl
                                .mInjector
                                .getPackageManagerInternal()
                                .queryIntentActivities(
                                        intent3,
                                        intent3.resolveTypeIfNeeded(
                                                crossProfileAppsServiceImpl.mContext
                                                        .getContentResolver()),
                                        786432L,
                                        callingUid,
                                        i)
                                .isEmpty()) {
                            throw new SecurityException("Activity cannot handle intent");
                        }
                    }
                });
        this.mInjector
                .getActivityTaskManagerInternal()
                .startActivityAsUser(iApplicationThread, str, str2, intent2, iBinder, 0, bundle, i);
        DevicePolicyEventLogger.createEvent(150)
                .setStrings(new String[] {str})
                .setBoolean(
                        ((Boolean)
                                        this.mInjector.withCleanCallingIdentity(
                                                new CrossProfileAppsServiceImpl$$ExternalSyntheticLambda1(
                                                        this,
                                                        this.mInjector.getCallingUserId(),
                                                        0)))
                                .booleanValue())
                .write();
    }

    public final void verifyCallingPackage(String str) {
        this.mInjector.getAppOpsManager().checkPackage(this.mInjector.getCallingUid(), str);
    }
}
