package com.android.systemui.flags;

import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;

public final class SystemExitRestarter implements Restarter {
    public SystemExitRestarter(IStatusBarService iStatusBarService) {
    }

    @Override // com.android.systemui.flags.Restarter
    public final void restartSystemUI(String str) {
        Log.d("SysUIFlags", "Restarting SystemUI: ".concat(str));
        System.exit(0);
    }
}
