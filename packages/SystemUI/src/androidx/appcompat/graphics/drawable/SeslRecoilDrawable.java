package androidx.appcompat.graphics.drawable;

import android.R;
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
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import androidx.appcompat.R$styleable;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SeslRecoilDrawable extends LayerDrawable {
    public static final Interpolator PRESS_INTERPOLATOR = new LinearInterpolator();
    public static final Interpolator RELEASE_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
    public final ValueAnimator mAnimator;
    public float mHotspotPointX;
    public float mHotspotPointY;
    public boolean mIsActive;
    public boolean mIsPressed;
    public RecyclerView.ItemBackgroundHolder.AnonymousClass1 mListener;
    public long mPressDuration;
    public int mRadius;
    public long mReleaseDuration;
    public int mTintColor;

    public SeslRecoilDrawable() {
        super(new Drawable[0]);
        this.mIsActive = false;
        this.mIsPressed = false;
        this.mAnimator = ValueAnimator.ofFloat(0.0f);
        this.mListener = null;
        init();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0039 A[PHI: r4
      0x0039: PHI (r4v7 int) = (r4v2 int), (r4v5 int) binds: [B:8:0x0037, B:11:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(Canvas canvas) {
        float fHeight;
        int saveCount = canvas.getSaveCount();
        if (getNumberOfLayers() <= 0) {
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
            int iHeight = this.mRadius;
            if (iHeight > 0) {
                fHeight = iHeight;
                canvas.drawCircle(0.0f, 0.0f, fHeight, paint);
                canvas.translate(-fCenterX, -fCenterY);
            } else {
                Rect rect2 = new Rect();
                getHotspotBounds(rect2);
                iHeight = rect2.height() / 2;
                if (iHeight <= 0) {
                    fHeight = getBounds().height() / 2;
                }
                canvas.drawCircle(0.0f, 0.0f, fHeight, paint);
                canvas.translate(-fCenterX, -fCenterY);
            }
        } else {
            super.draw(canvas);
        }
        canvas.restoreToCount(saveCount);
    }

    public final int getAnimatingTintColor() {
        return ColorUtils.setAlphaComponent(this.mTintColor, (int) (((Float) this.mAnimator.getAnimatedValue()).floatValue() * Color.valueOf(this.mTintColor).alpha() * 255.0f));
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return null;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final boolean hasFocusStateSpecified() {
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R$styleable.SeslRecoil);
        try {
            try {
                updateStateFromTypedArray(typedArrayObtainAttributes);
            } catch (XmlPullParserException e) {
                Log.e("SeslRecoilDrawable", "Failed to parse!!", e);
            }
            super.inflate(resources, xmlPullParser, attributeSet, theme);
            Drawable drawableFindDrawableByLayerId = findDrawableByLayerId(R.id.mask);
            if (drawableFindDrawableByLayerId != null) {
                drawableFindDrawableByLayerId.setTint(0);
                drawableFindDrawableByLayerId.setTintBlendMode(BlendMode.SRC_IN);
            }
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    public final void init() {
        this.mPressDuration = 100L;
        this.mReleaseDuration = 350L;
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.appcompat.graphics.drawable.SeslRecoilDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SeslRecoilDrawable seslRecoilDrawable = this.f$0;
                Interpolator interpolator = SeslRecoilDrawable.PRESS_INTERPOLATOR;
                int animatingTintColor = seslRecoilDrawable.getAnimatingTintColor();
                Drawable drawableFindDrawableByLayerId = seslRecoilDrawable.findDrawableByLayerId(R.id.mask);
                if (drawableFindDrawableByLayerId != null) {
                    drawableFindDrawableByLayerId.setTint(animatingTintColor);
                } else {
                    seslRecoilDrawable.setTintBlendMode(BlendMode.HARD_LIGHT);
                    seslRecoilDrawable.setTint(animatingTintColor);
                }
                seslRecoilDrawable.invalidateSelf();
            }
        });
        setPaddingMode(1);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final boolean isProjected() {
        return getNumberOfLayers() <= 0;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void jumpToCurrentState() {
        super.jumpToCurrentState();
        if (this.mAnimator.isRunning()) {
            this.mAnimator.end();
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
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
        boolean z4 = z || z2 || z3;
        if (z3) {
            this.mIsPressed = true;
            startEnterAnimation(1.0f);
        } else if (z2) {
            startEnterAnimation(0.6f);
        } else if (z) {
            startEnterAnimation(0.8f);
        } else if (this.mIsActive && !z4) {
            if (this.mAnimator.isRunning()) {
                this.mAnimator.cancel();
            }
            this.mAnimator.setFloatValues(this.mIsPressed ? 1.0f : ((Float) this.mAnimator.getAnimatedValue()).floatValue(), 0.0f);
            this.mAnimator.setInterpolator(RELEASE_INTERPOLATOR);
            this.mAnimator.setDuration(this.mReleaseDuration);
            this.mAnimator.start();
            RecyclerView.ItemBackgroundHolder.AnonymousClass1 anonymousClass1 = this.mListener;
            if (anonymousClass1 != null) {
                RecyclerView.ItemBackgroundHolder itemBackgroundHolder = RecyclerView.ItemBackgroundHolder.this;
                SeslRecoilDrawable seslRecoilDrawable = itemBackgroundHolder.mActiveBg;
                if (seslRecoilDrawable.mListener != null) {
                    seslRecoilDrawable.mListener = null;
                }
                itemBackgroundHolder.mActiveBg = null;
            }
        }
        this.mIsActive = z4;
        this.mIsPressed = z3;
        return super.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
        this.mHotspotPointX = f;
        this.mHotspotPointY = f2;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void setTintBlendMode(BlendMode blendMode) {
        super.setTintBlendMode(blendMode);
        Drawable drawableFindDrawableByLayerId = findDrawableByLayerId(R.id.mask);
        if (drawableFindDrawableByLayerId != null) {
            drawableFindDrawableByLayerId.setTintBlendMode(BlendMode.SRC_IN);
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        Drawable drawableFindDrawableByLayerId = findDrawableByLayerId(R.id.mask);
        if (drawableFindDrawableByLayerId != null) {
            drawableFindDrawableByLayerId.setTint(getAnimatingTintColor());
        }
    }

    public final void startEnterAnimation(float f) {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        ValueAnimator valueAnimator = this.mAnimator;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), f);
        this.mAnimator.setInterpolator(PRESS_INTERPOLATOR);
        this.mAnimator.setDuration(this.mPressDuration);
        this.mAnimator.start();
    }

    public final void updateStateFromTypedArray(TypedArray typedArray) {
        Drawable drawable;
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            if (index == 0) {
                this.mTintColor = typedArray.getColor(index, 419430400);
            } else if (index == 2) {
                this.mRadius = typedArray.getDimensionPixelSize(index, -1);
            } else if (index == 1 && (drawable = typedArray.getDrawable(index)) != null) {
                setId(addLayer(drawable), R.id.mask);
            }
        }
    }

    public SeslRecoilDrawable(Drawable[] drawableArr) {
        super(drawableArr);
        this.mIsActive = false;
        this.mIsPressed = false;
        this.mAnimator = ValueAnimator.ofFloat(0.0f);
        this.mListener = null;
        init();
    }

    public SeslRecoilDrawable(int i, Drawable[] drawableArr, Drawable drawable) {
        this(drawableArr);
        init();
        this.mTintColor = i;
        if (drawable != null) {
            setId(addLayer(drawable), R.id.mask);
        }
    }
}
