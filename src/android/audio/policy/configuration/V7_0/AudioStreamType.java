package android.audio.policy.configuration.V7_0;

import com.samsung.android.media.AudioTag;

/* loaded from: classes.dex */
public enum AudioStreamType {
    AUDIO_STREAM_VOICE_CALL("AUDIO_STREAM_VOICE_CALL"),
    AUDIO_STREAM_SYSTEM("AUDIO_STREAM_SYSTEM"),
    AUDIO_STREAM_RING(AudioTag.AUDIO_STREAM_RING),
    AUDIO_STREAM_MUSIC("AUDIO_STREAM_MUSIC"),
    AUDIO_STREAM_ALARM("AUDIO_STREAM_ALARM"),
    AUDIO_STREAM_NOTIFICATION("AUDIO_STREAM_NOTIFICATION"),
    AUDIO_STREAM_BLUETOOTH_SCO("AUDIO_STREAM_BLUETOOTH_SCO"),
    AUDIO_STREAM_ENFORCED_AUDIBLE("AUDIO_STREAM_ENFORCED_AUDIBLE"),
    AUDIO_STREAM_DTMF("AUDIO_STREAM_DTMF"),
    AUDIO_STREAM_TTS("AUDIO_STREAM_TTS"),
    AUDIO_STREAM_ACCESSIBILITY("AUDIO_STREAM_ACCESSIBILITY"),
    AUDIO_STREAM_ASSISTANT("AUDIO_STREAM_ASSISTANT"),
    AUDIO_STREAM_REROUTING("AUDIO_STREAM_REROUTING"),
    AUDIO_STREAM_PATCH("AUDIO_STREAM_PATCH"),
    AUDIO_STREAM_CALL_ASSISTANT("AUDIO_STREAM_CALL_ASSISTANT");

    private final String rawName;

    AudioStreamType(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static AudioStreamType fromString(String str) {
        for (AudioStreamType audioStreamType : values()) {
            if (audioStreamType.getRawName().equals(str)) {
                return audioStreamType;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
