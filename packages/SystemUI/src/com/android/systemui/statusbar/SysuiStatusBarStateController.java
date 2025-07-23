package com.android.systemui.statusbar;

import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SysuiStatusBarStateController extends StatusBarStateController, CoreStartable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RankedListener {
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
