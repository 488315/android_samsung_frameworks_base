package com.android.server.permission.access.permission;

import android.content.pm.PermissionInfo;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.util.Arrays;
import libcore.util.EmptyArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Permission {
    public final int appId;
    public final boolean areGidsPerUser;
    public final int[] gids;
    public final boolean isReconciled;
    public final PermissionInfo permissionInfo;
    public final int type;

    public /* synthetic */ Permission(PermissionInfo permissionInfo, boolean z, int i, int i2) {
        this(permissionInfo, z, i, i2, EmptyArray.INT, false);
    }

    public Permission(PermissionInfo permissionInfo, boolean z, int i, int i2, int[] iArr, boolean z2) {
        this.permissionInfo = permissionInfo;
        this.isReconciled = z;
        this.type = i;
        this.appId = i2;
        this.gids = iArr;
        this.areGidsPerUser = z2;
    }

    public static Permission copy$default(Permission permission, PermissionInfo permissionInfo, boolean z, int i) {
        return new Permission(permissionInfo, z, permission.type, i, permission.gids, permission.areGidsPerUser);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Permission)) {
            return false;
        }
        Permission permission = (Permission) obj;
        return Intrinsics.areEqual(this.permissionInfo, permission.permissionInfo) && this.isReconciled == permission.isReconciled && this.type == permission.type && this.appId == permission.appId && Intrinsics.areEqual(this.gids, permission.gids) && this.areGidsPerUser == permission.areGidsPerUser;
    }

    public final int[] getGidsForUser(int i) {
        boolean z = this.areGidsPerUser;
        int[] iArr = this.gids;
        if (!z) {
            int[] copyOf = Arrays.copyOf(iArr, iArr.length);
            Intrinsics.checkNotNullExpressionValue("copyOf(...)", copyOf);
            return copyOf;
        }
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr2[i2] = UserHandle.getUid(i, iArr[i2]);
        }
        return iArr2;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.areGidsPerUser) + ((Arrays.hashCode(this.gids) + ((Integer.hashCode(this.appId) + ((Integer.hashCode(this.type) + ((Boolean.hashCode(this.isReconciled) + (this.permissionInfo.hashCode() * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        PermissionInfo permissionInfo = this.permissionInfo;
        String arrays = Arrays.toString(this.gids);
        StringBuilder sb = new StringBuilder("Permission(permissionInfo=");
        sb.append(permissionInfo);
        sb.append(", isReconciled=");
        sb.append(this.isReconciled);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", appId=");
        AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.appId, ", gids=", arrays, ", areGidsPerUser=", sb);
        return OptionalBool$$ExternalSyntheticOutline0.m(")", sb, this.areGidsPerUser);
    }
}
