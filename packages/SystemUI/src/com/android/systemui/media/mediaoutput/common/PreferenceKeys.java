package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;

public final class PreferenceKeys {
    public static final PreferenceKeys INSTANCE = new PreferenceKeys();
    public static final Preferences.Key CASTING_PRIORITY = new Preferences.Key("wifispeaker_chromecast_mode_enabled");
    public static final Preferences.Key SHOW_MUSIC_SHARE = new Preferences.Key("show_music_share_enabled");

    private PreferenceKeys() {
    }
}
