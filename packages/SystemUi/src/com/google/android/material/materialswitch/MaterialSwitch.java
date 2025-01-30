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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MaterialSwitch extends SwitchCompat {
    public static final int[] STATE_SET_WITH_ICON = {R.attr.state_with_icon};
    public int[] currentStateChecked;
    public int[] currentStateUnchecked;
    public Drawable thumbDrawable;
    public Drawable thumbIconDrawable;
    public final ColorStateList thumbIconTintList;
    public final ColorStateList thumbTintList;
    public Drawable trackDecorationDrawable;
    public final ColorStateList trackDecorationTintList;
    public Drawable trackDrawable;
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
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.thumbIconDrawable != null) {
            CompoundButton.mergeDrawableStates(onCreateDrawableState, STATE_SET_WITH_ICON);
        }
        int[] iArr = new int[onCreateDrawableState.length];
        int i2 = 0;
        for (int i3 : onCreateDrawableState) {
            if (i3 != 16842912) {
                iArr[i2] = i3;
                i2++;
            }
        }
        this.currentStateUnchecked = iArr;
        this.currentStateChecked = DrawableUtils.getCheckedState(onCreateDrawableState);
        return onCreateDrawableState;
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
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018974), attributeSet, i);
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
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R$styleable.MaterialSwitch, i, 2132018974, new int[0]);
        this.thumbIconDrawable = obtainTintedStyledAttributes.getDrawable(0);
        ColorStateList colorStateList3 = obtainTintedStyledAttributes.getColorStateList(1);
        this.thumbIconTintList = colorStateList3;
        PorterDuff.Mode parseTintMode = ViewUtils.parseTintMode(obtainTintedStyledAttributes.getInt(2, -1), PorterDuff.Mode.SRC_IN);
        this.trackDecorationDrawable = obtainTintedStyledAttributes.getDrawable(3);
        ColorStateList colorStateList4 = obtainTintedStyledAttributes.getColorStateList(4);
        this.trackDecorationTintList = colorStateList4;
        PorterDuff.Mode parseTintMode2 = ViewUtils.parseTintMode(obtainTintedStyledAttributes.getInt(5, -1), PorterDuff.Mode.SRC_IN);
        obtainTintedStyledAttributes.recycle();
        invalidate();
        this.thumbDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.thumbDrawable, colorStateList, this.mThumbTintMode);
        this.thumbIconDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.thumbIconDrawable, colorStateList3, parseTintMode);
        updateDrawableTints();
        Drawable compositeTwoLayeredDrawable = DrawableUtils.compositeTwoLayeredDrawable(this.thumbDrawable, this.thumbIconDrawable);
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.setCallback(null);
        }
        this.mThumbDrawable = compositeTwoLayeredDrawable;
        if (compositeTwoLayeredDrawable != null) {
            compositeTwoLayeredDrawable.setCallback(this);
        }
        requestLayout();
        refreshDrawableState();
        this.trackDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.trackDrawable, colorStateList2, this.mTrackTintMode);
        this.trackDecorationDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.trackDecorationDrawable, colorStateList4, parseTintMode2);
        updateDrawableTints();
        Drawable drawable2 = this.trackDrawable;
        if (drawable2 != null && this.trackDecorationDrawable != null) {
            drawable2 = new LayerDrawable(new Drawable[]{this.trackDrawable, this.trackDecorationDrawable});
        } else if (drawable2 == null) {
            drawable2 = this.trackDecorationDrawable;
        }
        if (drawable2 != null) {
            drawable2.getIntrinsicWidth();
            requestLayout();
        }
        Drawable drawable3 = this.mTrackDrawable;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        this.mTrackDrawable = drawable2;
        if (drawable2 != null) {
            Drawable.ConstantState constantState = drawable2.getConstantState();
            if (constantState != null) {
                this.mTrackOnDrawable = constantState.newDrawable();
                this.mTrackOffDrawable = constantState.newDrawable();
            } else {
                this.mTrackOnDrawable = drawable2;
                this.mTrackOffDrawable = drawable2;
            }
            this.mTrackOnDrawable.setState(new int[]{android.R.attr.state_enabled, android.R.attr.state_checked});
            this.mTrackOffDrawable.setState(new int[]{android.R.attr.state_enabled, -16842912});
            drawable2.setCallback(this);
        }
        requestLayout();
    }
}
