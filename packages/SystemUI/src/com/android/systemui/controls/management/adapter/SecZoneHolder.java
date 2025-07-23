package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.SecZoneNameWrapper;
import com.android.systemui.controls.util.ControlsUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecZoneHolder extends SecHolder {
    public final TextView zone;

    public SecZoneHolder(View view) {
        super(view, null);
        TextView textView = (TextView) this.itemView.requireViewById(R.id.controls_zone_header);
        ControlsUtil.Companion companion = ControlsUtil.Companion;
        textView.getClass();
        companion.getClass();
        ControlsUtil.Companion.updateFontSize(textView, R.dimen.basic_interaction_sub_header_text_size, 1.3f);
        this.zone = textView;
    }

    @Override // com.android.systemui.controls.management.adapter.SecHolder
    public final void bindData(SecElementWrapper secElementWrapper) {
        this.zone.setText(((SecZoneNameWrapper) secElementWrapper).zoneName);
    }
}
