package com.android.server.location.gnss;

import android.telephony.CellInfo;

import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssLocationProvider$$ExternalSyntheticLambda15
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((CellInfo) obj).getCellSignalStrength().getAsuLevel();
    }
}
