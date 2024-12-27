package com.android.server.hdmi;

import android.content.Context;
import android.media.AudioManager;

public final class DefaultAudioManagerWrapper implements AudioManagerWrapper {
    public final AudioManager mAudioManager;

    public DefaultAudioManagerWrapper(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }
}
