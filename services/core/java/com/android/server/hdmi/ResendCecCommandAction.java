package com.android.server.hdmi;

import android.util.Slog;

import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

public final class ResendCecCommandAction extends HdmiCecFeatureAction {
    public final AnonymousClass1 mCallback;
    public final HdmiCecMessage mCommand;
    public final HdmiControlService.SendMessageCallback mResultCallback;
    public int mRetransmissionCount;

    public ResendCecCommandAction(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            HdmiCecMessage hdmiCecMessage,
            HdmiControlService.SendMessageCallback sendMessageCallback) {
        super(hdmiCecLocalDevice);
        this.mRetransmissionCount = 0;
        this.mCallback =
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.ResendCecCommandAction.1
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i) {
                        ResendCecCommandAction resendCecCommandAction = ResendCecCommandAction.this;
                        if (i != 0) {
                            int i2 = resendCecCommandAction.mRetransmissionCount;
                            resendCecCommandAction.mRetransmissionCount = i2 + 1;
                            if (i2 < 1) {
                                resendCecCommandAction.mState = 1;
                                resendCecCommandAction.addTimer(1, 300);
                                return;
                            }
                        }
                        HdmiControlService.SendMessageCallback sendMessageCallback2 =
                                resendCecCommandAction.mResultCallback;
                        if (sendMessageCallback2 != null) {
                            sendMessageCallback2.onSendCompleted(i);
                        }
                        resendCecCommandAction.finish(true);
                    }
                };
        this.mCommand = hdmiCecMessage;
        this.mResultCallback = sendMessageCallback;
        this.mState = 1;
        addTimer(1, 300);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 != i) {
            Slog.w(
                    "ResendCecCommandAction",
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            this.mState,
                            i,
                            ", Actual:",
                            "]",
                            new StringBuilder("Timeout in invalid state:[Expected:")));
        } else if (i2 == 1) {
            Slog.d("ResendCecCommandAction", "sendCommandWithoutRetries failed, retry");
            this.mService.sendCecCommandWithoutRetries(this.mCommand, this.mCallback);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        Slog.d("ResendCecCommandAction", "ResendCecCommandAction started");
    }
}
