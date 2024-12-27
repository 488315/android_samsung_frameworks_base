package android.graphics.drawable;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;

import com.android.internal.R;
import com.android.internal.graphics.ColorUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SemRecoilDrawable extends LayerDrawable {
    private static final int DEFAULT_TINT_COLOR = 419430400;
    private static final int RADIUS_AUTO = -1;
    private final ValueAnimator mAnimator;
    private float mHotspotPointX;
    private float mHotspotPointY;
    private boolean mIsActive;
    private boolean mIsPressed;
    private Drawable mMask;
    private long mPressDuration;
    private int mRadius;
    private long mReleaseDuration;
    private int mTintColor;
    private static final String TAG = SemRecoilDrawable.class.getSimpleName();
    private static final Long PRESS_ANIMATION_DURATION = 100L;
    private static final Long RELEASE_ANIMATION_DURATION = 350L;
    private static final Interpolator PRESS_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator RELEASE_INTERPOLATOR =
            new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);

    public SemRecoilDrawable() {
        super(new Drawable[0]);
        this.mIsActive = false;
        this.mIsPressed = false;
        this.mAnimator = ValueAnimator.ofFloat(0.0f);
        init();
    }

    public SemRecoilDrawable(Drawable[] layers) {
        super(layers);
        this.mIsActive = false;
        this.mIsPressed = false;
        this.mAnimator = ValueAnimator.ofFloat(0.0f);
        init();
    }

    public SemRecoilDrawable(int color, Drawable[] layers, Drawable mask) {
        this(layers);
        init();
        this.mTintColor = color;
        if (mask != null) {
            this.mMask = mask;
            int maskIndex = addLayer(this.mMask);
            setId(maskIndex, 16908334);
        }
    }

    private void init() {
        this.mPressDuration = PRESS_ANIMATION_DURATION.longValue();
        this.mReleaseDuration = RELEASE_ANIMATION_DURATION.longValue();
        initAnimator();
        setPaddingMode(1);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] state) {
        boolean pressed = false;
        boolean focused = false;
        boolean hovered = false;
        for (int s : state) {
            switch (s) {
                case 16842908:
                    focused = true;
                    break;
                case 16842909:
                    break;
                case 16842910:
                    break;
                case 16842919:
                    pressed = true;
                    break;
                case 16843623:
                    hovered = true;
                    break;
            }
        }
        setActive(focused, hovered, pressed);
        return super.onStateChange(state);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setTintList(ColorStateList tint) {
        super.setTintList(tint);
        Drawable mask = findDrawableByLayerId(16908334);
        if (mask != null) {
            mask.setTint(getAnimatingTintColor());
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        super.setTintBlendMode(blendMode);
        Drawable mask = findDrawableByLayerId(16908334);
        if (mask != null) {
            mask.setTintBlendMode(BlendMode.SRC_IN);
        }
    }

    private void setActive(boolean focused, boolean hovered, boolean pressed) {
        boolean isActive = focused || hovered || pressed;
        if (pressed) {
            this.mIsPressed = true;
            startEnterAnimation(1.0f);
        } else if (hovered) {
            startEnterAnimation(0.6f);
        } else if (focused) {
            startEnterAnimation(0.8f);
        } else if (this.mIsActive && !isActive) {
            startExitAnimation();
        }
        this.mIsActive = isActive;
        this.mIsPressed = pressed;
    }

    private void initAnimator() {
        this.mAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() { // from class:
                    // android.graphics.drawable.SemRecoilDrawable$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SemRecoilDrawable.this.lambda$initAnimator$0(valueAnimator);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnimator$0(ValueAnimator animation) {
        setTint();
        invalidateSelf();
    }

    private void startEnterAnimation(float opacity) {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        this.mAnimator.setFloatValues(
                ((Float) this.mAnimator.getAnimatedValue()).floatValue(), opacity);
        this.mAnimator.setInterpolator(PRESS_INTERPOLATOR);
        this.mAnimator.setDuration(this.mPressDuration);
        this.mAnimator.start();
    }

    private void startExitAnimation() {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        float startValue = 1.0f;
        if (!this.mIsPressed) {
            startValue = ((Float) this.mAnimator.getAnimatedValue()).floatValue();
        }
        this.mAnimator.setFloatValues(startValue, 0.0f);
        this.mAnimator.setInterpolator(RELEASE_INTERPOLATOR);
        this.mAnimator.setDuration(this.mReleaseDuration);
        this.mAnimator.start();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void inflate(
            Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme)
            throws IOException, XmlPullParserException {
        TypedArray a = r.obtainAttributes(attrs, R.styleable.SemRecoil);
        try {
            try {
                updateStateFromTypedArray(a);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Failed to parse!!", e);
            }
            super.inflate(r, parser, attrs, theme);
            updateMaskLayer();
        } finally {
            a.recycle();
        }
    }

    private void updateStateFromTypedArray(TypedArray a) throws XmlPullParserException {
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == 0) {
                this.mTintColor = a.getColor(attr, 419430400);
            } else if (attr == 2) {
                this.mRadius = a.getDimensionPixelSize(attr, -1);
            } else if (attr == 1) {
                this.mMask = a.getDrawable(attr);
                if (this.mMask != null) {
                    int maskIndex = addLayer(this.mMask);
                    setId(maskIndex, 16908334);
                }
            }
        }
    }

    private boolean isDrawHotspot() {
        return getNumberOfLayers() <= 0;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return null;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int saveCount = canvas.getSaveCount();
        if (isDrawHotspot()) {
            drawHotspot(canvas);
        } else {
            super.draw(canvas);
        }
        canvas.restoreToCount(saveCount);
    }

    private void drawHotspot(Canvas canvas) {
        float cx = this.mHotspotPointX;
        float cy = this.mHotspotPointY;
        Rect hotspotRect = new Rect();
        getHotspotBounds(hotspotRect);
        if (hotspotRect.height() > 0) {
            cx = hotspotRect.centerX();
            cy = hotspotRect.centerY();
        }
        canvas.translate(cx, cy);
        Paint p = new Paint();
        p.setColor(getAnimatingTintColor());
        canvas.drawCircle(0.0f, 0.0f, getRadius(), p);
        canvas.translate(-cx, -cy);
    }

    private float getRadius() {
        if (this.mRadius > 0) {
            return this.mRadius;
        }
        Rect hotspotRect = new Rect();
        getHotspotBounds(hotspotRect);
        int hotSpotRadois = hotspotRect.height() / 2;
        if (hotSpotRadois > 0) {
            return hotSpotRadois;
        }
        return getBounds().height() / 2;
    }

    private void setTint() {
        int tintColor = getAnimatingTintColor();
        Drawable mask = findDrawableByLayerId(16908334);
        if (mask != null) {
            mask.setTint(tintColor);
        } else {
            setTintBlendMode(BlendMode.HARD_LIGHT);
            setTint(tintColor);
        }
    }

    private int getAnimatingTintColor() {
        float alpha =
                Color.valueOf(this.mTintColor).alpha()
                        * ((Float) this.mAnimator.getAnimatedValue()).floatValue();
        return ColorUtils.setAlphaComponent(this.mTintColor, (int) (255.0f * alpha));
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setHotspot(float x, float y) {
        super.setHotspot(x, y);
        this.mHotspotPointX = x;
        this.mHotspotPointY = y;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isProjected() {
        return isDrawHotspot();
    }

    private void updateMaskLayer() {
        Drawable mask = findDrawableByLayerId(16908334);
        if (mask != null) {
            mask.setTint(0);
            mask.setTintBlendMode(BlendMode.SRC_IN);
        }
    }

    public boolean isActive() {
        if (this.mIsActive) {
            return true;
        }
        return this.mAnimator.isRunning();
    }

    public void setCancel() {
        setState(new int[0]);
    }
}
