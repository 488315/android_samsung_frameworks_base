package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumePanelRadioButtonBarDefaults {
    public static final float DefaultIndicatorBackgroundCornerRadius;
    public static final float DefaultIndicatorBackgroundPadding;
    public static final float DefaultIndicatorCornerRadius;
    public static final float DefaultLabelIndicatorBackgroundSpacing;
    public static final float DefaultSpacing;
    public static final VolumePanelRadioButtonBarDefaults INSTANCE = new VolumePanelRadioButtonBarDefaults();

    static {
        Dp.Companion companion = Dp.Companion;
        DefaultIndicatorBackgroundPadding = 8;
        DefaultSpacing = 24;
        DefaultLabelIndicatorBackgroundSpacing = 12;
        DefaultIndicatorCornerRadius = 20;
        DefaultIndicatorBackgroundCornerRadius = 28;
    }

    private VolumePanelRadioButtonBarDefaults() {
    }
}
