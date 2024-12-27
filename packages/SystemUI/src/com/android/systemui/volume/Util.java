package com.android.systemui.volume;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Util extends com.android.settingslib.volume.Util {
    public static final int[] SAMSUNG_AUDIO_MANAGER_FLAGS = {1, 16, 4, 2, 8, 2048, 128, 4096, 1024, QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED, QuickStepContract.SYSUI_STATE_BACK_DISABLED, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, 524288, 262144};
    public static final String[] SAMSUNG_AUDIO_MANAGER_FLAG_NAMES = {"SHOW_UI", "VIBRATE", "PLAY_SOUND", "ALLOW_RINGER_MODES", "REMOVE_SOUND_AND_VIBRATE", "SHOW_VIBRATE_HINT", "SHOW_SILENT_HINT", "FROM_KEY", "SHOW_UI_WARNINGS", "MULTI_SOUND", "DISPLAY_VOLUME_CONTROL", "REMOTE_MIC", "DUAL_A2DP_MODE", "FIXED_SCO_VOLUME"};

    public static String logTag(Class cls) {
        String concat = "vol.".concat(cls.getSimpleName());
        return concat.length() < 23 ? concat : concat.substring(0, 23);
    }

    public static String ringerModeToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "RINGER_MODE_UNKNOWN_") : "RINGER_MODE_NORMAL" : "RINGER_MODE_VIBRATE" : "RINGER_MODE_SILENT";
    }
}
