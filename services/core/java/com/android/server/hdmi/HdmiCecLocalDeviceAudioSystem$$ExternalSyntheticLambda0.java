package com.android.server.hdmi;

import android.media.AudioFormat;

import java.util.function.IntFunction;

public final /* synthetic */ class HdmiCecLocalDeviceAudioSystem$$ExternalSyntheticLambda0
        implements IntFunction {
    @Override // java.util.function.IntFunction
    public final Object apply(int i) {
        return AudioFormat.toLogFriendlyEncoding(i);
    }
}
