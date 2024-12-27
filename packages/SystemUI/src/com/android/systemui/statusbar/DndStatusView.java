package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DndStatusView extends StackScrollerDecorView {
    public ImageView mDndStatusIcon;
    public TextView mDndStatusText;
    public int mDndStatusVisibility;
    public int mIcon;
    public int mSize;
    public final int mText;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DndStatusViewState extends ExpandableViewState {
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
        this.mText = R.string.zen_mode_settings_dnd_manual_indefinite;
        this.mIcon = R.drawable.ic_notification_dnd_on;
        this.mDndStatusVisibility = 8;
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

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        Drawable drawable;
        super.onConfigurationChanged(configuration);
        this.mSize = getResources().getDimensionPixelSize(R.dimen.notification_dnd_status_icon_size);
        this.mDndStatusText.setText(this.mText);
        this.mDndStatusIcon.setVisibility(this.mDndStatusVisibility);
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

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDndStatusText = (TextView) findViewById(R.id.notification_dnd_status_text);
        this.mDndStatusIcon = (ImageView) findViewById(R.id.notification_dnd_status_icon);
    }
}
