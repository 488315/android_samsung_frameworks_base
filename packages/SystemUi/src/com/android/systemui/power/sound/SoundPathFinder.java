package com.android.systemui.power.sound;

import android.app.ActivityManager;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.PowerUiRune;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundPathFinder {
    public static boolean checkDefaultCondition(String str) {
        return str == null || TextUtils.isEmpty(str) || "Galaxy".equals(str);
    }

    public static String getChargerConnectionPath(Context context, boolean z) {
        int currentUser = ActivityManager.getCurrentUser();
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "system_sound", currentUser);
        String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), "prev_system_sound", currentUser);
        if (checkDefaultCondition(stringForUser)) {
            String m29m = PathParser$$ExternalSyntheticOutline0.m29m("system/media/audio/ui/", z ? "ChargingStarted_Fast" : "ChargingStarted", ".ogg");
            CustomizationProvider$$ExternalSyntheticOutline0.m135m("system sound theme name is : ", stringForUser, " so it will be : ", m29m, "PowerUI-SoundPathFinder");
            return m29m;
        }
        if (!"Open_theme".equals(stringForUser)) {
            String systemSoundPath = getSystemSoundPath(z ? "ChargingStarted_Fast" : "ChargingStarted", stringForUser);
            AbstractC0000x2c234b15.m3m("sound theme name is applied so it will be : ", systemSoundPath, "PowerUI-SoundPathFinder");
            return systemSoundPath;
        }
        Log.d("PowerUI-SoundPathFinder", "prevSound : " + stringForUser2 + ", in userID : " + currentUser);
        if (checkDefaultCondition(stringForUser2)) {
            String m29m2 = PathParser$$ExternalSyntheticOutline0.m29m("system/media/audio/ui/", z ? "ChargingStarted_Fast" : "ChargingStarted", ".ogg");
            ExifInterface$$ExternalSyntheticOutline0.m35m(AbstractC0866xb1ce8deb.m87m("system sound theme name is : ", stringForUser, " but prevSound is ", stringForUser2, ", so it will be : "), m29m2, "PowerUI-SoundPathFinder");
            return m29m2;
        }
        String systemSoundPath2 = getSystemSoundPath(z ? "ChargingStarted_Fast" : "ChargingStarted", stringForUser2);
        CustomizationProvider$$ExternalSyntheticOutline0.m135m("system sound theme name is : ", stringForUser, " so we should play prev sound, so it will be : ", systemSoundPath2, "PowerUI-SoundPathFinder");
        return systemSoundPath2;
    }

    public static String getSoundPath(int i, Context context) {
        switch (i) {
            case 1:
                return getChargerConnectionPath(context, false);
            case 2:
                return getChargerConnectionPath(context, true);
            case 3:
                String string = Settings.System.getString(context.getContentResolver(), "low_battery_sound");
                if (!PowerUiRune.LOW_BATTTERY_SOUND_THEME) {
                    return string;
                }
                int currentUser = ActivityManager.getCurrentUser();
                String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "system_sound", currentUser);
                String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), "prev_system_sound", currentUser);
                if (checkDefaultCondition(stringForUser)) {
                    CustomizationProvider$$ExternalSyntheticOutline0.m135m("system sound theme name is : ", stringForUser, " so it will be : ", string, "PowerUI-SoundPathFinder");
                    return string;
                }
                if (!"Open_theme".equals(stringForUser)) {
                    String systemSoundPath = getSystemSoundPath("LowBattery", stringForUser);
                    AbstractC0000x2c234b15.m3m("sound theme name is applied so it will be : ", systemSoundPath, "PowerUI-SoundPathFinder");
                    return systemSoundPath;
                }
                Log.d("PowerUI-SoundPathFinder", "prevSound : " + stringForUser2 + ", in userID : " + currentUser);
                if (checkDefaultCondition(stringForUser2)) {
                    ExifInterface$$ExternalSyntheticOutline0.m35m(AbstractC0866xb1ce8deb.m87m("system sound theme name is : ", stringForUser, " but prevSound is ", stringForUser2, ", so it will be : "), string, "PowerUI-SoundPathFinder");
                    return string;
                }
                String systemSoundPath2 = getSystemSoundPath("LowBattery", stringForUser2);
                CustomizationProvider$$ExternalSyntheticOutline0.m135m("system sound theme name is : ", stringForUser, " so we should play prev sound, so it will be : ", systemSoundPath2, "PowerUI-SoundPathFinder");
                return systemSoundPath2;
            case 4:
                return "system/media/audio/ui/TW_Battery_caution.ogg";
            case 5:
            case 6:
            case 7:
                return "system/media/audio/ui/Water_Protection.ogg";
            default:
                return "";
        }
    }

    public static String getSystemSoundPath(String str, String str2) {
        return MotionLayout$$ExternalSyntheticOutline0.m22m("system/media/audio/ui/", str, "_", str2, ".ogg");
    }
}
