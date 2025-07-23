package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class DrawableWrapper extends Drawable implements Drawable.Callback {
    private Drawable mDrawable;
    private boolean mMutated;
    private DrawableWrapperState mState;

    DrawableWrapper(DrawableWrapperState drawableWrapperState, Resources resources) {
        this.mState = drawableWrapperState;
        updateLocalState(resources);
    }

    public DrawableWrapper(Drawable drawable) {
        this.mState = null;
        setDrawable(drawable);
    }

    private void updateLocalState(Resources resources) {
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState == null || drawableWrapperState.mDrawableState == null) {
            return;
        }
        setDrawable(this.mState.mDrawableState.newDrawable(resources));
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(Xfermode xfermode) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setXfermode(xfermode);
        }
    }

    public void setDrawable(Drawable drawable) {
        Drawable drawable2 = this.mDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setVisible(isVisible(), true);
            drawable.setState(getState());
            drawable.setLevel(getLevel());
            drawable.setBounds(getBounds());
            drawable.setLayoutDirection(getLayoutDirection());
            DrawableWrapperState drawableWrapperState = this.mState;
            if (drawableWrapperState != null) {
                drawableWrapperState.mDrawableState = drawable.getConstantState();
            }
        }
        invalidateSelf();
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState == null) {
            return;
        }
        int i = resources.getDisplayMetrics().densityDpi;
        if (i == 0) {
            i = 160;
        }
        drawableWrapperState.setDensity(i);
        drawableWrapperState.mSrcDensityOverride = this.mSrcDensityOverride;
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.DrawableWrapper);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        inflateChildDrawable(resources, xmlPullParser, attributeSet, theme);
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        Drawable drawable = this.mDrawable;
        if (drawable != null && drawable.canApplyTheme()) {
            this.mDrawable.applyTheme(theme);
        }
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState == null) {
            return;
        }
        int i = theme.getResources().getDisplayMetrics().densityDpi;
        if (i == 0) {
            i = 160;
        }
        drawableWrapperState.setDensity(i);
        if (drawableWrapperState.mThemeAttrs != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(drawableWrapperState.mThemeAttrs, R.styleable.DrawableWrapper);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState == null) {
            return;
        }
        drawableWrapperState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        drawableWrapperState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValueOrEmpty(0)) {
            setDrawable(typedArray.getDrawable(0));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        DrawableWrapperState drawableWrapperState = this.mState;
        return (drawableWrapperState != null && drawableWrapperState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        DrawableWrapperState drawableWrapperState = this.mState;
        return this.mDrawable.getChangingConfigurations() | changingConfigurations | (drawableWrapperState != null ? drawableWrapperState.getChangingConfigurations() : 0);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public Insets getOpticalInsets() {
        Drawable drawable = this.mDrawable;
        return drawable != null ? drawable.getOpticalInsets() : Insets.NONE;
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setHotspotBounds(i, i2, i3, i4);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void getHotspotBounds(Rect rect) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.getHotspotBounds(rect);
        } else {
            rect.set(getBounds());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        Drawable drawable = this.mDrawable;
        return (drawable != null && drawable.setVisible(z, z2)) | visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getAlpha();
        }
        return 255;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            return drawable.getColorFilter();
        }
        return super.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setTintList(colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setTintBlendMode(blendMode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.setLayoutDirection(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        Drawable drawable = this.mDrawable;
        if (drawable == null || !drawable.isStateful()) {
            return false;
        }
        boolean state = this.mDrawable.setState(iArr);
        if (state) {
            onBoundsChange(getBounds());
        }
        return state;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        Drawable drawable = this.mDrawable;
        return drawable != null && drawable.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.getOutline(outline);
        } else {
            super.getOutline(outline);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        DrawableWrapperState drawableWrapperState = this.mState;
        if (drawableWrapperState == null || !drawableWrapperState.canConstantState()) {
            return null;
        }
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = mutateConstantState();
            Drawable drawable = this.mDrawable;
            if (drawable != null) {
                drawable.mutate();
            }
            DrawableWrapperState drawableWrapperState = this.mState;
            if (drawableWrapperState != null) {
                Drawable drawable2 = this.mDrawable;
                drawableWrapperState.mDrawableState = drawable2 != null ? drawable2.getConstantState() : null;
            }
            this.mMutated = true;
        }
        return this;
    }

    DrawableWrapperState mutateConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.clearMutated();
        }
        this.mMutated = false;
    }

    private void inflateChildDrawable(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        Drawable drawable = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            } else if (next == 2) {
                drawable = Drawable.createFromXmlInnerForDensity(resources, xmlPullParser, attributeSet, this.mState.mSrcDensityOverride, theme);
            }
        }
        if (drawable != null) {
            setDrawable(drawable);
        }
    }

    static abstract class DrawableWrapperState extends Drawable.ConstantState {
        int mChangingConfigurations;
        int mDensity;
        Drawable.ConstantState mDrawableState;
        int mSrcDensityOverride;
        private int[] mThemeAttrs;

        @Override // android.graphics.drawable.Drawable.ConstantState
        public abstract Drawable newDrawable(Resources resources);

        void onDensityChanged(int i, int i2) {
        }

        DrawableWrapperState(DrawableWrapperState drawableWrapperState, Resources resources) {
            this.mDensity = 160;
            int i = 0;
            this.mSrcDensityOverride = 0;
            if (drawableWrapperState != null) {
                this.mThemeAttrs = drawableWrapperState.mThemeAttrs;
                this.mChangingConfigurations = drawableWrapperState.mChangingConfigurations;
                this.mDrawableState = drawableWrapperState.mDrawableState;
                this.mSrcDensityOverride = drawableWrapperState.mSrcDensityOverride;
            }
            if (resources != null) {
                i = resources.getDisplayMetrics().densityDpi;
            } else if (drawableWrapperState != null) {
                i = drawableWrapperState.mDensity;
            }
            this.mDensity = i != 0 ? i : 160;
        }

        public final void setDensity(int i) {
            int i2 = this.mDensity;
            if (i2 != i) {
                this.mDensity = i;
                onDensityChanged(i2, i);
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            Drawable.ConstantState constantState = this.mDrawableState;
            return (constantState != null && constantState.canApplyTheme()) || super.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return newDrawable(null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            Drawable.ConstantState constantState = this.mDrawableState;
            return (constantState != null ? constantState.getChangingConfigurations() : 0) | i;
        }

        public boolean canConstantState() {
            return this.mDrawableState != null;
        }
    }
}
