package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;

/* loaded from: classes2.dex */
public final class PreferenceLabsKeys {
    public static final PreferenceLabsKeys INSTANCE = new PreferenceLabsKeys();
    public static final Preferences.Key CLOSE_ON_TOUCH_OUTSIDE = new Preferences.Key("support_outside_touch");
    public static final Preferences.Key SUPPORT_VOLUME_INTERACTION = new Preferences.Key("support_volume_interaction");
    public static final Preferences.Key GROUP_SPEAKER_DEFAULT_EXPANDED = new Preferences.Key("group_speaker_default_expanded");
    public static final Preferences.Key SUPPORT_RECENT_GROUP_SPEAKER = new Preferences.Key("support_recent_group_speaker");

    static {
        new Preferences.Key("recent_group_speaker_ids");
    }

    private PreferenceLabsKeys() {
    }
}
