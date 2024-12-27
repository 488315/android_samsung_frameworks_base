package com.android.server.media;

import android.media.AudioAttributes;

public abstract class AudioRoutingUtils {
    public static final AudioAttributes ATTRIBUTES_MEDIA =
            new AudioAttributes.Builder().setUsage(1).build();
}
