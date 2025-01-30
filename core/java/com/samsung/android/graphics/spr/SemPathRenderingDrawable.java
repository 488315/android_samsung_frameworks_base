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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes5.dex */
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

    static {
        Method md = null;
        try {
            md = Drawable.class.getDeclaredMethod("updateTintFilter", PorterDuffColorFilter.class, ColorStateList.class, PorterDuff.Mode.class);
        } catch (Exception e) {
        }
        mUpdateTintFilter = md;
        try {
            md = Drawable.class.getMethod("parseTintMode", Integer.TYPE, PorterDuff.Mode.class);
        } catch (Exception e2) {
        }
        mParseTintMode = md;
        try {
            md = Drawable.class.getMethod("getLayoutDirection", new Class[0]);
        } catch (Exception e3) {
        }
        mGetLayoutDirection = md;
        try {
            md = TypedArray.class.getDeclaredMethod("extractThemeAttrs", new Class[0]);
        } catch (Exception e4) {
        }
        mExtractThemeAttrs = md;
        try {
            md = Resources.Theme.class.getDeclaredMethod("resolveAttributes", int[].class, int[].class);
        } catch (Exception e5) {
        }
        mResolveAttributes = md;
        try {
            md = ColorStateList.class.getDeclaredMethod("obtainForTheme", Resources.Theme.class);
        } catch (Exception e6) {
        }
        mObtainForTheme = md;
        try {
            md = ColorStateList.class.getDeclaredMethod("canApplyTheme", new Class[0]);
        } catch (Exception e7) {
        }
        mCanApplyTheme = md;
        try {
            Class clasz = Class.forName("com.android.internal.R$styleable");
            Field field = clasz.getDeclaredField("BitmapDrawable");
            mStyleableBitmapDrawable = (int[]) field.get(null);
            Field field2 = clasz.getDeclaredField("BitmapDrawable_src");
            mBitmapDrawable_src = field2.getInt(null);
            Field field3 = clasz.getDeclaredField("BitmapDrawable_alpha");
            mBitmapDrawable_alpha = field3.getInt(null);
            Field field4 = clasz.getDeclaredField("BitmapDrawable_autoMirrored");
            mBitmapDrawable_autoMirrored = field4.getInt(null);
            Field field5 = clasz.getDeclaredField("BitmapDrawable_gravity");
            mBitmapDrawable_gravity = field5.getInt(null);
            Field field6 = clasz.getDeclaredField("BitmapDrawable_tileMode");
            mBitmapDrawable_tileMode = field6.getInt(null);
            Field field7 = clasz.getDeclaredField("BitmapDrawable_tileModeX");
            mBitmapDrawable_tileModeX = field7.getInt(null);
            Field field8 = clasz.getDeclaredField("BitmapDrawable_tileModeY");
            mBitmapDrawable_tileModeY = field8.getInt(null);
            Field field9 = clasz.getDeclaredField("BitmapDrawable_tint");
            mBitmapDrawable_tint = field9.getInt(null);
            Field field10 = clasz.getDeclaredField("BitmapDrawable_tintMode");
            mBitmapDrawable_tintMode = field10.getInt(null);
        } catch (Exception e8) {
            e8.printStackTrace();
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

    public SemPathRenderingDrawable(SprDocument document) {
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
        SprState sprState = new SprState(document);
        this.mState = sprState;
        SprDocument sprDocument = sprState.mDocument;
        this.mDocument = sprDocument;
        if (sprDocument != null) {
            float densityScale = this.mState.getDensityScale();
            super.setBounds(Math.round(this.mDocument.mLeft * densityScale), Math.round(this.mDocument.mTop * densityScale), Math.round(this.mDocument.mRight * densityScale), Math.round(this.mDocument.mBottom * densityScale));
        }
    }

    public SemPathRenderingDrawable(SprState state, Resources res) {
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
        this.mState = state;
        SprDocument sprDocument = state.mDocument;
        this.mDocument = sprDocument;
        if (sprDocument != null) {
            float densityScale = this.mState.getDensityScale();
            super.setBounds(Math.round(this.mDocument.mLeft * densityScale), Math.round(this.mDocument.mTop * densityScale), Math.round(this.mDocument.mRight * densityScale), Math.round(this.mDocument.mBottom * densityScale));
            this.mTintFilter = updateTintFilterInternal(this.mTintFilter, state.mTint, state.mTintMode);
        }
        if (res != null) {
            updateLocalState(res);
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

    /* JADX WARN: Can't wrap try/catch for region: R(7:(2:102|103)|(4:(3:128|129|(8:134|135|107|108|109|110|111|112))|110|111|112)|105|106|107|108|109) */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0131, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0132, code lost:
    
        r17 = r11;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void draw(Canvas canvas) {
        int scaledHeight;
        int scaledHeight2;
        SprDocument sprDocument;
        Bitmap bitmap;
        Canvas tmpCanvas;
        boolean clearColorFilter;
        int scaledWidth;
        int scaledHeight3;
        if (this.mDstRect.width() <= 0 || this.mDstRect.height() <= 0 || this.mDocument == null) {
            return;
        }
        if (this.mState.mTileModeX == null && this.mState.mTileModeY == null) {
            canvas.getMatrix(this.mTmpMatrix);
            this.mTmpMatrix.getValues(this.mTmpFloats);
            float canvasScaleX = Math.abs(this.mTmpFloats[0]);
            float canvasScaleY = Math.abs(this.mTmpFloats[4]);
            float[] fArr = this.mTmpFloats;
            if (fArr[1] == 0.0f && fArr[3] == 0.0f) {
                int scaledWidth2 = (int) (this.mDstRect.width() * canvasScaleX);
                int scaledHeight4 = (int) (this.mDstRect.height() * canvasScaleY);
                scaledWidth = Math.min(2048, scaledWidth2);
                scaledHeight3 = Math.min(2048, scaledHeight4);
            } else {
                Bitmap bitmap2 = this.mCacheBitmap;
                if (bitmap2 != null) {
                    scaledWidth = bitmap2.getWidth();
                    scaledHeight3 = this.mCacheBitmap.getHeight();
                } else {
                    scaledWidth = this.mDstRect.width();
                    scaledHeight3 = this.mDstRect.height();
                }
            }
            scaledHeight = scaledHeight3;
            scaledHeight2 = scaledWidth;
        } else {
            int scaledWidth3 = getIntrinsicWidth();
            int scaledHeight5 = getIntrinsicHeight();
            scaledHeight = scaledHeight5;
            scaledHeight2 = scaledWidth3;
        }
        if (scaledHeight2 <= 0 || scaledHeight <= 0) {
            return;
        }
        boolean isRunning = isRunning();
        Paint paint = this.mState.mBitmapPaint;
        synchronized (this.mState) {
            if (this.mState.mNinePatch) {
                if (this.mState.mNinePatchRenderer == null) {
                    this.mState.createNinePatchRenderer();
                }
            } else if (!isRunning) {
                updateCachedBitmap(scaledHeight2, scaledHeight, this.mState.mDensityDpi);
            } else {
                int animationFrameIndex = this.mSprAnimation.getAnimationIndex();
                SprDocument sprDocument2 = this.mDocument;
                synchronized (sprDocument2) {
                    try {
                        this.mDocument.preDraw(animationFrameIndex);
                        bitmap = this.mAnimationBitmap;
                    } catch (Throwable th) {
                        th = th;
                        sprDocument = sprDocument2;
                    }
                    try {
                        if (bitmap != null) {
                            try {
                                if (bitmap.getWidth() == scaledHeight2 && this.mAnimationBitmap.getHeight() == scaledHeight) {
                                    Canvas tmpCanvas2 = new Canvas(this.mAnimationBitmap);
                                    tmpCanvas2.drawColor(0, PorterDuff.Mode.CLEAR);
                                    tmpCanvas = tmpCanvas2;
                                    sprDocument = sprDocument2;
                                    this.mDocument.draw(tmpCanvas, scaledHeight2, scaledHeight, animationFrameIndex, this.mState.mDensityDpi);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                sprDocument = sprDocument2;
                                while (true) {
                                    try {
                                        throw th;
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                }
                            }
                        }
                        this.mDocument.draw(tmpCanvas, scaledHeight2, scaledHeight, animationFrameIndex, this.mState.mDensityDpi);
                    } catch (Throwable th4) {
                        th = th4;
                        while (true) {
                            throw th;
                        }
                    }
                    this.mAnimationBitmap = Bitmap.createBitmap(scaledHeight2, scaledHeight, Bitmap.Config.ARGB_8888);
                    tmpCanvas = new Canvas(this.mAnimationBitmap);
                    sprDocument = sprDocument2;
                }
            }
            if (this.mState.mRebuildShader || isRunning) {
                if (this.mState.mTileModeX == null && this.mState.mTileModeY == null) {
                    paint.setShader(null);
                } else {
                    Shader.TileMode tmx = this.mState.mTileModeX;
                    Shader.TileMode tmy = this.mState.mTileModeY;
                    Bitmap bitmap3 = this.mAnimationBitmap;
                    if (bitmap3 == null) {
                        bitmap3 = this.mCacheBitmap;
                    }
                    paint.setShader(new BitmapShader(bitmap3, tmx == null ? Shader.TileMode.CLAMP : tmx, tmy == null ? Shader.TileMode.CLAMP : tmy));
                }
                this.mState.mRebuildShader = false;
            }
        }
        if (this.mTintFilter != null && paint.getColorFilter() == null) {
            paint.setColorFilter(this.mTintFilter);
            clearColorFilter = true;
        } else {
            clearColorFilter = false;
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
        if (clearColorFilter) {
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

    public void setGravity(int gravity) {
        if (this.mState.mGravity != gravity) {
            this.mState.mGravity = gravity;
            updateDstRectAndInsetsIfDirty();
            invalidateSelf();
        }
    }

    private void updateDstRectAndInsetsIfDirty() {
        if (this.mState.mTileModeX == null && this.mState.mTileModeY == null) {
            try {
                Rect bounds = getBounds();
                int layoutDirection = ((Integer) mGetLayoutDirection.invoke(this, new Object[0])).intValue();
                Gravity.apply(this.mState.mGravity, getIntrinsicWidth(), getIntrinsicHeight(), bounds, this.mDstRect, layoutDirection);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        copyBounds(this.mDstRect);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updateDstRectAndInsetsIfDirty();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect padding) {
        if (this.mDocument == null) {
            padding.set(0, 0, 0, 0);
            return false;
        }
        float densityScale = this.mState.getDensityScale();
        padding.set(Math.round(this.mDocument.mPaddingLeft * densityScale), Math.round(this.mDocument.mPaddingTop * densityScale), Math.round(this.mDocument.mPaddingRight * densityScale), Math.round(this.mDocument.mPaddingBottom * densityScale));
        return (this.mDocument.mPaddingLeft == 0.0f || this.mDocument.mPaddingTop == 0.0f || this.mDocument.mPaddingRight == 0.0f || this.mDocument.mPaddingBottom == 0.0f) ? false : true;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Bitmap bitmap;
        return (this.mState.mGravity == 119 && (bitmap = this.mCacheBitmap) != null && !bitmap.hasAlpha() && this.mState.mBitmapPaint.getAlpha() >= 255) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        int oldAlpha = this.mState.mBitmapPaint.getAlpha();
        if (alpha != oldAlpha) {
            this.mState.mBitmapPaint.setAlpha(alpha);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mState.mBitmapPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter cf) {
        this.mState.mBitmapPaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mState.mBitmapPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList tint) {
        SprState state = this.mState;
        state.mTint = tint;
        this.mTintFilter = updateTintFilterInternal(this.mTintFilter, state.mTint, state.mTintMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode tintMode) {
        SprState state = this.mState;
        state.mTintMode = tintMode;
        this.mTintFilter = updateTintFilterInternal(this.mTintFilter, state.mTint, state.mTintMode);
        invalidateSelf();
    }

    private static Shader.TileMode parseTileMode(int tileMode) {
        switch (tileMode) {
            case 0:
                return Shader.TileMode.CLAMP;
            case 1:
                return Shader.TileMode.REPEAT;
            case 2:
                return Shader.TileMode.MIRROR;
            default:
                return null;
        }
    }

    public Shader.TileMode getTileModeX() {
        return this.mState.mTileModeX;
    }

    public Shader.TileMode getTileModeY() {
        return this.mState.mTileModeY;
    }

    public void setTileModeX(Shader.TileMode mode) {
        setTileModeXY(mode, this.mState.mTileModeY);
    }

    public final void setTileModeY(Shader.TileMode mode) {
        setTileModeXY(this.mState.mTileModeX, mode);
    }

    public void setTileModeXY(Shader.TileMode xmode, Shader.TileMode ymode) {
        SprState state = this.mState;
        if (state.mTileModeX != xmode || state.mTileModeY != ymode) {
            state.mTileModeX = xmode;
            state.mTileModeY = ymode;
            state.mRebuildShader = true;
            updateDstRectAndInsetsIfDirty();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        SprState sprState;
        return super.isStateful() || !((sprState = this.mState) == null || sprState.mTint == null || !this.mState.mTint.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] stateSet) {
        SprState state = this.mState;
        if (state.mTint != null && state.mTintMode != null) {
            this.mTintFilter = updateTintFilterInternal(this.mTintFilter, state.mTint, state.mTintMode);
            invalidateSelf();
            return true;
        }
        return false;
    }

    PorterDuffColorFilter updateTintFilterInternal(PorterDuffColorFilter tintFilter, ColorStateList tint, PorterDuff.Mode tintMode) {
        Method method = mUpdateTintFilter;
        if (method == null) {
            return updateTintFilter(tintFilter, tint, tintMode);
        }
        PorterDuffColorFilter result = null;
        method.setAccessible(true);
        try {
            result = (PorterDuffColorFilter) method.invoke(this, tintFilter, tint, tintMode);
        } catch (Exception e) {
        }
        mUpdateTintFilter.setAccessible(false);
        return result;
    }

    private static SprDocument createFromStreamInternal(String name, InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] header = new byte[3];
        String name2 = name == null ? NA_NAME : name;
        bis.mark(3);
        if (bis.read(header) < 3) {
            bis.close();
            throw new IOException("file is too short");
        }
        bis.reset();
        if ((header[0] == 83 && header[1] == 86 && header[2] == 70) || (header[0] == 83 && header[1] == 80 && header[2] == 82)) {
            SprDocument document = new SprDocument(name2, bis);
            return document;
        }
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(bis, null);
            SprDocument document2 = new SprDocument(name2, parser);
            return document2;
        } catch (XmlPullParserException e) {
            throw new IOException(e.getCause());
        }
    }

    private static SemPathRenderingDrawable getErrorDrawable(String name) {
        SprDocument document = new SprDocument(name, 0.0f, 0.0f, 5 * 70, 5 * 55);
        SprObjectShapeRectangle object1 = new SprObjectShapeRectangle(0.0f, 0.0f, 5 * 10, 5 * 40);
        object1.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 200, 200, 200)));
        document.appendObject(object1);
        SprObjectShapeRectangle object12 = new SprObjectShapeRectangle(5 * 10, 0.0f, 5 * 20, 5 * 40);
        object12.appendAttribute(new SprAttributeFill((byte) 1, -256));
        document.appendObject(object12);
        SprObjectShapeRectangle object13 = new SprObjectShapeRectangle(5 * 20, 0.0f, 5 * 30, 5 * 40);
        object13.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 255, 255)));
        document.appendObject(object13);
        SprObjectShapeRectangle object14 = new SprObjectShapeRectangle(5 * 30, 0.0f, 5 * 40, 5 * 40);
        object14.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 255, 0)));
        document.appendObject(object14);
        SprObjectShapeRectangle object15 = new SprObjectShapeRectangle(5 * 40, 0.0f, 5 * 50, 5 * 40);
        object15.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 255, 0, 255)));
        document.appendObject(object15);
        SprObjectShapeRectangle object16 = new SprObjectShapeRectangle(5 * 50, 0.0f, 5 * 60, 5 * 40);
        object16.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 255, 0, 0)));
        document.appendObject(object16);
        SprObjectShapeRectangle object17 = new SprObjectShapeRectangle(5 * 60, 0.0f, 5 * 70, 5 * 40);
        object17.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 255)));
        document.appendObject(object17);
        SprObjectShapeRectangle object18 = new SprObjectShapeRectangle(0.0f, 5 * 40, 5 * 10, 5 * 45);
        object18.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 255)));
        document.appendObject(object18);
        SprObjectShapeRectangle object19 = new SprObjectShapeRectangle(5 * 10, 5 * 40, 5 * 20, 5 * 45);
        object19.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 0)));
        document.appendObject(object19);
        SprObjectShapeRectangle object110 = new SprObjectShapeRectangle(5 * 20, 5 * 40, 5 * 30, 5 * 45);
        object110.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 255, 0, 255)));
        document.appendObject(object110);
        SprObjectShapeRectangle object111 = new SprObjectShapeRectangle(5 * 30, 5 * 40, 5 * 40, 5 * 45);
        object111.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 0)));
        document.appendObject(object111);
        SprObjectShapeRectangle object112 = new SprObjectShapeRectangle(5 * 40, 5 * 40, 5 * 50, 5 * 45);
        object112.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 255, 255)));
        document.appendObject(object112);
        SprObjectShapeRectangle object113 = new SprObjectShapeRectangle(5 * 50, 5 * 40, 5 * 60, 5 * 45);
        object113.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 0, 0, 0)));
        document.appendObject(object113);
        SprObjectShapeRectangle object114 = new SprObjectShapeRectangle(5 * 60, 5 * 40, 5 * 70, 5 * 45);
        object114.appendAttribute(new SprAttributeFill((byte) 1, Color.argb(255, 200, 200, 200)));
        document.appendObject(object114);
        SprObjectShapeRectangle object115 = new SprObjectShapeRectangle(0.0f, 5 * 45, 5 * 70, 5 * 55);
        SprLinearGradient l = new SprLinearGradient();
        l.spreadMode = (byte) 1;
        l.f2977x1 = 0.0f;
        l.f2979y1 = 5 * 45;
        l.f2978x2 = 5 * 70;
        l.f2980y2 = 5 * 45;
        l.colors = new int[]{-1, -16777216};
        l.positions = new float[]{0.0f, 1.0f};
        l.updateGradient();
        SprAttributeFill fill = new SprAttributeFill((byte) 3, l);
        object115.appendAttribute(fill);
        document.appendObject(object115);
        return new SemPathRenderingDrawable(document) { // from class: com.samsung.android.graphics.spr.SemPathRenderingDrawable.1
            @Override // com.samsung.android.graphics.spr.SemPathRenderingDrawable, android.graphics.drawable.Drawable
            public void draw(Canvas canvas) {
                super.draw(canvas);
                Paint textOutlinePaint = new Paint();
                textOutlinePaint.setAntiAlias(true);
                textOutlinePaint.setTextSize(20.0f);
                textOutlinePaint.setStyle(Paint.Style.STROKE);
                textOutlinePaint.setColor(-16777216);
                textOutlinePaint.setStrokeWidth(4.0f);
                Paint textPaint = new Paint();
                textPaint.setAntiAlias(true);
                textPaint.setTextSize(20.0f);
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setColor(-1);
                canvas.drawText(this.mDocument.mName, 5.0f, 40.0f, textOutlinePaint);
                canvas.drawText(this.mDocument.mName, 5.0f, 40.0f, textPaint);
            }
        };
    }

    @Deprecated
    public static SemPathRenderingDrawable createFromStream(InputStream is) throws IOException {
        return createFromStream(NA_NAME, is);
    }

    public static SemPathRenderingDrawable createFromStream(String name, InputStream is) throws IOException {
        return createFromStream(name, is, null);
    }

    public static SemPathRenderingDrawable createFromStream(String name, InputStream is, Resources res) throws IOException {
        try {
            SemPathRenderingDrawable d = new SemPathRenderingDrawable(createFromStreamInternal(name, is));
            if (res != null) {
                d.updateLocalState(res);
            }
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorDrawable(name);
        }
    }

    public static SemPathRenderingDrawable createFromResourceStream(Resources resources, int resId) {
        InputStream is = null;
        try {
            is = resources.openRawResource(resId);
            SprDocument document = createFromStreamInternal(resources.getString(resId), is);
            is.close();
            SemPathRenderingDrawable d = new SemPathRenderingDrawable(document);
            d.updateLocalState(resources);
            return d;
        } catch (Exception e) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
            return getErrorDrawable(resources.getString(resId));
        }
    }

    public static SemPathRenderingDrawable createFromPathName(String pathName) {
        InputStream is = null;
        try {
            is = new FileInputStream(pathName);
            SprDocument document = createFromStreamInternal(pathName, is);
            is.close();
            return new SemPathRenderingDrawable(document);
        } catch (Exception e) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
            return getErrorDrawable(pathName);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations |= getChangingConfigurations();
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        inflate(r, parser, attrs, null);
    }

    protected static TypedArray sprObtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs) {
        if (theme == null) {
            return res.obtainAttributes(set, attrs);
        }
        return theme.obtainStyledAttributes(set, attrs, 0, 0);
    }

    private void updateStateFromTypedArray(TypedArray a) throws XmlPullParserException, IOException {
        Resources r = a.getResources();
        SprState state = this.mState;
        state.mChangingConfigurations |= a.getChangingConfigurations();
        try {
            state.mThemeAttrs = (int[]) mExtractThemeAttrs.invoke(a, new Object[0]);
        } catch (Exception e) {
            state.mThemeAttrs = null;
            e.printStackTrace();
        }
        int srcResId = a.getResourceId(mBitmapDrawable_src, 0);
        if (srcResId != 0) {
            InputStream is = null;
            try {
                is = r.openRawResource(srcResId);
                this.mState.setDocument(createFromStreamInternal(r.getString(srcResId), is));
                this.mDocument = this.mState.mDocument;
                is.close();
            } catch (Exception e2) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                throw new IOException(e2);
            }
        }
        int tintMode = a.getInt(mBitmapDrawable_tintMode, -1);
        if (tintMode != -1) {
            try {
                this.mState.mTintMode = (PorterDuff.Mode) mParseTintMode.invoke(null, Integer.valueOf(tintMode), PorterDuff.Mode.SRC_IN);
            } catch (Exception e3) {
                this.mState.mTintMode = PorterDuff.Mode.SRC_IN;
                e3.printStackTrace();
            }
        }
        ColorStateList tint = a.getColorStateList(mBitmapDrawable_tint);
        if (tint != null) {
            this.mState.mTint = tint;
        }
        this.mState.mGravity = a.getInt(mBitmapDrawable_gravity, 119);
        SprState sprState = this.mState;
        sprState.mAutoMirrored = a.getBoolean(mBitmapDrawable_autoMirrored, sprState.mAutoMirrored);
        this.mState.mBitmapPaint.setAlpha((int) (a.getFloat(mBitmapDrawable_alpha, 1.0f) * 255.0f));
        int tileMode = a.getInt(mBitmapDrawable_tileMode, -2);
        if (tileMode != -2) {
            Shader.TileMode mode = parseTileMode(tileMode);
            setTileModeXY(mode, mode);
        }
        int tileModeX = a.getInt(mBitmapDrawable_tileModeX, -2);
        if (tileModeX != -2) {
            setTileModeX(parseTileMode(tileModeX));
        }
        int tileModeY = a.getInt(mBitmapDrawable_tileModeY, -2);
        if (tileModeY != -2) {
            setTileModeY(parseTileMode(tileModeY));
        }
        updateDensity(r);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002a, code lost:
    
        if (r1 != null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0032, code lost:
    
        r1.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0030, code lost:
    
        if (r1 == null) goto L34;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void applyTheme(Resources.Theme t) {
        boolean canApplyTheme;
        super.applyTheme(t);
        SprState state = this.mState;
        if (state == null) {
            return;
        }
        if (state.mThemeAttrs != null) {
            TypedArray a = null;
            try {
                try {
                    a = (TypedArray) mResolveAttributes.invoke(t, state.mThemeAttrs, mStyleableBitmapDrawable);
                    updateStateFromTypedArray(a);
                } catch (XmlPullParserException e) {
                    throw new RuntimeException(e);
                } catch (Exception e2) {
                }
            } catch (Throwable th) {
                if (a != null) {
                    a.recycle();
                }
                throw th;
            }
        }
        try {
            canApplyTheme = ((Boolean) mCanApplyTheme.invoke(state.mTint, new Object[0])).booleanValue();
        } catch (Exception e3) {
            canApplyTheme = false;
        }
        if (state.mTint != null && canApplyTheme) {
            try {
                state.mTint = (ColorStateList) mObtainForTheme.invoke(state.mTint, t);
            } catch (Exception e4) {
            }
        }
        updateLocalState(t.getResources());
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0015, code lost:
    
        if (r0 == null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0018, code lost:
    
        updateLocalState(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
    
        return;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(r, parser, attrs, theme);
        TypedArray a = sprObtainAttributes(r, theme, attrs, mStyleableBitmapDrawable);
        try {
            try {
                updateStateFromTypedArray(a);
            } catch (XmlPullParserException e) {
                throw new RuntimeException(e);
            } catch (Exception e2) {
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public String toString() {
        if (this.mDocument == null) {
            return "SprDocument is null";
        }
        return this.mDocument.mLeft + "," + this.mDocument.mTop + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mDocument.mRight + "," + this.mDocument.mBottom + "\nLoading:" + this.mDocument.getLoadingTime() + "ms\nElement:" + this.mDocument.getTotalElementCount() + "\nSegment:" + this.mDocument.getTotalSegmentCount() + "\nAttribute:" + this.mDocument.getTotalAttributeCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDeviceDensityDpi(Resources res) {
        if (res == null) {
            return Resources.getSystem().getDisplayMetrics().densityDpi;
        }
        return res.getDisplayMetrics().densityDpi;
    }

    public void toSPR(OutputStream out) throws IOException {
        SprDocument sprDocument = this.mDocument;
        if (sprDocument != null) {
            sprDocument.toSPR(out);
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

    private void updateLocalState(Resources res) {
        setTintList(this.mState.mTint);
        updateDensity(res);
        if (this.mDocument != null) {
            float densityScale = this.mState.getDensityScale();
            super.setBounds(Math.round(this.mDocument.mLeft * densityScale), Math.round(this.mDocument.mTop * densityScale), Math.round(this.mDocument.mRight * densityScale), Math.round(this.mDocument.mBottom * densityScale));
        }
    }

    private void updateDensity(Resources res) {
        int densityDpi = getDeviceDensityDpi(res);
        if (this.mState.mDensityDpi != densityDpi) {
            this.mState.mDensityDpi = densityDpi;
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

        SprState(SprDocument document) {
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
            setDocument(document);
            Paint paint = new Paint();
            this.mBitmapPaint = paint;
            paint.setFilterBitmap(true);
        }

        SprState(SprState state) {
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
            this.mDocument = state.mDocument;
            this.mThemeAttrs = state.mThemeAttrs;
            this.mNinePatch = state.mNinePatch;
            this.mBitmapPaint = new Paint(state.mBitmapPaint);
            if (state.mNinePatch && state.mNinePatchRenderer == null) {
                state.createNinePatchRenderer();
            }
            this.mCacheManager = state.mCacheManager;
            this.mNinePatchBitmap = state.mNinePatchBitmap;
            this.mNinePatchRenderer = state.mNinePatchRenderer;
            this.mMultiNinePatch = state.mMultiNinePatch;
            this.mTint = state.mTint;
            this.mTintMode = state.mTintMode;
            this.mAutoMirrored = state.mAutoMirrored;
            this.mGravity = state.mGravity;
            this.mChangingConfigurations = state.mChangingConfigurations;
            this.mRebuildShader = state.mRebuildShader;
            this.mTileModeX = state.mTileModeX;
            this.mTileModeY = state.mTileModeY;
            this.mDensityDpi = state.mDensityDpi;
        }

        public void setDocument(SprDocument document) {
            if (document == null) {
                return;
            }
            SprDocument sprDocument = this.mDocument;
            if (sprDocument == null || (sprDocument.mName != null && !this.mDocument.mName.equals(document.mName))) {
                this.mDocument = document;
                this.mNinePatch = (document.mNinePatchLeft == 0.0f && this.mDocument.mNinePatchTop == 0.0f && this.mDocument.mNinePatchRight == 0.0f && this.mDocument.mNinePatchBottom == 0.0f) ? false : true;
                int c = 0;
                while (true) {
                    if (c < this.mDocument.getFileAttributeSize()) {
                        SprFileAttributeNinePatch attribute = (SprFileAttributeNinePatch) this.mDocument.getFileAttribute(c);
                        if (attribute == null || attribute.mType != 1) {
                            c++;
                        } else {
                            this.mNinePatch = true;
                            this.mMultiNinePatch = attribute;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                this.mDensityDpi = SemPathRenderingDrawable.getDeviceDensityDpi(null);
                if (this.mCacheManager != null) {
                    if (SprDebug.IsDebug) {
                        this.mCacheManager.printDebug();
                        Exception e = new Exception();
                        e.printStackTrace();
                    }
                    this.mCacheManager = null;
                }
                this.mCacheManager = new SprCacheManager(this.mDocument.mName, this.mDocument.hashCode());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void createNinePatchRenderer() {
            if (this.mNinePatchRenderer == null && this.mDocument != null) {
                int width = getIntrinsicWidth();
                int height = getIntrinsicHeight();
                synchronized (this.mDocument) {
                    if (!this.mDocument.isPredraw()) {
                        this.mDocument.preDraw(0);
                    }
                    Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    this.mNinePatchBitmap = createBitmap;
                    if (createBitmap != null) {
                        Canvas tmpCanvas = new Canvas(this.mNinePatchBitmap);
                        this.mDocument.draw(tmpCanvas, width, height, 0, this.mDensityDpi);
                    }
                }
                if (this.mNinePatch && this.mMultiNinePatch == null) {
                    float densityScale = getDensityScale();
                    int left = Math.round(this.mDocument.mNinePatchLeft * densityScale);
                    int top = Math.round(this.mDocument.mNinePatchTop * densityScale);
                    int right = width - Math.round(this.mDocument.mNinePatchRight * densityScale);
                    int bottom = height - Math.round(this.mDocument.mNinePatchBottom * densityScale);
                    ByteBuffer buffer = getNinePatchChunk(left, top, right > left ? right : left + 1, bottom > top ? bottom : top + 1);
                    this.mNinePatchRenderer = new NinePatch(this.mNinePatchBitmap, buffer.array());
                    return;
                }
                ByteBuffer buffer2 = getNinePatchChunk(this.mMultiNinePatch);
                this.mNinePatchRenderer = new NinePatch(this.mNinePatchBitmap, buffer2.array());
            }
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
            SprDocument sprDocument = this.mDocument;
            return sprDocument == null ? (this.mDensityDpi / 160.0f) / 3.0f : (this.mDensityDpi / 160.0f) / sprDocument.mDensity;
        }

        private ByteBuffer getNinePatchChunk(int left, int top, int right, int bottom) {
            ByteBuffer buffer = ByteBuffer.allocate(84).order(ByteOrder.nativeOrder());
            buffer.put((byte) 1);
            buffer.put((byte) 2);
            buffer.put((byte) 2);
            buffer.put((byte) 9);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(left);
            buffer.putInt(right);
            buffer.putInt(top);
            buffer.putInt(bottom);
            buffer.putInt(1);
            buffer.putInt(1);
            buffer.putInt(1);
            buffer.putInt(1);
            buffer.putInt(1);
            buffer.putInt(1);
            buffer.putInt(1);
            buffer.putInt(1);
            buffer.putInt(1);
            return buffer;
        }

        private ByteBuffer getNinePatchChunk(SprFileAttributeNinePatch attr) {
            float densityScale = getDensityScale();
            int xSize = 0;
            int ySize = 0;
            int[] xStart = new int[attr.xSize];
            int[] xEnd = new int[attr.xSize];
            int[] yStart = new int[attr.ySize];
            int[] yEnd = new int[attr.ySize];
            int prevEnd = -1;
            for (int c = 0; c < attr.xSize; c++) {
                int start = Math.round(attr.xStart[c] * densityScale);
                int end = Math.round(attr.xEnd[c] * densityScale);
                if (end <= start) {
                    end = start + 1;
                }
                if (start <= prevEnd) {
                    xEnd[xSize - 1] = end;
                } else {
                    xStart[xSize] = start;
                    xEnd[xSize] = end;
                    xSize++;
                }
                prevEnd = end;
            }
            int prevEnd2 = -1;
            for (int c2 = 0; c2 < attr.ySize; c2++) {
                int start2 = Math.round(attr.yStart[c2] * densityScale);
                int end2 = Math.round(attr.yEnd[c2] * densityScale);
                if (end2 <= start2) {
                    end2 = start2 + 1;
                }
                if (start2 <= prevEnd2) {
                    yEnd[ySize - 1] = end2;
                } else {
                    yStart[ySize] = start2;
                    yEnd[ySize] = end2;
                    ySize++;
                }
                prevEnd2 = end2;
            }
            int c3 = xSize * 2;
            int colorSize = (c3 + 1) * ((ySize * 2) + 1);
            ByteBuffer buffer = ByteBuffer.allocate((xSize * 8) + 42 + (ySize * 8) + (colorSize * 4)).order(ByteOrder.nativeOrder());
            buffer.put((byte) 1);
            buffer.put((byte) (attr.xSize * 2));
            buffer.put((byte) (attr.ySize * 2));
            buffer.put((byte) colorSize);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            buffer.putInt(0);
            for (int c4 = 0; c4 < xSize; c4++) {
                buffer.putInt(xStart[c4]);
                buffer.putInt(xEnd[c4]);
            }
            for (int c5 = 0; c5 < ySize; c5++) {
                buffer.putInt(yStart[c5]);
                buffer.putInt(yEnd[c5]);
            }
            for (int c6 = 0; c6 < colorSize; c6++) {
                buffer.putInt(1);
            }
            return buffer;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            return i | (colorStateList != null ? colorStateList.getChangingConfigurations() : 0);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            boolean canApplyTheme;
            try {
                canApplyTheme = ((Boolean) SemPathRenderingDrawable.mCanApplyTheme.invoke(this.mTint, new Object[0])).booleanValue();
            } catch (Exception e) {
                canApplyTheme = false;
            }
            return this.mThemeAttrs != null || (this.mTint != null && canApplyTheme);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new SemPathRenderingDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources res) {
            return new SemPathRenderingDrawable(this, res);
        }
    }

    public SprDocument getDocument() {
        return this.mDocument;
    }

    public static int getVersion() {
        return mVersion;
    }

    private boolean needMirroring() {
        try {
            int direction = ((Integer) mGetLayoutDirection.invoke(this, new Object[0])).intValue();
            return isAutoMirrored() && direction == 1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean mirrored) {
        if (this.mState.mAutoMirrored != mirrored) {
            this.mState.mAutoMirrored = mirrored;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.getChangingConfigurations();
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
                    this.mDocument = this.mDocument.m8902clone();
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
        if (sprDrawableAnimation != null && sprDrawableAnimation.isRunning()) {
            return true;
        }
        return false;
    }

    private void updateCachedBitmap(int width, int height, int dpi) {
        SprDocument sprDocument = this.mDocument;
        if (sprDocument == null) {
            return;
        }
        synchronized (sprDocument) {
            Bitmap bitmap = this.mCacheBitmap;
            if (bitmap != null && !bitmap.isMutable() && this.mCacheBitmap.getWidth() == width && this.mCacheBitmap.getHeight() == height && this.mCacheDensityDpi == dpi) {
                return;
            }
            if (this.mCacheBitmap != null) {
                this.mState.mCacheManager.unlock(this.mCacheBitmap);
                this.mCacheBitmap = null;
                this.mCacheDensityDpi = 0;
            }
            Bitmap cache = this.mState.mCacheManager.getCache(width, height, dpi);
            this.mCacheBitmap = cache;
            this.mCacheDensityDpi = dpi;
            if (cache == null) {
                if (!this.mDocument.isPredraw()) {
                    this.mDocument.preDraw(0);
                }
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                this.mCacheBitmap = createBitmap;
                if (createBitmap != null) {
                    Canvas tmpCanvas = new Canvas(this.mCacheBitmap);
                    this.mDocument.draw(tmpCanvas, width, height, 0, this.mState.mDensityDpi);
                    this.mState.mCacheManager.addCache(this.mCacheBitmap, this.mCacheDensityDpi);
                }
            }
            this.mState.mCacheManager.lock(this.mCacheBitmap);
        }
    }

    private PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter tintFilter, ColorStateList tint, PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        int color = tint.getColorForState(getState(), 0);
        return new PorterDuffColorFilter(color, tintMode);
    }
}
