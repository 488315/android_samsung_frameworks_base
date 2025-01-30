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
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsRuneWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureControlHolder extends CustomStructureHolder {
    public final Context context;
    public CustomControlAdapter controlAdapter;
    public final LinearLayout structureLayout;

    public StructureControlHolder(View view, int i, LayoutUtil layoutUtil, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper) {
        super(view, null);
        this.structureLayout = (LinearLayout) this.itemView.requireViewById(R.id.structure_layout);
        RecyclerView recyclerView = (RecyclerView) this.itemView.requireViewById(R.id.structure_recyclerview);
        Context context = this.itemView.getContext();
        this.context = context;
        CustomControlAdapter customControlAdapter = new CustomControlAdapter(context, layoutUtil, controlsUtil, controlsRuneWrapper, i);
        this.controlAdapter = customControlAdapter;
        recyclerView.setAdapter(customControlAdapter);
        recyclerView.setItemAnimator(null);
        Resources resources = recyclerView.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.controls_list_margin_horizontal) - resources.getDimensionPixelSize(R.dimen.control_base_item_side_margin);
        layoutUtil.getClass();
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams != null) {
            marginLayoutParams.setMargins(dimensionPixelSize, marginLayoutParams.topMargin, dimensionPixelSize, marginLayoutParams.bottomMargin);
            recyclerView.requestLayout();
        }
    }

    @Override // com.android.systemui.controls.management.adapter.CustomStructureHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        ControlWrapper controlWrapper = (ControlWrapper) structureElementWrapper;
        this.structureLayout.setBackground(controlWrapper.needChunk ? this.context.getDrawable(R.drawable.control_structure_background) : null);
        CustomControlAdapter customControlAdapter = this.controlAdapter;
        if (customControlAdapter == null) {
            customControlAdapter = null;
        }
        AllControlsModel allControlsModel = controlWrapper.controlsModel;
        customControlAdapter.model = allControlsModel;
        customControlAdapter.notifyDataSetChanged();
        CustomControlAdapter customControlAdapter2 = this.controlAdapter;
        allControlsModel.adapter = customControlAdapter2 != null ? customControlAdapter2 : null;
    }
}
