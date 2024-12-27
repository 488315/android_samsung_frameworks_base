package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
