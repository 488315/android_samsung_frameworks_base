package com.android.wm.shell.pip2.phone;

import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.pip2.phone.PipController;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$PipImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipController.PipImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PipController$PipImpl$$ExternalSyntheticLambda0(PipController.PipImpl pipImpl, EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0) {
        this.$r8$classId = 3;
        this.f$0 = pipImpl;
        this.f$1 = edgeBackGestureHandler$$ExternalSyntheticLambda0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipController.PipImpl pipImpl = this.f$0;
                pipImpl.this$0.mPipBoundsState.addPipExclusionBoundsChangeCallback((Consumer) this.f$1);
                break;
            case 1:
                PipController.PipImpl pipImpl2 = this.f$0;
                ((ArrayList) pipImpl2.this$0.mPipBoundsState.mOnPipExclusionBoundsChangeCallbacks).remove((Consumer) this.f$1);
                break;
            case 2:
                PipController.PipImpl pipImpl3 = this.f$0;
                Consumer consumer = (Consumer) this.f$1;
                PipController pipController = pipImpl3.this$0;
                if (consumer == null) {
                    pipController.getClass();
                    break;
                } else {
                    ((ArrayList) pipController.mOnIsInPipStateChangedListeners).add(consumer);
                    consumer.accept(Boolean.valueOf(pipController.mPipTransitionState.isInPip()));
                    break;
                }
            default:
                PipController.PipImpl pipImpl4 = this.f$0;
                EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0 = (EdgeBackGestureHandler$$ExternalSyntheticLambda0) this.f$1;
                PipController pipController2 = pipImpl4.this$0;
                if (edgeBackGestureHandler$$ExternalSyntheticLambda0 == null) {
                    pipController2.getClass();
                    break;
                } else {
                    ((ArrayList) pipController2.mOnIsInPipStateChangedListeners).remove(edgeBackGestureHandler$$ExternalSyntheticLambda0);
                    break;
                }
        }
    }

    public /* synthetic */ PipController$PipImpl$$ExternalSyntheticLambda0(PipController.PipImpl pipImpl, Consumer consumer, int i) {
        this.$r8$classId = i;
        this.f$0 = pipImpl;
        this.f$1 = consumer;
    }
}
