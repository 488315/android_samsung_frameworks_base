package com.samsung.android.server.audio;

import android.media.AudioPlaybackConfiguration;

import java.util.function.Function;

public final /* synthetic */ class ScreenSharingHelper$$ExternalSyntheticLambda2
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((AudioPlaybackConfiguration) obj).getClientUid());
    }
}
