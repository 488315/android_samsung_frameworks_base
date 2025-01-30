package com.android.settingslib.volume;

import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.support.v4.media.AbstractC0000x2c234b15;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Util {
    public static final int[] AUDIO_MANAGER_FLAGS = {1, 16, 4, 2, 8, 2048, 128, 4096, 1024};
    public static final String[] AUDIO_MANAGER_FLAG_NAMES = {"SHOW_UI", "VIBRATE", "PLAY_SOUND", "ALLOW_RINGER_MODES", "REMOVE_SOUND_AND_VIBRATE", "SHOW_VIBRATE_HINT", "SHOW_SILENT_HINT", "FROM_KEY", "SHOW_UI_WARNINGS"};

    public static String bitFieldToString(int i, String[] strArr, int[] iArr) {
        if (i == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if ((iArr[i2] & i) != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(strArr[i2]);
            }
            i &= ~iArr[i2];
        }
        if (i != 0) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append("UNKNOWN_");
            sb.append(i);
        }
        return sb.toString();
    }

    public static String playbackInfoToString(MediaController.PlaybackInfo playbackInfo) {
        if (playbackInfo == null) {
            return null;
        }
        int playbackType = playbackInfo.getPlaybackType();
        String m0m = playbackType != 1 ? playbackType != 2 ? AbstractC0000x2c234b15.m0m("UNKNOWN_", playbackType) : "REMOTE" : "LOCAL";
        int volumeControl = playbackInfo.getVolumeControl();
        return String.format("PlaybackInfo[vol=%s,max=%s,type=%s,vc=%s],atts=%s", Integer.valueOf(playbackInfo.getCurrentVolume()), Integer.valueOf(playbackInfo.getMaxVolume()), m0m, volumeControl != 0 ? volumeControl != 1 ? volumeControl != 2 ? AbstractC0000x2c234b15.m0m("VOLUME_CONTROL_UNKNOWN_", volumeControl) : "VOLUME_CONTROL_ABSOLUTE" : "VOLUME_CONTROL_RELATIVE" : "VOLUME_CONTROL_FIXED", playbackInfo.getAudioAttributes());
    }

    public static String playbackStateToString(PlaybackState playbackState) {
        if (playbackState == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int state = playbackState.getState();
        sb.append(state != 0 ? state != 1 ? state != 2 ? state != 3 ? AbstractC0000x2c234b15.m0m("UNKNOWN_", state) : "STATE_PLAYING" : "STATE_PAUSED" : "STATE_STOPPED" : "STATE_NONE");
        sb.append(" ");
        sb.append(playbackState);
        return sb.toString();
    }
}
