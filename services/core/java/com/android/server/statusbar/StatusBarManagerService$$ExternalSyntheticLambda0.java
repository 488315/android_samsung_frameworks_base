package com.android.server.statusbar;

import com.android.server.power.ShutdownThread;

public final /* synthetic */ class StatusBarManagerService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ StatusBarManagerService$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ShutdownThread.shutdown(
                        StatusBarManagerService.getUiContext(), "userrequested", false);
                break;
            default:
                ShutdownThread.shutdown(
                        StatusBarManagerService.getUiContext(), "bixbyrequest", false);
                break;
        }
    }
}
