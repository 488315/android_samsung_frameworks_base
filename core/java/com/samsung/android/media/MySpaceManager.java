package com.samsung.android.media;

import android.media.AudioSystem;

public class MySpaceManager {
    public static final int MYSPACE_EFFECT_MAX_TIMED_OUT = 1500;
    public static final int MYSPACE_EFFECT_TIMED_OUT = 1000;
    public static final int MYSPACE_MUSIC_FADEIN = 0;
    public static final int MYSPACE_MUSIC_FADEOUT = 1;
    public static final int MYSPACE_MUSIC_FADE_OUT_IN = 2;
    private static final String TAG = "MySpaceManager";
    private static int preset;

    private static void setParameter(int presetType) {
        SemAudioSystem.setEffectParameters("g_effect_myspace_type=" + presetType);
        preset = presetType;
    }

    public static int getCurrentPreset() {
        return preset;
    }

    public static void playMySpaceEffect(int effectPreset) {
        preset = 0;
        if (effectPreset == 0 && AudioSystem.isStreamActive(3, 0)) {
            setParameter(2);
        }
        setParameter(effectPreset);
    }
}
