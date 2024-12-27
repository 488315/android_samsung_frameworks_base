package com.android.systemui.process;

import android.os.Process;

public final class ProcessWrapper {
    public static boolean isSystemUser() {
        return Process.myUserHandle().isSystem();
    }
}
