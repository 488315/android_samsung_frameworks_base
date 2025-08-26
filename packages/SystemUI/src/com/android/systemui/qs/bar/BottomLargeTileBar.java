package com.android.systemui.qs.bar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.tuner.TunerService;

/* loaded from: classes2.dex */
public class BottomLargeTileBar extends LargeTileBar {
    public BottomLargeTileBar(Context context) {
        super(context);
    }

    public final void adjustMarginForHideSmartView(LinearLayout linearLayout, boolean z) {
        View childAt;
        boolean z2 = ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).getValue(0, "hide_smart_view_large_tile_on_panel") != 0;
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("adjustMarginForHideSmartView : show=", "  isHideSmartView=", "BottomLargeTileBar", z, z2);
        if (z2 && z && (childAt = linearLayout.getChildAt(0)) != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams.setMarginEnd(this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_between_margin));
            linearLayout.getChildAt(0).setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarWidthWeight(Context context) {
        return (this.mSecQsUiDisplayModeInteractor.isTablet() || context.getResources().getConfiguration().orientation != 2) ? 4 : 2;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final View getClonedBarView() {
        removeCloneTileBG();
        makeCloneBar();
        return this.mClonedBarView;
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        super.inflateViews(viewGroup);
        this.mTileContainer.setWeightSum(2.0f);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void showBar(boolean z) {
        super.showBar(z);
        adjustMarginForHideSmartView(this.mTileContainer, z);
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        updateLayout(this.mBarRootView, this.mTileContainer);
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar
    public final void updateLayout(View view, LinearLayout linearLayout) {
        updateLayout(linearLayout);
        linearLayout.setWeightSum(2.0f);
        adjustMarginForHideSmartView(linearLayout, true);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBarRootView.getLayoutParams();
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height);
        if (this.mSecQsUiDisplayModeInteractor.isTablet() || this.mOrientation != 2) {
            layoutParams.width = -1;
            layoutParams.setMarginEnd(0);
        } else {
            layoutParams.weight = 2.0f;
            layoutParams.width = 0;
        }
        this.mBarRootView.setLayoutParams(layoutParams);
    }
}
