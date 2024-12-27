package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.SecQSCommonTileView;

public final class CustomizerNoLabelTileView extends QSTileView {
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;

    public CustomizerNoLabelTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        super(context);
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context);
        this.iconView = qSIconViewImpl;
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, false, 112, null);
        this.commonTileView = secQSCommonTileView;
        FrameLayout frameLayout = secQSCommonTileView.iconFrame;
        this.iconFrame = frameLayout;
        addView(frameLayout);
        int tileIconSize = secQSPanelResourcePicker.getTileIconSize(getContext());
        setLayoutParams(new LinearLayout.LayoutParams(tileIconSize, tileIconSize));
        setClipChildren(false);
        setClipToPadding(false);
        setGravity(17);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final int getDetailY() {
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final QSIconView getIcon() {
        return this.iconView;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getIconWithBackground() {
        return this.iconFrame;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void init(QSTile qSTile) {
        this.commonTileView.init(qSTile, this);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(QSTile.State state) {
        this.commonTileView.handleStateChanged(state, true);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        return this;
    }
}
