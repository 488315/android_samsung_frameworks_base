package com.android.server.display;

import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;

public abstract /* synthetic */
class DisplayManagerService$BinderService$$ExternalSyntheticOutline0 {
    public static void m(String str, StringBuilder sb, boolean z) {
        sb.append(PowerManagerUtil.callerInfoToString(z));
        Slog.d(str, sb.toString());
    }
}
