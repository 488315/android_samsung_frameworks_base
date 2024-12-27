package com.samsung.android.server.audio;

import android.os.FileUtils;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class FactoryUtils {
    public static void increaseEarJackCounter() {
        try {
            File file = new File("/efs/FactoryApp/earjack_count");
            String str = "1";
            if (!file.exists()) {
                if (file.createNewFile()) {
                    FileUtils.stringToFile("/efs/FactoryApp/earjack_count", "1");
                }
            } else {
                String readTextFile =
                        FileUtils.readTextFile(new File("/efs/FactoryApp/earjack_count"), 0, null);
                if (!TextUtils.isEmpty(readTextFile)) {
                    str = Long.toString(Long.parseLong(readTextFile) + 1);
                }
                FileUtils.stringToFile("/efs/FactoryApp/earjack_count", str);
            }
        } catch (IOException | NumberFormatException e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Exception in increaseEarJackCounter : "),
                    "AS.FactoryUtils");
        }
    }

    public static boolean isFactoryMode() {
        return "factory".equals(SystemProperties.get("ro.factory.factory_binary"));
    }
}
