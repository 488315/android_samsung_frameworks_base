package com.android.wifitrackerlib;

import android.util.Log;
import com.android.wifitrackerlib.BaseWifiTracker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BaseWifiTracker$Scanner$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker.Scanner f$0;

    public /* synthetic */ BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(BaseWifiTracker.Scanner scanner, int i) {
        this.$r8$classId = i;
        this.f$0 = scanner;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BaseWifiTracker.Scanner scanner = this.f$0;
        switch (i) {
            case 0:
                int i2 = BaseWifiTracker.Scanner.$r8$clinit;
                scanner.stopScanning();
                break;
            case 1:
                int i3 = BaseWifiTracker.Scanner.$r8$clinit;
                if (scanner.shouldScan()) {
                    Log.i(scanner.this$0.mTag, "Scanning started");
                    scanner.scanLoop();
                    break;
                }
                break;
            default:
                int i4 = BaseWifiTracker.Scanner.$r8$clinit;
                scanner.scanLoop();
                break;
        }
    }
}
