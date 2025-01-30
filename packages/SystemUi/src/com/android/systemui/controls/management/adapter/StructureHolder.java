package com.android.systemui.controls.management.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.util.ControlsUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureHolder extends Holder {
    public final String emptyStructure;
    public final TextView structureTextView;

    public StructureHolder(View view) {
        super(view, null);
        TextView textView = (TextView) this.itemView.requireViewById(R.id.controls_custom_main_zone_header);
        ControlsUtil.Companion.updateFontSize$default(ControlsUtil.Companion, textView, R.dimen.basic_interaction_subheader_text_size);
        this.structureTextView = textView;
        this.emptyStructure = this.itemView.getContext().getResources().getString(R.string.controls_favorite_other_structure_zone_header);
    }

    @Override // com.android.systemui.controls.management.adapter.Holder
    public final void bindData(MainModel mainModel) {
        if (mainModel instanceof MainControlModel) {
            MainControlModel mainControlModel = (MainControlModel) mainModel;
            this.structureTextView.setText(TextUtils.isEmpty(mainControlModel.structure) ? this.emptyStructure : mainControlModel.structure);
            boolean z = mainControlModel.needToHide;
            View view = this.itemView;
            if (z) {
                view.setVisibility(8);
                view.getLayoutParams().height = view.getResources().getDimensionPixelOffset(R.dimen.controls_custom_management_list_padding);
            } else {
                view.setVisibility(0);
                view.getLayoutParams().height = -2;
            }
        }
    }
}
