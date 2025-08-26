package com.android.systemui.statusbar;

import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.Comparator;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarStateControllerImpl$$ExternalSyntheticLambda1 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        Comparator comparator = StatusBarStateControllerImpl.sComparator;
        return ((SysuiStatusBarStateController.RankedListener) obj).mRank;
    }
}
