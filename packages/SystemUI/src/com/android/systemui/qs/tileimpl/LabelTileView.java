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
import com.samsung.android.knox.custom.IKnoxCustomManager;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class LabelTileView extends QSTileView implements LaunchableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;
    public final LinearLayout labelContainer;
    public final LaunchableViewDelegate launchableViewDelegate;

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

    public LabelTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) throws Resources.NotFoundException {
        super(context);
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context);
        this.iconView = qSIconViewImpl;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.LabelTileView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int iIntValue = ((Integer) obj).intValue();
                int i = LabelTileView.$r8$clinit;
                this.f$0.setVisibility(iIntValue);
                return Unit.INSTANCE;
            }
        });
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, false, false, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp, null);
        this.commonTileView = secQSCommonTileView;
        FrameLayout frameLayout = secQSCommonTileView.iconFrame;
        addView(frameLayout);
        this.iconFrame = frameLayout;
        LinearLayout linearLayoutCreateLabel = secQSCommonTileView.createLabel(R.layout.sec_qs_tile_label, this);
        addView(linearLayoutCreateLabel);
        this.labelContainer = linearLayoutCreateLabel;
        setClipChildren(false);
        setClipToPadding(false);
        setClickable(true);
        setOrientation(1);
        setGravity(49);
        setBackground(getContext().getDrawable(R.drawable.sec_tile_view_ripple_background));
        setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(getContext()));
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.width = -1;
            int dimensionPixelSize = frameLayout.getResources().getDimensionPixelSize(R.dimen.label_tile_icon_top_padding);
            int touchIconSize = secQSPanelResourcePicker.getTouchIconSize(frameLayout.getContext());
            int i = layoutParams2.height;
            layoutParams2.topMargin = i + dimensionPixelSize > touchIconSize ? touchIconSize - i : dimensionPixelSize;
            layoutParams2.bottomMargin = frameLayout.getResources().getDimensionPixelSize(R.dimen.label_tile_icon_bottom_padding);
            frameLayout.setLayoutParams(layoutParams2);
        }
        frameLayout.setLayerType(2, null);
        if (linearLayoutCreateLabel != null) {
            ViewGroup.LayoutParams layoutParams3 = linearLayoutCreateLabel.getLayoutParams();
            LinearLayout.LayoutParams layoutParams4 = layoutParams3 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams3 : null;
            if (layoutParams4 != null) {
                layoutParams4.height = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getLabelHeight(linearLayoutCreateLabel.getContext());
                linearLayoutCreateLabel.setLayoutParams(layoutParams4);
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
    public final void init(final QSTile qSTile) {
        Expandable.Companion.getClass();
        final Expandable$Companion$fromView$1 expandable$Companion$fromView$1 = new Expandable$Companion$fromView$1(this);
        this.commonTileView.tileSpec = qSTile.getTileSpec();
        this.iconFrame.setBackground(null);
        this.iconView.setBackground(null);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.LabelTileView.init.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                qSTile.click(expandable$Companion$fromView$1);
            }
        });
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.LabelTileView.init.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                qSTile.longClick(expandable$Companion$fromView$1);
                this.setPressed(false);
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

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.commonTileView.updateRippleSize();
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
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.LabelTileView.onStateChanged.1
            @Override // java.lang.Runnable
            public final void run() {
                SecQSCommonTileView secQSCommonTileView = LabelTileView.this.commonTileView;
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
    public final String toString() throws Resources.NotFoundException {
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
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LabelTileView{", idSting, ", label = ", idSting2, ", iconFrame = ");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, idSting3, ", icon = ", idSting4, ", common = ");
        return TransitionKt$$ExternalSyntheticOutline0.m(sbM, shortIdSting, "}");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        setAccessibilityTraversalAfter(view != null ? view.getId() : 0);
        return this;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void updateColoredBackground() {
    }
}
