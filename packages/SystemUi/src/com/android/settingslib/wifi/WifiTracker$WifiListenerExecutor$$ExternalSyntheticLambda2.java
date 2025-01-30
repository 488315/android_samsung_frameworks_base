package com.android.settingslib.wifi;

import android.util.Log;
import com.android.settingslib.wifi.WifiTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ WifiTracker.WifiListenerExecutor f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(WifiTracker.WifiListenerExecutor wifiListenerExecutor, String str, Runnable runnable) {
        this.f$0 = wifiListenerExecutor;
        this.f$1 = str;
        this.f$2 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WifiTracker.WifiListenerExecutor wifiListenerExecutor = this.f$0;
        String str = this.f$1;
        Runnable runnable = this.f$2;
        if (wifiListenerExecutor.this$0.mRegistered) {
            if (WifiTracker.isVerboseLoggingEnabled()) {
                Log.i("WifiTracker", str);
            }
            runnable.run();
        }
    }
}
