package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "StatusIconContainerStatusIconContainer:", "StatusIconContainer   mParent=");
        m75m.append(statusIconContainer.mSidelingCutoutContainerInfo);
        printWriter.println(m75m.toString());
        printWriter.println("StatusIconContainer   displayCutoutRect=" + statusIconContainer.mIndicatorCutoutUtil.getDisplayCutoutAreaToExclude());
        printWriter.println("StatusIconContainer   StatusIconContainer width=" + statusIconContainer.getWidth());
        printWriter.println("StatusIconContainer   StatusIconContainer measuredWidth=" + statusIconContainer.getMeasuredWidth());
        StringBuilder m77m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("StatusIconContainer   mCutoutRightSideAvailableWidth="), statusIconContainer.mCutoutRightSideAvailableWidth, printWriter, "StatusIconContainer   mCutoutRightSideIconsWidth="), statusIconContainer.mCutoutRightSideIconsWidth, printWriter, "StatusIconContainer   mDeltaWidth=");
        m77m.append(statusIconContainer.mDeltaWidth);
        printWriter.println(m77m.toString());
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
