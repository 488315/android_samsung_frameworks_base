package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;

/* loaded from: classes2.dex */
public final class PreferenceKeys {
    public static final PreferenceKeys INSTANCE = new PreferenceKeys();
    public static final Preferences.Key CASTING_PRIORITY = new Preferences.Key("wifispeaker_chromecast_mode_enabled");
    public static final Preferences.Key SHOW_MUSIC_SHARE_ENABLED = new Preferences.Key("show_music_share_enabled");
    public static final Preferences.Key MIRRORING_PRIORITY = new Preferences.Key("mirroring_priority");
    public static final Preferences.Key SPOTIFY_CASTING_PRIORITY = new Preferences.Key("spotify_casting_priority");
    public static final Preferences.Key SHOW_MUSIC_SHARE = new Preferences.Key("show_music_share");
    public static final Preferences.Key SCPM_CONFIGURATION = new Preferences.Key("scpm_configuration");
    public static final Preferences.Key SETTING_VERSION = new Preferences.Key("setting_version");

    private PreferenceKeys() {
    }
}
