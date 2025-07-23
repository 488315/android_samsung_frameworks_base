package com.android.systemui.blur.data.repository;

import android.content.Context;
import com.android.systemui.blur.QSColorCurve;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecPanelWindowBlurRepository {
    public final Context context;
    public final QSColorCurve qsColorCurve;
    public int windowBlurRadius;

    public SecPanelWindowBlurRepository(Context context) {
        this.context = context;
        this.qsColorCurve = new QSColorCurve(context);
    }
}
