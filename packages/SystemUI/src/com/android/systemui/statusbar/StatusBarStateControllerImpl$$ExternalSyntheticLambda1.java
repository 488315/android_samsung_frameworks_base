package com.android.systemui.statusbar;

import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.Comparator;
import java.util.function.ToIntFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarStateControllerImpl$$ExternalSyntheticLambda1 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        Comparator comparator = StatusBarStateControllerImpl.sComparator;
        return ((SysuiStatusBarStateController.RankedListener) obj).mRank;
    }
}
