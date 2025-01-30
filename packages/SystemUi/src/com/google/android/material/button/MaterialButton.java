package com.google.android.material.button;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatBackgroundHelper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$styleable;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MaterialButton extends AppCompatButton implements Checkable, Shapeable {
    public static final int[] CHECKABLE_STATE_SET = {R.attr.state_checkable};
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public boolean broadcasting;
    public boolean checked;
    public Drawable icon;
    public final int iconGravity;
    public int iconLeft;
    public final int iconPadding;
    public final int iconSize;
    public final ColorStateList iconTint;
    public final PorterDuff.Mode iconTintMode;
    public int iconTop;
    public final MaterialButtonHelper materialButtonHelper;
    public final LinkedHashSet onCheckedChangeListeners;
    public MaterialButtonToggleGroup.PressedStateTracker onPressedChangeListenerInternal;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.button.MaterialButton.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        };
        public boolean checked;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.checked ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                SavedState.class.getClassLoader();
            }
            this.checked = parcel.readInt() == 1;
        }
    }

    public MaterialButton(Context context) {
        this(context, null);
    }

    private void updateIcon(boolean z) {
        Drawable drawable = this.icon;
        boolean z2 = true;
        if (drawable != null) {
            Drawable mutate = drawable.mutate();
            this.icon = mutate;
            mutate.setTintList(this.iconTint);
            PorterDuff.Mode mode = this.iconTintMode;
            if (mode != null) {
                this.icon.setTintMode(mode);
            }
            int i = this.iconSize;
            if (i == 0) {
                i = this.icon.getIntrinsicWidth();
            }
            int i2 = this.iconSize;
            if (i2 == 0) {
                i2 = this.icon.getIntrinsicHeight();
            }
            Drawable drawable2 = this.icon;
            int i3 = this.iconLeft;
            int i4 = this.iconTop;
            drawable2.setBounds(i3, i4, i + i3, i2 + i4);
            this.icon.setVisible(true, z);
        }
        if (z) {
            resetIconDrawable();
            return;
        }
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        Drawable drawable3 = compoundDrawablesRelative[0];
        Drawable drawable4 = compoundDrawablesRelative[1];
        Drawable drawable5 = compoundDrawablesRelative[2];
        int i5 = this.iconGravity;
        if (!(i5 == 1 || i5 == 2) || drawable3 == this.icon) {
            if (!(i5 == 3 || i5 == 4) || drawable5 == this.icon) {
                if (!(i5 == 16 || i5 == 32) || drawable4 == this.icon) {
                    z2 = false;
                }
            }
        }
        if (z2) {
            resetIconDrawable();
        }
    }

    @Override // android.view.View
    public final ColorStateList getBackgroundTintList() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.backgroundTint;
        }
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Override // android.view.View
    public final PorterDuff.Mode getBackgroundTintMode() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.backgroundTintMode;
        }
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.checked;
    }

    public final boolean isUsingOriginalBackground() {
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        return (materialButtonHelper == null || materialButtonHelper.backgroundOverwritten) ? false : true;
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isUsingOriginalBackground()) {
            MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialButtonHelper.getMaterialShapeDrawable(false));
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if (materialButtonHelper != null && materialButtonHelper.checkable) {
            Button.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        if (isChecked()) {
            Button.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        accessibilityEvent.setClassName((materialButtonHelper != null && materialButtonHelper.checkable ? CompoundButton.class : Button.class).getName());
        accessibilityEvent.setChecked(isChecked());
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        accessibilityNodeInfo.setClassName((materialButtonHelper != null && materialButtonHelper.checkable ? CompoundButton.class : Button.class).getName());
        MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
        accessibilityNodeInfo.setCheckable(materialButtonHelper2 != null && materialButtonHelper2.checkable);
        accessibilityNodeInfo.setChecked(isChecked());
        accessibilityNodeInfo.setClickable(isClickable());
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        setChecked(savedState.checked);
    }

    @Override // android.widget.TextView, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.checked;
        return savedState;
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.widget.TextView
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (this.materialButtonHelper.toggleCheckedStateOnClick) {
            toggle();
        }
        return super.performClick();
    }

    @Override // android.view.View
    public final void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.icon != null) {
            if (this.icon.setState(getDrawableState())) {
                invalidate();
            }
        }
    }

    public final void resetIconDrawable() {
        int i = this.iconGravity;
        if (i == 1 || i == 2) {
            setCompoundDrawablesRelative(this.icon, null, null, null);
            return;
        }
        if (i == 3 || i == 4) {
            setCompoundDrawablesRelative(null, null, this.icon, null);
            return;
        }
        if (i == 16 || i == 32) {
            setCompoundDrawablesRelative(null, this.icon, null, null);
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        if (!isUsingOriginalBackground()) {
            super.setBackgroundColor(i);
            return;
        }
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if (materialButtonHelper.getMaterialShapeDrawable(false) != null) {
            materialButtonHelper.getMaterialShapeDrawable(false).setTint(i);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (!isUsingOriginalBackground()) {
            super.setBackgroundDrawable(drawable);
            return;
        }
        if (drawable == getBackground()) {
            getBackground().setState(drawable.getState());
            return;
        }
        Log.w("MaterialButton", "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        materialButtonHelper.backgroundOverwritten = true;
        ColorStateList colorStateList = materialButtonHelper.backgroundTint;
        MaterialButton materialButton = materialButtonHelper.materialButton;
        materialButton.setSupportBackgroundTintList(colorStateList);
        materialButton.setSupportBackgroundTintMode(materialButtonHelper.backgroundTintMode);
        super.setBackgroundDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public final void setBackgroundResource(int i) {
        setBackgroundDrawable(i != 0 ? AppCompatResources.getDrawable(i, getContext()) : null);
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        setSupportBackgroundTintList(colorStateList);
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        setSupportBackgroundTintMode(mode);
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if ((materialButtonHelper != null && materialButtonHelper.checkable) && isEnabled() && this.checked != z) {
            this.checked = z;
            refreshDrawableState();
            if (getParent() instanceof MaterialButtonToggleGroup) {
                MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) getParent();
                boolean z2 = this.checked;
                if (!materialButtonToggleGroup.skipCheckedStateTracker) {
                    materialButtonToggleGroup.checkInternal(getId(), z2);
                }
            }
            if (this.broadcasting) {
                return;
            }
            this.broadcasting = true;
            Iterator it = this.onCheckedChangeListeners.iterator();
            if (it.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
                throw null;
            }
            this.broadcasting = false;
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.getMaterialShapeDrawable(false).setElevation(f);
        }
    }

    public final void setInternalBackground(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setPressed(boolean z) {
        MaterialButtonToggleGroup.PressedStateTracker pressedStateTracker = this.onPressedChangeListenerInternal;
        if (pressedStateTracker != null) {
            MaterialButtonToggleGroup.this.invalidate();
        }
        super.setPressed(z);
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        if (!isUsingOriginalBackground()) {
            throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
        }
        this.materialButtonHelper.setShapeAppearanceModel(shapeAppearanceModel);
    }

    public final void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (!isUsingOriginalBackground()) {
            AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
            if (appCompatBackgroundHelper != null) {
                appCompatBackgroundHelper.setSupportBackgroundTintList(colorStateList);
                return;
            }
            return;
        }
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if (materialButtonHelper.backgroundTint != colorStateList) {
            materialButtonHelper.backgroundTint = colorStateList;
            if (materialButtonHelper.getMaterialShapeDrawable(false) != null) {
                materialButtonHelper.getMaterialShapeDrawable(false).setTintList(materialButtonHelper.backgroundTint);
            }
        }
    }

    public final void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (!isUsingOriginalBackground()) {
            AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
            if (appCompatBackgroundHelper != null) {
                appCompatBackgroundHelper.setSupportBackgroundTintMode(mode);
                return;
            }
            return;
        }
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if (materialButtonHelper.backgroundTintMode != mode) {
            materialButtonHelper.backgroundTintMode = mode;
            if (materialButtonHelper.getMaterialShapeDrawable(false) == null || materialButtonHelper.backgroundTintMode == null) {
                return;
            }
            materialButtonHelper.getMaterialShapeDrawable(false).setTintMode(materialButtonHelper.backgroundTintMode);
        }
    }

    @Override // android.view.View
    public final void setTextAlignment(int i) {
        super.setTextAlignment(i);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.checked);
    }

    public final void updateIconPosition(int i, int i2) {
        Layout.Alignment alignment;
        int min;
        if (this.icon == null || getLayout() == null) {
            return;
        }
        int i3 = this.iconGravity;
        if (!(i3 == 1 || i3 == 2)) {
            if (!(i3 == 3 || i3 == 4)) {
                if (i3 == 16 || i3 == 32) {
                    this.iconLeft = 0;
                    if (i3 == 16) {
                        this.iconTop = 0;
                        updateIcon(false);
                        return;
                    }
                    int i4 = this.iconSize;
                    if (i4 == 0) {
                        i4 = this.icon.getIntrinsicHeight();
                    }
                    if (getLineCount() > 1) {
                        min = getLayout().getHeight();
                    } else {
                        TextPaint paint = getPaint();
                        String charSequence = getText().toString();
                        if (getTransformationMethod() != null) {
                            charSequence = getTransformationMethod().getTransformation(charSequence, this).toString();
                        }
                        Rect rect = new Rect();
                        paint.getTextBounds(charSequence, 0, charSequence.length(), rect);
                        min = Math.min(rect.height(), getLayout().getHeight());
                    }
                    int max = Math.max(0, (((((i2 - min) - getPaddingTop()) - i4) - this.iconPadding) - getPaddingBottom()) / 2);
                    if (this.iconTop != max) {
                        this.iconTop = max;
                        updateIcon(false);
                        return;
                    }
                    return;
                }
                return;
            }
        }
        this.iconTop = 0;
        int textAlignment = getTextAlignment();
        if (textAlignment != 1) {
            alignment = (textAlignment == 6 || textAlignment == 3) ? Layout.Alignment.ALIGN_OPPOSITE : textAlignment != 4 ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER;
        } else {
            int gravity = getGravity() & 8388615;
            alignment = gravity != 1 ? (gravity == 5 || gravity == 8388613) ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER;
        }
        int i5 = this.iconGravity;
        if (i5 == 1 || i5 == 3 || ((i5 == 2 && alignment == Layout.Alignment.ALIGN_NORMAL) || (i5 == 4 && alignment == Layout.Alignment.ALIGN_OPPOSITE))) {
            this.iconLeft = 0;
            updateIcon(false);
            return;
        }
        int i6 = this.iconSize;
        if (i6 == 0) {
            i6 = this.icon.getIntrinsicWidth();
        }
        int lineCount = getLineCount();
        int i7 = 0;
        for (int i8 = 0; i8 < lineCount; i8++) {
            CharSequence subSequence = getText().subSequence(getLayout().getLineStart(i8), getLayout().getLineEnd(i8));
            TextPaint paint2 = getPaint();
            String charSequence2 = subSequence.toString();
            if (getTransformationMethod() != null) {
                charSequence2 = getTransformationMethod().getTransformation(charSequence2, this).toString();
            }
            i7 = Math.max(i7, Math.min((int) paint2.measureText(charSequence2), getLayout().getEllipsizedWidth()));
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int paddingEnd = ((((i - i7) - ViewCompat.Api17Impl.getPaddingEnd(this)) - i6) - this.iconPadding) - ViewCompat.Api17Impl.getPaddingStart(this);
        if (alignment == Layout.Alignment.ALIGN_CENTER) {
            paddingEnd /= 2;
        }
        if ((ViewCompat.Api17Impl.getLayoutDirection(this) == 1) != (this.iconGravity == 4)) {
            paddingEnd = -paddingEnd;
        }
        if (this.iconLeft != paddingEnd) {
            this.iconLeft = paddingEnd;
            updateIcon(false);
        }
    }

    public MaterialButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.materialButtonStyle);
    }

    public MaterialButton(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019084), attributeSet, i);
        boolean z;
        this.onCheckedChangeListeners = new LinkedHashSet();
        this.checked = false;
        this.broadcasting = false;
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.MaterialButton, i, 2132019084, new int[0]);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        this.iconPadding = dimensionPixelSize;
        this.iconTintMode = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(15, -1), PorterDuff.Mode.SRC_IN);
        this.iconTint = MaterialResources.getColorStateList(getContext(), obtainStyledAttributes, 14);
        this.icon = MaterialResources.getDrawable(getContext(), obtainStyledAttributes, 10);
        this.iconGravity = obtainStyledAttributes.getInteger(11, 1);
        this.iconSize = obtainStyledAttributes.getDimensionPixelSize(13, 0);
        MaterialButtonHelper materialButtonHelper = new MaterialButtonHelper(this, ShapeAppearanceModel.builder(context2, attributeSet, i, 2132019084).build());
        this.materialButtonHelper = materialButtonHelper;
        materialButtonHelper.insetLeft = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
        materialButtonHelper.insetRight = obtainStyledAttributes.getDimensionPixelOffset(2, 0);
        materialButtonHelper.insetTop = obtainStyledAttributes.getDimensionPixelOffset(3, 0);
        materialButtonHelper.insetBottom = obtainStyledAttributes.getDimensionPixelOffset(4, 0);
        if (obtainStyledAttributes.hasValue(8)) {
            materialButtonHelper.setShapeAppearanceModel(materialButtonHelper.shapeAppearanceModel.withCornerSize(obtainStyledAttributes.getDimensionPixelSize(8, -1)));
        }
        materialButtonHelper.strokeWidth = obtainStyledAttributes.getDimensionPixelSize(20, 0);
        materialButtonHelper.backgroundTintMode = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(7, -1), PorterDuff.Mode.SRC_IN);
        MaterialButton materialButton = materialButtonHelper.materialButton;
        materialButtonHelper.backgroundTint = MaterialResources.getColorStateList(materialButton.getContext(), obtainStyledAttributes, 6);
        materialButtonHelper.strokeColor = MaterialResources.getColorStateList(materialButton.getContext(), obtainStyledAttributes, 19);
        materialButtonHelper.rippleColor = MaterialResources.getColorStateList(materialButton.getContext(), obtainStyledAttributes, 16);
        materialButtonHelper.checkable = obtainStyledAttributes.getBoolean(5, false);
        materialButtonHelper.elevation = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        materialButtonHelper.toggleCheckedStateOnClick = obtainStyledAttributes.getBoolean(21, true);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int paddingStart = ViewCompat.Api17Impl.getPaddingStart(materialButton);
        int paddingTop = materialButton.getPaddingTop();
        int paddingEnd = ViewCompat.Api17Impl.getPaddingEnd(materialButton);
        int paddingBottom = materialButton.getPaddingBottom();
        if (obtainStyledAttributes.hasValue(0)) {
            materialButtonHelper.backgroundOverwritten = true;
            materialButton.setSupportBackgroundTintList(materialButtonHelper.backgroundTint);
            materialButton.setSupportBackgroundTintMode(materialButtonHelper.backgroundTintMode);
            z = false;
        } else {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(materialButtonHelper.shapeAppearanceModel);
            materialShapeDrawable.initializeElevationOverlay(materialButton.getContext());
            materialShapeDrawable.setTintList(materialButtonHelper.backgroundTint);
            PorterDuff.Mode mode = materialButtonHelper.backgroundTintMode;
            if (mode != null) {
                materialShapeDrawable.setTintMode(mode);
            }
            float f = materialButtonHelper.strokeWidth;
            ColorStateList colorStateList = materialButtonHelper.strokeColor;
            materialShapeDrawable.drawableState.strokeWidth = f;
            materialShapeDrawable.invalidateSelf();
            materialShapeDrawable.setStrokeColor(colorStateList);
            MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialButtonHelper.shapeAppearanceModel);
            materialShapeDrawable2.setTint(0);
            float f2 = materialButtonHelper.strokeWidth;
            int color = materialButtonHelper.shouldDrawSurfaceColorStroke ? MaterialColors.getColor(materialButton, com.android.systemui.R.attr.colorSurface) : 0;
            materialShapeDrawable2.drawableState.strokeWidth = f2;
            materialShapeDrawable2.invalidateSelf();
            materialShapeDrawable2.setStrokeColor(ColorStateList.valueOf(color));
            MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable(materialButtonHelper.shapeAppearanceModel);
            materialButtonHelper.maskDrawable = materialShapeDrawable3;
            materialShapeDrawable3.setTint(-1);
            RippleDrawable rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(materialButtonHelper.rippleColor), new InsetDrawable((Drawable) new LayerDrawable(new Drawable[]{materialShapeDrawable2, materialShapeDrawable}), materialButtonHelper.insetLeft, materialButtonHelper.insetTop, materialButtonHelper.insetRight, materialButtonHelper.insetBottom), materialButtonHelper.maskDrawable);
            materialButtonHelper.rippleDrawable = rippleDrawable;
            materialButton.setInternalBackground(rippleDrawable);
            z = false;
            MaterialShapeDrawable materialShapeDrawable4 = materialButtonHelper.getMaterialShapeDrawable(false);
            if (materialShapeDrawable4 != null) {
                materialShapeDrawable4.setElevation(materialButtonHelper.elevation);
                materialShapeDrawable4.setState(materialButton.getDrawableState());
            }
        }
        ViewCompat.Api17Impl.setPaddingRelative(materialButton, paddingStart + materialButtonHelper.insetLeft, paddingTop + materialButtonHelper.insetTop, paddingEnd + materialButtonHelper.insetRight, paddingBottom + materialButtonHelper.insetBottom);
        obtainStyledAttributes.recycle();
        setCompoundDrawablePadding(dimensionPixelSize);
        updateIcon(this.icon != null ? true : z);
    }
}
