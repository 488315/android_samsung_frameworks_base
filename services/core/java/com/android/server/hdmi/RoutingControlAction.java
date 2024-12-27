package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Slog;

import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RoutingControlAction extends HdmiCecFeatureAction {
    static final int STATE_WAIT_FOR_ROUTING_INFORMATION = 1;
    public int mCurrentRoutingPath;
    public final boolean mNotifyInputChange;

    public RoutingControlAction(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            int i,
            IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mCurrentRoutingPath = i;
        this.mNotifyInputChange = iHdmiControlCallback == null;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != i || i2 == 0) {
            Slog.w("CEC", "Timer in a wrong state. Ignored.");
            return;
        }
        if (i != 1) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(
                    i, "Invalid timeoutState (", ").", "CEC");
            return;
        }
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) this.mSource;
        hdmiCecLocalDeviceTv.setPrevPortId(hdmiCecLocalDeviceTv.getActivePortId());
        hdmiCecLocalDeviceTv.updateActiveInput(this.mCurrentRoutingPath, this.mNotifyInputChange);
        this.mService.sendCecCommand(
                HdmiCecMessage.build(
                        getSourceAddress(),
                        15,
                        134,
                        HdmiCecMessageBuilder.physicalAddressToParam(this.mCurrentRoutingPath)),
                null);
        finishWithCallback(0);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int i = hdmiCecMessage.mOpcode;
        if (this.mState != 1 || i != 129) {
            return false;
        }
        int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
        if (!HdmiUtils.isInActiveRoutingPath(this.mCurrentRoutingPath, twoBytesToInt)) {
            return true;
        }
        this.mCurrentRoutingPath = twoBytesToInt;
        this.mSource.removeActionExcept(RoutingControlAction.class, this);
        addTimer(this.mState, 1000);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        addTimer(1, 1000);
    }
}
