package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class QSPanel extends LinearLayout implements TunerService.Tunable {
    public final boolean mCanCollapse;
    public final ArrayMap mChildrenLayoutTop;
    public View mFooter;
    public boolean mListening;
    public final List mOnConfigurationChangedListeners;
    public final boolean mShouldMoveMediaOnExpansion;
    public QSTileLayout mTileLayout;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface QSTileLayout {
        void addTile(QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord);

        int getHeight();

        int getTilesHeight();

        void removeTile(QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord);

        void setListening(boolean z, UiEventLogger uiEventLogger);

        boolean updateResources();
    }

    public QSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnConfigurationChangedListeners = new ArrayList();
        this.mChildrenLayoutTop = new ArrayMap();
        new Rect();
        this.mShouldMoveMediaOnExpansion = true;
        this.mCanCollapse = true;
        Utils.useQsMediaPlayer(context);
        getResources().getDimensionPixelSize(R.dimen.quick_settings_bottom_margin_media);
        getResources().getDimensionPixelSize(R.dimen.qs_tile_margin_vertical);
        setOrientation(1);
        getChildCount();
    }

    public static void switchToParent(View view, ViewGroup viewGroup, int i, String str) {
        if (viewGroup == null) {
            Log.w(str, "Trying to move view to null parent", new IllegalStateException());
            return;
        }
        ViewGroup viewGroup2 = (ViewGroup) view.getParent();
        if (viewGroup2 != viewGroup) {
            if (viewGroup2 != null) {
                viewGroup2.removeView(view);
            }
            viewGroup.addView(view, i);
        } else {
            if (viewGroup.indexOfChild(view) == i) {
                return;
            }
            viewGroup.removeView(view);
            viewGroup.addView(view, i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.plugins.qs.QSTile$Callback, com.android.systemui.qs.QSPanel$1] */
    public final void addTile(final QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord) {
        ?? r0 = new SQSTile.SCallback() { // from class: com.android.systemui.qs.QSPanel.1
            @Override // com.android.systemui.plugins.qs.QSTile.Callback
            public final void onStateChanged(QSTile.State state) {
                QSPanel.this.getClass();
                qSPanelControllerBase$TileRecord.tileView.onStateChanged(state);
            }
        };
        qSPanelControllerBase$TileRecord.tile.addCallback(r0);
        qSPanelControllerBase$TileRecord.callback = r0;
        QSTileView qSTileView = qSPanelControllerBase$TileRecord.tileView;
        QSTile qSTile = qSPanelControllerBase$TileRecord.tile;
        qSTileView.init(qSTile);
        qSTile.refreshState();
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.addTile(qSPanelControllerBase$TileRecord);
        }
    }

    public View getMediaPlaceholder() {
        return null;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((ArrayList) this.mOnConfigurationChangedListeners).forEach(new QSPanel$$ExternalSyntheticLambda0());
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mFooter = findViewById(R.id.qs_footer);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mCanCollapse) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            this.mChildrenLayoutTop.put(childAt, Integer.valueOf(childAt.getTop()));
        }
        int tilesHeight = this.mTileLayout.getTilesHeight() - this.mTileLayout.getHeight();
        boolean z2 = false;
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            View childAt2 = getChildAt(i6);
            if (z2) {
                int i7 = (childAt2 != null || this.mShouldMoveMediaOnExpansion) ? tilesHeight : 0;
                Integer num = (Integer) this.mChildrenLayoutTop.get(childAt2);
                if (num != null) {
                    int intValue = num.intValue() + i7;
                    childAt2.setLeftTopRightBottom(childAt2.getLeft(), intValue, childAt2.getRight(), childAt2.getHeight() + intValue);
                }
            }
            if (childAt2 == this.mTileLayout) {
                z2 = true;
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Object obj = this.mTileLayout;
        if ((obj instanceof PagedTileLayout) && ((View) obj).getParent() == this) {
            int size = 10000 - View.MeasureSpec.getSize(i2);
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(10000, 1073741824);
            ((PagedTileLayout) this.mTileLayout).mExcessHeight = size;
            i2 = makeMeasureSpec;
        }
        super.onMeasure(i, i2);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                int measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                paddingTop = marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + measuredHeight;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), paddingTop);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String str, String str2) {
        "qs_show_brightness".equals(str);
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i != 262144) {
        }
        return super.performAccessibilityAction(i, bundle);
    }
}
