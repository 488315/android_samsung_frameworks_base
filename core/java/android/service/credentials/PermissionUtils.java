package android.service.credentials;

import android.content.Context;

/* loaded from: classes3.dex */
public class PermissionUtils {
    public static boolean hasPermission(Context context, String packageName, String permission) {
        return context.getPackageManager().checkPermission(permission, packageName) == 0;
    }
}
