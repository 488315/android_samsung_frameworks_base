package com.android.systemui.flags;

import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SystemExitRestarter implements Restarter {
    public SystemExitRestarter(IStatusBarService iStatusBarService) {
    }

    @Override // com.android.systemui.flags.Restarter
    public final void restartSystemUI(String str) {
        Log.d("SysUIFlags", "Restarting SystemUI: " + str);
        System.exit(0);
    }
}
