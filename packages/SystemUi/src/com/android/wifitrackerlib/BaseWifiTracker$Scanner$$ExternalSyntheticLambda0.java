package com.android.wifitrackerlib;

import com.android.wifitrackerlib.BaseWifiTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BaseWifiTracker$Scanner$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker.Scanner f$0;

    public /* synthetic */ BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(BaseWifiTracker.Scanner scanner, int i) {
        this.$r8$classId = i;
        this.f$0 = scanner;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.possiblyStartScanning();
                break;
            case 1:
                this.f$0.stopScanning();
                break;
            default:
                this.f$0.scanLoop();
                break;
        }
    }
}
