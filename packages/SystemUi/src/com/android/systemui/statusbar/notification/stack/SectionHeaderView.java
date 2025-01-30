package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SectionHeaderView extends StackScrollerDecorView {
    public ImageView mClearAllButton;
    public ViewGroup mContents;
    public View.OnClickListener mLabelClickListener;
    public Integer mLabelTextId;
    public TextView mLabelView;
    public View.OnClickListener mOnClearClickListener;

    public SectionHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLabelClickListener = null;
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
        View.OnClickListener onClickListener2 = this.mLabelClickListener;
        if (onClickListener2 != null) {
            this.mLabelView.setOnClickListener(onClickListener2);
        }
        Integer num = this.mLabelTextId;
        if (num != null) {
            this.mLabelView.setText(num.intValue());
        }
        super.onFinishInflate();
        setVisible(true, false);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }
}
