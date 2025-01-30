package com.google.android.material.chip;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.core.graphics.drawable.WrappedDrawableApi14;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChipDrawable extends MaterialShapeDrawable implements Drawable.Callback, TextDrawableHelper.TextDrawableDelegate {
    public static final int[] DEFAULT_STATE = {R.attr.state_enabled};
    public static final ShapeDrawable closeIconRippleMask = new ShapeDrawable(new OvalShape());
    public int alpha;
    public boolean checkable;
    public Drawable checkedIcon;
    public ColorStateList checkedIconTint;
    public boolean checkedIconVisible;
    public ColorStateList chipBackgroundColor;
    public float chipCornerRadius;
    public float chipEndPadding;
    public Drawable chipIcon;
    public float chipIconSize;
    public ColorStateList chipIconTint;
    public boolean chipIconVisible;
    public float chipMinHeight;
    public final Paint chipPaint;
    public float chipStartPadding;
    public ColorStateList chipStrokeColor;
    public float chipStrokeWidth;
    public ColorStateList chipSurfaceColor;
    public Drawable closeIcon;
    public float closeIconEndPadding;
    public Drawable closeIconRipple;
    public float closeIconSize;
    public float closeIconStartPadding;
    public int[] closeIconStateSet;
    public ColorStateList closeIconTint;
    public boolean closeIconVisible;
    public ColorFilter colorFilter;
    public ColorStateList compatRippleColor;
    public final Context context;
    public boolean currentChecked;
    public int currentChipBackgroundColor;
    public int currentChipStrokeColor;
    public int currentChipSurfaceColor;
    public int currentCompatRippleColor;
    public int currentCompositeSurfaceBackgroundColor;
    public int currentTextColor;
    public int currentTint;
    public WeakReference delegate;
    public final Paint.FontMetrics fontMetrics;
    public boolean hasChipIconTint;
    public float iconEndPadding;
    public float iconStartPadding;
    public boolean isSeslFullText;
    public boolean isShapeThemingEnabled;
    public int maxWidth;
    public final PointF pointF;
    public final RectF rectF;
    public ColorStateList rippleColor;
    public float seslFinalWidth;
    public final Path shapePath;
    public boolean shouldDrawText;
    public CharSequence text;
    public final TextDrawableHelper textDrawableHelper;
    public float textEndPadding;
    public float textStartPadding;
    public ColorStateList tint;
    public PorterDuffColorFilter tintFilter;
    public PorterDuff.Mode tintMode;
    public TextUtils.TruncateAt truncateAt;
    public boolean useCompatRipple;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Delegate {
    }

    private ChipDrawable(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.chipCornerRadius = -1.0f;
        this.chipPaint = new Paint(1);
        this.fontMetrics = new Paint.FontMetrics();
        this.rectF = new RectF();
        this.pointF = new PointF();
        this.shapePath = new Path();
        this.alpha = 255;
        this.tintMode = PorterDuff.Mode.SRC_IN;
        this.delegate = new WeakReference(null);
        initializeElevationOverlay(context);
        this.context = context;
        TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper;
        this.text = "";
        textDrawableHelper.textPaint.density = context.getResources().getDisplayMetrics().density;
        int[] iArr = DEFAULT_STATE;
        setState(iArr);
        if (!Arrays.equals(this.closeIconStateSet, iArr)) {
            this.closeIconStateSet = iArr;
            if (showsCloseIcon()) {
                onStateChange(getState(), iArr);
            }
        }
        this.shouldDrawText = true;
        closeIconRippleMask.setTint(-1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ChipDrawable createFromAttributes(Context context, AttributeSet attributeSet, int i) {
        Drawable drawable;
        Drawable drawable2;
        ColorStateList colorStateList;
        int resourceId;
        ChipDrawable chipDrawable = new ChipDrawable(context, attributeSet, i, 2132019098);
        boolean z = false;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(chipDrawable.context, attributeSet, R$styleable.Chip, i, 2132019098, new int[0]);
        chipDrawable.isShapeThemingEnabled = obtainStyledAttributes.hasValue(37);
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(chipDrawable.context, obtainStyledAttributes, 24);
        if (chipDrawable.chipSurfaceColor != colorStateList2) {
            chipDrawable.chipSurfaceColor = colorStateList2;
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(chipDrawable.context, obtainStyledAttributes, 11);
        if (chipDrawable.chipBackgroundColor != colorStateList3) {
            chipDrawable.chipBackgroundColor = colorStateList3;
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        float dimension = obtainStyledAttributes.getDimension(19, 0.0f);
        if (chipDrawable.chipMinHeight != dimension) {
            chipDrawable.chipMinHeight = dimension;
            chipDrawable.invalidateSelf();
            chipDrawable.onSizeChange();
        }
        if (obtainStyledAttributes.hasValue(12)) {
            float dimension2 = obtainStyledAttributes.getDimension(12, 0.0f);
            if (chipDrawable.chipCornerRadius != dimension2) {
                chipDrawable.chipCornerRadius = dimension2;
                chipDrawable.setShapeAppearanceModel(chipDrawable.drawableState.shapeAppearanceModel.withCornerSize(dimension2));
            }
        }
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(chipDrawable.context, obtainStyledAttributes, 22);
        if (chipDrawable.chipStrokeColor != colorStateList4) {
            chipDrawable.chipStrokeColor = colorStateList4;
            if (chipDrawable.isShapeThemingEnabled) {
                chipDrawable.setStrokeColor(colorStateList4);
            }
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        float dimension3 = obtainStyledAttributes.getDimension(23, 0.0f);
        if (chipDrawable.chipStrokeWidth != dimension3) {
            chipDrawable.chipStrokeWidth = dimension3;
            chipDrawable.chipPaint.setStrokeWidth(dimension3);
            if (chipDrawable.isShapeThemingEnabled) {
                chipDrawable.drawableState.strokeWidth = dimension3;
                chipDrawable.invalidateSelf();
            }
            chipDrawable.invalidateSelf();
        }
        ColorStateList colorStateList5 = MaterialResources.getColorStateList(chipDrawable.context, obtainStyledAttributes, 36);
        if (chipDrawable.rippleColor != colorStateList5) {
            chipDrawable.rippleColor = colorStateList5;
            chipDrawable.compatRippleColor = chipDrawable.useCompatRipple ? RippleUtils.sanitizeRippleDrawableColor(colorStateList5) : null;
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        chipDrawable.setText(obtainStyledAttributes.getText(5));
        TextAppearance textAppearance = (!obtainStyledAttributes.hasValue(0) || (resourceId = obtainStyledAttributes.getResourceId(0, 0)) == 0) ? null : new TextAppearance(chipDrawable.context, resourceId);
        textAppearance.textSize = obtainStyledAttributes.getDimension(1, textAppearance.textSize);
        chipDrawable.textDrawableHelper.setTextAppearance(textAppearance, chipDrawable.context);
        int i2 = obtainStyledAttributes.getInt(3, 0);
        if (i2 == 1) {
            chipDrawable.truncateAt = TextUtils.TruncateAt.START;
        } else if (i2 == 2) {
            chipDrawable.truncateAt = TextUtils.TruncateAt.MIDDLE;
        } else if (i2 == 3) {
            chipDrawable.truncateAt = TextUtils.TruncateAt.END;
        }
        chipDrawable.setChipIconVisible(obtainStyledAttributes.getBoolean(18, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconVisible") == null) {
            chipDrawable.setChipIconVisible(obtainStyledAttributes.getBoolean(15, false));
        }
        Drawable drawable3 = MaterialResources.getDrawable(chipDrawable.context, obtainStyledAttributes, 14);
        Drawable drawable4 = chipDrawable.chipIcon;
        if (drawable4 != 0) {
            boolean z2 = drawable4 instanceof WrappedDrawable;
            drawable = drawable4;
            if (z2) {
                drawable = ((WrappedDrawableApi14) ((WrappedDrawable) drawable4)).mDrawable;
            }
        } else {
            drawable = null;
        }
        if (drawable != drawable3) {
            float calculateChipIconWidth = chipDrawable.calculateChipIconWidth();
            chipDrawable.chipIcon = drawable3 != null ? drawable3.mutate() : null;
            float calculateChipIconWidth2 = chipDrawable.calculateChipIconWidth();
            unapplyChildDrawable(drawable);
            if (chipDrawable.showsChipIcon()) {
                chipDrawable.applyChildDrawable(chipDrawable.chipIcon);
            }
            chipDrawable.invalidateSelf();
            if (calculateChipIconWidth != calculateChipIconWidth2) {
                chipDrawable.onSizeChange();
            }
        }
        if (obtainStyledAttributes.hasValue(17)) {
            ColorStateList colorStateList6 = MaterialResources.getColorStateList(chipDrawable.context, obtainStyledAttributes, 17);
            chipDrawable.hasChipIconTint = true;
            if (chipDrawable.chipIconTint != colorStateList6) {
                chipDrawable.chipIconTint = colorStateList6;
                if (chipDrawable.showsChipIcon()) {
                    chipDrawable.chipIcon.setTintList(colorStateList6);
                }
                chipDrawable.onStateChange(chipDrawable.getState());
            }
        }
        float dimension4 = obtainStyledAttributes.getDimension(16, -1.0f);
        if (chipDrawable.chipIconSize != dimension4) {
            float calculateChipIconWidth3 = chipDrawable.calculateChipIconWidth();
            chipDrawable.chipIconSize = dimension4;
            float calculateChipIconWidth4 = chipDrawable.calculateChipIconWidth();
            chipDrawable.invalidateSelf();
            if (calculateChipIconWidth3 != calculateChipIconWidth4) {
                chipDrawable.onSizeChange();
            }
        }
        chipDrawable.setCloseIconVisible(obtainStyledAttributes.getBoolean(31, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconVisible") == null) {
            chipDrawable.setCloseIconVisible(obtainStyledAttributes.getBoolean(26, false));
        }
        Drawable drawable5 = MaterialResources.getDrawable(chipDrawable.context, obtainStyledAttributes, 25);
        Drawable drawable6 = chipDrawable.closeIcon;
        if (drawable6 != 0) {
            boolean z3 = drawable6 instanceof WrappedDrawable;
            drawable2 = drawable6;
            if (z3) {
                drawable2 = ((WrappedDrawableApi14) ((WrappedDrawable) drawable6)).mDrawable;
            }
        } else {
            drawable2 = null;
        }
        if (drawable2 != drawable5) {
            float calculateCloseIconWidth = chipDrawable.calculateCloseIconWidth();
            chipDrawable.closeIcon = drawable5 != null ? drawable5.mutate() : null;
            chipDrawable.closeIconRipple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(chipDrawable.rippleColor), chipDrawable.closeIcon, closeIconRippleMask);
            float calculateCloseIconWidth2 = chipDrawable.calculateCloseIconWidth();
            unapplyChildDrawable(drawable2);
            if (chipDrawable.showsCloseIcon()) {
                chipDrawable.applyChildDrawable(chipDrawable.closeIcon);
            }
            chipDrawable.invalidateSelf();
            if (calculateCloseIconWidth != calculateCloseIconWidth2) {
                chipDrawable.onSizeChange();
            }
        }
        ColorStateList colorStateList7 = MaterialResources.getColorStateList(chipDrawable.context, obtainStyledAttributes, 30);
        if (chipDrawable.closeIconTint != colorStateList7) {
            chipDrawable.closeIconTint = colorStateList7;
            if (chipDrawable.showsCloseIcon()) {
                chipDrawable.closeIcon.setTintList(colorStateList7);
            }
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        float dimension5 = obtainStyledAttributes.getDimension(28, 0.0f);
        if (chipDrawable.closeIconSize != dimension5) {
            chipDrawable.closeIconSize = dimension5;
            chipDrawable.invalidateSelf();
            if (chipDrawable.showsCloseIcon()) {
                chipDrawable.onSizeChange();
            }
        }
        boolean z4 = obtainStyledAttributes.getBoolean(6, false);
        if (chipDrawable.checkable != z4) {
            chipDrawable.checkable = z4;
            float calculateChipIconWidth5 = chipDrawable.calculateChipIconWidth();
            if (!z4 && chipDrawable.currentChecked) {
                chipDrawable.currentChecked = false;
            }
            float calculateChipIconWidth6 = chipDrawable.calculateChipIconWidth();
            chipDrawable.invalidateSelf();
            if (calculateChipIconWidth5 != calculateChipIconWidth6) {
                chipDrawable.onSizeChange();
            }
        }
        chipDrawable.setCheckedIconVisible(obtainStyledAttributes.getBoolean(10, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconVisible") == null) {
            chipDrawable.setCheckedIconVisible(obtainStyledAttributes.getBoolean(8, false));
        }
        Drawable drawable7 = MaterialResources.getDrawable(chipDrawable.context, obtainStyledAttributes, 7);
        if (chipDrawable.checkedIcon != drawable7) {
            float calculateChipIconWidth7 = chipDrawable.calculateChipIconWidth();
            chipDrawable.checkedIcon = drawable7;
            float calculateChipIconWidth8 = chipDrawable.calculateChipIconWidth();
            unapplyChildDrawable(chipDrawable.checkedIcon);
            chipDrawable.applyChildDrawable(chipDrawable.checkedIcon);
            chipDrawable.invalidateSelf();
            if (calculateChipIconWidth7 != calculateChipIconWidth8) {
                chipDrawable.onSizeChange();
            }
        }
        if (obtainStyledAttributes.hasValue(9) && chipDrawable.checkedIconTint != (colorStateList = MaterialResources.getColorStateList(chipDrawable.context, obtainStyledAttributes, 9))) {
            chipDrawable.checkedIconTint = colorStateList;
            if (chipDrawable.checkedIconVisible && chipDrawable.checkedIcon != null && chipDrawable.checkable) {
                z = true;
            }
            if (z) {
                chipDrawable.checkedIcon.setTintList(colorStateList);
            }
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        MotionSpec.createFromAttribute(chipDrawable.context, obtainStyledAttributes, 39);
        MotionSpec.createFromAttribute(chipDrawable.context, obtainStyledAttributes, 33);
        float dimension6 = obtainStyledAttributes.getDimension(21, 0.0f);
        if (chipDrawable.chipStartPadding != dimension6) {
            chipDrawable.chipStartPadding = dimension6;
            chipDrawable.invalidateSelf();
            chipDrawable.onSizeChange();
        }
        float dimension7 = obtainStyledAttributes.getDimension(35, 0.0f);
        if (chipDrawable.iconStartPadding != dimension7) {
            float calculateChipIconWidth9 = chipDrawable.calculateChipIconWidth();
            chipDrawable.iconStartPadding = dimension7;
            float calculateChipIconWidth10 = chipDrawable.calculateChipIconWidth();
            chipDrawable.invalidateSelf();
            if (calculateChipIconWidth9 != calculateChipIconWidth10) {
                chipDrawable.onSizeChange();
            }
        }
        float dimension8 = obtainStyledAttributes.getDimension(34, 0.0f);
        if (chipDrawable.iconEndPadding != dimension8) {
            float calculateChipIconWidth11 = chipDrawable.calculateChipIconWidth();
            chipDrawable.iconEndPadding = dimension8;
            float calculateChipIconWidth12 = chipDrawable.calculateChipIconWidth();
            chipDrawable.invalidateSelf();
            if (calculateChipIconWidth11 != calculateChipIconWidth12) {
                chipDrawable.onSizeChange();
            }
        }
        float dimension9 = obtainStyledAttributes.getDimension(41, 0.0f);
        if (chipDrawable.textStartPadding != dimension9) {
            chipDrawable.textStartPadding = dimension9;
            chipDrawable.invalidateSelf();
            chipDrawable.onSizeChange();
        }
        float dimension10 = obtainStyledAttributes.getDimension(40, 0.0f);
        if (chipDrawable.textEndPadding != dimension10) {
            chipDrawable.textEndPadding = dimension10;
            chipDrawable.invalidateSelf();
            chipDrawable.onSizeChange();
        }
        float dimension11 = obtainStyledAttributes.getDimension(29, 0.0f);
        if (chipDrawable.closeIconStartPadding != dimension11) {
            chipDrawable.closeIconStartPadding = dimension11;
            chipDrawable.invalidateSelf();
            if (chipDrawable.showsCloseIcon()) {
                chipDrawable.onSizeChange();
            }
        }
        float dimension12 = obtainStyledAttributes.getDimension(27, 0.0f);
        if (chipDrawable.closeIconEndPadding != dimension12) {
            chipDrawable.closeIconEndPadding = dimension12;
            chipDrawable.invalidateSelf();
            if (chipDrawable.showsCloseIcon()) {
                chipDrawable.onSizeChange();
            }
        }
        float dimension13 = obtainStyledAttributes.getDimension(13, 0.0f);
        if (chipDrawable.chipEndPadding != dimension13) {
            chipDrawable.chipEndPadding = dimension13;
            chipDrawable.invalidateSelf();
            chipDrawable.onSizeChange();
        }
        chipDrawable.maxWidth = obtainStyledAttributes.getDimensionPixelSize(4, Integer.MAX_VALUE);
        obtainStyledAttributes.recycle();
        return chipDrawable;
    }

    public static void unapplyChildDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    public final void applyChildDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.setCallback(this);
        drawable.setLayoutDirection(getLayoutDirection());
        drawable.setLevel(getLevel());
        drawable.setVisible(isVisible(), false);
        if (drawable == this.closeIcon) {
            if (drawable.isStateful()) {
                drawable.setState(this.closeIconStateSet);
            }
            drawable.setTintList(this.closeIconTint);
            return;
        }
        Drawable drawable2 = this.chipIcon;
        if (drawable == drawable2 && this.hasChipIconTint) {
            drawable2.setTintList(this.chipIconTint);
        }
        if (drawable.isStateful()) {
            drawable.setState(getState());
        }
    }

    public final void calculateChipIconBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (showsChipIcon() || showsCheckedIcon()) {
            float f = this.chipStartPadding + this.iconStartPadding;
            Drawable drawable = this.currentChecked ? this.checkedIcon : this.chipIcon;
            float f2 = this.chipIconSize;
            if (f2 <= 0.0f && drawable != null) {
                f2 = drawable.getIntrinsicWidth();
            }
            if (getLayoutDirection() == 0) {
                float f3 = rect.left + f;
                rectF.left = f3;
                rectF.right = f3 + f2;
            } else {
                float f4 = rect.right - f;
                rectF.right = f4;
                rectF.left = f4 - f2;
            }
            Drawable drawable2 = this.currentChecked ? this.checkedIcon : this.chipIcon;
            float f5 = this.chipIconSize;
            if (f5 <= 0.0f && drawable2 != null) {
                float ceil = (float) Math.ceil(ViewUtils.dpToPx(24, this.context));
                if (drawable2.getIntrinsicHeight() <= ceil) {
                    ceil = drawable2.getIntrinsicHeight();
                }
                f5 = ceil;
            }
            float exactCenterY = rect.exactCenterY() - (f5 / 2.0f);
            rectF.top = exactCenterY;
            rectF.bottom = exactCenterY + f5;
        }
    }

    public final float calculateChipIconWidth() {
        if (!showsChipIcon() && !showsCheckedIcon()) {
            return 0.0f;
        }
        float f = this.iconStartPadding;
        Drawable drawable = this.currentChecked ? this.checkedIcon : this.chipIcon;
        float f2 = this.chipIconSize;
        if (f2 <= 0.0f && drawable != null) {
            f2 = drawable.getIntrinsicWidth();
        }
        return f2 + f + this.iconEndPadding;
    }

    public final float calculateCloseIconWidth() {
        if (showsCloseIcon()) {
            return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
        }
        return 0.0f;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Rect bounds = getBounds();
        if (bounds.isEmpty() || (i = this.alpha) == 0) {
            return;
        }
        int saveLayerAlpha = i < 255 ? canvas.saveLayerAlpha(bounds.left, bounds.top, bounds.right, bounds.bottom, i) : 0;
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipSurfaceColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            this.rectF.set(bounds);
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipBackgroundColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            Paint paint = this.chipPaint;
            ColorFilter colorFilter = this.colorFilter;
            if (colorFilter == null) {
                colorFilter = this.tintFilter;
            }
            paint.setColorFilter(colorFilter);
            this.rectF.set(bounds);
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
        if (this.isShapeThemingEnabled) {
            super.draw(canvas);
        }
        float f = 0.0f;
        if (this.chipStrokeWidth > 0.0f && !this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipStrokeColor);
            this.chipPaint.setStyle(Paint.Style.STROKE);
            if (!this.isShapeThemingEnabled) {
                Paint paint2 = this.chipPaint;
                ColorFilter colorFilter2 = this.colorFilter;
                if (colorFilter2 == null) {
                    colorFilter2 = this.tintFilter;
                }
                paint2.setColorFilter(colorFilter2);
            }
            RectF rectF = this.rectF;
            float f2 = bounds.left;
            float f3 = this.chipStrokeWidth / 2.0f;
            rectF.set(f2 + f3, bounds.top + f3, bounds.right - f3, bounds.bottom - f3);
            float f4 = this.chipCornerRadius - (this.chipStrokeWidth / 2.0f);
            canvas.drawRoundRect(this.rectF, f4, f4, this.chipPaint);
        }
        this.chipPaint.setColor(this.currentCompatRippleColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.rectF.set(bounds);
        if (this.isShapeThemingEnabled) {
            RectF rectF2 = new RectF(bounds);
            Path path = this.shapePath;
            ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
            MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
            shapeAppearancePathProvider.calculatePath(materialShapeDrawableState.shapeAppearanceModel, materialShapeDrawableState.interpolation, rectF2, this.pathShadowListener, path);
            drawShape(canvas, this.chipPaint, this.shapePath, this.drawableState.shapeAppearanceModel, getBoundsAsRectF());
        } else {
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
        if (showsChipIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            RectF rectF3 = this.rectF;
            float f5 = rectF3.left;
            float f6 = rectF3.top;
            canvas.translate(f5, f6);
            this.chipIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.chipIcon.draw(canvas);
            canvas.translate(-f5, -f6);
        }
        if (showsCheckedIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            RectF rectF4 = this.rectF;
            float f7 = rectF4.left;
            float f8 = rectF4.top;
            canvas.translate(f7, f8);
            this.checkedIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.checkedIcon.draw(canvas);
            canvas.translate(-f7, -f8);
        }
        if (!this.shouldDrawText || this.text == null) {
            i2 = saveLayerAlpha;
            i3 = 0;
            i4 = 255;
        } else {
            PointF pointF = this.pointF;
            pointF.set(0.0f, 0.0f);
            Paint.Align align = Paint.Align.LEFT;
            if (this.text != null) {
                float calculateChipIconWidth = this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
                if (getLayoutDirection() == 0) {
                    pointF.x = bounds.left + calculateChipIconWidth;
                    align = Paint.Align.LEFT;
                } else {
                    pointF.x = bounds.right - calculateChipIconWidth;
                    align = Paint.Align.RIGHT;
                }
                float centerY = bounds.centerY();
                this.textDrawableHelper.textPaint.getFontMetrics(this.fontMetrics);
                Paint.FontMetrics fontMetrics = this.fontMetrics;
                pointF.y = centerY - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f);
            }
            RectF rectF5 = this.rectF;
            rectF5.setEmpty();
            if (this.text != null) {
                float calculateChipIconWidth2 = calculateChipIconWidth();
                float calculateCloseIconWidth = calculateCloseIconWidth();
                float f9 = this.chipStartPadding + calculateChipIconWidth2 + this.textStartPadding;
                float f10 = this.chipEndPadding + calculateCloseIconWidth + this.textEndPadding;
                if (this.isSeslFullText) {
                    f10 -= calculateCloseIconWidth;
                    if (this.seslFinalWidth > 0.0f) {
                        Rect bounds2 = getBounds();
                        float f11 = calculateCloseIconWidth - (this.seslFinalWidth - (bounds2.right - bounds2.left));
                        if (this.closeIconVisible && f11 > 0.0f) {
                            f = f11;
                        }
                        f10 += f;
                    }
                }
                if (getLayoutDirection() == 0) {
                    rectF5.left = bounds.left + f9;
                    rectF5.right = bounds.right - f10;
                } else {
                    rectF5.left = bounds.left + f10;
                    rectF5.right = bounds.right - f9;
                }
                rectF5.top = bounds.top;
                rectF5.bottom = bounds.bottom;
            }
            TextDrawableHelper textDrawableHelper = this.textDrawableHelper;
            if (textDrawableHelper.textAppearance != null) {
                textDrawableHelper.textPaint.drawableState = getState();
                TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
                textDrawableHelper2.textAppearance.updateDrawState(this.context, textDrawableHelper2.textPaint, textDrawableHelper2.fontCallback);
            }
            this.textDrawableHelper.textPaint.setTextAlign(align);
            boolean z = Math.round(this.textDrawableHelper.getTextWidth(this.text.toString())) > Math.round(this.rectF.width());
            if (z) {
                i5 = canvas.save();
                canvas.clipRect(this.rectF);
            } else {
                i5 = 0;
            }
            CharSequence charSequence = this.text;
            if (z && this.truncateAt != null) {
                charSequence = TextUtils.ellipsize(charSequence, this.textDrawableHelper.textPaint, this.rectF.width(), this.truncateAt);
            }
            CharSequence charSequence2 = charSequence;
            int length = charSequence2.length();
            PointF pointF2 = this.pointF;
            i2 = saveLayerAlpha;
            i3 = 0;
            i4 = 255;
            canvas.drawText(charSequence2, 0, length, pointF2.x, pointF2.y, this.textDrawableHelper.textPaint);
            if (z) {
                canvas.restoreToCount(i5);
            }
        }
        if (showsCloseIcon()) {
            RectF rectF6 = this.rectF;
            rectF6.setEmpty();
            if (showsCloseIcon()) {
                float f12 = this.chipEndPadding + this.closeIconEndPadding;
                if (this.isSeslFullText) {
                    Rect bounds3 = getBounds();
                    f12 -= this.seslFinalWidth - (bounds3.right - bounds3.left);
                }
                if (getLayoutDirection() == 0) {
                    float f13 = bounds.right - f12;
                    rectF6.right = f13;
                    rectF6.left = f13 - this.closeIconSize;
                } else {
                    float f14 = bounds.left + f12;
                    rectF6.left = f14;
                    rectF6.right = f14 + this.closeIconSize;
                }
                float exactCenterY = bounds.exactCenterY();
                float f15 = this.closeIconSize;
                float f16 = exactCenterY - (f15 / 2.0f);
                rectF6.top = f16;
                rectF6.bottom = f16 + f15;
            }
            RectF rectF7 = this.rectF;
            float f17 = rectF7.left;
            float f18 = rectF7.top;
            canvas.translate(f17, f18);
            this.closeIcon.setBounds(i3, i3, (int) this.rectF.width(), (int) this.rectF.height());
            this.closeIconRipple.setBounds(this.closeIcon.getBounds());
            this.closeIconRipple.jumpToCurrentState();
            this.closeIconRipple.draw(canvas);
            canvas.translate(-f17, -f18);
        }
        if (this.alpha < i4) {
            canvas.restoreToCount(i2);
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.alpha;
    }

    public final float getChipCornerRadius() {
        return this.isShapeThemingEnabled ? getTopLeftCornerResolvedSize() : this.chipCornerRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.colorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (int) this.chipMinHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return Math.min(Math.round(calculateCloseIconWidth() + this.textDrawableHelper.getTextWidth(this.text.toString()) + calculateChipIconWidth() + this.chipStartPadding + this.textStartPadding + this.textEndPadding + this.chipEndPadding), this.maxWidth);
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        if (this.isShapeThemingEnabled) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            outline.setRoundRect(0, 0, getIntrinsicWidth(), (int) this.chipMinHeight, this.chipCornerRadius);
        } else {
            outline.setRoundRect(bounds, this.chipCornerRadius);
        }
        outline.setAlpha(this.alpha / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList;
        if (isStateful(this.chipSurfaceColor) || isStateful(this.chipBackgroundColor) || isStateful(this.chipStrokeColor)) {
            return true;
        }
        if (this.useCompatRipple && isStateful(this.compatRippleColor)) {
            return true;
        }
        TextAppearance textAppearance = this.textDrawableHelper.textAppearance;
        if ((textAppearance == null || (colorStateList = textAppearance.textColor) == null || !colorStateList.isStateful()) ? false : true) {
            return true;
        }
        return (this.checkedIconVisible && this.checkedIcon != null && this.checkable) || isStateful(this.chipIcon) || isStateful(this.checkedIcon) || isStateful(this.tint);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i) {
        boolean onLayoutDirectionChanged = super.onLayoutDirectionChanged(i);
        if (showsChipIcon()) {
            onLayoutDirectionChanged |= this.chipIcon.setLayoutDirection(i);
        }
        if (showsCheckedIcon()) {
            onLayoutDirectionChanged |= this.checkedIcon.setLayoutDirection(i);
        }
        if (showsCloseIcon()) {
            onLayoutDirectionChanged |= this.closeIcon.setLayoutDirection(i);
        }
        if (!onLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        boolean onLevelChange = super.onLevelChange(i);
        if (showsChipIcon()) {
            onLevelChange |= this.chipIcon.setLevel(i);
        }
        if (showsCheckedIcon()) {
            onLevelChange |= this.checkedIcon.setLevel(i);
        }
        if (showsCloseIcon()) {
            onLevelChange |= this.closeIcon.setLevel(i);
        }
        if (onLevelChange) {
            invalidateSelf();
        }
        return onLevelChange;
    }

    public final void onSizeChange() {
        Delegate delegate = (Delegate) this.delegate.get();
        if (delegate != null) {
            Chip chip = (Chip) delegate;
            chip.ensureAccessibleTouchTarget(chip.minTouchTargetSize);
            chip.requestLayout();
            chip.invalidateOutline();
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final boolean onStateChange(int[] iArr) {
        if (this.isShapeThemingEnabled) {
            super.onStateChange(iArr);
        }
        return onStateChange(iArr, this.closeIconStateSet);
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final void onTextSizeChange() {
        onSizeChange();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (this.alpha != i) {
            this.alpha = i;
            invalidateSelf();
        }
    }

    public final void setCheckedIconVisible(boolean z) {
        if (this.checkedIconVisible != z) {
            boolean showsCheckedIcon = showsCheckedIcon();
            this.checkedIconVisible = z;
            boolean showsCheckedIcon2 = showsCheckedIcon();
            if (showsCheckedIcon != showsCheckedIcon2) {
                if (showsCheckedIcon2) {
                    applyChildDrawable(this.checkedIcon);
                } else {
                    unapplyChildDrawable(this.checkedIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    public final void setChipIconVisible(boolean z) {
        if (this.chipIconVisible != z) {
            boolean showsChipIcon = showsChipIcon();
            this.chipIconVisible = z;
            boolean showsChipIcon2 = showsChipIcon();
            if (showsChipIcon != showsChipIcon2) {
                if (showsChipIcon2) {
                    applyChildDrawable(this.chipIcon);
                } else {
                    unapplyChildDrawable(this.chipIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    public final void setCloseIconVisible(boolean z) {
        if (this.closeIconVisible != z) {
            boolean showsCloseIcon = showsCloseIcon();
            this.closeIconVisible = z;
            boolean showsCloseIcon2 = showsCloseIcon();
            if (showsCloseIcon != showsCloseIcon2) {
                if (showsCloseIcon2) {
                    applyChildDrawable(this.closeIcon);
                } else {
                    unapplyChildDrawable(this.closeIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        if (this.colorFilter != colorFilter) {
            this.colorFilter = colorFilter;
            invalidateSelf();
        }
    }

    public final void setText(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "";
        }
        if (TextUtils.equals(this.text, charSequence)) {
            return;
        }
        this.text = charSequence;
        this.textDrawableHelper.textWidthDirty = true;
        invalidateSelf();
        onSizeChange();
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        if (this.tint != colorStateList) {
            this.tint = colorStateList;
            onStateChange(getState());
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        if (this.tintMode != mode) {
            this.tintMode = mode;
            ColorStateList colorStateList = this.tint;
            this.tintFilter = (colorStateList == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (showsChipIcon()) {
            visible |= this.chipIcon.setVisible(z, z2);
        }
        if (showsCheckedIcon()) {
            visible |= this.checkedIcon.setVisible(z, z2);
        }
        if (showsCloseIcon()) {
            visible |= this.closeIcon.setVisible(z, z2);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    public final boolean showsCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.currentChecked;
    }

    public final boolean showsChipIcon() {
        return this.chipIconVisible && this.chipIcon != null;
    }

    public final boolean showsCloseIcon() {
        return this.closeIconVisible && this.closeIcon != null;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final boolean onStateChange(int[] iArr, int[] iArr2) {
        boolean z;
        boolean z2;
        ColorStateList colorStateList;
        boolean onStateChange = super.onStateChange(iArr);
        ColorStateList colorStateList2 = this.chipSurfaceColor;
        int compositeElevationOverlayIfNeeded = compositeElevationOverlayIfNeeded(colorStateList2 != null ? colorStateList2.getColorForState(iArr, this.currentChipSurfaceColor) : 0);
        boolean z3 = true;
        if (this.currentChipSurfaceColor != compositeElevationOverlayIfNeeded) {
            this.currentChipSurfaceColor = compositeElevationOverlayIfNeeded;
            onStateChange = true;
        }
        ColorStateList colorStateList3 = this.chipBackgroundColor;
        int compositeElevationOverlayIfNeeded2 = compositeElevationOverlayIfNeeded(colorStateList3 != null ? colorStateList3.getColorForState(iArr, this.currentChipBackgroundColor) : 0);
        if (this.currentChipBackgroundColor != compositeElevationOverlayIfNeeded2) {
            this.currentChipBackgroundColor = compositeElevationOverlayIfNeeded2;
            onStateChange = true;
        }
        int compositeColors = ColorUtils.compositeColors(compositeElevationOverlayIfNeeded2, compositeElevationOverlayIfNeeded);
        if ((this.currentCompositeSurfaceBackgroundColor != compositeColors) | (this.drawableState.fillColor == null)) {
            this.currentCompositeSurfaceBackgroundColor = compositeColors;
            setFillColor(ColorStateList.valueOf(compositeColors));
            onStateChange = true;
        }
        ColorStateList colorStateList4 = this.chipStrokeColor;
        int colorForState = colorStateList4 != null ? colorStateList4.getColorForState(iArr, this.currentChipStrokeColor) : 0;
        if (this.currentChipStrokeColor != colorForState) {
            this.currentChipStrokeColor = colorForState;
            onStateChange = true;
        }
        int colorForState2 = (this.compatRippleColor == null || !RippleUtils.shouldDrawRippleCompat(iArr)) ? 0 : this.compatRippleColor.getColorForState(iArr, this.currentCompatRippleColor);
        if (this.currentCompatRippleColor != colorForState2) {
            this.currentCompatRippleColor = colorForState2;
            if (this.useCompatRipple) {
                onStateChange = true;
            }
        }
        TextAppearance textAppearance = this.textDrawableHelper.textAppearance;
        int colorForState3 = (textAppearance == null || (colorStateList = textAppearance.textColor) == null) ? 0 : colorStateList.getColorForState(iArr, this.currentTextColor);
        if (this.currentTextColor != colorForState3) {
            this.currentTextColor = colorForState3;
            onStateChange = true;
        }
        int[] state = getState();
        if (state != null) {
            for (int i : state) {
                if (i == 16842912) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        boolean z4 = z && this.checkable;
        if (this.currentChecked == z4 || this.checkedIcon == null) {
            z2 = false;
        } else {
            float calculateChipIconWidth = calculateChipIconWidth();
            this.currentChecked = z4;
            if (calculateChipIconWidth != calculateChipIconWidth()) {
                onStateChange = true;
                z2 = true;
            } else {
                z2 = false;
                onStateChange = true;
            }
        }
        ColorStateList colorStateList5 = this.tint;
        int colorForState4 = colorStateList5 != null ? colorStateList5.getColorForState(iArr, this.currentTint) : 0;
        if (this.currentTint != colorForState4) {
            this.currentTint = colorForState4;
            ColorStateList colorStateList6 = this.tint;
            PorterDuff.Mode mode = this.tintMode;
            this.tintFilter = (colorStateList6 == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList6.getColorForState(getState(), 0), mode);
        } else {
            z3 = onStateChange;
        }
        if (isStateful(this.chipIcon)) {
            z3 |= this.chipIcon.setState(iArr);
        }
        if (isStateful(this.checkedIcon)) {
            z3 |= this.checkedIcon.setState(iArr);
        }
        if (isStateful(this.closeIcon)) {
            int[] iArr3 = new int[iArr.length + iArr2.length];
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
            z3 |= this.closeIcon.setState(iArr3);
        }
        if (isStateful(this.closeIconRipple)) {
            z3 |= this.closeIconRipple.setState(iArr2);
        }
        if (z3) {
            invalidateSelf();
        }
        if (z2) {
            onSizeChange();
        }
        return z3;
    }

    public static boolean isStateful(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    public static boolean isStateful(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }
}
