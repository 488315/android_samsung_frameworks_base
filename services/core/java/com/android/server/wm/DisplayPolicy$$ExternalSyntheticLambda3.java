package com.android.server.wm;

import com.android.internal.policy.GestureNavigationSettingsObserver;

public final /* synthetic */ class DisplayPolicy$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GestureNavigationSettingsObserver f$0;

    public /* synthetic */ DisplayPolicy$$ExternalSyntheticLambda3(
            GestureNavigationSettingsObserver gestureNavigationSettingsObserver, int i) {
        this.$r8$classId = i;
        this.f$0 = gestureNavigationSettingsObserver;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        GestureNavigationSettingsObserver gestureNavigationSettingsObserver = this.f$0;
        switch (i) {
            case 0:
                gestureNavigationSettingsObserver.register();
                break;
            default:
                gestureNavigationSettingsObserver.unregister();
                break;
        }
    }
}
