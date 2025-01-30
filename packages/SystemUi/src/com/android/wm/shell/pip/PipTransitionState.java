package com.android.wm.shell.pip;

import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda7;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipTransitionState {
    public boolean mInSwipePipToHomeTransition;
    public final List mOnPipTransitionStateChangedListeners = new ArrayList();
    public int mState = 0;
    public long mTaskAppearedTime;

    public static String transitStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? Integer.toString(i) : "EXITING_PIP" : "ENTERED_PIP" : "ENTERING_PIP" : "ENTRY_SCHEDULED" : "TASK_APPEARED" : PeripheralBarcodeConstants.Symbology.UNDEFINED;
    }

    public final boolean isInPip() {
        int i = this.mState;
        return i >= 1 && i != 5;
    }

    public final void setTransitionState(int i) {
        if (this.mState != i) {
            int i2 = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) this.mOnPipTransitionStateChangedListeners;
                if (i2 >= arrayList.size()) {
                    break;
                }
                PipController$$ExternalSyntheticLambda7 pipController$$ExternalSyntheticLambda7 = (PipController$$ExternalSyntheticLambda7) arrayList.get(i2);
                int i3 = this.mState;
                Consumer consumer = pipController$$ExternalSyntheticLambda7.f$0.mOnIsInPipStateChangedListener;
                if (consumer != null) {
                    boolean z = i3 >= 1 && i3 != 5;
                    boolean z2 = i >= 1 && i != 5;
                    if (z2 != z) {
                        consumer.accept(Boolean.valueOf(z2));
                    }
                }
                i2++;
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                this.mTaskAppearedTime = i == 1 ? System.currentTimeMillis() : 0L;
            }
            StringBuilder sb = new StringBuilder("[PipTransitionState] setState: ");
            sb.append(transitStateToString(this.mState));
            sb.append(" -> ");
            sb.append(transitStateToString(i));
            sb.append(", Callers=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(CoreRune.SAFE_DEBUG ? 5 : 1, sb, "PipTaskOrganizer");
            this.mState = i;
        }
    }

    public final String toString() {
        String str;
        Object[] objArr = new Object[2];
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
        objArr[0] = str;
        objArr[1] = Boolean.valueOf(this.mInSwipePipToHomeTransition);
        return String.format("PipTransitionState(mState=%s, mInSwipePipToHomeTransition=%b)", objArr);
    }
}
