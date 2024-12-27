package com.android.server.om.wallpapertheme;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SemWallpaperThemeUtils {
    public static boolean isFotaUpgrade(Context context) {
        if (context.getPackageManager().isDeviceUpgrading()) {
            return true;
        }
        SharedPreferences preferences = FotaPreferenceUtils.getPreferences(context);
        String str = SystemProperties.get("ro.build.PDA", "");
        String readCSCVersion = readCSCVersion();
        String str2 = SystemProperties.get("ro.omc.build.id", "");
        String string = preferences.getString("stored_pda_version", null);
        String string2 = preferences.getString("stored_csc_version", null);
        String string3 = preferences.getString("stored_qbid_version", null);
        StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("isFotaUpgrade : currentPDAVersion = ", str, ", currentCSCVersion = ", readCSCVersion, ", currentQBId = ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, str2, ", storedPDAVersion = ", string, ", storedCSCVersion = ");
        m.append(string2);
        m.append(", storedQBId = ");
        m.append(string3);
        Log.d("SWT_Utils", m.toString());
        return (str2.equals(string3) && str.equals(string) && readCSCVersion.equals(string2)) ? false : true;
    }

    public static String readCSCVersion() {
        String str = "";
        String str2 = SystemProperties.get("ril.official_cscver", "");
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        Log.i("SWT_Utils", "reading CSC Version from file");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("system/CSCVersion.txt"), Charset.defaultCharset()));
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null && !readLine.isEmpty()) {
                    str = readLine.trim();
                }
                bufferedReader.close();
            } finally {
            }
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("readTextFromFile failed: "), "SWT_Utils");
        }
        return str;
    }

    public static void updateFotaUpgradeStatus(Context context) {
        SharedPreferences preferences = FotaPreferenceUtils.getPreferences(context);
        String str = SystemProperties.get("ro.build.PDA", "");
        String readCSCVersion = readCSCVersion();
        String str2 = SystemProperties.get("ro.omc.build.id", "");
        preferences.edit().putString("stored_pda_version", str).commit();
        preferences.edit().putString("stored_csc_version", readCSCVersion).commit();
        preferences.edit().putString("stored_qbid_version", str2).commit();
        StringBuilder sb = new StringBuilder("updateFotaUpgradeStatus : currentPDAVersion = ");
        sb.append(str);
        Log.d("SWT_Utils", OptionalModelParameterRange$$ExternalSyntheticOutline0.m(sb, ", currentCSCVersion = ", readCSCVersion, ", currentQBId = ", str2));
    }
}
