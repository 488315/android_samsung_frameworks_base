package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.AllControlsModel;
import com.android.systemui.controls.management.model.ControlWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsUtil;

public final class StructureControlHolder extends SecStructureViewHolder {
    public final Context context;
    public final StatelessControlAdapter controlAdapter;
    public final LinearLayout structureLayout;

    public StructureControlHolder(View view, int i, LayoutUtil layoutUtil, ControlsUtil controlsUtil) {
        super(view, null);
        this.structureLayout = (LinearLayout) this.itemView.requireViewById(R.id.structure_layout);
        RecyclerView recyclerView = (RecyclerView) this.itemView.requireViewById(R.id.structure_recyclerview);
        Context context = this.itemView.getContext();
        this.context = context;
        StatelessControlAdapter statelessControlAdapter = new StatelessControlAdapter(context, layoutUtil, controlsUtil, i);
        this.controlAdapter = statelessControlAdapter;
        recyclerView.setAdapter(statelessControlAdapter);
        recyclerView.setItemAnimator(null);
        Resources resources = recyclerView.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_list_horizontal_margin) - resources.getDimensionPixelSize(R.dimen.control_base_item_side_margin);
        layoutUtil.getClass();
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams != null) {
            marginLayoutParams.setMargins(dimensionPixelSize, marginLayoutParams.topMargin, dimensionPixelSize, marginLayoutParams.bottomMargin);
            recyclerView.requestLayout();
        }
    }

    @Override // com.android.systemui.controls.management.adapter.SecStructureViewHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        ControlWrapper controlWrapper = (ControlWrapper) structureElementWrapper;
        this.structureLayout.setBackground(controlWrapper.needChunk ? this.context.getDrawable(R.drawable.control_structure_bg) : null);
        StatelessControlAdapter statelessControlAdapter = this.controlAdapter;
        if (statelessControlAdapter == null) {
            statelessControlAdapter = null;
        }
        AllControlsModel allControlsModel = controlWrapper.controlsModel;
        statelessControlAdapter.model = allControlsModel;
        statelessControlAdapter.notifyDataSetChanged();
        StatelessControlAdapter statelessControlAdapter2 = this.controlAdapter;
        allControlsModel.adapter = statelessControlAdapter2 != null ? statelessControlAdapter2 : null;
    }
}
