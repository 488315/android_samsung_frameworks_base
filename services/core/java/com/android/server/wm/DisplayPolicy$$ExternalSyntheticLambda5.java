package com.android.server.wm;

import com.android.internal.policy.ForceShowNavBarSettingsObserver;

public final /* synthetic */ class DisplayPolicy$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ForceShowNavBarSettingsObserver f$0;

    public /* synthetic */ DisplayPolicy$$ExternalSyntheticLambda5(
            ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver, int i) {
        this.$r8$classId = i;
        this.f$0 = forceShowNavBarSettingsObserver;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver = this.f$0;
        switch (i) {
            case 0:
                forceShowNavBarSettingsObserver.register();
                break;
            default:
                forceShowNavBarSettingsObserver.unregister();
                break;
        }
    }
}
