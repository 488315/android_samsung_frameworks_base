package com.google.android.material.card;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.FrameLayout;
import androidx.cardview.widget.CardView;
import androidx.cardview.widget.CardViewApi21Impl;
import androidx.cardview.widget.RoundRectDrawable;
import androidx.core.view.ViewCompat;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class MaterialCardView extends CardView implements Checkable, Shapeable {
    public static final int[] CHECKABLE_STATE_SET = {R.attr.state_checkable};
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public final MaterialCardViewHelper cardViewHelper;
    public boolean checked;
    public final boolean isParentCardViewDoneInitializing;

    public MaterialCardView(Context context) {
        this(context, null);
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.checked;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.cardViewHelper.updateClickable();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.cardViewHelper.bgDrawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 3);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable) {
            FrameLayout.mergeDrawableStates(iArrOnCreateDrawableState, CHECKABLE_STATE_SET);
        }
        if (this.checked) {
            FrameLayout.mergeDrawableStates(iArrOnCreateDrawableState, CHECKED_STATE_SET);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.cardview.widget.CardView");
        accessibilityEvent.setChecked(this.checked);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.cardview.widget.CardView");
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        accessibilityNodeInfo.setCheckable(materialCardViewHelper != null && materialCardViewHelper.checkable);
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setChecked(this.checked);
    }

    @Override // androidx.cardview.widget.CardView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int iCeil;
        int iCeil2;
        int i3;
        int i4;
        super.onMeasure(i, i2);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (materialCardViewHelper.clickableForegroundDrawable != null) {
            MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
            if (materialCardView.mCompatPadding) {
                CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
                CardView.AnonymousClass1 anonymousClass1 = materialCardView.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                iCeil = (int) Math.ceil(((((RoundRectDrawable) anonymousClass1.mCardBackground).mPadding * 1.5f) + (materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground() ? materialCardViewHelper.calculateActualCornerPadding() : 0.0f)) * 2.0f);
                CardView.AnonymousClass1 anonymousClass12 = materialCardView.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                iCeil2 = (int) Math.ceil((((RoundRectDrawable) anonymousClass12.mCardBackground).mPadding + (materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground() ? materialCardViewHelper.calculateActualCornerPadding() : 0.0f)) * 2.0f);
            } else {
                iCeil = 0;
                iCeil2 = 0;
            }
            int i5 = materialCardViewHelper.checkedIconGravity;
            int i6 = (i5 & 8388613) == 8388613 ? ((measuredWidth - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - iCeil2 : materialCardViewHelper.checkedIconMargin;
            int i7 = (i5 & 80) == 80 ? materialCardViewHelper.checkedIconMargin : ((measuredHeight - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - iCeil;
            int i8 = (i5 & 8388613) == 8388613 ? materialCardViewHelper.checkedIconMargin : ((measuredWidth - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - iCeil2;
            int i9 = (i5 & 80) == 80 ? ((measuredHeight - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - iCeil : materialCardViewHelper.checkedIconMargin;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (materialCardView.getLayoutDirection() == 1) {
                i4 = i8;
                i3 = i6;
            } else {
                i3 = i8;
                i4 = i6;
            }
            materialCardViewHelper.clickableForegroundDrawable.setLayerInset(2, i4, i9, i3, i7);
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (this.isParentCardViewDoneInitializing) {
            if (!this.cardViewHelper.isBackgroundOverwritten) {
                Log.i("MaterialCardView", "Setting a custom background is not supported.");
                this.cardViewHelper.isBackgroundOverwritten = true;
            }
            super.setBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        if (this.checked != z) {
            toggle();
        }
    }

    @Override // android.view.View
    public final void setClickable(boolean z) {
        super.setClickable(z);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null) {
            materialCardViewHelper.updateClickable();
        }
    }

    @Override // androidx.cardview.widget.CardView
    public final void setRadius(float f) {
        super.setRadius(f);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        materialCardViewHelper.setShapeAppearanceModel(materialCardViewHelper.shapeAppearanceModel.withCornerSize(f));
        materialCardViewHelper.fgDrawable.invalidateSelf();
        boolean zShouldAddCornerPaddingOutsideCardBackground = materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground();
        MaterialShapeDrawable materialShapeDrawable = materialCardViewHelper.bgDrawable;
        MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
        if (zShouldAddCornerPaddingOutsideCardBackground || (materialCardView.mPreventCornerOverlap && !materialShapeDrawable.drawableState.shapeAppearanceModel.isRoundRect(materialShapeDrawable.getBoundsAsRectF$1()))) {
            materialCardViewHelper.updateContentPadding();
        }
        if (materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground()) {
            if (!materialCardViewHelper.isBackgroundOverwritten) {
                super.setBackgroundDrawable(materialCardViewHelper.insetDrawable(materialShapeDrawable));
            }
            materialCardView.setForeground(materialCardViewHelper.insetDrawable(materialCardViewHelper.fgDrawable));
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        RectF rectF = new RectF();
        rectF.set(this.cardViewHelper.bgDrawable.getBounds());
        setClipToOutline(shapeAppearanceModel.isRoundRect(rectF));
        this.cardViewHelper.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable && isEnabled()) {
            this.checked = !this.checked;
            refreshDrawableState();
            MaterialCardViewHelper materialCardViewHelper2 = this.cardViewHelper;
            Drawable drawable = materialCardViewHelper2.rippleDrawable;
            if (drawable != null) {
                Rect bounds = drawable.getBounds();
                int i = bounds.bottom;
                materialCardViewHelper2.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, i - 1);
                materialCardViewHelper2.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, i);
            }
            this.cardViewHelper.setChecked(this.checked, true);
        }
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.materialCardViewStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v6, types: [android.graphics.drawable.Drawable] */
    public MaterialCardView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, com.android.systemui.R.style.Widget_MaterialComponents_CardView), attributeSet, i);
        this.checked = false;
        this.isParentCardViewDoneInitializing = true;
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R$styleable.MaterialCardView, i, com.android.systemui.R.style.Widget_MaterialComponents_CardView, new int[0]);
        MaterialCardViewHelper materialCardViewHelper = new MaterialCardViewHelper(this, attributeSet, i, com.android.systemui.R.style.Widget_MaterialComponents_CardView);
        this.cardViewHelper = materialCardViewHelper;
        CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
        CardView.AnonymousClass1 anonymousClass1 = this.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        ColorStateList colorStateList = ((RoundRectDrawable) anonymousClass1.mCardBackground).mBackground;
        MaterialShapeDrawable materialShapeDrawable = materialCardViewHelper.bgDrawable;
        materialShapeDrawable.setFillColor(colorStateList);
        Rect rect = this.mContentPadding;
        materialCardViewHelper.userContentPadding.set(rect.left, rect.top, rect.right, rect.bottom);
        materialCardViewHelper.updateContentPadding();
        MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(materialCardView.getContext(), typedArrayObtainStyledAttributes, 11);
        materialCardViewHelper.strokeColor = colorStateList2;
        if (colorStateList2 == null) {
            materialCardViewHelper.strokeColor = ColorStateList.valueOf(-1);
        }
        materialCardViewHelper.strokeWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(12, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
        materialCardViewHelper.checkable = z;
        materialCardView.setLongClickable(z);
        materialCardViewHelper.checkedIconTint = MaterialResources.getColorStateList(materialCardView.getContext(), typedArrayObtainStyledAttributes, 6);
        Drawable drawable = MaterialResources.getDrawable(materialCardView.getContext(), typedArrayObtainStyledAttributes, 2);
        if (drawable != null) {
            Drawable drawableMutate = drawable.mutate();
            materialCardViewHelper.checkedIcon = drawableMutate;
            drawableMutate.setTintList(materialCardViewHelper.checkedIconTint);
            materialCardViewHelper.setChecked(materialCardView.checked, false);
        } else {
            materialCardViewHelper.checkedIcon = null;
        }
        LayerDrawable layerDrawable = materialCardViewHelper.clickableForegroundDrawable;
        if (layerDrawable != null) {
            layerDrawable.setDrawableByLayerId(com.android.systemui.R.id.mtrl_card_checked_layer_id, materialCardViewHelper.checkedIcon);
        }
        materialCardViewHelper.checkedIconSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(5, 0);
        materialCardViewHelper.checkedIconMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, 0);
        materialCardViewHelper.checkedIconGravity = typedArrayObtainStyledAttributes.getInteger(3, 8388661);
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(materialCardView.getContext(), typedArrayObtainStyledAttributes, 7);
        materialCardViewHelper.rippleColor = colorStateList3;
        if (colorStateList3 == null) {
            materialCardViewHelper.rippleColor = ColorStateList.valueOf(MaterialColors.getColor(materialCardView, com.android.systemui.R.attr.colorControlHighlight));
        }
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(materialCardView.getContext(), typedArrayObtainStyledAttributes, 1);
        colorStateList4 = colorStateList4 == null ? ColorStateList.valueOf(0) : colorStateList4;
        MaterialShapeDrawable materialShapeDrawable2 = materialCardViewHelper.foregroundContentDrawable;
        materialShapeDrawable2.setFillColor(colorStateList4);
        Drawable drawable2 = materialCardViewHelper.rippleDrawable;
        if (drawable2 != null) {
            ((RippleDrawable) drawable2).setColor(materialCardViewHelper.rippleColor);
        }
        CardView.AnonymousClass1 anonymousClass12 = materialCardView.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        materialShapeDrawable.setElevation(CardView.this.getElevation());
        float f = materialCardViewHelper.strokeWidth;
        ColorStateList colorStateList5 = materialCardViewHelper.strokeColor;
        materialShapeDrawable2.drawableState.strokeWidth = f;
        materialShapeDrawable2.invalidateSelf();
        materialShapeDrawable2.setStrokeColor(colorStateList5);
        super.setBackgroundDrawable(materialCardViewHelper.insetDrawable(materialShapeDrawable));
        MaterialShapeDrawable clickableForeground = materialCardViewHelper.shouldUseClickableForeground() ? materialCardViewHelper.getClickableForeground() : materialShapeDrawable2;
        materialCardViewHelper.fgDrawable = clickableForeground;
        materialCardView.setForeground(materialCardViewHelper.insetDrawable(clickableForeground));
        typedArrayObtainStyledAttributes.recycle();
    }
}
