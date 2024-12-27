package com.android.systemui.edgelighting.data.style;

import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public enum EdgeLightingStyleOption {
    EFFECT(R.string.edge_lighting_style_effect, R.drawable.edge_lighting_setting_icon_style),
    COLOR(R.string.edge_lighting_header_color, R.drawable.edge_lighting_setting_icon_select_color),
    TRANSPARENCY(R.string.edge_lighting_transparency, R.drawable.edge_lighting_setting_transparency),
    WIDTH(R.string.edge_lighting_header_width, R.drawable.edge_lighting_setting_icon_select_width),
    DURATION(R.string.edge_lighting_header_duration, R.drawable.edge_lighting_setting_duration);

    private int mIconResID;
    private int mTitleID;

    EdgeLightingStyleOption(int i, int i2) {
        this.mIconResID = i2;
        this.mTitleID = i;
    }

    public final int getTitleStringID() {
        return this.mTitleID;
    }
}
