package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import java.util.Iterator;
import kotlin.NotImplementedError;
import kotlin.Triple;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

public final class CustomizerLargeTileView extends QSTileView {
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;
    public final View labelGroup;

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

    public /* synthetic */ CustomizerLargeTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, secQSPanelResourcePicker, (i & 4) != 0 ? false : z);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final int getDetailY() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final QSIconView getIcon() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getIconWithBackground() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void init(QSTile qSTile) {
        this.commonTileView.init(qSTile, this);
        this.iconFrame.setBackground(null);
        this.iconView.setBackground(null);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(QSTile.State state) {
        TextView textView;
        this.commonTileView.handleStateChanged(state, true);
        View view = this.labelGroup;
        if (view != null && (textView = (TextView) view.requireViewById(R.id.tile_label)) != null) {
            textView.setSelected(false);
            textView.setSingleLine(!(state.label != null ? StringsKt__StringsKt.contains(r5, " ", false) : false));
        }
        View view2 = this.labelGroup;
        View requireViewById = view2 != null ? view2.requireViewById(R.id.app_label) : null;
        if (requireViewById == null) {
            return;
        }
        requireViewById.setSelected(false);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    public CustomizerLargeTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, boolean z) {
        super(context);
        Triple triple;
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context, z);
        this.iconView = qSIconViewImpl;
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, z, 48, null);
        this.commonTileView = secQSCommonTileView;
        FrameLayout frameLayout = secQSCommonTileView.iconFrame;
        this.iconFrame = frameLayout;
        addView(frameLayout);
        View createLabel = secQSCommonTileView.createLabel(R.layout.sec_qs_large_tile_label, this);
        this.labelGroup = createLabel;
        addView(createLabel);
        Resources resources = context.getResources();
        setLayoutParams(new LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.large_tile_width), resources.getDimensionPixelSize(R.dimen.large_tile_height), 1.0f));
        setClipChildren(false);
        setClipToPadding(false);
        setClickable(true);
        setOrientation(0);
        setGravity(16);
        setBackground(context.getDrawable(R.drawable.sec_large_button_ripple_background));
        if (z) {
            triple = new Triple(Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconSize(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileLabelStartMargin(context)));
        } else {
            Integer valueOf = Integer.valueOf(secQSPanelResourcePicker.getTileIconSize(context));
            SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
            triple = new Triple(valueOf, Integer.valueOf(secQSPanelResourcePickHelper.getTargetPicker().getTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePickHelper.getTargetPicker().getTileLabelStartMargin(context)));
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height = -1;
        Iterator it = CollectionsKt__CollectionsKt.listOf(triple.getFirst(), triple.getSecond(), triple.getThird()).iterator();
        int i = 0;
        while (it.hasNext()) {
            i += ((Number) it.next()).intValue();
        }
        layoutParams.width = i;
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setPaddingRelative(((Number) triple.getSecond()).intValue(), 0, ((Number) triple.getThird()).intValue(), 0);
    }
}
