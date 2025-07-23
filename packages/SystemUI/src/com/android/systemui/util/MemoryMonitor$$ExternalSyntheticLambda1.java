package com.android.systemui.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MemoryMonitor$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryMonitor f$0;

    public /* synthetic */ MemoryMonitor$$ExternalSyntheticLambda1(MemoryMonitor memoryMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MemoryMonitor memoryMonitor = this.f$0;
        switch (i) {
            case 0:
                memoryMonitor.lambda$new$0();
                break;
            default:
                memoryMonitor.takeNotificationCount();
                break;
        }
    }
}
