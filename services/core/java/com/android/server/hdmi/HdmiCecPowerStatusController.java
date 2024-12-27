package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HdmiCecPowerStatusController {
    public final HdmiControlService mHdmiControlService;
    public int mPowerStatus = 1;

    public HdmiCecPowerStatusController(HdmiControlService hdmiControlService) {
        this.mHdmiControlService = hdmiControlService;
    }

    public final void setPowerStatus(int i, boolean z) {
        if (i == this.mPowerStatus) {
            return;
        }
        this.mPowerStatus = i;
        if (z) {
            HdmiControlService hdmiControlService = this.mHdmiControlService;
            if (hdmiControlService.getCecVersion() >= 6) {
                int i2 = this.mPowerStatus;
                Iterator it = ((ArrayList) hdmiControlService.getAllCecLocalDevices()).iterator();
                while (it.hasNext()) {
                    hdmiControlService.sendCecCommand(HdmiCecMessage.build(((HdmiCecLocalDevice) it.next()).getDeviceInfo().getLogicalAddress(), 15, 144, new byte[]{(byte) (i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)}), null);
                }
            }
        }
    }
}
