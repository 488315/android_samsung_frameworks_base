package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;

/* loaded from: classes3.dex */
public class DndStatusView extends StackScrollerDecorView {
    public ImageView mDndStatusIcon;
    public TextView mDndStatusText;
    public int mIcon;
    public final int mSize;

    public class DndStatusViewState extends ExpandableViewState {
        public DndStatusViewState(DndStatusView dndStatusView) {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof DndStatusView) {
                DndStatusView dndStatusView = (DndStatusView) view;
                dndStatusView.setContentVisibleAnimated(dndStatusView.mIsVisible);
            }
        }
    }

    public DndStatusView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIcon = R.drawable.ic_notification_dnd_on;
        this.mSize = getResources().getDimensionPixelSize(R.dimen.notification_dnd_status_icon_size);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final ExpandableViewState createExpandableViewState() {
        return new DndStatusViewState(this);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findContentView() {
        return findViewById(R.id.notification_dnd_status_text);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findSecondaryView() {
        return findViewById(R.id.notification_dnd_status_icon);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDndStatusText = (TextView) findViewById(R.id.notification_dnd_status_text);
        this.mDndStatusIcon = (ImageView) findViewById(R.id.notification_dnd_status_icon);
    }

    public final void setDndTextAndIcon(String str) throws Resources.NotFoundException {
        Drawable drawable;
        this.mDndStatusText.setText(str);
        int i = this.mIcon;
        this.mIcon = i;
        if (i == 0) {
            drawable = null;
        } else {
            drawable = getResources().getDrawable(i);
            int i2 = this.mSize;
            drawable.setBounds(0, 0, i2, i2);
        }
        this.mDndStatusIcon.setImageDrawable(drawable);
    }
}
