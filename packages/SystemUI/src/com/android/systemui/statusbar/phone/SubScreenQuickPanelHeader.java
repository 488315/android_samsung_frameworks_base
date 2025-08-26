package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.LinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.QSClockHeaderView;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: classes3.dex */
public final class SubScreenQuickPanelHeader extends LinearLayout {
    public SubScreenQuickPanelHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        BatteryMeterView batteryMeterView = (BatteryMeterView) findViewById(R.id.batteryRemainingIcon);
        QSClockHeaderView qSClockHeaderView = (QSClockHeaderView) findViewById(R.id.header_clock);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.quick_qs_network_speed_viewstub);
            if (viewStub != null) {
                viewStub.inflate();
            }
            NetspeedView netspeedView = (NetspeedView) findViewById(R.id.networkSpeed);
            ArrayList arrayListArrayListOf = CollectionsKt__CollectionsKt.arrayListOf(new Rect(0, 0, 0, 0));
            int color = getContext().getColor(R.color.sub_screen_quick_panel_header_icon_color);
            if (netspeedView != null) {
                netspeedView.onDarkChanged(arrayListArrayListOf, 0.0f, color);
            }
        }
        if (batteryMeterView != null) {
            batteryMeterView.setPercentShowMode(3);
        }
        if (batteryMeterView != null) {
            batteryMeterView.setTag("SubScreenQuickPanelHeader");
        }
        ArrayList arrayListArrayListOf2 = CollectionsKt__CollectionsKt.arrayListOf(new Rect(0, 0, 0, 0));
        int color2 = getContext().getColor(R.color.sub_screen_quick_panel_header_icon_color);
        if (qSClockHeaderView != null) {
            qSClockHeaderView.setTextColor(color2);
        }
        if (batteryMeterView != null) {
            batteryMeterView.onDarkChanged(arrayListArrayListOf2, 0.0f, color2);
        }
    }
}
