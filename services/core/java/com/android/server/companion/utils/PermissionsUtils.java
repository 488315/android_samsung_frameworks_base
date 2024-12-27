package com.android.server.companion.utils;

import android.app.role.RoleManager;
import android.companion.AssociationRequest;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;

import com.android.internal.app.IAppOpsService;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import java.util.Collections;
import java.util.Map;

public abstract class PermissionsUtils {
    public static final Map DEVICE_PROFILE_TO_PERMISSION;
    public static IAppOpsService sAppOpsService;

    static {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(
                "android.app.role.COMPANION_DEVICE_WATCH",
                "android.permission.REQUEST_COMPANION_PROFILE_WATCH");
        arrayMap.put(
                "android.app.role.COMPANION_DEVICE_APP_STREAMING",
                "android.permission.REQUEST_COMPANION_PROFILE_APP_STREAMING");
        arrayMap.put(
                "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION",
                "android.permission.REQUEST_COMPANION_PROFILE_AUTOMOTIVE_PROJECTION");
        arrayMap.put(
                "android.app.role.COMPANION_DEVICE_COMPUTER",
                "android.permission.REQUEST_COMPANION_PROFILE_COMPUTER");
        arrayMap.put(
                "android.app.role.COMPANION_DEVICE_GLASSES",
                "android.permission.REQUEST_COMPANION_PROFILE_GLASSES");
        arrayMap.put(
                "android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING",
                "android.permission.REQUEST_COMPANION_PROFILE_NEARBY_DEVICE_STREAMING");
        DEVICE_PROFILE_TO_PERMISSION = Collections.unmodifiableMap(arrayMap);
        sAppOpsService = null;
    }

    public static boolean checkPackage(int i, String str) {
        try {
            if (sAppOpsService == null) {
                synchronized (PermissionsUtils.class) {
                    try {
                        if (sAppOpsService == null) {
                            sAppOpsService =
                                    IAppOpsService.Stub.asInterface(
                                            ServiceManager.getService("appops"));
                        }
                    } finally {
                    }
                }
            }
            return sAppOpsService.checkPackage(i, str) == 0;
        } catch (RemoteException unused) {
            return true;
        }
    }

    public static void enforceCallerCanManageAssociationsForPackage(
            int i, Context context, String str, String str2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000) {
            return;
        }
        boolean z = context.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0;
        boolean z2 =
                context.checkCallingPermission("android.permission.MANAGE_COMPANION_DEVICES") == 0;
        if (UserHandle.getCallingUserId() == i) {
            if (checkPackage(callingUid, str) || z2) {
                return;
            }
        } else if (z && z2) {
            return;
        }
        StringBuilder sb = new StringBuilder("Caller (uid=");
        sb.append(Binder.getCallingUid());
        sb.append(") does not have permissions to ");
        if (str2 == null) {
            str2 = "manage associations";
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, str2, " for u", "/", sb);
        sb.append(str);
        throw new SecurityException(sb.toString());
    }

    public static void enforceCallerCanObserveDevicePresenceByUuid(
            final Context context, final String str, final int i) {
        if (context.checkCallingPermission(
                                "android.permission.REQUEST_OBSERVE_DEVICE_UUID_PRESENCE")
                        == 0
                && context.checkCallingPermission("android.permission.BLUETOOTH_SCAN") == 0
                && context.checkCallingPermission("android.permission.BLUETOOTH_CONNECT") == 0
                && Boolean.TRUE.equals(
                        Binder.withCleanCallingIdentity(
                                new FunctionalUtils.ThrowingSupplier() { // from class:
                                    // com.android.server.companion.utils.PermissionsUtils$$ExternalSyntheticLambda0
                                    public final Object getOrThrow() {
                                        Context context2 = context;
                                        int i2 = i;
                                        return Boolean.valueOf(
                                                ((RoleManager)
                                                                context2.getSystemService(
                                                                        RoleManager.class))
                                                        .getRoleHoldersAsUser(
                                                                "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION",
                                                                UserHandle.of(i2))
                                                        .contains(str));
                                    }
                                }))) {
            return;
        }
        throw new SecurityException(
                "Caller (uid="
                        + Binder.getCallingUid()
                        + ") does not have permissions to request observing device presence base on"
                        + " the UUID");
    }

    public static void enforceCallerIsSystemOrCanInteractWithUserId(Context context, int i) {
        if (Binder.getCallingUid() == 1000 || UserHandle.getCallingUserId() == i) {
            return;
        }
        context.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", null);
    }

    public static void enforcePermissionForCreatingAssociation(
            Context context, AssociationRequest associationRequest, int i) {
        String deviceProfile = associationRequest.getDeviceProfile();
        if (deviceProfile != null) {
            Map map = DEVICE_PROFILE_TO_PERMISSION;
            if (!map.containsKey(deviceProfile)) {
                throw new IllegalArgumentException(
                        "Unsupported device profile: ".concat(deviceProfile));
            }
            String str = (String) map.get(deviceProfile);
            if (context.checkPermission(str, Binder.getCallingPid(), i) != 0) {
                throw new SecurityException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Application must hold ",
                                str,
                                " to associate with a device with ",
                                deviceProfile,
                                " profile."));
            }
        }
        if (associationRequest.isSelfManaged()
                && context.checkPermission(
                                "android.permission.REQUEST_COMPANION_SELF_MANAGED",
                                Binder.getCallingPid(),
                                i)
                        != 0) {
            throw new SecurityException(
                    "Application does not hold android.permission.REQUEST_COMPANION_SELF_MANAGED");
        }
    }
}
