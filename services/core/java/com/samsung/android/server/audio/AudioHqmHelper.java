package com.samsung.android.server.audio;

import android.content.Context;
import android.media.AudioSystem;
import android.os.SemHqmManager;
import android.util.Log;

import com.samsung.android.media.AudioParameter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AudioHqmHelper {
    public static final String[][] BIG_DATA = {
        new String[] {
            "HW_CSPT", "HW_CSPC", "HW_CRET", "HW_CREC", "HW_CEAT", "HW_CEAC", "HW_CBTT", "HW_CBTC",
            "HW_CUST", "HW_CUSC"
        },
        new String[] {
            "HW_MSPT", "HW_MSPC", "HW_MEAT", "HW_MEAC", "HW_MBTT", "HW_MBTC", "HW_MUST", "HW_MUSC",
            "HW_MUHT", "HW_MUHC", "HW_DLBY", "HW_3EJC", "HW_4EJC", "MU_MSVL", "MU_MEVL", "MU_MUHV"
        },
        new String[] {"FW_VSMC"},
        new String[] {
            "RCV_TEMP_MAX",
            "SPK_TEMP_MAX",
            "RCV_TEMP_OVERCNT",
            "SPK_TEMP_OVERCNT",
            "RCV_EXCU_MAX",
            "SPK_EXCU_MAX",
            "RCV_EXCU_OVERCNT",
            "SPK_EXCU_OVERCNT"
        }
    };
    public static int mAudioServerResetCount;
    public static boolean mAudioServerResetCountMaxLimit;
    public static int mPreAudioServerResetCount;

    public static void sendHqmData(Context context, boolean z) {
        AudioParameter audioParameter =
                new AudioParameter(
                        AudioSystem.getParameters(
                                z ? "l_bigdata_logging;l_bigdata_app" : "l_bigdata_logging"));
        int i = 0;
        while (true) {
            if (i >= 4) {
                break;
            }
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
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        int i2 = mAudioServerResetCount;
        int i3 = i2 - mPreAudioServerResetCount;
        if (!z) {
            mPreAudioServerResetCount = i2;
        }
        arrayList3.add("FW_ASRC");
        arrayList4.add(Integer.toString(i3));
        sendLoggingDataToHQM(context, arrayList3, arrayList4, "FWAC");
    }

    public static void sendLoggingDataToHQM(
            Context context, ArrayList arrayList, ArrayList arrayList2, String str) {
        String str2;
        int size = arrayList.size();
        try {
            JSONObject jSONObject = new JSONObject();
            for (int i = 0; i < size; i++) {
                jSONObject.put((String) arrayList.get(i), arrayList2.get(i));
            }
            str2 = jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = null;
        }
        if (str2 == null) {
            return;
        }
        String replaceAll = str2.replaceAll("\\{", "").replaceAll("\\}", "");
        SemHqmManager semHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        Log.v("AS.AudioHqmHelper", "sendLoggingDataToHQM() Server update !!!");
        semHqmManager.sendHWParamToHQM(0, "Audio", str, "sm", "0.0", "sec", "", replaceAll, "");
    }
}
