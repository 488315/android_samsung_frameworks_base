package com.android.wm.shell.pip.phone;

import android.window.WindowContainerTransaction;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PipController$$ExternalSyntheticLambda4(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipController pipController = (PipController) this.f$0;
                DisplayLayout displayLayout = (DisplayLayout) this.f$1;
                int i = PipController.$r8$clinit;
                pipController.getClass();
                boolean z = Transitions.ENABLE_SHELL_TRANSITIONS;
                PipDisplayLayoutState pipDisplayLayoutState = pipController.mPipDisplayLayoutState;
                boolean z2 = z && pipDisplayLayoutState.getDisplayLayout().mRotation != displayLayout.mRotation;
                pipDisplayLayoutState.mDisplayLayout.set(displayLayout);
                WindowContainerTransaction windowContainerTransaction = z2 ? new WindowContainerTransaction() : null;
                pipController.updateMovementBounds(null, z2, false, false, windowContainerTransaction);
                if (windowContainerTransaction != null) {
                    pipController.mPipTaskOrganizer.applyFinishBoundsResize(1, windowContainerTransaction, false);
                    break;
                }
                break;
            default:
                PipController.PipImpl pipImpl = (PipController.PipImpl) this.f$0;
                EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0 = (EdgeBackGestureHandler$$ExternalSyntheticLambda0) this.f$1;
                PipController pipController2 = PipController.this;
                if (edgeBackGestureHandler$$ExternalSyntheticLambda0 == null) {
                    int i2 = PipController.$r8$clinit;
                    pipController2.getClass();
                    break;
                } else {
                    ((ArrayList) pipController2.mOnIsInPipStateChangedListeners).remove(edgeBackGestureHandler$$ExternalSyntheticLambda0);
                    break;
                }
        }
    }
}
