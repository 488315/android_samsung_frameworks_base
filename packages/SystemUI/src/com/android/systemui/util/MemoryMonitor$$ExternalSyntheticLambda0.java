package com.android.systemui.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MemoryMonitor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryMonitor f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ MemoryMonitor$$ExternalSyntheticLambda0(MemoryMonitor memoryMonitor, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryMonitor;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$startMonitoring$2(this.f$1);
                break;
            default:
                this.f$0.lambda$startMonitoring$3(this.f$1);
                break;
        }
    }
}
