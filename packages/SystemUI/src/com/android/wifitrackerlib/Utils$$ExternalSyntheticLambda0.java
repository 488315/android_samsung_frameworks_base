package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import java.util.List;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final /* synthetic */ class Utils$$ExternalSyntheticLambda0 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        List list = Utils.defaultSsidList;
        return ((ScanResult) obj).level;
    }
}
