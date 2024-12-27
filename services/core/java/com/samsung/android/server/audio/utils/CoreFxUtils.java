package com.samsung.android.server.audio.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioSystem;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.media.SemAudioSystem;

public abstract class CoreFxUtils {
    public static void setAdaptSound(Context context, int i) {
        String str;
        ContentResolver contentResolver = context.getContentResolver();
        int i2 = Settings.System.getInt(contentResolver, "hearing_diagnosis", 0);
        SemAudioSystem.setEffectParameters("adapt_sound_enable=".concat(i == 1 ? "true" : "false"));
        if (i2 == 1) {
            str =
                    Settings.System.getInt(contentResolver, "hearing_revision", 0)
                            + ","
                            + Settings.System.getInt(contentResolver, "hearing_direction", 0)
                            + ","
                            + Settings.System.getString(contentResolver, "hearing_parameters");
        } else {
            str = "0,0,0,0,0,0,0,0,0,0,0,0,0,0";
        }
        SemAudioSystem.setEffectParameters("adapt_sound=" + str);
        Log.d("CoreFxUtils", "setAdaptSound: gain dha Parameter : " + str);
    }

    public static void setUpScalerMode(int i) {
        AudioSystem.setParameters("l_effect_upscaler_mode=" + i);
    }
}
