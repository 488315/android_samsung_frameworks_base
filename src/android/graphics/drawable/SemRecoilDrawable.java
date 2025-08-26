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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.R;
import com.android.internal.graphics.ColorUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SemRecoilDrawable extends LayerDrawable {
    private static final int DEFAULT_TINT_COLOR = 419430400;
    private static final int RADIUS_AUTO = -1;
    private static final String TAG = "SemRecoilDrawable";
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
    private static final Long PRESS_ANIMATION_DURATION = 100L;
    private static final Long RELEASE_ANIMATION_DURATION = 350L;
    private static final Interpolator PRESS_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator RELEASE_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return null;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    public SemRecoilDrawable() {
        super(new Drawable[0]);
        this.mIsActive = false;
        this.mIsPressed = false;
        this.mAnimator = ValueAnimator.ofFloat(0.0f);
        init();
    }

    public SemRecoilDrawable(Drawable[] drawableArr) {
        super(drawableArr);
        this.mIsActive = false;
        this.mIsPressed = false;
        this.mAnimator = ValueAnimator.ofFloat(0.0f);
        init();
    }

    public SemRecoilDrawable(int i, Drawable[] drawableArr, Drawable drawable) {
        this(drawableArr);
        init();
        this.mTintColor = i;
        if (drawable != null) {
            this.mMask = drawable;
            setId(addLayer(drawable), 16908334);
        }
    }

    private void init() {
        this.mPressDuration = PRESS_ANIMATION_DURATION.longValue();
        this.mReleaseDuration = RELEASE_ANIMATION_DURATION.longValue();
        initAnimator();
        setPaddingMode(1);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (int i : iArr) {
            if (i == 16842908) {
                z = true;
            } else if (i == 16842919) {
                z3 = true;
            } else if (i == 16843623) {
                z2 = true;
            }
        }
        setActive(z, z2, z3);
        return super.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        Drawable drawableFindDrawableByLayerId = findDrawableByLayerId(16908334);
        if (drawableFindDrawableByLayerId != null) {
            drawableFindDrawableByLayerId.setTint(getAnimatingTintColor());
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        super.setTintBlendMode(blendMode);
        Drawable drawableFindDrawableByLayerId = findDrawableByLayerId(16908334);
        if (drawableFindDrawableByLayerId != null) {
            drawableFindDrawableByLayerId.setTintBlendMode(BlendMode.SRC_IN);
        }
    }

    private void setActive(boolean z, boolean z2, boolean z3) {
        boolean z4 = z || z2 || z3;
        if (z3) {
            this.mIsPressed = true;
            startEnterAnimation(1.0f);
        } else if (z2) {
            startEnterAnimation(0.6f);
        } else if (z) {
            startEnterAnimation(0.8f);
        } else if (this.mIsActive && !z4) {
            startExitAnimation();
        }
        this.mIsActive = z4;
        this.mIsPressed = z3;
    }

    private void initAnimator() {
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.SemRecoilDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$initAnimator$0(valueAnimator);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnimator$0(ValueAnimator valueAnimator) {
        setTint();
        invalidateSelf();
    }

    private void startEnterAnimation(float f) {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        ValueAnimator valueAnimator = this.mAnimator;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), f);
        this.mAnimator.setInterpolator(PRESS_INTERPOLATOR);
        this.mAnimator.setDuration(this.mPressDuration);
        this.mAnimator.start();
    }

    private void startExitAnimation() {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        this.mAnimator.setFloatValues(!this.mIsPressed ? ((Float) this.mAnimator.getAnimatedValue()).floatValue() : 1.0f, 0.0f);
        this.mAnimator.setInterpolator(RELEASE_INTERPOLATOR);
        this.mAnimator.setDuration(this.mReleaseDuration);
        this.mAnimator.start();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.SemRecoil);
        try {
            try {
                updateStateFromTypedArray(typedArrayObtainAttributes);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Failed to parse!!", e);
            }
            super.inflate(resources, xmlPullParser, attributeSet, theme);
            updateMaskLayer();
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) throws XmlPullParserException {
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            if (index == 0) {
                this.mTintColor = typedArray.getColor(index, 419430400);
            } else if (index == 2) {
                this.mRadius = typedArray.getDimensionPixelSize(index, -1);
            } else if (index == 1) {
                Drawable drawable = typedArray.getDrawable(index);
                this.mMask = drawable;
                if (drawable != null) {
                    setId(addLayer(drawable), 16908334);
                }
            }
        }
    }

    private boolean isDrawHotspot() {
        return getNumberOfLayers() <= 0;
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
        float fCenterX = this.mHotspotPointX;
        float fCenterY = this.mHotspotPointY;
        Rect rect = new Rect();
        getHotspotBounds(rect);
        if (rect.height() > 0) {
            fCenterX = rect.centerX();
            fCenterY = rect.centerY();
        }
        canvas.translate(fCenterX, fCenterY);
        Paint paint = new Paint();
        paint.setColor(getAnimatingTintColor());
        canvas.drawCircle(0.0f, 0.0f, getRadius(), paint);
        canvas.translate(-fCenterX, -fCenterY);
    }

    private float getRadius() {
        int i = this.mRadius;
        if (i > 0) {
            return i;
        }
        Rect rect = new Rect();
        getHotspotBounds(rect);
        int iHeight = rect.height() / 2;
        return iHeight > 0 ? iHeight : getBounds().height() / 2;
    }

    private void setTint() {
        int animatingTintColor = getAnimatingTintColor();
        Drawable drawableFindDrawableByLayerId = findDrawableByLayerId(16908334);
        if (drawableFindDrawableByLayerId != null) {
            drawableFindDrawableByLayerId.setTint(animatingTintColor);
        } else {
            setTintBlendMode(BlendMode.HARD_LIGHT);
            setTint(animatingTintColor);
        }
    }

    private int getAnimatingTintColor() {
        return ColorUtils.setAlphaComponent(this.mTintColor, (int) (Color.valueOf(this.mTintColor).alpha() * ((Float) this.mAnimator.getAnimatedValue()).floatValue() * 255.0f));
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
        this.mHotspotPointX = f;
        this.mHotspotPointY = f2;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isProjected() {
        return isDrawHotspot();
    }

    private void updateMaskLayer() {
        Drawable drawableFindDrawableByLayerId = findDrawableByLayerId(16908334);
        if (drawableFindDrawableByLayerId != null) {
            drawableFindDrawableByLayerId.setTint(0);
            drawableFindDrawableByLayerId.setTintBlendMode(BlendMode.SRC_IN);
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
