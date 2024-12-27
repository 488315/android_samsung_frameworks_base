package com.android.systemui.process;

import android.os.Process;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ProcessWrapper {
    public static boolean isSystemUser() {
        return Process.myUserHandle().isSystem();
    }
}
