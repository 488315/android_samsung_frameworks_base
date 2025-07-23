package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Removed duplicated region for block: B:104:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0160  */
    /* JADX WARN: Type inference failed for: r6v37, types: [java.lang.CharSequence] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRows() {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.ChannelEditorListView.updateRows():void");
    }
}
