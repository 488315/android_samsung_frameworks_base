package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.Pair;

/* loaded from: classes2.dex */
public final class QSPopOverTopTileCustomizer extends QSPopOverTileCustomizerBase {
    public QSPopOverTopTileCustomizer(Context context, int i, int i2) throws Resources.NotFoundException {
        super(context, i, i2);
        this.mIsTopEdit = true;
        int qsTileMinNum = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQsTileMinNum(getContext());
        this.mActiveRows = 1;
        this.mActiveColumns = qsTileMinNum;
        this.mAvailableRows = 4;
        this.mActiveShowLabel = false;
        initResources();
        TextView textView = (TextView) findViewById(R.id.qs_edit_more_summary);
        if (textView != null) {
            textView.setText(textView.getResources().getQuantityString(R.plurals.sec_qs_add_minimum, qsTileMinNum, Integer.valueOf(qsTileMinNum)));
            textView.setVisibility(0);
        }
        View viewFindViewById = findViewById(R.id.qs_edit_summary_container);
        if (viewFindViewById != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
            int i3 = this.editSummaryTextTopBottomMargin;
            layoutParams.topMargin = i3;
            layoutParams.bottomMargin = i3;
            viewFindViewById.setLayoutParams(layoutParams);
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_panel_active_parent_padding);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.qs_active_page_parent);
        if (linearLayout != null) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.setMargins(dimensionPixelSize, 0, dimensionPixelSize, 0);
            linearLayout.setLayoutParams(layoutParams2);
            linearLayout.setBackground(null);
        }
        TextView textView2 = (TextView) findViewById(R.id.qs_edit_available_text);
        if (textView2 != null) {
            textView2.setPadding(0, textView2.getResources().getDimensionPixelSize(R.dimen.qs_pop_over_top_tile_edit_available_text_top_padding), 0, 0);
            ViewGroup.LayoutParams layoutParams3 = textView2.getLayoutParams();
            layoutParams3.height = this.availableTextHeight;
            textView2.setLayoutParams(layoutParams3);
        }
        View viewFindViewById2 = findViewById(R.id.qs_customizer_active_pager);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setHorizontalFadingEdgeEnabled(false);
        }
        View viewFindViewById3 = findViewById(R.id.scroll_top_area);
        if (viewFindViewById3 != null) {
            viewFindViewById3.setVisibility(8);
        }
        View viewFindViewById4 = findViewById(R.id.scroll_bottom_area);
        if (viewFindViewById4 != null) {
            viewFindViewById4.setVisibility(8);
        }
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final Pair calculateActiveAvailableHeight() {
        int displayableHeight = getDisplayableHeight();
        int i = this.requiredActiveHeight;
        return new Pair(Integer.valueOf(i), Integer.valueOf(this.isAvailableTextVisible ? Math.max(Math.min(this.requiredAvailableHeight, Math.min((int) (displayableHeight * this.maximumAvailableAreaRatio), displayableHeight - ((this.topSummaryAndButtonsHeight + i) + this.gapBetweenArea))), this.minimumAvailableAreaHeight) : this.minimumAvailableAreaHeight));
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final int getIDEAL_NUM_OF_ROW_REQUIRED() {
        return 1;
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final void initResources() throws Resources.NotFoundException {
        super.initResources();
        this.maximumAvailableAreaRatio = getResources().getFloat(R.dimen.qs_available_area_top_tile_edit_max_ratio);
        this.requiredAvailableTileRowNum = 4;
        this.requiredActiveTileRowNum = 1;
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.topSummaryAndButtonsHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_tile_top_edit_buttons_area_height);
        this.requiredAvailableHeight = ((this.tileIconHeight + this.tileLabelHeight) * this.requiredAvailableTileRowNum) + getResources().getDimensionPixelSize(R.dimen.qs_pop_over_edit_available_area_indicator_bottom) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.gapBetweenArea = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_top_tile_edit_gap_between_areas);
        this.requiredActiveHeight = this.tileIconHeight;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_top_tile_edit_available_text_height);
        this.availableTextHeight = dimensionPixelSize2;
        this.minimumAvailableAreaHeight = dimensionPixelSize2 + this.availableIndicatorHeight + this.cellHeight;
        this.editSummaryTextTopBottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_summary_container_vertical_margin);
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final void setActiveAreaScrollHeight() {
        ViewGroup.LayoutParams layoutParams;
        View viewFindViewById = findViewById(R.id.qs_active_page_parent);
        int i = (viewFindViewById == null || (layoutParams = viewFindViewById.getLayoutParams()) == null) ? this.cellHeight : layoutParams.height;
        View viewFindViewById2 = findViewById(R.id.qs_active_page_content);
        if (viewFindViewById2 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewFindViewById2.getLayoutParams();
            layoutParams2.height = i;
            viewFindViewById2.setLayoutParams(layoutParams2);
        }
    }
}
