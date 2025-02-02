package androidx.core.content;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Process;
import androidx.core.app.AppOpsManagerCompat;
import java.util.Objects;

/* loaded from: classes.dex */
public final class PermissionChecker {
    public static int checkSelfPermission(Context context, String str) {
        int myPid = Process.myPid();
        int myUid = Process.myUid();
        String packageName = context.getPackageName();
        if (context.checkPermission(str, myPid, myUid) == -1) {
            return -1;
        }
        String permissionToOp = AppOpsManager.permissionToOp(str);
        if (permissionToOp != null) {
            if (packageName == null) {
                String[] packagesForUid = context.getPackageManager().getPackagesForUid(myUid);
                if (packagesForUid == null || packagesForUid.length <= 0) {
                    return -1;
                }
                packageName = packagesForUid[0];
            }
            if ((Process.myUid() == myUid && Objects.equals(context.getPackageName(), packageName) ? AppOpsManagerCompat.checkOrNoteProxyOp(context, myUid, permissionToOp, packageName) : ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOpNoThrow(permissionToOp, packageName)) != 0) {
                return -2;
            }
        }
        return 0;
    }
}
