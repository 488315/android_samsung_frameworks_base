package com.android.systemui.statusbar;

import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.function.ToIntFunction;

public final /* synthetic */ class StatusBarStateControllerImpl$$ExternalSyntheticLambda1 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((SysuiStatusBarStateController.RankedListener) obj).mRank;
    }
}
