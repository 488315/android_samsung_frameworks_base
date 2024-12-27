package com.android.server.devicepolicy;

import android.app.admin.ParcelableResource;
import android.content.ComponentName;
import android.content.PermissionChecker;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalManagerRegistry;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.pkg.PackageState;
import com.android.server.utils.Slogf;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda47 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda47(DevicePolicyManagerService devicePolicyManagerService, UserHandle userHandle, ComponentName componentName, CallerIdentity callerIdentity) {
        this.$r8$classId = 1;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = userHandle;
        this.f$2 = componentName;
        this.f$3 = callerIdentity;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda47(DevicePolicyManagerService devicePolicyManagerService, String str, String str2, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = obj;
    }

    public final Object getOrThrow() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                String str = (String) this.f$1;
                String str2 = (String) this.f$2;
                String str3 = (String) this.f$3;
                DeviceManagementResourcesProvider deviceManagementResourcesProvider = devicePolicyManagerService.mDeviceManagementResourcesProvider;
                synchronized (deviceManagementResourcesProvider.mLock) {
                    try {
                        ParcelableResource drawableForSourceLocked = deviceManagementResourcesProvider.getDrawableForSourceLocked(str, str2, str3);
                        if (drawableForSourceLocked != null) {
                            return drawableForSourceLocked;
                        }
                        if (((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForStyle).containsKey(str)) {
                            return (ParcelableResource) ((Map) ((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForStyle).get(str)).get(str2);
                        }
                        return null;
                    } finally {
                    }
                }
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                UserHandle userHandle = (UserHandle) this.f$1;
                ComponentName componentName = (ComponentName) this.f$2;
                CallerIdentity callerIdentity = (CallerIdentity) this.f$3;
                devicePolicyManagerService2.getClass();
                String str4 = devicePolicyManagerService2.isManagedProfile(userHandle.getIdentifier()) ? "no_remove_managed_profile" : "no_remove_user";
                if (!devicePolicyManagerService2.isAdminAffectedByRestriction(componentName, UserHandle.getUserId(callerIdentity.mUid), str4)) {
                    return Boolean.valueOf(devicePolicyManagerService2.mUserManagerInternal.removeUserEvenWhenDisallowed(userHandle.getIdentifier()));
                }
                Slogf.w("DevicePolicyManager", "The device owner cannot remove a user because %s is enabled, and was not set by the device owner", str4);
                return Boolean.FALSE;
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                String str5 = (String) this.f$1;
                String str6 = (String) this.f$2;
                CallerIdentity callerIdentity2 = (CallerIdentity) this.f$3;
                devicePolicyManagerService3.getClass();
                int userId = UserHandle.getUserId(callerIdentity2.mUid);
                devicePolicyManagerService3.getClass();
                int i2 = 0;
                if (devicePolicyManagerService3.getTargetSdk(UserHandle.getUserId(callerIdentity2.mUid), callerIdentity2.mPackageName) >= 29) {
                    devicePolicyManagerService3.mInjector.getClass();
                    PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = ((PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class)).withUnfilteredSnapshot();
                    try {
                        PackageState packageState = (PackageState) withUnfilteredSnapshot.getPackageStates().get(str5);
                        if (packageState == null) {
                            Slog.w("DevicePolicyManager", "Can't get permission state for missing package " + str5);
                        } else if (packageState.getUserStateOrDefault(userId).isInstalled()) {
                            int i3 = PermissionChecker.checkPermissionForPreflight(devicePolicyManagerService3.mContext, str6, -1, UserHandle.getUid(userId, packageState.getAppId()), str5) == 0 ? 0 : -1;
                            withUnfilteredSnapshot.close();
                            i = i3;
                        } else {
                            Slog.w("DevicePolicyManager", "Can't get permission state for uninstalled package " + str5);
                        }
                        withUnfilteredSnapshot.close();
                        return Integer.valueOf(i2);
                    } catch (Throwable th) {
                        if (withUnfilteredSnapshot != null) {
                            try {
                                withUnfilteredSnapshot.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                i = devicePolicyManagerService3.mIPackageManager.checkPermission(str6, str5, userId);
                if ((devicePolicyManagerService3.mInjector.mContext.getPackageManager().getPermissionFlags(str6, str5, UserHandle.of(userId)) & 4) == 4) {
                    i2 = i == 0 ? 1 : 2;
                }
                return Integer.valueOf(i2);
        }
    }
}
