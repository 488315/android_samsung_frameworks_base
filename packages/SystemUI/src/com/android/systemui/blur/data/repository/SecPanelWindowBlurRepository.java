package com.android.systemui.blur.data.repository;

import android.content.Context;
import com.android.systemui.blur.QSColorCurve;

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
