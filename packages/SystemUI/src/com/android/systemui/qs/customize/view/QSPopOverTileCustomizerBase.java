package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import kotlin.Pair;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class QSPopOverTileCustomizerBase extends QSTileCustomizerBase {
    public final String TAG;
    public int availableIndicatorHeight;
    public int availableTextHeight;
    public int cellHeight;
    public int editSummaryTextSize;
    public int editSummaryTextTopBottomMargin;
    public int gapBetweenArea;
    public boolean isActiveTilesLabelVisible;
    public boolean isAvailableTextVisible;
    public boolean isEditMoreSummaryVisible;
    public boolean isEditSummaryContainerVisible;
    public float maximumAvailableAreaRatio;
    public int minimumActiveAreaHeight;
    public int minimumAvailableAreaHeight;
    public int navBarHeight;
    public int requiredActiveHeight;
    public int requiredActiveTileRowNum;
    public int requiredAvailableHeight;
    public int requiredAvailableTileRowNum;
    public int tileAreaTopBottomMinimumGap;
    public int tileIconHeight;
    public int tileLabelHeight;
    public int topSummaryAndButtonsHeight;

    public QSPopOverTileCustomizerBase(Context context, int i) {
        super(context, i);
        this.TAG = Reflection.getOrCreateKotlinClass(QSPopOverTileCustomizerBase.class).getSimpleName();
        this.tileAreaTopBottomMinimumGap = getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin);
        this.editSummaryTextSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_summary_text);
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.availableTextHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.availableIndicatorHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_area_indicator_bottom);
        this.maximumAvailableAreaRatio = getResources().getFloat(R.dimen.qs_available_area_max_ratio);
        this.topSummaryAndButtonsHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_items_top_margin);
        this.editSummaryTextTopBottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_active_between_margin);
        this.gapBetweenArea = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_tile_edit_gap_between_areas);
        int i2 = this.availableTextHeight + this.availableIndicatorHeight;
        int i3 = this.cellHeight;
        this.minimumAvailableAreaHeight = i2 + i3;
        this.minimumActiveAreaHeight = (this.tileAreaTopBottomMinimumGap * 2) + i3;
        this.requiredAvailableTileRowNum = 2;
        int integer = getResources().getInteger(R.integer.qs_active_area_default_tile_row);
        this.requiredActiveTileRowNum = integer;
        int i4 = this.availableTextHeight + this.availableIndicatorHeight;
        int i5 = this.cellHeight;
        this.requiredAvailableHeight = (this.requiredAvailableTileRowNum * i5) + i4;
        this.requiredActiveHeight = (this.tileAreaTopBottomMinimumGap * 2) + (i5 * integer);
        this.navBarHeight = getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
        this.isAvailableTextVisible = true;
        this.isActiveTilesLabelVisible = true;
        this.isEditMoreSummaryVisible = true;
        this.isEditSummaryContainerVisible = true;
        this.mAvailableColumns = 5;
    }

    public Pair calculateActiveAvailableHeight() {
        return new Pair(0, 0);
    }

    public final void calculateAreasHeight() {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2;
        Pair calculateActiveAvailableHeight = calculateActiveAvailableHeight();
        int intValue = ((Number) calculateActiveAvailableHeight.getFirst()).intValue();
        int intValue2 = ((Number) calculateActiveAvailableHeight.getSecond()).intValue();
        int displayHeight = DeviceState.getDisplayHeight(this.mContext) - (this.mCutoutTopMargin + this.navBarHeight);
        int i = this.topSummaryAndButtonsHeight + intValue + this.gapBetweenArea + intValue2;
        int min = Math.min(i, displayHeight);
        int i2 = this.availableTextHeight;
        int i3 = intValue2 - (this.availableIndicatorHeight + i2);
        if (i > displayHeight) {
            if (this.isAvailableTextVisible) {
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
        findViewById(R.id.qs_edit_available_text).setVisibility(this.isAvailableTextVisible ? 0 : 8);
        View findViewById = findViewById(R.id.qs_available_area);
        if (findViewById != null) {
            findViewById.setPadding(getPaddingLeft(), this.isAvailableTextVisible ? 0 : this.tileAreaTopBottomMinimumGap, getPaddingRight(), getPaddingBottom());
            ViewGroup.LayoutParams layoutParams3 = findViewById.getLayoutParams();
            layoutParams3.height = intValue2;
            findViewById.setLayoutParams(layoutParams3);
        }
        View findViewById2 = findViewById(R.id.qs_edit_more_summary);
        if (findViewById2 != null) {
            findViewById2.setVisibility((this.mIsTopEdit && this.isEditMoreSummaryVisible) ? 0 : 8);
        }
        View findViewById3 = findViewById(R.id.qs_edit_summary_container);
        if (findViewById3 != null) {
            findViewById3.setVisibility(this.isEditSummaryContainerVisible ? 0 : 8);
        }
        int popOverMargin = this.mResourcePicker.resourcePickHelper.getTargetPicker().getPopOverMargin(this.mContext);
        View findViewById4 = findViewById(R.id.qs_customize_top_summary_buttons);
        if (findViewById4 != null) {
            findViewById4.setPadding(popOverMargin, getPaddingTop(), popOverMargin, this.isEditSummaryContainerVisible ? 0 : this.gapBetweenArea);
        }
        View findViewById5 = findViewById(R.id.qs_active_page_parent);
        if (findViewById5 != null && (layoutParams2 = findViewById5.getLayoutParams()) != null) {
            layoutParams2.height = intValue;
        }
        ViewGroup.LayoutParams layoutParams4 = findViewById(R.id.tile_edit_layout).getLayoutParams();
        if (layoutParams4 != null) {
            layoutParams4.height = min;
        }
        View findViewById6 = findViewById(R.id.qs_available_page_parent);
        if (findViewById6 != null && (layoutParams = findViewById6.getLayoutParams()) != null) {
            layoutParams.height = i3;
        }
        int max = Math.max(i3 / this.cellHeight, 1);
        int i6 = this.requiredAvailableTileRowNum;
        this.mAvailableRows = i6;
        this.mAvailableRows = Math.min(i6, max);
        setActiveAreaScrollHeight();
    }

    @Override // com.android.systemui.qs.customize.view.QSTileCustomizerBase
    public final void calculateAvailableArea() {
        initResources();
        calculateAreasHeight();
    }

    public int getIDEAL_NUM_OF_ROW_REQUIRED() {
        return 2;
    }

    public void initResources() {
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.availableTextHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.availableIndicatorHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_area_indicator_bottom);
        this.navBarHeight = getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
        this.tileAreaTopBottomMinimumGap = getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin);
        this.editSummaryTextSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_summary_text);
        this.editSummaryTextTopBottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_active_between_margin);
        this.requiredAvailableTileRowNum = getIDEAL_NUM_OF_ROW_REQUIRED();
        this.isAvailableTextVisible = true;
        this.isEditMoreSummaryVisible = true;
        this.isEditSummaryContainerVisible = true;
        this.isActiveTilesLabelVisible = true;
    }

    public void setActiveAreaScrollHeight() {
        ViewGroup.LayoutParams layoutParams;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin);
        View findViewById = findViewById(R.id.qs_active_page_parent);
        int i = (findViewById == null || (layoutParams = findViewById.getLayoutParams()) == null) ? this.cellHeight + (dimensionPixelSize * 2) : layoutParams.height;
        View findViewById2 = findViewById(R.id.qs_active_page_content);
        if (findViewById2 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) findViewById2.getLayoutParams();
            layoutParams2.topMargin = dimensionPixelSize;
            layoutParams2.height = i - (dimensionPixelSize * 2);
            findViewById2.setLayoutParams(layoutParams2);
        }
    }
}
