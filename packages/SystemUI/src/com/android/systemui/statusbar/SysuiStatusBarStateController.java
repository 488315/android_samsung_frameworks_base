package com.android.systemui.statusbar;

import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SysuiStatusBarStateController extends StatusBarStateController, CoreStartable {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class RankedListener {
        public final StatusBarStateController.StateListener mListener;
        public final int mRank;

        public RankedListener(StatusBarStateController.StateListener stateListener, int i) {
            this.mListener = stateListener;
            this.mRank = i;
        }
    }

    default void setState(int i) {
        ((StatusBarStateControllerImpl) this).setState(i, false);
    }
}
