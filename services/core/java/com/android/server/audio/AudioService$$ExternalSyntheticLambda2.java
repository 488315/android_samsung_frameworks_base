package com.android.server.audio;

import android.media.AudioDeviceAttributes;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
