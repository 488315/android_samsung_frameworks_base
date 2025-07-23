package android.content;

import android.app.AppOpsManager;
import android.os.Binder;
import android.os.Process;
import android.permission.IPermissionChecker;
import android.permission.PermissionCheckerManager;

/* loaded from: classes.dex */
public final class PermissionChecker {
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;
    public static final int PID_UNKNOWN = -1;
    private static volatile IPermissionChecker sService;

    private PermissionChecker() {
    }

    public static int checkPermissionForDataDelivery(Context context, String str, int i, int i2, String str2, String str3, String str4, boolean z) {
        return checkPermissionForDataDelivery(context, str, i, new AttributionSource(i2, i, str2, str3), str4, z);
    }

    public static int checkPermissionForDataDelivery(Context context, String str, int i, int i2, String str2, String str3, String str4) {
        return checkPermissionForDataDelivery(context, str, i, i2, str2, str3, str4, false);
    }

    public static int checkPermissionForDataDeliveryFromDataSource(Context context, String str, int i, AttributionSource attributionSource, String str2) {
        return checkPermissionForDataDeliveryCommon(context, str, attributionSource, str2, false, true);
    }

    public static int checkPermissionForDataDelivery(Context context, String str, int i, AttributionSource attributionSource, String str2) {
        return checkPermissionForDataDelivery(context, str, i, attributionSource, str2, false);
    }

    public static int checkPermissionForDataDelivery(Context context, String str, int i, AttributionSource attributionSource, String str2, boolean z) {
        return checkPermissionForDataDeliveryCommon(context, str, attributionSource, str2, z, false);
    }

    private static int checkPermissionForDataDeliveryCommon(Context context, String str, AttributionSource attributionSource, String str2, boolean z, boolean z2) {
        return ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).checkPermission(str, attributionSource.asState(), str2, true, z, z2, -1);
    }

    public static int checkPermissionAndStartDataDelivery(Context context, String str, AttributionSource attributionSource, String str2) {
        return ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).checkPermission(str, attributionSource.asState(), str2, true, true, false, -1);
    }

    public static int startOpForDataDelivery(Context context, String str, AttributionSource attributionSource, String str2) {
        return ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).checkOp(AppOpsManager.strOpToOp(str), attributionSource.asState(), str2, true, true);
    }

    public static void finishDataDelivery(Context context, String str, AttributionSource attributionSource) {
        ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).finishDataDelivery(AppOpsManager.strOpToOp(str), attributionSource.asState(), false);
    }

    public static void finishDataDeliveryFromDatasource(Context context, String str, AttributionSource attributionSource) {
        ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).finishDataDelivery(AppOpsManager.strOpToOp(str), attributionSource.asState(), true);
    }

    public static int checkOpForPreflight(Context context, String str, AttributionSource attributionSource, String str2) {
        return ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).checkOp(AppOpsManager.strOpToOp(str), attributionSource.asState(), str2, false, false);
    }

    public static int checkOpForDataDelivery(Context context, String str, AttributionSource attributionSource, String str2) {
        return ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).checkOp(AppOpsManager.strOpToOp(str), attributionSource.asState(), str2, true, false);
    }

    public static int checkPermissionForPreflight(Context context, String str, int i, int i2, String str2) {
        return checkPermissionForPreflight(context, str, new AttributionSource(i2, str2, null));
    }

    public static int checkPermissionForPreflight(Context context, String str, AttributionSource attributionSource) {
        return ((PermissionCheckerManager) context.getSystemService(PermissionCheckerManager.class)).checkPermission(str, attributionSource.asState(), null, false, false, false, -1);
    }

    public static int checkSelfPermissionForDataDelivery(Context context, String str, String str2) {
        return checkPermissionForDataDelivery(context, str, Process.myPid(), Process.myUid(), context.getPackageName(), context.getAttributionTag(), str2, false);
    }

    public static int checkSelfPermissionForPreflight(Context context, String str) {
        return checkPermissionForPreflight(context, str, Process.myPid(), Process.myUid(), context.getPackageName());
    }

    public static int checkCallingPermissionForDataDelivery(Context context, String str, String str2, String str3, String str4) {
        if (Binder.getCallingPid() == Process.myPid()) {
            return 2;
        }
        return checkPermissionForDataDelivery(context, str, Binder.getCallingPid(), Binder.getCallingUid(), str2, str3, str4, false);
    }

    public static int checkCallingPermissionForPreflight(Context context, String str, String str2) {
        if (Binder.getCallingPid() == Process.myPid()) {
            return 2;
        }
        return checkPermissionForPreflight(context, str, Binder.getCallingPid(), Binder.getCallingUid(), str2);
    }

    public static int checkCallingOrSelfPermissionForDataDelivery(Context context, String str, String str2, String str3, String str4) {
        if (Binder.getCallingPid() == Process.myPid()) {
            str2 = context.getPackageName();
        }
        String str5 = str2;
        if (Binder.getCallingPid() == Process.myPid()) {
            str3 = context.getAttributionTag();
        }
        return checkPermissionForDataDelivery(context, str, Binder.getCallingPid(), Binder.getCallingUid(), str5, str3, str4, false);
    }

    public static int checkCallingOrSelfPermissionForPreflight(Context context, String str) {
        return checkPermissionForPreflight(context, str, Binder.getCallingPid(), Binder.getCallingUid(), Binder.getCallingPid() == Process.myPid() ? context.getPackageName() : null);
    }
}
