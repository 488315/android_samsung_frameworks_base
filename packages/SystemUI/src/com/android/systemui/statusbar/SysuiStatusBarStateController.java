package com.android.systemui.statusbar;

import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;

public interface SysuiStatusBarStateController extends StatusBarStateController, CoreStartable {

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
