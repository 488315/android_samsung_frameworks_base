package com.android.systemui.bixby2.util;

import android.content.Context;
import android.media.AudioManager;
import android.view.KeyEvent;
import com.sec.ims.presence.ServiceTuple;

public class AudioManagerWrapper {
    private final AudioManager mAudioManager;

    public AudioManagerWrapper(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    private int getDefaultStream(Context context) {
        return isMediaVolumeOn(context) ? 3 : 2;
    }

    private boolean isMediaVolumeOn(Context context) {
        new SemSoundAssistantManagerWrapper(context);
        return false;
    }

    private boolean isMusicActive() {
        return this.mAudioManager.isMusicActive();
    }

    private boolean isStreamValid(int i) {
        return i == 2 || i == 3 || i == 5 || i == 1;
    }

    public void adjustStreamVolume(int i, int i2, int i3) {
        this.mAudioManager.adjustStreamVolume(i, i2, i3);
    }

    public void dispatchMediaKeyEvent(KeyEvent keyEvent) {
        this.mAudioManager.dispatchMediaKeyEvent(keyEvent);
    }

    public int getAdjustedStreamType(Context context) {
        int semGetActiveStreamType = AudioManager.semGetActiveStreamType();
        return isStreamValid(semGetActiveStreamType) ? semGetActiveStreamType : getDefaultStream(context);
    }

    public int getRingerMode() {
        return this.mAudioManager.getRingerModeInternal();
    }

    public int getStreamMaxVolume(int i) {
        return this.mAudioManager.getStreamMaxVolume(i);
    }

    public int getStreamMinVolume(int i) {
        return this.mAudioManager.getStreamMinVolumeInt(i);
    }

    public int getStreamVolume(int i) {
        return this.mAudioManager.getStreamVolume(i);
    }

    public boolean isAllStreamMute() {
        return getRingerMode() != 2 && isStreamMute(3) && isStreamMute(11);
    }

    public boolean isCurrentDeviceTypeBluetooth() {
        return this.mAudioManager.semGetCurrentDeviceType() == 8;
    }

    public boolean isInCall() {
        return this.mAudioManager.getModeInternal() != 0;
    }

    public boolean isStreamMute(int i) {
        return getStreamVolume(i) == getStreamMinVolume(i);
    }

    public void setRingerMode(int i) {
        this.mAudioManager.setRingerMode(i);
    }

    public void setStreamVolume(int i, int i2, int i3) {
        this.mAudioManager.setStreamVolume(i, i2, i3);
    }

    public boolean shouldShowRingtoneVolume() {
        return this.mAudioManager.shouldShowRingtoneVolume();
    }
}
