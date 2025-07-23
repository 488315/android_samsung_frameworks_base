package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class CustomizerActiveTileViewPager extends CustomizerTileViewPager {
    public CustomizerActiveTileViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.qs.customize.CustomizerTileViewPager
    public final void setIsTopEdit(boolean z) {
        this.mIsTopEdit = z;
        this.mIsScrollView = !z;
    }
}
