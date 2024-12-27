package com.android.server.display;

import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class DisplayManagerService$BinderService$$ExternalSyntheticOutline0 {
    public static void m(String str, StringBuilder sb, boolean z) {
        sb.append(PowerManagerUtil.callerInfoToString(z));
        Slog.d(str, sb.toString());
    }
}
