package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PreferenceLabsKeys {
    public static final PreferenceLabsKeys INSTANCE = new PreferenceLabsKeys();
    public static final Preferences.Key CLOSE_ON_TOUCH_OUTSIDE = new Preferences.Key("support_outside_touch");
    public static final Preferences.Key SUPPORT_VOLUME_INTERACTION = new Preferences.Key("support_volume_interaction");
    public static final Preferences.Key GROUP_SPEAKER_DEFAULT_EXPANDED = new Preferences.Key("default_group_speaker_default_expanded");

    private PreferenceLabsKeys() {
    }
}
