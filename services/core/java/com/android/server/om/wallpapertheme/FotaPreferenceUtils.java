package com.android.server.om.wallpapertheme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.FileUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class FotaPreferenceUtils {
    public static SharedPreferences getPreferences(Context context) {
        File file = new File("/data/overlays/wallpapertheme/");
        if (!file.exists()) {
            if (file.mkdir()) {
                FileUtils.setPermissions(file, 511, -1, -1);
            } else {
                Log.e("SWT_FotaPreferenceUtils", "getPreferences: Unable to create dir: " + file);
            }
        }
        File file2 = new File("/data/overlays/wallpapertheme/fota_preference.xml");
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            }
        } catch (IOException e) {
            Log.e(
                    "SWT_FotaPreferenceUtils",
                    "getPreferences: Unable to create file, " + e.getMessage());
        }
        return context.createDeviceProtectedStorageContext().getSharedPreferences(file2, 0);
    }
}
