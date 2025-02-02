package androidx.core.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;

/* loaded from: classes.dex */
public final class AppOpsManagerCompat {
    public static int checkOrNoteProxyOp(Context context, int i, String str, String str2) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        int checkOpNoThrow = appOpsManager == null ? 1 : appOpsManager.checkOpNoThrow(str, Binder.getCallingUid(), str2);
        if (checkOpNoThrow != 0) {
            return checkOpNoThrow;
        }
        String opPackageName = context.getOpPackageName();
        if (appOpsManager == null) {
            return 1;
        }
        return appOpsManager.checkOpNoThrow(str, i, opPackageName);
    }
}
