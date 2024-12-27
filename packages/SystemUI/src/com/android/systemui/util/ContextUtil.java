package com.android.systemui.util;

import android.app.ActivityManager;
import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ContextUtil {
    public static final int $stable = 0;
    public static final ContextUtil INSTANCE = new ContextUtil();

    private ContextUtil() {
    }

    public final ActivityManager getActivityManager(Context context) {
        return (ActivityManager) context.getSystemService(ActivityManager.class);
    }
}
