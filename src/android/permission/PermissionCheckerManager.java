package android.permission;

import android.app.AppOpsManager;
import android.content.AttributionSourceState;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.permission.IPermissionChecker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public class PermissionCheckerManager {
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;
    private final Context mContext;
    private final PackageManager mPackageManager;
    private final IPermissionChecker mService = IPermissionChecker.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.PERMISSION_CHECKER_SERVICE));

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    public PermissionCheckerManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(attributionSourceState);
        if (AppOpsManager.permissionToOpCode(str) == -1) {
            if (!z3) {
                return this.mContext.checkPermission(str, attributionSourceState.pid, attributionSourceState.uid) == 0 ? 0 : 2;
            }
            if (attributionSourceState.next != null && attributionSourceState.next.length > 0) {
                return this.mContext.checkPermission(str, attributionSourceState.next[0].pid, attributionSourceState.next[0].uid) == 0 ? 0 : 2;
            }
        }
        try {
            return this.mService.checkPermission(str, attributionSourceState, str2, z, z2, z3, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 2;
        }
    }

    public void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) {
        Objects.requireNonNull(attributionSourceState);
        try {
            this.mService.finishDataDelivery(i, attributionSourceState, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) {
        Objects.requireNonNull(attributionSourceState);
        try {
            return this.mService.checkOp(i, attributionSourceState, str, z, z2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 2;
        }
    }
}
