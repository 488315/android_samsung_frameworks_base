package com.samsung.android.media;

import android.media.AudioSystem;
import android.util.ArrayMap;
import android.util.Log;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes6.dex */
public class AudioFxHelper {
    public static final String KEY_SITUATION_VOLUME_TOUCH_TONE = "stv_touch_tone";
    private static final String TAG = "AS.AudioFxHelper";
    private static final ArrayMap<Integer, Float> sPreDefinedVolumeEffects;
    private static float sSoundEffectVolume = -1.0f;

    public static int getPlaySoundTypeForSEP(int i) {
        return (i < 100 || i > 106) ? i : i - 84;
    }

    static {
        ArrayMap<Integer, Float> arrayMap = new ArrayMap<>();
        sPreDefinedVolumeEffects = arrayMap;
        Float fValueOf = Float.valueOf(0.5f);
        arrayMap.put(5, fValueOf);
        arrayMap.put(6, fValueOf);
        arrayMap.put(7, fValueOf);
        arrayMap.put(8, fValueOf);
        arrayMap.put(Integer.valueOf(getPlaySoundTypeForSEP(101)), Float.valueOf(1.0f));
    }

    public static float getSoundFxVolumeByType(int i) {
        AudioParameter audioParameterBuild = new AudioParameter.Builder().setParam(AudioParameter.SEC_GLOBAL_VOLUME_SITUATION_KEY).setParam("type", 1).setParam("device", 0).build();
        ArrayMap<Integer, Float> arrayMap = sPreDefinedVolumeEffects;
        if (arrayMap.containsKey(Integer.valueOf(i))) {
            return arrayMap.get(Integer.valueOf(i)).floatValue();
        }
        if (Rune.SEC_AUDIO_EXTENSION_SITUATION_VOLUME) {
            return 1.0f;
        }
        float f = sSoundEffectVolume;
        if (f != -1.0f) {
            return f;
        }
        try {
            sSoundEffectVolume = Float.parseFloat(AudioSystem.getParameters(audioParameterBuild.toString()));
        } catch (NumberFormatException unused) {
        }
        return sSoundEffectVolume;
    }

    public static void setSoundFxVolume(float f) {
        sSoundEffectVolume = f;
        Log.i(TAG, "set sound effect volume : " + sSoundEffectVolume);
    }

    public static void setSoundEffectVolume() {
        try {
            setSoundFxVolume(Float.parseFloat(AudioSystem.getParameters(new AudioParameter.Builder().setParam(AudioParameter.SEC_GLOBAL_VOLUME_SITUATION_KEY).setParam("type", 1).setParam("device", 0).build().toString())));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPreDefinedEffectKey(int i) {
        return sPreDefinedVolumeEffects.containsKey(Integer.valueOf(i));
    }
}
