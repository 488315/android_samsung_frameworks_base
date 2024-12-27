package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
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
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoLabelTileView extends QSTileView implements LaunchableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecQSCommonTileView commonTileView;
    public final FrameLayout iconFrame;
    public final QSIconViewImpl iconView;
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

    public NoLabelTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        super(context);
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context);
        this.iconView = qSIconViewImpl;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.NoLabelTileView$launchableViewDelegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                NoLabelTileView noLabelTileView = NoLabelTileView.this;
                int i = NoLabelTileView.$r8$clinit;
                noLabelTileView.setVisibility(intValue);
                return Unit.INSTANCE;
            }
        });
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, false, 112, null);
        this.commonTileView = secQSCommonTileView;
        FrameLayout frameLayout = secQSCommonTileView.iconFrame;
        addView(frameLayout);
        this.iconFrame = frameLayout;
        int tileIconSize = secQSPanelResourcePicker.getTileIconSize(getContext());
        setLayoutParams(new LinearLayout.LayoutParams(tileIconSize, tileIconSize));
        setClipChildren(false);
        setClipToPadding(false);
        setGravity(17);
        setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(getContext()));
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
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.NoLabelTileView$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTile.this.click(expandable$Companion$fromView$1);
            }
        });
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.NoLabelTileView$init$2
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

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        if (i != i2) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            int i4 = layoutParams.width;
            if ((i4 == 0 && layoutParams.height == 0) || i4 == (i3 = layoutParams.height)) {
                return;
            }
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i4, i3, "onMeasure forced setWidth : w=", ", h=", "NoLabelTileView");
            setMeasuredDimension(getLayoutParams().height, getLayoutParams().height);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(final QSTile.State state) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.NoLabelTileView$onStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                SecQSCommonTileView secQSCommonTileView = NoLabelTileView.this.commonTileView;
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

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        setAccessibilityTraversalAfter(view != null ? view.getId() : 0);
        return this;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
    }
}
