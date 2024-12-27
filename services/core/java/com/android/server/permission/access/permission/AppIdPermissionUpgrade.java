package com.android.server.permission.access.permission;

import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.util.Slog;
import com.android.server.SystemService;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.immutable.IndexedSetExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedSet;
import com.android.server.permission.access.util.IntExtensionsKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.pm.pkg.PackageState;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppIdPermissionUpgrade {
    public final AppIdPermissionPolicy policy;
    public static final MutableIndexedSet LEGACY_RESTRICTED_PERMISSIONS = IndexedSetExtensionsKt.indexedSetOf("android.permission.ACCESS_BACKGROUND_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS", "android.permission.READ_CELL_BROADCASTS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.PROCESS_OUTGOING_CALLS");
    public static final MutableIndexedSet STORAGE_PERMISSIONS = IndexedSetExtensionsKt.indexedSetOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
    public static final MutableIndexedSet AURAL_VISUAL_MEDIA_PERMISSIONS = IndexedSetExtensionsKt.indexedSetOf("android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.ACCESS_MEDIA_LOCATION", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
    public static final MutableIndexedSet VISUAL_MEDIA_PERMISSIONS = IndexedSetExtensionsKt.indexedSetOf("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.ACCESS_MEDIA_LOCATION");

    public AppIdPermissionUpgrade(AppIdPermissionPolicy appIdPermissionPolicy) {
        this.policy = appIdPermissionPolicy;
    }

    public final void grantRuntimePermission(MutateStateScope mutateStateScope, PackageState packageState, int i, String str) {
        GmsAlarmManager$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("Granting runtime permission for package: ", packageState.getPackageName(), ", permission: ", str, ", userId: "), i, "AppIdPermissionUpgrade");
        Object obj = mutateStateScope.newState.getSystemState().getPermissions().map.get(str);
        Intrinsics.checkNotNull(obj);
        Permission permission = (Permission) obj;
        if (!packageState.getUserStateOrDefault(i).isInstantApp() || IntExtensionsKt.hasBits(permission.permissionInfo.getProtectionFlags(), 4096)) {
            int appId = packageState.getAppId();
            this.policy.getClass();
            int permissionFlags = AppIdPermissionPolicy.getPermissionFlags(mutateStateScope.state, appId, i, str);
            if (!IntExtensionsKt.hasAnyBit(permissionFlags, SystemService.PHASE_LOCK_SETTINGS_READY)) {
                this.policy.updatePermissionFlags(mutateStateScope, appId, i, str, -1, (permissionFlags | 16) & (-7345153));
                return;
            }
            Slog.v("AppIdPermissionUpgrade", "Not allowed to grant " + str + " to package " + packageState.getPackageName());
        }
    }
}
