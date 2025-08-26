package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.res.Resources;
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
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.material.materialswitch.MaterialSwitch;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class ChannelEditorListView extends LinearLayout {
    public AppControlView appControlRow;
    public Drawable appIcon;
    public String appName;
    public LinearLayout channelListView;
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
        this.appControlRow = (AppControlView) requireViewById(R.id.app_control);
        this.channelListView = (LinearLayout) requireViewById(R.id.scrollView);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0131  */
    /* JADX WARN: Type inference failed for: r6v37, types: [java.lang.CharSequence] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateRows() throws Resources.NotFoundException {
        MaterialSwitch materialSwitch;
        MaterialSwitch materialSwitch2;
        TextView textView;
        CharSequence text;
        ChannelEditorDialogController channelEditorDialogController = this.controller;
        if (channelEditorDialogController == null) {
            channelEditorDialogController = null;
        }
        boolean z = channelEditorDialogController.appNotificationsEnabled;
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200L);
        autoTransition.addListener(new Transition.TransitionListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorListView.updateRows.1
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
        ArrayList arrayList = (ArrayList) this.channelRows;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ChannelRow channelRow = (ChannelRow) obj;
            LinearLayout linearLayout = this.channelListView;
            if (linearLayout == null) {
                linearLayout = null;
            }
            linearLayout.removeView(channelRow);
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
        String string = getContext().getResources().getString(R.string.notification_channel_dialog_title, this.appName);
        AppControlView appControlView2 = this.appControlRow;
        if (appControlView2 == null) {
            appControlView2 = null;
        }
        TextView textView2 = appControlView2.channelName;
        if (textView2 == null) {
            textView2 = null;
        }
        textView2.setText(string);
        AppControlView appControlView3 = this.appControlRow;
        if (appControlView3 == null) {
            appControlView3 = null;
        }
        MaterialSwitch materialSwitch3 = appControlView3.f105switch;
        if (materialSwitch3 == null) {
            materialSwitch3 = null;
        }
        materialSwitch3.setContentDescription(string);
        AppControlView appControlView4 = this.appControlRow;
        if (appControlView4 == null) {
            appControlView4 = null;
        }
        MaterialSwitch materialSwitch4 = appControlView4.f105switch;
        if (materialSwitch4 == null) {
            materialSwitch4 = null;
        }
        materialSwitch4.setChecked(z);
        AppControlView appControlView5 = this.appControlRow;
        if (appControlView5 == null) {
            appControlView5 = null;
        }
        MaterialSwitch materialSwitch5 = appControlView5.f105switch;
        if (materialSwitch5 == null) {
            materialSwitch5 = null;
        }
        materialSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorListView$updateAppControlRow$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) throws Resources.NotFoundException {
                ChannelEditorDialogController channelEditorDialogController2 = this.this$0.controller;
                if (channelEditorDialogController2 == null) {
                    channelEditorDialogController2 = null;
                }
                channelEditorDialogController2.appNotificationsEnabled = z2;
                ChannelEditorDialog channelEditorDialog = channelEditorDialogController2.dialog;
                ChannelEditorDialog channelEditorDialog2 = channelEditorDialog != null ? channelEditorDialog : null;
                boolean z3 = (channelEditorDialogController2.edits.isEmpty() && Boolean.valueOf(channelEditorDialogController2.appNotificationsEnabled).equals(channelEditorDialogController2.appNotificationsCurrentlyEnabled)) ? false : true;
                TextView textView3 = (TextView) channelEditorDialog2.findViewById(R.id.done_button);
                if (textView3 != null) {
                    textView3.setText(z3 ? R.string.inline_ok_button : R.string.inline_done_button);
                }
                this.this$0.updateRows();
            }
        });
        if (z) {
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
            for (NotificationChannel notificationChannel : this.channels) {
                layoutInflaterFrom.getClass();
                ChannelRow channelRow2 = (ChannelRow) layoutInflaterFrom.inflate(R.layout.notif_half_shelf_row, (ViewGroup) null);
                ChannelEditorDialogController channelEditorDialogController2 = this.controller;
                if (channelEditorDialogController2 == null) {
                    channelEditorDialogController2 = null;
                }
                channelRow2.controller = channelEditorDialogController2;
                channelRow2.channel = notificationChannel;
                if (notificationChannel != null) {
                    notificationChannel.getImportance();
                }
                NotificationChannel notificationChannel2 = channelRow2.channel;
                if (notificationChannel2 != null) {
                    TextView textView3 = channelRow2.channelName;
                    if (textView3 == null) {
                        textView3 = null;
                    }
                    CharSequence name = notificationChannel2.getName();
                    if (name == null) {
                        name = "";
                    }
                    textView3.setText(name);
                    String group = notificationChannel2.getGroup();
                    if (group != null) {
                        TextView textView4 = channelRow2.channelDescription;
                        if (textView4 == null) {
                            textView4 = null;
                        }
                        ChannelEditorDialogController channelEditorDialogController3 = channelRow2.controller;
                        if (channelEditorDialogController3 == null) {
                            channelEditorDialogController3 = null;
                        }
                        ?? r6 = (CharSequence) channelEditorDialogController3.groupNameLookup.get(group);
                        textView4.setText(r6 != 0 ? r6 : "");
                    }
                    if (notificationChannel2.getGroup() == null) {
                        TextView textView5 = channelRow2.channelDescription;
                        if (textView5 == null) {
                            textView5 = null;
                        }
                        textView5.setVisibility(8);
                        materialSwitch = channelRow2.f106switch;
                        if (materialSwitch == null) {
                            materialSwitch = null;
                        }
                        materialSwitch.setChecked(notificationChannel2.getImportance() == 0);
                        materialSwitch2 = channelRow2.f106switch;
                        if (materialSwitch2 == null) {
                            materialSwitch2 = null;
                        }
                        textView = channelRow2.channelDescription;
                        if (textView == null) {
                            textView = null;
                        }
                        if (TextUtils.isEmpty(textView.getText())) {
                            TextView textView6 = channelRow2.channelName;
                            if (textView6 == null) {
                                textView6 = null;
                            }
                            CharSequence text2 = textView6.getText();
                            TextView textView7 = channelRow2.channelDescription;
                            if (textView7 == null) {
                                textView7 = null;
                            }
                            text = ((Object) text2) + " " + ((Object) textView7.getText());
                        } else {
                            TextView textView8 = channelRow2.channelName;
                            if (textView8 == null) {
                                textView8 = null;
                            }
                            text = textView8.getText();
                        }
                        materialSwitch2.setContentDescription(text);
                    } else {
                        TextView textView9 = channelRow2.channelDescription;
                        if (textView9 == null) {
                            textView9 = null;
                        }
                        if (!TextUtils.isEmpty(textView9.getText())) {
                            TextView textView10 = channelRow2.channelDescription;
                            if (textView10 == null) {
                                textView10 = null;
                            }
                            textView10.setVisibility(0);
                        }
                        materialSwitch = channelRow2.f106switch;
                        if (materialSwitch == null) {
                        }
                        materialSwitch.setChecked(notificationChannel2.getImportance() == 0);
                        materialSwitch2 = channelRow2.f106switch;
                        if (materialSwitch2 == null) {
                        }
                        textView = channelRow2.channelDescription;
                        if (textView == null) {
                        }
                        if (TextUtils.isEmpty(textView.getText())) {
                        }
                        materialSwitch2.setContentDescription(text);
                    }
                }
                ((ArrayList) this.channelRows).add(channelRow2);
                LinearLayout linearLayout2 = this.channelListView;
                if (linearLayout2 == null) {
                    linearLayout2 = null;
                }
                linearLayout2.addView(channelRow2);
            }
        }
    }
}
