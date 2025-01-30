package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class IllustrationPreference extends Preference {
    public final C09381 mAnimationCallback;
    public final C09392 mAnimationCallbackCompat;
    public boolean mCacheComposition;
    public int mImageResId;
    public boolean mLottieDynamicColor;
    public final int mMaxHeight;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context) {
        super(context);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init(context, null);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        this.mLayoutResId = R.layout.illustration_preference;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, 0, 0);
            this.mImageResId = obtainStyledAttributes.getResourceId(13, 0);
            this.mCacheComposition = obtainStyledAttributes.getBoolean(2, true);
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.IllustrationPreference, 0, 0);
            this.mLottieDynamicColor = obtainStyledAttributes2.getBoolean(0, false);
            obtainStyledAttributes2.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.background_view);
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.findViewById(R.id.middleground_layout);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) preferenceViewHolder.findViewById(R.id.lottie_view);
        int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
        int i2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        FrameLayout frameLayout2 = (FrameLayout) preferenceViewHolder.findViewById(R.id.illustration_frame);
        ViewGroup.LayoutParams layoutParams = frameLayout2.getLayoutParams();
        if (i >= i2) {
            i = i2;
        }
        layoutParams.width = i;
        frameLayout2.setLayoutParams(layoutParams);
        lottieAnimationView.cacheComposition = this.mCacheComposition;
        if (this.mImageResId > 0) {
            Object drawable = lottieAnimationView.getDrawable();
            if (drawable instanceof Animatable) {
                if (drawable instanceof Animatable2) {
                    ((Animatable2) drawable).clearAnimationCallbacks();
                } else if (drawable instanceof Animatable2Compat) {
                    ((Animatable2Compat) drawable).clearAnimationCallbacks();
                }
                ((Animatable) drawable).stop();
            }
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setImageResource(this.mImageResId);
            Object drawable2 = lottieAnimationView.getDrawable();
            if (drawable2 == null) {
                final int i3 = this.mImageResId;
                lottieAnimationView.failureListener = new LottieListener() { // from class: com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda0
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        Log.w("IllustrationPreference", "Invalid illustration resource id: " + i3, (Throwable) obj);
                    }
                };
                lottieAnimationView.setAnimation(i3);
                lottieAnimationView.setRepeatCount(-1);
                lottieAnimationView.playAnimation();
            } else if (drawable2 instanceof Animatable) {
                if (drawable2 instanceof Animatable2) {
                    ((Animatable2) drawable2).registerAnimationCallback(this.mAnimationCallback);
                } else if (drawable2 instanceof Animatable2Compat) {
                    ((Animatable2Compat) drawable2).registerAnimationCallback(this.mAnimationCallbackCompat);
                } else if (drawable2 instanceof AnimationDrawable) {
                    ((AnimationDrawable) drawable2).setOneShot(false);
                }
                ((Animatable) drawable2).start();
            }
        }
        if (this.mMaxHeight != -1) {
            Resources resources = imageView.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.settingslib_illustration_width);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.settingslib_illustration_height);
            int min = Math.min(this.mMaxHeight, dimensionPixelSize2);
            imageView.setMaxHeight(min);
            lottieAnimationView.setMaxHeight(min);
            lottieAnimationView.setMaxWidth((int) (min * (dimensionPixelSize / dimensionPixelSize2)));
        }
        frameLayout.removeAllViews();
        frameLayout.setVisibility(8);
        if (this.mLottieDynamicColor) {
            LottieColorUtils.applyDynamicColors(this.mContext, lottieAnimationView);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init(context, attributeSet);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init(context, attributeSet);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init(context, attributeSet);
    }
}
