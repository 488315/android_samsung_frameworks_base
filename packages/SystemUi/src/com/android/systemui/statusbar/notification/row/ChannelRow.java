package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.NotificationChannel;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.settingslib.Utils;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChannelRow extends LinearLayout {
    public NotificationChannel channel;
    public TextView channelDescription;
    public TextView channelName;
    public ChannelEditorDialogController controller;
    public final int highlightColor;

    /* renamed from: switch, reason: not valid java name */
    public Switch f835switch;

    public ChannelRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.highlightColor = Utils.getColorAttrDefaultColor(R.attr.colorControlHighlight, getContext(), 0);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.channelName = (TextView) findViewById(com.android.systemui.R.id.channel_name);
        this.channelDescription = (TextView) findViewById(com.android.systemui.R.id.channel_description);
        Switch r0 = (Switch) findViewById(com.android.systemui.R.id.toggle);
        this.f835switch = r0;
        r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelRow$onFinishInflate$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ChannelRow channelRow = ChannelRow.this;
                NotificationChannel notificationChannel = channelRow.channel;
                if (notificationChannel != null) {
                    ChannelEditorDialogController channelEditorDialogController = channelRow.controller;
                    if (channelEditorDialogController == null) {
                        channelEditorDialogController = null;
                    }
                    int importance = z ? notificationChannel.getImportance() : 0;
                    channelEditorDialogController.getClass();
                    int importance2 = notificationChannel.getImportance();
                    Map map = channelEditorDialogController.edits;
                    if (importance2 == importance) {
                        map.remove(notificationChannel);
                    } else {
                        map.put(notificationChannel, Integer.valueOf(importance));
                    }
                    ChannelEditorDialog channelEditorDialog = channelEditorDialogController.dialog;
                    ChannelEditorDialog channelEditorDialog2 = channelEditorDialog != null ? channelEditorDialog : null;
                    boolean z2 = (map.isEmpty() ^ true) || !Intrinsics.areEqual(Boolean.valueOf(channelEditorDialogController.appNotificationsEnabled), channelEditorDialogController.appNotificationsCurrentlyEnabled);
                    TextView textView = (TextView) channelEditorDialog2.findViewById(com.android.systemui.R.id.done_button);
                    if (textView != null) {
                        textView.setText(z2 ? com.android.systemui.R.string.inline_ok_button : com.android.systemui.R.string.inline_done_button);
                    }
                }
            }
        });
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelRow$onFinishInflate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Switch r02 = ChannelRow.this.f835switch;
                if (r02 == null) {
                    r02 = null;
                }
                r02.toggle();
            }
        });
    }
}
