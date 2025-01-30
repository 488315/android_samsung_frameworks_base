package com.android.systemui.statusbar;

import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SysuiStatusBarStateController extends StatusBarStateController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RankedListener {
        public final StatusBarStateController.StateListener mListener;
        public final int mRank;

        public RankedListener(StatusBarStateController.StateListener stateListener, int i) {
            this.mListener = stateListener;
            this.mRank = i;
        }
    }

    default void setState$1(int i) {
        ((StatusBarStateControllerImpl) this).setState(i, false);
    }
}
