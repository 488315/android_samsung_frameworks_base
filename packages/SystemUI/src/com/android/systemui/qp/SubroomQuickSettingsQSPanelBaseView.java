package com.android.systemui.qp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SystemUIAnalytics;

/* loaded from: classes2.dex */
public class SubroomQuickSettingsQSPanelBaseView extends QSPanel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SubroomBrightnessSettingsView mBrightnessView;
    public final Context mContext;
    public SecPageIndicator mFooterPageIndicator;
    public LinearLayout mQuickSettingsContainer;

    public class SubscreenTileRecord extends QSPanelControllerBase$TileRecord {
        public final String mTilespec;

        public SubscreenTileRecord(QSTile qSTile, QSTileView qSTileView, View.OnLongClickListener onLongClickListener) {
            super(qSTile, qSTileView);
            this.mTilespec = qSTile.getTileSpec();
        }
    }

    public SubroomQuickSettingsQSPanelBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d("SubroomQuickSettingsQSPanelBaseView", "SubroomQuickSettingsQSPanelBaseView");
        this.mContext = context;
    }

    public final void addPagedTileLayout() throws Resources.NotFoundException {
        SubscreenPagedTileLayout subscreenPagedTileLayout = this.mTileLayout;
        if (subscreenPagedTileLayout != null) {
            if (subscreenPagedTileLayout.getParent() != null) {
                ((ViewGroup) subscreenPagedTileLayout.getParent()).removeView(subscreenPagedTileLayout);
            }
            this.mQuickSettingsContainer.addView(subscreenPagedTileLayout);
        }
        updatePageIndicator();
        SubscreenPagedTileLayout subscreenPagedTileLayout2 = this.mTileLayout;
        if (subscreenPagedTileLayout2 != null) {
            subscreenPagedTileLayout2.setCurrentItem(0, false);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        SubroomBrightnessSettingsView subroomBrightnessSettingsView = this.mBrightnessView;
        if (subroomBrightnessSettingsView != null) {
            SubscreenUtil.applyRotation(this.mContext, subroomBrightnessSettingsView);
        }
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Log.d("SubroomQuickSettingsQSPanelBaseView", "onFinishInflate");
        this.mQuickSettingsContainer = (LinearLayout) findViewById(R.id.subscreen_tile_layout);
        this.mFooterPageIndicator = (SecPageIndicator) findViewById(R.id.footer_page_indicator);
        SubroomBrightnessSettingsView subroomBrightnessSettingsView = (SubroomBrightnessSettingsView) findViewById(R.id.subroom_brightness_settings);
        this.mBrightnessView = subroomBrightnessSettingsView;
        ImageView imageView = (ImageView) subroomBrightnessSettingsView.findViewById(R.id.brightness_panel_more_icon);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.cover_screen_page_layout_top_margin);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        Context context = this.mContext;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        int screenWidth = (DeviceState.getScreenWidth(context) - ((getResources().getDimensionPixelSize(R.dimen.cover_screen_horizontal_margin) * 3) + (getResources().getDimensionPixelSize(R.dimen.subscreen_qs_tile_icon_size) * 4))) / 2;
        layoutParams.setMarginEnd(screenWidth);
        layoutParams.setMarginStart(screenWidth);
        this.mQuickSettingsContainer.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.topMargin = 0;
        layoutParams2.bottomMargin = 0;
        this.mBrightnessView.setLayoutParams(layoutParams2);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomQuickSettingsQSPanelBaseView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView = this.f$0;
                    int i = SubroomQuickSettingsQSPanelBaseView.$r8$clinit;
                    subroomQuickSettingsQSPanelBaseView.getClass();
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_BAR_MORE_COVER);
                    ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).startActivity(subroomQuickSettingsQSPanelBaseView.mContext, "com.android.systemui.qp.SubscreenBrightnessDetailActivity");
                }
            });
        }
    }

    public final void updatePageIndicator() throws Resources.NotFoundException {
        SecPageIndicator secPageIndicator;
        SubscreenPagedTileLayout subscreenPagedTileLayout = this.mTileLayout;
        if (subscreenPagedTileLayout == null || (secPageIndicator = this.mFooterPageIndicator) == null) {
            return;
        }
        subscreenPagedTileLayout.setPageIndicator(secPageIndicator);
    }

    public final void updateResources$1() throws Resources.NotFoundException {
        updatePageIndicator();
        if (this.mTileLayout != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.cover_screen_page_layout_height);
            SubscreenPagedTileLayout subscreenPagedTileLayout = this.mTileLayout;
            if (subscreenPagedTileLayout != null) {
                subscreenPagedTileLayout.getClass();
                Log.d("SubscreenPagedTileLayout", "setTilePageHeight pageHeight: " + dimensionPixelSize);
                int i = subscreenPagedTileLayout.mPageHeight;
                if (i != dimensionPixelSize) {
                    subscreenPagedTileLayout.mLastMaxHeight = i;
                    subscreenPagedTileLayout.mPageHeight = dimensionPixelSize;
                }
            }
        }
        SubscreenPagedTileLayout subscreenPagedTileLayout2 = this.mTileLayout;
        if (subscreenPagedTileLayout2 != null) {
            subscreenPagedTileLayout2.updateResources();
        }
    }
}
