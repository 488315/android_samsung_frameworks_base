package android.service.credentials;

import android.content.Context;

/* loaded from: classes3.dex */
public class PermissionUtils {
    public static boolean hasPermission(Context context, String str, String str2) {
        return context.getPackageManager().checkPermission(str2, str) == 0;
    }
}
