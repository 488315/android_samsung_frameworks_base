package android.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.ComplexColor;
import android.content.res.GradientColor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Log;
import android.util.PathParser;
import android.util.Property;
import android.util.Xml;
import android.widget.ProgressBar;
import com.android.internal.R;
import com.android.internal.util.VirtualRefBasePtr;
import com.samsung.android.wallpaperbackup.GenerateXML;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class VectorDrawable extends Drawable {
    private static final String LOGTAG = "VectorDrawable";
    private static final int PATH_COLOR_UPDATE_MODE_FILL = 1;
    private static final int PATH_COLOR_UPDATE_MODE_STROKE = 0;
    private static final int PATH_COLOR_UPDATE_MODE_STROKE_AND_FILL = 2;
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";
    private BlendModeColorFilter mBlendModeColorFilter;
    private ColorFilter mColorFilter;
    private boolean mDpiScaledDirty;
    private int mDpiScaledHeight;
    private Insets mDpiScaledInsets;
    private int mDpiScaledWidth;
    private boolean mMutated;
    private int mTargetDensity;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds;
    private VectorDrawableState mVectorState;

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nAddChild(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateClipPath();

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateClipPath(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateFullPath();

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateFullPath(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateGroup();

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateGroup(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateTree(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateTreeFromCopy(long j, long j2);

    private static native int nDraw(long j, long j2, long j3, Rect rect, boolean z, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetFillAlpha(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native int nGetFillColor(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nGetFullPathProperties(long j, byte[] bArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nGetGroupProperties(long j, float[] fArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetPivotX(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetPivotY(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetRootAlpha(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetRotation(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetScaleX(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetScaleY(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetStrokeAlpha(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native int nGetStrokeColor(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetStrokeWidth(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetTranslateX(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetTranslateY(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetTrimPathEnd(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetTrimPathOffset(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native float nGetTrimPathStart(long j);

    @FastNative
    private static native void nSetAllowCaching(long j, boolean z);

    @FastNative
    private static native void nSetAntiAlias(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetFillAlpha(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetFillColor(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetName(long j, String str);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetPathData(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetPathString(long j, String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetPivotX(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetPivotY(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetRendererViewportSize(long j, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native boolean nSetRootAlpha(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetRotation(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetScaleX(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetScaleY(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetStrokeAlpha(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetStrokeColor(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetStrokeWidth(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetTranslateX(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetTranslateY(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetTrimPathEnd(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetTrimPathOffset(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetTrimPathStart(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nUpdateFullPathFillGradient(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nUpdateFullPathProperties(long j, float f, int i, float f2, int i2, float f3, float f4, float f5, float f6, float f7, int i3, int i4, int i5);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nUpdateFullPathStrokeGradient(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nUpdateGroupProperties(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7);

    public VectorDrawable() {
        this(null, null);
    }

    private VectorDrawable(VectorDrawableState vectorDrawableState, Resources resources) {
        this.mDpiScaledWidth = 0;
        this.mDpiScaledHeight = 0;
        this.mDpiScaledInsets = Insets.NONE;
        this.mDpiScaledDirty = true;
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableState(vectorDrawableState);
        updateLocalState(resources);
    }

    private void updateLocalState(Resources resources) {
        int iResolveDensity = Drawable.resolveDensity(resources, this.mVectorState.mDensity);
        if (this.mTargetDensity != iResolveDensity) {
            this.mTargetDensity = iResolveDensity;
            this.mDpiScaledDirty = true;
        }
        updateColorFilters(this.mVectorState.mBlendMode, this.mVectorState.mTint);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = new VectorDrawableState(this.mVectorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    Object getTargetByName(String str) {
        return this.mVectorState.mVGTargetsMap.get(str);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mVectorState;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int i;
        copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() <= 0 || this.mTmpBounds.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.mColorFilter;
        if (colorFilter == null) {
            colorFilter = this.mBlendModeColorFilter;
        }
        int iNDraw = nDraw(this.mVectorState.getNativeRenderer(), canvas.getNativeCanvasWrapper(), colorFilter == null ? 0L : colorFilter.getNativeInstance(), this.mTmpBounds, needMirroring(), this.mVectorState.canReuseCache());
        if (iNDraw == 0) {
            return;
        }
        if (canvas.isHardwareAccelerated()) {
            i = (iNDraw - this.mVectorState.mLastHWCachePixelCount) * 4;
            this.mVectorState.mLastHWCachePixelCount = iNDraw;
        } else {
            i = (iNDraw - this.mVectorState.mLastSWCachePixelCount) * 4;
            this.mVectorState.mLastSWCachePixelCount = iNDraw;
        }
        if (i > 0) {
            VMRuntime.getRuntime().registerNativeAllocation(i);
        } else if (i < 0) {
            VMRuntime.getRuntime().registerNativeFree(-i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return (int) (this.mVectorState.getAlpha() * 255.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mVectorState.setAlpha(i / 255.0f)) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState.mTint != colorStateList) {
            vectorDrawableState.mTint = colorStateList;
            updateColorFilters(this.mVectorState.mBlendMode, colorStateList);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState.mBlendMode != blendMode) {
            vectorDrawableState.mBlendMode = blendMode;
            updateColorFilters(vectorDrawableState.mBlendMode, vectorDrawableState.mTint);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        if (super.isStateful()) {
            return true;
        }
        VectorDrawableState vectorDrawableState = this.mVectorState;
        return vectorDrawableState != null && vectorDrawableState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        return vectorDrawableState != null && vectorDrawableState.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        if (isStateful()) {
            mutate();
        }
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState.onStateChange(iArr)) {
            vectorDrawableState.mCacheDirty = true;
            z = true;
        } else {
            z = false;
        }
        if (vectorDrawableState.mTint == null || vectorDrawableState.mBlendMode == null) {
            return z;
        }
        updateColorFilters(vectorDrawableState.mBlendMode, vectorDrawableState.mTint);
        return true;
    }

    private void updateColorFilters(BlendMode blendMode, ColorStateList colorStateList) {
        this.mTintFilter = updateTintFilter(this.mTintFilter, colorStateList, BlendMode.blendModeToPorterDuffMode(blendMode));
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, blendMode);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return getAlpha() == 0 ? -2 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.mDpiScaledDirty) {
            computeVectorSize();
        }
        return this.mDpiScaledWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.mDpiScaledDirty) {
            computeVectorSize();
        }
        return this.mDpiScaledHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public Insets getOpticalInsets() {
        if (this.mDpiScaledDirty) {
            computeVectorSize();
        }
        return this.mDpiScaledInsets;
    }

    void computeVectorSize() {
        Insets insets = this.mVectorState.mOpticalInsets;
        int i = this.mVectorState.mDensity;
        int i2 = this.mTargetDensity;
        if (i2 != i) {
            this.mDpiScaledWidth = Drawable.scaleFromDensity(this.mVectorState.mBaseWidth, i, i2, true);
            this.mDpiScaledHeight = Drawable.scaleFromDensity(this.mVectorState.mBaseHeight, i, i2, true);
            this.mDpiScaledInsets = Insets.of(Drawable.scaleFromDensity(insets.left, i, i2, false), Drawable.scaleFromDensity(insets.top, i, i2, false), Drawable.scaleFromDensity(insets.right, i, i2, false), Drawable.scaleFromDensity(insets.bottom, i, i2, false));
        } else {
            this.mDpiScaledWidth = this.mVectorState.mBaseWidth;
            this.mDpiScaledHeight = this.mVectorState.mBaseHeight;
            this.mDpiScaledInsets = insets;
        }
        this.mDpiScaledDirty = false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        return (vectorDrawableState != null && vectorDrawableState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState == null) {
            return;
        }
        this.mDpiScaledDirty = vectorDrawableState.setDensity(Drawable.resolveDensity(theme.getResources(), 0)) | this.mDpiScaledDirty;
        if (vectorDrawableState.mThemeAttrs != null) {
            TypedArray typedArrayResolveAttributes = theme.resolveAttributes(vectorDrawableState.mThemeAttrs, R.styleable.VectorDrawable);
            try {
                try {
                    vectorDrawableState.mCacheDirty = true;
                    updateStateFromTypedArray(typedArrayResolveAttributes);
                    typedArrayResolveAttributes.recycle();
                    this.mDpiScaledDirty = true;
                } catch (XmlPullParserException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                typedArrayResolveAttributes.recycle();
                throw th;
            }
        }
        if (vectorDrawableState.mTint != null && vectorDrawableState.mTint.canApplyTheme()) {
            vectorDrawableState.mTint = vectorDrawableState.mTint.obtainForTheme(theme);
        }
        VectorDrawableState vectorDrawableState2 = this.mVectorState;
        if (vectorDrawableState2 != null && vectorDrawableState2.canApplyTheme()) {
            this.mVectorState.applyTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    public float getPixelSize() {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState == null || vectorDrawableState.mBaseWidth == 0 || this.mVectorState.mBaseHeight == 0 || this.mVectorState.mViewportHeight == 0.0f || this.mVectorState.mViewportWidth == 0.0f) {
            return 1.0f;
        }
        return Math.min(this.mVectorState.mViewportWidth / this.mVectorState.mBaseWidth, this.mVectorState.mViewportHeight / this.mVectorState.mBaseHeight);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState != null && vectorDrawableState.mRootGroup != null) {
            Object targetByName = getTargetByName(ProgressBar.SEM_PROGRESS_PATH_NAME_PRIMARY);
            if (targetByName instanceof VFullPath) {
                ((VFullPath) targetByName).setTrimPathEnd(i * 1.0E-4f);
                invalidateSelf();
                return true;
            }
            Object targetByName2 = getTargetByName(ProgressBar.SEM_PROGRESS_PATH_NAME_MEASURES);
            if (targetByName2 instanceof VFullPath) {
                float f = i * 1.0E-4f;
                VFullPath vFullPath = (VFullPath) targetByName2;
                vFullPath.setTrimPathStart(f);
                vFullPath.setTrimPathEnd(f + 1.0E-4f);
                invalidateSelf();
                return true;
            }
        }
        return false;
    }

    public void setPathStrokeColor(String str, int i) {
        VectorDrawableState vectorDrawableState;
        if (str == null || str.isEmpty() || (vectorDrawableState = this.mVectorState) == null || vectorDrawableState.mRootGroup == null) {
            return;
        }
        this.mVectorState.mRootGroup.updatePathColorTraversal(str, i, 0);
    }

    public void setPathFillColor(String str, int i) {
        VectorDrawableState vectorDrawableState;
        if (str == null || str.isEmpty() || (vectorDrawableState = this.mVectorState) == null || vectorDrawableState.mRootGroup == null) {
            return;
        }
        this.mVectorState.mRootGroup.updatePathColorTraversal(str, i, 1);
    }

    public void setPathColor(String str, int i) {
        VectorDrawableState vectorDrawableState;
        if (str == null || str.isEmpty() || (vectorDrawableState = this.mVectorState) == null || vectorDrawableState.mRootGroup == null) {
            return;
        }
        this.mVectorState.mRootGroup.updatePathColorTraversal(str, i, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updatePathColor(VFullPath vFullPath, int i, int i2) {
        if (i2 == 0) {
            vFullPath.setStrokeColor(i);
            return;
        }
        if (i2 == 1) {
            vFullPath.setFillColor(i);
            return;
        }
        if (i2 != 2) {
            return;
        }
        int strokeColor = vFullPath.getStrokeColor() & (-16777216);
        if (strokeColor != 0) {
            vFullPath.setStrokeColor(strokeColor | i);
        }
        int fillColor = vFullPath.getFillColor() & (-16777216);
        if (fillColor != 0) {
            vFullPath.setFillColor(i | fillColor);
        }
    }

    public static VectorDrawable create(Resources resources, int i) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int next;
        try {
            XmlResourceParser xml = resources.getXml(i);
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                throw new XmlPullParserException("No start tag found");
            }
            VectorDrawable vectorDrawable = new VectorDrawable();
            vectorDrawable.inflate(resources, xml, attributeSetAsAttributeSet);
            return vectorDrawable;
        } catch (IOException e) {
            Log.e(LOGTAG, "parser error", e);
            return null;
        } catch (XmlPullParserException e2) {
            Log.e(LOGTAG, "parser error", e2);
            return null;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        try {
            Trace.traceBegin(8192L, "VectorDrawable#inflate");
            if (this.mVectorState.mRootGroup != null || this.mVectorState.mNativeTree != null) {
                if (this.mVectorState.mRootGroup != null) {
                    VMRuntime.getRuntime().registerNativeFree(this.mVectorState.mRootGroup.getNativeSize());
                    this.mVectorState.mRootGroup.setTree(null);
                }
                this.mVectorState.mRootGroup = new VGroup();
                if (this.mVectorState.mNativeTree != null) {
                    VMRuntime.getRuntime().registerNativeFree(316);
                    this.mVectorState.mNativeTree.release();
                }
                VectorDrawableState vectorDrawableState = this.mVectorState;
                vectorDrawableState.createNativeTree(vectorDrawableState.mRootGroup);
            }
            VectorDrawableState vectorDrawableState2 = this.mVectorState;
            vectorDrawableState2.setDensity(Drawable.resolveDensity(resources, 0));
            TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.VectorDrawable);
            updateStateFromTypedArray(typedArrayObtainAttributes);
            typedArrayObtainAttributes.recycle();
            this.mDpiScaledDirty = true;
            vectorDrawableState2.mCacheDirty = true;
            inflateChildElements(resources, xmlPullParser, attributeSet, theme);
            vectorDrawableState2.onTreeConstructionFinished();
            updateLocalState(resources);
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) throws XmlPullParserException {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        vectorDrawableState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        vectorDrawableState.mThemeAttrs = typedArray.extractThemeAttrs();
        int i = typedArray.getInt(6, -1);
        if (i != -1) {
            vectorDrawableState.mBlendMode = Drawable.parseBlendMode(i, BlendMode.SRC_IN);
        }
        ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            vectorDrawableState.mTint = colorStateList;
        }
        vectorDrawableState.mAutoMirrored = typedArray.getBoolean(5, vectorDrawableState.mAutoMirrored);
        vectorDrawableState.setViewportSize(typedArray.getFloat(7, vectorDrawableState.mViewportWidth), typedArray.getFloat(8, vectorDrawableState.mViewportHeight));
        if (vectorDrawableState.mViewportWidth <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (vectorDrawableState.mViewportHeight <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        vectorDrawableState.mBaseWidth = typedArray.getDimensionPixelSize(3, vectorDrawableState.mBaseWidth);
        vectorDrawableState.mBaseHeight = typedArray.getDimensionPixelSize(2, vectorDrawableState.mBaseHeight);
        if (vectorDrawableState.mBaseWidth <= 0) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
        }
        if (vectorDrawableState.mBaseHeight <= 0) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
        }
        vectorDrawableState.mOpticalInsets = Insets.of(typedArray.getDimensionPixelOffset(9, vectorDrawableState.mOpticalInsets.left), typedArray.getDimensionPixelOffset(10, vectorDrawableState.mOpticalInsets.top), typedArray.getDimensionPixelOffset(11, vectorDrawableState.mOpticalInsets.right), typedArray.getDimensionPixelOffset(12, vectorDrawableState.mOpticalInsets.bottom));
        vectorDrawableState.setAlpha(typedArray.getFloat(4, vectorDrawableState.getAlpha()));
        String string = typedArray.getString(0);
        if (string != null) {
            vectorDrawableState.mRootName = string;
            vectorDrawableState.mVGTargetsMap.put(string, vectorDrawableState);
        }
    }

    private void inflateChildElements(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        Stack stack = new Stack();
        stack.push(vectorDrawableState.mRootGroup);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                VGroup vGroup = (VGroup) stack.peek();
                if ("path".equals(name)) {
                    VFullPath vFullPath = new VFullPath();
                    vFullPath.inflate(resources, attributeSet, theme);
                    vGroup.addChild(vFullPath);
                    if (vFullPath.getPathName() != null) {
                        vectorDrawableState.mVGTargetsMap.put(vFullPath.getPathName(), vFullPath);
                    }
                    vectorDrawableState.mChangingConfigurations = vFullPath.mChangingConfigurations | vectorDrawableState.mChangingConfigurations;
                    z = false;
                } else if (SHAPE_CLIP_PATH.equals(name)) {
                    VClipPath vClipPath = new VClipPath();
                    vClipPath.inflate(resources, attributeSet, theme);
                    vGroup.addChild(vClipPath);
                    if (vClipPath.getPathName() != null) {
                        vectorDrawableState.mVGTargetsMap.put(vClipPath.getPathName(), vClipPath);
                    }
                    vectorDrawableState.mChangingConfigurations = vClipPath.mChangingConfigurations | vectorDrawableState.mChangingConfigurations;
                } else if (SHAPE_GROUP.equals(name)) {
                    VGroup vGroup2 = new VGroup();
                    vGroup2.inflate(resources, attributeSet, theme);
                    vGroup.addChild(vGroup2);
                    stack.push(vGroup2);
                    if (vGroup2.getGroupName() != null) {
                        vectorDrawableState.mVGTargetsMap.put(vGroup2.getGroupName(), vGroup2);
                    }
                    vectorDrawableState.mChangingConfigurations = vGroup2.mChangingConfigurations | vectorDrawableState.mChangingConfigurations;
                }
            } else if (eventType == 3 && SHAPE_GROUP.equals(xmlPullParser.getName())) {
                stack.pop();
            }
            eventType = xmlPullParser.next();
        }
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            if (stringBuffer.length() > 0) {
                stringBuffer.append(" or ");
            }
            stringBuffer.append("path");
            throw new XmlPullParserException("no " + ((Object) stringBuffer) + " defined");
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mVectorState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    void setAllowCaching(boolean z) {
        nSetAllowCaching(this.mVectorState.getNativeRenderer(), z);
    }

    private boolean needMirroring() {
        return isAutoMirrored() && getLayoutDirection() == 1;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.mVectorState.mAutoMirrored != z) {
            this.mVectorState.mAutoMirrored = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mVectorState.mAutoMirrored;
    }

    public long getNativeTree() {
        return this.mVectorState.getNativeRenderer();
    }

    public void setAntiAlias(boolean z) {
        nSetAntiAlias(this.mVectorState.mNativeTree.get(), z);
    }

    static class VectorDrawableState extends Drawable.ConstantState {
        static final Property<VectorDrawableState, Float> ALPHA = new FloatProperty<VectorDrawableState>("alpha") { // from class: android.graphics.drawable.VectorDrawable.VectorDrawableState.1
            @Override // android.util.FloatProperty
            public void setValue(VectorDrawableState vectorDrawableState, float f) {
                vectorDrawableState.setAlpha(f);
            }

            @Override // android.util.Property
            public Float get(VectorDrawableState vectorDrawableState) {
                return Float.valueOf(vectorDrawableState.getAlpha());
            }
        };
        private static final int NATIVE_ALLOCATION_SIZE = 316;
        private int mAllocationOfAllNodes;
        boolean mAutoMirrored;
        int mBaseHeight;
        int mBaseWidth;
        BlendMode mBlendMode;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        BlendMode mCachedBlendMode;
        int[] mCachedThemeAttrs;
        ColorStateList mCachedTint;
        int mChangingConfigurations;
        int mDensity;
        int mLastHWCachePixelCount;
        int mLastSWCachePixelCount;
        Insets mOpticalInsets;
        VGroup mRootGroup;
        String mRootName;
        int[] mThemeAttrs;
        ColorStateList mTint;
        final ArrayMap<String, Object> mVGTargetsMap;
        float mViewportWidth = 0.0f;
        float mViewportHeight = 0.0f;
        VirtualRefBasePtr mNativeTree = null;

        Property getProperty(String str) {
            Property<VectorDrawableState, Float> property = ALPHA;
            if (property.getName().equals(str)) {
                return property;
            }
            return null;
        }

        public VectorDrawableState(VectorDrawableState vectorDrawableState) {
            this.mTint = null;
            this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
            this.mBaseWidth = 0;
            this.mBaseHeight = 0;
            this.mOpticalInsets = Insets.NONE;
            this.mRootName = null;
            this.mDensity = 160;
            ArrayMap<String, Object> arrayMap = new ArrayMap<>();
            this.mVGTargetsMap = arrayMap;
            this.mLastSWCachePixelCount = 0;
            this.mLastHWCachePixelCount = 0;
            this.mAllocationOfAllNodes = 0;
            if (vectorDrawableState != null) {
                this.mThemeAttrs = vectorDrawableState.mThemeAttrs;
                this.mChangingConfigurations = vectorDrawableState.mChangingConfigurations;
                this.mTint = vectorDrawableState.mTint;
                this.mBlendMode = vectorDrawableState.mBlendMode;
                this.mAutoMirrored = vectorDrawableState.mAutoMirrored;
                VGroup vGroup = new VGroup(vectorDrawableState.mRootGroup, arrayMap);
                this.mRootGroup = vGroup;
                createNativeTreeFromCopy(vectorDrawableState, vGroup);
                this.mBaseWidth = vectorDrawableState.mBaseWidth;
                this.mBaseHeight = vectorDrawableState.mBaseHeight;
                setViewportSize(vectorDrawableState.mViewportWidth, vectorDrawableState.mViewportHeight);
                this.mOpticalInsets = vectorDrawableState.mOpticalInsets;
                this.mRootName = vectorDrawableState.mRootName;
                this.mDensity = vectorDrawableState.mDensity;
                String str = vectorDrawableState.mRootName;
                if (str != null) {
                    arrayMap.put(str, this);
                }
            } else {
                VGroup vGroup2 = new VGroup();
                this.mRootGroup = vGroup2;
                createNativeTree(vGroup2);
            }
            onTreeConstructionFinished();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void createNativeTree(VGroup vGroup) {
            this.mNativeTree = new VirtualRefBasePtr(VectorDrawable.nCreateTree(vGroup.mNativePtr));
            VMRuntime.getRuntime().registerNativeAllocation(316);
        }

        private void createNativeTreeFromCopy(VectorDrawableState vectorDrawableState, VGroup vGroup) {
            this.mNativeTree = new VirtualRefBasePtr(VectorDrawable.nCreateTreeFromCopy(vectorDrawableState.mNativeTree.get(), vGroup.mNativePtr));
            VMRuntime.getRuntime().registerNativeAllocation(316);
        }

        void onTreeConstructionFinished() {
            this.mRootGroup.setTree(this.mNativeTree);
            this.mAllocationOfAllNodes = this.mRootGroup.getNativeSize();
            VMRuntime.getRuntime().registerNativeAllocation(this.mAllocationOfAllNodes);
        }

        long getNativeRenderer() {
            VirtualRefBasePtr virtualRefBasePtr = this.mNativeTree;
            if (virtualRefBasePtr == null) {
                return 0L;
            }
            return virtualRefBasePtr.get();
        }

        public boolean canReuseCache() {
            if (!this.mCacheDirty && this.mCachedThemeAttrs == this.mThemeAttrs && this.mCachedTint == this.mTint && this.mCachedBlendMode == this.mBlendMode && this.mCachedAutoMirrored == this.mAutoMirrored) {
                return true;
            }
            updateCacheStates();
            return false;
        }

        public void updateCacheStates() {
            this.mCachedThemeAttrs = this.mThemeAttrs;
            this.mCachedTint = this.mTint;
            this.mCachedBlendMode = this.mBlendMode;
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

        public void applyTheme(Resources.Theme theme) {
            this.mRootGroup.applyTheme(theme);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            VGroup vGroup = this.mRootGroup;
            if (vGroup != null && vGroup.canApplyTheme()) {
                return true;
            }
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null && colorStateList.canApplyTheme()) || super.canApplyTheme();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new VectorDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new VectorDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int i = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            return (colorStateList != null ? colorStateList.getChangingConfigurations() : 0) | i;
        }

        public boolean isStateful() {
            ColorStateList colorStateList = this.mTint;
            if (colorStateList != null && colorStateList.isStateful()) {
                return true;
            }
            VGroup vGroup = this.mRootGroup;
            return vGroup != null && vGroup.isStateful();
        }

        public boolean hasFocusStateSpecified() {
            ColorStateList colorStateList = this.mTint;
            if (colorStateList != null && colorStateList.hasFocusStateSpecified()) {
                return true;
            }
            VGroup vGroup = this.mRootGroup;
            return vGroup != null && vGroup.hasFocusStateSpecified();
        }

        void setViewportSize(float f, float f2) {
            this.mViewportWidth = f;
            this.mViewportHeight = f2;
            VectorDrawable.nSetRendererViewportSize(getNativeRenderer(), f, f2);
        }

        public final boolean setDensity(int i) {
            int i2 = this.mDensity;
            if (i2 == i) {
                return false;
            }
            this.mDensity = i;
            applyDensityScaling(i2, i);
            return true;
        }

        private void applyDensityScaling(int i, int i2) {
            this.mBaseWidth = Drawable.scaleFromDensity(this.mBaseWidth, i, i2, true);
            this.mBaseHeight = Drawable.scaleFromDensity(this.mBaseHeight, i, i2, true);
            this.mOpticalInsets = Insets.of(Drawable.scaleFromDensity(this.mOpticalInsets.left, i, i2, false), Drawable.scaleFromDensity(this.mOpticalInsets.top, i, i2, false), Drawable.scaleFromDensity(this.mOpticalInsets.right, i, i2, false), Drawable.scaleFromDensity(this.mOpticalInsets.bottom, i, i2, false));
        }

        public boolean onStateChange(int[] iArr) {
            return this.mRootGroup.onStateChange(iArr);
        }

        public void finalize() throws Throwable {
            super.finalize();
            VMRuntime.getRuntime().registerNativeFree(this.mAllocationOfAllNodes + 316 + (this.mLastHWCachePixelCount * 4) + (this.mLastSWCachePixelCount * 4));
        }

        public boolean setAlpha(float f) {
            return VectorDrawable.nSetRootAlpha(this.mNativeTree.get(), f);
        }

        public float getAlpha() {
            return VectorDrawable.nGetRootAlpha(this.mNativeTree.get());
        }
    }

    static class VGroup extends VObject {
        private static final int NATIVE_ALLOCATION_SIZE = 100;
        private static final Property<VGroup, Float> PIVOT_X;
        private static final int PIVOT_X_INDEX = 1;
        private static final Property<VGroup, Float> PIVOT_Y;
        private static final int PIVOT_Y_INDEX = 2;
        private static final Property<VGroup, Float> ROTATION;
        private static final int ROTATION_INDEX = 0;
        private static final Property<VGroup, Float> SCALE_X;
        private static final int SCALE_X_INDEX = 3;
        private static final Property<VGroup, Float> SCALE_Y;
        private static final int SCALE_Y_INDEX = 4;
        private static final int TRANSFORM_PROPERTY_COUNT = 7;
        private static final Property<VGroup, Float> TRANSLATE_X;
        private static final int TRANSLATE_X_INDEX = 5;
        private static final Property<VGroup, Float> TRANSLATE_Y;
        private static final int TRANSLATE_Y_INDEX = 6;
        private static final Map<String, Integer> sPropertyIndexMap = Map.of("translateX", 5, "translateY", 6, "scaleX", 3, "scaleY", 4, "pivotX", 1, "pivotY", 2, GenerateXML.ROTATION, 0);
        private static final Map<String, Property> sPropertyMap;
        private int mChangingConfigurations;
        private final ArrayList<VObject> mChildren;
        private String mGroupName;
        private boolean mIsStateful;
        private final long mNativePtr;
        private int[] mThemeAttrs;
        private float[] mTransform;

        static {
            FloatProperty<VGroup> floatProperty = new FloatProperty<VGroup>("translateX") { // from class: android.graphics.drawable.VectorDrawable.VGroup.1
                @Override // android.util.FloatProperty
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setTranslateX(f);
                }

                @Override // android.util.Property
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getTranslateX());
                }
            };
            TRANSLATE_X = floatProperty;
            FloatProperty<VGroup> floatProperty2 = new FloatProperty<VGroup>("translateY") { // from class: android.graphics.drawable.VectorDrawable.VGroup.2
                @Override // android.util.FloatProperty
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setTranslateY(f);
                }

                @Override // android.util.Property
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getTranslateY());
                }
            };
            TRANSLATE_Y = floatProperty2;
            FloatProperty<VGroup> floatProperty3 = new FloatProperty<VGroup>("scaleX") { // from class: android.graphics.drawable.VectorDrawable.VGroup.3
                @Override // android.util.FloatProperty
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setScaleX(f);
                }

                @Override // android.util.Property
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getScaleX());
                }
            };
            SCALE_X = floatProperty3;
            FloatProperty<VGroup> floatProperty4 = new FloatProperty<VGroup>("scaleY") { // from class: android.graphics.drawable.VectorDrawable.VGroup.4
                @Override // android.util.FloatProperty
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setScaleY(f);
                }

                @Override // android.util.Property
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getScaleY());
                }
            };
            SCALE_Y = floatProperty4;
            FloatProperty<VGroup> floatProperty5 = new FloatProperty<VGroup>("pivotX") { // from class: android.graphics.drawable.VectorDrawable.VGroup.5
                @Override // android.util.FloatProperty
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setPivotX(f);
                }

                @Override // android.util.Property
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getPivotX());
                }
            };
            PIVOT_X = floatProperty5;
            FloatProperty<VGroup> floatProperty6 = new FloatProperty<VGroup>("pivotY") { // from class: android.graphics.drawable.VectorDrawable.VGroup.6
                @Override // android.util.FloatProperty
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setPivotY(f);
                }

                @Override // android.util.Property
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getPivotY());
                }
            };
            PIVOT_Y = floatProperty6;
            FloatProperty<VGroup> floatProperty7 = new FloatProperty<VGroup>(GenerateXML.ROTATION) { // from class: android.graphics.drawable.VectorDrawable.VGroup.7
                @Override // android.util.FloatProperty
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setRotation(f);
                }

                @Override // android.util.Property
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getRotation());
                }
            };
            ROTATION = floatProperty7;
            sPropertyMap = Map.of("translateX", floatProperty, "translateY", floatProperty2, "scaleX", floatProperty3, "scaleY", floatProperty4, "pivotX", floatProperty5, "pivotY", floatProperty6, GenerateXML.ROTATION, floatProperty7);
        }

        static int getPropertyIndex(String str) {
            Map<String, Integer> map = sPropertyIndexMap;
            if (map.containsKey(str)) {
                return map.get(str).intValue();
            }
            return -1;
        }

        public VGroup(VGroup vGroup, ArrayMap<String, Object> arrayMap) {
            VPath vClipPath;
            this.mChildren = new ArrayList<>();
            this.mGroupName = null;
            this.mIsStateful = vGroup.mIsStateful;
            this.mThemeAttrs = vGroup.mThemeAttrs;
            String str = vGroup.mGroupName;
            this.mGroupName = str;
            this.mChangingConfigurations = vGroup.mChangingConfigurations;
            if (str != null) {
                arrayMap.put(str, this);
            }
            this.mNativePtr = VectorDrawable.nCreateGroup(vGroup.mNativePtr);
            ArrayList<VObject> arrayList = vGroup.mChildren;
            for (int i = 0; i < arrayList.size(); i++) {
                VObject vObject = arrayList.get(i);
                if (vObject instanceof VGroup) {
                    addChild(new VGroup((VGroup) vObject, arrayMap));
                } else {
                    if (vObject instanceof VFullPath) {
                        vClipPath = new VFullPath((VFullPath) vObject);
                    } else if (vObject instanceof VClipPath) {
                        vClipPath = new VClipPath((VClipPath) vObject);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    addChild(vClipPath);
                    if (vClipPath.mPathName != null) {
                        arrayMap.put(vClipPath.mPathName, vClipPath);
                    }
                }
            }
        }

        public VGroup() {
            this.mChildren = new ArrayList<>();
            this.mGroupName = null;
            this.mNativePtr = VectorDrawable.nCreateGroup();
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        Property getProperty(String str) {
            Map<String, Property> map = sPropertyMap;
            if (map.containsKey(str)) {
                return map.get(str);
            }
            return null;
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public void addChild(VObject vObject) {
            VectorDrawable.nAddChild(this.mNativePtr, vObject.getNativePtr());
            this.mChildren.add(vObject);
            this.mIsStateful = vObject.isStateful() | this.mIsStateful;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void setTree(VirtualRefBasePtr virtualRefBasePtr) {
            super.setTree(virtualRefBasePtr);
            for (int i = 0; i < this.mChildren.size(); i++) {
                this.mChildren.get(i).setTree(virtualRefBasePtr);
            }
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme) {
            TypedArray typedArrayObtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R.styleable.VectorDrawableGroup);
            updateStateFromTypedArray(typedArrayObtainAttributes);
            typedArrayObtainAttributes.recycle();
        }

        void updateStateFromTypedArray(TypedArray typedArray) {
            this.mChangingConfigurations |= typedArray.getChangingConfigurations();
            this.mThemeAttrs = typedArray.extractThemeAttrs();
            if (this.mTransform == null) {
                this.mTransform = new float[7];
            }
            if (!VectorDrawable.nGetGroupProperties(this.mNativePtr, this.mTransform, 7)) {
                throw new RuntimeException("Error: inconsistent property count");
            }
            float f = typedArray.getFloat(5, this.mTransform[0]);
            float f2 = typedArray.getFloat(1, this.mTransform[1]);
            float f3 = typedArray.getFloat(2, this.mTransform[2]);
            float f4 = typedArray.getFloat(3, this.mTransform[3]);
            float f5 = typedArray.getFloat(4, this.mTransform[4]);
            float f6 = typedArray.getFloat(6, this.mTransform[5]);
            float f7 = typedArray.getFloat(7, this.mTransform[6]);
            String string = typedArray.getString(0);
            if (string != null) {
                this.mGroupName = string;
                VectorDrawable.nSetName(this.mNativePtr, string);
            }
            VectorDrawable.nUpdateGroupProperties(this.mNativePtr, f, f2, f3, f4, f5, f6, f7);
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean onStateChange(int[] iArr) {
            ArrayList<VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            boolean zOnStateChange = false;
            for (int i = 0; i < size; i++) {
                VObject vObject = arrayList.get(i);
                if (vObject.isStateful()) {
                    zOnStateChange |= vObject.onStateChange(iArr);
                }
            }
            return zOnStateChange;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean isStateful() {
            return this.mIsStateful;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean hasFocusStateSpecified() {
            ArrayList<VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            boolean zHasFocusStateSpecified = false;
            for (int i = 0; i < size; i++) {
                VObject vObject = arrayList.get(i);
                if (vObject.isStateful()) {
                    zHasFocusStateSpecified |= vObject.hasFocusStateSpecified();
                }
            }
            return zHasFocusStateSpecified;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        int getNativeSize() {
            int nativeSize = 100;
            for (int i = 0; i < this.mChildren.size(); i++) {
                nativeSize += this.mChildren.get(i).getNativeSize();
            }
            return nativeSize;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            ArrayList<VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i).canApplyTheme()) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void applyTheme(Resources.Theme theme) {
            int[] iArr = this.mThemeAttrs;
            if (iArr != null) {
                TypedArray typedArrayResolveAttributes = theme.resolveAttributes(iArr, R.styleable.VectorDrawableGroup);
                updateStateFromTypedArray(typedArrayResolveAttributes);
                typedArrayResolveAttributes.recycle();
            }
            ArrayList<VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                VObject vObject = arrayList.get(i);
                if (vObject.canApplyTheme()) {
                    vObject.applyTheme(theme);
                    this.mIsStateful = vObject.isStateful() | this.mIsStateful;
                }
            }
        }

        public float getRotation() {
            if (isTreeValid()) {
                return VectorDrawable.nGetRotation(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setRotation(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetRotation(this.mNativePtr, f);
            }
        }

        public float getPivotX() {
            if (isTreeValid()) {
                return VectorDrawable.nGetPivotX(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setPivotX(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetPivotX(this.mNativePtr, f);
            }
        }

        public float getPivotY() {
            if (isTreeValid()) {
                return VectorDrawable.nGetPivotY(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setPivotY(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetPivotY(this.mNativePtr, f);
            }
        }

        public float getScaleX() {
            if (isTreeValid()) {
                return VectorDrawable.nGetScaleX(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setScaleX(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetScaleX(this.mNativePtr, f);
            }
        }

        public float getScaleY() {
            if (isTreeValid()) {
                return VectorDrawable.nGetScaleY(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setScaleY(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetScaleY(this.mNativePtr, f);
            }
        }

        public float getTranslateX() {
            if (isTreeValid()) {
                return VectorDrawable.nGetTranslateX(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setTranslateX(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetTranslateX(this.mNativePtr, f);
            }
        }

        public float getTranslateY() {
            if (isTreeValid()) {
                return VectorDrawable.nGetTranslateY(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setTranslateY(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetTranslateY(this.mNativePtr, f);
            }
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        void updatePathColorTraversal(String str, int i, int i2) {
            for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
                this.mChildren.get(i3).updatePathColorTraversal(str, i, i2);
            }
        }
    }

    static abstract class VPath extends VObject {
        private static final Property<VPath, PathParser.PathData> PATH_DATA = new Property<VPath, PathParser.PathData>(PathParser.PathData.class, "pathData") { // from class: android.graphics.drawable.VectorDrawable.VPath.1
            @Override // android.util.Property
            public void set(VPath vPath, PathParser.PathData pathData) {
                vPath.setPathData(pathData);
            }

            @Override // android.util.Property
            public PathParser.PathData get(VPath vPath) {
                return vPath.getPathData();
            }
        };
        int mChangingConfigurations;
        protected PathParser.PathData mPathData;
        String mPathName;

        @Override // android.graphics.drawable.VectorDrawable.VObject
        Property getProperty(String str) {
            Property<VPath, PathParser.PathData> property = PATH_DATA;
            if (property.getName().equals(str)) {
                return property;
            }
            return null;
        }

        public VPath() {
            this.mPathData = null;
        }

        public VPath(VPath vPath) {
            this.mPathData = null;
            this.mPathName = vPath.mPathName;
            this.mChangingConfigurations = vPath.mChangingConfigurations;
            this.mPathData = vPath.mPathData != null ? new PathParser.PathData(vPath.mPathData) : null;
        }

        public String getPathName() {
            return this.mPathName;
        }

        public PathParser.PathData getPathData() {
            return this.mPathData;
        }

        public void setPathData(PathParser.PathData pathData) {
            this.mPathData.setPathData(pathData);
            if (isTreeValid()) {
                VectorDrawable.nSetPathData(getNativePtr(), this.mPathData.getNativePtr());
            }
        }
    }

    private static class VClipPath extends VPath {
        private static final int NATIVE_ALLOCATION_SIZE = 120;
        private final long mNativePtr;

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void applyTheme(Resources.Theme theme) {
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean canApplyTheme() {
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        int getNativeSize() {
            return 120;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean hasFocusStateSpecified() {
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean isStateful() {
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean onStateChange(int[] iArr) {
            return false;
        }

        public VClipPath() {
            this.mNativePtr = VectorDrawable.nCreateClipPath();
        }

        public VClipPath(VClipPath vClipPath) {
            super(vClipPath);
            this.mNativePtr = VectorDrawable.nCreateClipPath(vClipPath.mNativePtr);
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme) {
            TypedArray typedArrayObtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R.styleable.VectorDrawableClipPath);
            updateStateFromTypedArray(typedArrayObtainAttributes);
            typedArrayObtainAttributes.recycle();
        }

        private void updateStateFromTypedArray(TypedArray typedArray) {
            this.mChangingConfigurations |= typedArray.getChangingConfigurations();
            String string = typedArray.getString(0);
            if (string != null) {
                this.mPathName = string;
                VectorDrawable.nSetName(this.mNativePtr, this.mPathName);
            }
            String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.mPathData = new PathParser.PathData(string2);
                VectorDrawable.nSetPathString(this.mNativePtr, string2, string2.length());
            }
        }
    }

    static class VFullPath extends VPath {
        private static final Property<VFullPath, Float> FILL_ALPHA;
        private static final int FILL_ALPHA_INDEX = 4;
        private static final Property<VFullPath, Integer> FILL_COLOR;
        private static final int FILL_COLOR_INDEX = 3;
        private static final int FILL_TYPE_INDEX = 11;
        private static final int NATIVE_ALLOCATION_SIZE = 264;
        private static final Property<VFullPath, Float> STROKE_ALPHA;
        private static final int STROKE_ALPHA_INDEX = 2;
        private static final Property<VFullPath, Integer> STROKE_COLOR;
        private static final int STROKE_COLOR_INDEX = 1;
        private static final int STROKE_LINE_CAP_INDEX = 8;
        private static final int STROKE_LINE_JOIN_INDEX = 9;
        private static final int STROKE_MITER_LIMIT_INDEX = 10;
        private static final Property<VFullPath, Float> STROKE_WIDTH;
        private static final int STROKE_WIDTH_INDEX = 0;
        private static final int TOTAL_PROPERTY_COUNT = 12;
        private static final Property<VFullPath, Float> TRIM_PATH_END;
        private static final int TRIM_PATH_END_INDEX = 6;
        private static final Property<VFullPath, Float> TRIM_PATH_OFFSET;
        private static final int TRIM_PATH_OFFSET_INDEX = 7;
        private static final Property<VFullPath, Float> TRIM_PATH_START;
        private static final int TRIM_PATH_START_INDEX = 5;
        private static final Map<String, Integer> sPropertyIndexMap = Map.of("strokeWidth", 0, "strokeColor", 1, "strokeAlpha", 2, "fillColor", 3, "fillAlpha", 4, "trimPathStart", 5, "trimPathEnd", 6, "trimPathOffset", 7);
        private static final Map<String, Property> sPropertyMap;
        ComplexColor mFillColors;
        private final long mNativePtr;
        private byte[] mPropertyData;
        ComplexColor mStrokeColors;
        private int[] mThemeAttrs;

        @Override // android.graphics.drawable.VectorDrawable.VObject
        int getNativeSize() {
            return 264;
        }

        static {
            FloatProperty<VFullPath> floatProperty = new FloatProperty<VFullPath>("strokeWidth") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.1
                @Override // android.util.FloatProperty
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setStrokeWidth(f);
                }

                @Override // android.util.Property
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getStrokeWidth());
                }
            };
            STROKE_WIDTH = floatProperty;
            IntProperty<VFullPath> intProperty = new IntProperty<VFullPath>("strokeColor") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.2
                @Override // android.util.IntProperty
                public void setValue(VFullPath vFullPath, int i) {
                    vFullPath.setStrokeColor(i);
                }

                @Override // android.util.Property
                public Integer get(VFullPath vFullPath) {
                    return Integer.valueOf(vFullPath.getStrokeColor());
                }
            };
            STROKE_COLOR = intProperty;
            FloatProperty<VFullPath> floatProperty2 = new FloatProperty<VFullPath>("strokeAlpha") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.3
                @Override // android.util.FloatProperty
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setStrokeAlpha(f);
                }

                @Override // android.util.Property
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getStrokeAlpha());
                }
            };
            STROKE_ALPHA = floatProperty2;
            IntProperty<VFullPath> intProperty2 = new IntProperty<VFullPath>("fillColor") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.4
                @Override // android.util.IntProperty
                public void setValue(VFullPath vFullPath, int i) {
                    vFullPath.setFillColor(i);
                }

                @Override // android.util.Property
                public Integer get(VFullPath vFullPath) {
                    return Integer.valueOf(vFullPath.getFillColor());
                }
            };
            FILL_COLOR = intProperty2;
            FloatProperty<VFullPath> floatProperty3 = new FloatProperty<VFullPath>("fillAlpha") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.5
                @Override // android.util.FloatProperty
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setFillAlpha(f);
                }

                @Override // android.util.Property
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getFillAlpha());
                }
            };
            FILL_ALPHA = floatProperty3;
            FloatProperty<VFullPath> floatProperty4 = new FloatProperty<VFullPath>("trimPathStart") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.6
                @Override // android.util.FloatProperty
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setTrimPathStart(f);
                }

                @Override // android.util.Property
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getTrimPathStart());
                }
            };
            TRIM_PATH_START = floatProperty4;
            FloatProperty<VFullPath> floatProperty5 = new FloatProperty<VFullPath>("trimPathEnd") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.7
                @Override // android.util.FloatProperty
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setTrimPathEnd(f);
                }

                @Override // android.util.Property
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getTrimPathEnd());
                }
            };
            TRIM_PATH_END = floatProperty5;
            FloatProperty<VFullPath> floatProperty6 = new FloatProperty<VFullPath>("trimPathOffset") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.8
                @Override // android.util.FloatProperty
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setTrimPathOffset(f);
                }

                @Override // android.util.Property
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getTrimPathOffset());
                }
            };
            TRIM_PATH_OFFSET = floatProperty6;
            sPropertyMap = Map.of("strokeWidth", floatProperty, "strokeColor", intProperty, "strokeAlpha", floatProperty2, "fillColor", intProperty2, "fillAlpha", floatProperty3, "trimPathStart", floatProperty4, "trimPathEnd", floatProperty5, "trimPathOffset", floatProperty6);
        }

        public VFullPath() {
            this.mStrokeColors = null;
            this.mFillColors = null;
            this.mNativePtr = VectorDrawable.nCreateFullPath();
        }

        public VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.mStrokeColors = null;
            this.mFillColors = null;
            this.mNativePtr = VectorDrawable.nCreateFullPath(vFullPath.mNativePtr);
            this.mThemeAttrs = vFullPath.mThemeAttrs;
            this.mStrokeColors = vFullPath.mStrokeColors;
            this.mFillColors = vFullPath.mFillColors;
        }

        @Override // android.graphics.drawable.VectorDrawable.VPath, android.graphics.drawable.VectorDrawable.VObject
        Property getProperty(String str) {
            Property property = super.getProperty(str);
            if (property != null) {
                return property;
            }
            Map<String, Property> map = sPropertyMap;
            if (map.containsKey(str)) {
                return map.get(str);
            }
            return null;
        }

        int getPropertyIndex(String str) {
            Map<String, Integer> map = sPropertyIndexMap;
            if (map.containsKey(str)) {
                return map.get(str).intValue();
            }
            return -1;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean onStateChange(int[] iArr) {
            boolean z;
            ComplexColor complexColor = this.mStrokeColors;
            if (complexColor == null || !(complexColor instanceof ColorStateList)) {
                z = false;
            } else {
                int strokeColor = getStrokeColor();
                int colorForState = ((ColorStateList) this.mStrokeColors).getColorForState(iArr, strokeColor);
                z = strokeColor != colorForState;
                if (strokeColor != colorForState) {
                    VectorDrawable.nSetStrokeColor(this.mNativePtr, colorForState);
                }
            }
            ComplexColor complexColor2 = this.mFillColors;
            if (complexColor2 == null || !(complexColor2 instanceof ColorStateList)) {
                return z;
            }
            int fillColor = getFillColor();
            int colorForState2 = ((ColorStateList) this.mFillColors).getColorForState(iArr, fillColor);
            boolean z2 = (fillColor != colorForState2) | z;
            if (fillColor != colorForState2) {
                VectorDrawable.nSetFillColor(this.mNativePtr, colorForState2);
            }
            return z2;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean isStateful() {
            return (this.mStrokeColors == null && this.mFillColors == null) ? false : true;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean hasFocusStateSpecified() {
            ComplexColor complexColor;
            ComplexColor complexColor2 = this.mStrokeColors;
            return complexColor2 != null && (complexColor2 instanceof ColorStateList) && ((ColorStateList) complexColor2).hasFocusStateSpecified() && (complexColor = this.mFillColors) != null && (complexColor instanceof ColorStateList) && ((ColorStateList) complexColor).hasFocusStateSpecified();
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme) {
            TypedArray typedArrayObtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R.styleable.VectorDrawablePath);
            updateStateFromTypedArray(typedArrayObtainAttributes);
            typedArrayObtainAttributes.recycle();
        }

        private void updateStateFromTypedArray(TypedArray typedArray) {
            int i;
            int i2;
            Shader shader;
            int defaultColor;
            int defaultColor2;
            int i3;
            long nativeInstance;
            if (this.mPropertyData == null) {
                this.mPropertyData = new byte[48];
            }
            if (!VectorDrawable.nGetFullPathProperties(this.mNativePtr, this.mPropertyData, 48)) {
                throw new RuntimeException("Error: inconsistent property count");
            }
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.mPropertyData);
            byteBufferWrap.order(ByteOrder.nativeOrder());
            float f = byteBufferWrap.getFloat(0);
            int i4 = byteBufferWrap.getInt(4);
            float f2 = byteBufferWrap.getFloat(8);
            int i5 = byteBufferWrap.getInt(12);
            float f3 = byteBufferWrap.getFloat(16);
            float f4 = byteBufferWrap.getFloat(20);
            float f5 = byteBufferWrap.getFloat(24);
            float f6 = byteBufferWrap.getFloat(28);
            int i6 = byteBufferWrap.getInt(32);
            int i7 = byteBufferWrap.getInt(36);
            float f7 = byteBufferWrap.getFloat(40);
            int i8 = byteBufferWrap.getInt(44);
            this.mChangingConfigurations |= typedArray.getChangingConfigurations();
            this.mThemeAttrs = typedArray.extractThemeAttrs();
            String string = typedArray.getString(0);
            if (string != null) {
                this.mPathName = string;
                i = i5;
                VectorDrawable.nSetName(this.mNativePtr, this.mPathName);
            } else {
                i = i5;
            }
            String string2 = typedArray.getString(2);
            if (string2 != null) {
                this.mPathData = new PathParser.PathData(string2);
                i2 = i4;
                VectorDrawable.nSetPathString(this.mNativePtr, string2, string2.length());
            } else {
                i2 = i4;
            }
            ComplexColor complexColor = typedArray.getComplexColor(1);
            Shader shader2 = null;
            if (complexColor != null) {
                if (complexColor instanceof GradientColor) {
                    this.mFillColors = complexColor;
                    shader = ((GradientColor) complexColor).getShader();
                } else {
                    if (complexColor.isStateful() || complexColor.canApplyTheme()) {
                        this.mFillColors = complexColor;
                    } else {
                        this.mFillColors = null;
                    }
                    shader = null;
                }
                defaultColor = complexColor.getDefaultColor();
            } else {
                shader = null;
                defaultColor = i;
            }
            ComplexColor complexColor2 = typedArray.getComplexColor(3);
            if (complexColor2 != null) {
                if (complexColor2 instanceof GradientColor) {
                    this.mStrokeColors = complexColor2;
                    shader2 = ((GradientColor) complexColor2).getShader();
                } else if (complexColor2.isStateful() || complexColor2.canApplyTheme()) {
                    this.mStrokeColors = complexColor2;
                } else {
                    this.mStrokeColors = null;
                }
                defaultColor2 = complexColor2.getDefaultColor();
            } else {
                defaultColor2 = i2;
            }
            Shader shader3 = shader;
            long j = this.mNativePtr;
            if (shader3 != null) {
                i3 = i8;
                nativeInstance = shader3.getNativeInstance();
            } else {
                i3 = i8;
                nativeInstance = 0;
            }
            VectorDrawable.nUpdateFullPathFillGradient(j, nativeInstance);
            VectorDrawable.nUpdateFullPathStrokeGradient(this.mNativePtr, shader2 != null ? shader2.getNativeInstance() : 0L);
            float f8 = typedArray.getFloat(12, f3);
            int i9 = typedArray.getInt(8, i6);
            int i10 = typedArray.getInt(9, i7);
            float f9 = typedArray.getFloat(10, f7);
            VectorDrawable.nUpdateFullPathProperties(this.mNativePtr, typedArray.getFloat(4, f), defaultColor2, typedArray.getFloat(11, f2), defaultColor, f8, typedArray.getFloat(5, f4), typedArray.getFloat(6, f5), typedArray.getFloat(7, f6), f9, i9, i10, typedArray.getInt(13, i3));
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            return canComplexColorApplyTheme(this.mFillColors) || canComplexColorApplyTheme(this.mStrokeColors);
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void applyTheme(Resources.Theme theme) {
            int[] iArr = this.mThemeAttrs;
            if (iArr != null) {
                TypedArray typedArrayResolveAttributes = theme.resolveAttributes(iArr, R.styleable.VectorDrawablePath);
                updateStateFromTypedArray(typedArrayResolveAttributes);
                typedArrayResolveAttributes.recycle();
            }
            boolean zCanComplexColorApplyTheme = canComplexColorApplyTheme(this.mFillColors);
            boolean zCanComplexColorApplyTheme2 = canComplexColorApplyTheme(this.mStrokeColors);
            if (zCanComplexColorApplyTheme) {
                ComplexColor complexColorObtainForTheme = this.mFillColors.obtainForTheme(theme);
                this.mFillColors = complexColorObtainForTheme;
                if (complexColorObtainForTheme instanceof GradientColor) {
                    VectorDrawable.nUpdateFullPathFillGradient(this.mNativePtr, ((GradientColor) complexColorObtainForTheme).getShader().getNativeInstance());
                } else if (complexColorObtainForTheme instanceof ColorStateList) {
                    VectorDrawable.nSetFillColor(this.mNativePtr, complexColorObtainForTheme.getDefaultColor());
                }
            }
            if (zCanComplexColorApplyTheme2) {
                ComplexColor complexColorObtainForTheme2 = this.mStrokeColors.obtainForTheme(theme);
                this.mStrokeColors = complexColorObtainForTheme2;
                if (complexColorObtainForTheme2 instanceof GradientColor) {
                    VectorDrawable.nUpdateFullPathStrokeGradient(this.mNativePtr, ((GradientColor) complexColorObtainForTheme2).getShader().getNativeInstance());
                } else if (complexColorObtainForTheme2 instanceof ColorStateList) {
                    VectorDrawable.nSetStrokeColor(this.mNativePtr, complexColorObtainForTheme2.getDefaultColor());
                }
            }
        }

        private boolean canComplexColorApplyTheme(ComplexColor complexColor) {
            return complexColor != null && complexColor.canApplyTheme();
        }

        int getStrokeColor() {
            if (isTreeValid()) {
                return VectorDrawable.nGetStrokeColor(this.mNativePtr);
            }
            return 0;
        }

        void setStrokeColor(int i) {
            this.mStrokeColors = null;
            if (isTreeValid()) {
                VectorDrawable.nSetStrokeColor(this.mNativePtr, i);
            }
        }

        float getStrokeWidth() {
            if (isTreeValid()) {
                return VectorDrawable.nGetStrokeWidth(this.mNativePtr);
            }
            return 0.0f;
        }

        void setStrokeWidth(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetStrokeWidth(this.mNativePtr, f);
            }
        }

        float getStrokeAlpha() {
            if (isTreeValid()) {
                return VectorDrawable.nGetStrokeAlpha(this.mNativePtr);
            }
            return 0.0f;
        }

        void setStrokeAlpha(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetStrokeAlpha(this.mNativePtr, f);
            }
        }

        int getFillColor() {
            if (isTreeValid()) {
                return VectorDrawable.nGetFillColor(this.mNativePtr);
            }
            return 0;
        }

        void setFillColor(int i) {
            this.mFillColors = null;
            if (isTreeValid()) {
                VectorDrawable.nSetFillColor(this.mNativePtr, i);
            }
        }

        float getFillAlpha() {
            if (isTreeValid()) {
                return VectorDrawable.nGetFillAlpha(this.mNativePtr);
            }
            return 0.0f;
        }

        void setFillAlpha(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetFillAlpha(this.mNativePtr, f);
            }
        }

        float getTrimPathStart() {
            if (isTreeValid()) {
                return VectorDrawable.nGetTrimPathStart(this.mNativePtr);
            }
            return 0.0f;
        }

        void setTrimPathStart(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetTrimPathStart(this.mNativePtr, f);
            }
        }

        float getTrimPathEnd() {
            if (isTreeValid()) {
                return VectorDrawable.nGetTrimPathEnd(this.mNativePtr);
            }
            return 0.0f;
        }

        void setTrimPathEnd(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetTrimPathEnd(this.mNativePtr, f);
            }
        }

        float getTrimPathOffset() {
            if (isTreeValid()) {
                return VectorDrawable.nGetTrimPathOffset(this.mNativePtr);
            }
            return 0.0f;
        }

        void setTrimPathOffset(float f) {
            if (isTreeValid()) {
                VectorDrawable.nSetTrimPathOffset(this.mNativePtr, f);
            }
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        void updatePathColorTraversal(String str, int i, int i2) {
            if ("all".equals(str) || (this.mPathName != null && this.mPathName.equals(str))) {
                VectorDrawable.updatePathColor(this, i, i2);
            }
        }
    }

    static abstract class VObject {
        VirtualRefBasePtr mTreePtr = null;

        abstract void applyTheme(Resources.Theme theme);

        abstract boolean canApplyTheme();

        abstract long getNativePtr();

        abstract int getNativeSize();

        abstract Property getProperty(String str);

        abstract boolean hasFocusStateSpecified();

        abstract void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme);

        abstract boolean isStateful();

        abstract boolean onStateChange(int[] iArr);

        void updatePathColorTraversal(String str, int i, int i2) {
        }

        VObject() {
        }

        boolean isTreeValid() {
            VirtualRefBasePtr virtualRefBasePtr = this.mTreePtr;
            return (virtualRefBasePtr == null || virtualRefBasePtr.get() == 0) ? false : true;
        }

        void setTree(VirtualRefBasePtr virtualRefBasePtr) {
            this.mTreePtr = virtualRefBasePtr;
        }
    }
}
