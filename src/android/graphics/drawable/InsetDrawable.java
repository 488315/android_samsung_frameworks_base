package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class InsetDrawable extends DrawableWrapper {
    private InsetState mState;
    private final Rect mTmpInsetRect;
    private final Rect mTmpRect;

    InsetDrawable() {
        this(new InsetState(null, null), (Resources) null);
    }

    public InsetDrawable(Drawable drawable, int i) {
        this(drawable, i, i, i, i);
    }

    public InsetDrawable(Drawable drawable, float f) {
        this(drawable, f, f, f, f);
    }

    public InsetDrawable(Drawable drawable, int i, int i2, int i3, int i4) {
        this(new InsetState(null, null), (Resources) null);
        this.mState.mInsetLeft = new InsetValue(0.0f, i);
        this.mState.mInsetTop = new InsetValue(0.0f, i2);
        this.mState.mInsetRight = new InsetValue(0.0f, i3);
        this.mState.mInsetBottom = new InsetValue(0.0f, i4);
        setDrawable(drawable);
    }

    public InsetDrawable(Drawable drawable, float f, float f2, float f3, float f4) {
        this(new InsetState(null, null), (Resources) null);
        this.mState.mInsetLeft = new InsetValue(f, 0);
        this.mState.mInsetTop = new InsetValue(f2, 0);
        this.mState.mInsetRight = new InsetValue(f3, 0);
        this.mState.mInsetBottom = new InsetValue(f4, 0);
        setDrawable(drawable);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.InsetDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        InsetState insetState = this.mState;
        if (insetState == null || insetState.mThemeAttrs == null) {
            return;
        }
        TypedArray resolveAttributes = theme.resolveAttributes(insetState.mThemeAttrs, R.styleable.InsetDrawable);
        try {
            updateStateFromTypedArray(resolveAttributes);
            verifyRequiredAttributes(resolveAttributes);
        } catch (XmlPullParserException e) {
            rethrowAsRuntimeException(e);
        } finally {
            resolveAttributes.recycle();
        }
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (getDrawable() == null) {
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <inset> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        InsetState insetState = this.mState;
        if (insetState == null) {
            return;
        }
        insetState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        insetState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValue(6)) {
            InsetValue inset = getInset(typedArray, 6, new InsetValue());
            insetState.mInsetLeft = inset;
            insetState.mInsetTop = inset;
            insetState.mInsetRight = inset;
            insetState.mInsetBottom = inset;
        }
        insetState.mInsetLeft = getInset(typedArray, 2, insetState.mInsetLeft);
        insetState.mInsetTop = getInset(typedArray, 4, insetState.mInsetTop);
        insetState.mInsetRight = getInset(typedArray, 3, insetState.mInsetRight);
        insetState.mInsetBottom = getInset(typedArray, 5, insetState.mInsetBottom);
    }

    private InsetValue getInset(TypedArray typedArray, int i, InsetValue insetValue) {
        if (typedArray.hasValue(i)) {
            TypedValue peekValue = typedArray.peekValue(i);
            if (peekValue.type == 6) {
                float fraction = peekValue.getFraction(1.0f, 1.0f);
                if (fraction >= 1.0f) {
                    throw new IllegalStateException("Fraction cannot be larger than 1");
                }
                return new InsetValue(fraction, 0);
            }
            int dimensionPixelOffset = typedArray.getDimensionPixelOffset(i, 0);
            if (dimensionPixelOffset != 0) {
                return new InsetValue(0.0f, dimensionPixelOffset);
            }
        }
        return insetValue;
    }

    private void getInsets(Rect rect) {
        Rect bounds = getBounds();
        rect.left = this.mState.mInsetLeft.getDimension(bounds.width());
        rect.right = this.mState.mInsetRight.getDimension(bounds.width());
        rect.top = this.mState.mInsetTop.getDimension(bounds.height());
        rect.bottom = this.mState.mInsetBottom.getDimension(bounds.height());
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        boolean padding = super.getPadding(rect);
        getInsets(this.mTmpInsetRect);
        rect.left += this.mTmpInsetRect.left;
        rect.right += this.mTmpInsetRect.right;
        rect.top += this.mTmpInsetRect.top;
        rect.bottom += this.mTmpInsetRect.bottom;
        if (padding) {
            return true;
        }
        return (this.mTmpInsetRect.bottom | ((this.mTmpInsetRect.left | this.mTmpInsetRect.right) | this.mTmpInsetRect.top)) != 0;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public Insets getOpticalInsets() {
        Insets opticalInsets = super.getOpticalInsets();
        getInsets(this.mTmpInsetRect);
        return Insets.of(opticalInsets.left + this.mTmpInsetRect.left, opticalInsets.top + this.mTmpInsetRect.top, opticalInsets.right + this.mTmpInsetRect.right, opticalInsets.bottom + this.mTmpInsetRect.bottom);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        int opacity = getDrawable().getOpacity();
        getInsets(this.mTmpInsetRect);
        if (opacity != -1 || (this.mTmpInsetRect.left <= 0 && this.mTmpInsetRect.top <= 0 && this.mTmpInsetRect.right <= 0 && this.mTmpInsetRect.bottom <= 0)) {
            return opacity;
        }
        return -3;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        Rect rect2 = this.mTmpRect;
        rect2.set(rect);
        rect2.left += this.mState.mInsetLeft.getDimension(rect.width());
        rect2.top += this.mState.mInsetTop.getDimension(rect.height());
        rect2.right -= this.mState.mInsetRight.getDimension(rect.width());
        rect2.bottom -= this.mState.mInsetBottom.getDimension(rect.height());
        super.onBoundsChange(rect2);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        int intrinsicWidth = getDrawable().getIntrinsicWidth();
        float f = this.mState.mInsetLeft.mFraction + this.mState.mInsetRight.mFraction;
        if (intrinsicWidth < 0 || f >= 1.0f) {
            return -1;
        }
        return ((int) (intrinsicWidth / (1.0f - f))) + this.mState.mInsetLeft.mDimension + this.mState.mInsetRight.mDimension;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        int intrinsicHeight = getDrawable().getIntrinsicHeight();
        float f = this.mState.mInsetTop.mFraction + this.mState.mInsetBottom.mFraction;
        if (intrinsicHeight < 0 || f >= 1.0f) {
            return -1;
        }
        return ((int) (intrinsicHeight / (1.0f - f))) + this.mState.mInsetTop.mDimension + this.mState.mInsetBottom.mDimension;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        getDrawable().getOutline(outline);
    }

    @Override // android.graphics.drawable.DrawableWrapper
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        InsetState insetState = new InsetState(this.mState, null);
        this.mState = insetState;
        return insetState;
    }

    static final class InsetState extends DrawableWrapper.DrawableWrapperState {
        InsetValue mInsetBottom;
        InsetValue mInsetLeft;
        InsetValue mInsetRight;
        InsetValue mInsetTop;
        private int[] mThemeAttrs;

        InsetState(InsetState insetState, Resources resources) {
            super(insetState, resources);
            if (insetState != null) {
                this.mInsetLeft = insetState.mInsetLeft.m1397clone();
                this.mInsetTop = insetState.mInsetTop.m1397clone();
                this.mInsetRight = insetState.mInsetRight.m1397clone();
                this.mInsetBottom = insetState.mInsetBottom.m1397clone();
                if (insetState.mDensity != this.mDensity) {
                    applyDensityScaling(insetState.mDensity, this.mDensity);
                    return;
                }
                return;
            }
            this.mInsetLeft = new InsetValue();
            this.mInsetTop = new InsetValue();
            this.mInsetRight = new InsetValue();
            this.mInsetBottom = new InsetValue();
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState
        void onDensityChanged(int i, int i2) {
            super.onDensityChanged(i, i2);
            applyDensityScaling(i, i2);
        }

        private void applyDensityScaling(int i, int i2) {
            this.mInsetLeft.scaleFromDensity(i, i2);
            this.mInsetTop.scaleFromDensity(i, i2);
            this.mInsetRight.scaleFromDensity(i, i2);
            this.mInsetBottom.scaleFromDensity(i, i2);
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            if (resources != null) {
                int i = resources.getDisplayMetrics().densityDpi;
                if (i == 0) {
                    i = 160;
                }
                if (i != this.mDensity) {
                    this = new InsetState(this, resources);
                }
            }
            return new InsetDrawable(this, resources);
        }
    }

    static final class InsetValue implements Cloneable {
        int mDimension;
        final float mFraction;

        public InsetValue() {
            this(0.0f, 0);
        }

        public InsetValue(float f, int i) {
            this.mFraction = f;
            this.mDimension = i;
        }

        int getDimension(int i) {
            return ((int) (i * this.mFraction)) + this.mDimension;
        }

        void scaleFromDensity(int i, int i2) {
            int i3 = this.mDimension;
            if (i3 != 0) {
                this.mDimension = Bitmap.scaleFromDensity(i3, i, i2);
            }
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public InsetValue m1397clone() {
            return new InsetValue(this.mFraction, this.mDimension);
        }
    }

    private InsetDrawable(InsetState insetState, Resources resources) {
        super(insetState, resources);
        this.mTmpRect = new Rect();
        this.mTmpInsetRect = new Rect();
        this.mState = insetState;
    }
}
