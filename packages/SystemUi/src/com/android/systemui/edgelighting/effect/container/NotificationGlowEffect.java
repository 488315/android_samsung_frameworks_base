package com.android.systemui.edgelighting.effect.container;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView;
import com.android.systemui.edgelighting.effect.view.GlowGradientEffect;
import com.android.systemui.edgelighting.effect.view.GradientEffectView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationGlowEffect extends NotificationEffect {
    public GlowGradientEffect mGlowEffectView;

    public NotificationGlowEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        this.mGlowEffectView.hide();
        this.mHandler.sendEmptyMessageDelayed(3, 1000L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        GlowGradientEffect glowGradientEffect = new GlowGradientEffect(getContext());
        this.mGlowEffectView = glowGradientEffect;
        addView(glowGradientEffect, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        this.mGlowEffectView.hide();
        this.mHandler.sendEmptyMessageDelayed(3, 1000L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeWidth;
        if (f > 0.0f) {
            if (edgeEffectInfo.mWidthDepth >= 0) {
                this.mGlowEffectView.mGradientView.mStrokeWidth = f;
            } else {
                this.mGlowEffectView.mGradientView.mStrokeWidth = f;
            }
        }
        float f2 = edgeEffectInfo.mStrokeAlpha;
        if (f2 > 0.0f) {
            GlowGradientEffect glowGradientEffect = this.mGlowEffectView;
            glowGradientEffect.mLightingAlpha = f2;
            glowGradientEffect.mGradientView.mStrokeAlpha = f2;
            glowGradientEffect.mGlowView.setAlpha(f2);
            GradientEffectView gradientEffectView = glowGradientEffect.mGradientView;
            gradientEffectView.mContainer.setAlpha(gradientEffectView.mStrokeAlpha);
            glowGradientEffect.mGradientView.invalidate();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        GlowGradientEffect glowGradientEffect = this.mGlowEffectView;
        int i = this.mConvertColor;
        glowGradientEffect.mGlowView.setColorFilter(i);
        GradientEffectView gradientEffectView = glowGradientEffect.mGradientView;
        gradientEffectView.mMainColor = i;
        gradientEffectView.mMainLayer.setBackgroundColor(0);
        gradientEffectView.mTopLayer.setColorFilter(i);
        Log.i("GLOW", "set Main Color");
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setIsMultiResolutionSupoorted(boolean z) {
        this.mGlowEffectView.mGradientView.mIsMultiResolutionSupoorted = z;
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        super.show();
        GlowGradientEffect glowGradientEffect = this.mGlowEffectView;
        Log.i("GLOW", "show " + glowGradientEffect.mGradientView.isAttachedToWindow());
        GradientEffectView gradientEffectView = glowGradientEffect.mGradientView;
        if (!gradientEffectView.mIsAnimating) {
            gradientEffectView.mIsAnimating = true;
            gradientEffectView.mContainer.setAlpha(0.0f);
            gradientEffectView.setImageDrawable();
            gradientEffectView.mRotateDuration = 3000L;
            gradientEffectView.startRotation(3000L);
            AbsEdgeLightingMaskView.changeRingImageAlpha(gradientEffectView.mContainer, gradientEffectView.mStrokeAlpha, 900L);
        }
        glowGradientEffect.mGlowView.setAlpha(0.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(glowGradientEffect.mGlowView, "alpha", glowGradientEffect.mLightingAlpha);
        ofFloat.setDuration(1500L);
        ofFloat.start();
        GradientEffectView gradientEffectView2 = glowGradientEffect.mGradientView;
        gradientEffectView2.mContainer.setAlpha(0.0f);
        AbsEdgeLightingMaskView.changeRingImageAlpha(gradientEffectView2.mContainer, gradientEffectView2.mStrokeAlpha, 1500L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        GlowGradientEffect glowGradientEffect = this.mGlowEffectView;
        if (glowGradientEffect != null) {
            glowGradientEffect.mGlowView.setAlpha(glowGradientEffect.mLightingAlpha);
            GradientEffectView gradientEffectView = glowGradientEffect.mGradientView;
            gradientEffectView.mContainer.setAlpha(gradientEffectView.mStrokeAlpha);
            glowGradientEffect.mGradientView.invalidate();
            if (this.mHandler.hasMessages(2)) {
                this.mHandler.removeMessages(2);
            }
        }
    }
}
