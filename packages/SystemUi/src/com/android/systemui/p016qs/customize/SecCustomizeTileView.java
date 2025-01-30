package com.android.systemui.p016qs.customize;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.p016qs.tileimpl.SecQSTileView;
import com.android.systemui.plugins.p013qs.QSIconView;
import com.android.systemui.plugins.p013qs.QSTile;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecCustomizeTileView extends SecQSTileView implements CustomActionView {
    public final QSIconView mCustomizeIcon;
    public final ArrayList mIds;
    public final boolean mIsActive;
    public float mPointX;
    public float mPointY;

    public SecCustomizeTileView(Context context, QSIconView qSIconView, boolean z) {
        super(context, qSIconView);
        ArrayList arrayList = new ArrayList();
        this.mIds = arrayList;
        this.mIsActive = z;
        this.mCustomizeIcon = qSIconView;
        setImportantForAccessibility(1);
        if (z) {
            arrayList.add(CustomActionId.MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE);
        } else {
            arrayList.add(CustomActionId.MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE);
        }
        this.mIcon.setBackground(null);
        setBackground(null);
    }

    public final void customTileHandleStateChange(QSTile.State state) {
        super.handleStateChanged(state);
        this.mIcon.setImportantForAccessibility(2);
        this.mLabel.setImportantForAccessibility(2);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SecQSTileBaseView
    public final int getCircleColor(int i) {
        return this.mIsActive ? super.getCircleColor(i) : ((LinearLayout) this).mContext.getColor(R.color.qs_edit_tile_round_background_available);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SecQSTileView, com.android.systemui.p016qs.tileimpl.SecQSTileBaseView
    public final void handleStateChanged(QSTile.State state) {
        super.handleStateChanged(state);
    }

    @Override // android.view.View
    public final boolean isLongClickable() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        FontSizeUtils.updateFontSize(this.mLabel, R.dimen.qs_edit_bar_summary_text, 0.8f, 1.0f);
        if (!this.mIsActive) {
            this.mLabel.setTextColor(((LinearLayout) this).mContext.getColor(R.color.qs_edit_tile_available_label_color));
        }
        this.mLabel.setBackground(null);
        this.mCustomizeIcon.setBackground(null);
        this.mSecondLine.setText((CharSequence) null);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SecQSTileView, android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mPointX = motionEvent.getX();
        this.mPointY = motionEvent.getY();
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SecQSTileView
    public final void updateTouchTargetArea() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLabelContainer.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_label_height);
        layoutParams.topMargin = 0;
        this.mLabelContainer.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mIconFrame.getLayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_icon_frame_size);
        this.mIconFrame.setLayoutParams(layoutParams2);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_icon_size);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(dimensionPixelOffset, dimensionPixelOffset, 81);
        this.mIcon.setLayoutParams(layoutParams3);
        this.mBg.setLayoutParams(layoutParams3);
        setLayoutParams(new LinearLayout.LayoutParams(-1, getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_icon_frame_size)));
    }
}
