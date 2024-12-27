package com.android.systemui.statusbar.phone.ongoingactivity;

import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingActivityController$addDecoratorForRecyclerView$1 extends RecyclerView.ItemDecoration {
    public final /* synthetic */ OngoingActivityController this$0;

    public OngoingActivityController$addDecoratorForRecyclerView$1(OngoingActivityController ongoingActivityController) {
        this.this$0 = ongoingActivityController;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        recyclerView.getClass();
        if (RecyclerView.getChildAdapterPosition(view) != state.getItemCount() - 1) {
            OngoingActivityController ongoingActivityController = this.this$0;
            int m = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(ongoingActivityController.mContext);
            ongoingActivityController.mLayoutMode = m;
            IndicatorScaleGardener indicatorScaleGardener = ongoingActivityController.indicatorScaleGardener;
            if (m != 1) {
                rect.left = (int) ((ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_layer_offset) - ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_min_width)) * indicatorScaleGardener.getLatestScaleModel(ongoingActivityController.mContext).ratio);
            } else {
                rect.right = (int) ((ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_layer_offset) - ongoingActivityController.mContext.getResources().getDimensionPixelOffset(R.dimen.ongoing_activity_chip_min_width)) * indicatorScaleGardener.getLatestScaleModel(ongoingActivityController.mContext).ratio);
            }
        }
    }
}
