package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
