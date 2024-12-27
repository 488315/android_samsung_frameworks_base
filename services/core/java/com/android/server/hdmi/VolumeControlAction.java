package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;

import java.util.Map;

public final class VolumeControlAction extends HdmiCecFeatureAction {
    public final int mAvrAddress;
    public boolean mIsVolumeUp;
    public boolean mLastAvrMute;
    public int mLastAvrVolume;
    public long mLastKeyUpdateTime;
    public boolean mSentKeyPressed;

    public VolumeControlAction(HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv, int i, boolean z) {
        super(hdmiCecLocalDeviceTv);
        this.mAvrAddress = i;
        this.mIsVolumeUp = z;
        this.mLastAvrVolume = -1;
        this.mLastAvrMute = false;
        this.mSentKeyPressed = false;
        this.mLastKeyUpdateTime = System.currentTimeMillis();
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void clear() {
        super.clear();
        if (this.mSentKeyPressed) {
            sendCommand(HdmiCecMessage.build(getSourceAddress(), this.mAvrAddress, 69));
            this.mSentKeyPressed = false;
        }
        int i = this.mLastAvrVolume;
        if (i != -1) {
            ((HdmiCecLocalDeviceTv) this.mSource).setAudioStatus(i, this.mLastAvrMute);
            this.mLastAvrVolume = -1;
            this.mLastAvrMute = false;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        if (i != 1) {
            return;
        }
        if (System.currentTimeMillis() - this.mLastKeyUpdateTime >= 300) {
            finish(true);
            return;
        }
        sendVolumeKeyPressed();
        ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
        addTimer(1, 300);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || hdmiCecMessage.mSource != this.mAvrAddress) {
            return false;
        }
        byte[] bArr = hdmiCecMessage.mParams;
        int i = hdmiCecMessage.mOpcode;
        if (i == 0) {
            if ((bArr[0] & 255) != 68) {
                return false;
            }
            finish(true);
            return true;
        }
        if (i != 122) {
            return false;
        }
        Map map = HdmiUtils.ADDRESS_TO_TYPE;
        boolean z = (bArr[0] & 128) == 128;
        int audioStatusVolume = HdmiUtils.getAudioStatusVolume(hdmiCecMessage);
        this.mLastAvrVolume = audioStatusVolume;
        this.mLastAvrMute = z;
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        if (!z) {
            DefaultAudioManagerWrapper defaultAudioManagerWrapper =
                    (DefaultAudioManagerWrapper)
                            ((HdmiCecLocalDeviceTv) hdmiCecLocalDevice).mService.mAudioManager;
            int streamVolume = defaultAudioManagerWrapper.mAudioManager.getStreamVolume(3);
            if (!this.mIsVolumeUp) {}
            return true;
        }
        HdmiLogger.debug(
                "Force volume change[mute:%b, volume=%d]",
                Boolean.valueOf(z), Integer.valueOf(audioStatusVolume));
        ((HdmiCecLocalDeviceTv) hdmiCecLocalDevice).setAudioStatus(audioStatusVolume, z);
        this.mLastAvrVolume = -1;
        this.mLastAvrMute = false;
        return true;
    }

    public final void sendVolumeKeyPressed() {
        this.mService.sendCecCommand(
                HdmiCecMessage.build(
                        getSourceAddress(),
                        this.mAvrAddress,
                        68,
                        new byte[] {
                            (byte)
                                    ((this.mIsVolumeUp ? 65 : 66)
                                            & IDnsResolverUnsolicitedEventListener
                                                    .DNS_HEALTH_RESULT_TIMEOUT)
                        }),
                null);
        this.mSentKeyPressed = true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        sendVolumeKeyPressed();
        ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
        addTimer(1, 300);
    }
}
