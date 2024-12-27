package com.android.systemui.edgelighting.effect.container;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.ReflectEffectView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationReflectEffect extends NotificationEffect {
    public ReflectEffectView mReflectEffectView;

    public NotificationReflectEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        this.mReflectEffectView.stopAnimation();
        this.mHandler.sendEmptyMessageDelayed(3, 700L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        ReflectEffectView reflectEffectView = new ReflectEffectView(getContext());
        this.mReflectEffectView = reflectEffectView;
        reflectEffectView.setVisibility(0);
        addView(this.mReflectEffectView, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void onFlickUpAnimation() {
        super.onFlickUpAnimation();
        ReflectEffectView reflectEffectView = this.mReflectEffectView;
        if (reflectEffectView != null) {
            reflectEffectView.stopAnimation();
            this.mReflectEffectView.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        this.mReflectEffectView.stopAnimation();
        this.mHandler.sendEmptyMessageDelayed(3, 700L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            ReflectEffectView reflectEffectView = this.mReflectEffectView;
            reflectEffectView.mLightingAlpha = f;
            reflectEffectView.mImageFrame.setAlpha(f);
        }
        float f2 = edgeEffectInfo.mStrokeWidth;
        if (f2 > 0.0f) {
            if (edgeEffectInfo.mWidthDepth >= 0) {
                this.mReflectEffectView.mStrokeWidth = f2;
            } else {
                this.mReflectEffectView.mStrokeWidth = f2;
            }
            this.mReflectEffectView.invalidate();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setIsMultiResolutionSupoorted(boolean z) {
        this.mReflectEffectView.mIsMultiResolutionSupoorted = z;
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        this.mReflectEffectView.setVisibility(0);
        this.mReflectEffectView.startAnimation();
        super.show();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void updateEffectLocation() {
        ReflectEffectView reflectEffectView = this.mReflectEffectView;
        if (reflectEffectView != null) {
            AnimatorSet animatorSet = reflectEffectView.mAnimationSet;
            if (animatorSet != null) {
                animatorSet.cancel();
                reflectEffectView.mAnimationSet = null;
            }
            ReflectEffectView reflectEffectView2 = this.mReflectEffectView;
            WindowManager windowManager = (WindowManager) reflectEffectView2.getContext().getSystemService("window");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            int round = (int) Math.round(Math.sqrt(Math.pow(displayMetrics.heightPixels, 2.0d) + Math.pow(displayMetrics.widthPixels, 2.0d)));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(round, round);
            layoutParams.setMarginStart((displayMetrics.widthPixels - round) / 2);
            layoutParams.topMargin = (displayMetrics.heightPixels - round) / 2;
            reflectEffectView2.mImageFrame.setLayoutParams(layoutParams);
            this.mReflectEffectView.startAnimation();
        }
    }

    public NotificationReflectEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
