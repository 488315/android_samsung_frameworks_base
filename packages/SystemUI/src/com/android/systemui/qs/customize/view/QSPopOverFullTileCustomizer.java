package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;
import kotlin.Pair;

/* loaded from: classes2.dex */
public final class QSPopOverFullTileCustomizer extends QSPopOverTileCustomizerBase {
    public QSPopOverFullTileCustomizer(Context context, int i, int i2) {
        super(context, i, i2);
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final Pair calculateActiveAvailableHeight() {
        int displayableHeight = getDisplayableHeight();
        int iMax = this.isAvailableTextVisible ? Math.max(Math.min(this.requiredAvailableHeight, (int) (displayableHeight * this.maximumAvailableAreaRatio)), this.minimumAvailableAreaHeight) : this.minimumAvailableAreaHeight;
        return new Pair(Integer.valueOf(this.isActiveTilesLabelVisible ? Math.max(Math.min(this.requiredActiveHeight, displayableHeight - ((this.topSummaryAndButtonsHeight + this.gapBetweenArea) + iMax)), this.minimumActiveAreaHeight) : this.minimumActiveAreaHeight), Integer.valueOf(iMax));
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final void initResources() throws Resources.NotFoundException {
        super.initResources();
        this.minimumAvailableAreaHeight = this.availableTextHeight + this.availableIndicatorHeight + this.cellHeight;
        this.maximumAvailableAreaRatio = getResources().getFloat(R.dimen.qs_available_area_max_ratio);
        this.requiredActiveTileRowNum = getResources().getInteger(R.integer.qs_active_area_default_tile_row);
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.topSummaryAndButtonsHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_items_top_margin);
        this.requiredAvailableHeight = ((this.tileIconHeight + this.tileLabelHeight) * this.requiredAvailableTileRowNum) + getResources().getDimensionPixelSize(R.dimen.qs_pop_over_edit_available_area_indicator_bottom) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.gapBetweenArea = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_tile_edit_gap_between_areas);
        this.requiredActiveHeight = (getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin) * 2) + (this.cellHeight * this.requiredActiveTileRowNum);
    }
}
