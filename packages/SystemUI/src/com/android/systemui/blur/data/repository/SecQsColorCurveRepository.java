package com.android.systemui.blur.data.repository;

import android.content.Context;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor;

/* loaded from: classes.dex */
public final class SecQsColorCurveRepository {
    public final boolean hasCustomColorBg;
    public final QSColorCurve qsColorCurve;

    public SecQsColorCurveRepository(Context context, SecBlurCustomColorInteractor secBlurCustomColorInteractor) {
        this.qsColorCurve = new QSColorCurve(context);
        this.hasCustomColorBg = ((Boolean) secBlurCustomColorInteractor.hasCustomColorApplied.$$delegate_0.getValue()).booleanValue();
    }
}
