package com.samsung.android.server.audio;

import android.os.FileUtils;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class FactoryUtils {
    public static boolean isFactoryMode() {
        return "factory".equals(SystemProperties.get("ro.factory.factory_binary"));
    }

    public static void increaseEarJackCounter() {
        try {
            File file = new File("/efs/FactoryApp/earjack_count");
            String str = "1";
            if (!file.exists()) {
                if (file.createNewFile()) {
                    FileUtils.stringToFile("/efs/FactoryApp/earjack_count", "1");
                }
            } else {
                String readTextFile = FileUtils.readTextFile(new File("/efs/FactoryApp/earjack_count"), 0, null);
                if (!TextUtils.isEmpty(readTextFile)) {
                    str = Long.toString(Long.parseLong(readTextFile) + 1);
                }
                FileUtils.stringToFile("/efs/FactoryApp/earjack_count", str);
            }
        } catch (IOException | NumberFormatException e) {
            Log.d("AS.FactoryUtils", "Exception in increaseEarJackCounter : " + e.getMessage());
        }
    }
}
