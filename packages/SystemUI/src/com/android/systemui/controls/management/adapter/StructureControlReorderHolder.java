package com.android.systemui.controls.management.adapter;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.ReorderWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import java.util.Arrays;
import java.util.function.Consumer;
import kotlin.jvm.internal.StringCompanionObject;

public final class StructureControlReorderHolder extends SecStructureViewHolder {
    public final Consumer actionCallback;
    public final String alternativeDescription;
    public final String emptyStructureName;
    public final ImageView reorder;
    public final TextView title;
    public final View view;

    public StructureControlReorderHolder(View view, Consumer<StructureControlReorderHolder> consumer) {
        super(view, null);
        this.view = view;
        this.actionCallback = consumer;
        this.title = (TextView) this.itemView.requireViewById(R.id.reorder_title);
        this.reorder = (ImageView) this.itemView.requireViewById(R.id.reorder_structure);
        this.emptyStructureName = view.getContext().getResources().getString(R.string.controls_favorite_other_structure_zone_header);
        Resources resources = view.getContext().getResources();
        int i = StringCompanionObject.$r8$clinit;
        this.alternativeDescription = String.format(resources.getString(R.string.controls_and_hold_to), Arrays.copyOf(new Object[]{resources.getString(R.string.controls_double_tap), resources.getString(R.string.controls_reorder)}, 2));
    }

    @Override // com.android.systemui.controls.management.adapter.SecStructureViewHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        ReorderWrapper reorderWrapper = (ReorderWrapper) structureElementWrapper;
        this.title.setText(TextUtils.isEmpty(reorderWrapper.displayName) ? this.emptyStructureName : reorderWrapper.displayName);
        this.reorder.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.controls.management.adapter.StructureControlReorderHolder$bindData$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0) {
                    return true;
                }
                StructureControlReorderHolder structureControlReorderHolder = StructureControlReorderHolder.this;
                structureControlReorderHolder.actionCallback.accept(structureControlReorderHolder);
                return true;
            }
        });
        this.view.setContentDescription(((Object) this.title.getText()) + ", " + this.alternativeDescription);
    }
}
