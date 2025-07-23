package com.android.wm.shell.pip.phone;

import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PipController;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$PipImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipController.PipImpl f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ PipController$PipImpl$$ExternalSyntheticLambda0(PipController.PipImpl pipImpl, Consumer consumer, int i) {
        this.$r8$classId = i;
        this.f$0 = pipImpl;
        this.f$1 = consumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipController.PipImpl pipImpl = this.f$0;
                ((ArrayList) PipController.this.mPipBoundsState.mOnPipExclusionBoundsChangeCallbacks).remove(this.f$1);
                break;
            case 1:
                PipController.PipImpl pipImpl2 = this.f$0;
                PipController.this.mPipBoundsState.addPipExclusionBoundsChangeCallback(this.f$1);
                break;
            default:
                PipController.PipImpl pipImpl3 = this.f$0;
                Consumer consumer = this.f$1;
                PipController pipController = PipController.this;
                if (consumer == null) {
                    int i = PipController.$r8$clinit;
                    pipController.getClass();
                    break;
                } else {
                    ((ArrayList) pipController.mOnIsInPipStateChangedListeners).add(consumer);
                    consumer.accept(Boolean.valueOf(PipTransitionState.isInPip(pipController.mPipTransitionState.mState)));
                    break;
                }
        }
    }
}
