package com.android.systemui.edgelighting.effect.container;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.EchoEffectView;
import java.util.ArrayList;

public final class NotificationEchoEffect extends NotificationEffect {
    public EchoEffectView mEchoEffectView;

    public NotificationEchoEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        this.mHandler.sendEmptyMessageDelayed(3, 700L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        EchoEffectView echoEffectView = this.mEchoEffectView;
        if (echoEffectView != null) {
            AnimatorSet animatorSet = echoEffectView.mAnimatorSet;
            if (animatorSet != null) {
                animatorSet.cancel();
                echoEffectView.mAnimatorSet = null;
            }
            echoEffectView.invalidate();
            this.mEchoEffectView.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        EchoEffectView echoEffectView = new EchoEffectView(getContext());
        this.mEchoEffectView = echoEffectView;
        echoEffectView.setVisibility(0);
        EchoEffectView echoEffectView2 = this.mEchoEffectView;
        int i = this.mScreenWidth;
        int i2 = this.mScreenHeight;
        echoEffectView2.mScreenWidth = i;
        echoEffectView2.mScreenHeight = i2;
        addView(this.mEchoEffectView, new RelativeLayout.LayoutParams(-1, -1));
        this.mEchoEffectView.setZ(-1.0f);
        EchoEffectView echoEffectView3 = this.mEchoEffectView;
        for (int i3 = 0; i3 < 3; i3++) {
            ArrayList arrayList = echoEffectView3.mLeftLine;
            View view = new View(echoEffectView3.getContext());
            view.setBackground(EchoEffectView.makeGradientBg(-256));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Math.round((echoEffectView3.mScreenWidth * 4.0f) / 1080.0f), (int) (echoEffectView3.mScreenHeight * echoEffectView3.LINE_HEIGHT[i3]));
            float f = (i3 * 10) + 1;
            layoutParams.setMarginStart(Math.round((echoEffectView3.mScreenWidth * f) / 1080.0f));
            layoutParams.addRule(15);
            view.setAlpha(0.0f);
            echoEffectView3.addView(view, layoutParams);
            arrayList.add(view);
            ArrayList arrayList2 = echoEffectView3.mRightLine;
            View view2 = new View(echoEffectView3.getContext());
            view2.setBackground(echoEffectView3.getResources().getDrawable(R.drawable.echo_line_bg, null));
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(Math.round((echoEffectView3.mScreenWidth * 4.0f) / 1080.0f), (int) (echoEffectView3.mScreenHeight * echoEffectView3.LINE_HEIGHT[i3]));
            layoutParams2.setMarginEnd(Math.round((echoEffectView3.mScreenWidth * f) / 1080.0f));
            layoutParams2.addRule(21);
            layoutParams2.addRule(15);
            view2.setAlpha(0.0f);
            echoEffectView3.addView(view2, layoutParams2);
            arrayList2.add(view2);
        }
        echoEffectView3.getClass();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void onFlickUpAnimation() {
        super.onFlickUpAnimation();
        EchoEffectView echoEffectView = this.mEchoEffectView;
        if (echoEffectView != null) {
            AnimatorSet animatorSet = echoEffectView.mAnimatorSet;
            if (animatorSet != null) {
                animatorSet.cancel();
                echoEffectView.mAnimatorSet = null;
            }
            echoEffectView.invalidate();
            this.mEchoEffectView.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        this.mEchoEffectView.setVisibility(4);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            this.mEchoEffectView.setAlpha(f);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        EchoEffectView echoEffectView = this.mEchoEffectView;
        if (echoEffectView != null) {
            int i = this.mConvertColor;
            for (int i2 = 0; i2 < 3; i2++) {
                ((View) echoEffectView.mRightLine.get(i2)).setBackground(EchoEffectView.makeGradientBg(i));
                ((View) echoEffectView.mLeftLine.get(i2)).setBackground(EchoEffectView.makeGradientBg(i));
            }
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        EchoEffectView echoEffectView;
        EchoEffectView echoEffectView2 = this.mEchoEffectView;
        if (echoEffectView2 != null) {
            AnimatorSet animatorSet = echoEffectView2.mAnimatorSet;
            if (animatorSet != null) {
                animatorSet.cancel();
                echoEffectView2.mAnimatorSet = null;
            }
            echoEffectView2.invalidate();
            this.mEchoEffectView.setVisibility(8);
        }
        if (!this.mIsShowMorphView && (echoEffectView = this.mEchoEffectView) != null) {
            echoEffectView.setVisibility(0);
            this.mEchoEffectView.startAnimation();
        }
        super.show();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void startToastPopupAnimation() {
        EchoEffectView echoEffectView = this.mEchoEffectView;
        if (echoEffectView != null) {
            echoEffectView.setVisibility(0);
            this.mEchoEffectView.startAnimation();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        EchoEffectView echoEffectView = this.mEchoEffectView;
        if (echoEffectView != null) {
            echoEffectView.setVisibility(0);
            this.mEchoEffectView.startAnimation();
        }
    }

    public NotificationEchoEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
