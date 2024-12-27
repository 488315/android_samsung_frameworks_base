package com.android.server.hdmi;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class HdmiControlService$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HdmiControlService f$0;

    public /* synthetic */ HdmiControlService$$ExternalSyntheticLambda1(
            HdmiControlService hdmiControlService, int i) {
        this.$r8$classId = i;
        this.f$0 = hdmiControlService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        HdmiControlService hdmiControlService = this.f$0;
        switch (i) {
            case 0:
                if (hdmiControlService.mPowerManager.mPowerManager.isInteractive()
                        && hdmiControlService.isPowerStandbyOrTransient()) {
                    hdmiControlService.mPowerStatusController.setPowerStatus(0, true);
                    if (hdmiControlService.mAddressAllocated) {
                        Iterator it =
                                ((ArrayList) hdmiControlService.getAllCecLocalDevices()).iterator();
                        while (it.hasNext()) {
                            ((HdmiCecLocalDevice) it.next()).startQueuedActions();
                        }
                        break;
                    }
                }
                break;
            case 1:
                hdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
                break;
            case 2:
                hdmiControlService.mPowerStatusController.setPowerStatus(0, true);
                break;
            default:
                hdmiControlService.mPowerStatusController.setPowerStatus(1, true);
                break;
        }
    }
}
