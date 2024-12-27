package com.android.systemui.util;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class DesktopManagerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopManagerImpl f$0;

    public /* synthetic */ DesktopManagerImpl$$ExternalSyntheticLambda0(DesktopManagerImpl desktopManagerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopManagerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DesktopManagerImpl desktopManagerImpl = this.f$0;
        switch (i) {
            case 0:
                desktopManagerImpl.lambda$stopSystemUIDesktopService$1();
                break;
            default:
                desktopManagerImpl.lambda$startSystemUIDesktopService$0();
                break;
        }
    }
}
