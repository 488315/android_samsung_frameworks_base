package com.google.android.setupcompat.internal;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FallbackThemeWrapper extends ContextThemeWrapper {
    public FallbackThemeWrapper(Context context, int i) {
        super(context, i);
    }

    @Override // android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(i, false);
    }
}
