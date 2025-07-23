package com.android.systemui.globalactions.features;

import android.view.SemBlurInfo;
import android.view.View;
import com.samsung.android.globalactions.presentation.strategies.ViewInflateStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.ScreenCaptureUtil;
import com.samsung.android.globalactions.util.SystemConditions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class CapturedBlurStrategy implements ViewInflateStrategy {
    public final ScreenCaptureUtil mCaptureUtil;
    public final ConditionChecker mConditionChecker;

    public CapturedBlurStrategy(ScreenCaptureUtil screenCaptureUtil, ConditionChecker conditionChecker) {
        this.mCaptureUtil = screenCaptureUtil;
        this.mConditionChecker = conditionChecker;
    }

    public final void onInflateView(View view) {
        view.semSetBlurInfo(new SemBlurInfo.Builder(1).setBitmap(this.mCaptureUtil.takeScreenShot()).setColorCurvePreset(this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME) ? 136 : 137).setRadius(60).build());
    }
}
