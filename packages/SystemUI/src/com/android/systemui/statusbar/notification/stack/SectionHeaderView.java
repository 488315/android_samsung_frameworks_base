package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.HighlightingTextView;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;

public class SectionHeaderView extends StackScrollerDecorView {
    public ImageView mClearAllButton;
    public ViewGroup mContents;
    public Integer mLabelTextId;
    public HighlightingTextView mLabelView;
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
        this.mLabelView = (HighlightingTextView) requireViewById(R.id.header_label);
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
        super.onFinishInflate();
        setVisible(true, false);
    }
}
