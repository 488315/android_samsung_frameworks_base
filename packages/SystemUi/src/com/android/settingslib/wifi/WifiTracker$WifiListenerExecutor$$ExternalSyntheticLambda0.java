package com.android.settingslib.wifi;

import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.wifi.WifiTracker;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiTracker.WifiListener f$0;

    public /* synthetic */ WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(WifiTracker.WifiListener wifiListener, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WifiTracker.WifiListenerExecutor wifiListenerExecutor = (WifiTracker.WifiListenerExecutor) this.f$0;
                WifiTracker.WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
                Objects.requireNonNull(wifiListener);
                ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor, "Invoking onConnectedChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener, 0)));
                break;
            default:
                WifiTracker.WifiListenerExecutor wifiListenerExecutor2 = (WifiTracker.WifiListenerExecutor) this.f$0;
                WifiTracker.WifiListener wifiListener2 = wifiListenerExecutor2.mDelegatee;
                Objects.requireNonNull(wifiListener2);
                ThreadUtils.postOnMainThread(new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2(wifiListenerExecutor2, "Invoking onAccessPointsChanged callback", new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(wifiListener2, 1)));
                break;
        }
    }
}
