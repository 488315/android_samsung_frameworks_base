package com.android.systemui.util;

public final /* synthetic */ class MemoryMonitor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryMonitor f$0;

    public /* synthetic */ MemoryMonitor$$ExternalSyntheticLambda0(MemoryMonitor memoryMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MemoryMonitor memoryMonitor = this.f$0;
        switch (i) {
            case 0:
                memoryMonitor.lambda$printMemoryInfo$1();
                break;
            case 1:
                memoryMonitor.lambda$new$0();
                break;
            case 2:
                memoryMonitor.lambda$printMemoryInfo$2();
                break;
            default:
                memoryMonitor.takeNotificationCount();
                break;
        }
    }
}
