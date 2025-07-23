package com.android.wm.shell.pip.phone;

import android.animation.RectEvaluator;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipNaturalSwitchingHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipNaturalSwitchingHandler$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PipNaturalSwitchingHandler pipNaturalSwitchingHandler = (PipNaturalSwitchingHandler) obj;
                RectEvaluator rectEvaluator = PipNaturalSwitchingHandler.RECT_EVALUATOR;
                pipNaturalSwitchingHandler.getClass();
                Log.w("PipNaturalSwitchingHandler", "mTaskVanishedTimeout: " + pipNaturalSwitchingHandler);
                pipNaturalSwitchingHandler.updateWaitingForTaskVanished("timeout", false);
                break;
            default:
                RectEvaluator rectEvaluator2 = PipNaturalSwitchingHandler.RECT_EVALUATOR;
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("startEnterAnimation: up-scale finished, ", (String) obj, "PipNaturalSwitchingHandler");
                break;
        }
    }
}
