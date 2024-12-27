package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R;

public final class AppControlView extends LinearLayout {
    public TextView channelName;
    public ImageView iconView;

    /* renamed from: switch, reason: not valid java name */
    public Switch f75switch;

    public AppControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        this.iconView = (ImageView) requireViewById(R.id.icon);
        this.channelName = (TextView) requireViewById(R.id.app_name);
        this.f75switch = (Switch) requireViewById(R.id.toggle);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.AppControlView$onFinishInflate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Switch r0 = AppControlView.this.f75switch;
                if (r0 == null) {
                    r0 = null;
                }
                r0.toggle();
            }
        });
    }
}
