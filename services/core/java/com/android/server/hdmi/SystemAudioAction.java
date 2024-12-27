package com.android.server.hdmi;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Pair;
import android.util.Slog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SystemAudioAction extends HdmiCecFeatureAction {
    public final int mAvrLogicalAddress;
    public int mSendRetryCount;
    public boolean mTargetAudioStatus;

    public SystemAudioAction(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            int i,
            IHdmiControlCallback iHdmiControlCallback,
            boolean z) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mSendRetryCount = 0;
        if (!HdmiUtils.verifyAddressType(i, 5)) {
            Slog.w("SystemAudioAction", "Device type mismatch, stop the action.");
            finish(true);
        }
        this.mAvrLogicalAddress = i;
        this.mTargetAudioStatus = z;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 2) {
            if (this.mTargetAudioStatus) {
                int i3 = this.mSendRetryCount;
                this.mSendRetryCount = i3 + 1;
                if (i3 < 2) {
                    sendSystemAudioModeRequest();
                    return;
                }
            }
            HdmiLogger.debug("[T]:wait for <Set System Audio Mode>.", new Object[0]);
            setSystemAudioMode(false);
            finishWithCallback(1);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int i = hdmiCecMessage.mSource;
        int i2 = this.mAvrLogicalAddress;
        if (i != i2 || this.mState != 2) {
            return false;
        }
        int i3 = hdmiCecMessage.mOpcode;
        if (i3 == 0 && (hdmiCecMessage.mParams[0] & 255) == 112) {
            HdmiLogger.debug("Failed to start system audio mode request.", new Object[0]);
            setSystemAudioMode(false);
            finishWithCallback(5);
            return true;
        }
        if (i3 == 114 && HdmiUtils.checkCommandSource(hdmiCecMessage, i2, "SystemAudioAction")) {
            boolean parseCommandParamSystemAudioStatus =
                    HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
            if (parseCommandParamSystemAudioStatus == this.mTargetAudioStatus) {
                setSystemAudioMode(parseCommandParamSystemAudioStatus);
                finish(true);
                return true;
            }
            HdmiLogger.debug(
                    "Unexpected system audio mode request:" + parseCommandParamSystemAudioStatus,
                    new Object[0]);
            finishWithCallback(5);
        }
        return false;
    }

    public final void sendSystemAudioModeRequest() {
        List actions = this.mSource.getActions(RoutingControlAction.class);
        if (actions.isEmpty()) {
            sendSystemAudioModeRequestInternal();
            return;
        }
        this.mState = 1;
        RoutingControlAction routingControlAction = (RoutingControlAction) actions.get(0);
        Runnable runnable =
                new Runnable() { // from class: com.android.server.hdmi.SystemAudioAction.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemAudioAction.this.sendSystemAudioModeRequestInternal();
                    }
                };
        if (routingControlAction.mOnFinishedCallbacks == null) {
            routingControlAction.mOnFinishedCallbacks = new ArrayList();
        }
        routingControlAction.mOnFinishedCallbacks.add(Pair.create(this, runnable));
    }

    public final void sendSystemAudioModeRequestInternal() {
        int activePath;
        int sourceAddress = getSourceAddress();
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) this.mSource;
        if (HdmiUtils.isValidAddress(
                hdmiCecLocalDeviceTv.mService.getLocalActiveSource().logicalAddress)) {
            activePath = hdmiCecLocalDeviceTv.mService.getLocalActiveSource().physicalAddress;
        } else {
            activePath = hdmiCecLocalDeviceTv.getActivePath();
            if (activePath == 65535) {
                activePath = 0;
            }
        }
        boolean z = this.mTargetAudioStatus;
        int i = this.mAvrLogicalAddress;
        this.mService.sendCecCommand(
                z
                        ? HdmiCecMessage.build(
                                sourceAddress,
                                i,
                                112,
                                HdmiCecMessageBuilder.physicalAddressToParam(activePath))
                        : HdmiCecMessage.build(sourceAddress, i, 112),
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.SystemAudioAction.2
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i2) {
                        if (i2 != 0) {
                            HdmiLogger.debug(
                                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            i2, "Failed to send <System Audio Mode Request>:"),
                                    new Object[0]);
                            SystemAudioAction systemAudioAction = SystemAudioAction.this;
                            systemAudioAction.setSystemAudioMode(false);
                            systemAudioAction.finishWithCallback(7);
                        }
                    }
                });
        this.mState = 2;
        addTimer(2, this.mTargetAudioStatus ? 5000 : 2000);
    }

    public final void setSystemAudioMode(boolean z) {
        ((HdmiCecLocalDeviceTv) this.mSource).setSystemAudioMode$1(z);
    }
}
