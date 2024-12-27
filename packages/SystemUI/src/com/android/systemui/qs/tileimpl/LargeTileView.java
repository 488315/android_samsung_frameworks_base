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
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.qs.tileimpl.SecQSCommonTileView;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.ViewUtil;
import java.util.Iterator;
import kotlin.NotImplementedError;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LargeTileView extends QSTileView implements LaunchableView {
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

    public /* synthetic */ LargeTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
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
        ConstraintWidget$$ExternalSyntheticOutline0.m(m, idSting3, ", icon = ", idSting4, ", common = ");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(m, shortIdSting, "}");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    public LargeTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, boolean z) {
        super(context);
        Triple triple;
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context, z);
        this.iconView = qSIconViewImpl;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.LargeTileView$launchableViewDelegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                LargeTileView largeTileView = LargeTileView.this;
                int i = LargeTileView.$r8$clinit;
                largeTileView.setVisibility(intValue);
                return Unit.INSTANCE;
            }
        });
        SecQSCommonTileView secQSCommonTileView = new SecQSCommonTileView(context, secQSPanelResourcePicker, qSIconViewImpl, this, null, null, z, 48, null);
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
        if (z) {
            triple = new Triple(Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconSize(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePicker.getNoBGTileLabelStartMargin(context)));
        } else {
            Integer valueOf = Integer.valueOf(secQSPanelResourcePicker.getTileIconSize(context));
            SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
            triple = new Triple(valueOf, Integer.valueOf(secQSPanelResourcePickHelper.getTargetPicker().getTileIconStartMargin(context)), Integer.valueOf(secQSPanelResourcePickHelper.getTargetPicker().getTileLabelStartMargin(context)));
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 != null) {
            layoutParams2.height = -1;
            Iterator it = CollectionsKt__CollectionsKt.listOf(triple.getFirst(), triple.getSecond(), triple.getThird()).iterator();
            int i = 0;
            while (it.hasNext()) {
                i += ((Number) it.next()).intValue();
            }
            layoutParams2.width = i;
            frameLayout.setLayoutParams(layoutParams2);
        }
        frameLayout.setPaddingRelative(((Number) triple.getSecond()).intValue(), 0, ((Number) triple.getThird()).intValue(), 0);
    }
}
