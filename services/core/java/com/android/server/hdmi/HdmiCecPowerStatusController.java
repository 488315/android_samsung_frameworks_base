package com.android.server.hdmi;

import java.util.Iterator;

/* loaded from: classes2.dex */
public class HdmiCecPowerStatusController {
  public final HdmiControlService mHdmiControlService;
  public int mPowerStatus = 1;

  public HdmiCecPowerStatusController(HdmiControlService hdmiControlService) {
    this.mHdmiControlService = hdmiControlService;
  }

  public int getPowerStatus() {
    return this.mPowerStatus;
  }

  public boolean isPowerStatusOn() {
    return this.mPowerStatus == 0;
  }

  public boolean isPowerStatusStandby() {
    return this.mPowerStatus == 1;
  }

  public boolean isPowerStatusTransientToOn() {
    return this.mPowerStatus == 2;
  }

  public boolean isPowerStatusTransientToStandby() {
    return this.mPowerStatus == 3;
  }

  public void setPowerStatus(int i) {
    setPowerStatus(i, true);
  }

  public void setPowerStatus(int i, boolean z) {
    if (i == this.mPowerStatus) {
      return;
    }
    this.mPowerStatus = i;
    if (!z || this.mHdmiControlService.getCecVersion() < 6) {
      return;
    }
    sendReportPowerStatus(this.mPowerStatus);
  }

  public final void sendReportPowerStatus(int i) {
    Iterator it = this.mHdmiControlService.getAllCecLocalDevices().iterator();
    while (it.hasNext()) {
      this.mHdmiControlService.sendCecCommand(
          HdmiCecMessageBuilder.buildReportPowerStatus(
              ((HdmiCecLocalDevice) it.next()).getDeviceInfo().getLogicalAddress(), 15, i));
    }
  }
}
