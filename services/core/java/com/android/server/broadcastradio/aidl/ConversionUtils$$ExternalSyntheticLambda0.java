package com.android.server.broadcastradio.aidl;

import android.hardware.broadcastradio.ProgramIdentifier;
import android.hardware.broadcastradio.VendorKeyValue;
import android.hardware.radio.RadioManager;

import java.util.function.IntFunction;

public final /* synthetic */ class ConversionUtils$$ExternalSyntheticLambda0
        implements IntFunction {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.IntFunction
    public final Object apply(int i) {
        switch (this.$r8$classId) {
            case 0:
                return new ProgramIdentifier[i];
            case 1:
                return new ProgramIdentifier[i];
            case 2:
                return new VendorKeyValue[i];
            default:
                return new RadioManager.BandDescriptor[i];
        }
    }
}
