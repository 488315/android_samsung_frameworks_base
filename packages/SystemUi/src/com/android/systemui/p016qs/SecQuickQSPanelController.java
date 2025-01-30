package com.android.systemui.p016qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p016qs.SecQSPanel;
import com.android.systemui.p016qs.bar.BarController;
import com.android.systemui.p016qs.bar.BarController$$ExternalSyntheticLambda5;
import com.android.systemui.p016qs.bar.BarController$$ExternalSyntheticLambda6;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.util.ConfigurationState;
import java.util.Arrays;
import java.util.function.DoubleSupplier;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQuickQSPanelController extends SecQSPanelControllerBase {
    public View mBrightnessMediaDeviceBar;
    public final ConfigurationState mLastConfigurationState;
    public View mMediaPlayerBar;
    public int mOrientation;

    public SecQuickQSPanelController(SecQuickQSPanel secQuickQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, Provider provider, final SecQSPanelResourcePicker secQSPanelResourcePicker) {
        super(secQuickQSPanel, qSHost, metricsLogger, uiEventLogger, qSLogger, dumpManager, qSPanelHost, (BarController) provider.get(), secQSPanelResourcePicker);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.SCREEN_LAYOUT));
        final BarController barController = (BarController) provider.get();
        ((SecQuickQSPanel) this.mView).mMeasuredHeightSupplier = new DoubleSupplier() { // from class: com.android.systemui.qs.SecQuickQSPanelController$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                SecQuickQSPanelController secQuickQSPanelController = SecQuickQSPanelController.this;
                SecQSPanelResourcePicker secQSPanelResourcePicker2 = secQSPanelResourcePicker;
                BarController barController2 = barController;
                int measuredHeight = ((View) secQuickQSPanelController.mTileLayout).getMeasuredHeight();
                Context context = ((SecQuickQSPanel) secQuickQSPanelController.mView).getContext();
                secQSPanelResourcePicker2.getClass();
                return SecQSPanelResourcePicker.getQuickQSCommonBottomMargin(context) + measuredHeight + (barController2 != null ? Math.max(0, barController2.mCollapsedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda5(0)).mapToInt(new BarController$$ExternalSyntheticLambda6()).sum()) : 0);
            }
        };
    }

    @Override // com.android.systemui.p016qs.SecQSPanelControllerBase
    public final SecQSPanel.QSTileLayout getOrCreateTileLayout() {
        return new SecHeaderTileLayout(((SecQuickQSPanel) this.mView).getContext());
    }

    @Override // com.android.systemui.p016qs.SecQSPanelControllerBase
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = ((SecQuickQSPanel) this.mView).getContext().getResources().getConfiguration().orientation;
        RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("onConfigurationChanged currentOrientation = ", i, ",newConfig.orientation = "), configuration.orientation, "SecQuickQSPanelController");
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration) || this.mOrientation != i) {
            this.mOrientation = i;
            this.mQsPanelHost.setTiles();
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.p016qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mBrightnessMediaDeviceBar = ((SecQuickQSPanel) this.mView).findViewById(R.id.brightness_media_device_bar);
        this.mMediaPlayerBar = ((SecQuickQSPanel) this.mView).findViewById(R.id.media_player_root_view);
        updatePaddingAndMargins();
    }

    @Override // com.android.systemui.p016qs.SecQSPanelControllerBase
    public final void setExpanded(boolean z) {
        super.setExpanded(z);
        ((SecQuickQSPanel) this.mView).setVisibility(z ? 4 : 0);
    }

    @Override // com.android.systemui.p016qs.SecQSPanelControllerBase
    public final void updatePaddingAndMargins() {
        int notificationSidePadding;
        if (this.mView == null) {
            return;
        }
        Context context = getContext();
        this.mResourcePicker.getClass();
        int qQSPanelSidePadding = SecQSPanelResourcePicker.getQQSPanelSidePadding(context);
        SecQuickQSPanel secQuickQSPanel = (SecQuickQSPanel) this.mView;
        secQuickQSPanel.setPadding(qQSPanelSidePadding, secQuickQSPanel.getPaddingTop(), qQSPanelSidePadding, 0);
        int panelStartEndPadding = SecQSPanelResourcePicker.getPanelStartEndPadding(context);
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            int i = panelStartEndPadding + 0;
            SecHeaderTileLayout secHeaderTileLayout = (SecHeaderTileLayout) qSTileLayout;
            secHeaderTileLayout.setPadding(i, secHeaderTileLayout.getPaddingTop(), i, 0);
        }
        View view = this.mBrightnessMediaDeviceBar;
        if (view != null) {
            view.setPadding(panelStartEndPadding, view.getPaddingTop(), panelStartEndPadding, this.mBrightnessMediaDeviceBar.getPaddingBottom());
        }
        if (this.mMediaPlayerBar != null) {
            Resources resources = context.getResources();
            if (QpRune.QUICK_TABLET) {
                notificationSidePadding = context.getResources().getDimensionPixelSize(R.dimen.sec_media_player_side_padding_tablet);
            } else {
                notificationSidePadding = resources.getConfiguration().orientation == 2 ? 0 : SecQSPanelResourcePicker.getNotificationSidePadding(context);
            }
            View view2 = this.mMediaPlayerBar;
            view2.setPadding(notificationSidePadding, view2.getPaddingTop(), notificationSidePadding, this.mMediaPlayerBar.getPaddingBottom());
        }
    }

    @Override // com.android.systemui.p016qs.SecQSPanelControllerBase
    public final void updatePanelContents() {
        LinearLayout linearLayout = new LinearLayout(((SecQuickQSPanel) this.mView).getContext());
        linearLayout.setTag("qqs_expand_anim");
        linearLayout.addView((View) this.mTileLayout);
        ((SecQuickQSPanel) this.mView).addView(linearLayout);
        addBarItems();
    }
}
