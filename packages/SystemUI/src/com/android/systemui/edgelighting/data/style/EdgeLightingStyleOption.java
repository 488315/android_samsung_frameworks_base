package com.android.systemui.edgelighting.data.style;

import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
