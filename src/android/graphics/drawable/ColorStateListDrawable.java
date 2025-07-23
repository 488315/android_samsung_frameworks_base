package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.MathUtils;

/* loaded from: classes.dex */
public class ColorStateListDrawable extends Drawable implements Drawable.Callback {
    private ColorDrawable mColorDrawable;
    private boolean mMutated;
    private ColorStateListDrawableState mState;

    public ColorStateListDrawable() {
        this.mMutated = false;
        this.mState = new ColorStateListDrawableState();
        initializeColorDrawable();
    }

    public ColorStateListDrawable(ColorStateList colorStateList) {
        this.mMutated = false;
        this.mState = new ColorStateListDrawableState();
        initializeColorDrawable();
        setColorStateList(colorStateList);
    }

    private ColorStateListDrawable(ColorStateListDrawableState colorStateListDrawableState) {
        this.mMutated = false;
        this.mState = colorStateListDrawableState;
        initializeColorDrawable();
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mColorDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mColorDrawable.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mState.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable getCurrent() {
        return this.mColorDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        if (this.mState.mColor != null) {
            setColorStateList(this.mState.mColor.obtainForTheme(theme));
        }
        if (this.mState.mTint != null) {
            setTintList(this.mState.mTint.obtainForTheme(theme));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return super.canApplyTheme() || this.mState.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mState.mAlpha = i;
        onStateChange(getState());
    }

    public void clearAlpha() {
        this.mState.mAlpha = -1;
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mState.mTint = colorStateList;
        this.mColorDrawable.setTintList(colorStateList);
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        this.mState.mBlendMode = blendMode;
        this.mColorDrawable.setTintBlendMode(blendMode);
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mColorDrawable.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mColorDrawable.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mColorDrawable.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        if (this.mState.mColor == null) {
            return false;
        }
        int colorForState = this.mState.mColor.getColorForState(iArr, this.mState.mColor.getDefaultColor());
        if (this.mState.mAlpha != -1) {
            colorForState = (colorForState & 16777215) | (MathUtils.constrain(this.mState.mAlpha, 0, 255) << 24);
        }
        if (colorForState != this.mColorDrawable.getColor()) {
            this.mColorDrawable.setColor(colorForState);
            this.mColorDrawable.setState(iArr);
            return true;
        }
        return this.mColorDrawable.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (drawable != this.mColorDrawable || getCallback() == null) {
            return;
        }
        getCallback().invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (drawable != this.mColorDrawable || getCallback() == null) {
            return;
        }
        getCallback().scheduleDrawable(this, runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (drawable != this.mColorDrawable || getCallback() == null) {
            return;
        }
        getCallback().unscheduleDrawable(this, runnable);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations |= getChangingConfigurations() & (~this.mState.getChangingConfigurations());
        return this.mState;
    }

    public ColorStateList getColorStateList() {
        if (this.mState.mColor == null) {
            return ColorStateList.valueOf(this.mColorDrawable.getColor());
        }
        return this.mState.mColor;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new ColorStateListDrawableState(this.mState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    public void setColorStateList(ColorStateList colorStateList) {
        this.mState.mColor = colorStateList;
        onStateChange(getState());
    }

    static final class ColorStateListDrawableState extends Drawable.ConstantState {
        int mAlpha;
        BlendMode mBlendMode;
        int mChangingConfigurations;
        ColorStateList mColor;
        ColorStateList mTint;

        ColorStateListDrawableState() {
            this.mColor = null;
            this.mTint = null;
            this.mAlpha = -1;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mChangingConfigurations = 0;
        }

        ColorStateListDrawableState(ColorStateListDrawableState colorStateListDrawableState) {
            this.mColor = null;
            this.mTint = null;
            this.mAlpha = -1;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mChangingConfigurations = 0;
            this.mColor = colorStateListDrawableState.mColor;
            this.mTint = colorStateListDrawableState.mTint;
            this.mAlpha = colorStateListDrawableState.mAlpha;
            this.mBlendMode = colorStateListDrawableState.mBlendMode;
            this.mChangingConfigurations = colorStateListDrawableState.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ColorStateListDrawable(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mColor;
            int changingConfigurations = i | (colorStateList != null ? colorStateList.getChangingConfigurations() : 0);
            ColorStateList colorStateList2 = this.mTint;
            return changingConfigurations | (colorStateList2 != null ? colorStateList2.getChangingConfigurations() : 0);
        }

        public boolean isStateful() {
            ColorStateList colorStateList = this.mColor;
            if (colorStateList != null && colorStateList.isStateful()) {
                return true;
            }
            ColorStateList colorStateList2 = this.mTint;
            return colorStateList2 != null && colorStateList2.isStateful();
        }

        public boolean hasFocusStateSpecified() {
            ColorStateList colorStateList = this.mColor;
            if (colorStateList != null && colorStateList.hasFocusStateSpecified()) {
                return true;
            }
            ColorStateList colorStateList2 = this.mTint;
            return colorStateList2 != null && colorStateList2.hasFocusStateSpecified();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            ColorStateList colorStateList = this.mColor;
            if (colorStateList != null && colorStateList.canApplyTheme()) {
                return true;
            }
            ColorStateList colorStateList2 = this.mTint;
            return colorStateList2 != null && colorStateList2.canApplyTheme();
        }
    }

    private void initializeColorDrawable() {
        ColorDrawable colorDrawable = new ColorDrawable();
        this.mColorDrawable = colorDrawable;
        colorDrawable.setCallback(this);
        if (this.mState.mTint != null) {
            this.mColorDrawable.setTintList(this.mState.mTint);
        }
        if (this.mState.mBlendMode != DEFAULT_BLEND_MODE) {
            this.mColorDrawable.setTintBlendMode(this.mState.mBlendMode);
        }
    }
}
