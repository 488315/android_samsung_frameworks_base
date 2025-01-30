package com.android.systemui.edgelighting.effect.container;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView;
import com.android.systemui.edgelighting.effect.view.LineSpreadEffectView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NotificationWaveEffect extends NotificationNormalEffect {
    public LineSpreadEffectView mLineEffect;

    public NotificationWaveEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismissToastPopup() {
        LineSpreadEffectView lineSpreadEffectView = this.mLineEffect;
        if (lineSpreadEffectView != null) {
            lineSpreadEffectView.stopLineAnimation();
            this.mLineEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void finishToastPopupAnimation() {
        LineSpreadEffectView lineSpreadEffectView = this.mLineEffect;
        if (lineSpreadEffectView != null) {
            lineSpreadEffectView.setVisibility(0);
            LineSpreadEffectView lineSpreadEffectView2 = this.mLineEffect;
            Rect rootRect = this.mMorphView.getRootRect();
            lineSpreadEffectView2.mCurRect.set(rootRect.left, rootRect.top, rootRect.right, rootRect.bottom);
            this.mLineEffect.startLineAnimation(this.mLightingDuration);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        LineSpreadEffectView lineSpreadEffectView = new LineSpreadEffectView(getContext());
        this.mLineEffect = lineSpreadEffectView;
        lineSpreadEffectView.setVisibility(0);
        addView(this.mLineEffect, 0, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void onFlickUpAnimation() {
        super.onFlickUpAnimation();
        LineSpreadEffectView lineSpreadEffectView = this.mLineEffect;
        if (lineSpreadEffectView != null) {
            lineSpreadEffectView.stopLineAnimation();
            this.mLineEffect.setVisibility(8);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        super.requestHideEffectView();
        this.mLineEffect.setVisibility(4);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeAlpha;
        if (f > 0.0f) {
            this.mLineEffect.setAlpha(f);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        LineSpreadEffectView lineSpreadEffectView = this.mLineEffect;
        if (lineSpreadEffectView != null) {
            int i = this.mConvertColor;
            lineSpreadEffectView.mColor = i;
            lineSpreadEffectView.mSquarePaint1.setColor(i);
            lineSpreadEffectView.mSquarePaint2.setColor(lineSpreadEffectView.mColor);
            lineSpreadEffectView.mSquarePaint3.setColor(lineSpreadEffectView.mColor);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        LineSpreadEffectView lineSpreadEffectView = this.mLineEffect;
        if (lineSpreadEffectView != null) {
            lineSpreadEffectView.stopLineAnimation();
            this.mLineEffect.setVisibility(8);
        }
        super.show();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationNormalEffect, com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        LineSpreadEffectView lineSpreadEffectView;
        super.update();
        EdgeLightNotiEffectView edgeLightNotiEffectView = this.mLightEffectView;
        if (edgeLightNotiEffectView == null || !edgeLightNotiEffectView.isRotateAnimating() || (lineSpreadEffectView = this.mLineEffect) == null) {
            return;
        }
        lineSpreadEffectView.setVisibility(0);
        LineSpreadEffectView lineSpreadEffectView2 = this.mLineEffect;
        Rect rootRect = this.mMorphView.getRootRect();
        lineSpreadEffectView2.mCurRect.set(rootRect.left, rootRect.top, rootRect.right, rootRect.bottom);
        this.mLineEffect.startLineAnimation(this.mLightingDuration);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void updateEffectLocation() {
        LineSpreadEffectView lineSpreadEffectView;
        if (this.mLightEffectView == null || (lineSpreadEffectView = this.mLineEffect) == null) {
            return;
        }
        lineSpreadEffectView.stopLineAnimation();
        this.mLineEffect.setVisibility(8);
    }

    public NotificationWaveEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
