package com.android.systemui.controls.management.model;

import android.graphics.Canvas;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ReorderStructureModel implements StructureModel {
    public RecyclerView.Adapter adapter;
    public int dragPos = -1;
    public final List elements;
    public boolean isDragging;
    public final ItemTouchHelper itemTouchHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

            /* JADX WARN: Code restructure failed: missing block: B:26:0x0063, code lost:
            
                if ((r21 == 0.0f) != false) goto L21;
             */
            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
                float top = r3.getTop() + f2;
                float height = r3.getHeight() + top;
                LinearLayout linearLayout = (LinearLayout) viewHolder.itemView.requireViewById(R.id.reorder_structure_layout);
                ReorderStructureModel reorderStructureModel = ReorderStructureModel.this;
                if (reorderStructureModel.isDragging && reorderStructureModel.dragPos == -1 && 0.0f < ((float) Math.hypot(f, f2))) {
                    reorderStructureModel.dragPos = viewHolder.getBindingAdapterPosition();
                    linearLayout.setPressed(true);
                    linearLayout.setElevation(linearLayout.getResources().getDimension(R.dimen.basic_interaction_reorder_background_elevation));
                } else {
                    if (reorderStructureModel.isDragging) {
                        if (f == 0.0f) {
                        }
                    }
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
                List list2 = reorderStructureModel.elements;
                if (bindingAdapterPosition < bindingAdapterPosition2) {
                    int i = bindingAdapterPosition;
                    while (i < bindingAdapterPosition2) {
                        int i2 = i + 1;
                        Collections.swap(list2, i, i2);
                        i = i2;
                    }
                } else {
                    int i3 = bindingAdapterPosition2 + 1;
                    if (i3 <= bindingAdapterPosition) {
                        int i4 = bindingAdapterPosition;
                        while (true) {
                            int i5 = i4 - 1;
                            Collections.swap(list2, i4, i5);
                            if (i4 == i3) {
                                break;
                            }
                            i4 = i5;
                        }
                    }
                }
                RecyclerView.Adapter adapter = reorderStructureModel.adapter;
                if (adapter == null) {
                    return true;
                }
                adapter.notifyItemMoved(bindingAdapterPosition, bindingAdapterPosition2);
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
