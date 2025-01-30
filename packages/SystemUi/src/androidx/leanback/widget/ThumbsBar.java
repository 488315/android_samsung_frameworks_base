package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ThumbsBar extends LinearLayout {
    public final int mHeroThumbHeightInPixel;
    public final int mHeroThumbWidthInPixel;
    public final int mMeasuredMarginInPixel;
    public int mNumOfThumbs;
    public final int mThumbHeightInPixel;
    public final int mThumbWidthInPixel;

    public ThumbsBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int childCount = getChildCount() / 2;
        View childAt = getChildAt(childCount);
        int width = (getWidth() / 2) - (childAt.getMeasuredWidth() / 2);
        int measuredWidth = (childAt.getMeasuredWidth() / 2) + (getWidth() / 2);
        childAt.layout(width, getPaddingTop(), measuredWidth, childAt.getMeasuredHeight() + getPaddingTop());
        int measuredHeight = (childAt.getMeasuredHeight() / 2) + getPaddingTop();
        for (int i5 = childCount - 1; i5 >= 0; i5--) {
            int i6 = width - this.mMeasuredMarginInPixel;
            View childAt2 = getChildAt(i5);
            childAt2.layout(i6 - childAt2.getMeasuredWidth(), measuredHeight - (childAt2.getMeasuredHeight() / 2), i6, (childAt2.getMeasuredHeight() / 2) + measuredHeight);
            width = i6 - childAt2.getMeasuredWidth();
        }
        while (true) {
            childCount++;
            if (childCount >= this.mNumOfThumbs) {
                return;
            }
            int i7 = measuredWidth + this.mMeasuredMarginInPixel;
            View childAt3 = getChildAt(childCount);
            childAt3.layout(i7, measuredHeight - (childAt3.getMeasuredHeight() / 2), childAt3.getMeasuredWidth() + i7, (childAt3.getMeasuredHeight() / 2) + measuredHeight);
            measuredWidth = i7 + childAt3.getMeasuredWidth();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth() - this.mHeroThumbWidthInPixel;
        int i3 = ((measuredWidth + r5) - 1) / (this.mThumbWidthInPixel + this.mMeasuredMarginInPixel);
        if (i3 < 2) {
            i3 = 2;
        } else if ((i3 & 1) != 0) {
            i3++;
        }
        int i4 = i3 + 1;
        if (this.mNumOfThumbs != i4) {
            this.mNumOfThumbs = i4;
            while (getChildCount() > this.mNumOfThumbs) {
                removeView(getChildAt(getChildCount() - 1));
            }
            while (getChildCount() < this.mNumOfThumbs) {
                addView(new ImageView(getContext()), new LinearLayout.LayoutParams(this.mThumbWidthInPixel, this.mThumbHeightInPixel));
            }
            int childCount = getChildCount() / 2;
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View childAt = getChildAt(i5);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (childCount == i5) {
                    layoutParams.width = this.mHeroThumbWidthInPixel;
                    layoutParams.height = this.mHeroThumbHeightInPixel;
                } else {
                    layoutParams.width = this.mThumbWidthInPixel;
                    layoutParams.height = this.mThumbHeightInPixel;
                }
                childAt.setLayoutParams(layoutParams);
            }
        }
    }

    public ThumbsBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNumOfThumbs = -1;
        new SparseArray();
        this.mThumbWidthInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_thumbs_width);
        this.mThumbHeightInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_thumbs_height);
        this.mHeroThumbHeightInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_hero_thumbs_width);
        this.mHeroThumbWidthInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_hero_thumbs_height);
        this.mMeasuredMarginInPixel = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_transport_thumbs_margin);
    }
}
