package android.view;

import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.animation.RenderNodeAnimator;

/* loaded from: classes4.dex */
public class RenderNodeAnimator extends android.graphics.animation.RenderNodeAnimator implements RenderNodeAnimator.ViewListener {
    private View mViewTarget;

    public RenderNodeAnimator(int i, float f) {
        super(i, f);
    }

    public RenderNodeAnimator(CanvasProperty<Float> canvasProperty, float f) {
        super(canvasProperty, f);
    }

    public RenderNodeAnimator(CanvasProperty<Paint> canvasProperty, int i, float f) {
        super(canvasProperty, i, f);
    }

    public RenderNodeAnimator(int i, int i2, float f, float f2) {
        super(i, i2, f, f2);
    }

    @Override // android.graphics.animation.RenderNodeAnimator.ViewListener
    public void onAlphaAnimationStart(float f) {
        this.mViewTarget.ensureTransformationInfo();
        this.mViewTarget.setAlphaInternal(f);
    }

    @Override // android.graphics.animation.RenderNodeAnimator.ViewListener
    public void invalidateParent(boolean z) {
        this.mViewTarget.invalidateViewProperty(true, false);
    }

    public void setTarget(View view) {
        this.mViewTarget = view;
        setViewListener(this);
        setTarget(this.mViewTarget.mRenderNode);
    }
}
