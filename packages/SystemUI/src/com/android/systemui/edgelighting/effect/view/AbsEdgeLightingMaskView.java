package com.android.systemui.edgelighting.effect.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class AbsEdgeLightingMaskView extends DrawEdgeLayout {
    public final String TAG;
    public AnimatorSet mAnimationSet;
    public ImageView mBottomLayer;
    public FrameLayout mContainer;
    public final float[] mHsvColors;
    public boolean mIsAnimating;
    public int mMainColor;
    public View mMainLayer;
    public AnonymousClass3 mOneHandSettingObserver;
    public long mRotateDuration;
    public float mStrokeAlpha;
    public int mSubColor;
    public ImageView mTopLayer;

    public AbsEdgeLightingMaskView(Context context) {
        super(context);
        this.TAG = "AbsEdgeLightingMaskView";
        this.mHsvColors = new float[3];
        this.mIsAnimating = false;
        this.mRotateDuration = 5000L;
        this.mStrokeAlpha = 1.0f;
        init();
    }

    public static void changeRingImageAlpha(View view, float f, long j) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", f);
        ofFloat.setStartDelay(0L);
        ofFloat.setDuration(j);
        ofFloat.start();
    }

    public final void expandViewSize(ImageView imageView) {
        int i = this.mWidth;
        int i2 = this.mHeight;
        if (i2 > i) {
            i = i2;
        }
        int i3 = (int) (i * 1.5f);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = i3;
        layoutParams.height = i3;
        imageView.setLayoutParams(layoutParams);
    }

    public void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.mask_effect_layout, this);
        this.mContainer = (FrameLayout) findViewById(R.id.effect_container);
        this.mTopLayer = (ImageView) findViewById(R.id.top_layer);
        this.mBottomLayer = (ImageView) findViewById(R.id.bottom_layer);
        this.mMainLayer = findViewById(R.id.main_layer);
        this.mStrokeWidth = getResources().getDimensionPixelSize(R.dimen.thick_stroke_width);
        this.mContainer.setAlpha(0.0f);
    }

    public final boolean isRotateAnimating() {
        AnimatorSet animatorSet = this.mAnimationSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return false;
        }
        Log.i(this.TAG, "Rotation animation running");
        return true;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView$3] */
    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mOneHandSettingObserver == null) {
            this.mOneHandSettingObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView.3
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    super.onChange(z);
                    AbsEdgeLightingMaskView.this.invalidate();
                }
            };
            getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING), false, this.mOneHandSettingObserver);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().getContentResolver().unregisterContentObserver(this.mOneHandSettingObserver);
    }

    public final void startRotation(long j) {
        AnimatorSet animatorSet = this.mAnimationSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mAnimationSet = null;
        }
        this.mMainLayer.setAlpha(0.0f);
        this.mTopLayer.setRotation(0.0f);
        this.mBottomLayer.setRotation(0.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mMainLayer, "alpha", this.mStrokeAlpha);
        ofFloat.setDuration(300L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mTopLayer, "rotation", 0.0f, 360.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mBottomLayer, "rotation", 0.0f, 360.0f);
        ofFloat2.setDuration(j);
        ofFloat3.setDuration(j);
        ofFloat2.setRepeatCount(-1);
        ofFloat2.setRepeatMode(1);
        ofFloat3.setRepeatCount(-1);
        ofFloat3.setRepeatMode(1);
        ofFloat3.setStartDelay(500L);
        ofFloat2.setInterpolator(new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f));
        ofFloat3.setInterpolator(new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mAnimationSet = animatorSet2;
        animatorSet2.playTogether(ofFloat2, ofFloat3, ofFloat);
        this.mAnimationSet.start();
    }

    public AbsEdgeLightingMaskView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "AbsEdgeLightingMaskView";
        this.mHsvColors = new float[3];
        this.mIsAnimating = false;
        this.mRotateDuration = 5000L;
        this.mStrokeAlpha = 1.0f;
        init();
    }

    public AbsEdgeLightingMaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "AbsEdgeLightingMaskView";
        this.mHsvColors = new float[3];
        this.mIsAnimating = false;
        this.mRotateDuration = 5000L;
        this.mStrokeAlpha = 1.0f;
        init();
    }

    public AbsEdgeLightingMaskView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "AbsEdgeLightingMaskView";
        this.mHsvColors = new float[3];
        this.mIsAnimating = false;
        this.mRotateDuration = 5000L;
        this.mStrokeAlpha = 1.0f;
        init();
    }
}
