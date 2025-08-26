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
import androidx.collection.ArrayMap;
import androidx.core.content.res.ComplexColorCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.core.graphics.drawable.DrawableCompat;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class VectorDrawableCompat extends VectorDrawableCommon {
    public static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    public boolean mAllowCaching;
    public ColorFilter mColorFilter;
    public boolean mMutated;
    public PorterDuffColorFilter mTintFilter;
    public final Rect mTmpBounds;
    public final float[] mTmpFloats;
    public final Matrix mTmpMatrix;
    public VectorDrawableCompatState mVectorState;

    public class VClipPath extends VPath {
        public VClipPath() {
        }

        public VClipPath(VClipPath vClipPath) {
            super(vClipPath);
        }
    }

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

    public class VectorDrawableCompatState extends Drawable.ConstantState {
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

    @Override // android.graphics.drawable.Drawable
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
        float fAbs = Math.abs(this.mTmpFloats[0]);
        float fAbs2 = Math.abs(this.mTmpFloats[4]);
        float fAbs3 = Math.abs(this.mTmpFloats[1]);
        float fAbs4 = Math.abs(this.mTmpFloats[3]);
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs = 1.0f;
            fAbs2 = 1.0f;
        }
        int iMin = Math.min(2048, (int) (this.mTmpBounds.width() * fAbs));
        int iMin2 = Math.min(2048, (int) (this.mTmpBounds.height() * fAbs2));
        if (iMin <= 0 || iMin2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        Rect rect = this.mTmpBounds;
        canvas.translate(rect.left, rect.top);
        if (isAutoMirrored() && getLayoutDirection() == 1) {
            canvas.translate(this.mTmpBounds.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.mTmpBounds.offsetTo(0, 0);
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        Bitmap bitmap = vectorDrawableCompatState.mCachedBitmap;
        if (bitmap == null || iMin != bitmap.getWidth() || iMin2 != vectorDrawableCompatState.mCachedBitmap.getHeight()) {
            vectorDrawableCompatState.mCachedBitmap = Bitmap.createBitmap(iMin, iMin2, Bitmap.Config.ARGB_8888);
            vectorDrawableCompatState.mCacheDirty = true;
        }
        if (this.mAllowCaching) {
            VectorDrawableCompatState vectorDrawableCompatState2 = this.mVectorState;
            if (vectorDrawableCompatState2.mCacheDirty || vectorDrawableCompatState2.mCachedTint != vectorDrawableCompatState2.mTint || vectorDrawableCompatState2.mCachedTintMode != vectorDrawableCompatState2.mTintMode || vectorDrawableCompatState2.mCachedAutoMirrored != vectorDrawableCompatState2.mAutoMirrored || vectorDrawableCompatState2.mCachedRootAlpha != vectorDrawableCompatState2.mVPathRenderer.getRootAlpha()) {
                VectorDrawableCompatState vectorDrawableCompatState3 = this.mVectorState;
                vectorDrawableCompatState3.mCachedBitmap.eraseColor(0);
                Canvas canvas2 = new Canvas(vectorDrawableCompatState3.mCachedBitmap);
                VPathRenderer vPathRenderer = vectorDrawableCompatState3.mVPathRenderer;
                vPathRenderer.drawGroupTree(vPathRenderer.mRootGroup, VPathRenderer.IDENTITY_MATRIX, canvas2, iMin, iMin2);
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
            vPathRenderer2.drawGroupTree(vPathRenderer2.mRootGroup, VPathRenderer.IDENTITY_MATRIX, canvas3, iMin, iMin2);
        }
        VectorDrawableCompatState vectorDrawableCompatState6 = this.mVectorState;
        Rect rect2 = this.mTmpBounds;
        if (vectorDrawableCompatState6.mVPathRenderer.getRootAlpha() >= 255 && colorFilter == null) {
            paint = null;
        } else {
            if (vectorDrawableCompatState6.mTempPaint == null) {
                Paint paint2 = new Paint();
                vectorDrawableCompatState6.mTempPaint = paint2;
                paint2.setFilterBitmap(true);
            }
            vectorDrawableCompatState6.mTempPaint.setAlpha(vectorDrawableCompatState6.mVPathRenderer.getRootAlpha());
            vectorDrawableCompatState6.mTempPaint.setColorFilter(colorFilter);
            paint = vectorDrawableCompatState6.mTempPaint;
        }
        canvas.drawBitmap(vectorDrawableCompatState6.mCachedBitmap, (Rect) null, rect2, paint);
        canvas.restoreToCount(iSave);
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
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
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
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.isStateful();
        }
        if (super.isStateful()) {
            return true;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState == null) {
            return false;
        }
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
        if (vPathRenderer.mIsStateful == null) {
            vPathRenderer.mIsStateful = Boolean.valueOf(vPathRenderer.mRootGroup.isStateful());
        }
        if (vPathRenderer.mIsStateful.booleanValue()) {
            return true;
        }
        ColorStateList colorStateList = this.mVectorState.mTint;
        return colorStateList != null && colorStateList.isStateful();
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
            boolean zOnStateChanged = vectorDrawableCompatState.mVPathRenderer.mRootGroup.onStateChanged(iArr);
            vectorDrawableCompatState.mCacheDirty |= zOnStateChanged;
            if (zOnStateChanged) {
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
            DrawableCompat.setTint(drawable, i);
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

    public class VectorDrawableDelegateState extends Drawable.ConstantState {
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

    public abstract class VPath extends VObject {
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
            PathParser.PathDataNode[] pathDataNodeArr2 = this.mNodes;
            boolean z = false;
            if (pathDataNodeArr2 != null && pathDataNodeArr != null && pathDataNodeArr2.length == pathDataNodeArr.length) {
                int i = 0;
                while (true) {
                    if (i >= pathDataNodeArr2.length) {
                        z = true;
                        break;
                    }
                    PathParser.PathDataNode pathDataNode = pathDataNodeArr2[i];
                    char c = pathDataNode.mType;
                    PathParser.PathDataNode pathDataNode2 = pathDataNodeArr[i];
                    if (c != pathDataNode2.mType || pathDataNode.mParams.length != pathDataNode2.mParams.length) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            if (!z) {
                this.mNodes = PathParser.deepCopyNodes(pathDataNodeArr);
                return;
            }
            PathParser.PathDataNode[] pathDataNodeArr3 = this.mNodes;
            for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
                pathDataNodeArr3[i2].mType = pathDataNodeArr[i2].mType;
                int i3 = 0;
                while (true) {
                    float[] fArr = pathDataNodeArr[i2].mParams;
                    if (i3 < fArr.length) {
                        pathDataNodeArr3[i2].mParams[i3] = fArr[i3];
                        i3++;
                    }
                }
            }
        }

        public VPath(VPath vPath) {
            super();
            this.mNodes = null;
            this.mFillRule = 0;
            this.mPathName = vPath.mPathName;
            this.mNodes = PathParser.deepCopyNodes(vPath.mNodes);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VPathRenderer vPathRenderer;
        int i;
        boolean z;
        int i2;
        int i3;
        boolean z2;
        Paint.Cap cap;
        Paint.Join join;
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet, theme);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        vectorDrawableCompatState.mVPathRenderer = new VPathRenderer();
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY);
        VectorDrawableCompatState vectorDrawableCompatState2 = this.mVectorState;
        VPathRenderer vPathRenderer2 = vectorDrawableCompatState2.mVPathRenderer;
        int namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "tintMode", 6, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        int i4 = 3;
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
        int i5 = 1;
        ColorStateList namedColorStateList = TypedArrayUtils.getNamedColorStateList(typedArrayObtainAttributes, xmlPullParser, theme, 1);
        if (namedColorStateList != null) {
            vectorDrawableCompatState2.mTint = namedColorStateList;
        }
        boolean z3 = vectorDrawableCompatState2.mAutoMirrored;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "autoMirrored") != null) {
            z3 = typedArrayObtainAttributes.getBoolean(5, z3);
        }
        vectorDrawableCompatState2.mAutoMirrored = z3;
        float f = vPathRenderer2.mViewportWidth;
        boolean z4 = false;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportWidth") != null) {
            f = typedArrayObtainAttributes.getFloat(7, f);
        }
        vPathRenderer2.mViewportWidth = f;
        float f2 = vPathRenderer2.mViewportHeight;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportHeight") != null) {
            f2 = typedArrayObtainAttributes.getFloat(8, f2);
        }
        vPathRenderer2.mViewportHeight = f2;
        if (vPathRenderer2.mViewportWidth <= 0.0f) {
            throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (f2 > 0.0f) {
            vPathRenderer2.mBaseWidth = typedArrayObtainAttributes.getDimension(3, vPathRenderer2.mBaseWidth);
            int i6 = 2;
            float dimension = typedArrayObtainAttributes.getDimension(2, vPathRenderer2.mBaseHeight);
            vPathRenderer2.mBaseHeight = dimension;
            if (vPathRenderer2.mBaseWidth <= 0.0f) {
                throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires width > 0");
            }
            if (dimension > 0.0f) {
                float alpha = vPathRenderer2.getAlpha();
                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "alpha") != null) {
                    alpha = typedArrayObtainAttributes.getFloat(4, alpha);
                }
                vPathRenderer2.setAlpha(alpha);
                String string = typedArrayObtainAttributes.getString(0);
                if (string != null) {
                    vPathRenderer2.mRootName = string;
                    vPathRenderer2.mVGTargetsMap.put(string, vPathRenderer2);
                }
                typedArrayObtainAttributes.recycle();
                vectorDrawableCompatState.mChangingConfigurations = getChangingConfigurations();
                vectorDrawableCompatState.mCacheDirty = true;
                VectorDrawableCompatState vectorDrawableCompatState3 = this.mVectorState;
                VPathRenderer vPathRenderer3 = vectorDrawableCompatState3.mVPathRenderer;
                ArrayDeque arrayDeque = new ArrayDeque();
                arrayDeque.push(vPathRenderer3.mRootGroup);
                int eventType = xmlPullParser.getEventType();
                int depth = xmlPullParser.getDepth() + 1;
                boolean z5 = true;
                while (eventType != i5 && (xmlPullParser.getDepth() >= depth || eventType != i4)) {
                    if (eventType == i6) {
                        String name = xmlPullParser.getName();
                        VGroup vGroup = (VGroup) arrayDeque.peek();
                        if (vGroup != null) {
                            boolean zEquals = "path".equals(name);
                            i2 = depth;
                            ArrayMap arrayMap = vPathRenderer3.mVGTargetsMap;
                            if (zEquals) {
                                VFullPath vFullPath = new VFullPath();
                                TypedArray typedArrayObtainAttributes2 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "pathData") != null) {
                                    vPathRenderer = vPathRenderer3;
                                    String string2 = typedArrayObtainAttributes2.getString(0);
                                    if (string2 != null) {
                                        vFullPath.mPathName = string2;
                                    }
                                    String string3 = typedArrayObtainAttributes2.getString(2);
                                    if (string3 != null) {
                                        vFullPath.mNodes = PathParser.createNodesFromPathData(string3);
                                    }
                                    vFullPath.mFillColor = TypedArrayUtils.getNamedComplexColor(typedArrayObtainAttributes2, xmlPullParser, theme, "fillColor", 1);
                                    float f3 = vFullPath.mFillAlpha;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "fillAlpha") != null) {
                                        f3 = typedArrayObtainAttributes2.getFloat(12, f3);
                                    }
                                    vFullPath.mFillAlpha = f3;
                                    int i7 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeLineCap") != null ? typedArrayObtainAttributes2.getInt(8, -1) : -1;
                                    Paint.Cap cap2 = vFullPath.mStrokeLineCap;
                                    if (i7 == 0) {
                                        cap = Paint.Cap.BUTT;
                                    } else if (i7 != 1) {
                                        cap = i7 != 2 ? cap2 : Paint.Cap.SQUARE;
                                    } else {
                                        cap = Paint.Cap.ROUND;
                                    }
                                    vFullPath.mStrokeLineCap = cap;
                                    int i8 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeLineJoin") != null ? typedArrayObtainAttributes2.getInt(9, -1) : -1;
                                    Paint.Join join2 = vFullPath.mStrokeLineJoin;
                                    if (i8 == 0) {
                                        join = Paint.Join.MITER;
                                    } else if (i8 != 1) {
                                        join = i8 != 2 ? join2 : Paint.Join.BEVEL;
                                    } else {
                                        join = Paint.Join.ROUND;
                                    }
                                    vFullPath.mStrokeLineJoin = join;
                                    float f4 = vFullPath.mStrokeMiterlimit;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeMiterLimit") != null) {
                                        f4 = typedArrayObtainAttributes2.getFloat(10, f4);
                                    }
                                    vFullPath.mStrokeMiterlimit = f4;
                                    vFullPath.mStrokeColor = TypedArrayUtils.getNamedComplexColor(typedArrayObtainAttributes2, xmlPullParser, theme, "strokeColor", 3);
                                    float f5 = vFullPath.mStrokeAlpha;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeAlpha") != null) {
                                        f5 = typedArrayObtainAttributes2.getFloat(11, f5);
                                    }
                                    vFullPath.mStrokeAlpha = f5;
                                    float f6 = vFullPath.mStrokeWidth;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeWidth") != null) {
                                        f6 = typedArrayObtainAttributes2.getFloat(4, f6);
                                    }
                                    vFullPath.mStrokeWidth = f6;
                                    float f7 = vFullPath.mTrimPathEnd;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathEnd") != null) {
                                        f7 = typedArrayObtainAttributes2.getFloat(6, f7);
                                    }
                                    vFullPath.mTrimPathEnd = f7;
                                    float f8 = vFullPath.mTrimPathOffset;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathOffset") != null) {
                                        f8 = typedArrayObtainAttributes2.getFloat(7, f8);
                                    }
                                    vFullPath.mTrimPathOffset = f8;
                                    float f9 = vFullPath.mTrimPathStart;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathStart") != null) {
                                        f9 = typedArrayObtainAttributes2.getFloat(5, f9);
                                    }
                                    vFullPath.mTrimPathStart = f9;
                                    int i9 = vFullPath.mFillRule;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "fillType") != null) {
                                        i9 = typedArrayObtainAttributes2.getInt(13, i9);
                                    }
                                    vFullPath.mFillRule = i9;
                                } else {
                                    vPathRenderer = vPathRenderer3;
                                }
                                typedArrayObtainAttributes2.recycle();
                                vGroup.mChildren.add(vFullPath);
                                if (vFullPath.getPathName() != null) {
                                    arrayMap.put(vFullPath.getPathName(), vFullPath);
                                }
                                vectorDrawableCompatState3.mChangingConfigurations = vectorDrawableCompatState3.mChangingConfigurations;
                                z2 = false;
                                i3 = 1;
                                z5 = false;
                            } else {
                                vPathRenderer = vPathRenderer3;
                                if ("clip-path".equals(name)) {
                                    VClipPath vClipPath = new VClipPath();
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "pathData") != null) {
                                        TypedArray typedArrayObtainAttributes3 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
                                        String string4 = typedArrayObtainAttributes3.getString(0);
                                        if (string4 != null) {
                                            vClipPath.mPathName = string4;
                                        }
                                        String string5 = typedArrayObtainAttributes3.getString(1);
                                        if (string5 != null) {
                                            vClipPath.mNodes = PathParser.createNodesFromPathData(string5);
                                        }
                                        vClipPath.mFillRule = !TypedArrayUtils.hasAttribute(xmlPullParser, "fillType") ? 0 : typedArrayObtainAttributes3.getInt(2, 0);
                                        typedArrayObtainAttributes3.recycle();
                                    }
                                    vGroup.mChildren.add(vClipPath);
                                    if (vClipPath.getPathName() != null) {
                                        arrayMap.put(vClipPath.getPathName(), vClipPath);
                                    }
                                    vectorDrawableCompatState3.mChangingConfigurations = vectorDrawableCompatState3.mChangingConfigurations;
                                } else if ("group".equals(name)) {
                                    VGroup vGroup2 = new VGroup();
                                    TypedArray typedArrayObtainAttributes4 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
                                    float f10 = vGroup2.mRotate;
                                    if (TypedArrayUtils.hasAttribute(xmlPullParser, "rotation")) {
                                        f10 = typedArrayObtainAttributes4.getFloat(5, f10);
                                    }
                                    vGroup2.mRotate = f10;
                                    i3 = 1;
                                    vGroup2.mPivotX = typedArrayObtainAttributes4.getFloat(1, vGroup2.mPivotX);
                                    vGroup2.mPivotY = typedArrayObtainAttributes4.getFloat(2, vGroup2.mPivotY);
                                    float f11 = vGroup2.mScaleX;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "scaleX") != null) {
                                        f11 = typedArrayObtainAttributes4.getFloat(3, f11);
                                    }
                                    vGroup2.mScaleX = f11;
                                    float f12 = vGroup2.mScaleY;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "scaleY") != null) {
                                        f12 = typedArrayObtainAttributes4.getFloat(4, f12);
                                    }
                                    vGroup2.mScaleY = f12;
                                    float f13 = vGroup2.mTranslateX;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "translateX") != null) {
                                        f13 = typedArrayObtainAttributes4.getFloat(6, f13);
                                    }
                                    vGroup2.mTranslateX = f13;
                                    float f14 = vGroup2.mTranslateY;
                                    if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "translateY") != null) {
                                        f14 = typedArrayObtainAttributes4.getFloat(7, f14);
                                    }
                                    vGroup2.mTranslateY = f14;
                                    z2 = false;
                                    String string6 = typedArrayObtainAttributes4.getString(0);
                                    if (string6 != null) {
                                        vGroup2.mGroupName = string6;
                                    }
                                    vGroup2.updateLocalMatrix();
                                    typedArrayObtainAttributes4.recycle();
                                    vGroup.mChildren.add(vGroup2);
                                    arrayDeque.push(vGroup2);
                                    if (vGroup2.getGroupName() != null) {
                                        arrayMap.put(vGroup2.getGroupName(), vGroup2);
                                    }
                                    vectorDrawableCompatState3.mChangingConfigurations = vectorDrawableCompatState3.mChangingConfigurations;
                                }
                                z2 = false;
                                i3 = 1;
                            }
                        } else {
                            vPathRenderer = vPathRenderer3;
                            z2 = z4;
                            i2 = depth;
                            i3 = 1;
                        }
                        z = z2;
                        i = 3;
                    } else {
                        vPathRenderer = vPathRenderer3;
                        i = i4;
                        z = z4;
                        i2 = depth;
                        i3 = 1;
                        if (eventType == i && "group".equals(xmlPullParser.getName())) {
                            arrayDeque.pop();
                        }
                    }
                    eventType = xmlPullParser.next();
                    i4 = i;
                    i5 = i3;
                    z4 = z;
                    vPathRenderer3 = vPathRenderer;
                    depth = i2;
                    i6 = 2;
                }
                if (!z5) {
                    this.mTintFilter = updateTintFilter(vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
                    return;
                }
                throw new XmlPullParserException("no path defined");
            }
            throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires height > 0");
        }
        throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
    }

    public VectorDrawableCompat(VectorDrawableCompatState vectorDrawableCompatState) {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = vectorDrawableCompatState;
        this.mTintFilter = updateTintFilter(vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
    }

    public class VFullPath extends VPath {
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

        /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onStateChanged(int[] iArr) {
            boolean z;
            ComplexColorCompat complexColorCompat = this.mFillColor;
            boolean z2 = false;
            if (complexColorCompat.isStateful()) {
                ColorStateList colorStateList = complexColorCompat.mColorStateList;
                int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
                if (colorForState != complexColorCompat.mColor) {
                    complexColorCompat.mColor = colorForState;
                    z = true;
                } else {
                    z = false;
                }
            }
            ComplexColorCompat complexColorCompat2 = this.mStrokeColor;
            if (complexColorCompat2.isStateful()) {
                ColorStateList colorStateList2 = complexColorCompat2.mColorStateList;
                int colorForState2 = colorStateList2.getColorForState(iArr, colorStateList2.getDefaultColor());
                if (colorForState2 != complexColorCompat2.mColor) {
                    complexColorCompat2.mColor = colorForState2;
                    z2 = true;
                }
            }
            return z | z2;
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

    public class VPathRenderer {
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

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v0 */
        /* JADX WARN: Type inference failed for: r8v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r8v9 */
        public final void drawGroupTree(VGroup vGroup, Matrix matrix, Canvas canvas, int i, int i2) {
            int i3;
            vGroup.mStackedMatrix.set(matrix);
            vGroup.mStackedMatrix.preConcat(vGroup.mLocalMatrix);
            canvas.save();
            ?? r8 = 0;
            int i4 = 0;
            while (i4 < vGroup.mChildren.size()) {
                VObject vObject = (VObject) vGroup.mChildren.get(i4);
                if (vObject instanceof VGroup) {
                    drawGroupTree((VGroup) vObject, vGroup.mStackedMatrix, canvas, i, i2);
                } else if (vObject instanceof VPath) {
                    VPath vPath = (VPath) vObject;
                    float f = i / this.mViewportWidth;
                    float f2 = i2 / this.mViewportHeight;
                    float fMin = Math.min(f, f2);
                    Matrix matrix2 = vGroup.mStackedMatrix;
                    this.mFinalPathMatrix.set(matrix2);
                    this.mFinalPathMatrix.postScale(f, f2);
                    float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
                    matrix2.mapVectors(fArr);
                    float fHypot = (float) Math.hypot(fArr[r8], fArr[1]);
                    float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
                    float f3 = (fArr[r8] * fArr[3]) - (fArr[1] * fArr[2]);
                    float fMax = Math.max(fHypot, fHypot2);
                    float fAbs = fMax > 0.0f ? Math.abs(f3) / fMax : 0.0f;
                    if (fAbs != 0.0f) {
                        Path path = this.mPath;
                        vPath.getClass();
                        path.reset();
                        PathParser.PathDataNode[] pathDataNodeArr = vPath.mNodes;
                        if (pathDataNodeArr != null) {
                            PathParser.nodesToPath(pathDataNodeArr, path);
                        }
                        Path path2 = this.mPath;
                        this.mRenderPath.reset();
                        if (vPath instanceof VClipPath) {
                            this.mRenderPath.setFillType(vPath.mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                            this.mRenderPath.addPath(path2, this.mFinalPathMatrix);
                            canvas.clipPath(this.mRenderPath);
                        } else {
                            VFullPath vFullPath = (VFullPath) vPath;
                            float f4 = vFullPath.mTrimPathStart;
                            if (f4 != 0.0f || vFullPath.mTrimPathEnd != 1.0f) {
                                float f5 = vFullPath.mTrimPathOffset;
                                float f6 = (f4 + f5) % 1.0f;
                                float f7 = (vFullPath.mTrimPathEnd + f5) % 1.0f;
                                if (this.mPathMeasure == null) {
                                    this.mPathMeasure = new PathMeasure();
                                }
                                this.mPathMeasure.setPath(this.mPath, r8);
                                float length = this.mPathMeasure.getLength();
                                float f8 = f6 * length;
                                float f9 = f7 * length;
                                path2.reset();
                                if (f8 > f9) {
                                    this.mPathMeasure.getSegment(f8, length, path2, true);
                                    this.mPathMeasure.getSegment(0.0f, f9, path2, true);
                                } else {
                                    this.mPathMeasure.getSegment(f8, f9, path2, true);
                                }
                                path2.rLineTo(0.0f, 0.0f);
                            }
                            this.mRenderPath.addPath(path2, this.mFinalPathMatrix);
                            ComplexColorCompat complexColorCompat = vFullPath.mFillColor;
                            if ((complexColorCompat.mShader == null && complexColorCompat.mColor == 0) ? r8 : true) {
                                if (this.mFillPaint == null) {
                                    Paint paint = new Paint(1);
                                    this.mFillPaint = paint;
                                    paint.setStyle(Paint.Style.FILL);
                                }
                                Paint paint2 = this.mFillPaint;
                                Shader shader = complexColorCompat.mShader;
                                if (shader != null) {
                                    shader.setLocalMatrix(this.mFinalPathMatrix);
                                    paint2.setShader(shader);
                                    paint2.setAlpha(Math.round(vFullPath.mFillAlpha * 255.0f));
                                    i3 = 16777215;
                                } else {
                                    paint2.setShader(null);
                                    paint2.setAlpha(255);
                                    int i5 = complexColorCompat.mColor;
                                    float f10 = vFullPath.mFillAlpha;
                                    PorterDuff.Mode mode = VectorDrawableCompat.DEFAULT_TINT_MODE;
                                    i3 = 16777215;
                                    paint2.setColor((i5 & 16777215) | (((int) (Color.alpha(i5) * f10)) << 24));
                                }
                                paint2.setColorFilter(null);
                                this.mRenderPath.setFillType(vFullPath.mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                canvas.drawPath(this.mRenderPath, paint2);
                            } else {
                                i3 = 16777215;
                            }
                            ComplexColorCompat complexColorCompat2 = vFullPath.mStrokeColor;
                            if (complexColorCompat2.mShader != null || complexColorCompat2.mColor != 0) {
                                if (this.mStrokePaint == null) {
                                    Paint paint3 = new Paint(1);
                                    this.mStrokePaint = paint3;
                                    paint3.setStyle(Paint.Style.STROKE);
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
                                if (shader2 != null) {
                                    shader2.setLocalMatrix(this.mFinalPathMatrix);
                                    paint4.setShader(shader2);
                                    paint4.setAlpha(Math.round(vFullPath.mStrokeAlpha * 255.0f));
                                } else {
                                    paint4.setShader(null);
                                    paint4.setAlpha(255);
                                    int i6 = complexColorCompat2.mColor;
                                    float f11 = vFullPath.mStrokeAlpha;
                                    PorterDuff.Mode mode2 = VectorDrawableCompat.DEFAULT_TINT_MODE;
                                    paint4.setColor((i6 & i3) | (((int) (Color.alpha(i6) * f11)) << 24));
                                }
                                paint4.setColorFilter(null);
                                paint4.setStrokeWidth(vFullPath.mStrokeWidth * fAbs * fMin);
                                canvas.drawPath(this.mRenderPath, paint4);
                            }
                        }
                    }
                }
                i4++;
                r8 = 0;
            }
            canvas.restore();
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

    public class VGroup extends VObject {
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
            VPath vClipPath;
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
            for (int i = 0; i < this.mChildren.size(); i++) {
                if (((VObject) this.mChildren.get(i)).isStateful()) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public final boolean onStateChanged(int[] iArr) {
            boolean zOnStateChanged = false;
            for (int i = 0; i < this.mChildren.size(); i++) {
                zOnStateChanged |= ((VObject) this.mChildren.get(i)).onStateChanged(iArr);
            }
            return zOnStateChanged;
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
            this.mLocalMatrix.reset();
            this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY);
            this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
            this.mLocalMatrix.postRotate(this.mRotate, 0.0f, 0.0f);
            this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
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
