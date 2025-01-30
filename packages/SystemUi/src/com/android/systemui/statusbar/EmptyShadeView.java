package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class EmptyShadeView extends StackScrollerDecorView {
    public TextView mEmptyFooterText;
    public TextView mEmptyText;
    public int mFooterIcon;
    public int mFooterText;
    public int mFooterVisibility;
    public int mSize;
    public int mText;
    public int mTopPadding;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmptyShadeViewState extends ExpandableViewState {
        public EmptyShadeViewState() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof EmptyShadeView) {
                EmptyShadeView emptyShadeView = (EmptyShadeView) view;
                float f = this.clipTopAmount;
                EmptyShadeView emptyShadeView2 = EmptyShadeView.this;
                boolean z = false;
                boolean z2 = f <= ((float) emptyShadeView2.mEmptyText.getPaddingTop()) * 0.6f;
                if (NotiRune.NOTI_STYLE_EMPTY_SHADE) {
                    z2 = emptyShadeView.getTranslationY() > ((float) emptyShadeView2.mTopPadding) * 0.5f;
                    emptyShadeView.mEndAlpha = 1.0f;
                }
                if (z2 && emptyShadeView.mIsVisible) {
                    z = true;
                }
                emptyShadeView.setContentVisible(null, z, true);
            }
        }
    }

    public EmptyShadeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mText = R.string.empty_shade_text;
        this.mFooterIcon = R.drawable.ic_friction_lock_closed;
        this.mFooterText = R.string.unlock_to_see_notif_text;
        this.mFooterVisibility = 8;
        this.mTopPadding = 200;
        this.mSize = getResources().getDimensionPixelSize(R.dimen.notifications_unseen_footer_icon_size);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final ExpandableViewState createExpandableViewState() {
        return new EmptyShadeViewState();
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findContentView() {
        return findViewById(R.id.no_notifications);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findSecondaryView() {
        return findViewById(R.id.no_notifications_footer);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        Drawable drawable;
        super.onConfigurationChanged(configuration);
        this.mSize = getResources().getDimensionPixelSize(R.dimen.notifications_unseen_footer_icon_size);
        this.mEmptyText.setText(this.mText);
        this.mEmptyFooterText.setVisibility(this.mFooterVisibility);
        int i = this.mFooterText;
        this.mFooterText = i;
        if (i != 0) {
            this.mEmptyFooterText.setText(i);
        } else {
            this.mEmptyFooterText.setText((CharSequence) null);
        }
        int i2 = this.mFooterIcon;
        this.mFooterIcon = i2;
        if (i2 == 0) {
            drawable = null;
        } else {
            drawable = getResources().getDrawable(i2);
            int i3 = this.mSize;
            drawable.setBounds(0, 0, i3, i3);
        }
        this.mEmptyFooterText.setCompoundDrawablesRelative(drawable, null, null, null);
        if (NotiRune.NOTI_STYLE_EMPTY_SHADE) {
            this.mEmptyText.setTextColor(getResources().getColor(R.color.sec_no_notification_text_color));
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mEmptyText = (TextView) findContentView();
        TextView textView = (TextView) findSecondaryView();
        this.mEmptyFooterText = textView;
        textView.setCompoundDrawableTintList(textView.getTextColors());
    }
}
