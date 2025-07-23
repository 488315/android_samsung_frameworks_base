package com.android.wifitrackerlib;

import android.net.NetworkKey;
import android.net.wifi.ScanResult;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BaseWifiTracker$1$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return NetworkKey.createFromScanResult((ScanResult) obj);
    }
}
