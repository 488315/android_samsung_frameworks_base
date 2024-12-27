package com.samsung.android.media;

import android.media.AudioSystem;
import android.util.ArrayMap;
import android.util.Log;

import com.samsung.android.audio.Rune;

/* loaded from: classes6.dex */
public class AudioFxHelper {
    public static final String KEY_SITUATION_VOLUME_TOUCH_TONE = "stv_touch_tone";
    private static final String TAG = "AS.AudioFxHelper";
    private static float sSoundEffectVolume = -1.0f;
    private static final ArrayMap<Integer, Float> sPreDefinedVolumeEffects = new ArrayMap<>();

    static {
        ArrayMap<Integer, Float> arrayMap = sPreDefinedVolumeEffects;
        Float valueOf = Float.valueOf(0.5f);
        arrayMap.put(5, valueOf);
        sPreDefinedVolumeEffects.put(6, valueOf);
        sPreDefinedVolumeEffects.put(7, valueOf);
        sPreDefinedVolumeEffects.put(8, valueOf);
        sPreDefinedVolumeEffects.put(
                Integer.valueOf(getPlaySoundTypeForSEP(101)), Float.valueOf(1.0f));
    }

    public static int getPlaySoundTypeForSEP(int effectType) {
        if (effectType >= 100 && effectType <= 106) {
            return (effectType - 99) + 15;
        }
        return effectType;
    }

    public static float getSoundFxVolumeByType(int type) {
        AudioParameter parameters =
                new AudioParameter.Builder()
                        .setParam(AudioParameter.SEC_GLOBAL_VOLUME_SITUATION_KEY)
                        .setParam("type", 1)
                        .setParam("device", 0)
                        .build();
        if (sPreDefinedVolumeEffects.containsKey(Integer.valueOf(type))) {
            return sPreDefinedVolumeEffects.get(Integer.valueOf(type)).floatValue();
        }
        if (Rune.SEC_AUDIO_EXTENSION_SITUATION_VOLUME) {
            return 1.0f;
        }
        if (sSoundEffectVolume != -1.0f) {
            return sSoundEffectVolume;
        }
        try {
            sSoundEffectVolume = Float.parseFloat(AudioSystem.getParameters(parameters.toString()));
        } catch (NumberFormatException e) {
        }
        return sSoundEffectVolume;
    }

    public static void setSoundFxVolume(float volume) {
        sSoundEffectVolume = volume;
        Log.i(TAG, "set sound effect volume : " + sSoundEffectVolume);
    }

    public static void setSoundEffectVolume() {
        AudioParameter parameter =
                new AudioParameter.Builder()
                        .setParam(AudioParameter.SEC_GLOBAL_VOLUME_SITUATION_KEY)
                        .setParam("type", 1)
                        .setParam("device", 0)
                        .build();
        try {
            setSoundFxVolume(Float.parseFloat(AudioSystem.getParameters(parameter.toString())));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPreDefinedEffectKey(int x) {
        return sPreDefinedVolumeEffects.containsKey(Integer.valueOf(x));
    }
}
