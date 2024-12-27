package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;

public final class QSTopTileCustomizer extends QSTileCustomizerBase {
    public QSTopTileCustomizer(Context context) {
        super(context);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        int qsTileMinNum = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getQsTileMinNum(getContext());
        this.mIsTopEdit = true;
        this.mActiveRows = 1;
        this.mActiveColumns = qsTileMinNum;
        this.mAvailableRows = 4;
        this.mAvailableColumns = 4;
        this.mActiveShowLabel = false;
        LinearLayout linearLayout = (LinearLayout) requireViewById(R.id.qs_active_page_parent);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.qs_edit_panel_active_parent_padding), 0, getResources().getDimensionPixelSize(R.dimen.qs_edit_panel_active_parent_padding), 0);
        linearLayout.setLayoutParams(layoutParams);
        TextView textView = (TextView) findViewById(R.id.qs_edit_more_summary);
        textView.setText(getResources().getQuantityString(R.plurals.sec_qs_add_minimum, qsTileMinNum, Integer.valueOf(qsTileMinNum)));
        textView.setVisibility(0);
        requireViewById(R.id.qs_active_page_parent).setBackground(null);
        requireViewById(R.id.qs_customizer_active_pager).setHorizontalFadingEdgeEnabled(false);
        requireViewById(R.id.scroll_top_area).setVisibility(8);
        requireViewById(R.id.scroll_bottom_area).setVisibility(8);
    }
}
