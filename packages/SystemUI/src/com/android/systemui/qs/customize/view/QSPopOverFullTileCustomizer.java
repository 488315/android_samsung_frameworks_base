package com.android.systemui.qs.customize.view;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPopOverFullTileCustomizer extends QSPopOverTileCustomizerBase {
    public QSPopOverFullTileCustomizer(Context context, int i) {
        super(context, i);
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final Pair calculateActiveAvailableHeight() {
        int displayHeight = DeviceState.getDisplayHeight(this.mContext) - (this.mCutoutTopMargin + this.navBarHeight);
        int max = this.isAvailableTextVisible ? Math.max(Math.min(this.requiredAvailableHeight, (int) (displayHeight * this.maximumAvailableAreaRatio)), this.minimumAvailableAreaHeight) : this.minimumAvailableAreaHeight;
        return new Pair(Integer.valueOf(this.isActiveTilesLabelVisible ? Math.max(Math.min(this.requiredActiveHeight, displayHeight - ((this.topSummaryAndButtonsHeight + this.gapBetweenArea) + max)), this.minimumActiveAreaHeight) : this.minimumActiveAreaHeight), Integer.valueOf(max));
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final void initResources() {
        super.initResources();
        this.minimumAvailableAreaHeight = this.availableTextHeight + this.availableIndicatorHeight + this.cellHeight;
        this.maximumAvailableAreaRatio = getResources().getFloat(R.dimen.qs_available_area_max_ratio);
        this.requiredActiveTileRowNum = getResources().getInteger(R.integer.qs_active_area_default_tile_row);
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.topSummaryAndButtonsHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_items_top_margin);
        this.requiredAvailableHeight = ((this.tileIconHeight + this.tileLabelHeight) * this.requiredAvailableTileRowNum) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_area_indicator_bottom) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.gapBetweenArea = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_tile_edit_gap_between_areas);
        this.requiredActiveHeight = (getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin) * 2) + (this.cellHeight * this.requiredActiveTileRowNum);
    }
}
