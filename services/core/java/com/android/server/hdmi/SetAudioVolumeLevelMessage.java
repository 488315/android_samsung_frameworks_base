package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;

public final class SetAudioVolumeLevelMessage extends HdmiCecMessage {
    public final int mAudioVolumeLevel;

    public SetAudioVolumeLevelMessage(int i, int i2, int i3, byte[] bArr) {
        super(i, i2, 115, 0, bArr);
        this.mAudioVolumeLevel = i3;
    }

    public static HdmiCecMessage build(int i, int i2, int i3) {
        byte[] bArr = {
            (byte) (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
        };
        int validateAddress = HdmiCecMessageValidator.validateAddress(i, i2, 32767, 32767);
        return validateAddress == 0
                ? new SetAudioVolumeLevelMessage(i, i2, i3, bArr)
                : new HdmiCecMessage(i, i2, 115, validateAddress, bArr);
    }
}
