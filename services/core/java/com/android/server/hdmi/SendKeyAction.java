package com.android.server.hdmi;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SendKeyAction extends HdmiCecFeatureAction {
    public int mLastKeycode;
    public long mLastSendKeyTime;
    public final int mTargetAddress;

    public SendKeyAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i, int i2) {
        super(hdmiCecLocalDevice);
        this.mTargetAddress = i;
        this.mLastKeycode = i2;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != 1) {
            if (i2 != 2) {
                Slog.w("SendKeyAction", "Not in a valid state");
                return;
            } else {
                sendKeyUp();
                finish(true);
                return;
            }
        }
        ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
        this.mState = 2;
        sendKeyDown(this.mLastKeycode);
        this.mLastSendKeyTime = System.currentTimeMillis();
        addTimer(this.mState, 1000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        return false;
    }

    public final void processKeyEvent(int i, boolean z) {
        int i2 = this.mState;
        if (i2 != 1 && i2 != 2) {
            Slog.w("SendKeyAction", "Not in a valid state");
            return;
        }
        if (!z) {
            if (i == this.mLastKeycode) {
                sendKeyUp();
                finish(true);
                return;
            }
            return;
        }
        if (i != this.mLastKeycode) {
            sendKeyDown(i);
            this.mLastSendKeyTime = System.currentTimeMillis();
            if (!HdmiCecKeycode.isRepeatableKey(i)) {
                sendKeyUp();
                finish(true);
                return;
            }
        } else if (System.currentTimeMillis() - this.mLastSendKeyTime >= 300) {
            sendKeyDown(i);
            this.mLastSendKeyTime = System.currentTimeMillis();
        }
        ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
        addTimer(this.mState, 1000);
        this.mLastKeycode = i;
    }

    public final void sendKeyDown(int i) {
        byte[] androidKeyToCecKey = HdmiCecKeycode.androidKeyToCecKey(i);
        if (androidKeyToCecKey == null) {
            return;
        }
        int i2 = this.mTargetAddress;
        if (i2 != 5 || this.mSource.getDeviceInfo().getLogicalAddress() == 0) {
            sendCommand(HdmiCecMessage.build(getSourceAddress(), i2, 68, androidKeyToCecKey));
        } else {
            sendCommand(
                    HdmiCecMessage.build(getSourceAddress(), i2, 68, androidKeyToCecKey),
                    new HdmiControlService
                            .SendMessageCallback() { // from class:
                                                     // com.android.server.hdmi.SendKeyAction.1
                        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                        public final void onSendCompleted(int i3) {
                            if (i3 == 1) {
                                HdmiLogger.debug(
                                        "AVR did not acknowledge <User Control Pressed>",
                                        new Object[0]);
                                SendKeyAction.this.mSource.mService.setSystemAudioActivated(false);
                            }
                        }
                    });
        }
    }

    public final void sendKeyUp() {
        boolean isVolumeKeycode = HdmiCecKeycode.isVolumeKeycode(this.mLastKeycode);
        int i = this.mTargetAddress;
        if (isVolumeKeycode && this.mSource.mService.isAbsoluteVolumeBehaviorEnabled()) {
            sendCommand(
                    HdmiCecMessage.build(getSourceAddress(), i, 69),
                    new HdmiControlService
                            .SendMessageCallback() { // from class:
                                                     // com.android.server.hdmi.SendKeyAction$$ExternalSyntheticLambda0
                        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                        public final void onSendCompleted(int i2) {
                            SendKeyAction sendKeyAction = SendKeyAction.this;
                            sendKeyAction.sendCommand(
                                    HdmiCecMessage.build(
                                            sendKeyAction.getSourceAddress(),
                                            sendKeyAction.mSource.findAudioReceiverAddress(),
                                            113));
                        }
                    });
        } else {
            sendCommand(HdmiCecMessage.build(getSourceAddress(), i, 69));
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        sendKeyDown(this.mLastKeycode);
        this.mLastSendKeyTime = System.currentTimeMillis();
        if (HdmiCecKeycode.isRepeatableKey(this.mLastKeycode)) {
            this.mState = 1;
            addTimer(1, 400);
        } else {
            sendKeyUp();
            finish(true);
        }
    }
}
