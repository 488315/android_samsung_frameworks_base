package com.android.systemui.controls.management.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.util.ControlsUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class StructureHolder extends Holder {
    public final String emptyStructure;
    public final TextView structureTextView;

    public StructureHolder(View view) {
        super(view, null);
        TextView textView = (TextView) this.itemView.requireViewById(R.id.controls_main_zone_header);
        ControlsUtil.Companion.updateFontSize$default(ControlsUtil.Companion, textView, R.dimen.basic_interaction_sub_header_text_size);
        this.structureTextView = textView;
        this.emptyStructure = this.itemView.getContext().getResources().getString(R.string.controls_favorite_other_structure_zone_header);
    }

    @Override // com.android.systemui.controls.management.adapter.Holder
    public final void bindData(MainModel mainModel) {
        if (mainModel instanceof MainControlModel) {
            MainControlModel mainControlModel = (MainControlModel) mainModel;
            this.structureTextView.setText(TextUtils.isEmpty(mainControlModel.structure) ? this.emptyStructure : mainControlModel.structure);
            if (mainControlModel.needToHide) {
                this.itemView.setVisibility(8);
                this.itemView.getLayoutParams().height = this.itemView.getResources().getDimensionPixelOffset(R.dimen.control_management_list_padding);
            } else {
                this.itemView.setVisibility(0);
                this.itemView.getLayoutParams().height = -2;
            }
        }
    }
}
