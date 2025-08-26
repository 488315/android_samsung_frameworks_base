package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qp.SubscreenPagedTileLayout;
import com.android.systemui.qp.SubscreenTileLayout;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class QSPanel extends LinearLayout implements TunerService.Tunable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean mCanCollapse;
    public final ArrayMap mChildrenLayoutTop;
    public View mFooter;
    public boolean mListening;
    public final List mOnConfigurationChangedListeners;
    public final boolean mShouldMoveMediaOnExpansion;
    public SubscreenPagedTileLayout mTileLayout;

    public interface QSTileLayout {
        int getHeight();
    }

    public QSPanel(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
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
        SubscreenPagedTileLayout subscreenPagedTileLayout = this.mTileLayout;
        if (subscreenPagedTileLayout != null) {
            subscreenPagedTileLayout.mTiles.add(qSPanelControllerBase$TileRecord);
            subscreenPagedTileLayout.mDistributeTiles = true;
            subscreenPagedTileLayout.requestLayout();
        }
    }

    public View getMediaPlaceholder() {
        return null;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        isAttachedToWindow();
        ((ArrayList) this.mOnConfigurationChangedListeners).forEach(new QSPanel$$ExternalSyntheticLambda0());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0077 A[SYNTHETIC] */
    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            this.mChildrenLayoutTop.put(childAt, Integer.valueOf(childAt.getTop()));
        }
        SubscreenTileLayout subscreenTileLayout = (SubscreenTileLayout) this.mTileLayout.mPages.get(0);
        int paddingBottom = (subscreenTileLayout == null ? 0 : subscreenTileLayout.getPaddingBottom() + subscreenTileLayout.mLastTileBottom) - this.mTileLayout.getHeight();
        boolean z2 = false;
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            View childAt2 = getChildAt(i6);
            if (z2) {
                int i7 = (childAt2 != null || this.mShouldMoveMediaOnExpansion) ? paddingBottom : 0;
                Integer num = (Integer) this.mChildrenLayoutTop.get(childAt2);
                if (num != null) {
                    int iIntValue = num.intValue() + i7;
                    childAt2.setLeftTopRightBottom(childAt2.getLeft(), iIntValue, childAt2.getRight(), childAt2.getHeight() + iIntValue);
                    if (childAt2 != this.mTileLayout) {
                    }
                }
            } else if (childAt2 != this.mTileLayout) {
                z2 = true;
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
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
