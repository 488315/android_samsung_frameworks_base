package com.android.wm.shell.pip2.phone;

import android.view.SurfaceControl;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipScheduler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipScheduler f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PipScheduler$$ExternalSyntheticLambda2(PipScheduler pipScheduler, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = pipScheduler;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipScheduler pipScheduler = this.f$0;
                ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipScheduler.mSurfaceControlTransactionFactory).getTransaction().remove((SurfaceControl) this.f$1).apply();
                break;
            default:
                PipScheduler pipScheduler2 = this.f$0;
                Runnable runnable = (Runnable) this.f$1;
                int i = PipScheduler.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                pipScheduler2.getClass();
                runnable.run();
                pipScheduler2.mOverlayFadeoutAnimator = null;
                break;
        }
    }
}
