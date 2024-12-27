package com.android.server.hdmi;

import android.media.AudioDescriptor;

import java.util.function.Function;

public final /* synthetic */ class HdmiCecLocalDeviceTv$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new AudioDescriptor(1, 0, (byte[]) obj);
    }
}
