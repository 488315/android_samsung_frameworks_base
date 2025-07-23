package com.android.wm.shell.compatui.letterbox;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxConfiguration {
    public final Context context;
    public int letterboxActivityCornersRadius;
    public final int letterboxActivityDefaultCornersRadius;
    public Color letterboxBackgroundColorOverride;
    public Integer letterboxBackgroundColorResourceIdOverride;

    public LetterboxConfiguration(Context context) {
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
        Integer num = this.letterboxBackgroundColorResourceIdOverride;
        if (num == null) {
            num = Integer.valueOf(R.color.dim_foreground_disabled_holo_light);
        }
        Resources resources = this.context.getResources();
        num.getClass();
        return Color.valueOf(resources.getColor(num.intValue(), null));
    }
}
