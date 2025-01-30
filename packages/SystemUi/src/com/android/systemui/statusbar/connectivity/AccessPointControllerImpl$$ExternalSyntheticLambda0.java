package com.android.systemui.statusbar.connectivity;

import androidx.lifecycle.Lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class AccessPointControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AccessPointControllerImpl f$0;

    public /* synthetic */ AccessPointControllerImpl$$ExternalSyntheticLambda0(AccessPointControllerImpl accessPointControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = accessPointControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mLifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                break;
            case 1:
                this.f$0.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
                break;
            case 2:
                this.f$0.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
                break;
            default:
                this.f$0.mLifecycle.setCurrentState(Lifecycle.State.STARTED);
                break;
        }
    }
}
