package com.android.internal.telephony;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes4.dex */
public class CellBroadcastUtils {
    private static final String TAG = "CellBroadcastUtils";
    private static final boolean VDBG = false;

    public static String getDefaultCellBroadcastReceiverPackageName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ResolveInfo resolveActivity = packageManager.resolveActivity(new Intent(Telephony.Sms.Intents.SMS_CB_RECEIVED_ACTION), 1048576);
        if (resolveActivity == null) {
            Log.e(TAG, "getDefaultCellBroadcastReceiverPackageName: no package found");
            return null;
        }
        String str = resolveActivity.activityInfo.applicationInfo.packageName;
        if (!TextUtils.isEmpty(str) && packageManager.checkPermission(Manifest.permission.READ_CELL_BROADCASTS, str) != -1) {
            return str;
        }
        Log.e(TAG, "getDefaultCellBroadcastReceiverPackageName: returning null; permission check failed for : " + str);
        return null;
    }

    public static ComponentName getDefaultCellBroadcastAlertDialogComponent(Context context) {
        String defaultCellBroadcastReceiverPackageName = getDefaultCellBroadcastReceiverPackageName(context);
        if (TextUtils.isEmpty(defaultCellBroadcastReceiverPackageName)) {
            return null;
        }
        return ComponentName.createRelative(defaultCellBroadcastReceiverPackageName, "com.android.cellbroadcastreceiver.CellBroadcastAlertDialog");
    }
}
