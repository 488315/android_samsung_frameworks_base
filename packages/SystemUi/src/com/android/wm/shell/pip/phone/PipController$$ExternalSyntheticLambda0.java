package com.android.wm.shell.pip.phone;

import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PipController$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
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
            case 1:
                PipController.PipImpl pipImpl = (PipController.PipImpl) this.f$0;
                Consumer consumer = (Consumer) this.f$1;
                PipBoundsState pipBoundsState = PipController.this.mPipBoundsState;
                ArrayList arrayList = (ArrayList) pipBoundsState.mOnPipExclusionBoundsChangeCallbacks;
                arrayList.add(consumer);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((Consumer) it.next()).accept(pipBoundsState.getBounds());
                }
                break;
            case 2:
                PipController.PipImpl pipImpl2 = (PipController.PipImpl) this.f$0;
                Consumer consumer2 = (Consumer) this.f$1;
                PipController pipController2 = PipController.this;
                pipController2.mOnIsInPipStateChangedListener = consumer2;
                if (consumer2 != null) {
                    consumer2.accept(Boolean.valueOf(pipController2.mPipTransitionState.isInPip()));
                    break;
                }
                break;
            default:
                ((ArrayList) PipController.this.mPipBoundsState.mOnPipExclusionBoundsChangeCallbacks).remove((Consumer) this.f$1);
                break;
        }
    }
}
