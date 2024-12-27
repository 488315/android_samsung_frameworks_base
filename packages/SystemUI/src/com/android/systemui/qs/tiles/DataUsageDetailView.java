package com.android.systemui.qs.tiles;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.FontSizeUtils;
import java.text.DecimalFormat;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class DataUsageDetailView extends LinearLayout {
    public DataUsageDetailView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new DecimalFormat("#.##");
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FontSizeUtils.updateFontSize((TextView) findViewById(R.id.title), com.android.systemui.R.dimen.qs_data_usage_text_size);
        FontSizeUtils.updateFontSize((TextView) findViewById(com.android.systemui.R.id.usage_text), com.android.systemui.R.dimen.qs_data_usage_usage_text_size);
        FontSizeUtils.updateFontSize((TextView) findViewById(com.android.systemui.R.id.usage_carrier_text), com.android.systemui.R.dimen.qs_data_usage_text_size);
        FontSizeUtils.updateFontSize((TextView) findViewById(com.android.systemui.R.id.usage_info_top_text), com.android.systemui.R.dimen.qs_data_usage_text_size);
        FontSizeUtils.updateFontSize((TextView) findViewById(com.android.systemui.R.id.usage_period_text), com.android.systemui.R.dimen.qs_data_usage_text_size);
        FontSizeUtils.updateFontSize((TextView) findViewById(com.android.systemui.R.id.usage_info_bottom_text), com.android.systemui.R.dimen.qs_data_usage_text_size);
    }
}
