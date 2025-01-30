package androidx.vectordrawable.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import androidx.collection.ArrayMap;
import androidx.core.content.res.ColorStateListInflaterCompat;
import androidx.core.content.res.ComplexColorCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.core.graphics.drawable.DrawableCompat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VectorDrawableCompat extends VectorDrawableCommon {
    public static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    public boolean mAllowCaching;
    public ColorFilter mColorFilter;
    public boolean mMutated;
    public PorterDuffColorFilter mTintFilter;
    public final Rect mTmpBounds;
    public final float[] mTmpFloats;
    public final Matrix mTmpMatrix;
    public VectorDrawableCompatState mVectorState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VClipPath extends VPath {
        public VClipPath() {
        }

        public VClipPath(VClipPath vClipPath) {
            super(vClipPath);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class VObject {
        private VObject() {
        }

        public boolean isStateful() {
            return false;
        }

        public boolean onStateChanged(int[] iArr) {
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VectorDrawableCompatState extends Drawable.ConstantState {
        public boolean mAutoMirrored;
        public boolean mCacheDirty;
        public boolean mCachedAutoMirrored;
        public Bitmap mCachedBitmap;
        public int mCachedRootAlpha;
        public ColorStateList mCachedTint;
        public PorterDuff.Mode mCachedTintMode;
        public int mChangingConfigurations;
        public Paint mTempPaint;
        public ColorStateList mTint;
        public PorterDuff.Mode mTintMode;
        public VPathRenderer mVPathRenderer;

        public VectorDrawableCompatState(VectorDrawableCompatState vectorDrawableCompatState) {
            this.mTint = null;
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE;
            if (vectorDrawableCompatState != null) {
                this.mChangingConfigurations = vectorDrawableCompatState.mChangingConfigurations;
                VPathRenderer vPathRenderer = new VPathRenderer(vectorDrawableCompatState.mVPathRenderer);
                this.mVPathRenderer = vPathRenderer;
                if (vectorDrawableCompatState.mVPathRenderer.mFillPaint != null) {
                    vPathRenderer.mFillPaint = new Paint(vectorDrawableCompatState.mVPathRenderer.mFillPaint);
                }
                if (vectorDrawableCompatState.mVPathRenderer.mStrokePaint != null) {
                    this.mVPathRenderer.mStrokePaint = new Paint(vectorDrawableCompatState.mVPathRenderer.mStrokePaint);
                }
                this.mTint = vectorDrawableCompatState.mTint;
                this.mTintMode = vectorDrawableCompatState.mTintMode;
                this.mAutoMirrored = vectorDrawableCompatState.mAutoMirrored;
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        public VectorDrawableCompatState() {
            this.mTint = null;
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE;
            this.mVPathRenderer = new VPathRenderer();
        }
    }

    public VectorDrawableCompat() {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableCompatState();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable == null) {
            return false;
        }
        drawable.canApplyTheme();
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d3, code lost:
    
        if ((r15 == r8.getWidth() && r3 == r6.mCachedBitmap.getHeight()) == false) goto L40;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(Canvas canvas) {
        Paint paint;
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() <= 0 || this.mTmpBounds.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.mColorFilter;
        if (colorFilter == null) {
            colorFilter = this.mTintFilter;
        }
        canvas.getMatrix(this.mTmpMatrix);
        this.mTmpMatrix.getValues(this.mTmpFloats);
        float abs = Math.abs(this.mTmpFloats[0]);
        float abs2 = Math.abs(this.mTmpFloats[4]);
        float abs3 = Math.abs(this.mTmpFloats[1]);
        float abs4 = Math.abs(this.mTmpFloats[3]);
        if (abs3 != 0.0f || abs4 != 0.0f) {
            abs = 1.0f;
            abs2 = 1.0f;
        }
        int min = Math.min(2048, (int) (this.mTmpBounds.width() * abs));
        int min2 = Math.min(2048, (int) (this.mTmpBounds.height() * abs2));
        if (min <= 0 || min2 <= 0) {
            return;
        }
        int save = canvas.save();
        Rect rect = this.mTmpBounds;
        canvas.translate(rect.left, rect.top);
        if (isAutoMirrored() && getLayoutDirection() == 1) {
            canvas.translate(this.mTmpBounds.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.mTmpBounds.offsetTo(0, 0);
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        Bitmap bitmap = vectorDrawableCompatState.mCachedBitmap;
        if (bitmap != null) {
        }
        vectorDrawableCompatState.mCachedBitmap = Bitmap.createBitmap(min, min2, Bitmap.Config.ARGB_8888);
        vectorDrawableCompatState.mCacheDirty = true;
        if (this.mAllowCaching) {
            VectorDrawableCompatState vectorDrawableCompatState2 = this.mVectorState;
            if (!(!vectorDrawableCompatState2.mCacheDirty && vectorDrawableCompatState2.mCachedTint == vectorDrawableCompatState2.mTint && vectorDrawableCompatState2.mCachedTintMode == vectorDrawableCompatState2.mTintMode && vectorDrawableCompatState2.mCachedAutoMirrored == vectorDrawableCompatState2.mAutoMirrored && vectorDrawableCompatState2.mCachedRootAlpha == vectorDrawableCompatState2.mVPathRenderer.getRootAlpha())) {
                VectorDrawableCompatState vectorDrawableCompatState3 = this.mVectorState;
                vectorDrawableCompatState3.mCachedBitmap.eraseColor(0);
                Canvas canvas2 = new Canvas(vectorDrawableCompatState3.mCachedBitmap);
                VPathRenderer vPathRenderer = vectorDrawableCompatState3.mVPathRenderer;
                vPathRenderer.drawGroupTree(vPathRenderer.mRootGroup, VPathRenderer.IDENTITY_MATRIX, canvas2, min, min2);
                VectorDrawableCompatState vectorDrawableCompatState4 = this.mVectorState;
                vectorDrawableCompatState4.mCachedTint = vectorDrawableCompatState4.mTint;
                vectorDrawableCompatState4.mCachedTintMode = vectorDrawableCompatState4.mTintMode;
                vectorDrawableCompatState4.mCachedRootAlpha = vectorDrawableCompatState4.mVPathRenderer.getRootAlpha();
                vectorDrawableCompatState4.mCachedAutoMirrored = vectorDrawableCompatState4.mAutoMirrored;
                vectorDrawableCompatState4.mCacheDirty = false;
            }
        } else {
            VectorDrawableCompatState vectorDrawableCompatState5 = this.mVectorState;
            vectorDrawableCompatState5.mCachedBitmap.eraseColor(0);
            Canvas canvas3 = new Canvas(vectorDrawableCompatState5.mCachedBitmap);
            VPathRenderer vPathRenderer2 = vectorDrawableCompatState5.mVPathRenderer;
            vPathRenderer2.drawGroupTree(vPathRenderer2.mRootGroup, VPathRenderer.IDENTITY_MATRIX, canvas3, min, min2);
        }
        VectorDrawableCompatState vectorDrawableCompatState6 = this.mVectorState;
        Rect rect2 = this.mTmpBounds;
        if ((vectorDrawableCompatState6.mVPathRenderer.getRootAlpha() < 255) || colorFilter != null) {
            if (vectorDrawableCompatState6.mTempPaint == null) {
                Paint paint2 = new Paint();
                vectorDrawableCompatState6.mTempPaint = paint2;
                paint2.setFilterBitmap(true);
            }
            vectorDrawableCompatState6.mTempPaint.setAlpha(vectorDrawableCompatState6.mVPathRenderer.getRootAlpha());
            vectorDrawableCompatState6.mTempPaint.setColorFilter(colorFilter);
            paint = vectorDrawableCompatState6.mTempPaint;
        } else {
            paint = null;
        }
        canvas.drawBitmap(vectorDrawableCompatState6.mCachedBitmap, (Rect) null, rect2, paint);
        canvas.restoreToCount(save);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        Drawable drawable = this.mDelegateDrawable;
        return drawable != null ? drawable.getAlpha() : this.mVectorState.mVPathRenderer.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getChangingConfigurations();
        }
        return this.mVectorState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        Drawable drawable = this.mDelegateDrawable;
        return drawable != null ? drawable.getColorFilter() : this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (this.mDelegateDrawable != null) {
            return new VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
        }
        this.mVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mVectorState;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        Drawable drawable = this.mDelegateDrawable;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.mVectorState.mVPathRenderer.mBaseHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        Drawable drawable = this.mDelegateDrawable;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.mVectorState.mVPathRenderer.mBaseWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        Drawable drawable = this.mDelegateDrawable;
        return drawable != null ? drawable.isAutoMirrored() : this.mVectorState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList;
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.isStateful();
        }
        if (!super.isStateful()) {
            VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
            if (vectorDrawableCompatState != null) {
                VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
                if (vPathRenderer.mIsStateful == null) {
                    vPathRenderer.mIsStateful = Boolean.valueOf(vPathRenderer.mRootGroup.isStateful());
                }
                if (vPathRenderer.mIsStateful.booleanValue() || ((colorStateList = this.mVectorState.mTint) != null && colorStateList.isStateful())) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        boolean z;
        PorterDuff.Mode mode;
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        ColorStateList colorStateList = vectorDrawableCompatState.mTint;
        if (colorStateList == null || (mode = vectorDrawableCompatState.mTintMode) == null) {
            z = false;
        } else {
            this.mTintFilter = updateTintFilter(colorStateList, mode);
            invalidateSelf();
            z = true;
        }
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
        if (vPathRenderer.mIsStateful == null) {
            vPathRenderer.mIsStateful = Boolean.valueOf(vPathRenderer.mRootGroup.isStateful());
        }
        if (vPathRenderer.mIsStateful.booleanValue()) {
            boolean onStateChanged = vectorDrawableCompatState.mVPathRenderer.mRootGroup.onStateChanged(iArr);
            vectorDrawableCompatState.mCacheDirty |= onStateChanged;
            if (onStateChanged) {
                invalidateSelf();
                return true;
            }
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public final void scheduleSelf(Runnable runnable, long j) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j);
        } else {
            super.scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setAlpha(i);
        } else if (this.mVectorState.mVPathRenderer.getRootAlpha() != i) {
            this.mVectorState.mVPathRenderer.setRootAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAutoMirrored(boolean z) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setAutoMirrored(z);
        } else {
            this.mVectorState.mAutoMirrored = z;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.mColorFilter = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            DrawableCompat.setTint(i, drawable);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setTintList(colorStateList);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.mTint != colorStateList) {
            vectorDrawableCompatState.mTint = colorStateList;
            this.mTintFilter = updateTintFilter(colorStateList, vectorDrawableCompatState.mTintMode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setTintMode(mode);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.mTintMode != mode) {
            vectorDrawableCompatState.mTintMode = mode;
            this.mTintFilter = updateTintFilter(vectorDrawableCompatState.mTint, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.mDelegateDrawable;
        return drawable != null ? drawable.setVisible(z, z2) : super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    public final PorterDuffColorFilter updateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VectorDrawableDelegateState extends Drawable.ConstantState {
        public final Drawable.ConstantState mDelegateState;

        public VectorDrawableDelegateState(Drawable.ConstantState constantState) {
            this.mDelegateState = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable();
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(resources);
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class VPath extends VObject {
        public final int mChangingConfigurations;
        public int mFillRule;
        public PathParser.PathDataNode[] mNodes;
        public String mPathName;

        public VPath() {
            super();
            this.mNodes = null;
            this.mFillRule = 0;
        }

        public PathParser.PathDataNode[] getPathData() {
            return this.mNodes;
        }

        public String getPathName() {
            return this.mPathName;
        }

        public void setPathData(PathParser.PathDataNode[] pathDataNodeArr) {
            if (!PathParser.canMorph(this.mNodes, pathDataNodeArr)) {
                this.mNodes = PathParser.deepCopyNodes(pathDataNodeArr);
                return;
            }
            PathParser.PathDataNode[] pathDataNodeArr2 = this.mNodes;
            for (int i = 0; i < pathDataNodeArr.length; i++) {
                pathDataNodeArr2[i].mType = pathDataNodeArr[i].mType;
                int i2 = 0;
                while (true) {
                    float[] fArr = pathDataNodeArr[i].mParams;
                    if (i2 < fArr.length) {
                        pathDataNodeArr2[i].mParams[i2] = fArr[i2];
                        i2++;
                    }
                }
            }
        }

        public VPath(VPath vPath) {
            super();
            this.mNodes = null;
            this.mFillRule = 0;
            this.mPathName = vPath.mPathName;
            this.mChangingConfigurations = vPath.mChangingConfigurations;
            this.mNodes = PathParser.deepCopyNodes(vPath.mNodes);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00dc  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        ColorStateList colorStateList;
        VPathRenderer vPathRenderer;
        int i;
        boolean z;
        int i2;
        char c;
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet, theme);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        vectorDrawableCompatState.mVPathRenderer = new VPathRenderer();
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY);
        VectorDrawableCompatState vectorDrawableCompatState2 = this.mVectorState;
        VPathRenderer vPathRenderer2 = vectorDrawableCompatState2.mVPathRenderer;
        int namedInt = TypedArrayUtils.getNamedInt(obtainAttributes, xmlPullParser, "tintMode", 6, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        int i3 = 3;
        if (namedInt == 3) {
            mode = PorterDuff.Mode.SRC_OVER;
        } else if (namedInt != 5) {
            if (namedInt != 9) {
                switch (namedInt) {
                    case 14:
                        mode = PorterDuff.Mode.MULTIPLY;
                        break;
                    case 15:
                        mode = PorterDuff.Mode.SCREEN;
                        break;
                    case 16:
                        mode = PorterDuff.Mode.ADD;
                        break;
                }
            } else {
                mode = PorterDuff.Mode.SRC_ATOP;
            }
        }
        vectorDrawableCompatState2.mTintMode = mode;
        boolean z2 = false;
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "tint")) {
            TypedValue typedValue = new TypedValue();
            obtainAttributes.getValue(1, typedValue);
            int i4 = typedValue.type;
            if (i4 == 2) {
                throw new UnsupportedOperationException("Failed to resolve attribute at index 1: " + typedValue);
            }
            if (i4 >= 28 && i4 <= 31) {
                colorStateList = ColorStateList.valueOf(typedValue.data);
            } else {
                Resources resources2 = obtainAttributes.getResources();
                int resourceId = obtainAttributes.getResourceId(1, 0);
                ThreadLocal threadLocal = ColorStateListInflaterCompat.sTempTypedValue;
                try {
                    colorStateList = ColorStateListInflaterCompat.createFromXml(resources2, resources2.getXml(resourceId), theme);
                } catch (Exception e) {
                    Log.e("CSLCompat", "Failed to inflate ColorStateList.", e);
                }
            }
            if (colorStateList != null) {
                vectorDrawableCompatState2.mTint = colorStateList;
            }
            boolean z3 = vectorDrawableCompatState2.mAutoMirrored;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "autoMirrored")) {
                z3 = obtainAttributes.getBoolean(5, z3);
            }
            vectorDrawableCompatState2.mAutoMirrored = z3;
            vPathRenderer2.mViewportWidth = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "viewportWidth", 7, vPathRenderer2.mViewportWidth);
            float namedFloat = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "viewportHeight", 8, vPathRenderer2.mViewportHeight);
            vPathRenderer2.mViewportHeight = namedFloat;
            if (vPathRenderer2.mViewportWidth > 0.0f) {
                throw new XmlPullParserException(obtainAttributes.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
            }
            if (namedFloat > 0.0f) {
                vPathRenderer2.mBaseWidth = obtainAttributes.getDimension(3, vPathRenderer2.mBaseWidth);
                float dimension = obtainAttributes.getDimension(2, vPathRenderer2.mBaseHeight);
                vPathRenderer2.mBaseHeight = dimension;
                if (vPathRenderer2.mBaseWidth <= 0.0f) {
                    throw new XmlPullParserException(obtainAttributes.getPositionDescription() + "<vector> tag requires width > 0");
                }
                if (dimension > 0.0f) {
                    vPathRenderer2.setAlpha(TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "alpha", 4, vPathRenderer2.getAlpha()));
                    String string = obtainAttributes.getString(0);
                    if (string != null) {
                        vPathRenderer2.mRootName = string;
                        vPathRenderer2.mVGTargetsMap.put(string, vPathRenderer2);
                    }
                    obtainAttributes.recycle();
                    vectorDrawableCompatState.mChangingConfigurations = getChangingConfigurations();
                    vectorDrawableCompatState.mCacheDirty = true;
                    VectorDrawableCompatState vectorDrawableCompatState3 = this.mVectorState;
                    VPathRenderer vPathRenderer3 = vectorDrawableCompatState3.mVPathRenderer;
                    ArrayDeque arrayDeque = new ArrayDeque();
                    arrayDeque.push(vPathRenderer3.mRootGroup);
                    int eventType = xmlPullParser.getEventType();
                    int depth = xmlPullParser.getDepth() + 1;
                    boolean z4 = true;
                    for (int i5 = 1; eventType != i5 && (xmlPullParser.getDepth() >= depth || eventType != i3); i5 = 1) {
                        if (eventType == 2) {
                            String name = xmlPullParser.getName();
                            VGroup vGroup = (VGroup) arrayDeque.peek();
                            boolean equals = "path".equals(name);
                            i = depth;
                            ArrayMap arrayMap = vPathRenderer3.mVGTargetsMap;
                            if (equals) {
                                VFullPath vFullPath = new VFullPath();
                                TypedArray obtainAttributes2 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
                                if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                                    String string2 = obtainAttributes2.getString(0);
                                    if (string2 != null) {
                                        vFullPath.mPathName = string2;
                                    }
                                    String string3 = obtainAttributes2.getString(2);
                                    if (string3 != null) {
                                        vFullPath.mNodes = PathParser.createNodesFromPathData(string3);
                                    }
                                    vFullPath.mFillColor = TypedArrayUtils.getNamedComplexColor(obtainAttributes2, xmlPullParser, theme, "fillColor", 1);
                                    vPathRenderer = vPathRenderer3;
                                    vFullPath.mFillAlpha = TypedArrayUtils.getNamedFloat(obtainAttributes2, xmlPullParser, "fillAlpha", 12, vFullPath.mFillAlpha);
                                    int namedInt2 = TypedArrayUtils.getNamedInt(obtainAttributes2, xmlPullParser, "strokeLineCap", 8, -1);
                                    Paint.Cap cap = vFullPath.mStrokeLineCap;
                                    if (namedInt2 == 0) {
                                        cap = Paint.Cap.BUTT;
                                    } else if (namedInt2 == 1) {
                                        cap = Paint.Cap.ROUND;
                                    } else if (namedInt2 == 2) {
                                        cap = Paint.Cap.SQUARE;
                                    }
                                    vFullPath.mStrokeLineCap = cap;
                                    int namedInt3 = TypedArrayUtils.getNamedInt(obtainAttributes2, xmlPullParser, "strokeLineJoin", 9, -1);
                                    Paint.Join join = vFullPath.mStrokeLineJoin;
                                    if (namedInt3 == 0) {
                                        join = Paint.Join.MITER;
                                    } else if (namedInt3 == 1) {
                                        join = Paint.Join.ROUND;
                                    } else if (namedInt3 == 2) {
                                        join = Paint.Join.BEVEL;
                                    }
                                    vFullPath.mStrokeLineJoin = join;
                                    vFullPath.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(obtainAttributes2, xmlPullParser, "strokeMiterLimit", 10, vFullPath.mStrokeMiterlimit);
                                    vFullPath.mStrokeColor = TypedArrayUtils.getNamedComplexColor(obtainAttributes2, xmlPullParser, theme, "strokeColor", 3);
                                    vFullPath.mStrokeAlpha = TypedArrayUtils.getNamedFloat(obtainAttributes2, xmlPullParser, "strokeAlpha", 11, vFullPath.mStrokeAlpha);
                                    vFullPath.mStrokeWidth = TypedArrayUtils.getNamedFloat(obtainAttributes2, xmlPullParser, "strokeWidth", 4, vFullPath.mStrokeWidth);
                                    vFullPath.mTrimPathEnd = TypedArrayUtils.getNamedFloat(obtainAttributes2, xmlPullParser, "trimPathEnd", 6, vFullPath.mTrimPathEnd);
                                    vFullPath.mTrimPathOffset = TypedArrayUtils.getNamedFloat(obtainAttributes2, xmlPullParser, "trimPathOffset", 7, vFullPath.mTrimPathOffset);
                                    vFullPath.mTrimPathStart = TypedArrayUtils.getNamedFloat(obtainAttributes2, xmlPullParser, "trimPathStart", 5, vFullPath.mTrimPathStart);
                                    vFullPath.mFillRule = TypedArrayUtils.getNamedInt(obtainAttributes2, xmlPullParser, "fillType", 13, vFullPath.mFillRule);
                                } else {
                                    vPathRenderer = vPathRenderer3;
                                }
                                obtainAttributes2.recycle();
                                vGroup.mChildren.add(vFullPath);
                                if (vFullPath.getPathName() != null) {
                                    arrayMap.put(vFullPath.getPathName(), vFullPath);
                                }
                                vectorDrawableCompatState3.mChangingConfigurations |= vFullPath.mChangingConfigurations;
                                z = false;
                                c = 5;
                                z4 = false;
                            } else {
                                vPathRenderer = vPathRenderer3;
                                if ("clip-path".equals(name)) {
                                    VClipPath vClipPath = new VClipPath();
                                    if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                                        TypedArray obtainAttributes3 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
                                        String string4 = obtainAttributes3.getString(0);
                                        if (string4 != null) {
                                            vClipPath.mPathName = string4;
                                        }
                                        String string5 = obtainAttributes3.getString(1);
                                        if (string5 != null) {
                                            vClipPath.mNodes = PathParser.createNodesFromPathData(string5);
                                        }
                                        vClipPath.mFillRule = TypedArrayUtils.getNamedInt(obtainAttributes3, xmlPullParser, "fillType", 2, 0);
                                        obtainAttributes3.recycle();
                                    }
                                    vGroup.mChildren.add(vClipPath);
                                    if (vClipPath.getPathName() != null) {
                                        arrayMap.put(vClipPath.getPathName(), vClipPath);
                                    }
                                    vectorDrawableCompatState3.mChangingConfigurations = vClipPath.mChangingConfigurations | vectorDrawableCompatState3.mChangingConfigurations;
                                } else if ("group".equals(name)) {
                                    VGroup vGroup2 = new VGroup();
                                    TypedArray obtainAttributes4 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
                                    c = 5;
                                    vGroup2.mRotate = TypedArrayUtils.getNamedFloat(obtainAttributes4, xmlPullParser, "rotation", 5, vGroup2.mRotate);
                                    vGroup2.mPivotX = obtainAttributes4.getFloat(1, vGroup2.mPivotX);
                                    vGroup2.mPivotY = obtainAttributes4.getFloat(2, vGroup2.mPivotY);
                                    vGroup2.mScaleX = TypedArrayUtils.getNamedFloat(obtainAttributes4, xmlPullParser, "scaleX", 3, vGroup2.mScaleX);
                                    vGroup2.mScaleY = TypedArrayUtils.getNamedFloat(obtainAttributes4, xmlPullParser, "scaleY", 4, vGroup2.mScaleY);
                                    vGroup2.mTranslateX = TypedArrayUtils.getNamedFloat(obtainAttributes4, xmlPullParser, "translateX", 6, vGroup2.mTranslateX);
                                    vGroup2.mTranslateY = TypedArrayUtils.getNamedFloat(obtainAttributes4, xmlPullParser, "translateY", 7, vGroup2.mTranslateY);
                                    z = false;
                                    String string6 = obtainAttributes4.getString(0);
                                    if (string6 != null) {
                                        vGroup2.mGroupName = string6;
                                    }
                                    vGroup2.updateLocalMatrix();
                                    obtainAttributes4.recycle();
                                    vGroup.mChildren.add(vGroup2);
                                    arrayDeque.push(vGroup2);
                                    if (vGroup2.getGroupName() != null) {
                                        arrayMap.put(vGroup2.getGroupName(), vGroup2);
                                    }
                                    vectorDrawableCompatState3.mChangingConfigurations = vGroup2.mChangingConfigurations | vectorDrawableCompatState3.mChangingConfigurations;
                                }
                                z = false;
                                c = 5;
                            }
                            i2 = 3;
                        } else {
                            vPathRenderer = vPathRenderer3;
                            i = depth;
                            z = z2;
                            i2 = 3;
                            if (eventType == 3 && "group".equals(xmlPullParser.getName())) {
                                arrayDeque.pop();
                            }
                        }
                        eventType = xmlPullParser.next();
                        z2 = z;
                        i3 = i2;
                        depth = i;
                        vPathRenderer3 = vPathRenderer;
                    }
                    if (!z4) {
                        this.mTintFilter = updateTintFilter(vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
                        return;
                    }
                    throw new XmlPullParserException("no path defined");
                }
                throw new XmlPullParserException(obtainAttributes.getPositionDescription() + "<vector> tag requires height > 0");
            }
            throw new XmlPullParserException(obtainAttributes.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        colorStateList = null;
        if (colorStateList != null) {
        }
        boolean z32 = vectorDrawableCompatState2.mAutoMirrored;
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "autoMirrored")) {
        }
        vectorDrawableCompatState2.mAutoMirrored = z32;
        vPathRenderer2.mViewportWidth = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "viewportWidth", 7, vPathRenderer2.mViewportWidth);
        float namedFloat2 = TypedArrayUtils.getNamedFloat(obtainAttributes, xmlPullParser, "viewportHeight", 8, vPathRenderer2.mViewportHeight);
        vPathRenderer2.mViewportHeight = namedFloat2;
        if (vPathRenderer2.mViewportWidth > 0.0f) {
        }
    }

    public VectorDrawableCompat(VectorDrawableCompatState vectorDrawableCompatState) {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = vectorDrawableCompatState;
        this.mTintFilter = updateTintFilter(vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VFullPath extends VPath {
        public float mFillAlpha;
        public ComplexColorCompat mFillColor;
        public float mStrokeAlpha;
        public ComplexColorCompat mStrokeColor;
        public Paint.Cap mStrokeLineCap;
        public Paint.Join mStrokeLineJoin;
        public float mStrokeMiterlimit;
        public float mStrokeWidth;
        public float mTrimPathEnd;
        public float mTrimPathOffset;
        public float mTrimPathStart;

        public VFullPath() {
            this.mStrokeWidth = 0.0f;
            this.mStrokeAlpha = 1.0f;
            this.mFillAlpha = 1.0f;
            this.mTrimPathStart = 0.0f;
            this.mTrimPathEnd = 1.0f;
            this.mTrimPathOffset = 0.0f;
            this.mStrokeLineCap = Paint.Cap.BUTT;
            this.mStrokeLineJoin = Paint.Join.MITER;
            this.mStrokeMiterlimit = 4.0f;
        }

        public float getFillAlpha() {
            return this.mFillAlpha;
        }

        public int getFillColor() {
            return this.mFillColor.mColor;
        }

        public float getStrokeAlpha() {
            return this.mStrokeAlpha;
        }

        public int getStrokeColor() {
            return this.mStrokeColor.mColor;
        }

        public float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        public float getTrimPathEnd() {
            return this.mTrimPathEnd;
        }

        public float getTrimPathOffset() {
            return this.mTrimPathOffset;
        }

        public float getTrimPathStart() {
            return this.mTrimPathStart;
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public final boolean isStateful() {
            return this.mFillColor.isStateful() || this.mStrokeColor.isStateful();
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onStateChanged(int[] iArr) {
            boolean z;
            ComplexColorCompat complexColorCompat;
            ComplexColorCompat complexColorCompat2 = this.mFillColor;
            boolean z2 = true;
            if (complexColorCompat2.isStateful()) {
                ColorStateList colorStateList = complexColorCompat2.mColorStateList;
                int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
                if (colorForState != complexColorCompat2.mColor) {
                    complexColorCompat2.mColor = colorForState;
                    z = true;
                    complexColorCompat = this.mStrokeColor;
                    if (complexColorCompat.isStateful()) {
                        ColorStateList colorStateList2 = complexColorCompat.mColorStateList;
                        int colorForState2 = colorStateList2.getColorForState(iArr, colorStateList2.getDefaultColor());
                        if (colorForState2 != complexColorCompat.mColor) {
                            complexColorCompat.mColor = colorForState2;
                            return z2 | z;
                        }
                    }
                    z2 = false;
                    return z2 | z;
                }
            }
            z = false;
            complexColorCompat = this.mStrokeColor;
            if (complexColorCompat.isStateful()) {
            }
            z2 = false;
            return z2 | z;
        }

        public void setFillAlpha(float f) {
            this.mFillAlpha = f;
        }

        public void setFillColor(int i) {
            this.mFillColor.mColor = i;
        }

        public void setStrokeAlpha(float f) {
            this.mStrokeAlpha = f;
        }

        public void setStrokeColor(int i) {
            this.mStrokeColor.mColor = i;
        }

        public void setStrokeWidth(float f) {
            this.mStrokeWidth = f;
        }

        public void setTrimPathEnd(float f) {
            this.mTrimPathEnd = f;
        }

        public void setTrimPathOffset(float f) {
            this.mTrimPathOffset = f;
        }

        public void setTrimPathStart(float f) {
            this.mTrimPathStart = f;
        }

        public VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.mStrokeWidth = 0.0f;
            this.mStrokeAlpha = 1.0f;
            this.mFillAlpha = 1.0f;
            this.mTrimPathStart = 0.0f;
            this.mTrimPathEnd = 1.0f;
            this.mTrimPathOffset = 0.0f;
            this.mStrokeLineCap = Paint.Cap.BUTT;
            this.mStrokeLineJoin = Paint.Join.MITER;
            this.mStrokeMiterlimit = 4.0f;
            vFullPath.getClass();
            this.mStrokeColor = vFullPath.mStrokeColor;
            this.mStrokeWidth = vFullPath.mStrokeWidth;
            this.mStrokeAlpha = vFullPath.mStrokeAlpha;
            this.mFillColor = vFullPath.mFillColor;
            this.mFillRule = vFullPath.mFillRule;
            this.mFillAlpha = vFullPath.mFillAlpha;
            this.mTrimPathStart = vFullPath.mTrimPathStart;
            this.mTrimPathEnd = vFullPath.mTrimPathEnd;
            this.mTrimPathOffset = vFullPath.mTrimPathOffset;
            this.mStrokeLineCap = vFullPath.mStrokeLineCap;
            this.mStrokeLineJoin = vFullPath.mStrokeLineJoin;
            this.mStrokeMiterlimit = vFullPath.mStrokeMiterlimit;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VPathRenderer {
        public static final Matrix IDENTITY_MATRIX = new Matrix();
        public float mBaseHeight;
        public float mBaseWidth;
        public Paint mFillPaint;
        public final Matrix mFinalPathMatrix;
        public Boolean mIsStateful;
        public final Path mPath;
        public PathMeasure mPathMeasure;
        public final Path mRenderPath;
        public int mRootAlpha;
        public final VGroup mRootGroup;
        public String mRootName;
        public Paint mStrokePaint;
        public final ArrayMap mVGTargetsMap;
        public float mViewportHeight;
        public float mViewportWidth;

        public VPathRenderer() {
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mIsStateful = null;
            this.mVGTargetsMap = new ArrayMap();
            this.mRootGroup = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        public final void drawGroupTree(VGroup vGroup, Matrix matrix, Canvas canvas, int i, int i2) {
            int i3;
            float f;
            boolean z;
            vGroup.mStackedMatrix.set(matrix);
            Matrix matrix2 = vGroup.mLocalMatrix;
            Matrix matrix3 = vGroup.mStackedMatrix;
            matrix3.preConcat(matrix2);
            canvas.save();
            char c = 0;
            int i4 = 0;
            while (true) {
                ArrayList arrayList = vGroup.mChildren;
                if (i4 >= arrayList.size()) {
                    canvas.restore();
                    return;
                }
                VObject vObject = (VObject) arrayList.get(i4);
                if (vObject instanceof VGroup) {
                    drawGroupTree((VGroup) vObject, matrix3, canvas, i, i2);
                } else if (vObject instanceof VPath) {
                    VPath vPath = (VPath) vObject;
                    float f2 = i / this.mViewportWidth;
                    float f3 = i2 / this.mViewportHeight;
                    float min = Math.min(f2, f3);
                    Matrix matrix4 = this.mFinalPathMatrix;
                    matrix4.set(matrix3);
                    matrix4.postScale(f2, f3);
                    float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
                    matrix3.mapVectors(fArr);
                    float hypot = (float) Math.hypot(fArr[c], fArr[1]);
                    i3 = i4;
                    float hypot2 = (float) Math.hypot(fArr[2], fArr[3]);
                    float f4 = (fArr[0] * fArr[3]) - (fArr[1] * fArr[2]);
                    float max = Math.max(hypot, hypot2);
                    float abs = max > 0.0f ? Math.abs(f4) / max : 0.0f;
                    if (abs != 0.0f) {
                        vPath.getClass();
                        Path path = this.mPath;
                        path.reset();
                        PathParser.PathDataNode[] pathDataNodeArr = vPath.mNodes;
                        if (pathDataNodeArr != null) {
                            PathParser.PathDataNode.nodesToPath(pathDataNodeArr, path);
                        }
                        Path path2 = this.mRenderPath;
                        path2.reset();
                        if (vPath instanceof VClipPath) {
                            path2.setFillType(vPath.mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                            path2.addPath(path, matrix4);
                            canvas.clipPath(path2);
                        } else {
                            VFullPath vFullPath = (VFullPath) vPath;
                            float f5 = vFullPath.mTrimPathStart;
                            if (f5 != 0.0f || vFullPath.mTrimPathEnd != 1.0f) {
                                float f6 = vFullPath.mTrimPathOffset;
                                float f7 = (f5 + f6) % 1.0f;
                                float f8 = (vFullPath.mTrimPathEnd + f6) % 1.0f;
                                if (this.mPathMeasure == null) {
                                    this.mPathMeasure = new PathMeasure();
                                }
                                this.mPathMeasure.setPath(path, false);
                                float length = this.mPathMeasure.getLength();
                                float f9 = f7 * length;
                                float f10 = f8 * length;
                                path.reset();
                                if (f9 > f10) {
                                    this.mPathMeasure.getSegment(f9, length, path, true);
                                    f = 0.0f;
                                    this.mPathMeasure.getSegment(0.0f, f10, path, true);
                                } else {
                                    f = 0.0f;
                                    this.mPathMeasure.getSegment(f9, f10, path, true);
                                }
                                path.rLineTo(f, f);
                            }
                            path2.addPath(path, matrix4);
                            ComplexColorCompat complexColorCompat = vFullPath.mFillColor;
                            if ((complexColorCompat.mShader != null) || complexColorCompat.mColor != 0) {
                                if (this.mFillPaint == null) {
                                    Paint paint = new Paint(1);
                                    this.mFillPaint = paint;
                                    paint.setStyle(Paint.Style.FILL);
                                }
                                Paint paint2 = this.mFillPaint;
                                Shader shader = complexColorCompat.mShader;
                                if (shader != null) {
                                    shader.setLocalMatrix(matrix4);
                                    paint2.setShader(shader);
                                    paint2.setAlpha(Math.round(vFullPath.mFillAlpha * 255.0f));
                                } else {
                                    paint2.setShader(null);
                                    paint2.setAlpha(255);
                                    int i5 = complexColorCompat.mColor;
                                    float f11 = vFullPath.mFillAlpha;
                                    PorterDuff.Mode mode = VectorDrawableCompat.DEFAULT_TINT_MODE;
                                    paint2.setColor((i5 & 16777215) | (((int) (Color.alpha(i5) * f11)) << 24));
                                }
                                paint2.setColorFilter(null);
                                path2.setFillType(vFullPath.mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                canvas.drawPath(path2, paint2);
                            }
                            ComplexColorCompat complexColorCompat2 = vFullPath.mStrokeColor;
                            if ((complexColorCompat2.mShader != null) || complexColorCompat2.mColor != 0) {
                                if (this.mStrokePaint == null) {
                                    z = true;
                                    Paint paint3 = new Paint(1);
                                    this.mStrokePaint = paint3;
                                    paint3.setStyle(Paint.Style.STROKE);
                                } else {
                                    z = true;
                                }
                                Paint paint4 = this.mStrokePaint;
                                Paint.Join join = vFullPath.mStrokeLineJoin;
                                if (join != null) {
                                    paint4.setStrokeJoin(join);
                                }
                                Paint.Cap cap = vFullPath.mStrokeLineCap;
                                if (cap != null) {
                                    paint4.setStrokeCap(cap);
                                }
                                paint4.setStrokeMiter(vFullPath.mStrokeMiterlimit);
                                Shader shader2 = complexColorCompat2.mShader;
                                if (shader2 == null) {
                                    z = false;
                                }
                                if (z) {
                                    shader2.setLocalMatrix(matrix4);
                                    paint4.setShader(shader2);
                                    paint4.setAlpha(Math.round(vFullPath.mStrokeAlpha * 255.0f));
                                } else {
                                    paint4.setShader(null);
                                    paint4.setAlpha(255);
                                    int i6 = complexColorCompat2.mColor;
                                    float f12 = vFullPath.mStrokeAlpha;
                                    PorterDuff.Mode mode2 = VectorDrawableCompat.DEFAULT_TINT_MODE;
                                    paint4.setColor((i6 & 16777215) | (((int) (Color.alpha(i6) * f12)) << 24));
                                }
                                paint4.setColorFilter(null);
                                paint4.setStrokeWidth(vFullPath.mStrokeWidth * abs * min);
                                canvas.drawPath(path2, paint4);
                            }
                        }
                    }
                    i4 = i3 + 1;
                    c = 0;
                }
                i3 = i4;
                i4 = i3 + 1;
                c = 0;
            }
        }

        public float getAlpha() {
            return getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.mRootAlpha;
        }

        public void setAlpha(float f) {
            setRootAlpha((int) (f * 255.0f));
        }

        public void setRootAlpha(int i) {
            this.mRootAlpha = i;
        }

        public VPathRenderer(VPathRenderer vPathRenderer) {
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mIsStateful = null;
            ArrayMap arrayMap = new ArrayMap();
            this.mVGTargetsMap = arrayMap;
            this.mRootGroup = new VGroup(vPathRenderer.mRootGroup, arrayMap);
            this.mPath = new Path(vPathRenderer.mPath);
            this.mRenderPath = new Path(vPathRenderer.mRenderPath);
            this.mBaseWidth = vPathRenderer.mBaseWidth;
            this.mBaseHeight = vPathRenderer.mBaseHeight;
            this.mViewportWidth = vPathRenderer.mViewportWidth;
            this.mViewportHeight = vPathRenderer.mViewportHeight;
            this.mRootAlpha = vPathRenderer.mRootAlpha;
            this.mRootName = vPathRenderer.mRootName;
            String str = vPathRenderer.mRootName;
            if (str != null) {
                arrayMap.put(str, this);
            }
            this.mIsStateful = vPathRenderer.mIsStateful;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VGroup extends VObject {
        public final int mChangingConfigurations;
        public final ArrayList mChildren;
        public String mGroupName;
        public final Matrix mLocalMatrix;
        public float mPivotX;
        public float mPivotY;
        public float mRotate;
        public float mScaleX;
        public float mScaleY;
        public final Matrix mStackedMatrix;
        public float mTranslateX;
        public float mTranslateY;

        public VGroup(VGroup vGroup, ArrayMap arrayMap) {
            super();
            VPath vClipPath;
            this.mStackedMatrix = new Matrix();
            this.mChildren = new ArrayList();
            this.mRotate = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            Matrix matrix = new Matrix();
            this.mLocalMatrix = matrix;
            this.mGroupName = null;
            this.mRotate = vGroup.mRotate;
            this.mPivotX = vGroup.mPivotX;
            this.mPivotY = vGroup.mPivotY;
            this.mScaleX = vGroup.mScaleX;
            this.mScaleY = vGroup.mScaleY;
            this.mTranslateX = vGroup.mTranslateX;
            this.mTranslateY = vGroup.mTranslateY;
            String str = vGroup.mGroupName;
            this.mGroupName = str;
            this.mChangingConfigurations = vGroup.mChangingConfigurations;
            if (str != null) {
                arrayMap.put(str, this);
            }
            matrix.set(vGroup.mLocalMatrix);
            ArrayList arrayList = vGroup.mChildren;
            for (int i = 0; i < arrayList.size(); i++) {
                Object obj = arrayList.get(i);
                if (obj instanceof VGroup) {
                    this.mChildren.add(new VGroup((VGroup) obj, arrayMap));
                } else {
                    if (obj instanceof VFullPath) {
                        vClipPath = new VFullPath((VFullPath) obj);
                    } else {
                        if (!(obj instanceof VClipPath)) {
                            throw new IllegalStateException("Unknown object in the tree!");
                        }
                        vClipPath = new VClipPath((VClipPath) obj);
                    }
                    this.mChildren.add(vClipPath);
                    Object obj2 = vClipPath.mPathName;
                    if (obj2 != null) {
                        arrayMap.put(obj2, vClipPath);
                    }
                }
            }
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() {
            return this.mLocalMatrix;
        }

        public float getPivotX() {
            return this.mPivotX;
        }

        public float getPivotY() {
            return this.mPivotY;
        }

        public float getRotation() {
            return this.mRotate;
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public float getTranslateX() {
            return this.mTranslateX;
        }

        public float getTranslateY() {
            return this.mTranslateY;
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public final boolean isStateful() {
            int i = 0;
            while (true) {
                ArrayList arrayList = this.mChildren;
                if (i >= arrayList.size()) {
                    return false;
                }
                if (((VObject) arrayList.get(i)).isStateful()) {
                    return true;
                }
                i++;
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public final boolean onStateChanged(int[] iArr) {
            int i = 0;
            boolean z = false;
            while (true) {
                ArrayList arrayList = this.mChildren;
                if (i >= arrayList.size()) {
                    return z;
                }
                z |= ((VObject) arrayList.get(i)).onStateChanged(iArr);
                i++;
            }
        }

        public void setPivotX(float f) {
            if (f != this.mPivotX) {
                this.mPivotX = f;
                updateLocalMatrix();
            }
        }

        public void setPivotY(float f) {
            if (f != this.mPivotY) {
                this.mPivotY = f;
                updateLocalMatrix();
            }
        }

        public void setRotation(float f) {
            if (f != this.mRotate) {
                this.mRotate = f;
                updateLocalMatrix();
            }
        }

        public void setScaleX(float f) {
            if (f != this.mScaleX) {
                this.mScaleX = f;
                updateLocalMatrix();
            }
        }

        public void setScaleY(float f) {
            if (f != this.mScaleY) {
                this.mScaleY = f;
                updateLocalMatrix();
            }
        }

        public void setTranslateX(float f) {
            if (f != this.mTranslateX) {
                this.mTranslateX = f;
                updateLocalMatrix();
            }
        }

        public void setTranslateY(float f) {
            if (f != this.mTranslateY) {
                this.mTranslateY = f;
                updateLocalMatrix();
            }
        }

        public final void updateLocalMatrix() {
            Matrix matrix = this.mLocalMatrix;
            matrix.reset();
            matrix.postTranslate(-this.mPivotX, -this.mPivotY);
            matrix.postScale(this.mScaleX, this.mScaleY);
            matrix.postRotate(this.mRotate, 0.0f, 0.0f);
            matrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        public VGroup() {
            super();
            this.mStackedMatrix = new Matrix();
            this.mChildren = new ArrayList();
            this.mRotate = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            this.mLocalMatrix = new Matrix();
            this.mGroupName = null;
        }
    }
}
