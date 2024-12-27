package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.SecZoneNameWrapper;
import com.android.systemui.controls.util.ControlsUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecZoneHolder extends SecHolder {
    public final TextView zone;

    public SecZoneHolder(View view) {
        super(view, null);
        TextView textView = (TextView) this.itemView.requireViewById(R.id.controls_zone_header);
        ControlsUtil.Companion companion = ControlsUtil.Companion;
        Intrinsics.checkNotNull(textView);
        ControlsUtil.Companion.updateFontSize$default(companion, textView, R.dimen.basic_interaction_sub_header_text_size);
        this.zone = textView;
    }

    @Override // com.android.systemui.controls.management.adapter.SecHolder
    public final void bindData(SecElementWrapper secElementWrapper) {
        this.zone.setText(((SecZoneNameWrapper) secElementWrapper).zoneName);
    }
}
