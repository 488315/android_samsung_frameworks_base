package com.android.server.wm;

import android.content.ServiceConnection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WmScreenshotController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WmScreenshotController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ WmScreenshotController$$ExternalSyntheticLambda0(WmScreenshotController wmScreenshotController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = wmScreenshotController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.invalidateForScreenShot((DisplayContent) this.f$1, false);
                break;
            default:
                this.f$0.resetConnection((ServiceConnection) this.f$1, true);
                break;
        }
    }
}
