package com.android.systemui.edgelighting.effect.container;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.SpotLightEffectView;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NotificationSpotlightEffect extends NotificationEffect {
    public SpotLightEffectView mSpotlightEffect;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.effect.container.NotificationSpotlightEffect$1 */
    public final class C13211 {
        public C13211() {
        }
    }

    public NotificationSpotlightEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        sendEmptyMessageDelayed(3, 700L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void finishToastPopupAnimation() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            AnimatorSet animatorSet = spotLightEffectView.mAnimatorSet;
            if (animatorSet != null ? animatorSet.isRunning() : false) {
                return;
            }
            this.mSpotlightEffect.setVisibility(0);
            SpotLightEffectView spotLightEffectView2 = this.mSpotlightEffect;
            spotLightEffectView2.mCurrWidth = VolteConstants.ErrorCode.KDDI_INVITE_FAIL;
            spotLightEffectView2.startAnimation();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        SpotLightEffectView spotLightEffectView = new SpotLightEffectView(getContext());
        this.mSpotlightEffect = spotLightEffectView;
        spotLightEffectView.setVisibility(0);
        this.mSpotlightEffect.mAnimListener = new C13211();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(VolteConstants.ErrorCode.KDDI_INVITE_FAIL, 2521);
        layoutParams.setMarginStart((this.mScreenWidth - VolteConstants.ErrorCode.KDDI_INVITE_FAIL) / 2);
        layoutParams.topMargin = -1912;
        addView(this.mSpotlightEffect, layoutParams);
        this.mSpotlightEffect.setZ(-1.0f);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void onFlickUpAnimation() {
        super.onFlickUpAnimation();
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        this.mSpotlightEffect.setVisibility(4);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            this.mSpotlightEffect.setAlpha(f);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.mSpotlightDrawable.setColorFilter(this.mConvertColor, PorterDuff.Mode.SRC_ATOP);
            spotLightEffectView.setBackground(null);
            spotLightEffectView.setBackground(spotLightEffectView.mSpotlightDrawable);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.setVisibility(8);
            if (!this.mIsShowMorphView) {
                this.mSpotlightEffect.setVisibility(0);
                SpotLightEffectView spotLightEffectView2 = this.mSpotlightEffect;
                spotLightEffectView2.mCurrWidth = VolteConstants.ErrorCode.KDDI_INVITE_FAIL;
                spotLightEffectView2.startAnimation();
            }
        }
        super.show();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.setVisibility(0);
            SpotLightEffectView spotLightEffectView2 = this.mSpotlightEffect;
            spotLightEffectView2.mCurrWidth = VolteConstants.ErrorCode.KDDI_INVITE_FAIL;
            spotLightEffectView2.startAnimation();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void updateEffectLocation() {
        SpotLightEffectView spotLightEffectView = this.mSpotlightEffect;
        if (spotLightEffectView != null) {
            spotLightEffectView.stopAnimation();
            this.mSpotlightEffect.startAnimation();
        }
    }

    public NotificationSpotlightEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
