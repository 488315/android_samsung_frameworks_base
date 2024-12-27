package com.android.systemui.statusbar.phone.logo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CarrierLogoView extends ImageView implements DarkIconDispatcher.DarkReceiver {
    public CarrierLogoView(Context context) {
        super(context);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        setImageTintList(ColorStateList.valueOf(DarkIconDispatcher.getTint(arrayList, this, i)));
    }

    public CarrierLogoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CarrierLogoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
