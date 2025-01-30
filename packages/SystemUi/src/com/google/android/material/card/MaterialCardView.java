package com.google.android.material.card;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.cardViewHelper.bgDrawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 3);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        if (isChecked()) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.cardview.widget.CardView");
        accessibilityEvent.setChecked(isChecked());
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.cardview.widget.CardView");
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        accessibilityNodeInfo.setCheckable(materialCardViewHelper != null && materialCardViewHelper.checkable);
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setChecked(isChecked());
    }

    @Override // androidx.cardview.widget.CardView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        super.onMeasure(i, i2);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (materialCardViewHelper.clickableForegroundDrawable != null) {
            MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
            if (materialCardView.mCompatPadding) {
                CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
                CardView.C01101 c01101 = materialCardView.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                i4 = (int) Math.ceil(((((RoundRectDrawable) c01101.mCardBackground).mPadding * 1.5f) + (materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground() ? materialCardViewHelper.calculateActualCornerPadding() : 0.0f)) * 2.0f);
                CardView.C01101 c011012 = materialCardView.mCardViewDelegate;
                cardViewApi21Impl.getClass();
                i3 = (int) Math.ceil((((RoundRectDrawable) c011012.mCardBackground).mPadding + (materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground() ? materialCardViewHelper.calculateActualCornerPadding() : 0.0f)) * 2.0f);
            } else {
                i3 = 0;
                i4 = 0;
            }
            int i7 = materialCardViewHelper.checkedIconGravity;
            int i8 = (i7 & 8388613) == 8388613 ? ((measuredWidth - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i3 : materialCardViewHelper.checkedIconMargin;
            int i9 = (i7 & 80) == 80 ? materialCardViewHelper.checkedIconMargin : ((measuredHeight - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i4;
            int i10 = (i7 & 8388613) == 8388613 ? materialCardViewHelper.checkedIconMargin : ((measuredWidth - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i3;
            int i11 = (i7 & 80) == 80 ? ((measuredHeight - materialCardViewHelper.checkedIconMargin) - materialCardViewHelper.checkedIconSize) - i4 : materialCardViewHelper.checkedIconMargin;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api17Impl.getLayoutDirection(materialCardView) == 1) {
                i6 = i10;
                i5 = i8;
            } else {
                i5 = i10;
                i6 = i8;
            }
            materialCardViewHelper.clickableForegroundDrawable.setLayerInset(2, i6, i11, i5, i9);
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
            Drawable drawable = materialCardViewHelper.fgDrawable;
            MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
            Drawable clickableForeground = materialCardView.isClickable() ? materialCardViewHelper.getClickableForeground() : materialCardViewHelper.foregroundContentDrawable;
            materialCardViewHelper.fgDrawable = clickableForeground;
            if (drawable != clickableForeground) {
                if (materialCardView.getForeground() instanceof InsetDrawable) {
                    ((InsetDrawable) materialCardView.getForeground()).setDrawable(clickableForeground);
                } else {
                    materialCardView.setForeground(materialCardViewHelper.insetDrawable(clickableForeground));
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x002a, code lost:
    
        if ((r1.mPreventCornerOverlap && !r0.isRoundRect()) != false) goto L11;
     */
    @Override // androidx.cardview.widget.CardView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setRadius(float f) {
        super.setRadius(f);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        materialCardViewHelper.setShapeAppearanceModel(materialCardViewHelper.shapeAppearanceModel.withCornerSize(f));
        materialCardViewHelper.fgDrawable.invalidateSelf();
        boolean shouldAddCornerPaddingOutsideCardBackground = materialCardViewHelper.shouldAddCornerPaddingOutsideCardBackground();
        MaterialShapeDrawable materialShapeDrawable = materialCardViewHelper.bgDrawable;
        MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
        if (!shouldAddCornerPaddingOutsideCardBackground) {
        }
        materialCardViewHelper.updateContentPadding();
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
        if ((materialCardViewHelper != null && materialCardViewHelper.checkable) && isEnabled()) {
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
            MaterialCardViewHelper materialCardViewHelper3 = this.cardViewHelper;
            boolean z = this.checked;
            Drawable drawable2 = materialCardViewHelper3.checkedIcon;
            if (drawable2 != null) {
                drawable2.setAlpha(z ? 255 : 0);
            }
        }
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.materialCardViewStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.graphics.drawable.Drawable] */
    public MaterialCardView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019096), attributeSet, i);
        this.checked = false;
        this.isParentCardViewDoneInitializing = true;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R$styleable.MaterialCardView, i, 2132019096, new int[0]);
        MaterialCardViewHelper materialCardViewHelper = new MaterialCardViewHelper(this, attributeSet, i, 2132019096);
        this.cardViewHelper = materialCardViewHelper;
        CardViewApi21Impl cardViewApi21Impl = CardView.IMPL;
        CardView.C01101 c01101 = this.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        ColorStateList colorStateList = ((RoundRectDrawable) c01101.mCardBackground).mBackground;
        MaterialShapeDrawable materialShapeDrawable = materialCardViewHelper.bgDrawable;
        materialShapeDrawable.setFillColor(colorStateList);
        Rect rect = this.mContentPadding;
        materialCardViewHelper.userContentPadding.set(rect.left, rect.top, rect.right, rect.bottom);
        materialCardViewHelper.updateContentPadding();
        MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 11);
        materialCardViewHelper.strokeColor = colorStateList2;
        if (colorStateList2 == null) {
            materialCardViewHelper.strokeColor = ColorStateList.valueOf(-1);
        }
        materialCardViewHelper.strokeWidth = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        materialCardViewHelper.checkable = z;
        materialCardView.setLongClickable(z);
        materialCardViewHelper.checkedIconTint = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 6);
        Drawable drawable = MaterialResources.getDrawable(materialCardView.getContext(), obtainStyledAttributes, 2);
        if (drawable != null) {
            Drawable mutate = drawable.mutate();
            materialCardViewHelper.checkedIcon = mutate;
            mutate.setTintList(materialCardViewHelper.checkedIconTint);
            boolean isChecked = materialCardView.isChecked();
            Drawable drawable2 = materialCardViewHelper.checkedIcon;
            if (drawable2 != null) {
                drawable2.setAlpha(isChecked ? 255 : 0);
            }
        } else {
            materialCardViewHelper.checkedIcon = null;
        }
        LayerDrawable layerDrawable = materialCardViewHelper.clickableForegroundDrawable;
        if (layerDrawable != null) {
            layerDrawable.setDrawableByLayerId(com.android.systemui.R.id.mtrl_card_checked_layer_id, materialCardViewHelper.checkedIcon);
        }
        materialCardViewHelper.checkedIconSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        materialCardViewHelper.checkedIconMargin = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        materialCardViewHelper.checkedIconGravity = obtainStyledAttributes.getInteger(3, 8388661);
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 7);
        materialCardViewHelper.rippleColor = colorStateList3;
        if (colorStateList3 == null) {
            materialCardViewHelper.rippleColor = ColorStateList.valueOf(MaterialColors.getColor(materialCardView, com.android.systemui.R.attr.colorControlHighlight));
        }
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(materialCardView.getContext(), obtainStyledAttributes, 1);
        MaterialShapeDrawable materialShapeDrawable2 = materialCardViewHelper.foregroundContentDrawable;
        materialShapeDrawable2.setFillColor(colorStateList4 == null ? ColorStateList.valueOf(0) : colorStateList4);
        Drawable drawable3 = materialCardViewHelper.rippleDrawable;
        if (drawable3 != null) {
            ((RippleDrawable) drawable3).setColor(materialCardViewHelper.rippleColor);
        }
        CardView.C01101 c011012 = materialCardView.mCardViewDelegate;
        cardViewApi21Impl.getClass();
        materialShapeDrawable.setElevation(CardView.this.getElevation());
        float f = materialCardViewHelper.strokeWidth;
        ColorStateList colorStateList5 = materialCardViewHelper.strokeColor;
        materialShapeDrawable2.drawableState.strokeWidth = f;
        materialShapeDrawable2.invalidateSelf();
        materialShapeDrawable2.setStrokeColor(colorStateList5);
        super.setBackgroundDrawable(materialCardViewHelper.insetDrawable(materialShapeDrawable));
        MaterialShapeDrawable clickableForeground = materialCardView.isClickable() ? materialCardViewHelper.getClickableForeground() : materialShapeDrawable2;
        materialCardViewHelper.fgDrawable = clickableForeground;
        materialCardView.setForeground(materialCardViewHelper.insetDrawable(clickableForeground));
        obtainStyledAttributes.recycle();
    }
}
