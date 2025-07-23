package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import com.android.internal.R;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class BitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private static final String TAG = "BitmapDrawable";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_DISABLED = -1;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private static final int TILE_MODE_UNDEFINED = -2;
    private int mBitmapHeight;
    private BitmapState mBitmapState;
    private int mBitmapWidth;
    private BlendModeColorFilter mBlendModeFilter;
    private final Rect mDstRect;
    private boolean mDstRectAndInsetsDirty;
    private Matrix mMirrorMatrix;
    private boolean mMutated;
    private Insets mOpticalInsets;
    private int mTargetDensity;

    @Deprecated
    public BitmapDrawable() {
        this.mDstRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = Insets.NONE;
        init(new BitmapState((Bitmap) null), null);
    }

    @Deprecated
    public BitmapDrawable(Resources resources) {
        this.mDstRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = Insets.NONE;
        init(new BitmapState((Bitmap) null), resources);
    }

    @Deprecated
    public BitmapDrawable(Bitmap bitmap) {
        this.mDstRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = Insets.NONE;
        if (bitmap == null) {
            Log.w(TAG, "BitmapDrawable created with null Bitmap");
        }
        init(new BitmapState(bitmap), null);
    }

    public BitmapDrawable(Resources resources, Bitmap bitmap) {
        this.mDstRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = Insets.NONE;
        if (bitmap == null) {
            Log.w(TAG, "BitmapDrawable created with null Bitmap");
        }
        init(new BitmapState(bitmap), resources);
    }

    @Deprecated
    public BitmapDrawable(String str) {
        this((Resources) null, str);
    }

    public BitmapDrawable(Resources resources, String str) {
        this.mDstRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = Insets.NONE;
        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(resources, fileInputStream), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.BitmapDrawable$$ExternalSyntheticLambda0
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        imageDecoder.setAllocator(1);
                    }
                });
                fileInputStream.close();
                init(new BitmapState(bitmap), resources);
                if (this.mBitmapState.mBitmap == null) {
                    Log.w(TAG, "BitmapDrawable cannot decode " + str);
                }
            } finally {
            }
        } catch (Exception unused) {
            init(new BitmapState(bitmap), resources);
            if (this.mBitmapState.mBitmap == null) {
                Log.w(TAG, "BitmapDrawable cannot decode " + str);
            }
        } catch (Throwable th) {
            init(new BitmapState(bitmap), resources);
            if (this.mBitmapState.mBitmap == null) {
                Log.w(TAG, "BitmapDrawable cannot decode " + str);
            }
            throw th;
        }
    }

    @Deprecated
    public BitmapDrawable(InputStream inputStream) {
        this((Resources) null, inputStream);
    }

    public BitmapDrawable(Resources resources, InputStream inputStream) {
        this.mDstRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = Insets.NONE;
        try {
            init(new BitmapState(ImageDecoder.decodeBitmap(ImageDecoder.createSource(resources, inputStream), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.BitmapDrawable$$ExternalSyntheticLambda2
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    imageDecoder.setAllocator(1);
                }
            })), resources);
            if (this.mBitmapState.mBitmap == null) {
                Log.w(TAG, "BitmapDrawable cannot decode " + inputStream);
            }
        } catch (Exception unused) {
            init(new BitmapState((Bitmap) null), resources);
            if (this.mBitmapState.mBitmap == null) {
                Log.w(TAG, "BitmapDrawable cannot decode " + inputStream);
            }
        } catch (Throwable th) {
            init(new BitmapState((Bitmap) null), resources);
            if (this.mBitmapState.mBitmap == null) {
                Log.w(TAG, "BitmapDrawable cannot decode " + inputStream);
            }
            throw th;
        }
    }

    public final Paint getPaint() {
        return this.mBitmapState.mPaint;
    }

    public final Bitmap getBitmap() {
        return this.mBitmapState.mBitmap;
    }

    private void computeBitmapSize() {
        Bitmap bitmap = this.mBitmapState.mBitmap;
        if (bitmap != null) {
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = bitmap.getScaledHeight(this.mTargetDensity);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
        }
    }

    public void setBitmap(Bitmap bitmap) {
        if (this.mBitmapState.mBitmap != bitmap) {
            this.mBitmapState.mBitmap = bitmap;
            computeBitmapSize();
            invalidateSelf();
        }
    }

    public void setTargetDensity(Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        if (this.mTargetDensity != i) {
            if (i == 0) {
                i = 160;
            }
            this.mTargetDensity = i;
            if (this.mBitmapState.mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public int getGravity() {
        return this.mBitmapState.mGravity;
    }

    public void setGravity(int i) {
        if (this.mBitmapState.mGravity != i) {
            this.mBitmapState.mGravity = i;
            this.mDstRectAndInsetsDirty = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z) {
        if (this.mBitmapState.mBitmap != null) {
            this.mBitmapState.mBitmap.setHasMipMap(z);
            invalidateSelf();
        }
    }

    public boolean hasMipMap() {
        return this.mBitmapState.mBitmap != null && this.mBitmapState.mBitmap.hasMipMap();
    }

    public void setAntiAlias(boolean z) {
        this.mBitmapState.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    public boolean hasAntiAlias() {
        return this.mBitmapState.mPaint.isAntiAlias();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mBitmapState.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isFilterBitmap() {
        return this.mBitmapState.mPaint.isFilterBitmap();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mBitmapState.mPaint.setDither(z);
        invalidateSelf();
    }

    public Shader.TileMode getTileModeX() {
        return this.mBitmapState.mTileModeX;
    }

    public Shader.TileMode getTileModeY() {
        return this.mBitmapState.mTileModeY;
    }

    public void setTileModeX(Shader.TileMode tileMode) {
        setTileModeXY(tileMode, this.mBitmapState.mTileModeY);
    }

    public final void setTileModeY(Shader.TileMode tileMode) {
        setTileModeXY(this.mBitmapState.mTileModeX, tileMode);
    }

    public void setTileModeXY(Shader.TileMode tileMode, Shader.TileMode tileMode2) {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTileModeX == tileMode && bitmapState.mTileModeY == tileMode2) {
            return;
        }
        bitmapState.mTileModeX = tileMode;
        bitmapState.mTileModeY = tileMode2;
        bitmapState.mRebuildShader = true;
        this.mDstRectAndInsetsDirty = true;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.mBitmapState.mAutoMirrored != z) {
            this.mBitmapState.mAutoMirrored = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        return this.mBitmapState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mBitmapState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    private boolean needMirroring() {
        return isAutoMirrored() && getLayoutDirection() == 1;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.mDstRectAndInsetsDirty = true;
        Bitmap bitmap = this.mBitmapState.mBitmap;
        Shader shader = this.mBitmapState.mPaint.getShader();
        if (bitmap == null || shader == null) {
            return;
        }
        updateShaderMatrix(bitmap, this.mBitmapState.mPaint, shader, needMirroring());
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int i;
        Bitmap bitmap = this.mBitmapState.mBitmap;
        if (bitmap == null) {
            return;
        }
        if (bitmap.getConfig() == Bitmap.Config.HARDWARE && !canvas.isHardwareAccelerated()) {
            Log.d(TAG, "skip drawing hardware bitmap on S/W canvas");
            return;
        }
        BitmapState bitmapState = this.mBitmapState;
        Paint paint = bitmapState.mPaint;
        boolean z = false;
        if (bitmapState.mRebuildShader) {
            Shader.TileMode tileMode = bitmapState.mTileModeX;
            Shader.TileMode tileMode2 = bitmapState.mTileModeY;
            if (tileMode == null && tileMode2 == null) {
                paint.setShader(null);
            } else {
                if (tileMode == null) {
                    tileMode = Shader.TileMode.CLAMP;
                }
                if (tileMode2 == null) {
                    tileMode2 = Shader.TileMode.CLAMP;
                }
                paint.setShader(new BitmapShader(bitmap, tileMode, tileMode2));
            }
            bitmapState.mRebuildShader = false;
        }
        if (bitmapState.mBaseAlpha != 1.0f) {
            Paint paint2 = getPaint();
            i = paint2.getAlpha();
            paint2.setAlpha((int) ((i * bitmapState.mBaseAlpha) + 0.5f));
        } else {
            i = -1;
        }
        if (this.mBlendModeFilter != null && paint.getColorFilter() == null) {
            paint.setColorFilter(this.mBlendModeFilter);
            z = true;
        }
        updateDstRectAndInsetsIfDirty();
        Shader shader = paint.getShader();
        boolean needMirroring = needMirroring();
        if (shader == null) {
            if (needMirroring) {
                canvas.save();
                canvas.translate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            canvas.drawBitmap(bitmap, (Rect) null, this.mDstRect, paint);
            if (needMirroring) {
                canvas.restore();
            }
        } else {
            updateShaderMatrix(bitmap, paint, shader, needMirroring);
            canvas.drawRect(this.mDstRect, paint);
        }
        if (z) {
            paint.setColorFilter(null);
        }
        if (i >= 0) {
            paint.setAlpha(i);
        }
    }

    private void updateShaderMatrix(Bitmap bitmap, Paint paint, Shader shader, boolean z) {
        int density = bitmap.getDensity();
        int i = this.mTargetDensity;
        boolean z2 = (density == 0 || density == i) ? false : true;
        if (z2 || z) {
            Matrix orCreateMirrorMatrix = getOrCreateMirrorMatrix();
            orCreateMirrorMatrix.reset();
            if (z) {
                orCreateMirrorMatrix.setTranslate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                orCreateMirrorMatrix.setScale(-1.0f, 1.0f);
            }
            if (z2) {
                float f = i / density;
                orCreateMirrorMatrix.postScale(f, f);
            }
            shader.setLocalMatrix(orCreateMirrorMatrix);
        } else {
            this.mMirrorMatrix = null;
            shader.setLocalMatrix(Matrix.IDENTITY_MATRIX);
        }
        paint.setShader(shader);
    }

    private Matrix getOrCreateMirrorMatrix() {
        if (this.mMirrorMatrix == null) {
            this.mMirrorMatrix = new Matrix();
        }
        return this.mMirrorMatrix;
    }

    private void updateDstRectAndInsetsIfDirty() {
        if (this.mDstRectAndInsetsDirty) {
            if (this.mBitmapState.mTileModeX == null && this.mBitmapState.mTileModeY == null) {
                Rect bounds = getBounds();
                Gravity.apply(this.mBitmapState.mGravity, this.mBitmapWidth, this.mBitmapHeight, bounds, this.mDstRect, getLayoutDirection());
                this.mOpticalInsets = Insets.of(this.mDstRect.left - bounds.left, this.mDstRect.top - bounds.top, bounds.right - this.mDstRect.right, bounds.bottom - this.mDstRect.bottom);
            } else {
                copyBounds(this.mDstRect);
                this.mOpticalInsets = Insets.NONE;
            }
        }
        this.mDstRectAndInsetsDirty = false;
    }

    @Override // android.graphics.drawable.Drawable
    public Insets getOpticalInsets() {
        updateDstRectAndInsetsIfDirty();
        return this.mOpticalInsets;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        updateDstRectAndInsetsIfDirty();
        outline.setRect(this.mDstRect);
        outline.setAlpha((this.mBitmapState.mBitmap == null || this.mBitmapState.mBitmap.hasAlpha()) ? 0.0f : getAlpha() / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mBitmapState.mPaint.getAlpha()) {
            this.mBitmapState.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mBitmapState.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mBitmapState.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mBitmapState.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTint != colorStateList) {
            bitmapState.mTint = colorStateList;
            this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, colorStateList, this.mBitmapState.mBlendMode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mBlendMode != blendMode) {
            bitmapState.mBlendMode = blendMode;
            this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, this.mBitmapState.mTint, blendMode);
            invalidateSelf();
        }
    }

    private ColorStateList getTint() {
        return this.mBitmapState.mTint;
    }

    private PorterDuff.Mode getTintMode() {
        return BlendMode.blendModeToPorterDuffMode(this.mBitmapState.mBlendMode);
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(Xfermode xfermode) {
        this.mBitmapState.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mBitmapState = new BitmapState(this.mBitmapState);
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
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTint == null || bitmapState.mBlendMode == null) {
            return false;
        }
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, bitmapState.mTint, bitmapState.mBlendMode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return (this.mBitmapState.mTint != null && this.mBitmapState.mTint.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mBitmapState.mTint != null && this.mBitmapState.mTint.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.BitmapDrawable);
        updateStateFromTypedArray(obtainAttributes, this.mSrcDensityOverride);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState(resources);
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mBitmap == null) {
            if (bitmapState.mThemeAttrs == null || bitmapState.mThemeAttrs[1] == 0) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <bitmap> requires a valid 'src' attribute");
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray, int i) throws XmlPullParserException {
        int i2;
        Resources resources = typedArray.getResources();
        BitmapState bitmapState = this.mBitmapState;
        bitmapState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        bitmapState.mThemeAttrs = typedArray.extractThemeAttrs();
        bitmapState.mSrcDensityOverride = i;
        bitmapState.mTargetDensity = Drawable.resolveDensity(resources, 0);
        int resourceId = typedArray.getResourceId(1, 0);
        if (resourceId != 0) {
            TypedValue typedValue = new TypedValue();
            resources.getValueForDensity(resourceId, i, typedValue, true);
            if (i > 0 && typedValue.density > 0 && typedValue.density != 65535) {
                if (typedValue.density == i) {
                    typedValue.density = resources.getDisplayMetrics().densityDpi;
                } else {
                    typedValue.density = (typedValue.density * resources.getDisplayMetrics().densityDpi) / i;
                }
            }
            if (typedValue.density == 0) {
                i2 = 160;
            } else {
                i2 = typedValue.density != 65535 ? typedValue.density : 0;
            }
            Bitmap bitmap = null;
            try {
                InputStream openRawResource = resources.openRawResource(resourceId, typedValue);
                try {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(resources, openRawResource, i2), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.BitmapDrawable$$ExternalSyntheticLambda1
                        @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                        public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                            imageDecoder.setAllocator(1);
                        }
                    });
                    if (openRawResource != null) {
                        openRawResource.close();
                    }
                } finally {
                }
            } catch (Exception unused) {
            }
            if (bitmap == null) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <bitmap> requires a valid 'src' attribute");
            }
            bitmapState.mBitmap = bitmap;
        }
        setMipMap(typedArray.getBoolean(8, bitmapState.mBitmap != null ? bitmapState.mBitmap.hasMipMap() : false));
        bitmapState.mAutoMirrored = typedArray.getBoolean(9, bitmapState.mAutoMirrored);
        bitmapState.mBaseAlpha = typedArray.getFloat(7, bitmapState.mBaseAlpha);
        int i3 = typedArray.getInt(10, -1);
        if (i3 != -1) {
            bitmapState.mBlendMode = Drawable.parseBlendMode(i3, BlendMode.SRC_IN);
        }
        ColorStateList colorStateList = typedArray.getColorStateList(5);
        if (colorStateList != null) {
            bitmapState.mTint = colorStateList;
        }
        Paint paint = this.mBitmapState.mPaint;
        paint.setAntiAlias(typedArray.getBoolean(2, paint.isAntiAlias()));
        paint.setFilterBitmap(typedArray.getBoolean(3, paint.isFilterBitmap()));
        paint.setDither(typedArray.getBoolean(4, paint.isDither()));
        setGravity(typedArray.getInt(0, bitmapState.mGravity));
        int i4 = typedArray.getInt(6, -2);
        if (i4 != -2) {
            Shader.TileMode parseTileMode = parseTileMode(i4);
            setTileModeXY(parseTileMode, parseTileMode);
        }
        int i5 = typedArray.getInt(11, -2);
        if (i5 != -2) {
            setTileModeX(parseTileMode(i5));
        }
        int i6 = typedArray.getInt(12, -2);
        if (i6 != -2) {
            setTileModeY(parseTileMode(i6));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState == null) {
            return;
        }
        if (bitmapState.mThemeAttrs != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(bitmapState.mThemeAttrs, R.styleable.BitmapDrawable);
            try {
                try {
                    updateStateFromTypedArray(resolveAttributes, bitmapState.mSrcDensityOverride);
                } catch (XmlPullParserException e) {
                    rethrowAsRuntimeException(e);
                }
            } finally {
                resolveAttributes.recycle();
            }
        }
        if (bitmapState.mTint != null && bitmapState.mTint.canApplyTheme()) {
            bitmapState.mTint = bitmapState.mTint.obtainForTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    private static Shader.TileMode parseTileMode(int i) {
        if (i == 0) {
            return Shader.TileMode.CLAMP;
        }
        if (i == 1) {
            return Shader.TileMode.REPEAT;
        }
        if (i != 2) {
            return null;
        }
        return Shader.TileMode.MIRROR;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        BitmapState bitmapState = this.mBitmapState;
        return bitmapState != null && bitmapState.canApplyTheme();
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
        Bitmap bitmap;
        return (this.mBitmapState.mGravity == 119 && (bitmap = this.mBitmapState.mBitmap) != null && !bitmap.hasAlpha() && this.mBitmapState.mPaint.getAlpha() >= 255) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        this.mBitmapState.mChangingConfigurations |= getChangingConfigurations();
        return this.mBitmapState;
    }

    static final class BitmapState extends Drawable.ConstantState {
        boolean mAutoMirrored;
        float mBaseAlpha;
        Bitmap mBitmap;
        BlendMode mBlendMode;
        int mChangingConfigurations;
        int mGravity;
        final Paint mPaint;
        boolean mRebuildShader;
        int mSrcDensityOverride;
        int mTargetDensity;
        int[] mThemeAttrs;
        Shader.TileMode mTileModeX;
        Shader.TileMode mTileModeY;
        ColorStateList mTint;

        BitmapState(Bitmap bitmap) {
            this.mThemeAttrs = null;
            this.mBitmap = null;
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mGravity = 119;
            this.mBaseAlpha = 1.0f;
            this.mTileModeX = null;
            this.mTileModeY = null;
            this.mSrcDensityOverride = 0;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mBitmap = bitmap;
            this.mPaint = new Paint(6);
        }

        BitmapState(BitmapState bitmapState) {
            this.mThemeAttrs = null;
            this.mBitmap = null;
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mGravity = 119;
            this.mBaseAlpha = 1.0f;
            this.mTileModeX = null;
            this.mTileModeY = null;
            this.mSrcDensityOverride = 0;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mBitmap = bitmapState.mBitmap;
            this.mTint = bitmapState.mTint;
            this.mBlendMode = bitmapState.mBlendMode;
            this.mThemeAttrs = bitmapState.mThemeAttrs;
            this.mChangingConfigurations = bitmapState.mChangingConfigurations;
            this.mGravity = bitmapState.mGravity;
            this.mTileModeX = bitmapState.mTileModeX;
            this.mTileModeY = bitmapState.mTileModeY;
            this.mSrcDensityOverride = bitmapState.mSrcDensityOverride;
            this.mTargetDensity = bitmapState.mTargetDensity;
            this.mBaseAlpha = bitmapState.mBaseAlpha;
            this.mPaint = new Paint(bitmapState.mPaint);
            this.mRebuildShader = bitmapState.mRebuildShader;
            this.mAutoMirrored = bitmapState.mAutoMirrored;
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
            return new BitmapDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new BitmapDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null ? colorStateList.getChangingConfigurations() : 0) | i;
        }
    }

    private BitmapDrawable(BitmapState bitmapState, Resources resources) {
        this.mDstRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = Insets.NONE;
        init(bitmapState, resources);
    }

    private void init(BitmapState bitmapState, Resources resources) {
        this.mBitmapState = bitmapState;
        updateLocalState(resources);
        BitmapState bitmapState2 = this.mBitmapState;
        if (bitmapState2 == null || resources == null) {
            return;
        }
        bitmapState2.mTargetDensity = this.mTargetDensity;
    }

    private void updateLocalState(Resources resources) {
        this.mTargetDensity = resolveDensity(resources, this.mBitmapState.mTargetDensity);
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, this.mBitmapState.mTint, this.mBitmapState.mBlendMode);
        computeBitmapSize();
    }
}
