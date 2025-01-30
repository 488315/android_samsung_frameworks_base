package com.samsung.android.server.vibrator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SemHqmManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.server.backup.BackupManagerConstants;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VibratorHqmHelper {
    public static final String[] BIG_DATA = {"FW_RVPC", "FW_AVPC", "FW_NVPC", "FW_TVPC", "FW_EVPC"};
    public static VibratorHqmHelper sInstance;
    public AlarmManager mAlarmManager;
    public SemHqmManager mSemHqmManager;

    public VibratorHqmHelper(Context context) {
        this.mSemHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
    }

    public static VibratorHqmHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new VibratorHqmHelper(context);
        }
        return sInstance;
    }

    public void startLogging(Context context) {
        this.mAlarmManager.setRepeating(3, SystemClock.elapsedRealtime() + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, PendingIntent.getBroadcast(context, 0, new Intent("com.sec.media.action.VIBRTOR_LOGGING"), 67108864));
    }

    public void sendHqmVibratorData(VibratorHqmData vibratorHqmData) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        while (true) {
            String[] strArr = BIG_DATA;
            if (i < strArr.length) {
                String num = Integer.toString(vibratorHqmData.get(strArr[i]));
                if (num != null) {
                    arrayList.add(strArr[i]);
                    arrayList2.add(num);
                }
                i++;
            } else {
                sendLoggingDataToHQM(arrayList, arrayList2);
                return;
            }
        }
    }

    public final String convertToBigDataFormat(ArrayList arrayList, ArrayList arrayList2) {
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

    public final void sendLoggingDataToHQM(ArrayList arrayList, ArrayList arrayList2) {
        String convertToBigDataFormat = convertToBigDataFormat(arrayList, arrayList2);
        if (convertToBigDataFormat == null) {
            return;
        }
        String replaceAll = convertToBigDataFormat.replaceAll("\\{", "").replaceAll("\\}", "");
        if (this.mSemHqmManager != null) {
            Log.v("VibratorHqmHelper", "sendLoggingDataToHQM() Server update !!!");
            this.mSemHqmManager.sendHWParamToHQM(0, "Audio", "VIBE", "sm", "0.0", "sec", "", replaceAll, "");
        }
    }
}
