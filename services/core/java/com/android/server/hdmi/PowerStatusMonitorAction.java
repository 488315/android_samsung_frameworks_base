package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerStatusMonitorAction extends HdmiCecFeatureAction {
    public final SparseIntArray mPowerStatus;

    public PowerStatusMonitorAction(HdmiCecLocalDevice hdmiCecLocalDevice) {
        super(hdmiCecLocalDevice);
        this.mPowerStatus = new SparseIntArray();
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            queryPowerStatus$1();
        } else {
            for (int i3 = 0; i3 < this.mPowerStatus.size(); i3++) {
                updatePowerStatus(this.mPowerStatus.keyAt(i3), -1, false);
            }
            this.mPowerStatus.clear();
            this.mState = 2;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || hdmiCecMessage.mOpcode != 144) {
            return false;
        }
        SparseIntArray sparseIntArray = this.mPowerStatus;
        int i = hdmiCecMessage.mSource;
        if (sparseIntArray.get(i, -2) == -2) {
            return false;
        }
        updatePowerStatus(i, hdmiCecMessage.mParams[0] & 255, true);
        return true;
    }

    public final void queryPowerStatus$1() {
        HdmiControlService hdmiControlService;
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        List deviceInfoList = hdmiCecLocalDevice.mService.mHdmiCecNetwork.getDeviceInfoList();
        this.mPowerStatus.clear();
        ArrayList arrayList = (ArrayList) deviceInfoList;
        Iterator it = arrayList.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            hdmiControlService = hdmiCecLocalDevice.mService;
            if (!hasNext) {
                break;
            }
            HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) it.next();
            if (hdmiControlService.getCecVersion() < 6 || hdmiDeviceInfo.getCecVersion() < 6) {
                this.mPowerStatus.append(
                        hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            HdmiDeviceInfo hdmiDeviceInfo2 = (HdmiDeviceInfo) it2.next();
            if (hdmiControlService.getCecVersion() < 6 || hdmiDeviceInfo2.getCecVersion() < 6) {
                final int logicalAddress = hdmiDeviceInfo2.getLogicalAddress();
                this.mService.sendCecCommand(
                        HdmiCecMessage.build(getSourceAddress(), logicalAddress, 143),
                        new HdmiControlService
                                .SendMessageCallback() { // from class:
                                                         // com.android.server.hdmi.PowerStatusMonitorAction.1
                            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                            public final void onSendCompleted(int i) {
                                if (i != 0) {
                                    PowerStatusMonitorAction.this.updatePowerStatus(
                                            logicalAddress, -1, true);
                                }
                            }
                        });
            }
        }
        this.mState = 1;
        addTimer(2, 60000);
        addTimer(1, 5000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        queryPowerStatus$1();
    }

    public final void updatePowerStatus(int i, int i2, boolean z) {
        this.mSource.mService.mHdmiCecNetwork.updateDevicePowerStatus(i, i2);
        if (z) {
            this.mPowerStatus.delete(i);
        }
    }
}
