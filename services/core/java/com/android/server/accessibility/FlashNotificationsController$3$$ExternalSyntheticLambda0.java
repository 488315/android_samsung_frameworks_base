package com.android.server.accessibility;

import android.media.AudioPlaybackConfiguration;

import java.util.function.Predicate;

public final /* synthetic */ class FlashNotificationsController$3$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) obj;
        return audioPlaybackConfiguration.isActive()
                && audioPlaybackConfiguration.getAudioAttributes().getUsage() == 4;
    }
}
