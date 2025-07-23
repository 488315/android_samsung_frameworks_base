package android.os;

import android.app.AppOpsManager;
import android.content.AttributionSource;
import android.content.Context;
import android.content.PermissionChecker;

/* loaded from: classes3.dex */
public class PermissionEnforcer {
    private static final String ACCESS_DENIED = "Access denied, requires: ";
    private final Context mContext;

    private static int permissionToOpCode$ravenwood(String str) {
        return -1;
    }

    protected PermissionEnforcer() {
        this.mContext = null;
    }

    public PermissionEnforcer(Context context) {
        this.mContext = context;
    }

    protected int checkPermission(String str, AttributionSource attributionSource) {
        return PermissionChecker.checkPermissionForDataDelivery(this.mContext, str, -1, attributionSource, "");
    }

    protected int checkPermission(String str, int i, int i2) {
        return this.mContext.checkPermission(str, i, i2) == 0 ? 0 : 2;
    }

    private static int permissionToOpCode(String str) {
        return AppOpsManager.permissionToOpCode(str);
    }

    private boolean anyAppOps(String[] strArr) {
        for (String str : strArr) {
            if (permissionToOpCode(str) != -1) {
                return true;
            }
        }
        return false;
    }

    public void enforcePermission(String str, AttributionSource attributionSource) throws SecurityException {
        if (checkPermission(str, attributionSource) == 0) {
            return;
        }
        throw new SecurityException(ACCESS_DENIED + str);
    }

    public void enforcePermission(String str, int i, int i2) throws SecurityException {
        if (permissionToOpCode(str) != -1) {
            enforcePermission(str, new AttributionSource(i2, null, null));
        } else {
            if (checkPermission(str, i, i2) == 0) {
                return;
            }
            throw new SecurityException(ACCESS_DENIED + str);
        }
    }

    public void enforcePermissionAllOf(String[] strArr, AttributionSource attributionSource) throws SecurityException {
        for (String str : strArr) {
            if (checkPermission(str, attributionSource) != 0) {
                throw new SecurityException("Access denied, requires: allOf={" + String.join(", ", strArr) + "}");
            }
        }
    }

    public void enforcePermissionAllOf(String[] strArr, int i, int i2) throws SecurityException {
        if (anyAppOps(strArr)) {
            enforcePermissionAllOf(strArr, new AttributionSource(i2, null, null));
            return;
        }
        for (String str : strArr) {
            if (checkPermission(str, i, i2) != 0) {
                throw new SecurityException("Access denied, requires: allOf={" + String.join(", ", strArr) + "}");
            }
        }
    }

    public void enforcePermissionAnyOf(String[] strArr, AttributionSource attributionSource) throws SecurityException {
        for (String str : strArr) {
            if (checkPermission(str, attributionSource) == 0) {
                return;
            }
        }
        throw new SecurityException("Access denied, requires: anyOf={" + String.join(", ", strArr) + "}");
    }

    public void enforcePermissionAnyOf(String[] strArr, int i, int i2) throws SecurityException {
        if (anyAppOps(strArr)) {
            enforcePermissionAnyOf(strArr, new AttributionSource(i2, null, null));
            return;
        }
        for (String str : strArr) {
            if (checkPermission(str, i, i2) == 0) {
                return;
            }
        }
        throw new SecurityException("Access denied, requires: anyOf={" + String.join(", ", strArr) + "}");
    }

    public static PermissionEnforcer fromContext(Context context) {
        return (PermissionEnforcer) context.getSystemService(Context.PERMISSION_ENFORCER_SERVICE);
    }
}
