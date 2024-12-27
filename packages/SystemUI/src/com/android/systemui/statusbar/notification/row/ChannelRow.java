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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ChannelRow extends LinearLayout {
    public NotificationChannel channel;
    public TextView channelDescription;
    public TextView channelName;
    public ChannelEditorDialogController controller;
    public final int highlightColor;

    /* renamed from: switch, reason: not valid java name */
    public Switch f76switch;

    public ChannelRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.highlightColor = Utils.getColorAttrDefaultColor(getContext(), R.attr.colorControlHighlight, 0);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.channelName = (TextView) requireViewById(com.android.systemui.R.id.channel_name);
        this.channelDescription = (TextView) requireViewById(com.android.systemui.R.id.channel_description);
        Switch r0 = (Switch) requireViewById(com.android.systemui.R.id.toggle);
        this.f76switch = r0;
        r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelRow$onFinishInflate$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                int i;
                ChannelRow channelRow = ChannelRow.this;
                NotificationChannel notificationChannel = channelRow.channel;
                if (notificationChannel != null) {
                    ChannelEditorDialogController channelEditorDialogController = channelRow.controller;
                    if (channelEditorDialogController == null) {
                        channelEditorDialogController = null;
                    }
                    if (z) {
                        i = notificationChannel.getOriginalImportance();
                        if (i < 2) {
                            i = 2;
                        }
                    } else {
                        i = 0;
                    }
                    channelEditorDialogController.getClass();
                    if (notificationChannel.getImportance() == i) {
                        channelEditorDialogController.edits.remove(notificationChannel);
                    } else {
                        channelEditorDialogController.edits.put(notificationChannel, Integer.valueOf(i));
                    }
                    ChannelEditorDialog channelEditorDialog = channelEditorDialogController.dialog;
                    ChannelEditorDialog channelEditorDialog2 = channelEditorDialog != null ? channelEditorDialog : null;
                    boolean z2 = (channelEditorDialogController.edits.isEmpty() ^ true) || !Boolean.valueOf(channelEditorDialogController.appNotificationsEnabled).equals(channelEditorDialogController.appNotificationsCurrentlyEnabled);
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
                Switch r02 = ChannelRow.this.f76switch;
                if (r02 == null) {
                    r02 = null;
                }
                r02.toggle();
            }
        });
    }
}
