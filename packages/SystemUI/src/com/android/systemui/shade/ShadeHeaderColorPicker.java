package com.android.systemui.shade;

import android.content.Context;
import android.view.ContextThemeWrapper;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeHeaderColorPicker {
    public final Context context;

    public ShadeHeaderColorPicker(Context context) {
        this.context = context;
        if (DeviceState.isOpenTheme(context)) {
            int color = this.context.getColor(R.color.sec_qs_header_tint_color);
            int i = (color >> 16) & 255;
            int i2 = (color >> 8) & 255;
            int i3 = color & 255;
            Math.sqrt((i3 * i3 * 0.068d) + (i2 * i2 * 0.691d) + (i * i * 0.241d));
        }
        new DualToneHandler(new ContextThemeWrapper(this.context, R.style.Theme_SystemUI_QuickSettings_Header));
    }
}
