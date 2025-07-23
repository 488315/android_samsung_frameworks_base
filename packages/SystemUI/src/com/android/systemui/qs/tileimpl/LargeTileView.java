package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.tileimpl.SecQSCommonTileView;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.ViewUtil;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Arrays;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LargeTileView extends QSTileView implements LaunchableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;
    public final LaunchableViewDelegate launchableViewDelegate;

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

    public /* synthetic */ LargeTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, secQSPanelResourcePicker, (i & 4) != 0 ? false : z);
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
    public final void init(final QSTile qSTile) {
        this.commonTileView.init(qSTile, this);
        this.iconFrame.setBackground(null);
        this.iconView.setBackground(null);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.LargeTileView$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTile qSTile2 = QSTile.this;
                Expandable.Companion companion = Expandable.Companion;
                LargeTileView largeTileView = this;
                companion.getClass();
                qSTile2.secondaryClick(new Expandable$Companion$fromView$1(largeTileView));
            }
        });
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.LargeTileView$init$2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSTile qSTile2 = QSTile.this;
                Expandable.Companion companion = Expandable.Companion;
                LargeTileView largeTileView = this;
                companion.getClass();
                qSTile2.longClick(new Expandable$Companion$fromView$1(largeTileView));
                return true;
            }
        });
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getContentChangeTypes() == 64) {
            accessibilityEvent.getText().add(getStateDescription());
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.commonTileView.setLabelSingleLine(false)) {
            super.onMeasure(i, i2);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(final QSTile.State state) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.LargeTileView$onStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                SecQSCommonTileView secQSCommonTileView = LargeTileView.this.commonTileView;
                QSTile.State state2 = state;
                SecQSCommonTileView.Companion companion = SecQSCommonTileView.Companion;
                secQSCommonTileView.handleStateChanged(state2, false);
            }
        });
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.launchableViewDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final String toString() {
        TextView textView = (TextView) findViewById(R.id.tile_label);
        if (textView == null) {
            return super.toString();
        }
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        String idSting = viewUtil.toIdSting(textView);
        String idSting2 = viewUtil.toIdSting(textView);
        String idSting3 = viewUtil.toIdSting(this.iconFrame);
        String idSting4 = viewUtil.toIdSting(this.iconView);
        String shortIdSting = viewUtil.toShortIdSting(this.commonTileView);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LargeTileView{", idSting, ", label = ", idSting2, ", iconFrame = ");
        MoveResult$$ExternalSyntheticOutline0.m(m, idSting3, ", icon = ", idSting4, ", common = ");
        return TransitionKt$$ExternalSyntheticOutline0.m(m, shortIdSting, "}");
    }

    public LargeTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, boolean z) {
        super(context);
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context, z);
        this.iconView = qSIconViewImpl;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.LargeTileView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int intValue = ((Integer) obj).intValue();
                int i = LargeTileView.$r8$clinit;
                LargeTileView.this.setVisibility(intValue);
                return Unit.INSTANCE;
            }
        });
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, z, false, 176, null);
        this.commonTileView = secQSCommonTileView;
        FrameLayout frameLayout = secQSCommonTileView.iconFrame;
        addView(frameLayout);
        this.iconFrame = frameLayout;
        addView(secQSCommonTileView.createLabel(R.layout.sec_qs_large_tile_label, this));
        Resources resources = context.getResources();
        setLayoutParams(new LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.large_tile_width), resources.getDimensionPixelSize(R.dimen.large_tile_height), 1.0f));
        setClipChildren(false);
        setClipToPadding(false);
        setClickable(true);
        setOrientation(0);
        setGravity(16);
        setBackground(context.getDrawable(R.drawable.sec_large_button_ripple_background));
        setTag("anchor");
        setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(context));
        Triple triple = z ? new Triple(Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconSize(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileLabelStartMargin(context))) : new Triple(Integer.valueOf(secQSPanelResourcePicker.getTileIconSize(context)), Integer.valueOf(secQSPanelResourcePicker.getTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTileLabelStartMargin(context)));
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.height = -1;
            layoutParams2.width = CollectionsKt___CollectionsKt.sumOfInt(Arrays.asList(triple.getFirst(), triple.getSecond(), triple.getThird()));
            frameLayout.setLayoutParams(layoutParams2);
        }
        frameLayout.setPaddingRelative(((Number) triple.getSecond()).intValue(), 0, ((Number) triple.getThird()).intValue(), 0);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        return this;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void updateColoredBackground() {
    }
}
