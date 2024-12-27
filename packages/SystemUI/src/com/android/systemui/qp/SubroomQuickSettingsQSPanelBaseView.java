package com.android.systemui.qp;

import android.content.Context;
import android.content.res.Configuration;
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
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class SubroomQuickSettingsQSPanelBaseView extends QSPanel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SubroomBrightnessSettingsView mBrightnessView;
    public final Context mContext;
    public SecPageIndicator mFooterPageIndicator;
    public LinearLayout mQuickSettingsContainer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SubscreenTileRecord extends QSPanelControllerBase$TileRecord {
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

    public final void addPagedTileLayout() {
        View view = (View) this.mTileLayout;
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            this.mQuickSettingsContainer.addView(view);
        }
        updatePageIndicator$1();
        QSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            ((SubscreenPagedTileLayout) qSTileLayout).setCurrentItem(0, false);
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

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
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
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomQuickSettingsQSPanelBaseView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubroomQuickSettingsQSPanelBaseView subroomQuickSettingsQSPanelBaseView = SubroomQuickSettingsQSPanelBaseView.this;
                    int i = SubroomQuickSettingsQSPanelBaseView.$r8$clinit;
                    subroomQuickSettingsQSPanelBaseView.getClass();
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_BAR_MORE_COVER);
                    ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).startActivity(subroomQuickSettingsQSPanelBaseView.mContext, "com.android.systemui.qp.SubscreenBrightnessDetailActivity");
                }
            });
        }
    }

    public final void updatePageIndicator$1() {
        SecPageIndicator secPageIndicator;
        QSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout == null || !(qSTileLayout instanceof SubscreenPagedTileLayout) || (secPageIndicator = this.mFooterPageIndicator) == null) {
            return;
        }
        ((SubscreenPagedTileLayout) qSTileLayout).setPageIndicator(secPageIndicator);
    }

    public final void updateResources$1() {
        updatePageIndicator$1();
        if (this.mTileLayout != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_tile_page_layout_height);
            QSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
            if (qSTileLayout instanceof SubscreenPagedTileLayout) {
                SubscreenPagedTileLayout subscreenPagedTileLayout = (SubscreenPagedTileLayout) qSTileLayout;
                subscreenPagedTileLayout.getClass();
                Log.d("SubscreenPagedTileLayout", "setTilePageHeight pageHeight: " + dimensionPixelSize);
                int i = subscreenPagedTileLayout.mPageHeight;
                if (i != dimensionPixelSize) {
                    subscreenPagedTileLayout.mLastMaxHeight = i;
                    subscreenPagedTileLayout.mPageHeight = dimensionPixelSize;
                }
            }
        }
        QSPanel.QSTileLayout qSTileLayout2 = this.mTileLayout;
        if (qSTileLayout2 != null) {
            qSTileLayout2.updateResources();
        }
    }
}
