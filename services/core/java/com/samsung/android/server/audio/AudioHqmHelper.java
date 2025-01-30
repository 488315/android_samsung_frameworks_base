package com.samsung.android.server.audio;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioSystem;
import android.os.SemHqmManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.server.backup.BackupManagerConstants;
import com.samsung.android.media.AudioParameter;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class AudioHqmHelper {
    public static final String[][] BIG_DATA = {new String[]{"HW_CSPT", "HW_CSPC", "HW_CRET", "HW_CREC", "HW_CEAT", "HW_CEAC", "HW_CBTT", "HW_CBTC", "HW_CUST", "HW_CUSC"}, new String[]{"HW_MSPT", "HW_MSPC", "HW_MEAT", "HW_MEAC", "HW_MBTT", "HW_MBTC", "HW_MUST", "HW_MUSC", "HW_MUHT", "HW_MUHC", "HW_DLBY", "HW_3EJC", "HW_4EJC", "MU_MSVL", "MU_MEVL", "MU_MUHV"}, new String[]{"FW_VSMC"}, new String[]{"RCV_TEMP_MAX", "SPK_TEMP_MAX", "RCV_TEMP_OVERCNT", "SPK_TEMP_OVERCNT", "RCV_EXCU_MAX", "SPK_EXCU_MAX", "RCV_EXCU_OVERCNT", "SPK_EXCU_OVERCNT"}};
    public static int mAudioServerResetCount = 0;
    public static boolean mAudioServerResetCountMaxLimit = false;
    public static int mPreAudioServerResetCount;

    public static void startLogging(Context context) {
        ((AlarmManager) context.getSystemService("alarm")).setRepeating(3, SystemClock.elapsedRealtime() + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, PendingIntent.getBroadcast(context, 0, new Intent("com.sec.media.action.AUDIOCORE_LOGGING"), 67108864));
    }

    public static void sendHqmData(Context context, boolean z) {
        AudioParameter audioParameter = new AudioParameter(AudioSystem.getParameters(z ? "l_bigdata_logging;l_bigdata_app" : "l_bigdata_logging"));
        int i = 0;
        while (i < 4) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (String str : BIG_DATA[i]) {
                String str2 = audioParameter.get(str);
                if (str2 != null) {
                    arrayList.add(str);
                    arrayList2.add(str2);
                }
            }
            if (arrayList.size() != 0) {
                sendLoggingDataToHQM(context, arrayList, arrayList2, i == 3 ? "AUDI" : "FWAC");
            }
            i++;
        }
        loggingAudioServerResetCount(context, z);
    }

    public static String createJSONObj(ArrayList arrayList, ArrayList arrayList2) {
        int size = arrayList.size();
        try {
            JSONObject jSONObject = new JSONObject();
            for (int i = 0; i < size; i++) {
                jSONObject.put((String) arrayList.get(i), arrayList2.get(i));
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendLoggingDataToHQM(Context context, ArrayList arrayList, ArrayList arrayList2, String str) {
        String createJSONObj = createJSONObj(arrayList, arrayList2);
        if (createJSONObj == null) {
            return;
        }
        String replaceAll = createJSONObj.replaceAll("\\{", "").replaceAll("\\}", "");
        SemHqmManager semHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        Log.v("AS.AudioHqmHelper", "sendLoggingDataToHQM() Server update !!!");
        semHqmManager.sendHWParamToHQM(0, "Audio", str, "sm", "0.0", "sec", "", replaceAll, "");
    }

    public static void increaseAudioServerResetCount() {
        int i = mAudioServerResetCount + 1;
        mAudioServerResetCount = i;
        if (i == Integer.MAX_VALUE) {
            mAudioServerResetCount = i - mPreAudioServerResetCount;
            mPreAudioServerResetCount = 0;
            mAudioServerResetCountMaxLimit = true;
        }
    }

    public static int getAudioServerResetCount() {
        if (mAudioServerResetCountMaxLimit) {
            return Integer.MAX_VALUE;
        }
        return mAudioServerResetCount;
    }

    public static void loggingAudioServerResetCount(Context context, boolean z) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = mAudioServerResetCount;
        int i2 = i - mPreAudioServerResetCount;
        if (!z) {
            mPreAudioServerResetCount = i;
        }
        arrayList.add("FW_ASRC");
        arrayList2.add(Integer.toString(i2));
        sendLoggingDataToHQM(context, arrayList, arrayList2, "FWAC");
    }
}
