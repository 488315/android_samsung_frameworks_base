package com.android.systemui.util;

public final /* synthetic */ class MemoryMonitor$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryMonitor f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ MemoryMonitor$$ExternalSyntheticLambda2(MemoryMonitor memoryMonitor, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryMonitor;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$startMonitoring$4(this.f$1);
                break;
            default:
                this.f$0.lambda$startMonitoring$5(this.f$1);
                break;
        }
    }
}
