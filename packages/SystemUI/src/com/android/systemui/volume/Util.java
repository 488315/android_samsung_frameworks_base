package com.android.systemui.volume;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Util extends com.android.settingslib.volume.Util {
    public static final int[] SAMSUNG_AUDIO_MANAGER_FLAGS = {1, 16, 4, 2, 8, 2048, 128, 4096, 1024, 8388608, 4194304, 67108864, NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME, 262144};
    public static final String[] SAMSUNG_AUDIO_MANAGER_FLAG_NAMES = {"SHOW_UI", "VIBRATE", "PLAY_SOUND", "ALLOW_RINGER_MODES", "REMOVE_SOUND_AND_VIBRATE", "SHOW_VIBRATE_HINT", "SHOW_SILENT_HINT", "FROM_KEY", "SHOW_UI_WARNINGS", "MULTI_SOUND", "DISPLAY_VOLUME_CONTROL", "REMOTE_MIC", "DUAL_A2DP_MODE", "FIXED_SCO_VOLUME"};

    public static String logTag(Class cls) {
        String concat = "vol.".concat(cls.getSimpleName());
        return concat.length() < 23 ? concat : concat.substring(0, 23);
    }

    public static String ringerModeToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "RINGER_MODE_UNKNOWN_") : "RINGER_MODE_NORMAL" : "RINGER_MODE_VIBRATE" : "RINGER_MODE_SILENT";
    }
}
