package com.android.wm.shell.pip;

import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda9;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class PipTransitionState {
    public boolean mInSwipePipToHomeTransition;
    public final List mOnPipTransitionStateChangedListeners = new ArrayList();
    public int mState = 0;
    public long mTaskAppearedTime;

    public static boolean isInPip(int i) {
        return i >= 1 && i != 5;
    }

    public static String transitStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? Integer.toString(i) : "EXITING_PIP" : "ENTERED_PIP" : "ENTERING_PIP" : "ENTRY_SCHEDULED" : "TASK_APPEARED" : PeripheralBarcodeConstants.Symbology.UNDEFINED;
    }

    public final boolean hasEnteredPip() {
        return this.mState == 4;
    }

    public final void setTransitionState(int i) {
        if (this.mState != i) {
            for (int i2 = 0; i2 < ((ArrayList) this.mOnPipTransitionStateChangedListeners).size(); i2++) {
                PipController$$ExternalSyntheticLambda9 pipController$$ExternalSyntheticLambda9 = (PipController$$ExternalSyntheticLambda9) ((ArrayList) this.mOnPipTransitionStateChangedListeners).get(i2);
                boolean zIsInPip = isInPip(this.mState);
                boolean zIsInPip2 = isInPip(i);
                PipController pipController = pipController$$ExternalSyntheticLambda9.f$0;
                if (zIsInPip2 != zIsInPip) {
                    ArrayList arrayList = (ArrayList) pipController.mOnIsInPipStateChangedListeners;
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        ((Consumer) obj).accept(Boolean.valueOf(zIsInPip2));
                    }
                } else {
                    int i4 = PipController.$r8$clinit;
                }
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                this.mTaskAppearedTime = i == 1 ? System.currentTimeMillis() : 0L;
            }
            int i5 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            StringBuilder sb = new StringBuilder("[PipTransitionState] setState: ");
            sb.append(transitStateToString(this.mState));
            sb.append(" -> ");
            sb.append(transitStateToString(i));
            sb.append(", Callers=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(1, "PipTaskOrganizer", sb);
            this.mState = i;
        }
    }

    public final String toString() {
        String str;
        int i = this.mState;
        if (i == 0) {
            str = "undefined";
        } else if (i == 1) {
            str = "task-appeared";
        } else if (i == 2) {
            str = "entry-scheduled";
        } else if (i == 3) {
            str = "entering-pip";
        } else if (i == 4) {
            str = "entered-pip";
        } else {
            if (i != 5) {
                throw new IllegalStateException("Unknown state: " + this.mState);
            }
            str = "exiting-pip";
        }
        return "PipTransitionState(mState=" + str + ", mInSwipePipToHomeTransition=" + this.mInSwipePipToHomeTransition + ")";
    }
}
