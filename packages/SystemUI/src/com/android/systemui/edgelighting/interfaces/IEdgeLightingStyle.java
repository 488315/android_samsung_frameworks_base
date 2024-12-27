package com.android.systemui.edgelighting.interfaces;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyleOption;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface IEdgeLightingStyle {
    String getKey();

    Drawable getRoundedIcon(Context context);

    CharSequence getTitle(Context context);

    boolean isSupportEffect();

    boolean isSupportOption(EdgeLightingStyleOption edgeLightingStyleOption);
}
