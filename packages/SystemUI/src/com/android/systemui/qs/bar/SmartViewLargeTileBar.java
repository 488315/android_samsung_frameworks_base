package com.android.systemui.qs.bar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSHost;
import com.android.systemui.tuner.TunerService;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SmartViewLargeTileBar extends LargeTileBar implements TunerService.Tunable {
    public boolean isSmartViewBarShowOnQSPanel;
    public final TunerService tunerService;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SmartViewLargeTileBar(Context context, TunerService tunerService, QSHost qSHost) {
        super(context, qSHost);
        this.tunerService = tunerService;
        boolean z = true;
        if (QpRune.QUICK_TILE_HIDE_FROM_BAR && !Intrinsics.areEqual(tunerService.getValue("hide_smart_view_large_tile_on_panel", "0"), "0")) {
            z = false;
        }
        this.isSmartViewBarShowOnQSPanel = z;
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        super.destroy();
        this.tunerService.removeTunable(this);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarWidthWeight(Context context) {
        return (QpRune.QUICK_TABLET || context.getResources().getConfiguration().orientation != 2) ? 4 : 2;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final View getClonedBarView() {
        View view = this.mClonedBarView;
        LinearLayout linearLayout = view != null ? (LinearLayout) view.findViewById(R.id.large_tile_container) : null;
        if (linearLayout != null && linearLayout.getChildCount() == 0) {
            makeCloneBar();
        }
        return this.mClonedBarView;
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        super.inflateViews(viewGroup);
        this.tunerService.addTunable(this, "hide_smart_view_large_tile_on_panel");
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if (str2 == null || !str.equals("hide_smart_view_large_tile_on_panel")) {
            return;
        }
        Log.d(this.TAG, FontProvider$$ExternalSyntheticOutline0.m("onTuningChanged() : key = ", str, ", newValue = ", str2));
        this.isSmartViewBarShowOnQSPanel = str2.equals("0");
        updateLayout(this.mBarRootView, this.mTileContainer);
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar, com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        updateLayout(this.mBarRootView, this.mTileContainer);
    }

    @Override // com.android.systemui.qs.bar.LargeTileBar
    public final void updateLayout(View view, LinearLayout linearLayout) {
        if (this.isSmartViewBarShowOnQSPanel) {
            updateLayout(linearLayout);
        }
        LinearLayout.LayoutParams layoutParams = view.getLayoutParams() != null ? (LinearLayout.LayoutParams) view.getLayoutParams() : new LinearLayout.LayoutParams(-1, this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height));
        boolean z = QpRune.QUICK_TABLET;
        int i = 0;
        if (z || this.mContext.getResources().getConfiguration().orientation != 2) {
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
        if (!this.isSmartViewBarShowOnQSPanel || (!z && this.mContext.getResources().getConfiguration().orientation == 2)) {
            i = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_between_margin);
        }
        layoutParams2.setMarginEnd(i);
        linearLayout.setLayoutParams(layoutParams2);
    }
}
