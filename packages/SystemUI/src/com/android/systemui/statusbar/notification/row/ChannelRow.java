package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.NotificationChannel;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.google.android.material.materialswitch.MaterialSwitch;

/* loaded from: classes3.dex */
public final class ChannelRow extends LinearLayout {
    public NotificationChannel channel;
    public TextView channelDescription;
    public TextView channelName;
    public ChannelEditorDialogController controller;
    public final int highlightColor;

    /* renamed from: switch, reason: not valid java name */
    public MaterialSwitch f106switch;

    public ChannelRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.highlightColor = Utils.getColorAttrDefaultColor(getContext(), R.attr.colorControlHighlight, 0);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.channelName = (TextView) requireViewById(com.android.systemui.R.id.channel_name);
        this.channelDescription = (TextView) requireViewById(com.android.systemui.R.id.channel_description);
        MaterialSwitch materialSwitch = (MaterialSwitch) requireViewById(com.android.systemui.R.id.material_toggle);
        this.f106switch = materialSwitch;
        if (materialSwitch == null) {
            materialSwitch = null;
        }
        materialSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelRow.onFinishInflate.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                int originalImportance;
                ChannelRow channelRow = ChannelRow.this;
                NotificationChannel notificationChannel = channelRow.channel;
                if (notificationChannel != null) {
                    ChannelEditorDialogController channelEditorDialogController = channelRow.controller;
                    if (channelEditorDialogController == null) {
                        channelEditorDialogController = null;
                    }
                    if (z) {
                        originalImportance = notificationChannel.getOriginalImportance();
                        if (originalImportance < 2) {
                            originalImportance = 2;
                        }
                    } else {
                        originalImportance = 0;
                    }
                    channelEditorDialogController.getClass();
                    if (notificationChannel.getImportance() == originalImportance) {
                        channelEditorDialogController.edits.remove(notificationChannel);
                    } else {
                        channelEditorDialogController.edits.put(notificationChannel, Integer.valueOf(originalImportance));
                    }
                    ChannelEditorDialog channelEditorDialog = channelEditorDialogController.dialog;
                    ChannelEditorDialog channelEditorDialog2 = channelEditorDialog != null ? channelEditorDialog : null;
                    boolean z2 = (channelEditorDialogController.edits.isEmpty() && Boolean.valueOf(channelEditorDialogController.appNotificationsEnabled).equals(channelEditorDialogController.appNotificationsCurrentlyEnabled)) ? false : true;
                    TextView textView = (TextView) channelEditorDialog2.findViewById(com.android.systemui.R.id.done_button);
                    if (textView != null) {
                        textView.setText(z2 ? com.android.systemui.R.string.inline_ok_button : com.android.systemui.R.string.inline_done_button);
                    }
                }
            }
        });
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelRow.onFinishInflate.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaterialSwitch materialSwitch2 = ChannelRow.this.f106switch;
                if (materialSwitch2 == null) {
                    materialSwitch2 = null;
                }
                materialSwitch2.toggle();
            }
        });
    }
}
