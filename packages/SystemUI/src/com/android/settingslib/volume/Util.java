package com.android.settingslib.volume;

import android.media.AudioAttributes;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String m = playbackType != 1 ? playbackType != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(playbackType, "UNKNOWN_") : "REMOTE" : "LOCAL";
        int volumeControl = playbackInfo.getVolumeControl();
        String m2 = volumeControl != 0 ? volumeControl != 1 ? volumeControl != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(volumeControl, "VOLUME_CONTROL_UNKNOWN_") : "VOLUME_CONTROL_ABSOLUTE" : "VOLUME_CONTROL_RELATIVE" : "VOLUME_CONTROL_FIXED";
        int currentVolume = playbackInfo.getCurrentVolume();
        int maxVolume = playbackInfo.getMaxVolume();
        AudioAttributes audioAttributes = playbackInfo.getAudioAttributes();
        StringBuilder m3 = MutableObjectList$$ExternalSyntheticOutline0.m(currentVolume, maxVolume, "PlaybackInfo[vol=", ",max=", ",type=");
        MoveResult$$ExternalSyntheticOutline0.m(m3, m, ",vc=", m2, "],atts=");
        m3.append(audioAttributes);
        return m3.toString();
    }

    public static String playbackStateToString(PlaybackState playbackState) {
        if (playbackState == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int state = playbackState.getState();
        sb.append(state != 0 ? state != 1 ? state != 2 ? state != 3 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(state, "UNKNOWN_") : "STATE_PLAYING" : "STATE_PAUSED" : "STATE_STOPPED" : "STATE_NONE");
        sb.append(" ");
        sb.append(playbackState);
        return sb.toString();
    }
}
