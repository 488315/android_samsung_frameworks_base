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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationEclipseEffect extends NotificationEffect {
    public LottieAnimationView mEclipseEffect;

    public NotificationEclipseEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        this.mHandler.sendEmptyMessageDelayed(3, 700L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        LottieAnimationView lottieAnimationView = this.mEclipseEffect;
        if (lottieAnimationView != null) {
            lottieAnimationView.pauseAnimation();
            this.mEclipseEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void finishToastPopupAnimation() {
        LottieAnimationView lottieAnimationView = this.mEclipseEffect;
        if (lottieAnimationView == null || lottieAnimationView.isAnimating()) {
            return;
        }
        this.mEclipseEffect.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new PorterDuffColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP)));
        this.mEclipseEffect.playAnimation();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());
        this.mEclipseEffect = lottieAnimationView;
        lottieAnimationView.setVisibility(0);
        this.mEclipseEffect.setAnimation("Edge_Eclipse_small.json");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, getResources().getDimensionPixelOffset(R.dimen.eclipse_effect_height));
        layoutParams.addRule(10, -1);
        this.mNotificationContainer.addView(this.mEclipseEffect, layoutParams);
        this.mEclipseEffect.setZ(-1.0f);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        LottieAnimationView lottieAnimationView = this.mEclipseEffect;
        if (lottieAnimationView != null) {
            lottieAnimationView.pauseAnimation();
            this.mEclipseEffect.setVisibility(8);
        }
        this.mHandler.sendEmptyMessageDelayed(3, 50L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            this.mEclipseEffect.setAlpha(f);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        if (this.mEclipseEffect != null) {
            this.mEclipseEffect.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new PorterDuffColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP)));
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        LottieAnimationView lottieAnimationView = this.mEclipseEffect;
        if (lottieAnimationView != null) {
            lottieAnimationView.playAnimation();
        }
    }

    public NotificationEclipseEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
