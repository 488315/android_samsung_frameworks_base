package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.media.AudioDeviceAttributes;
import android.media.VolumeInfo;
import android.util.Slog;

import com.android.server.hdmi.HdmiControlService.AbsoluteVolumeChangedListener;

import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AbsoluteVolumeAudioStatusAction extends HdmiCecFeatureAction {
    public int mInitialAudioStatusRetriesLeft;
    public AudioStatus mLastAudioStatus;
    public final int mTargetAddress;

    public AbsoluteVolumeAudioStatusAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        super(hdmiCecLocalDevice);
        this.mInitialAudioStatusRetriesLeft = 2;
        this.mTargetAddress = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2;
        if (this.mState == i && (i2 = this.mInitialAudioStatusRetriesLeft) > 0) {
            this.mInitialAudioStatusRetriesLeft = i2 - 1;
            sendGiveAudioStatus();
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.mOpcode != 122 || this.mTargetAddress != hdmiCecMessage.mSource) {
            return false;
        }
        byte[] bArr = hdmiCecMessage.mParams;
        if (bArr.length == 0) {
            return false;
        }
        Map map = HdmiUtils.ADDRESS_TO_TYPE;
        boolean z = (bArr[0] & 128) == 128;
        int audioStatusVolume = HdmiUtils.getAudioStatusVolume(hdmiCecMessage);
        if (audioStatusVolume != -1) {
            AudioStatus audioStatus = new AudioStatus(audioStatusVolume, z);
            int i = audioStatus.mVolume;
            boolean z2 = audioStatus.mMute;
            int i2 = this.mState;
            HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
            if (i2 == 1) {
                HdmiControlService hdmiControlService = hdmiCecLocalDevice.mService;
                HdmiCecLocalDevice playback =
                        hdmiControlService.isPlaybackDevice()
                                ? hdmiControlService.playback()
                                : hdmiControlService.tv();
                int findAudioReceiverAddress = playback.findAudioReceiverAddress();
                hdmiControlService.assertRunOnServiceThread();
                HdmiDeviceInfo cecDeviceInfo =
                        hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(
                                findAudioReceiverAddress);
                VolumeInfo build =
                        new VolumeInfo.Builder(3)
                                .setMuted(z2)
                                .setVolumeIndex(i)
                                .setMaxVolumeIndex(100)
                                .setMinVolumeIndex(0)
                                .build();
                hdmiControlService.mAbsoluteVolumeChangedListener =
                        hdmiControlService
                        .new AbsoluteVolumeChangedListener(playback, cecDeviceInfo);
                hdmiControlService.notifyAvbMuteChange(z2);
                if (cecDeviceInfo.getDeviceFeatures().getSetAudioVolumeLevelSupport() == 1) {
                    Slog.d("HdmiControlService", "Enabling absolute volume behavior");
                    Iterator it = hdmiControlService.getAvbCapableAudioOutputDevices().iterator();
                    while (it.hasNext()) {
                        ((DefaultAudioDeviceVolumeManagerWrapper)
                                        hdmiControlService.mAudioDeviceVolumeManager)
                                .mAudioDeviceVolumeManager.setDeviceAbsoluteVolumeBehavior(
                                        (AudioDeviceAttributes) it.next(),
                                        build,
                                        hdmiControlService.mServiceThreadExecutor,
                                        hdmiControlService.mAbsoluteVolumeChangedListener,
                                        true);
                    }
                } else if (hdmiControlService.tv() != null) {
                    Slog.d("HdmiControlService", "Enabling adjust-only absolute volume behavior");
                    Iterator it2 = hdmiControlService.getAvbCapableAudioOutputDevices().iterator();
                    while (it2.hasNext()) {
                        ((DefaultAudioDeviceVolumeManagerWrapper)
                                        hdmiControlService.mAudioDeviceVolumeManager)
                                .mAudioDeviceVolumeManager
                                        .setDeviceAbsoluteVolumeAdjustOnlyBehavior(
                                                (AudioDeviceAttributes) it2.next(),
                                                build,
                                                hdmiControlService.mServiceThreadExecutor,
                                                hdmiControlService.mAbsoluteVolumeChangedListener,
                                                true);
                    }
                }
                this.mState = 2;
            } else if (i2 == 2) {
                boolean z3 = i != this.mLastAudioStatus.mVolume;
                if (z3) {
                    HdmiControlService hdmiControlService2 = hdmiCecLocalDevice.mService;
                    if (hdmiControlService2.isAbsoluteVolumeBehaviorEnabled()) {
                        Iterator it3 =
                                ((DefaultAudioManagerWrapper) hdmiControlService2.mAudioManager)
                                        .mAudioManager
                                        .getDevicesForAttributes(
                                                HdmiControlService.STREAM_MUSIC_ATTRIBUTES)
                                        .iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            if (hdmiControlService2
                                    .getAvbCapableAudioOutputDevices()
                                    .contains((AudioDeviceAttributes) it3.next())) {
                                ((DefaultAudioManagerWrapper) hdmiControlService2.mAudioManager)
                                        .mAudioManager.setStreamVolume(
                                                3,
                                                (i * hdmiControlService2.mStreamMusicMaxVolume)
                                                        / 100,
                                                hdmiControlService2.isTvDevice() ? 8193 : 8192);
                            }
                        }
                    }
                }
                if (z2 != this.mLastAudioStatus.mMute
                        || z3
                        || hdmiCecLocalDevice.mService.isTvDevice()) {
                    hdmiCecLocalDevice.mService.notifyAvbMuteChange(z2);
                }
            }
            this.mLastAudioStatus = audioStatus;
        }
        return true;
    }

    public final void sendGiveAudioStatus() {
        addTimer(this.mState, 2000);
        this.mService.sendCecCommand(
                HdmiCecMessage.build(getSourceAddress(), this.mTargetAddress, 113), null);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mState = 1;
        sendGiveAudioStatus();
    }
}
