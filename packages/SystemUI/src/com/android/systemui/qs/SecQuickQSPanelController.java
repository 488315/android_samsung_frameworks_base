package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda10;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda9;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.ConfigurationState;
import java.util.Arrays;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import javax.inject.Provider;

public final class SecQuickQSPanelController extends SecQSPanelControllerBase {
    public View mBrightnessMediaDeviceBar;
    public final ConfigurationState mLastConfigurationState;
    public int mOrientation;
    public boolean mPanelSplitEnabled;
    public final SecPanelSplitHelper mPanelSplitHelper;
    public final AnonymousClass1 mPanelTransitionStateListener;

    /* renamed from: $r8$lambda$yteqxZy8VxfIcKpup0HXsu7a-yw, reason: not valid java name */
    public static double m2064$r8$lambda$yteqxZy8VxfIcKpup0HXsu7ayw(SecQuickQSPanelController secQuickQSPanelController, SecQSPanelResourcePicker secQSPanelResourcePicker, BarController barController) {
        return (secQSPanelResourcePicker.getQuickQSCommonBottomMargin(((SecQuickQSPanel) secQuickQSPanelController.mView).getContext()) * 2) + ((View) secQuickQSPanelController.mTileLayout).getMeasuredHeight() + (barController != null ? Math.max(0, barController.mCollapsedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda9()).mapToInt(new BarController$$ExternalSyntheticLambda10()).sum()) : 0);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.qs.SecQuickQSPanelController$1] */
    public SecQuickQSPanelController(SecQuickQSPanel secQuickQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, Provider provider, final SecQSPanelResourcePicker secQSPanelResourcePicker, MediaHost mediaHost, SecQSDetailController secQSDetailController, SecPanelSplitHelper secPanelSplitHelper) {
        super(secQuickQSPanel, qSHost, metricsLogger, uiEventLogger, qSLogger, dumpManager, qSPanelHost, (BarController) provider.get(), secQSPanelResourcePicker, mediaHost, secQSDetailController);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.SCREEN_LAYOUT, ConfigurationState.ConfigurationField.UI_MODE));
        this.mPanelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.SecQuickQSPanelController.1
            @Override // com.android.systemui.shade.PanelTransitionStateListener
            public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                SecQuickQSPanelController secQuickQSPanelController = SecQuickQSPanelController.this;
                boolean z = secQuickQSPanelController.mPanelSplitEnabled;
                boolean z2 = panelTransitionStateChangeEvent.enabled;
                if (z2 == z) {
                    return;
                }
                secQuickQSPanelController.mPanelSplitEnabled = z2;
                secQuickQSPanelController.updateVisibility$4();
            }
        };
        this.mPanelSplitEnabled = SecPanelSplitHelper.isEnabled();
        final BarController barController = (BarController) provider.get();
        ((SecQuickQSPanel) this.mView).mMeasuredHeightSupplier = new DoubleSupplier() { // from class: com.android.systemui.qs.SecQuickQSPanelController$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                return SecQuickQSPanelController.m2064$r8$lambda$yteqxZy8VxfIcKpup0HXsu7ayw(SecQuickQSPanelController.this, secQSPanelResourcePicker, barController);
            }
        };
        secQSDetailController.getClass();
        this.mPanelSplitHelper = secPanelSplitHelper;
        this.mQsPanelHost.mOrientationSupplier = new IntSupplier() { // from class: com.android.systemui.qs.SecQuickQSPanelController$$ExternalSyntheticLambda1
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                SecQuickQSPanelController secQuickQSPanelController = SecQuickQSPanelController.this;
                int i = secQuickQSPanelController.mOrientation;
                return i != 0 ? i : secQuickQSPanelController.getContext().getResources().getConfiguration().orientation;
            }
        };
        updateVisibility$4();
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final SecQSPanel.QSTileLayout getOrCreateTileLayout() {
        return new SecHeaderTileLayout(((SecQuickQSPanel) this.mView).getContext());
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = getContext().getResources().getConfiguration().orientation;
        ConfigurationState configurationState = this.mLastConfigurationState;
        boolean needToUpdate = configurationState.needToUpdate(configuration);
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onConfigurationChanged currentOrientation = ", ", newConfig.orientation = ");
        m.append(configuration.orientation);
        m.append(", mOrientation = ");
        m.append(this.mOrientation);
        m.append(", needToUpdate = ");
        m.append(needToUpdate);
        Log.d("SecQuickQSPanelController", m.toString());
        Log.d("SecQuickQSPanelController", "onConfigurationChanged diff = " + configurationState.toCompareString(configuration));
        if (needToUpdate || this.mOrientation != i) {
            this.mOrientation = i;
            this.mQsPanelHost.setTiles();
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        this.mMediaHost.init(1);
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mBrightnessMediaDeviceBar = ((SecQuickQSPanel) this.mView).findViewById(R.id.brightness_media_device_bar);
        updatePaddingAndMargins();
        this.mPanelSplitHelper.addListener(this.mPanelTransitionStateListener);
        this.mOrientation = getContext().getResources().getConfiguration().orientation;
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mPanelSplitHelper.removeListener(this.mPanelTransitionStateListener);
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void setExpanded(boolean z) {
        super.setExpanded(z);
        updateVisibility$4();
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void updatePaddingAndMargins() {
        if (this.mView == 0) {
            return;
        }
        Context context = getContext();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        int qQSPanelSidePadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getQQSPanelSidePadding(context);
        SecQuickQSPanel secQuickQSPanel = (SecQuickQSPanel) this.mView;
        secQuickQSPanel.setPadding(qQSPanelSidePadding, secQuickQSPanel.getPaddingTop(), qQSPanelSidePadding, 0);
        int i = context.getResources().getConfiguration().orientation;
        SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
        int panelStartEndPadding = secQSPanelResourcePickHelper.getTargetPicker().getPanelStartEndPadding(i, context);
        if (this.mTileLayout != null) {
            int quickSettingExtraSidePadding = panelStartEndPadding - secQSPanelResourcePickHelper.getTargetPicker().getQuickSettingExtraSidePadding(context);
            SecHeaderTileLayout secHeaderTileLayout = (SecHeaderTileLayout) this.mTileLayout;
            secHeaderTileLayout.setPadding(quickSettingExtraSidePadding, secHeaderTileLayout.getPaddingTop(), quickSettingExtraSidePadding, 0);
        }
        View view = this.mBrightnessMediaDeviceBar;
        if (view != null) {
            view.setPadding(panelStartEndPadding, view.getPaddingTop(), panelStartEndPadding, this.mBrightnessMediaDeviceBar.getPaddingBottom());
        }
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void updatePanelContents() {
        LinearLayout linearLayout = new LinearLayout(((SecQuickQSPanel) this.mView).getContext());
        linearLayout.setTag("qqs_expand_anim");
        linearLayout.addView((View) this.mTileLayout);
        ((SecQuickQSPanel) this.mView).addView(linearLayout);
        addBarItems();
    }

    public final void updateVisibility$4() {
        if (this.mPanelSplitEnabled) {
            ((SecQuickQSPanel) this.mView).setVisibility(8);
        } else {
            ((SecQuickQSPanel) this.mView).setVisibility(0);
        }
    }
}
