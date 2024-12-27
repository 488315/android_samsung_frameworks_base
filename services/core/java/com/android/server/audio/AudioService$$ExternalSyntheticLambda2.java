package com.android.server.audio;

import android.media.AudioDeviceAttributes;

import java.util.function.Function;

public final /* synthetic */ class AudioService$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = AudioService.BECOMING_NOISY_DELAY_MS;
                break;
            default:
                int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                break;
        }
        return audioDeviceAttributes.toString();
    }
}
