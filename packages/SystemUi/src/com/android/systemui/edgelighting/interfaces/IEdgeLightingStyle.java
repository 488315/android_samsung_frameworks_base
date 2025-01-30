package com.android.systemui.edgelighting.interfaces;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyleOption;
import com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface IEdgeLightingStyle {
    String getKey();

    Drawable getRoundedIcon(EdgeLightingStyleActivity edgeLightingStyleActivity);

    CharSequence getTitle(Context context);

    boolean isSupportEffect();

    boolean isSupportOption(EdgeLightingStyleOption edgeLightingStyleOption);
}
