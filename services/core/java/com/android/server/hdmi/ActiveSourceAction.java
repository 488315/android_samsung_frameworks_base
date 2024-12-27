package com.android.server.hdmi;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ActiveSourceAction extends HdmiCecFeatureAction {
    public final int mDestination;

    public ActiveSourceAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        super(hdmiCecLocalDevice);
        this.mDestination = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {}

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        int sourceAddress = getSourceAddress();
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        int physicalAddress = hdmiCecLocalDevice.getDeviceInfo().getPhysicalAddress();
        HdmiCecMessage buildActiveSource =
                HdmiCecMessageBuilder.buildActiveSource(sourceAddress, physicalAddress);
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.sendCecCommand(buildActiveSource, null);
        HdmiCecLocalDeviceSource hdmiCecLocalDeviceSource =
                (HdmiCecLocalDeviceSource) hdmiCecLocalDevice;
        if (hdmiCecLocalDeviceSource.mDeviceType == 4) {
            hdmiControlService.sendCecCommand(
                    HdmiCecMessage.build(
                            sourceAddress, this.mDestination, 142, new byte[] {(byte) 0}),
                    null);
        }
        hdmiCecLocalDeviceSource.setActiveSource(
                sourceAddress, physicalAddress, "ActiveSourceAction");
        this.mState = 2;
        finish(true);
    }
}
