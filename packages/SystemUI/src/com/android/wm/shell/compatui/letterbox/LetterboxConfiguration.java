package com.android.wm.shell.compatui.letterbox;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

/* loaded from: classes3.dex */
public final class LetterboxConfiguration {
    public final Context context;
    public int letterboxActivityCornersRadius;
    public final int letterboxActivityDefaultCornersRadius;
    public Color letterboxBackgroundColorOverride;
    public Integer letterboxBackgroundColorResourceIdOverride;

    public LetterboxConfiguration(Context context) throws Resources.NotFoundException {
        this.context = context;
        int integer = context.getResources().getInteger(R.integer.config_notificationsBatteryLedOn);
        this.letterboxActivityDefaultCornersRadius = integer;
        this.letterboxActivityCornersRadius = integer;
    }

    public final Color getLetterboxBackgroundColor() {
        Color color = this.letterboxBackgroundColorOverride;
        if (color != null) {
            color.getClass();
            return color;
        }
        Integer numValueOf = this.letterboxBackgroundColorResourceIdOverride;
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(R.color.dim_foreground_disabled_holo_light);
        }
        Resources resources = this.context.getResources();
        numValueOf.getClass();
        return Color.valueOf(resources.getColor(numValueOf.intValue(), null));
    }
}
