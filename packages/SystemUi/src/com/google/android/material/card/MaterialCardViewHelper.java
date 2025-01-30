package com.google.android.material.card;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import androidx.cardview.R$styleable;
import androidx.cardview.widget.CardView;
import androidx.cardview.widget.CardViewApi21Impl;
import androidx.cardview.widget.RoundRectDrawable;
import com.android.systemui.R;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MaterialCardViewHelper {
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
    public final MaterialCardView materialCardView;
    public ColorStateList rippleColor;
    public Drawable rippleDrawable;
    public ShapeAppearanceModel shapeAppearanceModel;
    public ColorStateList strokeColor;
    public int strokeWidth;
    public final Rect userContentPadding = new Rect();
    public boolean isBackgroundOverwritten = false;

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
        return Math.max(Math.max(calculateCornerPaddingForCornerTreatment(cornerTreatment, materialShapeDrawable.getTopLeftCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.topRightCorner, materialShapeDrawable.drawableState.shapeAppearanceModel.topRightCornerSize.getCornerSize(materialShapeDrawable.getBoundsAsRectF()))), Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.bottomRightCorner, materialShapeDrawable.drawableState.shapeAppearanceModel.bottomRightCornerSize.getCornerSize(materialShapeDrawable.getBoundsAsRectF())), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.bottomLeftCorner, materialShapeDrawable.drawableState.shapeAppearanceModel.bottomLeftCornerSize.getCornerSize(materialShapeDrawable.getBoundsAsRectF()))));
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

    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.material.card.MaterialCardViewHelper$1] */
    public final C42431 insetDrawable(Drawable drawable) {
        int i;
        int i2;
        MaterialCardView materialCardView = this.materialCardView;
        if (materialCardView.mCompatPadding) {
            CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
            CardView.C01101 c01101 = materialCardView.mCardViewDelegate;
            cardViewApi21Impl.getClass();
            int ceil = (int) Math.ceil((((RoundRectDrawable) c01101.mCardBackground).mPadding * 1.5f) + (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f));
            CardView.C01101 c011012 = materialCardView.mCardViewDelegate;
            cardViewApi21Impl.getClass();
            i = (int) Math.ceil(((RoundRectDrawable) c011012.mCardBackground).mPadding + (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f));
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

    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.shadowBitmapDrawingEnable = !materialShapeDrawable.isRoundRect();
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
        return materialCardView.mPreventCornerOverlap && this.bgDrawable.isRoundRect() && materialCardView.mCompatPadding;
    }

    public final void updateContentPadding() {
        MaterialCardView materialCardView = this.materialCardView;
        boolean z = true;
        if (!(materialCardView.mPreventCornerOverlap && !this.bgDrawable.isRoundRect()) && !shouldAddCornerPaddingOutsideCardBackground()) {
            z = false;
        }
        float f = 0.0f;
        float calculateActualCornerPadding = z ? calculateActualCornerPadding() : 0.0f;
        if (materialCardView.mPreventCornerOverlap && materialCardView.mCompatPadding) {
            double d = 1.0d - COS_45;
            CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
            CardView.C01101 c01101 = materialCardView.mCardViewDelegate;
            cardViewApi21Impl.getClass();
            f = (float) (d * ((RoundRectDrawable) c01101.mCardBackground).mRadius);
        }
        int i = (int) (calculateActualCornerPadding - f);
        Rect rect = this.userContentPadding;
        materialCardView.mContentPadding.set(rect.left + i, rect.top + i, rect.right + i, rect.bottom + i);
        CardView.IMPL.updatePadding(materialCardView.mCardViewDelegate);
    }
}
