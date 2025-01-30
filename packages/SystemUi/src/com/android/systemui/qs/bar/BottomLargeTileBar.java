package com.android.systemui.qs.bar;

import android.content.Context;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BottomLargeTileBar extends LargeTileBar {
    public BottomLargeTileBar(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        super.updateHeightMargins();
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBarRootView.getLayoutParams();
        layoutParams.topMargin = dimensionPixelSize;
        this.mBarRootView.setLayoutParams(layoutParams);
    }
}
