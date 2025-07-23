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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            ColorStateList withAlpha = colorStateList.withAlpha(i);
            TextAppearance textAppearance2 = chipDrawable.textDrawableHelper.textAppearance;
            if (textAppearance2 != null) {
                textAppearance2.textColor = withAlpha;
                chipDrawable.invalidateSelf();
            }
        }
        ChipDrawable chipDrawable2 = this.chipDrawable;
        Drawable drawable3 = null;
        Drawable unwrap = (chipDrawable2 == null || (drawable2 = chipDrawable2.closeIcon) == null) ? null : DrawableCompat.unwrap(drawable2);
        if (unwrap != null) {
            unwrap.setAlpha(i);
        }
        ChipDrawable chipDrawable3 = this.chipDrawable;
        if (chipDrawable3 != null && (drawable = chipDrawable3.chipIcon) != null) {
            drawable3 = DrawableCompat.unwrap(drawable);
        }
        if (drawable3 == null) {
            return;
        }
        drawable3.setAlpha(i);
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
