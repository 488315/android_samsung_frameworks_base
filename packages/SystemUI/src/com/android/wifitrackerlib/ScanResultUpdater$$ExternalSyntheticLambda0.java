package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class ScanResultUpdater$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScanResultUpdater f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ ScanResultUpdater$$ExternalSyntheticLambda0(ScanResultUpdater scanResultUpdater, long j, int i) {
        this.$r8$classId = i;
        this.f$0 = scanResultUpdater;
        this.f$1 = j;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ScanResultUpdater scanResultUpdater = this.f$0;
                if (scanResultUpdater.mClock.millis() - (((ScanResult) ((Map.Entry) obj).getValue()).timestamp / 1000) > this.f$1) {
                }
                break;
            default:
                ScanResultUpdater scanResultUpdater2 = this.f$0;
                if (scanResultUpdater2.mClock.millis() - (((ScanResult) ((Map.Entry) obj).getValue()).timestamp / 1000) > this.f$1) {
                }
                break;
        }
        return false;
    }
}
