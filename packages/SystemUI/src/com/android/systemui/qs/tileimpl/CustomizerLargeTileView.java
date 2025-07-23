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
import java.util.Arrays;
import kotlin.NotImplementedError;
import kotlin.Triple;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomizerLargeTileView extends QSTileView {
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;
    public final View labelGroup;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        View requireViewById;
        TextView textView;
        this.commonTileView.handleStateChanged(state, true);
        View view = this.labelGroup;
        if (view != null && (textView = (TextView) view.requireViewById(R.id.tile_label)) != null) {
            textView.setSelected(false);
            textView.setSingleLine(!(state.label != null ? StringsKt__StringsKt.contains(r5, " ", false) : false));
        }
        View view2 = this.labelGroup;
        if (view2 == null || (requireViewById = view2.requireViewById(R.id.app_label)) == null) {
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
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context, z);
        this.iconView = qSIconViewImpl;
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, z, false, 176, null);
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
        Triple triple = z ? new Triple(Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconSize(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileLabelStartMargin(context))) : new Triple(Integer.valueOf(secQSPanelResourcePicker.getTileIconSize(context)), Integer.valueOf(secQSPanelResourcePicker.getTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTileLabelStartMargin(context)));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height = -1;
        layoutParams.width = CollectionsKt___CollectionsKt.sumOfInt(Arrays.asList(triple.getFirst(), triple.getSecond(), triple.getThird()));
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setPaddingRelative(((Number) triple.getSecond()).intValue(), 0, ((Number) triple.getThird()).intValue(), 0);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void updateColoredBackground() {
    }
}
