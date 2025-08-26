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
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ShapeDrawable extends Drawable {
    private BlendModeColorFilter mBlendModeColorFilter;
    private boolean mMutated;
    private ShapeState mShapeState;

    public static abstract class ShaderFactory {
        public abstract Shader resize(int i, int i2);
    }

    private static int modulateAlpha(int i, int i2) {
        return (i * (i2 + (i2 >>> 7))) >>> 8;
    }

    public ShapeDrawable() {
        this(new ShapeState(), null);
    }

    public ShapeDrawable(Shape shape) {
        this(new ShapeState(), null);
        this.mShapeState.mShape = shape;
    }

    public Shape getShape() {
        return this.mShapeState.mShape;
    }

    public void setShape(Shape shape) {
        this.mShapeState.mShape = shape;
        updateShape();
    }

    public void setShaderFactory(ShaderFactory shaderFactory) {
        this.mShapeState.mShaderFactory = shaderFactory;
    }

    public ShaderFactory getShaderFactory() {
        return this.mShapeState.mShaderFactory;
    }

    public Paint getPaint() {
        return this.mShapeState.mPaint;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if ((i | i2 | i3 | i4) == 0) {
            this.mShapeState.mPadding = null;
        } else {
            if (this.mShapeState.mPadding == null) {
                this.mShapeState.mPadding = new Rect();
            }
            this.mShapeState.mPadding.set(i, i2, i3, i4);
        }
        invalidateSelf();
    }

    public void setPadding(Rect rect) {
        if (rect == null) {
            this.mShapeState.mPadding = null;
        } else {
            if (this.mShapeState.mPadding == null) {
                this.mShapeState.mPadding = new Rect();
            }
            this.mShapeState.mPadding.set(rect);
        }
        invalidateSelf();
    }

    public void setIntrinsicWidth(int i) {
        this.mShapeState.mIntrinsicWidth = i;
        invalidateSelf();
    }

    public void setIntrinsicHeight(int i) {
        this.mShapeState.mIntrinsicHeight = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mShapeState.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mShapeState.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        if (this.mShapeState.mPadding != null) {
            rect.set(this.mShapeState.mPadding);
            return true;
        }
        return super.getPadding(rect);
    }

    protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
        shape.draw(canvas, paint);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        Rect bounds = getBounds();
        ShapeState shapeState = this.mShapeState;
        Paint paint = shapeState.mPaint;
        int alpha = paint.getAlpha();
        paint.setAlpha(modulateAlpha(alpha, shapeState.mAlpha));
        if (paint.getAlpha() != 0 || paint.getXfermode() != null || paint.hasShadowLayer()) {
            if (this.mBlendModeColorFilter == null || paint.getColorFilter() != null) {
                z = false;
            } else {
                paint.setColorFilter(this.mBlendModeColorFilter);
                z = true;
            }
            if (shapeState.mShape != null) {
                int iSave = canvas.save();
                canvas.translate(bounds.left, bounds.top);
                onDraw(shapeState.mShape, canvas, paint);
                canvas.restoreToCount(iSave);
            } else {
                canvas.drawRect(bounds, paint);
            }
            if (z) {
                paint.setColorFilter(null);
            }
        }
        paint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mShapeState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mShapeState.mAlpha = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mShapeState.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mShapeState.mTint = colorStateList;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, this.mShapeState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        this.mShapeState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mShapeState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mShapeState.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(Xfermode xfermode) {
        this.mShapeState.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mShapeState.mShape != null) {
            return -3;
        }
        Paint paint = this.mShapeState.mPaint;
        if (paint.getXfermode() != null) {
            return -3;
        }
        int alpha = paint.getAlpha();
        if (alpha == 0) {
            return -2;
        }
        return alpha == 255 ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mShapeState.mPaint.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateShape();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        ShapeState shapeState = this.mShapeState;
        if (shapeState.mTint == null || shapeState.mBlendMode == null) {
            return false;
        }
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, shapeState.mTint, shapeState.mBlendMode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ShapeState shapeState = this.mShapeState;
        if (super.isStateful()) {
            return true;
        }
        return shapeState.mTint != null && shapeState.mTint.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mShapeState.mTint != null && this.mShapeState.mTint.hasFocusStateSpecified();
    }

    protected boolean inflateTag(String str, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        if (!"padding".equals(str)) {
            return false;
        }
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.ShapeDrawablePadding);
        setPadding(typedArrayObtainAttributes.getDimensionPixelOffset(0, 0), typedArrayObtainAttributes.getDimensionPixelOffset(1, 0), typedArrayObtainAttributes.getDimensionPixelOffset(2, 0), typedArrayObtainAttributes.getDimensionPixelOffset(3, 0));
        typedArrayObtainAttributes.recycle();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.ShapeDrawable);
        updateStateFromTypedArray(typedArrayObtainAttributes);
        typedArrayObtainAttributes.recycle();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (!inflateTag(name, resources, xmlPullParser, attributeSet)) {
                    Log.w("drawable", "Unknown element: " + name + " for ShapeDrawable " + this);
                }
            }
        }
        updateLocalState();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        ShapeState shapeState = this.mShapeState;
        if (shapeState == null) {
            return;
        }
        if (shapeState.mThemeAttrs != null) {
            TypedArray typedArrayResolveAttributes = theme.resolveAttributes(shapeState.mThemeAttrs, R.styleable.ShapeDrawable);
            updateStateFromTypedArray(typedArrayResolveAttributes);
            typedArrayResolveAttributes.recycle();
        }
        if (shapeState.mTint != null && shapeState.mTint.canApplyTheme()) {
            shapeState.mTint = shapeState.mTint.obtainForTheme(theme);
        }
        updateLocalState();
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ShapeState shapeState = this.mShapeState;
        Paint paint = shapeState.mPaint;
        shapeState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        shapeState.mThemeAttrs = typedArray.extractThemeAttrs();
        paint.setColor(typedArray.getColor(4, paint.getColor()));
        paint.setDither(typedArray.getBoolean(0, paint.isDither()));
        shapeState.mIntrinsicWidth = (int) typedArray.getDimension(3, shapeState.mIntrinsicWidth);
        shapeState.mIntrinsicHeight = (int) typedArray.getDimension(2, shapeState.mIntrinsicHeight);
        int i = typedArray.getInt(5, -1);
        if (i != -1) {
            shapeState.mBlendMode = Drawable.parseBlendMode(i, BlendMode.SRC_IN);
        }
        ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            shapeState.mTint = colorStateList;
        }
    }

    private void updateShape() {
        if (this.mShapeState.mShape != null) {
            Rect bounds = getBounds();
            int iWidth = bounds.width();
            int iHeight = bounds.height();
            this.mShapeState.mShape.resize(iWidth, iHeight);
            if (this.mShapeState.mShaderFactory != null) {
                this.mShapeState.mPaint.setShader(this.mShapeState.mShaderFactory.resize(iWidth, iHeight));
            }
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        if (this.mShapeState.mShape != null) {
            this.mShapeState.mShape.getOutline(outline);
            outline.setAlpha(getAlpha() / 255.0f);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mShapeState.mChangingConfigurations = getChangingConfigurations();
        return this.mShapeState;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mShapeState = new ShapeState(this.mShapeState);
            updateLocalState();
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static final class ShapeState extends Drawable.ConstantState {
        int mAlpha;
        BlendMode mBlendMode;
        int mChangingConfigurations;
        int mIntrinsicHeight;
        int mIntrinsicWidth;
        Rect mPadding;
        final Paint mPaint;
        ShaderFactory mShaderFactory;
        Shape mShape;
        int[] mThemeAttrs;
        ColorStateList mTint;

        ShapeState() {
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mAlpha = 255;
            this.mPaint = new Paint(1);
        }

        ShapeState(ShapeState shapeState) {
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mAlpha = 255;
            this.mChangingConfigurations = shapeState.mChangingConfigurations;
            this.mPaint = new Paint(shapeState.mPaint);
            this.mThemeAttrs = shapeState.mThemeAttrs;
            Shape shape = shapeState.mShape;
            if (shape != null) {
                try {
                    this.mShape = shape.mo1491clone();
                } catch (CloneNotSupportedException unused) {
                    this.mShape = shapeState.mShape;
                }
            }
            this.mTint = shapeState.mTint;
            this.mBlendMode = shapeState.mBlendMode;
            if (shapeState.mPadding != null) {
                this.mPadding = new Rect(shapeState.mPadding);
            }
            this.mIntrinsicWidth = shapeState.mIntrinsicWidth;
            this.mIntrinsicHeight = shapeState.mIntrinsicHeight;
            this.mAlpha = shapeState.mAlpha;
            this.mShaderFactory = shapeState.mShaderFactory;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            ColorStateList colorStateList = this.mTint;
            return colorStateList != null && colorStateList.canApplyTheme();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ShapeDrawable(new ShapeState(this), null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new ShapeDrawable(new ShapeState(this), resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null ? colorStateList.getChangingConfigurations() : 0) | i;
        }
    }

    private ShapeDrawable(ShapeState shapeState, Resources resources) {
        this.mShapeState = shapeState;
        updateLocalState();
    }

    private void updateLocalState() {
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mShapeState.mTint, this.mShapeState.mBlendMode);
    }
}
