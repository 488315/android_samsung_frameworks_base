package android.graphics.drawable;

import android.graphics.Rect;

/* loaded from: classes.dex */
abstract class RippleComponent {
    protected final Rect mBounds;
    protected float mDensityScale;
    private boolean mHasMaxRadius;
    protected final RippleDrawable mOwner;
    protected float mTargetRadius;

    protected void onTargetRadiusChanged(float f) {
    }

    public RippleComponent(RippleDrawable rippleDrawable, Rect rect) {
        this.mOwner = rippleDrawable;
        this.mBounds = rect;
    }

    public void onBoundsChange() {
        if (this.mHasMaxRadius) {
            return;
        }
        float targetRadius = getTargetRadius(this.mBounds);
        this.mTargetRadius = targetRadius;
        onTargetRadiusChanged(targetRadius);
    }

    public final void setup(float f, int i) {
        if (f >= 0.0f) {
            this.mHasMaxRadius = true;
            this.mTargetRadius = f;
        } else {
            this.mTargetRadius = getTargetRadius(this.mBounds);
        }
        this.mDensityScale = i * 0.00625f;
        onTargetRadiusChanged(this.mTargetRadius);
    }

    private static float getTargetRadius(Rect rect) {
        float width = rect.width() / 2.0f;
        float height = rect.height() / 2.0f;
        return (float) Math.sqrt((width * width) + (height * height));
    }

    public void getBounds(Rect rect) {
        int ceil = (int) Math.ceil(this.mTargetRadius);
        int i = -ceil;
        rect.set(i, i, ceil, ceil);
    }

    protected final void invalidateSelf() {
        this.mOwner.invalidateSelf(false);
    }

    protected final void onHotspotBoundsChanged() {
        if (this.mHasMaxRadius) {
            return;
        }
        float targetRadius = getTargetRadius(this.mBounds);
        this.mTargetRadius = targetRadius;
        onTargetRadiusChanged(targetRadius);
    }
}
