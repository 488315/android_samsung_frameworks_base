package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DummyTileView extends QSTileView {
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DummyTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, ColorStateList colorStateList) {
        super(context);
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context);
        this.iconView = qSIconViewImpl;
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, colorStateList, colorStateList, false, 64, null);
        this.commonTileView = secQSCommonTileView;
        FrameLayout frameLayout = secQSCommonTileView.iconFrame;
        addView(frameLayout);
        this.iconFrame = frameLayout;
        LinearLayout createLabel = secQSCommonTileView.createLabel(R.layout.sec_qs_tile_label, this);
        addView(createLabel);
        setClipChildren(false);
        setClipToPadding(false);
        setClickable(true);
        setOrientation(1);
        setGravity(49);
        setBackground(getContext().getDrawable(R.drawable.sec_tile_view_ripple_background));
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.width = -1;
            frameLayout.setLayoutParams(layoutParams2);
        }
        if (createLabel != null) {
            ViewGroup.LayoutParams layoutParams3 = createLabel.getLayoutParams();
            LinearLayout.LayoutParams layoutParams4 = layoutParams3 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams3 : null;
            if (layoutParams4 != null) {
                layoutParams4.height = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getLabelHeight(createLabel.getContext());
                createLabel.setLayoutParams(layoutParams4);
            }
        }
        setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final int getDetailY() {
        return (getHeight() / 2) + getTop();
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
    public final void onStateChanged(QSTile.State state) {
        this.commonTileView.handleStateChanged(state, true);
    }

    public DummyTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, ColorStateList colorStateList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class) : secQSPanelResourcePicker, (i & 4) != 0 ? ColorStateList.valueOf(context.getColor(R.color.add_tile_label_color)) : colorStateList);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void init(QSTile qSTile) {
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        return this;
    }
}
