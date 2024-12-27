package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
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
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LabelTileView extends QSTileView implements LaunchableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;
    public final LinearLayout labelContainer;
    public final LaunchableViewDelegate launchableViewDelegate;

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

    public LabelTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        super(context);
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context);
        this.iconView = qSIconViewImpl;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.LabelTileView$launchableViewDelegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                LabelTileView labelTileView = LabelTileView.this;
                int i = LabelTileView.$r8$clinit;
                labelTileView.setVisibility(intValue);
                return Unit.INSTANCE;
            }
        });
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, false, 112, null);
        this.commonTileView = secQSCommonTileView;
        FrameLayout frameLayout = secQSCommonTileView.iconFrame;
        addView(frameLayout);
        this.iconFrame = frameLayout;
        LinearLayout createLabel = secQSCommonTileView.createLabel(R.layout.sec_qs_tile_label, this);
        addView(createLabel);
        this.labelContainer = createLabel;
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
            layoutParams2.topMargin = frameLayout.getResources().getDimensionPixelSize(R.dimen.label_tile_icon_top_bottom_padding);
            layoutParams2.bottomMargin = frameLayout.getResources().getDimensionPixelSize(R.dimen.label_tile_icon_top_bottom_padding);
            frameLayout.setLayoutParams(layoutParams2);
        }
        frameLayout.setLayerType(2, null);
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
    public final void init(final QSTile qSTile) {
        Expandable.Companion.getClass();
        final Expandable$Companion$fromView$1 expandable$Companion$fromView$1 = new Expandable$Companion$fromView$1(this);
        this.commonTileView.tileSpec = qSTile.getTileSpec();
        this.iconFrame.setBackground(null);
        this.iconView.setBackground(null);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.LabelTileView$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTile.this.click(expandable$Companion$fromView$1);
            }
        });
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.LabelTileView$init$2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSTile.this.longClick(expandable$Companion$fromView$1);
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

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(final QSTile.State state) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.LabelTileView$onStateChanged$1
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
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LabelTileView{", idSting, ", label = ", idSting2, ", iconFrame = ");
        ConstraintWidget$$ExternalSyntheticOutline0.m(m, idSting3, ", icon = ", idSting4, ", common = ");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(m, shortIdSting, "}");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        setAccessibilityTraversalAfter(view != null ? view.getId() : 0);
        return this;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
    }
}
