package com.android.server.statusbar;

import com.android.server.power.ShutdownThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatusBarManagerService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ StatusBarManagerService$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ShutdownThread.shutdown(StatusBarManagerService.getUiContext(), "userrequested", false);
                break;
            default:
                ShutdownThread.shutdown(StatusBarManagerService.getUiContext(), "bixbyrequest", false);
                break;
        }
    }
}
