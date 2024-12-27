package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
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

    private ColorStateListDrawable(ColorStateListDrawableState state) {
        this.mMutated = false;
        this.mState = state;
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
    public void applyTheme(Resources.Theme t) {
        super.applyTheme(t);
        if (this.mState.mColor != null) {
            setColorStateList(this.mState.mColor.obtainForTheme(t));
        }
        if (this.mState.mTint != null) {
            setTintList(this.mState.mTint.obtainForTheme(t));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return super.canApplyTheme() || this.mState.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mState.mAlpha = alpha;
        onStateChange(getState());
    }

    public void clearAlpha() {
        this.mState.mAlpha = -1;
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList tint) {
        this.mState.mTint = tint;
        this.mColorDrawable.setTintList(tint);
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
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mColorDrawable.setBounds(bounds);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] state) {
        if (this.mState.mColor == null) {
            return false;
        }
        int color =
                this.mState.mColor.getColorForState(state, this.mState.mColor.getDefaultColor());
        if (this.mState.mAlpha != -1) {
            color = (16777215 & color) | (MathUtils.constrain(this.mState.mAlpha, 0, 255) << 24);
        }
        if (color != this.mColorDrawable.getColor()) {
            this.mColorDrawable.setColor(color);
            this.mColorDrawable.setState(state);
            return true;
        }
        return this.mColorDrawable.setState(state);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable who) {
        if (who == this.mColorDrawable && getCallback() != null) {
            getCallback().invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (who == this.mColorDrawable && getCallback() != null) {
            getCallback().scheduleDrawable(this, what, when);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (who == this.mColorDrawable && getCallback() != null) {
            getCallback().unscheduleDrawable(this, what);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations |=
                getChangingConfigurations() & (~this.mState.getChangingConfigurations());
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
        return super.getChangingConfigurations() | this.mState.getChangingConfigurations();
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

        ColorStateListDrawableState(ColorStateListDrawableState state) {
            this.mColor = null;
            this.mTint = null;
            this.mAlpha = -1;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mChangingConfigurations = 0;
            this.mColor = state.mColor;
            this.mTint = state.mTint;
            this.mAlpha = state.mAlpha;
            this.mBlendMode = state.mBlendMode;
            this.mChangingConfigurations = state.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ColorStateListDrawable(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations
                    | (this.mColor != null ? this.mColor.getChangingConfigurations() : 0)
                    | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }

        public boolean isStateful() {
            return (this.mColor != null && this.mColor.isStateful())
                    || (this.mTint != null && this.mTint.isStateful());
        }

        public boolean hasFocusStateSpecified() {
            return (this.mColor != null && this.mColor.hasFocusStateSpecified())
                    || (this.mTint != null && this.mTint.hasFocusStateSpecified());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return (this.mColor != null && this.mColor.canApplyTheme())
                    || (this.mTint != null && this.mTint.canApplyTheme());
        }
    }

    private void initializeColorDrawable() {
        this.mColorDrawable = new ColorDrawable();
        this.mColorDrawable.setCallback(this);
        if (this.mState.mTint != null) {
            this.mColorDrawable.setTintList(this.mState.mTint);
        }
        if (this.mState.mBlendMode != DEFAULT_BLEND_MODE) {
            this.mColorDrawable.setTintBlendMode(this.mState.mBlendMode);
        }
    }
}
