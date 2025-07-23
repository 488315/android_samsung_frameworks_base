package com.android.systemui.qs.bar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SmartViewLargeTileBar extends LargeTileBar implements TunerService.Tunable {
    public final TunerService tunerService;

    public SmartViewLargeTileBar(Context context, TunerService tunerService) {
        super(context);
        this.tunerService = tunerService;
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        super.destroy();
        this.tunerService.removeTunable(this);
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
        this.tunerService.addTunable(this, "hide_smart_view_large_tile_on_panel");
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if (str2 == null || !Intrinsics.areEqual(str, "hide_smart_view_large_tile_on_panel")) {
            return;
        }
        Log.d(this.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("onTuningChanged() : key = ", str, ", newValue = ", str2));
        updateLayout(this.mBarRootView, this.mTileContainer);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void showBar(boolean z) {
        super.showBar(z);
        if (this.mBarRootView == null) {
            this.mShowing = z;
        }
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        updateLayout(this.mBarRootView, this.mTileContainer);
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar
    public final void updateLayout(View view, LinearLayout linearLayout) {
        updateLayout(linearLayout);
        LinearLayout.LayoutParams layoutParams = view.getLayoutParams() != null ? (LinearLayout.LayoutParams) view.getLayoutParams() : new LinearLayout.LayoutParams(-1, this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height));
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.mSecQsUiDisplayModeInteractor;
        int i = 0;
        if (secQsUiDisplayModeInteractor.isTablet() || this.mOrientation != 2) {
            layoutParams.weight = 2.0f;
            layoutParams.width = -1;
            layoutParams.setMarginEnd(0);
            linearLayout.setWeightSum(2.0f);
        } else {
            layoutParams.weight = 4.0f;
            layoutParams.width = 0;
            linearLayout.setWeightSum(4.0f);
        }
        view.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        if (!secQsUiDisplayModeInteractor.isTablet() && this.mContext.getResources().getConfiguration().orientation == 2) {
            i = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_between_margin);
        }
        layoutParams2.setMarginEnd(i);
        linearLayout.setLayoutParams(layoutParams2);
    }
}
