package com.google.android.material.materialswitch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes4.dex */
public class MaterialSwitch extends SwitchCompat {
    public static final int[] STATE_SET_WITH_ICON = {R.attr.state_with_icon};
    public int[] currentStateChecked;
    public int[] currentStateUnchecked;
    public final Drawable thumbDrawable;
    public final Drawable thumbIconDrawable;
    public final ColorStateList thumbIconTintList;
    public final ColorStateList thumbTintList;
    public final Drawable trackDecorationDrawable;
    public final ColorStateList trackDecorationTintList;
    public final Drawable trackDrawable;
    public final ColorStateList trackTintList;

    public MaterialSwitch(Context context) {
        this(context, null);
    }

    public static void setInterpolatedDrawableTintIfPossible(Drawable drawable, ColorStateList colorStateList, int[] iArr, int[] iArr2, float f) {
        if (drawable == null || colorStateList == null) {
            return;
        }
        drawable.setTint(ColorUtils.blendARGB(f, colorStateList.getColorForState(iArr, 0), colorStateList.getColorForState(iArr2, 0)));
    }

    @Override // android.view.View
    public final void invalidate() {
        updateDrawableTints();
        super.invalidate();
    }

    @Override // androidx.appcompat.widget.SwitchCompat, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.thumbIconDrawable != null) {
            CompoundButton.mergeDrawableStates(iArrOnCreateDrawableState, STATE_SET_WITH_ICON);
        }
        int[] iArr = new int[iArrOnCreateDrawableState.length];
        int i2 = 0;
        for (int i3 : iArrOnCreateDrawableState) {
            if (i3 != 16842912) {
                iArr[i2] = i3;
                i2++;
            }
        }
        this.currentStateUnchecked = iArr;
        this.currentStateChecked = DrawableUtils.getCheckedState(iArrOnCreateDrawableState);
        return iArrOnCreateDrawableState;
    }

    public final void updateDrawableTints() {
        ColorStateList colorStateList = this.thumbTintList;
        if (colorStateList == null && this.thumbIconTintList == null && this.trackTintList == null && this.trackDecorationTintList == null) {
            return;
        }
        float f = this.mThumbPosition;
        if (colorStateList != null) {
            setInterpolatedDrawableTintIfPossible(this.thumbDrawable, colorStateList, this.currentStateUnchecked, this.currentStateChecked, f);
        }
        ColorStateList colorStateList2 = this.thumbIconTintList;
        if (colorStateList2 != null) {
            setInterpolatedDrawableTintIfPossible(this.thumbIconDrawable, colorStateList2, this.currentStateUnchecked, this.currentStateChecked, f);
        }
        ColorStateList colorStateList3 = this.trackTintList;
        if (colorStateList3 != null) {
            setInterpolatedDrawableTintIfPossible(this.trackDrawable, colorStateList3, this.currentStateUnchecked, this.currentStateChecked, f);
        }
        ColorStateList colorStateList4 = this.trackDecorationTintList;
        if (colorStateList4 != null) {
            setInterpolatedDrawableTintIfPossible(this.trackDecorationDrawable, colorStateList4, this.currentStateUnchecked, this.currentStateChecked, f);
        }
    }

    public MaterialSwitch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialSwitchStyle);
    }

    public MaterialSwitch(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_Material3_CompoundButton_MaterialSwitch), attributeSet, i);
        Context context2 = getContext();
        this.thumbDrawable = this.mThumbDrawable;
        ColorStateList colorStateList = this.mThumbTintList;
        this.thumbTintList = colorStateList;
        this.mThumbTintList = null;
        this.mHasThumbTint = true;
        applyThumbTint();
        this.trackDrawable = this.mTrackDrawable;
        ColorStateList colorStateList2 = this.mTrackTintList;
        this.trackTintList = colorStateList2;
        this.mTrackTintList = null;
        this.mHasTrackTint = true;
        applyTrackTint();
        int[] iArr = R$styleable.MaterialSwitch;
        ThemeEnforcement.checkCompatibleTheme(context2, attributeSet, i, R.style.Widget_Material3_CompoundButton_MaterialSwitch);
        ThemeEnforcement.checkTextAppearance(context2, attributeSet, iArr, i, R.style.Widget_Material3_CompoundButton_MaterialSwitch, new int[0]);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attributeSet, iArr, i, R.style.Widget_Material3_CompoundButton_MaterialSwitch);
        this.thumbIconDrawable = tintTypedArrayObtainStyledAttributes.getDrawable(0);
        int dimensionPixelSize = tintTypedArrayObtainStyledAttributes.mWrapped.getDimensionPixelSize(1, -1);
        ColorStateList colorStateList3 = tintTypedArrayObtainStyledAttributes.getColorStateList(2);
        this.thumbIconTintList = colorStateList3;
        int i2 = tintTypedArrayObtainStyledAttributes.mWrapped.getInt(3, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        PorterDuff.Mode tintMode = ViewUtils.parseTintMode(i2, mode);
        this.trackDecorationDrawable = tintTypedArrayObtainStyledAttributes.getDrawable(4);
        ColorStateList colorStateList4 = tintTypedArrayObtainStyledAttributes.getColorStateList(5);
        this.trackDecorationTintList = colorStateList4;
        PorterDuff.Mode tintMode2 = ViewUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.mWrapped.getInt(6, -1), mode);
        tintTypedArrayObtainStyledAttributes.recycle();
        invalidate();
        this.thumbDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.thumbDrawable, colorStateList, this.mThumbTintMode);
        this.thumbIconDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.thumbIconDrawable, colorStateList3, tintMode);
        updateDrawableTints();
        Drawable drawableCompositeTwoLayeredDrawable = DrawableUtils.compositeTwoLayeredDrawable(this.thumbDrawable, this.thumbIconDrawable, dimensionPixelSize, dimensionPixelSize);
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.setCallback(null);
        }
        this.mThumbDrawable = drawableCompositeTwoLayeredDrawable;
        if (drawableCompositeTwoLayeredDrawable != null) {
            drawableCompositeTwoLayeredDrawable.setCallback(this);
        }
        requestLayout();
        refreshDrawableState();
        this.trackDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.trackDrawable, colorStateList2, this.mTrackTintMode);
        this.trackDecorationDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.trackDecorationDrawable, colorStateList4, tintMode2);
        updateDrawableTints();
        Drawable layerDrawable = this.trackDrawable;
        if (layerDrawable != null && this.trackDecorationDrawable != null) {
            layerDrawable = new LayerDrawable(new Drawable[]{this.trackDrawable, this.trackDecorationDrawable});
        } else if (layerDrawable == null) {
            layerDrawable = this.trackDecorationDrawable;
        }
        if (layerDrawable != null) {
            layerDrawable.getIntrinsicWidth();
            requestLayout();
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mTrackDrawable = layerDrawable;
        if (layerDrawable != null) {
            Drawable.ConstantState constantState = layerDrawable.getConstantState();
            if (constantState != null) {
                this.mTrackOnDrawable = constantState.newDrawable();
                this.mTrackOffDrawable = constantState.newDrawable();
            } else {
                this.mTrackOnDrawable = layerDrawable;
                this.mTrackOffDrawable = layerDrawable;
            }
            this.mTrackOnDrawable.setState(new int[]{android.R.attr.state_enabled, android.R.attr.state_checked});
            this.mTrackOffDrawable.setState(new int[]{android.R.attr.state_enabled, -16842912});
            layerDrawable.setCallback(this);
        }
        requestLayout();
    }
}
