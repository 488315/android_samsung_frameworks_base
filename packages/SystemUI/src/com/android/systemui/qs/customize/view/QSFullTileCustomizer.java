package com.android.systemui.qs.customize.view;

import android.content.Context;

/* loaded from: classes2.dex */
public class QSFullTileCustomizer extends QSTileCustomizerBase {
    public QSFullTileCustomizer(Context context, int i, int i2) {
        super(context, i, i2);
        if (QSTileCustomizerBase.isLargeScreen()) {
            this.mAvailableColumns = 5;
        }
    }
}
