package com.android.server.enterprise.security;

import android.os.Bundle;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.ContainerProxy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda51
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda51(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void runOrThrow() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PasswordPolicy passwordPolicy = (PasswordPolicy) obj;
                ((SemDesktopModeManager)
                                passwordPolicy
                                        .mContext
                                        .getApplicationContext()
                                        .getSystemService("desktopmode"))
                        .unregisterBlocker(passwordPolicy.mBlocker);
                Log.d("PasswordPolicy", "registerDexBlocker was unregistered");
                break;
            case 1:
                PasswordPolicy passwordPolicy2 = (PasswordPolicy) obj;
                ((SemDesktopModeManager)
                                passwordPolicy2
                                        .mContext
                                        .getApplicationContext()
                                        .getSystemService("desktopmode"))
                        .registerBlocker(passwordPolicy2.mBlocker);
                Log.d("PasswordPolicy", "registerDexBlocker was registered");
                break;
            default:
                Bundle bundle = (Bundle) obj;
                ContainerProxy.sendEvent("knox.container.proxy.EVENT_LOCK_TIMEOUT", bundle);
                ContainerProxy.sendCommand("knox.container.proxy.COMMAND_ENFORCE_PASSWORD", bundle);
                break;
        }
    }
}
