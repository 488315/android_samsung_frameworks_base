package com.android.systemui.globalactions.features;

import android.view.SemBlurInfo;
import android.view.View;
import com.samsung.android.globalactions.presentation.strategies.ViewInflateStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.ScreenCaptureUtil;
import com.samsung.android.globalactions.util.SystemConditions;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CapturedBlurStrategy implements ViewInflateStrategy {
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
