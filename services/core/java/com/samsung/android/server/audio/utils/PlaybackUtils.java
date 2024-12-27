package com.samsung.android.server.audio.utils;

import android.media.AudioPlaybackConfiguration;

public abstract class PlaybackUtils {
    public static boolean isMusicActive(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        if (!audioPlaybackConfiguration.isActive()) {
            return false;
        }
        int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
        return usage == 0 || usage == 1 || usage == 12 || usage == 14 || usage == 16;
    }
}
