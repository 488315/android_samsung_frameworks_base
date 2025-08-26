package com.android.systemui.statusbar.phone;

import android.content.res.Resources;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$9$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl.AnonymousClass9 f$0;

    public /* synthetic */ CentralSurfacesImpl$9$$ExternalSyntheticLambda0(CentralSurfacesImpl.AnonymousClass9 anonymousClass9, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass9;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        int i = this.$r8$classId;
        CentralSurfacesImpl.AnonymousClass9 anonymousClass9 = this.f$0;
        switch (i) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mCommandQueueCallbacks.onCameraLaunchGestureDetected(centralSurfacesImpl.mLastCameraLaunchSource);
                break;
            case 1:
                CentralSurfacesImpl.this.mCommandQueueCallbacks.onWalletLaunchGestureDetected();
                break;
            case 2:
                CentralSurfacesImpl.this.mCommandQueueCallbacks.onEmergencyActionLaunchGestureDetected();
                break;
            case 3:
                CentralSurfacesImpl centralSurfacesImpl2 = CentralSurfacesImpl.this;
                centralSurfacesImpl2.mDeviceInteractive = true;
                centralSurfacesImpl2.mWakeUpCoordinator.setWakingUp(true);
                centralSurfacesImpl2.updateIsKeyguard(false);
                DozeParameters dozeParameters = centralSurfacesImpl2.mDozeParameters;
                boolean z = dozeParameters.getAlwaysOn() && !dozeParameters.getDisplayNeedsBlanking();
                centralSurfacesImpl2.mShouldDelayLockscreenTransitionFromAod = z;
                if (!z) {
                    anonymousClass9.startLockscreenTransitionFromAod();
                    break;
                }
                break;
            default:
                anonymousClass9.startLockscreenTransitionFromAod();
                break;
        }
    }
}
