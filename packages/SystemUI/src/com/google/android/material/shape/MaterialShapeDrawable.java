package com.google.android.material.shape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.ShapePath;
import java.util.BitSet;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MaterialShapeDrawable extends Drawable implements Shapeable {
    public static final Paint clearPaint;
    public final BitSet containsIncompatibleShadowOp;
    public final ShapePath.ShadowCompatOperation[] cornerShadowOperation;
    public MaterialShapeDrawableState drawableState;
    public final ShapePath.ShadowCompatOperation[] edgeShadowOperation;
    public final Paint fillPaint;
    public final RectF insetRectF;
    public final Matrix matrix;
    public final Path path;
    public final RectF pathBounds;
    public boolean pathDirty;
    public final Path pathInsetByStroke;
    public final ShapeAppearancePathProvider pathProvider;
    public final AnonymousClass1 pathShadowListener;
    public final RectF rectF;
    public int resolvedTintColor;
    public final Region scratchRegion;
    public boolean shadowBitmapDrawingEnable;
    public final ShadowRenderer shadowRenderer;
    public final Paint strokePaint;
    public ShapeAppearanceModel strokeShapeAppearance;
    public PorterDuffColorFilter strokeTintFilter;
    public PorterDuffColorFilter tintFilter;
    public final Region transparentRegion;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.shape.MaterialShapeDrawable$1, reason: invalid class name */
    public class AnonymousClass1 implements ShapeAppearancePathProvider.PathListener {
        public AnonymousClass1() {
        }
    }

    static {
        Paint paint = new Paint(1);
        clearPaint = paint;
        paint.setColor(-1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public MaterialShapeDrawable() {
        this(new ShapeAppearanceModel());
    }

    public final void calculatePath(RectF rectF, Path path) {
        ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        shapeAppearancePathProvider.calculatePath(materialShapeDrawableState.shapeAppearanceModel, materialShapeDrawableState.interpolation, rectF, this.pathShadowListener, path);
        if (this.drawableState.scale != 1.0f) {
            this.matrix.reset();
            Matrix matrix = this.matrix;
            float f = this.drawableState.scale;
            matrix.setScale(f, f, rectF.width() / 2.0f, rectF.height() / 2.0f);
            path.transform(this.matrix);
        }
        path.computeBounds(this.pathBounds, true);
    }

    public final int compositeElevationOverlayIfNeeded(int i) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        float f = materialShapeDrawableState.elevation + 0.0f + materialShapeDrawableState.parentAbsoluteElevation;
        ElevationOverlayProvider elevationOverlayProvider = materialShapeDrawableState.elevationOverlayProvider;
        return elevationOverlayProvider != null ? elevationOverlayProvider.compositeOverlayIfNeeded(f, i) : i;
    }

    /* JADX WARN: Type inference failed for: r6v17, types: [com.google.android.material.shape.MaterialShapeDrawable$2] */
    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable;
        Canvas canvas2;
        this.fillPaint.setColorFilter(this.tintFilter);
        int alpha = this.fillPaint.getAlpha();
        Paint paint = this.fillPaint;
        int i = this.drawableState.alpha;
        paint.setAlpha(((i + (i >>> 7)) * alpha) >>> 8);
        this.strokePaint.setColorFilter(this.strokeTintFilter);
        this.strokePaint.setStrokeWidth(this.drawableState.strokeWidth);
        int alpha2 = this.strokePaint.getAlpha();
        Paint paint2 = this.strokePaint;
        int i2 = this.drawableState.alpha;
        paint2.setAlpha(((i2 + (i2 >>> 7)) * alpha2) >>> 8);
        if (this.pathDirty) {
            final float f = -(hasStroke() ? this.strokePaint.getStrokeWidth() / 2.0f : 0.0f);
            ShapeAppearanceModel shapeAppearanceModel = this.drawableState.shapeAppearanceModel;
            ?? r6 = new Object(this) { // from class: com.google.android.material.shape.MaterialShapeDrawable.2
                public final CornerSize apply(CornerSize cornerSize) {
                    return cornerSize instanceof RelativeCornerSize ? cornerSize : new AdjustedCornerSize(f, cornerSize);
                }
            };
            shapeAppearanceModel.getClass();
            ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
            builder.topLeftCornerSize = r6.apply(shapeAppearanceModel.topLeftCornerSize);
            builder.topRightCornerSize = r6.apply(shapeAppearanceModel.topRightCornerSize);
            builder.bottomLeftCornerSize = r6.apply(shapeAppearanceModel.bottomLeftCornerSize);
            builder.bottomRightCornerSize = r6.apply(shapeAppearanceModel.bottomRightCornerSize);
            ShapeAppearanceModel build = builder.build();
            this.strokeShapeAppearance = build;
            ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
            float f2 = this.drawableState.interpolation;
            this.insetRectF.set(getBoundsAsRectF$1());
            float strokeWidth = hasStroke() ? this.strokePaint.getStrokeWidth() / 2.0f : 0.0f;
            this.insetRectF.inset(strokeWidth, strokeWidth);
            shapeAppearancePathProvider.calculatePath(build, f2, this.insetRectF, null, this.pathInsetByStroke);
            calculatePath(getBoundsAsRectF$1(), this.path);
            this.pathDirty = false;
        }
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        int i3 = materialShapeDrawableState.shadowCompatMode;
        if (i3 != 1 && materialShapeDrawableState.shadowCompatRadius > 0) {
            if (i3 == 2) {
                canvas.save();
                MaterialShapeDrawableState materialShapeDrawableState2 = this.drawableState;
                canvas.translate((int) (Math.sin(Math.toRadians(materialShapeDrawableState2.shadowCompatRotation)) * materialShapeDrawableState2.shadowCompatOffset), getShadowOffsetY());
                if (this.shadowBitmapDrawingEnable) {
                    int width = (int) (this.pathBounds.width() - getBounds().width());
                    int height = (int) (this.pathBounds.height() - getBounds().height());
                    if (width < 0 || height < 0) {
                        throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
                    }
                    Bitmap createBitmap = Bitmap.createBitmap((this.drawableState.shadowCompatRadius * 2) + ((int) this.pathBounds.width()) + width, (this.drawableState.shadowCompatRadius * 2) + ((int) this.pathBounds.height()) + height, Bitmap.Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(createBitmap);
                    float f3 = (getBounds().left - this.drawableState.shadowCompatRadius) - width;
                    float f4 = (getBounds().top - this.drawableState.shadowCompatRadius) - height;
                    canvas3.translate(-f3, -f4);
                    drawCompatShadow(canvas3);
                    canvas.drawBitmap(createBitmap, f3, f4, (Paint) null);
                    createBitmap.recycle();
                    canvas.restore();
                } else {
                    drawCompatShadow(canvas);
                    canvas.restore();
                }
            } else if (!materialShapeDrawableState.shapeAppearanceModel.isRoundRect(getBoundsAsRectF$1())) {
                this.path.isConvex();
            }
        }
        MaterialShapeDrawableState materialShapeDrawableState3 = this.drawableState;
        Paint.Style style = materialShapeDrawableState3.paintStyle;
        if (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.FILL) {
            materialShapeDrawable = this;
            canvas2 = canvas;
            materialShapeDrawable.drawShape(canvas2, this.fillPaint, this.path, materialShapeDrawableState3.shapeAppearanceModel, getBoundsAsRectF$1());
        } else {
            materialShapeDrawable = this;
            canvas2 = canvas;
        }
        if (materialShapeDrawable.hasStroke()) {
            materialShapeDrawable.drawStrokeShape(canvas2);
        }
        materialShapeDrawable.fillPaint.setAlpha(alpha);
        materialShapeDrawable.strokePaint.setAlpha(alpha2);
    }

    public final void drawCompatShadow(Canvas canvas) {
        if (this.containsIncompatibleShadowOp.cardinality() > 0) {
            Log.w("MaterialShapeDrawable", "Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        if (this.drawableState.shadowCompatOffset != 0) {
            canvas.drawPath(this.path, this.shadowRenderer.shadowPaint);
        }
        for (int i = 0; i < 4; i++) {
            ShapePath.ShadowCompatOperation shadowCompatOperation = this.cornerShadowOperation[i];
            ShadowRenderer shadowRenderer = this.shadowRenderer;
            int i2 = this.drawableState.shadowCompatRadius;
            Matrix matrix = ShapePath.ShadowCompatOperation.IDENTITY_MATRIX;
            shadowCompatOperation.draw(matrix, shadowRenderer, i2, canvas);
            this.edgeShadowOperation[i].draw(matrix, this.shadowRenderer, this.drawableState.shadowCompatRadius, canvas);
        }
        if (this.shadowBitmapDrawingEnable) {
            MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
            int sin = (int) (Math.sin(Math.toRadians(materialShapeDrawableState.shadowCompatRotation)) * materialShapeDrawableState.shadowCompatOffset);
            int shadowOffsetY = getShadowOffsetY();
            canvas.translate(-sin, -shadowOffsetY);
            canvas.drawPath(this.path, clearPaint);
            canvas.translate(sin, shadowOffsetY);
        }
    }

    public final void drawShape(Canvas canvas, Paint paint, Path path, ShapeAppearanceModel shapeAppearanceModel, RectF rectF) {
        if (!shapeAppearanceModel.isRoundRect(rectF)) {
            canvas.drawPath(path, paint);
        } else {
            float cornerSize = shapeAppearanceModel.topRightCornerSize.getCornerSize(rectF) * this.drawableState.interpolation;
            canvas.drawRoundRect(rectF, cornerSize, cornerSize, paint);
        }
    }

    public void drawStrokeShape(Canvas canvas) {
        Paint paint = this.strokePaint;
        Path path = this.pathInsetByStroke;
        ShapeAppearanceModel shapeAppearanceModel = this.strokeShapeAppearance;
        this.insetRectF.set(getBoundsAsRectF$1());
        float strokeWidth = hasStroke() ? this.strokePaint.getStrokeWidth() / 2.0f : 0.0f;
        this.insetRectF.inset(strokeWidth, strokeWidth);
        drawShape(canvas, paint, path, shapeAppearanceModel, this.insetRectF);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.drawableState.alpha;
    }

    public final RectF getBoundsAsRectF$1() {
        this.rectF.set(getBounds());
        return this.rectF;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return this.drawableState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.shadowCompatMode == 2) {
            return;
        }
        if (materialShapeDrawableState.shapeAppearanceModel.isRoundRect(getBoundsAsRectF$1())) {
            outline.setRoundRect(getBounds(), getTopLeftCornerResolvedSize() * this.drawableState.interpolation);
        } else {
            calculatePath(getBoundsAsRectF$1(), this.path);
            DrawableUtils.OutlineCompatR.setPath(outline, this.path);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        Rect rect2 = this.drawableState.padding;
        if (rect2 == null) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    public final int getShadowOffsetY() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        return (int) (Math.cos(Math.toRadians(materialShapeDrawableState.shadowCompatRotation)) * materialShapeDrawableState.shadowCompatOffset);
    }

    public final float getTopLeftCornerResolvedSize() {
        return this.drawableState.shapeAppearanceModel.topLeftCornerSize.getCornerSize(getBoundsAsRectF$1());
    }

    @Override // android.graphics.drawable.Drawable
    public final Region getTransparentRegion() {
        this.transparentRegion.set(getBounds());
        calculatePath(getBoundsAsRectF$1(), this.path);
        this.scratchRegion.setPath(this.path, this.transparentRegion);
        this.transparentRegion.op(this.scratchRegion, Region.Op.DIFFERENCE);
        return this.transparentRegion;
    }

    public final boolean hasStroke() {
        Paint.Style style = this.drawableState.paintStyle;
        return (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.STROKE) && this.strokePaint.getStrokeWidth() > 0.0f;
    }

    public final void initializeElevationOverlay(Context context) {
        this.drawableState.elevationOverlayProvider = new ElevationOverlayProvider(context);
        updateZ();
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        this.pathDirty = true;
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        if (super.isStateful()) {
            return true;
        }
        ColorStateList colorStateList = this.drawableState.tintList;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        this.drawableState.getClass();
        ColorStateList colorStateList2 = this.drawableState.strokeColor;
        if (colorStateList2 != null && colorStateList2.isStateful()) {
            return true;
        }
        ColorStateList colorStateList3 = this.drawableState.fillColor;
        return colorStateList3 != null && colorStateList3.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        this.drawableState = new MaterialShapeDrawableState(this.drawableState);
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.pathDirty = true;
        super.onBoundsChange(rect);
    }

    @Override // android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public boolean onStateChange(int[] iArr) {
        boolean z = updateColorsForState(iArr) || updateTintFilter();
        if (z) {
            invalidateSelf();
        }
        return z;
    }

    public void onTextSizeChange() {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.alpha != i) {
            materialShapeDrawableState.alpha = i;
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.drawableState.getClass();
        super.invalidateSelf();
    }

    public final void setElevation(float f) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.elevation != f) {
            materialShapeDrawableState.elevation = f;
            updateZ();
        }
    }

    public final void setFillColor(ColorStateList colorStateList) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.fillColor != colorStateList) {
            materialShapeDrawableState.fillColor = colorStateList;
            onStateChange(getState());
        }
    }

    public final void setInterpolation(float f) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.interpolation != f) {
            materialShapeDrawableState.interpolation = f;
            this.pathDirty = true;
            invalidateSelf();
        }
    }

    public final void setPaintStyle() {
        this.drawableState.paintStyle = Paint.Style.FILL;
        super.invalidateSelf();
    }

    public final void setShadowColor() {
        this.shadowRenderer.setShadowColor(-12303292);
        this.drawableState.getClass();
        super.invalidateSelf();
    }

    public final void setShadowCompatRotation(int i) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.shadowCompatRotation != i) {
            materialShapeDrawableState.shadowCompatRotation = i;
            super.invalidateSelf();
        }
    }

    public final void setShadowCompatibilityMode(int i) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.shadowCompatMode != i) {
            materialShapeDrawableState.shadowCompatMode = i;
            super.invalidateSelf();
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.drawableState.shapeAppearanceModel = shapeAppearanceModel;
        invalidateSelf();
    }

    public final void setStrokeColor(ColorStateList colorStateList) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.strokeColor != colorStateList) {
            materialShapeDrawableState.strokeColor = colorStateList;
            onStateChange(getState());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.drawableState.tintList = colorStateList;
        updateTintFilter();
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.tintMode != mode) {
            materialShapeDrawableState.tintMode = mode;
            updateTintFilter();
            super.invalidateSelf();
        }
    }

    public final boolean updateColorsForState(int[] iArr) {
        boolean z;
        int color;
        int colorForState;
        int color2;
        int colorForState2;
        if (this.drawableState.fillColor == null || color2 == (colorForState2 = this.drawableState.fillColor.getColorForState(iArr, (color2 = this.fillPaint.getColor())))) {
            z = false;
        } else {
            this.fillPaint.setColor(colorForState2);
            z = true;
        }
        if (this.drawableState.strokeColor == null || color == (colorForState = this.drawableState.strokeColor.getColorForState(iArr, (color = this.strokePaint.getColor())))) {
            return z;
        }
        this.strokePaint.setColor(colorForState);
        return true;
    }

    public final boolean updateTintFilter() {
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = this.tintFilter;
        PorterDuffColorFilter porterDuffColorFilter3 = this.strokeTintFilter;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        ColorStateList colorStateList = materialShapeDrawableState.tintList;
        PorterDuff.Mode mode = materialShapeDrawableState.tintMode;
        Paint paint = this.fillPaint;
        if (colorStateList == null || mode == null) {
            int color = paint.getColor();
            int compositeElevationOverlayIfNeeded = compositeElevationOverlayIfNeeded(color);
            this.resolvedTintColor = compositeElevationOverlayIfNeeded;
            porterDuffColorFilter = compositeElevationOverlayIfNeeded != color ? new PorterDuffColorFilter(compositeElevationOverlayIfNeeded, PorterDuff.Mode.SRC_IN) : null;
        } else {
            int compositeElevationOverlayIfNeeded2 = compositeElevationOverlayIfNeeded(colorStateList.getColorForState(getState(), 0));
            this.resolvedTintColor = compositeElevationOverlayIfNeeded2;
            porterDuffColorFilter = new PorterDuffColorFilter(compositeElevationOverlayIfNeeded2, mode);
        }
        this.tintFilter = porterDuffColorFilter;
        this.drawableState.getClass();
        this.strokeTintFilter = null;
        this.drawableState.getClass();
        return (Objects.equals(porterDuffColorFilter2, this.tintFilter) && Objects.equals(porterDuffColorFilter3, this.strokeTintFilter)) ? false : true;
    }

    public final void updateZ() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        float f = materialShapeDrawableState.elevation + 0.0f;
        materialShapeDrawableState.shadowCompatRadius = (int) Math.ceil(0.75f * f);
        this.drawableState.shadowCompatOffset = (int) Math.ceil(f * 0.25f);
        updateTintFilter();
        super.invalidateSelf();
    }

    public MaterialShapeDrawable(Context context, AttributeSet attributeSet, int i, int i2) {
        this(ShapeAppearanceModel.builder(context, attributeSet, i, i2).build());
    }

    @Deprecated
    public MaterialShapeDrawable(ShapePathModel shapePathModel) {
        this((ShapeAppearanceModel) shapePathModel);
    }

    public MaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        this(new MaterialShapeDrawableState(shapeAppearanceModel, null));
    }

    public MaterialShapeDrawable(MaterialShapeDrawableState materialShapeDrawableState) {
        ShapeAppearancePathProvider shapeAppearancePathProvider;
        this.cornerShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.edgeShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.containsIncompatibleShadowOp = new BitSet(8);
        this.matrix = new Matrix();
        this.path = new Path();
        this.pathInsetByStroke = new Path();
        this.rectF = new RectF();
        this.insetRectF = new RectF();
        this.transparentRegion = new Region();
        this.scratchRegion = new Region();
        Paint paint = new Paint(1);
        this.fillPaint = paint;
        Paint paint2 = new Paint(1);
        this.strokePaint = paint2;
        this.shadowRenderer = new ShadowRenderer();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            shapeAppearancePathProvider = ShapeAppearancePathProvider.Lazy.INSTANCE;
        } else {
            shapeAppearancePathProvider = new ShapeAppearancePathProvider();
        }
        this.pathProvider = shapeAppearancePathProvider;
        this.pathBounds = new RectF();
        this.shadowBitmapDrawingEnable = true;
        this.drawableState = materialShapeDrawableState;
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        updateTintFilter();
        updateColorsForState(getState());
        this.pathShadowListener = new AnonymousClass1();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MaterialShapeDrawableState extends Drawable.ConstantState {
        public int alpha;
        public float elevation;
        public ElevationOverlayProvider elevationOverlayProvider;
        public ColorStateList fillColor;
        public float interpolation;
        public Rect padding;
        public Paint.Style paintStyle;
        public float parentAbsoluteElevation;
        public final float scale;
        public int shadowCompatMode;
        public int shadowCompatOffset;
        public int shadowCompatRadius;
        public int shadowCompatRotation;
        public ShapeAppearanceModel shapeAppearanceModel;
        public ColorStateList strokeColor;
        public float strokeWidth;
        public ColorStateList tintList;
        public PorterDuff.Mode tintMode;

        public MaterialShapeDrawableState(ShapeAppearanceModel shapeAppearanceModel, ElevationOverlayProvider elevationOverlayProvider) {
            this.fillColor = null;
            this.strokeColor = null;
            this.tintList = null;
            this.tintMode = PorterDuff.Mode.SRC_IN;
            this.padding = null;
            this.scale = 1.0f;
            this.interpolation = 1.0f;
            this.alpha = 255;
            this.parentAbsoluteElevation = 0.0f;
            this.elevation = 0.0f;
            this.shadowCompatMode = 0;
            this.shadowCompatRadius = 0;
            this.shadowCompatOffset = 0;
            this.shadowCompatRotation = 0;
            this.paintStyle = Paint.Style.FILL_AND_STROKE;
            this.shapeAppearanceModel = shapeAppearanceModel;
            this.elevationOverlayProvider = elevationOverlayProvider;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this);
            materialShapeDrawable.pathDirty = true;
            return materialShapeDrawable;
        }

        public MaterialShapeDrawableState(MaterialShapeDrawableState materialShapeDrawableState) {
            this.fillColor = null;
            this.strokeColor = null;
            this.tintList = null;
            this.tintMode = PorterDuff.Mode.SRC_IN;
            this.padding = null;
            this.scale = 1.0f;
            this.interpolation = 1.0f;
            this.alpha = 255;
            this.parentAbsoluteElevation = 0.0f;
            this.elevation = 0.0f;
            this.shadowCompatMode = 0;
            this.shadowCompatRadius = 0;
            this.shadowCompatOffset = 0;
            this.shadowCompatRotation = 0;
            this.paintStyle = Paint.Style.FILL_AND_STROKE;
            this.shapeAppearanceModel = materialShapeDrawableState.shapeAppearanceModel;
            this.elevationOverlayProvider = materialShapeDrawableState.elevationOverlayProvider;
            this.strokeWidth = materialShapeDrawableState.strokeWidth;
            this.fillColor = materialShapeDrawableState.fillColor;
            this.strokeColor = materialShapeDrawableState.strokeColor;
            this.tintMode = materialShapeDrawableState.tintMode;
            this.tintList = materialShapeDrawableState.tintList;
            this.alpha = materialShapeDrawableState.alpha;
            this.scale = materialShapeDrawableState.scale;
            this.shadowCompatOffset = materialShapeDrawableState.shadowCompatOffset;
            this.shadowCompatMode = materialShapeDrawableState.shadowCompatMode;
            this.interpolation = materialShapeDrawableState.interpolation;
            this.parentAbsoluteElevation = materialShapeDrawableState.parentAbsoluteElevation;
            this.elevation = materialShapeDrawableState.elevation;
            this.shadowCompatRadius = materialShapeDrawableState.shadowCompatRadius;
            this.shadowCompatRotation = materialShapeDrawableState.shadowCompatRotation;
            this.paintStyle = materialShapeDrawableState.paintStyle;
            if (materialShapeDrawableState.padding != null) {
                this.padding = new Rect(materialShapeDrawableState.padding);
            }
        }
    }
}
