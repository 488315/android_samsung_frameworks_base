package com.android.server.hdmi;

import android.content.Context;
import android.media.AudioManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DefaultAudioManagerWrapper implements AudioManagerWrapper {
    public final AudioManager mAudioManager;

    public DefaultAudioManagerWrapper(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }
}
