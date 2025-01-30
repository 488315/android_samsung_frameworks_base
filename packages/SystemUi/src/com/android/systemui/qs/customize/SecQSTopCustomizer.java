package com.android.systemui.qs.customize;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSTopCustomizer extends SecQSCustomizerBase {
    public SecQSTopCustomizer(Context context) {
        super(context);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context2 = getContext();
        secQSPanelResourcePicker.getClass();
        int qsTileMinNum = SecQSPanelResourcePicker.getQsTileMinNum(context2);
        this.mActiveRows = 1;
        this.mActiveColumns = qsTileMinNum;
        this.mAvailableRows = 4;
        this.mAvailableColumns = 4;
        this.mActiveShowLabel = false;
        this.mActiveWeight = 1;
        this.mAvailableWeight = 5;
        requireViewById(R.id.qs_customizer_top_summary).setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.qs_edit_summary_more_height));
        LinearLayout linearLayout = (LinearLayout) requireViewById(R.id.qs_active_page_parent);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.qs_edit_panel_active_parent_padding), 0, getResources().getDimensionPixelSize(R.dimen.qs_edit_panel_active_parent_padding), 0);
        linearLayout.setLayoutParams(layoutParams);
        TextView textView = (TextView) findViewById(R.id.qs_edit_more_summary);
        textView.setText(getResources().getQuantityString(R.plurals.sec_qs_add_minimum, qsTileMinNum, Integer.valueOf(qsTileMinNum)));
        textView.setVisibility(0);
        requireViewById(R.id.qs_active_empty_header).setVisibility(8);
        requireViewById(R.id.qs_active_page_parent).setBackground(null);
        requireViewById(R.id.qs_active_paged_indicator_container).setVisibility(8);
        requireViewById(R.id.active_page_area_left).setVisibility(8);
        requireViewById(R.id.active_page_area_right).setVisibility(8);
        requireViewById(R.id.qs_customizer_active_pager).setHorizontalFadingEdgeEnabled(false);
    }
}
