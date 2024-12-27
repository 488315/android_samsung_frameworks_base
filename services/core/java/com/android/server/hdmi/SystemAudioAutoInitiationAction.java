package com.android.server.hdmi;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemAudioAutoInitiationAction extends HdmiCecFeatureAction {
    static final int RETRIES_ON_TIMEOUT = 1;
    public final int mAvrAddress;
    public int mRetriesOnTimeOut;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.SystemAudioAutoInitiationAction$1, reason: invalid class name */
    public final class AnonymousClass1 implements HdmiControlService.SendMessageCallback {
        public AnonymousClass1() {}

        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
        public final void onSendCompleted(int i) {
            if (i != 0) {
                SystemAudioAutoInitiationAction.this.handleSystemAudioModeStatusTimeout();
            }
        }
    }

    public SystemAudioAutoInitiationAction(HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv, int i) {
        super(hdmiCecLocalDeviceTv);
        this.mRetriesOnTimeOut = 1;
        this.mAvrAddress = i;
    }

    public final void handleSystemAudioModeStatusTimeout() {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
        if (!((hdmiCecLocalDeviceTv.hasAction(SystemAudioActionFromTv.class)
                        || hdmiCecLocalDeviceTv.hasAction(SystemAudioActionFromAvr.class))
                ? false
                : true)) {
            HdmiLogger.debug(
                    "Cannot change system audio mode in auto initiation action.", new Object[0]);
            finish(true);
        } else {
            HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv2 = (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
            hdmiCecLocalDevice.addAndStartAction(
                    new SystemAudioActionFromTv(
                            hdmiCecLocalDeviceTv2,
                            this.mAvrAddress,
                            null,
                            hdmiCecLocalDeviceTv2.isSystemAudioControlFeatureEnabled()));
            finish(true);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 1) {
            int i3 = this.mRetriesOnTimeOut;
            if (i3 <= 0) {
                handleSystemAudioModeStatusTimeout();
                return;
            }
            this.mRetriesOnTimeOut = i3 - 1;
            addTimer(i2, 2000);
            sendCommand(
                    HdmiCecMessage.build(getSourceAddress(), this.mAvrAddress, 125),
                    new AnonymousClass1());
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState == 1) {
            int i = hdmiCecMessage.mSource;
            int i2 = this.mAvrAddress;
            if (i2 == i && hdmiCecMessage.mOpcode == 126) {
                boolean parseCommandParamSystemAudioStatus =
                        HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
                HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
                HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv =
                        (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
                if (hdmiCecLocalDeviceTv.hasAction(SystemAudioActionFromTv.class)
                        || hdmiCecLocalDeviceTv.hasAction(SystemAudioActionFromAvr.class)) {
                    HdmiLogger.debug(
                            "Cannot change system audio mode in auto initiation action.",
                            new Object[0]);
                    finish(true);
                } else {
                    HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv2 =
                            (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
                    boolean isSystemAudioControlFeatureEnabled =
                            hdmiCecLocalDeviceTv2.isSystemAudioControlFeatureEnabled();
                    if (parseCommandParamSystemAudioStatus != isSystemAudioControlFeatureEnabled) {
                        hdmiCecLocalDevice.addAndStartAction(
                                new SystemAudioActionFromTv(
                                        hdmiCecLocalDeviceTv2,
                                        i2,
                                        null,
                                        isSystemAudioControlFeatureEnabled));
                    } else {
                        hdmiCecLocalDeviceTv2.setSystemAudioMode$1(
                                isSystemAudioControlFeatureEnabled);
                    }
                    finish(true);
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        addTimer(1, 2000);
        sendCommand(
                HdmiCecMessage.build(getSourceAddress(), this.mAvrAddress, 125),
                new AnonymousClass1());
    }
}
