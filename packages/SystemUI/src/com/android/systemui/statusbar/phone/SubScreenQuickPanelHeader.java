package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.policy.QSClockHeaderView;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SubScreenQuickPanelHeader extends LinearLayout {
    public SubScreenQuickPanelHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        BatteryMeterView batteryMeterView = (BatteryMeterView) findViewById(R.id.batteryRemainingIcon);
        QSClockHeaderView qSClockHeaderView = (QSClockHeaderView) findViewById(R.id.header_clock);
        if (batteryMeterView != null) {
            batteryMeterView.setPercentShowMode(3);
        }
        ArrayList arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(new Rect(0, 0, 0, 0));
        int color = getContext().getColor(R.color.sub_screen_quick_panel_header_icon_color);
        if (qSClockHeaderView != null) {
            qSClockHeaderView.setTextColor(color);
        }
        if (batteryMeterView != null) {
            batteryMeterView.onDarkChanged(arrayListOf, 0.0f, color);
        }
    }
}
