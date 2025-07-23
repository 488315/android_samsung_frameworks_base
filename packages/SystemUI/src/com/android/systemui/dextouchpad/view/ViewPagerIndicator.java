package com.android.systemui.dextouchpad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.dextouchpad.util.Utils;
import com.android.systemui.res.R$styleable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ViewPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Drawable mDefaultDrawable;
    public int mLastReportedPosition;
    public int mMargin;
    public Drawable mSelectedDrawable;
    public int mSelectedPosition;

    public ViewPagerIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLastReportedPosition = -1;
        init(attributeSet);
    }

    public final void init(AttributeSet attributeSet) {
        setOrientation(0);
        setGravity(17);
        this.mSelectedPosition = 0;
        this.mLastReportedPosition = 0;
        if (attributeSet != null) {
            TypedArray obtainAttributes = getResources().obtainAttributes(attributeSet, R$styleable.ViewPagerIndicator);
            this.mDefaultDrawable = obtainAttributes.getDrawable(0);
            this.mSelectedDrawable = obtainAttributes.getDrawable(2);
            this.mMargin = obtainAttributes.getDimensionPixelSize(1, 0);
            obtainAttributes.recycle();
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public final void onPageSelected(int i) {
        ((ImageView) getChildAt(this.mSelectedPosition)).setImageDrawable(this.mDefaultDrawable);
        ((ImageView) getChildAt(i)).setImageDrawable(this.mSelectedDrawable);
        this.mSelectedPosition = i;
        if (this.mLastReportedPosition != i) {
            this.mLastReportedPosition = i;
            Utils.sendSALogging("705", "7007", String.valueOf(i + 1));
        }
    }

    public final void setViewPager(final ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            return;
        }
        removeAllViews();
        int count = adapter.getCount();
        for (final int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            if (i == 0) {
                imageView.setImageDrawable(this.mSelectedDrawable);
            } else {
                imageView.setImageDrawable(this.mDefaultDrawable);
            }
            int i2 = (int) (this.mMargin * 0.5d);
            imageView.setPadding(i2, i2, i2, i2);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.dextouchpad.view.ViewPagerIndicator$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewPager viewPager2 = ViewPager.this;
                    int i3 = i;
                    int i4 = ViewPagerIndicator.$r8$clinit;
                    viewPager2.setCurrentItem(i3);
                }
            });
            addView(imageView, layoutParams);
        }
        viewPager.addOnPageChangeListener(this);
    }

    public ViewPagerIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastReportedPosition = -1;
        init(attributeSet);
    }

    public ViewPagerIndicator(Context context) {
        super(context);
        this.mLastReportedPosition = -1;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public final void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public final void onPageScrolled(float f, int i) {
    }
}
