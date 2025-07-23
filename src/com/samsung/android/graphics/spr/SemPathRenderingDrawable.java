package com.samsung.android.graphics.spr;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.graphics.spr.animation.SprDrawableAnimation;
import com.samsung.android.graphics.spr.animation.SprDrawableAnimationFrame;
import com.samsung.android.graphics.spr.animation.SprDrawableAnimationValue;
import com.samsung.android.graphics.spr.cache.SprCacheManager;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeFill;
import com.samsung.android.graphics.spr.document.attribute.impl.SprLinearGradient;
import com.samsung.android.graphics.spr.document.debug.SprDebug;
import com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeNinePatch;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapeRectangle;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes6.dex */
public class SemPathRenderingDrawable extends Drawable implements Animatable {
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;
    private static final String NA_NAME = "n/a";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private static int mBitmapDrawable_alpha = 0;
    private static int mBitmapDrawable_autoMirrored = 0;
    private static int mBitmapDrawable_gravity = 0;
    private static int mBitmapDrawable_src = 0;
    private static int mBitmapDrawable_tileMode = 0;
    private static int mBitmapDrawable_tileModeX = 0;
    private static int mBitmapDrawable_tileModeY = 0;
    private static int mBitmapDrawable_tint = 0;
    private static int mBitmapDrawable_tintMode = 0;
    private static final Method mCanApplyTheme;
    private static final Method mExtractThemeAttrs;
    private static final Method mGetLayoutDirection;
    private static final Method mObtainForTheme;
    private static final Method mParseTintMode;
    private static final Method mResolveAttributes;
    private static int[] mStyleableBitmapDrawable = null;
    private static final Method mUpdateTintFilter;
    private static final int mVersion = 151023;
    private Bitmap mAnimationBitmap;
    private Bitmap mCacheBitmap;
    private int mCacheDensityDpi;
    protected SprDocument mDocument;
    private Rect mDstRect;
    private Matrix mIdentityMatrix;
    private Matrix mMirrorMatrix;
    private boolean mMutated;
    private SprDrawableAnimation mSprAnimation;
    private SprState mState;
    private PorterDuffColorFilter mTintFilter;
    private final float[] mTmpFloats;
    private final Matrix mTmpMatrix;

    public static int getVersion() {
        return mVersion;
    }

