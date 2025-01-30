package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Slog;

/* loaded from: classes2.dex */
public final class RoutingControlAction extends HdmiCecFeatureAction {
    static final int STATE_WAIT_FOR_ROUTING_INFORMATION = 1;
    public int mCurrentRoutingPath;
    public final boolean mNotifyInputChange;

    public RoutingControlAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i, IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mCurrentRoutingPath = i;
        this.mNotifyInputChange = iHdmiControlCallback == null;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean start() {
        this.mState = 1;
        addTimer(1, 1000);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int opcode = hdmiCecMessage.getOpcode();
        byte[] params = hdmiCecMessage.getParams();
        if (this.mState != 1 || opcode != 129) {
            return false;
        }
        int twoBytesToInt = HdmiUtils.twoBytesToInt(params);
        if (!HdmiUtils.isInActiveRoutingPath(this.mCurrentRoutingPath, twoBytesToInt)) {
            return true;
        }
        this.mCurrentRoutingPath = twoBytesToInt;
        removeActionExcept(RoutingControlAction.class, this);
        addTimer(this.mState, 1000);
        return true;
    }

    public final void updateActiveInput() {
        HdmiCecLocalDeviceTv m27tv = m27tv();
        m27tv.setPrevPortId(m27tv.getActivePortId());
        m27tv.updateActiveInput(this.mCurrentRoutingPath, this.mNotifyInputChange);
    }

    public final void sendSetStreamPath() {
        sendCommand(HdmiCecMessageBuilder.buildSetStreamPath(getSourceAddress(), this.mCurrentRoutingPath));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != i || i2 == 0) {
            Slog.w("CEC", "Timer in a wrong state. Ignored.");
            return;
        }
        if (i == 1) {
            updateActiveInput();
            sendSetStreamPath();
            finishWithCallback(0);
        } else {
            Slog.e("CEC", "Invalid timeoutState (" + i + ").");
        }
    }
}
