package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScanResultUpdater$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ ScanResultUpdater f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ScanResultUpdater scanResultUpdater = this.f$0;
        return scanResultUpdater.mClock.millis() - (((ScanResult) ((Map.Entry) obj).getValue()).timestamp / 1000) > scanResultUpdater.mMaxScanAgeMillis;
    }
}
