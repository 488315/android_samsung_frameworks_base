package com.android.systemui.globalactions.features;

import android.view.SemBlurInfo;
import android.view.View;
import com.samsung.android.globalactions.presentation.strategies.ViewInflateStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SepBlurStrategy implements ViewInflateStrategy {
    public final ConditionChecker mConditionChecker;

    public SepBlurStrategy(ConditionChecker conditionChecker) {
        this.mConditionChecker = conditionChecker;
    }

    public final void onInflateView(View view) {
        view.semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME) ? 12 : 15).build());
    }
}
