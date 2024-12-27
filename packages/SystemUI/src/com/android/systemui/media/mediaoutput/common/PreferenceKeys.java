package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PreferenceKeys {
    public static final PreferenceKeys INSTANCE = new PreferenceKeys();
    public static final Preferences.Key CASTING_PRIORITY = new Preferences.Key("wifispeaker_chromecast_mode_enabled");
    public static final Preferences.Key SHOW_MUSIC_SHARE = new Preferences.Key("show_music_share_enabled");

    private PreferenceKeys() {
    }
}
