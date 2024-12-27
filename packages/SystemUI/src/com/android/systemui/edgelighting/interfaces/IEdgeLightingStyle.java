package com.android.systemui.edgelighting.interfaces;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyleOption;

public interface IEdgeLightingStyle {
    String getKey();

    Drawable getRoundedIcon(Context context);

    CharSequence getTitle(Context context);

    boolean isSupportEffect();

    boolean isSupportOption(EdgeLightingStyleOption edgeLightingStyleOption);
}
