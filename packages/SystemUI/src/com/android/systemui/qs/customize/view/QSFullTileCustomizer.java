package com.android.systemui.qs.customize.view;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class QSFullTileCustomizer extends QSTileCustomizerBase {
    public QSFullTileCustomizer(Context context, int i) {
        super(context, i);
        if (QSTileCustomizerBase.isLargeScreen()) {
            this.mAvailableColumns = 5;
        }
    }
}
