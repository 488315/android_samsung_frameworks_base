package com.samsung.android.server.audio.utils;

import android.media.AudioPlaybackConfiguration;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PlaybackUtils {
    public static boolean isMusicActive(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        if (!audioPlaybackConfiguration.isActive()) {
            return false;
        }
        int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
        return usage == 0 || usage == 1 || usage == 12 || usage == 14 || usage == 16;
    }
}
