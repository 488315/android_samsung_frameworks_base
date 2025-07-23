package com.android.systemui.edgelighting.interfaces;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyleOption;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface IEdgeLightingStyle {
    String getKey();

    Drawable getRoundedIcon(Context context);

    CharSequence getTitle(Context context);

    boolean isSupportEffect();

    boolean isSupportOption(EdgeLightingStyleOption edgeLightingStyleOption);
}
