package com.android.systemui.statusbar.connectivity;

import androidx.lifecycle.Lifecycle;

public final /* synthetic */ class AccessPointControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AccessPointControllerImpl f$0;

    public /* synthetic */ AccessPointControllerImpl$$ExternalSyntheticLambda0(AccessPointControllerImpl accessPointControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = accessPointControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AccessPointControllerImpl accessPointControllerImpl = this.f$0;
        switch (i) {
            case 0:
                accessPointControllerImpl.mLifecycle.setCurrentState(Lifecycle.State.STARTED);
                break;
            case 1:
                accessPointControllerImpl.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
                break;
            case 2:
                accessPointControllerImpl.mLifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                break;
            default:
                accessPointControllerImpl.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
                break;
        }
    }
}
