package com.android.server.hdmi;

import android.media.AudioFormat;

import java.util.function.IntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class HdmiCecLocalDeviceAudioSystem$$ExternalSyntheticLambda0
        implements IntFunction {
    @Override // java.util.function.IntFunction
    public final Object apply(int i) {
        return AudioFormat.toLogFriendlyEncoding(i);
    }
}
