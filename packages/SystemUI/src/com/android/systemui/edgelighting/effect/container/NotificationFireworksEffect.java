package com.android.systemui.edgelighting.effect.container;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationFireworksEffect extends NotificationNormalEffect {
    public LottieAnimationView mFireworksEffect;

    public NotificationFireworksEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        LottieAnimationView lottieAnimationView = this.mFireworksEffect;
        if (lottieAnimationView != null) {
            lottieAnimationView.pauseAnimation();
            this.mFireworksEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void finishToastPopupAnimation() {
        LottieAnimationView lottieAnimationView = this.mFireworksEffect;
        if (lottieAnimationView != null) {
            this.mFireworksEffect.setLayoutParams((RelativeLayout.LayoutParams) lottieAnimationView.getLayoutParams());
            this.mFireworksEffect.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new PorterDuffColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP)));
            this.mFireworksEffect.setRepeatMode(1);
            this.mFireworksEffect.setRepeatCount(5);
            this.mFireworksEffect.playAnimation();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());
        this.mFireworksEffect = lottieAnimationView;
        lottieAnimationView.setVisibility(0);
        this.mFireworksEffect.setAnimation("Edge_Fireworks.json");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, getResources().getDimensionPixelOffset(R.dimen.fireworks_effect_height));
        layoutParams.addRule(10, -1);
        this.mNotificationContainer.addView(this.mFireworksEffect, layoutParams);
        this.mFireworksEffect.setZ(-1.0f);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        super.requestHideEffectView();
        LottieAnimationView lottieAnimationView = this.mFireworksEffect;
        if (lottieAnimationView != null) {
            lottieAnimationView.pauseAnimation();
            this.mFireworksEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            this.mFireworksEffect.setAlpha(f);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        if (this.mFireworksEffect != null) {
            this.mFireworksEffect.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new PorterDuffColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP)));
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        LottieAnimationView lottieAnimationView;
        super.update();
        EdgeLightNotiEffectView edgeLightNotiEffectView = this.mLightEffectView;
        if (edgeLightNotiEffectView == null || !edgeLightNotiEffectView.isRotateAnimating() || (lottieAnimationView = this.mFireworksEffect) == null) {
            return;
        }
        lottieAnimationView.playAnimation();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void updateEffectLocation() {
        LottieAnimationView lottieAnimationView = this.mFireworksEffect;
        if (lottieAnimationView != null) {
            this.mNotificationContainer.removeView(lottieAnimationView);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, getResources().getDimensionPixelOffset(R.dimen.fireworks_effect_height));
            layoutParams.addRule(10, -1);
            this.mNotificationContainer.addView(this.mFireworksEffect, layoutParams);
        }
    }

    public NotificationFireworksEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
