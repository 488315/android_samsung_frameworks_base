package com.android.server.pm.permission;

import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.SyncNotedAppOp;
import android.content.AttributionSource;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.IBinder;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.DodecFunction;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.UndecFunction;
import com.android.server.LocalServices;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AccessCheckDelegate$AccessCheckDelegateImpl
        implements AppOpsManagerInternal.CheckOpsDelegate {
    public boolean mDelegateAllPermissions;
    public int mDelegateAndOwnerUid;
    public String mDelegatePackage;
    public String[] mDelegatePermissions;
    public SparseArray mOverridePermissionStates;

    public final void addOverridePermissionState(int i, int i2, String str, int i3) {
        ArrayMap arrayMap;
        if (this.mOverridePermissionStates == null) {
            this.mDelegateAndOwnerUid = i;
            this.mOverridePermissionStates = new SparseArray();
        }
        int indexOfKey = this.mOverridePermissionStates.indexOfKey(i2);
        if (indexOfKey < 0) {
            arrayMap = new ArrayMap();
            this.mOverridePermissionStates.put(i2, arrayMap);
        } else {
            arrayMap = (ArrayMap) this.mOverridePermissionStates.valueAt(indexOfKey);
        }
        arrayMap.put(str, Integer.valueOf(i3));
        PackageManager.invalidatePackageInfoCache();
    }

    public final int checkAudioOperation(
            int i, int i2, int i3, String str, QuadFunction quadFunction) {
        if (i3 != this.mDelegateAndOwnerUid || !isDelegateOp(i)) {
            return ((Integer)
                            quadFunction.apply(
                                    Integer.valueOf(i),
                                    Integer.valueOf(i2),
                                    Integer.valueOf(i3),
                                    str))
                    .intValue();
        }
        int uid = UserHandle.getUid(UserHandle.getUserId(i3), 2000);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((Integer)
                            quadFunction.apply(
                                    Integer.valueOf(i),
                                    Integer.valueOf(i2),
                                    Integer.valueOf(uid),
                                    "com.android.shell"))
                    .intValue();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int checkOperation(
            int i, int i2, String str, String str2, int i3, boolean z, HexFunction hexFunction) {
        if (i2 != this.mDelegateAndOwnerUid || !isDelegateOp(i)) {
            return ((Integer)
                            hexFunction.apply(
                                    Integer.valueOf(i),
                                    Integer.valueOf(i2),
                                    str,
                                    str2,
                                    Integer.valueOf(i3),
                                    Boolean.valueOf(z)))
                    .intValue();
        }
        int uid = UserHandle.getUid(UserHandle.getUserId(i2), 2000);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((Integer)
                            hexFunction.apply(
                                    Integer.valueOf(i),
                                    Integer.valueOf(uid),
                                    "com.android.shell",
                                    (Object) null,
                                    Integer.valueOf(i3),
                                    Boolean.valueOf(z)))
                    .intValue();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int checkPermission(
            String str,
            String str2,
            String str3,
            int i,
            PermissionManagerService$$ExternalSyntheticLambda0
                    permissionManagerService$$ExternalSyntheticLambda0) {
        int packageUid;
        Map map;
        if (!TextUtils.equals(this.mDelegatePackage, str)
                || "com.android.shell".equals(str)
                || !isDelegatePermission(str2)) {
            return (this.mOverridePermissionStates == null
                            || (packageUid =
                                            ((PackageManagerInternal)
                                                            LocalServices.getService(
                                                                    PackageManagerInternal.class))
                                                    .getPackageUid(str, 0L, i))
                                    < 0
                            || (map = (Map) this.mOverridePermissionStates.get(packageUid)) == null
                            || !map.containsKey(str2))
                    ? ((Integer)
                                    permissionManagerService$$ExternalSyntheticLambda0.apply(
                                            str, str2, str3, Integer.valueOf(i)))
                            .intValue()
                    : ((Integer) map.get(str2)).intValue();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return checkPermission(
                    "com.android.shell",
                    str2,
                    str3,
                    i,
                    permissionManagerService$$ExternalSyntheticLambda0);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int checkUidPermission(
            int i,
            String str,
            String str2,
            PermissionManagerService$$ExternalSyntheticLambda1
                    permissionManagerService$$ExternalSyntheticLambda1) {
        Map map;
        if (i != this.mDelegateAndOwnerUid || i == 2000 || !isDelegatePermission(str)) {
            SparseArray sparseArray = this.mOverridePermissionStates;
            return (sparseArray == null
                            || (map = (Map) sparseArray.get(i)) == null
                            || !map.containsKey(str))
                    ? ((Integer)
                                    permissionManagerService$$ExternalSyntheticLambda1.apply(
                                            Integer.valueOf(i), str, str2))
                            .intValue()
                    : ((Integer) map.get(str)).intValue();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return checkUidPermission(
                    2000, str, str2, permissionManagerService$$ExternalSyntheticLambda1);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void finishOperation(
            IBinder iBinder,
            int i,
            int i2,
            String str,
            String str2,
            int i3,
            HexConsumer hexConsumer) {
        if (i2 != this.mDelegateAndOwnerUid || !isDelegateOp(i)) {
            hexConsumer.accept(
                    iBinder,
                    Integer.valueOf(i),
                    Integer.valueOf(i2),
                    str,
                    str2,
                    Integer.valueOf(i3));
            return;
        }
        int uid = UserHandle.getUid(UserHandle.getUserId(i2), 2000);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            hexConsumer.accept(
                    iBinder,
                    Integer.valueOf(i),
                    Integer.valueOf(uid),
                    "com.android.shell",
                    str2,
                    Integer.valueOf(i3));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void finishProxyOperation(
            IBinder iBinder,
            int i,
            AttributionSource attributionSource,
            boolean z,
            QuadFunction quadFunction) {
        if (attributionSource.getUid() == this.mDelegateAndOwnerUid && isDelegateOp(i)) {
            int uid = UserHandle.getUid(UserHandle.getUserId(attributionSource.getUid()), 2000);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                quadFunction.apply(
                        iBinder,
                        Integer.valueOf(i),
                        new AttributionSource(
                                uid,
                                -1,
                                "com.android.shell",
                                attributionSource.getAttributionTag(),
                                attributionSource.getToken(),
                                null,
                                attributionSource.getDeviceId(),
                                attributionSource.getNext()),
                        Boolean.valueOf(z));
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        quadFunction.apply(iBinder, Integer.valueOf(i), attributionSource, Boolean.valueOf(z));
    }

    public final boolean hasDelegateOrOverrides() {
        return (!this.mDelegateAllPermissions
                        && this.mDelegatePermissions == null
                        && this.mOverridePermissionStates == null)
                ? false
                : true;
    }

    public final boolean isDelegateAndOwnerUid(int i) {
        return i == this.mDelegateAndOwnerUid;
    }

    public final boolean isDelegateOp(int i) {
        String opToPermission;
        if (this.mDelegateAllPermissions
                || (opToPermission = AppOpsManager.opToPermission(i)) == null) {
            return true;
        }
        return isDelegatePermission(opToPermission);
    }

    public final boolean isDelegatePermission(String str) {
        return this.mDelegateAndOwnerUid != -1
                && (this.mDelegateAllPermissions
                        || ArrayUtils.contains(this.mDelegatePermissions, str));
    }

    public final SyncNotedAppOp noteOperation(
            int i,
            int i2,
            String str,
            String str2,
            int i3,
            boolean z,
            String str3,
            boolean z2,
            OctFunction octFunction) {
        if (i2 != this.mDelegateAndOwnerUid || !isDelegateOp(i)) {
            return (SyncNotedAppOp)
                    octFunction.apply(
                            Integer.valueOf(i),
                            Integer.valueOf(i2),
                            str,
                            str2,
                            Integer.valueOf(i3),
                            Boolean.valueOf(z),
                            str3,
                            Boolean.valueOf(z2));
        }
        int uid = UserHandle.getUid(UserHandle.getUserId(i2), 2000);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (SyncNotedAppOp)
                    octFunction.apply(
                            Integer.valueOf(i),
                            Integer.valueOf(uid),
                            "com.android.shell",
                            str2,
                            Integer.valueOf(i3),
                            Boolean.valueOf(z),
                            str3,
                            Boolean.valueOf(z2));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final SyncNotedAppOp noteProxyOperation(
            int i,
            AttributionSource attributionSource,
            boolean z,
            String str,
            boolean z2,
            boolean z3,
            HexFunction hexFunction) {
        if (attributionSource.getUid() != this.mDelegateAndOwnerUid || !isDelegateOp(i)) {
            return (SyncNotedAppOp)
                    hexFunction.apply(
                            Integer.valueOf(i),
                            attributionSource,
                            Boolean.valueOf(z),
                            str,
                            Boolean.valueOf(z2),
                            Boolean.valueOf(z3));
        }
        int uid = UserHandle.getUid(UserHandle.getUserId(attributionSource.getUid()), 2000);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (SyncNotedAppOp)
                    hexFunction.apply(
                            Integer.valueOf(i),
                            new AttributionSource(
                                    uid,
                                    -1,
                                    "com.android.shell",
                                    attributionSource.getAttributionTag(),
                                    attributionSource.getToken(),
                                    null,
                                    attributionSource.getDeviceId(),
                                    attributionSource.getNext()),
                            Boolean.valueOf(z),
                            str,
                            Boolean.valueOf(z2),
                            Boolean.valueOf(z3));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final SyncNotedAppOp startOperation(
            IBinder iBinder,
            int i,
            int i2,
            String str,
            String str2,
            int i3,
            boolean z,
            boolean z2,
            String str3,
            boolean z3,
            int i4,
            int i5,
            DodecFunction dodecFunction) {
        if (i2 == this.mDelegateAndOwnerUid && isDelegateOp(i)) {
            int uid = UserHandle.getUid(UserHandle.getUserId(i2), 2000);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return (SyncNotedAppOp)
                        dodecFunction.apply(
                                iBinder,
                                Integer.valueOf(i),
                                Integer.valueOf(uid),
                                "com.android.shell",
                                str2,
                                Integer.valueOf(i3),
                                Boolean.valueOf(z),
                                Boolean.valueOf(z2),
                                str3,
                                Boolean.valueOf(z3),
                                Integer.valueOf(i4),
                                Integer.valueOf(i5));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return (SyncNotedAppOp)
                dodecFunction.apply(
                        iBinder,
                        Integer.valueOf(i),
                        Integer.valueOf(i2),
                        str,
                        str2,
                        Integer.valueOf(i3),
                        Boolean.valueOf(z),
                        Boolean.valueOf(z2),
                        str3,
                        Boolean.valueOf(z3),
                        Integer.valueOf(i4),
                        Integer.valueOf(i5));
    }

    public final SyncNotedAppOp startProxyOperation(
            IBinder iBinder,
            int i,
            AttributionSource attributionSource,
            boolean z,
            boolean z2,
            String str,
            boolean z3,
            boolean z4,
            int i2,
            int i3,
            int i4,
            UndecFunction undecFunction) {
        if (attributionSource.getUid() == this.mDelegateAndOwnerUid && isDelegateOp(i)) {
            int uid = UserHandle.getUid(UserHandle.getUserId(attributionSource.getUid()), 2000);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return (SyncNotedAppOp)
                        undecFunction.apply(
                                iBinder,
                                Integer.valueOf(i),
                                new AttributionSource(
                                        uid,
                                        -1,
                                        "com.android.shell",
                                        attributionSource.getAttributionTag(),
                                        attributionSource.getToken(),
                                        null,
                                        attributionSource.getDeviceId(),
                                        attributionSource.getNext()),
                                Boolean.valueOf(z),
                                Boolean.valueOf(z2),
                                str,
                                Boolean.valueOf(z3),
                                Boolean.valueOf(z4),
                                Integer.valueOf(i2),
                                Integer.valueOf(i3),
                                Integer.valueOf(i4));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return (SyncNotedAppOp)
                undecFunction.apply(
                        iBinder,
                        Integer.valueOf(i),
                        attributionSource,
                        Boolean.valueOf(z),
                        Boolean.valueOf(z2),
                        str,
                        Boolean.valueOf(z3),
                        Boolean.valueOf(z4),
                        Integer.valueOf(i2),
                        Integer.valueOf(i3),
                        Integer.valueOf(i4));
    }
}
