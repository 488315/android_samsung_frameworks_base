package android.p009os;

import android.app.AppOpsManager;
import android.content.AttributionSource;
import android.content.Context;
import android.content.PermissionChecker;

/* loaded from: classes3.dex */
public class PermissionEnforcer {
  private static final String ACCESS_DENIED = "Access denied, requires: ";
  private final Context mContext;

  protected PermissionEnforcer() {
    this.mContext = null;
  }

  public PermissionEnforcer(Context context) {
    this.mContext = context;
  }

  protected int checkPermission(String permission, AttributionSource source) {
    return PermissionChecker.checkPermissionForDataDelivery(
        this.mContext, permission, -1, source, "");
  }

  protected int checkPermission(String permission, int pid, int uid) {
    if (this.mContext.checkPermission(permission, pid, uid) == 0) {
      return 0;
    }
    return 2;
  }

  private boolean anyAppOps(String[] permissions) {
    for (String permission : permissions) {
      if (AppOpsManager.permissionToOpCode(permission) != -1) {
        return true;
      }
    }
    return false;
  }

  public void enforcePermission(String permission, AttributionSource source)
      throws SecurityException {
    int result = checkPermission(permission, source);
    if (result != 0) {
      throw new SecurityException(ACCESS_DENIED + permission);
    }
  }

  public void enforcePermission(String permission, int pid, int uid) throws SecurityException {
    if (AppOpsManager.permissionToOpCode(permission) != -1) {
      AttributionSource source = new AttributionSource(uid, null, null);
      enforcePermission(permission, source);
    } else {
      int result = checkPermission(permission, pid, uid);
      if (result != 0) {
        throw new SecurityException(ACCESS_DENIED + permission);
      }
    }
  }

  public void enforcePermissionAllOf(String[] permissions, AttributionSource source)
      throws SecurityException {
    for (String permission : permissions) {
      int result = checkPermission(permission, source);
      if (result != 0) {
        throw new SecurityException(
            "Access denied, requires: allOf={" + String.join(", ", permissions) + "}");
      }
    }
  }

  public void enforcePermissionAllOf(String[] permissions, int pid, int uid)
      throws SecurityException {
    if (anyAppOps(permissions)) {
      AttributionSource source = new AttributionSource(uid, null, null);
      enforcePermissionAllOf(permissions, source);
      return;
    }
    for (String permission : permissions) {
      int result = checkPermission(permission, pid, uid);
      if (result != 0) {
        throw new SecurityException(
            "Access denied, requires: allOf={" + String.join(", ", permissions) + "}");
      }
    }
  }

  public void enforcePermissionAnyOf(String[] permissions, AttributionSource source)
      throws SecurityException {
    for (String permission : permissions) {
      int result = checkPermission(permission, source);
      if (result == 0) {
        return;
      }
    }
    throw new SecurityException(
        "Access denied, requires: anyOf={" + String.join(", ", permissions) + "}");
  }

  public void enforcePermissionAnyOf(String[] permissions, int pid, int uid)
      throws SecurityException {
    if (anyAppOps(permissions)) {
      AttributionSource source = new AttributionSource(uid, null, null);
      enforcePermissionAnyOf(permissions, source);
      return;
    }
    for (String permission : permissions) {
      int result = checkPermission(permission, pid, uid);
      if (result == 0) {
        return;
      }
    }
    throw new SecurityException(
        "Access denied, requires: anyOf={" + String.join(", ", permissions) + "}");
  }

  public static PermissionEnforcer fromContext(Context context) {
    return (PermissionEnforcer) context.getSystemService(PermissionEnforcer.class);
  }
}
