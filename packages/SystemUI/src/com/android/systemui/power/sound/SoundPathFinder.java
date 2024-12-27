package com.android.systemui.power.sound;

import android.app.ActivityManager;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.PowerUiRune;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SoundPathFinder {
    public static boolean checkDefaultCondition(String str) {
        return str == null || TextUtils.isEmpty(str) || "Galaxy".equals(str);
    }

    public static String getChargerConnectionPath(Context context, boolean z) {
        if (!PowerUiRune.SYSTEM_SOUND_THEME) {
            return getDefaultChargerConnectionPath(z);
        }
        int currentUser = ActivityManager.getCurrentUser();
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "system_sound", currentUser);
        String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), "prev_system_sound", currentUser);
        if (checkDefaultCondition(stringForUser)) {
            String defaultChargerConnectionPath = getDefaultChargerConnectionPath(z);
            MWBixbyController$$ExternalSyntheticOutline0.m("system sound theme name is : ", stringForUser, " so it will be : ", defaultChargerConnectionPath, "PowerUI-SoundPathFinder");
            return defaultChargerConnectionPath;
        }
        if (!"Open_theme".equals(stringForUser)) {
            String systemSoundPath = getSystemSoundPath(z ? "ChargingStarted_Fast" : "ChargingStarted", stringForUser);
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("sound theme name is applied so it will be : ", systemSoundPath, "PowerUI-SoundPathFinder");
            return systemSoundPath;
        }
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(currentUser, "prevSound : ", stringForUser2, ", in userID : ", "PowerUI-SoundPathFinder");
        if (checkDefaultCondition(stringForUser2)) {
            String defaultChargerConnectionPath2 = getDefaultChargerConnectionPath(z);
            ExifInterface$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("system sound theme name is : ", stringForUser, " but prevSound is ", stringForUser2, ", so it will be : "), defaultChargerConnectionPath2, "PowerUI-SoundPathFinder");
            return defaultChargerConnectionPath2;
        }
        String systemSoundPath2 = getSystemSoundPath(z ? "ChargingStarted_Fast" : "ChargingStarted", stringForUser2);
        MWBixbyController$$ExternalSyntheticOutline0.m("system sound theme name is : ", stringForUser, " so we should play prev sound, so it will be : ", systemSoundPath2, "PowerUI-SoundPathFinder");
        return systemSoundPath2;
    }

    public static String getDefaultChargerConnectionPath(boolean z) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("system/media/audio/ui/", z ? "ChargingStarted_Fast" : "ChargingStarted", ".ogg");
    }

    public static String getSoundPath(int i, Context context) {
        switch (i) {
            case 1:
                return getChargerConnectionPath(context, false);
            case 2:
                return getChargerConnectionPath(context, true);
            case 3:
                String string = Settings.System.getString(context.getContentResolver(), "low_battery_sound");
                if (!PowerUiRune.SYSTEM_SOUND_THEME || !PowerUiRune.LOW_BATTTERY_SOUND_THEME) {
                    return string;
                }
                int currentUser = ActivityManager.getCurrentUser();
                String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "system_sound", currentUser);
                String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), "prev_system_sound", currentUser);
                if (checkDefaultCondition(stringForUser)) {
                    MWBixbyController$$ExternalSyntheticOutline0.m("system sound theme name is : ", stringForUser, " so it will be : ", string, "PowerUI-SoundPathFinder");
                    return string;
                }
                if (!"Open_theme".equals(stringForUser)) {
                    String systemSoundPath = getSystemSoundPath("LowBattery", stringForUser);
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("sound theme name is applied so it will be : ", systemSoundPath, "PowerUI-SoundPathFinder");
                    return systemSoundPath;
                }
                SecNotificationBlockManager$$ExternalSyntheticOutline0.m(currentUser, "prevSound : ", stringForUser2, ", in userID : ", "PowerUI-SoundPathFinder");
                if (checkDefaultCondition(stringForUser2)) {
                    ExifInterface$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("system sound theme name is : ", stringForUser, " but prevSound is ", stringForUser2, ", so it will be : "), string, "PowerUI-SoundPathFinder");
                    return string;
                }
                String systemSoundPath2 = getSystemSoundPath("LowBattery", stringForUser2);
                MWBixbyController$$ExternalSyntheticOutline0.m("system sound theme name is : ", stringForUser, " so we should play prev sound, so it will be : ", systemSoundPath2, "PowerUI-SoundPathFinder");
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
        return MotionLayout$$ExternalSyntheticOutline0.m("system/media/audio/ui/", str, "_", str2, ".ogg");
    }
}
