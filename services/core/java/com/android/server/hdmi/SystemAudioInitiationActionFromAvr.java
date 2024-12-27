package com.android.server.hdmi;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemAudioInitiationActionFromAvr extends HdmiCecFeatureAction {
    static final int MAX_RETRY_COUNT = 5;
    public int mSendRequestActiveSourceRetryCount;
    public int mSendSetSystemAudioModeRetryCount;

    public SystemAudioInitiationActionFromAvr(HdmiCecLocalDevice hdmiCecLocalDevice) {
        super(hdmiCecLocalDevice);
        this.mSendRequestActiveSourceRetryCount = 0;
        this.mSendSetSystemAudioModeRetryCount = 0;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 1) {
            HdmiLogger.debug("Cannot get active source.", new Object[0]);
            HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
            HdmiCecLocalDeviceAudioSystem hdmiCecLocalDeviceAudioSystem =
                    (HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice;
            HdmiControlService hdmiControlService = hdmiCecLocalDeviceAudioSystem.mService;
            if (hdmiControlService.mStandbyMessageReceived) {
                Slog.d(
                        "SystemAudioInitiationActionFromAvr",
                        "Device is going to sleep, avoid to wake it up.");
                return;
            }
            if (hdmiControlService.isPlaybackDevice()) {
                hdmiCecLocalDeviceAudioSystem.mService.setAndBroadcastActiveSourceFromOneDeviceType(
                        15,
                        hdmiCecLocalDevice.getDeviceInfo().getPhysicalAddress(),
                        "SystemAudioInitiationActionFromAvr#handleActiveSourceTimeout()");
                this.mState = 2;
                HdmiCecLocalDeviceAudioSystem hdmiCecLocalDeviceAudioSystem2 =
                        (HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice;
                SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0
                        systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0 =
                                new SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0(
                                        this);
                Boolean bool = hdmiCecLocalDeviceAudioSystem2.mTvSystemAudioModeSupport;
                if (bool == null) {
                    DetectTvSystemAudioModeSupportAction detectTvSystemAudioModeSupportAction =
                            new DetectTvSystemAudioModeSupportAction(
                                    hdmiCecLocalDeviceAudioSystem2);
                    detectTvSystemAudioModeSupportAction.mSendSetSystemAudioModeRetryCount = 0;
                    detectTvSystemAudioModeSupportAction.mCallback =
                            systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0;
                    hdmiCecLocalDeviceAudioSystem2.addAndStartAction(
                            detectTvSystemAudioModeSupportAction);
                } else {
                    systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0.onResult(
                            bool.booleanValue());
                }
            } else {
                hdmiCecLocalDeviceAudioSystem.checkSupportAndSetSystemAudioMode(false);
            }
            finish(true);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.mOpcode != 130 || this.mState != 1) {
            return false;
        }
        ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        ((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice).handleActiveSource(hdmiCecMessage);
        this.mState = 2;
        HdmiCecLocalDeviceAudioSystem hdmiCecLocalDeviceAudioSystem =
                (HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice;
        SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0
                systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0 =
                        new SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0(this);
        Boolean bool = hdmiCecLocalDeviceAudioSystem.mTvSystemAudioModeSupport;
        if (bool == null) {
            DetectTvSystemAudioModeSupportAction detectTvSystemAudioModeSupportAction =
                    new DetectTvSystemAudioModeSupportAction(hdmiCecLocalDeviceAudioSystem);
            detectTvSystemAudioModeSupportAction.mSendSetSystemAudioModeRetryCount = 0;
            detectTvSystemAudioModeSupportAction.mCallback =
                    systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0;
            hdmiCecLocalDeviceAudioSystem.addAndStartAction(detectTvSystemAudioModeSupportAction);
        } else {
            systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0.onResult(
                    bool.booleanValue());
        }
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        if (((HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice)
                        .mService.getLocalActiveSource().physicalAddress
                == 65535) {
            this.mState = 1;
            addTimer(1, 2000);
            this.mService.sendCecCommand(
                    HdmiCecMessage.build(getSourceAddress(), 15, 133),
                    new SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0(this));
            return;
        }
        this.mState = 2;
        HdmiCecLocalDeviceAudioSystem hdmiCecLocalDeviceAudioSystem =
                (HdmiCecLocalDeviceAudioSystem) hdmiCecLocalDevice;
        SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0
                systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0 =
                        new SystemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0(this);
        Boolean bool = hdmiCecLocalDeviceAudioSystem.mTvSystemAudioModeSupport;
        if (bool != null) {
            systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0.onResult(
                    bool.booleanValue());
            return;
        }
        DetectTvSystemAudioModeSupportAction detectTvSystemAudioModeSupportAction =
                new DetectTvSystemAudioModeSupportAction(hdmiCecLocalDeviceAudioSystem);
        detectTvSystemAudioModeSupportAction.mSendSetSystemAudioModeRetryCount = 0;
        detectTvSystemAudioModeSupportAction.mCallback =
                systemAudioInitiationActionFromAvr$$ExternalSyntheticLambda0;
        hdmiCecLocalDeviceAudioSystem.addAndStartAction(detectTvSystemAudioModeSupportAction);
    }
}
