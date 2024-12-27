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
public final class NotificationHeartEffect extends NotificationNormalEffect {
    public LottieAnimationView mHeartEffect;

    public NotificationHeartEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        LottieAnimationView lottieAnimationView = this.mHeartEffect;
        if (lottieAnimationView != null) {
            lottieAnimationView.pauseAnimation();
            this.mHeartEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void finishToastPopupAnimation() {
        if (this.mHeartEffect != null) {
            this.mHeartEffect.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new PorterDuffColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP)));
            this.mHeartEffect.setRepeatMode(1);
            this.mHeartEffect.setRepeatCount(5);
            this.mHeartEffect.playAnimation();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());
        this.mHeartEffect = lottieAnimationView;
        lottieAnimationView.setVisibility(0);
        this.mHeartEffect.setAnimation("Edge_Heart.json");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, getResources().getDimensionPixelOffset(R.dimen.heart_effect_size));
        layoutParams.addRule(10, -1);
        layoutParams.addRule(14, -1);
        this.mNotificationContainer.addView(this.mHeartEffect, layoutParams);
        this.mHeartEffect.setZ(-1.0f);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        super.requestHideEffectView();
        LottieAnimationView lottieAnimationView = this.mHeartEffect;
        if (lottieAnimationView != null) {
            lottieAnimationView.pauseAnimation();
            this.mHeartEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            this.mHeartEffect.setAlpha(f);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        if (this.mHeartEffect != null) {
            this.mHeartEffect.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new PorterDuffColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP)));
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        LottieAnimationView lottieAnimationView;
        super.update();
        EdgeLightNotiEffectView edgeLightNotiEffectView = this.mLightEffectView;
        if (edgeLightNotiEffectView == null || !edgeLightNotiEffectView.isRotateAnimating() || (lottieAnimationView = this.mHeartEffect) == null) {
            return;
        }
        lottieAnimationView.playAnimation();
    }

    public NotificationHeartEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
