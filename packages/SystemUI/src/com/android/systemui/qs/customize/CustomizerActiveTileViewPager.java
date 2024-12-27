package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.AttributeSet;

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
