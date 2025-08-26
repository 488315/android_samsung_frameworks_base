package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import kotlin.Pair;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public class QSPopOverTileCustomizerBase extends QSTileCustomizerBase {
    public final String TAG;
    public int availableAreaBottomMargin;
    public int availableIndicatorHeight;
    public int availableTextHeight;
    public int cellHeight;
    public int editSummaryTextSize;
    public int editSummaryTextTopBottomMargin;
    public int gapBetweenArea;
    public boolean isActiveTilesLabelVisible;
    public boolean isAvailableTextVisible;
    public boolean isBigGapBetweenArea;
    public boolean isBigIndicatorHeight;
    public boolean isEditMoreSummaryVisible;
    public boolean isEditSummaryContainerVisible;
    public boolean isNoShowingAvailableBottomGap;
    public float maximumAvailableAreaRatio;
    public int minimumActiveAreaHeight;
    public int minimumAvailableAreaHeight;
    public int requiredActiveHeight;
    public int requiredActiveTileRowNum;
    public int requiredAvailableHeight;
    public int requiredAvailableTileRowNum;
    public int tileAreaTopBottomMinimumGap;
    public int tileIconHeight;
    public int tileLabelHeight;
    public int topSummaryAndButtonsHeight;

    public QSPopOverTileCustomizerBase(Context context, int i, int i2) throws Resources.NotFoundException {
        super(context, i, i2);
        this.TAG = Reflection.getOrCreateKotlinClass(QSPopOverTileCustomizerBase.class).getSimpleName();
        this.tileAreaTopBottomMinimumGap = getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin);
        this.editSummaryTextSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_summary_text);
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.availableTextHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.availableIndicatorHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_edit_available_area_indicator_bottom);
        this.maximumAvailableAreaRatio = getResources().getFloat(R.dimen.qs_available_area_max_ratio);
        this.topSummaryAndButtonsHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_items_top_margin);
        this.editSummaryTextTopBottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_active_between_margin);
        this.gapBetweenArea = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_tile_edit_gap_between_areas);
        int i3 = this.availableTextHeight + this.availableIndicatorHeight;
        int i4 = this.cellHeight;
        this.minimumAvailableAreaHeight = i3 + i4;
        this.minimumActiveAreaHeight = (this.tileAreaTopBottomMinimumGap * 2) + i4;
        this.requiredAvailableTileRowNum = 2;
        int integer = getResources().getInteger(R.integer.qs_active_area_default_tile_row);
        this.requiredActiveTileRowNum = integer;
        int i5 = this.availableTextHeight + this.availableIndicatorHeight;
        int i6 = this.cellHeight;
        this.requiredAvailableHeight = (this.requiredAvailableTileRowNum * i6) + i5;
        this.requiredActiveHeight = (this.tileAreaTopBottomMinimumGap * 2) + (i6 * integer);
        this.isBigGapBetweenArea = true;
        this.isBigIndicatorHeight = true;
        this.isAvailableTextVisible = true;
        this.isActiveTilesLabelVisible = true;
        this.isEditMoreSummaryVisible = true;
        this.isEditSummaryContainerVisible = true;
        this.isNoShowingAvailableBottomGap = true;
        this.mAvailableColumns = 5;
    }

    public Pair calculateActiveAvailableHeight() {
        return new Pair(0, 0);
    }

    public final void calculateAreasHeight() throws Resources.NotFoundException {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2;
        ViewGroup.LayoutParams layoutParams3;
        Pair pairCalculateActiveAvailableHeight = calculateActiveAvailableHeight();
        int iIntValue = ((Number) pairCalculateActiveAvailableHeight.getFirst()).intValue();
        int iIntValue2 = ((Number) pairCalculateActiveAvailableHeight.getSecond()).intValue();
        int displayableHeight = getDisplayableHeight();
        int i = this.topSummaryAndButtonsHeight + iIntValue + this.gapBetweenArea + iIntValue2;
        int iMin = Math.min(i, displayableHeight);
        int i2 = this.availableTextHeight;
        int i3 = iIntValue2 - (this.availableIndicatorHeight + i2);
        if (i > displayableHeight) {
            if (this.isBigIndicatorHeight) {
                this.isBigIndicatorHeight = false;
                int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sec_qs_page_indicator_container_height);
                this.minimumAvailableAreaHeight -= this.availableIndicatorHeight - dimensionPixelSize;
                this.availableIndicatorHeight = dimensionPixelSize;
            } else if (this.isBigGapBetweenArea) {
                this.isBigGapBetweenArea = false;
                this.gapBetweenArea = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_tile_edit_gap_between_areas);
            } else if (this.isAvailableTextVisible) {
                this.isAvailableTextVisible = false;
                int i4 = this.minimumAvailableAreaHeight;
                int i5 = this.tileAreaTopBottomMinimumGap;
                this.minimumAvailableAreaHeight = i4 - (i2 - i5);
                this.availableTextHeight = i5;
            } else if (this.isEditMoreSummaryVisible) {
                this.isEditMoreSummaryVisible = false;
                this.topSummaryAndButtonsHeight -= this.editSummaryTextSize;
            } else if (this.isEditSummaryContainerVisible) {
                this.isEditSummaryContainerVisible = false;
                this.topSummaryAndButtonsHeight -= ((this.editSummaryTextTopBottomMargin * 2) + this.editSummaryTextSize) - this.gapBetweenArea;
            } else if (this.mIsTopEdit || !this.isActiveTilesLabelVisible) {
                Log.d(this.TAG, "This case cannot be displayed properly because the screen height is much smaller than we expected.");
            } else {
                this.isActiveTilesLabelVisible = false;
                this.minimumActiveAreaHeight = (this.tileAreaTopBottomMinimumGap * 2) + this.tileIconHeight;
            }
            calculateAreasHeight();
            return;
        }
        if (displayableHeight - i < getResources().getDimensionPixelSize(R.dimen.qs_pop_over_edit_available_area_indicator_bottom) && this.isNoShowingAvailableBottomGap) {
            this.availableAreaBottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_edit_available_area_indicator_bottom);
            this.isNoShowingAvailableBottomGap = false;
            calculateAreasHeight();
            return;
        }
        View viewFindViewById = findViewById(R.id.qs_available_paged_indicator_container);
        if (viewFindViewById != null && (layoutParams3 = viewFindViewById.getLayoutParams()) != null) {
            layoutParams3.height = this.availableIndicatorHeight;
        }
        findViewById(R.id.qs_edit_available_text).setVisibility(this.isAvailableTextVisible ? 0 : 8);
        View viewFindViewById2 = findViewById(R.id.qs_available_area);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setPadding(getPaddingLeft(), this.isAvailableTextVisible ? 0 : this.tileAreaTopBottomMinimumGap, getPaddingRight(), getPaddingBottom());
            ViewGroup.LayoutParams layoutParams4 = viewFindViewById2.getLayoutParams();
            layoutParams4.height = iIntValue2;
            viewFindViewById2.setLayoutParams(layoutParams4);
        }
        View viewFindViewById3 = findViewById(R.id.qs_edit_more_summary);
        if (viewFindViewById3 != null) {
            viewFindViewById3.setVisibility((this.mIsTopEdit && this.isEditMoreSummaryVisible) ? 0 : 8);
        }
        View viewFindViewById4 = findViewById(R.id.qs_edit_summary_container);
        if (viewFindViewById4 != null) {
            viewFindViewById4.setVisibility(this.isEditSummaryContainerVisible ? 0 : 8);
        }
        int popOverMargin = this.mResourcePicker.getPopOverMargin(this.mContext);
        View viewFindViewById5 = findViewById(R.id.qs_customize_top_summary_buttons);
        if (viewFindViewById5 != null) {
            viewFindViewById5.setPadding(popOverMargin, getPaddingTop(), popOverMargin, this.isEditSummaryContainerVisible ? 0 : this.gapBetweenArea);
        }
        View viewFindViewById6 = findViewById(R.id.qs_active_page_parent);
        if (viewFindViewById6 != null && (layoutParams2 = viewFindViewById6.getLayoutParams()) != null) {
            layoutParams2.height = iIntValue;
        }
        ViewGroup.LayoutParams layoutParams5 = findViewById(R.id.tile_edit_layout).getLayoutParams();
        if (layoutParams5 != null) {
            layoutParams5.height = iMin;
        }
        View viewFindViewById7 = findViewById(R.id.qs_available_page_parent);
        if (viewFindViewById7 != null && (layoutParams = viewFindViewById7.getLayoutParams()) != null) {
            layoutParams.height = i3;
        }
        int iMax = Math.max(i3 / this.cellHeight, 1);
        int i6 = this.requiredAvailableTileRowNum;
        this.mAvailableRows = i6;
        this.mAvailableRows = Math.min(i6, iMax);
        setActiveAreaScrollHeight();
    }

    @Override // com.android.systemui.qs.customize.view.QSTileCustomizerBase
    public final void calculateAvailableArea() throws Resources.NotFoundException {
        initResources();
        calculateAreasHeight();
    }

    public final int getDisplayableHeight() {
        return DeviceState.getDisplayHeight(this.mContext) - ((this.mCutoutTopMargin + this.mCutoutBottomMargin) + this.availableAreaBottomMargin);
    }

    public int getIDEAL_NUM_OF_ROW_REQUIRED() {
        return 2;
    }

    public void initResources() throws Resources.NotFoundException {
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.availableTextHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.availableIndicatorHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_edit_available_area_indicator_bottom);
        this.tileAreaTopBottomMinimumGap = getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin);
        this.editSummaryTextSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_summary_text);
        this.editSummaryTextTopBottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_active_between_margin);
        this.requiredAvailableTileRowNum = getIDEAL_NUM_OF_ROW_REQUIRED();
        this.availableAreaBottomMargin = 0;
        this.isBigGapBetweenArea = true;
        this.isBigIndicatorHeight = true;
        this.isAvailableTextVisible = true;
        this.isEditMoreSummaryVisible = true;
        this.isEditSummaryContainerVisible = true;
        this.isActiveTilesLabelVisible = true;
        this.isNoShowingAvailableBottomGap = true;
    }

    public void setActiveAreaScrollHeight() throws Resources.NotFoundException {
        ViewGroup.LayoutParams layoutParams;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin);
        View viewFindViewById = findViewById(R.id.qs_active_page_parent);
        int i = (viewFindViewById == null || (layoutParams = viewFindViewById.getLayoutParams()) == null) ? this.cellHeight + (dimensionPixelSize * 2) : layoutParams.height;
        View viewFindViewById2 = findViewById(R.id.qs_active_page_content);
        if (viewFindViewById2 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewFindViewById2.getLayoutParams();
            layoutParams2.topMargin = dimensionPixelSize;
            layoutParams2.height = i - (dimensionPixelSize * 2);
            viewFindViewById2.setLayoutParams(layoutParams2);
        }
    }
}
