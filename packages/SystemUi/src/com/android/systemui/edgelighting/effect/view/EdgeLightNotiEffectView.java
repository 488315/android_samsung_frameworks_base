package com.android.systemui.edgelighting.effect.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EdgeLightNotiEffectView extends AbsEdgeLightingMaskView {
    public final String TAG;
    public final boolean mBasicLighting;
    public final HandlerC13251 mHandler;

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView$1] */
    public EdgeLightNotiEffectView(Context context) {
        super(context);
        this.TAG = "EdgeLightNotiEffectView";
        this.mBasicLighting = isSupportFrameEffect();
        this.mHandler = new Handler(getContext().getMainLooper()) { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView.1
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                EdgeLightNotiEffectView.this.resetImageDrawable();
            }
        };
    }

    public static final boolean isSupportFrameEffect() {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_EDGELIGHTING_FRAME_EFFECT");
        return string != null && string.contains("frame_effect");
    }

    public final void hide() {
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder(" hide : "), this.mIsAnimating, this.TAG);
        if (this.mIsAnimating) {
            AbsEdgeLightingMaskView.changeRingImageAlpha(this.mContainer, 0.0f, 600L);
            AnimatorSet animatorSet = this.mAnimationSet;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.mIsAnimating = false;
            sendEmptyMessageDelayed(1, 500L);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView
    public final void init() {
        if (!isSupportFrameEffect()) {
            this.mMaskingEdgeArea = false;
        }
        super.init();
        this.mStrokeWidth = this.mStrokeWidth;
        invalidate();
        AbsEdgeLightingMaskView.changeRingImageAlpha(this.mContainer, 0.0f, 200L);
        this.mRotateDuration = 3000L;
    }

    public final void setImageDrawable() {
        ImageView imageView = this.mTopLayer;
        if (imageView != null && imageView.getDrawable() == null) {
            this.mTopLayer.setImageResource(R.drawable.noti_basic_gradient);
        }
        ImageView imageView2 = this.mBottomLayer;
        if (imageView2 == null || imageView2.getDrawable() != null) {
            return;
        }
        this.mBottomLayer.setImageResource(R.drawable.noti_basic_gradient);
    }

    public final void show() {
        if (!this.mBasicLighting || this.mIsNoFrame) {
            Log.i(this.TAG, "Basic lighting is not supported.");
            return;
        }
        if (this.mIsAnimating) {
            Log.i(this.TAG, " Already animating ");
            return;
        }
        removeMessages(1);
        this.mIsAnimating = true;
        setImageDrawable();
        startRotation(this.mRotateDuration);
        AbsEdgeLightingMaskView.changeRingImageAlpha(this.mContainer, this.mStrokeAlpha, 200L);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView$1] */
    public EdgeLightNotiEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "EdgeLightNotiEffectView";
        this.mBasicLighting = isSupportFrameEffect();
        this.mHandler = new Handler(getContext().getMainLooper()) { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView.1
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                EdgeLightNotiEffectView.this.resetImageDrawable();
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView$1] */
    public EdgeLightNotiEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "EdgeLightNotiEffectView";
        this.mBasicLighting = isSupportFrameEffect();
        this.mHandler = new Handler(getContext().getMainLooper()) { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView.1
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                EdgeLightNotiEffectView.this.resetImageDrawable();
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView$1] */
    public EdgeLightNotiEffectView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "EdgeLightNotiEffectView";
        this.mBasicLighting = isSupportFrameEffect();
        this.mHandler = new Handler(getContext().getMainLooper()) { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView.1
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                EdgeLightNotiEffectView.this.resetImageDrawable();
            }
        };
    }
}
