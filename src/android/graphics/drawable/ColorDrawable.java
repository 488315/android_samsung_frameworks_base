package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewDebug;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ColorDrawable extends Drawable {
    private BlendModeColorFilter mBlendModeColorFilter;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "state_")
    private ColorState mColorState;
    private boolean mMutated;
    private final Paint mPaint;

    public ColorDrawable() {
        this.mPaint = new Paint(1);
        this.mColorState = new ColorState();
    }

    public ColorDrawable(int i) {
        this.mPaint = new Paint(1);
        this.mColorState = new ColorState();
        setColor(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mColorState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mColorState = new ColorState(this.mColorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        ColorFilter colorFilter = this.mPaint.getColorFilter();
        if ((this.mColorState.mUseColor >>> 24) == 0 && colorFilter == null && this.mBlendModeColorFilter == null) {
            return;
        }
        if (colorFilter == null) {
            this.mPaint.setColorFilter(this.mBlendModeColorFilter);
        }
        this.mPaint.setColor(this.mColorState.mUseColor);
        canvas.drawRect(getBounds(), this.mPaint);
        this.mPaint.setColorFilter(colorFilter);
    }

    public int getColor() {
        return this.mColorState.mUseColor;
    }

    public void setColor(int i) {
        if (this.mColorState.mBaseColor == i && this.mColorState.mUseColor == i) {
            return;
        }
        ColorState colorState = this.mColorState;
        colorState.mUseColor = i;
        colorState.mBaseColor = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mColorState.mUseColor >>> 24;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        int i2 = ((((this.mColorState.mBaseColor >>> 24) * (i + (i >> 7))) >> 8) << 24) | ((this.mColorState.mBaseColor << 8) >>> 8);
        if (this.mColorState.mUseColor != i2) {
            this.mColorState.mUseColor = i2;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mColorState.mTint = colorStateList;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, this.mColorState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        this.mColorState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mColorState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        ColorState colorState = this.mColorState;
        if (colorState.mTint == null || colorState.mBlendMode == null) {
            return false;
        }
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorState.mTint, colorState.mBlendMode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mColorState.mTint != null && this.mColorState.mTint.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mColorState.mTint != null && this.mColorState.mTint.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(Xfermode xfermode) {
        this.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    public Xfermode getXfermode() {
        return this.mPaint.getXfermode();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mBlendModeColorFilter != null || this.mPaint.getColorFilter() != null) {
            return -3;
        }
        int i = this.mColorState.mUseColor >>> 24;
        if (i != 0) {
            return i != 255 ? -3 : -1;
        }
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setRect(getBounds());
        outline.setAlpha(getAlpha() / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.ColorDrawable);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState(resources);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ColorState colorState = this.mColorState;
        colorState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        colorState.mThemeAttrs = typedArray.extractThemeAttrs();
        colorState.mBaseColor = typedArray.getColor(0, colorState.mBaseColor);
        colorState.mUseColor = colorState.mBaseColor;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mColorState.canApplyTheme() || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        ColorState colorState = this.mColorState;
        if (colorState == null) {
            return;
        }
        if (colorState.mThemeAttrs != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(colorState.mThemeAttrs, R.styleable.ColorDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (colorState.mTint != null && colorState.mTint.canApplyTheme()) {
            colorState.mTint = colorState.mTint.obtainForTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mColorState;
    }

    static final class ColorState extends Drawable.ConstantState {
        int mBaseColor;
        BlendMode mBlendMode;
        int mChangingConfigurations;
        int[] mThemeAttrs;
        ColorStateList mTint;

        @ViewDebug.ExportedProperty
        int mUseColor;

        ColorState() {
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        }

        ColorState(ColorState colorState) {
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mThemeAttrs = colorState.mThemeAttrs;
            this.mBaseColor = colorState.mBaseColor;
            this.mUseColor = colorState.mUseColor;
            this.mChangingConfigurations = colorState.mChangingConfigurations;
            this.mTint = colorState.mTint;
            this.mBlendMode = colorState.mBlendMode;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            ColorStateList colorStateList = this.mTint;
            return colorStateList != null && colorStateList.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ColorDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new ColorDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null ? colorStateList.getChangingConfigurations() : 0) | i;
        }
    }

    private ColorDrawable(ColorState colorState, Resources resources) {
        this.mPaint = new Paint(1);
        this.mColorState = colorState;
        updateLocalState(resources);
    }

    private void updateLocalState(Resources resources) {
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mColorState.mTint, this.mColorState.mBlendMode);
    }
}
