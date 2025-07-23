package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SectionHeaderView extends StackScrollerDecorView {
    public ImageView mClearAllButton;
    public ViewGroup mContents;
    public Integer mLabelTextId;
    public TextView mLabelView;
    public View.OnClickListener mOnClearClickListener;

    public SectionHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnClearClickListener = null;
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findContentView() {
        return this.mContents;
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findSecondaryView() {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        this.mContents = (ViewGroup) requireViewById(R.id.content);
        this.mLabelView = (TextView) requireViewById(R.id.header_label);
        ImageView imageView = (ImageView) requireViewById(R.id.btn_clear_all);
        this.mClearAllButton = imageView;
        View.OnClickListener onClickListener = this.mOnClearClickListener;
        if (onClickListener != null) {
            imageView.setOnClickListener(onClickListener);
        }
        Integer num = this.mLabelTextId;
        if (num != null) {
            this.mLabelView.setText(num.intValue());
        }
        this.mLabelView.setAccessibilityHeading(true);
        ViewCompat.replaceAccessibilityAction(this.mLabelView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, getResources().getString(R.string.accessibility_notification_section_header_open_settings), null);
        super.onFinishInflate();
        setVisible(true, false);
    }

    public final void setForegroundColors(int i, int i2) {
        this.mLabelView.setTextColor(i);
        this.mClearAllButton.setImageTintList(ColorStateList.valueOf(i2));
    }
}
