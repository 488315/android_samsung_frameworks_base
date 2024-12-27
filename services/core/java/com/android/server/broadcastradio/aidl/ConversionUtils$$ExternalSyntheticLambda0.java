package com.android.server.broadcastradio.aidl;

import android.hardware.broadcastradio.ProgramIdentifier;
import android.hardware.broadcastradio.VendorKeyValue;
import android.hardware.radio.RadioManager;

import java.util.function.IntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
