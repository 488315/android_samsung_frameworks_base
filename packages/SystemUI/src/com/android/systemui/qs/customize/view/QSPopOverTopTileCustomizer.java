package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.util.DeviceState;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPopOverTopTileCustomizer extends QSPopOverTileCustomizerBase {
    public QSPopOverTopTileCustomizer(Context context, int i) {
        super(context, i);
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
        View findViewById = findViewById(R.id.qs_edit_summary_container);
        if (findViewById != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById.getLayoutParams();
            int i2 = this.editSummaryTextTopBottomMargin;
            layoutParams.topMargin = i2;
            layoutParams.bottomMargin = i2;
            findViewById.setLayoutParams(layoutParams);
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
        View findViewById2 = findViewById(R.id.qs_customizer_active_pager);
        if (findViewById2 != null) {
            findViewById2.setHorizontalFadingEdgeEnabled(false);
        }
        if (findViewById(R.id.scroll_top_area) != null) {
            setVisibility(8);
        }
        if (findViewById(R.id.scroll_bottom_area) != null) {
            setVisibility(8);
        }
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final Pair calculateActiveAvailableHeight() {
        int displayHeight = DeviceState.getDisplayHeight(this.mContext) - (this.mCutoutTopMargin + this.navBarHeight);
        int i = this.requiredActiveHeight;
        return new Pair(Integer.valueOf(i), Integer.valueOf(this.isAvailableTextVisible ? Math.max(Math.min(this.requiredAvailableHeight, Math.min((int) (displayHeight * this.maximumAvailableAreaRatio), displayHeight - ((this.topSummaryAndButtonsHeight + i) + this.gapBetweenArea))), this.minimumAvailableAreaHeight) : this.minimumAvailableAreaHeight));
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final int getIDEAL_NUM_OF_ROW_REQUIRED() {
        return 1;
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final void initResources() {
        super.initResources();
        this.maximumAvailableAreaRatio = getResources().getFloat(R.dimen.qs_available_area_top_tile_edit_max_ratio);
        this.requiredAvailableTileRowNum = 4;
        this.requiredActiveTileRowNum = 1;
        this.tileIconHeight = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height);
        this.tileLabelHeight = dimensionPixelSize;
        this.cellHeight = this.tileIconHeight + dimensionPixelSize;
        this.topSummaryAndButtonsHeight = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_tile_top_edit_buttons_area_height);
        this.requiredAvailableHeight = ((this.tileIconHeight + this.tileLabelHeight) * this.requiredAvailableTileRowNum) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_area_indicator_bottom) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height);
        this.gapBetweenArea = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_buttons_top_margin);
        this.requiredActiveHeight = this.tileIconHeight;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_top_tile_edit_available_text_height);
        this.availableTextHeight = dimensionPixelSize2;
        this.minimumAvailableAreaHeight = dimensionPixelSize2 + this.availableIndicatorHeight + this.cellHeight;
        this.editSummaryTextTopBottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_summary_container_vertical_margin);
    }

    @Override // com.android.systemui.qs.customize.view.QSPopOverTileCustomizerBase
    public final void setActiveAreaScrollHeight() {
        ViewGroup.LayoutParams layoutParams;
        View findViewById = findViewById(R.id.qs_active_page_parent);
        int i = (findViewById == null || (layoutParams = findViewById.getLayoutParams()) == null) ? this.cellHeight : layoutParams.height;
        View findViewById2 = findViewById(R.id.qs_active_page_content);
        if (findViewById2 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) findViewById2.getLayoutParams();
            layoutParams2.height = i;
            findViewById2.setLayoutParams(layoutParams2);
        }
    }
}
