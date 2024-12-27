package com.android.systemui.edgelighting.plus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.android.systemui.edgelighting.effect.container.NotificationEffect;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;

public final class NotificationELPlusEffect extends NotificationEffect {
    public final EdgeEffectInfo mEdgeEffectInfo;
    public final Bundle mEmitterItemInfo;
    public boolean mIsUsedAppIconForEdgeLightingPlus;
    public final String mPackageName;
    public EdgeLightingPlusEffectView mParticleView;

    public NotificationELPlusEffect(Context context, Bundle bundle, EdgeEffectInfo edgeEffectInfo) {
        super(context);
        this.mIsUsedAppIconForEdgeLightingPlus = false;
        this.mEmitterItemInfo = bundle;
        this.mEdgeEffectInfo = edgeEffectInfo;
        this.mPackageName = edgeEffectInfo.mPackageName;
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        this.mHandler.sendEmptyMessageDelayed(3, 500L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        fadeOutAnimator();
    }

    public final void fadeOutAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mParticleView, "alpha", 1.0f, 0.0f);
        ofFloat.setDuration(500L);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.edgelighting.plus.NotificationELPlusEffect.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                NotificationELPlusEffect.this.mParticleView.setVisibility(8);
            }
        });
        ofFloat.start();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        EdgeLightingPlusEffectView edgeLightingPlusEffectView = new EdgeLightingPlusEffectView(getContext());
        this.mParticleView = edgeLightingPlusEffectView;
        edgeLightingPlusEffectView.setVisibility(0);
        EdgeLightingPlusEffectView edgeLightingPlusEffectView2 = this.mParticleView;
        edgeLightingPlusEffectView2.listener = new NotificationELPlusEffect$$ExternalSyntheticLambda0(this);
        edgeLightingPlusEffectView2.setZ(-1.0f);
        this.mNotificationContainer.addView(this.mParticleView, new RelativeLayout.LayoutParams(-1, -1));
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        fadeOutAnimator();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        int[] iArr = {this.mEmitterItemInfo.getInt("color")};
        if (this.mEmitterItemInfo.getBoolean("isUsedAutoColor")) {
            int loadAppCustomColor = EdgeLightingSettingUtils.loadAppCustomColor(getContext(), this.mPackageName);
            if (loadAppCustomColor == 0) {
                loadAppCustomColor = EdgeLightingPolicyManager.getInstance(getContext(), false).getEdgeLightingColor(getContext(), this.mPackageName);
            }
            int i = loadAppCustomColor | (-16777216);
            iArr[0] = i;
            this.mEmitterItemInfo.putInt("color", i);
        }
        setEffectColors(iArr);
    }
}
