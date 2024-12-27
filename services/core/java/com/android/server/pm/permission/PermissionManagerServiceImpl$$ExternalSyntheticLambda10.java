package com.android.server.pm.permission;

import android.content.pm.PackageManager;
import android.util.Slog;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.SharedUserSetting;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class PermissionManagerServiceImpl$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PermissionManagerServiceImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PermissionManagerServiceImpl$$ExternalSyntheticLambda10(PermissionManagerServiceImpl permissionManagerServiceImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = permissionManagerServiceImpl;
        this.f$1 = obj;
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16, types: [int] */
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        LegacyPermissionState legacyPermissionState;
        LegacyPermissionState legacyPermissionState2;
        int i = 0;
        boolean z = true;
        switch (this.$r8$classId) {
            case 0:
                PermissionManagerServiceImpl permissionManagerServiceImpl = this.f$0;
                int[] iArr = (int[]) this.f$1;
                PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                permissionManagerServiceImpl.getClass();
                int appId = packageStateInternal.getAppId();
                if (packageStateInternal.hasSharedUser()) {
                    int sharedUserAppId = packageStateInternal.getSharedUserAppId();
                    SharedUserSetting sharedUser = ((PackageManagerService.PackageManagerInternalImpl) permissionManagerServiceImpl.mPackageManagerInt).mService.snapshotComputer().getSharedUser(sharedUserAppId);
                    if (sharedUser == null) {
                        Slog.wtf("PermissionManager", "Missing shared user Api for " + sharedUserAppId);
                        return;
                    }
                    legacyPermissionState = sharedUser.mLegacyPermissionsState;
                } else {
                    legacyPermissionState = packageStateInternal.getLegacyPermissionState();
                }
                PackageManagerTracedLock packageManagerTracedLock = permissionManagerServiceImpl.mLock;
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        for (int i2 : iArr) {
                            UserPermissionState orCreateUserState = permissionManagerServiceImpl.mState.getOrCreateUserState(i2);
                            orCreateUserState.setInstallPermissionsFixed(packageStateInternal.getPackageName(), packageStateInternal.isInstallPermissionsFixed());
                            UidPermissionState orCreateUidState = orCreateUserState.getOrCreateUidState(appId);
                            orCreateUidState.mMissing = false;
                            orCreateUidState.mPermissions = null;
                            PackageManager.invalidatePackageInfoCache();
                            legacyPermissionState.getClass();
                            LegacyPermissionState.checkUserId(i2);
                            orCreateUidState.mMissing = legacyPermissionState.mMissing.get(i2);
                            permissionManagerServiceImpl.readLegacyPermissionStatesLocked(orCreateUidState, legacyPermissionState.getPermissionStates(i2));
                        }
                    } catch (Throwable th) {
                        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                return;
            case 1:
                PermissionManagerServiceImpl permissionManagerServiceImpl2 = this.f$0;
                int[] iArr2 = (int[]) this.f$1;
                PackageSetting packageSetting = (PackageSetting) obj;
                permissionManagerServiceImpl2.getClass();
                packageSetting.setBoolean(1, false);
                if (packageSetting.hasSharedUser()) {
                    int i3 = packageSetting.mSharedUserAppId;
                    SharedUserSetting sharedUser2 = ((PackageManagerService.PackageManagerInternalImpl) permissionManagerServiceImpl2.mPackageManagerInt).mService.snapshotComputer().getSharedUser(i3);
                    if (sharedUser2 == null) {
                        Slog.wtf("PermissionManager", "Missing shared user Api for " + i3);
                        return;
                    }
                    legacyPermissionState2 = sharedUser2.mLegacyPermissionsState;
                } else {
                    legacyPermissionState2 = packageSetting.mLegacyPermissionsState;
                }
                legacyPermissionState2.mUserStates.clear();
                legacyPermissionState2.mMissing.clear();
                int i4 = packageSetting.mAppId;
                PackageManagerTracedLock packageManagerTracedLock2 = permissionManagerServiceImpl2.mLock;
                boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock2) {
                    try {
                        int length = iArr2.length;
                        int i5 = 0;
                        while (i5 < length) {
                            int i6 = iArr2[i5];
                            UserPermissionState userPermissionState = (UserPermissionState) permissionManagerServiceImpl2.mState.mUserStates.get(i6);
                            if (userPermissionState == null) {
                                Slog.e("PermissionManager", "Missing user state for " + i6);
                            } else {
                                if (userPermissionState.mInstallPermissionsFixed.contains(packageSetting.mName)) {
                                    packageSetting.setBoolean(z ? 1 : 0, z);
                                }
                                UidPermissionState uidState = userPermissionState.getUidState(i4);
                                if (uidState == null) {
                                    Slog.e("PermissionManager", "Missing permission state for " + packageSetting.mName + " and user " + i6);
                                } else {
                                    legacyPermissionState2.setMissing(i6, uidState.mMissing);
                                    List permissionStates = uidState.getPermissionStates();
                                    int size = permissionStates.size();
                                    int i7 = i;
                                    while (i7 < size) {
                                        PermissionState permissionState = (PermissionState) permissionStates.get(i7);
                                        legacyPermissionState2.putPermissionState(new LegacyPermissionState.PermissionState(permissionState.getName(), permissionState.mPermission.isRuntime(), permissionState.isGranted(), permissionState.getFlags()), i6);
                                        i7++;
                                        z = true;
                                        iArr2 = iArr2;
                                    }
                                }
                            }
                            int[] iArr3 = iArr2;
                            ?? r0 = z;
                            i5 += r0;
                            z = r0;
                            i = 0;
                            iArr2 = iArr3;
                        }
                    } catch (Throwable th2) {
                        boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th2;
                    }
                }
                boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                return;
            default:
                PermissionManagerServiceImpl permissionManagerServiceImpl3 = this.f$0;
                Permission permission = (Permission) this.f$1;
                AndroidPackage androidPackage = (AndroidPackage) obj;
                int[] userIds = permissionManagerServiceImpl3.mUserManagerInt.getUserIds();
                PackageManagerTracedLock packageManagerTracedLock3 = permissionManagerServiceImpl3.mLock;
                boolean z8 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock3) {
                    try {
                        int length2 = userIds.length;
                        while (i < length2) {
                            int i8 = userIds[i];
                            UidPermissionState uidStateLocked = permissionManagerServiceImpl3.getUidStateLocked(androidPackage.getUid(), i8);
                            if (uidStateLocked == null) {
                                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i8);
                            } else {
                                uidStateLocked.removePermissionState(permission.mPermissionInfo.name);
                            }
                            i++;
                        }
                    } catch (Throwable th3) {
                        boolean z9 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th3;
                    }
                }
                boolean z10 = PackageManagerService.DEBUG_COMPRESSION;
                return;
        }
    }
}
