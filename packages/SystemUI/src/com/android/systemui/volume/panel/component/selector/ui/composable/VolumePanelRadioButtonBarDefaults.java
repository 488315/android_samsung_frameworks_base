package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.compose.ui.unit.Dp;

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
