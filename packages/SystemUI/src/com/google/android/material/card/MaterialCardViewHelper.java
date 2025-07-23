package com.google.android.material.card;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.cardview.R$styleable;
import androidx.cardview.widget.CardView;
import androidx.cardview.widget.CardViewApi21Impl;
import androidx.cardview.widget.RoundRectDrawable;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MaterialCardViewHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    public final MaterialShapeDrawable bgDrawable;
    public boolean checkable;
    public Drawable checkedIcon;
    public int checkedIconGravity;
    public int checkedIconMargin;
    public int checkedIconSize;
    public ColorStateList checkedIconTint;
    public LayerDrawable clickableForegroundDrawable;
    public Drawable fgDrawable;
    public final MaterialShapeDrawable foregroundContentDrawable;
    public MaterialShapeDrawable foregroundShapeDrawable;
    public ValueAnimator iconAnimator;
    public final TimeInterpolator iconFadeAnimInterpolator;
    public final int iconFadeInAnimDuration;
    public final int iconFadeOutAnimDuration;
    public final MaterialCardView materialCardView;
    public ColorStateList rippleColor;
    public Drawable rippleDrawable;
    public ShapeAppearanceModel shapeAppearanceModel;
    public ColorStateList strokeColor;
    public int strokeWidth;
    public final Rect userContentPadding = new Rect();
    public boolean isBackgroundOverwritten = false;
    public float checkedAnimationProgress = 0.0f;

    public MaterialCardViewHelper(MaterialCardView materialCardView, AttributeSet attributeSet, int i, int i2) {
        this.materialCardView = materialCardView;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(materialCardView.getContext(), attributeSet, i, i2);
        this.bgDrawable = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(materialCardView.getContext());
        materialShapeDrawable.setShadowColor();
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawable.drawableState.shapeAppearanceModel;
        shapeAppearanceModel.getClass();
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
        TypedArray obtainStyledAttributes = materialCardView.getContext().obtainStyledAttributes(attributeSet, R$styleable.CardView, i, R.style.CardView);
        if (obtainStyledAttributes.hasValue(3)) {
            builder.setAllCornerSizes(obtainStyledAttributes.getDimension(3, 0.0f));
        }
        this.foregroundContentDrawable = new MaterialShapeDrawable();
        setShapeAppearanceModel(builder.build());
        this.iconFadeAnimInterpolator = MotionUtils.resolveThemeInterpolator(materialCardView.getContext(), R.attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
        this.iconFadeInAnimDuration = MotionUtils.resolveThemeDuration(materialCardView.getContext(), R.attr.motionDurationShort2, 300);
        this.iconFadeOutAnimDuration = MotionUtils.resolveThemeDuration(materialCardView.getContext(), R.attr.motionDurationShort1, 300);
        obtainStyledAttributes.recycle();
    }

    public static float calculateCornerPaddingForCornerTreatment(CornerTreatment cornerTreatment, float f) {
        if (cornerTreatment instanceof RoundedCornerTreatment) {
            return (float) ((1.0d - COS_45) * f);
        }
        if (cornerTreatment instanceof CutCornerTreatment) {
            return f / 2.0f;
        }
        return 0.0f;
    }

    public final float calculateActualCornerPadding() {
        CornerTreatment cornerTreatment = this.shapeAppearanceModel.topLeftCorner;
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        return Math.max(Math.max(calculateCornerPaddingForCornerTreatment(cornerTreatment, materialShapeDrawable.getTopLeftCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.topRightCorner, materialShapeDrawable.drawableState.shapeAppearanceModel.topRightCornerSize.getCornerSize(materialShapeDrawable.getBoundsAsRectF$1()))), Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.bottomRightCorner, materialShapeDrawable.drawableState.shapeAppearanceModel.bottomRightCornerSize.getCornerSize(materialShapeDrawable.getBoundsAsRectF$1())), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.bottomLeftCorner, materialShapeDrawable.drawableState.shapeAppearanceModel.bottomLeftCornerSize.getCornerSize(materialShapeDrawable.getBoundsAsRectF$1()))));
    }

    public final Drawable getClickableForeground() {
        if (this.rippleDrawable == null) {
            this.foregroundShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.rippleDrawable = new RippleDrawable(this.rippleColor, null, this.foregroundShapeDrawable);
        }
        if (this.clickableForegroundDrawable == null) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.rippleDrawable, this.foregroundContentDrawable, this.checkedIcon});
            this.clickableForegroundDrawable = layerDrawable;
            layerDrawable.setId(2, R.id.mtrl_card_checked_layer_id);
        }
        return this.clickableForegroundDrawable;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.google.android.material.card.MaterialCardViewHelper$1] */
    public final AnonymousClass1 insetDrawable(Drawable drawable) {
        int i;
        int i2;
        MaterialCardView materialCardView = this.materialCardView;
        if (materialCardView.mCompatPadding) {
            CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
            CardView.AnonymousClass1 anonymousClass1 = materialCardView.mCardViewDelegate;
            cardViewApi21Impl.getClass();
            int ceil = (int) Math.ceil((((RoundRectDrawable) anonymousClass1.mCardBackground).mPadding * 1.5f) + (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f));
            CardView.AnonymousClass1 anonymousClass12 = materialCardView.mCardViewDelegate;
            cardViewApi21Impl.getClass();
            i = (int) Math.ceil(((RoundRectDrawable) anonymousClass12.mCardBackground).mPadding + (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f));
            i2 = ceil;
        } else {
            i = 0;
            i2 = 0;
        }
        return new InsetDrawable(this, drawable, i, i2, i, i2) { // from class: com.google.android.material.card.MaterialCardViewHelper.1
            @Override // android.graphics.drawable.Drawable
            public final int getMinimumHeight() {
                return -1;
            }

            @Override // android.graphics.drawable.Drawable
            public final int getMinimumWidth() {
                return -1;
            }

            @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
            public final boolean getPadding(Rect rect) {
                return false;
            }
        };
    }

    public final void setChecked(boolean z, boolean z2) {
        Drawable drawable = this.checkedIcon;
        if (drawable != null) {
            if (!z2) {
                drawable.setAlpha(z ? 255 : 0);
                this.checkedAnimationProgress = z ? 1.0f : 0.0f;
                return;
            }
            float f = z ? 1.0f : 0.0f;
            float f2 = z ? 1.0f - this.checkedAnimationProgress : this.checkedAnimationProgress;
            ValueAnimator valueAnimator = this.iconAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.iconAnimator = null;
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.checkedAnimationProgress, f);
            this.iconAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.card.MaterialCardViewHelper$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    MaterialCardViewHelper materialCardViewHelper = MaterialCardViewHelper.this;
                    int i = MaterialCardViewHelper.$r8$clinit;
                    materialCardViewHelper.getClass();
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    materialCardViewHelper.checkedIcon.setAlpha((int) (255.0f * floatValue));
                    materialCardViewHelper.checkedAnimationProgress = floatValue;
                }
            });
            this.iconAnimator.setInterpolator(this.iconFadeAnimInterpolator);
            this.iconAnimator.setDuration((long) ((z ? this.iconFadeInAnimDuration : this.iconFadeOutAnimDuration) * f2));
            this.iconAnimator.start();
        }
    }

    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.shadowBitmapDrawingEnable = !materialShapeDrawable.drawableState.shapeAppearanceModel.isRoundRect(materialShapeDrawable.getBoundsAsRectF$1());
        MaterialShapeDrawable materialShapeDrawable2 = this.foregroundContentDrawable;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel);
        }
        MaterialShapeDrawable materialShapeDrawable3 = this.foregroundShapeDrawable;
        if (materialShapeDrawable3 != null) {
            materialShapeDrawable3.setShapeAppearanceModel(shapeAppearanceModel);
        }
    }

    public final boolean shouldAddCornerPaddingOutsideCardBackground() {
        MaterialCardView materialCardView = this.materialCardView;
        if (!materialCardView.mPreventCornerOverlap) {
            return false;
        }
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        return materialShapeDrawable.drawableState.shapeAppearanceModel.isRoundRect(materialShapeDrawable.getBoundsAsRectF$1()) && materialCardView.mCompatPadding;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.view.View] */
    public final boolean shouldUseClickableForeground() {
        MaterialCardView materialCardView = this.materialCardView;
        boolean isClickable = materialCardView.isClickable();
        MaterialCardView materialCardView2 = materialCardView;
        if (isClickable) {
            return true;
        }
        while (materialCardView2.isDuplicateParentStateEnabled() && (materialCardView2.getParent() instanceof View)) {
            materialCardView2 = (View) materialCardView2.getParent();
        }
        return materialCardView2.isClickable();
    }

    public final void updateClickable() {
        Drawable drawable = this.fgDrawable;
        Drawable clickableForeground = shouldUseClickableForeground() ? getClickableForeground() : this.foregroundContentDrawable;
        this.fgDrawable = clickableForeground;
        if (drawable != clickableForeground) {
            MaterialCardView materialCardView = this.materialCardView;
            if (materialCardView.getForeground() instanceof InsetDrawable) {
                ((InsetDrawable) materialCardView.getForeground()).setDrawable(clickableForeground);
            } else {
                materialCardView.setForeground(insetDrawable(clickableForeground));
            }
        }
    }

    public final void updateContentPadding() {
        boolean z;
        float f;
        MaterialCardView materialCardView = this.materialCardView;
        if (materialCardView.mPreventCornerOverlap) {
            MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
            if (!materialShapeDrawable.drawableState.shapeAppearanceModel.isRoundRect(materialShapeDrawable.getBoundsAsRectF$1())) {
                z = true;
                f = 0.0f;
                float calculateActualCornerPadding = (!z || shouldAddCornerPaddingOutsideCardBackground()) ? calculateActualCornerPadding() : 0.0f;
                if (materialCardView.mPreventCornerOverlap && materialCardView.mCompatPadding) {
                    double d = 1.0d - COS_45;
                    CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
                    CardView.AnonymousClass1 anonymousClass1 = materialCardView.mCardViewDelegate;
                    cardViewApi21Impl.getClass();
                    f = (float) (d * ((RoundRectDrawable) anonymousClass1.mCardBackground).mRadius);
                }
                int i = (int) (calculateActualCornerPadding - f);
                Rect rect = this.userContentPadding;
                materialCardView.mContentPadding.set(rect.left + i, rect.top + i, rect.right + i, rect.bottom + i);
                CardView.IMPL.updatePadding(materialCardView.mCardViewDelegate);
            }
        }
        z = false;
        f = 0.0f;
        if (z) {
        }
        if (materialCardView.mPreventCornerOverlap) {
            double d2 = 1.0d - COS_45;
            CardViewApi21Impl cardViewApi21Impl2 = CardView.IMPL;
            CardView.AnonymousClass1 anonymousClass12 = materialCardView.mCardViewDelegate;
            cardViewApi21Impl2.getClass();
            f = (float) (d2 * ((RoundRectDrawable) anonymousClass12.mCardBackground).mRadius);
        }
        int i2 = (int) (calculateActualCornerPadding - f);
        Rect rect2 = this.userContentPadding;
        materialCardView.mContentPadding.set(rect2.left + i2, rect2.top + i2, rect2.right + i2, rect2.bottom + i2);
        CardView.IMPL.updatePadding(materialCardView.mCardViewDelegate);
    }
}
