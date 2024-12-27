package com.android.server;

import com.android.internal.util.FunctionalUtils;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryService$$ExternalSyntheticLambda9
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryService f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ PrintWriter f$2;

    public /* synthetic */ BatteryService$$ExternalSyntheticLambda9(
            BatteryService batteryService, boolean z, PrintWriter printWriter, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryService;
        this.f$1 = z;
        this.f$2 = printWriter;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                BatteryService batteryService = this.f$0;
                boolean z = this.f$1;
                PrintWriter printWriter = this.f$2;
                batteryService.processValuesLocked(z);
                if (printWriter != null && z) {
                    printWriter.println(batteryService.mSequence);
                    break;
                }
                break;
            default:
                BatteryService batteryService2 = this.f$0;
                boolean z2 = this.f$1;
                PrintWriter printWriter2 = this.f$2;
                batteryService2.processValuesLocked(z2);
                if (printWriter2 != null && z2) {
                    printWriter2.println(batteryService2.mSequence);
                    break;
                }
                break;
        }
    }
}
