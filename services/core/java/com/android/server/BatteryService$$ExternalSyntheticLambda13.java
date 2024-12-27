package com.android.server;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryService$$ExternalSyntheticLambda13 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryService f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BatteryService$$ExternalSyntheticLambda13(BatteryService batteryService, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryService;
        this.f$1 = z;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.processValuesLocked(this.f$1);
                break;
            default:
                this.f$0.processValuesLocked(this.f$1);
                break;
        }
    }
}
