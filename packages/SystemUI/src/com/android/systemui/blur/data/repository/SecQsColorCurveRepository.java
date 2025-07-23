package com.android.systemui.blur.data.repository;

import android.content.Context;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecQsColorCurveRepository {
    public final boolean hasCustomColorBg;
    public final QSColorCurve qsColorCurve;

    public SecQsColorCurveRepository(Context context, SecBlurCustomColorInteractor secBlurCustomColorInteractor) {
        this.qsColorCurve = new QSColorCurve(context);
        this.hasCustomColorBg = ((Boolean) secBlurCustomColorInteractor.hasCustomColorApplied.$$delegate_0.getValue()).booleanValue();
    }
}
