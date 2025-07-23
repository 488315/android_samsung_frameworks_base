package com.android.systemui.qs;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda10;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda9;
import java.util.function.DoubleSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQuickQSPanelController$$ExternalSyntheticLambda0 implements DoubleSupplier {
    public final /* synthetic */ SecQuickQSPanelController f$0;
    public final /* synthetic */ BarController f$1;

    public /* synthetic */ SecQuickQSPanelController$$ExternalSyntheticLambda0(SecQuickQSPanelController secQuickQSPanelController, BarController barController) {
        this.f$0 = secQuickQSPanelController;
        this.f$1 = barController;
    }

    @Override // java.util.function.DoubleSupplier
    public final double getAsDouble() {
        SecQuickQSPanelController secQuickQSPanelController = this.f$0;
        return secQuickQSPanelController.getResources().getDimensionPixelSize(R.dimen.quick_qs_noti_top_margin) + ((View) secQuickQSPanelController.mTileLayout).getMeasuredHeight() + (this.f$1 != null ? Math.max(0, r3.mCollapsedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda9()).mapToInt(new BarController$$ExternalSyntheticLambda10()).sum()) : 0);
    }
}
