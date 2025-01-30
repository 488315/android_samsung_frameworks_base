package com.android.systemui.controls.management;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.management.ControlAdapter;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureAdapter extends RecyclerView.Adapter {
    public final int currentUserId;
    public final List models;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StructureHolder extends RecyclerView.ViewHolder {
        public final ControlAdapter controlAdapter;

        public StructureHolder(View view, int i) {
            super(view);
            final RecyclerView recyclerView = (RecyclerView) this.itemView.requireViewById(R.id.listAll);
            ControlAdapter controlAdapter = new ControlAdapter(this.itemView.getContext().getResources().getFloat(R.dimen.control_card_elevation), i);
            this.controlAdapter = controlAdapter;
            View view2 = this.itemView;
            int dimensionPixelSize = view2.getContext().getResources().getDimensionPixelSize(R.dimen.controls_card_margin);
            MarginItemDecorator marginItemDecorator = new MarginItemDecorator(dimensionPixelSize, dimensionPixelSize);
            ControlAdapter.Companion companion = ControlAdapter.Companion;
            Resources resources = view2.getResources();
            companion.getClass();
            final int findMaxColumns = ControlAdapter.Companion.findMaxColumns(resources);
            recyclerView.setAdapter(controlAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), findMaxColumns);
            gridLayoutManager.mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.StructureAdapter$StructureHolder$setUpRecyclerView$1$1$1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public final int getSpanSize(int i2) {
                    RecyclerView.Adapter adapter = RecyclerView.this.mAdapter;
                    boolean z = false;
                    if (adapter != null && adapter.getItemViewType(i2) == 1) {
                        z = true;
                    }
                    if (z) {
                        return 1;
                    }
                    return findMaxColumns;
                }
            };
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(marginItemDecorator);
        }
    }

    public StructureAdapter(List<StructureContainer> list, int i) {
        this.models = list;
        this.currentUserId = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.models.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ControlsModel controlsModel = ((StructureContainer) this.models.get(i)).model;
        ControlAdapter controlAdapter = ((StructureHolder) viewHolder).controlAdapter;
        controlAdapter.model = controlsModel;
        controlAdapter.notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new StructureHolder(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.controls_structure_page, (ViewGroup) recyclerView, false), this.currentUserId);
    }
}
