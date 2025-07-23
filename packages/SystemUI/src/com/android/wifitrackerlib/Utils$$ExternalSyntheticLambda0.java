package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import java.util.List;
import java.util.function.ToIntFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class Utils$$ExternalSyntheticLambda0 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        List list = Utils.defaultSsidList;
        return ((ScanResult) obj).level;
    }
}
