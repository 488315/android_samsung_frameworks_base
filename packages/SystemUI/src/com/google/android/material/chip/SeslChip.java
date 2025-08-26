package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import com.android.systemui.R;
import com.google.android.material.resources.TextAppearance;

/* loaded from: classes4.dex */
public class SeslChip extends Chip {
    public SeslChip(Context context) {
        this(context, null);
    }

    @Override // com.google.android.material.chip.Chip
    public final Drawable getBackgroundDrawable() {
        return this.chipDrawable;
    }

    @Override // android.widget.TextView
    public final CharSequence getText() {
        return this.chipDrawable.text;
    }

    public final void setInternalsAlpha(int i) {
        Drawable drawable;
        Drawable drawable2;
        ColorStateList colorStateList;
        ChipDrawable chipDrawable = this.chipDrawable;
        TextAppearance textAppearance = chipDrawable.textDrawableHelper.textAppearance;
        if (textAppearance != null && (colorStateList = textAppearance.textColor) != null) {
            ColorStateList colorStateListWithAlpha = colorStateList.withAlpha(i);
            TextAppearance textAppearance2 = chipDrawable.textDrawableHelper.textAppearance;
            if (textAppearance2 != null) {
                textAppearance2.textColor = colorStateListWithAlpha;
                chipDrawable.invalidateSelf();
            }
        }
        ChipDrawable chipDrawable2 = this.chipDrawable;
        Drawable drawableUnwrap = null;
        Drawable drawableUnwrap2 = (chipDrawable2 == null || (drawable2 = chipDrawable2.closeIcon) == null) ? null : DrawableCompat.unwrap(drawable2);
        if (drawableUnwrap2 != null) {
            drawableUnwrap2.setAlpha(i);
        }
        ChipDrawable chipDrawable3 = this.chipDrawable;
        if (chipDrawable3 != null && (drawable = chipDrawable3.chipIcon) != null) {
            drawableUnwrap = DrawableCompat.unwrap(drawable);
        }
        if (drawableUnwrap == null) {
            return;
        }
        drawableUnwrap.setAlpha(i);
    }

    @Override // android.widget.TextView, android.view.View
    public final void setSelected(boolean z) {
        super.setSelected(z);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            int intrinsicWidth = this.chipDrawable.getIntrinsicWidth();
            ChipDrawable chipDrawable = this.chipDrawable;
            layoutParams.width = intrinsicWidth + ((int) (chipDrawable != null ? chipDrawable.chipEndPadding : 0.0f));
            setLayoutParams(layoutParams);
        }
    }

    @Override // com.google.android.material.chip.Chip, android.widget.TextView
    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            super.setText(charSequence, bufferType);
            return;
        }
        chipDrawable.setText(charSequence);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            int intrinsicWidth = this.chipDrawable.getIntrinsicWidth();
            ChipDrawable chipDrawable2 = this.chipDrawable;
            layoutParams.width = intrinsicWidth + ((int) (chipDrawable2 != null ? chipDrawable2.chipEndPadding : 0.0f));
            setLayoutParams(layoutParams);
        }
    }

    public SeslChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipStyle);
    }

    public SeslChip(Context context, AttributeSet attributeSet, int i) {
        super(new ContextThemeWrapper(context, R.style.SeslPeoplePickerStyle), attributeSet, i);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.shouldDrawText = true;
            if (chipDrawable.closeIconTint != null) {
                chipDrawable.closeIconTint = null;
                if (chipDrawable.showsCloseIcon()) {
                    chipDrawable.closeIcon.setTintList(null);
                }
                chipDrawable.onStateChange(chipDrawable.getState());
            }
            chipDrawable.hasChipIconTint = true;
            if (chipDrawable.chipIconTint != null) {
                chipDrawable.chipIconTint = null;
                if (chipDrawable.showsChipIcon()) {
                    chipDrawable.chipIcon.setTintList(null);
                }
                chipDrawable.onStateChange(chipDrawable.getState());
            }
        }
    }
}
