package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.qs.customize.CustomizerTileViewPager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SecPageIndicator extends LinearLayout {
    public CustomizerTileViewPager.AnonymousClass4 mCallback;
    public final Context mContext;
    public final LayoutInflater mInflater;
    public int mNumPages;
    public int mPosition;
    public float mQsExpansion;
    public int mSelectedColor;
    public int mSelectedColorResId;
    public int mUnselectedColor;
    public int mUnselectedColorResId;

    public SecPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumPages = -1;
        this.mPosition = -1;
        this.mSelectedColorResId = -1;
        this.mUnselectedColorResId = -1;
        this.mContext = context;
        Resources resources = context.getResources();
        this.mSelectedColor = resources.getColor(R.color.qs_page_indicator_tint_color_selected);
        this.mUnselectedColor = resources.getColor(R.color.sec_qs_page_indicator_tint_color_unselected);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        setFocusable(false);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public final void playAnimation(ImageView imageView, boolean z, boolean z2) {
        String string;
        if (imageView == null) {
            return;
        }
        Drawable background = imageView.getBackground();
        if (background instanceof TransitionDrawable) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) background;
            if (z) {
                transitionDrawable.startTransition(150);
                string = this.mContext.getString(R.string.accessibility_quick_settings_selected);
            } else {
                if (z2) {
                    transitionDrawable.resetTransition();
                } else {
                    transitionDrawable.reverseTransition(150);
                }
                string = this.mContext.getString(R.string.accessibility_quick_settings_not_selected);
            }
            ViewParent parent = imageView.getParent();
            if (parent instanceof LinearLayout) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ", ");
                m.append(this.mContext.getString(R.string.accessibility_quick_settings_page, Integer.valueOf(imageView.getId() + 1), Integer.valueOf(this.mNumPages)));
                ((LinearLayout) parent).setContentDescription(m.toString());
            }
        }
    }

    public final void reset(int i) {
        int i2 = 0;
        while (i2 < getChildCount()) {
            View childAt = ((LinearLayout) getChildAt(i2)).getChildAt(0);
            if (childAt instanceof ImageView) {
                playAnimation((ImageView) childAt, i2 == i, true);
            }
            i2++;
        }
        this.mPosition = i;
    }

    public final void setLocation(float f) {
        int round = Math.round(f);
        if (getLayoutDirection() == 1) {
            round = (this.mNumPages - 1) - round;
        }
        if (round == this.mPosition) {
            return;
        }
        if (isVisibleToUser() && Math.abs(this.mPosition - round) == 1) {
            int i = this.mPosition;
            if (getChildAt(i) != null && getChildAt(round) != null) {
                View childAt = ((LinearLayout) getChildAt(i)).getChildAt(0);
                View childAt2 = ((LinearLayout) getChildAt(round)).getChildAt(0);
                playAnimation((ImageView) childAt, false, false);
                playAnimation((ImageView) childAt2, true, false);
            }
        } else {
            reset(round);
        }
        this.mPosition = round;
    }

    public final void setNumPages(int i) {
        setVisibility(i > 1 ? 0 : 8);
        this.mNumPages = i;
        while (getChildAt(0) != null) {
            removeViewAt(0);
        }
        for (final int i2 = 0; i2 < i; i2++) {
            LinearLayout linearLayout = (LinearLayout) this.mInflater.inflate(R.layout.page_indicator_contents, (ViewGroup) this, false);
            Drawable[] drawableArr = {getContext().getDrawable(R.drawable.qs_page_indicator_unselected), getContext().getDrawable(R.drawable.qs_page_indicator_selected)};
            Drawable drawable = drawableArr[0];
            int i3 = this.mUnselectedColor;
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            drawable.setColorFilter(i3, mode);
            drawableArr[1].setColorFilter(this.mSelectedColor, mode);
            TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
            transitionDrawable.setCrossFadeEnabled(true);
            ((ImageView) linearLayout.findViewById(R.id.page_indicator_dot)).setBackground(transitionDrawable);
            linearLayout.findViewById(R.id.page_indicator_dot).setId(i2);
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecPageIndicator$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecPageIndicator secPageIndicator = SecPageIndicator.this;
                    int i4 = i2;
                    CustomizerTileViewPager.AnonymousClass4 anonymousClass4 = secPageIndicator.mCallback;
                    if (anonymousClass4 == null || secPageIndicator.mQsExpansion != 1.0f) {
                        return;
                    }
                    CustomizerTileViewPager.this.setCurrentItem(i4, true);
                }
            });
            addView(linearLayout);
        }
        reset(0);
        requestLayout();
    }
}
