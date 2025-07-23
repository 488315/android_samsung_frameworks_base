package android.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.TtmlUtils;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.io.IOException;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ImageView extends View {
    private static final String LOG_TAG = "ImageView";
    private static boolean sCompatAdjustViewBounds;
    private static boolean sCompatDone;
    private static boolean sCompatDrawableVisibilityDispatch;
    private static boolean sCompatUseCorrectStreamDensity;
    private boolean mAdjustViewBounds;
    private int mAlpha;
    private int mBaseline;
    private boolean mBaselineAlignBottom;
    private ColorFilter mColorFilter;
    private boolean mCropToPadding;
    private Matrix mDrawMatrix;
    private Drawable mDrawable;
    private BlendMode mDrawableBlendMode;
    private int mDrawableHeight;
    private ColorStateList mDrawableTintList;
    private int mDrawableWidth;
    private boolean mHasAlpha;
    private boolean mHasColorFilter;
    private boolean mHasDrawableBlendMode;
    private boolean mHasDrawableTint;
    private boolean mHasLevelSet;
    private boolean mHasXfermode;
    private boolean mHaveFrame;
    private int mLevel;
    private Matrix mMatrix;
    private int mMaxHeight;
    private int mMaxWidth;
    private boolean mMergeState;
    private BitmapDrawable mRecycleableBitmapDrawable;
    private int mResource;
    private ScaleType mScaleType;
    private int[] mState;
    private final RectF mTempDst;
    private final RectF mTempSrc;
    private Uri mUri;
    private final int mViewAlphaScale;
    private Xfermode mXfermode;
    private static final ScaleType[] sScaleTypeArray = {ScaleType.MATRIX, ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER, ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE};
    private static final Matrix.ScaleToFit[] sS2FArray = {Matrix.ScaleToFit.FILL, Matrix.ScaleToFit.START, Matrix.ScaleToFit.CENTER, Matrix.ScaleToFit.END};

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ImageView> {
        private int mAdjustViewBoundsId;
        private int mBaselineAlignBottomId;
        private int mBaselineId;
        private int mBlendModeId;
        private int mCropToPaddingId;
        private int mMaxHeightId;
        private int mMaxWidthId;
        private boolean mPropertiesMapped = false;
        private int mScaleTypeId;
        private int mSrcId;
        private int mTintId;
        private int mTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mAdjustViewBoundsId = propertyMapper.mapBoolean("adjustViewBounds", 16843038);
            this.mBaselineId = propertyMapper.mapInt("baseline", 16843548);
            this.mBaselineAlignBottomId = propertyMapper.mapBoolean("baselineAlignBottom", 16843042);
            this.mBlendModeId = propertyMapper.mapObject("blendMode", 9);
            this.mCropToPaddingId = propertyMapper.mapBoolean("cropToPadding", 16843043);
            this.mMaxHeightId = propertyMapper.mapInt("maxHeight", 16843040);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mScaleTypeId = propertyMapper.mapObject("scaleType", 16843037);
            this.mSrcId = propertyMapper.mapObject("src", 16843033);
            this.mTintId = propertyMapper.mapObject("tint", 16843041);
            this.mTintModeId = propertyMapper.mapObject("tintMode", 16843771);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ImageView imageView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAdjustViewBoundsId, imageView.getAdjustViewBounds());
            propertyReader.readInt(this.mBaselineId, imageView.getBaseline());
            propertyReader.readBoolean(this.mBaselineAlignBottomId, imageView.getBaselineAlignBottom());
            propertyReader.readObject(this.mBlendModeId, imageView.getImageTintBlendMode());
            propertyReader.readBoolean(this.mCropToPaddingId, imageView.getCropToPadding());
            propertyReader.readInt(this.mMaxHeightId, imageView.getMaxHeight());
            propertyReader.readInt(this.mMaxWidthId, imageView.getMaxWidth());
            propertyReader.readObject(this.mScaleTypeId, imageView.getScaleType());
            propertyReader.readObject(this.mSrcId, imageView.getDrawable());
            propertyReader.readObject(this.mTintId, imageView.getImageTintList());
            propertyReader.readObject(this.mTintModeId, imageView.getImageTintMode());
        }
    }

    public ImageView(Context context) {
        super(context);
        this.mResource = 0;
        this.mHaveFrame = false;
        this.mAdjustViewBounds = false;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mColorFilter = null;
        this.mHasColorFilter = false;
        this.mHasXfermode = false;
        this.mAlpha = 255;
        this.mHasAlpha = false;
        this.mViewAlphaScale = 256;
        this.mDrawable = null;
        this.mRecycleableBitmapDrawable = null;
        this.mDrawableTintList = null;
        this.mDrawableBlendMode = null;
        this.mHasDrawableTint = false;
        this.mHasDrawableBlendMode = false;
        this.mState = null;
        this.mMergeState = false;
        this.mHasLevelSet = false;
        this.mLevel = 0;
        this.mDrawMatrix = null;
        this.mTempSrc = new RectF();
        this.mTempDst = new RectF();
        this.mBaseline = -1;
        this.mBaselineAlignBottom = false;
        initImageView();
    }

    public ImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mResource = 0;
        this.mHaveFrame = false;
        this.mAdjustViewBounds = false;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mColorFilter = null;
        this.mHasColorFilter = false;
        this.mHasXfermode = false;
        this.mAlpha = 255;
        this.mHasAlpha = false;
        this.mViewAlphaScale = 256;
        this.mDrawable = null;
        this.mRecycleableBitmapDrawable = null;
        this.mDrawableTintList = null;
        this.mDrawableBlendMode = null;
        this.mHasDrawableTint = false;
        this.mHasDrawableBlendMode = false;
        this.mState = null;
        this.mMergeState = false;
        this.mHasLevelSet = false;
        this.mLevel = 0;
        this.mDrawMatrix = null;
        this.mTempSrc = new RectF();
        this.mTempDst = new RectF();
        this.mBaseline = -1;
        this.mBaselineAlignBottom = false;
        initImageView();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ImageView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ImageView, attributeSet, obtainStyledAttributes, i, i2);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            setImageDrawable(drawable);
        }
        this.mBaselineAlignBottom = obtainStyledAttributes.getBoolean(6, false);
        this.mBaseline = obtainStyledAttributes.getDimensionPixelSize(8, -1);
        setAdjustViewBounds(obtainStyledAttributes.getBoolean(2, false));
        setMaxWidth(obtainStyledAttributes.getDimensionPixelSize(3, Integer.MAX_VALUE));
        setMaxHeight(obtainStyledAttributes.getDimensionPixelSize(4, Integer.MAX_VALUE));
        int i3 = obtainStyledAttributes.getInt(1, -1);
        if (i3 >= 0) {
            setScaleType(sScaleTypeArray[i3]);
        }
        if (obtainStyledAttributes.hasValue(5)) {
            this.mDrawableTintList = obtainStyledAttributes.getColorStateList(5);
            this.mHasDrawableTint = true;
            this.mDrawableBlendMode = BlendMode.SRC_ATOP;
            this.mHasDrawableBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(9)) {
            this.mDrawableBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(9, -1), this.mDrawableBlendMode);
            this.mHasDrawableBlendMode = true;
        }
        applyImageTint();
        int i4 = obtainStyledAttributes.getInt(10, 255);
        if (i4 != 255) {
            setImageAlpha(i4);
        }
        this.mCropToPadding = obtainStyledAttributes.getBoolean(7, false);
        obtainStyledAttributes.recycle();
    }

    private void initImageView() {
        this.mMatrix = new Matrix();
        this.mScaleType = ScaleType.FIT_CENTER;
        if (!sCompatDone) {
            int i = this.mContext.getApplicationInfo().targetSdkVersion;
            sCompatAdjustViewBounds = i <= 17;
            sCompatUseCorrectStreamDensity = i > 23;
            sCompatDrawableVisibilityDispatch = i < 24;
            sCompatDone = true;
        }
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(2);
        }
        if (getImportantForContentCapture() == 0) {
            setImportantForContentCapture(1);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return this.mDrawable == drawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (drawable == this.mDrawable) {
            if (drawable != null) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicWidth != this.mDrawableWidth || intrinsicHeight != this.mDrawableHeight) {
                    this.mDrawableWidth = intrinsicWidth;
                    this.mDrawableHeight = intrinsicHeight;
                    configureBounds();
                }
            }
            invalidate();
            return;
        }
        super.invalidateDrawable(drawable);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return (getBackground() == null || getBackground().getCurrent() == null) ? false : true;
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        CharSequence contentDescription = getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            return;
        }
        accessibilityEvent.getText().add(contentDescription);
    }

    public boolean getAdjustViewBounds() {
        return this.mAdjustViewBounds;
    }

    @RemotableViewMethod
    public void setAdjustViewBounds(boolean z) {
        this.mAdjustViewBounds = z;
        if (z) {
            setScaleType(ScaleType.FIT_CENTER);
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    @RemotableViewMethod
    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    @RemotableViewMethod
    public void setMaxHeight(int i) {
        this.mMaxHeight = i;
    }

    public Drawable getDrawable() {
        Drawable drawable = this.mDrawable;
        if (drawable == this.mRecycleableBitmapDrawable) {
            this.mRecycleableBitmapDrawable = null;
        }
        return drawable;
    }

    private class ImageDrawableCallback implements Runnable {
        private final Drawable drawable;
        private final int resource;
        private final Uri uri;

        ImageDrawableCallback(Drawable drawable, Uri uri, int i) {
            this.drawable = drawable;
            this.uri = uri;
            this.resource = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            ImageView.this.setImageDrawable(this.drawable);
            ImageView.this.mUri = this.uri;
            ImageView.this.mResource = this.resource;
        }
    }

    @RemotableViewMethod(asyncImpl = "setImageResourceAsync")
    public void setImageResource(int i) {
        int i2 = this.mDrawableWidth;
        int i3 = this.mDrawableHeight;
        updateDrawable(null);
        this.mResource = i;
        this.mUri = null;
        resolveUri();
        if (i2 != this.mDrawableWidth || i3 != this.mDrawableHeight) {
            requestLayout();
        }
        invalidate();
    }

    public Runnable setImageResourceAsync(int i) {
        Drawable drawable;
        if (i != 0) {
            try {
                drawable = getContext().getDrawable(i);
            } catch (Exception e) {
                Log.w(LOG_TAG, "Unable to find resource: " + i, e);
                i = 0;
            }
            return new ImageDrawableCallback(drawable, null, i);
        }
        drawable = null;
        return new ImageDrawableCallback(drawable, null, i);
    }

    @RemotableViewMethod(asyncImpl = "setImageURIAsync")
    public void setImageURI(Uri uri) {
        if (this.mResource == 0) {
            Uri uri2 = this.mUri;
            if (uri2 == uri) {
                return;
            }
            if (uri != null && uri2 != null && uri.equals(uri2)) {
                return;
            }
        }
        updateDrawable(null);
        this.mResource = 0;
        this.mUri = uri;
        int i = this.mDrawableWidth;
        int i2 = this.mDrawableHeight;
        resolveUri();
        if (i != this.mDrawableWidth || i2 != this.mDrawableHeight) {
            requestLayout();
        }
        invalidate();
    }

    public Runnable setImageURIAsync(Uri uri) {
        Uri uri2;
        if (this.mResource == 0 && ((uri2 = this.mUri) == uri || (uri != null && uri2 != null && uri.equals(uri2)))) {
            return null;
        }
        Drawable drawableFromUri = uri == null ? null : getDrawableFromUri(uri);
        if (drawableFromUri == null) {
            uri = null;
        }
        return new ImageDrawableCallback(drawableFromUri, uri, 0);
    }

    public void setImageDrawable(Drawable drawable) {
        if (this.mDrawable != drawable) {
            this.mResource = 0;
            this.mUri = null;
            int i = this.mDrawableWidth;
            int i2 = this.mDrawableHeight;
            updateDrawable(drawable);
            if (i != this.mDrawableWidth || i2 != this.mDrawableHeight) {
                requestLayout();
            }
            invalidate();
        }
    }

    @RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(Icon icon) {
        setImageDrawable(icon == null ? null : icon.loadDrawable(this.mContext));
    }

    public Runnable setImageIconAsync(Icon icon) {
        return new ImageDrawableCallback(icon == null ? null : icon.loadDrawable(this.mContext), null, 0);
    }

    @RemotableViewMethod
    public void setImageTintList(ColorStateList colorStateList) {
        this.mDrawableTintList = colorStateList;
        this.mHasDrawableTint = true;
        applyImageTint();
    }

    public ColorStateList getImageTintList() {
        return this.mDrawableTintList;
    }

    public void setImageTintMode(PorterDuff.Mode mode) {
        setImageTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setImageTintBlendMode(BlendMode blendMode) {
        this.mDrawableBlendMode = blendMode;
        this.mHasDrawableBlendMode = true;
        applyImageTint();
    }

    public PorterDuff.Mode getImageTintMode() {
        BlendMode blendMode = this.mDrawableBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getImageTintBlendMode() {
        return this.mDrawableBlendMode;
    }

    private void applyImageTint() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            if (this.mHasDrawableTint || this.mHasDrawableBlendMode) {
                Drawable mutate = drawable.mutate();
                this.mDrawable = mutate;
                if (this.mHasDrawableTint) {
                    mutate.setTintList(this.mDrawableTintList);
                }
                if (this.mHasDrawableBlendMode) {
                    this.mDrawable.setTintBlendMode(this.mDrawableBlendMode);
                }
                if (this.mDrawable.isStateful()) {
                    this.mDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @RemotableViewMethod
    public void setImageBitmap(Bitmap bitmap) {
        this.mDrawable = null;
        BitmapDrawable bitmapDrawable = this.mRecycleableBitmapDrawable;
        if (bitmapDrawable == null) {
            this.mRecycleableBitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
        } else {
            bitmapDrawable.setBitmap(bitmap);
        }
        setImageDrawable(this.mRecycleableBitmapDrawable);
    }

    public void setImageState(int[] iArr, boolean z) {
        this.mState = iArr;
        this.mMergeState = z;
        if (this.mDrawable != null) {
            refreshDrawableState();
            resizeFromDrawable();
        }
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        resizeFromDrawable();
    }

    @RemotableViewMethod
    public void setImageLevel(int i) {
        this.mLevel = i;
        this.mHasLevelSet = true;
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setLevel(i);
            resizeFromDrawable();
        }
    }

    public enum ScaleType {
        MATRIX(0),
        FIT_XY(1),
        FIT_START(2),
        FIT_CENTER(3),
        FIT_END(4),
        CENTER(5),
        CENTER_CROP(6),
        CENTER_INSIDE(7);

        final int nativeInt;

        ScaleType(int i) {
            this.nativeInt = i;
        }
    }

    public void setScaleType(ScaleType scaleType) {
        scaleType.getClass();
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            requestLayout();
            invalidate();
        }
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public Matrix getImageMatrix() {
        Matrix matrix = this.mDrawMatrix;
        return matrix == null ? new Matrix(Matrix.IDENTITY_MATRIX) : matrix;
    }

    public void setImageMatrix(Matrix matrix) {
        if (matrix != null && matrix.isIdentity()) {
            matrix = null;
        }
        if ((matrix != null || this.mMatrix.isIdentity()) && (matrix == null || this.mMatrix.equals(matrix))) {
            return;
        }
        this.mMatrix.set(matrix);
        configureBounds();
        invalidate();
    }

    public boolean getCropToPadding() {
        return this.mCropToPadding;
    }

    public void setCropToPadding(boolean z) {
        if (this.mCropToPadding != z) {
            this.mCropToPadding = z;
            requestLayout();
            invalidate();
        }
    }

    private void resolveUri() {
        if (this.mDrawable == null && getResources() != null) {
            Drawable drawable = null;
            if (this.mResource != 0) {
                try {
                    drawable = this.mContext.getDrawable(this.mResource);
                } catch (Exception e) {
                    Log.w(LOG_TAG, "Unable to find resource: " + this.mResource, e);
                    this.mResource = 0;
                }
            } else {
                Uri uri = this.mUri;
                if (uri == null) {
                    return;
                }
                Drawable drawableFromUri = getDrawableFromUri(uri);
                if (drawableFromUri == null) {
                    Log.w(LOG_TAG, "resolveUri failed on bad bitmap uri: " + this.mUri);
                    this.mUri = null;
                }
                drawable = drawableFromUri;
            }
            updateDrawable(drawable);
        }
    }

    private Drawable getDrawableFromUri(Uri uri) {
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            try {
                ContentResolver.OpenResourceIdResult resourceId = this.mContext.getContentResolver().getResourceId(uri);
                return resourceId.r.getDrawable(resourceId.id, this.mContext.getTheme());
            } catch (Exception e) {
                Log.w(LOG_TAG, "Unable to open content: " + uri, e);
            }
        } else if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return ImageDecoder.decodeDrawable(ImageDecoder.createSource(this.mContext.getContentResolver(), uri, sCompatUseCorrectStreamDensity ? getResources() : null), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.widget.ImageView$$ExternalSyntheticLambda0
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        imageDecoder.setAllocator(1);
                    }
                });
            } catch (IOException e2) {
                Log.w(LOG_TAG, "Unable to open content: " + uri, e2);
            }
        } else {
            return Drawable.createFromPath(uri.toString());
        }
        return null;
    }

    @Override // android.view.View
    public int[] onCreateDrawableState(int i) {
        int[] iArr = this.mState;
        if (iArr == null) {
            return super.onCreateDrawableState(i);
        }
        return !this.mMergeState ? iArr : mergeDrawableStates(super.onCreateDrawableState(i + iArr.length), this.mState);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x005b, code lost:
    
        r3 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateDrawable(android.graphics.drawable.Drawable r6) {
        /*
            r5 = this;
            android.graphics.drawable.BitmapDrawable r0 = r5.mRecycleableBitmapDrawable
            r1 = 0
            if (r6 == r0) goto La
            if (r0 == 0) goto La
            r0.setBitmap(r1)
        La:
            android.graphics.drawable.Drawable r0 = r5.mDrawable
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L2f
            if (r0 != r6) goto L14
            r4 = r2
            goto L15
        L14:
            r4 = r3
        L15:
            r0.setCallback(r1)
            android.graphics.drawable.Drawable r0 = r5.mDrawable
            r5.unscheduleDrawable(r0)
            boolean r0 = android.widget.ImageView.sCompatDrawableVisibilityDispatch
            if (r0 != 0) goto L30
            if (r4 != 0) goto L30
            boolean r0 = r5.isAttachedToWindow()
            if (r0 == 0) goto L30
            android.graphics.drawable.Drawable r0 = r5.mDrawable
            r0.setVisible(r3, r3)
            goto L30
        L2f:
            r4 = r3
        L30:
            r5.mDrawable = r6
            if (r6 == 0) goto L98
            r6.setCallback(r5)
            int r0 = r5.getLayoutDirection()
            r6.setLayoutDirection(r0)
            boolean r0 = r6.isStateful()
            if (r0 == 0) goto L4b
            int[] r0 = r5.getDrawableState()
            r6.setState(r0)
        L4b:
            if (r4 == 0) goto L51
            boolean r0 = android.widget.ImageView.sCompatDrawableVisibilityDispatch
            if (r0 == 0) goto L73
        L51:
            boolean r0 = android.widget.ImageView.sCompatDrawableVisibilityDispatch
            if (r0 == 0) goto L5d
            int r0 = r5.getVisibility()
            if (r0 != 0) goto L70
        L5b:
            r3 = r2
            goto L70
        L5d:
            boolean r0 = r5.isAttachedToWindow()
            if (r0 == 0) goto L70
            int r0 = r5.getWindowVisibility()
            if (r0 != 0) goto L70
            boolean r0 = r5.isShown()
            if (r0 == 0) goto L70
            goto L5b
        L70:
            r6.setVisible(r3, r2)
        L73:
            boolean r0 = r5.mHasLevelSet
            if (r0 == 0) goto L7c
            int r0 = r5.mLevel
            r6.setLevel(r0)
        L7c:
            int r0 = r6.getIntrinsicWidth()
            r5.mDrawableWidth = r0
            int r6 = r6.getIntrinsicHeight()
            r5.mDrawableHeight = r6
            r5.applyImageTint()
            r5.applyColorFilter()
            r5.applyAlpha()
            r5.applyXfermode()
            r5.configureBounds()
            return
        L98:
            r6 = -1
            r5.mDrawableHeight = r6
            r5.mDrawableWidth = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ImageView.updateDrawable(android.graphics.drawable.Drawable):void");
    }

    private void resizeFromDrawable() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (intrinsicWidth < 0) {
                intrinsicWidth = this.mDrawableWidth;
            }
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicHeight < 0) {
                intrinsicHeight = this.mDrawableHeight;
            }
            if (intrinsicWidth == this.mDrawableWidth && intrinsicHeight == this.mDrawableHeight) {
                return;
            }
            this.mDrawableWidth = intrinsicWidth;
            this.mDrawableHeight = intrinsicHeight;
            requestLayout();
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setLayoutDirection(i);
        }
    }

    private static Matrix.ScaleToFit scaleTypeToScaleToFit(ScaleType scaleType) {
        return sS2FArray[scaleType.nativeInt - 1];
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00cd  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onMeasure(int r19, int r20) {
        /*
            Method dump skipped, instructions count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ImageView.onMeasure(int, int):void");
    }

    private int resolveAdjustedSize(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i3);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(Math.min(i, size), i2);
        }
        if (mode != 0) {
            return mode != 1073741824 ? i : size;
        }
        return Math.min(i, i2);
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        this.mHaveFrame = true;
        configureBounds();
        return frame;
    }

    private void configureBounds() {
        float f;
        float f2;
        if (this.mDrawable == null || !this.mHaveFrame) {
            return;
        }
        int i = this.mDrawableWidth;
        int i2 = this.mDrawableHeight;
        int width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
        int height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
        boolean z = (i < 0 || width == i) && (i2 < 0 || height == i2);
        if (i <= 0 || i2 <= 0 || ScaleType.FIT_XY == this.mScaleType) {
            this.mDrawable.setBounds(0, 0, width, height);
            this.mDrawMatrix = null;
            return;
        }
        this.mDrawable.setBounds(0, 0, i, i2);
        if (ScaleType.MATRIX == this.mScaleType) {
            if (this.mMatrix.isIdentity()) {
                this.mDrawMatrix = null;
                return;
            } else {
                this.mDrawMatrix = this.mMatrix;
                return;
            }
        }
        if (z) {
            this.mDrawMatrix = null;
            return;
        }
        if (ScaleType.CENTER == this.mScaleType) {
            Matrix matrix = this.mMatrix;
            this.mDrawMatrix = matrix;
            matrix.setTranslate(Math.round((width - i) * 0.5f), Math.round((height - i2) * 0.5f));
            return;
        }
        float f3 = 0.0f;
        if (ScaleType.CENTER_CROP == this.mScaleType) {
            Matrix matrix2 = this.mMatrix;
            this.mDrawMatrix = matrix2;
            if (i * height > width * i2) {
                f2 = height / i2;
                float f4 = (width - (i * f2)) * 0.5f;
                f = 0.0f;
                f3 = f4;
            } else {
                float f5 = width / i;
                f = (height - (i2 * f5)) * 0.5f;
                f2 = f5;
            }
            matrix2.setScale(f2, f2);
            this.mDrawMatrix.postTranslate(Math.round(f3), Math.round(f));
            return;
        }
        if (ScaleType.CENTER_INSIDE == this.mScaleType) {
            this.mDrawMatrix = this.mMatrix;
            float min = (i > width || i2 > height) ? Math.min(width / i, height / i2) : 1.0f;
            float round = Math.round((width - (i * min)) * 0.5f);
            float round2 = Math.round((height - (i2 * min)) * 0.5f);
            this.mDrawMatrix.setScale(min, min);
            this.mDrawMatrix.postTranslate(round, round2);
            return;
        }
        this.mTempSrc.set(0.0f, 0.0f, i, i2);
        this.mTempDst.set(0.0f, 0.0f, width, height);
        Matrix matrix3 = this.mMatrix;
        this.mDrawMatrix = matrix3;
        matrix3.setRectToRect(this.mTempSrc, this.mTempDst, scaleTypeToScaleToFit(this.mScaleType));
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    public void animateTransform(Matrix matrix) {
        Drawable drawable = this.mDrawable;
        if (drawable == null) {
            return;
        }
        if (matrix == null) {
            this.mDrawable.setBounds(0, 0, (getWidth() - this.mPaddingLeft) - this.mPaddingRight, (getHeight() - this.mPaddingTop) - this.mPaddingBottom);
            this.mDrawMatrix = null;
        } else {
            drawable.setBounds(0, 0, this.mDrawableWidth, this.mDrawableHeight);
            if (this.mDrawMatrix == null) {
                this.mDrawMatrix = new Matrix();
            }
            this.mDrawMatrix.set(matrix);
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawable == null || this.mDrawableWidth == 0 || this.mDrawableHeight == 0) {
            return;
        }
        if (this.mDrawMatrix == null && this.mPaddingTop == 0 && this.mPaddingLeft == 0) {
            this.mDrawable.draw(canvas);
            return;
        }
        int saveCount = canvas.getSaveCount();
        canvas.save();
        if (this.mCropToPadding) {
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + i, this.mPaddingTop + i2, ((i + this.mRight) - this.mLeft) - this.mPaddingRight, ((i2 + this.mBottom) - this.mTop) - this.mPaddingBottom);
        }
        canvas.translate(this.mPaddingLeft, this.mPaddingTop);
        Matrix matrix = this.mDrawMatrix;
        if (matrix != null) {
            canvas.concat(matrix);
        }
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override // android.view.View
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public int getBaseline() {
        if (this.mBaselineAlignBottom) {
            return getMeasuredHeight();
        }
        return this.mBaseline;
    }

    public void setBaseline(int i) {
        if (this.mBaseline != i) {
            this.mBaseline = i;
            requestLayout();
        }
    }

    public void setBaselineAlignBottom(boolean z) {
        if (this.mBaselineAlignBottom != z) {
            this.mBaselineAlignBottom = z;
            requestLayout();
        }
    }

    public boolean getBaselineAlignBottom() {
        return this.mBaselineAlignBottom;
    }

    public final void setColorFilter(int i, PorterDuff.Mode mode) {
        setColorFilter(new PorterDuffColorFilter(i, mode));
    }

    @RemotableViewMethod
    public final void setColorFilter(int i) {
        setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
    }

    public final void clearColorFilter() {
        setColorFilter((ColorFilter) null);
    }

    public final void setXfermode(Xfermode xfermode) {
        if (this.mXfermode != xfermode) {
            this.mXfermode = xfermode;
            this.mHasXfermode = true;
            applyXfermode();
            invalidate();
        }
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mColorFilter = colorFilter;
            this.mHasColorFilter = true;
            applyColorFilter();
            invalidate();
        }
    }

    public int getImageAlpha() {
        return this.mAlpha;
    }

    @RemotableViewMethod
    public void setImageAlpha(int i) {
        setAlpha(i);
    }

    @RemotableViewMethod
    @Deprecated
    public void setAlpha(int i) {
        int i2 = i & 255;
        if (this.mAlpha != i2) {
            this.mAlpha = i2;
            this.mHasAlpha = true;
            applyAlpha();
            invalidate();
        }
    }

    private void applyXfermode() {
        Drawable drawable = this.mDrawable;
        if (drawable == null || !this.mHasXfermode) {
            return;
        }
        Drawable mutate = drawable.mutate();
        this.mDrawable = mutate;
        mutate.setXfermode(this.mXfermode);
    }

    private void applyColorFilter() {
        Drawable drawable = this.mDrawable;
        if (drawable == null || !this.mHasColorFilter) {
            return;
        }
        Drawable mutate = drawable.mutate();
        this.mDrawable = mutate;
        mutate.setColorFilter(this.mColorFilter);
    }

    private void applyAlpha() {
        Drawable drawable = this.mDrawable;
        if (drawable == null || !this.mHasAlpha) {
            return;
        }
        Drawable mutate = drawable.mutate();
        this.mDrawable = mutate;
        mutate.setAlpha((this.mAlpha * 256) >> 8);
    }

    @Override // android.view.View
    public boolean isOpaque() {
        if (super.isOpaque()) {
            return true;
        }
        Drawable drawable = this.mDrawable;
        return drawable != null && this.mXfermode == null && drawable.getOpacity() == -1 && ((this.mAlpha * 256) >> 8) == 255 && isFilledByImage();
    }

    private boolean isFilledByImage() {
        Drawable drawable = this.mDrawable;
        if (drawable == null) {
            return false;
        }
        Rect bounds = drawable.getBounds();
        Matrix matrix = this.mDrawMatrix;
        if (matrix == null) {
            return bounds.left <= 0 && bounds.top <= 0 && bounds.right >= getWidth() && bounds.bottom >= getHeight();
        }
        if (matrix.rectStaysRect()) {
            RectF rectF = this.mTempSrc;
            RectF rectF2 = this.mTempDst;
            rectF.set(bounds);
            matrix.mapRect(rectF2, rectF);
            if (rectF2.left <= 0.0f && rectF2.top <= 0.0f && rectF2.right >= getWidth() && rectF2.bottom >= getHeight()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        Drawable drawable = this.mDrawable;
        if (drawable == null || sCompatDrawableVisibilityDispatch) {
            return;
        }
        drawable.setVisible(z, false);
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setVisibility(int i) {
        super.setVisibility(i);
        Drawable drawable = this.mDrawable;
        if (drawable == null || !sCompatDrawableVisibilityDispatch) {
            return;
        }
        drawable.setVisible(i == 0, false);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable drawable = this.mDrawable;
        if (drawable == null || !sCompatDrawableVisibilityDispatch) {
            return;
        }
        drawable.setVisible(getVisibility() == 0, false);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Drawable drawable = this.mDrawable;
        if (drawable == null || !sCompatDrawableVisibilityDispatch) {
            return;
        }
        drawable.setVisible(false, false);
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return ImageView.class.getName();
    }

    @Override // android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:baseline", getBaseline());
    }

    @Override // android.view.View
    public boolean isDefaultFocusHighlightNeeded(Drawable drawable, Drawable drawable2) {
        Drawable drawable3 = this.mDrawable;
        return super.isDefaultFocusHighlightNeeded(drawable, drawable2) && (drawable3 == null || !drawable3.isStateful() || !this.mDrawable.hasFocusStateSpecified());
    }
}
