package com.android.systemui.shade.carrier;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.IndicatorGardenMaxWidthLinearLayout;

/* loaded from: classes3.dex */
public class ShadeCarrierGroup extends IndicatorGardenMaxWidthLinearLayout {
    public ShadeCarrierGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final ShadeCarrier getCarrier1View() {
        return (ShadeCarrier) findViewById(R.id.carrier1);
    }
}
