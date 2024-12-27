package com.android.systemui.flags;

import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SystemExitRestarter implements Restarter {
    public SystemExitRestarter(IStatusBarService iStatusBarService) {
    }

    @Override // com.android.systemui.flags.Restarter
    public final void restartSystemUI(String str) {
        Log.d("SysUIFlags", "Restarting SystemUI: ".concat(str));
        System.exit(0);
    }
}
