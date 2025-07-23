package android.graphics.drawable;

import android.app.ActivityThread;
import android.app.SemAppIconSolution;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.PathParser;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AdaptiveIconDrawable extends Drawable implements Drawable.Callback {
    private static final int BACKGROUND_ID = 0;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float EXTRA_INSET_PERCENTAGE = 0.25f;
    private static final int FOREGROUND_ID = 1;
    public static final float MASK_SIZE = 100.0f;
    private static final int MONOCHROME_ID = 2;
    private static final float SAFEZONE_SCALE = 0.9166667f;
    private static Path sMask;
    private final Canvas mCanvas;
    private boolean mChildRequestedInvalidation;
    private Rect mHotspotBounds;
    LayerState mLayerState;
    private Bitmap mLayersBitmap;
    private Shader mLayersShader;
    private final Path mMask;
    private final Matrix mMaskMatrix;
    private final Path mMaskScaleOnly;
    private boolean mMutated;
    private boolean mNightModeLayer;
    private Paint mPaint;
    private boolean mSuspendChildInvalidation;
    private final Rect mTmpOutRect;
    private final Region mTransparentRegion;

    public static float getExtraInsetFraction() {
        return 0.25f;
    }

    public static float getExtraInsetPercentage() {
        return 0.25f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    AdaptiveIconDrawable() {
        this((LayerState) null, (Resources) null);
    }

    AdaptiveIconDrawable(LayerState layerState, Resources resources) {
        Resources resources2;
        this.mTmpOutRect = new Rect();
        this.mPaint = new Paint(7);
        this.mNightModeLayer = false;
        this.mLayerState = createConstantState(layerState, resources);
        if (ActivityThread.currentActivityThread() == null) {
            resources2 = Resources.getSystem();
        } else {
            resources2 = ActivityThread.currentActivityThread().getApplication().getResources();
        }
        sMask = PathParser.createPathFromPathData(resources2.getString(R.string.config_icon_mask));
        Path path = new Path(sMask);
        this.mMask = path;
        this.mMaskScaleOnly = new Path(path);
        this.mMaskMatrix = new Matrix();
        this.mCanvas = new Canvas();
        this.mTransparentRegion = new Region();
    }

    private ChildDrawable createChildDrawable(Drawable drawable) {
        ChildDrawable childDrawable = new ChildDrawable(this.mLayerState.mDensity);
        childDrawable.mDrawable = drawable;
        childDrawable.mDrawable.setCallback(this);
        this.mLayerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
        return childDrawable;
    }

    LayerState createConstantState(LayerState layerState, Resources resources) {
        return new LayerState(layerState, this, resources);
    }

    public AdaptiveIconDrawable(Drawable drawable, Drawable drawable2) {
        this(drawable, drawable2, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AdaptiveIconDrawable(Drawable drawable, Drawable drawable2, Drawable drawable3) {
        this((LayerState) null, (Resources) null);
        if (drawable != null) {
            addLayer(0, createChildDrawable(drawable));
        }
        if (drawable2 != null) {
            addLayer(1, createChildDrawable(drawable2));
        }
        if (drawable3 != null) {
            addLayer(2, createChildDrawable(drawable3));
        }
    }

    private void addLayer(int i, ChildDrawable childDrawable) {
        this.mLayerState.mChildren[i] = childDrawable;
        this.mLayerState.invalidateCache();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return;
        }
        int resolveDensity = Drawable.resolveDensity(resources, 0);
        layerState.setDensity(resolveDensity);
        layerState.mSrcDensityOverride = this.mSrcDensityOverride;
        layerState.mSourceDrawableId = Resources.getAttributeSetSourceResId(attributeSet);
        for (ChildDrawable childDrawable : layerState.mChildren) {
            childDrawable.setDensity(resolveDensity);
        }
        inflateLayers(resources, xmlPullParser, attributeSet, theme);
    }

    public Path getIconMask() {
        return this.mMask;
    }

    public Drawable getForeground() {
        return this.mLayerState.mChildren[1].mDrawable;
    }

    public Drawable getBackground() {
        return this.mLayerState.mChildren[0].mDrawable;
    }

    public Drawable getMonochrome() {
        return this.mLayerState.mChildren[2].mDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        updateLayerBounds(rect);
    }

    private void updateLayerBounds(Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        try {
            suspendChildInvalidation();
            updateLayerBoundsInternal(rect);
            updateMaskBoundsInternal(rect);
        } finally {
            resumeChildInvalidation();
        }
    }

    private void updateLayerBoundsInternal(Rect rect) {
        int width = rect.width() / 2;
        int height = rect.height() / 2;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = this.mLayerState.mChildren[i].mDrawable;
            if (drawable != null) {
                int width2 = (int) (rect.width() / 1.3333334f);
                int height2 = (int) (rect.height() / 1.3333334f);
                Rect rect2 = this.mTmpOutRect;
                rect2.set(width - width2, height - height2, width2 + width, height2 + height);
                drawable.setBounds(rect2);
            }
        }
    }

    private void updateMaskBoundsInternal(Rect rect) {
        this.mMaskMatrix.setScale(rect.width() / 100.0f, rect.height() / 100.0f);
        sMask.transform(this.mMaskMatrix, this.mMaskScaleOnly);
        this.mMaskMatrix.postTranslate(rect.left, rect.top);
        sMask.transform(this.mMaskMatrix, this.mMask);
        Bitmap bitmap = this.mLayersBitmap;
        if (bitmap == null || bitmap.getWidth() != rect.width() || this.mLayersBitmap.getHeight() != rect.height()) {
            this.mLayersBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        }
        this.mPaint.setShader(null);
        this.mTransparentRegion.setEmpty();
        this.mLayersShader = null;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Bitmap bitmap = this.mLayersBitmap;
        if (bitmap == null) {
            return;
        }
        if (this.mLayersShader == null) {
            this.mCanvas.setBitmap(bitmap);
            this.mCanvas.drawColor(-16777216);
            if (this.mLayerState.mChildren[0].mDrawable != null) {
                this.mLayerState.mChildren[0].mDrawable.draw(this.mCanvas);
            }
            if (this.mLayerState.mChildren[1].mDrawable != null) {
                this.mLayerState.mChildren[1].mDrawable.draw(this.mCanvas);
            }
            if (this.mNightModeLayer) {
                this.mCanvas.drawPaint(SemAppIconSolution.PAINT_FOR_NIGHT_LAYER);
            }
            BitmapShader bitmapShader = new BitmapShader(this.mLayersBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mLayersShader = bitmapShader;
            this.mPaint.setShader(bitmapShader);
        }
        if (this.mMaskScaleOnly != null) {
            Rect bounds = getBounds();
            canvas.translate(bounds.left, bounds.top);
            canvas.drawPath(this.mMaskScaleOnly, this.mPaint);
            canvas.translate(-bounds.left, -bounds.top);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        this.mLayersShader = null;
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setPath(this.mMask);
    }

    public Region getSafeZone() {
        Path iconMask = getIconMask();
        this.mMaskMatrix.setScale(SAFEZONE_SCALE, SAFEZONE_SCALE, getBounds().centerX(), getBounds().centerY());
        Path path = new Path();
        iconMask.transform(this.mMaskMatrix, path);
        Region region = new Region(getBounds());
        region.setPath(path, region);
        return region;
    }

    @Override // android.graphics.drawable.Drawable
    public Region getTransparentRegion() {
        if (this.mTransparentRegion.isEmpty()) {
            this.mMask.toggleInverseFillType();
            this.mTransparentRegion.set(getBounds());
            Region region = this.mTransparentRegion;
            region.setPath(this.mMask, region);
            this.mMask.toggleInverseFillType();
        }
        return this.mTransparentRegion;
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return;
        }
        int resolveDensity = Drawable.resolveDensity(theme.getResources(), 0);
        layerState.setDensity(resolveDensity);
        ChildDrawable[] childDrawableArr = layerState.mChildren;
        for (int i = 0; i < 3; i++) {
            ChildDrawable childDrawable = childDrawableArr[i];
            childDrawable.setDensity(resolveDensity);
            if (childDrawable.mThemeAttrs != null) {
                TypedArray resolveAttributes = theme.resolveAttributes(childDrawable.mThemeAttrs, R.styleable.AdaptiveIconDrawableLayer);
                updateLayerFromTypedArray(childDrawable, resolveAttributes);
                resolveAttributes.recycle();
            }
            Drawable drawable = childDrawable.mDrawable;
            if (drawable != null && drawable.canApplyTheme()) {
                drawable.applyTheme(theme);
                layerState.mChildrenChangingConfigurations = drawable.getChangingConfigurations() | layerState.mChildrenChangingConfigurations;
            }
        }
    }

    public int getSourceDrawableResId() {
        LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return 0;
        }
        return layerState.mSourceDrawableId;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0008, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0097 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void inflateLayers(android.content.res.Resources r9, org.xmlpull.v1.XmlPullParser r10, android.util.AttributeSet r11, android.content.res.Resources.Theme r12) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r8 = this;
            android.graphics.drawable.AdaptiveIconDrawable$LayerState r0 = r8.mLayerState
            int r1 = r10.getDepth()
            r2 = 1
            int r1 = r1 + r2
        L8:
            int r3 = r10.next()
            if (r3 == r2) goto Lb7
            int r4 = r10.getDepth()
            if (r4 >= r1) goto L17
            r5 = 3
            if (r3 == r5) goto Lb7
        L17:
            r5 = 2
            if (r3 == r5) goto L1b
            goto L8
        L1b:
            if (r4 <= r1) goto L1e
            goto L8
        L1e:
            java.lang.String r3 = r10.getName()
            r3.hashCode()
            int r4 = r3.hashCode()
            r6 = 0
            r7 = -1
            switch(r4) {
                case -1905977571: goto L45;
                case -1332194002: goto L3a;
                case 1984457027: goto L2f;
                default: goto L2e;
            }
        L2e:
            goto L50
        L2f:
            java.lang.String r4 = "foreground"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L38
            goto L50
        L38:
            r7 = r5
            goto L50
        L3a:
            java.lang.String r4 = "background"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L43
            goto L50
        L43:
            r7 = r2
            goto L50
        L45:
            java.lang.String r4 = "monochrome"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L4f
            goto L50
        L4f:
            r7 = r6
        L50:
            switch(r7) {
                case 0: goto L56;
                case 1: goto L57;
                case 2: goto L54;
                default: goto L53;
            }
        L53:
            goto L8
        L54:
            r6 = r2
            goto L57
        L56:
            r6 = r5
        L57:
            android.graphics.drawable.AdaptiveIconDrawable$ChildDrawable r3 = new android.graphics.drawable.AdaptiveIconDrawable$ChildDrawable
            int r4 = r0.mDensity
            r3.<init>(r4)
            int[] r4 = com.android.internal.R.styleable.AdaptiveIconDrawableLayer
            android.content.res.TypedArray r4 = obtainAttributes(r9, r12, r11, r4)
            r8.updateLayerFromTypedArray(r3, r4)
            r4.recycle()
            android.graphics.drawable.Drawable r4 = r3.mDrawable
            if (r4 != 0) goto Lb2
            int[] r4 = r3.mThemeAttrs
            if (r4 != 0) goto Lb2
        L72:
            int r4 = r10.next()
            r7 = 4
            if (r4 != r7) goto L7a
            goto L72
        L7a:
            if (r4 != r5) goto L97
            android.graphics.drawable.AdaptiveIconDrawable$LayerState r4 = r8.mLayerState
            int r4 = r4.mSrcDensityOverride
            android.graphics.drawable.Drawable r4 = android.graphics.drawable.Drawable.createFromXmlInnerForDensity(r9, r10, r11, r4, r12)
            r3.mDrawable = r4
            android.graphics.drawable.Drawable r4 = r3.mDrawable
            r4.setCallback(r8)
            int r4 = r0.mChildrenChangingConfigurations
            android.graphics.drawable.Drawable r5 = r3.mDrawable
            int r5 = r5.getChangingConfigurations()
            r4 = r4 | r5
            r0.mChildrenChangingConfigurations = r4
            goto Lb2
        L97:
            org.xmlpull.v1.XmlPullParserException r8 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = r10.getPositionDescription()
            r9.append(r10)
            java.lang.String r10 = ": <foreground> or <background> tag requires a 'drawable'attribute or child tag defining a drawable"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        Lb2:
            r8.addLayer(r6, r3)
            goto L8
        Lb7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.drawable.AdaptiveIconDrawable.inflateLayers(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):void");
    }

    private void updateLayerFromTypedArray(ChildDrawable childDrawable, TypedArray typedArray) {
        LayerState layerState = this.mLayerState;
        layerState.mChildrenChangingConfigurations |= typedArray.getChangingConfigurations();
        childDrawable.mThemeAttrs = typedArray.extractThemeAttrs();
        Drawable drawableForDensity = typedArray.getDrawableForDensity(0, layerState.mSrcDensityOverride);
        if (drawableForDensity != null) {
            if (childDrawable.mDrawable != null) {
                childDrawable.mDrawable.setCallback(null);
            }
            childDrawable.mDrawable = drawableForDensity;
            childDrawable.mDrawable.setCallback(this);
            layerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        LayerState layerState = this.mLayerState;
        return (layerState != null && layerState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isProjected() {
        if (super.isProjected()) {
            return true;
        }
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            if (childDrawableArr[i].mDrawable != null && childDrawableArr[i].mDrawable.isProjected()) {
                return true;
            }
        }
        return false;
    }

    private void suspendChildInvalidation() {
        this.mSuspendChildInvalidation = true;
    }

    private void resumeChildInvalidation() {
        this.mSuspendChildInvalidation = false;
        if (this.mChildRequestedInvalidation) {
            this.mChildRequestedInvalidation = false;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (this.mSuspendChildInvalidation) {
            this.mChildRequestedInvalidation = true;
        } else {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mLayerState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setHotspot(f, f2);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i5 = 0; i5 < 3; i5++) {
            Drawable drawable = childDrawableArr[i5].mDrawable;
            if (drawable != null) {
                drawable.setHotspotBounds(i, i2, i3, i4);
            }
        }
        Rect rect = this.mHotspotBounds;
        if (rect == null) {
            this.mHotspotBounds = new Rect(i, i2, i3, i4);
        } else {
            rect.set(i, i2, i3, i4);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void getHotspotBounds(Rect rect) {
        Rect rect2 = this.mHotspotBounds;
        if (rect2 != null) {
            rect.set(rect2);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setVisible(z, z2);
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setDither(z);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setTintList(colorStateList);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(BlendMode blendMode) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setTintBlendMode(blendMode);
            }
        }
    }

    public void setOpacity(int i) {
        this.mLayerState.mOpacityOverride = i;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.mLayerState.mAutoMirrored = z;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setAutoMirrored(z);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mLayerState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.jumpToCurrentState();
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mLayerState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mLayerState.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        boolean z = false;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null && drawable.isStateful() && drawable.setState(iArr)) {
                z = true;
            }
        }
        if (z) {
            updateLayerBounds(getBounds());
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        boolean z = false;
        for (int i2 = 0; i2 < 3; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null && drawable.setLevel(i)) {
                z = true;
            }
        }
        if (z) {
            updateLayerBounds(getBounds());
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) (getMaxIntrinsicWidth() * DEFAULT_VIEW_PORT_SCALE);
    }

    private int getMaxIntrinsicWidth() {
        int intrinsicWidth;
        int i = -1;
        int i2 = 0;
        while (true) {
            LayerState layerState = this.mLayerState;
            if (i2 >= 3) {
                return i;
            }
            ChildDrawable childDrawable = layerState.mChildren[i2];
            if (childDrawable.mDrawable != null && (intrinsicWidth = childDrawable.mDrawable.getIntrinsicWidth()) > i) {
                i = intrinsicWidth;
            }
            i2++;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) (getMaxIntrinsicHeight() * DEFAULT_VIEW_PORT_SCALE);
    }

    private int getMaxIntrinsicHeight() {
        int intrinsicHeight;
        int i = -1;
        int i2 = 0;
        while (true) {
            LayerState layerState = this.mLayerState;
            if (i2 >= 3) {
                return i;
            }
            ChildDrawable childDrawable = layerState.mChildren[i2];
            if (childDrawable.mDrawable != null && (intrinsicHeight = childDrawable.mDrawable.getIntrinsicHeight()) > i) {
                i = intrinsicHeight;
            }
            i2++;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (!this.mLayerState.canConstantState()) {
            return null;
        }
        this.mLayerState.mChangingConfigurations = getChangingConfigurations();
        return this.mLayerState;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mLayerState = createConstantState(this.mLayerState, null);
            int i = 0;
            while (true) {
                LayerState layerState = this.mLayerState;
                if (i >= 3) {
                    break;
                }
                Drawable drawable = layerState.mChildren[i].mDrawable;
                if (drawable != null) {
                    drawable.mutate();
                }
                i++;
            }
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.clearMutated();
            }
        }
        this.mMutated = false;
    }

    public void setNightModeLayer(boolean z) {
        this.mNightModeLayer = z;
    }

    static class ChildDrawable {
        public int mDensity;
        public Drawable mDrawable;
        public int[] mThemeAttrs;

        ChildDrawable(int i) {
            this.mDensity = i;
        }

        ChildDrawable(ChildDrawable childDrawable, AdaptiveIconDrawable adaptiveIconDrawable, Resources resources) {
            Drawable drawable;
            this.mDensity = 160;
            Drawable drawable2 = childDrawable.mDrawable;
            if (drawable2 != null) {
                Drawable.ConstantState constantState = drawable2.getConstantState();
                if (constantState == null) {
                    drawable = drawable2;
                } else if (resources != null) {
                    drawable = constantState.newDrawable(resources);
                } else {
                    drawable = constantState.newDrawable();
                }
                drawable.setCallback(adaptiveIconDrawable);
                drawable.setBounds(drawable2.getBounds());
                drawable.setLevel(drawable2.getLevel());
            } else {
                drawable = null;
            }
            this.mDrawable = drawable;
            this.mThemeAttrs = childDrawable.mThemeAttrs;
            this.mDensity = Drawable.resolveDensity(resources, childDrawable.mDensity);
        }

        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            Drawable drawable = this.mDrawable;
            return drawable != null && drawable.canApplyTheme();
        }

        public final void setDensity(int i) {
            if (this.mDensity != i) {
                this.mDensity = i;
            }
        }
    }

    static class LayerState extends Drawable.ConstantState {
        static final int N_CHILDREN = 3;
        private boolean mAutoMirrored;
        int mChangingConfigurations;
        private boolean mCheckedOpacity;
        private boolean mCheckedStateful;
        ChildDrawable[] mChildren;
        int mChildrenChangingConfigurations;
        int mDensity;
        private boolean mIsStateful;
        private int mOpacity;
        int mOpacityOverride;
        int mSourceDrawableId;
        int mSrcDensityOverride;
        private int[] mThemeAttrs;

        LayerState(LayerState layerState, AdaptiveIconDrawable adaptiveIconDrawable, Resources resources) {
            int i = 0;
            this.mSrcDensityOverride = 0;
            this.mOpacityOverride = 0;
            this.mSourceDrawableId = 0;
            this.mAutoMirrored = false;
            this.mDensity = Drawable.resolveDensity(resources, layerState != null ? layerState.mDensity : 0);
            this.mChildren = new ChildDrawable[3];
            if (layerState == null) {
                while (i < 3) {
                    this.mChildren[i] = new ChildDrawable(this.mDensity);
                    i++;
                }
                return;
            }
            ChildDrawable[] childDrawableArr = layerState.mChildren;
            this.mChangingConfigurations = layerState.mChangingConfigurations;
            this.mChildrenChangingConfigurations = layerState.mChildrenChangingConfigurations;
            this.mSourceDrawableId = layerState.mSourceDrawableId;
            while (i < 3) {
                this.mChildren[i] = new ChildDrawable(childDrawableArr[i], adaptiveIconDrawable, resources);
                i++;
            }
            this.mCheckedOpacity = layerState.mCheckedOpacity;
            this.mOpacity = layerState.mOpacity;
            this.mCheckedStateful = layerState.mCheckedStateful;
            this.mIsStateful = layerState.mIsStateful;
            this.mAutoMirrored = layerState.mAutoMirrored;
            this.mThemeAttrs = layerState.mThemeAttrs;
            this.mOpacityOverride = layerState.mOpacityOverride;
            this.mSrcDensityOverride = layerState.mSrcDensityOverride;
        }

        public final void setDensity(int i) {
            if (this.mDensity != i) {
                this.mDensity = i;
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null || super.canApplyTheme()) {
                return true;
            }
            ChildDrawable[] childDrawableArr = this.mChildren;
            for (int i = 0; i < 3; i++) {
                if (childDrawableArr[i].canApplyTheme()) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new AdaptiveIconDrawable(this, (Resources) null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new AdaptiveIconDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChildrenChangingConfigurations | this.mChangingConfigurations;
        }

        public final int getOpacity() {
            if (this.mCheckedOpacity) {
                return this.mOpacity;
            }
            ChildDrawable[] childDrawableArr = this.mChildren;
            int i = 0;
            while (true) {
                if (i >= 3) {
                    i = -1;
                    break;
                }
                if (childDrawableArr[i].mDrawable != null) {
                    break;
                }
                i++;
            }
            int opacity = i >= 0 ? childDrawableArr[i].mDrawable.getOpacity() : -2;
            for (int i2 = i + 1; i2 < 3; i2++) {
                Drawable drawable = childDrawableArr[i2].mDrawable;
                if (drawable != null) {
                    opacity = Drawable.resolveOpacity(opacity, drawable.getOpacity());
                }
            }
            this.mOpacity = opacity;
            this.mCheckedOpacity = true;
            return opacity;
        }

        public final boolean isStateful() {
            if (this.mCheckedStateful) {
                return this.mIsStateful;
            }
            ChildDrawable[] childDrawableArr = this.mChildren;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i < 3) {
                    Drawable drawable = childDrawableArr[i].mDrawable;
                    if (drawable != null && drawable.isStateful()) {
                        z = true;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            this.mIsStateful = z;
            this.mCheckedStateful = true;
            return z;
        }

        public final boolean hasFocusStateSpecified() {
            ChildDrawable[] childDrawableArr = this.mChildren;
            for (int i = 0; i < 3; i++) {
                Drawable drawable = childDrawableArr[i].mDrawable;
                if (drawable != null && drawable.hasFocusStateSpecified()) {
                    return true;
                }
            }
            return false;
        }

        public final boolean canConstantState() {
            ChildDrawable[] childDrawableArr = this.mChildren;
            for (int i = 0; i < 3; i++) {
                Drawable drawable = childDrawableArr[i].mDrawable;
                if (drawable != null && drawable.getConstantState() == null) {
                    return false;
                }
            }
            return true;
        }

        public void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }
    }
}
