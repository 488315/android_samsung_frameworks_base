package com.android.server.location.gnss;

import android.telephony.CellInfo;

import java.util.function.ToIntFunction;

public final /* synthetic */ class GnssLocationProvider$$ExternalSyntheticLambda15
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((CellInfo) obj).getCellSignalStrength().getAsuLevel();
    }
}
