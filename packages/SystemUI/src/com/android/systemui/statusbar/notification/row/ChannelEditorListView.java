package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRows() {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.ChannelEditorListView.updateRows():void");
    }
}
