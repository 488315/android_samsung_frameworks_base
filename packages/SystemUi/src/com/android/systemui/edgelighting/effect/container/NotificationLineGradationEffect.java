package com.android.systemui.edgelighting.effect.container;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView;
import com.android.systemui.edgelighting.effect.view.GradientMultiLineEffectView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NotificationLineGradationEffect extends NotificationEffect {
    public GradientMultiLineEffectView mGradationEffect;

    public NotificationLineGradationEffect(Context context) {
        super(context);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void dismiss() {
        super.dismiss();
        this.mGradationEffect.hide();
        this.mHandler.sendEmptyMessageDelayed(3, 500L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void init() {
        super.init();
        GradientMultiLineEffectView gradientMultiLineEffectView = new GradientMultiLineEffectView(getContext());
        this.mGradationEffect = gradientMultiLineEffectView;
        int i = this.mScreenWidth;
        int i2 = this.mScreenHeight;
        gradientMultiLineEffectView.mWidth = i;
        gradientMultiLineEffectView.mHeight = i2;
        gradientMultiLineEffectView.setImageDrawable();
        gradientMultiLineEffectView.expandViewSize(gradientMultiLineEffectView.mTopLayer);
        gradientMultiLineEffectView.expandViewSize(gradientMultiLineEffectView.mBottomLayer);
        gradientMultiLineEffectView.expandViewSize(gradientMultiLineEffectView.mMidLayer);
        addView(this.mGradationEffect, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void requestHideEffectView() {
        this.mGradationEffect.hide();
        this.mHandler.sendEmptyMessageDelayed(3, 500L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEdgeEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        super.setEdgeEffectInfo(edgeEffectInfo);
        float f = edgeEffectInfo.mStrokeWidth;
        if (f > 0.0f) {
            if (edgeEffectInfo.mWidthDepth >= 0) {
                this.mGradationEffect.mStrokeWidth = f;
            } else {
                this.mGradationEffect.mStrokeWidth = f;
            }
        }
        float f2 = edgeEffectInfo.mStrokeAlpha;
        if (f2 > 0.0f) {
            this.mGradationEffect.mStrokeAlpha = f2;
        }
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setEffectColors(int[] iArr) {
        super.setEffectColors(iArr);
        GradientMultiLineEffectView gradientMultiLineEffectView = this.mGradationEffect;
        gradientMultiLineEffectView.mMainColor = this.mConvertColor;
        gradientMultiLineEffectView.mMainLayer.setBackgroundColor(0);
        Color.colorToHSV(gradientMultiLineEffectView.mMainColor, gradientMultiLineEffectView.mHsvColors);
        float[] fArr = gradientMultiLineEffectView.mHsvColors;
        float f = fArr[1];
        if (f <= 0.15d && fArr[2] >= 0.9f) {
            gradientMultiLineEffectView.mTopLayer.setColorFilter(-1639683);
            gradientMultiLineEffectView.mMidLayer.setColorFilter(-2429460);
            gradientMultiLineEffectView.mBottomLayer.setColorFilter(-2493465);
            Log.i(gradientMultiLineEffectView.TAG, " White color : H : " + gradientMultiLineEffectView.mHsvColors[0] + " S : " + gradientMultiLineEffectView.mHsvColors[1] + " B : " + gradientMultiLineEffectView.mHsvColors[2]);
            return;
        }
        if (0.0f <= f && f <= 1.0f && fArr[2] <= 0.1f) {
            gradientMultiLineEffectView.mTopLayer.setColorFilter(-12696757);
            gradientMultiLineEffectView.mMidLayer.setColorFilter(-11384745);
            gradientMultiLineEffectView.mBottomLayer.setColorFilter(-11708070);
            Log.i(gradientMultiLineEffectView.TAG, " Black color : H : " + gradientMultiLineEffectView.mHsvColors[0] + " S : " + gradientMultiLineEffectView.mHsvColors[1] + " B : " + gradientMultiLineEffectView.mHsvColors[2]);
            return;
        }
        float f2 = fArr[0];
        if (f2 >= 150.0f && f2 <= 360.0f) {
            fArr[0] = f2 - 60.0f;
            if (f > 0.8f) {
                fArr[1] = 0.8f;
            }
            if (fArr[2] > 0.8f) {
                fArr[2] = 0.8f;
            }
        } else if (f2 <= 10.0f || f2 >= 150.0f) {
            fArr[0] = f2 + 40.0f;
            fArr[1] = f + 0.3f;
            fArr[2] = fArr[2] + 0.3f;
        } else {
            fArr[0] = f2 + 40.0f;
            if (f > 0.8f) {
                fArr[1] = 0.8f;
            }
            if (fArr[2] > 0.8f) {
                fArr[2] = 0.8f;
            }
        }
        if (gradientMultiLineEffectView.mColorType == 2) {
            gradientMultiLineEffectView.mMidLayer.setColorFilter(Color.HSVToColor(fArr));
            float[] fArr2 = gradientMultiLineEffectView.mHsvColors;
            float f3 = fArr2[0];
            if (f3 > 80.0f && f3 <= 360.0f) {
                fArr2[0] = f3 - 60.0f;
            } else if (f3 <= 10.0f || f3 > 80.0f) {
                fArr2[0] = f3 + 60.0f;
            } else {
                fArr2[0] = f3 + 120.0f;
            }
            fArr2[1] = fArr2[1] - 0.2f;
            if (fArr2[2] > 0.8f) {
                fArr2[2] = 0.8f;
            }
            fArr2[1] = 0.3f;
        }
        gradientMultiLineEffectView.mBottomLayer.setColorFilter(Color.HSVToColor(gradientMultiLineEffectView.mHsvColors));
        gradientMultiLineEffectView.mTopLayer.setColorFilter(gradientMultiLineEffectView.mMainColor);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void setIsMultiResolutionSupoorted(boolean z) {
        this.mGradationEffect.mIsMultiResolutionSupoorted = z;
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void show() {
        super.show();
        GradientMultiLineEffectView gradientMultiLineEffectView = this.mGradationEffect;
        if (gradientMultiLineEffectView.mIsAnimating) {
            return;
        }
        gradientMultiLineEffectView.mIsAnimating = true;
        gradientMultiLineEffectView.setAlpha(1.0f);
        gradientMultiLineEffectView.setImageDrawable();
        gradientMultiLineEffectView.mRotateDuration = 3000L;
        gradientMultiLineEffectView.startRotation(3000L);
        AbsEdgeLightingMaskView.changeRingImageAlpha(gradientMultiLineEffectView.mMultiLineEffectContainer, gradientMultiLineEffectView.mStrokeAlpha, 300L);
    }

    @Override // com.android.systemui.edgelighting.effect.container.NotificationEffect
    public final void update() {
        GradientMultiLineEffectView gradientMultiLineEffectView = this.mGradationEffect;
        if (gradientMultiLineEffectView != null) {
            gradientMultiLineEffectView.updateEffectAlpha();
            this.mGradationEffect.invalidate();
        }
    }

    public NotificationLineGradationEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NotificationLineGradationEffect(Context context, int i) {
        super(context);
        GradientMultiLineEffectView gradientMultiLineEffectView = this.mGradationEffect;
        if (gradientMultiLineEffectView != null) {
            gradientMultiLineEffectView.mColorType = i;
            if (i == 1) {
                gradientMultiLineEffectView.mMidLayer.setVisibility(8);
            } else {
                gradientMultiLineEffectView.mMidLayer.setVisibility(0);
            }
        }
    }
}
