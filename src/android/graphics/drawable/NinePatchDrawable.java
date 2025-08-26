package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
import android.graphics.NinePatch;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class NinePatchDrawable extends Drawable {
    private static final boolean DEFAULT_DITHER = false;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private BlendModeColorFilter mBlendModeFilter;
    private boolean mMutated;
    private NinePatchState mNinePatchState;
    private Insets mOpticalInsets;
    private Rect mOutlineInsets;
    private float mOutlineRadius;
    private Rect mPadding;
    private Paint mPaint;
    private int mTargetDensity;
    private Rect mTempRect;

    NinePatchDrawable() {
        this.mOpticalInsets = Insets.NONE;
        this.mTargetDensity = 160;
        this.mBitmapWidth = -1;
        this.mBitmapHeight = -1;
        this.mNinePatchState = new NinePatchState();
    }

    @Deprecated
    public NinePatchDrawable(Bitmap bitmap, byte[] bArr, Rect rect, String str) {
        this(new NinePatchState(new NinePatch(bitmap, bArr, str), rect), (Resources) null);
    }

    public NinePatchDrawable(Resources resources, Bitmap bitmap, byte[] bArr, Rect rect, String str) {
        this(new NinePatchState(new NinePatch(bitmap, bArr, str), rect), resources);
    }

    public NinePatchDrawable(Resources resources, Bitmap bitmap, byte[] bArr, Rect rect, Rect rect2, String str) {
        this(new NinePatchState(new NinePatch(bitmap, bArr, str), rect, rect2), resources);
    }

    @Deprecated
    public NinePatchDrawable(NinePatch ninePatch) {
        this(new NinePatchState(ninePatch, new Rect()), (Resources) null);
    }

    public NinePatchDrawable(Resources resources, NinePatch ninePatch) {
        this(new NinePatchState(ninePatch, new Rect()), resources);
    }

    public void setTargetDensity(Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        if (i == 0) {
            i = 160;
        }
        if (this.mTargetDensity != i) {
            this.mTargetDensity = i;
            computeBitmapSize();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        int alpha;
        NinePatchState ninePatchState = this.mNinePatchState;
        Rect bounds = getBounds();
        if (this.mBlendModeFilter == null || getPaint().getColorFilter() != null) {
            z = false;
        } else {
            this.mPaint.setColorFilter(this.mBlendModeFilter);
            z = true;
        }
        int iSave = -1;
        if (ninePatchState.mBaseAlpha != 1.0f) {
            alpha = getPaint().getAlpha();
            this.mPaint.setAlpha((int) ((alpha * ninePatchState.mBaseAlpha) + 0.5f));
        } else {
            alpha = -1;
        }
        if (canvas.getDensity() == 0 && ninePatchState.mNinePatch.getDensity() != 0) {
            iSave = canvas.save();
            float density = this.mTargetDensity / ninePatchState.mNinePatch.getDensity();
            canvas.scale(density, density, bounds.left, bounds.top);
            if (this.mTempRect == null) {
                this.mTempRect = new Rect();
            }
            Rect rect = this.mTempRect;
            rect.left = bounds.left;
            rect.top = bounds.top;
            rect.right = bounds.left + Math.round(bounds.width() / density);
            rect.bottom = bounds.top + Math.round(bounds.height() / density);
            bounds = rect;
        }
        if (needsMirroring()) {
            if (iSave < 0) {
                iSave = canvas.save();
            }
            canvas.scale(-1.0f, 1.0f, (bounds.left + bounds.right) / 2.0f, (bounds.top + bounds.bottom) / 2.0f);
        }
        ninePatchState.mNinePatch.draw(canvas, bounds, this.mPaint);
        if (iSave >= 0) {
            canvas.restoreToCount(iSave);
        }
        if (z) {
            this.mPaint.setColorFilter(null);
        }
        if (alpha >= 0) {
            this.mPaint.setAlpha(alpha);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mNinePatchState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2 != null) {
            rect.set(rect2);
            return (((rect.left | rect.top) | rect.right) | rect.bottom) != 0;
        }
        return super.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        NinePatch.InsetStruct ninePatchInsets;
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState != null && this.mOutlineInsets != null && (ninePatchInsets = ninePatchState.mNinePatch.getBitmap().getNinePatchInsets()) != null) {
            outline.setRoundRect(bounds.left + this.mOutlineInsets.left, bounds.top + this.mOutlineInsets.top, bounds.right - this.mOutlineInsets.right, bounds.bottom - this.mOutlineInsets.bottom, this.mOutlineRadius);
            outline.setAlpha(ninePatchInsets.outlineAlpha * (getAlpha() / 255.0f));
        } else {
            super.getOutline(outline);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Insets getOpticalInsets() {
        Insets insets = this.mOpticalInsets;
        return needsMirroring() ? Insets.of(insets.right, insets.top, insets.left, insets.bottom) : insets;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mPaint == null && i == 255) {
            return;
        }
        getPaint().setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        if (this.mPaint == null) {
            return 255;
        }
        return getPaint().getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mPaint == null && colorFilter == null) {
            return;
        }
        getPaint().setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mNinePatchState.mTint = colorStateList;
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, colorStateList, this.mNinePatchState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        this.mNinePatchState.mBlendMode = blendMode;
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, this.mNinePatchState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        if (this.mPaint != null || z) {
            getPaint().setDither(z);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.mNinePatchState.mAutoMirrored = z;
    }

    private boolean needsMirroring() {
        return isAutoMirrored() && getLayoutDirection() == 1;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mNinePatchState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        getPaint().setFilterBitmap(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isFilterBitmap() {
        return this.mPaint != null && getPaint().isFilterBitmap();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.NinePatchDrawable);
        updateStateFromTypedArray(typedArrayObtainAttributes);
        typedArrayObtainAttributes.recycle();
        updateLocalState(resources);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) throws XmlPullParserException, Resources.NotFoundException, IOException {
        Resources resources = typedArray.getResources();
        NinePatchState ninePatchState = this.mNinePatchState;
        ninePatchState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        ninePatchState.mThemeAttrs = typedArray.extractThemeAttrs();
        ninePatchState.mDither = typedArray.getBoolean(1, ninePatchState.mDither);
        int i = 0;
        int resourceId = typedArray.getResourceId(0, 0);
        if (resourceId != 0) {
            final Rect rect = new Rect();
            Rect rect2 = new Rect();
            Bitmap bitmapDecodeBitmap = null;
            try {
                TypedValue typedValue = new TypedValue();
                InputStream inputStreamOpenRawResource = resources.openRawResource(resourceId, typedValue);
                if (typedValue.density == 0) {
                    i = 160;
                } else if (typedValue.density != 65535) {
                    i = typedValue.density;
                }
                bitmapDecodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(resources, inputStreamOpenRawResource, i), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.NinePatchDrawable$$ExternalSyntheticLambda0
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        NinePatchDrawable.lambda$updateStateFromTypedArray$0(rect, imageDecoder, imageInfo, source);
                    }
                });
                inputStreamOpenRawResource.close();
            } catch (IOException unused) {
            }
            if (bitmapDecodeBitmap == null) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <nine-patch> requires a valid src attribute");
            }
            if (bitmapDecodeBitmap.getNinePatchChunk() == null) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <nine-patch> requires a valid 9-patch source image");
            }
            bitmapDecodeBitmap.getOpticalInsets(rect2);
            ninePatchState.mNinePatch = new NinePatch(bitmapDecodeBitmap, bitmapDecodeBitmap.getNinePatchChunk());
            ninePatchState.mPadding = rect;
            ninePatchState.mOpticalInsets = Insets.of(rect2);
        }
        ninePatchState.mAutoMirrored = typedArray.getBoolean(4, ninePatchState.mAutoMirrored);
        ninePatchState.mBaseAlpha = typedArray.getFloat(3, ninePatchState.mBaseAlpha);
        int i2 = typedArray.getInt(5, -1);
        if (i2 != -1) {
            ninePatchState.mBlendMode = Drawable.parseBlendMode(i2, BlendMode.SRC_IN);
        }
        ColorStateList colorStateList = typedArray.getColorStateList(2);
        if (colorStateList != null) {
            ninePatchState.mTint = colorStateList;
        }
    }

    static /* synthetic */ void lambda$updateStateFromTypedArray$0(Rect rect, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setOutPaddingRect(rect);
        imageDecoder.setAllocator(1);
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState == null) {
            return;
        }
        if (ninePatchState.mThemeAttrs != null) {
            TypedArray typedArrayResolveAttributes = theme.resolveAttributes(ninePatchState.mThemeAttrs, R.styleable.NinePatchDrawable);
            try {
                try {
                    updateStateFromTypedArray(typedArrayResolveAttributes);
                } catch (XmlPullParserException e) {
                    rethrowAsRuntimeException(e);
                }
            } finally {
                typedArrayResolveAttributes.recycle();
            }
        }
        if (ninePatchState.mTint != null && ninePatchState.mTint.canApplyTheme()) {
            ninePatchState.mTint = ninePatchState.mTint.obtainForTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        NinePatchState ninePatchState = this.mNinePatchState;
        return ninePatchState != null && ninePatchState.canApplyTheme();
    }

    public Paint getPaint() {
        if (this.mPaint == null) {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setDither(false);
        }
        return this.mPaint;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mNinePatchState.mNinePatch.hasAlpha()) {
            return -3;
        }
        Paint paint = this.mPaint;
        return (paint == null || paint.getAlpha() >= 255) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public Region getTransparentRegion() {
        return this.mNinePatchState.mNinePatch.getTransparentRegion(getBounds());
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mNinePatchState.mChangingConfigurations = getChangingConfigurations();
        return this.mNinePatchState;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mNinePatchState = new NinePatchState(this.mNinePatchState);
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
    protected boolean onStateChange(int[] iArr) {
        NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState.mTint == null || ninePatchState.mBlendMode == null) {
            return false;
        }
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, ninePatchState.mTint, ninePatchState.mBlendMode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        NinePatchState ninePatchState = this.mNinePatchState;
        if (super.isStateful()) {
            return true;
        }
        return ninePatchState.mTint != null && ninePatchState.mTint.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mNinePatchState.mTint != null && this.mNinePatchState.mTint.hasFocusStateSpecified();
    }

    static final class NinePatchState extends Drawable.ConstantState {
        boolean mAutoMirrored;
        float mBaseAlpha;
        BlendMode mBlendMode;
        int mChangingConfigurations;
        boolean mDither;
        NinePatch mNinePatch;
        Insets mOpticalInsets;
        Rect mPadding;
        int[] mThemeAttrs;
        ColorStateList mTint;

        NinePatchState() {
            this.mNinePatch = null;
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mPadding = null;
            this.mOpticalInsets = Insets.NONE;
            this.mBaseAlpha = 1.0f;
            this.mDither = false;
            this.mAutoMirrored = false;
        }

        NinePatchState(NinePatch ninePatch, Rect rect) {
            this(ninePatch, rect, null, false, false);
        }

        NinePatchState(NinePatch ninePatch, Rect rect, Rect rect2) {
            this(ninePatch, rect, rect2, false, false);
        }

        NinePatchState(NinePatch ninePatch, Rect rect, Rect rect2, boolean z, boolean z2) {
            this.mNinePatch = null;
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mPadding = null;
            this.mOpticalInsets = Insets.NONE;
            this.mBaseAlpha = 1.0f;
            this.mDither = false;
            this.mAutoMirrored = false;
            this.mNinePatch = ninePatch;
            this.mPadding = rect;
            this.mOpticalInsets = Insets.of(rect2);
            this.mDither = z;
            this.mAutoMirrored = z2;
        }

        NinePatchState(NinePatchState ninePatchState) {
            this.mNinePatch = null;
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mPadding = null;
            this.mOpticalInsets = Insets.NONE;
            this.mBaseAlpha = 1.0f;
            this.mDither = false;
            this.mAutoMirrored = false;
            this.mChangingConfigurations = ninePatchState.mChangingConfigurations;
            this.mNinePatch = ninePatchState.mNinePatch;
            this.mTint = ninePatchState.mTint;
            this.mBlendMode = ninePatchState.mBlendMode;
            this.mPadding = ninePatchState.mPadding;
            this.mOpticalInsets = ninePatchState.mOpticalInsets;
            this.mBaseAlpha = ninePatchState.mBaseAlpha;
            this.mDither = ninePatchState.mDither;
            this.mAutoMirrored = ninePatchState.mAutoMirrored;
            this.mThemeAttrs = ninePatchState.mThemeAttrs;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null && colorStateList.canApplyTheme()) || super.canApplyTheme();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new NinePatchDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new NinePatchDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null ? colorStateList.getChangingConfigurations() : 0) | i;
        }
    }

    private void computeBitmapSize() {
        NinePatch ninePatch = this.mNinePatchState.mNinePatch;
        if (ninePatch == null) {
            return;
        }
        int i = this.mTargetDensity;
        int density = ninePatch.getDensity() == 0 ? i : ninePatch.getDensity();
        Insets insets = this.mNinePatchState.mOpticalInsets;
        if (insets != Insets.NONE) {
            this.mOpticalInsets = Insets.of(Drawable.scaleFromDensity(insets.left, density, i, true), Drawable.scaleFromDensity(insets.top, density, i, true), Drawable.scaleFromDensity(insets.right, density, i, true), Drawable.scaleFromDensity(insets.bottom, density, i, true));
        } else {
            this.mOpticalInsets = Insets.NONE;
        }
        Rect rect = this.mNinePatchState.mPadding;
        if (rect != null) {
            if (this.mPadding == null) {
                this.mPadding = new Rect();
            }
            this.mPadding.left = Drawable.scaleFromDensity(rect.left, density, i, true);
            this.mPadding.top = Drawable.scaleFromDensity(rect.top, density, i, true);
            this.mPadding.right = Drawable.scaleFromDensity(rect.right, density, i, true);
            this.mPadding.bottom = Drawable.scaleFromDensity(rect.bottom, density, i, true);
        } else {
            this.mPadding = null;
        }
        this.mBitmapHeight = Drawable.scaleFromDensity(ninePatch.getHeight(), density, i, true);
        this.mBitmapWidth = Drawable.scaleFromDensity(ninePatch.getWidth(), density, i, true);
        NinePatch.InsetStruct ninePatchInsets = ninePatch.getBitmap().getNinePatchInsets();
        if (ninePatchInsets != null) {
            Rect rect2 = ninePatchInsets.outlineRect;
            this.mOutlineInsets = NinePatch.InsetStruct.scaleInsets(rect2.left, rect2.top, rect2.right, rect2.bottom, i / density);
            this.mOutlineRadius = Drawable.scaleFromDensity(ninePatchInsets.outlineRadius, density, i);
            return;
        }
        this.mOutlineInsets = null;
    }

    private NinePatchDrawable(NinePatchState ninePatchState, Resources resources) {
        this.mOpticalInsets = Insets.NONE;
        this.mTargetDensity = 160;
        this.mBitmapWidth = -1;
        this.mBitmapHeight = -1;
        this.mNinePatchState = ninePatchState;
        updateLocalState(resources);
    }

    private void updateLocalState(Resources resources) {
        NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState.mDither) {
            setDither(ninePatchState.mDither);
        }
        if (resources == null && ninePatchState.mNinePatch != null) {
            this.mTargetDensity = ninePatchState.mNinePatch.getDensity();
        } else {
            this.mTargetDensity = Drawable.resolveDensity(resources, this.mTargetDensity);
        }
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, ninePatchState.mTint, ninePatchState.mBlendMode);
        computeBitmapSize();
    }
}
