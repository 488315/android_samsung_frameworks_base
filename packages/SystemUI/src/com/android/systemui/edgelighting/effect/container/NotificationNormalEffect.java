package com.android.systemui.edgelighting.effect.container;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Slog;
import android.view.ViewGroup;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.EdgeLightNotiEffectView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NotificationNormalEffect extends NotificationEffect {
    public EdgeLightNotiEffectView mLightEffectView;
    public long mLightingDuration;

    public NotificationNormalEffect(Context context) {
        super(context);
        this.mLightingDuration = 3300L;
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        this.mLightEffectView.hide();
        this.mHandler.sendEmptyMessageDelayed(3, 500L);
    }

    public final void hideLightingEffect(long j) {
        if (this.mHandler.hasMessages(4)) {
            this.mHandler.removeMessages(4);
        }
        if (this.mInfiniteLighting) {
            Slog.i("NotificationNormalEffect", "hideLightingEffect infinite Noti Type ");
        } else {
            this.mHandler.sendEmptyMessageDelayed(4, j);
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public void init() {
        super.init();
        if (this.mLightEffectView == null) {
            EdgeLightNotiEffectView edgeLightNotiEffectView = new EdgeLightNotiEffectView(getContext());
            this.mLightEffectView = edgeLightNotiEffectView;
            int i = this.mScreenWidth;
            int i2 = this.mScreenHeight;
            edgeLightNotiEffectView.mWidth = i;
            edgeLightNotiEffectView.mHeight = i2;
            edgeLightNotiEffectView.setImageDrawable();
            edgeLightNotiEffectView.expandViewSize(edgeLightNotiEffectView.mTopLayer);
            edgeLightNotiEffectView.expandViewSize(edgeLightNotiEffectView.mBottomLayer);
            addView(this.mLightEffectView, new ViewGroup.LayoutParams(-1, -1));
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public void requestHideEffectView() {
        this.mLightEffectView.hide();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        this.mLightEffectView.setVisibility(0);
        float f = edgeEffectInfo.mStrokeWidth;
        if (f > 0.0f) {
            if (edgeEffectInfo.mWidthDepth >= 0) {
                this.mLightEffectView.mStrokeWidth = f;
            } else {
                this.mLightEffectView.mStrokeWidth = f;
            }
        }
        long j = edgeEffectInfo.mLightingDuration;
        if (j > 0) {
            this.mLightingDuration = j;
        }
        float f2 = edgeEffectInfo.mStrokeAlpha;
        if (f2 > 0.0f) {
            this.mLightEffectView.mStrokeAlpha = f2;
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        EdgeLightNotiEffectView edgeLightNotiEffectView = this.mLightEffectView;
        int i = this.mConvertColor;
        edgeLightNotiEffectView.mMainColor = i;
        Color.colorToHSV(i, edgeLightNotiEffectView.mHsvColors);
        float[] fArr = edgeLightNotiEffectView.mHsvColors;
        float f = fArr[1];
        if (f <= 0.15f && fArr[2] >= 0.85f) {
            edgeLightNotiEffectView.mMainColor = -1639683;
        } else if (0.0f <= f && f <= 1.0f && fArr[2] <= 0.15f) {
            edgeLightNotiEffectView.mMainColor = -12696757;
        }
        float f2 = fArr[2];
        fArr[2] = (0.2f * f2) + f2;
        edgeLightNotiEffectView.mBottomLayer.setColorFilter(Color.HSVToColor(fArr));
        Color.colorToHSV(edgeLightNotiEffectView.mMainColor, edgeLightNotiEffectView.mHsvColors);
        float[] fArr2 = edgeLightNotiEffectView.mHsvColors;
        float f3 = fArr2[2];
        fArr2[2] = f3 - (0.5f * f3);
        edgeLightNotiEffectView.mTopLayer.setColorFilter(Color.HSVToColor(fArr2));
        edgeLightNotiEffectView.mMainLayer.setBackgroundColor(edgeLightNotiEffectView.mMainColor);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setIsMultiResolutionSupoorted(boolean z) {
        this.mLightEffectView.mIsMultiResolutionSupoorted = z;
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        super.show();
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        if (!this.mInfiniteLighting) {
            hideLightingEffect(this.mLightingDuration);
        }
        this.mLightEffectView.show();
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public void update() {
        if (this.mLightEffectView.isRotateAnimating()) {
            EdgeLightNotiEffectView edgeLightNotiEffectView = this.mLightEffectView;
            edgeLightNotiEffectView.mContainer.setAlpha(edgeLightNotiEffectView.mStrokeAlpha);
            this.mLightEffectView.invalidate();
        } else {
            this.mLightEffectView.show();
        }
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        hideLightingEffect(this.mLightingDuration);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void updateText(boolean z) {
        super.updateText(z);
        if (z) {
            return;
        }
        if (!this.mLightEffectView.isRotateAnimating()) {
            this.mLightEffectView.show();
        }
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        hideLightingEffect(this.mLightingDuration);
    }

    public NotificationNormalEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLightingDuration = 3300L;
    }
}
