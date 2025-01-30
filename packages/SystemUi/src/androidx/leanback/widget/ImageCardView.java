package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ImageCardView extends BaseCardView {
    public boolean mAttachedToWindow;
    public ImageView mBadgeImage;
    public TextView mContentView;
    public ObjectAnimator mFadeInAnimator;
    public ImageView mImageView;
    public ViewGroup mInfoArea;
    public TextView mTitleView;

    @Deprecated
    public ImageCardView(Context context, int i) {
        this(new ContextThemeWrapper(context, i));
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        if (this.mImageView.getAlpha() == 0.0f) {
            this.mImageView.setAlpha(0.0f);
            if (this.mAttachedToWindow) {
                this.mFadeInAnimator.start();
            }
        }
    }

    @Override // androidx.leanback.widget.BaseCardView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mFadeInAnimator.cancel();
        this.mImageView.setAlpha(1.0f);
        super.onDetachedFromWindow();
    }

    public ImageCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ViewGroup viewGroup;
        setFocusable(true);
        setFocusableInTouchMode(true);
        LayoutInflater from = LayoutInflater.from(getContext());
        from.inflate(R.layout.lb_image_card_view, this);
        Context context2 = getContext();
        int[] iArr = R$styleable.lbImageCardView;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, iArr, i, 2132018867);
        Context context3 = getContext();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context3, iArr, attributeSet, obtainStyledAttributes, i, 2132018867);
        int i2 = obtainStyledAttributes.getInt(1, 0);
        boolean z = i2 == 0;
        boolean z2 = (i2 & 1) == 1;
        boolean z3 = (i2 & 2) == 2;
        boolean z4 = (i2 & 4) == 4;
        boolean z5 = !z4 && (i2 & 8) == 8;
        ImageView imageView = (ImageView) findViewById(R.id.main_image);
        this.mImageView = imageView;
        if (imageView.getDrawable() == null) {
            this.mImageView.setVisibility(4);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mImageView, "alpha", 1.0f);
        this.mFadeInAnimator = ofFloat;
        ofFloat.setDuration(this.mImageView.getResources().getInteger(android.R.integer.config_shortAnimTime));
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.info_field);
        this.mInfoArea = viewGroup2;
        if (z) {
            removeView(viewGroup2);
            obtainStyledAttributes.recycle();
            return;
        }
        if (z2) {
            TextView textView = (TextView) from.inflate(R.layout.lb_image_card_view_themed_title, viewGroup2, false);
            this.mTitleView = textView;
            this.mInfoArea.addView(textView);
        }
        if (z3) {
            TextView textView2 = (TextView) from.inflate(R.layout.lb_image_card_view_themed_content, this.mInfoArea, false);
            this.mContentView = textView2;
            this.mInfoArea.addView(textView2);
        }
        if (z4 || z5) {
            ImageView imageView2 = (ImageView) from.inflate(z5 ? R.layout.lb_image_card_view_themed_badge_left : R.layout.lb_image_card_view_themed_badge_right, this.mInfoArea, false);
            this.mBadgeImage = imageView2;
            this.mInfoArea.addView(imageView2);
        }
        if (z2 && !z3 && this.mBadgeImage != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mTitleView.getLayoutParams();
            if (z5) {
                layoutParams.addRule(17, this.mBadgeImage.getId());
            } else {
                layoutParams.addRule(16, this.mBadgeImage.getId());
            }
            this.mTitleView.setLayoutParams(layoutParams);
        }
        if (z3) {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mContentView.getLayoutParams();
            if (!z2) {
                layoutParams2.addRule(10);
            }
            if (z5) {
                layoutParams2.removeRule(16);
                layoutParams2.removeRule(20);
                layoutParams2.addRule(17, this.mBadgeImage.getId());
            }
            this.mContentView.setLayoutParams(layoutParams2);
        }
        ImageView imageView3 = this.mBadgeImage;
        if (imageView3 != null) {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) imageView3.getLayoutParams();
            if (z3) {
                layoutParams3.addRule(8, this.mContentView.getId());
            } else if (z2) {
                layoutParams3.addRule(8, this.mTitleView.getId());
            }
            this.mBadgeImage.setLayoutParams(layoutParams3);
        }
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable != null && (viewGroup = this.mInfoArea) != null) {
            viewGroup.setBackground(drawable);
        }
        ImageView imageView4 = this.mBadgeImage;
        if (imageView4 != null && imageView4.getDrawable() == null) {
            this.mBadgeImage.setVisibility(8);
        }
        obtainStyledAttributes.recycle();
    }

    public ImageCardView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ImageCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.imageCardViewStyle);
    }
}
