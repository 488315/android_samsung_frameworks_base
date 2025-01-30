package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppControlView extends LinearLayout {
    public TextView channelName;
    public ImageView iconView;

    /* renamed from: switch, reason: not valid java name */
    public Switch f834switch;

    public AppControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        this.iconView = (ImageView) findViewById(R.id.icon);
        this.channelName = (TextView) findViewById(R.id.app_name);
        this.f834switch = (Switch) findViewById(R.id.toggle);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.AppControlView$onFinishInflate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Switch r0 = AppControlView.this.f834switch;
                if (r0 == null) {
                    r0 = null;
                }
                r0.toggle();
            }
        });
    }
}
