package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.core.graphics.drawable.WrappedDrawableApi14;
import com.android.systemui.R;
import com.google.android.material.resources.TextAppearance;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final void setInternalsAlpha(int i) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
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
        Drawable drawable4 = null;
        if (chipDrawable2 == null || (drawable3 = chipDrawable2.closeIcon) == 0) {
            drawable = null;
        } else {
            boolean z = drawable3 instanceof WrappedDrawable;
            drawable = drawable3;
            if (z) {
                drawable = ((WrappedDrawableApi14) ((WrappedDrawable) drawable3)).mDrawable;
            }
        }
        if (drawable != null) {
            drawable.setAlpha(i);
        }
        ChipDrawable chipDrawable3 = this.chipDrawable;
        if (chipDrawable3 != null && (drawable2 = chipDrawable3.chipIcon) != 0) {
            boolean z2 = drawable2 instanceof WrappedDrawable;
            Drawable drawable5 = drawable2;
            if (z2) {
                drawable5 = ((WrappedDrawableApi14) ((WrappedDrawable) drawable2)).mDrawable;
            }
            drawable4 = drawable5;
        }
        if (drawable4 == null) {
            return;
        }
        drawable4.setAlpha(i);
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
