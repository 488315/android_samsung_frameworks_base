package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.CustomElementWrapper;
import com.android.systemui.controls.management.model.CustomZoneNameWrapper;
import com.android.systemui.controls.p005ui.util.ControlsUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ZoneCustomHolder extends CustomHolder {
    public final TextView zone;

    public ZoneCustomHolder(View view) {
        super(view, null);
        TextView textView = (TextView) this.itemView.requireViewById(R.id.controls_custom_zone_header);
        ControlsUtil.Companion.updateFontSize$default(ControlsUtil.Companion, textView, R.dimen.basic_interaction_subheader_text_size);
        this.zone = textView;
    }

    @Override // com.android.systemui.controls.management.adapter.CustomHolder
    public final void bindData(CustomElementWrapper customElementWrapper) {
        this.zone.setText(((CustomZoneNameWrapper) customElementWrapper).zoneName);
    }
}
