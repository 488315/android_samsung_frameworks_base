package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusIconContainerController extends ViewController implements ConfigurationController.ConfigurationListener {
    public final ConfigurationController configurationController;
    public final Context context;
    public final IndicatorCutoutUtil indicatorCutoutUtil;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final StatusIconContainer view;

    public StatusIconContainerController(StatusIconContainer statusIconContainer, Context context, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorCutoutUtil indicatorCutoutUtil) {
        super(statusIconContainer);
        this.view = statusIconContainer;
        this.context = context;
        this.configurationController = configurationController;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.indicatorCutoutUtil = indicatorCutoutUtil;
    }

    public final void dump(PrintWriter printWriter) {
        StatusIconContainer statusIconContainer = this.view;
        statusIconContainer.getClass();
        if (!BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT || statusIconContainer.mSidelingCutoutContainerInfo == null || statusIconContainer.mIndicatorCutoutUtil == null) {
            return;
        }
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "StatusIconContainerStatusIconContainer:", "StatusIconContainer   mParent=");
        m.append(statusIconContainer.mSidelingCutoutContainerInfo);
        printWriter.println(m.toString());
        printWriter.println("StatusIconContainer   displayCutoutRect=" + statusIconContainer.mIndicatorCutoutUtil.getDisplayCutoutAreaToExclude());
        printWriter.println("StatusIconContainer   StatusIconContainer width=" + statusIconContainer.getWidth());
        printWriter.println("StatusIconContainer   StatusIconContainer measuredWidth=" + statusIconContainer.getMeasuredWidth());
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("StatusIconContainer   mCutoutRightSideAvailableWidth="), statusIconContainer.mCutoutRightSideAvailableWidth, printWriter, "StatusIconContainer   mCutoutRightSideIconsWidth="), statusIconContainer.mCutoutRightSideIconsWidth, printWriter, "StatusIconContainer   mDeltaWidth=");
        m2.append(statusIconContainer.mDeltaWidth);
        printWriter.println(m2.toString());
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        updateStatusIconContainerPadding();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this);
        updateStatusIconContainerPadding();
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            this.view.mIndicatorCutoutUtil = this.indicatorCutoutUtil;
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
    }

    public final void updateStatusIconContainerPadding() {
        int rint = (int) Math.rint(getResources().getDimensionPixelSize(R.dimen.signal_cluster_battery_padding) * this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio);
        StatusIconContainer statusIconContainer = this.view;
        statusIconContainer.setPaddingRelative(statusIconContainer.getPaddingStart(), statusIconContainer.getPaddingTop(), rint, statusIconContainer.getPaddingBottom());
    }
}
