package com.samsung.android.server.audio.utils;

import android.media.AudioSystem;
import android.os.IInstalld;
import android.util.Log;
import com.android.server.SystemService;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.SemAudioSystem;

/* loaded from: classes2.dex */
public abstract class AudioStreamUtils {
    public static int getActiveStreams() {
        try {
            return Integer.parseInt(SemAudioSystem.getPolicyParameters("l_stream_active"));
        } catch (NumberFormatException e) {
            Log.e("AS.AudioStreamUtils", "Can't get active stream", e);
            return -1;
        }
    }

    public static int getActiveStreamTypeInternal(int i, boolean z) {
        int activeStreams = getActiveStreams();
        if ((activeStreams & 1024) != 0) {
            return 10;
        }
        if ((activeStreams & 1) != 0) {
            return 0;
        }
        if ((activeStreams & SystemService.PHASE_DEVICE_SPECIFIC_SERVICES_READY) != 0 || AudioSystem.isStreamActiveRemotely(3, 0)) {
            Log.v("AS.AudioStreamUtils", "getActiveStreamTypeInternal: forcing STREAM_MUSIC");
            return 3;
        }
        if ((activeStreams & 16) != 0) {
            return 4;
        }
        if ((activeStreams & 32) != 0) {
            return 5;
        }
        if ((activeStreams & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
            Log.v("AS.AudioStreamUtils", "getActiveStreamTypeInternal: forcing STREAM_ASSISTANT...");
            return 11;
        }
        if (i == 1) {
            if (z && (activeStreams & 4) == 0) {
                Log.v("AS.AudioStreamUtils", "getActiveStreamTypeInternal: only adjust media volume");
                return 3;
            }
            Log.v("AS.AudioStreamUtils", "getActiveStreamTypeInternal: forcing STREAM_RING b/c default");
            return 2;
        }
        if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION && !z) {
            Log.v("AS.AudioStreamUtils", "getActiveStreamTypeInternal: using STREAM_NOTIFICATION as default");
            return 5;
        }
        Log.v("AS.AudioStreamUtils", "getActiveStreamTypeInternal: using STREAM_MUSIC as default");
        return 3;
    }
}
