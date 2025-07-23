package com.samsung.android.media;

import android.media.AudioSystem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes6.dex */
public class SemAudioSystem {
    public static final Set<Integer> MULTI_SOUND_PRIMARY_DEVICE_SET;
    private static final int OFFSET_FOR_SAMSUNG_AUDIO = 10000;
    public static final int STREAM_MULTI_SOUND = 10003;

    static {
        HashSet hashSet = new HashSet();
        MULTI_SOUND_PRIMARY_DEVICE_SET = hashSet;
        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(16384);
        hashSet.add(67108864);
    }

    public static String getEffectParameters(String str) {
        return AudioSystem.getParameters("g_effect_param_key;" + str);
    }

    public static int setEffectParameters(String str) {
        return AudioSystem.setParameters("g_effect_param_key;" + str);
    }

    public static String getPolicyParameters(String str) {
        return AudioSystem.getParameters("audioParam;" + str);
    }

    public static int setPolicyParameters(String str) {
        return AudioSystem.setParameters("audioParam;" + str);
    }

    public static int makeDeviceBit(Set<Integer> set) {
        Iterator<Integer> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= it.next().intValue();
        }
        return i;
    }
}
