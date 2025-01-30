package com.android.systemui.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DesktopManagerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopManagerImpl f$0;

    public /* synthetic */ DesktopManagerImpl$$ExternalSyntheticLambda0(DesktopManagerImpl desktopManagerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopManagerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DesktopManagerImpl desktopManagerImpl = this.f$0;
                desktopManagerImpl.mWakefulnessLifecycle.removeObserver(desktopManagerImpl.mObserver);
                desktopManagerImpl.mUpdateMonitor.removeCallback(desktopManagerImpl.mUpdateMonitorCallback);
                break;
            default:
                DesktopManagerImpl desktopManagerImpl2 = this.f$0;
                desktopManagerImpl2.mWakefulnessLifecycle.addObserver(desktopManagerImpl2.mObserver);
                desktopManagerImpl2.mUpdateMonitor.registerCallback(desktopManagerImpl2.mUpdateMonitorCallback);
                break;
        }
    }
}
