package com.samsung.android.server.audio;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/* loaded from: classes2.dex */
public abstract class RecordingPopupHelper {
    public static int sOldPortId = -1;
    public static long sPreviousTime = -1;

    public static void showRecordingPopup(Context context, int i, int i2) {
        String string;
        String appName = getAppName(context, i);
        if (i2 == 22 || i2 == 3) {
            string = context.getString(17042376, appName, context.getString(R.string.mediasize_na_arch_d));
        } else if (i2 == 7) {
            string = context.getString(17042375, appName);
        } else if (i2 == 15) {
            string = context.getString(17042376, appName, context.getString(17042374));
        } else {
            string = context.getString(17042373, appName);
        }
        Toast.makeText(context, string, 0).show();
    }

    public static void notifyRecordingPopup(Handler handler, int i, int i2, int i3) {
        if (handler == null) {
            Log.w("AS.RecordingPopupHelper", "[RECORDING POPUP] There is no audio handler");
            return;
        }
        if (sOldPortId != i2) {
            sOldPortId = i2;
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - sPreviousTime;
            sPreviousTime = currentTimeMillis;
            Log.d("AS.RecordingPopupHelper", "[RECORDING POPUP] notifyRecordingPopup uid " + i + " portId " + i2 + " periodTime " + j);
            handler.removeMessages(2772);
            if (j > 4000) {
                handler.sendMessageDelayed(handler.obtainMessage(2772, i, i3), 1000L);
            } else {
                handler.sendMessageDelayed(handler.obtainMessage(2772, i, i3), 3000L);
            }
        }
    }

    public static String getAppName(Context context, int i) {
        PackageManager packageManager = context.getPackageManager();
        String nameForUid = packageManager.getNameForUid(i);
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(nameForUid, 0)).toString();
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("AS.RecordingPopupHelper", "[RECORDING POPUP] getAppName can't find the name of " + nameForUid);
            Resources system = Resources.getSystem();
            if (i == 1000) {
                return system.getString(R.string.call_notification_hang_up_action);
            }
            return system.getString(R.string.unknownName);
        }
    }
}
