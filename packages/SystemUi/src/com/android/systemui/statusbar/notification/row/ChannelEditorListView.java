package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChannelEditorListView extends LinearLayout {
    public AppControlView appControlRow;
    public Drawable appIcon;
    public String appName;
    public final List channelRows;
    public List channels;
    public ChannelEditorDialogController controller;

    public ChannelEditorListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.channels = new ArrayList();
        this.channelRows = new ArrayList();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.appControlRow = (AppControlView) findViewById(R.id.app_control);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:77:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0131  */
    /* JADX WARN: Type inference failed for: r5v22, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.view.ViewGroup, android.widget.LinearLayout, com.android.systemui.statusbar.notification.row.ChannelEditorListView] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateRows() {
        Switch r5;
        ChannelEditorDialogController channelEditorDialogController = this.controller;
        if (channelEditorDialogController == null) {
            channelEditorDialogController = null;
        }
        boolean z = channelEditorDialogController.appNotificationsEnabled;
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200L);
        autoTransition.addListener(new Transition.TransitionListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorListView$updateRows$1
            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                ChannelEditorListView.this.notifySubtreeAccessibilityStateChangedIfNeeded();
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionPause(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionResume(Transition transition) {
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
            }
        });
        TransitionManager.beginDelayedTransition(this, autoTransition);
        Iterator it = ((ArrayList) this.channelRows).iterator();
        while (it.hasNext()) {
            removeView((ChannelRow) it.next());
        }
        ((ArrayList) this.channelRows).clear();
        AppControlView appControlView = this.appControlRow;
        if (appControlView == null) {
            appControlView = null;
        }
        ImageView imageView = appControlView.iconView;
        if (imageView == null) {
            imageView = null;
        }
        imageView.setImageDrawable(this.appIcon);
        AppControlView appControlView2 = this.appControlRow;
        if (appControlView2 == null) {
            appControlView2 = null;
        }
        TextView textView = appControlView2.channelName;
        if (textView == null) {
            textView = null;
        }
        textView.setText(getContext().getResources().getString(R.string.notification_channel_dialog_title, this.appName));
        AppControlView appControlView3 = this.appControlRow;
        if (appControlView3 == null) {
            appControlView3 = null;
        }
        Switch r2 = appControlView3.f834switch;
        if (r2 == null) {
            r2 = null;
        }
        r2.setChecked(z);
        AppControlView appControlView4 = this.appControlRow;
        if (appControlView4 == null) {
            appControlView4 = null;
        }
        Switch r22 = appControlView4.f834switch;
        if (r22 == null) {
            r22 = null;
        }
        r22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorListView$updateAppControlRow$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                ChannelEditorDialogController channelEditorDialogController2 = ChannelEditorListView.this.controller;
                if (channelEditorDialogController2 == null) {
                    channelEditorDialogController2 = null;
                }
                channelEditorDialogController2.appNotificationsEnabled = z2;
                ChannelEditorDialog channelEditorDialog = channelEditorDialogController2.dialog;
                ChannelEditorDialog channelEditorDialog2 = channelEditorDialog != null ? channelEditorDialog : null;
                boolean z3 = true;
                if (!(!channelEditorDialogController2.edits.isEmpty()) && Intrinsics.areEqual(Boolean.valueOf(channelEditorDialogController2.appNotificationsEnabled), channelEditorDialogController2.appNotificationsCurrentlyEnabled)) {
                    z3 = false;
                }
                TextView textView2 = (TextView) channelEditorDialog2.findViewById(R.id.done_button);
                if (textView2 != null) {
                    textView2.setText(z3 ? R.string.inline_ok_button : R.string.inline_done_button);
                }
                ChannelEditorListView.this.updateRows();
            }
        });
        if (z) {
            LayoutInflater from = LayoutInflater.from(getContext());
            for (NotificationChannel notificationChannel : this.channels) {
                ChannelRow channelRow = (ChannelRow) from.inflate(R.layout.notif_half_shelf_row, (ViewGroup) null);
                ChannelEditorDialogController channelEditorDialogController2 = this.controller;
                if (channelEditorDialogController2 == null) {
                    channelEditorDialogController2 = null;
                }
                channelRow.controller = channelEditorDialogController2;
                channelRow.channel = notificationChannel;
                if (notificationChannel != null) {
                    notificationChannel.getImportance();
                }
                NotificationChannel notificationChannel2 = channelRow.channel;
                if (notificationChannel2 != null) {
                    TextView textView2 = channelRow.channelName;
                    if (textView2 == null) {
                        textView2 = null;
                    }
                    CharSequence name = notificationChannel2.getName();
                    if (name == null) {
                        name = "";
                    }
                    textView2.setText(name);
                    String group = notificationChannel2.getGroup();
                    if (group != null) {
                        TextView textView3 = channelRow.channelDescription;
                        if (textView3 == null) {
                            textView3 = null;
                        }
                        ChannelEditorDialogController channelEditorDialogController3 = channelRow.controller;
                        if (channelEditorDialogController3 == null) {
                            channelEditorDialogController3 = null;
                        }
                        ?? r52 = (CharSequence) channelEditorDialogController3.groupNameLookup.get(group);
                        textView3.setText(r52 != 0 ? r52 : "");
                    }
                    if (notificationChannel2.getGroup() != null) {
                        TextView textView4 = channelRow.channelDescription;
                        if (textView4 == null) {
                            textView4 = null;
                        }
                        if (!TextUtils.isEmpty(textView4.getText())) {
                            TextView textView5 = channelRow.channelDescription;
                            if (textView5 == null) {
                                textView5 = null;
                            }
                            textView5.setVisibility(0);
                            r5 = channelRow.f835switch;
                            if (r5 == null) {
                                r5 = null;
                            }
                            r5.setChecked(notificationChannel2.getImportance() != 0);
                        }
                    }
                    TextView textView6 = channelRow.channelDescription;
                    if (textView6 == null) {
                        textView6 = null;
                    }
                    textView6.setVisibility(8);
                    r5 = channelRow.f835switch;
                    if (r5 == null) {
                    }
                    r5.setChecked(notificationChannel2.getImportance() != 0);
                }
                ((ArrayList) this.channelRows).add(channelRow);
                addView(channelRow);
            }
        }
    }
}