    static {
        Method method;
        try {
            method = Drawable.class.getDeclaredMethod("updateTintFilter", PorterDuffColorFilter.class, ColorStateList.class, PorterDuff.Mode.class);
        } catch (Exception unused) {
            method = null;
        }
        mUpdateTintFilter = method;
        try {
            method = Drawable.class.getMethod("parseTintMode", Integer.TYPE, PorterDuff.Mode.class);
        } catch (Exception unused2) {
        }
        mParseTintMode = method;
        try {
            Class[] clsArr = new Class[0];
            method = Drawable.class.getMethod("getLayoutDirection", null);
        } catch (Exception unused3) {
        }
        mGetLayoutDirection = method;
        try {
            Class[] clsArr2 = new Class[0];
            method = TypedArray.class.getDeclaredMethod("extractThemeAttrs", null);
        } catch (Exception unused4) {
        }
        mExtractThemeAttrs = method;
        try {
            method = Resources.Theme.class.getDeclaredMethod("resolveAttributes", int[].class, int[].class);
        } catch (Exception unused5) {
        }
        mResolveAttributes = method;
        try {
            method = ColorStateList.class.getDeclaredMethod("obtainForTheme", Resources.Theme.class);
        } catch (Exception unused6) {
        }
        mObtainForTheme = method;
        try {
            Class[] clsArr3 = new Class[0];
            method = ColorStateList.class.getDeclaredMethod("canApplyTheme", null);
        } catch (Exception unused7) {
        }
        mCanApplyTheme = method;
        try {
            Class<?> cls = Class.forName("com.android.internal.R$styleable");
            mStyleableBitmapDrawable = (int[]) cls.getDeclaredField("BitmapDrawable").get(null);
            mBitmapDrawable_src = cls.getDeclaredField("BitmapDrawable_src").getInt(null);
            mBitmapDrawable_alpha = cls.getDeclaredField("BitmapDrawable_alpha").getInt(null);
            mBitmapDrawable_autoMirrored = cls.getDeclaredField("BitmapDrawable_autoMirrored").getInt(null);
            mBitmapDrawable_gravity = cls.getDeclaredField("BitmapDrawable_gravity").getInt(null);
            mBitmapDrawable_tileMode = cls.getDeclaredField("BitmapDrawable_tileMode").getInt(null);
            mBitmapDrawable_tileModeX = cls.getDeclaredField("BitmapDrawable_tileModeX").getInt(null);
            mBitmapDrawable_tileModeY = cls.getDeclaredField("BitmapDrawable_tileModeY").getInt(null);
            mBitmapDrawable_tint = cls.getDeclaredField("BitmapDrawable_tint").getInt(null);
            mBitmapDrawable_tintMode = cls.getDeclaredField("BitmapDrawable_tintMode").getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SemPathRenderingDrawable() {
        this.mState = null;
        this.mMutated = false;
        this.mCacheBitmap = null;
        this.mCacheDensityDpi = 0;
        this.mDocument = null;
        this.mTintFilter = null;
        this.mSprAnimation = null;
        this.mAnimationBitmap = null;
        this.mDstRect = new Rect();
        this.mMirrorMatrix = null;
        this.mIdentityMatrix = null;
        this.mTmpMatrix = new Matrix();
        this.mTmpFloats = new float[9];
        this.mState = new SprState(this.mDocument);
    }

    public SemPathRenderingDrawable(SprDocument sprDocument) {
        this.mState = null;
        this.mMutated = false;
        this.mCacheBitmap = null;
        this.mCacheDensityDpi = 0;
        this.mDocument = null;
        this.mTintFilter = null;
        this.mSprAnimation = null;
        this.mAnimationBitmap = null;
        this.mDstRect = new Rect();
        this.mMirrorMatrix = null;
        this.mIdentityMatrix = null;
        this.mTmpMatrix = new Matrix();
        this.mTmpFloats = new float[9];
        SprState sprState = new SprState(sprDocument);
        this.mState = sprState;
        SprDocument sprDocument2 = sprState.mDocument;
        this.mDocument = sprDocument2;
        if (sprDocument2 != null) {
            float densityScale = this.mState.getDensityScale();
            super.setBounds(Math.round(this.mDocument.mLeft * densityScale), Math.round(this.mDocument.mTop * densityScale), Math.round(this.mDocument.mRight * densityScale), Math.round(this.mDocument.mBottom * densityScale));
        }
    }

    public SemPathRenderingDrawable(SprState sprState, Resources resources) {
        this.mState = null;
        this.mMutated = false;
        this.mCacheBitmap = null;
        this.mCacheDensityDpi = 0;
        this.mDocument = null;
        this.mTintFilter = null;
        this.mSprAnimation = null;
        this.mAnimationBitmap = null;
        this.mDstRect = new Rect();
        this.mMirrorMatrix = null;
        this.mIdentityMatrix = null;
        this.mTmpMatrix = new Matrix();
        this.mTmpFloats = new float[9];
        this.mState = sprState;
        SprDocument sprDocument = sprState.mDocument;
        this.mDocument = sprDocument;
        if (sprDocument != null) {
            float densityScale = this.mState.getDensityScale();
            super.setBounds(Math.round(this.mDocument.mLeft * densityScale), Math.round(this.mDocument.mTop * densityScale), Math.round(this.mDocument.mRight * densityScale), Math.round(this.mDocument.mBottom * densityScale));
            this.mTintFilter = updateTintFilterInternal(this.mTintFilter, sprState.mTint, sprState.mTintMode);
        }
        if (resources != null) {
            updateLocalState(resources);
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        stop();
        if (this.mCacheBitmap != null) {
            this.mState.mCacheManager.unlock(this.mCacheBitmap);
            this.mCacheDensityDpi = 0;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int intrinsicWidth;
        int intrinsicHeight;
        Canvas canvas2;
        if (this.mDstRect.width() <= 0 || this.mDstRect.height() <= 0 || this.mDocument == null) {
            return;
        }
        boolean z = true;
        if (this.mState.mTileModeX == null && this.mState.mTileModeY == null) {
            canvas.getMatrix(this.mTmpMatrix);
            this.mTmpMatrix.getValues(this.mTmpFloats);
            float abs = Math.abs(this.mTmpFloats[0]);
            float abs2 = Math.abs(this.mTmpFloats[4]);
            float[] fArr = this.mTmpFloats;
            if (fArr[1] == 0.0f && fArr[3] == 0.0f) {
                intrinsicWidth = Math.min(2048, (int) (this.mDstRect.width() * abs));
                intrinsicHeight = Math.min(2048, (int) (this.mDstRect.height() * abs2));
            } else {
                Bitmap bitmap = this.mCacheBitmap;
                if (bitmap != null) {
                    intrinsicWidth = bitmap.getWidth();
                    intrinsicHeight = this.mCacheBitmap.getHeight();
                } else {
                    intrinsicWidth = this.mDstRect.width();
                    intrinsicHeight = this.mDstRect.height();
                }
            }
        } else {
            intrinsicWidth = getIntrinsicWidth();
            intrinsicHeight = getIntrinsicHeight();
        }
        int i = intrinsicWidth;
        int i2 = intrinsicHeight;
        if (i <= 0 || i2 <= 0) {
            return;
        }
        boolean isRunning = isRunning();
        Paint paint = this.mState.mBitmapPaint;
        synchronized (this.mState) {
            if (this.mState.mNinePatch) {
                if (this.mState.mNinePatchRenderer == null) {
                    this.mState.createNinePatchRenderer();
                }
            } else if (isRunning) {
                int animationIndex = this.mSprAnimation.getAnimationIndex();
                synchronized (this.mDocument) {
                    this.mDocument.preDraw(animationIndex);
                    Bitmap bitmap2 = this.mAnimationBitmap;
                    if (bitmap2 != null && bitmap2.getWidth() == i && this.mAnimationBitmap.getHeight() == i2) {
                        canvas2 = new Canvas(this.mAnimationBitmap);
                        canvas2.drawColor(0, PorterDuff.Mode.CLEAR);
                        this.mDocument.draw(canvas2, i, i2, animationIndex, this.mState.mDensityDpi);
                    }
                    this.mAnimationBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                    canvas2 = new Canvas(this.mAnimationBitmap);
                    this.mDocument.draw(canvas2, i, i2, animationIndex, this.mState.mDensityDpi);
                }
            } else {
                updateCachedBitmap(i, i2, this.mState.mDensityDpi);
            }
            if (this.mState.mRebuildShader || isRunning) {
                if (this.mState.mTileModeX == null && this.mState.mTileModeY == null) {
                    paint.setShader(null);
                } else {
                    Shader.TileMode tileMode = this.mState.mTileModeX;
                    Shader.TileMode tileMode2 = this.mState.mTileModeY;
                    Bitmap bitmap3 = this.mAnimationBitmap;
                    if (bitmap3 == null) {
                        bitmap3 = this.mCacheBitmap;
                    }
                    if (tileMode == null) {
                        tileMode = Shader.TileMode.CLAMP;
                    }
                    if (tileMode2 == null) {
                        tileMode2 = Shader.TileMode.CLAMP;
                    }
                    paint.setShader(new BitmapShader(bitmap3, tileMode, tileMode2));
                }
                this.mState.mRebuildShader = false;
            }
        }
        if (this.mTintFilter == null || paint.getColorFilter() != null) {
            z = false;
        } else {
            paint.setColorFilter(this.mTintFilter);
        }
        Shader shader = paint.getShader();
        boolean needMirroring = needMirroring();
        if (shader == null) {
            synchronized (this.mState) {
                if (this.mState.mNinePatch) {
                    if (this.mState.mNinePatchRenderer != null) {
                        this.mState.mNinePatchRenderer.draw(canvas, this.mDstRect, paint);
                    }
                } else {
                    if (needMirroring) {
                        canvas.save();
                        canvas.translate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                        canvas.scale(-1.0f, 1.0f);
                    }
                    Bitmap bitmap4 = this.mCacheBitmap;
                    if (bitmap4 != null) {
                        Bitmap bitmap5 = this.mAnimationBitmap;
                        if (bitmap5 != null) {
                            bitmap4 = bitmap5;
                        }
                        canvas.drawBitmap(bitmap4, (Rect) null, this.mDstRect, paint);
                    }
                    if (isRunning) {
                        this.mSprAnimation.update();
                    }
                    if (needMirroring) {
                        canvas.restore();
                    }
                }
            }
        } else {
            if (needMirroring) {
                if (this.mMirrorMatrix == null) {
                    this.mMirrorMatrix = new Matrix();
                }
                this.mMirrorMatrix.setTranslate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                this.mMirrorMatrix.preScale(-1.0f, 1.0f);
                shader.setLocalMatrix(this.mMirrorMatrix);
                paint.setShader(shader);
            } else if (this.mMirrorMatrix != null) {
                this.mMirrorMatrix = null;
                if (this.mIdentityMatrix == null) {
                    this.mIdentityMatrix = new Matrix();
                }
                shader.setLocalMatrix(this.mIdentityMatrix);
                paint.setShader(shader);
            }
            canvas.drawRect(this.mDstRect, paint);
        }
        if (z) {
            paint.setColorFilter(null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        SprState sprState = this.mState;
        return sprState != null && sprState.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mState.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mState.getIntrinsicHeight();
    }

    public Bitmap getBitmap() {
        updateCachedBitmap(getIntrinsicWidth(), getIntrinsicHeight(), this.mState.mDensityDpi);
        return this.mCacheBitmap;
    }

    public int getGravity() {
        return this.mState.mGravity;
    }

    public void setGravity(int i) {
        if (this.mState.mGravity != i) {
            this.mState.mGravity = i;
            updateDstRectAndInsetsIfDirty();
            invalidateSelf();
        }
    }

    private void updateDstRectAndInsetsIfDirty() {
        if (this.mState.mTileModeX == null && this.mState.mTileModeY == null) {
            try {
                Gravity.apply(this.mState.mGravity, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), this.mDstRect, ((Integer) mGetLayoutDirection.invoke(this, null)).intValue());
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        copyBounds(this.mDstRect);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateDstRectAndInsetsIfDirty();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        if (this.mDocument == null) {
            rect.set(0, 0, 0, 0);
            return false;
        }
        float densityScale = this.mState.getDensityScale();
        rect.set(Math.round(this.mDocument.mPaddingLeft * densityScale), Math.round(this.mDocument.mPaddingTop * densityScale), Math.round(this.mDocument.mPaddingRight * densityScale), Math.round(this.mDocument.mPaddingBottom * densityScale));
        return (this.mDocument.mPaddingLeft == 0.0f || this.mDocument.mPaddingTop == 0.0f || this.mDocument.mPaddingRight == 0.0f || this.mDocument.mPaddingBottom == 0.0f) ? false : true;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Bitmap bitmap;
        return (this.mState.mGravity == 119 && (bitmap = this.mCacheBitmap) != null && !bitmap.hasAlpha() && this.mState.mBitmapPaint.getAlpha() >= 255) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mState.mBitmapPaint.getAlpha()) {
            this.mState.mBitmapPaint.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mState.mBitmapPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mState.mBitmapPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mState.mBitmapPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        SprState sprState = this.mState;
        sprState.mTint = colorStateList;
        this.mTintFilter = updateTintFilterInternal(this.mTintFilter, sprState.mTint, sprState.mTintMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        SprState sprState = this.mState;
        sprState.mTintMode = mode;
        this.mTintFilter = updateTintFilterInternal(this.mTintFilter, sprState.mTint, sprState.mTintMode);
        invalidateSelf();
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

    public Shader.TileMode getTileModeX() {
        return this.mState.mTileModeX;
    }

    public Shader.TileMode getTileModeY() {
        return this.mState.mTileModeY;
    }

    public void setTileModeX(Shader.TileMode tileMode) {
        setTileModeXY(tileMode, this.mState.mTileModeY);
    }

    public final void setTileModeY(Shader.TileMode tileMode) {
        setTileModeXY(this.mState.mTileModeX, tileMode);
    }

    public void setTileModeXY(Shader.TileMode tileMode, Shader.TileMode tileMode2) {
        SprState sprState = this.mState;
        if (sprState.mTileModeX == tileMode && sprState.mTileModeY == tileMode2) {
            return;
        }
        sprState.mTileModeX = tileMode;
        sprState.mTileModeY = tileMode2;
        sprState.mRebuildShader = true;
        updateDstRectAndInsetsIfDirty();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        if (super.isStateful()) {
            return true;
        }
        SprState sprState = this.mState;
        return (sprState == null || sprState.mTint == null || !this.mState.mTint.isStateful()) ? false : true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        SprState sprState = this.mState;
        if (sprState.mTint == null || sprState.mTintMode == null) {
            return false;
        }
        this.mTintFilter = updateTintFilterInternal(this.mTintFilter, sprState.mTint, sprState.mTintMode);
        invalidateSelf();
        return true;
    }

    PorterDuffColorFilter updateTintFilterInternal(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter2;
        Method method = mUpdateTintFilter;
        if (method == null) {
            return updateTintFilter(porterDuffColorFilter, colorStateList, mode);
        }
        method.setAccessible(true);
        try {
            porterDuffColorFilter2 = (PorterDuffColorFilter) method.invoke(this, porterDuffColorFilter, colorStateList, mode);
        } catch (Exception unused) {
            porterDuffColorFilter2 = null;
        }
        mUpdateTintFilter.setAccessible(false);
        return porterDuffColorFilter2;
    }

    private static SprDocument createFromStreamInternal(String str, InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bArr = new byte[3];
        if (str == null) {
            str = NA_NAME;
        }
        bufferedInputStream.mark(3);
        if (bufferedInputStream.read(bArr) < 3) {
            bufferedInputStream.close();
            throw new IOException("file is too short");
        }
        bufferedInputStream.reset();
        byte b = bArr[0];
        if ((b == 83 && bArr[1] == 86 && bArr[2] == 70) || (b == 83 && bArr[1] == 80 && bArr[2] == 82)) {
            return new SprDocument(str, bufferedInputStream);
        }
        try {
            XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            XmlPullParser newPullParser = newInstance.newPullParser();
            newPullParser.setInput(bufferedInputStream, null);
            return new SprDocument(str, newPullParser);
        } catch (XmlPullParserException e) {
            throw new IOException(e.getCause());
        }
    }

    private static SemPathRenderingDrawable getErrorDrawable(String str) {
        float f = 350;
        float f2 = 275;
        SprDocument sprDocument = new SprDocument(str, 0.0f, 0.0f, f, f2);
        float f3 = 50;
        float f4 = 200;
        SprObjectShapeRectangle sprObjectShapeRectangle = new SprObjectShapeRectangle(0.0f, 0.0f, f3, f4);
        sprObjectShapeRectangle.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 200, 200, 200)));
        sprDocument.appendObject(sprObjectShapeRectangle);
        float f5 = 100;
        SprObjectShapeRectangle sprObjectShapeRectangle2 = new SprObjectShapeRectangle(f3, 0.0f, f5, f4);
        sprObjectShapeRectangle2.appendAttribute(new SprAttributeFill((byte) 1, -256));
        sprDocument.appendObject(sprObjectShapeRectangle2);
        float f6 = 150;
        SprObjectShapeRectangle sprObjectShapeRectangle3 = new SprObjectShapeRectangle(f5, 0.0f, f6, f4);
        sprObjectShapeRectangle3.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 255, 255)));
        sprDocument.appendObject(sprObjectShapeRectangle3);
        SprObjectShapeRectangle sprObjectShapeRectangle4 = new SprObjectShapeRectangle(f6, 0.0f, f4, f4);
        sprObjectShapeRectangle4.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 255, 0)));
        sprDocument.appendObject(sprObjectShapeRectangle4);
        float f7 = 250;
        SprObjectShapeRectangle sprObjectShapeRectangle5 = new SprObjectShapeRectangle(f4, 0.0f, f7, f4);
        sprObjectShapeRectangle5.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 255, 0, 255)));
        sprDocument.appendObject(sprObjectShapeRectangle5);
        float f8 = 300;
        SprObjectShapeRectangle sprObjectShapeRectangle6 = new SprObjectShapeRectangle(f7, 0.0f, f8, f4);
        sprObjectShapeRectangle6.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 255, 0, 0)));
        sprDocument.appendObject(sprObjectShapeRectangle6);
        SprObjectShapeRectangle sprObjectShapeRectangle7 = new SprObjectShapeRectangle(f8, 0.0f, f, f4);
        sprObjectShapeRectangle7.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 255)));
        sprDocument.appendObject(sprObjectShapeRectangle7);
        float f9 = 225;
        SprObjectShapeRectangle sprObjectShapeRectangle8 = new SprObjectShapeRectangle(0.0f, f4, f3, f9);
        sprObjectShapeRectangle8.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 255)));
        sprDocument.appendObject(sprObjectShapeRectangle8);
        SprObjectShapeRectangle sprObjectShapeRectangle9 = new SprObjectShapeRectangle(f3, f4, f5, f9);
        sprObjectShapeRectangle9.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 0)));
        sprDocument.appendObject(sprObjectShapeRectangle9);
        SprObjectShapeRectangle sprObjectShapeRectangle10 = new SprObjectShapeRectangle(f5, f4, f6, f9);
        sprObjectShapeRectangle10.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 255, 0, 255)));
        sprDocument.appendObject(sprObjectShapeRectangle10);
        SprObjectShapeRectangle sprObjectShapeRectangle11 = new SprObjectShapeRectangle(f6, f4, f4, f9);
        sprObjectShapeRectangle11.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 0)));
        sprDocument.appendObject(sprObjectShapeRectangle11);
        SprObjectShapeRectangle sprObjectShapeRectangle12 = new SprObjectShapeRectangle(f4, f4, f7, f9);
        sprObjectShapeRectangle12.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 255, 255)));
        sprDocument.appendObject(sprObjectShapeRectangle12);
        SprObjectShapeRectangle sprObjectShapeRectangle13 = new SprObjectShapeRectangle(f7, f4, f8, f9);
        sprObjectShapeRectangle13.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 0)));
        sprDocument.appendObject(sprObjectShapeRectangle13);
        SprObjectShapeRectangle sprObjectShapeRectangle14 = new SprObjectShapeRectangle(f8, f4, f, f9);
        sprObjectShapeRectangle14.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 200, 200, 200)));
        sprDocument.appendObject(sprObjectShapeRectangle14);
        SprObjectShapeRectangle sprObjectShapeRectangle15 = new SprObjectShapeRectangle(0.0f, f9, f, f2);
        SprLinearGradient sprLinearGradient = new SprLinearGradient();
        sprLinearGradient.spreadMode = (byte) 1;
        sprLinearGradient.x1 = 0.0f;
        sprLinearGradient.y1 = f9;
        sprLinearGradient.x2 = f;
        sprLinearGradient.y2 = f9;
        sprLinearGradient.colors = new int[]{-1, -16777216};
        sprLinearGradient.positions = new float[]{0.0f, 1.0f};
        sprLinearGradient.updateGradient();
        sprObjectShapeRectangle15.appendAttribute(new SprAttributeFill((byte) 3, sprLinearGradient));
        sprDocument.appendObject(sprObjectShapeRectangle15);
        return new SemPathRenderingDrawable(sprDocument) { // from class: com.samsung.android.graphics.spr.SemPathRenderingDrawable.1
            @Override // com.samsung.android.graphics.spr.SemPathRenderingDrawable, android.graphics.drawable.Drawable
            public void draw(Canvas canvas) {
                super.draw(canvas);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextSize(20.0f);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(-16777216);
                paint.setStrokeWidth(4.0f);
                Paint paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setTextSize(20.0f);
                paint2.setStyle(Paint.Style.FILL);
                paint2.setColor(-1);
                canvas.drawText(this.mDocument.mName, 5.0f, 40.0f, paint);
                canvas.drawText(this.mDocument.mName, 5.0f, 40.0f, paint2);
            }
        };
    }

    @Deprecated
    public static SemPathRenderingDrawable createFromStream(InputStream inputStream) throws IOException {
        return createFromStream(NA_NAME, inputStream);
    }

    public static SemPathRenderingDrawable createFromStream(String str, InputStream inputStream) throws IOException {
        return createFromStream(str, inputStream, null);
    }

    public static SemPathRenderingDrawable createFromStream(String str, InputStream inputStream, Resources resources) throws IOException {
        try {
            SemPathRenderingDrawable semPathRenderingDrawable = new SemPathRenderingDrawable(createFromStreamInternal(str, inputStream));
            if (resources != null) {
                semPathRenderingDrawable.updateLocalState(resources);
            }
            return semPathRenderingDrawable;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorDrawable(str);
        }
    }

    public static SemPathRenderingDrawable createFromResourceStream(Resources resources, int i) {
        InputStream inputStream;
        try {
            inputStream = resources.openRawResource(i);
            try {
                SprDocument createFromStreamInternal = createFromStreamInternal(resources.getString(i), inputStream);
                inputStream.close();
                SemPathRenderingDrawable semPathRenderingDrawable = new SemPathRenderingDrawable(createFromStreamInternal);
                semPathRenderingDrawable.updateLocalState(resources);
                return semPathRenderingDrawable;
            } catch (Exception e) {
                e = e;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                e.printStackTrace();
                return getErrorDrawable(resources.getString(i));
            }
        } catch (Exception e3) {
            e = e3;
            inputStream = null;
        }
    }

    public static SemPathRenderingDrawable createFromPathName(String str) {
        FileInputStream fileInputStream;
        Exception e;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                SprDocument createFromStreamInternal = createFromStreamInternal(str, fileInputStream);
                fileInputStream.close();
                return new SemPathRenderingDrawable(createFromStreamInternal);
            } catch (Exception e2) {
                e = e2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                e.printStackTrace();
                return getErrorDrawable(str);
            }
        } catch (Exception e4) {
            fileInputStream = null;
            e = e4;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations |= getChangingConfigurations();
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    protected static TypedArray sprObtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, iArr);
        }
        return theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) throws XmlPullParserException, IOException {
        Resources resources = typedArray.getResources();
        SprState sprState = this.mState;
        sprState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        InputStream inputStream = null;
        try {
            sprState.mThemeAttrs = (int[]) mExtractThemeAttrs.invoke(typedArray, null);
        } catch (Exception e) {
            sprState.mThemeAttrs = null;
            e.printStackTrace();
        }
        int resourceId = typedArray.getResourceId(mBitmapDrawable_src, 0);
        if (resourceId != 0) {
            try {
                InputStream openRawResource = resources.openRawResource(resourceId);
                try {
                    this.mState.setDocument(createFromStreamInternal(resources.getString(resourceId), openRawResource));
                    this.mDocument = this.mState.mDocument;
                    openRawResource.close();
                } catch (Exception e2) {
                    e = e2;
                    inputStream = openRawResource;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw new IOException(e);
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
        int i = typedArray.getInt(mBitmapDrawable_tintMode, -1);
        if (i != -1) {
            try {
                this.mState.mTintMode = (PorterDuff.Mode) mParseTintMode.invoke(null, Integer.valueOf(i), PorterDuff.Mode.SRC_IN);
            } catch (Exception e5) {
                this.mState.mTintMode = PorterDuff.Mode.SRC_IN;
                e5.printStackTrace();
            }
        }
        ColorStateList colorStateList = typedArray.getColorStateList(mBitmapDrawable_tint);
        if (colorStateList != null) {
            this.mState.mTint = colorStateList;
        }
        this.mState.mGravity = typedArray.getInt(mBitmapDrawable_gravity, 119);
        SprState sprState2 = this.mState;
        sprState2.mAutoMirrored = typedArray.getBoolean(mBitmapDrawable_autoMirrored, sprState2.mAutoMirrored);
        this.mState.mBitmapPaint.setAlpha((int) (typedArray.getFloat(mBitmapDrawable_alpha, 1.0f) * 255.0f));
        int i2 = typedArray.getInt(mBitmapDrawable_tileMode, -2);
        if (i2 != -2) {
            Shader.TileMode parseTileMode = parseTileMode(i2);
            setTileModeXY(parseTileMode, parseTileMode);
        }
        int i3 = typedArray.getInt(mBitmapDrawable_tileModeX, -2);
        if (i3 != -2) {
            setTileModeX(parseTileMode(i3));
        }
        int i4 = typedArray.getInt(mBitmapDrawable_tileModeY, -2);
        if (i4 != -2) {
            setTileModeY(parseTileMode(i4));
        }
        updateDensity(resources);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0024, code lost:
    
        if (r1 != null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0032, code lost:
    
        r1.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0030, code lost:
    
        if (r1 == null) goto L39;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void applyTheme(android.content.res.Resources.Theme r6) {
        /*
            r5 = this;
            super.applyTheme(r6)
            com.samsung.android.graphics.spr.SemPathRenderingDrawable$SprState r0 = r5.mState
            if (r0 != 0) goto L8
            return
        L8:
            int[] r1 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.SprState.m9202$$Nest$fgetmThemeAttrs(r0)
            r2 = 0
            if (r1 == 0) goto L43
            java.lang.reflect.Method r1 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.mResolveAttributes     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f org.xmlpull.v1.XmlPullParserException -> L36
            int[] r3 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.SprState.m9202$$Nest$fgetmThemeAttrs(r0)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f org.xmlpull.v1.XmlPullParserException -> L36
            int[] r4 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.mStyleableBitmapDrawable     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f org.xmlpull.v1.XmlPullParserException -> L36
            java.lang.Object[] r3 = new java.lang.Object[]{r3, r4}     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f org.xmlpull.v1.XmlPullParserException -> L36
            java.lang.Object r1 = r1.invoke(r6, r3)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f org.xmlpull.v1.XmlPullParserException -> L36
            android.content.res.TypedArray r1 = (android.content.res.TypedArray) r1     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f org.xmlpull.v1.XmlPullParserException -> L36
            r5.updateStateFromTypedArray(r1)     // Catch: java.lang.Throwable -> L27 org.xmlpull.v1.XmlPullParserException -> L2a java.lang.Exception -> L30
            if (r1 == 0) goto L43
            goto L32
        L27:
            r5 = move-exception
            r2 = r1
            goto L3d
        L2a:
            r5 = move-exception
            r2 = r1
            goto L37
        L2d:
            r5 = move-exception
            goto L3d
        L2f:
            r1 = r2
        L30:
            if (r1 == 0) goto L43
        L32:
            r1.recycle()
            goto L43
        L36:
            r5 = move-exception
        L37:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L2d
            r6.<init>(r5)     // Catch: java.lang.Throwable -> L2d
            throw r6     // Catch: java.lang.Throwable -> L2d
        L3d:
            if (r2 == 0) goto L42
            r2.recycle()
        L42:
            throw r5
        L43:
            java.lang.reflect.Method r1 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.mCanApplyTheme     // Catch: java.lang.Exception -> L54
            android.content.res.ColorStateList r3 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.SprState.m9205$$Nest$fgetmTint(r0)     // Catch: java.lang.Exception -> L54
            java.lang.Object r1 = r1.invoke(r3, r2)     // Catch: java.lang.Exception -> L54
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch: java.lang.Exception -> L54
            boolean r1 = r1.booleanValue()     // Catch: java.lang.Exception -> L54
            goto L55
        L54:
            r1 = 0
        L55:
            android.content.res.ColorStateList r2 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.SprState.m9205$$Nest$fgetmTint(r0)
            if (r2 == 0) goto L70
            if (r1 == 0) goto L70
            java.lang.reflect.Method r1 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.mObtainForTheme     // Catch: java.lang.Exception -> L70
            android.content.res.ColorStateList r2 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.SprState.m9205$$Nest$fgetmTint(r0)     // Catch: java.lang.Exception -> L70
            java.lang.Object[] r3 = new java.lang.Object[]{r6}     // Catch: java.lang.Exception -> L70
            java.lang.Object r1 = r1.invoke(r2, r3)     // Catch: java.lang.Exception -> L70
            android.content.res.ColorStateList r1 = (android.content.res.ColorStateList) r1     // Catch: java.lang.Exception -> L70
            com.samsung.android.graphics.spr.SemPathRenderingDrawable.SprState.m9217$$Nest$fputmTint(r0, r1)     // Catch: java.lang.Exception -> L70
        L70:
            android.content.res.Resources r6 = r6.getResources()
            r5.updateLocalState(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.graphics.spr.SemPathRenderingDrawable.applyTheme(android.content.res.Resources$Theme):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0014, code lost:
    
        if (r2 != null) goto L5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0017, code lost:
    
        updateLocalState(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001a, code lost:
    
        return;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void inflate(android.content.res.Resources r1, org.xmlpull.v1.XmlPullParser r2, android.util.AttributeSet r3, android.content.res.Resources.Theme r4) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r0 = this;
            super.inflate(r1, r2, r3, r4)
            int[] r2 = com.samsung.android.graphics.spr.SemPathRenderingDrawable.mStyleableBitmapDrawable
            android.content.res.TypedArray r2 = sprObtainAttributes(r1, r4, r3, r2)
            r0.updateStateFromTypedArray(r2)     // Catch: java.lang.Throwable -> L12 java.lang.Exception -> L14 org.xmlpull.v1.XmlPullParserException -> L1b
            if (r2 == 0) goto L17
        Le:
            r2.recycle()
            goto L17
        L12:
            r0 = move-exception
            goto L22
        L14:
            if (r2 == 0) goto L17
            goto Le
        L17:
            r0.updateLocalState(r1)
            return
        L1b:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L12
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L12
            throw r1     // Catch: java.lang.Throwable -> L12
        L22:
            if (r2 == 0) goto L27
            r2.recycle()
        L27:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.graphics.spr.SemPathRenderingDrawable.inflate(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):void");
    }

    public String toString() {
        if (this.mDocument == null) {
            return "SprDocument is null";
        }
        return this.mDocument.mLeft + "," + this.mDocument.mTop + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mDocument.mRight + "," + this.mDocument.mBottom + "\nLoading:" + this.mDocument.getLoadingTime() + "ms\nElement:" + this.mDocument.getTotalElementCount() + "\nSegment:" + this.mDocument.getTotalSegmentCount() + "\nAttribute:" + this.mDocument.getTotalAttributeCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDeviceDensityDpi(Resources resources) {
        if (resources == null) {
            return Resources.getSystem().getDisplayMetrics().densityDpi;
        }
        return resources.getDisplayMetrics().densityDpi;
    }

    public void toSPR(OutputStream outputStream) throws IOException {
        SprDocument sprDocument = this.mDocument;
        if (sprDocument != null) {
            sprDocument.toSPR(outputStream);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new SprState(this.mState);
            this.mMutated = true;
        }
        return this;
    }

    private void updateLocalState(Resources resources) {
        setTintList(this.mState.mTint);
        updateDensity(resources);
        if (this.mDocument != null) {
            float densityScale = this.mState.getDensityScale();
            super.setBounds(Math.round(this.mDocument.mLeft * densityScale), Math.round(this.mDocument.mTop * densityScale), Math.round(this.mDocument.mRight * densityScale), Math.round(this.mDocument.mBottom * densityScale));
        }
    }

    private void updateDensity(Resources resources) {
        int deviceDensityDpi = getDeviceDensityDpi(resources);
        if (this.mState.mDensityDpi != deviceDensityDpi) {
            this.mState.mDensityDpi = deviceDensityDpi;
            if (this.mCacheBitmap != null) {
                this.mState.mCacheManager.unlock(this.mCacheBitmap);
                this.mCacheBitmap = null;
                this.mCacheDensityDpi = 0;
            }
            this.mState.mNinePatchRenderer = null;
            this.mState.mNinePatchBitmap = null;
        }
    }

    static final class SprState extends Drawable.ConstantState {
        private boolean mAutoMirrored;
        private final Paint mBitmapPaint;
        private SprCacheManager mCacheManager;
        private int mChangingConfigurations;
        private int mDensityDpi;
        private SprDocument mDocument;
        private int mGravity;
        private SprFileAttributeNinePatch mMultiNinePatch;
        private boolean mNinePatch;
        private Bitmap mNinePatchBitmap;
        private NinePatch mNinePatchRenderer;
        private boolean mRebuildShader;
        private int[] mThemeAttrs;
        private Shader.TileMode mTileModeX;
        private Shader.TileMode mTileModeY;
        private ColorStateList mTint;
        private PorterDuff.Mode mTintMode;

        SprState(SprDocument sprDocument) {
            this.mDocument = null;
            this.mThemeAttrs = null;
            this.mNinePatch = false;
            this.mMultiNinePatch = null;
            this.mDensityDpi = 0;
            this.mCacheManager = null;
            this.mNinePatchRenderer = null;
            this.mNinePatchBitmap = null;
            this.mTint = null;
            this.mTintMode = PorterDuff.Mode.SRC_IN;
            this.mAutoMirrored = false;
            this.mGravity = 119;
            this.mRebuildShader = false;
            this.mTileModeX = null;
            this.mTileModeY = null;
            setDocument(sprDocument);
            Paint paint = new Paint();
            this.mBitmapPaint = paint;
            paint.setFilterBitmap(true);
        }

        SprState(SprState sprState) {
            this.mDocument = null;
            this.mThemeAttrs = null;
            this.mNinePatch = false;
            this.mMultiNinePatch = null;
            this.mDensityDpi = 0;
            this.mCacheManager = null;
            this.mNinePatchRenderer = null;
            this.mNinePatchBitmap = null;
            this.mTint = null;
            this.mTintMode = PorterDuff.Mode.SRC_IN;
            this.mAutoMirrored = false;
            this.mGravity = 119;
            this.mRebuildShader = false;
            this.mTileModeX = null;
            this.mTileModeY = null;
            this.mDocument = sprState.mDocument;
            this.mThemeAttrs = sprState.mThemeAttrs;
            this.mNinePatch = sprState.mNinePatch;
            this.mBitmapPaint = new Paint(sprState.mBitmapPaint);
            if (sprState.mNinePatch && sprState.mNinePatchRenderer == null) {
                sprState.createNinePatchRenderer();
            }
            this.mCacheManager = sprState.mCacheManager;
            this.mNinePatchBitmap = sprState.mNinePatchBitmap;
            this.mNinePatchRenderer = sprState.mNinePatchRenderer;
            this.mMultiNinePatch = sprState.mMultiNinePatch;
            this.mTint = sprState.mTint;
            this.mTintMode = sprState.mTintMode;
            this.mAutoMirrored = sprState.mAutoMirrored;
            this.mGravity = sprState.mGravity;
            this.mChangingConfigurations = sprState.mChangingConfigurations;
            this.mRebuildShader = sprState.mRebuildShader;
            this.mTileModeX = sprState.mTileModeX;
            this.mTileModeY = sprState.mTileModeY;
            this.mDensityDpi = sprState.mDensityDpi;
        }

        public void setDocument(SprDocument sprDocument) {
            if (sprDocument == null) {
                return;
            }
            SprDocument sprDocument2 = this.mDocument;
            if (sprDocument2 == null || !(sprDocument2.mName == null || this.mDocument.mName.equals(sprDocument.mName))) {
                this.mDocument = sprDocument;
                int i = 0;
                this.mNinePatch = (sprDocument.mNinePatchLeft == 0.0f && this.mDocument.mNinePatchTop == 0.0f && this.mDocument.mNinePatchRight == 0.0f && this.mDocument.mNinePatchBottom == 0.0f) ? false : true;
                while (true) {
                    if (i < this.mDocument.getFileAttributeSize()) {
                        SprFileAttributeNinePatch sprFileAttributeNinePatch = (SprFileAttributeNinePatch) this.mDocument.getFileAttribute(i);
                        if (sprFileAttributeNinePatch != null && sprFileAttributeNinePatch.mType == 1) {
                            this.mNinePatch = true;
                            this.mMultiNinePatch = sprFileAttributeNinePatch;
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                this.mDensityDpi = SemPathRenderingDrawable.getDeviceDensityDpi(null);
                if (this.mCacheManager != null) {
                    if (SprDebug.IsDebug) {
                        this.mCacheManager.printDebug();
                        new Exception().printStackTrace();
                    }
                    this.mCacheManager = null;
                }
                this.mCacheManager = new SprCacheManager(this.mDocument.mName, this.mDocument.hashCode());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void createNinePatchRenderer() {
            if (this.mNinePatchRenderer != null || this.mDocument == null) {
                return;
            }
            int intrinsicWidth = getIntrinsicWidth();
            int intrinsicHeight = getIntrinsicHeight();
            synchronized (this.mDocument) {
                if (!this.mDocument.isPredraw()) {
                    this.mDocument.preDraw(0);
                }
                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                this.mNinePatchBitmap = createBitmap;
                if (createBitmap != null) {
                    this.mDocument.draw(new Canvas(this.mNinePatchBitmap), intrinsicWidth, intrinsicHeight, 0, this.mDensityDpi);
                }
            }
            if (this.mNinePatch && this.mMultiNinePatch == null) {
                float densityScale = getDensityScale();
                int round = Math.round(this.mDocument.mNinePatchLeft * densityScale);
                int round2 = Math.round(this.mDocument.mNinePatchTop * densityScale);
                int round3 = intrinsicWidth - Math.round(this.mDocument.mNinePatchRight * densityScale);
                int round4 = intrinsicHeight - Math.round(this.mDocument.mNinePatchBottom * densityScale);
                if (round3 <= round) {
                    round3 = round + 1;
                }
                if (round4 <= round2) {
                    round4 = round2 + 1;
                }
                this.mNinePatchRenderer = new NinePatch(this.mNinePatchBitmap, getNinePatchChunk(round, round2, round3, round4).array());
                return;
            }
            this.mNinePatchRenderer = new NinePatch(this.mNinePatchBitmap, getNinePatchChunk(this.mMultiNinePatch).array());
        }

        public int getIntrinsicWidth() {
            float densityScale = getDensityScale();
            SprDocument sprDocument = this.mDocument;
            if (sprDocument != null) {
                return Math.round(sprDocument.mRight * densityScale) - Math.round(this.mDocument.mLeft * densityScale);
            }
            return 0;
        }

        public int getIntrinsicHeight() {
            float densityScale = getDensityScale();
            SprDocument sprDocument = this.mDocument;
            if (sprDocument != null) {
                return Math.round(sprDocument.mBottom * densityScale) - Math.round(this.mDocument.mTop * densityScale);
            }
            return 0;
        }

        public float getDensityScale() {
            float f;
            float f2;
            SprDocument sprDocument = this.mDocument;
            if (sprDocument == null) {
                f = this.mDensityDpi / 160.0f;
                f2 = 3.0f;
            } else {
                f = this.mDensityDpi / 160.0f;
                f2 = sprDocument.mDensity;
            }
            return f / f2;
        }

        private ByteBuffer getNinePatchChunk(int i, int i2, int i3, int i4) {
            ByteBuffer order = ByteBuffer.allocate(84).order(ByteOrder.nativeOrder());
            order.put((byte) 1);
            order.put((byte) 2);
            order.put((byte) 2);
            order.put((byte) 9);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(i);
            order.putInt(i3);
            order.putInt(i2);
            order.putInt(i4);
            order.putInt(1);
            order.putInt(1);
            order.putInt(1);
            order.putInt(1);
            order.putInt(1);
            order.putInt(1);
            order.putInt(1);
            order.putInt(1);
            order.putInt(1);
            return order;
        }

        private ByteBuffer getNinePatchChunk(SprFileAttributeNinePatch sprFileAttributeNinePatch) {
            float densityScale = getDensityScale();
            int[] iArr = new int[sprFileAttributeNinePatch.xSize];
            int[] iArr2 = new int[sprFileAttributeNinePatch.xSize];
            int[] iArr3 = new int[sprFileAttributeNinePatch.ySize];
            int[] iArr4 = new int[sprFileAttributeNinePatch.ySize];
            int i = -1;
            int i2 = 0;
            int i3 = 0;
            int i4 = -1;
            while (i2 < sprFileAttributeNinePatch.xSize) {
                int round = Math.round(sprFileAttributeNinePatch.xStart[i2] * densityScale);
                int round2 = Math.round(sprFileAttributeNinePatch.xEnd[i2] * densityScale);
                if (round2 <= round) {
                    round2 = round + 1;
                }
                if (round <= i4) {
                    iArr2[i3 - 1] = round2;
                } else {
                    iArr[i3] = round;
                    iArr2[i3] = round2;
                    i3++;
                }
                i2++;
                i4 = round2;
            }
            int i5 = 0;
            int i6 = 0;
            while (i5 < sprFileAttributeNinePatch.ySize) {
                int round3 = Math.round(sprFileAttributeNinePatch.yStart[i5] * densityScale);
                int round4 = Math.round(sprFileAttributeNinePatch.yEnd[i5] * densityScale);
                if (round4 <= round3) {
                    round4 = round3 + 1;
                }
                if (round3 <= i) {
                    iArr4[i6 - 1] = round4;
                } else {
                    iArr3[i6] = round3;
                    iArr4[i6] = round4;
                    i6++;
                }
                i5++;
                i = round4;
            }
            int i7 = ((i3 * 2) + 1) * ((i6 * 2) + 1);
            ByteBuffer order = ByteBuffer.allocate((i3 * 8) + 42 + (i6 * 8) + (i7 * 4)).order(ByteOrder.nativeOrder());
            order.put((byte) 1);
            order.put((byte) (sprFileAttributeNinePatch.xSize * 2));
            order.put((byte) (sprFileAttributeNinePatch.ySize * 2));
            order.put((byte) i7);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            order.putInt(0);
            for (int i8 = 0; i8 < i3; i8++) {
                order.putInt(iArr[i8]);
                order.putInt(iArr2[i8]);
            }
            for (int i9 = 0; i9 < i6; i9++) {
                order.putInt(iArr3[i9]);
                order.putInt(iArr4[i9]);
            }
            for (int i10 = 0; i10 < i7; i10++) {
                order.putInt(1);
            }
            return order;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null ? colorStateList.getChangingConfigurations() : 0) | i;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            boolean z;
            try {
                z = ((Boolean) SemPathRenderingDrawable.mCanApplyTheme.invoke(this.mTint, null)).booleanValue();
            } catch (Exception unused) {
                z = false;
            }
            return this.mThemeAttrs != null || (this.mTint != null && z);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new SemPathRenderingDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new SemPathRenderingDrawable(this, resources);
        }
    }

    public SprDocument getDocument() {
        return this.mDocument;
    }

    private boolean needMirroring() {
        return isAutoMirrored() && ((Integer) mGetLayoutDirection.invoke(this, null)).intValue() == 1;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.mState.mAutoMirrored != z) {
            this.mState.mAutoMirrored = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        stop();
        SprDocument sprDocument = this.mDocument;
        if (sprDocument == null) {
            return;
        }
        if (sprDocument.getFrameAnimationCount() > 1) {
            this.mSprAnimation = new SprDrawableAnimationFrame(this, this.mDocument);
        } else if (this.mDocument.getValueAnimationObjects().size() > 0) {
            if (this.mDocument.isIntrinsic()) {
                try {
                    this.mDocument = this.mDocument.m9220clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.mSprAnimation = new SprDrawableAnimationValue(this, this.mDocument);
        }
        SprDrawableAnimation sprDrawableAnimation = this.mSprAnimation;
        if (sprDrawableAnimation != null) {
            sprDrawableAnimation.start();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        SprDrawableAnimation sprDrawableAnimation = this.mSprAnimation;
        if (sprDrawableAnimation != null) {
            sprDrawableAnimation.stop();
            this.mSprAnimation = null;
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        SprDrawableAnimation sprDrawableAnimation = this.mSprAnimation;
        return sprDrawableAnimation != null && sprDrawableAnimation.isRunning();
    }

    private void updateCachedBitmap(int i, int i2, int i3) {
        SprDocument sprDocument = this.mDocument;
        if (sprDocument == null) {
            return;
        }
        synchronized (sprDocument) {
            Bitmap bitmap = this.mCacheBitmap;
            if (bitmap != null && !bitmap.isMutable() && this.mCacheBitmap.getWidth() == i && this.mCacheBitmap.getHeight() == i2 && this.mCacheDensityDpi == i3) {
                return;
            }
            if (this.mCacheBitmap != null) {
                this.mState.mCacheManager.unlock(this.mCacheBitmap);
                this.mCacheBitmap = null;
                this.mCacheDensityDpi = 0;
            }
            Bitmap cache = this.mState.mCacheManager.getCache(i, i2, i3);
            this.mCacheBitmap = cache;
            this.mCacheDensityDpi = i3;
            if (cache == null) {
                if (!this.mDocument.isPredraw()) {
                    this.mDocument.preDraw(0);
                }
                Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                this.mCacheBitmap = createBitmap;
                if (createBitmap != null) {
                    this.mDocument.draw(new Canvas(this.mCacheBitmap), i, i2, 0, this.mState.mDensityDpi);
                    this.mState.mCacheManager.addCache(this.mCacheBitmap, this.mCacheDensityDpi);
                }
            }
            this.mState.mCacheManager.lock(this.mCacheBitmap);
        }
    }

    private PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }
}
