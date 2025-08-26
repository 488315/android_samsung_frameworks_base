package com.android.wm.shell.pip.phone;

import android.animation.RectEvaluator;
import java.util.function.Consumer;

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
