package com.android.systemui.qs;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PageIndicator extends LinearLayout {
    public boolean mAnimating;
    public final C20311 mAnimationCallback;
    public final int mPageIndicatorHeight;
    public final int mPageIndicatorWidth;
    public int mPosition;
    public final ArrayList mQueuedPositions;
    public final ColorStateList mTint;

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
        resources.getDimensionPixelSize(com.android.systemui.R.dimen.qs_page_indicator_dot_width);
    }

    public static int getTransition(boolean z, boolean z2, boolean z3) {
        return z3 ? z ? z2 ? com.android.systemui.R.drawable.major_b_a_animation : com.android.systemui.R.drawable.major_b_c_animation : z2 ? com.android.systemui.R.drawable.major_a_b_animation : com.android.systemui.R.drawable.major_c_b_animation : z ? z2 ? com.android.systemui.R.drawable.minor_b_c_animation : com.android.systemui.R.drawable.minor_b_a_animation : z2 ? com.android.systemui.R.drawable.minor_c_b_animation : com.android.systemui.R.drawable.minor_a_b_animation;
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
            ArrayList arrayList = this.mQueuedPositions;
            i3 = ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
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

    public void setNumPages(int i) {
        setVisibility(i > 1 ? 0 : 8);
        if (i == getChildCount()) {
            return;
        }
        if (this.mAnimating) {
            Log.w("PageIndicator", "setNumPages during animation");
        }
        while (i < getChildCount()) {
            removeViewAt(getChildCount() - 1);
        }
        while (i > getChildCount()) {
            ImageView imageView = new ImageView(((LinearLayout) this).mContext);
            imageView.setImageResource(com.android.systemui.R.drawable.minor_a_b);
            imageView.setImageTintList(this.mTint);
            addView(imageView, new LinearLayout.LayoutParams(this.mPageIndicatorWidth, this.mPageIndicatorHeight));
        }
        setIndex(this.mPosition >> 1);
        requestLayout();
    }

    public void setPosition(int i) {
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
