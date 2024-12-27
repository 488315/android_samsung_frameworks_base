package com.android.server.audio;

import android.media.AudioPlaybackConfiguration;

import java.util.function.Function;

public final /* synthetic */ class PlaybackActivityMonitor$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((AudioPlaybackConfiguration) obj).getPlayerState());
    }
}
