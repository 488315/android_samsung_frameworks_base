package com.google.android.material.switchmaterial;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SwitchMaterial extends SwitchCompat {
    public static final int[][] ENABLED_CHECKED_STATES = {new int[]{R.attr.state_enabled, R.attr.state_checked}, new int[]{R.attr.state_enabled, -16842912}, new int[]{-16842910, R.attr.state_checked}, new int[]{-16842910, -16842912}};
    public final ElevationOverlayProvider elevationOverlayProvider;
    public ColorStateList materialThemeColorsThumbTintList;
    public ColorStateList materialThemeColorsTrackTintList;
    public final boolean useMaterialThemeColors;

    public SwitchMaterial(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        boolean z = this.useMaterialThemeColors;
        int[][] iArr = ENABLED_CHECKED_STATES;
        if (z && this.mThumbTintList == null) {
            if (this.materialThemeColorsThumbTintList == null) {
                int color = MaterialColors.getColor(this, com.android.systemui.R.attr.colorSurface);
                int color2 = MaterialColors.getColor(this, com.android.systemui.R.attr.colorControlActivated);
                float dimension = getResources().getDimension(com.android.systemui.R.dimen.mtrl_switch_thumb_elevation);
                if (this.elevationOverlayProvider.elevationOverlayEnabled) {
                    float f = 0.0f;
                    for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        f += ViewCompat.Api21Impl.getElevation((View) parent);
                    }
                    dimension += f;
                }
                int compositeOverlayIfNeeded = this.elevationOverlayProvider.compositeOverlayIfNeeded(dimension, color);
                this.materialThemeColorsThumbTintList = new ColorStateList(iArr, new int[]{MaterialColors.layer(1.0f, color, color2), compositeOverlayIfNeeded, MaterialColors.layer(0.38f, color, color2), compositeOverlayIfNeeded});
            }
            this.mThumbTintList = this.materialThemeColorsThumbTintList;
            this.mHasThumbTint = true;
            applyThumbTint();
        }
        if (this.useMaterialThemeColors && this.mTrackTintList == null) {
            if (this.materialThemeColorsTrackTintList == null) {
                int color3 = MaterialColors.getColor(this, com.android.systemui.R.attr.colorSurface);
                int color4 = MaterialColors.getColor(this, com.android.systemui.R.attr.colorControlActivated);
                int color5 = MaterialColors.getColor(this, com.android.systemui.R.attr.colorOnSurface);
                this.materialThemeColorsTrackTintList = new ColorStateList(iArr, new int[]{MaterialColors.layer(0.54f, color3, color4), MaterialColors.layer(0.32f, color3, color5), MaterialColors.layer(0.12f, color3, color4), MaterialColors.layer(0.12f, color3, color5)});
            }
            this.mTrackTintList = this.materialThemeColorsTrackTintList;
            this.mHasTrackTint = true;
            applyTrackTint();
        }
    }

    public SwitchMaterial(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.switchStyle);
    }

    public SwitchMaterial(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019110), attributeSet, i);
        Context context2 = getContext();
        this.elevationOverlayProvider = new ElevationOverlayProvider(context2);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.SwitchMaterial, i, 2132019110, new int[0]);
        this.useMaterialThemeColors = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }
}
