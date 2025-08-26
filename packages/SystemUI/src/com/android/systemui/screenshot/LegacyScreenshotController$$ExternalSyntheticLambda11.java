package com.android.systemui.screenshot;

import android.os.UserHandle;
import com.android.systemui.screenshot.LegacyScreenshotController;
import java.util.UUID;

/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyScreenshotController$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ UUID f$1;
    public final /* synthetic */ UserHandle f$2;

    public /* synthetic */ LegacyScreenshotController$$ExternalSyntheticLambda11(Object obj, UUID uuid, UserHandle userHandle, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = uuid;
        this.f$2 = userHandle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LegacyScreenshotController legacyScreenshotController = (LegacyScreenshotController) this.f$0;
                UUID uuid = this.f$1;
                UserHandle userHandle = this.f$2;
                legacyScreenshotController.requestScrollCapture(uuid, userHandle);
                legacyScreenshotController.mWindow.peekDecorView().getViewRootImpl().setActivityConfigCallback(new LegacyScreenshotController.AnonymousClass3(legacyScreenshotController, uuid, userHandle));
                break;
            default:
                LegacyScreenshotController.AnonymousClass3 anonymousClass3 = (LegacyScreenshotController.AnonymousClass3) this.f$0;
                anonymousClass3.this$0.requestScrollCapture(this.f$1, this.f$2);
                break;
        }
    }
}
