package com.android.server.audio;

import java.util.function.Consumer;

public final /* synthetic */ class AudioService$$ExternalSyntheticLambda22 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = AudioService.BECOMING_NOISY_DELAY_MS;
        ((AudioService.AudioPolicyProxy) obj).release();
    }
}
