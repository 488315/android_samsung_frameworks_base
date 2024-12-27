package com.samsung.android.server.audio.utils;

import android.media.AudioSystem;
import android.util.Log;

import com.android.server.SystemService;

import com.samsung.android.audio.Rune;
import com.samsung.android.media.SemAudioSystem;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AudioStreamUtils {
    public static int getActiveStreamTypeInternal(int i, boolean z) {
        int i2;
        try {
            i2 = Integer.parseInt(SemAudioSystem.getPolicyParameters("l_stream_active"));
        } catch (NumberFormatException e) {
            Log.e("AS.AudioStreamUtils", "Can't get active stream", e);
            i2 = -1;
        }
        if ((i2 & 1024) != 0) {
            return 10;
        }
        if ((i2 & 1) != 0) {
            return 0;
        }
        if ((i2 & SystemService.PHASE_DEVICE_SPECIFIC_SERVICES_READY) != 0
                || AudioSystem.isStreamActiveRemotely(3, 0)) {
            Log.v("AS.AudioStreamUtils", "getActiveStreamTypeInternal: forcing STREAM_MUSIC");
            return 3;
        }
        if ((i2 & 16) != 0) {
            return 4;
        }
        if ((i2 & 32) != 0) {
            return 5;
        }
        if ((i2 & 2048) != 0) {
            Log.v(
                    "AS.AudioStreamUtils",
                    "getActiveStreamTypeInternal: forcing STREAM_ASSISTANT...");
            return 11;
        }
        if (i == 1) {
            if (z && (i2 & 4) == 0) {
                Log.v(
                        "AS.AudioStreamUtils",
                        "getActiveStreamTypeInternal: only adjust media volume");
                return 3;
            }
            Log.v(
                    "AS.AudioStreamUtils",
                    "getActiveStreamTypeInternal: forcing STREAM_RING b/c default");
            return 2;
        }
        if (!Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION || z) {
            Log.v(
                    "AS.AudioStreamUtils",
                    "getActiveStreamTypeInternal: using STREAM_MUSIC as default");
            return 3;
        }
        Log.v(
                "AS.AudioStreamUtils",
                "getActiveStreamTypeInternal: using STREAM_NOTIFICATION as default");
        return 5;
    }
}
