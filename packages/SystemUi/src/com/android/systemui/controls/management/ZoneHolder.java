package com.android.systemui.controls.management;

import android.view.View;
import android.widget.TextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ZoneHolder extends Holder {
    public final TextView zone;

    public ZoneHolder(View view) {
        super(view, null);
        this.zone = (TextView) this.itemView;
    }

    @Override // com.android.systemui.controls.management.Holder
    public final void bindData(ElementWrapper elementWrapper) {
        this.zone.setText(((ZoneNameWrapper) elementWrapper).zoneName);
    }
}
