package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.material.materialswitch.MaterialSwitch;

/* loaded from: classes3.dex */
public final class AppControlView extends LinearLayout {
    public TextView channelName;
    public ImageView iconView;

    /* renamed from: switch, reason: not valid java name */
    public MaterialSwitch f105switch;

    public AppControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        this.iconView = (ImageView) requireViewById(R.id.icon);
        this.channelName = (TextView) requireViewById(R.id.app_name);
        this.f105switch = (MaterialSwitch) requireViewById(R.id.material_toggle);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.AppControlView.onFinishInflate.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaterialSwitch materialSwitch = AppControlView.this.f105switch;
                if (materialSwitch == null) {
                    materialSwitch = null;
                }
                materialSwitch.toggle();
            }
        });
    }
}
