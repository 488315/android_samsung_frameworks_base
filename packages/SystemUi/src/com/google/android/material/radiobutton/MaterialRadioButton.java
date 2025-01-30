package com.google.android.material.radiobutton;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatRadioButton;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MaterialRadioButton extends AppCompatRadioButton {
    public static final int[][] ENABLED_CHECKED_STATES = {new int[]{R.attr.state_enabled, R.attr.state_checked}, new int[]{R.attr.state_enabled, -16842912}, new int[]{-16842910, R.attr.state_checked}, new int[]{-16842910, -16842912}};
    public ColorStateList materialThemeColorsTintList;
    public boolean useMaterialThemeColors;

    public MaterialRadioButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && getButtonTintList() == null) {
            this.useMaterialThemeColors = true;
            if (this.materialThemeColorsTintList == null) {
                int color = MaterialColors.getColor(this, com.android.systemui.R.attr.colorControlActivated);
                int color2 = MaterialColors.getColor(this, com.android.systemui.R.attr.colorOnSurface);
                int color3 = MaterialColors.getColor(this, com.android.systemui.R.attr.colorSurface);
                this.materialThemeColorsTintList = new ColorStateList(ENABLED_CHECKED_STATES, new int[]{MaterialColors.layer(1.0f, color3, color), MaterialColors.layer(0.54f, color3, color2), MaterialColors.layer(0.38f, color3, color2), MaterialColors.layer(0.38f, color3, color2)});
            }
            setButtonTintList(this.materialThemeColorsTintList);
        }
    }

    public MaterialRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.radioButtonStyle);
    }

    public MaterialRadioButton(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019109), attributeSet, i);
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.MaterialRadioButton, i, 2132019109, new int[0]);
        if (obtainStyledAttributes.hasValue(0)) {
            setButtonTintList(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 0));
        }
        this.useMaterialThemeColors = obtainStyledAttributes.getBoolean(1, false);
        obtainStyledAttributes.recycle();
    }
}
