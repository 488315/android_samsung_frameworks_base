package com.android.wm.shell.pip.phone;

import android.animation.RectEvaluator;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipNaturalSwitchingHandler$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ PipNaturalSwitchingHandler f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PipNaturalSwitchingHandler pipNaturalSwitchingHandler = this.f$0;
        RectEvaluator rectEvaluator = PipNaturalSwitchingHandler.RECT_EVALUATOR;
        pipNaturalSwitchingHandler.updateWaitingForTaskVanished("task_vanished", false);
    }
}
