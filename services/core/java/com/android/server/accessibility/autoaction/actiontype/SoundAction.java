package com.android.server.accessibility.autoaction.actiontype;

import android.media.AudioManager;

public final class SoundAction extends CornerActionType {
    public AudioManager mAudioManager;
    public String mType;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        char c;
        if (this.mAudioManager != null) {
            String str = this.mType;
            str.getClass();
            switch (str.hashCode()) {
                case -1428385405:
                    if (str.equals("ringtone_volume_up")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -417942503:
                    if (str.equals("sound_vibrate_mute")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 452226764:
                    if (str.equals("media_volume_down")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1158011717:
                    if (str.equals("media_volume_up")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1710656906:
                    if (str.equals("ringtone_volume_down")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mAudioManager.adjustStreamVolume(2, 0, 5);
                    this.mAudioManager.adjustStreamVolume(2, 1, 33554432);
                    return;
                case 1:
                    int ringerModeInternal = this.mAudioManager.getRingerModeInternal() - 1;
                    this.mAudioManager.setRingerModeInternal(
                            ringerModeInternal >= 0 ? ringerModeInternal : 2);
                    return;
                case 2:
                    this.mAudioManager.adjustStreamVolume(3, 0, 1);
                    this.mAudioManager.adjustStreamVolume(3, -1, 33554432);
                    return;
                case 3:
                    this.mAudioManager.adjustStreamVolume(3, 0, 1);
                    this.mAudioManager.adjustStreamVolume(3, 1, 33554432);
                    return;
                case 4:
                    this.mAudioManager.adjustStreamVolume(2, 0, 5);
                    this.mAudioManager.adjustStreamVolume(2, -1, 33554432);
                    return;
                default:
                    throw new IllegalArgumentException("Wrong Sound Action Type");
            }
        }
    }
}
