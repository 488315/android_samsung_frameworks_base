package com.android.systemui.controls.management.model;

import android.graphics.Canvas;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.management.adapter.SecStructureAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ReorderStructureModel implements StructureModel {
    public SecStructureAdapter adapter;
    public int dragPos = -1;
    public final List elements;
    public boolean isDragging;
    public final ItemTouchHelper itemTouchHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ReorderStructureModel(List<? extends CharSequence> list) {
        new ArrayList();
        ArrayList arrayList = new ArrayList();
        Iterator<? extends CharSequence> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ReorderWrapper(it.next()));
        }
        this.elements = arrayList;
        this.itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.controls.management.model.ReorderStructureModel$itemTouchHelper$1
            public final int MOVEMENT;

            {
                super(0, 0);
                this.MOVEMENT = 3;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                return ItemTouchHelper.Callback.makeMovementFlags(this.MOVEMENT, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
                float top = viewHolder.itemView.getTop() + f2;
                float height = viewHolder.itemView.getHeight() + top;
                LinearLayout linearLayout = (LinearLayout) viewHolder.itemView.requireViewById(R.id.reorder_structure_layout);
                ReorderStructureModel reorderStructureModel = ReorderStructureModel.this;
                if (reorderStructureModel.isDragging && reorderStructureModel.dragPos == -1 && 0.0f < ((float) Math.hypot(f, f2))) {
                    reorderStructureModel.dragPos = viewHolder.getBindingAdapterPosition();
                    linearLayout.setPressed(true);
                    linearLayout.setElevation(linearLayout.getResources().getDimension(R.dimen.basic_interaction_reorder_background_elevation));
                } else if (!reorderStructureModel.isDragging || (f == 0.0f && f2 == 0.0f)) {
                    if (reorderStructureModel.dragPos != -1) {
                        linearLayout.setPressed(false);
                        linearLayout.setElevation(0.0f);
                    }
                    reorderStructureModel.dragPos = -1;
                }
                if (top <= 0.0f || height >= recyclerView.getHeight()) {
                    return;
                }
                super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                ReorderStructureModel reorderStructureModel = ReorderStructureModel.this;
                reorderStructureModel.getClass();
                if (bindingAdapterPosition < bindingAdapterPosition2) {
                    int i = bindingAdapterPosition;
                    while (i < bindingAdapterPosition2) {
                        int i2 = i + 1;
                        Collections.swap(reorderStructureModel.elements, i, i2);
                        i = i2;
                    }
                } else {
                    int i3 = bindingAdapterPosition2 + 1;
                    if (i3 <= bindingAdapterPosition) {
                        int i4 = bindingAdapterPosition;
                        while (true) {
                            Collections.swap(reorderStructureModel.elements, i4, i4 - 1);
                            if (i4 == i3) {
                                break;
                            }
                            i4--;
                        }
                    }
                }
                SecStructureAdapter secStructureAdapter = reorderStructureModel.adapter;
                if (secStructureAdapter == null) {
                    return true;
                }
                secStructureAdapter.notifyItemMoved(bindingAdapterPosition, bindingAdapterPosition2);
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
                super.onSelectedChanged(viewHolder, i);
                ReorderStructureModel.this.isDragging = i == 2;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
            }
        });
    }

    @Override // com.android.systemui.controls.management.model.StructureModel
    public final List getElements() {
        return this.elements;
    }
}
