package com.android.systemui.qs;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.core.util.Consumer;
import com.android.settingslib.Utils;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PageIndicator extends ViewGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimating;
    public final AnonymousClass1 mAnimationCallback;
    public int mPageDotWidth;
    public int mPageIndicatorHeight;
    public int mPageIndicatorWidth;
    public int mPosition;
    public final ArrayList mQueuedPositions;
    public ColorStateList mTint;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.qs.PageIndicator$1] */
    public PageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mQueuedPositions = new ArrayList();
        this.mPosition = -1;
        this.mAnimationCallback = new Animatable2.AnimationCallback() { // from class: com.android.systemui.qs.PageIndicator.1
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                if (drawable instanceof AnimatedVectorDrawable) {
                    ((AnimatedVectorDrawable) drawable).unregisterAnimationCallback(PageIndicator.this.mAnimationCallback);
                }
                PageIndicator pageIndicator = PageIndicator.this;
                pageIndicator.mAnimating = false;
                if (pageIndicator.mQueuedPositions.size() != 0) {
                    PageIndicator pageIndicator2 = PageIndicator.this;
                    pageIndicator2.setPosition(((Integer) pageIndicator2.mQueuedPositions.remove(0)).intValue());
                }
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.tint});
        if (obtainStyledAttributes.hasValue(0)) {
            this.mTint = obtainStyledAttributes.getColorStateList(0);
        } else {
            this.mTint = Utils.getColorAttr(R.attr.colorAccent, context);
        }
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        this.mPageIndicatorWidth = resources.getDimensionPixelSize(com.android.systemui.R.dimen.qs_page_indicator_width);
        this.mPageIndicatorHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.qs_page_indicator_height);
        this.mPageDotWidth = resources.getDimensionPixelSize(com.android.systemui.R.dimen.qs_page_indicator_dot_width);
        LeftRightArrowPressedListener.Companion.getClass();
        LeftRightArrowPressedListener leftRightArrowPressedListener = new LeftRightArrowPressedListener(null);
        setOnKeyListener(leftRightArrowPressedListener);
        setOnFocusChangeListener(leftRightArrowPressedListener);
        leftRightArrowPressedListener.listener = new Consumer() { // from class: com.android.systemui.qs.PageIndicator$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i = PageIndicator.$r8$clinit;
                PageIndicator.this.getClass();
            }
        };
    }

    public static int getTransition(boolean z, boolean z2, boolean z3) {
        return z3 ? z ? z2 ? com.android.systemui.R.drawable.major_b_a_animation : com.android.systemui.R.drawable.major_b_c_animation : z2 ? com.android.systemui.R.drawable.major_a_b_animation : com.android.systemui.R.drawable.major_c_b_animation : z ? z2 ? com.android.systemui.R.drawable.minor_b_c_animation : com.android.systemui.R.drawable.minor_b_a_animation : z2 ? com.android.systemui.R.drawable.minor_c_b_animation : com.android.systemui.R.drawable.minor_a_b_animation;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        super.onConfigurationChanged(configuration);
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.qs_page_indicator_width);
        boolean z2 = true;
        if (dimensionPixelSize != this.mPageIndicatorWidth) {
            this.mPageIndicatorWidth = dimensionPixelSize;
            z = true;
        } else {
            z = false;
        }
        int dimensionPixelSize2 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.qs_page_indicator_height);
        if (dimensionPixelSize2 != this.mPageIndicatorHeight) {
            this.mPageIndicatorHeight = dimensionPixelSize2;
            z = true;
        }
        int dimensionPixelSize3 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.qs_page_indicator_dot_width);
        if (dimensionPixelSize3 != this.mPageDotWidth) {
            this.mPageDotWidth = dimensionPixelSize3;
        } else {
            z2 = z;
        }
        if (z2) {
            invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            int i6 = (this.mPageIndicatorWidth - this.mPageDotWidth) * i5;
            getChildAt(i5).layout(i6, 0, this.mPageIndicatorWidth + i6, this.mPageIndicatorHeight);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        if (childCount == 0) {
            super.onMeasure(i, i2);
            return;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mPageIndicatorWidth, 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mPageIndicatorHeight, 1073741824);
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).measure(makeMeasureSpec, makeMeasureSpec2);
        }
        int i4 = this.mPageIndicatorWidth;
        int i5 = this.mPageDotWidth;
        setMeasuredDimension(((childCount - 1) * (i4 - i5)) + i5, this.mPageIndicatorHeight);
    }

    public final void setIndex(int i) {
        int childCount = getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            ImageView imageView = (ImageView) getChildAt(i2);
            imageView.setTranslationX(0.0f);
            imageView.setImageResource(com.android.systemui.R.drawable.major_a_b);
            imageView.setAlpha(i2 == i ? 1.0f : 0.42f);
            i2++;
        }
    }

    public void setLocation(float f) {
        int i = (int) f;
        setContentDescription(getContext().getString(com.android.systemui.R.string.accessibility_quick_settings_page, Integer.valueOf(i + 1), Integer.valueOf(getChildCount())));
        int i2 = (f != ((float) i) ? 1 : 0) | (i << 1);
        int i3 = this.mPosition;
        if (this.mQueuedPositions.size() != 0) {
            i3 = ((Integer) AlertController$$ExternalSyntheticOutline0.m(this.mQueuedPositions, 1)).intValue();
        }
        if (i2 == i3) {
            return;
        }
        if (this.mAnimating) {
            this.mQueuedPositions.add(Integer.valueOf(i2));
        } else {
            setPosition(i2);
        }
    }

    public final void setNumPages(int i) {
        setVisibility(i > 1 ? 0 : 8);
        int childCount = getChildCount();
        if (i == childCount) {
            int i2 = this.mPageIndicatorWidth;
            int i3 = this.mPageDotWidth;
            if (((childCount - 1) * (i2 - i3)) + i3 == getMeasuredWidth()) {
                return;
            }
        }
        if (this.mAnimating) {
            Log.w("PageIndicator", "setNumPages during animation");
        }
        while (i < getChildCount()) {
            removeViewAt(getChildCount() - 1);
        }
        while (i > getChildCount()) {
            ImageView imageView = new ImageView(((ViewGroup) this).mContext);
            imageView.setImageResource(com.android.systemui.R.drawable.minor_a_b);
            imageView.setImageTintList(this.mTint);
            addView(imageView, new ViewGroup.LayoutParams(this.mPageIndicatorWidth, this.mPageIndicatorHeight));
        }
        setIndex(this.mPosition >> 1);
        requestLayout();
    }

    public final void setPosition(int i) {
        if (isVisibleToUser() && Math.abs(this.mPosition - i) == 1) {
            int i2 = this.mPosition;
            int i3 = i2 >> 1;
            int i4 = i >> 1;
            setIndex(i3);
            boolean z = (i2 & 1) != 0;
            boolean z2 = !z ? i2 >= i : i2 <= i;
            int min = Math.min(i3, i4);
            int max = Math.max(i3, i4);
            if (max == min) {
                max++;
            }
            ImageView imageView = (ImageView) getChildAt(min);
            ImageView imageView2 = (ImageView) getChildAt(max);
            if (imageView != null && imageView2 != null) {
                imageView2.setTranslationX(imageView.getX() - imageView2.getX());
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) getContext().getDrawable(getTransition(z, z2, false));
                imageView.setImageDrawable(animatedVectorDrawable);
                animatedVectorDrawable.forceAnimationOnUI();
                animatedVectorDrawable.registerAnimationCallback(this.mAnimationCallback);
                animatedVectorDrawable.start();
                imageView.setAlpha(0.42f);
                AnimatedVectorDrawable animatedVectorDrawable2 = (AnimatedVectorDrawable) getContext().getDrawable(getTransition(z, z2, true));
                imageView2.setImageDrawable(animatedVectorDrawable2);
                animatedVectorDrawable2.forceAnimationOnUI();
                animatedVectorDrawable2.registerAnimationCallback(this.mAnimationCallback);
                animatedVectorDrawable2.start();
                imageView2.setAlpha(1.0f);
                this.mAnimating = true;
            }
        } else {
            setIndex(i >> 1);
        }
        this.mPosition = i;
    }
}
