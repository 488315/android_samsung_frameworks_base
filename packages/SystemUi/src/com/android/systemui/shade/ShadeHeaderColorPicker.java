package com.android.systemui.shade;

import android.content.Context;
import android.view.ContextThemeWrapper;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeHeaderColorPicker {
    public float colorIntensity;
    public Context context;
    public final DualToneHandler dualToneHandler;

    public ShadeHeaderColorPicker(Context context) {
        this.context = context;
        update(context);
        this.dualToneHandler = new DualToneHandler(new ContextThemeWrapper(this.context, 2132018544));
    }

    public final int getClockColor() {
        if (DeviceState.isOpenTheme(this.context)) {
            return (this.colorIntensity > 1.0f ? 1 : (this.colorIntensity == 1.0f ? 0 : -1)) == 0 ? this.context.getColor(R.color.shade_header_clock_color) : this.context.getColor(R.color.shade_header_clock_color_dark);
        }
        return this.context.getColor(R.color.status_bar_clock_color);
    }

    public final void update(Context context) {
        float f;
        this.context = context;
        if (DeviceState.isOpenTheme(context)) {
            int color = this.context.getColor(R.color.sec_qs_header_tint_color);
            int i = (color >> 16) & 255;
            int i2 = (color >> 8) & 255;
            int i3 = color & 255;
            if (((int) Math.sqrt((i3 * i3 * 0.068d) + (i2 * i2 * 0.691d) + (i * i * 0.241d))) < 50) {
                f = 1.0f;
                this.colorIntensity = f;
            }
        }
        f = 0.0f;
        this.colorIntensity = f;
    }
}
