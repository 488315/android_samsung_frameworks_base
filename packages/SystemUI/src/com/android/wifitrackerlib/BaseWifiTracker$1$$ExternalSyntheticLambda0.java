package com.android.wifitrackerlib;

import android.net.NetworkKey;
import android.net.wifi.ScanResult;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class BaseWifiTracker$1$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return NetworkKey.createFromScanResult((ScanResult) obj);
    }
}
