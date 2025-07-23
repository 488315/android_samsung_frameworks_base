package com.google.android.setupcompat.internal;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FallbackThemeWrapper extends ContextThemeWrapper {
    public FallbackThemeWrapper(Context context, int i) {
        super(context, i);
    }

    @Override // android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(i, false);
    }
}
