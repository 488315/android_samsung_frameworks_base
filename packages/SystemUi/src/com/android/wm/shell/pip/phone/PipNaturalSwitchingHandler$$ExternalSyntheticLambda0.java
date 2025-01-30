package com.android.wm.shell.pip.phone;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipNaturalSwitchingHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipNaturalSwitchingHandler$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipNaturalSwitchingHandler pipNaturalSwitchingHandler = (PipNaturalSwitchingHandler) this.f$0;
                pipNaturalSwitchingHandler.getClass();
                Log.w("PipNaturalSwitchingHandler", "mTaskVanishedTimeout: " + pipNaturalSwitchingHandler);
                pipNaturalSwitchingHandler.updateWaitingForTaskVanished("timeout", false);
                break;
            default:
                AbstractC0000x2c234b15.m3m("startEnterAnimation: up-scale finished, ", (String) this.f$0, "PipNaturalSwitchingHandler");
                break;
        }
    }
}
