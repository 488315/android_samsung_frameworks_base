package com.android.systemui.blur.data.repository;

import android.view.SemBlurInfo;
import com.android.systemui.blur.BouncerColorCurve;
import com.android.systemui.keyguard.KeyguardFoldController;

/* loaded from: classes.dex */
public final class SecBouncerColorCurveRepository {
    public final BouncerColorCurve bouncerColorCurve = new BouncerColorCurve();

    public SecBouncerColorCurveRepository(KeyguardFoldController keyguardFoldController) {
    }

    public final SemBlurInfo.Builder getBlurInfo() {
        SemBlurInfo.Builder builder = new SemBlurInfo.Builder(1);
        BouncerColorCurve bouncerColorCurve = this.bouncerColorCurve;
        builder.setColorCurve(0.0f, bouncerColorCurve.mCurve, bouncerColorCurve.mMinX, bouncerColorCurve.mMaxX, bouncerColorCurve.mMinY, bouncerColorCurve.mMaxY);
        builder.setRadius((int) bouncerColorCurve.mRadius);
        return builder;
    }
}
