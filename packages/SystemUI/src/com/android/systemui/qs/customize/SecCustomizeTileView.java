package com.android.systemui.qs.customize;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.SecQSTileView;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecCustomizeTileView extends SecQSTileView {
    public final QSIconViewImpl mCustomizeIcon;
    public final ArrayList mIds;
    public final boolean mIsActive;
    public float mPointX;
    public float mPointY;

    public SecCustomizeTileView(Context context, QSIconViewImpl qSIconViewImpl, boolean z) {
        super(context, qSIconViewImpl);
        ArrayList arrayList = new ArrayList();
        this.mIsActive = z;
        this.mCustomizeIcon = qSIconViewImpl;
        setImportantForAccessibility(1);
        if (z) {
            arrayList.add(CustomActionId.MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE);
        } else {
            arrayList.add(CustomActionId.MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE);
        }
        this.mIcon.setBackground(null);
        setBackground(null);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public final int getCircleColor(int i) {
        return this.mIsActive ? super.getCircleColor(i) : ((LinearLayout) this).mContext.getColor(R.color.qs_edit_tile_round_background_available);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileView, com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public final void handleStateChanged(QSTile.State state) {
        super.handleStateChanged(state);
        setStateDescription(null);
    }

    @Override // android.view.View
    public final boolean isLongClickable() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mIsActive) {
            this.mLabel.setTextColor(((LinearLayout) this).mContext.getColor(R.color.qs_edit_tile_available_label_color));
        }
        this.mLabel.setBackground(null);
        this.mCustomizeIcon.setBackground(null);
        this.mSecondLine.setText((CharSequence) null);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mPointX = motionEvent.getX();
        this.mPointY = motionEvent.getY();
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileView
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
