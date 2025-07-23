package android.speech.tts;

import android.util.EventLog;

/* loaded from: classes3.dex */
public class EventLogTags {
    public static final int TTS_SPEAK_FAILURE = 76002;
    public static final int TTS_SPEAK_SUCCESS = 76001;
    public static final int TTS_V2_SPEAK_FAILURE = 76004;
    public static final int TTS_V2_SPEAK_SUCCESS = 76003;

    private EventLogTags() {
    }

    public static void writeTtsSpeakSuccess(String str, int i, int i2, int i3, String str2, int i4, int i5, long j, long j2, long j3) {
        EventLog.writeEvent(TTS_SPEAK_SUCCESS, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str2, Integer.valueOf(i4), Integer.valueOf(i5), Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3));
    }

    public static void writeTtsSpeakFailure(String str, int i, int i2, int i3, String str2, int i4, int i5) {
        EventLog.writeEvent(TTS_SPEAK_FAILURE, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str2, Integer.valueOf(i4), Integer.valueOf(i5));
    }

    public static void writeTtsV2SpeakSuccess(String str, int i, int i2, int i3, String str2, long j, long j2, long j3) {
        EventLog.writeEvent(TTS_V2_SPEAK_SUCCESS, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str2, Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3));
    }

    public static void writeTtsV2SpeakFailure(String str, int i, int i2, int i3, String str2, int i4) {
        EventLog.writeEvent(TTS_V2_SPEAK_FAILURE, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str2, Integer.valueOf(i4));
    }
}
